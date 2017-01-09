//package com.mama.server.main.service;
//
//import com.mama.server.main.dao.OrderPayDO;
//
///**
// * 支付订单
// */
//public interface OrderPayDB {
//
//	/**
//	 * 插入支付订单
//	 * @param orderPayDO
//	 * @return
//	 */
//	int  insertOrderPayDO(OrderPayDO orderPayDO);
//	
//	/**
//	 * 更新支付订单，只能更新订单的状态
//	 * @param record
//	 * @return
//	 */
//	int updateByPrimaryKey(OrderPayDO record);
//	
//	/**
//	 * 查找订单
//	 * @param payId
//	 * @return
//	 */
//	OrderPayDO selectByPrimaryKey(String payId);
//
//	OrderPayDO selectByOrderId(String orderId);
//	
//}