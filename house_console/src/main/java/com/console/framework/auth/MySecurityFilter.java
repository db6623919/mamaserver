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

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.console.util.Constant;


/**
 *  请求类(4)
 *	@ClassName:   MyAccessDecisionManager
 *	@Description: TODO
 *	@author YY
 *	@date   2013-9-28 上午11:40:05
 */

public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
	//public static Logger logger = Logger.getLogger(MySecurityFilter.class);
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	
	private void invoke(FilterInvocation fi) throws IOException, ServletException {
		String url = fi.getRequestUrl();
		if(url.charAt(0)=='/'){
			url=url.substring(1);
		}
		
		//logger.info("request url：   "+url);
		HttpSession session = fi.getRequest().getSession();
		

		if(session.getAttribute(Constant.SESSION_USERINFO)==null
				&& !"qiao/qiaoCallback.shtml".equals(url)){
			logout(fi.getRequest(), fi.getResponse());
		}else{
			InterceptorStatusToken token = null;			
			token = super.beforeInvocation(fi);
			try {
				fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			} finally {
				super.afterInvocation(token, null);
			}
		}
	
	}

	/**
	 * 退出到登录页面
	 * @param servletRequest
	 * @param servletResponse
	 * @throws IOException
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>window.parent.frames.location.href='"+request.getContextPath()+"/init.shtml';</script>");
	}

	
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
	
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	public void destroy() {
		
	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		//下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
		return FilterInvocation.class;
	}
}
