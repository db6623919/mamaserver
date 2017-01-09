package com.mmzb.house.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.ApplicationCache;
import com.mmzb.house.web.action.base.ApplicationConfig;
import com.mmzb.house.web.constant.WeChatConstants;
import com.mmzb.house.web.core.common.util.JsonMapUtil;
import com.mmzb.house.web.model.BaseMessage;
import com.mmzb.house.web.model.ClientBaseMessage;
import com.mmzb.house.web.model.WeChatUser;

import net.sf.json.JSONArray;

public class WeChatUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WeChatUtil.class);
	
	public static String KEY_ACCESS_TOKEN = "access_token";
	
	public static String WY_ACCESS_TOKEN ="wy_access_token";
	
	public static String JSAPI_TICKET = "jsapi_ticket";
	
	public static String MMSFANG_IMG_URL = "mmsfang_img_url";
	
	public static final int VALID_TIME_IN_SECOND = 2 * 60 * 60;
	
	private static final Mapping receiveMapping = new Mapping();
	
	static {
		try {
			receiveMapping.loadMapping(WeChatUtil.class.getClassLoader().getResource("wechatRequestMsgMapping.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BaseMessage parseXmlMsg(BaseMessage msg, String xmlStr) throws Exception {
		if (StringUtils.isBlank(xmlStr)) {
			return null;
		}
		
		StringReader reader = new StringReader(xmlStr);
		Unmarshaller um = new Unmarshaller(receiveMapping);
		um.setObject(msg);
		return (BaseMessage) um.unmarshal(reader);
	}
	
	public static String getAccessTokenInternal() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient(); 
		HttpGet getToken = new HttpGet(MsgUtil.getWechatAccessTokenUrl());  
		HttpResponse result = httpClient.execute(getToken);  
		String resData = EntityUtils.toString(result.getEntity());
		logger.info("获取getAccessTokenInternal"+resData);
		JSONObject json = JSONObject.parseObject(resData);
		String accessToken = json.getString("access_token");
		ApplicationCache.getInstance().put(KEY_ACCESS_TOKEN, accessToken, VALID_TIME_IN_SECOND);
		return accessToken;
	}
	
	public static String getAccessToken() {
		String accessToken = ApplicationCache.getInstance().getStr(KEY_ACCESS_TOKEN);
		if(accessToken != null) {
			return accessToken;
		}
		try {
			accessToken = getAccessTokenInternal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	
	private static String getCreateTicketUrl() {
		return MsgUtil.getWechatQrcodeTicketUrl(getAccessToken());
	}
	
	public static JSONObject getTicketInternal(String sceneId) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient(); 
		HttpPost getTicket = new HttpPost(getCreateTicketUrl());  
		StringEntity content = new StringEntity(MsgUtil.getWechatQrcodeParamUrl(sceneId), "UTF-8");
		logger.info("获取getTicketInternal发送"+content);
		getTicket.setEntity(content);
		HttpResponse result = httpClient.execute(getTicket);  
		String resData = EntityUtils.toString(result.getEntity());
		logger.info("获取getTicketInternal接受"+resData);
		JSONObject json = JSONObject.parseObject(resData);
		return json;
	}
	
	private static String getTicket(String sceneId) {
		String ticket = null;
		try {
			ticket = getTicketInternal(sceneId).getString("ticket");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}
	
	private static String getQrCodeUrl(String sceneId) throws UnsupportedEncodingException {
		return MsgUtil.getWechatQrcodeUrl(getTicket(sceneId));
	}
	
	public static byte[] generateQrCode(String sceneId) {
		byte[] data = null;
		ByteArrayOutputStream bo = null;
		try {
		InputStream is = HttpsUtil.doGetInputStream(getQrCodeUrl(sceneId));
			// 设置初始内存大小为28KB
		bo = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int cur = 0;
		   while (true) {
			cur = is.read(buffer, 0,256);
		    if (cur < 0) { break; }
		    bo.write(buffer, 0, cur);
		   }
			data = bo.toByteArray();
		} catch (Exception e) {
			logger.error("生成公众号二维码时报错: {}", e);
		} finally {
			try {
				if(bo != null) {
					bo.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return data;
	}
	
	public static boolean isSubscribe(String openId) {
		boolean flag = false;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getWeChatUserInfo = new HttpGet(MsgUtil.getWechatUserInfoUrl(WeChatUtil.getAccessToken(), openId));  
			HttpResponse result = httpClient.execute(getWeChatUserInfo);  
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("获取用户是否关注公众号getWechatUserInfoUrl"+resData);
			JSONObject json = JSONObject.parseObject(resData);
			int subscribe = json.getIntValue("subscribe");
			flag = (subscribe != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public static WeChatUser getWeChatUserInfoByCode(JSONObject json) throws Exception {
			WeChatUser weChatUser = new WeChatUser();
			DefaultHttpClient httpClient = new DefaultHttpClient(); 
			String openId = json.getString("openid");
			String accessToken = json.getString("access_token");
			
			HttpGet getWeChatUserInfo = new HttpGet(MsgUtil.getWechatOauth2UserInfoUrl(accessToken, json.getString("openid")));  
			HttpResponse result2 = httpClient.execute(getWeChatUserInfo);  
			String resData2 = EntityUtils.toString(result2.getEntity());
			JSONObject json2 = JSONObject.parseObject(resData2);
			logger.info("获取用户信息getWechatOauth2UserInfoUrl"+resData2);
			String nickname = json2.getString("nickname");
			String sex = json2.getString("sex");
			String province = json2.getString("province");
			String city = json2.getString("city");
			String country = json2.getString("country");
			String headImgUrl = json2.getString("headimgurl");
			String unionid = json2.getString("unionid");
			
			weChatUser.setOpenId(openId);
			weChatUser.setCity(city);
			weChatUser.setCountry(country);
			weChatUser.setHeadImgUrl(headImgUrl);
			weChatUser.setNickname(nickname);
			weChatUser.setProvince(province);
			weChatUser.setSex(sex);
			weChatUser.setUnionId(unionid);
			
		return weChatUser;
	}
	
	public static boolean sendMessage(ClientBaseMessage msg) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient(); 
		HttpPost post = new HttpPost(MsgUtil.getWechatSendMsgUrl(getAccessToken()));  
		String json = JsonMapUtil.objectToJson(msg);
		logger.debug("发送给微信客户端的消息为：{}", json);
		StringEntity entity = new StringEntity(json, "UTF-8");
		post.setEntity(entity);
		HttpResponse result = httpClient.execute(post);
		int status = result.getStatusLine().getStatusCode();
		return status == 200;
	}
	
	public static String deleteCDATA(String msg) {
		if (msg.startsWith("<![CDATA[")) {
			int start = "<![CDATA[".length();
			int end = msg.lastIndexOf("]]>");
			return msg.substring(start, end);
		}
		return msg.trim();
	}
	
	public static boolean isRightResponse(String responseJson) {
		boolean flag = false;
		Map<String, Object> map = JsonMapUtil.jsonToMap(responseJson);
		Object errcode = map.get("errcode");
		if(errcode != null && WeChatConstants.SUCC == Integer.parseInt(errcode.toString())) {
			flag = true;
		}
		return flag;
	}
	
	public static String getMsgItemFromXML(String startStr, String endStr, String xmlStr) {
		int start = xmlStr.indexOf(startStr);
		int end = xmlStr.indexOf(endStr);
		return xmlStr.substring(start + startStr.length(), end);
	}
	
	
	/**
	 * 公众号用于调用微信JS接口的临时票据
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	public static String getJsapiTicket(String access_token) throws Exception{
		String jsapi_ticket = ApplicationCache.getInstance().getStr(WeChatUtil.JSAPI_TICKET);
		if(null == jsapi_ticket){
			DefaultHttpClient httpClient = new DefaultHttpClient(); 
			String ticketUrl = MsgUtil.getWechatJsSdkTicketUrl(access_token);
			HttpGet getTicket = new HttpGet(ticketUrl);
			HttpResponse result = httpClient.execute(getTicket);
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("获取公众号用于调用微信JS接口的临时票据返回"+resData);
			JSONObject json = JSONObject.parseObject(resData);
			jsapi_ticket = json.getString("ticket");
			ApplicationCache.getInstance().put(WeChatUtil.JSAPI_TICKET, jsapi_ticket, 36000);
		}
		return jsapi_ticket;
	}
	
	/**
	 * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
	 * 获取网页授权token
	 * @param code
	 * @return
	 */
	public static JSONObject getWYaccessToken(String code) throws Exception{
		DefaultHttpClient httpClient = new DefaultHttpClient(); 
		HttpGet getToken = new HttpGet(MsgUtil.getWechatAccessTokenWithCodeUrl(code));  
		HttpResponse result = httpClient.execute(getToken);  
		String resData = EntityUtils.toString(result.getEntity());
		logger.info("获取用户授权token"+resData);
		return JSONObject.parseObject(resData);
	}
	
	/**
	 * 获取微信公众号关注列表
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 *//*
	public static void getWxGeneralInfo() throws Exception{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String access_token = ApplicationCache.getInstance().getStr(KEY_ACCESS_TOKEN);
		if(null ==access_token ) access_token = getAccessToken();
		HttpGet getToken = new HttpGet(MsgUtil.getWechatGetUserList(access_token, ""));
		HttpResponse result = httpClient.execute(getToken);
		String res = EntityUtils.toString(result.getEntity());
		logger.info("获取公众号关注列表返回"+res);
		net.sf.json.JSONObject json  =  net.sf.json.JSONObject.fromObject(JSONObject.parseObject(res).get("data"));
		JSONArray jsonArray = json.getJSONArray("openid");
		for (int i = 0; i < jsonArray.size(); i++) {
			ApplicationConfig.wxGeneralSet.add(jsonArray.getString(i));
		}
		logger.info("缓存列表"+ApplicationConfig.wxGeneralSet);
	}*/
	
	/*public static void main(String[] args) {
		try {
			new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/applicationContext.xml"});
			getWxGeneralInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
