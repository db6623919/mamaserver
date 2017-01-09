/**
 *
 */
package com.mmzb.house.web.listener;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.ApplicationConfig;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;

import net.sf.json.JSONArray;


public class ServletContextInitListener implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(ServletContextInitListener.class);
	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	 
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	    //城市	
		this.getCityInfo();
		//省份
	    this.getProvInfo();
	}

	//获取城市信息
	public void getCityInfo(){
    	Map<String, Object> postData=new HashMap<String, Object>();
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getCities");
       		if("0".equals(result.getString("code"))){
       			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result.get("data")); 
	       			JSONArray arrayCitys = JSONArray.fromObject(jsonObject.get("cities"));
	       			for (int j = 0; j < arrayCitys.size(); j++) {
	       				net.sf.json.JSONObject cityObject = arrayCitys.getJSONObject(j);
	       				ApplicationConfig.cityMap.put(cityObject.getInt("cityId"), cityObject.getString("cityName"));
	       		}
       		}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
       
	}
	
	//获取省份信息
	public void getProvInfo(){
    	Map<String, Object> postData=new HashMap<String, Object>();
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getProvinces");
       		if("0".equals(result.getString("code"))){
       			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result.get("data")); 
	       			JSONArray arrayProvs = JSONArray.fromObject(jsonObject.get("provinces"));
	       			for (int j = 0; j < arrayProvs.size(); j++) {
	       				net.sf.json.JSONObject provObject = arrayProvs.getJSONObject(j);
	       				ApplicationConfig.provinceshuMap.put(provObject.getInt("provId"), provObject.getString("provName"));
	       		}
       		}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
       
	}
	
	
	/*//获取公众号关注列表
	public void getWxGeneralInfo(){
		
		
		
		
		
	}*/
	
}
