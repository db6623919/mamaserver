package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.TopicShopPo;

/**
 * 添加或删除关联客栈
 * @author whl
 *
 */
@Component
public class AddOrDelTopicShopHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(AddOrDelTopicShopHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start AddOrDelTopicShopHandler.");
        	}
        	int topicID = (Integer)param.get("topicID"); //主题互动ID
        	int shopId = (Integer)param.get("shopId");   //客栈ID
        	String flag = (String)param.get("flag");    //标示
        	
        	TopicShopPo topicShop = new TopicShopPo();
        	topicShop.setTopicID(topicID);
        	topicShop.setShopId(shopId);
        	if (flag.equals("add")) { //add
        		mainService.insertTopicShop(topicShop);
        	} else {   //del
        		mainService.removeTopicShop(topicShop);
        	}
        	logger.info("run to end AddOrDelTopicShopHandler.");
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("AddOrDelTopicShopHandler is failed:关联客栈操作失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
