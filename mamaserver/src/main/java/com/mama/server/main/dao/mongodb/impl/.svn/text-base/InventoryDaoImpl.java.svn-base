package com.mama.server.main.dao.mongodb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.mongodb.InventoryPo;
import com.mama.server.main.dao.mongodb.InventoryDao;

@Component
public class InventoryDaoImpl implements InventoryDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void updateInventory(InventoryPo inventory){
		Query query = new Query();
		Criteria criatira = new Criteria();
		criatira.and("houseId").is(inventory.getHouseId());
		criatira.and("date").is(inventory.getDate());
		query.addCriteria(criatira);
		Update update = new Update();		
		update.set("inventory", inventory.getInventory());;
		mongoTemplate.updateFirst(query, update, InventoryPo.class);
	}

	@Override
	public void addInventory(InventoryPo inventory){		
		mongoTemplate.insert(inventory);
	}

	/**
	 * 删除过期的
	 */
	@Override
	public void delInventory(long houseId,String date){
		Query query = new Query();
		Criteria criatira = new Criteria();
		criatira.and("houseId").is(houseId);
		criatira.and("date").lt(date);
		query.addCriteria(criatira);
		mongoTemplate.remove(query, InventoryPo.class);
	}

	@Override
	public InventoryPo findInventory(InventoryPo inventoryPo) {
		Criteria criatira = new Criteria();
		criatira.and("date").is(inventoryPo.getDate());
		criatira.and("houseId").is(inventoryPo.getHouseId());
		Query query = new Query();
		query.addCriteria(criatira);
		query.skip(0);
		query.limit(10);
		InventoryPo inventory = new InventoryPo();
		inventory =	mongoTemplate.findOne(query, InventoryPo.class);;
		return inventory;
	}
	
}