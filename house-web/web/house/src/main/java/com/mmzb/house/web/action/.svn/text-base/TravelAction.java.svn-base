package com.mmzb.house.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.core.common.util.Contants;

/**
 * 
* @ClassName: TravelAction 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lijiaqi 
* @date 2015年12月9日 下午3:40:10
 */
 
@Controller
public class TravelAction extends BaseAction {

	@RequestMapping(value = "/travelIndex.htm")
	public ModelAndView toTravelIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/travel/platform-index");
	}
	
	@RequestMapping(value="/travelList.htm")
	public ModelAndView toTravelList(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return new ModelAndView(Contants.URL_PREFIX +"/travel/list");
	}
	
	@RequestMapping(value="/travelDetail.htm")
	public ModelAndView toTravelDetail(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return new ModelAndView(Contants.URL_PREFIX +"/travel/detail");
	}
}
