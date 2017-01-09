package com.mmzb.house.app.vo;

import java.util.List;

/**
 * 旅居券
 * @author whl
 *
 */
public class CouponVo {
	
	/** 房源ID */
	private int houseId;
	/** 旅居券名 */
	private String couponName;
	/** 旅居券IDs */
	private List<Long> hotelCouponIds; 
	/** 使用期限 */
	private Long couponDeadline;
	/** 状态  */
	private int couponState;
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public List<Long> getHotelCouponIds() {
		return hotelCouponIds;
	}
	public void setHotelCouponIds(List<Long> hotelCouponIds) {
		this.hotelCouponIds = hotelCouponIds;
	}
	public Long getCouponDeadline() {
		return couponDeadline;
	}
	public void setCouponDeadline(Long couponDeadline) {
		this.couponDeadline = couponDeadline;
	}
	public int getCouponState() {
		return couponState;
	}
	public void setCouponState(int couponState) {
		this.couponState = couponState;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	
	
	
}
