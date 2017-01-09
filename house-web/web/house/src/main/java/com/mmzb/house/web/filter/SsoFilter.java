package com.mmzb.house.web.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.meidusa.fastjson.JSONArray;
import com.mmzb.house.ext.service.MemberService;
import com.mmzb.house.web.core.common.constants.CommonConstant;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.netfinworks.ma.service.response.PersonalMemberInfo;
import com.netfinworks.ma.service.response.PersonalMemberResponse;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.enums.SessionStatusKind;

/**
 * 
 * @author caohaihong 
 * useful: 检查用户是否登录：1.尚未登录，到第三方登录，并注册到vfsso；2.已登录，刷新用户信息，放行；
 */
public class SsoFilter implements Filter, InitializingBean {
	private static final Logger logger = LoggerFactory
			.getLogger(SsoFilter.class);
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	@Resource(name = "cachedSessionService")
	private IVfSsoSession<SsoUser> vfSsoSession;
	
	@Resource(name="memberService")
    private MemberService memberClient;
	
	private String encode = "UTF8";
	
	/**
	 * 初始化.
	 * 
	 * @param filterConfig servlet filter config
	 * @throws ServletException sevlet exception when error
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		logger.info("house_web getRequestURL :{}", httpRequest.getRequestURL());
		if (!isLogin(httpRequest, httpResponse, chain))
			return;
		chain.doFilter(httpRequest, httpResponse);
	}
	
	/**
	 * 强制登录
	 * @param ssoUser
	 * @param token
	 * @throws Exception
	 */
	public void forceIn(SsoUser ssoUser, String token)throws Exception{
		if (SessionStatusKind.pending.equals(ssoUser.getSessionStatus())) {
			vfSsoSession.forceIn(token, ssoUser);
		}
		
		ssoUser = (SsoUser)vfSsoSession.get(token);
		vfSsoSession.forceIn(token, ssoUser);
	}

	/**
	 * 检查用户是否登录
	 * @param request
	 * @param response
	 * @param chain
	 * @return
	 */
	public boolean isLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) {
		try {
			User user = getUser(request);
			
			if (user != null) {
				refreshSession(request, response, user.getId());
			}
		} catch (Exception e) {
			logger.debug("从VfSso获取用户信息失败..." + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 从vfsso中获取用户信息
	 * @param request
	 * @return
	 */
	public User getSSoUser(HttpServletRequest request) {
		try {
			User user = VfSsoUser.get();
			if (null != user) {
				if (SessionStatusKind.pending.toString().equals(user.getSessionStatus())
						|| SessionStatusKind.kicked.toString().equals(user.getSessionStatus())) {
					Cookie cookie = CasCookie.getCookie(request);
					if (null != cookie) {
						String token = cookie.getValue();
						SsoUser ssoUser = (SsoUser) vfSsoSession.get(cookie.getValue());
						vfSsoSession.forceIn(token, ssoUser);
					}
				}
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取用户登录信息:1.从交易平台单点登录获取用户信息；
	 * 				2.如果1)存在，则去第三方平台验证用户状态
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request) {
		try {
			User user = getSSoUser(request);
			if (null != user) {
				return user;
			}
		} catch (Exception e) {
			logger.info("获取用户登录信息失败：{}", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 刷新session中的用户信息
	 * @param request
	 * @param memberId
	 */
	private void refreshSession(HttpServletRequest request,
			HttpServletResponse responses, String memberId) {
		try {
			// 更新session
			HttpSession session = request.getSession();
			
			String userJsonString = (String)session.getAttribute(CommonConstant.SESSION_ATTR_NAME_CURRENT_USER);
			if (userJsonString == null) {
				setMemberInfo(userJsonString, memberId, session);
			} else {
				PersonalMemberInfo personalMemberInfo = JSONArray.parseObject(userJsonString, PersonalMemberInfo.class);
				if (!VfSsoUser.get().getId().equals(personalMemberInfo.getMemberId())) {
					logger.info("member id 不一致, session memberId: " + personalMemberInfo.getMemberId() + " sso memberId: " + VfSsoUser.get().getId());
					setMemberInfo(userJsonString, memberId, session);
				}
			}
		} catch (Exception e) {
			logger.error("Filter更新Session失败！");
			logger.error("", e);
		}
	}
	
	private void setMemberInfo(String userJsonString, String memberId, HttpSession session) {
		PersonalMemberResponse response = memberClient.queryPersonalMember(memberId);
		userJsonString = JSONArray.toJSONString(response.getPersonalMemberInfo());
		session.setAttribute(CommonConstant.SESSION_ATTR_NAME_CURRENT_USER, userJsonString);
	}
	
	/**
	 * destroy.
	 */
	@Override
	public void destroy() {

	}

}