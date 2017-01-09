package com.mama.server.main.dao.mongodb;

import com.mama.server.main.dao.model.mongodb.HouseShop;

public interface IHouseShopDao {

	/** 新增接口 */
	public void addHouseShopPo(HouseShop houseShop);
	
	/** 修改接口 */
	public void updateHouseShopPo(HouseShop houseShop);
	
	/** 删除接口
	 *  @param houseId 房源id
	 *  */
	public void deleteHouseShopPoById(String id);
	
	/** 根据id查找店铺明细信息
	 *  @param houseId 房源id
	 *  */
	public HouseShop findHouseShopPoById(String id);
	
    /** 
     * 通过一定的条件查询一个实体 
     *  
     * @param query 
     * @return 
     */  
    public HouseShop findOne(String shopName) ;  
}