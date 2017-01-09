package com.mama.server.main.dao.mongodb;

import com.mama.server.main.dao.model.mongodb.InventoryPo;

public interface InventoryDao {
	
	/**
	 * 更新库存
	 * @param houseId 房源ID
	 * @param inventory  对应日期的库存（key为日期时间戳，value为新增（减）库存）
	 */
	public void updateInventory(InventoryPo inventory);
	
	/**
	 * 删除过期库存，增加新日期库存
	 */
	public void addInventory(InventoryPo inventoryPo);
	
	/**
	 * 删除库存
	 * @param houseId
	 * @param date 
	 * @param date
	 */
	public void delInventory(long houseId, String date);

	/**
	 * 查询库存
	 * @param searchConditionVo
	 * @return 
	 */
	public InventoryPo findInventory(InventoryPo inventoryPo);
	
}
