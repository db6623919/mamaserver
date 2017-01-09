package com.console.dto;

public class HouseDto {
	private String freezeAmt;
	private String totalAmt;
	private String memFreezeAmt;
	private String memTotalAmt;
	private String houseId;
	private String cityId;
	private String summaryInfo;
	private String status;
	private String date;
	private boolean inSeason;// 是否旺季
	
	private String devId;
	private String bldId;
	private String houseName;
	
	private String room;
	private String bedroom;
	private String livingroom;
	private String restroom;
	private String bed;
	
	private String totalRoomNum;
	private int market_price;//市场价
	private int specials_stauts=0;//特价状态默认0， 0 普通 1特价 2首页特价

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSummaryInfo() {
		return summaryInfo;
	}

	public void setSummaryInfo(String summaryInfo) {
		this.summaryInfo = summaryInfo;
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

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public boolean isInSeason() {
		return inSeason;
	}

	public void setInSeason(boolean inSeason) {
		this.inSeason = inSeason;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getBldId() {
		return bldId;
	}

	public void setBldId(String bldId) {
		this.bldId = bldId;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getBedroom() {
		return bedroom;
	}

	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}

	public String getLivingroom() {
		return livingroom;
	}

	public void setLivingroom(String livingroom) {
		this.livingroom = livingroom;
	}

	public String getRestroom() {
		return restroom;
	}

	public void setRestroom(String restroom) {
		this.restroom = restroom;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getTotalRoomNum() {
		return totalRoomNum;
	}

	public void setTotalRoomNum(String totalRoomNum) {
		this.totalRoomNum = totalRoomNum;
	}

	public int getMarket_price() {
		return market_price;
	}

	public void setMarket_price(int market_price) {
		this.market_price = market_price;
	}

	public int getSpecials_stauts() {
		return specials_stauts;
	}

	public void setSpecials_stauts(int specials_stauts) {
		this.specials_stauts = specials_stauts;
	}

}
