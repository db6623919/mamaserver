package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.TopicActivityPo;

/**
 * 获取活动信息
 * @author whl
 *
 */
@Component
public class GetTopicActivitysByHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(GetTopicActivitysByHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start GetTopicActivitysByHandler.");
        	}
        	TopicActivityPo topicActivity = new TopicActivityPo();
        	Integer id =0;
        	if (param.get("id") != null) {
        		id = (Integer)param.get("id");
        		topicActivity.setId(id);
            }
        	
        	if (param.get("activityName") != null) {
        		String activityName = (String)param.get("activityName");
        		topicActivity.setActivityName(activityName);;
            }
        	//获取活动信息
        	List<TopicActivityPo> list = mainService.getTopicActivityBy(topicActivity);
        	dataMap.put("topicActivity", list.size()>0?list.get(0):null);
        	logger.info("run to end GetTopicActivitysByHandler.");
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("GetTopicActivitysByHandler is failed:活动信息获取失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
