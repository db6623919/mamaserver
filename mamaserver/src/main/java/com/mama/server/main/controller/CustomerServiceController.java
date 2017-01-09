package com.mama.server.main.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.controller.handler.activity.AddOrDelTopicShopHandler;
import com.mama.server.main.controller.handler.activity.DoAddOrEditTopicActivityHandler;
import com.mama.server.main.controller.handler.activity.GetHouseShopByTopicIdHandler;
import com.mama.server.main.controller.handler.activity.GetTopicActivitysByHandler;
import com.mama.server.main.controller.handler.activity.GetTopicActivitysHandler;
import com.mama.server.main.controller.handler.activity.GetTopicShopHandler;
import com.mama.server.main.controller.handler.clickFarming.CFOrderStatusHandler;
import com.mama.server.main.controller.handler.clickFarming.GetCFOrderByShopHandler;
import com.mama.server.main.controller.handler.clickFarming.GetCFOrderHandler;
import com.mama.server.main.controller.handler.clickFarming.GetShopOrderHandler;
import com.mama.server.main.controller.handler.clickFarming.SettleOrderHandler;
import com.mama.server.main.controller.handler.clickFarming.ShopBillListHandler;
import com.mama.server.main.controller.handler.customerservice.AddBuildingHandler;
import com.mama.server.main.controller.handler.customerservice.AddCFHouseShopHandler;
import com.mama.server.main.controller.handler.customerservice.AddCityHandler;
import com.mama.server.main.controller.handler.customerservice.AddDeveloperHandler;
import com.mama.server.main.controller.handler.customerservice.AddFlowHandler;
import com.mama.server.main.controller.handler.customerservice.AddHouseHandler;
import com.mama.server.main.controller.handler.customerservice.AddHousePriceHandler;
import com.mama.server.main.controller.handler.customerservice.AddHouseShopHandler;
import com.mama.server.main.controller.handler.customerservice.AddHouseTagHandler;
import com.mama.server.main.controller.handler.customerservice.AddProvinceHandler;
import com.mama.server.main.controller.handler.customerservice.AddTradeAreaHandler;
import com.mama.server.main.controller.handler.customerservice.GetActivityEnrollsHandler;
import com.mama.server.main.controller.handler.customerservice.GetAllUserInfoHandler;
import com.mama.server.main.controller.handler.customerservice.GetCFHouseShopHandler;
import com.mama.server.main.controller.handler.customerservice.GetCityHandler;
import com.mama.server.main.controller.handler.customerservice.GetHouseOrdersHandler;
import com.mama.server.main.controller.handler.customerservice.GetHouseShopByParHandler;
import com.mama.server.main.controller.handler.customerservice.GetHouseShopHandler;
import com.mama.server.main.controller.handler.customerservice.GetHouseTagByIdHandler;
import com.mama.server.main.controller.handler.customerservice.GetHouseTagsHandler;
import com.mama.server.main.controller.handler.customerservice.GetTradeAreaByParHandler;
import com.mama.server.main.controller.handler.customerservice.GetTradeAreaHandler;
import com.mama.server.main.controller.handler.customerservice.RechargeHandler;
import com.mama.server.main.controller.handler.customerservice.RemoveBuildingHandler;
import com.mama.server.main.controller.handler.customerservice.RemoveCityHandler;
import com.mama.server.main.controller.handler.customerservice.RemoveDeveloperHandler;
import com.mama.server.main.controller.handler.customerservice.RemoveProvinceHandler;
import com.mama.server.main.controller.handler.customerservice.RemoveTradeAreaHandler;
import com.mama.server.main.controller.handler.customerservice.UpdateActivityEnrollHandler;
import com.mama.server.main.controller.handler.customerservice.UpdateBuildingHandler;
import com.mama.server.main.controller.handler.customerservice.UpdateCityHandler;
import com.mama.server.main.controller.handler.customerservice.UpdateDeveloperHandler;
import com.mama.server.main.controller.handler.customerservice.UpdateHouseHandler;
import com.mama.server.main.controller.handler.customerservice.UpdateHousePriceHandler;
import com.mama.server.main.controller.handler.customerservice.UpdateOrderHandler;
import com.mama.server.main.controller.handler.customerservice.UpdateProvinceHandler;
import com.mama.server.main.controller.handler.customerservice.n99.DoExchangeHouseCardfHandler;
import com.mama.server.main.controller.handler.customerservice.n99.GetShopTicketListHandler;
import com.mama.server.main.controller.handler.main.GetHousesHandler;
import com.mama.server.main.controller.handler.main.SelectCityByProvinceHandler;
import com.mama.server.util.Log;
import com.meidusa.fastjson.JSON;

@Controller
public class CustomerServiceController extends BaseController {
    
    @Autowired
    private RechargeHandler rechargeHandler;
    
    @Autowired
    private AddHouseHandler addHouseHandler;
    
    @Autowired
    private UpdateHouseHandler updateHouseHandler;
    
