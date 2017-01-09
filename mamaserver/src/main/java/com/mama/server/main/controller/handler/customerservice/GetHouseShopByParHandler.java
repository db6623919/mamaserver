package com.mama.server.main.controller.handler.customerservice;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.controller.handler.clickFarming.ClickFarmingOrderHandler;
import com.mama.server.main.dao.model.mongodb.HouseShop;

@Component
public class GetHouseShopByParHandler extends BaseHandler {
    
	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(ClickFarmingOrderHandler.class);
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	HouseShop houseShop = new HouseShop();
        	if (null!=param.get("shopName")) {
        		String shopName = param.get("shopName").toString();
            	houseShop = iHouseShopService.findOne(shopName);
			}else if (null!=param.get("shopId")) {
				String shopId = param.get("shopId").toString();
	        	houseShop = iHouseShopService.searchHouseShopPoById(shopId);
			}
            if (null!=houseShop) {
            	dataMap.put("houseShop", houseShop);
            	
                genSuccOutputMap();
			}else {
				genErrOutputMap("mongodb中无对应记录");
			}

            return outputMap;

        } catch (Exception e) {
            genErrOutputMap("获取客栈信息失败:" + e.toString());
            logger.error("获取客栈信息失败:",e);
        }
        return outputMap;
    }
    
}