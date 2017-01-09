package com.mama.server.main.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.server.main.dao.model.mongodb.HouseShop;
import com.mama.server.main.dao.mongodb.IHouseShopDao;
import com.mama.server.main.service.IHouseShopService;

@Service
public class HouseShopServiceImpl implements IHouseShopService {

	@Autowired
	private IHouseShopDao houseShopDao;

	@Override
	public void addHouseShopPo(HouseShop houseShop) {
		houseShopDao.addHouseShopPo(houseShop);
	}

	@Override
	public void delHouseShopPo(String id) {
		houseShopDao.deleteHouseShopPoById(id);
	}

	@Override
	public void updateHouseShopPo(HouseShop houseShop) {
		houseShopDao.updateHouseShopPo(houseShop);
	}

	@Override
	public HouseShop searchHouseShopPoById(String id) {
		return houseShopDao.findHouseShopPoById(id);
	}

	@Override
	public HouseShop findOne(String shopName) {
        return houseShopDao.findOne(shopName);
	}

}