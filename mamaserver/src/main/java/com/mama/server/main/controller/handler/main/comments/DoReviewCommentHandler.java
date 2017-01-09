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
 * 评论审核
 * @author whl
 *
 */
@Component
public class DoReviewCommentHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	@Autowired
    protected CommentsService commentsService;
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("start to run DoReviewCommentHandler.");
        	}
        	if(param.get("id") == null) {
   			 	genErrOutputMapWithCode("param error, id required", ReturnCode.PARAM_ERROR);
                return outputMap;
        	}
        	if(param.get("status") == null) {
   			 	genErrOutputMapWithCode("param error, status required", ReturnCode.PARAM_ERROR);
                return outputMap;
        	}
        	if(param.get("houseId") == null) {
   			 	genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
                return outputMap;
        	}
        	String id = (String)param.get("id"); //活动编号
        	int status = (Integer)param.get("status"); //活动编号
        	int houseId = (Integer)param.get("houseId"); 
        	OrderCommentPo orderComment = new OrderCommentPo();
        	orderComment.setId(id);
        	orderComment.setStatus(status);
        	orderComment.setHouseId(houseId);
        	commentsService.reviewOrderComment(orderComment);
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("评论身审核异常.",e);
            genErrOutputMap("interface error");
        }
        if (logger.isInfoEnabled()) {
        	logger.info("end to run DoReviewCommentHandler.");
        }
        return outputMap;
    }
}
