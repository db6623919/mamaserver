package com.mama.server.main.dao.model;

/**
 * 版本号
 * @author dengbiao
 *
 */

public class Version {

	private int id;
	private int versionNo = 1;//版本号
	private Integer versionType; //版本类型  1 城市列表版本号
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
	public Integer getVersionType() {
		return versionType;
	}
	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}

	
}