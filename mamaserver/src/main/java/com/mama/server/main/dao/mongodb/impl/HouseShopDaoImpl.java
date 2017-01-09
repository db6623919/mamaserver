package com.mama.server.main.dao.mongodb.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.mongodb.HouseShop;
import com.mama.server.main.dao.mongodb.IHouseShopDao;

@Component
public class HouseShopDaoImpl implements IHouseShopDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void addHouseShopPo(HouseShop houseShop) {
		mongoTemplate.insert(houseShop);
	}

	@Override
	public void updateHouseShopPo(HouseShop houseShop) {
		Query query = new Query(where("_id").is(houseShop.getId()));
		Update update = new Update();
		
		update.set("shopName", houseShop.getShopName());
		update.set("shopDesc", houseShop.getShopDesc());
		if (null!=houseShop.getBossImage() && !"".equals(houseShop.getBossImage())) {
			update.set("bossImage", houseShop.getBossImage());
		}
		update.set("bossPhone", houseShop.getBossPhone());
		update.set("bossWeChat", houseShop.getBossWeChat());
		update.set("partnership", houseShop.getPartnership());
		
		mongoTemplate.updateFirst(query, update, HouseShop.class);
	}

	@Override
	public void deleteHouseShopPoById(String id) {
		Query query = new Query(where("_id").is(id));
        mongoTemplate.remove(query, HouseShop.class);
	}

	@Override
	public HouseShop findHouseShopPoById(String id) {
		return mongoTemplate.findById(id, HouseShop.class);
	}

	@Override
	public HouseShop findOne(String shopName) {
		Query query = new Query(where("shopName").is(shopName));
		return mongoTemplate.findOne(query, HouseShop.class);  
	}

}
