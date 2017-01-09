package com.mama.server.main.controller.handler.main.comments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.vo.OrderCommentVo;
import com.mama.server.util.JsonUtil;

/**
 * 添加订单的评论信息
 * @author majiafei
 *
 */
@Component
public class AddOrderCommentHandler extends BaseHandler
{
	private static final Logger logger = LoggerFactory.getLogger(AddOrderCommentHandler.class);

	@Resource
	private CommentsService commentsService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("start to add order comments. {}", param);
		}  
		
		try 
		{
			OrderCommentVo commentVo = JsonUtil.mapToObject((Map<String, Object>)param.get("orderComment"), OrderCommentVo.class);
			if (commentVo != null)
			{
				if (logger.isInfoEnabled())
				{
					logger.info("start to add order comments. {}", commentVo);
				}
				
				String[] images = commentVo.getImages();
				//if ((images != null) && images[0] == null) 
				if (images == null || images.length < 1)
				{
					commentVo.setImages(new String[0]);
				}

				//为通过状态（即值为1）的评论均认为是后台手动添加的，不需要检查订单状态
				if (commentVo.getStatus() != 1)
				{
					//查询订单状态,如果已评论则不允许再评论
					if(!checkOrderStatus(commentVo.getOrderId()))
					{
						return outputMap;
					}
				}
	            
				//增加订单评论
				commentsService.addOrderComments(commentVo);
				genSuccOutputMap();
			}
		} 
		catch (Exception e) 
		{
			genErrOutputMap(e.getMessage());
			logger.error("Failed to add order comments,", e);
		}
		
		return outputMap;
	}

	//只允许评价已离店的订单，并且只能评价一次
	private boolean checkOrderStatus(String orderId)
	{
		if (StringUtils.isEmpty(orderId))
		{
			logger.error("");
			genErrOutputMapWithCode("订单不存在", ReturnCode.GET_HOUSE_ORDER_ERROR);
			return false;
		}
		
		OrderPo order = new OrderPo();
		order.setOrderId(orderId);
		List<OrderPo> orderList = mainService.getOrderByAllParam(order);
		if (CollectionUtils.isNotEmpty(orderList)) 
		{
			//已离店
			int status = orderList.get(0).getStatus();
			if (status == ConstValue.ORDER_USER_LEAVED)
			{
				return true;
			}
			else if (status == ConstValue.ORDER_USER_COMMENTED)
			{
				logger.error("failed to add order comments,illegal status.");
				genErrOutputMapWithCode("订单已评价", ReturnCode.ORDER_COMMENTED_ERROR);
				return false;
			}
		}
		
		genErrOutputMapWithCode("未知错误", ReturnCode.PARAM_ERROR);
		return false;
	}
	
	public OrderCommentVo getOrderCommentVo(Map<String, Object> params)
	{
		Map<String, Object> param = (Map<String, Object>) params.get("orderComment");
		
		OrderCommentVo vo = new OrderCommentVo();
		vo.setComments((String) param.get("comments"));
		vo.setCreateTime(Long.valueOf((String) param.get("createTime")));
		vo.setHouseId((Integer) param.get("houseId"));
		
		if (param.get("images") != null) {
			List<String> list = (ArrayList<String>) param.get("images");
			vo.setImages(list.toArray(new String[0]));
		}
		
		vo.setScore((Integer) param.get("score"));
		vo.setUid(Long.valueOf((String) param.get("uid")));
		vo.setUserPhone((String) param.get("userPhone"));
		
		return vo;
	}
	
}
