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
import com.mama.server.main.service.imp.CommentsServiceImpl;

/**
 * 根据订单ID查询评分详情 
 * @author whl
 *
 */
@Component
public class GetOrderCommentByOrderIdHandler extends BaseHandler
{
	private static final Logger logger = LoggerFactory.getLogger(CommentsServiceImpl.class);

	@Autowired
    protected CommentsService commentsService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		if (logger.isInfoEnabled()) {
			logger.info("run to start GetOrderCommentByOrderIdHandler.");
		}
		try {
			if (param.get("orderId") == null) {
				genErrOutputMapWithCode("param error, orderId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			String orderId = (String)param.get("orderId");
			OrderCommentPo orderComment = new OrderCommentPo();
			orderComment.setOrderId(orderId);
			orderComment = commentsService.queryOrderComment(orderComment);
			dataMap.put("orderComment", orderComment);
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("评论记录查询异常",e);
            genErrOutputMap("interface error");
        }
		
		if (logger.isInfoEnabled()) {
			logger.info("run to end GetOrderCommentByOrderIdHandler.");
		}
		
        return outputMap;
	}

}
