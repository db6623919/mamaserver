package com.mama.server.main.controller.handler.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.model.mongodb.InventoryPo;
import com.mama.server.main.dao.vo.HouseSearchConditionVo;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.service.IHouseSearchService;
import com.mama.server.util.JsonUtil;

/**
 * 
 * @author majiafei
 *
 */
@Component
public class AppHouseSearchHandler extends BaseHandler
{
	private static final Logger logger = LoggerFactory.getLogger(AppHouseSearchHandler.class);
	
	@Autowired
	private IHouseSearchService houseSearchService;

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("start to search house list. ");
		}
		
		try 
		{

			HouseSearchConditionVo conditionVo  = JsonUtil.mapToObject((Map<String, Object>)param.get("condition"), HouseSearchConditionVo.class);
        	//JSONObject jsonObject = JSONObject.fromObject(param.get("condition"));
        	//HouseSearchConditionVo conditionVo = (HouseSearchConditionVo) JSONObject.toBean(jsonObject, HouseSearchConditionVo.class);
			if (conditionVo != null)
			{
				if (logger.isInfoEnabled())
				{
					logger.info("start to search house list. {}", conditionVo);
				}
				
				SearchCondition condition = convertVo2Model(conditionVo);
				
				int sortBy = conditionVo.getSort();
				int pageNo = conditionVo.getPageIndex();
				int numPerPage = conditionVo.getPageSize();
				QueryResultVo resultVo = houseSearchService.searchHouseSourceAdvanced(condition, sortBy, pageNo, numPerPage);

				if (resultVo != null)
				{
					List list = resultVo.getSourceList();
					for (int i=0;i<list.size();i++) {
						HouseSearchPo houseSearch = (HouseSearchPo) list.get(i);
						List<InventoryPo> inventory_list =  inventoryService.findInventory(condition,houseSearch.getHouseId());
						for (InventoryPo inventory:inventory_list) {
							if (inventory.getInventory()==0) {
								list.remove(i);//移除库存为0的，不显示；
							}
						}
					}
					
					dataMap.put("itemCount", resultVo.getTotalNum());
					dataMap.put("houses", convertModel2List(resultVo.getSourceList()));
					//dataMap.put("resultVo", resultVo);
				}
				
				genSuccOutputMap();
			}
			
		}
		catch (Exception e) 
		{
			logger.error("failed to search house list", e);
			
			genErrOutputMap("failed to search houses list");
		}
		
		return outputMap;
	}
	
	private List<Map<String, Object>> convertModel2List(List<HouseSearchPo> pos){
		
		List<Map<String, Object>> houses = new ArrayList<Map<String,Object>>();
				
		for(HouseSearchPo po : pos)
		{
			Map<String, Object> house = new HashMap<String, Object>();
			house.put("houseId", po.getHouseId());
			house.put("houseName", po.getName());
			house.put("imgUrl", po.getImageUrl());
			house.put("price", po.getPrice());
			house.put("peopleCount", po.getPersonNum());
			house.put("roomCount", po.getRoomNum());
			house.put("tagList", po.getTagList());
			house.put("marketPrice", po.getMarketPrice());
			
			houses.add(house);
		}
		
		return houses;
	}

	private SearchCondition convertVo2Model(HouseSearchConditionVo conditionVo)
	{
		SearchCondition condition = new SearchCondition();
		condition.setCityId(conditionVo.getCityId());
		condition.setKeyWord(conditionVo.getKeyword());
		condition.setMerLocationId(conditionVo.getAreaId());
		condition.setPersonNum(conditionVo.getPeopleCount());
		condition.setPriceRange(conditionVo.getPriceSection());
		condition.setRoomNum(conditionVo.getRoomCount());
		condition.setHouseIds(conditionVo.getHouseIds());
		
		try 
		{
			condition.setLeavedTime(Long.valueOf(conditionVo.getCheckoutDate()));
			condition.setLivedTime(Long.valueOf(conditionVo.getCheckinDate()));
		} 
		catch (Exception e)
		{
			logger.warn("find no checkoutDate or checkiDate.");
		}
		
		int[] tags = conditionVo.getTags();
		if(tags != null)
		{
			List<Integer> tagList = new ArrayList<Integer>(tags.length);
			for(int i = 0; i < tags.length; i++)
			{
				tagList.add(tags[i]);
			}
			condition.setTagList(tagList);
		}
		return condition;
	}

}
