package com.mmzb.house.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.ext.common.ValueConsts;
import com.mmzb.house.ext.request.CertRequest;
import com.mmzb.house.ext.request.MemberRequest;
import com.mmzb.house.ext.response.CertResponse;
import com.mmzb.house.ext.response.MemberResponse;
import com.mmzb.house.ext.service.CertService;
import com.mmzb.house.ext.service.MemberService;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.dto.WalletMemberInfoBean;
import com.mmzb.house.web.action.util.JsonGeneratorUtils;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.filter.WebDynamicResource;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.ma.service.response.PersonalMemberInfo;
import com.netfinworks.service.exception.ServiceException;
import com.netfinworks.vfsso.cas.sevlet.VfSsoCookie;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;
import com.netfinworks.vfsso.client.common.VfSsoClientConfig;

@Controller
public class LoginAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;

	@Autowired
	public GerneralMethod gerneralMethod;

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "certService")
	private CertService certService;

	@RequestMapping(value = "/my/toLogin.htm")
    public ModelAndView toLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return new ModelAndView("redirect:/index.htm");
    }
	
	
	// 登录
	@Deprecated
	@RequestMapping(value = "/xxx/toLogin.htm", method = { RequestMethod.GET })
	public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		try {
			if (loginUser != null) {
				return new ModelAndView("redirect:/index.htm");
			}
		} catch (Exception e) {
			logger.error("登陆异常", e);
		}
		model.put("redirect_url", request.getParameter("redirect_url") == null ? "/toLogin.htm" : request.getParameter("redirect_url"));
		return new ModelAndView(Contants.URL_PREFIX + "/login");
	}

	/**
	 * 获取用户登录信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryUserInfo.htm", method = { RequestMethod.POST })
	@ResponseBody
	public RestResponse queryUserInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		RestResponse restP = new RestResponse();
		Map<String, Object> data = new HashMap<String, Object>();
		
		restP.setSuccess(false);
		String token = getToken(req);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			User ssoUser = VfSsoUser.get();
			if (ssoUser != null) {
				PersonalMemberInfo memberInfo = new PersonalMemberInfo();
				memberInfo.setMemberId(ssoUser.getId());
				memberInfo.setDefaultLoginName(ssoUser.getLoginName());
				
				data.put("memberInfo", memberInfo);
				restP.setData(data);
				restP.setSuccess(true);
			}
		} 
		
		return restP;
	}
	
	/**
     * 获取token
     * 
     * @param hsr
     * @return
     */
    private String getToken(HttpServletRequest hsr) {
        Cookie cookie = CasCookie.getCookie(hsr);
        logger.debug("Get cookie:{}", cookie);
        return cookie == null ? null : cookie.getValue();
    }
    
	@RequestMapping(value = "/login.htm", method = { RequestMethod.POST })
	public void login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String code = "0";
		String msg = "登录成功";
		try {
			if (this.checkCode(request, request.getParameter("code")) && 1 == Integer.valueOf(request.getParameter("flag"))) {
				code = "1";
				msg = "验证码输入错误";
				JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
				return;
			}
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
				logger.info("手机号：" + phone + "为空或者密码：" + password + "为空");
				JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "手机号与密码输入不正确");
				return;
			}

			WalletMemberInfoBean mmWalletMemberInfo = new WalletMemberInfoBean();
			mmWalletMemberInfo.setPhone(phone);
			MemberRequest memberRequest = new MemberRequest();
			memberRequest.setPhone(phone);
			memberRequest.setKey(ValueConsts.UES_KEY);
			memberRequest.setPassword(password);
			memberRequest.setPlatformType("1");
			MemberResponse mr = memberService.checkLoginPwd(memberRequest);
			if (!mr.isSuccess()) {
				logger.info("手机号：" + phone + "密码：" + password + "输入不正确");
				JsonGeneratorUtils.createRetMaptJSONObject(response, "2", "用户或密码不正确");
				return;
			}
			String memberId = mr.getMemberId();
			mmWalletMemberInfo.setMemberId(memberId);
			mmWalletMemberInfo.setMemberName(mr.getMemberName());
			CertRequest certRequest = new CertRequest();
			certRequest.setMemberId(memberId);
			CertResponse cr = certService.getAuthInfoByMemberId(certRequest);
			if (cr.isSuccess()) {
				mmWalletMemberInfo.setName(cr.getName());
				mmWalletMemberInfo.setCertNo(cr.getCertNo());
			}
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mmWalletId", mmWalletMemberInfo.getMemberId());
			postData.put("nickName", mmWalletMemberInfo.getMemberName());
			postData.put("phone", mmWalletMemberInfo.getPhone());
			postData.put("name", mmWalletMemberInfo.getName());
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "mmWalletLogin");
			code = result.getString("code");
			if (!"0".equals(code)) {
				JsonGeneratorUtils.createRetMaptJSONObject(response, code, "登录失败");
			} else {
				UserInfo userInfo = getUserInfo(result.getJSONObject("data").getString("uid"));
				loginSuccSaveToSession(request.getSession(), userInfo, mr.getToken());
				JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
			}
		} catch (Exception e) {
			logger.error("使用妈妈账户登录异常", e);
		}
	}

	/**
	 * 用户退出
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toLogout.htm", method = { RequestMethod.GET })
	public ModelAndView toLoginOut(HttpServletRequest req, HttpServletResponse resp, ModelMap model) throws UnsupportedEncodingException {
		// http://妈妈钱包地址/ssoLogout.htm?returnUrl=URLEncoder.encode("http://m.mmsfang.com/logout.htm", "UTF-8")
		String ssoLogout = VfSsoClientConfig.getDefaultConfig().getDefaultApiConfig().getLogoutUrl();
		StringBuffer returnUrl = req.getRequestURL();
		String logoutUrl = ssoLogout + "?returnUrl=" + URLEncoder.encode(returnUrl.toString().replace("toLogout", "logout"), "UTF-8");
		logger.info("logoutUrl: " + logoutUrl);
		return new ModelAndView("redirect:" + logoutUrl);
	}
	
	@RequestMapping(value = "/logout.htm", method = { RequestMethod.GET })
	public ModelAndView loginOut(HttpServletRequest req, HttpServletResponse resp, ModelMap model) {
		// 判断是否已经退出
		
		VfSsoUser.clear();
		VfSsoCookie.removeCookie(resp);
		req.getSession().invalidate();
		return new ModelAndView("redirect:/index.htm");
	}

	@RequestMapping(value = "/my/toUpdateUserInfo.htm", method = { RequestMethod.POST })
	public ModelAndView toUpdateUserInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		RestResponse restP = new RestResponse();
		return new ModelAndView(Contants.URL_PREFIX + "/person/updateUserInfo", "response", restP);
	}

	// 更新用户信息
	@RequestMapping(value = "/my/updateUserInfo.htm", method = { RequestMethod.POST })
	public void updateUserInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		try {
			if (loginUser == null) {
				logger.info("用户未登陆");
				JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "用户未登陆");
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			RestResponse restP = new RestResponse();
			Map<String, Object> postData = new HashMap<String, Object>();
			String nickName = request.getParameter("nickName");
			String email = request.getParameter("email");
			postData.put("email", email);
			postData.put("nickName", nickName);
			postData.put("uid", loginUser.getUid());
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "updateUserInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			// 更新成功之后，刷新session用户信息
			if ("0".equals(code)) {
				UserInfo userinfo = getUserInfo(loginUser.getUid());
				setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, userinfo);
			}
			restP.setData(map);
			JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

	}

	// 跳转到实名认证
	@RequestMapping(value = "/my/toVerifyIdentity.htm", method = { RequestMethod.POST })
	public ModelAndView toVerifyIdentity(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		RestResponse restP = new RestResponse();
		return new ModelAndView(Contants.URL_PREFIX + "/person/verifyIdentity", "response", restP);
	}

	// 实名认证
	@RequestMapping(value = "/my/verifyIdentity.htm", method = { RequestMethod.POST })
	public void verifyIdentity(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		try {
			if (loginUser == null) {
				logger.info("用户未登陆");
				JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "用户未登陆");
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			RestResponse restP = new RestResponse();
			Map<String, Object> postData = new HashMap<String, Object>();
			String name = request.getParameter("name");
			String idCard = request.getParameter("idCard");
			postData.put("uid", loginUser.getUid());
			postData.put("name", name);
			postData.put("idCard", idCard);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "verifyIdentity");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			// 更新成功之后，刷新session用户信息
			if ("0".equals(code)) {
				UserInfo userinfo = getUserInfo(loginUser.getUid());
				setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, userinfo);
			}
			map.put("msg", msg);
			map.put("code", code);
			restP.setData(map);
			JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
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

	@RequestMapping(value = "/checkLoginStatus.htm")
	public void checkLoginStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		try {
			if (loginUser == null) {
				logger.info("用户未登陆");
				JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "用户未登陆");
				return;
			} else {
				logger.info("用户已登陆");
				JsonGeneratorUtils.createRetMaptJSONObject(response, "0", "用户已登陆");
				return;
			}
		} catch (Exception e) {
			logger.error("获取登陆状态异常", e);
		}
	}

	@RequestMapping(value = "/checkLoginRole.htm")
	public void checkLoginRole(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		try {
			postData.put("phone", request.getParameter("phone"));
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "isPhoneRegistered");
			String code = result.getString("code");
			map.put("code", code);
			if ("0".equals(code)) {
				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result.get("data"));
				int registered = jsonObject.getInt("registered");
				int recharged = jsonObject.getInt("recharged");
				if (registered == 1 && recharged == 1) {
					JsonGeneratorUtils.createRetMaptJSONObject(response, "0", "手机号已注册已充值");
				} else if (registered == 1 && recharged == 0) {
					JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "手机号已注册未充值");
				} else {
					JsonGeneratorUtils.createRetMaptJSONObject(response, "2", "该手机未注册，无法重置密码");
				}
			} else {
				JsonGeneratorUtils.createRetMaptJSONObject(response, "2", "手机号未注册");
			}

		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

	}

	private boolean checkCode(HttpServletRequest request, String code) {
		String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		return StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(kaptchaExpected) && !code.equals(kaptchaExpected);
	}

}
