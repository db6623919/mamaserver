package com.mama.server.main.controller.handler.merchant;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;

/**
 * 修改订单价格信息
 * @author lihaifeng
 * 
 */
@Component
public class UpdateOrderPricelHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(UpdateOrderPricelHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String orderId = (String)param.get("orderId");
            if (orderId == null || orderId.trim().length() < 1) {
                genErrOutputMapWithCode("参数错误, orderId 不能为空", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int payAmt = Integer.parseInt(String.valueOf(param.get("payAmt")));//实际支付价格
            
            OrderPo order = new OrderPo();
            order.setOrderId(orderId);
            List<OrderPo> orderList = mainService.getOrderByAllParam(order);
            if (orderList == null || orderList.size() == 0) {
                genErrOutputMapWithCode("参数错误，未查到对应的订单信息", ReturnCode.GET_ORDER_ERROR);
                return outputMap;
            }
            
            order = orderList.get(0);
            order.setPayAmt(payAmt);
            order.setFreezeAmt(order.getTotalAmt() - order.getPayAmt());
            if (mainService.updateOrder(order) != 0) {
                genErrOutputMapWithCode("修改订单信息失败", ReturnCode.UPDATE_ORDER_ERROR);
                return outputMap;
            }

			//修改订单状态为待确认状态
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("接口异常", e);
            genErrOutputMap("接口异常");
        }
        return outputMap;
    }
}