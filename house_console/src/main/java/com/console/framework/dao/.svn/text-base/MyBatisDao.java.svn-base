package com.console.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * MyBatis的Dao基类
 * @author yangy
 * @date   2013-2-21
 */

@SuppressWarnings("unchecked")
public class MyBatisDao extends SqlSessionDaoSupport{
	
	public int save(String key, Object object) {
		return getSqlSession().insert(key, object);
	}
	
	public int save(String key) {
		return getSqlSession().insert(key);
	}
	
	public int update(String key, Object object) {
		return getSqlSession().update(key, object);
	}
	
	public int update(String key) {
		return getSqlSession().update(key);
	}
	
	public int delete(String key, Serializable id) {
		return getSqlSession().delete(key, id);
	}

	public int delete(String key, Object object) {
		return getSqlSession().delete(key, object);
	}
	
	public int delete(String key) {
		return getSqlSession().delete(key);
	}
	
	
	public <T> T getOne(String key, Object params) {
		return (T) getSqlSession().selectOne(key, params);
	}
	
	public <T> T getOne(String key) {
		return (T) getSqlSession().selectOne(key);
	}
	
	public <T> List<T> getList(String key, Object params) {
		return getSqlSession().selectList(key, params);
	}
	
	public <T> List<T> getList(String key) {
		return getSqlSession().selectList(key);
	}
	
	public <T> Map<String, Object> getMap(String key,String mapKey) {
		return getSqlSession().selectMap(key, mapKey);
	}
	
	public <T> Map<String, Object> getMap(String key,Object params,String mapKey) {
		return getSqlSession().selectMap(key, params, mapKey);
	}
	
	/**
	 * 按照偏移量取值(取坐标offset之后的limit条(从offset+1后开始取limit条))
	 * @param key
	 * @param params
	 * @param offset
	 * @param limit
	 * @return
	 */
	public <T> Pagination getPagination(String key, Object params,int offset,int limit) {
		int count             =  (Integer) getSqlSession().selectOne(key+"_count", params);
		List<Object> list     =  getSqlSession().selectList(key, params,new RowBounds(offset, limit));
		Pagination pagination =  new Pagination();
		pagination.setTotal_count(count);
		pagination.setList(list);
		return pagination;  
	}
	
	/**
	 * 按照页码取值(从current_page页开始，每页page_size条)
	 * @param key
	 * @param params
	 * @param current_page
	 * @param page_size
	 * @return
	 */
	public <T> Pagination getPaginationByPage(String key, Object params,int current_page,int page_size) {
		int count             =  (Integer) getSqlSession().selectOne(key+"_count", params);
		List<Object> list     =  getSqlSession().selectList(key, params,new RowBounds((current_page - 1) * page_size, page_size));
		Pagination pagination =  new Pagination();
		pagination.paging(current_page, page_size, count);
		pagination.setList(list);
		return pagination;  
	}
	

	
}
