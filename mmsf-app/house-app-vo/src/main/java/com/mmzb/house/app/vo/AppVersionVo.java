package com.mmzb.house.app.vo;

import com.mmzb.house.app.entity.AppVersionEntity;

/**
 * 版本控制表
 * @author whl
 *
 */
public class AppVersionVo {

	/** 版本信息*/
	private AppVersionEntity appVersion;
	/** 是否更新 1:更新 0:不更新*/
	private String versionBool;
	public AppVersionEntity getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(AppVersionEntity appVersion) {
		this.appVersion = appVersion;
	}
	public String getVersionBool() {
		return versionBool;
	}
	public void setVersionBool(String versionBool) {
		this.versionBool = versionBool;
	}
	
}
