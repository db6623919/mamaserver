package com.mmzb.house.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Properties;

public class MsgUtil {
	
	private static Properties properties;

	public static void setProperties(Properties properties) {
		MsgUtil.properties = properties;
	}
	
	/**
	 * 根据key获取value(根据需要手动进行格式化)，建议在以下情况下使用：
	 *   <ul>
	 *   	<li>属性不常用</li>
	 *   	<li>属性值中参数个数或位置变化比较大</li>
	 *   </ul>
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * 获取微信公众号的appid
	 * 
	 * @return
	 */
	public static String getWechatAppId() {
		return properties.getProperty("wechat_app_id");
	}
	
	/**
	 * 获取微信公众号的的appSecret
	 * 
	 * @return
	 */
	public static String getWechatAppSecret() {
		return properties.getProperty("wechat_app_secret");
	}
	
	/**
	 * 获取妈妈送房上传图片到图片服务器的URL
	 * 
	 * @return
	 */
	public static String getUploadHouseUrl() {
		return properties.getProperty("upload_house_url");
	}
	
	/**
	 * 获取图片服务器用于标识妈妈送房的来源名字
	 * 
	 * @return
	 */
	public static String getUploadSource() {
		return properties.getProperty("upload_source");
	}
	
	/**
	 * 获取妈妈送房上传图片所在的文件组
	 * 
	 * @return
	 */
	public static String getUploadFileGroup() {
		return properties.getProperty("upload_file_group");
	}
	
	/**
	 * 获取微信授权URL
	 * 
	 * @return
	 */
	public static String getWechatAuthorizeUrl(String redirectUri) {
		return MessageFormat.format(properties.getProperty("wechat_authorize_url"), getWechatAppId(), redirectUri);
	}
	
	/**
	 * 获取微信access token的URL
	 * 
	 * @return
	 */
	public static String getWechatAccessTokenUrl() {
		return MessageFormat.format(properties.getProperty("wechat_access_token_url"), getWechatAppId(), getWechatAppSecret()).intern();
	}
	
	/**
	 * 获取微信获取JS SDK的ticket的URL
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getWechatJsSdkTicketUrl(String accessToken) {
		return MessageFormat.format(properties.getProperty("wechat_js_sdk_ticket_url"), accessToken);
	}
	
	/**
	 * 获取微信页面授权access token的URL
	 * 
	 * @param code
	 * @return
	 * @see <a href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html">微信公众平台开发者文档</a>
	 */
	public static String getWechatAccessTokenWithCodeUrl(String code) {
		return MessageFormat.format(properties.getProperty("wechat_access_token_with_code_url"), getWechatAppId(), getWechatAppSecret(), code);
	}
	
	/**
	 * 获取微信用户信息的URL(通过网页授权)
	 * 
	 * @param accessToken 网页授权access_token(不是普通的access token)
	 * @param openId
	 * @return
	 * @see <a href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html">微信公众平台开发者文档</a>
	 */
	public static String getWechatOauth2UserInfoUrl(String accessToken, String openId) {
		return MessageFormat.format(properties.getProperty("wechat_oauth2_user_info_url"), accessToken, openId);
	}
	
	/**
	 * 获取微信用户信息的URL
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 * @see <a href="http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html">微信公众平台开发者文档</a>
	 */
	public static String getWechatUserInfoUrl(String accessToken, String openId) {
		return MessageFormat.format(properties.getProperty("wechat_user_info_url"), accessToken, openId);
	}
	
	/**
	 * 获取推送微信消息的URL
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getWechatSendMsgUrl(String accessToken) {
		return MessageFormat.format(properties.getProperty("wechat_send_msg_url"), accessToken);
	}
	
	/**
	 * 获取带参数二维码的URL
	 * 
	 * @param ticket
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getWechatQrcodeUrl(String ticket) throws UnsupportedEncodingException {
		return MessageFormat.format(properties.getProperty("wechat_qrcode_url"), URLEncoder.encode(ticket,"utf-8"));
	}
	
	/**
	 * 获取带参数二维码ticket的URL
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getWechatQrcodeTicketUrl(String accessToken) {
		return MessageFormat.format(properties.getProperty("wechat_qrcode_ticket_url"), accessToken);
	}
	
	/**
	 * 获取二维码参数的URL
	 * 
	 * @param sceneId 场景ID
	 * @return
	 */
	public static String getWechatQrcodeParamUrl(String sceneId) {
		return MessageFormat.format(properties.getProperty("wechat_qrcode_param_url"), sceneId);
	}
	
	/**
	 * 获取充积分接口的URL
	 * 
	 * @return
	 */
	public static String getRewardFacadeUrl() {
		return properties.getProperty("chargeUrl") + "rewardFacade";
	}
	
	public static String getWechatGetUserList(String access_token,String next_openid){
		return MessageFormat.format(properties.getProperty("wechat_user_list"), access_token, next_openid);
	}
}
