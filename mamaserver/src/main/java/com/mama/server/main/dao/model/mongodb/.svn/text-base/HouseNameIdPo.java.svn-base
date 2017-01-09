package com.mama.server.main.dao.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "house.search")
public class HouseNameIdPo
{
	@Id
	private long houseId; 

	private String name;
	
	private long cityId;
	
	public long getHouseId()
	{
		return houseId;
	}

	public void setHouseId(long houseId)
	{
		this.houseId = houseId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getCityId()
	{
		return cityId;
	}

	public void setCityId(long cityId)
	{
		this.cityId = cityId;
	}
}
