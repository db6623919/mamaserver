package com.console.vo;

public class OrderInfoVO {
	
	private String uid;//会员编号 
	private String orderId;//订单编号 
	private String houseId;//房屋编号 
	private String freezeAmt;//冻结金额
	private String totalAmt;//总金额
	private String status;//状态
	private String verifyCode;
	private String operTime;//时间
	private String liveDetail;//住房详情
	private String houseName;
	private String userName;
	private String bidName;
	private String cityName;
	private String beginTime;
	private String endTime;
	private String phone;
	private String devId;
	private	String bldId;
	private	String cityId;
	private String room;
	private String hall;
	private String Health;
	private String bed;
	private String totalRoomNum;
	private int houseType;
	private String pay_type;//支付方式
	private String user_phone;//登录用户手机号码
	private String buildPhone;//房源手机号码
	private String cancelOrdeReason;//取消原因
	private String realInSeasonDays;//实际抵扣天数
	private String payAmt; //支付金额
	
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	public String getBldId() {
		return bldId;
	}
	public void setBldId(String bldId) {
		this.bldId = bldId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	public String getFreezeAmt() {
		return freezeAmt;
	}
	public void setFreezeAmt(String freezeAmt) {
		this.freezeAmt = freezeAmt;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
	public String getHall() {
		return hall;
	}
	public void setHall(String hall) {
		this.hall = hall;
	}
	public String getHealth() {
		return Health;
	}
	public void setHealth(String health) {
		Health = health;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public String getTotalRoomNum() {
		return totalRoomNum;
	}
	public void setTotalRoomNum(String totalRoomNum) {
		this.totalRoomNum = totalRoomNum;
	}
	public int getHouseType() {
		return houseType;
	}
	public void setHouseType(int houseType) {
		this.houseType = houseType;
	}
	@Override
	public String toString() {
		return "OrderInfoVO [uid=" + uid + ", orderId=" + orderId
				+ ", houseId=" + houseId + ", freezeAmt=" + freezeAmt
				+ ", totalAmt=" + totalAmt + ", status=" + status
				+ ", verifyCode=" + verifyCode + ", operTime=" + operTime
				+ ", liveDetail=" + liveDetail + "]";
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getBuildPhone() {
		return buildPhone;
	}
	public void setBuildPhone(String buildPhone) {
		this.buildPhone = buildPhone;
	}
	public String getCancelOrdeReason() {
		return cancelOrdeReason;
	}
	public void setCancelOrdeReason(String cancelOrdeReason) {
		this.cancelOrdeReason = cancelOrdeReason;
	}
	public void setRealInSeasonDays(String realInSeasonDays) {
		this.realInSeasonDays = realInSeasonDays;
	}
	public String getRealInSeasonDays() {
		return realInSeasonDays;
	}
}
