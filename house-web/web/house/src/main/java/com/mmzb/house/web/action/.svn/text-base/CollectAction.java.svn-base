package com.mmzb.house.web.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.ClientProtocolException;
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
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.HouseSearchPo;
import com.mmzb.house.web.model.houses.HouseVo;

@Controller
public class CollectAction extends BaseAction {
	
    @Resource(name="appProperties")
	private AppProperties appProperties;
    
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    private static Logger logger = LoggerFactory.getLogger(CollectAction.class);
    
    /**
     * 收藏列表显示
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/my/getCollects.htm", method = { RequestMethod.GET })
    public ModelAndView getCollects(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	if (logger.isInfoEnabled()) {
			logger.info("start to run /my/getCollects");
		}
		Map<String, Object> map = new HashMap<String, Object>();
    	//RestResponse restP = new RestResponse();
    	List<HouseVo> houseInfoList = new ArrayList<HouseVo>();
    	try {
    		Map<String, Object> postData=new HashMap<String, Object>();
    		postData.put("uid", getMemberInfo(request.getSession()).getMemberId());
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getCollects");
    		String msg = result.get("msg").toString();
    		String code = result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
       	    if("0".equals(code)) {
       	    	net.sf.json.JSONObject jsonData= net.sf.json.JSONObject.fromObject(result.get("data")); 
       			if(0 == jsonData.getInt("num")){
       				model.addAttribute("cityName", URLDecoder.decode(request.getParameter("cityName")==null?"我的收藏":request.getParameter("cityName"),"UTF-8"));
       				return new ModelAndView(Contants.URL_PREFIX + "/collection/list", model);
       			} else {
       				JSONArray jsonArray = jsonData.getJSONArray("collects");
       				String houseIds = "";
       				for (int i = 0; i < jsonArray.size(); i++) {
       					net.sf.json.JSONObject object = jsonArray.getJSONObject(i);
       					houseIds += object.getInt("houseId") + ",";
					}
       				houseIds = houseIds.substring(0, houseIds.length() - 1);
       				Map<String,Object> houseMap = new HashMap<String, Object>();
       				houseMap.put("houseIds", houseIds);
       				JSONObject jsonResult=	HttpClientPostMethod.httpReqUrl(houseMap, appProperties.getRequestRoot(),"getHousesSearch");
		    		String codeSearch =jsonResult.getString("code");
		    		Object objectSearch = jsonResult.get("data");
	    			if ("0".equals(codeSearch)) {
	    				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(objectSearch);
	    				List<HouseSearchPo> houseSearchList = (List)JSONArray.toList(jsonObject.getJSONArray("list"), HouseSearchPo.class);
		    			for(int i = 0 ; i < houseSearchList.size() ; i++) {
		    				HouseSearchPo houseSearchPo = houseSearchList.get(i);
		    				
		    				HouseVo house = new HouseVo();
		    				house.setHouseId(houseSearchPo.getHouseId());
		    				house.setCollectFlag(1);
		    				//BigDecimal memTotalAmt = new BigDecimal(houseSearchPo.getPrice());
		    				//house.setPrice(memTotalAmt.subtract(memTotalAmt.multiply(new BigDecimal(appProperties.getDiscontParam()))).setScale(0, BigDecimal.ROUND_HALF_DOWN).toPlainString());
		    				house.setPrice(houseSearchPo.getPrice()+"");
		    				house.setImageUrl(houseSearchPo.getImageUrl().split(",")[0]);
		    				house.setTagNameList(getTagList(houseSearchPo.getTagList()));
		    				house.setName(houseSearchPo.getName());
		    				String partnership = "";
		    				/*if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.MemberInn.getCode())) {
		    					partnership = PartnershipEnum.MemberInn.getMessage();//会员
		    				} else if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.Holding.getCode())) {
		    					partnership = PartnershipEnum.Holding.getMessage();//控股
		    				} else if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.DepthCooperation.getCode())) {
		    					partnership = PartnershipEnum.DepthCooperation.getMessage();//深度
		    				} else if (String.valueOf(partnershipMap.get(String.valueOf(houseSearchPo.getHouseId()))).equals(PartnershipEnum.GuesthouseInn.getCode())) {
		    					partnership = PartnershipEnum.GuesthouseInn.getMessage();//民宿
		    				}*/
		    				house.setSpecialTag(partnership);
		    				String description = houseSearchPo.getRoomNum() + "室 宜" + houseSearchPo.getPersonNum() + "人居住";
		    				house.setDescription(description);
		    				house.setMarketPrice(houseSearchPo.getMarketPrice());
		    				
