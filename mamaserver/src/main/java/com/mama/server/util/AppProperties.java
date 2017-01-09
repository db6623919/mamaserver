package com.mama.server.util;

import org.springframework.beans.factory.annotation.Value;

public class AppProperties {
	@Value("#{properties['charge.center.url']}")
	private String chargeCenterUrl;
	
	public String getChargeCenterUrl() {
		return chargeCenterUrl;
	}

	public void setChargeCenterUrl(String chargeCenterUrl) {
		this.chargeCenterUrl = chargeCenterUrl;
	}
}

