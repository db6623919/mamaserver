package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;

@Component
public class AddCityHandler extends BaseHandler {
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("provId") == null) {
                genErrOutputMapWithCode("param error, provId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("name") == null) {
                genErrOutputMapWithCode("param error, name required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("type") == null) {
                genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("showDetail") == null) {
                genErrOutputMapWithCode("param error, showDetail required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            int sort = 0;
            if (param.get("sort") != null) {
                sort = (Integer)param.get("sort");
            }
            
            int provId = (Integer)param.get("provId");
            String name = (String)param.get("name");
            int type = (Integer)param.get("type");
            String pinyin = (String)param.get("pinyin");
            String showDetail = Json.format(param.get("showDetail"));
            
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
            
            
            
            CityPo city = new CityPo();
            city.setProvId(provId);
            city.setName(name);
            city.setType(type);
            city.setShowDetail(showDetail);
            city.setSort(sort);
            city.setPinyin(pinyin);
            CityPo cp=mainService.checkCity(city.getName());
            if(cp==null){
            	if (mainService.insCity(city) != 0) {
                    genErrOutputMapWithCode("fail to add city", ReturnCode.ADD_CITY_ERROR);
                    return outputMap;
                }
            	
            	//更新版本号
            	Version version = new Version();
                version.setVersionType(1);
                version = mainService.getVersionByPar(1);
                int versionNo = version.getId()!=0?version.getVersionNo():0;
                versionNo +=1;
                version.setVersionNo(versionNo);
                if (version.getId()!=0) {
                	//更新
                	if (mainService.updateVersion(version)!=0) {
                        genErrOutputMapWithCode("fail to update version", ReturnCode.ADD_CITY_ERROR);
                        return outputMap;
    				}
				}else{
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
        		
//                HouseCityCache.setHouseCityListToRedis(String.valueOf(versionNo));

            }else{
            	if(cp.getRemoved()==0){
                	dataMap.put("result", "ycz");
                }else{
                	if(mainService.updateCityByName(city)<1){
                		genErrOutputMapWithCode("fail to add city", ReturnCode.ADD_CITY_ERROR);
                        return outputMap;
                	}
                }
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
