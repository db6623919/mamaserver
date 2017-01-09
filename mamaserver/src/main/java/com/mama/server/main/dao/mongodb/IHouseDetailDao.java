package com.mama.server.main.dao.mongodb;

import java.util.List;

import com.mama.server.main.dao.model.mongodb.HouseDetailPo;
import com.mama.server.main.dao.model.mongodb.HouseInventory;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.vo.HouseDetailVo;

public interface IHouseDetailDao {

	/** 新增接口 */
	public void addHouseDetail(HouseDetailPo houseDeatil);
	
	/** 修改接口 */
	public void updateHouseDetail(HouseDetailPo houseDeatil);
	
	/** 删除接口
	 *  @param houseId 房源id
	 *  */
	public void deleteHouseDetailById(int houseId);
	
	/** 根据房源id查找房源明细信息
	 *  @param houseId 房源id
	 *  */
	public HouseDetailPo findHouseDeatilPoById(int houseId);
    
	/**
	 * 按条件搜索相关房源详情
	 * @param houseDetailVo
	 * @return
	 */
	public QueryResultVo queryHouseDetailByCondition(HouseDetailVo houseDetailVo,int pageNo, int numPerPage);
	
	public List<HouseInventory> QueryAllHouseInventory();
	
}