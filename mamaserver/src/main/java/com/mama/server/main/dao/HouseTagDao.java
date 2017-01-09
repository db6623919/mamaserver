package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.HouseTag;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface HouseTagDao extends GenericDao<HouseTag> {

	int insertHouseTag(HouseTag houseTag);
	List<HouseTag> getAllHouseTag(Map map);
	List<HouseTag> getHouseTagById(HouseTag houseTag);
	List<HouseTag> getHouseTagByPar(HouseTag houseTag);
	List<HouseTag> duplicateHouseTagByPar(HouseTag houseTag);
	int delHouseTagById(HouseTag houseTag);
	int updateHouseTag(HouseTag houseTag);
	
}