package com.mmzb.house.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.meidusa.fastjson.JSONArray;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.HotelCouponGroupVo;
import com.netfinworks.common.lang.StringUtil;
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
@RequestMapping("/my/coupon")
public class HotelCouponAction extends BaseAction {

	@Resource(name = "appProperties")
	private AppProperties appProperties;
	@Autowired
	public GerneralMethod gerneralMethod;
	
	private static Logger logger = LoggerFactory.getLogger(HotelCouponAction.class);

	@RequestMapping("/list.htm")
	public String index(HttpServletRequest request,HttpSession session, Model model) throws Exception{
		UserInfo userInfo = getLoginUserInfo(request);
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("mmWalletId", userInfo.getMmWalletId());
		try {
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGroups");
			String code = httpResult.getString("code");
			if ("0".equals(code)) {
				List<HotelCouponGroupVo> hotelCouponGroups = new ArrayList<HotelCouponGroupVo>();
				JSONArray array = httpResult.getJSONObject("data").getJSONArray("hotelCouponGroups");
				for (int i = 0; i < array.size(); i++) {
					hotelCouponGroups.add(JSONObject.parseObject(array.getString(i), HotelCouponGroupVo.class));
				}
				
				String travelAddress = appProperties.getTravelAddress();
				String token = getToken(request);
				if (StringUtil.isNotEmpty(token)) {
					VfSsoUser.setCurrentToken(token);
					User userInfos = VfSsoUser.get();
					if (userInfos != null) {
						travelAddress += ("?token=" + token);
						model.addAttribute("isLogin", "1");

					}
				} 		
				
				model.addAttribute("hotelCouponGroups", hotelCouponGroups);
				model.addAttribute("travelAddress", travelAddress);
			} else {
				log.error("获取旅居券异常", httpResult.getString("msg"));
			}
		} catch (Exception e) {
			log.error("获取旅居券异常", e);
		}
		return Contants.URL_PREFIX + "/travel/hotel-coupon";
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

	@RequestMapping("/{id}.htm")
	public String detail(@PathVariable Long id, HttpServletRequest request,Model model) {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("id", id);
		try {
			JSONObject httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGroupById");
			String code = httpResult.getString("code");
			
			//获取登录信息
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
			
			if ("0".equals(code)) {
				HotelCouponGroupVo hotelCouponGroup = JSONObject.parseObject(httpResult.getJSONObject("data").getString("hotelCouponGroup"), HotelCouponGroupVo.class);
				model.addAttribute("hotelCouponGroup", hotelCouponGroup);
				model.addAttribute("houseInfo", gerneralMethod.getHouseInfo(hotelCouponGroup.getHouseId()));
				model.addAttribute("travelAddress", travelAddress);
			} else {
				log.error("获取旅居券异常", httpResult.getString("msg"));
			}
		} catch (Exception e) {
			log.error("获取旅居券异常", e);
		}
		return Contants.URL_PREFIX + "/travel/hotel-coupon-detail";
	}

}
