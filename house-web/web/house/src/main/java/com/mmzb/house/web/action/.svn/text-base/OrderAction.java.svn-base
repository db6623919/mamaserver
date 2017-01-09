package com.mmzb.house.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.ext.service.MemberService;
import com.mmzb.house.web.action.base.ApplicationConfig;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.BuildingInfo;
import com.mmzb.house.web.action.dto.DeveloperInfo;
import com.mmzb.house.web.action.dto.HouseInfo;
import com.mmzb.house.web.action.dto.HousePrice;
import com.mmzb.house.web.action.dto.OrderInfo;
import com.mmzb.house.web.action.dto.PerdayHousePrice;
import com.mmzb.house.web.action.dto.UserCardInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.DateUtils;
import com.mmzb.house.web.action.util.JsonGeneratorUtils;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.constants.CommonConstant;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.WeixinPayBean;
import com.mmzb.house.web.util.CS;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;
import com.netfinworks.vfsso.client.exception.CasServiceException;

@Controller
public class OrderAction extends BaseAction {

	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	@Resource(name="memberService")
    private MemberService memberClient;

	@Autowired
	public GerneralMethod gerneralMethod;

	private static final Logger logger = LoggerFactory.getLogger(OrderAction.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 创建订单
	@RequestMapping(value = "/my/createOrder.htm", method = { RequestMethod.POST })
	public void createOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		try {
			//1.获取登陆用户信息
			UserInfo loginUser = getLoginUserInfo(request);
			
			//2.添加联系人
			add2Contacts(request, loginUser.getUid());
			
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			List<Long> hotelCouponIds = getHotelCouponIdsRequest(request);// 旅居券ID列表   以前的旅居券抵扣
			HouseInfo hi = gerneralMethod.getHouseInfo(houseId);
			BuildingInfo bi = gerneralMethod.getBuildingInfo(hi.getBldId());
			DeveloperInfo di = gerneralMethod.getDeveloperInfo(hi.getDevId());
			String startTime = request.getParameter("startTime");
			startTime = startTime.substring(0, 4) + "-" + startTime.substring(5, 7) + "-" + startTime.substring(8, 10);
			String endTime = request.getParameter("endTime");
			endTime = endTime.substring(0, 4) + "-" + endTime.substring(5, 7) + "-" + endTime.substring(8, 10);

			//TODO:
			List<Integer> contactIds = new ArrayList<Integer>();
			contactIds.add(0);
			
			Map<String, Object> liveDetail = new HashMap<String, Object>();
			liveDetail.put("user_phone", loginUser.getName());
			liveDetail.put("linkmanPhone", request.getParameter("phone"));
			liveDetail.put("linkmanName", request.getParameter("name"));
			liveDetail.put("houseName", request.getParameter("houseName"));
			liveDetail.put("memTotalAmt", Integer.parseInt(request.getParameter("memTotalAmt")));
			liveDetail.put("orderAmt", Integer.parseInt(request.getParameter("orderAmt")));
			liveDetail.put("prepayment", Integer.parseInt(request.getParameter("prepayment")));
			liveDetail.put("ammount", Integer.parseInt(request.getParameter("ammount")));
			liveDetail.put("buildingName", bi.getName());
			liveDetail.put("phone", hi.getTelephone());
			liveDetail.put("developerName", di.getName());
			liveDetail.put("realInSeasonDays", Integer.parseInt(request.getParameter("realInSeasonDays")));//实际抵扣天数
			
			//订单每日房间价格
			Map<String, Object> postData2 = new HashMap<String, Object>();
			postData2.put("houseId", houseId);
			Integer day = DateUtils.daysBetween(startTime,endTime);
			String dates[] = new String[day];
			for (int i = 0; i < day ; i++) {
				Calendar calendar = Calendar.getInstance();
				// 初始化 Calendar 对象，但并不必要，除非需要重置时间
				calendar.setTime(sdf.parse(startTime));
				calendar.add(Calendar.DAY_OF_MONTH, i);
				
				Date time = calendar.getTime();
				String dateStr = sdf.format(time);
				dates[i] = dateStr;
			}
			postData2.put("dates", dates);
			JSONObject result2 = HttpClientPostMethod.httpReqUrl(postData2,appProperties.getRequestRoot(), "getHousePrice");
			String code2 = result2.getString("code");
			List<PerdayHousePrice> list = new ArrayList<PerdayHousePrice>();
			if ("0".equals(code2)) {
				com.meidusa.fastjson.JSONArray arrayHouse = result2.getJSONObject("data").getJSONArray("prices");
				for (int j = 0; j < arrayHouse.size(); j++) {
					PerdayHousePrice perdayHousePrice = new PerdayHousePrice();
					JSONObject jsonObject = arrayHouse.getJSONObject(j);
//					if (null!=jsonObject.getString("priceId")&&!"".equals(jsonObject.getString("priceId"))) {
//						perdayHousePrice.setPriceId(Integer.parseInt(jsonObject.getString("priceId")));
//					}
					perdayHousePrice.setDate(jsonObject.getString("date"));
					perdayHousePrice.setOriginalPrice(Integer.parseInt(jsonObject.getString("memTotalAmt")));
					perdayHousePrice.setPresentPrice(Integer.parseInt(jsonObject.getString("memTotalAmt")));
					list.add(perdayHousePrice);
				}
			}
			liveDetail.put("list", list);
			//支付方式选择
			String pay_type = request.getParameter("pay_type");
			
			postData.put("uid", loginUser.getUid());
			postData.put("mmWalletId", loginUser.getMmWalletId());
			postData.put("hotelCouponIds", hotelCouponIds);
			postData.put("houseId", houseId);
			postData.put("beginTime", startTime);
			postData.put("endTime", endTime);
			postData.put("contactIds", contactIds);
			postData.put("liveDetail", liveDetail);
			postData.put("totalRoomNum", 1);
			//postData.put("pay_type", pay_type);
			postData.put("pay_type", "");
			
			//已优惠
			String freezeAmt = request.getParameter("discount");
			postData.put("freezeAmt", freezeAmt);
			
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "createOrder");
			String msg = result.get("msg").toString();
			String code = result.getString("code");

			if ("wxpay".equals(pay_type)) {//微信支付
				WeixinPayBean weixinPayBean = new WeixinPayBean();
				Object data = result.get("data");
				if (data != null)
				{
					JSONArray array = JSONArray.fromObject(data);
					for (int i = 0; i < array.size(); i++) {
						net.sf.json.JSONObject jsonObjectShowDeatil = array.getJSONObject(i);
						String orderId = (String) jsonObjectShowDeatil.get("orderId");
						map.put("orderId", orderId);
						weixinPayBean.setOrderNo(orderId);
						logger.info("orderId: " + orderId);
					}
					
					weixinPayBean.setPayAmt(request.getParameter("prepayment"));
					setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, weixinPayBean);
				}
			}

