package com.console.framework.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.console.util.Constant;

/**
 * @class MyFilter
 * @description TODO
 * @author YY
 * @version 1.0
 * @date 2011-11-2/下午11:53:45
 */

public class MyFilter implements Filter {
	
	private Logger logger = Logger.getLogger(MyFilter.class);
	
	public static final ThreadLocal<HttpSession> session = new ThreadLocal<HttpSession>();
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String servletPath = request.getServletPath().trim();
		if(servletPath.charAt(0)=='/'){
			servletPath=servletPath.substring(1);
		}
		logger.info("request url：  here is MyFilter "+servletPath);
		
		if(request.getSession().getAttribute(Constant.SESSION_USERINFO)==null
			&& !"qiao/login.jsp".equals(servletPath)
			&& !"init.shtml".equals(servletPath)
			&& !"index.shtml".equals(servletPath)
			&& !"timeoutLogout.shtml".equals(servletPath)
			&& !"toLogin.shtml".equals(servletPath)
			&& !"qiao/qiaoCallback.shtml".equals(servletPath)
			&& !"test.shtml".equals(servletPath)){
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>window.parent.location='"+request.getContextPath()+"/init.shtml';</script>");
		}else{
			chain.doFilter(servletRequest, servletResponse);
		}
	}
	
	public void destroy() {}

	public void init(FilterConfig filterConfig) throws ServletException {}
}
