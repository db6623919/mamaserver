package com.mama.server.main.controller.handler.customerservice.n99;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.service.n99.HouseCardService;
import com.mama.server.util.SmsUtil;

/** 房券兑换 */
@Component
public class DoExchangeHouseCardfHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(DoExchangeHouseCardfHandler.class);
	
	@Autowired
    protected HouseCardService houseCardService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start run to DoExchangeHouseCardfHandler.");
		try {
			int id = (Integer)param.get("id");
			String exchangeName = (String)param.get("exchangeName"); //兑换人
			String exchangePhoneNo = (String)param.get("exchangePhoneNo"); //兑换联系方式
			String cardNo = (String)param.get("cardNo"); //房券编号
			HouseCardPo hosueCard = new HouseCardPo();
			hosueCard.setId(id);
			hosueCard.setExchangeName(exchangeName);
			hosueCard.setExchangePhoneNo(exchangePhoneNo);
			int result = houseCardService.exchangeHouseCard(hosueCard);
			if (result > 0) {
				
				String msg = "您已成功兑换编号为" + cardNo + "的房券1 张！请按时入住，祝你旅途愉快！客服400-9966-633    兑换人:" + exchangeName + "  兑换电话:" + exchangePhoneNo + "  !";
				//发送短信
				SmsUtil.sendSms(exchangePhoneNo, msg);
			}
			logger.info("end run to DoExchangeHouseCardfHandler.");
			genSuccOutputMap();
		} catch (Exception e) {
			outputMap.put("exception", e.getMessage());
			logger.error("getShopTicketListHandler is error.",e);
		}
		return outputMap;
	}
	
}
