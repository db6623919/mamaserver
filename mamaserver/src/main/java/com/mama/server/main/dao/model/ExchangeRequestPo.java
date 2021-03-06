package com.mama.server.main.dao.model;

import java.util.Date;

/**
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年3月25日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
public class ExchangeRequestPo {

	private long id;// ID
	private String memberId;// 会员ID
	private long exchangeId;// 关联ID
	private String hotelCouponIds;// 旅居券ID列表
	private int status;// 状态:0交换中;1:成功交换;2：取消交换
	private String showDetail;//
	private Date updateTime;// 修改时间
	private Date createTime;// 创建时间

	/***** 非实体属性 *****/
	private String memberName;//会员名称
	

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHotelCouponIds() {
		return hotelCouponIds;
	}

	public void setHotelCouponIds(String hotelCouponIds) {
		this.hotelCouponIds = hotelCouponIds;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getShowDetail() {
		return showDetail;
	}

	public void setShowDetail(String showDetail) {
		this.showDetail = showDetail;
	}

	public long getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(long exchangeId) {
		this.exchangeId = exchangeId;
	}

}
