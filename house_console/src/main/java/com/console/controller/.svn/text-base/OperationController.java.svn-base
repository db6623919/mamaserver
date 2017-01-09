package com.console.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.caucho.hessian.client.HessianProxyFactory;
import com.console.dto.BuildingsDto;
import com.console.dto.CityInfo;
import com.console.dto.DevelopersDto;
import com.console.dto.HouseDto;
import com.console.dto.HouseShop;
import com.console.dto.HouseTag;
import com.console.dto.IntroductionDto;
import com.console.dto.MarkInfo;
import com.console.dto.OrderCommentVo;
import com.console.entity.TSysUser;
import com.console.framework.dao.Pagination;
import com.console.util.Constant;
import com.console.util.DateUtil;
import com.console.util.HttpClientPostMethod;
import com.console.util.JsonGeneratorUtils;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.console.util.StringUtil;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.fdfs.facade.FileFacade;
import com.mmzb.fdfs.facade.entity.FileUploadRequest;
import com.mmzb.fdfs.facade.entity.FileUploadResponse;

@Controller
@RequestMapping("/house")
public class OperationController extends BaseController {
	public static Logger logger = Logger.getLogger(OperationController.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
	
	@RequestMapping("/houseDel")
	public ModelAndView houseDel(HttpSession session, HttpServletRequest request, HttpServletResponse resposne) throws Exception {
		Map<String, Object> houseMap = new HashMap<String, Object>();
		houseMap.put("houseId", Integer.valueOf(request.getParameter("houseId")));
		houseMap.put("removed", 1);
		HttpClientPostMethod.httpReqUrl(houseMap, "updateHouse");
		return toHouse(session, request);
	}

	@RequestMapping("/houseAdd")
	public ModelAndView houseAdd(HttpSession session, HttpServletRequest request, HttpServletResponse resposne) throws Exception {
		logger.info("start to houseAdd ");
		
		StringBuffer introductionImgStr = new StringBuffer();
		StringBuffer shareImgStr = new StringBuffer();
		String flagUpdate = request.getParameter("flagUpdate");// 更新标志字段
		// String imgFlag=request.getParameter("imgFlag");
		// String oldImg=request.getParameter("oldImg");
		String mapImg = "";
		if ("update".equals(flagUpdate)) {// 更新地图图片
			saveImageBuffer(request, introductionImgStr);
			String mapImgFlag = request.getParameter("mapImgFlag");
			if ("0".equals(mapImgFlag)) {// 不替换以前的图片
				mapImg = saveMapImageBuffer(request);
			} else {// 替换以前的图片
				mapImg = saveMapImage(request);
			}
		} else {// 新增的地图图片
			mapImg = saveMapImage(request);
		}
		int introductionNum = 0;
		saveImage(request, introductionImgStr);// 保存房源图片,获取房源路径
		saveShareImage(request,shareImgStr);   //保存分享图片，获取分享图片路径
        String shareImg = request.getParameter("share");//更新时候原分享图
        if ("null".equals(shareImgStr.toString())) {
        	shareImgStr = new StringBuffer().append(shareImg);
		}
		
		Map<String, Object> showDeatilMap = new HashMap<String, Object>();
		String name = request.getParameter("name");// 房间名字
		String houseType = request.getParameter("houseType");// 房子类型
		String bedType = request.getParameter("bedType");// 床型
		String isInvoice = request.getParameter("isInvoice");// 是否要发票
		String checkInTime = request.getParameter("checkInTime");// 入住时间
		String checkOutTime = request.getParameter("checkOutTime");// 离店时间
		String flag = request.getParameter("flag");// 标志
		String recommendTime = request.getParameter("recommendTime") == null ? "" : request.getParameter("recommendTime");// 推荐时间
		String houseArea = request.getParameter("houseArea");// 房间面积
//		String inSeasonDesc = request.getParameter("inSeasonDesc");// 平旺季说明
		String houseshop_id = request.getParameter("houseshop_id");//店铺id

		String introductionName[] = {"其它","首页特色","公共空间","卧室","卫浴","客厅&厨房&阳台","外观","周边"};// 房间图片名字
		StringBuffer introductionNameStr = new StringBuffer();
		
		StringBuffer introductionTypeStr = new StringBuffer();
		String introductionType[] = request.getParameterValues("introductionType");// 房间图片类型名字
		for (int i = 0; i < introductionType.length; i++) {
			introductionTypeStr.append(introductionType[i]).append(",");

			String str = introductionType[i];
			introductionNameStr.append(introductionName[Integer.parseInt(introductionType[i])]).append(",");

		}
		
//		showDeatilMap.put("inSeasonDesc", inSeasonDesc);// 平旺季说明
		showDeatilMap.put("introductionName", introductionNameStr);// 房源图片名称
		showDeatilMap.put("introductionType", introductionTypeStr);//房源图片类型
		showDeatilMap.put("introductionImg", introductionImgStr);// 房源图片路径
		showDeatilMap.put("shareImg", shareImgStr);// 分享图片路径
		showDeatilMap.put("mapImg", mapImg);

		String live = request.getParameter("live");// 宜住人数
		String telephone = request.getParameter("telephone");// 咨询电话
		String longitude = request.getParameter("longitude");// 经度
		String latitude = request.getParameter("latitude");// 纬度
		String address = request.getParameter("address");// 房间详细地址
		String checkdesc = HtmlUtils.htmlEscape(request.getParameter("checkdesc"));// 入住说明

		String conditione = request.getParameter("conditione") == null ? "0" : request.getParameter("conditione");// 空调
		String washing = request.getParameter("washing") == null ? "0" : request.getParameter("washing");// 洗衣机
		String fridge = request.getParameter("fridge") == null ? "0" : request.getParameter("fridge");// 冰箱
		String drinking = request.getParameter("drinking") == null ? "0" : request.getParameter("drinking");// 淋浴
		String tv = request.getParameter("tv") == null ? "0" : request.getParameter("tv");// 电视机

		String towels = request.getParameter("towels") == null ? "0" : request.getParameter("towels");// 毛巾
		String tooth = request.getParameter("tooth") == null ? "0" : request.getParameter("tooth");// 牙具
		String slipper = request.getParameter("slipper") == null ? "0" : request.getParameter("slipper");// 拖鞋
		String shampoo = request.getParameter("shampoo") == null ? "0" : request.getParameter("shampoo");// 洗发/沐浴露
		String hairdrier = request.getParameter("hairdrier") == null ? "0" : request.getParameter("hairdrier");// 吹风机
		String shower = request.getParameter("shower") == null ? "0" : request.getParameter("shower");// 浴缸
		String hotpot = request.getParameter("hotpot") == null ? "0" : request.getParameter("hotpot");// 热水壶
		String heater = request.getParameter("heater") == null ? "0" : request.getParameter("heater");// 热水器
		String dryer = request.getParameter("dryer") == null ? "0" : request.getParameter("dryer");// 烘干机
		String smokedetector = request.getParameter("smokedetector") == null ? "0" : request.getParameter("smokedetector");// 烟雾探测器
		String heating = request.getParameter("heating") == null ? "0" : request.getParameter("heating");// 暖气
		String extinguisher = request.getParameter("extinguisher") == null ? "0" : request.getParameter("extinguisher");// 灭火器
		String aidkit = request.getParameter("aidkit") == null ? "0" : request.getParameter("aidkit");// 急救包

		Map<String, Object> facilities = new HashMap<String, Object>();
		facilities.put("conditione", conditione);// 空调
		facilities.put("washing", washing);// 洗衣机
		facilities.put("fridge", fridge);// 冰箱
		facilities.put("drinking", drinking);// 饮水机
		facilities.put("tv", tv);// 电视机

		facilities.put("towels", towels);// 空调
		facilities.put("tooth", tooth);// 洗衣机
		facilities.put("slipper", slipper);// 冰箱
		facilities.put("shampoo", shampoo);// 饮水机
		facilities.put("hairdrier", hairdrier);// 电视机
		facilities.put("shower", shower);// 空调
		facilities.put("heater", heater);// 洗衣机
		facilities.put("dryer", dryer);// 冰箱
		facilities.put("smokedetector", smokedetector);// 饮水机
		facilities.put("heating", heating);// 电视机
		facilities.put("extinguisher", extinguisher);// 空调
		facilities.put("aidkit", aidkit);// 洗衣机
		facilities.put("hotpot", hotpot);// 冰箱

		String wifi = request.getParameter("wifi") == null ? "0" : request.getParameter("wifi");
		String broadband = request.getParameter("broadband") == null ? "0" : request.getParameter("broadband");// 宽带
		String elevator = request.getParameter("elevator") == null ? "0" : request.getParameter("elevator");// 电梯
		String swimming = request.getParameter("swimming") == null ? "0" : request.getParameter("swimming");// 游泳池
		String accesscard = request.getParameter("accesscard") == null ? "0" : request.getParameter("accesscard");// 门禁卡
		String securitystaff = request.getParameter("securitystaff") == null ? "0" : request.getParameter("securitystaff");// 保安
		String store = request.getParameter("store") == null ? "0" : request.getParameter("store");// 电梯
		String parking = request.getParameter("parking") == null ? "0" : request.getParameter("parking");// 停车位
		String gym = request.getParameter("gym") == null ? "0" : request.getParameter("gym");// 健身房
		String playground = request.getParameter("playground") == null ? "0" : request.getParameter("playground");// 儿童乐园
		String wheelchair = request.getParameter("wheelchair") == null ? "0" : request.getParameter("wheelchair");// 无障碍设施
		String buzzer = request.getParameter("buzzer") == null ? "0" : request.getParameter("buzzer");// 蜂鸣器/无线对讲机

		Map<String, Object> supporting = new HashMap<String, Object>();
		supporting.put("wifi", wifi);
		supporting.put("broadband", broadband);
		supporting.put("elevator", elevator);
		supporting.put("swimming", swimming);

		supporting.put("accesscard", accesscard);
		supporting.put("securitystaff", securitystaff);
		supporting.put("store", store);
		supporting.put("parking", parking);
		supporting.put("gym", gym);
		supporting.put("playground", playground);
		supporting.put("wheelchair", wheelchair);
		supporting.put("buzzer", buzzer);

		String cook = request.getParameter("cook") == null ? "0" : request.getParameter("cook");// 做饭
		String party = request.getParameter("party") == null ? "0" : request.getParameter("party");// 聚会
		String smoking = request.getParameter("smoking") == null ? "0" : request.getParameter("smoking");// 吸烟

		String pet = request.getParameter("pet") == null ? "0" : request.getParameter("pet");// 可带宠物
		String extrabed = request.getParameter("extrabed") == null ? "0" : request.getParameter("extrabed");// 可加床
		String guests = request.getParameter("guests") == null ? "0" : request.getParameter("guests");// 接待外宾
		String breakfast = request.getParameter("breakfast") == null ? "0" : request.getParameter("breakfast");// 提供早餐
		String childrenstay = request.getParameter("childrenstay") == null ? "0" : request.getParameter("childrenstay");// 欢迎孩子入住

		Map<String, Object> other = new HashMap<String, Object>();
		other.put("cook", cook);
		other.put("party", party);
		other.put("smoking", smoking);

		other.put("pet", pet);
		other.put("extrabed", extrabed);
		other.put("guests", guests);
		other.put("breakfast", breakfast);
		other.put("childrenstay", childrenstay);

		String stock = request.getParameter("stock");
		String sideo_url = request.getParameter("sideo_url");

		showDeatilMap.put("other", other);// 其他
		showDeatilMap.put("supporting", supporting);// 房间配套
		showDeatilMap.put("introductionNum", introductionNum);
		showDeatilMap.put("facilities", facilities);// 房间设施
		showDeatilMap.put("houseType", houseType);
		showDeatilMap.put("bedType", bedType);
		showDeatilMap.put("isInvoice", isInvoice);
		showDeatilMap.put("houseArea", houseArea);

		showDeatilMap.put("checkInTime", checkInTime);
		showDeatilMap.put("checkOutTime", checkOutTime);
		showDeatilMap.put("live", live);
		showDeatilMap.put("telephone", telephone);
		showDeatilMap.put("longitude", longitude);
		showDeatilMap.put("latitude", latitude);
		showDeatilMap.put("address", address);
		showDeatilMap.put("checkdesc", checkdesc);

		showDeatilMap.put("videoUrl", sideo_url);// 视频

		Map<String, Object> houseMap = new HashMap<String, Object>();
		String freezeAmt = request.getParameter("freezeAmt");
		String totalAmt = request.getParameter("totalAmt");
		String memFreezeAmt = request.getParameter("memFreezeAmt");
		String memTotalAmt = request.getParameter("memTotalAmt");
		String market_price = request.getParameter("market_price");//市场价
		Map<String, Object> summaryInfo = new HashMap<String, Object>();
		String status = request.getParameter("stauts");
		String onLineTime = "";
		if(status.equals("1")) {
			onLineTime = request.getParameter("onLineTime") == null ? "" : request.getParameter("onLineTime");// 上架时间
		}
		summaryInfo.put("houseName", name);
		summaryInfo.put("status", status);
		summaryInfo.put("onLineTime", onLineTime);
		String theme = request.getParameter("theme");
		String luxury = request.getParameter("luxury");
		String devId = request.getParameter("devId");
		String bldId = request.getParameter("bldId");
		String cityId = request.getParameter("cityId");
		String room = request.getParameter("room");

		houseMap.put("freezeAmt", Integer.valueOf(freezeAmt));
		houseMap.put("totalAmt", Integer.valueOf(totalAmt));
		houseMap.put("memFreezeAmt", Integer.valueOf(memFreezeAmt));
		houseMap.put("memTotalAmt", Integer.valueOf(memTotalAmt));
		houseMap.put("market_price", market_price);
		houseMap.put("summaryInfo", summaryInfo);
		houseMap.put("theme", Integer.valueOf(theme));
		houseMap.put("luxury", Integer.valueOf(luxury));
		houseMap.put("devId", Integer.valueOf(devId));
		houseMap.put("bldId", Integer.valueOf(bldId));
		houseMap.put("cityId", Integer.valueOf(cityId));
		houseMap.put("room", Integer.valueOf(room));
		houseMap.put("flag", flag);
		houseMap.put("recommendTime", recommendTime);
		houseMap.put("houseshop_id", houseshop_id);

		String mark[] = request.getParameterValues("mark");// 获取标签

		if (mark != null) {
			int markInt[] = new int[mark.length];
			for (int i = 0; i < mark.length; i++) {
				markInt[i] = Integer.valueOf(mark[i]);
				houseMap.put("mark", markInt);
			}
		} else {
			int markInt[] = new int[1];
			houseMap.put("mark", markInt);
		}

		houseMap.put("type", houseType);
		houseMap.put("totalRoomNum", Integer.valueOf(stock));// 库存
		houseMap.put("showDetail", showDeatilMap);
		if ("update".equals(flagUpdate)) {
			houseMap.put("houseId", Integer.valueOf(request.getParameter("houseId")));
			JSONObject obj= HttpClientPostMethod.httpReqUrl(houseMap, "updateHouse");
			String code = obj.getString("code");
			if (Constant.RETURN_CODE.equals(code)) {
				logger.info("end to updateHouse 更新成功！ ");
				return toHouse(session, request);
			}else {
				logger.info("end to houseAdd 更新失败！ ");
				String msg = obj.getString("msg");
				ModelAndView mv=new ModelAndView();
				mv.setViewName("/common/error_new");
				mv.addObject("message", "更新房源失败！");
				return mv;
			}
		} else {
			JSONObject obj= HttpClientPostMethod.httpReqUrl(houseMap, "addHouse");
			String code = obj.getString("code");
			if (Constant.RETURN_CODE.equals(code)) {
				logger.info("end to houseAdd 添加成功！ ");
				return toHouse(session, request);
			}else {
				logger.info("end to houseAdd 添加失败！ ");
				String msg = obj.getString("msg");
				ModelAndView mv=new ModelAndView();
				mv.setViewName("/common/error_new");
				mv.addObject("message", "添加房源失败！");
				return mv;
			}
		}

	}

	private void saveImageBuffer(HttpServletRequest request, StringBuffer introductionImgStr) throws Exception {
		String introductionImg[] = request.getParameterValues("introductionImg");
		if (introductionImg.length > 0) {
			for (int k = 0; k < introductionImg.length; k++) {
				if (introductionImg[k] == null || "".equals(introductionImg[k])) {
					continue;
				}
				introductionImgStr.append(introductionImg[k]).append(",");
			}
		}
	}

	private String saveMapImageBuffer(HttpServletRequest request) throws Exception {
		String mapImgStr = "";
		Map<String, Object> postData1 = new HashMap<String, Object>();
		postData1.put("houseId", Integer.valueOf(request.getParameter("houseId")));
		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData1, "getHouseInfo");
		JSONArray array = JSONArray.fromObject(result.get("data"));
		for (int i = 0; i < array.size(); i++) {

			net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
			JSONArray arrayHouses = JSONArray.fromObject("[" + jsonObject.get("showDetail") + "]");

			for (int j = 0; j < arrayHouses.size(); j++) {

				net.sf.json.JSONObject jsonObjectShowDeatil = arrayHouses.getJSONObject(j);
				if (jsonObjectShowDeatil.get("mapImg") != null) {
					String mapImg[] = jsonObjectShowDeatil.get("mapImg").toString().split(",");
					if (mapImg.length > 0) {
						for (int k = 0; k < mapImg.length; k++) {
							if (mapImg[k] == null || "".equals(mapImg[k])) {
								continue;
							}
							mapImgStr = mapImg[k];
						}
					}
				}
			}
		}
		return mapImgStr;
	}

