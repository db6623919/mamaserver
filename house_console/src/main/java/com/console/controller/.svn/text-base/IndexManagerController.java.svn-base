package com.console.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.console.dto.BuildingsDto;
import com.console.dto.CityInfo;
import com.console.util.Constant;
import com.console.vo.HouseInfo;

@Controller
@RequestMapping("/indexManager")
public class IndexManagerController extends BaseController {
	
	public static Logger logger = Logger.getLogger(IndexManagerController.class);
	
	@RequestMapping("/toManager")
	public ModelAndView toCity(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		List<CityInfo> CitysList=getCities();
		
		List<BuildingsDto> dellist = getBuildings(null);
		//任我行
		List<Integer> temp1 = new ArrayList<Integer>();
		//老友巢
		List<Integer> temp2 = new ArrayList<Integer>();
		for(BuildingsDto bd : dellist){
			if(Constant.BUILDING_TYPE_00.equals(bd.getBuildingType()))
				temp1.add(bd.getBldId());
			else
				temp2.add(bd.getBldId());
		}
		Map<String,Object> postMap = new HashMap<String,Object>();
		postMap.put("reversed", 1);
		List<HouseInfo> houseList = getHouses(postMap);
		List<HouseInfo> renwoxing = new ArrayList<HouseInfo>();
		List<HouseInfo> laoyouchao = new ArrayList<HouseInfo>();
		List<Integer> num = new ArrayList<Integer>();
		for(int j=0; j<houseList.size(); j++){
			HouseInfo hi = houseList.get(j);
			int bldId = hi.getBldId();
			for (int i = 0; i < temp1.size(); i++) {
				if(bldId == temp1.get(i))
					renwoxing.add(hi);
				
			}
			for (int i = 0; i < temp2.size(); i++) {
				if(bldId == temp2.get(i))
					laoyouchao.add(hi);
			}
			num.add(j);
		}
		mv.addObject("houseList", houseList);
		mv.addObject("renwoxing", renwoxing);
		mv.addObject("laoyouchao", laoyouchao);
		mv.addObject("CitysList", CitysList);
		mv.addObject("num", num);
		String id = request.getParameter("id");
		id = "".equals(id) || null == id ?"/indexmanager/indexList_new":("1".equals(id)?"/indexmanager/cityManager_new":("2".equals(id)?"/indexmanager/renwoxingManager":"/indexmanager/laoyouchaoManager"));
		mv.setViewName(id);
		return mv;
	}
	@RequestMapping("/changeManager")
	public ModelAndView changeManager(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		int count = Integer.valueOf(request.getParameter("count"));
		String[] param = request.getParameterValues("partsort");
		Map<String,Object> postMap = new HashMap<String,Object>();
		for (int i = 0; i < param.length; i++) {
			if("city".equals(type)){
				postMap.put("cityId", Integer.valueOf(param[i]));
				postMap.put("sort", count);
				count--;
				updateCity(postMap);
			}else{
				postMap.put("houseId", Integer.valueOf(param[i]));
				postMap.put("sort", count);
				count--;
				updateHouse(postMap);
			}
		}
		return this.toCity(session, request);
	}
}
