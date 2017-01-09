package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.TopicActivityPo;

/**
 * 获取主题活动接口
 * @author whl
 *
 */
@Component
public class GetTopicsHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(GetTopicsHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start GetAllTopicActivitysHandler.");
        	}
        	List<TopicActivityPo> topicList = mainService.getTopicActivityBy(null);
        	dataMap.put("topicList", topicList);
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("GetAllTopicActivitysHandler is failed:活动主题列表获取失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