    @Autowired
    private UpdateOrderHandler updateOrderHandler;
    
    @Autowired
    private AddCityHandler addCityHandler;
    
    @Autowired
    private AddProvinceHandler addProvinceHandler;
    
    @Autowired
    private RemoveCityHandler removeCityHandler;
    
    @Autowired
    private RemoveProvinceHandler removeProvinceHandler;
    
    @Autowired
    private UpdateCityHandler updateCityHandler;
    
    @Autowired
    private UpdateProvinceHandler updateProvinceHandler;
    
    @Autowired
    private AddBuildingHandler addBuildingHandler;
    
    @Autowired
    private UpdateBuildingHandler updateBuildingHandler;
    
    @Autowired
    private RemoveBuildingHandler removeBuildingHandler;
    
    @Autowired
    private AddDeveloperHandler addDeveloperHandler;
    
    @Autowired
    private UpdateDeveloperHandler updateDeveloperHandler;
    
    @Autowired
    private RemoveDeveloperHandler removeDeveloperHandler;
    
    @Autowired
    private GetAllUserInfoHandler getAllUserInfoHandler;
    
    @Autowired
    private AddFlowHandler addFlowHandler;
    
    @Autowired
    private GetHouseOrdersHandler getHouseOrdersHandler;
    
    @Autowired
    private AddHousePriceHandler addHousePriceHandler;
    
    @Autowired
    private UpdateHousePriceHandler updateHousePriceHandler;

    @Autowired
    private GetCityHandler getCityHandler;
    
    @Autowired
    private GetHousesHandler getHousesHandler;

    @Autowired
    private SelectCityByProvinceHandler selectCityByProvinceHandler;
    
    @Autowired
    private GetTradeAreaHandler getTradeAreaHandler;
    
    @Autowired
    private AddTradeAreaHandler addTradeAreaHandler;
    
    @Autowired
    private GetTradeAreaByParHandler getTradeAreaByParHandler;
    
    @Autowired
    private RemoveTradeAreaHandler removeTradeAreaHandler;
    
    @Autowired
    private AddHouseTagHandler addHouseTagHandler;
    
    @Autowired
    private GetHouseTagsHandler getHouseTagsHandler;
    
    @Autowired
    private GetHouseTagByIdHandler getHouseTagByParHandler;

    @Autowired
    private GetActivityEnrollsHandler getActivityEnrollsHandler;
    
    @Autowired
    private UpdateActivityEnrollHandler updateActivityEnrollHandler;
    
    @Autowired
    private AddHouseShopHandler addHouseShopHandler;
    
    @Autowired
    private GetHouseShopHandler getHouseShopHandler;
    
    @Autowired
    private GetHouseShopByParHandler getHouseShopByParHandler;
    
    @Autowired
    private AddCFHouseShopHandler addCFHouseShopHandler;
    
    @Autowired
    private GetCFHouseShopHandler getCFHouseShopHandler;
    
    @Autowired
    private GetTopicActivitysHandler getTopicActivitysHandler;
    @Autowired
    private DoAddOrEditTopicActivityHandler doAddOrEditTopicActivityHandler;
    @Autowired
    private GetTopicActivitysByHandler getTopicActivitysByHandler;
    @Autowired
    private GetTopicShopHandler getTopicShopHandler;
    @Autowired
    private AddOrDelTopicShopHandler addOrDelTopicShopHandler;
    @Autowired
    private GetHouseShopByTopicIdHandler getHouseShopByTopicIdHandler;
    @Autowired
	private GetShopOrderHandler getShopOrderHandler;
    @Autowired
    private GetCFOrderHandler getCFOrderHandler;
    @Autowired
	private CFOrderStatusHandler cfOrderStatusHandler;
    @Autowired
    private SettleOrderHandler settleOrderHandler;
    @Autowired
    private ShopBillListHandler shopBillListHandler;
    @Autowired
    private GetCFOrderByShopHandler getCFOrderByShopHandler;
    @Autowired
    private GetShopTicketListHandler getShopTicketListHandler;
    @Autowired
    private DoExchangeHouseCardfHandler doExchangeHouseCardfHandler;
    
    private static Map<String, BaseHandler> interfaceMap = null;
   
