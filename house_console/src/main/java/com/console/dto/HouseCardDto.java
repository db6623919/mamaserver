package com.console.dto;

/**
 * 房券Po
 * @author whl
 *
 */
public class HouseCardDto {

	private Integer id;
	/** 房券编号 */
	private String cardNo;
	/** 订单号 */
	private String orderNo;
	/** 房券对应的房源id */
	private Integer houseId;
	/** 所属客栈id */
	private Integer shopId;
	/** 用户id */
	private String buyId;
	/** 购买人联系电话 */
	private String buyPhone;
	/** 购买时间 */
	private String buyTime;
	/** 兑换时间 */
	private String exchangeTime;
	/** 兑换人*/
	private String exchangeName;
	/** 兑换人联系方式 */
	private String exchangePhoneNo;
	/** 使用状态：0、未使用；1、已使用； */
	private Integer useStatus;
	/** endDate */
	private String endDate;
	
	private String shopName; //客栈名
	private String bossPhone; //客栈电话
	private String bossName;  //掌柜姓名
	private String houseName; //房源名称
	private String cardPrice;//房券价格
	
	public String getCardPrice() {
		return cardPrice;
	}
	public void setCardPrice(String cardPrice) {
		this.cardPrice = cardPrice;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getBossPhone() {
		return bossPhone;
	}
	public void setBossPhone(String bossPhone) {
		this.bossPhone = bossPhone;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getBuyId() {
		return buyId;
	}
	public void setBuyId(String buyId) {
		this.buyId = buyId;
	}
	public String getBuyPhone() {
		return buyPhone;
	}
	public void setBuyPhone(String buyPhone) {
		this.buyPhone = buyPhone;
	}
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public String getExchangeTime() {
		return exchangeTime;
	}
	public void setExchangeTime(String exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
	public String getExchangeName() {
		return exchangeName;
	}
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	public String getExchangePhoneNo() {
		return exchangePhoneNo;
	}
	public void setExchangePhoneNo(String exchangePhoneNo) {
		this.exchangePhoneNo = exchangePhoneNo;
	}
	public Integer getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
