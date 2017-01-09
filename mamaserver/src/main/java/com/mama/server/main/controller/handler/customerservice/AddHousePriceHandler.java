package com.mama.server.main.controller.handler.customerservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HousePricePo;

@Component
public class AddHousePriceHandler extends BaseHandler {

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			if (param.get("dates") == null) {
				genErrOutputMapWithCode("param error, dates required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (param.get("memTotalAmt") == null) {
				genErrOutputMapWithCode("param error, memTotalAmt required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (param.get("memFreezeAmt") == null) {
				genErrOutputMapWithCode("param error, memFreezeAmt required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (param.get("totalAmt") == null) {
				genErrOutputMapWithCode("param error, totalAmt required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (param.get("freezeAmt") == null) {
				genErrOutputMapWithCode("param error, freezeAmt required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (param.get("houseId") == null) {
				genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			List<String> dates = (ArrayList<String>) param.get("dates");
			int houseId = (Integer) param.get("houseId");
			int totalAmt = (Integer) param.get("totalAmt");
			int freezeAmt = (Integer) param.get("freezeAmt");
			int memTotalAmt = (Integer) param.get("memTotalAmt");
			int memFreezeAmt = (Integer) param.get("memFreezeAmt");
			Object inSeasonParam = param.get("inSeason"); // 是否旺季
			boolean inSeason = inSeasonParam == null ? false : (Boolean) inSeasonParam;

			for (int i = 0; i < dates.size(); ++i) {
				HousePricePo housePrice = new HousePricePo();
				housePrice.setDate(mainService.stringToDate(dates.get(i)));
				housePrice.setHouseId(houseId);
				housePrice.setMemFreezeAmt(memFreezeAmt);
				housePrice.setMemTotalAmt(memTotalAmt);
				housePrice.setTotalAmt(totalAmt);
				housePrice.setFreezeAmt(freezeAmt);
				housePrice.setInSeason(inSeason);
				
				//查询houseprice中是否有记录
				List<HousePricePo> housePriceList = mainService.getHousePriceByAllParam(housePrice);
	            if (housePriceList == null || housePriceList.size() == 0) {
	            	if (mainService.insertHousePrice(housePrice) != 0) {//新增
						genErrOutputMapWithCode("fail to add house price", ReturnCode.ADD_HOUSE_PRICE_ERROR);
						return outputMap;
					}
	            }else {
	            	housePrice.setPriceId(housePriceList.get(0).getPriceId());
	            	if (mainService.updateHousePriceById(housePrice) != 0) {//更新记录
	                    genErrOutputMapWithCode("fail to update house price", ReturnCode.UPDATE_HOUSE_PRICE_ERROR);
	                    return outputMap;
	                }
				}
	            
				
				
			}

			genSuccOutputMap();
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}
