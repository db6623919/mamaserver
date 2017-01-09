package com.mama.server.main.controller.handler.customerservice;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.main.dao.model.mongodb.HouseShop;

@Component
public class GetHouseShopHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	List<HouseShopPo> list = new ArrayList<HouseShopPo>();
        	List<HouseShopPo> hsList = new ArrayList<HouseShopPo>();
        	list = mainService.getHouseShops(param);
        	hsList = mainService.getHouseShops(null);
        	
            //同步到mangodb
            if (param.get("sync")!=null ) {
				syncShopToMango(list);
			}
            
        	dataMap.put("list", list);
        	dataMap.put("num", hsList.size());
            genSuccOutputMap();
            return outputMap;

        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private void syncShopToMango(List<HouseShopPo> list){
    	for (HouseShopPo houseShopPo:list) {
    		String id = String.valueOf(houseShopPo.getId());
    		HouseShop shop = iHouseShopService.searchHouseShopPoById(String.valueOf(houseShopPo.getId()));
            if (null != shop) {//已存在  去重 不添加
				continue;
			}
            HouseShop houseShop = new HouseShop();
            houseShop.setId(id);
    		houseShop.setBossImage(houseShopPo.getBossImage());
    		houseShop.setBossPhone(houseShopPo.getBossPhone());
    		houseShop.setBossWeChat(houseShopPo.getBossWeChat());
    		houseShop.setShopDesc(houseShopPo.getShopDesc());
    		houseShop.setShopName(houseShopPo.getShopName());
    		houseShop.setPartnership(houseShopPo.getPartnership());
    		houseShop.setRecommended_status(houseShopPo.getRecommended_status());
    		
    		iHouseShopService.addHouseShopPo(houseShop);
    		
    	}
    }
    
}