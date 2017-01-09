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
 * CFHouseShop add 接口
 * @author dengbiao
 *
 */
@Component
public class AddCFHouseShopHandler extends BaseHandler {   
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	Log.SERVICE.info("Start to AddCFHouseShopHandler");
            int shopId = (Integer)param.get("shopId");
            int totalAmt = (Integer)param.get("totalAmt");
            int discountLimit = (Integer)param.get("discountLimit");
            String discount = (String)param.get("discount");
            String message_switch = (String)param.get("message_switch");
            int discountType = (Integer)param.get("discountType");
            int lowestAmt = (Integer)param.get("lowestAmt");
            
            CFHouseShopPo cfHouseShopPo = new CFHouseShopPo();
            cfHouseShopPo.setDiscount(Float.valueOf(discount));
            cfHouseShopPo.setDiscountLimit(discountLimit);
            cfHouseShopPo.setShopId(shopId);
            cfHouseShopPo.setTotalAmt(totalAmt);
            cfHouseShopPo.setMessage_switch(Integer.parseInt(message_switch));
            cfHouseShopPo.setDiscountType(discountType);
            cfHouseShopPo.setLowestAmt(lowestAmt);
            if (mainService.insCFHouseShop(cfHouseShopPo) != 0) {
            	genErrOutputMapWithCode("fail to add insCFHouseShop", ReturnCode.ADD_HOUSE_SHOP);
            	Log.SERVICE.error("fail to add insCFHouseShop "+ReturnCode.ADD_HOUSE_SHOP);
            	return outputMap;
            }

            Log.SERVICE.info("end to AddCFHouseShopHandler");
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
            Log.SERVICE.error("fail to add insCFHouseShop "+e);
        }
        return outputMap;
    }
           
}