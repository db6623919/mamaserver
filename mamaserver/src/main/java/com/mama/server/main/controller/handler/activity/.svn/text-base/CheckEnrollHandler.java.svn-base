package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ActivityEnrollPo;
import com.mama.server.main.service.imp.RandomLuckyBeanServiceImpl;

/**
 * 手机，微信校验
 * @author Administrator
 *
 */
@Component
public class CheckEnrollHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start CheckEnrollHandler.");
        	}
        	String phone = (String)param.get("phone");
        	String weChat = (String)param.get("weChat");
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("phone", phone);
        	map.put("status", 0);
        	if(mainService.getActivityEnrolls(map).size() > 0) {
        		dataMap.put("isCheck", "phone");
        	}
        	map = new HashMap<String, Object>();
        	map.put("weChat", weChat);
        	map.put("status", 0);
        	if(mainService.getActivityEnrolls(map).size() > 0) {
        		dataMap.put("isCheck", "weChat");
        	}
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("CheckEnrollHandler is failed:检查失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
