package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;

@Component
public class UpdateDeveloperHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("devId") == null) {
                genErrOutputMapWithCode("param error, devId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            String name = null;
            if (param.get("name") != null) {
                name = (String)param.get("name");
            }
            int type = -1;
            if (param.get("type") != null) {
                type = (Integer)param.get("type");
            }
            String mark = null;
            if (param.get("mark") != null) {
                mark = (String)param.get("mark");
            }
            String showDetail = null;
            if (param.get("showDetail") != null) {
                showDetail = Json.format(param.get("showDetail"));
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
            if (name != null) {
                developer.setName(name);
            }
            if (type != -1) {
                developer.setType(type);
            }
            if (mark != null) {
                developer.setMark(mark);
            }
            if (showDetail != null) {
                developer.setShowDetail(showDetail);
            }
            
            if (mainService.updateDeveloper(developer) != 0) {
                genErrOutputMapWithCode("fail to add developer", ReturnCode.UPDATE_DEVELOPER_ERROR);
                return outputMap;
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
