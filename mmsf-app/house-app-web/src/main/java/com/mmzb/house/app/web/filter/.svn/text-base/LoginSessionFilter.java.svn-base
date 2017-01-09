package com.mmzb.house.app.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.RestResponse;
import com.mmzb.house.app.common.util.JsonUtil;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.vo.LoginInfoVo;
import com.mmzb.house.app.vo.UserInfoVo;
import com.mmzb.house.app.web.util.JsonGeneratorUtils;
import com.netfinworks.authorize.ws.dataobject.MemberToken;
import com.netfinworks.authorize.ws.response.impl.GetMemberTokenResponse;

/** session过滤器，统一获取用户信息 */
public class LoginSessionFilter implements Filter, InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(LoginSessionFilter.class);

	private MemberTokenService memberTokenService;
	
	/** 初始化. */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext context = filterConfig.getServletContext();  
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        memberTokenService = (MemberTokenService) ctx.getBean("memberTokenService");
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/** 拦截accessToken并进行相关登录状态的校验 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String accessToken = httpRequest.getParameter("accessToken");
		if(accessToken == null || accessToken.trim().length() < 1) {
			accessToken = httpRequest.getHeader("x-client-accessToken");
		}
		if(accessToken == null || "".equals(accessToken)){
			RestResponse restP = new RestResponse();
			restP.setCode(Constants.APP_LOGIN_UN_TOKEN);
			restP.setSuccess(false);
			restP.setMessage("没有令牌不能访问");
			JsonGeneratorUtils.createRetMaptJSONObject(httpResponse, restP);
            return ;
		}
		
		String str = isUserInLoginByAccessTokenId(accessToken);
   		if(str != null && str.trim().length() > 0){
	   		LoginInfoVo loginVo = JsonUtil.jsonToObject(str, LoginInfoVo.class);
	   		if(!"0".equals(loginVo.getCode())){
	   			memberTokenService.delLoginInfoFromRedis(accessToken);
	   			RestResponse restP = new RestResponse();
	   			restP.setCode(Constants.APP_LOGIN_TOKEN_EXPIRE);
	   			restP.setSuccess(false);
	   			restP.setMessage("自动登录有效期已过,请重新登录!");
	   			JsonGeneratorUtils.createRetMaptJSONObject(httpResponse, restP);
	   			return ;
	   		} else {
	   			UserInfoVo vo = new UserInfoVo();
	   			vo.setMemberId(loginVo.getMemberId());
	   			vo.setMobile(loginVo.getMobile());
	   			memberTokenService.saveLoginInfoToRedis(accessToken, vo);
	   			chain.doFilter(httpRequest, httpResponse);
	   		}
   		} else {
   			RestResponse restP = new RestResponse();
			restP.setCode(Constants.APP_LOGIN_SYTEM_ERROR);
			restP.setSuccess(false);
			restP.setMessage("系统异常，请稍后再试!");
			JsonGeneratorUtils.createRetMaptJSONObject(httpResponse, restP);
            return ;
   		}
	}
	
	public String isUserInLoginByAccessTokenId(String accessToken){
    	Map<String, String> map = new HashMap<String, String>();
    	String code = "-1";
    	String msg = "接口调用异常！";
    	try {
	    	GetMemberTokenResponse memberTokenResponse = memberTokenService.selectTokenRecord(accessToken);
	    	if(memberTokenResponse != null) {
	    		MemberToken memberToken = memberTokenResponse.getMemberToken();
	    		if(memberToken != null) {
	    			Date dateNow = new Date();
	    			/** 未过期 */
	    			if(memberToken.getExpiredTime().getTime() >= dateNow.getTime()){
	    				/** 状态(1 有效 0 无效) */
	    				if (memberToken.getStatus() == 1) {
	    					String memberId = memberToken.getMemberId();
	    					String mobile = memberTokenService.queryMobileByMemberId(memberId);
	    					code = "0";
	    					msg = "查询成功！";
	    					map.put("memberId", memberId);
	    					map.put("mobile", mobile);//
	    				} else {//无效
	    					code = "-2";
	    					msg = "查询成功！";
	    				}
	    			} else {
	    				code = "-3";//已过期
	    				msg = "查询成功！";
	    			}
	    		}
	    	}
    	} catch(Exception e) {
    		logger.error("系统异常！", e);
    	}
    	map.put("code", code);
    	map.put("msg", msg);
    	return JsonUtil.objectToString(map);
    }
	
	@Override
	public void destroy() {

	}

}