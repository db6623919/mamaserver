package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.TopicShopPo;

/**
 * 获取关联客栈
 * @author whl
 *
 */
@Component
public class GetTopicShopHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(GetTopicShopHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start GetTopicShopHandler.");
        	}
        	TopicShopPo topicShop = new TopicShopPo();
        	Integer topicID = (Integer)param.get("topicId");
        	topicShop.setTopicID(topicID);
        	//获取关联客栈
        	List<TopicShopPo> list = mainService.getTopicShopList(topicShop);
        	dataMap.put("shopIdList", list);
        	logger.info("run to end GetTopicShopHandler.");
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("GetTopicShopHandler is failed:关联客栈获取失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
