package com.mama.server.main.controller.handler.merchant;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;
import com.mama.server.util.SmsUtil;

@Component
public class UpdateOrderLiveDetailHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("orderId") == null) {
                genErrOutputMapWithCode("param error, orderId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("liveDetail") == null) {
                genErrOutputMapWithCode("param error, liveDetail required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String orderId = (String)param.get("orderId");
            String liveDetail = (String)param.get("liveDetail");
            int totalAmt = Integer.parseInt(String.valueOf(param.get("totalAmt")));//实际支付价格
            String user_phone = (String)param.get("user_phone");
            
            OrderPo order = new OrderPo();
            order.setOrderId(orderId);
            List<OrderPo> orderList = mainService.getOrderByAllParam(order);
            if (orderList == null || orderList.size() == 0) {
                genErrOutputMapWithCode("param error, invalid orderId", ReturnCode.GET_ORDER_ERROR);
                return outputMap;
            }
            
            order = orderList.get(0);
            order.setLiveDetail(liveDetail);
            order.setPayAmt(totalAmt);
            order.setFreezeAmt(order.getTotalAmt()-order.getPayAmt());
            if (mainService.updateOrder(order) != 0) {
                genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
                return outputMap;
            }

            //发短信给客户
//            String houseName = (String)param.get("houseName");
//            String beginTime = getOrderInfoByKey(order, "beginTime");
//            String endTime = getOrderInfoByKey(order, "endTime");
//			String msg = SmsUtil.genConfirmingOrderNotice2User(beginTime, endTime, houseName);
//			SmsUtil.sendSms(user_phone, msg);
			
//			String mama_msg = SmsUtil.genBeMamaDeterminedOrderSms(user_phone, orderId, (String)param.get("houseName"),(String)param.get("data"),
//					(String)param.get("presentPrice"),(String)param.get("originalPrice"));
//			SmsUtil.sendSms("15817459283", mama_msg);
			
			//修改订单状态为待确认状态
			
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private String getOrderInfoByKey(OrderPo order, String key){
		HashMap<String, Object> summaryInfo = (HashMap<String, Object>) Json.parse(order.getLiveDetail(), Object.class);
		
		if (summaryInfo.get(key) != null) {
			return (String) summaryInfo.get(key);
		}
		
		return "";
	}
}
