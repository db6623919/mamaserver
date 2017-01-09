package com.mama.server.main.controller;

public class ConstValue {

	// order
	public static int ORDER_WAITING_FOR_PAYMENT = 9;// 待支付
	public static int ORDER_MERCHANT_VERIFYING = 10; // 待审核
	public static int ORDER_MERCHANT_VERIFIED = 11; // 待入住
	public static int ORDER_USER_LIVED = 12; // 已入住
	public static int ORDER_USER_LEAVED = 13; // 离店
	
	/**
	 * 用户超时未入住
	 */
	public static int ORDER_USER_NOT_LIVED = 14; 
	
	public static int ORDER_USER_TO_COMMIT = 15; // 待客户确认
	
	/**
	 * 退房
	 */
	public static int ORDER_MERCHANT_CHECKOUT = 16;
	
	public static int ORDER_USER_COMMENTED = 17;//已评价
	
	// public static int ORDER_USER_CANCELED = 21; // 客户取消订单
	// public static int ORDER_MERCHANT_CLOSED = 22; // 商户取消订单
	public static int ORDER_MERCHANT_REFUND = 23; // 已取消
	public static int ORDER_USER_PRE_FAIL = 24; // 预订失败
	public static int ORDER_UNKNOWN_ERROR = 50;
	public static int STOR_TO_PAY = 1;//到店付

	// flow
	public static int FLOW_RECHARGE = 100;
	public static int FLOW_REWARD = 200;
	public static int FLOW_PAY = 300;
	public static int FLOW_FROZEN = 400;
	public static int FLOW_UNFROZEN = 500;
	public static int FLOW_REFUND = 600;

	// sms
	public static int SMS_REGISTER = 1;
	public static int SMS_UPDATE_PASSWORD = 2;
	public static int SMS_ORDER = 3;
	public static int SMS_UPDATE_EMAIL = 4;
	public static int SMS_HOTEL_COUPON_GIVE = 5;

	// user channel
	public static int USER_WEB = 1;
	public static int USER_IOS = 2;
	public static int USER_ANDROID = 3;

	// house mark,flag
	public static long[] houseBinaryList = { 0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152,
			4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824, 2147483648L, 4294967296L, 8589934592L, 17179869184L, 34359738368L };

}
