package com.mama.server.main.controller.handler.main.comments;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseCommentPo;
import com.mama.server.main.dao.model.mongodb.OrderCommentPo;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.service.imp.CommentsServiceImpl;
import com.mama.server.main.vo.HouseCommentVo;

/**
 * 评论精华
 * @author whl
 *
 */
@Component
public class StickOrderCommnetHandler extends BaseHandler
{
	private static final Logger logger = LoggerFactory.getLogger(CommentsServiceImpl.class);

	@Autowired
    protected CommentsService commentsService;
    
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		if (logger.isInfoEnabled()) {
			logger.info("run to start ModifyOrderCommentHandler.");
		}
		if(param.get("id") == null) {
			 genErrOutputMapWithCode("param error, id required", ReturnCode.PARAM_ERROR);
             return outputMap;
		}
		if(param.get("houseId") == null) {
			genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
            return outputMap;
		}
		if(param.get("recommendTime") == null) {
			genErrOutputMapWithCode("param error, recommendTime required", ReturnCode.PARAM_ERROR);
            return outputMap;
		}
		String id = (String)param.get("id");
		long recommendTime = new Long(0);
		if(!param.get("recommendTime").equals(0)){
			recommendTime = (Long)param.get("recommendTime") ;
		}
		
		int houseId = (Integer)param.get("houseId");
		try {
			OrderCommentPo orderComment = new OrderCommentPo();
			orderComment.setId(id);
			orderComment.setRecommendTime(recommendTime);
			orderComment.setHouseId(houseId);
			commentsService.modifyHouseComment(orderComment);
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("评论记录删除异常",e);
            genErrOutputMap("interface error");
        }
		if (logger.isInfoEnabled()) {
			logger.info("run to end ModifyOrderCommentHandler.");
		}
        return outputMap;
	}

}
