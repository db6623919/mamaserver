package com.console.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.console.dto.ActivityEnroll;
import com.console.dto.BuildingsDto;
import com.console.dto.CityInfo;
import com.console.dto.CrowdProjectPo;
import com.console.dto.DevelopersDto;
import com.console.dto.HouseShop;
import com.console.dto.HouseTag;
import com.console.dto.ProvincesDto;
import com.console.dto.TradeArea;
import com.console.entity.TSysUser;
import com.console.framework.dao.Pagination;
import com.console.util.Constant;
import com.console.util.HttpClientPostMethod;
import com.console.vo.HouseInfo;
import com.meidusa.fastjson.JSONObject;

import net.sf.json.JSONArray;

/**
 * 扩展ActionSupport的泛型基类
 * 
 * @author yangy
 * @param
 * @date 2010-07-29
 */
public class BaseController extends AbstractController {

	protected Logger logger = Logger.getLogger(BaseController.class);

	/**
	 * 分页参数
	 */
	protected int current_page = 1;

	protected int getCurrent_page() {
		return current_page;
	}

	protected void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	/**
	 * 返回登录用户对象
	 */
	protected TSysUser getSessionUser(HttpServletRequest request) {
		return (TSysUser) request.getSession().getAttribute(Constant.SESSION_USERINFO);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	//分页查询
	protected List<BuildingsDto> getBuildings(Pagination pager,String buildingType) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		if (null != buildingType)
			postData.put("building_type", buildingType);
		
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
		
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getBuildings");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<BuildingsDto> cityInfoList = new ArrayList<BuildingsDto>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				
				int num = Integer.parseInt(jsonObject.get("num").toString());
				pager.setTotal_count(num);
				
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("buildings").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					BuildingsDto buildingInfo = new BuildingsDto();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer devId = Integer.valueOf(jsonObject1.get("devId").toString());
					Integer bldId = Integer.valueOf(jsonObject1.get("bldId").toString());
					Integer cityId = Integer.valueOf(jsonObject1.get("cityId").toString());
					Integer provId = Integer.valueOf(jsonObject1.get("provId").toString());
					String name = jsonObject1.get("name").toString();
					Integer type = Integer.valueOf(jsonObject1.get("type").toString());
					String mark = jsonObject1.get("mark").toString();

					buildingInfo.setBldId(bldId);
					buildingInfo.setCityId(cityId);
					buildingInfo.setDevId(devId);
					buildingInfo.setMark(mark);
					buildingInfo.setMark(mark);
					buildingInfo.setName(name);
					buildingInfo.setProvId(provId);
					buildingInfo.setType(type);
					buildingInfo.setBuildingType(jsonObject1.getString("buildingType"));
					cityInfoList.add(buildingInfo);
				}
			}
		}
		return cityInfoList;
	}
	
	protected List<BuildingsDto> getBuildings(String buildingType) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		if (null != buildingType)
			postData.put("building_type", buildingType);
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getBuildings");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<BuildingsDto> cityInfoList = new ArrayList<BuildingsDto>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("buildings").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					BuildingsDto buildingInfo = new BuildingsDto();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer devId = Integer.valueOf(jsonObject1.get("devId").toString());
					Integer bldId = Integer.valueOf(jsonObject1.get("bldId").toString());
					Integer cityId = Integer.valueOf(jsonObject1.get("cityId").toString());
					Integer provId = Integer.valueOf(jsonObject1.get("provId").toString());
					String name = jsonObject1.get("name").toString();
					Integer type = Integer.valueOf(jsonObject1.get("type").toString());
					String mark = jsonObject1.get("mark").toString();

					buildingInfo.setBldId(bldId);
					buildingInfo.setCityId(cityId);
					buildingInfo.setDevId(devId);
					buildingInfo.setMark(mark);
					buildingInfo.setMark(mark);
					buildingInfo.setName(name);
					buildingInfo.setProvId(provId);
					buildingInfo.setType(type);
					buildingInfo.setBuildingType(jsonObject1.getString("buildingType"));
					cityInfoList.add(buildingInfo);
				}
			}
		}
		return cityInfoList;
	}

	protected List<BuildingsDto> getBuildingsDetail(Integer devIds) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("bldId", devIds);
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getBuildingInfo");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<BuildingsDto> cityInfoList = new ArrayList<BuildingsDto>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject1 = array.getJSONObject(i);
				BuildingsDto buildingInfo = new BuildingsDto();
				Integer devId = Integer.valueOf(jsonObject1.get("devId").toString());
				Integer bldId = Integer.valueOf(jsonObject1.get("bldId").toString());
				Integer cityId = Integer.valueOf(jsonObject1.get("cityId").toString());
				Integer provId = Integer.valueOf(jsonObject1.get("provId").toString());
				String name = jsonObject1.get("name").toString();
				Integer type = Integer.valueOf(jsonObject1.get("type").toString());
				String mark = jsonObject1.get("mark").toString();
				
				Object object = jsonObject1.get("trade_area");
				String trade_area = "";
				if (null!=object) {
				   trade_area = jsonObject1.get("trade_area").toString();
				}
				

				buildingInfo.setBldId(bldId);
				buildingInfo.setCityId(cityId);
				buildingInfo.setDevId(devId);
				buildingInfo.setMark(mark);
				buildingInfo.setMark(mark);
				buildingInfo.setName(name);
				buildingInfo.setProvId(provId);
				buildingInfo.setType(type);
				buildingInfo.setTrade_area(trade_area);
				JSONArray arrayTelphoe = JSONArray.fromObject("[" + jsonObject1.get("showDetail") + "]");
				for (int k = 0; k < arrayTelphoe.size(); k++) {
					net.sf.json.JSONObject jsonObjectel = arrayTelphoe.getJSONObject(k);
					String telPhone = jsonObjectel.getString("telphone").toString();
					String description = jsonObjectel.getString("description").toString();
					String video = jsonObjectel.getString("video").toString();
					String bldImg = jsonObjectel.getString("bldImg").toString();
					String houseExplain = jsonObjectel.optString("houseExplain", "");
					String peripheralConfig = jsonObjectel.optString("peripheralConfig", "");
					buildingInfo.setPeripheralConfig(peripheralConfig);
					buildingInfo.setHouseExplain(houseExplain);
					buildingInfo.setTelphone(telPhone);
					buildingInfo.setDescription(description);
					buildingInfo.setVedio(video);
					buildingInfo.setBldImg(bldImg);
				}
				cityInfoList.add(buildingInfo);
			}
		}
		return cityInfoList;
	}

	protected BuildingsDto getBuildingsInfo(Integer devIds) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("bldId", devIds);
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getBuildingInfo");
		String code = result.getString("code");
		BuildingsDto buildingInfo = new BuildingsDto();
		if ("0".equals(code)) {
			net.sf.json.JSONObject jsonObject1 = net.sf.json.JSONObject.fromObject(result.get("data"));
			if (jsonObject1.containsKey("bldId"))
				buildingInfo.setBldId(jsonObject1.getInt("bldId"));
			if (jsonObject1.containsKey("devId"))
				buildingInfo.setDevId(jsonObject1.getInt("devId"));
			if (jsonObject1.containsKey("provId"))
				buildingInfo.setProvId(jsonObject1.getInt("provId"));
			if (jsonObject1.containsKey("cityId"))
				buildingInfo.setCityId(jsonObject1.getInt("cityId"));
			if (jsonObject1.containsKey("showDetail"))
				buildingInfo.setShowDetail(jsonObject1.getString("showDetail"));
			if (jsonObject1.containsKey("type"))
				buildingInfo.setType(jsonObject1.getInt("type"));
			if (jsonObject1.containsKey("mark"))
				buildingInfo.setMark(jsonObject1.getString("mark"));
			if (jsonObject1.containsKey("removed"))
				buildingInfo.setRemoved(jsonObject1.getInt("removed"));
			if (jsonObject1.containsKey("name"))
				buildingInfo.setName(jsonObject1.getString("name"));
			if (jsonObject1.containsKey("project_introduction"))
				buildingInfo.setProjectIntroduction(jsonObject1.getString("project_introduction"));
			if (jsonObject1.containsKey("unit_area"))
				buildingInfo.setUnitArea(jsonObject1.getString("unit_area"));
			if (jsonObject1.containsKey("built_area"))
				buildingInfo.setBuiltArea(new BigDecimal(jsonObject1.getString("built_area")));
			if (jsonObject1.containsKey("average_price"))
				buildingInfo.setAveragePrice(jsonObject1.getInt("average_price"));
			if (jsonObject1.containsKey("surrounding_scenic"))
				buildingInfo.setSurroundingScenic(jsonObject1.getString("surrounding_scenic"));
			if (jsonObject1.containsKey("property_type"))
				buildingInfo.setPropertyType(jsonObject1.getString("property_type"));
			if (jsonObject1.containsKey("decoration_standard"))
				buildingInfo.setDecorationStandard(jsonObject1.getString("decoration_standard"));
			if (jsonObject1.containsKey("built_address"))
				buildingInfo.setBuiltAddress(jsonObject1.getString("built_address"));
			if (jsonObject1.containsKey("open_date"))
				buildingInfo.setOpenDate(jsonObject1.getString("open_date"));
			if (jsonObject1.containsKey("purchase_discount"))
				buildingInfo.setPurchaseDiscount(jsonObject1.getString("purchase_discount"));
			if (jsonObject1.containsKey("consultant_phone"))
				buildingInfo.setConsultantPhone(jsonObject1.getString("consultant_phone"));
			if (jsonObject1.containsKey("develop_name"))
				buildingInfo.setDevelopName(jsonObject1.getString("develop_name"));
			if (jsonObject1.containsKey("launch_date"))
				buildingInfo.setLaunchDate(jsonObject1.getString("launch_date"));
			if (jsonObject1.containsKey("year_limit"))
				buildingInfo.setYearLimit(jsonObject1.getInt("year_limit"));
			if (jsonObject1.containsKey("built_status"))
				buildingInfo.setBuiltStatus(jsonObject1.getInt("built_status"));
			if (jsonObject1.containsKey("built_type"))
				buildingInfo.setBuiltType(jsonObject1.getString("built_type"));
			if (jsonObject1.containsKey("volume_rate"))
				buildingInfo.setVolumeRate(jsonObject1.getString("volume_rate"));
			if (jsonObject1.containsKey("greening_rate"))
				buildingInfo.setGreeningRate(jsonObject1.getString("greening_rate"));
			if (jsonObject1.containsKey("plan_households"))
				buildingInfo.setPlanHouseholds(jsonObject1.getString("plan_households"));
			if (jsonObject1.containsKey("plan_parking"))
				buildingInfo.setPlanParking(jsonObject1.getString("plan_parking"));
			if (jsonObject1.containsKey("presale_permit"))
				buildingInfo.setPresalePermit(jsonObject1.getString("presale_permit"));
			if (jsonObject1.containsKey("property_comp_name"))
				buildingInfo.setPropertyCompName(jsonObject1.getString("property_comp_name"));
			if (jsonObject1.containsKey("property_fee"))
				buildingInfo.setPropertyFee(jsonObject1.getString("property_fee"));
			if (jsonObject1.containsKey("hearting_mode"))
				buildingInfo.setHeartingMode(jsonObject1.getString("hearting_mode"));
			if (jsonObject1.containsKey("water_elec"))
				buildingInfo.setWaterElec(jsonObject1.getString("water_elec"));
			if (jsonObject1.containsKey("project_feature"))
				buildingInfo.setProjectFeature(jsonObject1.getString("project_feature"));
			if (jsonObject1.containsKey("scenic_resource"))
				buildingInfo.setScenicResource(jsonObject1.getString("scenic_resource"));
			if (jsonObject1.containsKey("humanity_matching"))
				buildingInfo.setHumanityMatching(jsonObject1.getString("humanity_matching"));
			if (jsonObject1.containsKey("education_matching"))
				buildingInfo.setEducationMatching(jsonObject1.getString("education_matching"));
			if (jsonObject1.containsKey("business_matching"))
				buildingInfo.setBusinessMatching(jsonObject1.getString("business_matching"));
			if (jsonObject1.containsKey("commerce_matching"))
				buildingInfo.setCommerceMatching(jsonObject1.getString("commerce_matching"));
			if (jsonObject1.containsKey("leisure_matching"))
				buildingInfo.setLeisureMatching(jsonObject1.getString("leisure_matching"));
			if (jsonObject1.containsKey("hospital_resource"))
				buildingInfo.setHospitalResource(jsonObject1.getString("hospital_resource"));
			if (jsonObject1.containsKey("exhibition_images"))
				buildingInfo.setExhibitionImages(jsonObject1.getString("exhibition_images"));
			if (jsonObject1.containsKey("apartment_images"))
				buildingInfo.setApartmentImages(jsonObject1.getString("apartment_images"));
			if (jsonObject1.containsKey("showings_images"))
				buildingInfo.setShowingsImages(jsonObject1.getString("showings_images"));
			if (jsonObject1.containsKey("showings_videos"))
				buildingInfo.setShowingsVideos(jsonObject1.getString("showings_videos"));
			if (jsonObject1.containsKey("building_type"))
				buildingInfo.setBuildingType(jsonObject1.getString("building_type"));
			if (jsonObject1.containsKey("index_image"))
				buildingInfo.setIndexImage(jsonObject1.getString("index_image"));
		}
		return buildingInfo;
	}

	protected List<CityInfo> getCities() throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("reversed", 1);
		JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, "getCities");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					CityInfo cityInfo = new CityInfo();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer cityId = Integer.parseInt(jsonObject1.get("cityId").toString());
					String cityName = jsonObject1.get("cityName").toString();
					Integer provId = Integer.valueOf(jsonObject1.get("provId").toString());
					String type = jsonObject1.getString("type").toString();
					JSONArray arrayTelphoe = JSONArray.fromObject("[" + jsonObject1.get("showDetail") + "]");
					if (!"[{}]".equals(arrayTelphoe.toString())) {
						for (int k = 0; k < arrayTelphoe.size(); k++) {
							net.sf.json.JSONObject jsonObjectel = arrayTelphoe.getJSONObject(k);
							String img = jsonObjectel.getString("cityImg").toString();
							String description = jsonObjectel.getString("description").toString();
	
							cityInfo.setImg(img);
							cityInfo.setDescription(description);
							
						}
					}
					cityInfo.setType(type);
					cityInfo.setProvId(provId);
					cityInfo.setCityId(cityId);
					cityInfo.setCityName(cityName);
					cityInfo.setSort(jsonObject1.getInt("sort"));
					cityInfoList.add(cityInfo);
				}
			}
		}
		return cityInfoList;
	}

	protected List<CityInfo> getCities(Pagination pager) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		
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
		
		JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, "getCities");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				
				int num = Integer.parseInt(jsonObject.get("num").toString());
				pager.setTotal_count(num);
				
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					CityInfo cityInfo = new CityInfo();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer cityId = Integer.parseInt(jsonObject1.get("cityId").toString());
					String cityName = jsonObject1.get("cityName").toString();
					Integer provId = Integer.valueOf(jsonObject1.get("provId").toString());
					String type = jsonObject1.getString("type").toString();
					String pinyin = "";
					if (null!=jsonObject1.getString("pinyin")) {
						pinyin = jsonObject1.getString("pinyin").toString();
					}
					 
					JSONArray arrayTelphoe = JSONArray.fromObject("[" + jsonObject1.get("showDetail") + "]");
					if (!"[{}]".equals(arrayTelphoe.toString())) {
						for (int k = 0; k < arrayTelphoe.size(); k++) {
							net.sf.json.JSONObject jsonObjectel = arrayTelphoe.getJSONObject(k);
							String img = jsonObjectel.getString("cityImg").toString();
							String description = jsonObjectel.getString("description").toString();
							cityInfo.setImg(img);
							cityInfo.setDescription(description);
						}
					}

					cityInfo.setType(type);
					
					cityInfo.setProvId(provId);
					cityInfo.setCityId(cityId);
					cityInfo.setCityName(cityName);
					cityInfo.setPinyin(pinyin);
					cityInfo.setSort(jsonObject1.getInt("sort"));
					cityInfoList.add(cityInfo);
				}
			}
		}
		return cityInfoList;
	}

	protected CityInfo getCitiesDetail(Map<String, Object> postData) throws Exception {
		JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, "getCities");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		CityInfo cityInfoList = new CityInfo();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer cityId = Integer.parseInt(jsonObject1.get("cityId").toString());
					String cityName = jsonObject1.get("cityName").toString();
					Integer provId = Integer.valueOf(jsonObject1.get("provId").toString());
					String type = jsonObject1.getString("type").toString();
					JSONArray arrayTelphoe = JSONArray.fromObject("[" + jsonObject1.get("showDetail") + "]");
					for (int k = 0; k < arrayTelphoe.size(); k++) {
						net.sf.json.JSONObject jsonObjectel = arrayTelphoe.getJSONObject(k);
						String img = jsonObjectel.getString("cityImg").toString();
						String description = jsonObjectel.getString("description").toString();

						cityInfoList.setImg(img);
						cityInfoList.setDescription(description);
						cityInfoList.setType(type);
					}
					cityInfoList.setProvId(provId);
					cityInfoList.setCityId(cityId);
					cityInfoList.setCityName(cityName);
				}
			}
		}
		return cityInfoList;
	}
	
	protected CityInfo getCitiesDetailNew(Map<String, Object> postData, Integer parcityId) throws Exception {
		JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, "getCities");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		CityInfo cityInfoList = new CityInfo();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cities").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer cityId = Integer.parseInt(jsonObject1.get("cityId").toString());
					String cityName = jsonObject1.get("cityName").toString();
					Integer provId = Integer.valueOf(jsonObject1.get("provId").toString());
					String type = jsonObject1.getString("type").toString();
					String pinyin = jsonObject1.getString("pinyin").toString();
					Integer sort = Integer.valueOf(jsonObject1.getString("sort").toString());
					if (cityId == parcityId) {
						JSONArray arrayTelphoe = JSONArray.fromObject("[" + jsonObject1.get("showDetail") + "]");
						for (int k = 0; k < arrayTelphoe.size(); k++) {
							net.sf.json.JSONObject jsonObjectel = arrayTelphoe.getJSONObject(k);
							String img = jsonObjectel.getString("cityImg").toString();
							String description = jsonObjectel.getString("description").toString();

							cityInfoList.setImg(img);
							cityInfoList.setDescription(description);
							cityInfoList.setType(type);
							cityInfoList.setSort(sort);
						}
						cityInfoList.setProvId(provId);
						cityInfoList.setCityId(cityId);
						cityInfoList.setCityName(cityName);
						cityInfoList.setPinyin(pinyin);
					}
				}
			}
		}
		return cityInfoList;
	}

	//分页查询省份信息
	protected List<ProvincesDto> getProvinces(Pagination pager) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
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
		JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, "getProvinces");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<ProvincesDto> proInfoList = new ArrayList<ProvincesDto>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("provinces").toString());
				
				int num = Integer.parseInt(jsonObject.get("num").toString());
				pager.setTotal_count(num);
				
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					ProvincesDto proInfo = new ProvincesDto();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer provId = Integer.parseInt(jsonObject1.get("provId").toString());
					String provName = jsonObject1.get("provName").toString();
					proInfo.setProvId(provId);
					proInfo.setProvName(provName);
					proInfoList.add(proInfo);
				}
			}
		}
		return proInfoList;
	}
	
	protected List<ProvincesDto> getProvinces() throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, "getProvinces");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<ProvincesDto> proInfoList = new ArrayList<ProvincesDto>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("provinces").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					ProvincesDto proInfo = new ProvincesDto();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer provId = Integer.parseInt(jsonObject1.get("provId").toString());
					String provName = jsonObject1.get("provName").toString();
					proInfo.setProvId(provId);
					proInfo.setProvName(provName);
					proInfoList.add(proInfo);
				}
			}
		}
		return proInfoList;
	}

	//分页
	protected List<DevelopersDto> getDevelopers(Pagination pager) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		
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
		
		JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, "getDevelopers");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<DevelopersDto> proInfoList = new ArrayList<DevelopersDto>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("developers").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					DevelopersDto devInfo = new DevelopersDto();
					String num = jsonObject.get("num").toString();
					pager.setTotal_count(Integer.parseInt(num));
					if (!"0".equals(num)) {
						Integer devId = Integer.parseInt(jsonObject1.get("devId").toString());
						String devName = jsonObject1.get("name").toString();
						String type = jsonObject1.get("type").toString();
						String mark = jsonObject1.get("mark").toString();
						devInfo.setDevId(devId);
						devInfo.setDevName(devName);
						devInfo.setMark(mark);
						devInfo.setType(type);
						JSONArray arrayTelphoe = JSONArray.fromObject("[" + jsonObject1.get("showDetail") + "]");
						for (int k = 0; k < arrayTelphoe.size(); k++) {
							net.sf.json.JSONObject jsonObjectel = arrayTelphoe.getJSONObject(k);
							String telPhone = jsonObjectel.getString("telphone").toString();
							String description = jsonObjectel.getString("description").toString();
							String video = jsonObjectel.getString("video").toString();
							String devImg = jsonObjectel.getString("devImg").toString();
							devInfo.setTelphone(telPhone);
							devInfo.setDescription(description);
							devInfo.setVideo(video);
							devInfo.setDevImg(devImg);
						}

						proInfoList.add(devInfo);
					}
				}
			}
		}
		return proInfoList;
	}
	
	protected List<DevelopersDto> getDevelopers() throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		JSONObject result = HttpClientPostMethod.httpDataReqUrl(postData, "getDevelopers");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<DevelopersDto> proInfoList = new ArrayList<DevelopersDto>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("developers").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					DevelopersDto devInfo = new DevelopersDto();
					String num = jsonObject.get("num").toString();
					if (!"0".equals(num)) {
						Integer devId = Integer.parseInt(jsonObject1.get("devId").toString());
						String devName = jsonObject1.get("name").toString();
						String type = jsonObject1.get("type").toString();
						String mark = jsonObject1.get("mark").toString();
						devInfo.setDevId(devId);
						devInfo.setDevName(devName);
						devInfo.setMark(mark);
						devInfo.setType(type);
						JSONArray arrayTelphoe = JSONArray.fromObject("[" + jsonObject1.get("showDetail") + "]");
						for (int k = 0; k < arrayTelphoe.size(); k++) {
							net.sf.json.JSONObject jsonObjectel = arrayTelphoe.getJSONObject(k);
							String telPhone = jsonObjectel.getString("telphone").toString();
							String description = jsonObjectel.getString("description").toString();
							String video = jsonObjectel.getString("video").toString();
							String devImg = jsonObjectel.getString("devImg").toString();
							devInfo.setTelphone(telPhone);
							devInfo.setDescription(description);
							devInfo.setVideo(video);
							devInfo.setDevImg(devImg);
						}

						proInfoList.add(devInfo);
					}
				}
			}
		}
		return proInfoList;
	}

	protected List<HouseInfo> getHouses(Map<String, Object> postMap) {
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		try {
			JSONObject result = HttpClientPostMethod.httpDataReqUrl(postMap, "getHouses");
			net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
			if (dataJson.getInt("num") == 0)
				return list;
			JSONArray houseArray = dataJson.getJSONArray("houses");
			for (int i = 0; i < houseArray.size(); i++) {
				HouseInfo houseInfo = new HouseInfo();
				net.sf.json.JSONObject house = houseArray.getJSONObject(i);
				net.sf.json.JSONObject temp = net.sf.json.JSONObject.fromObject(house.getString("summaryInfo"));
				houseInfo.setHouseId(house.getInt("houseId"));
				houseInfo.setBldId(house.getInt("bldId"));
				houseInfo.setHouseName(temp.getString("houseName"));
				houseInfo.setSort(house.getInt("sort"));
				list.add(houseInfo);
			}
		} catch (Exception e) {
			logger.error("查询房屋列表异常", e);
		}
		return list;
	}

	protected void updateCity(Map<String, Object> postMap) {
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postMap, "updateCity");
			if (0 == result.getIntValue("code"))
				logger.info("更新城市成功");
		} catch (Exception e) {
			logger.error("更新城市异常", e);
		}
	}

	protected void updateHouse(Map<String, Object> postMap) {
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postMap, "updateHouse");
			if (0 == result.getIntValue("code"))
				logger.info("更新房源成功");
		} catch (Exception e) {
			logger.error("更新房源异常", e);
		}
	}

	protected List<DevelopersDto> getDeveloperInfo(String devIdParam) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("devId", Integer.valueOf(devIdParam));
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getDeveloperInfo");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<DevelopersDto> proInfoList = new ArrayList<DevelopersDto>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				DevelopersDto devInfo = new DevelopersDto();
				Integer devId = Integer.parseInt(jsonObject.get("devId").toString());
				String devName = jsonObject.get("name").toString();
				String type = jsonObject.get("type").toString();
				String mark = jsonObject.get("mark").toString();
				devInfo.setDevId(devId);
				devInfo.setDevName(devName);
				devInfo.setMark(mark);
				devInfo.setType(type);
				JSONArray arrayTelphoe = JSONArray.fromObject("[" + jsonObject.get("showDetail") + "]");
				for (int k = 0; k < arrayTelphoe.size(); k++) {
					net.sf.json.JSONObject jsonObjectel = arrayTelphoe.getJSONObject(k);
					String telPhone = jsonObjectel.getString("telphone").toString();
					String description = jsonObjectel.getString("description").toString();
					String video = jsonObjectel.getString("video").toString();
					String devImg = jsonObjectel.getString("devImg").toString();
					devInfo.setTelphone(telPhone);
					devInfo.setDescription(description);
					devInfo.setVideo(video);
					devInfo.setDevImg(devImg);
				}

				proInfoList.add(devInfo);
			}

		}
		return proInfoList;
	}

	public void saveImg(HttpServletRequest request, String fileURI) throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		String rootPath = request.getRealPath("/WEB-INF");
		// 临时存储路径
		File tempFile = new File(rootPath + "/Temp");
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		if (isMultipart) {
			try {
				// 创建磁盘工厂，利用构造器实现内存数据储存量和临时储存路径
				DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 4, tempFile);
				// 设置最多只允许在内存中存储的数据,单位:字节
				// factory.setSizeThreshold(4096);
				// 设置文件临时存储路径
				// factory.setRepository(new File("D:\\Temp"));
				// 产生一新的文件上传处理程式
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 设置路径、文件名的字符集
				upload.setHeaderEncoding("UTF-8");
				// 设置允许用户上传文件大小,单位:字节
				upload.setSizeMax(1024 * 1024 * 100);
				// 解析请求，开始读取数据
				// Iterator<FileItem> iter = (Iterator<FileItem>)
				// upload.getItemIterator(request);
				// 得到所有的表单域，它们目前都被当作FileItem
				List<FileItem> fileItems = upload.parseRequest(request);
				// 依次处理请求
				Iterator<FileItem> iter = fileItems.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						// 如果item是正常的表单域
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
						System.out.println("表单域名为:" + name + "表单域值为:" + value);
					} else {
						// 如果item是文件上传表单域
						// 获得文件名及路径
						String fileName = item.getName();
						if (fileName != null) {
							// 如果文件存在则上传
							File fullFile = new File(item.getName());
							if (!fullFile.exists()) {
								// 真实路径
								String realSavePath = makeDir(rootPath + "/img");
								// 判断是否存在，不存在，则创建
								if (realSavePath == null) {
									System.out.println("文件目录创建失败！");
								}
								File fileOnServer = new File(realSavePath + "/" + fullFile.getName());
								item.write(fileOnServer);

								System.out.println("文件" + fileOnServer.getName() + "上传成功");
							}
						}

					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();

			}
		}

	}

	private String makeDir(String savePath) {

		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sf.format(new Date());
		String saveDirectory = savePath + "/" + dateStr;
		File file = new File(saveDirectory);
		if (!file.exists()) {
			if (file.mkdirs()) {
				return saveDirectory;
			} else {
				return null;
			}
		} else {
			return saveDirectory;
		}

	}
	
	protected List<BuildingsDto> getBuildings() throws Exception{
		Map<String, Object> postData=new HashMap<String, Object>();
		JSONObject result=	HttpClientPostMethod.httpCustReqUrl(postData,"getBuildings");
		String msg=result.get("msg").toString();
		String code=result.getString("code");
		List<BuildingsDto> cityInfoList=new ArrayList<BuildingsDto>();
          if("0".equals(code)){
   			
   			JSONArray array = JSONArray.fromObject(result.get("data")); 
       		for(int i = 0; i < array.size(); i++){
       			net.sf.json.JSONObject jsonObject =array.getJSONObject(i);
       			JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("buildings").toString());
       			for(int j = 0; j < arrayShowDetail.size(); j++){
       				BuildingsDto buildingInfo=new BuildingsDto();
	       			net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
	       			Integer devId= Integer.valueOf(jsonObject1.get("devId").toString());
	       			Integer bldId= Integer.valueOf(jsonObject1.get("bldId").toString());
	       			Integer cityId=Integer.valueOf( jsonObject1.get("cityId").toString());
	       			Integer provId= Integer.valueOf(jsonObject1.get("provId").toString());
	    	    	String name= jsonObject1.get("name").toString();
	    	    	Integer type= Integer.valueOf(jsonObject1.get("type").toString());
	    	    	String mark= jsonObject1.get("mark").toString();
	    	    	
	    	    	buildingInfo.setBldId(bldId);
	    	    	buildingInfo.setCityId(cityId);
	    	    	buildingInfo.setDevId(devId);
	    	    	buildingInfo.setMark(mark);
	    	    	buildingInfo.setMark(mark);
	    	    	buildingInfo.setName(name);
	    	    	buildingInfo.setProvId(provId);
	    	    	buildingInfo.setType(type);
	    	    	cityInfoList.add(buildingInfo);
       			}
       		}
          }
       		return cityInfoList;
	}
	
	/**
	 * 房源标签
	 * @return
	 * @throws Exception
	 */
	protected List<HouseTag> getHouseTags(Pagination pager) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		if (0!=pager.getPage_size()) {
			postData.put("limit", "limit");
		}
		if (0==pager.getCurrent_page()) {
			postData.put("start", pager.getCurrent_page()*pager.getPage_size());
			postData.put("end", pager.getPage_size());
		}else {
			postData.put("start", (pager.getCurrent_page()-1)*pager.getPage_size());
			postData.put("end", pager.getPage_size());
		}
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getHouseTags");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<HouseTag> list = new ArrayList<HouseTag>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				int num = Integer.parseInt(jsonObject.get("num").toString());
				pager.setTotal_count(num);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					HouseTag houseTag = new HouseTag();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer id = Integer.parseInt(jsonObject1.get("id").toString());
					Object object = jsonObject1.get("isDispayOnSearch");
					Integer isDispayOnSearch = -1;
					if (null != object ) {
						 isDispayOnSearch = Integer.parseInt(jsonObject1.get("isDispayOnSearch").toString());
					}
					
					String name = jsonObject1.get("name").toString();
					houseTag.setId(id);
					houseTag.setName(name);
					houseTag.setIsDispayOnSearch(isDispayOnSearch);
					list.add(houseTag);
				}
			}
		}
		return list;
	}
	
	/**
	 * 按城市ID查询所有商圈
	 */
	protected List<TradeArea> getTradeArea(int cityId) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("cityId",String.valueOf(cityId));
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getTradeAreaByPar");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<TradeArea> list = new ArrayList<TradeArea>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					TradeArea tradeArea = new TradeArea();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer id = Integer.parseInt(jsonObject1.get("id").toString());
					String name = jsonObject1.get("name").toString();
					Integer sort = Integer.parseInt(jsonObject1.get("sort").toString());
					tradeArea.setId(id);
					tradeArea.setCityId(cityId);
					tradeArea.setName(name);
					tradeArea.setSort(sort);
					list.add(tradeArea);
				}
			}
		}
		return list;
	}
	
	protected TradeArea getTradeAreaById(Pagination pager,int id) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
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
		postData.put("id", id);
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getTradeAreaByPar");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		TradeArea tradeArea = new TradeArea();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(0);
				Integer cityId = Integer.parseInt(jsonObject1.get("cityId").toString());
				String name = jsonObject1.get("name").toString();
				Integer sort = Integer.parseInt(jsonObject1.get("sort").toString());
				tradeArea.setId(id);
				tradeArea.setCityId(cityId);
				tradeArea.setName(name);
				tradeArea.setSort(sort);
					
			}
		}
		return tradeArea;
	}
	
	/**
	 * 商圈
	 * @return
	 * @throws Exception
	 */
	protected List<TradeArea> getTradeArea(Pagination pager) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
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
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getTradeArea");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<TradeArea> list = new ArrayList<TradeArea>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				int num = Integer.parseInt(jsonObject.get("num").toString());
				pager.setTotal_count(num);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					TradeArea tradeArea = new TradeArea();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer id = Integer.parseInt(jsonObject1.get("id").toString());
					Integer cityId = Integer.parseInt(jsonObject1.get("cityId").toString());
					String name = jsonObject1.get("name").toString();
					Integer sort = Integer.parseInt(jsonObject1.get("sort").toString());
					tradeArea.setId(id);
					tradeArea.setCityId(cityId);
					tradeArea.setName(name);
					tradeArea.setSort(sort);
					list.add(tradeArea);
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询所有房源标签
	 */
	protected List<HouseTag> getHouseTags() throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getHouseTags");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<HouseTag> list = new ArrayList<HouseTag>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					HouseTag houseTag = new HouseTag();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer id = Integer.parseInt(jsonObject1.get("id").toString());
					String name = jsonObject1.get("name").toString();
					houseTag.setId(id);
					houseTag.setName(name);
					list.add(houseTag);
				}
			}
		}
		return list;
	}
	
	/**
	 * 按ID查询标签
	 * @param pager
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected HouseTag getHouseTagById(int id) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("id", id);
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getHouseTagById");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		HouseTag houseTag = new HouseTag();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(0);
				String name = jsonObject1.get("name").toString();
				int isDispayOnSearch = jsonObject1.getInt("isDispayOnSearch");
				houseTag.setId(id);
				houseTag.setName(name);
				houseTag.setIsDispayOnSearch(isDispayOnSearch);
			}
		}
		return houseTag;
	}
	
	/**
	 * 店铺
	 * @return
	 * @throws Exception
	 */
	protected List<HouseShop> getHouseShop(Pagination pager) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		if (null!=pager && !"".equals(pager)) {
			if (0!=pager.getPage_size()) {
				postData.put("limit", "limit");
			}
			if (0==pager.getCurrent_page()) {
				postData.put("start", pager.getCurrent_page()*pager.getPage_size());
				postData.put("end", pager.getPage_size());
			}else {
				postData.put("start", (pager.getCurrent_page()-1)*pager.getPage_size());
				postData.put("end", pager.getPage_size());
			}
		}
		
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getHouseShop");
		String msg = result.get("msg").toString();
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
					String shopName = "";
					if (jsonObject1.containsKey("shopName")) {
						shopName = jsonObject1.get("shopName").toString();
					}
					String shopDesc = "";
					if (jsonObject1.containsKey("shopDesc")) {
						shopDesc = jsonObject1.get("shopDesc").toString();
					}
					String bossImage = "";
					if (jsonObject1.containsKey("bossImage")) {
						bossImage = jsonObject1.get("bossImage").toString();
					}
					String bossPhone = "";
					if (jsonObject1.containsKey("bossPhone")) {
						bossPhone = jsonObject1.get("bossPhone").toString();
					}
					String bossWeChat = "";
					if (jsonObject1.containsKey("bossWeChat")) {
						bossWeChat = jsonObject1.get("bossWeChat").toString();
					}
					String partnership = "";
					if (jsonObject1.containsKey("partnership")) {
						partnership = jsonObject1.get("partnership").toString();
					}
					String createTime = jsonObject1.get("createTime").toString();
					
					String cityName = "";
					if (jsonObject1.containsKey("cityName")) {
						cityName = jsonObject1.get("cityName").toString(); //城市名称
					}
					int recommended_status = 0;
					if (jsonObject1.containsKey("recommended_status")) {
						recommended_status = jsonObject1.getInt("recommended_status");
					}
					
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
					houseShop.setCreateTime(createTime);
					houseShop.setCityName(cityName);
					houseShop.setBossName(bossName);
					houseShop.setLevel(levelName);
					houseShop.setTypeName(typeName);
					houseShop.setPartnership(partnership);
					houseShop.setRecommended_status(String.valueOf(recommended_status));
					list.add(houseShop);
				}
			}
		}
		return list;
	}
	
	/**
	 *活动报名
	 */
	protected List<ActivityEnroll> getActivityEnrolls(Map<String, Object> postData,Pagination pager,ModelAndView mv) throws Exception {
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
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getActivityEnrolls");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		List<ActivityEnroll> list = new ArrayList<ActivityEnroll>();
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				int num = Integer.parseInt(jsonObject.get("num").toString());
				int NoConfirmNum = jsonObject.getInt("NoConfirmNum");
				mv.addObject("NoConfirmNum", NoConfirmNum);
				int ConfirmNum = jsonObject.getInt("ConfirmNum");
				mv.addObject("ConfirmNum", ConfirmNum);
				
				pager.setTotal_count(num);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
                    ActivityEnroll activityEnroll = new ActivityEnroll();
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
					Integer id = Integer.parseInt(jsonObject1.get("id").toString());
					String name = jsonObject1.getString("name");
					String phone = jsonObject1.getString("phone");
					String weChat = jsonObject1.getString("weChat");
					String age = jsonObject1.getString("age");
					int sex = jsonObject1.getInt("sex");
					int educated = jsonObject1.getInt("educated");
					String enroll_time = jsonObject1.getString("enroll_time");
					int status = jsonObject1.getInt("status");
					activityEnroll.setId(id);
					activityEnroll.setName(name);
					activityEnroll.setPhone(phone);
					activityEnroll.setWeChat(weChat);
					activityEnroll.setAge(age);
					activityEnroll.setSex(sex);
					activityEnroll.setEducated(educated);
					activityEnroll.setEnroll_time(enroll_time);
					activityEnroll.setStatus(status);
					list.add(activityEnroll);
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询店铺byID
	 * @param pager
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected HouseShop getHouseShopById(Pagination pager,int id) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
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
		postData.put("id", id);
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getHouseShop");
		String msg = result.get("msg").toString();
		String code = result.getString("code");
		HouseShop houseShop = new HouseShop();
		
		
		if ("0".equals(code)) {

			JSONArray array = JSONArray.fromObject(result.get("data"));
			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
				int num = Integer.parseInt(jsonObject.get("num").toString());
				pager.setTotal_count(num);
				JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("list").toString());
				for (int j = 0; j < arrayShowDetail.size(); j++) {
					net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
//					Integer id = Integer.parseInt(jsonObject1.get("id").toString());
					String shopName = "";
					if (jsonObject1.containsKey("shopName")) {
						shopName = jsonObject1.get("shopName").toString();
					}
					
					String shopDesc = "";
					if (jsonObject1.containsKey("shopDesc")) {
						shopDesc = jsonObject1.get("shopDesc").toString();
					}
					String bossImage = "";
					if (jsonObject1.containsKey("bossImage")) {
						bossImage = jsonObject1.get("bossImage").toString();
					}
					String bossPhone = "";
					if (jsonObject1.containsKey("bossPhone")) {
						bossPhone = jsonObject1.get("bossPhone").toString();
					}
					String bossWeChat = "";
					if (jsonObject1.containsKey("bossWeChat")) {
						bossWeChat = jsonObject1.get("bossWeChat").toString();
					}
					String cityName = "";
					if (jsonObject1.containsKey("cityName")) {
						cityName = jsonObject1.get("cityName").toString(); //城市名称
					}
					String imgUrl = "";
					if (jsonObject1.containsKey("imgUrl")) {
						imgUrl = jsonObject1.getString("imgUrl"); //客栈图片
					}
					String level = "";
					if (jsonObject1.containsKey("level")) {
						level = jsonObject1.getString("level"); //等级
					}
					String type = "";
					if (jsonObject1.containsKey("type")) {
						type = jsonObject1.getString("type"); //类型
					}
					String bossName = "";
					if (jsonObject1.containsKey("bossName")) {
						bossName = jsonObject1.getString("bossName"); //boss姓名
					}
					String partnership = "";
					if (jsonObject1.containsKey("partnership")) {
						partnership = jsonObject1.getString("partnership"); //客栈关系
					}
					
					String createTime = jsonObject1.get("createTime").toString();
					int cityID = jsonObject1.getInt("cityID"); //城市ID
					
					houseShop.setId(id);
					houseShop.setShopName(shopName);
					houseShop.setShopDesc(shopDesc);
					houseShop.setBossImage(bossImage);
					houseShop.setBossPhone(bossPhone);
					houseShop.setBossWeChat(bossWeChat);
					houseShop.setCreateTime(createTime);
					houseShop.setCityID(cityID);
					houseShop.setLevel(level);
					houseShop.setType(type);
					houseShop.setBossName(bossName);
					houseShop.setImgUrl(imgUrl);
					houseShop.setCityName(cityName);
					houseShop.setPartnership(partnership);
//					list.add(houseShop);
				}
			}
			
		}
		return houseShop;
	}
	
}