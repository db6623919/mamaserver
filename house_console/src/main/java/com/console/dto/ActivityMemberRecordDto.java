package com.console.dto;

import java.util.Date;

import com.console.framework.dao.Pagination;

/**
 * 会员抽奖记录
 * @author Whl
 * 
 * @version 0.0.1 2016年6月16日
 *
 */
public class ActivityMemberRecordDto extends Pagination{

	private static final long serialVersionUID = 1L;
	private int id;
	//会员ID
	private String memberId;
	//会员标识手机号
	private String memberIdentity;
	//活动编码    ActivityPo(code)
	private String activityCode;
	//抽奖记录日期 YYYYmmDD
	private String recordDate;
	//会员积分总额
	private int totalPoint;
	//是否中奖 1中奖 0未中
	private String winFlag;
	//奖品ID
	private String prizeProdId;
	//奖品名称
	private String prizeProdName;
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModified;
	//分享次数
	private int shareTimes;
	
	//活动名称
	private String activityName;
	//抽奖次数
	private int recordTimes;
	
	
	public int getRecordTimes() {
		return recordTimes;
	}
	public void setRecordTimes(int recordTimes) {
		this.recordTimes = recordTimes;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public int getShareTimes() {
		return shareTimes;
	}
	public void setShareTimes(int shareTimes) {
		this.shareTimes = shareTimes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberIdentity() {
		return memberIdentity;
	}
	public void setMemberIdentity(String memberIdentity) {
		this.memberIdentity = memberIdentity;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public int getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
	public String getWinFlag() {
		return winFlag;
	}
	public void setWinFlag(String winFlag) {
		this.winFlag = winFlag;
	}
	public String getPrizeProdId() {
		return prizeProdId;
	}
	public void setPrizeProdId(String prizeProdId) {
		this.prizeProdId = prizeProdId;
	}
	public String getPrizeProdName() {
		return prizeProdName;
	}
	public void setPrizeProdName(String prizeProdName) {
		this.prizeProdName = prizeProdName;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	
	
}
