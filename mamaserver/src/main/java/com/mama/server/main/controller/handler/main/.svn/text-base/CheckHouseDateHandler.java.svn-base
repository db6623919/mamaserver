package com.mama.server.main.controller.handler.main;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Log;

@Component
public class CheckHouseDateHandler extends BaseHandler {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
     
    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("houseId") == null) {
                genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("dates") == null) {
                genErrOutputMapWithCode("param error, dates required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("roomNum") == null) {
                genErrOutputMapWithCode("param error, roomNum required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int houseId = (Integer)param.get("houseId");
            List<String> dates = (ArrayList<String>)param.get("dates");
            int roomNum = (Integer)param.get("roomNum");
            
            if (roomNum <= 0) {
                genErrOutputMapWithCode("param error, roomNum error", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (dates.size() == 0) {
                genErrOutputMapWithCode("param error, dates error", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            HousePo house = new HousePo();
            house.setHouseId(houseId);
            List<HousePo> houseList = mainService.getHouseByAllParam(house);
            if (houseList == null || houseList.size() == 0) {
                genErrOutputMapWithCode("invalid house id", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            house = houseList.get(0);

            for (int i = 0; i < dates.size(); ++i) {
                Date tmpDate = mainService.stringToDate(dates.get(i));
                if (tmpDate == null) {
                    genErrOutputMapWithCode("invalid date format", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
                HashMap<String, Object> infoMap = new HashMap<String, Object>();
                HouseOrderPo houseOrder = new HouseOrderPo();
                houseOrder.setHouseId(houseId);
                String daString =dates.get(i);
                houseOrder.setDate(dates.get(i));
                houseOrder.setRemoved(0);
                List<HouseOrderPo> houseOrderList = mainService.getHouseOrderByAllParam(houseOrder);
                if (houseOrderList == null) {
                    genErrOutputMapWithCode("fail to get house order list", ReturnCode.GET_HOUSE_ORDER_ERROR);
                    return outputMap;
                }
//                HousePricePo housePricePo = new HousePricePo();
//                housePricePo.setHouseId(houseId);
//                housePricePo.setDate(sdf.parse(dates.get(i)));
//                List<HousePricePo> housePriceList = mainService.getHousePriceByAllParam(housePricePo);
//                if (null!=housePriceList) {
//                	infoMap.put("priceId", housePriceList.get(0).getPriceId());
//				}
                
                infoMap.put("memFreezeAmt", house.getMemFreezeAmt());
                infoMap.put("memTotalAmt", house.getMemTotalAmt());
                infoMap.put("freezeAmt", house.getFreezeAmt());
                infoMap.put("totalAmt", house.getTotalAmt());
                int total = 0;
                for (int j = 0; j < houseOrderList.size(); ++j) {
                    total += houseOrderList.get(j).getTotalRoomNum();
                }
                Log.SERVICE.error(String.valueOf(total));
                Log.SERVICE.error(String.valueOf(houseOrderList.size()));
                Log.SERVICE.error(String.valueOf(dates.get(i)));
                
                infoMap.put("emptyRoom", house.getTotalRoomNum() - total);
                infoMap.put("requiredRoom", roomNum);
                if (total + roomNum > house.getTotalRoomNum()) {
                    infoMap.put("available", 0);
                } else {
                    infoMap.put("available", 1);
                }
                dataMap.put(dates.get(i), infoMap);
            }
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
