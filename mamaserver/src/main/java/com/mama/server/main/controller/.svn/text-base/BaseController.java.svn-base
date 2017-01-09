package com.mama.server.main.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.mama.server.main.service.MainService;

abstract public class BaseController {
    
    @Autowired
    protected MainService mainService;
    
    protected void genErrOutputMap(Map<String, Object> outputMap, String msg) {
        outputMap.put("code", -1);
        outputMap.put("msg", msg);
        outputMap.put("data", null);
    }
    
    protected void genErrOutputMapWithCode(Map<String, Object> outputMap, String msg, int code) {
        outputMap.put("code", code);
        outputMap.put("msg", msg);
        outputMap.put("data", null);
    }
    
    protected void genSuccOutputMap(Map<String, Object> outputMap, Object data) {
        outputMap.put("code", 0);
        outputMap.put("msg", "ok");
        outputMap.put("data", data);
    }
    
    protected abstract void initInterfaceMap();
}
