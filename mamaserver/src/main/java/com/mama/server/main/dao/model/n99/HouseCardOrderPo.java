package com.mama.server.main.dao.model.n99;

import com.mama.server.main.dao.Pagination;

/**
 * 房券列表订单对象
 * */
public class HouseCardOrderPo extends Pagination {
	
	private static final long serialVersionUID = -192229570272713891L;

	/** 订单号 */
	private String orderId;
	
	/** 订单状态 */
	private int orderStatus;
	
	/** 支付金额 */
	private int payAmt;
	
	/** 支付时间 */
	private String payTime;
	
	/** 房源id */
	private int houseId;
	
	/** 房券数量 */
	private int totalRoomNum;
	
	/** 下单时间 */
	private String operTime;
	
	/** 有效期 */
	private String endDate;
	
	/** 剩余张数 */
	private int leftNum;

	/** 房源名称 */
	private String houseName;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
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

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getTotalRoomNum() {
		return totalRoomNum;
	}

	public void setTotalRoomNum(int totalRoomNum) {
		this.totalRoomNum = totalRoomNum;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getLeftNum() {
		return leftNum;
	}

	public void setLeftNum(int leftNum) {
		this.leftNum = leftNum;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

}
