package com.console.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;














import net.sf.json.JSONArray;
import net.sf.json.JSONNull;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.console.dto.CityInfo;
import com.console.dto.HouseShop;
import com.console.dto.TopicActivityDto;
import com.console.framework.dao.Pagination;
import com.console.util.HttpClientPostMethod;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.mama.server.common.errorCode.ReturnCode;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.fdfs.facade.FileFacade;
import com.mmzb.fdfs.facade.entity.FileUploadRequest;
import com.mmzb.fdfs.facade.entity.FileUploadResponse;
import com.netfinworks.common.lang.StringUtil;

/**
 * 主题活动
 * @author whl
 *
 */
@Controller
@RequestMapping("/topic")
public class TopicActivityAction extends BaseController{
	
	//日志打印
	public static Logger logger = Logger.getLogger(TopicActivityAction.class);
	

	/**
	 * 活动列表翻页
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toList")
	public ModelAndView list(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("run start to list.");
		
		List<TopicActivityDto> topicShopList = new ArrayList<TopicActivityDto>();
	    int current_page;// 当前页
	    if (null==request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		int totalCount = 0;
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("currentPage", current_page);
			postData.put("pageSize", MsgPropertiesUtils.getPageSize());
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getTopicActivitys");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) {
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
				net.sf.json.JSONObject pageData = dataJson.getJSONObject("page");
				totalCount = pageData.getInt("total");
				JSONArray arrJson = pageData.getJSONArray("result");
				for(int i = 0 ; i < arrJson.size() ; i ++ ) {
					net.sf.json.JSONObject topicObject = arrJson.getJSONObject(i);
					TopicActivityDto topicShop = new TopicActivityDto();
					topicShop.setId(topicObject.getInt("id"));
					topicShop.setActivityName(topicObject.getString("activityName"));
					topicShop.setActivityType(topicObject.getString("activityType"));
					topicShop.setTitle(topicObject.getString("title"));
					topicShop.setIntroduction(topicObject.getString("introduction"));
					topicShop.setShopCount(topicObject.getInt("shopCount"));
					topicShopList.add(topicShop);
				}
			}
		} catch (Exception e) {
			logger.error("getTopicActivitys:主题列表查询失败.",e);
		} 
		Pagination pager = new Pagination();
		pager.paging(current_page, MsgPropertiesUtils.getPageSize(), totalCount);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pager", pager);
		mv.addObject("list", topicShopList);
		mv.setViewName("/topic/list");
		logger.info("run end to list.");
		return mv;
	}
	
	/**
	 * 操作跳转页面
	 * @return
	 */
	@RequestMapping("/toAddOrEdit")
	public ModelAndView toAddOrEdit(HttpServletRequest request,HttpSession session) {
		logger.info("run start to toAddOrEdit.");
		ModelAndView mv = new ModelAndView();;
		String flag = request.getParameter("flag");
		if (flag.equals("add")) {
			mv.setViewName("/topic/add");
		} else if (flag.equals("edit")) {
			//根据ID获取信息
			Integer id = Integer.parseInt(request.getParameter("id"));
			TopicActivityDto topicActivity = this.getTopicActivityById(id,"");
			mv.addObject("topicActivity",topicActivity);
			mv.setViewName("/topic/update");
		} else if (flag.equals("del")) {
			TopicActivityDto  topicActivity = this.getTopicActivity(request);
			mv = doTopicActivity(topicActivity,flag,mv,session,request);
		}
		logger.info("run end to toAddOrEdit.");
		return mv;
	}
	
	/**
	 * 活动操作（编辑、删除、添加）
	 * @param request
	 * @return
	 */
	@RequestMapping("/doAddOrEdit")
	public ModelAndView doAddOrEdit(HttpSession session,HttpServletRequest request) {
		logger.info("run start to doAddOrEdit.");
		String flag = request.getParameter("flag");
		TopicActivityDto  topicActivity = this.getTopicActivity(request);
		ModelAndView mv = new ModelAndView();;
		mv = doTopicActivity(topicActivity,flag,mv,session,request);
		logger.info("run end to doAddOrEdit.");
		return mv;
	}
	
