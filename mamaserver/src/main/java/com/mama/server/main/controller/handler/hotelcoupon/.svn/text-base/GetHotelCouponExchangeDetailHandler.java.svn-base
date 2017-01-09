package com.mama.server.main.controller.handler.hotelcoupon;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;

/**
 * 获取换券需求
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年3月24日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 *
 */
@Component
public class GetHotelCouponExchangeDetailHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			Object idObj = param.get("id");
			log.info("GetHotelCouponExchangeDetailHandler request. id:{}", idObj);
			long id = Long.valueOf(idObj.toString());
			if (id <= 0) {
				genErrOutputMapWithCode("param error, invalid id", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			dataMap.put("exchangeDetail", mainService.getExchangeDetailById(id));
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}
