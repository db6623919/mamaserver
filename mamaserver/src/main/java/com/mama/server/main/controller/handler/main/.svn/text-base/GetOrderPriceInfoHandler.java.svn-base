package com.mama.server.main.controller.handler.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.vo.OrderPriceInfoVo;

@Component
public class GetOrderPriceInfoHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			String mmWalletId = (String) param.get("mmWalletId"); // 妈妈钱包帐号ID
			Integer houseId = (Integer) param.get("houseId"); // 酒店房源ID
			String startDateStr = (String) param.get("startDate"); // 入住开始时间字符串格式
			String endDateStr = (String) param.get("endDate"); // 入住结束时间字符串格式
			@SuppressWarnings("unchecked")
			List<Long> hotelCouponIds = (List<Long>) param.get("hotelCouponIds");// 旅居券ID列表
			log.info("GetHousePriceInfoHandler mmWalletId:{}, houseId:{}, startDate:{}, endDate:{}, hotelCouponIds{}", mmWalletId, houseId, startDateStr, endDateStr,
					hotelCouponIds);
			Date startDate = null;// 入住开始时间
			Date endDate = null; // 入住结束时间
			if (StringUtils.isBlank(mmWalletId)) {
				genErrOutputMapWithCode("param error, mmwallet id required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (houseId == null || houseId <= 0) {
				genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (StringUtils.isBlank(startDateStr)) {
				genErrOutputMapWithCode("param error, start date required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (StringUtils.isBlank(endDateStr)) {
				genErrOutputMapWithCode("param error, end date required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				startDate = sdf.parse(startDateStr);
			} catch (Exception e) {
				genErrOutputMapWithCode("param error, invalid start date", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			try {
				endDate = sdf.parse(endDateStr);
			} catch (Exception e) {
				genErrOutputMapWithCode("param error, invalid end date", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (!endDate.after(startDate)) { // 结束日期必须在开始日期之后
				genErrOutputMapWithCode("param error, invalid end date", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			OrderPriceInfoVo orderPriceInfoVo = mainService.getOrderPriceInfo(mmWalletId, houseId, startDate, endDate, hotelCouponIds);
			dataMap.put("totalAmt", orderPriceInfoVo.getTotalAmt());
			dataMap.put("days", orderPriceInfoVo.getDays());
			dataMap.put("inSeasonDays", orderPriceInfoVo.getInSeasonDays());
			dataMap.put("offSeasonDays", orderPriceInfoVo.getOffSeasonDays());
			dataMap.put("priceMap", orderPriceInfoVo.getPriceMap());
			dataMap.put("hotelCouponDiscountAmt", orderPriceInfoVo.getHotelCouponDiscountAmt());
			dataMap.put("orderFinalAmt", orderPriceInfoVo.getOrderFinalAmt());
			dataMap.put("realInSeasonDays", orderPriceInfoVo.getRealInSeasonDays());
			genSuccOutputMap();
		} catch (Exception e) {
			log.error("interface error", e);
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