	/**
	 * 获取对象数据
	 * @param request
	 * @return
	 */
	public TopicActivityDto getTopicActivity(HttpServletRequest request) {
		TopicActivityDto topicActivity = new TopicActivityDto();
		if (null != request.getParameter("id") && !("").equals(request.getParameter("id"))) {
			topicActivity.setId(Integer.parseInt(request.getParameter("id")));
		}
		String activityName = request.getParameter("activityName");
		String activityType = request.getParameter("activityType");
		String title = request.getParameter("title");
		String introduction = request.getParameter("introduction");
		topicActivity.setActivityName(activityName);
		topicActivity.setActivityType(activityType);
		topicActivity.setTitle(title);
		topicActivity.setIntroduction(introduction);
		StringBuffer imgUrl = new StringBuffer();
		updateImage(request,imgUrl);
		if (!imgUrl.toString().equals("")) {
			topicActivity.setImgUrl(imgUrl.toString());
		}
		return topicActivity;
	}
	
	//add、edit
	public ModelAndView doTopicActivity(TopicActivityDto topicActivity,String flag,ModelAndView mv,HttpSession session,HttpServletRequest request) {
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("topicActivity",topicActivity);
			postData.put("flag",flag);
			
			//判断活动名称是否存在
			TopicActivityDto topic = this.getTopicActivityById(null,topicActivity.getActivityName());
			if (flag.equals("add")) { //添加数据判断
				if (topic != null) {
					mv.setViewName("/common/error_new");
					mv.addObject("message", "活动名称已存在!");
					return mv;
				}
			} else if (flag.equals("edit")) {  //修改数据判断
				if (null!=topic) {
					if (topic.getActivityName().equals(topicActivity.getActivityName()) && topic.getId() != topicActivity.getId()) {
						mv.setViewName("/common/error_new");
						mv.addObject("message", "活动名称已存在!");
						return mv;
					}
				}

			}
			
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "doAddOrEditTopicActivity");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) {
				mv = list(session, request);
			} else {
				mv.setViewName("/common/error_new");
				String msg = "";
				if (flag.equals("add")) {
					msg = "活动添加！  错误信息:活动添加失败!";
				} else if (flag.equals("edit")) {
					msg = "活动编辑！  错误信息:活动编辑失败!";
				} else if (flag.equals("del")) {
					msg = "活动删除！  错误信息:活动删除失败!";
				}	
				mv.addObject("message", msg);
			}
		} catch (Exception e) {
			logger.error("doAddOrEdit：活动添加失败.",e);
		}
		return mv;
	}
	
	/**
	 * 根据ID获取活动信息
	 * @param id
	 * @return
	 */
	public TopicActivityDto  getTopicActivityById(Integer id,String name) {
		TopicActivityDto topicActivity = null;
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			if (id != null) {
				postData.put("id",id);
			}
			if (!StringUtil.isEmpty(name)) {
				postData.put("activityName",name);
			}
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getTopicActivitysBy");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK && result.get("data") != null) {
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
				net.sf.json.JSONObject topicData = dataJson.getJSONObject("topicActivity");
				if (!topicData.isNullObject()) {		
					topicActivity = new TopicActivityDto();
					String activityName = topicData.getString("activityName");
					String activityType = topicData.getString("activityType");
					String imgUrl = topicData.getString("imgUrl");
					String title = topicData.getString("title");
					String introduction = topicData.getString("introduction");
					topicActivity.setId(topicData.getInt("id"));
					topicActivity.setActivityName(activityName);
					topicActivity.setActivityType(activityType);
					topicActivity.setImgUrl(imgUrl);
					topicActivity.setTitle(title);
					topicActivity.setIntroduction(introduction);
				}
			} 
		} catch (Exception e) {
			logger.error("toAddOrEdit:根据ID获取活动信息失败.",e);
		}
		return topicActivity;
	}
	
	/**
	 * 图片上传
	 * @param request
	 * @param shareImgStr
	 */
	private void updateImage(HttpServletRequest request, StringBuffer shareImgStr) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("imgUrl");
			for (int i = 0; i < iter.size(); i++) {
				String url = MsgPropertiesUtils.getUploadHouseUrl();
				HessianProxyFactory factory = new HessianProxyFactory();
				try {
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
					FileUploadRequest req = new FileUploadRequest();
					req.setSource("妈妈送房网");
					req.setFileType("jpg");// jpg 、png
					req.setGroupName("group1");
					req.setFile(iter.get(i).getBytes());
					if (req.getFile().length>0) {
						FileUploadResponse fileResponse = facade.uploadFile(req);
						if (null!=fileResponse.getFileUrl() && !"".equals(fileResponse.getFileUrl())) {
							shareImgStr.append(fileResponse.getFileUrl());
						}
						System.out.println("===图片路径为：" + fileResponse.getFileUrl() + "========" + fileResponse.getMessage());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}
	
	/**
	 * 客栈列表
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/shopList")
	public ModelAndView shopList(HttpSession session,HttpServletRequest request) throws  Exception {
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
		
		List<HouseShop> list = getHouseShopByTopicId(pager,request);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		mv.addObject("pager", pager);
		
		
		int topicId = Integer.parseInt(request.getParameter("topicId"));
		//关联店铺ID
		List<Integer> shopIdList = getTopicShop(topicId);
		List<HouseShop> houseShopList = new ArrayList<HouseShop>();
		for(int i = 0 ; i < list.size() ; i ++) {
			HouseShop houseShop = list.get(i);
			//判断是否被关联
			if(shopIdList.contains(houseShop.getId())) {
				houseShop.setCheckEd("1");
			}
			houseShopList.add(houseShop);
		}
		
		List<CityInfo> cityList = getCities();
		mv.addObject("cityList", cityList);
		
		mv.addObject("shopName", request.getParameter("shopName"));
		mv.addObject("cityName", request.getParameter("cityName"));
		mv.addObject("type", request.getParameter("type"));
		mv.addObject("list", houseShopList);
		mv.addObject("topicId", topicId);
		mv.setViewName("/topic/shopList");
		return mv;
	}
	
	/**
	 * 获取关联客栈信息
	 * @param topICId 主题活动ID
	 * @return
	 */
	public List<Integer> getTopicShop(int topicId) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("topicId",topicId);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getTopicShop");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) {
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
				JSONArray arr = dataJson.getJSONArray("shopIdList");
				for(int i = 0 ; i < arr.size() ; i ++) {
					net.sf.json.JSONObject object = arr.getJSONObject(i);
					list.add(object.getInt("shopId"));
				}
			} 
		} catch (Exception e) {
			logger.error("getTopicShop:获取客栈关联信息失败.",e);
		}
		return list;
	}
	
	/**
	 * 添加OR删除关联客栈
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrDelTopicShop")
	@ResponseBody
	public RestResponse addOrDelTopicShop(HttpSession session,HttpServletRequest request) throws  Exception {
		if(logger.isInfoEnabled()) {
			logger.info("start to run /addOrDelTopicShop.");
		}
		RestResponse restP = new RestResponse();
		try {
			int topicId = Integer.parseInt(request.getParameter("topicId"));//主题活动ID
			int shopId = Integer.parseInt(request.getParameter("shopId"));  //客栈店铺ID
			String flag = request.getParameter("flag");
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("topicID", topicId);
			postData.put("shopId", shopId);
			postData.put("flag", flag);
			JSONObject object = HttpClientPostMethod.httpReqUrl(postData, "addOrDelTopicShop");
			String code = object.getString("code");
			restP.setCode(code);
			logger.info("end to run /addOrDelTopicShop.");
		} catch (Exception e) {
			logger.error("addOrDelTopicShop：添加or删除关联异常.",e);
			e.printStackTrace();
		}
		return restP;
	}
	
	/**
	 * 店铺列表
	 * @return
	 * @throws Exception
	 */
	protected List<HouseShop> getHouseShopByTopicId(Pagination pager,HttpServletRequest request) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		if (null!=pager && !"".equals(pager)) {
			if (0!=pager.getPage_size()) {
				postData.put("limit", "limit");
			}
			if (0==pager.getCurrent_page()) {
				postData.put("start", pager.getCurrent_page()*pager.getPage_size());
				postData.put("end", (pager.getCurrent_page()+1)*pager.getPage_size());
			}else {
				postData.put("start", (pager.getCurrent_page()-1)*pager.getPage_size());
				postData.put("end", pager.getCurrent_page()*pager.getPage_size());
			}
		}
		//主题活动ID
		String ids = request.getParameter("topicId");
		int topicId = Integer.parseInt(request.getParameter("topicId"));
		//客栈名称
		if(!StringUtil.isEmpty(request.getParameter("shopName"))) {
			String shopName = request.getParameter("shopName");
			postData.put("shopName", shopName);
		}
		//城市名称
        if(!StringUtil.isEmpty(request.getParameter("cityName"))) {
        	String cityName = request.getParameter("cityName");
        	postData.put("cityName", cityName);
		}
        //客栈类型
		if(!StringUtil.isEmpty(request.getParameter("type"))) {
			String type = request.getParameter("type");
			postData.put("type", type);
		}
		
		postData.put("topicId", topicId);
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getHouseShopByTopicId");
		String code = result.getString("code");
		List<HouseShop> list = new ArrayList<HouseShop>();
		if ("0".equals(code)) {
			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				int num = Integer.parseInt(jsonObject.get("num").toString());
				if (null!=pager && !"".equals(pager)) {
				    pager.setTotal_count(num);
				}
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					HouseShop houseShop = new HouseShop();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer id = Integer.parseInt(jsonObject1.get("id").toString());
					String shopName = jsonObject1.get("shopName").toString();
					String shopDesc = jsonObject1.get("shopDesc").toString();
					String bossImage = jsonObject1.get("bossImage").toString();
					String bossPhone = jsonObject1.get("bossPhone").toString();
					String bossWeChat = jsonObject1.get("bossWeChat").toString();
					//String createTime = jsonObject1.get("createTime").toString();
					
					String cityName = jsonObject1.get("cityName").toString(); //城市名称
					String level = "";
					if (jsonObject1.containsKey("level")) {
						level = jsonObject1.getString("level"); //等级
					}
					String levelName = "";
					if("1".equals(level)) {
						levelName = "A";
					} else if ("2".equals(level)) {
						levelName = "AA";
					} else if ("3".equals(level)) {
						levelName = "AAA";
					}
					
					String type = "";
					if (jsonObject1.containsKey("type")) {
						type = jsonObject1.getString("type"); //类型
					}
					
					String typeName = "";
					if ("1".equals(type)) {
						typeName = "客栈";
					} else if ("2".equals(type)) {
						typeName = "酒店";
					} else if ("3".equals(type)) {
						typeName = "公寓";
					}
					
					String bossName = "";
					if (jsonObject1.containsKey("bossName")) {
						bossName = jsonObject1.getString("bossName"); //boss姓名
					}
				
					
					if (shopDesc.length()>80) {
						shopDesc = shopDesc.substring(0, 80)+"...";
					}
					houseShop.setId(id);
					houseShop.setShopName(shopName);
					houseShop.setShopDesc(shopDesc);
					houseShop.setBossImage(bossImage);
					houseShop.setBossPhone(bossPhone);
					houseShop.setBossWeChat(bossWeChat);
					//houseShop.setCreateTime(createTime);
					houseShop.setCityName(cityName);
					houseShop.setBossName(bossName);
					houseShop.setLevel(levelName);
					houseShop.setTypeName(typeName);
					
					list.add(houseShop);
				}
			}
		}
		return list;
	}
}
