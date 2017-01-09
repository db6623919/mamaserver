package com.console.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.console.dto.BuildingsDto;
import com.console.dto.CityInfo;
import com.console.dto.HouseDto;
import com.console.entity.PerdayHousePrice;
import com.console.entity.TSysRole;
import com.console.entity.TSysUser;
import com.console.enumDto.HousePriceDto;
import com.console.framework.dao.Pagination;
import com.console.service.RoleService;
import com.console.util.Constant;
import com.console.util.DateUtil;
import com.console.util.HttpClientPostMethod;
import com.console.util.RestResponse;
import com.console.vo.HouseOrderVO;
import com.console.vo.OrderInfoVO;
import com.mama.server.common.errorCode.ReturnCode;

@Controller
@RequestMapping("/order")
public class OrderInfoAction extends BaseController {

	@Autowired
	private RoleService roleService;

	//private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping("/toAudtingInfo")
	public ModelAndView toAudtingInfo(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String orderId = request.getParameter("orderId");
		String type = request.getParameter("type");
		String[] orderIdInt = new String[1];
		orderIdInt[0] = orderId;

		Integer bldIds[] = new Integer[1];

		bldIds[0] = user.getDevId();

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderIds", orderIdInt);

		selectOrderList(request, mv, postData);

		List<OrderInfoVO> list = (List<OrderInfoVO>) mv.getModel().get("orderList");
		OrderInfoVO order = list.get(0);
		List<String> datelist = DateUtil.getDateList(order.getBeginTime(), order.getEndTime());
		datelist.remove(datelist.size()-1);
		String houseId = order.getHouseId();
		postData.clear();
		postData.put("houseId", Integer.valueOf(houseId));
		postData.put("dates", datelist.toArray());
		postData.put("roomNum", Integer.valueOf(order.getTotalRoomNum()));
		List<HousePriceDto> halist = checkHouseDate(postData);
		List<PerdayHousePrice> hplist = (List<PerdayHousePrice>)mv.getModel().get("housePriceList");
		halist =  convertHousePriceList(halist,hplist);
		if ("1".equals(type) ) {
			mv.addObject("halist", halist);
		}else if ("2".equals(type) || "3".equals(type) ) {
			for (int i = 0; i < hplist.size(); i++) {
				PerdayHousePrice perdayHousePrice = new PerdayHousePrice();
				perdayHousePrice = hplist.get(i);
				if (perdayHousePrice.getPresentPrice()==0) {
					perdayHousePrice.setPresentPrice(perdayHousePrice.getOriginalPrice());
				}
			}
			mv.addObject("halist", hplist);
		}
//		mv.addObject("halist", halist);
		mv.addObject("size", halist.size());
        mv.addObject("type", type);
		mv.setViewName("/order/orderInfo_new");
		return mv;
	}
	
	/*
	 * 更新订单livedetail字段
	 */
	@RequestMapping("/updateOrderLiveDetail")
	public ModelAndView updateOrderLiveDetail(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String orderId = request.getParameter("orderId");
		String jsonArray = request.getParameter("jsonArray");
		String user_phone = request.getParameter("user_phone");
		String realInSeasonDays = request.getParameter("realInSeasonDays");
		
		JSONObject orderObjectObject = JSONObject.fromObject(jsonArray);
		JSONArray jsArray = JSONArray.fromObject(orderObjectObject);
		
		String[] ld = jsonArray.split(";");
		List<PerdayHousePrice> pHplist = new ArrayList<PerdayHousePrice>();
		for (int i = 0; i < ld.length; i++) {
			JSONObject order = JSONObject.fromObject(ld[i]);
			PerdayHousePrice pHousePrice = new PerdayHousePrice();
			String presentPrice = order.getString("presentPrice");
			String update_resson = order.getString("update_resson");
			String originalPrice = order.getString("originalPrice");
			if (""!=presentPrice  && !"".equals(presentPrice)) {
				pHousePrice.setPresentPrice(Integer.parseInt(presentPrice));
				pHousePrice.setUpdate_resson(update_resson);
				pHousePrice.setOriginalPrice(Integer.parseInt(originalPrice));
				pHousePrice.setDate(order.getString("data"));
			}else {
				pHousePrice.setPresentPrice(Integer.parseInt(originalPrice));
				pHousePrice.setUpdate_resson(update_resson);
				pHousePrice.setOriginalPrice(Integer.parseInt(originalPrice));
				pHousePrice.setDate(order.getString("data"));
			}
			pHplist.add(pHousePrice);
		}

		String[] orderIdInt = new String[1];
		orderIdInt[0] = orderId;

		Integer bldIds[] = new Integer[1];

		bldIds[0] = user.getDevId();

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderIds", orderIdInt);
		selectOrderList(request, mv, postData);

		List<OrderInfoVO> list = (List<OrderInfoVO>) mv.getModel().get("orderList");
		
		OrderInfoVO order = list.get(0);
		List<String> datelist = DateUtil.getDateList(order.getBeginTime(), order.getEndTime());
		String houseId = order.getHouseId();
		postData.clear();
		postData.put("houseId", Integer.valueOf(houseId));
		postData.put("dates", datelist.toArray());
		postData.put("roomNum", Integer.valueOf(order.getTotalRoomNum()));
		
		JSONObject jObject =  (JSONObject) mv.getModel().get("liveDetail");
		//旅居券
		String hotelCouponIds = jObject.getString("hotelCouponIds");
		String[] hcids = hotelCouponIds.split(",");
		
		List<PerdayHousePrice> hplist = (List<PerdayHousePrice>)mv.getModel().get("housePriceList");
		List<PerdayHousePrice> phlist = new ArrayList<PerdayHousePrice>();
		PerdayHousePrice perdayHouspPricee = new PerdayHousePrice();
		int addPirce = 0;//价格差异
		for (int i = 0; i < hplist.size(); i++) {
			PerdayHousePrice perdayHouse = new PerdayHousePrice();
			perdayHouse = hplist.get(i);
			for (int j = 0; j < pHplist.size(); j++) {
				PerdayHousePrice price = new PerdayHousePrice();
				price = pHplist.get(j);
				if (perdayHouse.getDate().equals(price.getDate())) {
					perdayHouse.setPresentPrice(price.getPresentPrice());
					perdayHouse.setUpdate_resson(price.getUpdate_resson());
					perdayHouspPricee = perdayHouse;
				}
			}	
			perdayHouse.setPresentPrice(perdayHouse.getPresentPrice()==null?perdayHouse.getOriginalPrice():perdayHouse.getPresentPrice());
			phlist.add(perdayHouse);
		}
		
		//排序
		Collections.sort(phlist);
		int hc_price = 0;//旅居券抵扣价格
		List<PerdayHousePrice> pList = new ArrayList<PerdayHousePrice>();
		
		
		if (Integer.parseInt(realInSeasonDays)>0) {
			for (int i = phlist.size()-1; i>=phlist.size()-Integer.parseInt(realInSeasonDays); i--) {
				PerdayHousePrice perdayHouse = new PerdayHousePrice();
				perdayHouse = phlist.get(i);
				hc_price+=perdayHouse.getOriginalPrice();
				pList.add(perdayHouse);
			}
			for (PerdayHousePrice perdayHousePrice :pList) {
				hplist.remove(perdayHousePrice);
			}
		}

		for (PerdayHousePrice price:hplist) {
			addPirce += price.getPresentPrice();
		}

		String phone = jObject.getString("phone");
		jObject.put("list", pHplist);
		Map<String, Object> postdataMap = new HashMap<String, Object>();
		postdataMap.put("orderId", orderId);
		String str = jObject.toString();
		postdataMap.put("liveDetail", jObject.toString());
		postdataMap.put("phone", phone);
		postdataMap.put("presentPrice", String.valueOf(perdayHouspPricee.getPresentPrice()));//现价
		postdataMap.put("originalPrice", String.valueOf(perdayHouspPricee.getOriginalPrice()));//原价
		postdataMap.put("data", perdayHouspPricee.getDate());//日期
		postdataMap.put("houseName",new String(request.getParameter("houseName").getBytes("iso8859-1"),"utf-8"));//房源
		postdataMap.put("totalAmt", addPirce);//当前预定总价格
		postdataMap.put("user_phone", user_phone);
		
		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpCustReqUrl(postdataMap, "updateOrderLiveDetail");

		List<HousePriceDto> halist = (List<HousePriceDto>)mv.getModel().get("updateOrderLiveDetail");

		mv.addObject("halist", halist);
		mv.setViewName("/order/orderInfo_new");
		if (0 == result.getInteger("code")) {//更新liveDetail成功
			logger.info("updateOrderLiveDetail()订单：" + orderId + " 价格修改成功！");
		}else {
			logger.info("updateOrderLiveDetail()订单：" + orderId + " 价格修改失败！");
		}
		return audtingInfo(session,request);
	}

