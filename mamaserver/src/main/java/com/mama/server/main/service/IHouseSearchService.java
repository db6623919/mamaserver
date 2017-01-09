package com.mama.server.main.service;

import java.util.List;
import java.util.Map;

import com.mama.server.common.entity.NameIdEntity;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;

/** 房源搜索服务类 */
public interface IHouseSearchService {
	/** 根据城市搜索房源
	 * 	@param cityId 需要查询的城市id
	 *  @param sortBy 排序方向：1、推荐排序；2、价格排序-低；3、价格排序-高；
	 *  @param pageNo 第几页
	 *  @param numPerPage 每页显示几条记录
	 *  */
	public QueryResultVo searchHouseSourceByCityId(int cityId, int sortBy, int pageNo, int numPerPage);
	
	/** 根据商圈位置区域搜索房源
	 *  @param cityId 需要查询的城市id
	 *  @param locationId 商圈位置id
	 *  @param sortBy     排序方向：1、推荐排序；2、价格排序-低；3、价格排序-高；
	 *  @param pageNo     第几页
	 *  @param numPerPage 每页显示几条记录
	 *  */
	public QueryResultVo searchHouseSourceByLocation(int cityId, int locationId, 
			int sortBy, int pageNo, int numPerPage);

	/** 高级搜索功能
	 *  @param priceRange 价格:{"price":1"}；其中1：代表0-200
	 *  @param personNum  人数
	 *  @param roomNum    户型房间数
	 *  @param tagList    标签：{ " remark ":["1","2","3"]}，1、2、3分别代表"经济型","舒适型","海景房"
	 *  @param businessAreaName 商圈名称
	 *  @param houseName   房源名称
	 *  @param sortBy     排序方向：1、推荐排序；2、价格排序-低；3、价格排序-高；
	 *  @param pageNo     第几页
	 *  @param numPerPage 每页显示几条记录
	 *  */
	public QueryResultVo searchHouseSourceAdvanced(SearchCondition searchConditionVo, 
			int sortBy, int pageNo, int numPerPage);
	
	/**
	 * 房源新增
	 * @param houseSearch
	 */
	public void addHouseSource(HouseSearchPo houseSearch);
	
	/**
	 * 房源修改
	 * @param houseSearch
	 */
	public void updateHouseSource(HouseSearchPo houseSearch);
	
	/** 根据房源id修改商圈名称 */
	public void updateLocationIdAndName(List<HousePo> houseList,String ids);
	
	/**
	 * 根据名称，城市查询房源
	 * @param name
	 * @return
	 */
	public List<NameIdEntity> searchByName(String name);
	
	/**
	 * 更新房源库存
	 * @param houseId
	 * @param inventory
	 */
	public void updateInventory(long houseId, Map<Long, Integer> inventory);
	
	/**
	 * 添加库存
	 * @param houseId
	 * @param firstDay
	 * @param lastDay
	 */
	public void addInventory(long houseId, Map<Long, Integer> inventory);
	
	/**
	 * 删除库存
	 * @param houseId
	 * @param firstDay
	 * @param lastDay
	 */
	public void deletInventory(long houseId, List<Long> dates);
}
