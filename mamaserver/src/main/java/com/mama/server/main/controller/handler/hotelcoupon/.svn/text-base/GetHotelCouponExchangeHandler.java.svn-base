package com.mama.server.main.controller.handler.hotelcoupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
public class GetHotelCouponExchangeHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			String memberId = (String) param.get("memberId"); // 会员ID
			Integer type = (Integer) param.get("type"); // 类型： 0:大厅；1:我发布的;
														// 2：我申请的
			@SuppressWarnings("unchecked")
			List<Integer> statusIds = (List<Integer>) param.get("statusIds"); // 状态
			log.info("GetHotelCouponExchangeDetailHandler request. memberId:{}, type:{}, statusIds:{}", memberId, type, statusIds);

			if (type == null || !(type == 0 || type == 1 || type == 2)) {
				genErrOutputMapWithCode("param error, invalid type", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (type != 0 && StringUtils.isBlank(memberId)) {// 只有查询大厅的，会员ID才可以为空
				genErrOutputMapWithCode("param error, when type is 0 the memberId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			params.put("type", type);
			params.put("statusIds", statusIds);
			dataMap.put("exchanges", mainService.getHotelCouponExchanges(params));
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}
