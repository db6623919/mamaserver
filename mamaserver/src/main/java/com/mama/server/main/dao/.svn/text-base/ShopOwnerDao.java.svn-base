package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.clickfarming.CFShopOrderPo;
import com.mama.server.main.dao.model.clickfarming.ShuaDanOrderPo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface ShopOwnerDao {
	/** 根据手机号码查询该手机号码下属的客栈数量
	 *  @param mobile 手机号码
	 *  */
	public int queryShopNumByPhone(String mobile);
	
	List<CFShopOrderPo> getShopOrderList(CFShopOrderPo cfShopOrder);
	
	/** 根据手机号码、支付状态分页查询订单列表 */
	public List<ShuaDanOrderPo> getShuaDanShopOrderList(Map<String ,Object> map);
	
	List<OrderPo> getCfOrderList(Map<String,Object> map);
	/** 店铺对账单*/
	List<OrderPo> getShopBillList(Map<String,Object> map);
	/** 获取所有店铺总的统计*/
	List<OrderPo> getTotalBill(Map<String,Object> map);
}
