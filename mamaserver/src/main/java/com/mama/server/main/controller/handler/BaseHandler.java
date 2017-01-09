package com.mama.server.main.controller.handler;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.service.IHouseDetailService;
import com.mama.server.main.service.IHouseSearchService;
import com.mama.server.main.service.IHouseShopService;
import com.mama.server.main.service.InventoryService;
import com.mama.server.main.service.MainService;
import com.mama.server.main.service.RandomLuckyBeanService;
import com.mama.server.main.service.clickfarming.ICFHouseShopService;
import com.mama.server.main.service.clickfarming.IShopOwnerService;
import com.mama.server.util.ThreadMap;

public abstract class BaseHandler {
	
	protected Logger log = LoggerFactory.getLogger(getClass()); 
	
//    protected HashMap<String, Object> dataMap;
//    protected HashMap<String, Object> outputMap;
//  解决 handler单利问题，  使用 ThreadMap 替代  HashMap
	protected ThreadMap dataMap;
	protected ThreadMap outputMap;
    
    @Autowired
    protected MainService mainService;
    @Autowired
    protected RandomLuckyBeanService randomLuckyBeanService;
    @Autowired
    protected IHouseSearchService iHouseSearchService;
    @Autowired
    protected IHouseDetailService iHouseDetailService;
    @Autowired
    protected IHouseShopService iHouseShopService;
    @Autowired
    protected ICFHouseShopService icfHouseShopService;
    @Autowired
    protected InventoryService inventoryService;  
    
    
    public BaseHandler() {
    	
//        outputMap = new HashMap<String, Object>();
//        dataMap = new HashMap<String, Object>();
    	dataMap=new ThreadMap();
    	outputMap=new ThreadMap();
    	
        genErrOutputMap("param error");
    }
    
//	解决单利问题， 将直接调用 handle方法，改为调用 InvokeHandle方法
    public HashMap<String, Object> InvokeHandle(HashMap<String, Object> param){
    	dataMap.reset();
    	outputMap.reset();
    	ThreadMap map=(ThreadMap) this.handle(param);
    	return map.getHashMap();
    }
    
    
    public abstract HashMap<String, Object> handle(HashMap<String, Object> param);
    
    public void genErrOutputMap(String msg) {
        outputMap.put("code", ReturnCode.INTERFACE_ERROR);
        outputMap.put("msg", msg);
    }
    
    public void genErrOutputMap(String code, String msg) {
        outputMap.put("code", code);
        outputMap.put("msg", msg);
    }
    
    public void genErrOutputMapWithCode(String msg, int code) {
        outputMap.put("code", code);
        outputMap.put("msg", msg);
    }
    
    public void genSuccOutputMap() {
        outputMap.put("code", ReturnCode.OK);
        outputMap.put("msg", "ok");
        outputMap.put("data", dataMap);
    }
}
