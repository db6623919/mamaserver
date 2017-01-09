package com.mama.server.main.dao.model.n99;

/** 房券领取流水对应实体类 */
public class HouseCardFlowPo {
	/** 主键id，自增 */
	private int id;
	
	/** 分享批次号 */
	private String shareBatchNo;
	
	/** 房券分享张数 */
	private String receivePhone;
	
	/** 房券订单id */
	private String cardNo;
	
	/** 分享人用户id */
	private String receiveTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShareBatchNo() {
		return shareBatchNo;
	}

	public void setShareBatchNo(String shareBatchNo) {
		this.shareBatchNo = shareBatchNo;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	
}
