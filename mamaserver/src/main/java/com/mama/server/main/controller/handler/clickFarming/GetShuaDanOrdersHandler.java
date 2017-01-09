package com.mama.server.main.controller.handler.clickFarming;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.OrderPayDO;
import com.mama.server.main.dao.model.clickfarming.ShuaDanOrderPo;
import com.mama.server.main.service.clickfarming.IShopOwnerService;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/** 获取店铺刷单订单列表 */
@Component
public class GetShuaDanOrdersHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(GetShuaDanOrdersHandler.class);

	@Autowired
    protected IShopOwnerService iShopOwnerService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to get orders, param is {}.", param.toString());
		try {
			String type = (String) param.get("type");// 订单类型：0：未完成 1：已完成
			String mobile = (String) param.get("mobile");// 手机号码

			int pageIndex = (Integer)param.get("pageIndex");// 当前页数
			int pageSize = (Integer)param.get("pageSize");// 每页记录数
			List<Integer> statusList = new ArrayList<Integer>();
			if ("0".equals(type)) {
				statusList.add(9);
			} else {
				statusList.add(11);
			}

			Page<ShuaDanOrderPo> page = iShopOwnerService
					.getShuaDanShopOrderList(pageIndex, pageSize, mobile, statusList);
			Map<String, Object> pager = new HashMap<String, Object>();
			pager.put("pageSize", pageSize);
			pager.put("pageIndex", pageIndex);
			pager.put("itemCount", page.getTotal());
			
			List<ShuaDanOrderPo> orders = page.getResult();//订单列表
			/** 处理已完成订单支付时间 */
			if(orders != null && orders.size() > 0 && "1".equals(type)) {
				for(ShuaDanOrderPo po : orders) {
					String orderId = po.getOrderId();
					List<OrderPayDO> payOrderList = mainService.queryPayOrderListByOrderId(orderId);
					if(payOrderList != null && payOrderList.size() > 0) {
						String payTime = po.getPayTime();
						Date createTime = null;
						boolean paySuccessFlag = false;//用于处理历史数据
						for(OrderPayDO orderPayDO : payOrderList) {
							if("PAY_SUCCESS".equals(orderPayDO.getStatus())) {
								paySuccessFlag = true;
								Date payDate = orderPayDO.getCreateTime();
								payTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(payDate);
								break;
							}
							createTime = orderPayDO.getCreateTime();
						}
						if(!paySuccessFlag && createTime != null) {
							payTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
						}
						po.setPayTime(payTime);
					}
				}
			}
			
			dataMap.put("orders", orders);
			dataMap.put("pager", pager);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("GetShuaDanOrdersHandler interface error", e);
			genErrOutputMap("查询客栈的刷单列表过程中出现异常");
		}
		return outputMap;
	}
}