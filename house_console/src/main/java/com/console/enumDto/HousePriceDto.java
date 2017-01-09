package com.console.enumDto;

public class HousePriceDto {
	
	private int priceId;
	private String houseId;
	private String date;
	private String freezeAmt;
	private String totalAmt;
	private String memFreezeAmt;
	private String memTotalAmt;
	
	private Integer emptyRoom;
	private Integer requiredRoom;
	private Integer available;
	
	public int getPriceId() {
		return priceId;
	}
	public void setPriceId(int priceId) {
		this.priceId = priceId;
	}
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFreezeAmt() {
		return freezeAmt;
	}
	public void setFreezeAmt(String freezeAmt) {
		this.freezeAmt = freezeAmt;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getMemFreezeAmt() {
		return memFreezeAmt;
	}
	public void setMemFreezeAmt(String memFreezeAmt) {
		this.memFreezeAmt = memFreezeAmt;
	}
	public String getMemTotalAmt() {
		return memTotalAmt;
	}
	public void setMemTotalAmt(String memTotalAmt) {
		this.memTotalAmt = memTotalAmt;
	}
	public Integer getEmptyRoom() {
		return emptyRoom;
	}
	public void setEmptyRoom(Integer emptyRoom) {
		this.emptyRoom = emptyRoom;
	}
	public Integer getRequiredRoom() {
		return requiredRoom;
	}
	public void setRequiredRoom(Integer requiredRoom) {
		this.requiredRoom = requiredRoom;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}

}