package com.mama.server.main.controller.handler.hotelcoupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ExchangePo;
import com.mama.server.main.dao.model.ExchangeRequestPo;
import com.meidusa.fastjson.JSONArray;

/**
 * 添加换券需求
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
public class AcceptHotelCouponExchangeHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			String memberId = (String) param.get("memberId"); // 会员ID
			Object exchangeIdObj = param.get("exchangeId");// 申请已存在的换券ID
			Object requestIdObj = param.get("requestId");// 请求换券ID
			log.info("AcceptHotelCouponExchangeHandler request.  memberId:{}, exchangeId:{}, requestId:{}", memberId, exchangeIdObj, requestIdObj);
			if (StringUtils.isBlank(memberId)) {
				genErrOutputMapWithCode("param error, memberId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (exchangeIdObj == null) {
				genErrOutputMapWithCode("param error, exchangeId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (requestIdObj == null) {
				genErrOutputMapWithCode("param error, requestId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			long exchangeId = Long.valueOf(exchangeIdObj.toString());
			long requestId = Long.valueOf(requestIdObj.toString());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", exchangeId);
			params.put("type", 1);
			params.put("memberId", memberId);
			List<ExchangePo> exchanges = mainService.getHotelCouponExchanges(params);
			if (exchanges == null || exchanges.isEmpty()) {
				genErrOutputMapWithCode("param error, invalid exchangeId", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			ExchangePo exchange = exchanges.get(0);
			if (exchange.getStatus() != 0) {
				genErrOutputMapWithCode("exchange status fail. can not accept.", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			ExchangeRequestPo exchangeRequest = mainService.getExchangeRequestById(requestId);
			if (exchangeRequest == null) {
				genErrOutputMapWithCode("param error, invalid requestId", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (exchangeRequest.getStatus() != 0) {
				genErrOutputMapWithCode("exchangeRequest status fail. can not accept.", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			/******************** core code ************************/
			List<Long> ids = new ArrayList<Long>();
			List<Long> fromHotelCouponIds = new ArrayList<Long>();
			JSONArray jsonArray = JSONArray.parseArray(exchange.getHotelCouponIds());
			for (int i = 0; i < jsonArray.size(); i++) {
				fromHotelCouponIds.add(jsonArray.getLongValue(i));
			}
			List<Long> toHotelCouponIds = new ArrayList<Long>();
			jsonArray = JSONArray.parseArray(exchangeRequest.getHotelCouponIds());
			for (int i = 0; i < jsonArray.size(); i++) {
				toHotelCouponIds.add(jsonArray.getLongValue(i));
			}

			ids.addAll(fromHotelCouponIds);
			List<ExchangeRequestPo> exchangeRequests = mainService.getExchangeRequests(exchangeId);
			for (ExchangeRequestPo exchangeRequestPo : exchangeRequests) {
				if (exchangeRequestPo.getStatus() == 0) {// 状态正常的，解除冻结的旅居券
					jsonArray = JSONArray.parseArray(exchangeRequestPo.getHotelCouponIds());
					for (int i = 0; i < jsonArray.size(); i++) {
						ids.add(jsonArray.getLongValue(i));
					}
				}
			}

			// 修改换券状态
			mainService.updateExchange(exchangeId, 1);
			mainService.updateExchangeRequest(requestId, 1);
			// 换券
			mainService.changeHotelCoupon(memberId, exchangeRequest.getMemberId(), fromHotelCouponIds, toHotelCouponIds);
			// 修改涉及的旅居券状态
			mainService.updateHotelCouponStatus(ids, 0);
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}
