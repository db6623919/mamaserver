package com.mmzb.house.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.domain.common.RewardTypeEnum;
import com.mmzb.charge.facade.RewardFacade;
import com.mmzb.charge.facade.entity.RewardInsertRequest;
import com.mmzb.charge.facade.entity.RewardInsertResponse;
import com.mmzb.fdfs.facade.FileFacade;
import com.mmzb.fdfs.facade.entity.FileUploadRequest;
import com.mmzb.fdfs.facade.entity.FileUploadResponse;
import com.mmzb.house.web.action.base.ApplicationCache;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.DeveloperInfo;
import com.mmzb.house.web.action.dto.OpenIdInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.JsonGeneratorUtils;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.WeChatUser;
import com.mmzb.house.web.util.MsgUtil;
import com.mmzb.house.web.util.PointUtil;
import com.mmzb.house.web.util.QrcodeUtil;
import com.mmzb.house.web.util.WeChatUtil;
import com.netfinworks.common.lang.StringUtil;

import net.sf.json.JSONArray;

@Controller
public class RegisterAction extends BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(RegisterAction.class);

	@Resource(name = "appProperties")
	private AppProperties appProperties;

	@Autowired
	public GerneralMethod gerneralMethod;
	
	@RequestMapping(value = "/toRegister.htm", method = { RequestMethod.GET })
	public ModelAndView toRegister(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String friendCode = request.getParameter("friendCode");
		if(null == friendCode)
			friendCode = (String)request.getSession().getAttribute("friendCode");
		logger.info("准备跳转到注册页面(toRegister)，朋友码为：{}", friendCode);
		model.addAttribute("friendCode", friendCode);
		String openId = (String)request.getSession().getAttribute("openId");
		if(null != openId){
			logger.info("用户微信号:"+openId);
			model.addAttribute("openId", openId);
		}
		return new ModelAndView(Contants.URL_PREFIX + "/register", model);
	}

	// 注册
	@RequestMapping(value = "/register.htm", method = { RequestMethod.POST })
	public void register(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String verifyCode = request.getParameter("verifyCode");
		String friendCode = request.getParameter("friendCode");
		String openId = request.getParameter("openId");
		postData.put("phone", phone);
		postData.put("nickName", phone);
		postData.put("password", password);
		postData.put("verifyCode", verifyCode);
		postData.put("channel", 1);
		if(StringUtils.isNotEmpty(friendCode))
			postData.put("friendCode", friendCode);
		if(StringUtils.isNotEmpty(openId))
			postData.put("openId", openId);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "register");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result.get("data"));
				String uid = jsonObject.getString("uid");
				// 获取邀请人的uid
				String inviterUid = (String) jsonObject.get("inviterUid");
				UserInfo userinfo = getUserInfo(uid);
				setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, userinfo);
				// 注册创建账户
				gerneralMethod.AccountBuild(uid);
				// 注册送红包
				gerneralMethod.sendReward(uid);
				if(inviterUid != null) {
					// 获取邀请人的邀请人数
					int invitedNumber = jsonObject.getInt("invitedNumber");
					// 获取要送给邀请人的积分
					int point = PointUtil.getPoint(invitedNumber);
					// 送积分给邀请人
					HessianProxyFactory factory = new HessianProxyFactory();  
			        RewardFacade facade = (RewardFacade) factory.create(RewardFacade.class, MsgUtil.getRewardFacadeUrl());
			        RewardInsertRequest insertRequest = new RewardInsertRequest();
			        insertRequest.setMemberID(inviterUid);
			        insertRequest.setRewardNum(String.valueOf(point));
			        insertRequest.setRemark("因邀请用户(" + phone + ")注册妈妈送房赠送积分");
			        insertRequest.setRewardType(RewardTypeEnum.INVITE);
			        RewardInsertResponse insertResponse = facade.insertReward(insertRequest);
			        logger.info("邀请送积分，支付系统返回"+insertResponse);
			        if(!insertResponse.isSuccess()) {
			        	// TODO 处理送积分失败的情况
			        	insertResponse.getMsg();
			        }
				}
				JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
			} else {
				JsonGeneratorUtils.createRetMaptJSONObject(response, code, "注册失败");
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
	}

	// 校验验证码
	@RequestMapping(value = "/checkVerifyCode.htm", method = { RequestMethod.POST })
	public void checkVerifyCode(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String verifyCode = request.getParameter("verifyCode");
		String type = request.getParameter("type");
		postData.put("phone", phone);
		postData.put("type", Integer.valueOf(type));
		postData.put("verifyCode", verifyCode);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),
					"checkVerifyCode");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if (!"0".equals(code)) {
				msg = "验证码输入错误或者已失效";
			}
			JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

	}

	// 校验验证码
	@RequestMapping(value = "/checkUser.htm", method = { RequestMethod.POST })
	public void checkUser(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		postData.put("phone", phone);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),
					"isPhoneRegistered");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					code = jsonObject.get("registered").toString();
				}
			}
			JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

	}

	// 获取用户信息
	public UserInfo getUserInfo(String uid) {
		UserInfo userInfo = new UserInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", uid);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),
					"getUserInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {

				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					String ruid = jsonObject.get("uid").toString();
					String icon = jsonObject.get("icon").toString();
					String nickName = jsonObject.get("nickName").toString();
					String name = jsonObject.get("name").toString();
					String idCard = jsonObject.get("idCard").toString();
					String phone = jsonObject.get("phone").toString();
					String email = jsonObject.get("email").toString();
					userInfo.setEmail(email);
					userInfo.setIcon(icon);
					userInfo.setIdCard(idCard);
					userInfo.setName(name);
					userInfo.setNickName(nickName);
					userInfo.setPhone(phone);
					userInfo.setUid(ruid);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return userInfo;
	}

	// 发送验证码
	@RequestMapping(value = "/sendVerifyCode.htm", method = { RequestMethod.POST })
	public void sendVerifyCode(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		String type = request.getParameter("type");
		String phone = request.getParameter("phone");
		postData.put("phone", phone);
		postData.put("type", Integer.valueOf(type));
		try {
			if (!"1".equals(type)) {
				UserInfo userinfo = gerneralMethod.getUserInfo(null, phone,null);
				postData.put("uid", userinfo.getUid());
			}
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),
					"sendVerifyCode");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
	}

	// 登录
	@RequestMapping(value = "/resetpassword.htm", method = { RequestMethod.GET })
	public ModelAndView resetpassword(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/resetpassword");
	}

	@RequestMapping(value = "/resetpasswordmain.htm", method = { RequestMethod.GET })
	public ModelAndView resetpasswordmain(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/resetpasswordmain");
	}

	@RequestMapping(value = "/my/changepwd.htm", method = { RequestMethod.GET })
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/changepwd");
	}

	// 忘记密码，直接修改密码
	@RequestMapping(value = "/updatePassword.htm", method = { RequestMethod.POST })
	public void updatePassword(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String verifyCode = request.getParameter("verifyCode");
		String type = request.getParameter("type");
		String newpassword = request.getParameter("newpassword");
		/*
		 * String name = request.getParameter("name"); String idCard =
		 * request.getParameter("idCard");
		 */
		try {
			/*
			 * //会员用户，需要验证姓名，身份证，手机号是否匹配 if(null != name && null != idCard){
			 * postData.put("phone", phone); postData.put("idCard", idCard);
			 * postData.put("name", name); JSONObject jsonRes =
			 * HttpClientPostMethod.httpReqUrl(postData,
			 * appProperties.getRequestRoot(),"checkVip"); if(0 !=
			 * jsonRes.getIntValue("code")){
			 * JsonGeneratorUtils.createRetMaptJSONObject(response,
			 * "2","手机号与姓名、身份证不否"); return; } }
			 */
			postData.put("phone", phone);
			postData.put("password", newpassword);
			if (null != password && !checkPassword(phone, password)) {
				JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "原登陆密码不正确");
			} else if (!checkVerifyCode(phone, Integer.parseInt(type), verifyCode)) {
				JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "验证码不正确");
			} else {
				JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),
						"updatePassword");
				String msg = result.get("msg").toString();
				String code = result.getString("code");
				map.put("msg", msg);
				map.put("code", code);
				restP.setData(map);
				JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
	}

	// 登陆状态下，更改密码
	@RequestMapping(value = "/changePassword.htm", method = { RequestMethod.POST })
	public void changePassword(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER,
				UserInfo.class);
		String msg = null;
		String code = null;
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		try {
			if (loginUser == null) {
				logger.info("用户未登陆");
				JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "用户未登陆");
				return;
			}
			Map<String, Object> postData = new HashMap<String, Object>();
			String newpassword = request.getParameter("newpassword");
			String repassword = request.getParameter("repassword");
			if (!repassword.equals(newpassword)) {
				logger.info("两次密码输入不一致");
				JsonGeneratorUtils.createRetMaptJSONObject(response, "1", "两次密码输入不一致");
				return;
			}
			String phone = loginUser.getPhone();
			postData.put("phone", phone);
			postData.put("password", newpassword);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),
					"updatePassword");
			msg = result.get("msg").toString();
			code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			restP.setData(map);
			JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (Exception e) {
			logger.error("修改密码失败", e);
		}
	}

	// 校验验证码
	public boolean checkVerifyCode(String phone, int type, String verifyCode) {
		boolean resultflag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("phone", phone);
		postData.put("type", type);
		postData.put("verifyCode", verifyCode);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),
					"checkVerifyCode");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if (!"0".equals(code)) {
				resultflag = false;
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return resultflag;
	}

	// 校验原登陆密码
	public boolean checkPassword(String phone, String password) {
		boolean resultflag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("phone", phone);
		postData.put("password", password);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "login");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if (!"0".equals(code)) {
				resultflag = false;
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (Exception e) {
			logger.error("错误日志", e);
		}

		return resultflag;
	}

	@RequestMapping(value = "/register-succ.htm", method = { RequestMethod.GET })
	public ModelAndView registerSucc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		generateSignature(request, model,"register-succ.htm");
		model.addAttribute("shareUrl", getInvitationUrl((String) model.get("friendCode")));
		return new ModelAndView(Contants.URL_PREFIX + "/register-succ");
	}

	@RequestMapping(value = "/register-info.htm", method = { RequestMethod.GET })
	public ModelAndView registerInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<DeveloperInfo> delList = gerneralMethod.getDevelops();
		model.addAttribute("delList", delList);
		return new ModelAndView(Contants.URL_PREFIX + "/register-info");
	}

	@RequestMapping(value = "/share-result.htm", method = { RequestMethod.GET })
	public ModelAndView toShareResult(HttpServletRequest req, HttpServletResponse resp, ModelMap model)
			throws Exception {
		model.addAttribute("friendCode", req.getParameter("friendCode"));
		return new ModelAndView(Contants.URL_PREFIX + "/share-result");
	}
	
	@RequestMapping(value = "/toSharePage.htm", method = { RequestMethod.GET })
	public ModelAndView toSharePage(HttpServletRequest req, HttpServletResponse resp, ModelMap model)
			throws Exception {
		String friendCode = req.getParameter("friendCode");
		generateSignature(req, model,"toSharePage.htm?friendCode="+friendCode);
		String openId = (String)req.getSession().getAttribute("openId");
		//创建用户与微信号关系
		if(StringUtil.isNotEmpty(openId) && StringUtil.isNotEmpty(friendCode)){
			OpenIdInfo oi = new OpenIdInfo();
			oi.setPhone(friendCode);
			oi.setOpenId(openId);
			gerneralMethod.insertOpenIdInfo(oi);
		}
		model.addAttribute("shareUrl", getInvitationUrl(req.getParameter("friendCode")));
		return new ModelAndView(Contants.URL_PREFIX + "/share");
	}

	@RequestMapping(value = "/toScanQRCode.htm", method = { RequestMethod.GET })
	public ModelAndView toScanQRCode(HttpServletRequest req, HttpServletResponse resp, ModelMap model)
			throws Exception {
		// 获取code
		String code = req.getParameter("code");
		String friendCode = req.getParameter("friendCode");
		logger.info("准备跳转到扫描二维码的页面(toScanQRCode): code={}, friendCode={}", code, friendCode);
		//将邀请码放在session中
		req.getSession().setAttribute("friendCode", friendCode);
		//获取微信用户基本信息openId,网页授权accessToken
		JSONObject json = WeChatUtil.getWYaccessToken(code);
		WeChatUser user = WeChatUtil.getWeChatUserInfoByCode(json);
		String openId = user.getOpenId();
		logger.info("用户openId："+openId);
		req.getSession().setAttribute("openId", openId);
		
		// 判断用户已经关注公众号
		if(WeChatUtil.isSubscribe(openId)) {
			//String shareUrl = getInvitationUrl(req.getParameter("friendCode"));
			//model.addAttribute("shareUrl", shareUrl);
			//generateSignature(req, model,URLDecoder.decode(shareUrl,"UTF-8"),"0");
			OpenIdInfo oi = new  OpenIdInfo();
			oi.setOpenId(openId);
			oi = gerneralMethod.selectOpenIdInfo(oi);
			//用户关注公众号，但手机号与微信号没有联系
			if(null == oi.getPhone()){
				model.put("redirect_url","/toLogin.htm");
				return new ModelAndView(Contants.URL_PREFIX + "/login");
			}else{
				model.addAttribute("friendCode", friendCode);
				return new ModelAndView(Contants.URL_PREFIX + "/invitation");
			}
		
		}else{
			JSONObject url = WeChatUtil.getTicketInternal(friendCode);
			model.addAttribute("qrCodeUrl", this.uploadImg(friendCode,url.getString("url")));
			return new ModelAndView(Contants.URL_PREFIX + "/qrCode");
		}
		
	}

	@RequestMapping(value = "/share.htm")
	public ModelAndView goSharePage(HttpServletRequest req, HttpServletResponse resp, ModelMap model) throws Exception {
		String friendCode = req.getParameter("friendCode");
		generateSignature(req, model,"share.htm?friendCode="+friendCode);
		String openId = (String)req.getSession().getAttribute("openId");
		//创建用户与微信号关系
		if(StringUtil.isNotEmpty(openId)&& StringUtil.isNotEmpty(friendCode)){
			OpenIdInfo oi = new OpenIdInfo();
			oi.setPhone(friendCode);
			oi.setOpenId(openId);
			gerneralMethod.insertOpenIdInfo(oi);
		}
		model.addAttribute("shareUrl", getInvitationUrl(friendCode));
		return new ModelAndView(Contants.URL_PREFIX + "/register-succ");
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	private void generateSignature(HttpServletRequest req, ModelMap model,String url) throws Exception {
		try {
				String nonceStr = UUID.randomUUID().toString();
				String jsapi_ticket = (String) ApplicationCache.getInstance().get(WeChatUtil.JSAPI_TICKET);
				String access_token = (String) ApplicationCache.getInstance().get(WeChatUtil.KEY_ACCESS_TOKEN);
				if (StringUtil.isBlank(access_token)) {
					access_token = WeChatUtil.getAccessTokenInternal();
				}
				logger.info("微信access_token："+access_token);
				
				if (StringUtil.isBlank(jsapi_ticket)) {
					jsapi_ticket = WeChatUtil.getJsapiTicket(access_token);
				}
				long timestamp = System.currentTimeMillis() / 1000;
				logger.info("微信jsapi_ticket："+jsapi_ticket);
				String timestampStr = Long.toString(timestamp);
				// 用sha1生成分享的签名
				String signature = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestampStr
						+ "&url="+appProperties.getHostAddress() + url;
				logger.info("用于生成分享签名的signature = " + signature);
				MessageDigest crypt = MessageDigest.getInstance("SHA-1");
				crypt.reset();
				crypt.update(signature.getBytes("UTF-8"));
				signature = byteToHex(crypt.digest());
				model.put("noncestr", nonceStr);
				model.put("signature", signature);
				model.put("timestamp", timestampStr);
				model.put("appId", MsgUtil.getWechatAppId());
		} catch (NoSuchAlgorithmException e1) {
			logger.error("获取微信凭证异常",e1);
		} catch (UnsupportedEncodingException e2) {
			logger.error("获取微信凭证异常",e2);
		}catch (Exception e3){
			logger.error("获取微信凭证异常",e3);
		}
	}

	// 验证输入验证码
	@RequestMapping(value = "/checkRandomCode.htm", method = { RequestMethod.POST })
	public void checkRandomCode(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		/*String verifycode = request.getParameter("verifycode");
		String code = "1";
		String msg = "验证码错误";
		try {
			String kaptchaExpected = (String) request.getSession()
					.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if (StringUtils.isNotEmpty(verifycode) && StringUtils.isNotEmpty(kaptchaExpected)
					&& verifycode.equals(kaptchaExpected)) {
				code = "0";
			}
			JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}*/
		String code = "0";
		String msg = null;
		String phone = request.getParameter("phone");
		try {
		Map<String,Integer> res = gerneralMethod.checkPhoneSms(phone);
		int isOutLimitCount = res.get("isOutLimitCount");
		int isOutDifTime = res.get("isOutDifTime");		
		if(isOutLimitCount ==1){
			code = "1";
			msg = "该手机号今天注册短信限额已满";
		}
		if(isOutDifTime ==1){
			code = "2";
			msg = "请不要频繁点击发送短信";
		}
		JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (Exception e) {
			logger.error("验证手机号短信信息异常",e);
		}
		
	}
	
	private String getInvitationUrl(String friendCode) throws UnsupportedEncodingException {
		String redirectUri = URLEncoder.encode(appProperties.getHostAddress() + "toScanQRCode.htm?friendCode=" + friendCode, "UTF-8");
		return MsgUtil.getWechatAuthorizeUrl(redirectUri);
	}
	
	//获取妈妈送房二维码地址
	private String uploadImg(String friendCode,String url){
		
		String imageUrl = ApplicationCache.getInstance().getStr(WeChatUtil.MMSFANG_IMG_URL);
		if(null == imageUrl){
			// 生成二维码
			// 获取图片服务器的地址
			byte[] fileData = QrcodeUtil.createQRCode(url);
			logger.info("生成二维码大小"+fileData.length);
			String fsUrl = MsgUtil.getUploadHouseUrl();
			// 将生成的二维码上传到图片服务器
			HessianProxyFactory factory = new HessianProxyFactory();  
		       try {
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, fsUrl);
				    FileUploadRequest uploadRequest = new FileUploadRequest();
				    uploadRequest.setSource(MsgUtil.getUploadSource());
				    uploadRequest.setFileType("png");//jpg 、png
				    uploadRequest.setGroupName(MsgUtil.getUploadFileGroup());
				    uploadRequest.setFile(fileData);
				    
				    FileUploadResponse fileResponse = facade.uploadFile(uploadRequest);   
				    imageUrl = fileResponse.getFileUrl();
				    logger.info("图片上传成功！图片路径为：" + imageUrl);
				    ApplicationCache.getInstance().put(WeChatUtil.MMSFANG_IMG_URL, imageUrl,WeChatUtil.VALID_TIME_IN_SECOND);
			  }catch(Exception ex){
				  logger.error("上传图片时报错：{}", ex);
				  ex.printStackTrace();
			  }
		}
	       return imageUrl;
	}
}
