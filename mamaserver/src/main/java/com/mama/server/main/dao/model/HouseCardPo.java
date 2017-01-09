package com.mama.server.main.dao.model;

/**
 * 房券Po
 * @author whl
 *
 */
public class HouseCardPo {

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
	/** 接收人手机号码 */
	private String receivePhone;
	private String shopName; //客栈名
	private String bossPhone; //客栈电话
	private String bossName;  //掌柜姓名
	private String houseName; //房源名称
	
	/** 房间数量 */
	private int totalRoomNum;

	/** 分享状态：0、批量分享；1、单张分享；2、已领取； */
	private int shareStatus;
	
	/** 分享时间 */
	private String shareTime;
	/** 分享对应的批次号*/
	private String sharePatchNo;
	
	/** 房券卡面值 */
	private double cardPrice;
	
	/** 0、尊享版；1、畅玩版； */
	private String cardType;
	/** h5详情url */
    private String h5DetailUrl;
    
	public String getSharePatchNo() {
		return sharePatchNo;
	}
	public void setSharePatchNo(String sharePatchNo) {
		this.sharePatchNo = sharePatchNo;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
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
	public int getTotalRoomNum() {
		return totalRoomNum;
	}
	public void setTotalRoomNum(int totalRoomNum) {
		this.totalRoomNum = totalRoomNum;
	}
	public int getShareStatus() {
		return shareStatus;
	}
	public void setShareStatus(int shareStatus) {
		this.shareStatus = shareStatus;
	}
	public String getShareTime() {
		return shareTime;
	}
	public void setShareTime(String shareTime) {
		this.shareTime = shareTime;
	}
	public double getCardPrice() {
		return cardPrice;
	}
	public void setCardPrice(double cardPrice) {
		this.cardPrice = cardPrice;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getH5DetailUrl() {
		return h5DetailUrl;
	}
	public void setH5DetailUrl(String h5DetailUrl) {
		this.h5DetailUrl = h5DetailUrl;
	}
	
}
