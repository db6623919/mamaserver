package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;

@Component
public class GetOrderInfoHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("orderId") == null) {
                genErrOutputMapWithCode("param error, orderId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            String orderId = (String)param.get("orderId");
            
            OrderPo order = new OrderPo();
            order.setOrderId(orderId);
            List<OrderPo> orderList = mainService.getOrderByAllParam(order);
            if (orderList == null || orderList.size() == 0) {
                genErrOutputMapWithCode("param error, orderId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            dataMap.put("uid", orderList.get(0).getUid());
            dataMap.put("orderId", orderList.get(0).getOrderId());
            dataMap.put("houseId", orderList.get(0).getHouseId());
            dataMap.put("operTime", orderList.get(0).getOperTime());
            dataMap.put("status", orderList.get(0).getStatus());
            dataMap.put("freezeAmt", orderList.get(0).getFreezeAmt());
            dataMap.put("totalAmt", orderList.get(0).getTotalAmt());
            dataMap.put("payAmt", orderList.get(0).getPayAmt());
            dataMap.put("liveDetail", orderList.get(0).getLiveDetail());
            dataMap.put("totalRoomNum", orderList.get(0).getTotalRoomNum());
            dataMap.put("verifyCode", orderList.get(0).getVerifyCode());
            dataMap.put("pay_type", orderList.get(0).getPay_type());
            dataMap.put("orderType", orderList.get(0).getOrderType());
            dataMap.put("shopId", orderList.get(0).getShopId());
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
