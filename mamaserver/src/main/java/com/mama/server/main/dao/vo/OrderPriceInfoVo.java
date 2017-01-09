package com.mama.server.main.dao.vo;

import java.util.Map;

import com.mama.server.main.dao.model.HousePricePo;

/**
 * 订单价格信息
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年3月3日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
public class OrderPriceInfoVo {

	private int totalAmt; // 总价格
	private int days; // 总天数
	private int inSeasonDays;// 旺季天数
	private int offSeasonDays;// 平季天数
	private Map<String, HousePricePo> priceMap;// key=日期yyyy-MM-dd, value=价格信息
	private int hotelCouponDiscountAmt;// 旅居券抵扣价格
	private int orderFinalAmt;// 订单最终价格
	private int realInSeasonDays;

	public int getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getInSeasonDays() {
		return inSeasonDays;
	}

	public void setInSeasonDays(int inSeasonDays) {
		this.inSeasonDays = inSeasonDays;
	}

	public int getOffSeasonDays() {
		return offSeasonDays;
	}

	public void setOffSeasonDays(int offSeasonDays) {
		this.offSeasonDays = offSeasonDays;
	}

	public Map<String, HousePricePo> getPriceMap() {
		return priceMap;
	}

	public void setPriceMap(Map<String, HousePricePo> priceMap) {
		this.priceMap = priceMap;
	}

	public int getHotelCouponDiscountAmt() {
		return hotelCouponDiscountAmt;
	}

	public void setHotelCouponDiscountAmt(int hotelCouponDiscountAmt) {
		this.hotelCouponDiscountAmt = hotelCouponDiscountAmt;
	}

	public int getOrderFinalAmt() {
		return orderFinalAmt;
	}

	public void setOrderFinalAmt(int orderFinalAmt) {
		this.orderFinalAmt = orderFinalAmt;
	}

	public int getRealInSeasonDays() {
		return realInSeasonDays;
	}

	public void setRealInSeasonDays(int realInSeasonDays) {
		this.realInSeasonDays = realInSeasonDays;
	}

}
