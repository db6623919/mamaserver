package com.console.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.console.dto.HouseTag;
import com.console.dto.TradeArea;
import com.console.framework.dao.Pagination;
import com.console.util.HttpClientPostMethod;
import com.console.util.JsonGeneratorUtils;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.meidusa.fastjson.JSONObject;


@Controller
@RequestMapping("/houseTag")
public class HouseTagAction extends BaseController {
	
	public static Logger logger = Logger.getLogger(HouseTagAction.class);
	
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
		List<HouseTag> list = getHouseTags(pager);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		mv.addObject("list", list);
		mv.setViewName("/operation/houseTagList");
		return mv;
	}

	/**
	 * TO新增房源标签页面
	 */
	@RequestMapping("/to_addHouseTag")
	public ModelAndView toaddHouseTag(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/operation/addHouseTag");
		return mv;
	}

	/**addTradeArea
	 * 新增标签
	 */
	@RequestMapping("/addHouseTag")
	public ModelAndView addHouseTag(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info( getSessionUser(request).getName()+" addHouseTag");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String isDispayOnSearch = request.getParameter("isDispayOnSearch");
		
		logger.info( getSessionUser(request).getName()+" addHouseTag; request paras:{name="+name+", id="+ id + "}");
		String flag = request.getParameter("flag");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("id", id);
		postData.put("name", name);
		postData.put("isDispayOnSearch", isDispayOnSearch);
		postData.put("flag", flag);
		logger.info( getSessionUser(request).getName()+" addHouseTag; Invoke addHouseTag API");
		JSONObject obj=HttpClientPostMethod.httpReqUrl(postData, "addHouseTag");
		String code = obj.getString("code");
		if ("0".equals(code)) {
			return list(session, request);
		}else {
			ModelAndView mv=new ModelAndView();
			mv.setViewName("/common/error_new");
			mv.addObject("message", "添加标签失败！  错误信息:该id/name已存在");
			return mv;
		}
		
	}

	/***
	 * 查询
	 */
	@RequestMapping("/getHouseTagById")
	public ModelAndView getHouseTagById(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		Integer id=Integer.valueOf(request.getParameter("id"));
		logger.info( getSessionUser(request).getName()+" getHouseTagById; request paras:{id="+id+"}");
		Pagination pager = new Pagination();
		HouseTag houseTag = getHouseTagById(id); 
		mv.addObject("houseTag", houseTag);;
		mv.setViewName("/operation/updateHouseTag");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/deleteTradeArea")
	public ModelAndView deleteTradeArea(HttpSession session,HttpServletRequest request) throws  Exception {
		String id=request.getParameter("id");
		String cityId = request.getParameter("cityId");
		logger.info( getSessionUser(request).getName()+" deleteTradeArea; request paras:{id="+id+"}");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("id", Integer.valueOf(id));
		postData.put("cityId", cityId);
		HttpClientPostMethod.httpReqUrl(postData, "removeTradeArea");;
		return list(session, request);
	}
	
	/**
	 * 按城市查询商圈
	 */
	@RequestMapping("/getTradeAreaByPro")
	public ModelAndView getCitysByProId(HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws Exception{
		String cityId = request.getParameter("cityId");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("cityId", cityId);
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getTradeAreaByPar");
		
		JSONArray array = JSONArray.fromObject(result.get("data")); 
		List<TradeArea> list = new ArrayList<TradeArea>();
       		for(int i = 0; i < array.size(); i++){
       			net.sf.json.JSONObject jsonObject =array.getJSONObject(i);
       			JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
       			for(int j = 0; j < arrayShowDetail.size(); j++){
       				TradeArea tradeArea = new TradeArea();
	       			net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
	       			String name = jsonObject1.get("name").toString();
	       			String id = jsonObject1.get("id").toString();
	       			String sort = jsonObject1.get("sort").toString();
	       			tradeArea.setCityId(Integer.parseInt(cityId));
	       			tradeArea.setId(Integer.parseInt(id));
	       			tradeArea.setName(name);
	       			tradeArea.setSort(Integer.parseInt(sort));
	       			list.add(tradeArea);
       			}
       		}
		RestResponse restP = new RestResponse();
		restP.setData(result);
		Map<String , Object> infoMap = new HashMap<String , Object>();
		infoMap.put("list", list);  
		restP.setData(infoMap);

		JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);
		return null;
	}
	
}