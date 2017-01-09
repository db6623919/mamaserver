package com.mmzb.house.web.util.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class PayConstants {
	public static String APP_ID = "2016080501706666";//开放平台应用的APPID
	public static String ALIPAY_SELLER_ID = "2088021762920623";//支付宝账号相关收款账号
	public static String APP_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOfsjSwp/0/q58Wm" + 
							"cC/vJM6MdZLwvGsdkR8qJG531OwRKI4taIbFMyzpN44VI+TILkWHDCpfXvi5o742" + 
							"Pq0Jk24cingwDYHKgdeczuCFZK+LZhMHh7giDjb7V/g4s0VEtaCcPuXZAHQzA6mv" + 
							"8lvSM94nAu77+oyAiMT1Tzgw/spVAgMBAAECgYBhm572nV5EhjqxZpEEu4MkIt1A" + 
							"0GycMD9pfFUE4Q4NpFMLM3Uwc0JMfoLRG3iVyuIehO3Lz4Oq7oZJgY7zSHpxKhtT" + 
							"fhorDURI7a5ZJ6DnpUNXNU7Wf5i/kzMQFcPV8mB9h504sZ3m4kwDp1zYGSXQzGJ5" + 
							"PK1O9rH7BBXsbSw+gQJBAPTV/AvLmp4Pn0jwRBfCM38muTGvF9GhsaPpnYWFVw7D" + 
							"7Qig2jXjqaSx9nj0ACg7NaddcPrMTN890WlN8dkkSeUCQQDyf9guc5qDrdePVUHE" + 
							"ghlihLKBA31Wdrz0zQNHpUrM1lv6SAp/Plkim/hsPufxfqUsjCOOSkziRcGmPn2A" + 
							"cbexAkAMav4Jqx4AflJZ5LXPbq4l+NTTzEms/EKCScsPhp/Pw+g109K9gPqNu+kA" + 
							"YX/QipBCGN8uGUac3AnPCYEGvmxxAj9oMoaxbvr1mb0F2Zxo034U1u/cf6Nbg6mt" + 
							"hmniXyPE4FalNE9OXXvvYxVbbaw8GC7g9VZRzZs7nr2WsUM77aECQQC9RrcjpPZM" + 
							"ma4lIkGuROYq9d/Y99fLgme92/LFfZH+4CJMLHAWUbzhOJkmlDtJqDVOl6aG70wD" + 
							"U/a+Uf4lZCgD";//开发者应用私钥-PKCS8格式的私钥
	public static String CHARSET = "utf-8";//请求和签名使用的字符编码格式，支持GBK和UTF-8
	
	/** alipay网关地址 */
	public static String aliPayGateWayUrl = "https://openapi.alipay.com/gateway.do";
	/** 支付宝公钥 */
	public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	
	/** 异步通知url */
	public static String ALIPAY_ASYNC_NOTIFY_URL = "/pay/aliPayAsyncNotify.htm";//http://mmsfh5.nat123.net:35754
	
	/** 回调地址url */
	public static String ALIPAY_RETURL_URL = "/pay/alipayReturnUrl.htm";//http://mmsfh5.nat123.net:35754
	
	public static AlipayClient alipayClient = new DefaultAlipayClient(aliPayGateWayUrl, 
			APP_ID, 
			APP_PRIVATE_KEY, 
			"json", 
			CHARSET, 
			ALIPAY_PUBLIC_KEY);//获得初始化的AlipayClient
	
	public static AlipayClient getAlipayClient() {
		if(alipayClient == null) {
			alipayClient = new DefaultAlipayClient(aliPayGateWayUrl, 
					APP_ID, 
					APP_PRIVATE_KEY, 
					"json", 
					CHARSET, 
					ALIPAY_PUBLIC_KEY);//获得初始化的AlipayClient
		}
		return alipayClient;
	}
	
}
