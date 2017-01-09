package com.mama.server.common.entity;

import java.util.List;

/**
 * 关键字搜索结果
 * @author lenovo
 *
 */
public class KWSearchResultEntityH5
{
	//商圈
	private List<NameIdEntity> areas;
	
	//房源
	private List<NameIdEntity> houses;
	
	//城市
	private List<NameIdEntity> cities;
	
	public List<NameIdEntity> getHouses()
	{
		return houses;
	}

	public void setHouses(List<NameIdEntity> houses)
	{
		this.houses = houses;
	}

	public List<NameIdEntity> getAreas()
	{
		return areas;
	}

	public void setAreas(List<NameIdEntity> areas)
	{
		this.areas = areas;
	}

	public List<NameIdEntity> getCities() {
		return cities;
	}

	public void setCities(List<NameIdEntity> cities) {
		this.cities = cities;
	}
	
}
