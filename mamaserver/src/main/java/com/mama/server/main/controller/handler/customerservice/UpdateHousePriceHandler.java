package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class UpdateHousePriceHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("date") == null) {
                genErrOutputMapWithCode("param error, date required", ReturnCode.PARAM_ERROR);
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
            
            String date = (String)param.get("date");
            int houseId = (Integer)param.get("houseId");
            int totalAmt = (Integer)param.get("totalAmt");
            int freezeAmt = (Integer)param.get("freezeAmt");
            int memTotalAmt = (Integer)param.get("memTotalAmt");
            int memFreezeAmt = (Integer)param.get("memFreezeAmt");
            Object inSeasonParam = param.get("inSeason"); // 是否旺季
            boolean inSeason = inSeasonParam == null ? false : (Boolean) inSeasonParam;
            
            HousePricePo housePrice = new HousePricePo();
            housePrice.setDate(mainService.stringToDate(date));
            housePrice.setHouseId(houseId);
            
            List<HousePricePo> housePriceList = mainService.getHousePriceByAllParam(housePrice);
            if (housePriceList == null || housePriceList.size() == 0) {
                genErrOutputMapWithCode("fail to get house price", ReturnCode.GET_HOUSE_PRICE_ERROR);
                return outputMap;
            }
            housePrice = housePriceList.get(0);
            housePrice.setMemFreezeAmt(memFreezeAmt);
            housePrice.setMemTotalAmt(memTotalAmt);
            housePrice.setTotalAmt(totalAmt);
            housePrice.setFreezeAmt(freezeAmt);
            housePrice.setInSeason(inSeason);
            if (mainService.updateHousePriceById(housePrice) != 0) {
                genErrOutputMapWithCode("fail to update house price", ReturnCode.UPDATE_HOUSE_PRICE_ERROR);
                return outputMap;
            }
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
