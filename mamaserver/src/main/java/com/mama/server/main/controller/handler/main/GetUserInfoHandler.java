package com.mama.server.main.controller.handler.main;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.UserInfoPo;

@Component
public class GetUserInfoHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			String uid = null;
			if (param.get("uid") != null) {
				uid = (String) param.get("uid");
			}

			String phone = null;
			if (param.get("phone") != null) {
				phone = (String) param.get("phone");
			}

			String openId = null;
			if (param.get("openId") != null) {
				openId = (String) param.get("openId");
			}

			if (uid == null && phone == null) {
				genErrOutputMapWithCode("param error, uid/phone required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			UserInfoPo userInfo = new UserInfoPo();
			if (uid != null) {
				userInfo.setUid(uid);
			}
			if (phone != null) {
				userInfo.setPhone(phone);
			}
			if (openId != null) {
				userInfo.setOpenId(openId);
			}

			userInfo = mainService.getUserinfoByAllParam(userInfo);
			if (userInfo == null) {
				genErrOutputMapWithCode("param error, user info not exists", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			dataMap.put("uid", userInfo.getUid());
			dataMap.put("name", userInfo.getName());
			dataMap.put("idCard", userInfo.getIdCard());
			dataMap.put("phone", userInfo.getPhone());
			dataMap.put("type", userInfo.getType());
			dataMap.put("icon", userInfo.getIcon());
			dataMap.put("nickName", userInfo.getNickName());
			dataMap.put("email", userInfo.getEmail());
			dataMap.put("openId", userInfo.getOpenId());
			dataMap.put("mmWalletId", userInfo.getMmWalletId());
			genSuccOutputMap();
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}
