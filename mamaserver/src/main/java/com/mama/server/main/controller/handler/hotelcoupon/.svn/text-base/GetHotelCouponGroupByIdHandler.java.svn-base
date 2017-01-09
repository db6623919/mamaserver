package com.mama.server.main.controller.handler.hotelcoupon;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.vo.HotelCouponGroupVo;

/**
 * 获取旅居券handler
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年3月2日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 *
 */
@Component
public class GetHotelCouponGroupByIdHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			Long id = Long.valueOf(param.get("id").toString()); // 旅居券ID
			log.info("GetHotelCouponGroupByIdHandler request. id:{}", id);
			HotelCouponGroupVo hotelCouponGroup = mainService.getHotelCouponGroup(id);
			dataMap.put("hotelCouponGroup", hotelCouponGroup);
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
