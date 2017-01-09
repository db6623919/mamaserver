package com.mama.server.main.controller.handler.main.n99;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.service.n99.HouseCardService;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/**
 * 房券列表
 * */
@Component
public class HouseCardListHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(HouseCardListHandler.class);
	
	@Autowired
	private HouseCardService houseCardService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run HouseCardOrderListHandler.");
		
		try {
			String uid = (String) param.get("uid");// 会员id
			String phone = (String)param.get("phone");//登录人手机号码
			String orderId = (String)param.get("orderId");//订单id
			int pageIndex = (Integer)param.get("pageIndex");// 当前页数
			int pageSize = (Integer)param.get("pageSize");// 每页记录数
			Page<HouseCardPo> page = houseCardService.queryHouseCardList(pageIndex, pageSize, orderId, uid);
			Map<String, Object> pager = new HashMap<String, Object>();
			pager.put("pageSize", pageSize);
			pager.put("pageIndex", pageIndex);
			pager.put("itemCount", page.getTotal());
			
			List<HouseCardPo> houseCardList = page.getResult();//订单列表
			String houseName = "妈妈美宿 “任我行” 系列"; 
			for(HouseCardPo po : houseCardList) {
				int shareStatus = po.getShareStatus();
				if(po.getReceivePhone() != null && po.getReceivePhone().trim().length() > 0) {
//					String receivePhone = po.getReceivePhone().trim();
//					if(!receivePhone.equals(phone)) {//如果是自己的即可以转赠
						shareStatus = 2;
						po.setCardNo("*****");
//					}
				}
				
				po.setTotalRoomNum(1);
				
				//获取房源名称
//				if(houseName == null || houseName.trim().length() < 1) {
//					houseName = houseCardService.queryHouseNameById(po.getHouseId());
//				}
				if(po.getCardPrice() == 99.0) {
					po.setCardType("0");
				} else if(po.getCardPrice() == 299.0) {
					po.setCardType("1");
				} else {
					po.setCardType("-1");
				}
				po.setHouseName(houseName);
				po.setShareStatus(shareStatus);
				String h5DetailUrl = PropertiesUtils.getMmsfWapWebUrl() + "/coupon/detail.htm?cardNo=" + po.getCardNo();
				po.setH5DetailUrl(h5DetailUrl);
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