	private void saveImage(HttpServletRequest request, StringBuffer introductionImgStr) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("introductionImg");
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
						introductionImgStr.append(fileResponse.getFileUrl()).append(",");
						System.out.println("===图片路径为：" + fileResponse.getFileUrl() + "========" + fileResponse.getMessage());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}
	
	private void saveShareImage(HttpServletRequest request, StringBuffer shareImgStr) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("shareImg");
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
						shareImgStr.append(fileResponse.getFileUrl());
						System.out.println("===图片路径为：" + fileResponse.getFileUrl() + "========" + fileResponse.getMessage());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}

	private String saveMapImage(HttpServletRequest request) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		String mapStr = null;
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("mapImg");
			for (int i = 0; i < iter.size(); i++) {
				String url = MsgPropertiesUtils.getUploadHouseUrl();
				;
				HessianProxyFactory factory = new HessianProxyFactory();
				try {
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
					FileUploadRequest req = new FileUploadRequest();
					req.setSource("妈妈送房网");
					req.setFileType("jpg");// jpg 、png
					req.setGroupName("group1");
					req.setFile(iter.get(i).getBytes());

					FileUploadResponse fileResponse = facade.uploadFile(req);
					mapStr = fileResponse.getFileUrl();
					System.out.println("===图片路径为：" + fileResponse.getFileUrl() + "========" + fileResponse.getMessage());
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
		return mapStr;
	}

	@RequestMapping("/to_houseUpdate")
	public ModelAndView toHouseUpdate(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("houseId", Integer.valueOf(request.getParameter("houseId")));
		mv.addObject("houseId", Integer.valueOf(request.getParameter("houseId")));
		try {
			com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getHouseInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {

				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {

					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);

					String freezeAmt = jsonObject.get("freezeAmt").toString();
					String flag = jsonObject.get("flag").toString();
					String totalAmt = jsonObject.get("totalAmt").toString();
					String memFreezeAmt = jsonObject.get("memFreezeAmt").toString();
					String memTotalAmt = jsonObject.get("memTotalAmt").toString();
					String summaryInfo = jsonObject.get("summaryInfo").toString();
					String theme = jsonObject.get("theme").toString();
					String luxury = jsonObject.get("luxury").toString();
					String devId = jsonObject.get("devId").toString();
					String bldId = jsonObject.get("bldId").toString();
					String cityId = jsonObject.get("cityId").toString();
					String room = jsonObject.get("room").toString();
					String stock = jsonObject.get("totalRoomNum").toString();
					String recommendTime = jsonObject.get("recommendTime") == null ? "" : jsonObject.get("recommendTime").toString().substring(0, 10);
					String houseshop_id = jsonObject.get("houseshop_id") == null ? "" :jsonObject.get("houseshop_id").toString();
					String market_price = jsonObject.get("market_price") == null ? "" :jsonObject.get("market_price").toString();

					String mark[] = jsonObject.get("mark").toString().replace("[", "").replace("]", "").split(",");
					List<MarkInfo> markList = getMarkInfoList();
					for (MarkInfo markInfo : markList) {
						for (String string : mark) {
							if (markInfo.getIndex().equals(string)) {
								markInfo.setCheck(1);
							}
						}
					}

					mv.addObject("markList", markList);
					mv.addObject("freezeAmt", freezeAmt);
					mv.addObject("totalAmt", totalAmt);
					mv.addObject("memFreezeAmt", memFreezeAmt);
					mv.addObject("memTotalAmt", memTotalAmt);
					mv.addObject("market_price", market_price);
					mv.addObject("summaryInfo", summaryInfo);
					mv.addObject("theme", theme);
					mv.addObject("luxury", luxury);
					mv.addObject("devId", devId);
					mv.addObject("bldId", bldId);
					mv.addObject("cityId", cityId);
					mv.addObject("room", room);
					mv.addObject("recommendTime", recommendTime);
					mv.addObject("houseshop_id", houseshop_id);

					JSONArray arraysummaryInfo = JSONArray.fromObject("[" + jsonObject.get("summaryInfo") + "]");

					for (int j = 0; j < arraysummaryInfo.size(); j++) {

						net.sf.json.JSONObject jsonObjectShowDeatil = arraysummaryInfo.getJSONObject(j);
						String houseName = jsonObjectShowDeatil.get("houseName").toString();
						String status = jsonObjectShowDeatil.get("status").toString();
						String onLineTime = jsonObjectShowDeatil.get("onLineTime") == null ? "" : jsonObjectShowDeatil.get("onLineTime").toString();
						mv.addObject("houseName", houseName);
						mv.addObject("status", status);
						mv.addObject("onLineTime", onLineTime);

					}
					JSONArray arrayHouses = JSONArray.fromObject("[" + jsonObject.get("showDetail") + "]");

					for (int j = 0; j < arrayHouses.size(); j++) {

						net.sf.json.JSONObject jsonObjectShowDeatil = arrayHouses.getJSONObject(j);

						String houseType = jsonObjectShowDeatil.get("houseType").toString();
						String bedType = jsonObjectShowDeatil.get("bedType").toString();
						String isInvoice = jsonObjectShowDeatil.get("isInvoice").toString();
						String introductionNum = jsonObjectShowDeatil.get("introductionNum").toString();
						String checkInTime = jsonObjectShowDeatil.get("checkInTime").toString();
						String checkOutTime = jsonObjectShowDeatil.get("checkOutTime").toString();
						String live = jsonObjectShowDeatil.get("live").toString();
						String telephone = jsonObjectShowDeatil.get("telephone").toString();
						String longitude = jsonObjectShowDeatil.get("longitude").toString();
						String latitude = jsonObjectShowDeatil.get("latitude").toString();
						String address = jsonObjectShowDeatil.get("address").toString();
						String checkdesc = HtmlUtils.htmlEscape(jsonObjectShowDeatil.get("checkdesc").toString());
					
						String inSeasonDesc;
						if (jsonObjectShowDeatil.containsKey("inSeasonDesc"))
							inSeasonDesc = jsonObjectShowDeatil.getString("inSeasonDesc");
						else
							inSeasonDesc = "";
						String houseArea = jsonObjectShowDeatil.get("houseArea").toString();
						String mapImg = jsonObjectShowDeatil.optString("mapImg");

						JSONArray arrayFacilities = JSONArray.fromObject("[" + jsonObjectShowDeatil.get("facilities") + "]");// 设施
						for (int l = 0; l < arrayFacilities.size(); l++) {
							net.sf.json.JSONObject jsonFacilitiesShowDeatil = arrayFacilities.getJSONObject(l);
							String conditione = jsonFacilitiesShowDeatil.get("conditione").toString();
							String drinking = jsonFacilitiesShowDeatil.get("drinking").toString();
							String fridge = jsonFacilitiesShowDeatil.get("fridge").toString();
							String tv = jsonFacilitiesShowDeatil.get("tv").toString();
							String washing = jsonFacilitiesShowDeatil.get("washing").toString();

							String towels = jsonFacilitiesShowDeatil.get("towels").toString();// 毛巾
							String tooth = jsonFacilitiesShowDeatil.get("tooth").toString();// 牙具
							String slipper = jsonFacilitiesShowDeatil.get("slipper").toString();// 拖鞋
							String shampoo = jsonFacilitiesShowDeatil.get("shampoo").toString();// 洗发/沐浴露
							String hairdrier = jsonFacilitiesShowDeatil.get("hairdrier").toString();// 吹风机
							String shower = jsonFacilitiesShowDeatil.get("shower").toString();// 浴缸
							String hotpot = jsonFacilitiesShowDeatil.get("hotpot").toString();// 热水壶
							String heater = jsonFacilitiesShowDeatil.get("heater").toString();// 热水器
							String dryer = jsonFacilitiesShowDeatil.get("dryer").toString();// 烘干机
							String smokedetector = jsonFacilitiesShowDeatil.get("smokedetector").toString();// 烟雾探测器
							String heating = jsonFacilitiesShowDeatil.get("heating").toString();// 暖气
							String extinguisher = jsonFacilitiesShowDeatil.get("extinguisher").toString();// 灭火器
							String aidkit = jsonFacilitiesShowDeatil.get("aidkit").toString();// 急救包

							mv.addObject("conditione", conditione);
							mv.addObject("drinking", drinking);
							mv.addObject("fridge", fridge);
							mv.addObject("tv", tv);
							mv.addObject("washing", washing);
							mv.addObject("mapImg", mapImg);
							mv.addObject("towels", towels);
							mv.addObject("tooth", tooth);
							mv.addObject("slipper", slipper);
							mv.addObject("shampoo", shampoo);
							mv.addObject("hairdrier", hairdrier);
							mv.addObject("shower", shower);
							mv.addObject("heater", heater);
							mv.addObject("dryer", dryer);
							mv.addObject("smokedetector", smokedetector);
							mv.addObject("heating", heating);
							mv.addObject("extinguisher", extinguisher);
							mv.addObject("aidkit", aidkit);
							mv.addObject("hotpot", hotpot);
						}

						List<IntroductionDto> IntroductionList = new ArrayList<IntroductionDto>();
						if (jsonObjectShowDeatil.get("introductionImg") != null) {
							String introductionImg[] = jsonObjectShowDeatil.get("introductionImg").toString().split(",");
//							String introductionName[] = jsonObjectShowDeatil.get("introductionName").toString().split(",");
							String introductionType[] = new String[100];
							if (jsonObjectShowDeatil.get("introductionType") != null) {
							   introductionType = jsonObjectShowDeatil.get("introductionType").toString().split(",");
							}
							if (introductionImg.length > 0) {

								for (int k = 0; k < introductionImg.length; k++) {
									if (introductionImg[k] == null || "".equals(introductionImg[k])) {
										continue;
									}
									IntroductionDto dto = new IntroductionDto();
									dto.setIntroductionImg(introductionImg[k]);
									if (jsonObjectShowDeatil.get("introductionType") != null) {
										dto.setIntroductionType(introductionType[k]);
									}
									
									
									IntroductionList.add(dto);
								}
							}
						}
						mv.addObject("IntroductionList", IntroductionList);
						
						Object shareImgObject = jsonObjectShowDeatil.get("shareImg")!=null?jsonObjectShowDeatil.get("shareImg"):"";
						String shareImg = "";
						if (!shareImgObject.equals("null")&&!shareImgObject.equals("")) {
							shareImg = jsonObjectShowDeatil.get("shareImg").toString();
						}
						mv.addObject("shareImg", shareImg);
						List<HouseShop> shopList = getHouseShop(null);
						mv.addObject("shopList",shopList);
						
						
						
						JSONArray arraySupporting = JSONArray.fromObject("[" + jsonObjectShowDeatil.get("supporting") + "]");// 配套
						for (int l = 0; l < arraySupporting.size(); l++) {
							net.sf.json.JSONObject jsonSupportingShowDeatil = arraySupporting.getJSONObject(l);
							String broadband = jsonSupportingShowDeatil.get("broadband") == null ? "0" : jsonSupportingShowDeatil.get("broadband").toString();
							String elevator = jsonSupportingShowDeatil.get("elevator") == null ? "0" : jsonSupportingShowDeatil.get("elevator").toString();
							String swimming = jsonSupportingShowDeatil.get("swimming") == null ? "0" : jsonSupportingShowDeatil.get("swimming").toString();
							String wifi = jsonSupportingShowDeatil.get("wifi") == null ? "0" : jsonSupportingShowDeatil.get("wifi").toString();

							String accesscard = jsonSupportingShowDeatil.get("accesscard") == null ? "0" : jsonSupportingShowDeatil.get("accesscard").toString();
							;// 门禁卡
							String securitystaff = jsonSupportingShowDeatil.get("securitystaff") == null ? "0" : jsonSupportingShowDeatil.get("securitystaff").toString();
							;// 保安
							String store = jsonSupportingShowDeatil.get("store") == null ? "0" : jsonSupportingShowDeatil.get("store").toString();
							;// 电梯
							String parking = jsonSupportingShowDeatil.get("parking") == null ? "0" : jsonSupportingShowDeatil.get("parking").toString();
							;// 停车位
							String gym = jsonSupportingShowDeatil.get("gym") == null ? "0" : jsonSupportingShowDeatil.get("gym").toString();
							;// 健身房
							String playground = jsonSupportingShowDeatil.get("playground") == null ? "0" : jsonSupportingShowDeatil.get("playground").toString();
							;// 儿童乐园
							String wheelchair = jsonSupportingShowDeatil.get("wheelchair") == null ? "0" : jsonSupportingShowDeatil.get("wheelchair").toString();
							;// 无障碍设施
							String buzzer = jsonSupportingShowDeatil.get("buzzer") == null ? "0" : jsonSupportingShowDeatil.get("buzzer").toString();
							;// 蜂鸣器/无线对讲机

							mv.addObject("broadband", broadband);
							mv.addObject("elevator", elevator);
							mv.addObject("swimming", swimming);
							mv.addObject("wifi", wifi);

							mv.addObject("accesscard", accesscard);
							mv.addObject("securitystaff", securitystaff);
							mv.addObject("store", store);
							mv.addObject("parking", parking);
							mv.addObject("gym", gym);
							mv.addObject("playground", playground);
							mv.addObject("wheelchair", wheelchair);
							mv.addObject("buzzer", buzzer);
						}

						JSONArray arrayOther = JSONArray.fromObject("[" + jsonObjectShowDeatil.get("other") + "]");// 其他
						for (int l = 0; l < arrayOther.size(); l++) {
							net.sf.json.JSONObject jsonOtherShowDeatil = arrayOther.getJSONObject(l);
							String cook = jsonOtherShowDeatil.get("cook").toString();
							String party = jsonOtherShowDeatil.get("party").toString();
							String smoking = jsonOtherShowDeatil.get("smoking").toString();

							String pet = jsonOtherShowDeatil.get("pet").toString();// 可带宠物
							String extrabed = jsonOtherShowDeatil.get("extrabed").toString();// 可加床
							String guests = jsonOtherShowDeatil.get("guests").toString();// 接待外宾
							String breakfast = jsonOtherShowDeatil.get("breakfast").toString();// 提供早餐
							String childrenstay = jsonOtherShowDeatil.get("childrenstay").toString();// 欢迎孩子入住

							mv.addObject("cook", cook);
							mv.addObject("party", party);
							mv.addObject("smoking", smoking);

							mv.addObject("pet", pet);
							mv.addObject("extrabed", extrabed);
							mv.addObject("guests", guests);
							mv.addObject("breakfast", breakfast);
							mv.addObject("childrenstay", childrenstay);
						}

						String sideoUrl = jsonObjectShowDeatil.get("videoUrl").toString();

						mv.addObject("houseType", houseType);// 房型
						mv.addObject("bedType", bedType);// 床型
						mv.addObject("isInvoice", isInvoice);// 是否要发票
						mv.addObject("introductionNum", introductionNum);
						mv.addObject("live", live);
						mv.addObject("telephone", telephone);
						mv.addObject("longitude", longitude);
						mv.addObject("latitude", latitude);
						mv.addObject("address", address);
						mv.addObject("checkdesc", checkdesc);
						mv.addObject("inSeasonDesc", inSeasonDesc);
						mv.addObject("stock", stock);
						mv.addObject("videoUrl", sideoUrl);
						mv.addObject("checkInTime", checkInTime);
						mv.addObject("checkOutTime", checkOutTime);
						mv.addObject("houseArea", houseArea);
						mv.addObject("flag", flag.replace("[", "").replace("]", ""));

					}
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
		List<CityInfo> cityInfoList = getCities();
		mv.addObject("cityInfoList", cityInfoList);
		List<BuildingsDto> buildingsList = getBuildings(Constant.BUILDING_TYPE_00);
		mv.addObject("buildings", buildingsList);
		List<DevelopersDto> devList = getDevelopers();
		mv.addObject("devList", devList);
		mv.setViewName("/operation/houseUpdate_new");
		return mv;
	}

	/**
	 * 更新特价状态
	 * @param session
	 * @param request
	 * @param resposne
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSpecialsStatus")
	public ModelAndView updateSpecialsStatus(HttpSession session, HttpServletRequest request, HttpServletResponse resposne) throws Exception {
		logger.info("start to updateSpecialsStatus");
		String specials_stauts = request.getParameter("specials_stauts");
		String houseId =request.getParameter("houseId");
		Map<String, Object> houseMap = new HashMap<String, Object>();
		houseMap.put("specials_stauts", specials_stauts);
		houseMap.put("houseId", houseId);
		houseMap.put("special_flag", 0);
		JSONObject result = HttpClientPostMethod.httpReqUrl(houseMap, "updateHouse");
		logger.info("end to updateSpecialsStatus");
		return toHouse(session, request);
	}
	
	private List<MarkInfo> getMarkInfoList() throws Exception {
//		EnumSet<Mark> currEnumSet = EnumSet.allOf(Mark.class);
		List<HouseTag> list = getHouseTags();
		List<MarkInfo> markList = new ArrayList<MarkInfo>();
		for (HouseTag houseTag : list) {
			MarkInfo info = new MarkInfo();
			info.setIndex(houseTag.getId().toString());
			info.setName(houseTag.getName());
			markList.add(info);

		}
		return markList;
	}

	@RequestMapping("/houseAddUpdate")
	public ModelAndView houseAddUpdate(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<CityInfo> cityInfoList = getCities();
		mv.addObject("cityInfoList", cityInfoList);
		mv.addObject("markList", getMarkInfoList());

		List<BuildingsDto> buildingsList = getBuildings(Constant.BUILDING_TYPE_00);
		mv.addObject("buildings", buildingsList);
		List<DevelopersDto> devList = getDevelopers();
		mv.addObject("devList", devList);
		
		List<HouseShop> shopList = getHouseShop(null);
		mv.addObject("shopList",shopList);
		
		mv.setViewName("/operation/houseAdd_new");
		return mv;
	}

	@RequestMapping("/baiduMap")
	public ModelAndView baiduMap(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/operation/baiduMap");
		return mv;
	}
	
	@RequestMapping("/toHouse")
	public ModelAndView toHouse(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String flag = request.getParameter("flag");
		String name = request.getParameter("name");
		mv.addObject("flag", flag);
		int citiesInt[] = null;
		String cities[] = request.getParameterValues("cities");
		if (cities != null) {
			citiesInt = new int[cities.length];
			for (int i = 0; i < cities.length; i++) {
				citiesInt[i] = Integer.valueOf(cities[i]);

			}
		}
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("cityIds", citiesInt);
		postData.put("name", name);
		String currentPageStr = request.getParameter("current_page") == null ? "1" : request.getParameter("current_page");
		Integer currentPage = Integer.valueOf(currentPageStr);
		postData.put("pageNum", currentPage);
		postData.put("pageCount", StringUtil.pageSize);

		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getHouses");
		List<HouseDto> houseList = new ArrayList<HouseDto>();
		String code = result.getString("code");
		Pagination pagination = new Pagination();
		if ("0".equals(code)) {
			JSONArray array = JSONArray.fromObject(result.get("data"));

			for (int i = 0; i < array.size(); i++) {
				net.sf.json.JSONObject houseObject = array.getJSONObject(i);
				String totalNum = houseObject.getString("totalNum");
				JSONArray arrayHouse = JSONArray.fromObject(houseObject.get("houses").toString());
				for (int j = 0; j < arrayHouse.size(); j++) {
					HouseDto dto = new HouseDto();
					net.sf.json.JSONObject jsonObject = arrayHouse.getJSONObject(j);
					String freezeAmt = jsonObject.get("freezeAmt").toString();
					String totalAmt = jsonObject.get("totalAmt").toString();
					String memFreezeAmt = jsonObject.get("memFreezeAmt").toString();
					String memTotalAmt = jsonObject.get("memTotalAmt").toString();
					String houseId = jsonObject.get("houseId").toString();
					String cityId = jsonObject.get("cityId").toString();
					String specials_stauts = jsonObject.get("specials_stauts").toString();
					dto.setCityId(cityId);
					dto.setFreezeAmt(memFreezeAmt);
					dto.setHouseId(houseId);
					dto.setMemFreezeAmt(freezeAmt);
					dto.setMemTotalAmt(memTotalAmt);
					dto.setTotalAmt(totalAmt);
					dto.setSpecials_stauts(Integer.parseInt(specials_stauts));
					JSONArray arraysummaryInfo = JSONArray.fromObject("[" + jsonObject.get("summaryInfo") + "]");

					for (int k = 0; k < arraysummaryInfo.size(); k++) {

						net.sf.json.JSONObject jsonObjectShowDeatil = arraysummaryInfo.getJSONObject(k);
						String houseName = jsonObjectShowDeatil.get("houseName").toString();
						String status = jsonObjectShowDeatil.get("status").toString();
						mv.addObject("houseName", houseName);
						mv.addObject("status", status);
						dto.setStatus(status);
						dto.setSummaryInfo(houseName);

					}

					// JSONArray arrayShowDetail =
					// JSONArray.fromObject(jsonObject.get("showDetail").toString());
					// for(int k = 0; k < arrayShowDetail.size(); k++){
					// net.sf.json.JSONObject jsonObject1 =
					// arrayShowDetail.getJSONObject(k);
					// String name= jsonObject1.get("name").toString();

					// }
					houseList.add(dto);
				}
				pagination.paging(Integer.valueOf(postData.get("pageNum").toString()), StringUtil.pageSize, Integer.valueOf(totalNum));
				pagination.setList(houseList);
			}
		}
		List<CityInfo> cityInfoList = getCities();
		for (CityInfo cityInfo : cityInfoList) {
			if (citiesInt != null) {
				for (int i = 0; i < citiesInt.length; i++) {
					int cityFlag = citiesInt[i];
					if (cityFlag == cityInfo.getCityId()) {
						cityInfo.setChecked(1);
					}

				}
			}
		}
		mv.addObject("cityInfoList", cityInfoList);
		mv.addObject("pager", pagination);
		mv.addObject("citiesInt", citiesInt);
		mv.addObject("name", name);
		mv.setViewName("/operation/houseList_new");
		return mv;
	}

	@RequestMapping("/toHouseDateList")
	public ModelAndView toHouseDateList(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Integer houseId = Integer.valueOf(request.getParameter("houseId"));
		String dates[] = request.getParameterValues("dates");
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("houseId", houseId);
		if (dates != null) {
			postData.put("dates", dates);
		}
		mv.addObject("houseId", houseId);
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getHousePrice");
		List<HouseDto> houseList = new ArrayList<HouseDto>();
		String code = result.getString("code");

		if ("0".equals(code)) {
			com.meidusa.fastjson.JSONArray arrayHouse = result.getJSONObject("data").getJSONArray("prices");
			for (int j = 0; j < arrayHouse.size(); j++) {
				HouseDto dto = new HouseDto();
				JSONObject jsonObject = arrayHouse.getJSONObject(j);
				dto.setHouseId(houseId.toString());
				dto.setDate(DateUtil.format(jsonObject.getDate("date")));
				dto.setMemFreezeAmt(jsonObject.getString("memFreezeAmt"));
				dto.setMemTotalAmt(jsonObject.getString("memTotalAmt"));
				dto.setTotalAmt(jsonObject.getString("totalAmt"));
				dto.setFreezeAmt(jsonObject.getString("freezeAmt"));
				dto.setInSeason(jsonObject.getBooleanValue("inSeason"));
				houseList.add(dto);
			}
		}
		mv.addObject("houseList", houseList);
		mv.setViewName("/operation/houseDateList");
		return mv;
	}

	@RequestMapping("/toAddHouseDateList")
	public ModelAndView toAddHouseDateList(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Integer houseId = Integer.valueOf(request.getParameter("houseId"));
		mv.addObject("houseId", houseId);
		mv.setViewName("/operation/addHouseDateList");
		return mv;
	}

	@RequestMapping("/toUpdateHouseDateList")
	public ModelAndView toUpdateHouseDateList(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Integer houseId = Integer.valueOf(request.getParameter("houseId"));
		String dates[] = request.getParameterValues("dates");
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("houseId", houseId);
		if (dates != null) {
			postData.put("dates", dates);
		}
		mv.addObject("houseId", houseId);
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getHousePrice");
		String code = result.getString("code");
		if ("0".equals(code)) {
			com.meidusa.fastjson.JSONArray arrayHouse = result.getJSONObject("data").getJSONArray("prices");
			for (int j = 0; j < arrayHouse.size(); j++) {
				HouseDto dto = new HouseDto();
				JSONObject jsonObject = arrayHouse.getJSONObject(j);
				dto.setHouseId(houseId.toString());
				dto.setDate(jsonObject.getString("date"));
				dto.setMemTotalAmt(jsonObject.getString("memTotalAmt"));
				dto.setMemFreezeAmt(jsonObject.getString("memFreezeAmt"));
				dto.setTotalAmt(jsonObject.getString("totalAmt"));
				dto.setFreezeAmt(jsonObject.getString("freezeAmt"));
				dto.setInSeason(jsonObject.getBooleanValue("inSeason"));
				mv.addObject("housePrice", dto);
			}
		}
		mv.addObject("houseId", houseId);
		mv.setViewName("/operation/updateHouseDateList");
		return mv;
	}

	@RequestMapping("/addHouseDateList")
	public ModelAndView addHouseDateList(HttpSession session, HttpServletRequest request) throws Exception {
		Integer houseId = Integer.valueOf(request.getParameter("houseId"));
		Integer memTotalAmt = Integer.valueOf(request.getParameter("memTotalAmt"));
		Integer memFreezeAmt = Integer.valueOf(request.getParameter("memFreezeAmt"));
		Integer totalAmt = Integer.valueOf(request.getParameter("totalAmt"));
		Integer freezeAmt = Integer.valueOf(request.getParameter("freezeAmt"));
		String date = request.getParameter("date");
		String dayStr = request.getParameter("day");
		boolean inSeason = Boolean.valueOf(request.getParameter("inSeason"));
		Map<String, Object> postData = new HashMap<String, Object>();
		if (dayStr != null) {
			Integer day = Integer.valueOf(dayStr);
			String dates[] = new String[day + 1];
			for (int i = 0; i < day + 1; i++) {
				Calendar calendar = Calendar.getInstance();
				// 初始化 Calendar 对象，但并不必要，除非需要重置时间
				calendar.setTime(sdf.parse(date));
				calendar.add(Calendar.DAY_OF_MONTH, i);

				Date time = calendar.getTime();
				String dateStr = sdf.format(time);
				dates[i] = dateStr;
			}
			postData.put("dates", dates);
		} else {
			postData.put("date", date);
		}
		postData.put("houseId", houseId);
		postData.put("memTotalAmt", memTotalAmt);
		postData.put("memFreezeAmt", memFreezeAmt);
		postData.put("totalAmt", totalAmt);
		postData.put("houseId", houseId);
		postData.put("freezeAmt", freezeAmt);
		postData.put("inSeason", inSeason);

		String flag = request.getParameter("flag");
		if ("update".equals(flag)) {
			HttpClientPostMethod.httpReqUrl(postData, "updateHousePrice");
		} else {
			HttpClientPostMethod.httpReqUrl(postData, "addHousePrice");
		}
		return toHouseDateList(session, request);
	}
	
	@RequestMapping("/getHousesBybldIds")
	public ModelAndView getHousesBybldIds(HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws Exception{
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String provId = request.getParameter("buildId");
		List<Integer> bldIds = new ArrayList<Integer>();
		if (null!=provId&&!("").equals(provId)) {
			bldIds.add(Integer.parseInt(provId));
		}
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("bldIds", bldIds);
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getHouses");
		JSONArray array = JSONArray.fromObject(result.get("data")); 

		RestResponse restP = new RestResponse();
		Map<String , Object> infoMap = new HashMap<String , Object>();
		infoMap.put("list", array.getJSONObject(0));  
		restP.setData(infoMap);

		JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);
		return null;
	}

	/**
	 * 同步房源到
	 * @throws Exception
	 */
	@RequestMapping("/syncDetailToMango")
	public ModelAndView syncDetailToMango(HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws Exception{
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("sync", "1");
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getHouses");
		String code = result.getString("code");
		String msg = result.get("msg").toString();
		RestResponse restP = new RestResponse();
		Map<String , Object> infoMap = new HashMap<String , Object>();
		infoMap.put("code", code);
		infoMap.put("msg", msg);
		restP.setData(infoMap);

		JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);
		return null;
	}
	
	//同步房源搜索
	@RequestMapping("/syncSearchToMango")
	public ModelAndView syncSearchToMango(HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws Exception{
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("syncSearch", "1");
		JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "getHouses");
		String code = result.getString("code");
		String msg = result.get("msg").toString();
		JSONObject jsonObject = result.getJSONObject("data");
		int success_count = jsonObject.getIntValue("success_count");
		int false_count = jsonObject.getIntValue("false_count");

		RestResponse restP = new RestResponse();
		Map<String , Object> infoMap = new HashMap<String , Object>();
		infoMap.put("code", code);
		infoMap.put("msg", msg);
		infoMap.put("message", "同步成功"+success_count+"条，失败"+false_count+"条!");
		restP.setData(infoMap);

		JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);
		return null;
	}
	
	@RequestMapping("/toComment")
	public String toComment(int houseId, Model model)
	{
		model.addAttribute("houseId", houseId);
		return "/comment/commentAdd";
	}
	
	@RequestMapping("/addComment")
	public String addComment(HttpServletRequest request, HttpServletResponse response, OrderCommentVo vo)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try 
    	{
    		//校验评分
    		if (vo.getScore() <= 0) 
    		{
    			resultMap.put("code", 2);
        		resultMap.put("msg", "请评分");
        		logger.error("请评分");
        		return "";
			}
    		
    		//校验评论内容
    		if (StringUtils.isEmpty(vo.getComments()))
    		{
    			resultMap.put("code", 2);
        		resultMap.put("msg", "请填写评论内容");
        		logger.error("请填写评论内容");
        		return "";
			}
    		
    		vo.setImages(getImageUrl(request));
    		vo.setStatus(1);
    		vo.setUserPhone(getRandomPhone());
    		vo.setCreateTime(String.valueOf(System.currentTimeMillis()));
    		
    		Map<String,Object> param = new HashMap<String,Object>();
    		param.put("orderComment", vo);
    		JSONObject result = HttpClientPostMethod.httpDataReqUrl(param, "addOrderComment");
    		resultMap.put("code", result.getShort("code"));
    		resultMap.put("msg", result.get("msg"));
		} 
    	catch (Exception e) 
    	{
    		resultMap.put("code", 2);
    		resultMap.put("msg", "添加失败");
    		
    		logger.error("failed to add comment", e);
		}
    	
    	return "redirect:toHouse.shtml";
    	
	}
	
	private List<String> getImageUrl(HttpServletRequest request)
	{
    	if (logger.isInfoEnabled())
    	{
			logger.info("start to run importCommentImg()");
		}
    	
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) 
		{
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("images");
			int size = iter.size();
			
			if (logger.isDebugEnabled())
			{
				logger.debug("recieved request to upload images, count is " + size);
			}
			
			List<String> images = new ArrayList<String>();
			for (int i = 0; i < size; i++)
			{
				String url = MsgPropertiesUtils.getUploadHouseUrl();
				HessianProxyFactory factory = new HessianProxyFactory();
				try
				{
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
					FileUploadRequest req = new FileUploadRequest();
					req.setSource("妈妈送房网");
					req.setFileType("jpg");// jpg 、png
					req.setGroupName("group1");
					req.setFile(iter.get(i).getBytes());

					FileUploadResponse fileResponse = facade.uploadFile(req);
					
					String imgUrl = fileResponse.getFileUrl();
					if (logger.isDebugEnabled())
					{
						logger.debug("upload image successfully, url is " + imgUrl);
					}
					
					images.add(imgUrl);
					
				}
				catch (Exception e) 
				{
					logger.error("failed to upload the imags.{}", e);
				}

			}
			
			return images;
		}
		
		return null;
	}
	
	private String getRandomPhone()
	{
		int index = (int)(Math.random() * (telFirst.length));
		String first3 = telFirst[index];
		String second4 = String.valueOf((int)(Math.random() * 10000 + 10000)).substring(1);
				
		return first3 + "****" + second4;
	}
	
	public static long getRandomDate()
	{
		long peroid = 3 * 30 * 24 * 60 * 60 * 1000;
		long endDate = System.currentTimeMillis();
		long date = endDate + (long) (Math.random() * peroid);
		return date;
	}
	
}