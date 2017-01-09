package com.mama.server.main.dao.model;

import java.util.Date;

public class HousePricePo {
	private int priceId;
	private int houseId;
	private int memTotalAmt;
	private int memFreezeAmt;
	private int totalAmt;
	private int freezeAmt;
	private Date date;
	private boolean inSeason;// 是否旺季

	public int getPriceId() {
		return priceId;
	}

	public void setPriceId(int priceId) {
		this.priceId = priceId;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getMemTotalAmt() {
		return memTotalAmt;
	}

	public void setMemTotalAmt(int memTotalAmt) {
		this.memTotalAmt = memTotalAmt;
	}

	public int getMemFreezeAmt() {
		return memFreezeAmt;
	}

	public void setMemFreezeAmt(int memFreezeAmt) {
		this.memFreezeAmt = memFreezeAmt;
	}

	public int getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(int freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public boolean isInSeason() {
		return inSeason;
	}

	public void setInSeason(boolean inSeason) {
		this.inSeason = inSeason;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
