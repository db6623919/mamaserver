package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetCitiesHandler extends BaseHandler {

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("provIds") == null) {
                param.put("provIds", new ArrayList<Integer>());
            }
            if (param.get("cityIds") == null) {
                param.put("cityIds", new ArrayList<Integer>());
            }
            if (param.get("type") == null) {
                param.put("type", -1);
            }
            int reversed = 0;
            if (param.get("reversed") != null) {
                reversed = (Integer)param.get("reversed");
            }
            if (reversed != 1 && reversed != 0) {
                reversed = 0;
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

            ArrayList<Integer> provIds = (ArrayList<Integer>)param.get("provIds");
            ArrayList<Integer> cityIds = (ArrayList<Integer>)param.get("cityIds");
            int type = (Integer)param.get("type");
            
            List<CityPo> cityList = null;
            List<CityPo> citysList = null;  //总记录数
            
            CityPo city = new CityPo();
//            cityList = mainService.getCityByType(city);
            cityList = mainService.getCityByType(param);
            citysList = mainService.getCityByType(null);
            
            if (cityList != null) {
                
                ArrayList<HashMap<String, Object>> cityMapList = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < cityList.size(); ++i) {

                	CityPo cityPo = cityList.get(i);
                    HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                    tmpMap.put("provId", cityList.get(i).getProvId());
                    tmpMap.put("cityId", cityList.get(i).getCityId());
                    tmpMap.put("cityName", cityList.get(i).getName());
                    tmpMap.put("showDetail", cityList.get(i).getShowDetail());
                    tmpMap.put("type", cityList.get(i).getType());
                    tmpMap.put("sort", cityList.get(i).getSort());
                    tmpMap.put("pinyin", cityList.get(i).getPinyin()==null?"":cityList.get(i).getPinyin());
                    HousePo house = new HousePo();
                    house.setCityId(cityList.get(i).getCityId());
                    List<HousePo> houseList = mainService.getHouseByAllParam(house);
                    int roomNum = 0;
                    int houseNum = 0;
                    if (houseList != null) {
                        for (int k = 0; k < houseList.size(); ++k) {
                            roomNum += houseList.get(k).getTotalRoomNum();
                            houseNum += 1;
                        }
                    }
                    tmpMap.put("roomNum", roomNum);
                    tmpMap.put("houseNum", houseNum);
                    cityMapList.add(tmpMap);
                }
                
                
                if (pageNum == -1 || pageCount == -1) {
                    dataMap.put("cities", cityMapList);
                    dataMap.put("num", citysList.size());
                    genSuccOutputMap();
                    return outputMap;
                }
                
                ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
                int startNum = (pageNum - 1) * pageCount;
                int endNum = startNum + pageCount;
                if (endNum > cityMapList.size()) {
                    endNum = cityMapList.size();
                }
                for (int i = startNum; i < endNum; ++i) {
                    pageList.add(cityMapList.get(i));
                }
                dataMap.put("cities", pageList);
                dataMap.put("num", citysList.size());
                
                genSuccOutputMap();
            } else {
                genErrOutputMapWithCode("fail to get cities", ReturnCode.GET_CITY_ERROR);
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
