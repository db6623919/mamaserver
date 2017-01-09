package com.mama.server.main.controller.handler.customerservice.n99;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.service.n99.HouseCardService;
import com.mama.server.util.Json;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/** 兑换券列表 */
@Component
public class GetShopTicketListHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(GetShopTicketListHandler.class);
	
	@Autowired
    protected HouseCardService houseCardService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start run to getShopTicketListHandler.");
		try {
			Page<HouseCardPo> page = houseCardService.houseCardList(param);
			//获取每个房源的名称
			List<HouseCardPo> houseCardList = page.getResult();
			for (int i = 0 ; i < houseCardList.size() ; i ++) {
				HousePo house = new HousePo();
				house.setHouseId(houseCardList.get(i).getHouseId());
				house.setHouseshop_id(houseCardList.get(i).getShopId());
				List<HousePo> houseList = mainService.getHouseByAllParam(house);
				String summaryInfo = houseList.get(0).getSummaryInfo();
				Map<String,Object> map = Json.parse(summaryInfo, Map.class);
				houseCardList.get(i).setHouseName((String)map.get("houseName"));
			}
			page.setResult(houseCardList);
			dataMap.put("page", page);
			logger.info("end run to getShopTicketListHandler.");
			genSuccOutputMap();
		} catch (Exception e) {
			outputMap.put("exception", e.getMessage());
			logger.error("getShopTicketListHandler is error.",e);
		}
		return outputMap;
	}
	
}