	@RequestMapping("/audtingInfo")
	public ModelAndView audtingInfo(HttpSession session, HttpServletRequest request) {
		try {
			String orderId = request.getParameter("orderId");
			String orderPhone = request.getParameter("orderPhone");
			String pay_type = request.getParameter("pay_type");
			String user_phone = request.getParameter("user_phone");
			String cancelOrdeReason = new String(request.getParameter("cancelOrdeReason").getBytes("iso8859-1"),"utf-8");
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("orderId", orderId);
			postData.put("orderPhone", orderPhone);
			postData.put("pay_type", pay_type);
			Integer status = Integer.valueOf(request.getParameter("status"));
			postData.put("status", status);
			postData.put("cancelOrdeReason", cancelOrdeReason);
			postData.put("user_phone", user_phone);
			com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpMerchReqUrl(postData, "updateOrderStatus");

			if (0 == result.getInteger("code")) {
				if (status.equals(11)) {
					request.setAttribute("successMessage", "订单：" + orderId + " 审核通过，等待客户入住");
					logger.info("订单：" + orderId + " 审核通过，等待客户入住");
				} else if (status.equals(23)) {
					request.setAttribute("successMessage", "订单：" + orderId + " 已被取消，订单预订失败");
					logger.info("订单：" + orderId + " 已被取消，订单预订失败");
				} else if (status.equals(24)) {
					request.setAttribute("successMessage", "订单：" + orderId + " 已被取消，订单预订失败");
					logger.info("订单：" + orderId + " 已被取消，订单预订失败");
				}else if (status.equals(15)) {
					request.setAttribute("successMessage", "订单：" + orderId + " 价格已被修改，请确认！");
					logger.info("订单：" + orderId + " 价格已被修改，请确认！");
				}else if (status.equals(9)) {
					request.setAttribute("successMessage", "订单：" + orderId + " 审核通过，待用户支付");
					logger.info("订单：" + orderId + "  审核通过，待用户支付");
				}   
				else {
					request.setAttribute("errorMessage", "订单：" + orderId + " 操作异常");
					logger.error("订单：" + orderId + " 订单状态异常， status=" + status);
				}
				return new ModelAndView("redirect:/order/toBookOrderList.shtml");
			} else {
				logger.error("审核订单：" + orderId + "出错，错误信息：" + result.getString("msg"));
				ModelAndView mv = new ModelAndView();
				mv.addObject("message", "审核订单入住操作失败！ 错误信息" + result.getString("msg"));
				mv.setViewName("/common/error_new");
				return mv;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("audtingInfo :", e);
		}
		return null; 
	}

	@RequestMapping("/toOrderList")
	public ModelAndView toOrderList(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> postData = new HashMap<String, Object>();
		Integer status = Integer.valueOf(request.getParameter("status"));
		Integer[] statusInt = new Integer[1];
		statusInt[0] = status;
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		Integer bldIds[] = new Integer[1];
		TSysRole role = roleService.getRole(user);

		Map<String, Object> phoneData = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		if (StringUtils.isNotBlank(phone)) {
			String uid = "";
			phoneData.put("phone", phone);
			com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpReqUrl(phoneData, "getUserInfo");
			String code = result.getString("code");

			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					uid = jsonObject.getString("uid");
					postData.put("uid", uid);
				}
				mv.addObject("phone", phone);

				postData.put("uid", uid);
			} else {
				mv.addObject("errorMessage", "手机号码不在列表中");
			}
		}

		bldIds[0] = user.getDevId();
		postData.put("statuses", statusInt);
		if (role.getId().compareTo(Constant.ADMIN_ROLE_ID) != 0) {
			postData.put("devIds", bldIds);
		}
		// 增加分页参数

