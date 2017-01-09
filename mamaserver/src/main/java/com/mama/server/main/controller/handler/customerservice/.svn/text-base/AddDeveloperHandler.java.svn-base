package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;

@Component
public class AddDeveloperHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("name") == null) {
                genErrOutputMapWithCode("param error, name required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("type") == null) {
                genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("mark") == null) {
                genErrOutputMapWithCode("param error, mark required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("showDetail") == null) {
                genErrOutputMapWithCode("param error, showDetail required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String name = (String)param.get("name");
            int type = (Integer)param.get("type");
            String mark = (String)param.get("mark");
            String showDetail = Json.format(param.get("showDetail"));
            
            DeveloperPo developer = new DeveloperPo();
            developer.setName(name);
            developer.setType(type);
            developer.setMark(mark);
            developer.setShowDetail(showDetail);
            
            if (mainService.insertDeveloper(developer) != 0) {
                genErrOutputMapWithCode("fail to add developer", ReturnCode.ADD_DEVELOPER_ERROR);
                return outputMap;
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
