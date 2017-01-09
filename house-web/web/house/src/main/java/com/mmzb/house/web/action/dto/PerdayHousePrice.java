package com.mmzb.house.web.action.dto;

/**
 * 
* @ClassName: HousePrice 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Lijiaqi 
* @date 2015年10月4日 下午1:31:35
 */
public class PerdayHousePrice {

	private int priceId;
	
	private String date;
	
	private int originalPrice;//原价
	
	private int presentPrice;//现价
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPriceId() {
		return priceId;
	}

	public void setPriceId(int priceId) {
		this.priceId = priceId;
	}

	public int getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
	}

	public int getPresentPrice() {
		return presentPrice;
	}

	public void setPresentPrice(int presentPrice) {
		this.presentPrice = presentPrice;
	}
	
}