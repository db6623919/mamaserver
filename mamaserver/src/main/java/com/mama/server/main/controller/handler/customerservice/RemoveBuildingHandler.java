package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class RemoveBuildingHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("bldId") == null) {
                genErrOutputMapWithCode("param error, bldId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int bldId = (Integer)param.get("bldId");
            
            BuildingPo building = null;
            List<BuildingPo> buildingList = mainService.getBuilding(building);
            if (buildingList == null || buildingList.size() == 0) {
                genErrOutputMapWithCode("param error, invalid building id", ReturnCode.GET_BUILDING_ERROR);
                return outputMap;
            }
            for (int i = 0; i < buildingList.size(); ++i) {
                if (buildingList.get(i).getBldId() == bldId) {
                    building = buildingList.get(i);
                    break;
                }
            }
            if (building == null) {
                genErrOutputMapWithCode("param error, invalid building id", ReturnCode.GET_BUILDING_ERROR);
                return outputMap;
            }
            building.setRemoved(1);
            
            if (mainService.updateBuliding(building) != 0) {
                genErrOutputMapWithCode("fail to remove building", ReturnCode.REMOVE_BUILDING_ERROR);
                return outputMap;
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
