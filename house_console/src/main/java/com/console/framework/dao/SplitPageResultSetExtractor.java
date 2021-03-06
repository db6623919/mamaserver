package com.console.framework.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

/**
 * @description JdbcTemplate扩展
 * @author yangy
 * @version 1.0
 * @date 2012-08-22
 */
public class SplitPageResultSetExtractor implements ResultSetExtractor<Object> {
	private final int start;// 起始行号
	private final int len;// 结果集合的长度
	private final RowMapper<?> rowMapper;// 行包装器

	public SplitPageResultSetExtractor(RowMapper<?> rowMapper, int start, int len) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
		this.start = start;
		this.len = len;
	}

	/**
	 * 处理结果集合,被接口自动调用，该类外边不应该调用
	 */
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Object> result = new ArrayList<Object>();
		int rowNum = 0;
		int end = start + len;
		point: while (rs.next()) {
			++rowNum;
			if (rowNum < start) {
				continue point;
			} else if (rowNum >= end) {
				break point;
			} else {
				result.add(this.rowMapper.mapRow(rs, rowNum));
			}
		}
		return result;
	}
}