		logger.info("查询会员订单列表记录请求参数,postData{}" + postData);
		selectOrderList(request, mv, postData);
		mv.addObject("status", request.getParameter("status"));
		mv.setViewName("/order/orderList");
		return mv;
	}

	private void selectOrderList(HttpServletRequest request, ModelAndView mv, Map<String, Object> postData)
			throws Exception {
		List<OrderInfoVO> orderList = new ArrayList<OrderInfoVO>();
		List<HousePriceDto> housePriceList = new ArrayList<HousePriceDto>();
		Pagination pager = new Pagination();
		int pageNum = request.getParameter("current_page") == null ? 1 : Integer.parseInt(request
				.getParameter("current_page"));
		postData.put("pageNum", pageNum);
		postData.put("pageCount", Constant.PAGE_SIZE);
		com.meidusa.fastjson.JSONObject result = null;
		try {
			result = HttpClientPostMethod.httpCustReqUrl(postData, "getOrders");
			logger.info("查询会员订单列表记录返回结果,result{}" + result);
			if (result != null) {
				JSONObject orderObjectObject = JSONObject.fromObject(result.getString("data"));
				JSONArray orderArray = JSONArray.fromObject(orderObjectObject.getString("orders"));
				for (int i = 0; i < orderArray.size() && orderArray != null; i++) {
					JSONObject order = orderArray.getJSONObject(i);
					OrderInfoVO orderInfoVO = new OrderInfoVO();
					orderInfoVO.setOrderId(order.getString("orderId"));
					orderInfoVO.setFreezeAmt(order.getString("freezeAmt"));
					orderInfoVO.setTotalAmt(order.getString("totalAmt"));
					orderInfoVO.setHouseId(order.getString("houseId"));
					orderInfoVO.setStatus(order.getString("status"));
					orderInfoVO.setVerifyCode(order.getString("verifyCode"));
					orderInfoVO.setTotalRoomNum(order.get("totalRoomNum").toString());
					orderInfoVO.setPayAmt(order.getString("payAmt"));
					Object object_pay_type = order.getString("pay_type");
					if(null!=object_pay_type){
						orderInfoVO.setPay_type(order.getString("pay_type"));
					}
					
					JSONObject houseObject = JSONObject.fromObject(order.getString("liveDetail"));
					String houseName = "";
					if (houseObject.containsKey("houseName")) {
						houseName = houseObject.getString("houseName");
					}
					
//                    String bulidPhone = houseObject.getString("phone");//房源电话
                    String bulidPhone ="";
					if (null!=houseObject.get("phone")) {
						bulidPhone = houseObject.getString("phone");
					}
					String endTime = "endTime";
					if (houseObject.containsKey("endTime")) {
						endTime = houseObject.getString("endTime");
					}
					String beginTime = "beginTime";
					if (houseObject.containsKey("beginTime")) {
						beginTime = houseObject.getString("beginTime");
					}
					String linkmanName = "";
					if (houseObject.containsKey("linkmanName")) {
						linkmanName = houseObject.getString("linkmanName");
					}
					String linkmanPhone = "";
					if (houseObject.containsKey("linkmanPhone")) {
						linkmanPhone = houseObject.getString("linkmanPhone");
					}
					
					String user_phone ="";
					if (null!=houseObject.get("user_phone")) {
						 user_phone = houseObject.getString("user_phone");
					}
					String realInSeasonDays = "";
					if (null!= houseObject.get("realInSeasonDays")) {
						realInSeasonDays = houseObject.getString("realInSeasonDays");
					}
					String cancelOrdeReason = "";
					if (null!=order.get("cancelOrdeReason")) {
						cancelOrdeReason = order.getString("cancelOrdeReason");
					}
					
					Object Object = houseObject.get("list");
					mv.addObject("liveDetail", houseObject); 
					if (null!=Object) {
						housePriceList = (List)JSONArray.toList(houseObject.getJSONArray("list"), PerdayHousePrice.class); 
					}
					
					String uid = order.getString("uid");
					orderInfoVO.setUserName(linkmanName);
					orderInfoVO.setPhone(linkmanPhone);
					orderInfoVO.setBeginTime(beginTime);
					orderInfoVO.setEndTime(endTime);
					orderInfoVO.setHouseName(houseName);
					orderInfoVO.setUid(uid);
					orderInfoVO.setUser_phone(user_phone);
					orderInfoVO.setBuildPhone(bulidPhone);
					orderInfoVO.setCancelOrdeReason(cancelOrdeReason);
					orderInfoVO.setRealInSeasonDays(realInSeasonDays);
					String operTime = order.getString("operTime");
					Date operDate = new SimpleDateFormat("yyyy-MM-dd").parse(operTime);
					operTime = new SimpleDateFormat("yyyy-MM-dd").format(operDate);
					orderInfoVO.setOperTime(operTime);
					Map<String, Object> houseData = new HashMap<String, Object>();
					houseData.put("houseId", Integer.valueOf(order.getString("houseId")));
					com.meidusa.fastjson.JSONObject houseresult = HttpClientPostMethod.httpCustReqUrl(houseData,
							"getHouseInfo");
					JSONArray array = JSONArray.fromObject(houseresult.get("data"));
					for (int j = 0; j < array.size(); j++) {
						net.sf.json.JSONObject jsonObject = array.getJSONObject(j);
						String devId = jsonObject.get("devId").toString();
						String bldId = jsonObject.get("bldId").toString();
						String cityId = jsonObject.get("cityId").toString();
						String room = jsonObject.get("room").toString();
						orderInfoVO.setRoom(room.substring(0, 1));
						orderInfoVO.setHall(room.substring(1, 2));
						orderInfoVO.setHealth(room.substring(2, 3));
						orderInfoVO.setBed(room.substring(3, 4));
						orderInfoVO.setDevId(devId);
						orderInfoVO.setBldId(bldId);
						orderInfoVO.setCityId(cityId);
						net.sf.json.JSONObject showDetailJsonObject = JSONObject.fromObject(jsonObject.optString("showDetail"));
						orderInfoVO.setHouseType(showDetailJsonObject.optInt("houseType"));
					}
					orderList.add(orderInfoVO);
				}
				pager.setList(orderList);
				pager.paging(pageNum, Constant.PAGE_SIZE, orderObjectObject.getInt("totalNum"));
			}
			logger.info("查询会员订单列表记录返回到页面的结果,orderList{}" + orderList);
		} catch (Exception e) {
			logger.info("查询会员订单列表记录出现异常，异常为：" + e.getMessage());
			e.printStackTrace();
		}
		mv.addObject("pager", pager);
		mv.addObject("orderList", orderList);
		List<CityInfo> cityList = getCities();
		List<BuildingsDto> buildList = getBuildings();
		mv.addObject("cityList", cityList);
		mv.addObject("buildList", buildList);
		mv.addObject("housePriceList",housePriceList);
	}

	@RequestMapping("/toCancel")
	public ModelAndView toCancel(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String orderId = request.getParameter("orderId");
		String[] orderIdInt = new String[1];
		orderIdInt[0] = orderId;

		Integer bldIds[] = new Integer[1];

		bldIds[0] = user.getDevId();

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderIds", orderIdInt);
		// postData.put("devIds", bldIds);

		selectOrderList(request, mv, postData);
		mv.addObject("flag", "cancel");
		mv.setViewName("/order/orderUpdate_new");
		return mv;
	}

	@RequestMapping("/toDeparture")
	public ModelAndView toDeparture(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String orderId = request.getParameter("orderId");
		String[] orderIdInt = new String[1];
		orderIdInt[0] = orderId;

		Integer bldIds[] = new Integer[1];

		bldIds[0] = user.getDevId();

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderIds", orderIdInt);
		// postData.put("devIds", bldIds);

		selectOrderList(request, mv, postData);
		mv.addObject("flag", "depart");
		mv.setViewName("/order/departInfo");
		return mv;
	}

	@RequestMapping("/departure")
	public ModelAndView departure(HttpSession session, HttpServletRequest request) throws Exception {
		String orderId = request.getParameter("orderId");
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderId", orderId);
		Integer status = Integer.valueOf(request.getParameter("status"));
		postData.put("status", status);
		HttpClientPostMethod.httpMerchReqUrl(postData, "updateOrderStatus");
		return toOrderList(session, request);
	}

	@RequestMapping("/cancel")
	public ModelAndView cancel(HttpSession session, HttpServletRequest request) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		Integer status = Integer.valueOf(request.getParameter("status"));
		postData.put("orderId", orderId);
		postData.put("status", status);
		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpMerchReqUrl(postData, "updateOrderStatus");
		if (0 == result.getInteger("code")) {
			request.setAttribute("successMessage", "订单：" + orderId + " 已取消");
			logger.info("订单：" + orderId + " 已被商户取消");
			return toManageOrderList(session, request);
		} else {
			logger.error("取消订单：" + orderId + "出错，错误信息：" + result.getString("msg"));
			ModelAndView mv = new ModelAndView();
			mv.addObject("message", "客户入住操作失败！ 错误信息" + result.getString("msg"));
			mv.setViewName("/common/error_new");
			return mv;
		}
	}

	@RequestMapping("/toCheckin")
	public ModelAndView toCheckin(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String orderId = request.getParameter("orderId");
		String[] orderIdInt = new String[1];
		orderIdInt[0] = orderId;

		Integer bldIds[] = new Integer[1];

		bldIds[0] = user.getDevId();

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderIds", orderIdInt);
		// postData.put("devIds", bldIds);

		selectOrderList(request, mv, postData);
		mv.addObject("flag", "checkin");
		mv.setViewName("/order/orderUpdate_new");
		return mv;
	}

	@RequestMapping("/checkin")
	public ModelAndView checkin(HttpSession session, HttpServletRequest request) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		Integer status = Integer.valueOf(request.getParameter("status"));
		postData.put("orderId", orderId);
		postData.put("status", status);
		postData.put("verifyCode", request.getParameter("verifyCode"));
		postData.put("orderPhone", request.getParameter("orderPhone"));
		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpMerchReqUrl(postData, "updateOrderStatus");
		if (0 == result.getInteger("code")) {
			request.setAttribute("successMessage", "订单：" + orderId + " 成功办理入住");
			logger.info("订单：" + orderId + " 成功办理入住");
			return toManageOrderList(session, request);
		} else {
			logger.error("订单：" + orderId + "办理入住出错，错误信息：" + result.getString("msg"));
			ModelAndView mv = new ModelAndView();
			mv.addObject("message", "办理客户入住操作失败！ 错误信息" + result.getString("msg"));
			mv.setViewName("/common/error_new");
			return mv;
		}
	}

	@RequestMapping("/toProlong")
	public ModelAndView toProlong(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String orderId = request.getParameter("orderId");

		String[] orderIdInt = new String[1];
		orderIdInt[0] = orderId;

		Integer bldIds[] = new Integer[1];

		bldIds[0] = user.getDevId();

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderIds", orderIdInt);
		// postData.put("devIds", bldIds);

		selectOrderList(request, mv, postData);

		List<OrderInfoVO> list = (List<OrderInfoVO>) mv.getModel().get("orderList");
		OrderInfoVO order = list.get(0);
		String endTime = order.getEndTime();
		List<String> dateList = DateUtil.getDateList(endTime, DateUtil.modify(endTime, 60));

		mv.addObject("dateList", dateList);
		mv.addObject("flag", "prolong");
		mv.setViewName("/order/orderUpdate_new");
		return mv;
	}

	@RequestMapping("/prolong")
	public ModelAndView prolong(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();

		Map<String, Object> postData = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		// String numofday = request.getParameter("numOfDay");
		String newEndTime = request.getParameter("date");

		// 取出订单信息
		// JSONArray orders = new JSONArray();
		// orders.add(orderId);
		// postData.put("orderIds", orders);
		// selectOrderList(request, mv, postData);
		// List<OrderInfoVO> orderlist = (List<OrderInfoVO>)
		// mv.getModel().get("orderList");
		// OrderInfoVO orderInfo = orderlist.get(0);

		// // 计算新的结束时间
		// Date date = new
		// SimpleDateFormat("yyyy-MM-dd").parse(orderInfo.getEndTime());
		// Calendar c = Calendar.getInstance();
		// c.setTime(date);
		// c.add(Calendar.DATE, Integer.parseInt(numofday));
		// String newEndTime = new
		// SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

		// 调用续房接口
		Map<String, Object> pData = new HashMap<String, Object>();
		pData.put("endTime", newEndTime);
		pData.put("orderId", orderId);

		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpMerchReqUrl(pData, "continueOrder");

		if (0 == result.getInteger("code")) {
			request.setAttribute("successMessage", "订单：" + orderId + " 成功办理续房,离店日期为" + newEndTime);
			logger.info("订单：" + orderId + " 成功办理续房，离店日期为" + newEndTime);
			return toManageOrderList(session, request);
		} else {
			logger.error("订单：" + orderId + "办理续房出错，错误信息：" + result.getString("msg"));
			ModelAndView rmv = new ModelAndView();
			rmv.addObject("message", "客户续房操作失败！ 错误信息" + result.getString("msg"));
			rmv.setViewName("/common/error_new");
			return rmv;
		}

	}

	@RequestMapping("/toCheckout")
	public ModelAndView toCheckout(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String orderId = request.getParameter("orderId");
		String[] orderIdInt = new String[1];
		orderIdInt[0] = orderId;

		Integer bldIds[] = new Integer[1];

		bldIds[0] = user.getDevId();

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderIds", orderIdInt);
		// postData.put("devIds", bldIds);

		selectOrderList(request, mv, postData);
		mv.addObject("flag", "checkout");
		mv.setViewName("/order/orderUpdate_new");
		return mv;
	}

	@RequestMapping("/checkout")
	public ModelAndView checkout(HttpSession session, HttpServletRequest request) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		Integer status = Integer.valueOf(request.getParameter("status"));
		postData.put("orderId", orderId);
		postData.put("status", status);
		postData.put("orderPhone", request.getParameter("orderPhone"));

		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpMerchReqUrl(postData, "updateOrderStatus");

		if (0 == result.getInteger("code")) {
			request.setAttribute("successMessage", "订单：" + orderId + " 客户已离店");
			logger.info("订单：" + orderId + " 客户已离店");
			return toManageOrderList(session, request);
		} else {
			logger.error("订单：" + orderId + "客户离店操作失败，错误信息：" + result.getString("msg"));
			ModelAndView rmv = new ModelAndView();
			rmv.addObject("message", "确认客户离店操作失败！ 错误信息" + result.getString("msg"));
			rmv.setViewName("/common/error_new");
			return rmv;
		}
	}

	@RequestMapping("/toRefund")
	public ModelAndView toRefund(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String orderId = request.getParameter("orderId");
		String[] orderIdInt = new String[1];
		orderIdInt[0] = orderId;

		Integer bldIds[] = new Integer[1];
		bldIds[0] = user.getDevId();

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderIds", orderIdInt);
		// postData.put("devIds", bldIds);

		selectOrderList(request, mv, postData);
		OrderInfoVO order = ((List<OrderInfoVO>) mv.getModel().get("orderList")).get(0);

		String houseId = order.getHouseId();
		String beginTime = order.getBeginTime();
		String endTime = order.getEndTime();

		List<String> datelist = DateUtil.getDateList(beginTime, endTime);
		datelist.remove(datelist.size() - 1);

		postData.clear();
		postData.put("houseId", Integer.valueOf(houseId));
		postData.put("dates", datelist.toArray());
		List<HousePriceDto> pricelist = getHousePrice(postData);

		mv.addObject("pricelist", pricelist);
		mv.addObject("orderInfo", order);
		mv.addObject("flag", "refund");
		mv.addObject("currentDate", DateUtil.getDateFormatString(new Date()));
		mv.setViewName("/order/orderRefund_new");
		return mv;
	}

	@RequestMapping("/refund")
	public ModelAndView refund(HttpSession session, HttpServletRequest request) throws Exception {

		Map<String, Object> postData = new HashMap<String, Object>();
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);

		// 获取订单信息
		String orderId = request.getParameter("orderId");
		Integer status = Integer.valueOf(request.getParameter("status"));
		// postData.put("orderIds", new String[]{orderId} );
		// postData.put("devIds", new int[]{ user.getDevId() });

		// List<OrderInfoVO> orderlist= getOrderList(postData);
		// OrderInfoVO order=orderlist.get(0);
		//
		String returnTime = request.getParameter("returnDate");
		// String houseId=order.getHouseId();
		// String beginTime=order.getBeginTime();
		// String endTime=order.getEndTime();

		// if( DateUtil.diffDays(beginTime, returnTime) < 0 ||
		// DateUtil.diffDays(returnTime, endTime) <0 ){
		// request.setAttribute("errorMessage", "订单：" + orderId + "退款日期错误, "+
		// returnTime +"不在订单日期范围内 " );
		// return toOrderList(session, request);
		// }
		//
		// List<String> datelist = DateUtil.getDateList(returnTime, endTime);
		// datelist.remove( datelist.size() -1 );

		// 调用获取房屋价格接口
		// postData.clear();
		// postData.put("houseId", Integer.valueOf(houseId));
		// postData.put("dates", datelist.toArray());
		// List<HousePriceDto> pricelist=getHousePrice(postData);

		// 计算退款金额
		// int amt=0;
		// for(HousePriceDto price: pricelist){
		// amt += Integer.parseInt( price.getMemTotalAmt() ) * Integer.parseInt(
		// order.getTotalRoomNum() );
		// }

		// 调用退款接口
		postData.clear();
		postData.put("orderId", orderId);
		postData.put("reason", request.getParameter("reason"));
		postData.put("startDate", returnTime);
		postData.put("status", status);
		// postData.put("amt", amt );

		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpMerchReqUrl(postData, "returnOrder");

		if (0 == result.getInteger("code")) {
			request.setAttribute("successMessage", "订单：" + orderId + " 已完成退款");
			logger.info("订单：" + orderId + " 已完成退款");
			return toManageOrderList(session, request);
		} else {
			logger.error("订单：" + orderId + "退款操作失败，错误信息：" + result.getString("msg"));
			request.setAttribute("errorMessage", "订单：" + orderId + "退款失败");
			return toManageOrderList(session, request);
		}
	}

	@RequestMapping("/toHouseOrderInfo")
	public ModelAndView toHouseOrderInfo(HttpSession session, HttpServletRequest request) throws ParseException {
		ModelAndView mv = new ModelAndView();
		String houseId = request.getParameter("houseId");
		String orderId = request.getParameter("orderId");
		String beginTime = request.getParameter("begintime");
		String endTime = request.getParameter("endtime");

		List<String> datelist = DateUtil.getDateList(beginTime, endTime);

		// 获取每天的houseOrder信息
		try {
			List<List<HouseOrderVO>> totlist = new ArrayList<List<HouseOrderVO>>();
			for (String dstr : datelist) {
				Map<String, Object> pdata = new HashMap();
				JSONArray harray = new JSONArray();
				harray.add(Integer.parseInt(houseId));
				JSONArray darray = new JSONArray();
				darray.add(dstr);
				pdata.put("houseIds", harray);
				pdata.put("dates", darray);
				List<HouseOrderVO> houseorders = getHouseOrderList(pdata);
				totlist.add(houseorders);
			}

			HouseDto houseinfo = getHouseInfo(Integer.parseInt(houseId));

			Map<String, Object> postData = new HashMap();
			JSONArray orders = new JSONArray();
			orders.add(orderId);
			postData.put("orderIds", orders);
			OrderInfoVO orderinfo = getOrderList(postData).get(0);

			postData.clear();
			postData.put("houseId", Integer.valueOf(houseId));
			postData.put("dates", datelist.toArray());
			postData.put("roomNum", Integer.valueOf(orderinfo.getTotalRoomNum()));

			List<HousePriceDto> halist = checkHouseDate(postData);

			mv.addObject("houseinfo", houseinfo);
			mv.addObject("orderinfo", orderinfo);
			mv.addObject("datelist", datelist);
			mv.addObject("tlist", totlist);
			mv.addObject("halist", halist);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取houseorderlist信息失败", e);
		}
		mv.setViewName("/order/houseOrderInfo");
		return mv;
	}

	private List<HouseOrderVO> getHouseOrderList(Map<String, Object> postData) throws ClientProtocolException,
			IOException, ParseException {
		com.meidusa.fastjson.JSONObject result = null;
		result = HttpClientPostMethod.httpCustReqUrl(postData, "getHouseOrders");
		JSONObject jsonData = JSONObject.fromObject(result.get("data"));
		int count = jsonData.getInt("num");
		List<HouseOrderVO> houseOrderList = new ArrayList<HouseOrderVO>();
		if (count > 0) {
			JSONArray jsonhouseOrders = jsonData.getJSONArray("houseOrders");
			for (int i = 0; i < jsonhouseOrders.size(); i++) {
				JSONObject jobject = jsonhouseOrders.getJSONObject(i);
				HouseOrderVO vo = new HouseOrderVO();
				Integer houseId = jobject.getInt("houseId");
				vo.setHouseId(houseId.toString());
				vo.setUid(jobject.getString("uid"));
				vo.setTotalRoomNum(jobject.get("totalRoomNum").toString());
				vo.setOrderId(jobject.getString("orderId"));
				vo.setDates(jobject.getString("date"));

				Map<String, Object> pdata = new HashMap<String, Object>();
				JSONArray oids = new JSONArray();
				oids.add(vo.getOrderId());
				pdata.put("orderIds", oids);
				List<OrderInfoVO> orderlist = getOrderList(pdata);
				OrderInfoVO order = orderlist.get(0);

				vo.setHouseName(order.getHouseName());
				vo.setPhone(order.getPhone());
				vo.setUserName(order.getUserName());

				houseOrderList.add(vo);
			}
		}
		return houseOrderList;
	}

	private List<OrderInfoVO> getOrderList(Map<String, Object> postData) throws ParseException,
			ClientProtocolException, IOException {
		List<OrderInfoVO> orderList = new ArrayList<OrderInfoVO>();
		com.meidusa.fastjson.JSONObject result = null;

		result = HttpClientPostMethod.httpReqUrl(postData, "getOrders");
		logger.info("查询订单列表记录返回结果,result{}" + result);
		if (result != null) {
			JSONObject orderObjectObject = JSONObject.fromObject(result.getString("data"));
			JSONArray orderArray = JSONArray.fromObject(orderObjectObject.getString("orders"));
			for (int i = 0; i < orderArray.size() && orderArray != null; i++) {
				JSONObject order = orderArray.getJSONObject(i);
				OrderInfoVO orderInfoVO = new OrderInfoVO();
				orderInfoVO.setOrderId(order.getString("orderId"));
				orderInfoVO.setFreezeAmt(order.getString("freezeAmt"));
				orderInfoVO.setTotalAmt(order.getString("totalAmt"));
				orderInfoVO.setHouseId(order.getString("houseId"));
				orderInfoVO.setStatus(order.getString("status"));
				orderInfoVO.setVerifyCode(order.getString("verifyCode"));
				orderInfoVO.setTotalRoomNum(order.get("totalRoomNum").toString());
				JSONObject houseObject = JSONObject.fromObject(order.getString("liveDetail"));
				String houseName = houseObject.getString("houseName");
				String endTime = houseObject.getString("endTime");
				String beginTime = houseObject.getString("beginTime");
				String linkmanName = houseObject.getString("linkmanName");
				String linkmanPhone = houseObject.getString("linkmanPhone");
				String uid = order.getString("uid");
				orderInfoVO.setUserName(linkmanName);
				orderInfoVO.setPhone(linkmanPhone);
				orderInfoVO.setBeginTime(beginTime);
				orderInfoVO.setEndTime(endTime);
				orderInfoVO.setHouseName(houseName);
				orderInfoVO.setUid(uid);
				String operTime = order.getString("operTime");
				Date operDate = new SimpleDateFormat("yyyy-MM-dd").parse(operTime);
				operTime = new SimpleDateFormat("yyyy-MM-dd").format(operDate);
				orderInfoVO.setOperTime(operTime);
				orderList.add(orderInfoVO);
			}
		}
		logger.info("查询订单列表记录返回到页面的结果,orderList{}" + orderList);
		return orderList;
	}

	private HouseDto getHouseInfo(Integer houseId) throws ClientProtocolException, IOException {
		Map<String, Object> houseData = new HashMap<String, Object>();
		houseData.put("houseId", houseId);
		com.meidusa.fastjson.JSONObject houseresult = HttpClientPostMethod.httpReqUrl(houseData, "getHouseInfo");
		if (0 == houseresult.getIntValue("code")) {

			HouseDto dto = new HouseDto();
			net.sf.json.JSONObject jsonObject = JSONObject.fromObject(houseresult.get("data"));

			dto.setDevId(jsonObject.getString("devId"));
			dto.setBldId(jsonObject.getString("bldId"));
			dto.setSummaryInfo(jsonObject.getString("summaryInfo"));

			dto.setCityId(jsonObject.getString("cityId"));
			dto.setHouseId(jsonObject.get("houseId").toString());
			dto.setFreezeAmt(jsonObject.get("freezeAmt").toString());
			dto.setMemFreezeAmt(jsonObject.get("memFreezeAmt").toString());
			dto.setMemTotalAmt(jsonObject.get("memTotalAmt").toString());
			dto.setTotalAmt(jsonObject.get("totalAmt").toString());

			String room = jsonObject.get("room").toString();
			dto.setRoom(room);
			dto.setBedroom(room.substring(0, 1));
			dto.setLivingroom(room.substring(1, 2));
			dto.setRestroom(room.substring(2, 3));
			dto.setBed(room.substring(3, 4));
			JSONObject sumobject = JSONObject.fromObject(jsonObject.get("summaryInfo"));
			dto.setHouseName(sumobject.getString("houseName"));

			return dto;
		} else {
			return null;
		}
	}

	private List<HousePriceDto> getHousePrice(Map<String, Object> postData) throws ClientProtocolException, IOException {
		List<HousePriceDto> list = new ArrayList<HousePriceDto>();
		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getHousePrice");
		if (0 == result.getIntValue("code")) {
			net.sf.json.JSONObject jsonObject = JSONObject.fromObject(result.get("data"));
			if (jsonObject.getInt("num") > 0) {
				JSONArray pricesJson = JSONArray.fromObject(jsonObject.getString("prices"));
				for (int i = 0; i < pricesJson.size() && pricesJson != null; i++) {
					HousePriceDto priceDto = new HousePriceDto();
					JSONObject price = pricesJson.getJSONObject(i);
					priceDto.setDate(price.get("date").toString());
					priceDto.setFreezeAmt(price.get("freezeAmt").toString());
					priceDto.setTotalAmt(price.get("totalAmt").toString());
					priceDto.setMemFreezeAmt(price.get("memFreezeAmt").toString());
					priceDto.setMemTotalAmt(price.get("memTotalAmt").toString());
					list.add(priceDto);
				}
			}
		}
		return list;
	}

	private List<HousePriceDto> checkHouseDate(Map<String, Object> postData) throws ClientProtocolException,
			IOException {

		List<HousePriceDto> list = new ArrayList<HousePriceDto>();
		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpCustReqUrl(postData, "checkHouseDate");
		if (0 == result.getIntValue("code")) {
			net.sf.json.JSONObject jsonObject = JSONObject.fromObject(result.get("data"));
			Iterator ks = jsonObject.keys();
			while (ks.hasNext()) {
				HousePriceDto priceDto = new HousePriceDto();
				Object key = ks.next();
				priceDto.setDate(key.toString());

				JSONObject obj = jsonObject.getJSONObject(key.toString());

				priceDto.setFreezeAmt(obj.get("freezeAmt").toString());
				priceDto.setTotalAmt(obj.get("totalAmt").toString());
				priceDto.setMemFreezeAmt(obj.get("memFreezeAmt").toString());
				priceDto.setMemTotalAmt(obj.get("memTotalAmt").toString());

				priceDto.setEmptyRoom(obj.getInt("emptyRoom"));
				priceDto.setAvailable(obj.getInt("available"));
				priceDto.setRequiredRoom(obj.getInt("requiredRoom"));
				list.add(priceDto);
			}
		}
		return list;
	}

	@RequestMapping("/toBookOrderList")
	public ModelAndView toBookOrderList(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> postData = new HashMap<String, Object>();
		
		
		List<Integer> stlist=new ArrayList<Integer>();
		String[] checkedstatus=request.getParameterValues("checkedstatus");
		if(checkedstatus != null){
			
			for(String s: checkedstatus){
				if(s.compareTo("-1")==0)
					stlist.add(-1);
				if(s.compareTo("10")==0)
					stlist.add(10);
				if(s.compareTo("24")==0)
					stlist.add(24);
			}
		}else{
			stlist.add(10);
			stlist.add(24);
			stlist.add(15);
			stlist.add(23);
		}
		
		postData.put("statuses", stlist);
		
		
		String city = request.getParameter("city");
		if(StringUtils.isNotBlank(city)){
			postData.put("cityIds", new Integer[]{ Integer.parseInt(city)});
		}
		
		String build = request.getParameter("build");
		if(StringUtils.isNotBlank(build)){
			postData.put("bldIds", new Integer[]{ Integer.parseInt(build)});
		}
		
		
		

		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		TSysRole role = roleService.getRole(user);
		Integer bldIds[] = new Integer[] { user.getDevId() };
		if (role.getId().compareTo(Constant.ADMIN_ROLE_ID) != 0) {
			// 将开发商选择条件修改为 楼盘选择条件
			postData.put("bldIds", bldIds);
		}

		Map<String, Object> phoneData = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		if (StringUtils.isNotBlank(phone)) {
			String uid = "";
			phoneData.put("phone", phone);
			com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpReqUrl(phoneData, "getUserInfo");
			String code = result.getString("code");

			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					uid = jsonObject.getString("uid");
					postData.put("uid", uid);
				}
				mv.addObject("phone", phone);
				postData.put("uid", uid);
			} else {
				mv.addObject("phone", phone);
				mv.addObject("errorMessage", "手机号码不在列表中");
			}
		}
		
		logger.info("查询会员订单列表记录请求参数,postData{}" + postData);
		postData.put("phone", phone);
		selectOrderList(request, mv, postData);
		
		List<BuildingsDto> buildList = (List<BuildingsDto>)mv.getModel().get("buildList");
		if (role.getId().compareTo(Constant.ADMIN_ROLE_ID) != 0) {
			for(Iterator<BuildingsDto> it=buildList.iterator(); it.hasNext(); ){
				BuildingsDto b=it.next();
				// 修改为 以用户-楼盘对应关系
				if(b.getBldId() != user.getDevId() ){
					it.remove();
				}
			}
		}
		
		
		List<CityInfo>	cityList= (List<CityInfo>)mv.getModel().get("cityList");
		JSONArray json=JSONArray.fromObject(cityList);
		mv.addObject("cityStr", json.toString());
