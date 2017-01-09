package com.console.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.console.dto.BuildingsDto;
import com.console.dto.CityInfo;
import com.console.dto.DevelopersDto;
import com.console.dto.ProvincesDto;
import com.console.framework.dao.Pagination;
import com.console.util.Constant;
import com.console.util.HttpClientPostMethod;
import com.console.util.MsgPropertiesUtils;
import com.mmzb.fdfs.facade.FileFacade;
import com.mmzb.fdfs.facade.entity.FileUploadRequest;
import com.mmzb.fdfs.facade.entity.FileUploadResponse;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sellHouse")
public class SellHouseInfoController extends BaseController {

	public static Logger logger = Logger.getLogger(SellHouseInfoController.class);

	@RequestMapping("/toSellHouseList")
	public ModelAndView toBuilding(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pager = new Pagination();
		int current_page;// 当前页
		if (null == request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		} else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		pager.setCurrent_page(current_page);
		pager.setPage_size(MsgPropertiesUtils.getPageSize());
		List<BuildingsDto> buildingsList = getBuildings(pager, Constant.BUILDING_TYPE_01);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());

		mv.addObject("pager", pager);

		mv.addObject("buildingsList", buildingsList);
		List<CityInfo> cityList = getCities();
		List<ProvincesDto> proList = getProvinces();
		List<DevelopersDto> devList = getDevelopers();
		mv.addObject("cityList", cityList);
		mv.addObject("proList", proList);
		mv.addObject("devList", devList);
		mv.setViewName("/sellHouse/sellbuilldList_new");
		return mv;
	}

	@RequestMapping("/getBuildingsDetail")
	public ModelAndView getBuildingsDetail(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			Integer buildId = Integer.valueOf(request.getParameter("buildId"));
			BuildingsDto buildingsList = getBuildingsInfo(buildId);
			// 转义
			// buildingsList.setName(HtmlUtils.htmlEscape(buildingsList.getName()));
			// buildingsList.setProjectIntroduction(HtmlUtils.htmlEscape(buildingsList.getProjectIntroduction()));
			// buildingsList.setUnitArea(HtmlUtils.htmlEscape(buildingsList.getUnitArea()));
			// buildingsList.setDecorationStandard(HtmlUtils.htmlEscape(buildingsList.getDecorationStandard()));
			// buildingsList.setPropertyType(HtmlUtils.htmlEscape(buildingsList.getPropertyType()));
			// buildingsList.setOpenDate(HtmlUtils.htmlEscape(buildingsList.getOpenDate()));
			// buildingsList.setLaunchDate(HtmlUtils.htmlEscape(buildingsList.getLaunchDate()));
			// buildingsList.setPurchaseDiscount(HtmlUtils.htmlEscape(buildingsList.getPurchaseDiscount()));
			// buildingsList.setConsultantPhone(HtmlUtils.htmlEscape(buildingsList.getConsultantPhone()));
			// buildingsList.setBuiltType(HtmlUtils.htmlEscape(buildingsList.getBuiltType()));
			// buildingsList.setVolumeRate(HtmlUtils.htmlEscape(buildingsList.getVolumeRate()));
			// buildingsList.setGreeningRate(HtmlUtils.htmlEscape(buildingsList.getGreeningRate()));
			// buildingsList.setPlanHouseholds(HtmlUtils.htmlEscape(buildingsList.getPlanHouseholds()));
			// buildingsList.setPlanParking(HtmlUtils.htmlEscape(buildingsList.getPlanParking()));
			// buildingsList.setPresalePermit(HtmlUtils.htmlEscape(buildingsList.getPresalePermit()));
			// buildingsList.setPropertyCompName(HtmlUtils.htmlEscape(buildingsList.getPropertyCompName()));
			// buildingsList.setPropertyFee(HtmlUtils.htmlEscape(buildingsList.getPropertyFee()));
			// buildingsList.setHeartingMode(HtmlUtils.htmlEscape(buildingsList.getHeartingMode()));
			// buildingsList.setWaterElec(HtmlUtils.htmlEscape(buildingsList.getWaterElec()));
			// buildingsList.setScenicResource(HtmlUtils.htmlEscape(buildingsList.getScenicResource()));
			// buildingsList.setHumanityMatching(HtmlUtils.htmlEscape(buildingsList.getHumanityMatching()));
			// buildingsList.setEducationMatching(HtmlUtils.htmlEscape(buildingsList.getEducationMatching()));
			// buildingsList.setBusinessMatching(HtmlUtils.htmlEscape(buildingsList.getBusinessMatching()));
			// buildingsList.setCommerceMatching(HtmlUtils.htmlEscape(buildingsList.getCommerceMatching()));
			// buildingsList.setLeisureMatching(HtmlUtils.htmlEscape(buildingsList.getLeisureMatching()));
			// buildingsList.setHospitalResource(HtmlUtils.htmlEscape(buildingsList.getHospitalResource()));
			// buildingsList.setExhibitionImages(HtmlUtils.htmlEscape(buildingsList.getExhibitionImages()));
			// buildingsList.setApartmentImages(HtmlUtils.htmlEscape(buildingsList.getApartmentImages()));
			// buildingsList.setShowingsImages(HtmlUtils.htmlEscape(buildingsList.getShowingsImages()));
			// buildingsList.setShowingsVideos(HtmlUtils.htmlEscape(buildingsList.getShowingsVideos()));

			mv.addObject("buildings", buildingsList);
			List<CityInfo> cityList = getCities();
			List<ProvincesDto> proList = getProvinces();
			List<DevelopersDto> devList = getDevelopers();
			mv.addObject("buildId", buildId);
			mv.addObject("cityList", cityList);
			mv.addObject("proList", proList);
			mv.addObject("devList", devList);
			mv.addObject("scenic_resource", arrayToList(buildingsList.getScenicResource(), ","));
			mv.addObject("humanity_matching", arrayToList(buildingsList.getHumanityMatching(), ","));
			mv.addObject("education_matching", arrayToList(buildingsList.getEducationMatching(), ","));
			mv.addObject("business_matching", arrayToList(buildingsList.getBusinessMatching(), ","));
			mv.addObject("commerce_matching", arrayToList(buildingsList.getCommerceMatching(), ","));
			mv.addObject("leisure_matching", arrayToList(buildingsList.getLeisureMatching(), ","));
			mv.addObject("hospital_resource", arrayToList(buildingsList.getHospitalResource(), ","));
			mv.addObject("exhibition_images", jsonToMap(buildingsList.getExhibitionImages()));
			mv.addObject("apartment_images", jsonToMap(buildingsList.getApartmentImages()));
			mv.addObject("showings_images", jsonToMap(buildingsList.getShowingsImages()));
			mv.addObject("showings_videos", jsonToMap(buildingsList.getShowingsVideos()));
			mv.setViewName("/sellHouse/updateSellBuilding_new");
		} catch (Exception e) {
			logger.info("更新楼盘信息异常", e);
		}
		return mv;
	}

	@RequestMapping("/deleteBuilding")
	public ModelAndView deleteBuilding(HttpSession session, HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("bldId", Integer.valueOf(id));
		com.meidusa.fastjson.JSONObject execJsonObject = HttpClientPostMethod.httpReqUrl(postData, "removeBuilding");
		if (!"0".equals(execJsonObject.getString("code"))) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/common/error");
			mv.addObject("message", "删除楼盘异常");
			return mv;
		}
		return new ModelAndView("redirect:/sellHouse/toSellHouseList.shtml");
	}

	@RequestMapping("/to_addBuilding")
	public ModelAndView toaddBuilding(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<CityInfo> cityList = getCities();
		List<ProvincesDto> proList = getProvinces();
		List<DevelopersDto> devList = getDevelopers();
		mv.addObject("cityList", cityList);
		mv.addObject("proList", proList);
		mv.addObject("devList", devList);
		mv.setViewName("/sellHouse/addSellBuilding_new");
		return mv;
	}

	@RequestMapping("/addBuilding")
	public ModelAndView addBuilding(HttpSession session, HttpServletRequest request) throws Exception {
		boolean update = request.getParameter("update") == null || "".equals(request.getParameter("update")) ? false : true;
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			Map<String, Object> showDetailData = new HashMap<String, Object>();
			String name = stringTip(request, "name");// 楼盘名称
			int type = integerTip(request, "type");// 楼盘类型（1别墅2高档公寓3酒店公寓）
			int proId = integerTip(request, "proId");// 省份
			int cityId = integerTip(request, "cityId"); // 城市
			int devId = integerTip(request, "devId"); // 开发商
			postData.put("project_introduction", stringTip(request, "project_introduction"));// 项目简介
			postData.put("unit_area", stringTip(request, "unit_area"));// 户型面积区间段
			String built_area = request.getParameter("built_area");// 建筑面积
			if (built_area != null && built_area.trim().length() != 0)
				postData.put("built_area", new BigDecimal(request.getParameter("built_area")).setScale(2, BigDecimal.ROUND_UP));
			postData.put("average_price", integerTip(request, "average_price")); // 楼盘均价
			postData.put("property_type", stringTip(request, "property_type"));// 物业类型
			postData.put("decoration_standard", stringTip(request, "decoration_standard"));// 装修标准
			postData.put("open_date", stringTip(request, "open_date"));// 最近开盘日期（yyyyMMdd）
			postData.put("purchase_discount", stringTip(request, "purchase_discount"));// 购房优惠
			postData.put("consultant_phone", stringTip(request, "consultant_phone")); // 置业顾问手机
			postData.put("launch_date", stringTip(request, "launch_date"));// 最早交房日期（yyyyMMdd）
			postData.put("year_limit", integerTip(request, "year_limit"));// 产权年限
			postData.put("built_type", stringTip(request, "built_type"));// 建筑类型
			postData.put("volume_rate", stringTip(request, "volume_rate"));// 容积率
			postData.put("greening_rate", stringTip(request, "greening_rate"));// 绿化率
			postData.put("plan_households", stringTip(request, "plan_households"));// 规划户数
			postData.put("plan_parking", stringTip(request, "plan_parking"));// 规划车位
			postData.put("presale_permit", stringTip(request, "presale_permit"));// 预售许可
			postData.put("property_comp_name", stringTip(request, "property_comp_name"));// 物业公司全称
			postData.put("property_fee", stringTip(request, "property_fee"));// 物业费
			postData.put("hearting_mode", stringTip(request, "hearting_mode")); // 供暖方式
			postData.put("water_elec", stringTip(request, "water_elec"));// 水电煤气
			postData.put("project_feature", stringTip(request, "project_feature"));// 项目特色

			String[] scenic_resource = stringTipArray(request, "scenic_resource");// 景观资源
			if (scenic_resource.length != 0)
				postData.put("scenic_resource", reverseStr(scenic_resource));
			String[] humanity_matching = stringTipArray(request, "humanity_matching");// 人文配套
			if (humanity_matching.length != 0)
				postData.put("humanity_matching", reverseStr(humanity_matching));
			String[] education_matching = stringTipArray(request, "education_matching");// 教育配套
			if (education_matching.length != 0)
				postData.put("education_matching", reverseStr(education_matching));
			String[] business_matching = stringTipArray(request, "business_matching");// 商业配套
			if (business_matching.length != 0)
				postData.put("business_matching", reverseStr(business_matching));
			String[] commerce_matching = stringTipArray(request, "commerce_matching"); // 商务配套
			if (commerce_matching.length != 0)
				postData.put("commerce_matching", reverseStr(commerce_matching));
			String[] leisure_matching = stringTipArray(request, "leisure_matching");// 休闲配套
			if (leisure_matching.length != 0)
				postData.put("leisure_matching", reverseStr(leisure_matching));
			String[] hospital_resource = stringTipArray(request, "hospital_resource");// 医院资源
			if (hospital_resource.length != 0)
				postData.put("hospital_resource", reverseStr(hospital_resource));
			String[] exhibition_images_name = stringTipArray(request, "exhibition_images_name");// 展示图片名称
			List<String> exhibition_images_url = new ArrayList<String>();// 展示图片url
			String[] apartment_images_name = stringTipArray(request, "apartment_images_name"); // 户型图片名称
			List<String> apartment_images_url = new ArrayList<String>(); // 户型图片url
			String[] showings_images_name = stringTipArray(request, "showings_images_name");// 看房图片名称
			List<String> showings_images_url = new ArrayList<String>(); // 看房图片url
			String[] showings_videos_name = stringTipArray(request, "showings_videos_name");// 看房视频名称
			List<String> showings_videos_url = new ArrayList<String>(); // 看房图片url
			List<String> index_image = new ArrayList<String>();// 详情页首图
			saveImage(request, exhibition_images_url, "exhibition_images_url");
			saveImage(request, apartment_images_url, "apartment_images_url");
			saveImage(request, showings_images_url, "showings_images_url");
			saveImage(request, showings_videos_url, "showings_videos_url");
			saveImage(request, index_image, "index_image");
			if (!exhibition_images_url.isEmpty() || update) {
				String[] str = stringTipArray(request, "exhibition_images_url");
				for (String temp : str) {
					exhibition_images_url.add(temp);
				}
				postData.put("exhibition_images", reverseJson(exhibition_images_name, exhibition_images_url));
			}
			if (!apartment_images_url.isEmpty() || update) {
				String[] str = stringTipArray(request, "apartment_images_url");
				for (String temp : str) {
					apartment_images_url.add(temp);
				}
				postData.put("apartment_images", reverseJson(apartment_images_name, apartment_images_url));
			}
			if (!showings_images_url.isEmpty() || update) {
				String[] str = stringTipArray(request, "showings_images_url");
				for (String temp : str) {
					showings_images_url.add(temp);
				}
				postData.put("showings_images", reverseJson(showings_images_name, showings_images_url));
			}
			if (!showings_videos_url.isEmpty() || update) {
				String[] str = stringTipArray(request, "showings_videos_url");
				for (String temp : str) {
					showings_videos_url.add(temp);
				}
				postData.put("showings_videos", reverseJson(showings_videos_name, showings_videos_url));
			}
			if (!index_image.isEmpty())
				postData.put("index_image", index_image.get(0));
			postData.put("provId", proId);
			postData.put("cityId", cityId);
			postData.put("devId", devId);
			postData.put("mark", "");
			postData.put("name", name);
			postData.put("type", type);
			postData.put("showDetail", showDetailData);
			postData.put("buildingType", Constant.BUILDING_TYPE_01);
			logger.info("=========发送后台server数据包======" + postData);
			if (update)
				postData.put("bldId", Integer.parseInt(request.getParameter("buildId")));
			HttpClientPostMethod.httpReqUrl(postData, !update ? "addBuilding" : "updateBuilding");
		} catch (Exception e) {
			logger.error("新增楼盘异常", e);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/common/error");
			mv.addObject("message", update ? "修改楼盘异常" : "新增楼盘异常");
			return mv;
		}
		return toBuilding(session, request);
	}

	@RequestMapping("/updateBuilding")
	public ModelAndView updateBuilding(HttpSession session, HttpServletRequest request) throws Exception {
		Integer bldId = Integer.valueOf(request.getParameter("bldId"));
		Integer proId = Integer.valueOf(request.getParameter("proId"));
		Integer cityId = Integer.valueOf(request.getParameter("cityId"));
		Integer devId = Integer.valueOf(request.getParameter("devId"));
		String mark = request.getParameter("mark");
		String name = request.getParameter("name");
		Integer type = Integer.valueOf(request.getParameter("type"));
		String houseExplain = request.getParameter("houseExplain");
		String peripheralConfig = request.getParameter("peripheralConfig");

		String description = request.getParameter("description");
		String telphone = request.getParameter("telphone");
		String bldImg = request.getParameter("bldImg");
		String video = request.getParameter("video");

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("bldId", bldId);
		postData.put("provId", proId);
		postData.put("cityId", cityId);
		postData.put("devId", devId);
		postData.put("mark", mark);
		postData.put("name", name);
		postData.put("type", type);

		Map<String, Object> showDetailData = new HashMap<String, Object>();
		showDetailData.put("telphone", telphone);
		showDetailData.put("description", description);
		showDetailData.put("bldImg", bldImg);
		showDetailData.put("video", video);
		showDetailData.put("houseExplain", houseExplain);
		showDetailData.put("peripheralConfig", peripheralConfig);
		postData.put("showDetail", showDetailData);
		HttpClientPostMethod.httpReqUrl(postData, "updateBuilding");
		return toBuilding(session, request);
	}

	private static Integer integerTip(HttpServletRequest request, String str) {
		return request.getParameter(str) == null || "".equals(request.getParameter(str)) ? 0 : Integer.parseInt(request.getParameter(str));
	}

	private static String stringTip(HttpServletRequest request, String str) {
		return request.getParameter(str) == null || "".equals(request.getParameter(str)) ? "" : request.getParameter(str);
	}

	private static String[] stringTipArray(HttpServletRequest request, String str) {
		return request.getParameterValues(str) == null || "".equals(request.getParameterValues(str)[0]) ? new String[0] : request.getParameterValues(str);
	}

	private static String reverseJson(String[] name, List<String> url) {
		if (null == name || name.length == 0 || null == url || url.size() == 0)
			return "";
		JSONObject temp = new JSONObject();
		for (int i = 0; i < name.length; i++) {
			temp.put(name[i], url.get(i));
		}
		return temp.toString();
	}

	private static Map<String, String> jsonToMap(String str) {
		Map<String, String> map = new HashMap<String, String>();
		if (null == str || str.length() == 0)
			return map;
		JSONObject json = JSONObject.fromObject(str);
		Iterator<String> iterator = json.keys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			map.put(key, json.getString(key));
		}
		return map;
	}

	private static List<String> arrayToList(String str, String pattern) {
		List<String> list = new ArrayList<String>();
		if (null == str || str.length() == 0)
			return list;
		String[] temp = str.split(pattern);
		if (temp.length == 0)
			return list;
		for (int i = 0; i < temp.length; i++) {
			list.add(temp[i]);
		}
		return list;
	}

	private static String reverseStr(String[] name) {
		if (name.length == 0 || null == name)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length; i++) {
			sb.append(name[i]).append(",");
		}
		return sb.toString();
	}

	private static void saveImage(HttpServletRequest request, List<String> ImgStr, String paramName) {

		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				List<MultipartFile> iter = multiRequest.getFiles(paramName);
				for (int i = 0; i < iter.size(); i++) {
					String url = MsgPropertiesUtils.getUploadHouseUrl();
					HessianProxyFactory factory = new HessianProxyFactory();
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
					FileUploadRequest req = new FileUploadRequest();
					req.setSource("妈妈送房网");
					req.setFileType("jpg");// jpg 、png
					req.setGroupName("group1");
					byte[] filedata = iter.get(i).getBytes();
					if (filedata.length == 0)
						return;
					req.setFile(filedata);
					FileUploadResponse fileResponse = facade.uploadFile(req);
					ImgStr.add(fileResponse.getFileUrl());
					logger.info("===图片路径为：" + fileResponse.getFileUrl() + "========" + fileResponse.getMessage());
				}
			}
		} catch (Exception ex) {
			logger.error("上传图片错误", ex);
		}
	}
}
