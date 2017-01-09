package com.mama.server.main.controller.handler.merchant;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.service.clickfarming.IShopOwnerService;

/** 检测是否是客栈老板 */
@Component
public class ChekShopOwnerStatusHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(ChekShopOwnerStatusHandler.class);

	@Autowired
	private IShopOwnerService shopOwnerService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			String mobile = (String) param.get("mobile");
			if (mobile == null || mobile.trim().length() < 1) {
				genErrOutputMapWithCode("参数错误, mobile 不能为空",
						ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			String shopOwnerStatus = "0";
			if(shopOwnerService.isShopOwnerByPhone(mobile)) {
				shopOwnerStatus = "1";
			}
			dataMap.put("shopOwnerStatus", shopOwnerStatus);
			// 修改订单状态为待确认状态
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("接口异常", e);
			genErrOutputMap("接口异常");
		}
		return outputMap;
	}
}