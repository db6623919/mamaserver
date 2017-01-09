package com.mama.server.main.service;

import com.mama.server.main.dao.model.OrderPo;

public interface OrderDB {

	/**
	 * 更新业务订单
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(OrderPo record);
	
	/**
	 * 查找业务订单
	 * @param orderId
	 * @return
	 */
	OrderPo selectByPrimaryKey(String orderId);
	
}