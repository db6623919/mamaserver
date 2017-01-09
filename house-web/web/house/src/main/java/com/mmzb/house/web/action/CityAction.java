package com.mmzb.house.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.ApplicationConfig;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.City;
import com.mmzb.house.web.action.dto.CityInfo;
import com.mmzb.house.web.action.dto.HouseInfo;
import com.mmzb.house.web.action.dto.HouseTag;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.JsonGeneratorUtils;
import com.mmzb.house.web.action.util.LetterUtil;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.core.common.vo.ResponseOut;
import com.mmzb.house.web.model.HotCityVo;
import com.mmzb.house.web.model.HouseSearchPo;
import com.mmzb.house.web.model.TradeArea;
import com.mmzb.house.web.model.houses.RecommondHouseVo;
import com.mmzb.house.web.util.CS;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;
import com.netfinworks.vfsso.client.exception.CasServiceException;

@Controller
public class CityAction extends BaseAction {

	@Resource(name = "appProperties")
	private AppProperties appProperties;

    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
	@Autowired
	public GerneralMethod gerneralMethod;

	private static Logger logger = LoggerFactory.getLogger(CityAction.class);

	// 获取vip会员卡信息
	@RequestMapping(value = "getCities.htm", method = { RequestMethod.GET })
	public ModelAndView getCities(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("isHot", "0");
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
		try {
			JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, appProperties.getRequestRoot(), "getCities");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {

				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
					for (int j = 0; j < arrayShowDetail.size(); j++) {
						CityInfo cityInfo = new CityInfo();
						net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
						Integer provId = Integer.parseInt(jsonObject1.get("provId").toString());
						Integer cityId = Integer.parseInt(jsonObject1.get("cityId").toString());
						String cityName = jsonObject1.get("cityName").toString();
						// Integer isHot=
						// Integer.parseInt(jsonObject1.get("isHot").toString());
						cityInfo.setCityId(cityId);
						cityInfo.setCityName(cityName);
						// cityInfo.setIsHot(isHot);
						cityInfo.setProvId(provId);
						cityInfoList.add(cityInfo);
					}
				}
			} else {
				try {
					JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
		map.put("cityInfoList", cityInfoList);
		restP.setData(map);
		return new ModelAndView(Contants.URL_PREFIX + "//house/house", "response", restP);

	}

	// 查询热卖城市
	@RequestMapping(value = "getHotCities.htm", method = { RequestMethod.GET })
	public String getHotCities(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws CasServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		// 显示热门城市//0非热门+非推荐，1热门，2推荐，10热门+推荐
		// postData.put("type", 0);
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
		List<CityInfo> cityInfoList1 = new ArrayList<CityInfo>();
		List<String> nameList = new ArrayList<String>();
		Map<String, CityInfo> beanMap = new HashMap<String, CityInfo>();
		Set<String> letterSet = new TreeSet<String>();
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCities");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
					for (int j = 0; j < arrayShowDetail.size(); j++) {
						CityInfo cityInfo = new CityInfo();
						net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
						cityInfo.setCityId(jsonObject1.getInt("cityId"));
						String cityName = jsonObject1.getString("cityName");
						cityInfo.setCityName(cityName);
						nameList.add(cityName);
						cityInfo.setFirstLetter(LetterUtil.getFirstLetter(cityName).toUpperCase());
						cityInfo.setType(jsonObject1.getInt("type"));
						cityInfo.setProvId(jsonObject1.getInt("provId"));
						beanMap.put(cityName, cityInfo);
					}
				}
				//Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
				Object[] temp = nameList.toArray();
				//Arrays.sort(temp, com);
				int a=0;
				for (Object str : temp) {
					if(beanMap.get(str).getType()==1||beanMap.get(str).getType()==10){
						if(a<8){
							cityInfoList1.add(beanMap.get(str));
							a++;
						}
					}
					cityInfoList.add(beanMap.get(str));
					letterSet.add(beanMap.get(str).getFirstLetter());
				}
			}
			
			
			
		} catch (Exception e) {
			logger.error("错误日志", e);
		}
		//添加登录信息
		String travelAddress = appProperties.getTravelAddress();
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			User userInfo = VfSsoUser.get();
			if (userInfo != null) {
				travelAddress += ("?token=" + token);
				model.addAttribute("isLogin", "1");

			}
		} 	
		
		model.addAttribute("cityInfoList", cityInfoList);
		model.addAttribute("cityInfoList1", cityInfoList1);
		model.addAttribute("letterSet", letterSet);
		model.addAttribute("travelAddress", travelAddress);
		return Contants.URL_PREFIX + "/city/searchnew";
	}

	// 查询城市房源列表
	@RequestMapping(value = "/city/list.htm", method = { RequestMethod.GET })
	public String getCitiesList(HttpServletRequest request, HttpServletResponse response, Model model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
		String cityIdParam = request.getParameter("cityId");
		Map<String, Object> postData = new HashMap<String, Object>();
		List<Integer> strList = new ArrayList<Integer>();
		List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
		List<String> description = new ArrayList<String>();
		List<Integer> collect = new ArrayList<Integer>();
		try {
			// 若登陆状态获取用户收藏记录
			if (loginUser != null) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("uid", loginUser.getUid());
				JSONObject json = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getCollects");
				net.sf.json.JSONObject dataObj = net.sf.json.JSONObject.fromObject(json.get("data"));
				JSONArray array = dataObj.getJSONArray("collects");
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject tempObj = array.getJSONObject(i);
					collect.add(tempObj.getInt("houseId"));
				}
			}
			strList.add(Integer.parseInt(cityIdParam));
			postData.put("cityIds", strList);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHouses");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("houses").toString());
					for (int j = 0; j < arrayShowDetail.size(); j++) {
						HouseInfo houseInfo = new HouseInfo();
						net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
						int houseId = jsonObject1.getInt("houseId");
						houseInfo.setHouseId(jsonObject1.getInt("houseId"));
						HouseInfo tempHouse = new HouseInfo();
						tempHouse = gerneralMethod.getHouseInfo(houseId);
						houseInfo.setDiscount(tempHouse.getDiscount());
						houseInfo.setTotalRoomNum(tempHouse.getTotalRoomNum());
						houseInfo.setIntroductionImg(tempHouse.getIntroductionImg());
						houseInfo.setCityId(jsonObject1.getInt("cityId"));
						houseInfo.setFreezeAmt(jsonObject1.getInt("freezeAmt"));
						houseInfo.setTotalAmt(jsonObject1.getInt("totalAmt"));
						BigDecimal memTotalAmt = new BigDecimal(jsonObject1.getInt("memTotalAmt"));
						houseInfo.setMemTotalAmt(memTotalAmt.intValue());
						houseInfo.setDiscontMax(memTotalAmt.subtract(memTotalAmt.multiply(new BigDecimal(appProperties.getDiscontParam()))).setScale(0, BigDecimal.ROUND_HALF_DOWN)
								.toPlainString());
						List<Integer> list = (List<Integer>) jsonObject1.get("mark");
						for (int a : list) {
							description.add(ApplicationConfig.miaoshuMap.get(a));
						}
						houseInfo.setMemFreezeAmt(jsonObject1.getInt("memFreezeAmt"));
						net.sf.json.JSONObject temp = net.sf.json.JSONObject.fromObject(jsonObject1.getString("summaryInfo"));
						houseInfo.setHouseName(temp.getString("houseName"));
						if ("2".equals(temp.getString("status")))
							continue;
						houseInfo.setRoom(String.valueOf(jsonObject1.getInt("room")));
						List<Integer> list1 = (List<Integer>) jsonObject1.get("flag");
						houseInfo.setFlag(list1.get(0));
						if (loginUser != null && collect.contains(houseId))
							houseInfo.setCollectFlag(1);
						else
							houseInfo.setCollectFlag(0);
						houseInfoList.add(houseInfo);
					}
				}
			} else {
				JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
			}
			
			//添加登录信息
			String travelAddress = appProperties.getTravelAddress();
			String token = getToken(request);
			if (StringUtil.isNotEmpty(token)) {
				VfSsoUser.setCurrentToken(token);
				User userInfo = VfSsoUser.get();
				if (userInfo != null) {
					travelAddress += ("?token=" + token);
					model.addAttribute("isLogin", "1");

				}
			} 		
			
			model.addAttribute("cityName", URLDecoder.decode(request.getParameter("cityName"), "UTF-8"));
			model.addAttribute("travelAddress", travelAddress);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		} catch (Exception e) {
			logger.error("错误日志", e);
		}
		model.addAttribute("houseInfoList", houseInfoList);
		model.addAttribute("cityId", cityIdParam);
		return Contants.URL_PREFIX + "/house/listnew";
	}
	
	   // 按城市名查询城市房源列表
		@RequestMapping(value = "/city/searchHouseByName.htm", method = { RequestMethod.GET })
		public String searchHouseByName(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
			String name = request.getParameter("name");
			if (name!=null && !name.equals("")) {
				name = URLDecoder.decode(name, "UTF-8");
			}
			String cityId = request.getParameter("cityId");
			//站长统计
			CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
			String imgurl = cs.trackPageView();
			
			Map<String, Object> map = new HashMap<String, Object>();
			RestResponse restP = new RestResponse();
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("name", name);
			List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
//			String cityId = "";
			try {
				String code= "";
				String msg= "";
				if (cityId==null||cityId.equals("")) {
					JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot()+"/customer","getCity");
		    		msg=result.get("msg").toString();
		    	    code=result.getString("code");
		    		Object object = result.get("data");
		    		if ("0".equals(code)) {
		    			net.sf.json.JSONObject Object = net.sf.json.JSONObject.fromObject(object);
		    			List<City> cityList = new ArrayList<City>();
		    			cityList = (List)JSONArray.toList(Object.getJSONArray("cityList"), City.class);
		    		    cityId = cityList.get(0).getCityId().toString();
		    		}
				}else {
					postData.clear();
					postData.put("cityId", cityId);
					JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot()+"/customer","getCity");
					msg=result.get("msg").toString();
		    	    code=result.getString("code");
		    		Object object = result.get("data");
		    		if ("0".equals(code)) {
		    			net.sf.json.JSONObject Object = net.sf.json.JSONObject.fromObject(object);
		    			List<City> cityList = new ArrayList<City>();
		    			cityList = (List)JSONArray.toList(Object.getJSONArray("cityList"), City.class);
		    		    name = cityList.get(0).getName().toString();
		    		}else {
						logger.error("按cityid查询城市名称异常!");
					}
				}
	    		    
	    		    //按城市ID查询redis中商圈
	    		    RedisRequest redisReq = new RedisRequest();
	    		    redisReq.setKey("mmsf:tradeArea:"+cityId);
					String tradeArea = "["+redisFacade.getValueByKey(redisReq)+"]";
