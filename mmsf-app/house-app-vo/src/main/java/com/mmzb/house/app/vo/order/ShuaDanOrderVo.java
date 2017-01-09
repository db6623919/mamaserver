package com.mmzb.house.app.vo.order;

/**
 * 刷单订单辅助实体类
 * @author lihaifeng
 * */
public class ShuaDanOrderVo {
	/** 订单编号 */
	private String orderId;
	
	/** 订单状态 */
	private Integer orderStatus;
	
	/** 客栈名称 */
	private String shopName;
	
	/** 预订人id */
	private String uid;
	
	/** 总价 */
	private int totalAmt;
	
	/** 优惠金额 */
	private int freezeAmt;
	
	/** 支付金额 */
	private int payAmt;
	
	/** 支付时间 */
	private String payTime;
	
	/** 预订人手机号码 */
	private String orderManPhone;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public int getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(int payAmt) {
		this.payAmt = payAmt;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getOrderManPhone() {
		return orderManPhone;
	}

	public void setOrderManPhone(String orderManPhone) {
		this.orderManPhone = orderManPhone;
	}
 }
