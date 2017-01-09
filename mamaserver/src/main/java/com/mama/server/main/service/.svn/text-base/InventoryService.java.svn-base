package com.mama.server.main.service;

import java.util.List;

import com.mama.server.main.dao.model.mongodb.InventoryPo;
import com.mama.server.main.dao.vo.SearchCondition;

/** 搜索库存服务类 */
public interface InventoryService {
	
	/**
	 * 更新房源库存
	 * @param houseId
	 * @param inventory
	 */
	public void updateInventory(InventoryPo inventory);
	
	/**
	 * 添加库存
	 * @param houseId
	 * @param firstDay
	 * @param lastDay
	 */
	public void addInventory(int houseId,int totalRoomNum);
	
	/**
	 * 删除库存
	 * @param houseId
	 * @param firstDay
	 * @param lastDay
	 */
	public void deletInventory(long houseId);
	
	/**
	 * 查询库存
	 * @return
	 */
	public List<InventoryPo> findInventory(SearchCondition searchConditionVo,long houseId);
	
	public InventoryPo findInventoryByPar(InventoryPo inventoryPo);
}