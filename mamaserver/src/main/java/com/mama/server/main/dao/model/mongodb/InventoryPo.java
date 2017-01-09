package com.mama.server.main.dao.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 库存记录表
 * @author dengbiao
 *
 */
@Document(collection = "house.inventory")
public class InventoryPo
{
	/** 库存 id */
	@Id
	private String id;
	
	private long houseId;
	
	private String date;//日期
	
	private int inventory;//库存数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
}