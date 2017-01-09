package com.mama.server.main.service.clickfarming;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.clickfarming.CFShopOrderPo;
import com.mama.server.main.dao.model.clickfarming.ShuaDanOrderPo;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/**
 * 刷单店铺老板相关功能服务接口
 * @author lihaifeng
 */
public interface IShopOwnerService {
	/** 根据手机号码校验是否为客栈老板
	 *  @param mobile 手机号码
	 *  */
	public boolean isShopOwnerByPhone(String mobile);
	
	/** 客栈刷单金额查询 */
	Page<CFShopOrderPo> getShopOrderList(CFShopOrderPo cfShopOrderPo);
	
	/** 根据手机号码、支付状态分页查询订单列表 */
	Page<ShuaDanOrderPo> getShuaDanShopOrderList(int pageNum, int pageSize, 
			String mobile, List<Integer> statusList);
	
	/**刷单订单明细 */
	Page<OrderPo> getCfOrderList(Map<String,Object> map);
	
	/** 查询店铺下所有订单*/
	List<OrderPo> getCfOrderListByShopId(Map<String,Object> map);
	
	/** 店铺对账明细*/
	Page<OrderPo> getShopBillList(Map<String,Object> map);
	
	/** 查询店铺刷单明细*/
	List<OrderPo> getCfOrderListByShop(Map<String,Object> map);
	/** 统计所有店铺的金额数据*/
	List<OrderPo> getTotalBill(Map<String,Object> map);
}
