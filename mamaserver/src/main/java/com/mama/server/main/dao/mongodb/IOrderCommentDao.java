package com.mama.server.main.dao.mongodb;

import java.util.List;

import com.mama.server.main.dao.model.mongodb.OrderCommentPo;
import com.mama.server.main.dao.vo.QueryResultVo;

/**
 * 订单评论DAO接口
 * @author majiafei
 *
 */
public interface IOrderCommentDao
{
	/**
	 * 添加订单评论
	 * @param po
	 */
	void addOrderComment(OrderCommentPo po);

	/**
	 * 分页查询订单评论
	 * @param po
	 * @param pageNo
	 * @param numPerPage
	 * @return
	 */
	QueryResultVo<OrderCommentPo> queryOrderComments(OrderCommentPo po, int Sort,int currentPage,int pageSize);

	/**
	 * 根据房源ID查询所有评论
	 * @param houseId
	 * @return
	 */
	List<OrderCommentPo> queryByHouseId(int houseId);
	
	/**
	 * 查询房源评论最新置顶信息
	 * @param po
	 * @return
	 */
	OrderCommentPo queryOrderComment(OrderCommentPo po);
	
	/**
	 * 评论信息修改
	 * @param orderComment
	 */
	void updateOrderComment(OrderCommentPo orderComment);
	
	/**
	 * 评论详情
	 * @param id 
	 * @return
	 */
	OrderCommentPo findOrderCommentById(String id);
}
