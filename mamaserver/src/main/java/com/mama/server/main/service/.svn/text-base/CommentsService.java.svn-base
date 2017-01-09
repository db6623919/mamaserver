package com.mama.server.main.service;

import com.mama.server.main.dao.model.mongodb.OrderCommentPo;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.vo.HouseCommentVo;
import com.mama.server.main.vo.OrderCommentVo;

/**
 * 房源评论服务接口
 * @author majiafei
 *
 */
public interface CommentsService
{
	/**
	 * 根据houseId查询房源概况
	 * @param houseId
	 * @return
	 */
	HouseCommentVo getHouseComments(int houseId);
	
	
	/**
	 * 根据房源ID查询评论列表
	 * @param houseId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	QueryResultVo<OrderCommentPo> getOrderComments(OrderCommentPo orderComment, int sort,int currentPage,int pageSize);

	/**
	 * 添加评论
	 * @param vo
	 */
	void addOrderComments(OrderCommentVo vo);
	
	/**
	 * 删除评论
	 * @param po
	 */
	void modifyHouseComment(OrderCommentPo po);
	
	/**
	 * 审核评论
	 * @param orderCommnet
	 */
	void reviewOrderComment(OrderCommentPo orderCommnet);
	
	/**
	 * 评论详情
	 * @param id
	 * @return
	 */
	OrderCommentPo findOrderCommnetById(String id);
	
	/**
	 * 根据订单号删除评论
	 * @param orderid
	 */
	void delOrderCommentByOrderId(String orderId);
	
	/**
	 * 根据ORDERID查询评论详情
	 * @param po
	 * @return
	 */
	OrderCommentPo queryOrderComment(OrderCommentPo po);
}