		    				if (houseSearchPo.getMarketPrice() != 0) {
		    					DecimalFormat df = new DecimalFormat("######0.0");
			    				double discount =  (double)houseSearchPo.getPrice()/(double)houseSearchPo.getMarketPrice() * 10;
			    				house.setDiscountDescription(df.format(discount) + "折");
		    				}
		    				houseInfoList.add(house);
		    			}
	    			}	
       				//model.addAttribute("cityName", URLDecoder.decode(request.getParameter("cityName")==null?"我的收藏":request.getParameter("cityName"),"UTF-8"));
       				//model.addAttribute("cityId", houseSearchList.get(0).getCityId());
       			}
    		}
		} catch (IOException e) {
			logger.error("/my/getCollects:收藏列表显示出错.", e);
		}finally{
			model.put("houseInfoList", houseInfoList);
			model.put("isLogin", 1);
		}
    	if (logger.isInfoEnabled()) {
		    logger.info("/app/getCollects is finished,params is {}.");
		}
    	return new ModelAndView(Contants.URL_PREFIX + "/collection/list", model);
    }
    
    /**
     * 删除或收藏
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/collect/addOrDeleCollect.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> addOrDeleCollect(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if (logger.isInfoEnabled()) {
			logger.info("start to run /collect/addOrDeleCollect");
		}
    	Map<String, Object> resultData = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		if(getMemberInfo(request.getSession()) != null) {
			try {
				String memberId = getMemberInfo(request.getSession()).getMemberId();
				int houseId = Integer.parseInt(request.getParameter("houseId"));
				Boolean favor = Boolean.valueOf(request.getParameter("favor"));
				postData.put("memberId", memberId); //会员ID
				postData.put("houseId", houseId); //房源ID
				postData.put("favor", favor);
				JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "addOrDeleCollect");
				String code = result.getString("code");
				//JSONObject dataJson = result.getJSONObject("data");
				resultData.put("code", code);
			} catch (ClientProtocolException e) {
				logger.error("/collect/addOrDeleCollect:收藏取消失败.", e);
			}
		} else {
			resultData.put("code", 2); //为登陆
		}
		if (logger.isInfoEnabled()) {
			logger.info("/collect/addOrDeleCollect is finished.",resultData.toString());
		}
		return resultData;
	}
    
    /**
     * 收藏房源列表翻页
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/my/getCollectsPage.htm", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String,Object> getCollectsPage(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws IOException {
    	if (logger.isInfoEnabled()) {
			logger.info("start to run /my/getCollectsPage");
		}
    	int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		Map<String, Object> postData=new HashMap<String, Object>();
        	postData.put("uid", getMemberInfo(request.getSession()).getMemberId());
        	postData.put("currentPage", pageNum);
        	postData.put("pageSize", 10);
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getCollectsPage");
    		String code = result.getString("code");
       	    if("0".equals(code)) {
       	    	net.sf.json.JSONObject jsonData= net.sf.json.JSONObject.fromObject(result.get("data")); 
       	    	JSONArray pageJson = jsonData.getJSONArray("page");
   				String houseIds = "";
   				for (int i = 0; i < pageJson.size(); i++) {
   					net.sf.json.JSONObject object = pageJson.getJSONObject(i);
   					houseIds += object.getInt("houseId") + ",";
				}
   				houseIds = houseIds.substring(0, houseIds.length() - 1);
   				Map<String,Object> houseMap = new HashMap<String, Object>();
   				List<HouseVo> houseInfoList = new ArrayList<HouseVo>();
   				houseMap.put("houseIds", houseIds);
   				JSONObject jsonResult=	HttpClientPostMethod.httpReqUrl(houseMap, appProperties.getRequestRoot(),"getHousesSearch");
	    		String codeSearch =jsonResult.getString("code");
	    		Object objectSearch = jsonResult.get("data");
    			if ("0".equals(codeSearch)) {
    				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(objectSearch);
    				List<HouseSearchPo> houseSearchList = (List)JSONArray.toList(jsonObject.getJSONArray("list"), HouseSearchPo.class);
	    			for(int i = 0 ; i < houseSearchList.size() ; i++) {
	    				HouseSearchPo houseSearchPo = houseSearchList.get(i);
	    				
	    				HouseVo house = new HouseVo();
	    				house.setHouseId(houseSearchPo.getHouseId());
	    				house.setCollectFlag(1);
	    				BigDecimal memTotalAmt = new BigDecimal(houseSearchPo.getPrice());
	    				//house.setPrice(memTotalAmt.subtract(memTotalAmt.multiply(new BigDecimal(appProperties.getDiscontParam()))).setScale(0, BigDecimal.ROUND_HALF_DOWN).toPlainString());
	    				house.setPrice(houseSearchPo.getPrice()+"");
	    				house.setImageUrl(houseSearchPo.getImageUrl().split(",")[0]);
	    				house.setTagNameList(getTagList(houseSearchPo.getTagList()));
	    				house.setName(houseSearchPo.getName());
	    				houseInfoList.add(house);
	    			}
    			}
   				map.put("code", code);
   				map.put("list", houseInfoList);
   				map.put("pageNum", pageNum);
    		}
		} catch (Exception e) {
			logger.error("getCollectsPage:房源收藏翻页异常.", e);
		}
    	if (logger.isInfoEnabled()) {
			logger.info("/my/getCollectsPage is finished.",map.toString());
		}
    	return map;
    }
    
    //获取redis中的房源标签
    public List<String> getTagList(List<Integer> houseTagId){
    	 List<String> tagNames = new ArrayList<String>();
    	 
 		RedisRequest req = new RedisRequest();
 		req.setKey("mmsf:allHouseTag");
 		
         if (redisFacade.getValueByKey(req) != null && CollectionUtils.isNotEmpty(houseTagId))
         {
        	String houseTag = "[" + redisFacade.getValueByKey(req) + "]";
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
             //net.sf.json.JSONObject tagObj = houseTags.getJSONObject(houseTagId.get(i));
             //tagNames.add(tagObj.getString("name"));
           }
         }
         return tagNames;
    }
}
