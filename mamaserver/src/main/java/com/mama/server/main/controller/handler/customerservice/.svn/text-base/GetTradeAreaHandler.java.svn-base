package com.mama.server.main.controller.handler.customerservice;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetTradeAreaHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	List<TradeArea> list = new ArrayList<TradeArea>();
        	List<TradeArea> taList = new ArrayList<TradeArea>();
        	list = mainService.getTradeAreas(param);
        	taList = mainService.getTradeAreas(null);
        	dataMap.put("list", list);
        	dataMap.put("num", taList.size());
            genSuccOutputMap();
            return outputMap;

        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
