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
public class CancelHotelCouponExchangeHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			String memberId = (String) param.get("memberId"); // 会员ID
			Integer type = (Integer) param.get("type");// 1：取消主动发起换券申请； 2：取消申请已存在的换券请求
			Object idObj = param.get("id");// ID
			log.info("CancelHotelCouponExchangeHandler request. memberId:{}, type:{}, id:{}", memberId, type, idObj);
			if (StringUtils.isBlank(memberId)) {
				genErrOutputMapWithCode("param error, memberId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (type == null) {
				genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (type != 1 && type != 2) {
				genErrOutputMapWithCode("param error, invalid type", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (idObj == null ) {
				genErrOutputMapWithCode("param error, id required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			long id = Long.valueOf(idObj.toString());

			/******************** core code ************************/
			if (type == 1) {// 发起方取消换券请求
				// 检查换券请求状态
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", id);
				List<ExchangePo> exchanges = mainService.getHotelCouponExchanges(params);
				if (exchanges == null || exchanges.isEmpty()) {
					genErrOutputMapWithCode("param error, invalid id", ReturnCode.PARAM_ERROR);
					return outputMap;
				}
				ExchangePo exchangePo = exchanges.get(0);
				if (exchangePo.getStatus() != 0) {
					genErrOutputMapWithCode("exchange status fail. can not cancel.", ReturnCode.PARAM_ERROR);
					return outputMap;
				}
				List<Long> ids = new ArrayList<Long>();
				JSONArray jsonArray = JSONArray.parseArray(exchangePo.getHotelCouponIds());
				for (int i = 0; i < jsonArray.size(); i++) {
					ids.add(jsonArray.getLongValue(i));
				}
				List<ExchangeRequestPo> exchangeRequests = mainService.getExchangeRequests(id);
				for (ExchangeRequestPo exchangeRequestPo : exchangeRequests) {
					if (exchangeRequestPo.getStatus() == 0) {// 状态正常的，解除冻结的旅居券
						jsonArray = JSONArray.parseArray(exchangeRequestPo.getHotelCouponIds());
						for (int i = 0; i < jsonArray.size(); i++) {
							ids.add(jsonArray.getLongValue(i));
						}
					}
				}
				// 取消换券请求
				mainService.updateExchange(id, 2);
				// 修改涉及的旅居券状态
				mainService.updateHotelCouponStatus(ids, 0);
			} else {// 申请换券方取消换券请求
				ExchangeRequestPo exchangeRequest = mainService.getExchangeRequestById(id);
				if (exchangeRequest == null || !exchangeRequest.getMemberId().equals(memberId)) {
					genErrOutputMapWithCode("param error, invalid id", ReturnCode.PARAM_ERROR);
					return outputMap;
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", exchangeRequest.getExchangeId());
				List<ExchangePo> exchanges = mainService.getHotelCouponExchanges(params);
				if (exchanges.get(0).getStatus() != 0) {
					genErrOutputMapWithCode("exchange status fail. can not cancel.", ReturnCode.PARAM_ERROR);
					return outputMap;
				}
				List<Long> ids = new ArrayList<Long>();
				JSONArray jsonArray = JSONArray.parseArray(exchangeRequest.getHotelCouponIds());
				for (int i = 0; i < jsonArray.size(); i++) {
					ids.add(jsonArray.getLongValue(i));
				}
				// 取消换券请求
				mainService.updateExchangeRequest(id, 2);
				// 修改涉及的旅居券状态
				mainService.updateHotelCouponStatus(ids, 0);
			}
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}
