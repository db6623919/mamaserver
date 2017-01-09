package com.mmzb.house.app.web.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.mmzb.house.app.web.common.util.JsonOutUtil;
import com.mmzb.house.app.web.common.util.MD5;
import com.mmzb.house.app.web.common.util.RSAUtil;
import com.mmzb.house.app.web.common.util.ResponseOutUtil;
import com.netfinworks.common.lang.StringUtil;

/**
 * @author lenovo
 * IOS APP通用过滤器
 */
public class GatewayFilter implements Filter {
	
	
	private Logger logger        = LoggerFactory.getLogger(GatewayFilter.class);
	private Logger deviceLogger  = LoggerFactory.getLogger("DEVICE-INFO");//收集手机设备相关信息

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				filterConfig.getServletContext());
	}

	@Override
	public void doFilter(ServletRequest reqt, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		DataResponse dataResp = new DataResponse();
		HttpServletRequest req = (HttpServletRequest)reqt;
		HttpServletResponse response = (HttpServletResponse)resp;
		try{
			//1、收集HTTP Header信息
			Map<String , String> infoMap = this.getHeader(req);
			
			//2、服务接口
			String service = req.getParameter("service");
			if(StringUtil.isEmpty(service)){
				logger.info("==========没有找到相关服务或接口=============");
				ResponseOutUtil.printOut(response, false, DataCommon.CODE0002, DataCommon.MSG_CODE0002);
				return;
			}
			
			//3、校验签名是否正确
			if(!this.verifySign(reqt, infoMap.get("x_client_request_token"))){
				logger.info("==========校验签名失败=============");
				ResponseOutUtil.printOut(response, false, DataCommon.CODE0003, DataCommon.MSG_CODE0003);
				return;
			}
			
			
			//放行
			chain.doFilter(reqt, resp);
			return;
		}catch(Exception e){
			logger.error("过滤器处理异常" ,e);
			dataResp.setSuccess(false);
			dataResp.setCode(DataCommon.CODE0001);
			dataResp.setMessage(DataCommon.MSG_CODE0001);
			JsonOutUtil.createRetMaptJSONObject(resp, dataResp);
		}
	}
	
	
	/**
	 * chenmeiyang
	 * 收集Http Header信息
	 */
	private Map<String , String> getHeader(HttpServletRequest req)throws Exception{
		String x_client_device = req.getHeader("x-client-device");
		String x_client_version = req.getHeader("x-client-version");
		String x_client_bundle_id = req.getHeader("x-client-bundle-id");
		String x_client_device_uuid = req.getHeader("x-client-device-uuid");
		String x_client_request_token = req.getHeader("x-client-request-token");
		
		if(null == x_client_request_token){//如果是测试
			x_client_request_token = "c0258933d65e7b48655aae9738f81f9a9dc02fa75ddd9f5641a5e793f6f1cc87564ed4d168a229c6e121e4260312afac488ba05c1f9b70211b184c1f6835eafed25fcf955f8e3d45361e97893cd53b2a8a7ddac50f64bee05a28dfe9b5a41bd6d46a6a143b20d469978bb48c5e66f002785a4a044853c7705d6d799a58a464bc";
		}
		
		//写入日志文件
		deviceLogger.info(x_client_device+ " " + x_client_version+" "+x_client_bundle_id+" "+x_client_device_uuid+" "+x_client_request_token );
	
	    Map<String , String> map = new HashMap<String , String>();
	    map.put("x_client_device", x_client_device);
	    map.put("x_client_version", x_client_version);
	    map.put("x_client_bundle_id", x_client_bundle_id);
	    map.put("x_client_device_uuid", x_client_device_uuid);
	    map.put("x_client_request_token", RSAUtil.decryptByPrivate(x_client_request_token));
	    
	    return map;
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private boolean verifySign(ServletRequest request , String salt){
		HashMap<String, Object> map = (HashMap<String, Object>) request.getParameterMap();//取所有串进来的参数
		List<Map.Entry<String,Object>> mappingList = new ArrayList<Map.Entry<String,Object>>(map.entrySet()); 
		//对map中的参数进行排序，
		Collections.sort(mappingList, new Comparator<Map.Entry<String,Object>>(){ 
			public int compare(Map.Entry<String,Object> mapping1,Map.Entry<String,Object> mapping2){ 
			    return mapping1.getKey().compareTo(mapping2.getKey()); 
			} 
		}); 
		Map.Entry<String,Object> mapEntry = null;
		String signvalue = "";
		//组装需加密的字符串
		for (int i = 0; i < mappingList.size(); i++) {
			mapEntry = mappingList.get(i);
			if(!"sign".equals(mapEntry.getKey())){
				signvalue = signvalue + mapEntry.getKey() + ((String[])mapEntry.getValue())[0];
				logger.info("传进来的参数"+i+":key"+mapEntry.getKey()+"-----------value=="+((String[])mapEntry.getValue())[0]);
			}
		}
		signvalue = signvalue + "salt"+ salt;
		logger.info("用来生成sign的值：signvalue:"+signvalue);
		String signType = request.getParameter("signType");
		String sign = request.getParameter("sign");
		boolean result = true;
		if ("RSA".equals(signType)) {

		} else if ("MD5".equals(signType)) { 
			if (!MD5.getEncodeString(signvalue).equals(sign.toUpperCase())) {// 签名不正确
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}
}
