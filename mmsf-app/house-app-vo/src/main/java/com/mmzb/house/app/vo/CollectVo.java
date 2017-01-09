package com.mmzb.house.app.vo;

/**
 * 收藏Vo
 * @author whl
 *
 */
public class CollectVo {
	
	private int id;
	
	/** 房源ID*/
	private int houseId;
	/** 用户ID*/
	private String uid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
