package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class RemoveTradeAreaHandler extends BaseHandler {
    
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("id") == null) {
                genErrOutputMapWithCode("param error, id required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int id = (Integer)param.get("id");
            String cityId = (String)param.get("cityId");
            TradeArea tradeArea = new TradeArea();
            tradeArea.setId(id);
            
            BuildingPo building = new BuildingPo();
            building.setCityId(Integer.parseInt(cityId));
            List<BuildingPo> buildingList = mainService.getBuildingByCityId(building);
            for(int i = 0 ; i < buildingList.size() ; i ++) {
            	String areaIds = buildingList.get(i).getTrade_area();
            	if(!StringUtils.isEmpty(areaIds)) {
            		String[] ids = areaIds.split(",");
            		for (String areaId : ids) {
						if(Integer.valueOf(areaId) == id) { //判断商圈是否被楼盘占用
							//outputMap.put("code", "2");
							genErrOutputMapWithCode("fail to remove TradeArea", 2);
							return outputMap;
						}
					}
            	}
            }
            
            if (mainService.delTradeArea(tradeArea) != 0) {
                genErrOutputMapWithCode("fail to remove TradeArea", ReturnCode.REMOVE_CITY_ERROR);
                return outputMap;
            }
            putRedis(cityId);
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private void putRedis(String cityId){
		List<TradeArea> tList = new ArrayList<TradeArea>();
		TradeArea tArea = new TradeArea();
		tArea.setCityId(Integer.parseInt(cityId));
		tList = mainService.getTradeAreaByPar(tArea);
		String value = "";
		for (int i = 0; i < tList.size(); i++) {
			TradeArea trade = new TradeArea();
			trade = tList.get(i);
			if (i!=tList.size()-1) {
				value+="{\"id\":"+trade.getId().toString()+";\"name\":"+trade.getName()+"},";
			}else {
				value+="{\"id\":"+trade.getId().toString()+";\"name\":"+trade.getName()+"}";
			}
		}
		RedisRequest reqRe = new RedisRequest();
		reqRe.setKey("mmsf:tradeArea:"+cityId);
		if ("".equals(value)||null==value) {
			reqRe.setValue("{}");
		}else {
			reqRe.setValue(value);
		}
		
		redisFacade.putKeyAndValue(reqRe);
//		TradeAreaCache.setTradeAreaToRedis(cityId,value);
		
    }
    
}