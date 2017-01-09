package com.mama.server.main.controller.handler.customerservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CityPo;
import com.mama.server.main.dao.model.TradeArea;

@Component
public class AddTradeAreaHandler extends BaseHandler {
	
	@Resource(name = "redisFacade")
	private RedisFacade      redisFacade;
	   
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("name") == null) {
                genErrOutputMapWithCode("param error, name required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("cityId") == null) {
                genErrOutputMapWithCode("param error, cityId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            int sort = 0;
            
            if (param.get("sort") != null) {
                sort = (Integer)param.get("sort");
            }
            String name = (String)param.get("name");
            String cityId = (String)param.get("cityId");
            String flag = (String)param.get("flag");
            
            TradeArea tradeArea = new TradeArea();
            tradeArea.setName(name);
            tradeArea.setCityId(Integer.parseInt(cityId));
            List<TradeArea> list = mainService.getTradeAreaByPar(tradeArea);
            
            if ("add".equals(flag)) {
                if (null!=list && 0 !=list.size()) {
                		genErrOutputMapWithCode("fail to add insTradeArea,"+name+"Already exist", ReturnCode.ADD_TRADE_AREA);
    	            	return outputMap;
    			}else {
    				//新增
    				tradeArea.setSort(sort);
    				if (mainService.insTradeArea(tradeArea) != 0) {
    	            	genErrOutputMapWithCode("fail to add insTradeArea", ReturnCode.ADD_TRADE_AREA);
    	            	return outputMap;
    	            }
                    
    			}
			}else if ("update".equals(flag)) {
				int id = Integer.parseInt((String)param.get("id"));
				if (null!=list && 0 !=list.size()) {
					TradeArea area = new TradeArea();
					area = list.get(0);
					if (area.getId()!=id) {
						genErrOutputMapWithCode("fail to add updateTradeArea,"+name+"Already exist", ReturnCode.ADD_TRADE_AREA);
		            	return outputMap;
					}
			      }
					//update
	            	
	            	tradeArea.setId(id);
	            	tradeArea.setSort(sort);
	            	if (mainService.updateTradeArea(tradeArea)!=0) {
	            		genErrOutputMapWithCode("fail to add updateTradeArea", ReturnCode.ADD_TRADE_AREA);
		            	return outputMap;
					}
	            	
			}
            putRedis();
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private void putRedis(){
    	List<CityPo> citysList = mainService.getCityByType(null);
    	for (CityPo cityPo:citysList) {
    		int cityId = cityPo.getCityId();
    		List<TradeArea> tList = new ArrayList<TradeArea>();
    		TradeArea tArea = new TradeArea();
    		tArea.setCityId(cityId);
    		tList = mainService.getTradeAreaByPar(tArea);
    		String value = "";
    		for (int i = 0; i < tList.size(); i++) {
    			TradeArea trade = new TradeArea();
    			trade = tList.get(i);
    			if (i!=tList.size()-1) {
    				value+="{\"id\":"+trade.getId().toString()+",\"name\":\""+trade.getName()+"\"},";
    			}else {
    				value+="{\"id\":"+trade.getId().toString()+",\"name\":\""+trade.getName()+"\"}";
    			}
    		}

    		RedisRequest req = new RedisRequest();
    		req.setKey("mmsf:tradeArea:"+cityId);
    		req.setValue(value);
    		redisFacade.putKeyAndValue(req);
    		
//    		TradeAreaCache.setTradeAreaToRedis(String.valueOf(cityId),value);
    		
		}

    }
    
}
