package com.mama.server.main.dao;

import java.util.List;

import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface OrderDao extends GenericDao<OrderPo> {
	public int insertOrder(OrderPo op);
	public int updateOrder(OrderPo op);
	public List<OrderPo> getOrderByAllParam(OrderPo op);
	
	public List<OrderPo> getOrderClickFarming(OrderPo op);
	public int insertClickFarming(OrderPo op); 
	
	/** 根据orderId查询OrderPo对象记录信息 */
    OrderPo queryOrderPoByOrderId(String orderId);
}
