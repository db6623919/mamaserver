package com.mama.server.main.controller.handler.clickFarming;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.service.clickfarming.IShopOwnerService;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/** 店铺对账单列表 */
@Component
public class ShopBillListHandler extends BaseHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopBillListHandler.class);
	@Autowired
    protected IShopOwnerService iShopOwnerService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run ShopBillListHandler.");
		try {
			Page<OrderPo> page = iShopOwnerService.getShopBillList(param);
			dataMap.put("page", page);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("ShopBillListHandler:对账单列表接口错误：", e);
			outputMap.put("exception", e.getMessage());
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
	
}
