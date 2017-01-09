package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class RemoveProvinceHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("provId") == null) {
                genErrOutputMapWithCode("param error, provId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int provId = (Integer)param.get("provId");
            
            ProvincePo province = null;
            List<ProvincePo> provinceList = mainService.getProvince();
            if (provinceList == null || provinceList.size() == 0) {
                genErrOutputMapWithCode("param error, invalid province id", ReturnCode.GET_PROVINCE_ERROR);
                return outputMap;
            }
            for (int i = 0; i < provinceList.size(); ++i) {
                if (provinceList.get(i).getProvId() == provId) {
                    province = provinceList.get(i);
                    break;
                }
            }
            if (province == null) {
                genErrOutputMapWithCode("param error, invalid province id", ReturnCode.GET_PROVINCE_ERROR);
                return outputMap;
            }
            province.setRemoved(1);
            
            if (mainService.updateProvinceById(province) != 0) {
                genErrOutputMapWithCode("fail to remove province", ReturnCode.REMOVE_PROVINCE_ERROR);
                return outputMap;
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
