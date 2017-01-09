package com.mama.server.main.dao.mongodb.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.mongodb.OrderCommentPo;
import com.mama.server.main.dao.mongodb.IOrderCommentDao;
import com.mama.server.main.dao.vo.QueryResultVo;

/**
 * 订单评论DAO接口实现
 * @author majiafei
 *
 */
@Component
public class OrderCommentDaoImpl implements IOrderCommentDao
{
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void addOrderComment(OrderCommentPo po)
	{
		mongoTemplate.insert(po);
	}

	@Override
	public QueryResultVo<OrderCommentPo> queryOrderComments(OrderCommentPo po, int sort,int currentPage,int pageSize)
	{
		//设置条件
		Criteria criteria = new Criteria();
		if (po.getHouseId() > 0)
		{
			criteria.and("HOUSE_ID").is(po.getHouseId());
		}
		
		if (po.getUid() > 0)
		{
			criteria.and("UID").is(po.getUid());
		}
		
		if (sort == 1) {
			criteria.and("STATUS").ne(2);
		} else {
			if (po.getStatus() > -1) 
			{
				criteria.and("STATUS").is(po.getStatus());
			}
		}
	
		criteria.and("IS_REMOVED").is(0);
		Query query = new Query(criteria);
		int skip = (currentPage - 1) * pageSize;
		query.skip(skip);// skip相当于从那条记录开始  
        query.limit(pageSize);// 从skip开始,取多少条记录 
        
        /** 总记录数 */
		long totalNum = mongoTemplate.count(query, OrderCommentPo.class);
		
		//按推荐时间排序
		if (sort == 1) {
			Sort sortBy = new Sort(Direction.DESC, "RECOMMEND_TIME");
			query.with(sortBy).with(new Sort(Direction.DESC, "CREATE_TIME"));
			//query.with(sortBy);
		}
		
		/** 当前页记录列表 */
        List<OrderCommentPo> pos = mongoTemplate.find(query, OrderCommentPo.class);
        
        QueryResultVo<OrderCommentPo> resultVo = new QueryResultVo<OrderCommentPo>();
        resultVo.setSourceList(pos);
        resultVo.setTotalNum(totalNum);
        
		return resultVo;
	}

	@Override
	public List<OrderCommentPo> queryByHouseId(int houseId)
	{
		if(houseId <= 0)
		{
			return null;
		}
		//设置条件
		Criteria criteria = new Criteria();
		criteria.and("HOUSE_ID").is(houseId);
		criteria.and("IS_REMOVED").is(0);
		criteria.and("STATUS").ne(2);
		
		/** 当前页记录列表 */
		Query query = new Query(criteria);
        List<OrderCommentPo> pos = mongoTemplate.find(query, OrderCommentPo.class);
        
		return pos;
	}
	
	@Override
	public OrderCommentPo queryOrderComment(OrderCommentPo po)
	{
		//设置条件
		Criteria criteria = new Criteria();
		if (po.getHouseId() > 0)
		{
			criteria.and("HOUSE_ID").is(po.getHouseId());
		}
		criteria.and("IS_REMOVED").is(0);
		criteria.and("STATUS").ne(2);
		
		
		if (!StringUtils.isEmpty(po.getOrderId())) {
			criteria.and("ORDERID").is(po.getOrderId());
		}
		Query query = new Query(criteria);
		
		Sort sort = new Sort(Direction.DESC, "RECOMMEND_TIME");
		query.with(sort).with(new Sort(Direction.DESC, "CREATE_TIME"));
		
		query.skip(0);// skip相当于从那条记录开始  
        query.limit(1);// 从skip开始,取多少条记录 
        
		/** 当前页记录列表 */
        List<OrderCommentPo> list = mongoTemplate.find(query, OrderCommentPo.class);
        if(list.size() > 0) {
        	return list.get(0);
        }
       return null;
	}
	
	@Override
	public void updateOrderComment(OrderCommentPo orderComment) {
		Query query = new Query(where("_id").is(orderComment.getId()));
		Update update = new Update();
		update.set("STATUS", orderComment.getStatus());
		if(orderComment.getRecommendTime() != null) {
			update.set("RECOMMEND_TIME", orderComment.getRecommendTime());
		}
		if(orderComment.getStatus() > 0 ) {
			update.set("STATUS", orderComment.getStatus());
		}
		if (orderComment.getOrderId() != null) {
			update.set("ORDERID", orderComment.getOrderId());
		}
		mongoTemplate.updateFirst(query, update, OrderCommentPo.class);
	}
	
	@Override
	public OrderCommentPo findOrderCommentById(String id) {
		return mongoTemplate.findById(id, OrderCommentPo.class);
	}
}
