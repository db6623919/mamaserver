package com.mmzb.house.app.vo;

import java.util.List;

/**
 * 热房推荐列表
 * @author whl
 *
 */
public class Recommend {

	/** 热房栏目名  */
	private String columnName;
	/** 热房房源列表  */
	private List<HouseVo> houses;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public List<HouseVo> getHouses() {
		return houses;
	}
	public void setHouses(List<HouseVo> houses) {
		this.houses = houses;
	}
	
	
	
	
}
