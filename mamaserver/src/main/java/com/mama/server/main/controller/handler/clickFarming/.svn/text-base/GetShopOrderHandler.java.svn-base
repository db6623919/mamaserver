package com.mama.server.main.controller.handler.clickFarming;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.clickfarming.CFShopOrderPo;
import com.mama.server.main.service.clickfarming.IShopOwnerService;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/**
 * 客栈刷单金额统计
 * @author whl
 *
 */
@Component
public class GetShopOrderHandler extends BaseHandler {

	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(GetShopOrderHandler.class);
	@Autowired
    protected IShopOwnerService iShopOwnerService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run GetShopOrderHandler.");
		try {
			int currentPage = (Integer)param.get("currentPage");
        	int pageSize = (Integer)param.get("pageSize");
        	CFShopOrderPo cfShopOrder = new CFShopOrderPo();
        	//店铺名称
        	if (null != param.get("shopName")) {
        		cfShopOrder.setShopName((String)param.get("shopName"));
        	}
        	//老板姓名
			if (null != param.get("bossName")) {
				cfShopOrder.setBossName((String)param.get("bossName"));    		
			}
			//老板电话
			if (null != param.get("bossPhone")) {
				cfShopOrder.setBossPhone((String)param.get("bossPhone"));
			}
        	cfShopOrder.setCurrent_page(currentPage);
        	cfShopOrder.setPage_size(pageSize);
        	//店铺统计
        	Page<CFShopOrderPo> page = iShopOwnerService.getShopOrderList(cfShopOrder);
        	//所有店铺合计统计
        	List<OrderPo> shopOrder = iShopOwnerService.getTotalBill(null);
        	if (shopOrder.size() > 0) {
        		dataMap.put("order", shopOrder.get(0));
        	}
        	dataMap.put("page", page);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("GetShopOrderHandler interface error",e);
		}
		return outputMap;
	}

}
