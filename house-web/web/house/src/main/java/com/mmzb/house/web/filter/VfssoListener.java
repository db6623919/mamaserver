package com.mmzb.house.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.cas.listener.DefaultListener;
import com.netfinworks.vfsso.client.common.CasEvent;
import com.netfinworks.vfsso.client.common.VfSsoClientLogger;
import com.netfinworks.vfsso.client.exception.CasServiceException;

public class VfssoListener extends DefaultListener {
    private static Logger logger = VfSsoClientLogger.getLogger();
    
    private static final String QUERY_STRING_FRIEND_CODE = "friendcode";

    @Override
    public void handleExceptionEvent(CasEvent event) {
        // TODO Auto-generated method stub
        super.handleExceptionEvent(event);
    }
    
    @Override
    public void handleNotLoginEvent(CasEvent event) {
    	redirectToLogin(event);
    }
    
    public static String removeParam(String queryString, String paramName) {
    	if (queryString != null && queryString.indexOf(paramName) >= 0) {
            if (queryString.indexOf("&") < 0) {
                int queryStart = queryString.indexOf("?");
                if (queryStart < 0) {
                    return null;
                } else {
                    return queryString.substring(0, queryStart);
                }
            }
            StringBuffer buf = new StringBuffer();
            int fromIndex = queryString.indexOf(paramName);
            int endIndex = queryString.indexOf("&", fromIndex);

            buf.append(queryString.subSequence(0,
                queryString.indexOf(paramName)));
            if (endIndex >= 0) {
                buf.append(queryString.substring(endIndex + 1));
            }
            String str = buf.toString();
            while (str.endsWith("&")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.indexOf(paramName) >= 0) {
                return removeParam(str, paramName);
            }
            return str;
        }
        return queryString;
    }

    @Override
    public void handleForbiddenEvent(CasEvent event) {
        logger.info("VfssoListener 进入处理");
        String appId = event.getConfig().getApiConfig().getAppId();
//        String loginUrl = event.getConfig().getApiConfig().getLoginUrl();
        String forbiddenUrl = event.getConfig().getApiConfig().getLoginUrl();
        try {
            User vfSsoUser = VfSsoUser.get();
            if ("EWEnterprise".equals(appId) && "person".equals(vfSsoUser.getUserType())) {//个人用户已登录的情况下访问特约商户网站，跳转到登录页面
                this.redirectToLogin(event);
            } else if ("EWPerson".equals(appId) && "enterprise".equals(vfSsoUser.getUserType())) {//特约商户已登录的情况下访问个人用户网站，跳转到登录页面
                this.redirectToLogin(event);
            } else if ("EWPerson".equals(appId) && "wap-person".equals(vfSsoUser.getUserType())) {//跳转到登录页面
                this.redirectToLogin(event);
            } else {
                this.redirectToUrl(event.getRequest(), event.getResponse(), forbiddenUrl);
            }
        } catch (CasServiceException e) {
            this.redirectToLogin(event);
        }

        //super.handleForbiddenEvent(event);
    }

    private void redirectToLogin(CasEvent event) {
    	logger.info("vfsso redirect to login from url : {}", event.getRequest().getRequestURI());
        String returnUrl = event.getRequest().getRequestURL().toString();
        String queryString = event.getRequest().getQueryString();
        
        // 记录朋友码，供单点登录系统记录
        String friendCode = event.getRequest().getParameter("friendcode");
        queryString = removeParam(queryString, QUERY_STRING_FRIEND_CODE);
        
        // 去掉queryString中的GuardianCookie信息
        queryString = removeCookieParam(queryString);
        if (queryString != null) {
            returnUrl = returnUrl + "?" + queryString;
        }
        
        try {
        	String ssoRedirectUrl = event.getConfig().getApiConfig().buildLoginUrl(returnUrl);
        	if (friendCode != null && friendCode.length() > 0)
        		ssoRedirectUrl = new StringBuffer(ssoRedirectUrl).append("&friendcode=").append(friendCode).toString();
        	
        	//刷单模块采用前后端分离，不能直接使用重定向
        	if (returnUrl.contains("clickFarming")) {
        		Map<String,Object> rmap = new HashMap<String,Object>();
        		ObjectMapper mapper = new ObjectMapper();
    			HttpServletResponse response = event.getResponse();
    			response.setCharacterEncoding("UTF-8");
    			response.setContentType("application/json;charset=UTF-8");
    			PrintWriter out = response.getWriter(); 
    			rmap.put("code", -2);
    			rmap.put("desc", "login fail PLS login again");
    			rmap.put("loginUrl", ssoRedirectUrl);
    			String rString = mapper.writeValueAsString(rmap);
    			out.print(rString);
    		}
        	else {
        		// 未登录 跳转到登录页面
        		logger.info("ssoRedirectUrl: " + ssoRedirectUrl);
        		event.getResponse().sendRedirect(ssoRedirectUrl);
			}
        } catch (IOException e) {
            logger.error("重定向异常", e);
        }
    }

    private void redirectToUrl(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            //response.sendRedirect(request.getContextPath() + url);
            response.sendRedirect(url);
        } catch (IOException e) {
            logger.error("重定向到" + url + "页面出错", e);
        }
    }

    //	private void writeRedirectResponse(HttpServletResponse response) {
    //		Map<String, Object> resp = new HashMap<String, Object>();
    //		resp.put("filterFoundNotLogin", true);
    //		resp.put("redirect", reloginUrl);
    //		try {
    //			response.setCharacterEncoding("UTF-8");
    //			response.setContentType("application/json;charset=utf-8");
    //			response.getWriter().write(JsonUtil.serialize(resp));
    //		} catch (IOException e) {
    //			logger.error("输出Json 重定向消息出错", e);
    //		}
    //	}
    //	
    //	private void writeForbiddenResponse(HttpServletResponse response) {
    //		Map<String, Object> resp = new HashMap<String, Object>();
    //		resp.put("filterFoundForbidden", true);
    //		try {
    //			response.setCharacterEncoding("UTF-8");
    //			response.setContentType("application/json;charset=utf-8");
    //			response.getWriter().write(JsonUtil.serialize(resp));
    //		} catch (IOException e) {
    //			logger.error("输出Json禁止消息出错", e);
    //		}
    //	}

    private boolean isDialogRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return "ria-dlg".equals(accept);
    }

    private boolean isModuleRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return "ria-module".equals(accept);
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestWith);
    }
}
