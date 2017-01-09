package com.mama.server.main.controller.handler.main.n99;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.n99.HouseCardOrderPo;
import com.mama.server.main.service.n99.HouseCardService;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/** 
 * 房券订单列表相关处理类
 *  */
@Component
public class HouseCardOrderListHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(HouseCardOrderListHandler.class);
	
	@Autowired
	private HouseCardService houseCardService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run HouseCardOrderListHandler.");
		
		try {
			String uid = (String) param.get("uid");// 会员id
			String phoneNo = (String)param.get("phoneNo");//登录人电话号码
			
			int pageIndex = (Integer)param.get("pageIndex");// 当前页数
			int pageSize = (Integer)param.get("pageSize");// 每页记录数
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(11);//支付成功
			statusList.add(23);//已退款
			Page<HouseCardOrderPo> page = houseCardService
					.queryHouseCardOrderList(pageIndex, pageSize, uid, statusList);
			Map<String, Object> pager = new HashMap<String, Object>();
			pager.put("pageSize", pageSize);
			pager.put("pageIndex", pageIndex);
			pager.put("itemCount", page.getTotal());
			
			List<HouseCardOrderPo> houseCardList = page.getResult();//订单列表
			/** 有效期处理 */
			for(HouseCardOrderPo po : houseCardList) {
//				Map<String,Object> summaryInfo = (Map<String, Object>) JSON.parse(po.getHouseName());
//				po.setHouseName((String)summaryInfo.get("houseName"));
				po.setLeftNum(houseCardService.queryHouseCardLeftNum(uid, po.getOrderId(), phoneNo));
				po.setHouseName("妈妈美宿 “任我行” 系列");
			}
			dataMap.put("houseCardList", houseCardList);
			dataMap.put("pager", pager);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("HouseCardOrderListHandler interface error",e);
			genErrOutputMap("查询客栈的刷单列表过程中出现异常");
		}
		return outputMap;
	}
}
