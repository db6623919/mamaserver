package com.mama.server.main.controller.handler.main;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CityPo;

@Component
public class SelectCityByProvinceHandler extends BaseHandler {
	
	@SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
            if (param.get("provId") == null) {
                genErrOutputMapWithCode("param error, provId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int provId = (Integer)param.get("provId");
            CityPo city=new CityPo();
            city.setProvId(provId);
            int result=mainService.getCityByProId(city);
            dataMap.put("result", result);
      
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
