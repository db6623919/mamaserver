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
import com.mama.server.main.controller.handler.merchant.ContinueOrderHandler;
import com.mama.server.main.controller.handler.merchant.ReturnOrderHandler;
import com.mama.server.main.controller.handler.merchant.UpdateOrderLiveDetailHandler;
import com.mama.server.main.controller.handler.merchant.UpdateOrderStatusHandler;
import com.mama.server.main.service.MainService;
import com.mama.server.util.Log;
import com.meidusa.fastjson.JSON;

@Controller
public class MerchantController extends BaseController {
    
    @Autowired
    private MainService mainService;
    
    @Autowired
    private UpdateOrderStatusHandler updateOrderStatusHandler;
    
    @Autowired
    private UpdateOrderLiveDetailHandler updateOrderLiveDetailHandler;
    
    @Autowired
    private ReturnOrderHandler returnOrderHandler;
    
    @Autowired
    private ContinueOrderHandler continueOrderHandler;
    
    private static Map<String, BaseHandler> interfaceMap = null;
    
    @Override
    protected void initInterfaceMap() {
        if (interfaceMap == null) {
            interfaceMap = new ConcurrentHashMap<String, BaseHandler>();
            interfaceMap.put("updateOrderStatus", updateOrderStatusHandler);
            interfaceMap.put("updateOrderLiveDetail", updateOrderLiveDetailHandler);
            interfaceMap.put("returnOrder", returnOrderHandler);
            interfaceMap.put("continueOrder", continueOrderHandler);
        }
    }
    
    @SuppressWarnings("unchecked")
    @ResponseBody
    public String index(HttpServletRequest req,@RequestBody Map<String,Object> inputMap) {
        Map<String, Object> outputMap = new HashMap<String, Object>();
        
        if (!inputMap.containsKey("interface") || !inputMap.containsKey("param")) {
            Log.SERVICE.error("no interface or param");
            genErrOutputMap(outputMap, "param error");
//          return Json.format(outputMap);
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
//            	解决handler单例问题， 使用InvokeHandle方法替代原始的 handle方法
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
