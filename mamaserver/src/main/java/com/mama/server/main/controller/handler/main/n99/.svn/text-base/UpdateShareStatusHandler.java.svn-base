package com.mama.server.main.controller.handler.main.n99;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.service.n99.HouseCardService;

/** 更新分享批次状态 */
@Component
public class UpdateShareStatusHandler extends BaseHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateShareStatusHandler.class);
	
	@Autowired
	private HouseCardService houseCardService;

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run UpdateShareStatusHandler.");
		
		try {
			String sharePatchNo = (String) param.get("sharePatchNo");//分享批次号
			String opType = (String)param.get("opType");//操作方式：0、取消；1、确定
			if(!"0".equals(opType) && !"1".equals(opType)) {
				genErrOutputMap("非法请求！");
				return outputMap;
			}
//			int count = houseCardService.updateShareStatus(sharePatchNo, opType);
//			if(count > 0) {
				genSuccOutputMap();
//			} else {
//				genErrOutputMap("分享失败");
//			}			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GiveHouseCardHandler interface error",e);
			genErrOutputMap("分享过程中出现异常");
		}
		return outputMap;
	}
}
