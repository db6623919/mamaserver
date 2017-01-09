package com.mmzb.house.web.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
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

import com.caucho.hessian.client.HessianProxyFactory;
import com.mama.server.common.entity.n99.HousesVo;
import com.mama.server.common.entity.n99.N99HousePagerVo;
import com.mama.server.common.entity.n99.N99Info;
import com.mama.server.common.entity.n99.SpecialHousesPagerEntity;
import com.mama.server.common.entity.n99.Tabs;
import com.mama.server.common.util.Page;
import com.mama.server.common.util.PartnershipEnum;
import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.banner.facade.BannerFacade;
import com.mmzb.banner.facade.entity.BannerEntity;
import com.mmzb.banner.facade.entity.BannerQueryRequest;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.CityInfo;
import com.mmzb.house.web.action.dto.HouseInfo;
import com.mmzb.house.web.action.util.JsonGeneratorUtils;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.core.common.vo.Response;
import com.mmzb.house.web.core.common.vo.ResponseOut;
import com.mmzb.house.web.model.HouseSearchPo;
import com.mmzb.house.web.model.houses.HouseDetailVo;
import com.mmzb.house.web.model.houses.HouseShopVo;
import com.mmzb.house.web.model.houses.SpecialsHouseVo;
import com.mmzb.house.web.util.CS;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;

@Controller
public class IndexAction extends BaseAction {

	@Resource(name = "appProperties")
	private AppProperties appProperties;
	private Logger logger = LoggerFactory.getLogger(IndexAction.class);

	@Autowired
	public GerneralMethod gerneralMethod;

	@RequestMapping(value = "/index.htm")
	public String toIndex(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		logger.info("===============【即将跳转到首页】============================");
		
		//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
		Map<String, Object> postData = new HashMap<String, Object>();
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCities");
			if ("0".equals(result.getString("code"))) {
				JSONObject jsonData = result.getJSONObject("data");
				com.meidusa.fastjson.JSONArray cityJsonArray = jsonData.getJSONArray("cities");
				for (int i = 0; i < cityJsonArray.size(); i++) {
					CityInfo cityInfo = new CityInfo();
					JSONObject cityJsonObject = cityJsonArray.getJSONObject(i);
					cityInfo.setCityName(cityJsonObject.getString("cityName"));
					cityInfo.setRoomNum(cityJsonObject.getString("roomNum"));
					cityInfo.setHouseNum(cityJsonObject.getString("houseNum"));
					cityInfo.setCityId(cityJsonObject.getInteger("cityId"));
					String showDetail = cityJsonObject.getString("showDetail");
					if (StringUtils.isNotBlank(showDetail)) {
						JSONObject showDetailJsonObject = JSONObject.parseObject(showDetail);
						cityInfo.setImgUrl(showDetailJsonObject.getString("cityImg"));
						cityInfo.setDescription(showDetailJsonObject.getString("description"));
					}
					cityInfoList.add(cityInfo);
				}
			}
		} catch (Exception e) {
			logger.error("错误日志", e);
		}
		String travelAddress = appProperties.getTravelAddress();
		
