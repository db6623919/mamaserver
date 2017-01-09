package com.mama.server.main.controller.handler.clickFarming;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.service.clickfarming.IShopOwnerService;

/**
 * 店铺订单明细
 * @author whl
 *
 */
@Component
public class GetCFOrderByShopHandler extends BaseHandler {

	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(GetCFOrderByShopHandler.class);
	@Autowired
    protected IShopOwnerService iShopOwnerService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run GetCFOrderByShopHandler.");
		try {
			List<OrderPo> orderList = iShopOwnerService.getCfOrderListByShop(param);
        	dataMap.put("orderList", orderList);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("GetCFOrderByShopHandler interface error",e);
		}
		return outputMap;
	}

}
