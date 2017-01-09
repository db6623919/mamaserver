package com.mmzb.house.web.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmzb.house.web.constant.WeChatConstants;
import com.mmzb.house.web.model.BaseMessage;
import com.mmzb.house.web.model.ClientBaseMessage;
import com.mmzb.house.web.model.SceneQrCodeScanEventMessage;
import com.mmzb.house.web.service.WeChatMessageHandler;
import com.mmzb.house.web.service.WeChatMessageDispatcher;
import com.mmzb.house.web.util.WeChatUtil;

public class WeChatMessageDispatcherImpl implements WeChatMessageDispatcher {

	private static Logger logger = LoggerFactory.getLogger(WeChatMessageDispatcherImpl.class);
	
	public Map<String, WeChatMessageHandler> callbackMap = new HashMap<String, WeChatMessageHandler>();
	public static final String MSG_TYPE_START = "<MsgType>";
	public static final String MSG_TYPE_EMD = "</MsgType>";
	public static final String EVENT_START = "<Event>";
	public static final String EVENT_EMD = "</Event>";
	public static final String KEY_MGS_OBJECT = "msgObj";
	public static final String KEY_HANDLER = "handler";
	
	public void setCallbackMap(Map<String, WeChatMessageHandler> callbackMap) {
		this.callbackMap = callbackMap;
	}

	@Override
	public void handleMessage(InputStream in) {
		String msgStr = null;
		BaseMessage msgObj = null;
		WeChatMessageHandler handler = null; 
		try {
			msgStr = streamToString(in);
			logger.debug("微信传递过来的报文:  {}", msgStr);
			Map<String, Object> objMap = this.buildMessageObject(msgStr);
			if (objMap == null || !objMap.containsKey("msgObj")) {
				return;
			}
			
			msgObj = (BaseMessage) objMap.get(KEY_MGS_OBJECT);
			handler = (WeChatMessageHandler) objMap.get(KEY_HANDLER);
			
			WeChatUtil.parseXmlMsg(msgObj, msgStr);
			
			ClientBaseMessage clientMsg = handler.onMessage(msgObj);

			if (clientMsg == null || StringUtils.isBlank(clientMsg.getTouser()) || StringUtils.isBlank(clientMsg.getMsgtype())) {
				return;
			}

			WeChatUtil.sendMessage(clientMsg);
		} catch (IOException e) {
			logger.error("解析消息流失败", e);
		} catch (Exception e) {
			logger.error("解析微信消息(json/xml)失败", e);
		}
	}
	
	public Map<String, Object> buildMessageObject(String msgStr) {
		String msgType = WeChatUtil.getMsgItemFromXML(MSG_TYPE_START, MSG_TYPE_EMD, msgStr);
		msgType = WeChatUtil.deleteCDATA(msgType);
		WeChatMessageHandler handler = null;
		Map<String, Object> objMap = new HashMap<String, Object>();
		handler = callbackMap.get(msgType);
		String event = null;
		// 事件消息
		if (WeChatConstants.MSG_TYPE_EVENT.equals(msgType)) {
			event = WeChatUtil.getMsgItemFromXML(EVENT_START, EVENT_EMD, msgStr);
			event = WeChatUtil.deleteCDATA(event);
			if (WeChatConstants.EVENT_TYPE_SCAN.equals(event)) {
				// 判断被扫描的二维码是否带参数
				if (msgStr.contains("<Ticket>") && msgStr.contains("</Ticket>")) {
					handler = callbackMap.get("scanSceneQrCode");
					objMap.put(KEY_HANDLER, handler);
					objMap.put(KEY_MGS_OBJECT, new SceneQrCodeScanEventMessage());
					return objMap;
				} else {
					// 普通订阅或取消关注的事件
				}
			}
		}
		
		return objMap;
	}
	
	public static String streamToString(InputStream in) throws IOException {
		if (in == null) {
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
		String str = null;
		StringBuilder sb = new StringBuilder();
		try {
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
		} finally {
			reader.close();
		}
		return sb.toString();
	}

}
