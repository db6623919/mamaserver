package com.mama.server.main.controller.handler.main.n99;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.util.Json;

/**
 * N99订单提交
 * @author dengbiao
 *
 */
@Component
public class N99OrderHandler extends BaseHandler {

	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(N99OrderHandler.class);
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run N99OrderHandler.");
		
		try {
            Integer houseId = (Integer)param.get("houseId");
			String uid = (String) param.get("uid");
			//int totalAmt = (Integer)param.get("payPrice");
			String userName = (String)param.get("userName");
			String userPhone = (String)param.get("userPhone");
			Integer totalRoomNum = (Integer)param.get("totalRoomNum");					
	
			HousePo hosue = new HousePo();
			hosue.setHouseId(houseId);
			List<HousePo> houseList = mainService.getHouseByAllParam(hosue);
			if (houseList.size() < 1) {
				genErrOutputMapWithCode("param error, houseId is not found", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			
			if (houseId == null || totalRoomNum == null ) {
				genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (StringUtils.isBlank(uid)) {
				genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			
			// create order
			String orderId = "";
			if (param.get("orderId") == null) {
				orderId = mainService.getNewOrderId();
			}
					
			OrderPo order = new OrderPo();
			order.setOrderId(orderId);
			order.setUid(uid);
			order.setHouseId(houseId);
			order.setOperTime(mainService.getCurrentDatetime());
			order.setStatus(ConstValue.ORDER_WAITING_FOR_PAYMENT);
			order.setVerifyCode("");
			order.setRemoved(0);
			order.setOrderType(3);
			order.setShopId(houseList.get(0).getHouseshop_id());
			order.setTotalRoomNum(totalRoomNum);
			Map<String,Object> detailMap = new HashMap<String, Object>();
			detailMap.put("userName", userName);
			detailMap.put("userPhone", userPhone);
			order.setLiveDetail(Json.format(detailMap));			
			order.setFreezeAmt(0); //优惠金额
			int totalAmti = houseList.get(0).getMemTotalAmt() * totalRoomNum;
			order.setPayAmt(totalAmti);
			order.setTotalAmt(totalAmti);
			
			if (mainService.insertOrder(order) != 0) {
					genErrOutputMapWithCode("fail to N99OrderHandler create order", ReturnCode.CREATE_ORDER_ERROR);
					return outputMap;
			}		
			
			dataMap.put("orderId", order.getOrderId());
			dataMap.put("payAmt", order.getPayAmt());
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("N99OrderHandler interface error",e);
		}
		return outputMap;
	}

}
