package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetHousePriceHandler extends BaseHandler {

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			List<String> dates = null;
			if (param.get("dates") != null) {
				dates = (ArrayList<String>) param.get("dates");
			}
			if (param.get("houseId") == null) {
				genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			int houseId = (Integer) param.get("houseId");

			HousePo house = new HousePo();
			house.setHouseId(houseId);
			List<HousePo> houseList = mainService.getHouseByAllParam(house);
			if (houseList == null || houseList.size() == 0) {
				genErrOutputMapWithCode("param error, invalid house id", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			house = houseList.get(0);

			ArrayList<HashMap<String, Object>> housePriceMapList = new ArrayList<HashMap<String, Object>>();

			if (dates != null && dates.size() > 0) {
				for (int i = 0; i < dates.size(); ++i) {
					HashMap<String, Object> infoMap = new HashMap<String, Object>();
					HousePricePo housePrice = new HousePricePo();
					housePrice.setDate(mainService.stringToDate(dates.get(i)));
					housePrice.setHouseId(houseId);
					List<HousePricePo> housePriceList = mainService.getHousePriceByAllParam(housePrice);
					if (housePriceList == null) {
						genErrOutputMapWithCode("fail to get house price", ReturnCode.GET_HOUSE_PRICE_ERROR);
						return outputMap;
					}
					infoMap.put("date", dates.get(i));
					if (housePriceList.size() == 0) {
						infoMap.put("memTotalAmt", house.getMemTotalAmt());
						infoMap.put("memFreezeAmt", house.getMemFreezeAmt());
						infoMap.put("totalAmt", house.getTotalAmt());
						infoMap.put("freezeAmt", house.getFreezeAmt());
					} else {
						infoMap.put("priceId", housePriceList.get(0).getPriceId());
						infoMap.put("memTotalAmt", housePriceList.get(0).getMemTotalAmt());
						infoMap.put("memFreezeAmt", housePriceList.get(0).getMemFreezeAmt());
						infoMap.put("totalAmt", housePriceList.get(0).getTotalAmt());
						infoMap.put("freezeAmt", housePriceList.get(0).getFreezeAmt());
						infoMap.put("inSeason", housePriceList.get(0).isInSeason());
					}

					housePriceMapList.add(infoMap);
				}
			} else {
				HousePricePo housePrice = new HousePricePo();
				housePrice.setHouseId(houseId);
				List<HousePricePo> housePriceList = mainService.getHousePriceByAllParam(housePrice);
				if (housePriceList == null) {
					genErrOutputMapWithCode("fail to get house price", ReturnCode.GET_HOUSE_PRICE_ERROR);
					return outputMap;
				}
				for (int i = 0; i < housePriceList.size(); ++i) {
					HashMap<String, Object> infoMap = new HashMap<String, Object>();
					infoMap.put("priceId", housePriceList.get(i).getPriceId());
					infoMap.put("memTotalAmt", housePriceList.get(i).getMemTotalAmt());
					infoMap.put("memFreezeAmt", housePriceList.get(i).getMemFreezeAmt());
					infoMap.put("totalAmt", housePriceList.get(i).getTotalAmt());
					infoMap.put("freezeAmt", housePriceList.get(i).getFreezeAmt());
					infoMap.put("date", housePriceList.get(i).getDate());
					infoMap.put("inSeason", housePriceList.get(i).isInSeason());
					housePriceMapList.add(infoMap);
				}
			}
			dataMap.put("num", housePriceMapList.size());
			dataMap.put("prices", housePriceMapList);
			genSuccOutputMap();
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}
