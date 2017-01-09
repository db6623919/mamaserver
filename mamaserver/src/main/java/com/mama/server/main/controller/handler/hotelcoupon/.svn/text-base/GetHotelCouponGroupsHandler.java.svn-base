package com.mama.server.main.controller.handler.hotelcoupon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
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
public class GetHotelCouponGroupsHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			String mmWalletId = (String) param.get("mmWalletId"); // 妈妈钱包ID
			Integer devId = (Integer) param.get("devId"); // 开发商ID
			Integer houseId = (Integer) param.get("houseId"); // 酒店房源ID
			String expireTimeFromStr = (String) param.get("expireTimeFrom"); // 过期时间从字符串
			String expireTimeFromTo = (String) param.get("expireTimeTo"); // 过期时间至字符串
			@SuppressWarnings("unchecked")
			List<Integer> statusIds = (List<Integer>) param.get("statusIds"); // 旅居券状态列表
			log.info("GetHotelCouponHandler request. mmWalletId:{}, devId:{}, houseId:{}, expireTimeFrom:{}, expireTimeTo:{}, statusIds:{}", mmWalletId, devId, houseId,
					expireTimeFromStr, expireTimeFromTo, statusIds);

			Date expireTimeFrom = null;// 过期时间从
			Date expireTimeTo = null;// 过期时间至
			if (StringUtils.isBlank(mmWalletId)) {
				genErrOutputMapWithCode("param error, mmwallet id required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isNoneBlank(expireTimeFromStr)) {
				expireTimeFrom = sdf.parse(expireTimeFromStr);
			}
			if (StringUtils.isNoneBlank(expireTimeFromTo)) {
				expireTimeTo = DateUtils.addDays(sdf.parse(expireTimeFromTo), 1);
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mmWalletId", mmWalletId);
			params.put("devId", devId);
			params.put("houseId", houseId);
			params.put("expireTimeFrom", expireTimeFrom);
			params.put("expireTimeTo", expireTimeTo);
			params.put("statusIds", statusIds);
			List<HotelCouponGroupVo> hotelCouponGroups = mainService.getHotelCouponGroups(params);
			dataMap.put("hotelCouponGroups", hotelCouponGroups);
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
