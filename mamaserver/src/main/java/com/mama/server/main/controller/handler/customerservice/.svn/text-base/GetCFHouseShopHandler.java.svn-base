package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import javax.annotation.Resource;

import org.apache.cxf.binding.soap.interceptor.StartBodyInterceptor;
import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.main.dao.model.clickfarming.CFHouseShopPo;
import com.mama.server.main.dao.model.mongodb.HouseShop;
import com.mama.server.util.Log;

/**
 * CFHouseShop 查询 接口
 * @author dengbiao
 *
 */
@Component
public class GetCFHouseShopHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	Log.SERVICE.info("Start to GetCFHouseShopHandler");
            int shopId = (Integer)param.get("shopId");      
            
            CFHouseShopPo cfHouseShopPo = new CFHouseShopPo();
            cfHouseShopPo.setShopId(shopId);
            
            CFHouseShopPo cfHouseShop = new CFHouseShopPo();
            cfHouseShop = mainService.getCFHouseShop(cfHouseShopPo);
            dataMap.put("cfHouseShop", cfHouseShop);
            Log.SERVICE.info("end to GetCFHouseShopHandler");
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
            Log.SERVICE.error("fail to add getCFHouseShop "+e);
        }
        return outputMap;
    }
           
}