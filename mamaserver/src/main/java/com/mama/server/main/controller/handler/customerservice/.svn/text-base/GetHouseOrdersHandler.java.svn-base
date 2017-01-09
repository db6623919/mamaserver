package com.mama.server.main.controller.handler.customerservice;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetHouseOrdersHandler extends BaseHandler {

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            List<Integer> houseIds = null;
            if (param.get("houseIds") != null) {
                houseIds = (List<Integer>)param.get("houseIds");
            }
            
            List<String> dates = null;
            if (param.get("dates") != null) {
                dates = (List<String>)param.get("dates");
            }

            int pageNum = -1;
            int pageCount = -1;
            if (param.get("pageNum") != null && param.get("pageCount") != null) {
                pageNum = (Integer)param.get("pageNum");
                pageCount = (Integer)param.get("pageCount");
                
                if (pageNum <= 0 || pageCount <= 0) {
                    genErrOutputMapWithCode("param error, invalid pageNum/pageCount", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
            }
            
            HouseOrderPo houseOrder = new HouseOrderPo();
            List<HouseOrderPo> houseOrderList = mainService.getHouseOrderByAllParam(houseOrder);
            if (houseOrderList == null) {
                genErrOutputMapWithCode("fail to get house orders", ReturnCode.GET_HOUSE_ERROR);
                return outputMap;
            }
            
            List<HouseOrderPo> tmpHouseOrderList = new ArrayList<HouseOrderPo>();
            if (houseIds != null && houseIds.size() != 0) {
                for (int i = 0; i < houseOrderList.size(); ++i) {
                    if (houseIds.contains(houseOrderList.get(i).getHouseId())) {
                        tmpHouseOrderList.add(houseOrderList.get(i));
                    }
                }
                houseOrderList = tmpHouseOrderList;
            }
            
            tmpHouseOrderList = new ArrayList<HouseOrderPo>();
            if (dates != null && dates.size() != 0) {
                for (int i = 0; i < houseOrderList.size(); ++i) {
                    boolean contain = false;
                    for (int j = 0; j < dates.size(); ++j) {
                        if (dates.get(j).compareTo(houseOrderList.get(i).getDate()) == 0) {
                            contain = true;
                            break;
                        }
                    }
                    if (contain) {
                        tmpHouseOrderList.add(houseOrderList.get(i));
                    }
                }
                houseOrderList = tmpHouseOrderList;
            }
            
            ArrayList<HashMap<String, Object>> houseOrderMapList = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < houseOrderList.size(); ++i) {
                if (houseOrderList.get(i).getRemoved() == 1) {
                    continue;
                }
                HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put("houseId", houseOrderList.get(i).getHouseId());
                tmpMap.put("date", houseOrderList.get(i).getDate());
                tmpMap.put("totalRoomNum", houseOrderList.get(i).getTotalRoomNum());
                tmpMap.put("uid", houseOrderList.get(i).getUid());
                tmpMap.put("orderId", houseOrderList.get(i).getOrderId());
                
                houseOrderMapList.add(tmpMap);
            }
            
            if (pageNum == -1 || pageCount == -1) {
                dataMap.put("houseOrders", houseOrderMapList);
                dataMap.put("num", houseOrderMapList.size());
                genSuccOutputMap();
                return outputMap;
            }
            
            ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
            int startNum = (pageNum - 1) * pageCount;
            int endNum = startNum + pageCount;
            if (endNum > houseOrderMapList.size()) {
                endNum = houseOrderMapList.size();
            }
            for (int i = startNum; i < endNum; ++i) {
                pageList.add(houseOrderMapList.get(i));
            }
            dataMap.put("houseOrders", pageList);
            dataMap.put("num", pageList.size());
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
