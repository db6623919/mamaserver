package com.mama.server.main.controller.handler.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.Version;
import com.mama.server.main.dao.vo.OrderPriceInfoVo;

@Component
public class GetVersionByParHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			Integer versionType = (Integer) param.get("versionType"); // 版本类型
        	Version version = new Version();
            version = mainService.getVersionByPar(versionType);
			dataMap.put("version", version.getVersionNo());
			genSuccOutputMap();
		} catch (Exception e) {
			log.error("interface error", e);
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
