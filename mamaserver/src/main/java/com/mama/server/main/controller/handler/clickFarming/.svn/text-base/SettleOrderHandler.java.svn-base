package com.mama.server.main.controller.handler.clickFarming;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.util.AppProperties;

/** 订单结算 */
@Component
public class SettleOrderHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(SettleOrderHandler.class);
	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		if (logger.isInfoEnabled()) {
			logger.info("Start to update order status, param is {}.", param.toString());
		}
		try {
			String orderId = (String) param.get("orderId");//业务订单号
			OrderPo orderPo = new OrderPo();
			orderPo.setOrderId(orderId);
			orderPo.setSettleStatus(1);//0:未结算 1：已结算
			mainService.updateOrder(orderPo);
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SettleOrderHandler:订单结算接口错误：", e);
			outputMap.put("exception", e.getMessage());
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
	
}
