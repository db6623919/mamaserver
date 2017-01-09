package com.mama.server.main.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.server.common.entity.NameIdEntity;
import com.mama.server.main.dao.TradeAreaDao;
import com.mama.server.main.dao.UtilDao;
import com.mama.server.main.dao.model.CityPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.TradeArea;
import com.mama.server.main.dao.model.mongodb.HouseNameIdPo;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.mongodb.IHouseSearchDao;
import com.mama.server.main.dao.vo.KeyWordVo;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.service.IHouseSearchService;
import com.meidusa.fastjson.JSONObject;

@Service
public class HouseSearchServiceImpl implements IHouseSearchService {
	@Autowired
	private IHouseSearchDao houseSearchDao;
	@Autowired
	private TradeAreaDao tradeAreaDao;
	@Autowired
	private UtilDao utilDao;
	
	@Override
	public QueryResultVo searchHouseSourceByCityId(int cityId, int sortBy,
			int pageNo, int numPerPage) {
		SearchCondition houseSearch = new SearchCondition();
		houseSearch.setCityId(cityId);
		return houseSearchDao.queryHouseSourceByCondition(houseSearch, sortBy, pageNo, numPerPage);
	}

	@Override
	public QueryResultVo searchHouseSourceByLocation(int cityId, int locationId,
			int sortBy, int pageNo, int numPerPage) {
		SearchCondition searchConditionVo = new SearchCondition();
		searchConditionVo.setCityId(cityId);
		searchConditionVo.setMerLocationId(locationId);
		return houseSearchDao.queryHouseSourceByCondition(searchConditionVo, sortBy, pageNo, numPerPage);
	}

	@Override
	public QueryResultVo searchHouseSourceAdvanced(SearchCondition searchCondition, 
			int sortBy, int pageNo, int numPerPage) {
		return houseSearchDao.queryHouseSourceByCondition(searchCondition, sortBy, pageNo, numPerPage);
	}
	
	@Override
	public void addHouseSource(HouseSearchPo houseSearch) {
		houseSearchDao.addHouseSource(houseSearch);
	}
	
	@Override
	public void updateHouseSource(HouseSearchPo houseSearch) {
		houseSearchDao.updateHouseSource(houseSearch);
	}
	
	@Override
	public void updateLocationIdAndName(List<HousePo> houseList,String ids) {
		List<Integer> areaIdList = new ArrayList<Integer>();
		String businessAreaName = "";
		//获取商圈名称
		if(!StringUtils.isEmpty(ids)) {
			String[] areaIds = ids.split(",");
			for (String areaID : areaIds) {
				TradeArea tradeArea = new TradeArea();
        		tradeArea.setId(Integer.valueOf(areaID));
        		businessAreaName += tradeAreaDao.getTradeAreaById(tradeArea).get(0).getName() + ",";
        		areaIdList.add(Integer.valueOf(areaID));
			}
		}
		//修改房源下商圈信息
		for(int i = 0 ; i < houseList.size() ; i ++ ) {
			Map<String,Object> houseMap = JSONObject.parseObject(houseList.get(i).getSummaryInfo());
			List<KeyWordVo> keyWordList = new ArrayList<KeyWordVo>();
	        KeyWordVo keyWord = new KeyWordVo();
	        keyWord.setKey("businessAreaName");
	        keyWord.setValue(businessAreaName);
	        keyWordList.add(keyWord);
	        KeyWordVo keyWord1 = new KeyWordVo();
	        keyWord1.setKey("name");
	        keyWord1.setValue(houseMap.get("houseName").toString());
	        keyWordList.add(keyWord1);
	        houseSearchDao.updateLocationIdAndName(houseList.get(i).getHouseId(), areaIdList, keyWordList);
		}
	}

	@Override
	public List<NameIdEntity> searchByName(String name)
	{
		List<HouseNameIdPo> pos = houseSearchDao.findByHouseName(name);
		if (CollectionUtils.isEmpty(pos))
		{
			return null;
		}
		
		List<NameIdEntity> houseEntities = new ArrayList<NameIdEntity>();
		for(HouseNameIdPo po : pos)
		{
			NameIdEntity houseEntity = new NameIdEntity();
			houseEntity.setId(po.getHouseId());
			CityPo cityPo = utilDao.getCityById(new Long(po.getCityId()).intValue());
			houseEntity.setName(po.getName()+"("+cityPo.getName()+")");
			houseEntity.setParentId(po.getCityId());
			houseEntities.add(houseEntity);			
		}
		
		return houseEntities;
	}

	@Override
	public void updateInventory(long houseId, Map<Long, Integer> inventory)
	{
		if((houseId < 1) || (inventory == null))
		{
			return;
		}
		
		houseSearchDao.updateInventory(houseId, inventory);
	}

	@Override
	public void addInventory(long houseId, Map<Long, Integer> inventory)
	{
		houseSearchDao.addInventory(houseId, inventory);
	}

	@Override
	public void deletInventory(long houseId, List<Long> dates)
	{
		houseSearchDao.delInventory(houseId, dates);
	}
}
