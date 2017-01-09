package com.mmzb.house.app.web.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.common.errorCode.ReturnCode;
import com.mama.server.common.vo.n99.N99OrderVo;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.util.DateUtils;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.entity.OrderEntity;
import com.mmzb.house.app.entity.PerdayHousePriceEntity;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.vo.GetCommentVo;
import com.mmzb.house.app.vo.GetOrdersVo;
import com.mmzb.house.app.vo.HousePriceVo;
import com.mmzb.house.app.vo.OrdersVo;
import com.mmzb.house.app.vo.Page;
import com.mmzb.house.app.vo.UserInfoVo;

/**
 * 订单
 * @author whl
 *
 */
@Controller
public class OrderAction {

	@Resource(name = "appProperties")
	private AppProperties appProperties;
	private Logger logger = LoggerFactory.getLogger(OrderAction.class);
	@Autowired
	private MemberTokenService memberTokenService;
	
	/**
	 *提交订单
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/app/my/submitOrder", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<String> submitOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/submitOrder");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
			Integer houseId = Integer.parseInt(request.getParameter("houseId")); //房源ID
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");  //入住人手机号
			Long startTime = Long.parseLong(request.getParameter("checkinDate")); //入住时间
			Date startDate= new Date(startTime);
			Long endTime = Long.parseLong(request.getParameter("checkoutDate"));   //离开时间
			Date endDate= new Date(endTime);
			List<Long> hotelCouponIds = getHotelCouponIdsRequest(request);// 旅居券ID列表   以前的旅居券抵扣
			String price = request.getParameter("price");
			
			Map<String, Object> liveDetail = new HashMap<String, Object>();
			liveDetail.put("user_phone", userInfo.getMobile());
			liveDetail.put("linkmanPhone", mobile);
			liveDetail.put("linkmanName", name);
			liveDetail.put("memTotalAmt", String.valueOf((int)Float.parseFloat(price)));
			liveDetail.put("orderAmt", String.valueOf((int)Float.parseFloat(price)));
			liveDetail.put("prepayment", String.valueOf((int)Float.parseFloat(price)));
			liveDetail.put("ammount", 1);
			liveDetail.put("realInSeasonDays", hotelCouponIds.size());//实际抵扣天数
			
			//订单每日房间价格
			Map<String, Object> priceData = new HashMap<String, Object>();
			priceData.put("houseId", houseId);
			Integer day = DateUtils.daysBetween(startTime,endTime);
			String dates[] = new String[day];
			for (int i = 0; i < day ; i++) {
				Calendar calendar = Calendar.getInstance();
				// 初始化 Calendar 对象，但并不必要，除非需要重置时间
				calendar.setTime(startDate);
				calendar.add(Calendar.DAY_OF_MONTH, i);
				Date time = calendar.getTime();
				String dateStr = sdf.format(time);
				dates[i] = dateStr;
			}
			priceData.put("dates", dates);
			Response<HousePriceVo> priceResult = HttpClientPostMethod.httpReqUrl(priceData,appProperties.getRequestRoot(), "getHousePrice",HousePriceVo.class);
			List<PerdayHousePriceEntity> list = new ArrayList<PerdayHousePriceEntity>();
			ArrayList<HashMap<String, Object>> prices = priceResult.getData().getPrices();
			for (int j = 0; j < prices.size(); j++) {
				PerdayHousePriceEntity perdayHousePrice = new PerdayHousePriceEntity();
				Map<String,Object> priceMap = prices.get(j);
				perdayHousePrice.setDate((String) priceMap.get("date"));
				perdayHousePrice.setOriginalPrice((Integer) priceMap.get("memTotalAmt"));
				perdayHousePrice.setPresentPrice((Integer) priceMap.get("memTotalAmt"));
				list.add(perdayHousePrice);
			}
			liveDetail.put("list", list);
			
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", userInfo.getMemberId());
			postData.put("mmWalletId", userInfo.getMemberId());
			postData.put("hotelCouponIds", hotelCouponIds); //旅居券IDs
			postData.put("houseId", houseId);
			postData.put("beginTime", sdf.format(startDate));
			postData.put("endTime", sdf.format(endDate));
			List<Integer> contactIds = new ArrayList<Integer>();
			contactIds.add(0);
			postData.put("contactIds", contactIds);
			postData.put("liveDetail", liveDetail);
			postData.put("totalRoomNum", 1);
			postData.put("pay_type", "");
			//已优惠
			//String freezeAmt = request.getParameter("discountPrice");
			float freezeAmt = Float.parseFloat(request.getParameter("discountPrice"));
			postData.put("freezeAmt",String.valueOf((int)freezeAmt));
			
			Response result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "appCreateOrder",null);
			if (result.getCode().equals("0")) {
				if (logger.isInfoEnabled()) {
				    logger.info("/app/submitOrder is finished,params is {}.");
				}
				return new ResponseOut<String>(Constants.APP_SUCCESSED,"订单提交成功",null);
			}
			//特殊处理（判断日期是否有可用房.）
			if (result.getCode().equals("56")) {
				return new ResponseOut<String>(Constants.APP_FAILED,"日期" + result.getMsg() + "无可用房.",null);
			}
			
			return new ResponseOut<String>(Constants.APP_FAILED,"订单提交失败",null);
			
		} catch (Exception e) {
			logger.error("/app/submitOrder:订单提交失败.",e);
			return new ResponseOut<String>(Constants.APP_FAILED,"订单提交失败",null);
		} 
	}
	
	/**
	 * n99订单提交
	 * @param request
	 * @param response
	 * @param orderParam 提交参数
	 * @return
	 */
	@RequestMapping(value = "/app/my/n99SubmitOrder", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public ResponseOut<N99OrderVo> n99SubmitOrder(HttpServletRequest request, HttpServletResponse response)  {
		
		logger.info("run start to /n99SubmitOrder.");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
			
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", userInfo.getMemberId());
			postData.put("houseId", Integer.parseInt(request.getParameter("houseId")));
			
			postData.put("userName",userInfo.getMemberId()); //
			postData.put("userPhone",userInfo.getMobile()); //用户手机号			
			postData.put("totalRoomNum", Integer.parseInt(request.getParameter("totalRoomNum")));//份数
			//提交订单
			Response<N99OrderVo> resultObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "n99Order",N99OrderVo.class);
			if (resultObject.getCode().equals("0")) {
				if (logger.isInfoEnabled()) {
				    logger.info("/app/submitOrder is finished,params is {}.");
				}
				N99OrderVo n99OrderVo = resultObject.getData();
				
				return new ResponseOut<N99OrderVo>(Constants.APP_SUCCESSED,"",n99OrderVo);
			}
			//特殊处理（判断日期是否有可用房.）
			if (resultObject.getCode().equals("56")) {
				return new ResponseOut<N99OrderVo>(Constants.APP_FAILED,"日期" + resultObject.getMsg() + "无可用房.",null);
			}
			
			return new ResponseOut<N99OrderVo>(Constants.APP_FAILED,"订单提交失败",null);
		} 
		catch (Exception e) {
			logger.error("订单(N99)提交失败.",e);
			result.put("code", ReturnCode.INTERFACE_ERROR);
			result.put("message", "订单(N99)提交失败.");
			return null;
		} 
	}
	
	private List<Long> getHotelCouponIdsRequest(HttpServletRequest request) {
		List<Long> ids = new ArrayList<Long>();
		String hotelCouponIdsStr = request.getParameter("coupons");
		if (StringUtils.isNotBlank(hotelCouponIdsStr)) {
			String[] hotelCouponIdsStrArray = hotelCouponIdsStr.split(",");
			if (hotelCouponIdsStrArray.length > 0) {
				for (String idStr : hotelCouponIdsStrArray) {
					if(!StringUtils.isEmpty(idStr)) {
						ids.add(Long.valueOf(idStr));
					}
				}
				return ids;
			}
		}
		return ids;
	}
	
	/**
	 * 订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/app/my/orderList", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<OrdersVo> orderList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/orderList");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<OrderEntity> orderList = new ArrayList<OrderEntity>();
		OrdersVo ordersVo = new OrdersVo();
		
		try {
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
			
			String type = request.getParameter("type");
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			
			List<Integer> list = new ArrayList<Integer>();
			if ("1".equals(type)) {
				list.add(13);
				list.add(17);
				list.add(23);
				list.add(24);
			} else if ("0".equals(type)) {
				list.add(9);
				list.add(10);
				list.add(11);
				list.add(12);
				list.add(15);
			}
			
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", userInfo.getMemberId()); //"100000420127"
			postData.put("pageNum", pageIndex);
			postData.put("pageCount", pageSize);
			postData.put("statuses", list);
			Response<GetOrdersVo> getOrdersResurt = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getOrders",GetOrdersVo.class);
			if (getOrdersResurt.getCode().equals("0")) {
				
				GetOrdersVo getOrders = getOrdersResurt.getData();
				for(int i = 0 ; i < getOrders.getOrders().size() ; i ++ ) {
					Map<String, Object> map = getOrders.getOrders().get(i);
					OrderEntity order = new OrderEntity();
					order.setOrderId(map.get("orderId").toString());
					int status = 0; 
					if(map.get("status").toString().equals("10")) { // 待审核
						status = 1;
					} else if (map.get("status").toString().equals("9")) { //待支付
						status = 3;
					} else if (map.get("status").toString().equals("11")) { //待入住
						status = 4;
					} else if (map.get("status").toString().equals("12")) { //已入住
						status = 5;
					} else if (map.get("status").toString().equals("13")) { //离店
						status = 6;
					} else if (map.get("status").toString().equals("15")) { //待客户确认
						status = 2;
					} else if (map.get("status").toString().equals("17")) { //已评价
						status = 7;
					} else if (map.get("status").toString().equals("23")) { //已取消
						status = 8;
					} else if (map.get("status").toString().equals("24")) { //已取消
						status = 8;
					}
					order.setStatus(status);
					net.sf.json.JSONObject detail = net.sf.json.JSONObject.fromObject(map.get("liveDetail").toString());
					if (detail.containsKey("houseName")) {
						order.setHouseName(detail.getString("houseName"));
					}
					if (detail.containsKey("linkmanName")) {
						order.setName(detail.getString("linkmanName"));
					}
					if (detail.containsKey("linkmanPhone")) {
						order.setMobile(detail.getString("linkmanPhone"));
					}
					if (detail.containsKey("beginTime")) {
						order.setCheckinDate(sdf.parse(detail.getString("beginTime")).getTime());
					}
					if (detail.containsKey("endTime")) {
						order.setCheckoutDate(sdf.parse(detail.getString("endTime")).getTime());
					}
					if (detail.containsKey("buildingName")) {
						order.setShopName(detail.getString("buildingName"));
					}
					if (map.containsKey("totalAmt")) {
						order.setPrice(map.get("totalAmt").toString());
					}
					order.setHouseId(Integer.parseInt(map.get("houseId").toString()));
					int a = (Integer) map.get("totalAmt");
					int b = (Integer) map.get("freezeAmt");
					order.setNewPrice(String.valueOf(a - b));
					if (detail.containsKey("hotelCouponIds")) {
						JSONArray hotelCouponIdsJsonArray = detail.getJSONArray("hotelCouponIds");
						order.setCouponCount(hotelCouponIdsJsonArray.size());
					}	
					order.setPayPrice(map.get("payAmt").toString());
					orderList.add(order);
				}
				ordersVo.setOrders(orderList);
				//分页信息
				Page page = new Page();
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setItemCount(getOrdersResurt.getData().getTotalNum());
				ordersVo.setPager(page);
			}
		} catch (Exception e ) {
			logger.error("/app/my/orderList：订单查询失败.",e);
			return new ResponseOut<OrdersVo>(Constants.APP_FAILED,"订单查询失败.",ordersVo);
		}
		return new ResponseOut<OrdersVo>(Constants.APP_SUCCESSED,"",ordersVo);
	}
	 
	/**
	 * 订单确认
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/app/my/confirmOrder", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<String> confirmOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/confirmOrder");
		}
		try {
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
			String orderId = request.getParameter("orderId");
			
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("status", 9);
			postData.put("orderId", orderId);
			postData.put("user_phone", userInfo.getMobile());
			
			Response result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot()+"/merchant","updateOrderStatus",null);
			if (result.getCode().equals("0")) {
				return new ResponseOut<String>(Constants.APP_SUCCESSED,"订单确认成功.","");
			}
			return new ResponseOut<String>(Constants.APP_FAILED,"订单确认失败.","");
		} catch (Exception e) {
			logger.error("/app/my/confirmOrder:订单确认失败.",e);
			return new ResponseOut<String>(Constants.APP_FAILED,"订单确认失败.","");
		}
		
	}
	
	/**
	 * 取消确认
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/app/my/cancelOrder", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<String> cancelOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/confirmOrder");
		}
		try {
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
			String orderId = request.getParameter("orderId");
			
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", userInfo.getMemberId());
			postData.put("orderId", orderId);
			Response result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"cancelOrder",null);
			if (result.getCode().equals("0")) {
				return new ResponseOut<String>(Constants.APP_SUCCESSED,"订单取消成功.","");
			}
			return new ResponseOut<String>(Constants.APP_FAILED,"订单取消失败.","");
		} catch (Exception e) {
			logger.error("/app/my/cancelOrder:订单取消失败.",e);
			return new ResponseOut<String>(Constants.APP_FAILED,"订单取消失败.","");
		}
		
	}
	
	/**
	 * 查询评论详情
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/app/my/getComment", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<GetCommentVo> getComment(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/a");
		}
		try {
			String orderId = request.getParameter("orderId");
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("orderId", orderId);
			Response<GetCommentVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getOrderCommentByOrderId",GetCommentVo.class);
			if (result.getCode().equals("0")) {
				return new ResponseOut<GetCommentVo>(Constants.APP_SUCCESSED,"",result.getData());
			}
			return new ResponseOut<GetCommentVo>(Constants.APP_FAILED,"评论详情获取失败.",null);
		} catch (Exception e) {
			logger.error("/app/my/getComment:评论详情获取失败.",e);
			return new ResponseOut<GetCommentVo>(Constants.APP_FAILED,"评论详情获取失败.",null);
		}
		
	}
	
}
