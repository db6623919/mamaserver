package com.mmzb.house.web.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.common.errorCode.ReturnCode;
import com.mama.server.common.util.PartnershipEnum;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.TopicActivityInfo;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.HouseSearchPo;
import com.mmzb.house.web.model.houses.HouseVo;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;

/**
 * 发现
 * @author ayou
 */
@Controller
@RequestMapping("/discover")
public class DiscoverAction extends BaseAction{
	
	//参数配置
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	@Resource(name = "redisFacade")
	private RedisFacade      redisFacade;
	
	
	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(DiscoverAction.class);
	
	
	/**
	 * 发现首页 
	 * @return
	 */
	@RequestMapping(value = "/index.htm", method = { RequestMethod.GET })
	public ModelAndView index()  {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
	
	
	/**
	 * 发现首页 数据获取
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIndexJson.htm", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> getIndexJson(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		logger.info("run start to /discover/index.");
		Map<String, Object> map = new HashMap<String, Object>();
		List<TopicActivityInfo> topicList = new ArrayList<TopicActivityInfo>();
		int code = 0;
		try {
			Map<String, Object> postData=new HashMap<String, Object>();
			JSONObject dataObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getTopics");
			JSONObject resultJson = dataObject.getJSONObject("data");
			if(dataObject.getInteger("code") == ReturnCode.OK){
				com.meidusa.fastjson.JSONArray topicListJson = resultJson.getJSONArray("topicList");
				for(int i=0 ; i<topicListJson.size(); i++){
					JSONObject jsonObject = topicListJson.getJSONObject(i);
					TopicActivityInfo topicActivity = new TopicActivityInfo();
					topicActivity.setId(jsonObject.getInteger("id"));
					topicActivity.setActivityName(jsonObject.getString("activityName")); //活动名称
					topicActivity.setActivityType(jsonObject.getString("activityType")); //活动类型
					topicActivity.setImgUrl(jsonObject.getString("imgUrl") + "!h5i6s");             //活动图片
					topicActivity.setTitle(jsonObject.getString("title"));               //活动标题
					topicActivity.setIntroduction(jsonObject.getString("introduction")); //活动描述 
					topicList.add(topicActivity);
				}
			} else {
				code = 1;
			}
		} catch (Exception e) {
			code = 1;
			logger.info("/discover/index.htm：发现首页接口异常.",e);
		}
		map.put("code", code);
		map.put("topicList", topicList);
		//判断是否登陆 1、登陆 0、未登陆
		map.put("isLogin", checkLogin(request));
		logger.info("run end to /discover/index.",map);
		return map; 
	}
	
	/**
	 * 发现详情页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail.htm", method = { RequestMethod.GET })
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		logger.info("run start to /discover/detail.");
		/*List<HouseVo> houseList = new ArrayList<HouseVo>();
		try {
			//主题活动ID
			//int topicId = Integer.parseInt(request.getParameter("id"));
			//int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			//获取活动相关房源ID
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("topicId", 1);
			dataMap.put("pageNum", 0);
			dataMap.put("pageSize", 10);
			JSONObject dataObject = HttpClientPostMethod.httpReqUrl(dataMap, appProperties.getRequestRoot(), "getTopicHouses");
			JSONObject resultJson = dataObject.getJSONObject("data");
			if (dataObject.getInteger("code") == ReturnCode.OK) {
				String houseIds = resultJson.getString("houseIds");
				
				//获取活动相关房源信息
				Map<String,Object> houseMap = new HashMap<String, Object>();
   				houseMap.put("houseIds", houseIds);
   				JSONObject jsonResult=	HttpClientPostMethod.httpReqUrl(houseMap, appProperties.getRequestRoot(),"getHousesSearch");
	    		Object objectSearch = jsonResult.get("data");
    			if (jsonResult.getInteger("code") == ReturnCode.OK) {
    				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(objectSearch);
    				List<HouseSearchPo> houseSearchList = (List)JSONArray.toList(jsonObject.getJSONArray("list"), HouseSearchPo.class);
	    			for(int i = 0 ; i < houseSearchList.size() ; i++) {
	    				HouseSearchPo houseSearchPo = houseSearchList.get(i);
	    				HouseVo house = new HouseVo();
	    				house.setHouseId(houseSearchPo.getHouseId());
	    				house.setPrice(String.valueOf(houseSearchPo.getPrice()));
	    				house.setImageUrl(houseSearchPo.getImageUrl().split(",")[0]);
	    				house.setTagNameList(getTagList(houseSearchPo.getTagList()));
	    				house.setName(houseSearchPo.getName());
	    				houseList.add(house);
	    			}
    			}	
			}	
		} catch (Exception e) {
			logger.error("/discover/detail:发现详情页错误.",e);
		}
		//返回房源列表信息
		model.addAttribute("houseList", houseList);
		//判断是否登陆 1、登陆 0、未登陆
		model.addAttribute("isLogin", checkLogin(request));*/
		logger.info("run end to /discover/detail.");
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex"); 
	}
	
