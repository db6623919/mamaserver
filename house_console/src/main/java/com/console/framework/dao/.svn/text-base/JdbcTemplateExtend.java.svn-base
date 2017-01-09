package com.console.framework.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * @description JdbcTemplate扩展
 * @author yangy
 * @version 1.0
 * @date 2012-08-22
 */
public class JdbcTemplateExtend extends JdbcTemplate {
	private DataSource dataSource;

	/**
	 * 默认构造器，调用此方法初始化，需要调用setDataSource设置数据源
	 */
	public JdbcTemplateExtend() {
	}

	/**
	 * 初始构造器
	 * 
	 * @param dataSource
	 *            数据源
	 */
	public JdbcTemplateExtend(DataSource dataSource) {
		this.dataSource = dataSource;
		super.setDataSource(dataSource);
	}

	/**
	 * 普通分页查询<br>
	 * 
	 * @param sql
	 *            查询的sql语句（从第startRow页开始，获取rowsCount条记录）
	 * @param startRow
	 *            起始页
	 * @param rowsCount
	 *            获取的行数
	 * @return
	 * @throws DataAccessException
	 */

	public Pagination queryForListPage(String sql, int startPage, int pageSize) throws DataAccessException {

		// 计算开始的行数
		int startRow = (startPage - 1) * pageSize + 1;

		Pagination Pagination = new Pagination();
		// 查询总条数
		Pagination.paging(startPage, pageSize, queryForInt("select count(1) from (" + sql + ")"));
		// 查询记录
		Pagination.setList(queryForListPage(sql, startRow, pageSize, getColumnMapRowMapper()));
		return Pagination;
	}

	/**
	 * 普通分页查询<br>
	 * 
	 * @param sql
	 *            查询的sql语句（从第startRow页开始，获取rowsCount条记录）
	 * @param args
	 *            替换？参数
	 * @param startRow
	 *            起始页
	 * @param rowsCount
	 *            获取的行数
	 * @return
	 * @throws DataAccessException
	 */

	public Pagination queryForListPage(String sql, Object[] args, int startPage, int pageSize) throws DataAccessException {

		// 计算开始的行数
		int startRow = (startPage - 1) * pageSize + 1;

		Pagination Pagination = new Pagination();
		// 查询总条数
		Pagination.paging(startPage, pageSize, queryForInt("select count(1) from (" + sql + ")", args));
		// 查询记录
		Pagination.setList(queryForListPage(sql, args, startRow, pageSize, getColumnMapRowMapper()));
		return Pagination;
	}

	/**
	 * 普通分页查询<br>
	 * 
	 * @param sql
	 *            查询的sql语句（从第startRow条记录开始，获取rowsCount条记录）
	 * @param startRow
	 *            起始行
	 * @param rowsCount
	 *            获取的行数
	 * @return
	 * @throws DataAccessException
	 */

	public Pagination queryForListPage2(String sql, int startRow, int pageSize) throws DataAccessException {
		Pagination Pagination = new Pagination();
		// 查询总条数
		Pagination.setTotal_count(queryForInt("select count(1) from (" + sql + ")"));
		// 查询记录
		Pagination.setList(queryForListPage(sql, startRow, pageSize, getColumnMapRowMapper()));
		return Pagination;
	}

	/**
	 * 普通分页查询<br>
	 * 
	 * @param sql
	 *            查询的sql语句（从第startRow条记录开始，获取rowsCount条记录）
	 * @param args
	 *            替换？参数
	 * @param startRow
	 *            起始行
	 * @param rowsCount
	 *            获取的行数
	 * @return
	 * @throws DataAccessException
	 */

	public Pagination queryForListPage2(String sql, Object[] args, int startRow, int pageSize) throws DataAccessException {
		Pagination Pagination = new Pagination();
		// 查询总条数
		Pagination.setTotal_count(queryForInt("select count(1) from (" + sql + ")", args));
		// 查询记录
		Pagination.setList(queryForListPage(sql, args, startRow, pageSize, getColumnMapRowMapper()));
		return Pagination;
	}

	public List<?> queryForListPage(String sql, int startRow, int pageSize, RowMapper<?> rowMapper) throws DataAccessException {
		return (List<?>) query(sql, new SplitPageResultSetExtractor(rowMapper, startRow, pageSize));
	}

	public List<?> queryForListPage(String sql, Object[] args, int startRow, int pageSize, RowMapper<?> rowMapper) throws DataAccessException {
		return (List<?>) query(sql, args, new SplitPageResultSetExtractor(rowMapper, startRow, pageSize));
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		super.setDataSource(dataSource);
	}
}