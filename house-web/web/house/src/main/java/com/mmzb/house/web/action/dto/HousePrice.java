package com.mmzb.house.web.action.dto;

/**
 * 
* @ClassName: HousePrice 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Lijiaqi 
* @date 2015年10月4日 下午1:31:35
 */
public class HousePrice {

	private int priceId;
	
	private String date;
	
	private int totalAmt;
	
	private int freezeAmt;
	
	private int memTotalAmt;
	
	private int memFreezeAmt;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public int getPriceId() {
		return priceId;
	}

	public void setPriceId(int priceId) {
		this.priceId = priceId;
	}
	
	
}
