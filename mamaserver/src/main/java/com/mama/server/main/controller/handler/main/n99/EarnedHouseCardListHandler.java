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
 * 领取的房券列表
 * */
@Component
public class EarnedHouseCardListHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(EarnedHouseCardListHandler.class);
	
	@Autowired
	private HouseCardService houseCardService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run EarnedHouseCardListHandler.");
		
		try {
			String phoneNo = (String) param.get("phoneNo");// 当前登录会员手机号码
			int pageIndex = (Integer)param.get("pageIndex");// 当前页数
			int pageSize = (Integer)param.get("pageSize");// 每页记录数
			Page<HouseCardPo> page = houseCardService.queryEarnedHouseCardList(pageIndex, pageSize, phoneNo);
			Map<String, Object> pager = new HashMap<String, Object>();
			pager.put("pageSize", pageSize);
			pager.put("pageIndex", pageIndex);
			pager.put("itemCount", page.getTotal());
			
			List<HouseCardPo> houseCardList = page.getResult();//订单列表
			
			/** 有效期处理 */
			for(HouseCardPo po : houseCardList) {
				if(po.getCardPrice() == 99.0) {
					po.setCardType("0");//99:畅玩版
				} else if(po.getCardPrice() == 299.0) {
					po.setCardType("1");//299:尊享版
				} else {
					po.setCardType("-1");
				}
				/** 如果是自己领取的则 */
				po.setTotalRoomNum(1);
				po.setHouseName("妈妈美宿 “任我行” 系列");
				String h5DetailUrl = PropertiesUtils.getMmsfWapWebUrl() + "/coupon/detail.htm?cardNo=" + po.getCardNo();
				po.setH5DetailUrl(h5DetailUrl);
			}
			dataMap.put("houseCardList", houseCardList);
			dataMap.put("pager", pager);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("EarnedHouseCardListHandler interface error",e);
			genErrOutputMap("查询客栈的刷单列表过程中出现异常");
		}
		return outputMap;
	}
}
