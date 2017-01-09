package com.mama.server.main.controller.handler.activity;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

/**
 * 查询店铺信息，并和主题活动关联
 * @author whl
 *
 */
@Component
public class GetHouseShopByTopicIdHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(GetHouseShopByTopicIdHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	logger.info("run satrt to GetHouseShopByTopicIdHandler.");
        	List<HouseShopPo> list = new ArrayList<HouseShopPo>();
        	List<HouseShopPo> hsList = new ArrayList<HouseShopPo>();
        	list = mainService.getAllHouseShopsByTopicId(param);
        	param.remove("limit");
        	hsList = mainService.getAllHouseShopsByTopicId(param);
        	dataMap.put("list", list);
        	dataMap.put("num", hsList.size());
        	logger.info("run end to GetHouseShopByTopicIdHandler.");
            genSuccOutputMap();
            return outputMap;
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    
}