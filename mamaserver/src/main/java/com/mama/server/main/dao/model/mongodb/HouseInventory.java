package com.mama.server.main.dao.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "house.detail")
public class HouseInventory
{
	/** 房源id */
	@Id
	private long houseId;
	
	private int roomNum;

	public long getHouseId()
	{
		return houseId;
	}

	public void setHouseId(long houseId)
	{
		this.houseId = houseId;
	}

	public int getRoomNum()
	{
		return roomNum;
	}

	public void setRoomNum(int roomNum)
	{
		this.roomNum = roomNum;
	}
}
