package com.mmzb.house.web.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.model.BaseMessage;
import com.mmzb.house.web.model.ClientBaseMessage;
import com.mmzb.house.web.model.ClientTextMessage;
import com.mmzb.house.web.model.SceneQrCodeScanEventMessage;
import com.mmzb.house.web.service.WeChatMessageHandler;

public class SceneQrCodeScanEventHandler implements WeChatMessageHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(SceneQrCodeScanEventHandler.class);
	
	private static final String EVENT_KEY_PREFIX = "qrscene_";
	
	@Resource(name="appProperties")
    private AppProperties appProperties;
	
	@Override
	public ClientBaseMessage onMessage(BaseMessage msg) {
		SceneQrCodeScanEventMessage scanEvent = (SceneQrCodeScanEventMessage) msg;
		// 获取朋友码
		String friendCode = scanEvent.getEventKey().substring(EVENT_KEY_PREFIX.length());
		
		ClientTextMessage txtMessage = new ClientTextMessage();
		String url = appProperties.getHostAddress() + "toRegister.htm?firendCode=" + friendCode;
		// FIXME 确认一下这里的提示语
		txtMessage.getText().setContent("<a href=\"" + url + "\">点击立即注册</a>");
		txtMessage.setTouser(msg.getFromUserName());
		return txtMessage;
	}
	
//	private String getWelcomeWord() {
//		return "亲,终于等到您了!妈妈送房已火热上线!十城千房";
//	}

}