//		System.out.println( json.toString() );
		json=JSONArray.fromObject(buildList);
		mv.addObject("buildStr", json.toString());
//		System.out.println( json.toString() );
		mv.setViewName("/order/bookOrderList_new");
		return mv;
	}
	
	
	@RequestMapping("/toManageOrderList")
	public ModelAndView toManageOrderList(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> postData = new HashMap<String, Object>();
		
		List<Integer> stlist=new ArrayList<Integer>();
		String[] checkedstatus=request.getParameterValues("checkedstatus");
		if(checkedstatus != null){
			for(String s: checkedstatus){
				if(s.compareTo("-1")==0)
					stlist.add(-1);
				if(s.compareTo("9")==0)
					stlist.add(9);
				if(s.compareTo("11")==0)
					stlist.add(11);
				if(s.compareTo("12")==0)
					stlist.add(12);
				if(s.compareTo("13")==0)
					stlist.add(13);
				if(s.compareTo("23")==0)
					stlist.add(23);
			}
		}else{
			stlist.add(9);
			stlist.add(11);
			stlist.add(12);
			stlist.add(13);
			stlist.add(23);
		}
		
		postData.put("statuses", stlist);
		
		
		String city = request.getParameter("city");
		if(StringUtils.isNotBlank(city)){
			postData.put("cityIds", new Integer[]{ Integer.parseInt(city)});
		}
		
		String build = request.getParameter("build");
		if(StringUtils.isNotBlank(build)){
			postData.put("bldIds", new Integer[]{ Integer.parseInt(build)});
		}
		
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		TSysRole role = roleService.getRole(user);
		Integer bldIds[] = new Integer[] { user.getDevId() };
		if (role.getId().compareTo(Constant.ADMIN_ROLE_ID) != 0) {
			// // 将开发商选择条件修改为 楼盘选择条件
			postData.put("bldIds", bldIds);
		}

		Map<String, Object> phoneData = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		if (StringUtils.isNotBlank(phone)) {
			String uid = "";
			phoneData.put("phone", phone);
			com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpReqUrl(phoneData, "getUserInfo");
			String code = result.getString("code");

			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					uid = jsonObject.getString("uid");
					postData.put("uid", uid);
				}
				mv.addObject("phone", phone);
				postData.put("uid", uid);
			} else {
				mv.addObject("errorMessage", "手机号码不在列表中");
			}
		}
		
		logger.info("查询会员订单列表记录请求参数,postData{}" + postData);
		selectOrderList(request, mv, postData);
		
		List<BuildingsDto> buildList = (List<BuildingsDto>)mv.getModel().get("buildList");
		if (role.getId().compareTo(Constant.ADMIN_ROLE_ID) != 0) {
			for(Iterator<BuildingsDto> it=buildList.iterator(); it.hasNext(); ){
				BuildingsDto b=it.next();
				//  将getDevId 修改为 getBldId, 将用户-开发商 关系，改为 用户-楼盘关系
				if(b.getBldId() != user.getDevId() ){
					it.remove();
				}
			}
		}
		
		List<CityInfo>	cityList= (List<CityInfo>)mv.getModel().get("cityList");
		JSONArray json=JSONArray.fromObject(cityList);
		mv.addObject("cityStr", json.toString());
		json=JSONArray.fromObject(buildList);
		mv.addObject("buildStr", json.toString());
		mv.setViewName("/order/manageOrderList_new");
		return mv;
	}

	private List<HousePriceDto> convertHousePriceList(List<HousePriceDto> halist,List<PerdayHousePrice> hplist){
		for (int i = 0; i < halist.size(); i++) {
			HousePriceDto housePriceDto = new HousePriceDto();
			housePriceDto = halist.get(i);
			for (int j = 0; j < hplist.size(); j++) {
				PerdayHousePrice perdayHousePrice = new PerdayHousePrice();
				perdayHousePrice = hplist.get(j);
				if (housePriceDto.getDate().equals(perdayHousePrice.getDate())) {
					housePriceDto.setMemTotalAmt(String.valueOf(perdayHousePrice.getOriginalPrice()));
				}
			}
		}
		return halist;
	}
	
	/**
	 * 检查支付接口是否正确支付
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkOrder")
	@ResponseBody
	public RestResponse checkOrder(HttpSession session,HttpServletRequest request) throws  Exception {
		if(logger.isInfoEnabled()) {
			logger.info("start to run /checkOrder.");
		}
		RestResponse restP = new RestResponse();
		try {
			//1、基本请求参数校验
		    String  uid            =  request.getParameter("uid");//会员编号
			String  orderId          =  request.getParameter("orderId"); //业务订单号
			String  productCode    =  request.getParameter("productCode");//支付方式编码
			String  trade_type	   =  request.getParameter("trade_type");	// 微信统一下单贯口，trade_type
			
			Map<String, Object> postData = new HashMap<String, Object>();
		    postData.put("uid", uid);
		    postData.put("orderId", orderId);
		    postData.put("productCode", productCode);
		    postData.put("trade_type", trade_type);
			logger.info("============orderId==" + orderId + "====uid====" + uid);
			//2、请求妈妈送房后台查询
			com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "cfOrderStatus");
			logger.info("=========订单查询结果为result=" + result);
    		String code = result.getString("code");
    		if(ReturnCode.OK == Integer.parseInt(code)){
    			com.meidusa.fastjson.JSONObject json = result.getJSONObject("data"); 
    			restP.setSuccess(true);
        		restP.setCode(json.getString("resultCode"));
    		}
			logger.info("end to run /checkOrder.");
		} catch (Exception e) {
			restP.setSuccess(false);
			logger.error("checkOrder：订单支付检查失败.",e);
			e.printStackTrace();
		}
		return restP;
	}
}
