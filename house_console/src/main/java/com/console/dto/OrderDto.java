package com.console.dto;


public class OrderDto {
	private String orderId;
	private String uid;
	private Integer houseId;
	private String operTime;
	private String status;
	private Integer freezeAmt;
	private Integer totalAmt;
	private String liveDetail;
	private HouseDto houseDto;
	private Integer payAmt;
	private String shopName; //店铺名称
	private String payType; //支付方式
	private String userPhone; //联系方式
	private String userName; //联系人
	private Integer settleStatus; //是否结算
	private int orderNum; //订单数
	private Integer shopId; //店铺ID
	private int settleNum; //结算数
	
	public int getSettleNum() {
		return settleNum;
	}
	public void setSettleNum(int settleNum) {
		this.settleNum = settleNum;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
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
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(Integer payAmt) {
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
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getFreezeAmt() {
		return freezeAmt;
	}
	public void setFreezeAmt(Integer freezeAmt) {
		this.freezeAmt = freezeAmt;
	}
	public Integer getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(Integer totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getLiveDetail() {
		return liveDetail;
	}
	public void setLiveDetail(String liveDetail) {
		this.liveDetail = liveDetail;
	}
	public HouseDto getHouseDto() {
		return houseDto;
	}
	public void setHouseDto(HouseDto houseDto) {
		this.houseDto = houseDto;
	}
	
}
