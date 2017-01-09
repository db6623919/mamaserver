package com.console.util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import com.console.controller.CityAction;
import com.meidusa.fastjson.JSONObject;

/**
 * @author chenmeiyang
 * 发送微信支付
 */
public class HttpClientGetMethod {
	
	public static Logger logger = Logger.getLogger(HttpClientGetMethod.class);
	
    public static String httpGetUrl(String url) throws ClientProtocolException, IOException{  
    	  
    		DefaultHttpClient httpClient = new DefaultHttpClient();  
            HttpGet getMethod = new HttpGet(url);
			HttpResponse result = httpClient.execute(getMethod);
			// 请求结束，返回结果
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("-----短信平台返回内容----【"+resData+"】--------");
	        return resData;
	}  
}
