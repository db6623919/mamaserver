package com.mama.server.main.service;

import com.mama.server.main.dao.model.mongodb.HouseShop;

/**
 * 店铺
 * @author dengbiao
 *
 */
public interface IHouseShopService {

	/**
	 *店铺详情新增
	 * @param HouseShopPo
	 */
	public void addHouseShopPo(HouseShop houseShop);
	
	/**
	 * 店铺删除
	 */
	public void delHouseShopPo(String id);
	
	/**
	 * 店铺修改
	 * @param houseSearch
	 */
	public void updateHouseShopPo(HouseShop houseShop);
	
	/**
	 * 按ID查询店铺
	 * searchHouseShopPoById
	 */
	public HouseShop searchHouseShopPoById(String id);
	
	/**
	 * 按名称查找
	 */
	public HouseShop findOne(String shopName);
	
}