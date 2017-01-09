package com.mmzb.house.web.action.base;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meidusa.fastjson.JSONArray;
import com.mmzb.house.web.action.dto.SessionUserInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.core.common.constants.CommonConstant;
import com.mmzb.house.web.core.common.util.Contants;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.ma.service.response.IdentityInfo;
import com.netfinworks.ma.service.response.PersonalMemberInfo;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;
import com.netfinworks.vfsso.client.exception.CasServiceException;

public class BaseAction {

	protected static Logger log = LoggerFactory.getLogger(BaseAction.class);

	/**
	 * 登录成功后保存session信息
	 * 
	 * @param session
	 * @param userInfo
	 * @param walletMemberInfo
	 */
	public static void loginSuccSaveToSession(HttpSession session, UserInfo userInfo, String token) {
		setJsonAttribute(session, Contants.SESSION_ATTR_NAME_CURRENT_USER, new SessionUserInfo(userInfo, token));
		session.setAttribute(Contants.SESSION_ATTR_NAME_IS_LOGIN, true);
	}

	/**
	 * 获取登录的用户信息
	 * 
	 * @param session
	 * @return
	 */
	public static SessionUserInfo getLoginUserInfo(HttpServletRequest request) {
		String token = getToken(request);
		User user = new User();
		UserInfo userInfo = new UserInfo();
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			try {
				user = VfSsoUser.get();
				userInfo.setPhone(user.getOpId());
				userInfo.setName(user.getName());
				userInfo.setUid(user.getId());
				userInfo.setMmWalletId(user.getId());
				userInfo.setNickName(user.getLoginName());
			} catch (CasServiceException e) {
				log.error("获取登录信息失败！token: " + token);
			}
		} 
		SessionUserInfo sessionUserInfo = new SessionUserInfo(userInfo, token);
		return sessionUserInfo;
	}

	/**
     * 获取token
     * 
     * @param hsr
     * @return
     */
    private static String getToken(HttpServletRequest hsr) {
        Cookie cookie = CasCookie.getCookie(hsr);
        return cookie == null ? null : cookie.getValue();
    }
    

	public static void setJsonAttribute(HttpSession session, String key, Object value) {
		String jsonStr = JSONArray.toJSONString(value);
		session.setAttribute(key, jsonStr);
	}

	public static <T> T getJsonAttribute(HttpSession session, String key, Class<T> clazz) {
		if (session.getAttribute(key) == null) {
			return null;
		}
		return JSONArray.parseObject((String) session.getAttribute(key), clazz);
	}
	
	/**
	 * 获取会话中的member信息
	 * @param session
	 * @return
	 */
	public static PersonalMemberInfo getMemberInfo(HttpSession session) {
		PersonalMemberInfo personalMemberInfo = getJsonAttribute(
				session, CommonConstant.SESSION_ATTR_NAME_CURRENT_USER, PersonalMemberInfo.class);
		return personalMemberInfo;
	}
	
	/**
	 * 获取回话中member的手机号码
	 * @param personalMemberInfo
	 * @return
	 */
	public static String getMemberPhone(PersonalMemberInfo personalMemberInfo) {
		for (IdentityInfo identityInfo : personalMemberInfo.getIdentitys()) {
			if(identityInfo.getIdentityType() == 2) {
				return identityInfo.getIdentity();
			}
		}
		return "";
	}
	
}
