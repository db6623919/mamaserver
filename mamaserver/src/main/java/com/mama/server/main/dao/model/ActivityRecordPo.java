package com.mama.server.main.dao.model;

import java.util.Date;

/**
 * 会员抽奖记录明细
 * @author Administrator
 *
 * @version 0.0.1 2016年6月16日
 */
public class ActivityRecordPo {

	private int id;
	//记录ID  ActivityMemberRecordInfo(id)
	private int recordId;
	//抽奖结果
	private String random;
	//抽奖积分
	private int point;
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModified;
	
	private Date startDate;
	private Date endDate;
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
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