			if (StringUtil.isEmpty(msg)) {
				msg = "创建订单失败，请稍后再试";
			}
			
			map.put("msg", msg);
			map.put("code", code);

			JsonGeneratorUtils.createRetMaptJSONObject(response, code, msg);
		} catch (ClientProtocolException e) {
			logger.error("创建订单异常", e);
		} catch (IOException e) {
			logger.error("创建订单异常", e);
		} catch (Exception e) {
			logger.error("创建订单异常", e);
		}
	}

	//添加联系人
	private void add2Contacts(HttpServletRequest request, String uid)throws ClientProtocolException, IOException
	{
		String contactsName = request.getParameter("name");
		String constactsPhone = request.getParameter("phone");
		Map<String, Object> postMapConstact = new HashMap<String, Object>();
		postMapConstact.put("contactsName", contactsName);
		postMapConstact.put("contactsPhone", constactsPhone);
		postMapConstact.put("uid", uid);
		JSONObject contactResult = HttpClientPostMethod.httpReqUrl(postMapConstact, appProperties.getRequestRoot(), "getIsExistMyContacts");
		String contactCode = contactResult.get("code").toString();
		if ("0".equals(contactCode))
		{
			HttpClientPostMethod.httpReqUrl(postMapConstact,appProperties.getRequestRoot(), "insertMyContacts");
		}
	}

	// 取消订单
	@RequestMapping(value = "/my/cancelOrder.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> cancelOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserInfo loginUser = getLoginUserInfo(request);
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", loginUser.getUid());
			postData.put("orderId", request.getParameter("orderId"));
			postData.put("phone", request.getParameter("phone"));
			postData.put("userPhone", loginUser.getName());
			JSONObject resultJson = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "cancelOrder");
			String msg = resultJson.get("msg").toString();
			String code = resultJson.getString("code");
			result.put("msg", msg);
			result.put("code", code);
		} catch (Exception e) {
			result.put("code", 1);
			result.put("msg", "系统繁忙，请稍后重试");
		}
		return result;
	}

	// 查询订单简介
	@RequestMapping(value = "/my/getOrders.htm", method = { RequestMethod.GET })
	public String getOrders(HttpServletRequest request, HttpServletResponse response, Model model) {
		//判断是否登陆
		setLoginStatus(request, model);
		
		//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		model.addAttribute("imgurl", imgurl);
		return Contants.URL_PREFIX + "/order/my_order_new";
	}

	private void setLoginStatus(HttpServletRequest request, Model model)
	{
		int isLogin = 0;
		try 
		{
			UserInfo loginUser = getLoginUserInfo(request);
			isLogin = (loginUser != null) ? 1 : 0;
			
		} 
		catch (Exception e)
		{
			logger.warn("failed to get loginStatus", e);
			isLogin = 0;
		}
		
		model.addAttribute("isLogin", isLogin);
	}
	
	// 继续加载订单列表
	@RequestMapping(value = "/my/getOrdersJson.htm", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getOrdersJson(HttpServletRequest request) throws Exception {
		UserInfo loginUser = getLoginUserInfo(request);
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
			net.sf.json.JSONObject resJson = new net.sf.json.JSONObject();
			String type = request.getParameter("type");
			postData.put("uid", loginUser.getUid());
			int page = Integer.parseInt(request.getParameter("page"));
			postData.put("pageNum", page);
			postData.put("pageCount", 5);
			List<Integer> list = new ArrayList<Integer>();
			if ("1".equals(type)) {
				list.add(13);
				list.add(17);
				list.add(23);
				list.add(24);
				postData.put("statuses", list);
			} else if ("0".equals(type)) {
				list.add(9);
				list.add(10);
				list.add(11);
				list.add(12);
				list.add(15);
				postData.put("statuses", list);
			}
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getOrders");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			resJson.put("msg", msg);
			resJson.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObjectShowDeatil = array.getJSONObject(i);
					int num = jsonObjectShowDeatil.getInt("num");
					if (num > 0) {
						JSONArray orderArray = JSONArray.fromObject(jsonObjectShowDeatil.get("orders"));
						for (int j = 0; j < orderArray.size(); j++) {
							try {
								OrderInfo order = new OrderInfo();
								net.sf.json.JSONObject jsonObject = orderArray.getJSONObject(j);
								order.setOrderId(jsonObject.getString("orderId"));
								order.setHouseId(jsonObject.getString("houseId"));
								order.setFreezeAmt(jsonObject.getInt("freezeAmt"));
								order.setStatus(OrderInfo.getStatusText(jsonObject.getInt("status")));
								order.setTotalAmt(String.valueOf(jsonObject.getInt("totalAmt")));
								net.sf.json.JSONObject detail = net.sf.json.JSONObject.fromObject(jsonObject.getString("liveDetail"));
								order.setHouseName(URLEncoder.encode(detail.getString("houseName"), "utf-8"));
								order.setBeginTime(detail.getString("beginTime"));
								order.setEndTime(detail.getString("endTime"));
								order.setPrepayment(detail.getInt("prepayment"));
								order.setBuildingName(URLEncoder.encode(detail.getString("buildingName"), "utf-8"));
								order.setAmmount(detail.getInt("ammount"));
								order.setPhone(detail.getString("phone"));
								order.setPayAmt(jsonObject.getString("payAmt"));
								if (detail.containsKey("hotelCouponIds")) {
									JSONArray hotelCouponIdsJsonArray = detail.getJSONArray("hotelCouponIds");
									if (hotelCouponIdsJsonArray != null && hotelCouponIdsJsonArray.size() > 0) {
										List<Long> hotelCouponIds = new ArrayList<Long>();
										for (int q = 0; q < hotelCouponIdsJsonArray.size(); q++) {
											hotelCouponIds.add(hotelCouponIdsJsonArray.getLong(q));
										}
										order.setHotelCouponCount(hotelCouponIds.size());
									}
								}
								orderInfoList.add(order);
								
							} catch (Exception e) {
								logger.error("error occured while parse json", e);
							}
						}
					}
				}

				response.put("code", 0);
				response.put("orderInfoList", orderInfoList);
			} else {
				response.put("code", 1);
				response.put("msg", "系统繁忙，请稍后重试.");
			}

		} catch (Exception e) {
			logger.error("错误日志", e);
			response.put("code", 1);
			response.put("msg", "系统繁忙，请稍后重试.");
		}
		return response;
	}

	@RequestMapping(value = "/my/myOrder.htm", method = { RequestMethod.GET })
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/order/my_order");
	}

	@RequestMapping(value = "/my/orderDetail.htm", method = { RequestMethod.GET })
	public String orderDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		
		UserInfo loginUser = getLoginUserInfo(request);
		BuildingInfo bi = new BuildingInfo();
		HouseInfo hi = new HouseInfo();
		try {
			OrderInfo orderInfo = gerneralMethod.getOrderInfo(loginUser.getUid(), request.getParameter("orderId"));
			hi = gerneralMethod.getHouseInfo(Integer.parseInt(orderInfo.getHouseId()));
			bi = gerneralMethod.getBuildingInfo(hi.getBldId());
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
			orderInfo.setStatus(OrderInfo.getStatusText(Integer.parseInt(orderInfo.getStatus())));
			orderInfo.setBuildingName(bi.getName());
			orderInfo.setPhone(hi.getTelephone());
			
			WeixinPayBean weixinPayBean =  new WeixinPayBean();
			weixinPayBean.setOrderNo(orderInfo.getOrderId());
			weixinPayBean.setPayAmt(orderInfo.getPayAmt());
			setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, weixinPayBean);
			
			model.addAttribute("orderInfo", orderInfo);
			model.addAttribute("address", hi.getAddress());
			model.addAttribute("checkdesc", hi.getCheckdesc());
			model.addAttribute("orderPhone", hi.getTelephone());
			model.addAttribute("travelAddress", travelAddress);
			model.addAttribute("imgurl", imgurl);
		} catch (Exception e) {
			logger.error("查询订单详情异常", e);
		}

		return Contants.URL_PREFIX + "/order/order_detail_new";
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

	@RequestMapping(value = "/my/orderDate.htm", method = { RequestMethod.GET })
	public ModelAndView orderDate(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String houseId = request.getParameter("houseId");
		Map<String, Object> postData = new HashMap<String, Object>();
		List<String> dateList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		List<HousePrice> housePrice = new ArrayList<HousePrice>();
		Map<String, Object> hp = new HashMap<String, Object>();
		try {
			housePrice = gerneralMethod.getHousePrice(Integer.parseInt(houseId), null);
			for (HousePrice temp : housePrice) {
				hp.put(temp.getDate(), temp.getMemTotalAmt());
			}
			String currentDate = DateUtils.getCurrDate("yyyy-MM-dd");
			String endDate = DateUtils.addDayUse(currentDate, 120);
			dateList.add(currentDate);
			while (true) {
				if (currentDate.equals(endDate))
					break;
				currentDate = DateUtils.addDayUse(currentDate, 1);
				dateList.add(currentDate);
			}
			postData.put("houseId", Integer.parseInt(houseId));
			postData.put("dates", dateList);
//			postData.put("roomNum", Integer.parseInt(request.getParameter("totalTime")));//
			
			postData.put("roomNum", 1);//每天的库存只会减少一套，所以上面的一段代码逻辑错误，现在修正成正确的值
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "checkHouseDate");
			if (0 == result.getIntValue("code")) {
				net.sf.json.JSONObject jsonData = net.sf.json.JSONObject.fromObject(result.get("data"));
				Set<String> key = jsonData.keySet();
				sb.append("{\"data\":{\"startDate\":\"");
				sb.append(DateUtils.getCurrDate("yyyy-MM-dd") + "\",\"endDate\":\"");
				sb.append(endDate + "\",\"items\":[");
				int index = 0;
				for (String tmp : key) {
					net.sf.json.JSONObject dateJson = net.sf.json.JSONObject.fromObject(jsonData.get(tmp));
					Object price = dateJson.get("memTotalAmt");
					if (hp.containsKey(tmp))
						price = hp.get(tmp);
					sb.append("{\"date\":\"");
					sb.append(tmp + "\",\"price\":");
					sb.append(price + ",\"originalPrice\":");
					sb.append(price + ",\"type\":");
					sb.append(6 + ",\"isRent\":");
					int available = dateJson.getInt("available");
					sb.append(available == 1 ? true : false);
					sb.append(",\"stock\":");
					sb.append(available == 1 ? 1 : 0);
					sb.append("}");
					index++;
					if (index != key.size()) {
						sb.append(",");
					}

				}
				sb.append("]}}");
			}
			logger.info("返回页面json串" + sb.toString());

			HouseInfo houseInfo = gerneralMethod.getHouseInfo(Integer.parseInt(request.getParameter("houseId")));
			model.put("dateData", sb.toString());
			model.put("houseId", request.getParameter("houseId"));
			model.put("startdate", request.getParameter("startdate"));
			model.put("enddate", request.getParameter("enddate"));
			model.put("checkInTime", houseInfo.getCheckInTime());
			model.put("checkOutTime", houseInfo.getCheckOutTime());
		} catch (Exception e) {
			logger.error("获取日期异常", e);
		}
		return new ModelAndView(Contants.URL_PREFIX + "/order/order-date");
	}

	@RequestMapping(value = "/my/orderSuccess.htm", method = { RequestMethod.GET })
	public String orderSuccess(HttpServletRequest request, HttpServletResponse response, Model model) throws CasServiceException {
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
		model.addAttribute("travelAddress", travelAddress);
		
		return Contants.URL_PREFIX + "/order/order-success_new";
	}

	// @what 新增 预订人列表
	@RequestMapping(value = "/my/clientList.htm", method = { RequestMethod.GET })
	public String clientList(HttpServletRequest request, HttpServletResponse response, Model model) {

		List<Map> clientList = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("name", "what");
		map.put("phone", "136474646");

		Map map2 = new HashMap();
		map2.put("name", "what lanlan ");
		map2.put("phone", "987656431");

		clientList.add(map);
		clientList.add(map2);
		model.addAttribute("clientList", clientList);
		return Contants.URL_PREFIX + "/order/choose_person";
	}

	@RequestMapping(value = "/my/toOrder.htm", method = { RequestMethod.GET })
	public String toOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		UserInfo loginUser = getLoginUserInfo(request);
		User userLogin = new User();
		//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		String checkInTime = request.getParameter("checkInTime");
		String checkOutTime = request.getParameter("checkOutTime");
		String footerTotalAmt = request.getParameter("footerTotalAmt");
		String total = request.getParameter("total");
		String actual = request.getParameter("actual");
		
		String _startdate = startdate;
		String _enddate = enddate;
		HouseInfo house = new HouseInfo();
		UserInfo user = new UserInfo();
		UserCardInfo card = new UserCardInfo();
		List<HousePrice> priceList = new ArrayList<HousePrice>();
		int totalAmt = 0;
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String contactsName = request.getParameter("name");
//		String contactsPhone = request.getParameter("phone");
		try {
			if (!StringUtils.isBlank(contactsName)) {
				contactsName= URLDecoder.decode(contactsName,"UTF-8");;
			}else{
//				if (StringUtil.isNotEmpty(loginUser.getName())) {
//					contactsPhone = loginUser.getName();
//				}
//				
////				if (!StringUtils.isBlank(loginUser.getName()) && !StringUtils.isBlank(loginUser.getPhone())) {
////					contactsName = loginUser.getName();
////					contactsPhone = loginUser.getPhone();
////				} else {
////					contactsPhone = loginUser.getName();
////				}
//				contactsPhone = loginUser.getPhone();
//				contactsName = loginUser.getName();
				
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			if ("".equals(startdate) || startdate == null || "".equals(enddate) || enddate == null) {
				startdate = DateUtils.getCurrDate("yyyyMMdd");
				enddate = DateUtils.addDay(startdate, 1);
				_startdate = DateUtils.getCurrDate("yyyy-MM-dd");
				_enddate = DateUtils.addDayUse(_startdate, 1);
			} else {
				startdate = startdate.replaceAll("-", "");
				enddate = enddate.replaceAll("-", "");
			}
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			List<String> dates = new ArrayList<String>();
			String currentDate = _startdate;
			dates.add(currentDate);
			while (true) {
				currentDate = DateUtils.addDayUse(currentDate, 1);
				if (currentDate.equals(_enddate))
					break;
				dates.add(currentDate);
			}
			priceList = gerneralMethod.getHousePrice(houseId, dates);
			house = gerneralMethod.getHouseInfo(houseId);

			if (priceList.size() != 0) {
				for (HousePrice price : priceList) {
					totalAmt += price.getMemTotalAmt();
				}
				house.setMemTotalAmt(totalAmt);
			} else {
				house.setMemTotalAmt(house.getMemTotalAmt() * dates.size());
			}
			house.setCityName(ApplicationConfig.cityMap.get(house.getCityId()));
			user = gerneralMethod.getUserInfo(loginUser.getUid(), null, null);
			// card = gerneralMethod.getUserCardInfo(loginUser.getUid());
			
			String token = getToken(request);
			if (StringUtil.isNotEmpty(token)) {
				VfSsoUser.setCurrentToken(token);
				userLogin = VfSsoUser.get();
			} 	
		} catch (Exception e) {
			logger.error("填写订单异常", e);
		} finally {
			
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
			
			// 更新session
			HttpSession session = request.getSession();
			
			String userJsonString = (String)session.getAttribute(CommonConstant.SESSION_ATTR_NAME_CURRENT_USER);
						
			model.addAttribute("houseInfo", house);
			model.addAttribute("userInfo", user);
			model.addAttribute("loginUser", userLogin);
			model.addAttribute("userCardInfo", card);
			model.addAttribute("startdate", startdate);
			model.addAttribute("enddate", enddate);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("checkInTime", checkInTime);
			model.addAttribute("checkOutTime", checkOutTime);
			model.addAttribute("travelAddress", travelAddress);
			
			model.addAttribute("contactsName", contactsName);
			model.addAttribute("contactsPhone", loginUser.getNickName());
			model.addAttribute("footerTotalAmt", footerTotalAmt);
			model.addAttribute("total", total);
			model.addAttribute("actual", actual);
			model.addAttribute("imgurl", imgurl);

		}

		return Contants.URL_PREFIX + "/order/order-index_new";

	}

	@RequestMapping(value = "/my/getOrderPriceInfo.htm", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getOrderPriceInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserInfo loginUserInfo = getLoginUserInfo(request);
			Integer houseId = Integer.valueOf(request.getParameter("houseId")); // 酒店房源ID
			String startDate = request.getParameter("startDate"); // 入住开始时间
			String endDate = request.getParameter("endDate"); // 入住结束时间
			List<Long> hotelCouponIds = getHotelCouponIdsRequest(request);// 旅居券ID列表
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mmWalletId", loginUserInfo.getMmWalletId());
			postData.put("houseId", houseId);
			postData.put("startDate", startDate);
			postData.put("endDate", endDate);
			postData.put("hotelCouponIds", hotelCouponIds);

			JSONObject getPriceResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getOrderPriceInfo");
			String code = getPriceResult.getString("code");
			if ("0".equals(code)) {
				JSONObject dataJson = getPriceResult.getJSONObject("data");
				// dataMap.put("priceMap", priceMap);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("totalAmt", dataJson.getInteger("totalAmt")); // 总价
				data.put("days", dataJson.getInteger("days")); // 总天数
				data.put("inSeasonDays", dataJson.getInteger("inSeasonDays")); // 旺季天数
				data.put("offSeasonDays", dataJson.getInteger("offSeasonDays")); // 平季天数
				data.put("hotelCouponDiscountAmt", dataJson.getInteger("hotelCouponDiscountAmt")); // 旅居券抵扣金额
				data.put("orderFinalAmt", dataJson.getInteger("orderFinalAmt")); // 实际支付价格(订单价格)
				data.put("hotelCouponSize", hotelCouponIds != null ? hotelCouponIds.size() : 0);
				data.put("realInSeasonDays", dataJson.getInteger("realInSeasonDays"));//实际抵扣天数
				result.put("code", 0);
				result.put("data", data);
			}
		} catch (Exception e) {
			logger.error("获取价格失败， 请稍后重试", e);
			result.put("code", 1);
			result.put("msg", "获取价格失败， 请稍后重试");
		}
		System.out.println(result);
		return result;
	}

	/* @what 新增 旅居劵使用页面 */
	@RequestMapping(value = "/my/choose_coupon.htm", method = { RequestMethod.GET })
	public String choose_coupon(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		UserInfo loginUser = getLoginUserInfo(request);
		postData.put("mmWalletId", loginUser.getMmWalletId());
		postData.put("statusIds", Arrays.asList(0));
//		postData.put("expireTimeFrom", request.getParameter("expireTimeFromStr"));
//		postData.put("expireTimeTo", request.getParameter("expireTimeFromTo"));
		postData.put("expireTimeFrom", request.getParameter("expireTimeFromTo"));
		if (StringUtils.isNotBlank(request.getParameter("devId")))
			postData.put("devId", Integer.valueOf(request.getParameter("devId")));
		if (StringUtils.isNotBlank(request.getParameter("houseId")))
			postData.put("houseId", Integer.valueOf(request.getParameter("houseId")));
		try {
			JSONObject getPriceResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGroups");
			String code = getPriceResult.getString("code");
			if ("0".equals(code)) {
				model.addAttribute("hotelCoupons", getPriceResult.getJSONObject("data").toJSONString());
			}
		} catch (Exception e) {
			logger.error("获取旅居券异常", e);
		}
		model.addAttribute("expireTimeFromStr", request.getParameter("expireTimeFromStr"));
		model.addAttribute("expireTimeFromTo", request.getParameter("expireTimeFromTo"));
		model.addAttribute("houseId", request.getParameter("houseId"));
		model.addAttribute("inSeasonDays", request.getParameter("inSeasonDays"));
		model.addAttribute("offSeasonDays", request.getParameter("offSeasonDays"));
		return Contants.URL_PREFIX + "/order/choose_coupon";
	}

	// @what 新增 订单确认页面
	@RequestMapping(value = "/my/orderConfirm.htm", method = { RequestMethod.POST })
	public String orderConfirm(HttpServletRequest request, HttpServletResponse response, Model model) throws CasServiceException {
		
		//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		
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
		model.addAttribute("houseId", request.getParameter("houseId"));
		model.addAttribute("name", request.getParameter("name"));
		model.addAttribute("phone", request.getParameter("phone"));
		model.addAttribute("startTime", request.getParameter("startTime"));
		model.addAttribute("endTime", request.getParameter("endTime"));
		model.addAttribute("memTotalAmt", request.getParameter("memTotalAmt"));
		model.addAttribute("orderAmt", request.getParameter("orderAmt"));
		model.addAttribute("houseName", request.getParameter("houseName"));
		model.addAttribute("ammount", request.getParameter("ammount"));
		model.addAttribute("prepayment", request.getParameter("prepayment"));
		model.addAttribute("cityName", request.getParameter("cityName"));
		model.addAttribute("totalTime", request.getParameter("totalTime"));
		model.addAttribute("footerTotalAmt", request.getParameter("footerTotalAmt"));
		model.addAttribute("pay_type", request.getParameter("pay_type"));//支付方式选择
		model.addAttribute("realInSeasonDays", request.getParameter("realInSeasonDays"));//旅居券实际抵扣天数
		String coupons="0";
		if(!StringUtils.isEmpty(request.getParameter("coupons"))){
			coupons=request.getParameter("coupons");
		}
		model.addAttribute("coupons", coupons);
		model.addAttribute("discount", request.getParameter("discount"));
		model.addAttribute("total", request.getParameter("total"));
		model.addAttribute("actual", request.getParameter("actual"));
		model.addAttribute("hotelCouponIds", request.getParameter("hotelCouponIds"));
		model.addAttribute("travelAddress", travelAddress);
		model.addAttribute("imgurl", imgurl);
		return Contants.URL_PREFIX + "/order/order-confirm_new";
	}

	private List<Long> getHotelCouponIdsRequest(HttpServletRequest request) {
		String hotelCouponIdsStr = request.getParameter("hotelCouponIds");
		if (StringUtils.isNotBlank(hotelCouponIdsStr)) {
			String[] hotelCouponIdsStrArray = hotelCouponIdsStr.split(",");
			if (hotelCouponIdsStrArray.length > 0) {
				List<Long> ids = new ArrayList<Long>();
				for (String idStr : hotelCouponIdsStrArray) {
					ids.add(Long.valueOf(idStr));
				}
				return ids;
			}
		}
		return null;
	}
	
	// 更新订单状态
	@RequestMapping(value = "/my/updateOrder.htm", method = { RequestMethod.GET })
	public ModelAndView updateOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {		
		//添加登录信息
		String travelAddress = appProperties.getTravelAddress();
		String token = getToken(request);
		User userInfo = new User();
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			userInfo = VfSsoUser.get();
			if (userInfo != null) {
				travelAddress += ("?token=" + token);
				model.addAttribute("isLogin", "1");

			}
		}
		String orderId = request.getParameter("orderId");
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("orderId", orderId);
		Integer status = Integer.valueOf(request.getParameter("status"));
		postData.put("status", status);
		postData.put("user_phone", userInfo.getName());
		postData.put("orderPhone", request.getParameter("orderPhone"));
		
		UserInfo loginUser = getLoginUserInfo(request);
		OrderInfo orderIfo = gerneralMethod.getOrderInfo(loginUser.getUid(), orderId);
		postData.put("roomName", orderIfo.getHouseName());
		postData.put("data", orderIfo.getBeginTime());
		Integer daysize = DateUtils.daysBetween(orderIfo.getBeginTime(), orderIfo.getEndTime());
		postData.put("size", String.valueOf(daysize));
		
		com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot()+"/merchant","updateOrderStatus");
		if (0 == result.getInteger("code")) {
				request.setAttribute("successMessage", "确认订单：" + orderId + " 通过，等待客户入住");

//				UserInfo loginUser = getLoginUserInfo(request);
				BuildingInfo bi = new BuildingInfo();
				HouseInfo hi = new HouseInfo();
				
				OrderInfo orderInfo = gerneralMethod.getOrderInfo(loginUser.getUid(), request.getParameter("orderId"));
				WeixinPayBean weixinPayBean = super.getJsonAttribute(request.getSession(),
		                Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, WeixinPayBean.class);
				weixinPayBean.setOrderNo(orderId);
				weixinPayBean.setPayAmt(orderInfo.getPayAmt());
				logger.info("orderInfo.getPayAmt(): " + orderInfo.getPayAmt());
				System.out.println("orderInfo.getPayAmt(): " + orderInfo.getPayAmt());
				setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, weixinPayBean);
				
				hi = gerneralMethod.getHouseInfo(Integer.parseInt(orderInfo.getHouseId()));
				bi = gerneralMethod.getBuildingInfo(hi.getBldId());
				
				orderInfo.setStatus(OrderInfo.getStatusText(Integer.parseInt(orderInfo.getStatus())));
				orderInfo.setBuildingName(bi.getName());
				orderInfo.setPhone(hi.getTelephone());
				
				
				model.addAttribute("orderInfo", orderInfo);
				model.addAttribute("address", hi.getAddress());
				model.addAttribute("checkdesc", hi.getCheckdesc());
				model.addAttribute("travelAddress", travelAddress);
				ModelAndView mv = new ModelAndView();
				mv.setViewName(Contants.URL_PREFIX + "/order/order_detail_new");
				mv.addObject("model", model);
