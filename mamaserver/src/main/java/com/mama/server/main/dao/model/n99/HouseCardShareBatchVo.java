package com.mama.server.main.dao.model.n99;

/** 房券分享实体对象 */
public class HouseCardShareBatchVo {
	/** 主键id */
	private int id;
	
	/** 分享批次号 */
	private String shareBatchNo;
	
	/** 房券分享张数 */
	private int shareCardNum;
	
	/** 房券订单id */
	private String orderId;
	
	/** 分享人用户id */
	private String shareUid;
	
	/** 单次分享是所对应的房券编码 */
	private String cardNo;
	
	/** 分享类型：0、批量分享；1、单张分享； */
	private int shareType;
	
	/** 分享时间 */
	private String shareTime;

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

	public int getShareCardNum() {
		return shareCardNum;
	}

	public void setShareCardNum(int shareCardNum) {
		this.shareCardNum = shareCardNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getShareUid() {
		return shareUid;
	}

	public void setShareUid(String shareUid) {
		this.shareUid = shareUid;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getShareType() {
		return shareType;
	}

	public void setShareType(int shareType) {
		this.shareType = shareType;
	}

	public String getShareTime() {
		return shareTime;
	}

	public void setShareTime(String shareTime) {
		this.shareTime = shareTime;
	}
	
}