	/**
	 * 发现详情页翻页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailPage.htm", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> detailPage(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		logger.info("run start to /discover/detailPage.");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//主题活动ID
			int topicId = Integer.parseInt(request.getParameter("id"));
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			//获取活动相关房源ID
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("topicId", topicId);
			dataMap.put("pageNum", pageNum);
			dataMap.put("pageSize", 10);
			JSONObject dataObject = HttpClientPostMethod.httpReqUrl(dataMap, appProperties.getRequestRoot(), "getTopicHouses");
			JSONObject resultJson = dataObject.getJSONObject("data");
			if (dataObject.getInteger("code") == ReturnCode.OK) {
				String houseIds = resultJson.getString("houseIds");
				
				Map<String,Object> partnershipMap = (Map<String, Object>) resultJson.getDataMap().get("partnershipMap");
				
				//获取活动相关房源信息
				Map<String,Object> houseMap = new HashMap<String, Object>();
   				houseMap.put("houseIds", houseIds);
   				JSONObject jsonResult=	HttpClientPostMethod.httpReqUrl(houseMap, appProperties.getRequestRoot(),"getHousesSearch");
	    		Object objectSearch = jsonResult.get("data");
    			if (jsonResult.getInteger("code") == ReturnCode.OK) {
    				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(objectSearch);
    				List<HouseSearchPo> houseSearchList = (List)JSONArray.toList(jsonObject.getJSONArray("list"), HouseSearchPo.class);
    				List<HouseVo> houseList = new ArrayList<HouseVo>();
    				for(int i = 0 ; i < houseSearchList.size() ; i++) {
	    				HouseSearchPo houseSearchPo = houseSearchList.get(i);
	    				HouseVo house = new HouseVo();
	    				house.setHouseId(houseSearchPo.getHouseId());
	    				house.setPrice(String.valueOf(houseSearchPo.getPrice()));
	    				house.setImageUrl(houseSearchPo.getImageUrl().split(",")[0] + "!h5i6s");
	    				house.setTagNameList(getTagList(houseSearchPo.getTagList()));
	    				house.setName(houseSearchPo.getName());
	    				String partnership = "";
	    				if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.MemberInn.getCode())) {
	    					partnership = PartnershipEnum.MemberInn.getMessage();//会员
	    				} else if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.Holding.getCode())) {
	    					partnership = PartnershipEnum.Holding.getMessage();//控股
	    				} else if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.DepthCooperation.getCode())) {
	    					partnership = PartnershipEnum.DepthCooperation.getMessage();//深度
	    				} else if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.GuesthouseInn.getCode())) {
	    					partnership = PartnershipEnum.GuesthouseInn.getMessage();//民宿
	    				} else if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.JointBusinessInn.getCode())) {
	    					partnership = PartnershipEnum.JointBusinessInn.getMessage();//联合运营客栈
	    				}
	    				house.setSpecialTag(partnership);
	    				String description = houseSearchPo.getRoomNum() + "室 宜" + houseSearchPo.getPersonNum() + "人居住";
	    				house.setDescription(description);
	    				house.setMarketPrice(houseSearchPo.getMarketPrice());
	    				
	    				if (houseSearchPo.getMarketPrice() != 0) {
	    					DecimalFormat df = new DecimalFormat("######0.0");
		    				double discount =  (double)houseSearchPo.getPrice()/(double)houseSearchPo.getMarketPrice() * 10;
		    				house.setDiscountDescription(df.format(discount) + "折");
	    				}
	    				
	    				houseList.add(house);
	    			}
    				map.put("houseList", houseList);
    			}	
			}	
		} catch (Exception e) {
			logger.error("/discover/detailPage:发现详情页翻页错误.",e);
		}
		logger.info("run end to /discover/detailPage.");
		return map;
	}
	
	//redis中获取标签
    public List<String> getTagList(List<Integer> houseTagId){
    	 List<String> tagNames = new ArrayList<String>();
    	 RedisRequest redisRequest = new RedisRequest();
    	 redisRequest.setKey("mmsf:allHouseTag");
         if (redisFacade.getMapByKey(redisRequest) != null && CollectionUtils.isNotEmpty(houseTagId))
         {
        	String houseTag = "[" + redisFacade.getValueByKey(redisRequest) + "]";
        	JSONArray houseTags = JSONArray.fromObject(houseTag);
           //只取前三个标签
           int tagSize = (houseTagId.size() > 3) ? 3 : houseTagId.size();
           for(int i = 0; i < tagSize; i++)
           {
        	   for(int y = 0 ; y < houseTags.size() ; y ++) {
        		   net.sf.json.JSONObject tagObject =  (net.sf.json.JSONObject) houseTags.get(y);
        		   if(tagObject.getInt("id") == houseTagId.get(i)) {
        			   tagNames.add(tagObject.getString("name"));
        			   break;
        		   }
        	   }
           }
         }
         return tagNames;
    }
    
    /**
     * 判断是否登陆
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    public int checkLogin(HttpServletRequest request) throws Exception {
		logger.info("start run to checkLogin()");
		String memberId="";
		User userInfo = null;
		//判断是否登陆
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			userInfo = VfSsoUser.get();
			if (userInfo != null) {
				memberId = getMemberInfo(request.getSession()).getMemberId();
			}
		} 
		if(!StringUtils.isEmpty(memberId)){
			return 1;
		}
		return 0;
	}
    
    /**
     * 获取token
     * 
     * @param hsr
     * @return
     */
    private String getToken(HttpServletRequest hsr) {
        Cookie cookie = CasCookie.getCookie(hsr);
        logger.debug("Get cookie:{}", cookie);
        return cookie == null ? null : cookie.getValue();
    }
}
