package com.mmzb.house.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;

/**
 * 搜索路由
 * @author ayou
 */
@Controller
@RequestMapping("/search")
public class SearchAction extends BaseAction{
	
	//参数配置
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(SearchAction.class);
	
	// 搜索首页
	@RequestMapping(value = "/index.htm", method = { RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
	
	// 查询城市、客栈页面
	@RequestMapping(value = "/detail.htm", method = { RequestMethod.GET })
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
}
