package com.mama.server.main.service.imp.clickfarming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.server.main.dao.ShopOwnerDao;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.clickfarming.CFShopOrderPo;
import com.mama.server.main.dao.model.clickfarming.ShuaDanOrderPo;
import com.mama.server.main.service.clickfarming.IShopOwnerService;
import com.mama.server.util.dao.PaginationInterceptor;
import com.mama.server.util.dao.PaginationInterceptor.Page;

@Service
public class ShopOwnerServiceImpl implements IShopOwnerService {
	@Autowired
	private ShopOwnerDao shopOwnerDao;
	
	@Override
	public boolean isShopOwnerByPhone(String mobile) {
		int shopNum = shopOwnerDao.queryShopNumByPhone(mobile);
		if(shopNum > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Page<CFShopOrderPo> getShopOrderList(CFShopOrderPo cfShopOrder) {
		PaginationInterceptor.startPage(cfShopOrder.getCurrent_page(), cfShopOrder.getPage_size());
		shopOwnerDao.getShopOrderList(cfShopOrder);
		Page<CFShopOrderPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	/** 根据手机号码、支付状态分页查询订单列表 */
	public Page<ShuaDanOrderPo> getShuaDanShopOrderList(int pageNum, int pageSize, String mobile, List<Integer> statusList) {
		PaginationInterceptor.startPage(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bossPhone", mobile);
		map.put("orderStatusList", statusList);
		shopOwnerDao.getShuaDanShopOrderList(map);
		Page<ShuaDanOrderPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	public Page<OrderPo> getCfOrderList(Map<String,Object> map) {
		PaginationInterceptor.startPage(Integer.parseInt(map.get("currentPage").toString()), Integer.parseInt(map.get("pageSize").toString()));
		shopOwnerDao.getCfOrderList(map);
		Page<OrderPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	public List<OrderPo> getCfOrderListByShop(Map<String,Object> map) {
		return shopOwnerDao.getCfOrderList(map);
	}

	@Override
	public List<OrderPo> getCfOrderListByShopId(Map<String,Object> map) {
		return shopOwnerDao.getCfOrderList(map);
	}
	
	@Override
	public Page<OrderPo> getShopBillList(Map<String,Object> map) {
		PaginationInterceptor.startPage(Integer.parseInt(map.get("currentPage").toString()), Integer.parseInt(map.get("pageSize").toString()));
		shopOwnerDao.getShopBillList(map);
		Page<OrderPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	public List<OrderPo> getTotalBill(Map<String,Object> map) {
		return  shopOwnerDao.getTotalBill(map);
	}
}