//			return Contants.URL_PREFIX + "/order/order_detail_new";
				return mv;
		} else {
			logger.error("审核订单：" + orderId + "出错，错误信息：" + result.getString("msg"));
			ModelAndView mv = new ModelAndView();
			mv.addObject("message", "审核订单入住操作失败！ 错误信息" + result.getString("msg"));
			mv.setViewName("/common/error_new");
			return mv;
		}
		
	}
	
	@RequestMapping(value = "/my/pay.htm", method = { RequestMethod.GET } )
	public ModelAndView pay(HttpServletRequest request, ModelMap model){
		String verifyCode="";
		Map<String, Object> postData = new HashMap<String, Object>();
		UserInfo userinfo = getLoginUserInfo(request);
		postData.put("uid", userinfo.getUid());
		postData.put("orderId", request.getParameter("orderId"));
		try{
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"sendOrderPayCode");
			int code = result.getIntValue("code");
			if(code==0){
				verifyCode = result.getJSONObject("data").getString("verifyCode");
				logger.info("测试代码####支付成功！！");
			}
		} catch (ClientProtocolException e) {
			logger.error("客户发送短信异常", e);
		} catch (IOException e) {
			logger.error("客户发送短信异常", e);
		}
		
		model.addAttribute("houseCode", verifyCode);
		return new ModelAndView(Contants.URL_PREFIX + "/weixin_pay/pay_success");
	}
	
}
