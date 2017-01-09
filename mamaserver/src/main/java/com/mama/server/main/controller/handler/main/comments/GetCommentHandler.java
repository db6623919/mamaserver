package com.mama.server.main.controller.handler.main.comments;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.mongodb.OrderCommentPo;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.service.imp.RandomLuckyBeanServiceImpl;

/**
 * 评论详情
 * @author whl
 *
 */
@Component
public class GetCommentHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	@Autowired
    protected CommentsService commentsService;
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("start to run GetCommentHandler.");
        	}
        	if(param.get("id") == null) {
   			 	genErrOutputMapWithCode("param error, id required", ReturnCode.PARAM_ERROR);
                return outputMap;
        	}
        	String id = (String)param.get("id"); //活动编号
        	OrderCommentPo orderCommnet = commentsService.findOrderCommnetById(id);
        	dataMap.put("orderCommnet", orderCommnet);
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("评论详情查询异常.",e);
            genErrOutputMap("interface error");
        }
        if (logger.isInfoEnabled()) {
        	logger.info("end to run GetCommentHandler.");
        }
        return outputMap;
    }
}
