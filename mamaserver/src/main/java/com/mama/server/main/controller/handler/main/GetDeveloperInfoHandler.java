package com.mama.server.main.controller.handler.main;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetDeveloperInfoHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("devId") == null) {
                genErrOutputMapWithCode("param error, devId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int devId = (Integer)param.get("devId");
            
            List<DeveloperPo> developerList = mainService.getDeveloper(null);
            
            if (developerList != null) {
                DeveloperPo developer = null;
                for (int i = 0; i < developerList.size(); ++i) {
                    if (developerList.get(i).getDevId() == devId) {
                        developer = developerList.get(i);
                        break;
                    }
                }
                if (developer == null) {
                    genErrOutputMapWithCode("param error, invalid dev id", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
                dataMap.put("devId", developer.getDevId());
                dataMap.put("name", developer.getName());
                dataMap.put("type", developer.getType());
                dataMap.put("mark", developer.getMark());
                dataMap.put("showDetail", developer.getShowDetail());
                genSuccOutputMap();
            } else {
                genErrOutputMapWithCode("fail to get developer info", ReturnCode.GET_DEVELOPER_ERROR);
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}