package com.mmzb.house.app.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * header拦截器
 * @author whl
 *
 */
public class HeaderFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//获取头部header信息
		Enumeration headerNames = httpRequest.getHeaderNames();
		Map<String, Object>  heardMap = new HashMap<String, Object>();
	    while (headerNames.hasMoreElements()) {
	        String key = (String) headerNames.nextElement();
	        String value = httpRequest.getHeader(key);
	        //System.out.println("{"+key+":"+value+"}");
	        heardMap.put(key, value);
	    }
	    HttpSession session = httpRequest.getSession(); 
	    session.setAttribute("headerSession", heardMap);
	    chain.doFilter(httpRequest, httpResponse);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
