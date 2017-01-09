package com.mama.server.main.dao.model.clickfarming;

import com.mama.server.main.dao.Pagination;

/**
 * 客栈刷单金额
 * @author whl
 *
 */
public class CFShopOrderPo extends Pagination{
	
	private static final long serialVersionUID = 1L;
	/** 店铺ID*/
	private int shopId;
	/** 店铺名称*/
	private String shopName;
	/** 店铺老板*/
	private String bossName;
	/** 店铺电话*/
	private String bossPhone;
	/** 优惠金额*/
	private int freezeAmt;
	/** 总金额*/
	private int totalAmt;
	/** 支付金额*/
	private int payAmt;
	/** 订单数*/
	private int orderNum;
	/** 订单结算数*/
	private int settleNum;
	
	public int getSettleNum() {
		return settleNum;
	}
	public void setSettleNum(int settleNum) {
		this.settleNum = settleNum;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public String getBossPhone() {
		return bossPhone;
	}
	public void setBossPhone(String bossPhone) {
		this.bossPhone = bossPhone;
	}
	public int getFreezeAmt() {
		return freezeAmt;
	}
	public void setFreezeAmt(int freezeAmt) {
		this.freezeAmt = freezeAmt;
	}
	public int getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}
	public int getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(int payAmt) {
		this.payAmt = payAmt;
	}
	
}
