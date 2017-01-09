package com.mama.server.main.service;

import java.util.List;

import com.mama.server.main.dao.model.mongodb.HouseDetailPo;
import com.mama.server.main.dao.model.mongodb.HouseInventory;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.vo.HouseDetailVo;

/**
 * 房源搜索详情类
 * @author dengbiao
 *
 */
public interface IHouseDetailService {

	/**
	 * 房源详情新增
	 * @param houseSearch
	 */
	public void addHouseDetail(HouseDetailVo houseDetail);
	
	/**
	 * 房源详情删除
	 */
	public void delHouseDetail(int houseId);
	
	/**
	 * 房源详情修改
	 * @param houseSearch
	 */
	public void updateHouseDeatil(HouseDetailPo houseDeatil);
	
	/**
	 * 按ID查询房源
	 * findHouseDeatilPoById
	 */
	public HouseDetailVo searchHouseDeatilPoById(int houseId);
	
	/**
	 * 按条件查询房源
	 */
	public QueryResultVo searchHouseDetailResultVo(HouseDetailVo houseDetail,int pageNo,int numPerPage);
	
	/**
	 * 查询所有房源的库存
	 * @return
	 */
	public List<HouseInventory> getAllHouseInventory();
	
}