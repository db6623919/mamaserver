package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;

@Component
public class UpdateCityHandler extends BaseHandler {
    
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("cityId") == null) {
                genErrOutputMapWithCode("param error, cityId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            int provId = -1;
            if (param.get("provId") != null) {
                provId = (Integer)param.get("provId");
            }
            String name = null;
            if (param.get("name") != null) {
                name = (String)param.get("name");
            }
            int type = -1;
            if (param.get("type") != null) {
                type = (Integer)param.get("type");
            }
            int sort = -1;
            if (param.get("sort") != null) {
            	sort = (Integer)param.get("sort");
            }
            String showDetail = null;
            if (param.get("showDetail") != null) {
                showDetail = Json.format(param.get("showDetail"));
            }
            
            int cityId = (Integer)param.get("cityId");
            String pinyin = (String)param.get("pinyin");
            
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
            if (provId != -1) {
                city.setProvId(provId);
            }
            if (name != null) {
                city.setName(name);
            }
            if (type != -1) {
                city.setType(type);
            }
            if (sort != -1) {
                city.setSort(sort);
            }
            if (showDetail != null) {
                city.setShowDetail(showDetail);
            }
            city.setPinyin(pinyin);
            if (mainService.updateCityById(city) != 0) {
                genErrOutputMapWithCode("fail to update city", ReturnCode.UPDATE_CITY_ERROR);
                return outputMap;
            }
            
        	//更新版本号
        	Version version = new Version();
            version.setVersionType(1);
            version = mainService.getVersionByPar(1);
            int versionNo =0;
            if (version!=null) {
            	 versionNo = version.getId()!=0?version.getVersionNo():0;
            	 versionNo +=1;
                 version.setVersionNo(versionNo);
            	//更新
            	if (mainService.updateVersion(version)!=0) {
                    genErrOutputMapWithCode("fail to update version", ReturnCode.ADD_CITY_ERROR);
                    return outputMap;
				}
			}else{
				version = new Version();
				version.setVersionType(1);
            	if (mainService.addVersion(version)!=0) {
                    genErrOutputMapWithCode("fail to add version", ReturnCode.ADD_CITY_ERROR);
                    return outputMap;
				}
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
