package com.mama.server.main.dao.clickfarming;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.clickfarming.CFHouseShopPo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface CFHouseShopDao
{
	/**
	 * 插入或更新客栈
	 * @param po
	 */
	void insertOrUpdatePo(CFHouseShopPo po);
	
	/**
	 * 查询客栈
	 * @param po
	 * @return
	 */
	CFHouseShopPo selectPo(CFHouseShopPo po);
	
	/**
	 * 查询所有参加刷单活动的客栈
	 * @return
	 */
	List<CFHouseShopPo> selectAll();
	
	/**
	 * 查询客栈的总优惠金额
	 * @param id
	 * @return
	 */
	Integer selectFreezeAmtById(Map<String,Object> map);
}
