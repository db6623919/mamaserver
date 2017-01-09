package com.mmzb.house.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.ActivityEnrollInfo;
import com.mmzb.house.web.action.dto.ActivityMemberRecordInfo;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.ActivityConfig;
import com.mmzb.house.web.model.ActivityDate;
import com.mmzb.house.web.model.Luckybean;

/**
 * 单身paty报名
 * @author whl
 *
 */
@Controller
public class EnrollAction extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(GiveAction.class);
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;

	@RequestMapping("/enroll/init.htm")
	public String init(HttpSession session, Model model) {
		logger.info("run to start /Enroll/init.htm.");
		return Contants.URL_PREFIX + "/enroll/enroll_show";
	}	
	
	@RequestMapping(value="/enroll/toAdd.htm", method = { RequestMethod.POST })
	public String toAdd(HttpServletRequest request, HttpServletResponse response,ActivityEnrollInfo activityEnroll,Model model) {
		logger.info("satrt to run toAdd()");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", activityEnroll.getName());
		postData.put("age", activityEnroll.getAge());
		postData.put("sex", activityEnroll.getSex());
		postData.put("phone", activityEnroll.getPhone());
		postData.put("weChat", activityEnroll.getWeChat()); 
		postData.put("educated", activityEnroll.getEducated()); 
		//postData.put("activityEnroll", activityEnroll);
		try {
			JSONObject getResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "activityEnrollAdd");
			String code = getResult.getString("code");
			model.addAttribute("code",code);
			if(code.equals("0")) {
				model.addAttribute("activityEnroll",activityEnroll);
			}
		} catch (Exception e) {
			logger.info("run toAdd is failed.",e);
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
		    logger.debug("toAdd() is finished,params is {}.",result.toString());
		 }
		return Contants.URL_PREFIX + "/enroll/enroll_paSuc";
	}
	
	/**
	 * 信息校验
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/enroll/check.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> check(HttpServletRequest request, HttpServletResponse response) {
		logger.info("start to run /enroll/check.htm.");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String phone = request.getParameter("phone");
			String weChat = request.getParameter("weChat");
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("phone", phone);
			postData.put("weChat", weChat);
			JSONObject getResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "checkEnroll");
			net.sf.json.JSONObject json= net.sf.json.JSONObject.fromObject(getResult.get("data"));
			String isCheck = json.getString("isCheck");
			map.put("isCheck", isCheck);
		} catch (Exception e) {
			logger.error("check:数据验证异常!",e);
			e.printStackTrace();
		}
		return map;
	}
	
}
