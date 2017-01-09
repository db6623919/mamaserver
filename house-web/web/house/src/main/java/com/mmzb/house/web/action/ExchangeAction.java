package com.mmzb.house.web.action;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.message.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.banner.facade.entity.BannerEntity;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.SessionUserInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.ExecResult;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.common.util.DateUtil;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;

/**
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年2月25日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
@Controller
public class ExchangeAction extends BaseAction {
	
	private Logger logger = LoggerFactory.getLogger(ExchangeAction.class);

	@Autowired
	public GerneralMethod gerneralMethod;
	@Resource(name = "appProperties")
	private AppProperties appProperties;

	@RequestMapping("/exchange/center.htm")
	public String exchangeCenter() {
		return Contants.URL_PREFIX + "/exchange/exchange_center";
	}

	@RequestMapping("/my/exchange/add.htm")
	public String addExchange(HttpServletRequest request,HttpSession session, Model model) throws Exception {
		SessionUserInfo loginUser = getLoginUserInfo(request);
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mmWalletId", loginUser.getMmWalletId());
			postData.put("statusIds", Arrays.asList(0));
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGroups");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				model.addAttribute("hotelCoupons", httpResult.getJSONObject("data").toJSONString());
			}

			String token = getToken(request);
			if (StringUtil.isNotEmpty(token)) {
				VfSsoUser.setCurrentToken(token);
				User userInfo = VfSsoUser.get();
				if (userInfo != null) {
					model.addAttribute("isLogin", "1");
				}
			} 
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return Contants.URL_PREFIX + "/exchange/exchange_add";
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

	@RequestMapping("/my/exchange/request.htm")
	public String addExchangeRequest(HttpServletRequest request,HttpSession session, Model model) throws Exception{
		SessionUserInfo loginUser = getLoginUserInfo(request);
		try {
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
		return Contants.URL_PREFIX + "/exchange/exchange_add";
	}

	@RequestMapping(value = "/my/exchange/cancel.htm", method = RequestMethod.POST)
	@ResponseBody
	public ExecResult cancelExchangeRequest(HttpServletRequest request,long id, int type, HttpSession session) throws Exception{
		try {
			SessionUserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("memberId", loginUser.getUid());
			postData.put("type", type);// 1：取消主动发起换券申请；2：取消申请已存在的换券请求
			postData.put("id", id);

			JSONObject jsonObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "cancelHotelCouponExchange");
			ExecResult execResult = new ExecResult();
			execResult.setCode(jsonObject.getIntValue("code"));
			if (execResult.getCode() != 0) {
				execResult.setMsg(ExecResult.failure_msg);
			}
			return execResult;

		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();
	}

	@RequestMapping(value = "/my/exchange/accept.htm", method = RequestMethod.POST)
	@ResponseBody
	public ExecResult acceptExchangeRequest(HttpServletRequest request,long id, long requestId, HttpSession session) {
		try {
			SessionUserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("memberId", loginUser.getUid());
			postData.put("exchangeId", id);
			postData.put("requestId", requestId);
			JSONObject jsonObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "acceptHotelCouponExchange");
			ExecResult execResult = new ExecResult();
			execResult.setCode(jsonObject.getIntValue("code"));
			if (execResult.getCode() != 0) {
				execResult.setMsg(ExecResult.failure_msg);
			}
			return execResult;
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();
	}

	@RequestMapping(value = "/my/exchange/add.htm", method = RequestMethod.POST)
	@ResponseBody
	public ExecResult addExchange(HttpServletRequest request,String intention, String hotelCouponIds, HttpSession session) throws Exception {
		SessionUserInfo loginUser = getLoginUserInfo(request);
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mmWalletId", loginUser.getMmWalletId());
			postData.put("memberId", loginUser.getUid());
			postData.put("type", 1);
			postData.put("intention", intention);
			postData.put("hotelCouponIds", Arrays.asList(hotelCouponIds.split(",")));
			JSONObject jsonObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "addHotelCouponExchange");
			ExecResult execResult = new ExecResult();
			execResult.setCode(jsonObject.getIntValue("code"));
			if (execResult.getCode() != 0) {
				execResult.setMsg(ExecResult.failure_msg);
			} else {
				execResult.setData(jsonObject.getJSONObject("data").getLongValue("id"));
			}
			return execResult;
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();
	}

	@RequestMapping(value = "/my/exchange/request/add.htm", method = RequestMethod.POST)
	@ResponseBody
	public ExecResult addExchangeRequest(HttpServletRequest request,String intention, String hotelCouponIds, long exchangeId, HttpSession session) throws Exception {
		SessionUserInfo loginUser = getLoginUserInfo(request);
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mmWalletId", loginUser.getMmWalletId());
			postData.put("memberId", loginUser.getUid());
			postData.put("type", 2);
			postData.put("exchangeId", exchangeId);
			postData.put("intention", intention);
			postData.put("hotelCouponIds", Arrays.asList(hotelCouponIds.split(",")));
			JSONObject jsonObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "addHotelCouponExchange");
			ExecResult execResult = new ExecResult();
			execResult.setCode(jsonObject.getIntValue("code"));
			if (execResult.getCode() != 0) {
				execResult.setMsg(ExecResult.failure_msg);
			} else {
				execResult.setData(jsonObject.getJSONObject("data").getLongValue("id"));
			}
			return execResult;
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();
	}

	@RequestMapping(value = "/my/exchange/choose_coupon.htm", method = { RequestMethod.GET })
	public String choose_coupon(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			UserInfo loginUser = getLoginUserInfo(request);
			postData.put("mmWalletId", loginUser.getMmWalletId());
			postData.put("statusIds", Arrays.asList(0));
			postData.put("expireTimeFrom", DateUtil.format(DateUtil.addHours(new Date(), 24), "yyyy-MM-dd"));
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGroups");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				model.addAttribute("hotelCoupons", httpResult.getJSONObject("data").toJSONString());
			}
		} catch (Exception e) {
			log.error("获取旅居券异常", e);
		}
		return Contants.URL_PREFIX + "/exchange/choose_coupon";
	}

	@RequestMapping("/my/exchange/list.htm")
	public String exchangeList(Model model, HttpSession session, HttpServletRequest request) {
		//判断是否登陆
		setLoginStatus(request, model);
		
		return Contants.URL_PREFIX + "/exchange/exchange_list";
	}
	
	private void setLoginStatus(HttpServletRequest request, Model model)
	{
		int isLogin = 0;
		try 
		{
			UserInfo loginUser = getLoginUserInfo(request);
			isLogin = (loginUser != null) ? 1 : 0;
			
		} 
		catch (Exception e)
		{
			logger.warn("failed to get loginStatus", e);
			isLogin = 0;
		}
		
		model.addAttribute("isLogin", isLogin);
	}

	@RequestMapping("/my/exchange/apply-{id}.htm")
	public String exchangeApply(@PathVariable long id, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) {
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("id", id);
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponExchangeDetail");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				model.addAttribute("exchange", httpResult.getJSONObject("data").toJSONString());
			}
			
			String token = getToken(request);
			if (StringUtil.isNotEmpty(token)) {
				VfSsoUser.setCurrentToken(token);
				User userInfo = VfSsoUser.get();
				if (userInfo != null) {
					model.addAttribute("isLogin", "1");
				}
			} 
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return Contants.URL_PREFIX + "/exchange/exchange_apply";
	}

	@RequestMapping("/exchange/listToJson.htm")
	@ResponseBody
	public ExecResult getExchangeToJson() {
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("type", 0);
			postData.put("statusIds", Arrays.asList(0));
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponExchange");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				return new ExecResult(0, null, httpResult.getJSONObject("data").getJSONArray("exchanges").toJSONString());
			}

		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();
	}

	@RequestMapping("/my/exchange/listToJson.htm")
	@ResponseBody
	public ExecResult getMyExchangeToJson(HttpServletRequest request,@RequestParam(defaultValue = "1") int type, HttpSession session) throws Exception{
		try {
			SessionUserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("type", type);
			postData.put("memberId", loginUser.getUid());
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponExchange");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				return new ExecResult(0, null, httpResult.getJSONObject("data").getJSONArray("exchanges").toJSONString());
			}

		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return ExecResult.getFailure();
	}

	@RequestMapping("/exchange/item-{id}.htm")
	public String exchangeDetail(@PathVariable long id, HttpSession session, Model model,HttpServletRequest request) {
		try {
			SessionUserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("id", id);
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponExchangeDetail");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				model.addAttribute("exchange", httpResult.getJSONObject("data").toJSONString());
				model.addAttribute("uid", loginUser.getUid());
			}
		} catch (Exception e) {
			log.error("服务器繁忙", e);
		}
		return Contants.URL_PREFIX + "/exchange/exchange_detail";
	}

}
