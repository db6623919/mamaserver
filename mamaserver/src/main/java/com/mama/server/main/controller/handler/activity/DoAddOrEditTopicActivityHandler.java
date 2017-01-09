package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.TopicActivityPo;
import com.mama.server.util.JsonUtil;

/**
 * 活动专题（add/del）
 * @author whl
 *
 */
@Component
public class DoAddOrEditTopicActivityHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(DoAddOrEditTopicActivityHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start DoAddOrEditTopicActivityHandler.");
        	}
        	if (param.get("flag") == null) {
                genErrOutputMapWithCode("param error, flag required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
        	String flag = (String)param.get("flag");
        	//转换活动PO
        	TopicActivityPo topicActivity  = JsonUtil.mapToObject((Map<String, Object>)param.get("topicActivity"), TopicActivityPo.class);
        	if (flag.equals("add")) { //add
        		mainService.insertTopicActivity(topicActivity);
        	} else if (flag.equals("edit")) { //edit
        		mainService.updateTopicActivity(topicActivity);
        	} else if (flag.equals("del")) { //del
        		mainService.removeTopicActivity(topicActivity);
        	}
        	logger.info("run to end DoAddOrEditTopicActivityHandler.");
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("CheckEnrollHandler is failed:检查失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
