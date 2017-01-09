package com.mmzb.house.web.action.n99;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mama.server.common.errorCode.ReturnCode;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.OrderParam;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.ResultSet;
import com.netfinworks.ma.service.response.PersonalMemberInfo;

/**
 * 券页面
 * @author whl
 *
 */
@Controller
public class HouseCardPageAction extends BaseAction{
	
	//参数配置
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(HouseCardPageAction.class);
	
	// 券列表
	@RequestMapping(value = "/my/coupon/list.htm", method = { RequestMethod.GET })
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
	
	// 每个订单下券领取状态列表
	@RequestMapping(value = "/my/coupon/status.htm", method = { RequestMethod.GET })
	public ModelAndView status(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
	
	// 券分享页
	@RequestMapping(value = "/my/coupon/share.htm", method = { RequestMethod.GET })
	public ModelAndView share(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
	
	// 券详情
	@RequestMapping(value = "/coupon/detail.htm", method = { RequestMethod.GET })
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
	
	// 券领取
	@RequestMapping(value = "/coupon/receive.htm", method = { RequestMethod.GET })
	public ModelAndView receive(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
	
}
