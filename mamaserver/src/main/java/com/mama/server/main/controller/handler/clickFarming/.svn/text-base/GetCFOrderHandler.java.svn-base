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

/**
 * 刷单订单列表
 * @author whl
 *
 */
@Component
public class GetCFOrderHandler extends BaseHandler {

	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(GetCFOrderHandler.class);
	@Autowired
    protected IShopOwnerService iShopOwnerService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run GetCFOrderHandler.");
		try {
			Page<OrderPo> page = iShopOwnerService.getCfOrderList(param);
        	dataMap.put("page", page);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("GetCFOrderHandler interface error",e);
		}
		return outputMap;
	}

}
