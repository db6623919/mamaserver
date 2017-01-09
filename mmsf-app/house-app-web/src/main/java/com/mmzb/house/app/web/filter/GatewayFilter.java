package com.mmzb.house.app.web.filter;

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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.mmzb.house.app.web.util.MD5;
import com.mmzb.house.app.web.util.RSA_PEM_KEY;

/** ios、app对参数验签进行校验 */
public class GatewayFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(GatewayFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				filterConfig.getServletContext());
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (vifityValicode((HttpServletRequest)request)) {
			chain.doFilter(request, response);
			return;
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/errorReturn");
			dispatcher.forward(request, response);
			return;
		}
	}

	public void destroy() {
		
	}

	private boolean vifityValicode(HttpServletRequest request){
		String MD5_KEY = request.getHeader("x-client-dynamic-value");//头传过来动态值
		String signType = request.getParameter("signType");
		String sign = request.getParameter("sign");
		HashMap<String, Object> map = (HashMap<String, Object>) request.getParameterMap();//取所有串进来的参数
		if(sign == null || sign.trim().length() < 1) {
			if(request.getContentType() != null && request.getContentType().startsWith("multipart")) {
			    return true;
			}
		}
		
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
		MD5_KEY =  RSA_PEM_KEY.decryptByPrivate(MD5_KEY);
		signvalue = signvalue + MD5_KEY;
		logger.info("用来生成sign的值：signvalue:"+signvalue);
		boolean result = true;
		if ("MD5".equals(signType)) { // 签名不正确
			if (!MD5.getEncodeString(signvalue)
					.equals(sign.toUpperCase())) {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}
}