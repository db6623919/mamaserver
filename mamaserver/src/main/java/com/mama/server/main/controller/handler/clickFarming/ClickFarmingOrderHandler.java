package com.mama.server.main.controller.handler.clickFarming;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.vo.clickfarming.CFHouseShopVo;
import com.mama.server.util.Json;

/**
 * 订单提交(刷单)
 * @author whl
 *
 */
@Component
public class ClickFarmingOrderHandler extends BaseHandler {

	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(ClickFarmingOrderHandler.class);
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run ClickFarmingOrderHandler.");
		
		try {
            Integer houseId = (Integer)param.get("houseId");
			String uid = (String) param.get("uid");
			int shopId = (Integer) param.get("shopId");
			int totalAmt = (Integer)param.get("payPrice");
			String pay_type = (String)param.get("pay_type");//支付方式 1 微信支付 2线下担保支付
			String act = (String)param.get("act");
			String userName = (String)param.get("userName");
			String userPhone = (String)param.get("userPhone");
					
	
			if (houseId == null) {
				genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (StringUtils.isBlank(uid)) {
				genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			
			if (param.get("pay_type") == null) {
                genErrOutputMapWithCode("param error, pay_type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
			
			// create order
			String orderId = "";
			if (param.get("orderId") == null) {
				orderId = mainService.getNewOrderId();
			} else {
				orderId = (String)param.get("orderId");
			}		
					
			OrderPo order = new OrderPo();
			order.setOrderId(orderId);
			order.setUid(uid);
			order.setHouseId(houseId);
			order.setOperTime(mainService.getCurrentDatetime());
			order.setStatus(ConstValue.ORDER_WAITING_FOR_PAYMENT);
			order.setTotalAmt(totalAmt);
			order.setPay_type(pay_type);
			order.setVerifyCode("");
			order.setRemoved(0);
			order.setOrderType(2);
			order.setShopId(shopId);
		
			Map<String,Object> detailMap = new HashMap<String, Object>();
			detailMap.put("userName", userName);
			detailMap.put("userPhone", userPhone);
			order.setLiveDetail(Json.format(detailMap));
			//客栈优惠信息
			CFHouseShopVo cfHouseShopVo = icfHouseShopService.getCFHouseShop(shopId);
			int cfFreezeAmt = 0; //优惠金额
			if (cfHouseShopVo != null) {
				
				//客栈已优惠总金额金额
				int totalFreezeAmt = icfHouseShopService.getTotalFreezeAmt(shopId,"");
				//客栈每天已优惠总金额
				int totalFreezeDayAmt = icfHouseShopService.getTotalFreezeAmt(shopId,"day");
				
				//优惠金额
				if (cfHouseShopVo.getDiscountType() == 1) {
					cfFreezeAmt = (int)(order.getTotalAmt() * (1 - cfHouseShopVo.getDiscount()));
				} else if (totalAmt >= cfHouseShopVo.getLowestAmt()) { //支付金额>支付有优惠最低金额
					cfFreezeAmt = cfHouseShopVo.getDiscount().intValue();
				}
				
				if (cfFreezeAmt > 0) {
					//每天已优惠金额大于每天上限金额
					if (totalFreezeDayAmt >= cfHouseShopVo.getDiscountLimit() ) { 
						cfFreezeAmt = 0;
					}else if (totalFreezeDayAmt < cfHouseShopVo.getDiscountLimit() && (cfFreezeAmt + totalFreezeDayAmt) > cfHouseShopVo.getDiscountLimit()) { //
						//每天已优惠金额小于每天上限金额 and 每天已优惠金额+优惠金额>每天上限金额
						cfFreezeAmt = cfHouseShopVo.getDiscountLimit() - totalFreezeDayAmt;
					}
					
					//剩余优惠金额
					int overAmt = cfHouseShopVo.getTotalAmt() - totalFreezeAmt;
					if (overAmt <= cfFreezeAmt && overAmt > 0) { //剩余优惠金额小于优惠金额
						cfFreezeAmt = overAmt;
					} else if (overAmt <= 0){
						cfFreezeAmt = 0;
					}
				}
				
			}
			//下过单不享受优惠
			if (mainService.getOrderClickFarming(order).size() > 0) {
				dataMap.put("isClickFarming", "1");
				cfFreezeAmt = 0;
			} else {
				dataMap.put("isClickFarming", "0");
				mainService.insertClickFarming(order);
			}
			
			order.setFreezeAmt(cfFreezeAmt); //优惠金额
			order.setPayAmt(order.getTotalAmt() - cfFreezeAmt);
			if (act.equals("add")) {
				if (mainService.insertOrder(order) != 0) {
					genErrOutputMapWithCode("fail to create order", ReturnCode.CREATE_ORDER_ERROR);
					return outputMap;
				}
			} else if (act.equals("edit")) {
				mainService.updateOrder(order);
			}
			
			
			dataMap.put("orderId", order.getOrderId());
			dataMap.put("payAmt", order.getPayAmt());
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("ClickFarmingOrderHandler interface error",e);
		}
		return outputMap;
	}

}
