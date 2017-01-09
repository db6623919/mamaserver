package com.console.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.console.dto.DevelopersDto;
import com.console.framework.dao.Pagination;
import com.console.util.HttpClientPostMethod;
import com.console.util.MsgPropertiesUtils;
import com.meidusa.fastjson.JSONObject;

@Controller
@RequestMapping("/dev")
public class DeveloperAction extends BaseController {
	
	@RequestMapping("/toDevpoler")
	public ModelAndView toDeveloper(HttpSession session, HttpServletRequest request) throws Exception {
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
		List<DevelopersDto> developersList=getDevelopers(pager);
		for (DevelopersDto developersDto:developersList) {
			developersDto.setDevName(HtmlUtils.htmlEscape(developersDto.getDevName()));
		}
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		
		mv.addObject("developersList", developersList);
		mv.setViewName("/dev/developerList_new");
		return mv;
	}

	@RequestMapping("/to_addDeveloper")
	public ModelAndView toaddDeveloper(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dev/addDeveloper_new");
		return mv;
	}

	@RequestMapping("/addDeveloper")
	public ModelAndView addDeveloper(HttpSession session, HttpServletRequest request) throws Exception {
		String devName = request.getParameter("devName");
		Integer type = Integer.valueOf(request.getParameter("type"));
		String description = request.getParameter("description");
		String telphone = request.getParameter("telphone");
		String devImg = request.getParameter("devImg");
		String video = request.getParameter("video") == null ? "" : request.getParameter("video");
		String mark = request.getParameter("mark");
		Map<String, Object> postData = new HashMap<String, Object>();
		Map<String, Object> showDetailData = new HashMap<String, Object>();
		showDetailData.put("telphone", telphone);
		showDetailData.put("description", description);
		showDetailData.put("devImg", devImg);
		showDetailData.put("video", video);
		postData.put("name", devName);
		postData.put("type", type);
		postData.put("mark", mark);
		postData.put("showDetail", showDetailData);
		HttpClientPostMethod.httpReqUrl(postData, "addDeveloper");
		;
		return toDeveloper(session, request);
	}

	@RequestMapping("/deleteDev")
	public ModelAndView deleteDev(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String id = request.getParameter("id");
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("devId", Integer.valueOf(id));
		JSONObject jsonObject = HttpClientPostMethod.httpReqUrl(postData, "removeDeveloper");
		if (jsonObject.getIntValue("code") == 0) {
			List<DevelopersDto> developersList = getDevelopers();
			mv.addObject("developersList", developersList);
			mv.setViewName("/dev/developerList_new");
		} else {
			mv.setViewName("/common/error_new");
			mv.addObject("message", "删除开发商失败！  错误信息:已被楼盘信息关联使用");
		}
		return mv;
	}

	@RequestMapping("/updateDeveloper")
	public ModelAndView updateDeveloper(HttpSession session, HttpServletRequest request) throws Exception {
		Integer devId = Integer.valueOf(request.getParameter("devId"));
		String devName = request.getParameter("devName");
		Integer type = Integer.valueOf(request.getParameter("type"));
		String description = request.getParameter("description");
		String telphone = request.getParameter("telphone");
		String devImg = request.getParameter("devImg");
		String video = request.getParameter("video") == null ? "" : request.getParameter("video");
		String mark = request.getParameter("mark");
		Map<String, Object> postData = new HashMap<String, Object>();
		Map<String, Object> showDetailData = new HashMap<String, Object>();
		showDetailData.put("telphone", telphone);
		showDetailData.put("description", description);
		showDetailData.put("devImg", devImg);
		showDetailData.put("video", video);
		postData.put("name", devName);
		postData.put("type", type);
		postData.put("mark", mark);
		postData.put("devId", devId);
		postData.put("showDetail", showDetailData);
		HttpClientPostMethod.httpReqUrl(postData, "updateDeveloper");
		;
		return toDeveloper(session, request);
	}

	@RequestMapping("/getDeveloperInfo")
	public ModelAndView getDeveloperInfo(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String devId = request.getParameter("devId");
		List<DevelopersDto> developersList = getDeveloperInfo(devId);
		DevelopersDto developersDto = developersList.get(0);
		developersDto.setDevName(HtmlUtils.htmlEscape(developersDto.getDevName()));
		developersDto.setDevImg(HtmlUtils.htmlEscape(developersDto.getDevImg()));
		developersDto.setVideo(HtmlUtils.htmlEscape(developersDto.getVideo()));
		developersDto.setDescription(HtmlUtils.htmlEscape(developersDto.getDescription()));
		developersDto.setMark(HtmlUtils.htmlEscape(developersDto.getMark()));
		
		mv.addObject("developer", developersDto);
		mv.setViewName("/dev/updateDeveloper_new");
		return mv;
	}

}
