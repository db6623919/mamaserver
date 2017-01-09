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
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.service.n99.HouseCardService;
import com.mama.server.util.constants.BusinessConstant;
import com.mongodb.util.JSON;

@Component
public class HouseCardDetailHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(HouseCardDetailHandler.class);
	
	@Autowired
	private HouseCardService houseCardService;

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run HouseCardDetailHandler.");
		
		try {
			String cardNo = (String)param.get("cardNo");//房券编码
			String cardId = (String)param.get("cardId");//房券主键id
			String phone = (String)param.get("phone");//登录人手机号码
			
			HouseCardPo houseCardPo = null; 
			if(cardId != null && cardId.trim().length() > 0) {
				houseCardPo = houseCardService.queryHouseCardDetailById(cardId);
			} else {
				houseCardPo = houseCardService.queryHouseCardDetail(cardNo);
			}
			
			String resultCardNo = houseCardPo.getCardNo();
			int shareStatus = houseCardPo.getShareStatus();
			/** 已领取房券 */
			if(houseCardPo.getReceivePhone() != null && houseCardPo.getReceivePhone().trim().length() > 0) {
				shareStatus = 2;
			}
			/** 订单列表进去的详情被领取的打* */
			if(cardId != null && cardId.trim().length() > 0 && shareStatus == 2) {
				resultCardNo = "*****";
			}
			String cardType = "-1";
			if(houseCardPo.getCardPrice() == 99.0) {
				cardType = "0";//畅玩版
			} else if(houseCardPo.getCardPrice() == 299.0) {
				cardType = "1";//尊享版
			}
			dataMap.put("cardNo", resultCardNo);
			dataMap.put("totalRoomNum", 1);
			dataMap.put("endDate", houseCardPo.getEndDate());
			dataMap.put("useStatus", houseCardPo.getUseStatus());//0:未使用
			dataMap.put("shareStatus", shareStatus);
			dataMap.put("cardType", cardType);
			
			//获取房源名称
//			HousePo hosue = new HousePo();
//			hosue.setHouseId(houseCardPo.getHouseId());
//			List<HousePo> houseList = mainService.getHouseByAllParam(hosue);
//			Map<String,Object> map = (Map<String, Object>) JSON.parse(houseList.get(0).getSummaryInfo());
//			dataMap.put("houseName", (String)map.get("houseName"));
			dataMap.put("houseName", BusinessConstant.N99_SHARE_DETAIL_TITLE);
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("HouseCardDetailHandler interface error",e);
			genErrOutputMap("查询房券详情过程中出现异常");
		}
		return outputMap;
	}
}
