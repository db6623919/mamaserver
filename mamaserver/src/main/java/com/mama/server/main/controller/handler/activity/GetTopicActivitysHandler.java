package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.TopicActivityPo;
import com.mama.server.main.service.imp.RandomLuckyBeanServiceImpl;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/**
 * 活动列表查询
 * @author whl
 *
 */
@Component
public class GetTopicActivitysHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(GetTopicActivitysHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start GetTopicActivitysHandler.");
        	}
        	int currentPage = (Integer)param.get("currentPage");
        	int pageSize = (Integer)param.get("pageSize");
        	TopicActivityPo topicActivity = new TopicActivityPo();
        	topicActivity.setCurrent_page(currentPage);
        	topicActivity.setPage_size(pageSize);
        	Page<TopicActivityPo> page = mainService.getTopicActivity(topicActivity);
        	dataMap.put("page", page);
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("GetTopicActivitysHandler is failed:列表信息获取失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
