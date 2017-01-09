package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class RemoveDeveloperHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("devId") == null) {
                genErrOutputMapWithCode("param error, devId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int devId = (Integer)param.get("devId");
            
            DeveloperPo developer = null;
            List<DeveloperPo> developerList = mainService.getDeveloper(null);
            if (developerList == null || developerList.size() == 0) {
                genErrOutputMapWithCode("param error, invalid dev id", ReturnCode.GET_DEVELOPER_ERROR);
                return outputMap;
            }
            for (int i = 0; i < developerList.size(); ++i) {
                if (developerList.get(i).getDevId() == devId) {
                    developer = developerList.get(i);
                    break;
                }
            }
            if (developer == null) {
                genErrOutputMapWithCode("param error, invalid dev id", ReturnCode.GET_DEVELOPER_ERROR);
                return outputMap;
            }
            
            if(mainService.getBuildingByDevId(developer.getDevId())>0){
            	dataMap.put("result", "N");
            }else{
            	dataMap.put("result", "Y");
            	developer.setRemoved(1);
                if (mainService.updateDeveloper(developer) != 0) {
                    genErrOutputMapWithCode("fail to remove developer", ReturnCode.REMOVE_DEVELOPER_ERROR);
                    return outputMap;
                }
            }
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