		String token = getToken(request);
		List<BannerEntity> banners = getBanners();
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			User userInfo = VfSsoUser.get();
			if (userInfo != null) {
				for (BannerEntity bannerDomain : banners) {
					if (travelAddress.equals(bannerDomain.getLink()))
						bannerDomain.setImg_url(travelAddress + "?token=" + token);
				}
				travelAddress += ("?token=" + token);
				model.addAttribute("isLogin", "1");

			}
		} 		

		model.addAttribute("citys", JSON.toJSONString(cityInfoList));
		model.addAttribute("registerAddress", appProperties.getRegisterAddress());
		model.addAttribute("banners", banners);
		model.addAttribute("travelAddress", travelAddress);
		model.addAttribute("imgurl", imgurl);
		
		//首页特价房源
		Map<String, Object> param = new HashMap<String, Object>();
		JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getIndexSpecialsHouses");
		JSONObject dataObj = houseDetailJson.getJSONObject("data");
		JSONObject data = dataObj.getJSONObject("specialsHouseVo");
		SpecialsHouseVo specialsHouseVo = JSON.toJavaObject(data, SpecialsHouseVo.class);
		specialsHouseVo = consoHouseToSpecial(specialsHouseVo);
		
		model.addAttribute("specialsHouse", specialsHouseVo);
		return Contants.URL_PREFIX + "/index_v3.0";
	}
	
	/**
	 * 团购抢房
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/groupList.htm", method = { RequestMethod.GET })
	public String groupHouseList(HttpServletRequest request, HttpServletResponse response, Model model) {
		return Contants.URL_PREFIX + "/house/groupList";
	}
	
	/*
	 * 特价列表
	 */
	@RequestMapping(value = "/specialsList.htm")
	public String specialsList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		logger.info("===============【即将跳转到specialsList 每日特价列表页面】============================");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("currentPage", 1);
		param.put("pageSize", Contants.PAGE_SIZE);
		
		JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getSpecialsHouses");
		
		JSONObject dataObj = houseDetailJson.getJSONObject("data");
		int itemCount = dataObj.getInteger("itemCount");
		com.meidusa.fastjson.JSONArray jsonArray = dataObj.getJSONArray("list");
		List<SpecialsHouseVo> list = consoHouseToSpecialList(jsonArray);
		
		model.addAttribute("list", list);
		model.addAttribute("pageNum", 1);;
		model.addAttribute("relatedInn", itemCount);
		
		logger.info("end to specialsList");
		return Contants.URL_PREFIX + "/house/special-house";
	}
	
	/**
     * 特价列表 下拉翻页
     */
    @RequestMapping(value = "/moreSpecialsList.htm")
    public ModelAndView moreSpecialsList(HttpServletRequest request, HttpServletResponse resp, ModelMap model) throws Exception {
    	logger.info("start to moreSpecialsList");
    	
		String current_page = request.getParameter("pageNum");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("currentPage", Integer.parseInt(current_page));
		param.put("pageSize", Contants.PAGE_SIZE);
		
		JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getSpecialsHouses");
		JSONObject dataObj = houseDetailJson.getJSONObject("data");
		com.meidusa.fastjson.JSONArray jsonArray = dataObj.getJSONArray("list");
		List<SpecialsHouseVo> list = consoHouseToSpecialList(jsonArray);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("pageNum", current_page);
		RestResponse restP = new RestResponse();
		restP.setData(data);
		
		JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);
		logger.info("end to moreSpecialsList");
        return null;
    }
    
	@RequestMapping(value = "/waiting.htm")
	public String toWaiting(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		logger.info("===============【即将跳转到默认在建页面】============================");
		model.addAttribute("type",request.getParameter("type"));
		return Contants.URL_PREFIX + "/waiting";
	}
	/**
	 * 游记
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/travels.htm")
	public String toTravels(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return Contants.URL_PREFIX + "/travels";
	}
	
	
	/**
	 *关于 
	 */
	@RequestMapping(value = "/about_us.htm")
	public String about_us(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		logger.info("===============【关于页面】============================");
		model.addAttribute("type",request.getParameter("type"));
		return Contants.URL_PREFIX + "/about_us";
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
    

	@RequestMapping(value = "/getCitys.htm")
	@ResponseBody
	public String getCitys(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("===============【即将跳转到首页】============================");
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
		Map<String, Object> postData = new HashMap<String, Object>();
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCities");
			if ("0".equals(result.getString("code"))) {
				JSONObject jsonData = result.getJSONObject("data");
				com.meidusa.fastjson.JSONArray cityJsonArray = jsonData.getJSONArray("cities");
				for (int i = 0; i < cityJsonArray.size(); i++) {
					CityInfo cityInfo = new CityInfo();
					JSONObject cityJsonObject = cityJsonArray.getJSONObject(i);
					cityInfo.setCityName(cityJsonObject.getString("cityName"));
					cityInfo.setRoomNum(cityJsonObject.getString("roomNum"));
					cityInfo.setHouseNum(cityJsonObject.getString("houseNum"));
					cityInfo.setCityId(cityJsonObject.getInteger("cityId"));
					String showDetail = cityJsonObject.getString("showDetail");
					if (StringUtils.isNotBlank(showDetail)) {
						JSONObject showDetailJsonObject = JSONObject.parseObject(showDetail);
						cityInfo.setImgUrl(showDetailJsonObject.getString("cityImg"));
						cityInfo.setDescription(showDetailJsonObject.getString("description"));
					}
					cityInfoList.add(cityInfo);
				}
			}
		} catch (Exception e) {
			logger.error("错误日志", e);
		}

		model.addAttribute("citys", cityInfoList);
		model.addAttribute("banners", this.getBanners());
		return Contants.URL_PREFIX + "/index";
	}

	@RequestMapping(value = "/indexJson.htm")
	public void toIndexJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> postData = new HashMap<String, Object>();
		net.sf.json.JSONObject resJson = new net.sf.json.JSONObject();
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
		try {
			postData.put("pageNum", Integer.parseInt(request.getParameter("page")));
			postData.put("pageCount", 3);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCities");
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
					JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
					for (int j = 0; j < arrayShowDetail.size(); j++) {
						CityInfo cityInfo = new CityInfo();
						net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
						cityInfo.setCityName(URLEncoder.encode(jsonObject1.getString("cityName"), "utf-8"));
						cityInfo.setRoomNum(jsonObject1.getString("roomNum"));
						cityInfo.setHouseNum(jsonObject1.getString("houseNum"));
						cityInfo.setCityId(jsonObject1.getInt("cityId"));
						String ShowDetail = jsonObject1.get("showDetail").toString();
						if (!"".equals(ShowDetail) && ShowDetail != null) {
							JSONObject objectShowDetail = JSONObject.parseObject(ShowDetail);
							cityInfo.setImgUrl(objectShowDetail.get("cityImg").toString());
							cityInfo.setDescription(URLEncoder.encode(objectShowDetail.getString("description"), "utf-8"));
						}
						cityInfoList.add(cityInfo);
					}
					if (cityInfoList.size() == 0)
						resJson.put("code", 1);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		} finally {
			resJson.put("cityInfoList", cityInfoList);
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

	/**
	 * 统计代码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/countPvUv.htm")
	public void Cs(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		final int siteId = 1256594870;
		final String imageDomain = "c.cnzz.com";
		try {
			String imageLocation = request.getScheme() + "://" + imageDomain + "/wapstat.php";
			String r = request.getHeader("referer") == null ? "" : request.getHeader("referer");
			String rnd = Integer.toString((int) (Math.random() * 0x7fffffff));
			String imageUrl = imageLocation + "?" + "siteid=" + siteId + "&r=" + URLEncoder.encode(r) + "&rnd=" + rnd;
			response.setContentType("text/html,charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(imageUrl);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
	}

	/**
	 * 
	 * @Title: getBanners @Description: 查询轮播图 @return @return List
	 *         <BannerEntity> 返回类型 @throws
	 */
	private List<BannerEntity> getBanners() {
		try {

			HessianProxyFactory factory = new HessianProxyFactory();
			BannerFacade facade = (BannerFacade) factory.create(BannerFacade.class, appProperties.getBannerUrl());
			BannerQueryRequest request = new BannerQueryRequest();
			//request.setBanners(appProperties.getBannerCount());
			request.setPlatform(appProperties.getHouse_platform());
			request.setSource(appProperties.getHouse_resoure());
			request.setTerminal(1);
			//BannerQueryResponse response = facade.queryBanner(request);
			//List<BannerEntity> list = response.getData();
			List<BannerEntity> list = facade.queryBanners(request);
			//return list == null ? new ArrayList<BannerEntity>() : list;
			return list == null ? new ArrayList<BannerEntity>() : list;
		} catch (Exception e) {
			logger.error("调用轮播图查询接口出现异常", e);
			return new ArrayList<BannerEntity>();
		}

	}

	@RequestMapping(value = "/toDailyActivity.htm")
	public ModelAndView toDailyActivity(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/daily-activity");
	}
	
	private SpecialsHouseVo consoHouseToSpecial(SpecialsHouseVo sHouseVo) throws Exception{
		//合作关系
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shopId", String.valueOf(sHouseVo.getShopId()));
		JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot()+"/customer", "getHouseShopByPar");
		String code = houseDetailJson.getString("code");
		if (Contants.CODE_SUCCESSED.equals(code)) {
			JSONObject dataObj = houseDetailJson.getJSONObject("data");
			JSONObject data = dataObj.getJSONObject("houseShop");
			HouseShopVo houseShopVo = JSON.toJavaObject(data, HouseShopVo.class);
			if (PartnershipEnum.MemberInn.getCode().equals(houseShopVo.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
			}else if (PartnershipEnum.Holding.getCode().equals(houseShopVo.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.Holding.getMessage());//控股
			}else if (PartnershipEnum.GuesthouseInn.getCode().equals(houseShopVo.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
			}else if (PartnershipEnum.DepthCooperation.getCode().equals(houseShopVo.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.DepthCooperation.getMessage());//深度合作
			}else {
				sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
			}
		}else {
			sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
		}		
		
		//折扣
		if (sHouseVo.getShopId()!=0&&sHouseVo.getMarket_price()>0) {
			float  discount = (float)sHouseVo.getMemTotalAmt()*10/(float)sHouseVo.getMarket_price();
			BigDecimal   b   =   new   BigDecimal(discount);  
			float   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();  
			sHouseVo.setDiscount(f1);
		}

		return sHouseVo;
	}
	
	private List<SpecialsHouseVo> consoHouseToSpecialList(com.meidusa.fastjson.JSONArray jsonArray) throws Exception{
		List<SpecialsHouseVo> list = new ArrayList<SpecialsHouseVo>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			SpecialsHouseVo specialsHouseVo = JSON.toJavaObject(jsonObject, SpecialsHouseVo.class);
			//合作关系
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("shopId", specialsHouseVo.getShopId());
			JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot()+"/customer", "getHouseShopByPar");
			String code = houseDetailJson.getString("code");
			if (Contants.CODE_SUCCESSED.equals(code)) {
				JSONObject dataObj = houseDetailJson.getJSONObject("data");
				JSONObject data = dataObj.getJSONObject("houseShop");
				HouseShopVo houseShopVo = JSON.toJavaObject(data, HouseShopVo.class);
				if (PartnershipEnum.MemberInn.getCode().equals(houseShopVo.getPartnership())) {
					specialsHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
				}else if (PartnershipEnum.Holding.getCode().equals(houseShopVo.getPartnership())) {
					specialsHouseVo.setPartnership(PartnershipEnum.Holding.getMessage());//控股
				}else if (PartnershipEnum.GuesthouseInn.getCode().equals(houseShopVo.getPartnership())) {
					specialsHouseVo.setPartnership(PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
				}else if (PartnershipEnum.DepthCooperation.getCode().equals(houseShopVo.getPartnership())) {
					specialsHouseVo.setPartnership(PartnershipEnum.DepthCooperation.getMessage());//深度合作
				}else {
					specialsHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
				}
			}else {
				specialsHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
			}
			
			
			if (specialsHouseVo.getMarket_price()>0) {
				float  discount = (float)specialsHouseVo.getMemTotalAmt()*10/(float)specialsHouseVo.getMarket_price();
				BigDecimal   b   =   new   BigDecimal(discount);  
				float   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();  
				specialsHouseVo.setDiscount(f1);
			}
			
			list.add(specialsHouseVo);
		}

		return list;
	}
	
	@RequestMapping(value = "/n99Info", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseOut<N99Info> n99Info(HttpServletRequest request, String accessToken){
		if (logger.isInfoEnabled())
		{
			logger.info("SpecialAction get the request to n99Info, param is {}");
		}
		
		ResponseOut<N99Info> response = new ResponseOut<N99Info>();
		
		N99Info n99Info = new N99Info();
		List<Tabs> list = new ArrayList<Tabs>();
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("currentPage", -1);
			param.put("pageSize", -1);
			Response<SpecialHousesPagerEntity> resp = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getSpecialsHouses",SpecialHousesPagerEntity.class);
			Set<String> n99set = getN99HouseList(resp.getData().getList());
			for (String str:n99set) {
				Tabs tabs = new Tabs();
				tabs.setTabName(str);
				if(str.substring(0, str.length()-2).equals("")){
					tabs.setTabId("0");
				}else {
					tabs.setTabId(str.substring(0, str.length()-2));
				}
				
				list.add(tabs);
			}
			Collections.sort(list, new TabIdComparator());

			n99Info.setTabs(list);	
			response.setCode(Contants.SUCCESSED);
			response.setMessage(resp.getMsg());
			response.setResult(n99Info);
		}
		catch (Exception e) {
			response.setCode(Contants.FAILED);
			response.setMessage(e.getMessage());
			logger.error("failed to get n99Info info", e);
		}
		return response;
	}
	
	   // 自定义比较器：按tabId  
	 static class TabIdComparator implements Comparator {  
	     public int compare(Object object1, Object object2) {// 实现接口中的方法  
	         Tabs p1 = (Tabs) object1; // 强制转换  
	         Tabs p2 = (Tabs) object2;  
	         return new Double(p1.getTabId()).compareTo(new Double(p2.getTabId()));  
	     }  
	 } 
 
	private Set<String> getN99HouseList(List<com.mama.server.common.entity.n99.SpecialsHouseVo> list) throws Exception{
		Set<String>  n99Set=new HashSet<String>();
		for (com.mama.server.common.entity.n99.SpecialsHouseVo specialsHouseVo:list) {
			if (specialsHouseVo.getMemTotalAmt()>=99) {
				String price = String.valueOf(specialsHouseVo.getMemTotalAmt());
				price = price.substring(price.length()-2, price.length());
				if (price.equals("99")) {
					n99Set.add(String.valueOf(specialsHouseVo.getMemTotalAmt()));
				}
				
			}
		}
		return n99Set;
	}
	
	@RequestMapping(value = "/n99HouseList", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseOut<N99HousePagerVo> n99HouseList(HttpServletRequest request, String accessToken){
		if (logger.isInfoEnabled())
		{
			logger.info("SpecialAction get the request to n99HouseList, param is {}", request.getParameter("tabId"));
		}
		
		ResponseOut<N99HousePagerVo> response = new ResponseOut<N99HousePagerVo>();
		String currentPage = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String priceRange = request.getParameter("tabId");//类型价位
		
		N99HousePagerVo n99HousePagerVo = new N99HousePagerVo();
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("currentPage", Integer.parseInt(currentPage));
			param.put("pageSize", Integer.parseInt(pageSize));
			param.put("priceRange", priceRange);
			Response<SpecialHousesPagerEntity> resp = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getRangeHouses",SpecialHousesPagerEntity.class);
			List<HousesVo> specialsHouseList = getSpecialList(resp.getData().getList());
			
			n99HousePagerVo.setN99Houses(specialsHouseList);
			
    			//设置分页信息
    			Page pager = new Page();
    			pager.setItemCount(resp.getData().getItemCount());
    			pager.setPageIndex(Integer.parseInt(currentPage));
    			pager.setPageSize(Integer.parseInt(pageSize));
    			n99HousePagerVo.setPager(pager);
    			
			response.setCode(Contants.SUCCESSED);
			response.setMessage(resp.getMsg());
			response.setResult(n99HousePagerVo);
		}
		catch (Exception e) {
			response.setCode(Contants.FAILED);
			response.setMessage(e.getMessage());
			logger.error("failed to get n99HouseList info", e);
		}
		return response;
	}
	
	private List<HousesVo> getSpecialList(List<com.mama.server.common.entity.n99.SpecialsHouseVo> list) throws Exception{
		List<HousesVo> housesVosList = new ArrayList<HousesVo>();
		for (com.mama.server.common.entity.n99.SpecialsHouseVo specialsHouseVo:list) {
			specialsHouseVo = consoHouse2Special(specialsHouseVo);
			HousesVo housesVo = conso2HousesVO(specialsHouseVo);
			housesVosList.add(housesVo);
		}
		return housesVosList;
	}
	
	private com.mama.server.common.entity.n99.SpecialsHouseVo consoHouse2Special(com.mama.server.common.entity.n99.SpecialsHouseVo sHouseVo) throws Exception{
		//合作关系
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shopId", String.valueOf(sHouseVo.getShopId()));
		JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot()+"/customer", "getHouseShopByPar");
		String code = houseDetailJson.getString("code");
		if (Contants.CODE_SUCCESSED.equals(code)) {
			JSONObject dataObj = houseDetailJson.getJSONObject("data");
			JSONObject data = dataObj.getJSONObject("houseShop");
			HouseShopVo houseShopVo = JSON.toJavaObject(data, HouseShopVo.class);
			if (PartnershipEnum.MemberInn.getCode().equals(houseShopVo.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
			}else if (PartnershipEnum.Holding.getCode().equals(houseShopVo.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.Holding.getMessage());//控股
			}else if (PartnershipEnum.GuesthouseInn.getCode().equals(houseShopVo.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
			}else if (PartnershipEnum.DepthCooperation.getCode().equals(houseShopVo.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.DepthCooperation.getMessage());//深度合作
			}else {
				sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
			}
		}else {
			sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
		}		
		
		//折扣
		if (sHouseVo.getShopId()!=0&&sHouseVo.getMarket_price()>0) {
			float  discount = (float)sHouseVo.getMemTotalAmt()*10/(float)sHouseVo.getMarket_price();
			BigDecimal   b   =   new   BigDecimal(discount);  
			float   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();  
			sHouseVo.setDiscount(f1);
		}

		return sHouseVo;
	}
	
	private HousesVo conso2HousesVO(com.mama.server.common.entity.n99.SpecialsHouseVo specialsHouseVo) {
		HousesVo housesVo = new HousesVo();
		housesVo.setHouseId(specialsHouseVo.getHouseId());
		housesVo.setPrice(String.valueOf(specialsHouseVo.getMemTotalAmt()));
		housesVo.setHouseName(specialsHouseVo.getHouseName());
		housesVo.setHouseUnit("起/晚");
		housesVo.setImgUrl(specialsHouseVo.getImageUrl());
		String market_price = "";
		if (specialsHouseVo.getMarket_price()>0) {
			market_price = String.valueOf(specialsHouseVo.getMarket_price());
		}
		housesVo.setMarketPrice(market_price);
		housesVo.setPrice(String.valueOf(specialsHouseVo.getMemTotalAmt()));
		housesVo.setSpecialTag(specialsHouseVo.getPartnership());
		housesVo.setDescription(specialsHouseVo.getRoom()+"室"+specialsHouseVo.getOffice()+"厅"+specialsHouseVo.getToilet()+"卫"+specialsHouseVo.getBeds()+"床 宜住"+specialsHouseVo.getPersonNum()+"人");
		String discount = "";
		if (specialsHouseVo.getDiscount()>0) {
			discount = String.valueOf(specialsHouseVo.getDiscount())+"折";
		}
		housesVo.setDiscountDescription(discount);
		
		return housesVo;
	}
	
}