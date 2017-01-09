package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;

@Component
public class GetCityHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("provId") == null && param.get("name")==null && param.get("cityId")==null) {
                genErrOutputMapWithCode("param error, provId or name required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            CityPo city = new CityPo();
            List<CityPo> cityList = new ArrayList<CityPo>();
            if (param.get("provId")!=null) {
            	String provId_str = param.get("provId").toString();
            	int provId = Integer.parseInt(provId_str);
            	city.setProvId(provId);
            	cityList = mainService.getCityByPar(city);
			}else if (param.get("name")!=null ) {
				String name = param.get("name").toString();
				city.setName(name);
				cityList = mainService.getCityByName(city);
			}else if (param.get("cityId")!= null) {
				city.setCityId(Integer.valueOf(param.get("cityId").toString()));
				cityList = mainService.getCityByPar(city);
			}  
            if (cityList.size()==0) {
            	genErrOutputMap("city is null");
            	return outputMap;
			}
            dataMap.put("cityList", cityList);
            genSuccOutputMap();
            return outputMap;

        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
