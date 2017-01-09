package com.mama.server.main.controller.handler.main.comments;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.vo.HouseCommentVo;

/**
 * 查询房源的评论概况
 * @author majiafei
 *
 */
@Component
public class GetHouseCommentsHandler extends BaseHandler
{
	private static final Logger logger = LoggerFactory.getLogger(GetHouseCommentsHandler.class);

	@Resource
	private CommentsService commentsService;

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("start to get house comments. ");
		}
		
		try 
		{
			if (param.get("houseId") == null) {
				return outputMap;
			}
			
			int houseId = (Integer) param.get("houseId");
			if (houseId > 0)
			{
				HouseCommentVo vo = commentsService.getHouseComments(houseId);
				dataMap.put("result", vo);
				genSuccOutputMap();
			}
		} 
		catch (Exception e) 
		{
			genErrOutputMap(e.getMessage());
			logger.error("Failed to get house comments.", e);
		}
		
		return outputMap;
	}

}
