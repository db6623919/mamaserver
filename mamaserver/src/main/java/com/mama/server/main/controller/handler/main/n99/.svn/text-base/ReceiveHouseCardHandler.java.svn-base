package com.mama.server.main.controller.handler.main.n99;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.service.n99.HouseCardService;

/**
 * 领取房券
 * */
@Component
public class ReceiveHouseCardHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(ReceiveHouseCardHandler.class);
	
	@Autowired
	private HouseCardService houseCardService;

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run ReceiveHouseCardHandler.");
		
		try {
			String sharePatchNo = (String) param.get("sharePatchNo");//分享批次号
			String phoneNo = (String)param.get("phoneNo");//手机号码
			Map<String, String> resultMap = houseCardService.receiveHouseCard(sharePatchNo, phoneNo);
			String receiveCode = resultMap.get("code");
			String receiveMsg = resultMap.get("msg");
			logger.info("receiveHouseCard method: sharePatchNo=" + sharePatchNo + ",phoneNo=" + phoneNo);
			dataMap.put("receiveCode", receiveCode);
			dataMap.put("receiveMsg", receiveMsg);
			String cardNo = (String)resultMap.get("cardNo");
			dataMap.put("cardNo", cardNo);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("ReceiveHouseCardHandler interface error",e);
			genErrOutputMap("房券领取过程中出现异常");
		}
		return outputMap;
	}
}
