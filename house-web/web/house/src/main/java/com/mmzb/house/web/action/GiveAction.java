package com.mmzb.house.web.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.SessionUserInfo;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.ExecResult;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;
import com.netfinworks.vfsso.client.exception.CasServiceException;

/**
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年4月5日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
@Controller
public class GiveAction extends BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(GiveAction.class);

	@Autowired
	public GerneralMethod gerneralMethod;
	@Resource(name = "appProperties")
	private AppProperties appProperties;

	@RequestMapping("/my/give/center.htm")
	public String center(HttpSession session, Model model) {
		return Contants.URL_PREFIX + "/give/give_center";
	}

	@RequestMapping("/my/give/getData/{type}.htm")
	@ResponseBody
	public ExecResult getData(HttpServletRequest request,HttpSession session, @PathVariable int type) {
		try {
			SessionUserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("memberId", loginUser.getUid());
			postData.put("type", type);
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGiveList");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				ExecResult execResult = new ExecResult();
				execResult.setData(httpResult.getJSONObject("data").toJSONString());
				return execResult;
			}
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();

	}

	@RequestMapping("/my/give/add.htm")
	public String addExchange(HttpServletRequest request,HttpSession session, Model model) throws CasServiceException {
		try {
			SessionUserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mmWalletId", loginUser.getMmWalletId());
			postData.put("statusIds", Arrays.asList(0));
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGroups");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				model.addAttribute("hotelCoupons", httpResult.getJSONObject("data").toJSONString());
			}
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		//添加登录信息
		String travelAddress = appProperties.getTravelAddress();
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			User userInfo = VfSsoUser.get();
			if (userInfo != null) {
				travelAddress += ("?token=" + token);
				model.addAttribute("isLogin", "1");

			}
		} 	
		model.addAttribute("travelAddress", travelAddress);
		return Contants.URL_PREFIX + "/give/give_add";
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

	@RequestMapping(value = "/my/give/add.htm", method = RequestMethod.POST)
	@ResponseBody
	public ExecResult addExchange(HttpServletRequest request,HttpSession session, String msg, int copiesCount, String hotelCouponIds) {
		try {
			SessionUserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mmWalletId", loginUser.getMmWalletId());
			postData.put("memberId", loginUser.getUid());
			postData.put("msg", msg);
			postData.put("copiesCount", copiesCount);
			postData.put("hotelCouponIds", Arrays.asList(hotelCouponIds.split(",")));
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "addHotelCouponGive");
			if (httpResult.getIntValue("code") != 0) {
				return ExecResult.getFailure();
			} else {
				postData = new HashMap<String, Object>();
				postData.put("id", httpResult.getJSONObject("data").getLongValue("id"));
				httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGiveDetail");
				int code = httpResult.getIntValue("code");
				if (code == 0) {
					String giveCode = httpResult.getJSONObject("data").getJSONObject("hotelCouponGive").getString("giveCode");
					String hostAddress = appProperties.getHostAddress();
					return new ExecResult(code, null, appProperties.getHostAddress() + "/receive/" + giveCode + ".htm");
				}
				return ExecResult.getFailure();
			}
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();
	}

	@RequestMapping("/give/item-{id}.htm")
	public String giveDetail(@PathVariable long id, HttpServletRequest request,Model model) throws CasServiceException {
		try {
			SessionUserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("id", id);
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGiveDetail");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				model.addAttribute("data", httpResult);
				model.addAttribute("name", loginUser.getName());
			}
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		//添加登录信息
		String travelAddress = appProperties.getTravelAddress();
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			User userInfo = VfSsoUser.get();
			if (userInfo != null) {
				travelAddress += ("?token=" + token);
				model.addAttribute("isLogin", "1");

			}
		} 	
		model.addAttribute("travelAddress", travelAddress);
		return Contants.URL_PREFIX + "/give/give_detail";
	}

	@RequestMapping("/receive/{giveCode}.htm")
	public String receive(@PathVariable String giveCode, Model model) {
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("giveCode", giveCode);
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGiveDetail");
			model.addAttribute("data", httpResult);
			System.out.println(httpResult);
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return Contants.URL_PREFIX + "/give/receive";
	}

	@RequestMapping("/receive/{giveCode}/success.htm")
	public String receiveSuccess(@PathVariable String giveCode, Model model) {
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("giveCode", giveCode);
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGiveDetail");
			model.addAttribute("data", httpResult);
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return Contants.URL_PREFIX + "/give/success";
	}

	@RequestMapping(value = "/receive/{giveCode}.htm", method = RequestMethod.POST)
	@ResponseBody
	public ExecResult receive(@PathVariable String giveCode, String phoneNumber, String verifyCode) {
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("giveCode", giveCode);
			postData.put("phoneNumber", phoneNumber);
			postData.put("verifyCode", verifyCode);
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "addHotelCouponGiveReceive");
			int code = httpResult.getIntValue("code");
			if (code != 0) {
				switch (code) {
				case 2:
					return new ExecResult(code, "参数错误", null);
				case 4:
					return new ExecResult(code, "验证码不正确", null);
				case 58:
					return new ExecResult(code, "旅居券已经领完", null);
				default:
					return ExecResult.getFailure();
				}
			}
			return ExecResult.getSucc();
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();
	}

}
