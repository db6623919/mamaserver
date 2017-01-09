package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;

@Component
public class AddProvinceHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("name") == null) {
                genErrOutputMapWithCode("param error, name required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("showDetail") == null) {
                genErrOutputMapWithCode("param error, showDetail required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String name = (String)param.get("name");
            String showDetail = Json.format(param.get("showDetail"));
            
            ProvincePo province = new ProvincePo();
            province.setName(name);
            province.setShowDetail(showDetail);
            
            if(mainService.checkProvince(name)>0){
            	if(mainService.updateProvinceByName(province)<1){
            		genErrOutputMapWithCode("fail to update province", ReturnCode.UPDATE_PROVINCE_ERROR);
                    return outputMap;
            	}
            }else{
            	if (mainService.insProvince(province) != 0) {
                    genErrOutputMapWithCode("fail to add province", ReturnCode.ADD_PROVINCE_ERROR);
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
