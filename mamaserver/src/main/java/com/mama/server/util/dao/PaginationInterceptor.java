package com.mama.server.util.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页查询
 * 1.拦截查询SQL，进行重写SQL
 * 2.拦截结果集，获取查询数据
 * @author majiafei
 *
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare" , args = {Connection.class}),
		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PaginationInterceptor implements Interceptor
{
	//记录日志
	private static final Logger LOGGER = LoggerFactory.getLogger(PaginationInterceptor.class);
	
	public static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();

	/**
	 * 开始分页
	 * @param pageNum
	 * @param pageSize
	 */
	public static void startPage(int pageNum, int pageSize)
	{
		localPage.set(new Page(pageNum, pageSize)); 
	}
	
	/** 
     * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage 
     * @return 
     */  
    public static Page endPage() {  
        Page page = localPage.get();  
        localPage.remove();  
        return page;  
    }
    
    @Override
	public Object intercept(Invocation invocation) throws Throwable
	{
    	//如果不分页则跳过
		if (localPage.get() == null) {
			return invocation.proceed();
		}
		
		if (invocation.getTarget() instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
			MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
			// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环  
            // 可以分离出最原始的的目标类)  
            while (metaObject.hasGetter("h")) {  
                Object object = metaObject.getValue("h");  
                metaObject = SystemMetaObject.forObject(object);  
            }  
            // 分离最后一个代理对象的目标类  
            while (metaObject.hasGetter("target")) {  
                Object object = metaObject.getValue("target");  
                metaObject = SystemMetaObject.forObject(object);  
            }  
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");  
            //分页信息if (localPage.get() != null) {  
            Page page = localPage.get();  
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");  
            // 分页参数作为参数对象parameterObject的一个属性  
            String sql = boundSql.getSql();
            // 重写sql  
            String pageSql = buildPageSql(sql, page);
            //重写分页sql  
            metaObject.setValue("delegate.boundSql.sql", pageSql);  
            Connection connection = (Connection) invocation.getArgs()[0];  
            // 重设分页参数里的总页数等  
            setPageParameter(sql, connection, mappedStatement, boundSql, page);  
            // 将执行权交给下一个拦截器  
            return invocation.proceed();  
		}
		else if (invocation.getTarget() instanceof ResultSetHandler) {  
            Object result = invocation.proceed();  
            Page page = localPage.get();  
            page.setResult((List) result);  
            return result;  
        }  
		return null;
	}
	
	private void setPageParameter(String sql, Connection connection,
			MappedStatement mappedStatement, BoundSql boundSql, Page page)
	{
		String countSql = "select count(*) from (" + sql + ") t";
		
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {  
            countStmt = connection.prepareStatement(countSql);  
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,  
                    boundSql.getParameterMappings(), boundSql.getParameterObject());  
            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, countBS.getParameterObject(), boundSql);  
            parameterHandler.setParameters(countStmt);  
            rs = countStmt.executeQuery();  
            int totalCount = 0;  
            if (rs.next()) {  
                totalCount = rs.getInt(1);  
            }  
            page.setTotal(totalCount);  
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);  
            page.setPages(totalPage);  
        } catch (SQLException e) {  
            LOGGER.error("Ignore this exception", e);  
        } finally {  
            try {  
                rs.close();  
            } catch (SQLException e) {  
            	LOGGER.error("Ignore this exception", e);  
            }  
            try {  
                countStmt.close();  
            } catch (SQLException e) {  
            	LOGGER.error("Ignore this exception", e);  
            }  
        }  
		
	}

	/**
	 * 构造SQL分页查询语句
	 * @param sql
	 * @param page
	 * @return
	 */
	private String buildPageSql(String sql, Page page)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(sql)
		  .append(" limit ")
		  .append(page.startRow)
		  .append(",")
		  .append(page.pageSize);
				  
		return sb.toString();
	}

	@Override
	public Object plugin(Object target)
	{
		if (target instanceof StatementHandler || target instanceof ResultSetHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }  
	}

	@Override
	public void setProperties(Properties arg0)
	{
		
	}
	
	/**
	 * 分页对象
	 * @author majiafei
	 *
	 * @param <E>
	 */
    public static class Page<E> 
    {  
        private int pageNum;  
        private int pageSize;  
        private int startRow;  
        private int endRow;  
        private long total;  
        private int pages;  
        private List<E> result;  
  
        public Page(int pageNum, int pageSize) {  
            this.pageNum = pageNum;  
            this.pageSize = pageSize;  
            this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;  
            this.endRow = pageNum * pageSize;  
        }  
  
        public List<E> getResult() {  
            return result;  
        }  
  
        public void setResult(List<E> result) {  
            this.result = result;  
        }  
  
        public int getPages() {  
            return pages;  
        }  
  
        public void setPages(int pages) {  
            this.pages = pages;  
        }  
  
        public int getEndRow() {  
            return endRow;  
        }  
  
        public void setEndRow(int endRow) {  
            this.endRow = endRow;  
        }  
  
        public int getPageNum() {  
            return pageNum;  
        }  
  
        public void setPageNum(int pageNum) {  
            this.pageNum = pageNum;  
        }  
  
        public int getPageSize() {  
            return pageSize;  
        }  
  
        public void setPageSize(int pageSize) {  
            this.pageSize = pageSize;  
        }  
  
        public int getStartRow() {  
            return startRow;  
        }  
  
        public void setStartRow(int startRow) {  
            this.startRow = startRow;  
        }  
  
        public long getTotal() {  
            return total;  
        }  
  
        public void setTotal(long total) {  
            this.total = total;  
        }  
  
        @Override  
        public String toString() {  
            return "Page{" +  
                    "pageNum=" + pageNum +  
                    ", pageSize=" + pageSize +  
                    ", startRow=" + startRow +  
                    ", endRow=" + endRow +  
                    ", total=" + total +  
                    ", pages=" + pages +  
                    '}';  
        }  
    }  
}
