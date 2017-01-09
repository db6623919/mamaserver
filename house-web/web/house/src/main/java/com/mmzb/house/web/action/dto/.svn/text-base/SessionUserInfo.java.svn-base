package com.mmzb.house.web.action.dto;

import org.springframework.beans.BeanUtils;

/**
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年3月4日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
public class SessionUserInfo extends UserInfo {

	private String token;

	public SessionUserInfo(UserInfo userInfo, String token) {
		BeanUtils.copyProperties(userInfo, this);
		this.setToken(token);
	}

	public SessionUserInfo() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
