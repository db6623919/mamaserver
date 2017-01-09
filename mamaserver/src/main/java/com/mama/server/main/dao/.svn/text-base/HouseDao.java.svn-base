package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.common.entity.RecommodHouse;
import com.mama.server.main.dao.model.BuildingPo;
import com.mama.server.main.dao.model.DeveloperPo;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.HousePricePo;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface HouseDao extends GenericDao<HousePo> {
	int insertDeveloper(DeveloperPo dp);

	int updateDeveloper(DeveloperPo dp);
	
	int getBuildingByDevId(int devId);

	List<DeveloperPo> getDeveloperByDevId(DeveloperPo dp);

	List<DeveloperPo> getDeveloper(Map map);

	int insertBuilding(BuildingPo bp);

	int updateBuilding(BuildingPo bp);

	List<BuildingPo> getBuilding(BuildingPo bp);
	List<BuildingPo> getBuildings(Map map);

	List<BuildingPo> getBuildingByCityId(BuildingPo bp);

	int insertHouse(HousePo hp);

	int updateHouse(HousePo hp);

	List<HousePo> getHouseByBldId(HousePo hp);

	List<HousePo> getHouseByAllParam(HousePo hp);

	int insertHouseOrder(HouseOrderPo hp);

	int updateHouseOrderById(HouseOrderPo hp);

	List<HouseOrderPo> getHouseOrderByAllParam(HouseOrderPo hp);

	int deleteHousePrice(HousePricePo hp);

	int insertHousePrice(HousePricePo hp);

	int updateHousePriceById(HousePricePo hp);

	List<HousePricePo> getHousePriceByAllParam(HousePricePo hp);

	List<HousePricePo> getHousePriceByByDate(Map<String, Object> params);
	
	int updateHouseOrderByOrderId(HouseOrderPo ho);
	
	List<HousePo> getTopicHouses(Map map);
	
	List<RecommodHouse> getRecommondHouse();
}
