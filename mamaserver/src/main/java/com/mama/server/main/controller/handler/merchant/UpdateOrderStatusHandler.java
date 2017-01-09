package com.mama.server.main.controller.handler.merchant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.OrderPayDO;
import com.mama.server.main.dao.model.BuildingPo;
import com.mama.server.main.dao.model.CityPo;
import com.mama.server.main.dao.model.FlowPo;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.main.dao.mongodb.IHouseSearchDao;
import com.mama.server.main.service.PayIdGenerateService;
import com.mama.server.util.Json;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.SmsUtil;
import com.meidusa.fastjson.JSONArray;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.domain.common.PayStatusEnum;
import com.mmzb.charge.domain.domain.PayDomain;

/**
 * 订单生命周期管理
 * @author majiafei
 *
 */
@Component
public class UpdateOrderStatusHandler extends BaseHandler {
	@Resource(name="payIdGenerateService")
	private PayIdGenerateService payIdGenerateService;
	
	@Resource
	private IHouseSearchDao houseSearchDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(UpdateOrderStatusHandler.class);
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Start to update order status, param is {}.", param.toString());
		}
		
		try {
			//1.基础参数校验
			if (!checkParams(param)) {
				return outputMap;
			}
			
            //2.获取订单详情
			String orderId = (String) param.get("orderId");
			OrderPo order = new OrderPo();
			order.setOrderId(orderId);
			List<OrderPo> orderList = mainService.getOrderByAllParam(order);
			if (orderList == null || orderList.size() == 0) {
				LOG.error("param error, invalid orderId:{}.", orderId);
				genErrOutputMapWithCode("param error, invalid orderId", ReturnCode.GET_ORDER_ERROR);
				
				return outputMap;
			}
			
			order = orderList.get(0);
			
			//3.检查房源信息
			int houseId = order.getHouseId();
			if (!checkResource(houseId)) {
				return outputMap;
			}

			String verifyCode = null;
			if (param.get("verifyCode") != null) {
				verifyCode = (String) param.get("verifyCode");
			}else {
				// 生成订单验证码
				verifyCode = mainService.genVerifyCode(ConstValue.SMS_ORDER);
			}
			
			int status = (Integer) param.get("status");
			int currentStatus = order.getStatus();
			
			//待审核-->其它状态
			if (currentStatus == ConstValue.ORDER_MERCHANT_VERIFYING) {
				return handleVerifyingOrder(verifyCode, param, order);
			}
			//待入住-->其它状态
			else if (currentStatus == ConstValue.ORDER_MERCHANT_VERIFIED) {
				return handleVerifiedOrder(verifyCode, param, order);
			}
			//已入住-->其它状态
			else if (currentStatus == ConstValue.ORDER_USER_LIVED) {
				return handleLivedOrder(order, param);
			}
			//待确认-->其它状态
			else if (currentStatus == ConstValue.ORDER_USER_TO_COMMIT) {
				return handleConfirmingOrder(verifyCode, param, order);
			}
			//已取消
			else if (currentStatus == ConstValue.ORDER_MERCHANT_REFUND) {
				genSuccOutputMap();
				return outputMap;
			}
			//待支付
			else if (currentStatus == ConstValue.ORDER_WAITING_FOR_PAYMENT) {
				if (status==ConstValue.ORDER_WAITING_FOR_PAYMENT) {
					genSuccOutputMap();
					return outputMap;
				}
			}
			//已离 店 -->已评价
			else if (currentStatus == ConstValue.ORDER_USER_LEAVED){
				return handleLeavedOrder(param, order);
			}
			
			genErrOutputMapWithCode("param error, invalid status", ReturnCode.UPDATE_ORDER_ERROR);
			
			// 修改旅居券的状态
			if (StringUtils.isNotBlank(order.getLiveDetail())) {
				JSONObject jsonObject = JSONObject.parseObject(order.getLiveDetail());
				String hotelCouponIdsKey = "hotelCouponIds";
				if (jsonObject.containsKey(hotelCouponIdsKey)) {
					JSONArray jsonArray = jsonObject.getJSONArray(hotelCouponIdsKey);
					if (jsonArray != null && jsonArray.size() > 0) {
						List<Long> ids = new ArrayList<Long>();
						for (int i = 0; i < jsonArray.size(); i++) {
							ids.add(jsonArray.getLongValue(i));
						}
						mainService.updateHotelCouponStatus(ids, 11);
					}
				}
			}
			
			return outputMap;
		} catch (Exception e) {
			log.error("update order status failed !", e);
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

	private HashMap<String, Object> handleLeavedOrder(HashMap<String, Object> param, OrderPo order)
	{
		int status = (Integer) param.get("status");
		if (status == ConstValue.ORDER_USER_COMMENTED) {
			order.setStatus(status);
			if (mainService.updateOrder(order) != 0) {
				genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
				return outputMap;
			}
		}
		
		genSuccOutputMap();
		return outputMap;
	}

	private List<Long> getHotelCoupons(OrderPo order)
	{
		List<Long> hotelCouponIds = null;
		if (StringUtils.isNotBlank(order.getLiveDetail())) {
			JSONObject jsonObject = JSONObject.parseObject(order.getLiveDetail());
			String hotelCouponIdsKey = "hotelCouponIds";
			if (jsonObject.containsKey(hotelCouponIdsKey)) {
				JSONArray jsonArray = jsonObject.getJSONArray(hotelCouponIdsKey);
				if (jsonArray != null && jsonArray.size() > 0) {
					hotelCouponIds = new ArrayList<Long>();
					for (int i = 0; i < jsonArray.size(); i++) {
						hotelCouponIds.add(jsonArray.getLongValue(i));
					}
				}
			}
		}
		return hotelCouponIds;
	}

	//参数校验
	private boolean checkParams(HashMap<String, Object> param)
	{
		if (param.get("orderId") == null) {
			LOG.error("param error, orderId required");
			genErrOutputMapWithCode("param error, orderId required", ReturnCode.PARAM_ERROR);
			
			return false;
		}
		
		if (param.get("status") == null) {
			LOG.error("param error, status required");
			genErrOutputMapWithCode("param error, status required", ReturnCode.PARAM_ERROR);
			
			return false;
		}
		
		return true;
	}

	private boolean checkResource(int houseId)
	{
		//house是否存在
		HousePo house = new HousePo();
		house.setHouseId(houseId);
		List<HousePo> houseList = mainService.getHouseByAllParam(house);
		if (houseList == null || houseList.size() == 0) {
			LOG.error("failed to get house, houseid is {}.", houseId);
			genErrOutputMapWithCode("fail to get house", ReturnCode.GET_HOUSE_ERROR);
			
			return false;
		}
		
		house = houseList.get(0);
		int cityId = house.getCityId();
		
		//city是否存在
		CityPo city = new CityPo();
		city.setCityId(cityId);
		List<CityPo> cityList = mainService.getCity();
		if (cityList == null || cityList.size() == 0) {
			LOG.error("failed to get city list.cityid is {}.", cityId);
			genErrOutputMapWithCode("fail to get city list", ReturnCode.GET_CITY_ERROR);
			return false;
		}

		//building是否存在
		int bldId = house.getBldId();
		BuildingPo building = new BuildingPo();
		building.setBldId(bldId);
		List<BuildingPo> buildingList = mainService.getBuilding(building);
		if (buildingList == null || buildingList.size() == 0) {
			LOG.error("failed to get building.bldId is {}.", bldId);
			genErrOutputMapWithCode("failed to get building list", ReturnCode.GET_BUILDING_ERROR);
			return false;
		}
		
		return true;
	}

	private HashMap<String, Object> handleConfirmingOrder(String verifyCode, HashMap<String, Object> param, OrderPo order)
	{
		//待确认改为待入住或待支付
		int status = (Integer) param.get("status");
		if (status != ConstValue.ORDER_MERCHANT_VERIFIED && status!= ConstValue.ORDER_WAITING_FOR_PAYMENT && status!= ConstValue.ORDER_MERCHANT_REFUND) {
			genErrOutputMapWithCode("param error, invalid status", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}

		order.setVerifyCode(verifyCode);
		
		order.setStatus(status);
		if (mainService.updateOrder(order) != 0) {
			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		
		//更新库存，数量+1
		if (status == ConstValue.ORDER_MERCHANT_REFUND)
		{
			updateInventory(order, 1);
		}
		
//		String user_phone = getOrderInfoByKey(order, "user_phone");
//		String beginTime = getOrderInfoByKey(order, "beginTime");
//		String endTime = getOrderInfoByKey(order, "endTime");
//		String houseName = getOrderInfoByKey(order, "houseName");
//		if (ConstValue.ORDER_WAITING_FOR_PAYMENT==status) {
//			//待支付短信下发
//			//短信通知用户
//			String userMsg = SmsUtil.genPayingOrderNotice2User(beginTime, endTime, houseName);
//			SmsUtil.sendSms(user_phone, userMsg);
//			
//		}
//		else {
//			//待确认， 妈妈客服取消，短信通知用户
//			String userMsg = SmsUtil.genCancelOrderNotice2User(beginTime, endTime, houseName);
//			SmsUtil.sendSms(user_phone, userMsg);
//			
//			// 短信通知商家
//			String phone = getOrderInfoByKey(order, "phone");
//			String msg = SmsUtil.genCancelOrderByUser(phone, order.getOrderId());
//			SmsUtil.sendSms(phone, msg);
//		}

		genSuccOutputMap();
		return outputMap;
	}

	private HashMap<String, Object> handleVerifiedOrder(String verifyCode, HashMap<String, Object> param, OrderPo order)
	{
		int status = (Integer) param.get("status");
		// 处于已确认有房状态，商户仅可以确认用户入住
		if (status != ConstValue.ORDER_USER_LIVED && status != ConstValue.ORDER_MERCHANT_REFUND && status!=ConstValue.ORDER_MERCHANT_VERIFIED) {
			genErrOutputMapWithCode("param error, invalid status", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		// 如果是确认入住
		if (status == ConstValue.ORDER_USER_LIVED) {
			return updateLivedOrder(verifyCode, order);				
		}
		//
		else if (status == ConstValue.ORDER_MERCHANT_VERIFIED) {
			genSuccOutputMap();
			return outputMap;
		} 
		else {
			order.setStatus(ConstValue.ORDER_MERCHANT_REFUND);
			if (mainService.updateOrder(order) != 0) {
				genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
				return outputMap;
			} else {
				HouseOrderPo ho = new HouseOrderPo();
				ho.setRemoved(1);
				ho.setOrderId(order.getOrderId());
				mainService.updateHouseOrderByOrderId(ho);
			}

			// 如使用旅居券，退还旅居券
//			if (hotelCouponIds != null && !hotelCouponIds.isEmpty())
//				mainService.updateHotelCouponStatus(hotelCouponIds, 0);
		}
		genSuccOutputMap();
		return outputMap;
	}

	//处理待审核的订单
	private HashMap<String, Object> handleVerifyingOrder(String verifyCode, HashMap<String, Object> param, OrderPo order)
	{
		int status = (Integer) param.get("status");
		
		// 处于待客服确认状态，商户仅可确认有房或确认没房
		if (status != ConstValue.ORDER_MERCHANT_VERIFIED && status != ConstValue.ORDER_USER_PRE_FAIL && status != ConstValue.ORDER_WAITING_FOR_PAYMENT
				&& status != ConstValue.ORDER_USER_TO_COMMIT && status != ConstValue.ORDER_MERCHANT_REFUND) {
			genErrOutputMapWithCode("param error, invalid status", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		
		//TODO:预订失败
		if (status == ConstValue.ORDER_USER_PRE_FAIL) {
			return upadateBookFailedOrder(order);
		}
		
		//已取消（退还资金）
		if (status == ConstValue.ORDER_MERCHANT_REFUND) {
			return updateRefundedOrder(param, order);
		}
		
		//待用户确认
		if (status == ConstValue.ORDER_USER_TO_COMMIT) {
			
			return updateConfirmingOrder(order);
		}
		
		//待支付
		if (status == ConstValue.ORDER_WAITING_FOR_PAYMENT) {
			
			if (order.getPayAmt() > 0) {
				// 需要支付订单
				return updatePayingOrder(verifyCode, param, order);
				
			} else {
				// 定金金额为0，无需支付订单，直接入住
				// 生成订单验证码
				return updateFreeOrder(order);
			}
		}
		
		genErrOutputMap("unknown status： " + status);
		return outputMap;

	}

	private HashMap<String, Object> handleLivedOrder(OrderPo order, HashMap<String, Object> param)
	{
		int status = (Integer) param.get("status");
		if (status != ConstValue.ORDER_USER_LEAVED) {
			genErrOutputMapWithCode("param error, invalid status", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}

		// 修改旅居券为已使用
		List<Long> hotelCouponIds = getHotelCoupons(order);
		if (hotelCouponIds != null && !hotelCouponIds.isEmpty())
			mainService.updateHotelCouponStatus(hotelCouponIds, 11);

		order.setStatus(ConstValue.ORDER_USER_LEAVED);
		if (mainService.updateOrder(order) != 0) {
			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		
		//更新库存，数量+1
		updateInventory(order, 1);
		
		// 离店发短信给用户
		String phone = getOrderInfoByKey(order, "linkmanPhone");
		String msg = SmsUtil.genLeavedOrderNotice2User(phone);
		SmsUtil.sendSms(phone, msg);
		
		// 离店发短信给妈妈客服
		String houseName = getOrderInfoByKey(order, "houseName");
		String msg_mama = SmsUtil.genLeavedOrderNotice2Mama(phone, houseName);
		SmsUtil.sendSms(PropertiesUtils.getMmManagerPhone(), msg_mama);

		genSuccOutputMap();
		return outputMap;
	}

	private HashMap<String, Object> updateLivedOrder(String verifyCode, OrderPo order)
	{
		// 检查验证码
		if (verifyCode == null || verifyCode.compareTo(order.getVerifyCode()) != 0) {
			genErrOutputMapWithCode("incorrect verify code", ReturnCode.CHECK_VERIFY_CODE_FAILED);
			return outputMap;
		}

		// 增加扣款流水
		FlowPo flow = new FlowPo();
		flow.setUid(order.getUid());
		flow.setAmt(order.getTotalAmt() - order.getFreezeAmt());
		flow.setType(ConstValue.FLOW_PAY);
		flow.setShowDetail("");
		flow.setOperTime(mainService.getCurrentDatetime());
		flow.setOrderId(order.getOrderId());
		
		String houseName = getOrderInfoByKey(order, "houseName");
		flow.setShowDetail("支付房费(" + houseName + ")");
		if (mainService.insertFlow(flow) != 0) {
			genErrOutputMapWithCode("fail to add flow", ReturnCode.ADD_FLOW_ERROR);
			return outputMap;
		}
		// 更新订单状态为确认入住
		order.setStatus(ConstValue.ORDER_USER_LIVED);
		if (mainService.updateOrder(order) != 0) {
			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}

		
		//更新状态
		PayDomain payDomain = new PayDomain();
		
		payDomain.setOrderId(order.getOrderId());
//					payDomain.setPayType("offlinepay");
		OrderPayDO orderPayDO = new OrderPayDO();
		orderPayDO = mainService.findOrderBypar(order.getOrderId());
		orderPayDO.setStatus(PayStatusEnum.PAY_SUCCESS.getCode());
		
		if (mainService.updateByPrimaryKey(orderPayDO)!=1) {
			genErrOutputMapWithCode("fail to updateOrderPay", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		
		// 发短信给用户
//		String phone = getOrderInfoByKey(order, "linkmanPhone");
//		int prepayment = Integer.parseInt(getOrderInfoByKey(order, "prepayment"));
//		String msg = SmsUtil.genUserLivedOrderSms(phone, houseName, order.getOrderId(), prepayment);
//		SmsUtil.sendSms(phone, msg);
		
		// 短信通知妈妈后台
//		String msg_mama = SmsUtil.getOrderStoreSuccessForMama(phone, order.getOrderId());
//		SmsUtil.sendSms(PropertiesUtils.getMmManagerPhone(), msg_mama);
//		genSuccOutputMap();
		
		return outputMap;
	}

	private HashMap<String, Object> updateFreeOrder(OrderPo order)
	{
		String orderVerifyCode = mainService.genVerifyCode(ConstValue.SMS_ORDER);
		
		String phone = getOrderInfoByKey(order, "user_phone");
		SmsPo sms = new SmsPo();
		sms.setType(ConstValue.SMS_ORDER);
		sms.setContent(orderVerifyCode);
		sms.setPhone(phone);
		sms.setStatus(0);
		sms.setUsed(0);
		sms.setUid(order.getUid());
		if (mainService.insSms(sms) != 0) {
			genErrOutputMap("fail to send sms");
			return outputMap;
		}
		
		// 发短信
		String orderId = order.getOrderId();
		String houseName = getOrderInfoByKey(order, "houseName");
		int prepayment = Integer.parseInt(getOrderInfoByKey(order, "prepayment"));
		String msg = SmsUtil.genMerchantVerifiedOrderSms(phone, orderId, houseName, orderVerifyCode, prepayment);
		SmsUtil.sendSms(phone, msg);
		// 如果联系人与持卡人手机号不一致，需要给联系人发送短信通知
		String linkmanPhone = getOrderInfoByKey(order, "linkmanPhone");
		if (!phone.equals(linkmanPhone)) {
			String msgcontent = SmsUtil.genMerchantVerifiedOrderSms(linkmanPhone, orderId, houseName, orderVerifyCode, prepayment);
			SmsUtil.sendSms(linkmanPhone, msgcontent);
		}

		// 更新订单信息
		order.setStatus(ConstValue.ORDER_MERCHANT_VERIFIED);
		order.setVerifyCode(orderVerifyCode);
		if (mainService.updateOrder(order) != 0) {
			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		genSuccOutputMap();
		return outputMap;
	}

	private HashMap<String, Object> updatePayingOrder(String verifyCode, HashMap<String, Object> param, OrderPo order){

			return handleWXPaidOrder(order);
	}

	private HashMap<String, Object> handleWXPaidOrder(OrderPo order)
	{
		//微信支付
		order.setStatus(ConstValue.ORDER_WAITING_FOR_PAYMENT);
		if (mainService.updateOrder(order) != 0) {
			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		
		//短信通知预订人
		String user_phone = getOrderInfoByKey(order, "user_phone");
		String beginTime = getOrderInfoByKey(order, "beginTime");
		String endTime = getOrderInfoByKey(order, "endTime");
		String houseName = getOrderInfoByKey(order, "houseName");
		SmsUtil.sendSms(user_phone, SmsUtil.genPayingOrderNotice2User(beginTime, endTime, houseName));
		genSuccOutputMap();
		return outputMap;
	}

//	private HashMap<String, Object> handleOfflinePaidOrder(String verifyCode,OrderPo order)
//	{
//		//创建order_pay记录
//		int  money = order.getTotalAmt()*100; //金额
//		PayDomain payDomain = new PayDomain();
//		String payId = payIdGenerateService.getPayID();
//		payDomain.setPayId(payId);
//		payDomain.setFee(String.valueOf(money));
//		payDomain.setOrderId(order.getOrderId());
//		Date date = new Date(); 
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
//		payDomain.setAccountDate(sdf.format(date));
//		payDomain.setStatus(PayStatusEnum.ORDER_SUCCESS.getCode());
//		payDomain.setPayType("offlinepay");
//		
//		if (mainService.order(payDomain)!=1) {
//			genErrOutputMapWithCode("fail to createOrderPay", ReturnCode.UPDATE_ORDER_ERROR);
//			return outputMap;
//		}
//		order.setVerifyCode(verifyCode);
//		order.setStatus(ConstValue.ORDER_MERCHANT_VERIFIED);
//		if (mainService.updateOrder(order) != 0) {
//			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
//			return outputMap;
//		}
//		
//		//短信通知用户
//		
//		SmsUtil.sendSms(user_phone, SmsUtil.getOrderStoreToPay(user_phone, orderId,verifyCode));
//		
//		// 短信通知妈妈后台
//		String msg = SmsUtil.getOrderStoreToPayForMama(phone, orderId);
//		SmsUtil.sendSms(PropertiesUtils.getMmManagerPhone(), msg);
//		
//		genSuccOutputMap();
//		return outputMap;
//	}

	private HashMap<String, Object> updateConfirmingOrder(OrderPo order)
	{
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start to invoke updateConfirmingOrder");
		}
		
		// 更新订单状态为待用户确认状态
		order.setStatus(ConstValue.ORDER_USER_TO_COMMIT);
		if (mainService.updateOrder(order) != 0) {
			
			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		
		//短信通知用户价格已修改
		String user_phone = getOrderInfoByKey(order, "user_phone");
		String beginTime = getOrderInfoByKey(order, "beginTime");
		String endTime = getOrderInfoByKey(order, "endTime");
		String houseName = getOrderInfoByKey(order, "houseName");
		String msg = SmsUtil.genConfirmingOrderNotice2User(beginTime, endTime, houseName);
		SmsUtil.sendSms(user_phone, msg);
		
		genSuccOutputMap();
		return outputMap;
	}

	private HashMap<String, Object> updateRefundedOrder(HashMap<String, Object> param, OrderPo order)
	{
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start to invoke updateRefundedOrder");
		}
		
		// 如使用旅居券，退还旅居券
		List<Long> hotelCouponIds = getHotelCoupons(order);
		if (hotelCouponIds != null && !hotelCouponIds.isEmpty())
			mainService.updateHotelCouponStatus(hotelCouponIds, 0);

		// 增加流水
		FlowPo flow = new FlowPo();
		flow.setUid(order.getUid());
		flow.setAmt(order.getFreezeAmt());
		flow.setOperTime(mainService.getCurrentDatetime());
		flow.setShowDetail("已取消，返还定金");
		flow.setType(ConstValue.FLOW_UNFROZEN);
		flow.setOrderId(order.getOrderId());
		if (mainService.insertFlow(flow) != 0) {
			genErrOutputMapWithCode("fail to add flow", ReturnCode.ADD_FLOW_ERROR);
			return outputMap;
		}
		// 更新订单状态为已取消
		String cancelOrdeReason = (String)param.get("cancelOrdeReason");
		order.setStatus(ConstValue.ORDER_MERCHANT_REFUND);
		order.setCancelOrdeReason(cancelOrdeReason);
		if (mainService.updateOrder(order) != 0) {
			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		// 更新houseOrder为删除状态
		HouseOrderPo houseOrder = new HouseOrderPo();
		houseOrder.setOrderId(order.getOrderId());
		List<HouseOrderPo> houseOrders = mainService.getHouseOrderByAllParam(houseOrder);
		if (houseOrders == null) {
			genErrOutputMapWithCode("fail to house order", ReturnCode.GET_HOUSE_ORDER_ERROR);
			return outputMap;
		}
		for (int i = 0; i < houseOrders.size(); ++i) {
			houseOrder = houseOrders.get(i);
			houseOrder.setRemoved(1);
			mainService.updateHouseOrderById(houseOrder);
		}
		
		//更新库存,数量+1
		updateInventory(order, 1);
		
		//短信通知用户
		String user_phone = getOrderInfoByKey(order, "user_phone");
		String beginTime = getOrderInfoByKey(order, "beginTime");
		String endTime = getOrderInfoByKey(order, "endTime");
		String houseName = getOrderInfoByKey(order, "houseName");
		SmsUtil.sendSms(user_phone, SmsUtil.genCancelOrderNotice2User(beginTime, endTime, houseName));
		
		// 短信通知妈妈后台
//		String msg = SmsUtil.genCancelOrderByCom(user_phone, orderId,cancelOrdeReason);
//		SmsUtil.sendSms("15817459283", msg);
		
		genSuccOutputMap();
		return outputMap;
	}

	private HashMap<String, Object> upadateBookFailedOrder(OrderPo order)
	{
		// 如使用旅居券，退还旅居券
		List<Long> hotelCouponIds = getHotelCoupons(order);
		if (hotelCouponIds != null && !hotelCouponIds.isEmpty())
			mainService.updateHotelCouponStatus(hotelCouponIds, 0);

		// 增加流水
		FlowPo flow = new FlowPo();
		flow.setUid(order.getUid());
		flow.setAmt(order.getFreezeAmt());
		flow.setOperTime(mainService.getCurrentDatetime());
		flow.setShowDetail("预订失败，返还定金");
		flow.setType(ConstValue.FLOW_UNFROZEN);
		flow.setOrderId(order.getOrderId());
		if (mainService.insertFlow(flow) != 0) {
			genErrOutputMapWithCode("fail to add flow", ReturnCode.ADD_FLOW_ERROR);
			return outputMap;
		}
		// 更新订单状态为预订失败
		order.setStatus(ConstValue.ORDER_USER_PRE_FAIL);
		if (mainService.updateOrder(order) != 0) {
			genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
			return outputMap;
		}
		// 更新houseOrder为删除状态
		HouseOrderPo houseOrder = new HouseOrderPo();
		houseOrder.setOrderId(order.getOrderId());
		List<HouseOrderPo> houseOrders = mainService.getHouseOrderByAllParam(houseOrder);
		if (houseOrders == null) {
			genErrOutputMapWithCode("fail to house order", ReturnCode.GET_HOUSE_ORDER_ERROR);
			return outputMap;
		}
		for (int i = 0; i < houseOrders.size(); ++i) {
			houseOrder = houseOrders.get(i);
			houseOrder.setRemoved(1);
			mainService.updateHouseOrderById(houseOrder);
		}
		
		//TODO: 发短信
//		String msg = SmsUtil.genMerchantCancelOrderSms(phone, orderId, houseName, "房源不足");
//		SmsUtil.sendSms(phone, msg);
		genSuccOutputMap();
		return outputMap;
	}
	
	private String getOrderInfoByKey(OrderPo order, String key){
		HashMap<String, Object> summaryInfo = (HashMap<String, Object>) Json.parse(order.getLiveDetail(), Object.class);
		
		if (summaryInfo.get(key) != null) {
			return summaryInfo.get(key).toString();
		}
		
		return "";
	}
	
	private void updateInventory(OrderPo po, int inc)
	{
		try 
		{
			long houseId = po.getHouseId();
			String startDate = getOrderInfoByKey(po, "beginTime");
			String endDate = getOrderInfoByKey(po, "endTime");
			long dayTimeLong = 24 * 60 * 60 * 1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long startDateLong = sdf.parse(startDate).getTime();
			long endDateLong = sdf.parse(endDate).getTime();
			
			Map<Long, Integer> inventory = new HashMap<Long, Integer>();
			for(long i = startDateLong; i <= endDateLong; i += dayTimeLong)
			{
				inventory.put(i, inc);
			}
			
			houseSearchDao.updateInventory(houseId, inventory);
		} 
		catch (Exception e)
		{
			LOG.error("Failed to update house inventory.", e);
		}
	}
}
