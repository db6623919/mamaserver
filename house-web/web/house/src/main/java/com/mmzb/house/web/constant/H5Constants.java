package com.mmzb.house.web.constant;

public class H5Constants {
	public static final String H5_SUCCESSED = "00000000";  	//成功
	
	public static final String H5_FAILED = "99990000";     	//失败
	
	public static final String APP_SIGN_FAILED = "99990001"; 	//签名错误
	
	public static final String APP_TOKEN_INVALID = "99990002";  //令牌无效
	
	public static final String APP_TOKEN_EXPIRATION = "99990003";        //令牌过期   
	
	public static final String APP_PHONE_IS_NULL = "00010001";       //手机号码不能为空
	
	public static final String APP_LOGIN_PASSWORD_IS_NULL = "00010002";    //登录密码不能为空
	
	public static final String APP_PHONE_PASSWORD_IS_DIFFERENT = "00010003";   //手机号和密码不匹配
	
	public static final String APP_LOGIN_UN_TOKEN = "00010004";//没有令牌不能访问
	
	public static final String APP_LOGIN_TOKEN_EXPIRE = "00010005"; //自动登录有效期已过,请重新登录
	
	public static final String APP_LOGIN_SYTEM_ERROR = "00010006"; //系统异常
	
	public static final String APP_PHONE_IS_NULL2 = "00020001";        //手机号码不能为空-发送短信
	 
	public static final String APP_PHONE_WRONG_FORMAT = "00020001";    //手机号码格式不正确-发送短信
	
	public static final String APP_REPEATED_PAY = "00030000";     	//重复支付
	
	public static final int SPECIAL_COUNT=20;//特价房源相关数
}
