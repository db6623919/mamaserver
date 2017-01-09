package com.mama.server.main.dao.model;

public class OrderPo {
	private String orderId;
	private String uid;
	private int houseId;
	private int freezeAmt;
	private int totalAmt;
	private int payAmt;
	private int totalRoomNum;
	private int status;
	private String verifyCode;
	private String operTime;
	private String liveDetail;
	private int removed;
	private String retentionTime;//房间保留时间
	private String cancelOrdeReason;//订单取消原因
	private String pay_type;//支付方式
	private int orderType;//订单类型
	private Integer shopId; //店铺ID
	private Integer settleStatus;//结算状态
	private int settleNum; //结算数
	private int orderNum; //订单数
	
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
	public Integer getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(Integer settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	private String shopName; //店铺名称
	
	
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public int getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(int payAmt) {
		this.payAmt = payAmt;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
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
	public int getTotalRoomNum() {
		return totalRoomNum;
	}
	public void setTotalRoomNum(int totalRoomNum) {
		this.totalRoomNum = totalRoomNum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getLiveDetail() {
		return liveDetail;
	}
	public void setLiveDetail(String liveDetail) {
		this.liveDetail = liveDetail;
	}
	public int getRemoved() {
		return removed;
	}
	public void setRemoved(int removed) {
		this.removed = removed;
	}
	public String getRetentionTime() {
		return retentionTime;
	}
	public void setRetentionTime(String retentionTime) {
		this.retentionTime = retentionTime;
	}
	public String getCancelOrdeReason() {
		return cancelOrdeReason;
	}
	public void setCancelOrdeReason(String cancelOrdeReason) {
		this.cancelOrdeReason = cancelOrdeReason;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	
}