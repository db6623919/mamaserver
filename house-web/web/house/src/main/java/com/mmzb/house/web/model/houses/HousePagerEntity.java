package com.mmzb.house.web.model.houses;

import java.util.List;

/**
 * 房源列表及分页信息
 * @author lenovo
 *
 */
public class HousePagerEntity
{
	//房源列表
	private List<HouseEntity> houses;
	
	//总房源数
	private int itemCount;

	public List<HouseEntity> getHouses()
	{
		return houses;
	}

	public void setHouses(List<HouseEntity> houses)
	{
		this.houses = houses;
	}

	public int getItemCount()
	{
		return itemCount;
	}

	public void setItemCount(int itemCount)
	{
		this.itemCount = itemCount;
	}

}
