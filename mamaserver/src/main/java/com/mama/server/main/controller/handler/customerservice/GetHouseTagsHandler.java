package com.mama.server.main.controller.handler.customerservice;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetHouseTagsHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	List<HouseTag> list = new ArrayList<HouseTag>();
        	List<HouseTag> htList = new ArrayList<HouseTag>();
        	list = mainService.getHouseTags(param);
        	htList = mainService.getHouseTags(null);
        	dataMap.put("list", list);
        	dataMap.put("num", htList.size());
            genSuccOutputMap();
            return outputMap;

        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
