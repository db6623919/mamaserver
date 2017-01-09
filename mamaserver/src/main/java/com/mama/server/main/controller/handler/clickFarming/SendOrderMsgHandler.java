package com.mama.server.main.controller.handler.clickFarming;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.mongodb.HouseShop;
import com.mama.server.util.SmsUtil;

/**
 * 刷单发送短信
 * @author whl
 *
 */
@Component
public class SendOrderMsgHandler extends BaseHandler {

	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(SendOrderMsgHandler.class);
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run SendOrderMsgHandler.");
		
		try {
			String userName = (String) param.get("userName");
			String orderId = (String) param.get("orderId");
			
			OrderPo order = new OrderPo();
			order.setOrderId(orderId);
			//查询订单
			List<OrderPo> orderList = mainService.getOrderByAllParam(order);
			//订单是否存在、是否为刷单数据
			if (orderList.size() > 0 && orderList.get(0).getOrderType() == 2) {
				//查询客栈
				HouseShop houseShop = iHouseShopService.searchHouseShopPoById(String.valueOf(orderList.get(0).getShopId()));
				if (houseShop == null) {
					logger.error("SendOrderMsgHandler:查询客栈不存在.");
				}
				String phone = houseShop.getBossPhone();
				String msg = "客户" +  userName + "预订的房间，已经支付成功。订单总价" + orderList.get(0).getTotalAmt() + "，优惠：" + orderList.get(0).getFreezeAmt() + "，实际支付：" + orderList.get(0).getPayAmt() + "，订单号" + orderId + "，谢谢！";
				//发送短信
				SmsUtil.sendSms(phone, msg);
			}
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("SendOrderMsgHandler interface error",e);
		}
		return outputMap;
	}

}
