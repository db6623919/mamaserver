package com.mmzb.house.web.action.dto;

import java.util.Date;

/**
 * 活动表
 * @author WHL
 *
 * @version 0.0.1 2016年6月16日
 */
public class ActivityInfo {
	
	//活动编码
	private String code; 
	//活动名称
	private String activityName;
	//活动开始时间
	private Date validStart;
	//活动结束时间
	private Date validEnd;
	//活动说明
	private String memo;
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModified;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Date getValidStart() {
		return validStart;
	}
	public void setValidStart(Date validStart) {
		this.validStart = validStart;
	}
	public Date getValidEnd() {
		return validEnd;
	}
	public void setValidEnd(Date validEnd) {
		this.validEnd = validEnd;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
