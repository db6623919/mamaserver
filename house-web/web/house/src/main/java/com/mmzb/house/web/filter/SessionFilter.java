package com.mmzb.house.web.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.meidusa.fastjson.JSONArray;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.ext.request.CertRequest;
import com.mmzb.house.ext.response.CertResponse;
import com.mmzb.house.ext.service.CertService;
import com.mmzb.house.ext.service.MemberService;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.netfinworks.ma.service.response.PersonalMemberInfo;

/**
 * 
 * <p>
 * session过滤器，统一获取用户信息
 * </p>
 * 
 * @author qinde
 * @version $Id: SessionFilter.java, v 0.1 2013-11-19 上午9:17:11 qinde Exp $
 */
public class SessionFilter implements Filter, InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

	@Resource(name = "webResource")
	private WebDynamicResource webResource;
	@Resource
	MemberService memberService;
	@Resource
	CertService certService;
	@Resource(name = "appProperties")
	private AppProperties appProperties;

	private ConcurrentMap<String, Object> initMap;

	/**
	 * 获取cas-sso返回信息，查询会员信息
	 * 
	 * @param request
	 *            http request
	 * @param response
	 *            http response
	 * @param chain
	 *            filer chain
	 * @throws IOException
	 *             io exception
	 * @throws ServletException
	 *             servlet exception
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);

		if (session.getAttribute("init") == null) {
			session.setAttribute("init", getInitMap());
		}

		String token = request.getParameter("token");
		Object sessionUser = session.getAttribute(Contants.SESSION_ATTR_NAME_CURRENT_USER);
		if (StringUtils.isNotBlank(token) && sessionUser == null) {
			PersonalMemberInfo mr = memberService.checkToken(token);
			if (mr != null) {
				CertRequest certRequest = new CertRequest();
				certRequest.setMemberId(mr.getMemberId());
				CertResponse cr = certService.getAuthInfoByMemberId(certRequest);
				if (cr.isSuccess()) {
					mr.setMemberName(cr.getName());
				}
				Map<String, Object> postData = new HashMap<String, Object>();
				postData.put("mmWalletId", mr.getMemberId());
				postData.put("nickName", mr.getMemberName());
				postData.put("phone", mr.getDefaultLoginName());
				postData.put("name", mr.getMemberName());
				JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "mmWalletLogin");
				String code = result.getString("code");
				if ("0".equals(code)) {
					UserInfo userInfo = getUserInfo(result.getJSONObject("data").getString("uid"));
					BaseAction.loginSuccSaveToSession(session, userInfo, token);
					chain.doFilter(httpRequest, httpResponse);
					return;
				}
			}
		}

		String requestURI = StringUtils.substringAfter(httpRequest.getRequestURI(), httpRequest.getContextPath());
		if (requestURI.matches("/my/.*") && sessionUser == null) {
			String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
			if (url.startsWith("/") && url.length() > 1) {
				url = url.substring(1);
			}
			if (null != httpRequest.getQueryString()) {
				url = url + "?" + httpRequest.getQueryString();
			}

			assemblyUrl(request, response, chain, httpRequest, httpResponse, url);
		} else {
			chain.doFilter(httpRequest, httpResponse);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public <T> T getJsonAttribute(HttpSession session, String key, Class<T> clazz) {
		if (session.getAttribute(key) == null) {
			return null;
		}
		return JSONArray.parseObject((String) session.getAttribute(key), clazz);
	}

	/**
	 * destroy.
	 */
	@Override
	public void destroy() {

	}

	private void assemblyUrl(ServletRequest request, ServletResponse response, FilterChain chain, HttpServletRequest httpRequest, HttpServletResponse httpResponse, String url)
			throws IOException, ServletException {
		httpResponse.sendRedirect(
				webResource.getHostAddress() + httpRequest.getContextPath() + "/toLogin.htm?redirect_url=" + URLEncoder.encode(webResource.getHostAddress() + "/" + url));
	}

	public UserInfo getUserInfo(String uid) {
		UserInfo userInfo = new UserInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", uid);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getUserInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONObject jsonObject = result.getJSONObject("data");
				userInfo.setEmail(jsonObject.getString("email"));
				userInfo.setIcon(jsonObject.getString("icon"));
				userInfo.setIdCard(jsonObject.getString("idCard"));
				userInfo.setName(jsonObject.getString("name"));
				userInfo.setNickName(jsonObject.getString("nickName"));
				userInfo.setPhone(jsonObject.getString("phone"));
				userInfo.setUid(jsonObject.getString("uid"));
				userInfo.setMmWalletId(jsonObject.getString("mmWalletId"));
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return userInfo;
	}

	private ConcurrentMap<String, Object> getInitMap() {
		if (initMap != null)
			return initMap;
		synchronized (initMap = new ConcurrentHashMap<String, Object>()) {
			initMap.put("registerAddress", appProperties.getRegisterAddress());
			initMap.put("forgetPasswordAddress", appProperties.getForgetPasswordAddress());
			return initMap;
		}
	}
}