package com.console.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.console.dto.ActivityEnroll;
import com.console.framework.dao.Pagination;
import com.console.util.HttpClientPostMethod;
import com.console.util.MsgPropertiesUtils;
import com.meidusa.fastjson.JSONObject;

/**
 * 单身party活动报名
 * @author dengbiao
 *
 */
@Controller
@RequestMapping("/enroll")
public class ActivityEnrollAction extends BaseController {
	
	public static Logger logger = Logger.getLogger(ActivityEnrollAction.class);
	
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pager = new Pagination();
	    int current_page;// 当前页
	    if (null==request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		pager.setCurrent_page(current_page);
		pager.setPage_size(MsgPropertiesUtils.getPageSize());
		Map<String, Object> postData = new HashMap<String, Object>();
    	String name = request.getParameter("name");    //姓名
    	if (!"".equals(name)&&null!=name) {
			postData.put("name", name);
			mv.addObject("name", name);
		}
    	String phone = request.getParameter("phone");  //电话
    	if (!"".equals(phone)&&null!=phone) {
			postData.put("phone", phone);
			mv.addObject("phone", phone);
		}
    	int educated = -1;  //学历
    	if (request.getParameter("educated") != null && !"".equals(request.getParameter("educated")) ) {
    		educated = Integer.parseInt(request.getParameter("educated"));
    		postData.put("educated", educated);
    		mv.addObject("educated", educated);
        }
    	int status = -1;//确认状态
    	if (request.getParameter("status")!=null && !"".equals(request.getParameter("status")) ) {
    		status = Integer.parseInt(request.getParameter("status"));
    		postData.put("status", status);
    		mv.addObject("status", status);
		}
		List<ActivityEnroll> list =  getActivityEnrolls(postData,pager,mv);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		mv.addObject("list", list);
		mv.setViewName("/enroll/enrollList");
		return mv;
	}

	/**
	 * 修改确认状态
	 */
	@RequestMapping("/updateEnroll")
	public ModelAndView updateEnroll(HttpSession session,HttpServletRequest request) throws  Exception {
		String id = request.getParameter("id");
		String phone = request.getParameter("phone");
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("id", id);
		postData.put("phone", phone);
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "updateActivityEnroll");
		
		ModelAndView mv = new ModelAndView();
		Pagination pager = new Pagination();
	    int current_page;// 当前页
	    if (null==request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		pager.setCurrent_page(current_page);
		pager.setPage_size(MsgPropertiesUtils.getPageSize());
		Map<String, Object> pData = new HashMap<String, Object>();

		List<ActivityEnroll> list =  getActivityEnrolls(pData,pager,mv);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		mv.addObject("list", list);
		mv.setViewName("/enroll/enrollList");
		return mv;
	}
	
}