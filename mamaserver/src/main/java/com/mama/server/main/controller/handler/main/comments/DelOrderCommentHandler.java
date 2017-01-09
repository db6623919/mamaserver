package com.mama.server.main.controller.handler.main.comments;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.service.CommentsService;

@Component
public class DelOrderCommentHandler extends BaseHandler
{
	@Resource
	private CommentsService commentsService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		String orderId = (String) param.get("orderId");
		if (log.isInfoEnabled())
		{
			log.info("start to delete order comment, orderId is {}", orderId);
		}
		
		if (StringUtils.isEmpty(orderId))
		{
			genSuccOutputMap();
			return outputMap;
		}
		
		try 
		{
			commentsService.delOrderCommentByOrderId(orderId);
			genSuccOutputMap();
		}
		catch (Exception e) 
		{
			log.error("failed to delete order comments, orderId is {}.", orderId);
			genErrOutputMap("删除订单失败");
		}
		
		
		return outputMap;
	}

}