//	    		    String tradeArea = "["+TradeAreaCache.getTradeAreaFromRedis(cityId)+"]";
					if (null!= redisFacade.getValueByKey(redisReq) && !"".equals(redisFacade.getValueByKey(redisReq)) 
							&& !"null".equals(redisFacade.getValueByKey(redisReq)) && !"{}".equals(redisFacade.getValueByKey(redisReq))) {
						JSONArray tradeArray = JSONArray.fromObject(tradeArea);
						List<TradeArea> tradeList = getTradeList(tradeArray);
						model.addAttribute("tradeList", tradeList);
					}
					
					RedisRequest reqRe = new RedisRequest();
					reqRe.setKey("mmsf:houseTag");
					String tagStr = "["+redisFacade.getValueByKey(reqRe)+"]";
//					String tagStr = "["+HouseTagCache.getHouseTagFromRedis()+"]";
					if (null!= redisFacade.getValueByKey(reqRe) && !"".equals(redisFacade.getValueByKey(reqRe)) 
							&& !"null".equals(redisFacade.getValueByKey(reqRe)) && !"{}".equals(redisFacade.getValueByKey(reqRe))) {
						JSONArray tagArray = JSONArray.fromObject(tagStr);
						List<HouseTag> tagList = getTagList(tagArray);
						setTagList(tagList,model);

					}else {
						JSONObject resObject = HttpClientPostMethod.httpDataReqUrl(postData, appProperties.getRequestRoot(), "putTagsToRedis");
						String codes = resObject.getString("code");
						if ("0".equals(codes)) {
//							String tagsStr = "["+redisFacade.getValueByKey(reqRe)+"]";
							if (null!= redisFacade.getValueByKey(reqRe) && !"".equals(redisFacade.getValueByKey(reqRe)) 
									&& !"null".equals(redisFacade.getValueByKey(reqRe)) && !"{}".equals(redisFacade.getValueByKey(reqRe))) {
								JSONArray tagArray = JSONArray.fromObject(tagStr);
								List<HouseTag> tagList = getTagList(tagArray);
								setTagList(tagList,model);

							}
							
						}
					}
					
	    			Map<String, Object> postDatMap = new HashMap<String, Object>();
	    			postDatMap.put("cityId", Integer.parseInt(cityId));
	    			JSONObject jsonResult=	HttpClientPostMethod.httpReqUrl(postDatMap, appProperties.getRequestRoot(),"getHousesSearch");
		    		String codeSearch =jsonResult.getString("code");
		    		Object objectSearch = jsonResult.get("data");
	    			if ("0".equals(codeSearch)) {
	    				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(objectSearch);
		    			List<HouseSearchPo> houseSearchList = new ArrayList<HouseSearchPo>();
		    			houseSearchList = (List)JSONArray.toList(jsonObject.getJSONArray("list"), HouseSearchPo.class);
		    			RedisRequest redisRequest = new RedisRequest();
		    			redisRequest.setKey("mmsf:allHouseTag");
		    			String houseTag = "["+redisFacade.getValueByKey(redisRequest)+"]";
//		    			String houseTag = "["+HouseTagCache.getAllHouseTagFromRedis()+"]";
		    			JSONArray jArray = JSONArray.fromObject(houseTag);
		    			for (HouseSearchPo houseSearchPo:houseSearchList) {
							HouseInfo houseInfo = new HouseInfo();
//							String[] introductionImg = new String[1];
//							introductionImg[0] = houseSearchPo.getImageUrl();
							String[] introductionImg = houseSearchPo.getImageUrl().split(",");
							houseInfo.setIntroductionImg(introductionImg);
							houseInfo.setHouseId((int)houseSearchPo.getHouseId());
							houseInfo.setHouseName(houseSearchPo.getName());
							houseInfo.setMemTotalAmt((int)houseSearchPo.getPrice());  //会员价
							List<Integer> tagList = houseSearchPo.getTagList();
						    List<String> nameList = new ArrayList<String>();
							for (int i = 0; i < jArray.size(); i++) {
								net.sf.json.JSONObject jObject = jArray.getJSONObject(i);
								for (int j = 0; j < tagList.size(); j++) {
									int tagId = tagList.get(j);
									if (jObject.getString("id").equals(String.valueOf(tagId))) {
										nameList.add(jObject.getString("name"));
									}
								}
							}
							if (nameList.size()>3) {
								nameList = nameList.subList(0, 3);
							}
							houseInfo.setTagNameList(nameList);
							
							if(getMemberInfo(request.getSession()) != null) { //判断是否登陆
								String memberId = getMemberInfo(request.getSession()).getMemberId();
								
				        		RedisRequest req = new RedisRequest();
			            		req.setKey("mmsf:collect:"+houseInfo.getHouseId()+memberId);		            		
			            		
								if(redisFacade.getValueByKey(req) != null) {
									houseInfo.setCollectFlag(1);
								}
							}	
							
							houseInfoList.add(houseInfo);
						}
					}
	    			
	       		map.put("msg",msg);
	       		map.put("code", code);
	       		restP.setSuccess(true);
				restP.setData(map);
				
			} catch (IOException e) {
				map.put("code", 2);
				map.put("msg", "系统繁忙，查询失败.");
				restP.setSuccess(false);
				logger.error("错误日志", e);
			}
			model.addAttribute("houseInfoList", houseInfoList);
			model.addAttribute("cityId", cityId);
			model.addAttribute("pageNum",1);
			model.addAttribute("cityName", name);
			model.addAttribute("imgurl", imgurl);
			
			//判断是否登陆
			setLoginStatus(request, model);
			
			return Contants.URL_PREFIX + "/house/HouseList";
		}

		private void setLoginStatus(HttpServletRequest request, Model model)
		{
			int isLogin = 0;
			try 
			{
				UserInfo loginUser = getLoginUserInfo(request);
				isLogin = (loginUser.getUid() != null) ? 1 : 0;
				
			} 
			catch (Exception e)
			{
				logger.warn("failed to get loginStatus", e);
				isLogin = 0;
			}
			
			model.addAttribute("isLogin", isLogin);
		}
		
		   private void setTagList(List<HouseTag> tagList, Model model) {
			   if (tagList.size()>6) {
					if (tagList.size()>=9) {
						model.addAttribute("tagList3", tagList.subList(6, 9));
					}else {
						model.addAttribute("tagList3", tagList.subList(6, tagList.size()));
					}
				} 
				if (tagList.size()>3) {
					if (tagList.size()>=6) {
						model.addAttribute("tagList2", tagList.subList(3, 6));
					}else {
						model.addAttribute("tagList2", tagList.subList(3, tagList.size()));
					}
				}
				if (tagList.size()>0){
					if (tagList.size()>=3) {
						model.addAttribute("tagList1", tagList.subList(0, 3));
					}else {
						model.addAttribute("tagList1", tagList.subList(0, tagList.size()));
					}
				}
			
		}

			// 按参数查询房源列表
			@RequestMapping(value = "/city/searchHouseByPar.htm", method = { RequestMethod.GET })
			public String searchHouseByPar(HttpServletRequest request, HttpServletResponse response, Model model) {
				String cityId = request.getParameter("cityId");
				String tradeAreaId = request.getParameter("tradeAreaId");
				String priceRange = request.getParameter("priceRange");
				String personNum = request.getParameter("personNum");
				String roomNum = request.getParameter("roomNum");
				String keyWord = request.getParameter("keyWord");
				String sortBy = request.getParameter("sortBy");
				String cityName = request.getParameter("cityName");
				String tagStr = request.getParameter("tag");
				Map<String, Object> map = new HashMap<String, Object>();
				RestResponse restP = new RestResponse();
				List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
				try {
		    		    
		    		    //按城市ID查询redis中商圈
		    		    RedisRequest redisReq = new RedisRequest();
		    		    redisReq.setKey("mmsf:tradeArea:"+cityId);
						String tradeArea = "["+redisFacade.getValueByKey(redisReq)+"]";
//					String tradeArea = "["+TradeAreaCache.getTradeAreaFromRedis(cityId)+"]";
						if (null!= redisFacade.getValueByKey(redisReq) && !"".equals(redisFacade.getValueByKey(redisReq)) 
								&& !"null".equals(redisFacade.getValueByKey(redisReq))) {
							JSONArray tradeArray = JSONArray.fromObject(tradeArea);
							List<TradeArea> tradeList = getTradeList(tradeArray);
							model.addAttribute("tradeList", tradeList);
						}
						
		    			Map<String, Object> postDatMap = new HashMap<String, Object>();
		    			if (null!=cityId && !"".equals(cityId)) {
		    				postDatMap.put("cityId", Integer.parseInt(cityId));
						}
		    			if (null!=tradeAreaId && !"".equals(tradeAreaId)) {
							postDatMap.put("merLocationId", Integer.parseInt(tradeAreaId));
						}
		    			if (null!=priceRange && !"".equals(priceRange)) {
		    				postDatMap.put("priceRange", Integer.parseInt(priceRange));
						}
		    			if (null!=personNum && !"".equals(personNum)) {
							postDatMap.put("personNum", Integer.parseInt(personNum));
						}
		    			if (null!=roomNum && !"".equals(roomNum)) {
							postDatMap.put("roomNum", Integer.parseInt(roomNum));
						}
		    			if (null!=keyWord && !"".equals(keyWord)) {
							postDatMap.put("keyWord", keyWord);
						}
		    			if (null!=sortBy && !"".equals(sortBy)) {
							postDatMap.put("sortBy", Integer.parseInt(sortBy));
						}
		    			if (null!=tagStr && !"".equals(tagStr)) {
		    				postDatMap.put("tag", tagStr);
						}
		    			
		    			JSONObject jsonResult=	HttpClientPostMethod.httpReqUrl(postDatMap, appProperties.getRequestRoot(),"getHousesSearch");
		    			String msg=jsonResult.get("msg").toString();
			    		String codeSearch =jsonResult.getString("code");
			    		Object objectSearch = jsonResult.get("data");
		    			if ("0".equals(codeSearch)) {
		    				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(objectSearch);
			    			List<HouseSearchPo> houseSearchList = new ArrayList<HouseSearchPo>();
			    			houseSearchList = (List)JSONArray.toList(jsonObject.getJSONArray("list"), HouseSearchPo.class);
			    			RedisRequest redisRequest = new RedisRequest();
			    			redisRequest.setKey("mmsf:houseTag");
			    			String houseTag = "["+redisFacade.getValueByKey(redisRequest)+"]";
//			    			String houseTag = "["+HouseTagCache.getHouseTagFromRedis()+"]";
			    			JSONArray jArray = JSONArray.fromObject(houseTag);
			    			for (HouseSearchPo houseSearchPo:houseSearchList) {
								HouseInfo houseInfo = new HouseInfo();
//								String[] introductionImg = new String[1];
//								introductionImg[0] = houseSearchPo.getImageUrl();
								String[] introductionImg = houseSearchPo.getImageUrl().split(",");
								houseInfo.setIntroductionImg(introductionImg);
								houseInfo.setHouseId((int)houseSearchPo.getHouseId());
								houseInfo.setHouseName(houseSearchPo.getName());
								houseInfo.setMemTotalAmt((int)houseSearchPo.getPrice());  //会员价
								List<Integer> tagList = houseSearchPo.getTagList();
							    List<String> nameList = new ArrayList<String>();
								for (int i = 0; i < jArray.size(); i++) {
									net.sf.json.JSONObject jObject = jArray.getJSONObject(i);
									for (int j = 0; j < tagList.size(); j++) {
										int tagId = tagList.get(j);
										if (jObject.getString("id").equals(String.valueOf(tagId))) {
											nameList.add(jObject.getString("name"));
										}
									}
								}
								if (nameList.size()>3) {
									nameList = nameList.subList(0, 3);
								}
								houseInfo.setTagNameList(nameList);
								houseInfoList.add(houseInfo);
							}
						}
		    			
		       		map.put("msg",msg);
		       		map.put("code", codeSearch);
		       		restP.setSuccess(true);
					restP.setData(map);
					
				} catch (IOException e) {
					map.put("code", 2);
					map.put("msg", "系统繁忙，查询失败.");
					restP.setSuccess(false);
					logger.error("错误日志", e);
				}
				model.addAttribute("houseInfoList", houseInfoList);
				model.addAttribute("cityId", cityId);
				model.addAttribute("pageNum",1);
				model.addAttribute("cityName", cityName);
				model.addAttribute("tradeAreaId", tradeAreaId);
				model.addAttribute("priceRange", priceRange);
				
				return Contants.URL_PREFIX + "/house/houseList";
			}

	    private List<TradeArea> getTradeList(JSONArray tradeArray) {
	    	List<TradeArea> tradeList = new ArrayList<TradeArea>();
	    	for (int i = 0; i < tradeArray.size(); i++) {
	    		TradeArea tradeArea = new TradeArea();
	    		net.sf.json.JSONObject jObject = tradeArray.getJSONObject(i);
	    		tradeArea.setId(Integer.parseInt(jObject.getString("id")));
	    		tradeArea.setName(jObject.getString("name"));
	    		tradeList.add(tradeArea);
	    	}
		    return tradeList;
	    }
	    
	    private List<HouseTag> getTagList(JSONArray tagArray) {
	    	List<HouseTag> tagList = new ArrayList<HouseTag>();
	    	for (int i = 0; i < tagArray.size(); i++) {
	    		HouseTag houseTag = new HouseTag();
	    		net.sf.json.JSONObject jObject = tagArray.getJSONObject(i);
	    		houseTag.setId(Integer.parseInt(jObject.getString("id")));
	    		houseTag.setName(jObject.getString("name"));
	    		tagList.add(houseTag);
	    	}
		    return tagList;
	    }

		/**
	     * 搜索 房源列表 下拉翻页
	     */
	    @RequestMapping(value = "/moreSearchHouseList.htm")
	    public ModelAndView moreSearchHouseList(HttpServletRequest request, HttpServletResponse resp, ModelMap model) throws Exception {
			String cityId = request.getParameter("cityId");
			String pageNum = request.getParameter("pageNum");
			String priceRange = request.getParameter("priceRange");
			String personNum = request.getParameter("personNum");
			String roomNum = request.getParameter("roomNum");
			String sortBy = request.getParameter("sortBy");
			String tradeAreaId = request.getParameter("tradeAreaId");
			String keyWord = request.getParameter("keyWord");
			String tag = request.getParameter("tag");
			
			Map<String, Object> map = new HashMap<String, Object>();
			RestResponse restP = new RestResponse();
			List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
			Map<String, Object> postDatMap = new HashMap<String, Object>();
			postDatMap.put("cityId", Integer.parseInt(cityId));
			postDatMap.put("pageNo", Integer.parseInt(pageNum));
			if (null!=priceRange && !"".equals(priceRange)) {
				postDatMap.put("priceRange", Integer.parseInt(priceRange));
			}
			if (null!=personNum && !"".equals(personNum)) {
				postDatMap.put("personNum", Integer.parseInt(personNum));
			}
			if (null!=sortBy && !"".equals(sortBy)) {
				postDatMap.put("sortBy", Integer.parseInt(sortBy));
			}
			if (null!=roomNum && !"".equals(roomNum)) {
				postDatMap.put("roomNum", Integer.parseInt(roomNum));
			}
			if (null!=tradeAreaId && !"".equals(tradeAreaId)) {
				postDatMap.put("merLocationId", Integer.parseInt(tradeAreaId));
			}
			if (null!=keyWord && !"".equals(keyWord)) {
				postDatMap.put("keyWord", keyWord);
			}
			if (null!=tag && !"".equals(tag)) {
				postDatMap.put("tag", tag);
			}
			JSONObject jsonResult=	HttpClientPostMethod.httpReqUrl(postDatMap, appProperties.getRequestRoot(),"getHousesSearch");
			String msg=jsonResult.get("msg").toString();
			String codeSearch =jsonResult.getString("code");
			Object objectSearch = jsonResult.get("data");
			if ("0".equals(codeSearch)) {
				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(objectSearch);
				List<HouseSearchPo> houseSearchList = new ArrayList<HouseSearchPo>();
				houseSearchList = (List)JSONArray.toList(jsonObject.getJSONArray("list"), HouseSearchPo.class);
				RedisRequest redisRequest = new RedisRequest();
				redisRequest.setKey("mmsf:allHouseTag");
				String houseTag = "["+redisFacade.getValueByKey(redisRequest)+"]";
//				String houseTag = "["+HouseTagCache.getAllHouseTagFromRedis()+"]";
				JSONArray jArray = JSONArray.fromObject(houseTag);
				for (HouseSearchPo houseSearchPo:houseSearchList) {
					HouseInfo houseInfo = new HouseInfo();
//					String[] introductionImg = new String[1];
//					introductionImg[0] = houseSearchPo.getImageUrl();
					String[] introductionImg = houseSearchPo.getImageUrl().split(",");
					houseInfo.setIntroductionImg(introductionImg);
					houseInfo.setHouseId((int)houseSearchPo.getHouseId());
					houseInfo.setHouseName(houseSearchPo.getName());
					houseInfo.setMemTotalAmt((int)houseSearchPo.getPrice());  //会员价
					List<Integer> tagList = houseSearchPo.getTagList();
					List<String> nameList = new ArrayList<String>();
					if (!("{}").equals(redisFacade.getValueByKey(redisRequest))) {
						for (int i = 0; i < jArray.size(); i++) {
							net.sf.json.JSONObject jObject = jArray.getJSONObject(i);
							for (int j = 0; j < tagList.size(); j++) {
								int tagId = tagList.get(j);
								if (jObject.getString("id").equals(String.valueOf(tagId))) {
									nameList.add(jObject.getString("name"));
								}
							}
						}
					}
					
					if (nameList.size()>3) {
						nameList = nameList.subList(0, 3);
					}
					houseInfo.setTagNameList(nameList);
					
					if(getMemberInfo(request.getSession()) != null) { //判断是否登陆
						String memberId = getMemberInfo(request.getSession()).getMemberId();
						
		          		RedisRequest req = new RedisRequest();
	            		req.setKey("mmsf:collect:"+houseInfo.getHouseId()+memberId);
	            		
						if(redisFacade.getValueByKey(req) != null) {
							houseInfo.setCollectFlag(1);
						}
					}
					
					houseInfoList.add(houseInfo);
				}
				
				
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("list", houseInfoList);
			data.put("pageNum", pageNum);
//			RestResponse restP = new RestResponse();
			restP.setData(data);
			
			JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);

	        return null;
	    }
	    
	@RequestMapping(value = "/getListJson.htm", method = { RequestMethod.GET })
	public void getListJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		String cityIdParam = request.getParameter("cityId");
		Map<String, Object> postData = new HashMap<String, Object>();
		List<Integer> strList = new ArrayList<Integer>();
		List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
		List<String> description = new ArrayList<String>();
		List<Integer> collect = new ArrayList<Integer>();
		net.sf.json.JSONObject resJson = new net.sf.json.JSONObject();
		try {
			// 若登陆状态获取用户收藏记录
			if (loginUser != null) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("uid", loginUser.getUid());
				JSONObject json = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getCollects");
				net.sf.json.JSONObject dataObj = net.sf.json.JSONObject.fromObject(json.get("data"));
				JSONArray array = dataObj.getJSONArray("collects");
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject tempObj = array.getJSONObject(i);
					collect.add(tempObj.getInt("houseId"));
				}
			}
			if (!"".equals(cityIdParam) && null != cityIdParam) {
				strList.add(Integer.parseInt(cityIdParam));
				postData.put("cityIds", strList);
			}
			postData.put("pageNum", Integer.parseInt(request.getParameter("page")));
			postData.put("pageCount", 3);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHouses");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			resJson.put("msg", msg);
			resJson.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					int num = jsonObject.getInt("num");
					if (num == 0) {
						resJson.put("code", 1);
						logger.info("没有查询到记录信息");
						return;
					}
					JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("houses").toString());
					for (int j = 0; j < arrayShowDetail.size(); j++) {
						HouseInfo houseInfo = new HouseInfo();
						net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
						int houseId = jsonObject1.getInt("houseId");
						houseInfo.setHouseId(jsonObject1.getInt("houseId"));
						HouseInfo tempHouse = new HouseInfo();
						tempHouse = gerneralMethod.getHouseInfo(houseId);
						houseInfo.setDiscount(tempHouse.getDiscount());
						houseInfo.setTotalRoomNum(tempHouse.getTotalRoomNum());
						houseInfo.setIntroductionImg(tempHouse.getIntroductionImg());
						houseInfo.setCityId(jsonObject1.getInt("cityId"));
						houseInfo.setFreezeAmt(jsonObject1.getInt("freezeAmt"));
						houseInfo.setTotalAmt(jsonObject1.getInt("totalAmt"));
						BigDecimal memTotalAmt = new BigDecimal(jsonObject1.getInt("memTotalAmt"));
						houseInfo.setMemTotalAmt(memTotalAmt.intValue());
						houseInfo.setDiscontMax(memTotalAmt.subtract(memTotalAmt.multiply(new BigDecimal(appProperties.getDiscontParam()))).setScale(0, BigDecimal.ROUND_HALF_DOWN)
								.toPlainString());
						List<Integer> list = (List<Integer>) jsonObject1.get("mark");
						for (int a : list) {
							description.add(ApplicationConfig.miaoshuMap.get(a));
						}
						houseInfo.setMemFreezeAmt(jsonObject1.getInt("memFreezeAmt"));
						net.sf.json.JSONObject temp = net.sf.json.JSONObject.fromObject(jsonObject1.getString("summaryInfo"));
						houseInfo.setHouseName(URLEncoder.encode(temp.getString("houseName"), "utf-8"));
						houseInfo.setRoom(String.valueOf(jsonObject1.getInt("room")));
						List<Integer> list1 = (List<Integer>) jsonObject1.get("flag");
						houseInfo.setFlag(list1.get(0));
						if (loginUser != null && collect.contains(houseId))
							houseInfo.setCollectFlag(1);
						else
							houseInfo.setCollectFlag(0);
						houseInfoList.add(houseInfo);
					}
				}
			} else {
				resJson.put("code", 1);
			}
			model.addAttribute("cityName",
					URLDecoder.decode("".equals(request.getParameter("cityName")) || null == request.getParameter("cityName") ? "" : request.getParameter("cityName"), "UTF-8"));
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		} catch (Exception e) {
			logger.error("错误日志", e);
		} finally {
			model.put("cityId", cityIdParam);
			resJson.put("houseInfoList", houseInfoList);
			response.setContentType("text/html,charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			try {
				response.getWriter().write(resJson.toString());
				response.getWriter().flush();
			} catch (IOException e) {
				logger.error("错误日志", e);
			}
		}
	}

	// 查询城市房源列表
	@RequestMapping(value = "/listall.htm", method = { RequestMethod.GET })
	public String getListAll(HttpServletRequest request, HttpServletResponse response, Model model) throws CasServiceException {

		UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
		List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
		List<String> description = new ArrayList<String>();
		List<Integer> collect = new ArrayList<Integer>();
		try {
			// 若登陆状态获取用户收藏记录
			if (loginUser != null) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("uid", loginUser.getUid());
				JSONObject json = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getCollects");
				net.sf.json.JSONObject dataObj = net.sf.json.JSONObject.fromObject(json.get("data"));
				JSONArray array = dataObj.getJSONArray("collects");
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject tempObj = array.getJSONObject(i);
					collect.add(tempObj.getInt("houseId"));
				}
			}
			Map<String, Object> postData = new HashMap<String, Object>();
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHouses");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			model.addAttribute("msg", msg);
			model.addAttribute("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("houses").toString());
					for (int j = 0; j < arrayShowDetail.size(); j++) {
						HouseInfo houseInfo = new HouseInfo();
						net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
						int houseId = jsonObject1.getInt("houseId");
						houseInfo.setHouseId(jsonObject1.getInt("houseId"));
						HouseInfo tempHouse = new HouseInfo();
						tempHouse = gerneralMethod.getHouseInfo(houseId);
						houseInfo.setDiscount(tempHouse.getDiscount());
						houseInfo.setTotalRoomNum(tempHouse.getTotalRoomNum());
						houseInfo.setIntroductionImg(tempHouse.getIntroductionImg());
						houseInfo.setCityId(jsonObject1.getInt("cityId"));
						houseInfo.setFreezeAmt(jsonObject1.getInt("freezeAmt"));
						houseInfo.setTotalAmt(jsonObject1.getInt("totalAmt"));
						BigDecimal memTotalAmt = new BigDecimal(jsonObject1.getInt("memTotalAmt"));
						houseInfo.setMemTotalAmt(memTotalAmt.intValue());
						houseInfo.setDiscontMax(memTotalAmt.subtract(memTotalAmt.multiply(new BigDecimal(appProperties.getDiscontParam()))).setScale(0, BigDecimal.ROUND_HALF_DOWN)
								.toPlainString());
						List<Integer> list = (List<Integer>) jsonObject1.get("mark");
						for (int a : list) {
							description.add(ApplicationConfig.miaoshuMap.get(a));
						}
						houseInfo.setMemFreezeAmt(jsonObject1.getInt("memFreezeAmt"));
						net.sf.json.JSONObject temp = net.sf.json.JSONObject.fromObject(jsonObject1.getString("summaryInfo"));
						houseInfo.setHouseName(temp.getString("houseName"));
						if ("2".equals(temp.getString("status")))
							continue;
						houseInfo.setRoom(String.valueOf(jsonObject1.getInt("room")));
						List<Integer> list1 = (List<Integer>) jsonObject1.get("flag");
						houseInfo.setFlag(list1.get(0));
						if (loginUser != null && collect.contains(houseId))
							houseInfo.setCollectFlag(1);
						else
							houseInfo.setCollectFlag(0);
						houseInfoList.add(houseInfo);
					}
				}
			} else {
				JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
			}

		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		} catch (Exception e) {
			logger.error("错误日志", e);
		}
		model.addAttribute("houseInfoList", houseInfoList);
		
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			User userInfo = VfSsoUser.get();
			if (userInfo != null) {
				model.addAttribute("isLogin", "1");
			}
		}
		return Contants.URL_PREFIX + "/house/listallnew";
	}
	
	@RequestMapping(value = "/getCityByName.htm", method = { RequestMethod.POST })
	@ResponseBody
	public RestResponse getCityByName(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws UnsupportedEncodingException {
		String name = request.getParameter("name");
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", name);
		try {
			JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot()+"/customer","getCity");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
    		Object object = result.get("data");
    		if ("0".equals(code)) {
    			net.sf.json.JSONObject Object = net.sf.json.JSONObject.fromObject(object);
    			List<City> cityList = new ArrayList<City>();
    			cityList = (List)JSONArray.toList(Object.getJSONArray("cityList"), City.class);
    			map.put("cityId", cityList.get(0).getCityId());
			}
       		map.put("msg",msg);
       		map.put("code", code);
       		restP.setSuccess(true);
			restP.setData(map);
		} catch (IOException e) {
			map.put("code", 2);
			map.put("msg", "系统繁忙，查询失败.");
			restP.setSuccess(false);
			logger.error("错误日志", e);
		}
		return restP;
	}
	
	@RequestMapping(value = "/getAllCitys.htm", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public RestResponse getAllCitys(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();	
		try {
			JSONObject result=	HttpClientPostMethod.httpDataReqUrl(postData, appProperties.getRequestRoot(), "getCities");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
    		if ("0".equals(code)) {
    			
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
					for (int j = 0; j < arrayShowDetail.size(); j++) {
						CityInfo cityInfo = new CityInfo();
						net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
						String cityName = jsonObject1.get("cityName").toString();
						String pinyin = jsonObject1.get("pinyin").toString();
						
						cityInfo.setCityName(cityName);
						cityInfo.setPinyin(pinyin);
						cityInfoList.add(cityInfo);
					}
				}
			}
    		map.put("list", cityInfoList);    		
       		map.put("msg",msg);
       		map.put("code", code);
       		restP.setSuccess(true);
			restP.setData(map);
		} catch (IOException e) {
			map.put("code", 2);
			map.put("msg", "系统繁忙，查询失败.");
			restP.setSuccess(false);
			logger.error("错误日志", e);
		}
		return restP;
	}
	
	   // 查询热门城市
		@RequestMapping(value = "/house/getHotCities", method = { RequestMethod.POST,RequestMethod.GET})
		@ResponseBody
		public ResponseOut<HotCityVo> getHotCitiy(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws CasServiceException {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> postData = new HashMap<String, Object>();
			// 显示热门城市//0非热门+非推荐，1热门，2推荐，10热门+推荐
			HotCityVo hotCityVo = new HotCityVo();
			List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
			List<CityInfo> cityInfoList1 = new ArrayList<CityInfo>();
			List<String> nameList = new ArrayList<String>();
			Map<String, CityInfo> beanMap = new HashMap<String, CityInfo>();
			Set<String> letterSet = new TreeSet<String>();
			try {
				JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCities");
				String msg = result.get("msg").toString();
				String code = result.getString("code");
				map.put("msg", msg);
				map.put("code", code);
				if ("0".equals(code)) {
					JSONArray array = JSONArray.fromObject(result.get("data"));
					for (int i = 0; i < array.size(); i++) {
						net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
						JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
						for (int j = 0; j < arrayShowDetail.size(); j++) {
							CityInfo cityInfo = new CityInfo();
							net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
							cityInfo.setCityId(jsonObject1.getInt("cityId"));
							String cityName = jsonObject1.getString("cityName");
							cityInfo.setCityName(cityName);
							nameList.add(cityName);
							cityInfo.setFirstLetter(LetterUtil.getFirstLetter(cityName).toUpperCase());
							cityInfo.setType(jsonObject1.getInt("type"));
							cityInfo.setProvId(jsonObject1.getInt("provId"));
							beanMap.put(cityName, cityInfo);
						}
					}
					//Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
					Object[] temp = nameList.toArray();
					//Arrays.sort(temp, com);
					int a=0;
					for (Object str : temp) {
						if(beanMap.get(str).getType()==1||beanMap.get(str).getType()==10){
							if(a<8){
								cityInfoList1.add(beanMap.get(str));
								a++;
							}
						}
						cityInfoList.add(beanMap.get(str));
						letterSet.add(beanMap.get(str).getFirstLetter());
					}
					hotCityVo.setHotList(cityInfoList1);
					hotCityVo.setAllList(cityInfoList);
				}else{
					return new ResponseOut<HotCityVo>(Contants.FAILED, msg);
				}
			
			} catch (Exception e) {
				logger.error("错误日志  查询热门城市接口getHotCitiy：", e);
			} 	
			
			return new ResponseOut<HotCityVo>(Contants.SUCCESSED, "", hotCityVo);
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

	public static void main(String[] args) {
		/*
		 * // TODO Auto-generated method stub Comparator<Object>
		 * com=Collator.getInstance(java.util.Locale.CHINA); String[]
		 * newArray={"中山","汕头","广州","安庆","阳江","南京","武汉","北京","安阳","北方"};
		 * Arrays.sort(newArray,com); for(String i:newArray){
		 * System.out.print(i+"  "); }
		 */
		int continueAmt = 150;
		double discount = 0.97;
		System.out.println(new BigDecimal(continueAmt * discount).setScale(0, BigDecimal.ROUND_HALF_DOWN).toPlainString());
		// System.out.println(new BigDecimal(continueAmt*discount).intValue());
	}
}
