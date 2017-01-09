package com.mmzb.house.web.core.common.util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.core.common.vo.Response;

public class HttpClientPostMethod {
	private static Logger logger = LoggerFactory.getLogger(HttpClientPostMethod.class);
    public static JSONObject httpReqUrl(Map<String,Object> postData, String url,String interfaceStr) throws ClientProtocolException, IOException{  
  
        JSONObject json = new JSONObject();  
        DefaultHttpClient httpClient = new DefaultHttpClient();  
  
        HttpPost method = new HttpPost(url);  
        JSONObject resJson=null;
			// 接收参数json列表
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("interface", interfaceStr);
			 jsonParam.put("param", postData);// 红谷滩新闻资讯，channelId														// 77
			StringEntity entity = new StringEntity(jsonParam.toString(),
					"utf-8");// 解决中文乱码问题
			logger.info("-----发送后端数据包内容----【"+jsonParam.toString()+"】--------");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			
			method.setEntity(entity);

			HttpResponse result = httpClient.execute(method);  

			// 请求结束，返回结果
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("-----接收后端数据包内容----【"+resData+"】--------");
			resJson = json.parseObject(resData);
			String code = resJson.get("code").toString(); // 对方接口请求返回结果：0成功														
			if (!StringUtils.isBlank(code) && code.trim().equals("0")) {// 成功
				logger.info("业务处理成功！");
			} else {
				logger.error("业务处理异常");
			}
	
		return resJson;
	}  
    public static JSONObject httpDataReqUrl(Map<String,Object> postData, String url,String interfaceStr) throws ClientProtocolException, IOException{  
    	  
    	 JSONObject json = new JSONObject();  
         DefaultHttpClient httpClient = new DefaultHttpClient();  
   
         HttpPost method = new HttpPost(url);  
         JSONObject resJson=null;

		// 接收参数json列表
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("interface", interfaceStr);
			Map<String,Object> dataParam=new HashMap<String, Object>();
			dataParam.put("condition", postData);
			jsonParam.put("param", dataParam);// 红谷滩新闻资讯，channelId	// 77								// 77
			logger.info("-----发送后端数据包内容----【"+jsonParam.toString()+"】--------");
			StringEntity entity = new StringEntity(jsonParam.toString(),
					"utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			
			method.setEntity(entity);

			HttpResponse result = httpClient.execute(method);  

			// 请求结束，返回结果
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("-----接收后端数据包内容----【"+resData+"】--------");
			resJson = json.parseObject(resData);
			String code = resJson.get("code").toString(); // 对方接口请求返回结果：0成功

			if (!StringUtils.isBlank(code) && code.trim().equals("0")) {// 成功
				logger.info("业务处理成功！");
			} else {
				logger.error("业务处理异常");
			}
	
		return resJson;
	} 
    
    public static Response httpReqUrl(Map<String,Object> postData, String serverUrl, String interfaceName, Class className) 
    		throws ClientProtocolException, IOException{  
        DefaultHttpClient httpClient = new DefaultHttpClient();  
        HttpPost method = new HttpPost(serverUrl);
        
        // 接收参数json列表
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("interface", interfaceName);
        paramMap.put("param", postData);												// 77
        StringEntity entity = new StringEntity(JsonUtil.objectToString(paramMap), "utf-8");// 解决中文乱码问题
        logger.info("-----发送后端数据包内容----【" + paramMap.toString() + "】--------");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
			
        method.setEntity(entity);

        HttpResponse result = httpClient.execute(method);  

        // 请求结束，返回结果
        String resData = EntityUtils.toString(result.getEntity());
        logger.info("-----接收后端数据包内容----【"+resData+"】--------");
        JSONObject resultJsonObj = JSON.parseObject(resData);
        String code = resultJsonObj.getString("code"); 
        String msg = resultJsonObj.getString("msg");
        String data = resultJsonObj.getString("data");
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        if(className != null) {
        	response.setData(JsonUtil.jsonToObject(data, className));
        } else {
        	response.setData(data);
        }
        return response;
	}
}
