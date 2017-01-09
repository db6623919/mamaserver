package com.mama.server.main.controller.handler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetBuildingsHandler extends BaseHandler {

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            List<BuildingPo> buildingList = mainService.getBuildings(param);
            HashMap<String, Object> building_type_map = new HashMap<String, Object>();
            building_type_map.put("building_type", param.get("building_type"));
            List<BuildingPo> buildingLists = mainService.getBuildings(building_type_map);
            
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
            
            if (buildingList == null) {
                genErrOutputMapWithCode("fail to get buildings", ReturnCode.GET_BUILDING_ERROR);
                return outputMap;
            }
            
            ArrayList<HashMap<String, Object>> buildingMapList = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < buildingList.size(); ++i) {
                HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put("devId", buildingList.get(i).getDevId());
                tmpMap.put("bldId", buildingList.get(i).getBldId());
                tmpMap.put("cityId", buildingList.get(i).getCityId());
                tmpMap.put("provId", buildingList.get(i).getProvId());
                tmpMap.put("name", buildingList.get(i).getName());
                tmpMap.put("type", buildingList.get(i).getType());
                tmpMap.put("mark", buildingList.get(i).getMark());
                tmpMap.put("buildingType", buildingList.get(i).getBuildingType());
                tmpMap.put("showDetail", buildingList.get(i).getShowDetail());
                buildingMapList.add(tmpMap);
            }
            
            if (pageNum == -1 || pageCount == -1) {
                dataMap.put("buildings", buildingMapList);
                dataMap.put("num", buildingLists.size());
                genSuccOutputMap();
                return outputMap;
            }
            
            ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
            int startNum = (pageNum - 1) * pageCount;
            int endNum = startNum + pageCount;
            if (endNum > buildingMapList.size()) {
                endNum = buildingMapList.size();
            }
            for (int i = startNum; i < endNum; ++i) {
                pageList.add(buildingMapList.get(i));
            }
            dataMap.put("buildings", pageList);
            dataMap.put("num", buildingLists.size());
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}