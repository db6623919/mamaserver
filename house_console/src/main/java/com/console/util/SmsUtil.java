package com.console.util;

import java.net.URLEncoder;

public class SmsUtil {
    public static boolean sendSms(String phone, String msg) {
        String encodedMsg = null;
        String sendResult = null;
        try {
            encodedMsg = URLEncoder.encode(msg, "UTF-8");
            String url = "http://61.145.229.29:9006/MWGate/wmgw.asmx/MongateCsSpSendSmsNew?userId=J10080&password=856015&pszMobis=" + phone + "&iMobiCount=1&pszSubPort=*&pszMsg=" + encodedMsg;
			sendResult = HttpClientGetMethod.httpGetUrl(url);
			 if (sendResult == null || sendResult.trim().compareTo("") == 0) {
		            return false;
		      }
		}  catch (Exception e) {
			return false;
		}
       
        return true;
    }
    //充值
    public static String genRechargeSms(String phone, String amt,String reward) {
        return "尊敬的" + phone + "用户，您已成功在妈妈送房网充值" + 
                amt + ".00元，平台已赠送您"+ reward +"积分，可立即开始订房，感谢您对妈妈送房的支持，希望能为您带来愉快的旅游体验。";
    }
    
 
}
