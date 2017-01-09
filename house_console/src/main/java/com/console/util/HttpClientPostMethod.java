package com.console.util;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.meidusa.fastjson.JSONObject;

public class HttpClientPostMethod {
	public static Logger logger = Logger.getLogger(HttpClientGetMethod.class);
    public static JSONObject httpReqUrl(Map<String,Object> postData,String interfaceStr) throws ClientProtocolException, IOException{  
    	String url = MsgPropertiesUtils.getCostumerUrl();
        JSONObject json = new JSONObject();  
        DefaultHttpClient httpClient = new DefaultHttpClient();  
  
        HttpPost method = new HttpPost(url);  
        JSONObject resJson=null;
			// 接收参数json列表
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("interface", interfaceStr);
			 jsonParam.put("param", postData);// 红谷滩新闻资讯，channelId														// 77
			 logger.info("发送server报文"+jsonParam.toString());
			StringEntity entity = new StringEntity(jsonParam.toString(),
					"utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			
			method.setEntity(entity);

			HttpResponse result = httpClient.execute(method);  

			// 请求结束，返回结果
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("接受server报文"+resData);
			resJson = json.parseObject(resData);
			String code = resJson.get("code").toString(); // 对方接口请求返回结果：0成功		
			if (!StringUtils.isBlank(code) && code.trim().equals("0")) {// 成功
				logger.info("业务处理成功！");
			} else {
				logger.error("业务处理异常");
			}
	
		return resJson;
	}  
    
    public static JSONObject httpCustReqUrl(Map<String,Object> postData,String interfaceStr) throws ClientProtocolException, IOException{  
    	String url= MsgPropertiesUtils.getMaMaServerUrl();
        JSONObject json = new JSONObject();  
        DefaultHttpClient httpClient = new DefaultHttpClient();  
  
        HttpPost method = new HttpPost(url);  
        JSONObject resJson=null;
			// 接收参数json列表
        JSONObject jsonParam = new JSONObject();
			jsonParam.put("interface", interfaceStr);
			 jsonParam.put("param", postData);// 红谷滩新闻资讯，channelId														// 77
			 logger.info("发送server报文"+jsonParam.toString());
			StringEntity entity = new StringEntity(jsonParam.toString(),
					"utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			
			method.setEntity(entity);

			HttpResponse result = httpClient.execute(method);  

			// 请求结束，返回结果
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("接受server报文"+resData);
			resJson = json.parseObject(resData);
			String code = resJson.get("code").toString(); // 对方接口请求返回结果：0成功														
			if (!StringUtils.isBlank(code) && code.trim().equals("0")) {// 成功
				logger.info("业务处理成功！");
			} else {
				logger.error("业务处理异常");
			}
	
		return resJson;
	} 
    public static JSONObject httpDataReqUrl(Map<String,Object> postData,String interfaceStr) throws ClientProtocolException, IOException{  
    	String url= MsgPropertiesUtils.getMaMaServerUrl();
    	 JSONObject json = new JSONObject();  
         DefaultHttpClient httpClient = new DefaultHttpClient();  
   
         HttpPost method = new HttpPost(url);  
         JSONObject resJson=null;

		// 接收参数json列表
         net.sf.json.JSONObject jsonParam = new net.sf.json.JSONObject();
			jsonParam.put("interface", interfaceStr);
			jsonParam.put("param", postData);// 红谷滩新闻资讯，channelId	// 77								// 77
			logger.info("发送server报文"+jsonParam.toString());
			StringEntity entity = new StringEntity(jsonParam.toString(),
					"utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			
			method.setEntity(entity);

			HttpResponse result = httpClient.execute(method);  

			// 请求结束，返回结果
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("接受server报文"+resData);
			resJson = json.parseObject(resData);
			String code = resJson.get("code").toString(); // 对方接口请求返回结果：0成功

			if (!StringUtils.isBlank(code) && code.trim().equals("0")) {// 成功
				logger.info("业务处理成功！");
			} else {
				logger.error("业务处理异常");
			}
	
		return resJson;
	}
	public static JSONObject httpMerchReqUrl(Map<String, Object> postData, String interfaceStr)
			throws ClientProtocolException, IOException {
		String url = MsgPropertiesUtils.getMerchantUrl();
		JSONObject json = new JSONObject();
		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpPost method = new HttpPost(url);
		JSONObject resJson = null;
		// 接收参数json列表
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("interface", interfaceStr);
		jsonParam.put("param", postData);// 红谷滩新闻资讯，channelId // 77
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		method.setEntity(entity);

		HttpResponse result = httpClient.execute(method);

		// 请求结束，返回结果
		String resData = EntityUtils.toString(result.getEntity());
		resJson = json.parseObject(resData);
		String code = resJson.get("code").toString(); // 对方接口请求返回结果：0成功
		if (!StringUtils.isBlank(code) && code.trim().equals("0")) {// 成功
			logger.info("业务处理成功！");
		} else {
			logger.error("业务处理异常");
		}
		return resJson;
	}
	
}