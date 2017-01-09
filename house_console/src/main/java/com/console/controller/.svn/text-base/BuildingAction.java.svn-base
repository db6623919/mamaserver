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

import com.console.dto.BuildingsDto;
import com.console.dto.CityInfo;
import com.console.dto.DevelopersDto;
import com.console.dto.ProvincesDto;
import com.console.dto.TradeArea;
import com.console.framework.dao.Pagination;
import com.console.util.Constant;
import com.console.util.HttpClientPostMethod;
import com.console.util.MsgPropertiesUtils;

@Controller
@RequestMapping("/builld")
public class BuildingAction extends BaseController {
	
	@RequestMapping("/toBuilding")
	public ModelAndView toBuilding(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pager = new Pagination();
	    int current_page;// ��ǰҳ
	    if (null==request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		pager.setCurrent_page(current_page);
		pager.setPage_size(MsgPropertiesUtils.getPageSize());
		List<BuildingsDto> buildingsList=getBuildings(pager,Constant.BUILDING_TYPE_00);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		
		mv.addObject("buildingsList", buildingsList);
		List<CityInfo> cityList=getCities();
		List<ProvincesDto> proList=getProvinces();
		List<DevelopersDto> devList=getDevelopers();
		mv.addObject("cityList", cityList);
		mv.addObject("proList", proList);
		mv.addObject("devList", devList);
		mv.setViewName("/builld/builldList_new");
		return mv;
	}
	@RequestMapping("/getBuildingsDetail")
	public ModelAndView getBuildingsDetail(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		Integer buildId=Integer.valueOf(request.getParameter("buildId"));
		Integer[] buildIdInt=new Integer[1];
		buildIdInt[0]=buildId;
		
		List<BuildingsDto> buildingsList=getBuildingsDetail(buildId);
		BuildingsDto buildingsDto = buildingsList.get(0);
		buildingsDto.setName(HtmlUtils.htmlEscape(buildingsDto.getName()));
		buildingsDto.setBldImg(HtmlUtils.htmlEscape(buildingsDto.getBldImg()));
		buildingsDto.setVedio(HtmlUtils.htmlEscape(buildingsDto.getVedio()));
		buildingsDto.setMark(HtmlUtils.htmlEscape(buildingsDto.getMark()));
		buildingsDto.setDescription(HtmlUtils.htmlEscape(buildingsDto.getDescription()));
		buildingsDto.setHouseExplain(HtmlUtils.htmlEscape(buildingsDto.getHouseExplain()));
		
		mv.addObject("buildings", buildingsDto);
		List<CityInfo> cityList=getCities();
		List<ProvincesDto> proList=getProvinces();
		List<DevelopersDto> devList=getDevelopers();
		mv.addObject("cityList", cityList);
		mv.addObject("proList", proList);
		mv.addObject("devList", devList);
		String tradeStr = buildingsDto.getTrade_area();
//		String trade[] = tradeStr.split(",");
// 		List<TradeArea> tradeList = getTradeArea(buildingsDto.getCityId());//���е�
//		for (TradeArea tradeArea : tradeList) {
//			for (String string:trade) {
//				if (tradeArea.getId().toString().equals(string)) {
//					tradeArea.setCheck(1);
//			  }
//			}
//		}
//		mv.addObject("tradeList", tradeList);
		mv.addObject("tradeStr",tradeStr);
		mv.setViewName("/builld/updateBuilding_new");
		return mv;
	}
	
	@RequestMapping("/deleteBuilding")
	public ModelAndView deleteBuilding(HttpSession session,HttpServletRequest request) throws  Exception {
		String id=request.getParameter("id");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("bldId", Integer.valueOf(id));
		HttpClientPostMethod.httpReqUrl(postData, "removeBuilding");;
		return toBuilding(session, request);
	}
	
	@RequestMapping("/to_addBuilding")
	public ModelAndView toaddBuilding(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		List<CityInfo> cityList=getCities();
		List<ProvincesDto> proList=getProvinces();
		List<DevelopersDto> devList=getDevelopers();
		CityInfo cityInfo = cityList.get(0);
		List<TradeArea> tradeList = getTradeArea(cityInfo.getCityId());
		mv.addObject("cityList", cityList);
		mv.addObject("proList", proList);
		mv.addObject("devList", devList);
		mv.addObject("tradeList", tradeList);
		mv.setViewName("/builld/addBuilding_new");
		return mv;
	}
	
	@RequestMapping("/addBuilding")
	public ModelAndView addBuilding(HttpSession session,HttpServletRequest request) throws  Exception {
		Integer proId=Integer.valueOf(request.getParameter("proId"));
		Integer cityId=Integer.valueOf(request.getParameter("cityId"));
		Integer devId=Integer.valueOf(request.getParameter("devId"));
		String mark=request.getParameter("mark");
		String name=request.getParameter("name");
		Integer type=Integer.valueOf(request.getParameter("type"));
		String description=request.getParameter("description");
		String telphone=request.getParameter("telphone");
		String bldImg=request.getParameter("bldImg");
		String video=request.getParameter("video");
		String houseExplain=request.getParameter("houseExplain");
		String peripheralConfig=request.getParameter("peripheralConfig");
		String trade[] = request.getParameterValues("trade");// ��ȡ��Ȧ
		
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("provId", proId);
		postData.put("cityId", cityId);
		postData.put("devId", devId);
		postData.put("mark", mark);
		postData.put("name", name);
		postData.put("type", type);
		String tradeArea = "";
		if (trade != null) {
			int tradeInt[] = new int[trade.length];
			for (int i = 0; i < trade.length; i++) {
				tradeInt[i] = Integer.valueOf(trade[i]);
				if (i!=trade.length-1) {
					tradeArea+= trade[i]+",";
				}else {
					tradeArea+= trade[i];
				}
			}
		}
		postData.put("trade", tradeArea);
		
		Map<String, Object> showDetailData=new HashMap<String, Object>();
		showDetailData.put("telphone", telphone);
		showDetailData.put("description", description);
		showDetailData.put("bldImg", bldImg);
		showDetailData.put("video", video);
		showDetailData.put("houseExplain", houseExplain);
		showDetailData.put("peripheralConfig", peripheralConfig);
		postData.put("showDetail", showDetailData);
		postData.put("buildingType", Constant.BUILDING_TYPE_00);
		HttpClientPostMethod.httpReqUrl(postData, "addBuilding");;
		return toBuilding(session, request);
	}
	
	@RequestMapping("/updateBuilding")
	public ModelAndView updateBuilding(HttpSession session,HttpServletRequest request) throws  Exception {
		Integer bldId=Integer.valueOf(request.getParameter("bldId"));
		Integer proId=Integer.valueOf(request.getParameter("proId"));
		Integer cityId=Integer.valueOf(request.getParameter("cityId"));
		Integer devId=Integer.valueOf(request.getParameter("devId"));
		String mark=request.getParameter("mark");
		String name=request.getParameter("name");
		Integer type=Integer.valueOf(request.getParameter("type"));
		String houseExplain=request.getParameter("houseExplain");
		String peripheralConfig=request.getParameter("peripheralConfig");
		
		String description=request.getParameter("description");
		String telphone=request.getParameter("telphone");
		String bldImg=request.getParameter("bldImg");
		String video=request.getParameter("video");
		String trade[] = request.getParameterValues("trade");// ��ȡ��Ȧ
		
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("bldId", bldId);
		postData.put("provId", proId);
		postData.put("cityId", cityId);
		postData.put("devId", devId);
		postData.put("mark", mark);
		postData.put("name", name);
		postData.put("type", type);
		String tradeArea = "";
		if (trade != null) {
			int tradeInt[] = new int[trade.length];
			for (int i = 0; i < trade.length; i++) {
				tradeInt[i] = Integer.valueOf(trade[i]);
				if (i!=trade.length-1) {
					tradeArea+= trade[i]+",";
				}else {
					tradeArea+= trade[i];
				}
			}
		}
		postData.put("trade", tradeArea);
		
		Map<String, Object> showDetailData=new HashMap<String, Object>();
		showDetailData.put("telphone", telphone);
		showDetailData.put("description", description);
		showDetailData.put("bldImg", bldImg);
		showDetailData.put("video", video);
		showDetailData.put("houseExplain", houseExplain);
		showDetailData.put("peripheralConfig", peripheralConfig);
		postData.put("showDetail", showDetailData);
		postData.put("buildingType", Constant.BUILDING_TYPE_00);
		HttpClientPostMethod.httpReqUrl(postData, "updateBuild");
		return toBuilding(session, request);
	}
	
}
