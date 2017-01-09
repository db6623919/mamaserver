package com.mama.server.main.controller.handler.main;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.UserCardPo;
import com.mama.server.main.dao.model.UserInfoPo;

@Component
public class MMWalletLoginHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			String mmWalletId = (String) param.get("mmWalletId");
			String phone = (String) param.get("phone");
			String nickName = (String) param.get("nickName");

			if (StringUtils.isBlank(mmWalletId)) {
				genErrOutputMapWithCode("param error, mmWalletId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (StringUtils.isBlank(phone)) {
				genErrOutputMapWithCode("param error, phone required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			/******************* 检查妈妈资本钱包帐号状态 **********************/
			UserInfoPo userQueryParam = new UserInfoPo();
			userQueryParam.setMmWalletId(mmWalletId);
			userQueryParam = mainService.getUserinfoByAllParam(userQueryParam);
			if (userQueryParam != null) { // 已绑定妈妈钱包帐号， 直接返回
				dataMap.put("uid", userQueryParam.getUid());
				genSuccOutputMap();
				return outputMap;
			}
			userQueryParam = new UserInfoPo();
			userQueryParam.setPhone(phone);
			userQueryParam = mainService.getUserinfoByAllParam(userQueryParam);
			if (userQueryParam != null) { // 已存在妈妈钱包帐号手机号，则直接绑定妈妈钱包ID
				UserInfoPo userUpdateParams = new UserInfoPo();
				String uid = userQueryParam.getUid();
				userUpdateParams.setUid(uid);
				userUpdateParams.setMmWalletId(mmWalletId);
				int code = mainService.updateUserInfo(userUpdateParams);
				if (code != 0) {// 更新失败，返回错误
					genErrOutputMapWithCode("register failed", ReturnCode.REGISTER_FAILED);
					return outputMap;
				}
				dataMap.put("uid", uid);
				genSuccOutputMap();
				return outputMap;
			}

			/******** 系统不存在妈妈钱包ID和妈妈钱包手机号，则直接创建新帐号，并且绑定妈妈钱包的ID **********/
			if (StringUtils.isBlank(nickName)) {
				nickName = phone;
			}
			UserInfoPo userInfo = new UserInfoPo();
			userInfo.setUid(mainService.getTimeToMd5());
			userInfo.setPhone(phone);
			userInfo.setName(nickName);
			userInfo.setNickName(nickName);
			userInfo.setMmWalletId(mmWalletId);

			userInfo.setIdCard("");
			userInfo.setType(1);
			userInfo.setIcon("");
			userInfo.setEmail("");
			userInfo.setRemoved(0);
			userInfo.setPasswordStamp(String.valueOf(new Date().getTime()));
			userInfo.setPassword(String.valueOf(new Date().getTime()));
			if (mainService.insertUserInfo(userInfo) == -1) {
				genErrOutputMapWithCode("register failed", ReturnCode.REGISTER_FAILED);
				return outputMap;
			}

			UserCardPo userCardPo = new UserCardPo();
			List<String> cardStr = mainService.genUserCard();
			userCardPo.setCardId(cardStr.get(0));
			userCardPo.setCardPassword(cardStr.get(1));
			userCardPo.setUid(userInfo.getUid());
			userCardPo.setAvailAmt(0);
			userCardPo.setFreezeAmt(0);
			userCardPo.setChannel(0);
			userCardPo.setStatus(0);
			userCardPo.setTotalRechargeAmt(0);
			userCardPo.setTotalRewardAmt(0);
			userCardPo.setOperTime(mainService.getCurrentDatetime());
			userCardPo.setLevel(1);
			userCardPo.setType(1);
			userCardPo.setChannel(ConstValue.USER_WEB);

			if (mainService.insertUserCard(userCardPo) != 0) {
				genErrOutputMapWithCode("register failed", ReturnCode.GENERATE_USER_CARD_FAILED);
				return outputMap;
			}

			dataMap.put("uid", userInfo.getUid());
			genSuccOutputMap();
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;

	}
}
