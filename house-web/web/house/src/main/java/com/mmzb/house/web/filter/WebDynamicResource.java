/**
 *
 */
package com.mmzb.house.web.filter;

import java.io.Serializable;


public class WebDynamicResource implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6728626598811865624L;

	/** 主机请求地址 */
	private String hostAddress;

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	
}
