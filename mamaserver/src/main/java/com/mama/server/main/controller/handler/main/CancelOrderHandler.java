package com.mama.server.main.controller.handler.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.mongodb.InventoryPo;
import com.mama.server.util.Json;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.SmsUtil;
import com.meidusa.fastjson.JSONArray;
import com.meidusa.fastjson.JSONObject;

@Component
public class CancelOrderHandler extends BaseHandler {

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			if (param.get("uid") == null) {
				genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (param.get("orderId") == null) {
				genErrOutputMapWithCode("param error, orderId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			String uid = (String) param.get("uid");
			String orderId = (String) param.get("orderId");
			String phone = (String)param.get("phone");
			String userPhone = (String)param.get("userPhone");

			OrderPo order = new OrderPo();
			order.setUid(uid);
			order.setOrderId(orderId);
			List<OrderPo> orderList = mainService.getOrderByAllParam(order);
			if (orderList == null) {
				genErrOutputMapWithCode("get order error", ReturnCode.GET_ORDER_ERROR);
				return outputMap;
			}
			order = orderList.get(0);

			if (order.getStatus() != ConstValue.ORDER_MERCHANT_VERIFIED && order.getStatus() != ConstValue.ORDER_USER_TO_COMMIT && order.getStatus() != ConstValue.ORDER_MERCHANT_VERIFYING
					&& order.getStatus() !=  ConstValue.ORDER_WAITING_FOR_PAYMENT) {
				genErrOutputMapWithCode("order can't be cancelled", ReturnCode.CANCEL_ORDER_FAILED);
				return outputMap;
			}

			order.setStatus(ConstValue.ORDER_MERCHANT_REFUND);
			if (mainService.updateOrder(order) != 0) {
				genErrOutputMapWithCode("update order error", ReturnCode.UPDATE_ORDER_ERROR);
				return outputMap;
			}else{
				HouseOrderPo ho=new HouseOrderPo();
				ho.setRemoved(1);
				ho.setOrderId(order.getOrderId());
				mainService.updateHouseOrderByOrderId(ho);
			}

			// 取消订单，如使用旅居券，退还旅居券
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
						mainService.updateHotelCouponStatus(ids, 0);
					}
				}
			}

			//短信商家
//			SmsUtil.sendSms(phone, SmsUtil.genCancelOrderByUser(userPhone, orderId));
			
			// 短信通知妈妈后台
			String beginTime = getOrderInfoByKey(order, "beginTime");
			String endTime = getOrderInfoByKey(order, "endTime");
			String houseName = getOrderInfoByKey(order, "houseName");
			String usePhone = getOrderInfoByKey(order, "user_phone");
		    int totalAmt = order.getTotalAmt();
			String msg = SmsUtil.genCancelOrderNotice2Mama(beginTime, endTime, houseName, usePhone, orderId, totalAmt);
			SmsUtil.sendSms(PropertiesUtils.getMmManagerPhone(), msg);
			
			//更新mongodb库存
			int ammount = getOrderInfoBykey(order, "ammount");
		    Date beginDate = simpleDateFormat.parse(beginTime);
		    Date endDate = simpleDateFormat.parse(endTime);
		    long day = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
		    for (int i = 0; i < day; i++) {
				InventoryPo inventory = new InventoryPo();
				inventory.setHouseId(order.getHouseId());
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, i);
				String str_date =  simpleDateFormat.format(c.getTime()).trim();
				inventory.setDate(str_date);
				InventoryPo inventoryPo = inventoryService.findInventoryByPar(inventory);
				
				if (null!=inventoryPo) {
					int invertory = inventoryPo.getInventory()+ammount;
					inventory.setInventory(invertory);
					inventoryService.updateInventory(inventory);
				}
			}
			
		} catch (Exception e) {
			genErrOutputMap("interface error");
			log.error("CancelOrderHandler exception:", e);
		}
		genSuccOutputMap();
		return outputMap;
	}
	
	private String getOrderInfoByKey(OrderPo order, String key){
		HashMap<String, Object> summaryInfo = (HashMap<String, Object>) Json.parse(order.getLiveDetail(), Object.class);
		
		if (summaryInfo.get(key) != null) {
			return (String) summaryInfo.get(key);
		}
		
		return "";
	}
	
	private int getOrderInfoBykey(OrderPo order, String key){
		HashMap<String, Object> summaryInfo = (HashMap<String, Object>) Json.parse(order.getLiveDetail(), Object.class);
		
		if (summaryInfo.get(key) != null) {
			return (Integer) summaryInfo.get(key);
		}
		
		return 0;
	}
}
