package com.mama.server.main.dao.mongodb;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.mongodb.HouseNameIdPo;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.vo.KeyWordVo;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;

public interface IHouseSearchDao {
	/** 新增接口 */
	public void addHouseSource(HouseSearchPo houseSearch);
	
	/** 修改接口 */
	public void updateHouseSource(HouseSearchPo houseSearch);
	
	/** 根据房源id修改商圈名称 */
	public void updateLocationIdAndName(long houseId, List<Integer> businessAreaList, List<KeyWordVo> keyWord);
	
	/** 删除接口
	 *  @param houseId 房源id
	 *  */
	public void deleteHouseSourceById(long houseId);
	
	/** 搜索相关房源 */
	public QueryResultVo queryHouseSourceByCondition(SearchCondition searchCondition, 
			int sortBy, int pageNo, int numPerPage);
	
	/** 根据房源id查找房源明细信息
	 *  @param houseId 房源id
	 *  */
	public HouseSearchPo findHouseSearchPoById(long houseId);
	
	/**
	 * 根据名称查询房源
	 * @return
	 */
	public List<HouseNameIdPo> findByHouseName(String name);
	
	/**
	 * 更新库存
	 * @param houseId 房源ID
	 * @param inventory  对应日期的库存（key为日期时间戳，value为新增（减）库存）
	 */
	public void updateInventory(long houseId, Map<Long, Integer> inventory);
	
	/**
	 * 删除过期库存，增加新日期库存
	 * @param houseId
	 * @param delDay
	 * @param inventory
	 */
	public void addInventory(long houseId, Map<Long, Integer> inventory);
	
	/**
	 * 删除库存
	 * @param houseId
	 * @param date
	 */
	public void delInventory(long houseId, List<Long> dates);
	
}
