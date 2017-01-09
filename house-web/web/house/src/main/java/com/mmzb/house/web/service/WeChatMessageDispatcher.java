package com.mmzb.house.web.service;

import java.io.InputStream;

public interface WeChatMessageDispatcher {
	
	void handleMessage(InputStream in);
	
}
