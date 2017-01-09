package com.mama.server.house;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mama.server.BaseUnitTest;
import com.mama.server.common.entity.NameIdEntity;
import com.mama.server.main.controller.handler.clickFarming.ClickFarmingOrderHandler;
import com.mama.server.main.controller.handler.main.house.KWSearchHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.mongodb.DateInventory;
import com.mama.server.main.dao.model.mongodb.HouseInventory;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.mongodb.IHouseSearchDao;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.service.IHouseDetailService;
import com.mama.server.main.service.IHouseSearchService;
import com.mama.server.main.service.MainService;
import com.mama.server.main.task.UpdateInventoryTask;


public class HouseUnitTest extends BaseUnitTest
{
	@Autowired
	private IHouseSearchService houseSearchService;
	
	@Autowired
	private IHouseDetailService houseDetailService;
	
	@Autowired
	private KWSearchHandler handler;
	
	@Autowired
	private UpdateInventoryTask task;
	
	@Autowired
	private IHouseSearchDao houseSearchDao;
	
	@Autowired
	private UpdateInventoryTask updateInventoryTask;
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	ClickFarmingOrderHandler clickFarmingOrderHandler;

	//@Test
	public void testHouseNameId()
	{
		List<NameIdEntity> entities = houseSearchService.searchByName("1");
		if (CollectionUtils.isEmpty(entities))
		{
			System.out.println("null");
		}
		else
		{
			System.out.println(entities.size());
		}
	}
	
	//@Test
	public void testHandler()
	{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyWord", "三");
		handler.handle(param);
	}
	
	//@Test
	public void testAddHouse()
	{
		HouseSearchPo po = new HouseSearchPo();
		List<DateInventory> inventories = new ArrayList<DateInventory>();
		inventories.add(new DateInventory(1000, 50));
		inventories.add(new DateInventory(2000, 50));
//		po.setInventory(inventories);
		
		houseSearchService.addHouseSource(po);
	}
	
//	@Test
	public void testUpdateInventory()
	{
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			long date = sdf.parse(sdf.format(new Date())).getTime();
			Map<Long, Integer> inventory = new HashMap<Long, Integer>();
			inventory.put(date, -1);
			houseSearchService.updateInventory(752, inventory);
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
//	@Test
	public void testGetInvenory()
	{
		List<HouseInventory> inventories = houseDetailService.getAllHouseInventory();
		if(CollectionUtils.isEmpty(inventories))
		{
			System.out.println("null");			
		}
		else {
			System.out.println(inventories.size());
		}
		
	}
	
//	@Test
	public void testAddInventory()
	{
		task.updateInventory();		
	}
	
//	@Test
	public void testQueryHouseId()
	{
		SearchCondition condition = new SearchCondition();
		condition.setCityId(3);
//		condition.setLivedTime(1480867200000L);
//		condition.setLeavedTime(1480953600000L);
//		houseSearchDao.queryHouseId(condition, 1, 10);
		houseSearchDao.queryHouseSourceByCondition(condition, 1, 1, 10);
	}
	
//	@Test
	public void testUpdateTask()
	{
		long houseId = 753;
//		List<Long> dates = new ArrayList<Long>();
//		dates.add(1488124800000L);
//		dates.add(1488038400000L);
		Map<Long, Integer> inventory = new HashMap<Long, Integer>();
		inventory.put(1488124800000L, 1);
		inventory.put(1488038400000L, 2);
		
		houseSearchDao.addInventory(houseId, inventory);
	}
	
	@Test
	public void getOrder()
	{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("houseId", 0);
		param.put("uid", "100008640033");
		param.put("shopId", 1);
		param.put("payPrice", 1);
		param.put("pay_type", "1");
		param.put("act", "add");
		
		HashMap<String, Object> reHashMap = clickFarmingOrderHandler.handle(param);
		String orderId = (String) ((HashMap<String, Object>)reHashMap.get("data")).get("orderId");
		
		OrderPo op = new OrderPo();
		op.setOrderId(orderId);
//		op.setUid(uid);
		List<OrderPo> orderList = mainService.getOrderByAllParam(op);
		System.out.println(orderList.get(0));
	}
	
	
	public static void main(String[] args)
	{
		HouseUnitTest test = new HouseUnitTest();
//		test.testHouseNameId();
//		test.testHandler();
//		test.testAddHouse();
//		test.testUpdateInventory();
//		test.testGetInvenory();
//		test.testAddInventory();
		
		test.testQueryHouseId();
		
//		test.testUpdateTask();
		
	}
}