    @Override
    protected void initInterfaceMap() {
        if (interfaceMap == null) {
            interfaceMap = new ConcurrentHashMap<String, BaseHandler>();
            interfaceMap.put("recharge", rechargeHandler);
            interfaceMap.put("addHouse", addHouseHandler);
            interfaceMap.put("updateHouse", updateHouseHandler);
            interfaceMap.put("updateOrder", updateOrderHandler);
            interfaceMap.put("addCity", addCityHandler);
            interfaceMap.put("addProvince", addProvinceHandler);
            interfaceMap.put("updateCity", updateCityHandler);
            interfaceMap.put("updateProvince", updateProvinceHandler);
            interfaceMap.put("removeCity", removeCityHandler);
            interfaceMap.put("removeProvince", removeProvinceHandler);
            interfaceMap.put("addBuilding", addBuildingHandler);
            interfaceMap.put("addDeveloper", addDeveloperHandler);
            interfaceMap.put("updateBuild", updateBuildingHandler);
            interfaceMap.put("updateDeveloper", updateDeveloperHandler);
            interfaceMap.put("removeBuilding", removeBuildingHandler);
            interfaceMap.put("removeDeveloper", removeDeveloperHandler);
            interfaceMap.put("getAllUserInfo", getAllUserInfoHandler);
            interfaceMap.put("addFlow", addFlowHandler);
            interfaceMap.put("getHouseOrders", getHouseOrdersHandler);
            interfaceMap.put("addHousePrice", addHousePriceHandler);
            interfaceMap.put("updateHousePrice", updateHousePriceHandler);
            interfaceMap.put("getCity", getCityHandler);
            interfaceMap.put("getCityByProId", selectCityByProvinceHandler);
            interfaceMap.put("getHouses", getHousesHandler);
            interfaceMap.put("getTradeArea", getTradeAreaHandler);
            interfaceMap.put("addTradeArea", addTradeAreaHandler);
            interfaceMap.put("getTradeAreaByPar", getTradeAreaByParHandler);
            interfaceMap.put("removeTradeArea", removeTradeAreaHandler);
            interfaceMap.put("addHouseTag", addHouseTagHandler);
            interfaceMap.put("getHouseTags", getHouseTagsHandler);
            interfaceMap.put("getHouseTagById", getHouseTagByParHandler);
            interfaceMap.put("getActivityEnrolls", getActivityEnrollsHandler);
            interfaceMap.put("updateActivityEnroll", updateActivityEnrollHandler);
            interfaceMap.put("addHouseShop", addHouseShopHandler);
            interfaceMap.put("getHouseShop", getHouseShopHandler);
            interfaceMap.put("getHouseShopByPar", getHouseShopByParHandler);
            interfaceMap.put("addCFHouseShop", addCFHouseShopHandler);
            interfaceMap.put("getCFHouseShop", getCFHouseShopHandler);
            interfaceMap.put("getTopicActivitys", getTopicActivitysHandler);
            interfaceMap.put("doAddOrEditTopicActivity", doAddOrEditTopicActivityHandler);
            interfaceMap.put("getTopicActivitysBy", getTopicActivitysByHandler);
            interfaceMap.put("getTopicShop", getTopicShopHandler);
            interfaceMap.put("addOrDelTopicShop", addOrDelTopicShopHandler);
            interfaceMap.put("getHouseShopByTopicId", getHouseShopByTopicIdHandler);
            interfaceMap.put("getShopOrder", getShopOrderHandler);
            interfaceMap.put("getCFOrder", getCFOrderHandler);
            interfaceMap.put("cfOrderStatus", cfOrderStatusHandler);
            interfaceMap.put("settleOrder", settleOrderHandler);
            interfaceMap.put("shopBillList", shopBillListHandler);
            interfaceMap.put("getCFOrderByShop", getCFOrderByShopHandler);
            interfaceMap.put("getShopTicketList", getShopTicketListHandler);
            interfaceMap.put("doExchangeHouseCard", doExchangeHouseCardfHandler);
        }
    }
    
    @SuppressWarnings("unchecked")
    @ResponseBody
    public String index(HttpServletRequest req,@RequestBody Map<String,Object> inputMap) {
        Map<String, Object> outputMap = new HashMap<String, Object>();
        
        if (!inputMap.containsKey("interface") || !inputMap.containsKey("param")) {
            Log.SERVICE.error("no interface or param");
            genErrOutputMap(outputMap, "param error");
//            return Json.format(outputMap);
            return JSON.toJSONString(outputMap);
        }
        
        initInterfaceMap();
        
        try {
            String keyInterface = (String)inputMap.get("interface");
            HashMap<String, Object> keyParam = (HashMap<String, Object>)(inputMap.get("param"));
            
            BaseHandler handler = interfaceMap.get(keyInterface);
            if (handler == null) {
                Log.SERVICE.error("no handler for " + keyInterface);
                genErrOutputMap(outputMap, "interface not exists");
            } else {
//                HashMap<String, Object> resultMap = handler.handle(keyParam);
//				解决handler单例问题， 使用InvokeHandle 替代 直接调用 handle
            	HashMap<String, Object> resultMap = handler.InvokeHandle(keyParam);
                Log.SERVICE.info(resultMap.toString());
                int code = (Integer)resultMap.get("code");
                String msg = (String)resultMap.get("msg");
                Object data = (Object)resultMap.get("data");
                if (code == 0) {
                    genSuccOutputMap(outputMap, data);
                } else {
                    genErrOutputMapWithCode(outputMap, msg, code);
                }
            }
        } catch (Exception e) {
            genErrOutputMap(outputMap, "param error");
        }
        
//        return Json.format(outputMap);
        return JSON.toJSONString(outputMap);
    }
}
