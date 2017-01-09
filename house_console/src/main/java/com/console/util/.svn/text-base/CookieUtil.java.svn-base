package com.console.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Cookie操作
 *	@ClassName:   CookieUtil
 *	@Description: TODO
 *	@author YY
 *	@date   2013-10-24 下午6:24:36
 */
public class CookieUtil {

	/**
	 * 
	 *  @Description: 增加cookie
	 *  @return void
	 *  @throws 
	 *  @author YY
	 *  @date   2013-10-25 下午2:31:04
	 */
	public static void addCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(60*60*24*7);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 
	 *  @Description: 删除cookie
	 *  @return void
	 *  @throws 
	 *  @author YY
	 *  @date   2013-10-25 下午2:31:04
	 */
	public static void deleteCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie); 
		
	}

	/**
	 * 
	 *  @Description: getCookie
	 *  @return void
	 *  @throws 
	 *  @author YY
	 *  @date   2013-10-25 下午2:31:04
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie c : cookies) {
			if (c.getName().equals(cookieName)) {
				return c;
			}
		}

		return null;
	}


}