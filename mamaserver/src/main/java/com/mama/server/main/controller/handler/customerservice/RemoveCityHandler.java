package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class RemoveCityHandler extends BaseHandler {
    
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("cityId") == null) {
                genErrOutputMapWithCode("param error, cityId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int cityId = (Integer)param.get("cityId");
            
            CityPo city = null;
            List<CityPo> cityList = mainService.getCity();
            if (cityList == null || cityList.size() == 0) {
                genErrOutputMapWithCode("param error, invalid city id", ReturnCode.GET_CITY_ERROR);
                return outputMap;
            }
            for (int i = 0; i < cityList.size(); ++i) {
                if (cityList.get(i).getCityId() == cityId) {
                    city = cityList.get(i);
                    break;
                }
            }
            if (city == null) {
                genErrOutputMapWithCode("param error, invalid city id", ReturnCode.GET_CITY_ERROR);
                return outputMap;
            }
            city.setRemoved(1);
            if (mainService.updateCityById(city) != 0) {
                genErrOutputMapWithCode("fail to remove city", ReturnCode.REMOVE_CITY_ERROR);
                return outputMap;
            }
            
        	//更新版本号
        	Version version = new Version();
            version.setVersionType(1);
            version = mainService.getVersionByPar(1);
            int versionNo = version.getId()!=0?version.getVersionNo():0;
            versionNo +=1;
            version.setVersionNo(versionNo);
            	//更新
            	if (mainService.updateVersion(version)!=0) {
                    genErrOutputMapWithCode("fail to update version", ReturnCode.REMOVE_CITY_ERROR);
                    return outputMap;
				}

        		RedisRequest req = new RedisRequest();
        		req.setKey("mmsf:cityVersion");
        		req.setValue(String.valueOf(versionNo));
        		redisFacade.putKeyAndValue(req);
            
//            HouseCityCache.setHouseCityListToRedis(String.valueOf(versionNo));
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
