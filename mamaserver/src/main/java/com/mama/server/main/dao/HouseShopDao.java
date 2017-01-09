package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.HouseShopPo;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface HouseShopDao extends GenericDao<HouseShopPo> {

	List<HouseShopPo> getAllHouseShops(Map map);
	List<HouseShopPo> getAllHouseShopsByTopicId(Map map);
	List<HouseShopPo> getHouseShopByPar(HouseShopPo houseShopPo);
	int insertHouseShop(HouseShopPo houseShopPo);
	int updateHouseShop(HouseShopPo houseShopPo);
	int delHouseShop(int id);
	
}