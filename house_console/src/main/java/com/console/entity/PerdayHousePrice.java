package com.console.entity;

/**
 * 
* @ClassName: HousePrice 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Lijiaqi 
* @date 2015年10月4日 下午1:31:35
 */
public class PerdayHousePrice implements Comparable<PerdayHousePrice>{

	private int priceId;
	
	private String date;
	
	private int originalPrice;//原价
	
	private Integer presentPrice;//现价
	
	private String update_resson;//修改原因
	
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

	public Integer getPresentPrice() {
		return presentPrice;
	}

	public void setPresentPrice(Integer presentPrice) {
		this.presentPrice = presentPrice;
	}

	public String getUpdate_resson() {
		return update_resson;
	}

	public void setUpdate_resson(String update_resson) {
		this.update_resson = update_resson;
	}
	
	@Override
	public int compareTo(PerdayHousePrice o) {
		return this.getPresentPrice().compareTo(o.getPresentPrice());
	}
}