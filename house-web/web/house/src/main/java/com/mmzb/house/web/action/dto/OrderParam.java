package com.mmzb.house.web.action.dto;


public class OrderParam {
	//房源ID
	private int houseId;
	//入住时间
	private String startTime;
	//离开时间
	private String endTime;
	//支付价格
	private int payPrice;
	//房间套数
	private int totalRoomNum;
	//客栈ID
	private int shopId;
	//订单ID
	private String orderId;
	
	//客栈名称
	private String shopName;
	
	//房源名称
	private String houseName;
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(int payPrice) {
		this.payPrice = payPrice;
	}
	public int getTotalRoomNum() {
		return totalRoomNum;
	}
	public void setTotalRoomNum(int totalRoomNum) {
		this.totalRoomNum = totalRoomNum;
	}
	public String getOrderId()
	{
		return orderId;
	}
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}
	public String getShopName()
	{
		return shopName;
	}
	public void setShopName(String shopName)
	{
		this.shopName = shopName;
	}
	public String getHouseName()
	{
		return houseName;
	}
	public void setHouseName(String houseName)
	{
		this.houseName = houseName;
	}
	
	
}
