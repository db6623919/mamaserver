package com.mmzb.house.web.action.dto;

import java.util.Date;

/**
 * 活动配置表
 * @author Whl
 *
 * @version 0.0.1 2016年6月16日
 * 
 */
public class ActivityConfInfo {

	private int id;
	//活动编码
	private String activityCode;
	//类型
	private String type;
	//类型值
	private String typeValue;
	//生效日期
	private Date validStart;
	//失效日期
	private Date validEnd;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
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
	
	
}
