package com.mama.server.main.controller.handler.main.n99;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.n99.HouseCardShareVo;
import com.mama.server.main.service.n99.HouseCardService;

/** 根据订单号、房券号分享 */
@Component
public class GiveHouseCardHandler extends BaseHandler {

	private static final Logger logger = LoggerFactory.getLogger(GiveHouseCardHandler.class);
	
	@Autowired
	private HouseCardService houseCardService;

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run GiveHouseCardHandler.");
		
		try {
			String uid = (String) param.get("uid");// 会员id
			String phoneNo = (String)param.get("phoneNo");//购买人电话号码
			String cardNo = (String)param.get("cardNo");//房券编码
			String receivePhone = (String)param.get("receivePhone");
			String orderId = (String)param.get("orderId");//订单编号
			String giveType = (String)param.get("giveType");//分享方式：1、订单；2、单张房券；
			Map<String, Object> resultMap = new HashMap<String, Object>();
			HouseCardShareVo houseCardShareVo = null;
			if("1".equals(giveType)) {//根据订单号分享房券
				String cardNumStr = (String)param.get("cardNum");
				int cardNum = Integer.valueOf(cardNumStr);
				resultMap = houseCardService.giveHouseCardByOrderId(uid, phoneNo, orderId, cardNum);
				houseCardShareVo = (HouseCardShareVo)resultMap.get("houseCardShareVo");
			} else if("2".equals(giveType)) {//单张房券分享
				resultMap = houseCardService.giveHouseCardByCardNo(uid, cardNo, receivePhone);
				houseCardShareVo = (HouseCardShareVo)resultMap.get("houseCardShareVo");
			} else {
				resultMap.put("code", "-1");
				resultMap.put("msg", "非法请求，分享失败！");
			}
			
			if("0".equals(resultMap.get("code"))) {
				dataMap.put("sharePatchNo", houseCardShareVo.getSharePatchNo());
				dataMap.put("shareTitle", houseCardShareVo.getShareTitle());
				dataMap.put("shareUrl", houseCardShareVo.getShareUrl());
				dataMap.put("shareSubTitle", houseCardShareVo.getShareSubTitle());
				dataMap.put("shareImgURL", houseCardShareVo.getShareImgURL());
				genSuccOutputMap();
			} else {
				genErrOutputMap("" + resultMap.get("msg"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GiveHouseCardHandler interface error",e);
			genErrOutputMap("分享、赠送房券过程中出现异常");
		}
		return outputMap;
	}

}
