package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CollectPo;
import com.mama.server.main.dao.model.OpenIdInfoPo;
import com.mysql.jdbc.StringUtils;
import com.netfinworks.common.lang.StringUtil;

@Component
public class GetOpenIdPhoneHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String phone = "";
        	if(null != param.get("phone")){
        		phone = (String)param.get("phone");
        	}
        	String openId = "";
        	if(null != param.get("openId")){
        		openId = (String)param.get("openId");
        	}
            OpenIdInfoPo openIdInfoPo = new OpenIdInfoPo();
            if(StringUtil.isNotBlank(phone))
            	openIdInfoPo.setPhone(phone);
            if(StringUtil.isNotBlank(openId))
            	openIdInfoPo.setOpenId(openId);
            openIdInfoPo =mainService.getOpenIdInfoPoByAllParam(openIdInfoPo);
            if(null != openIdInfoPo){
            	if(StringUtil.isNotBlank(openIdInfoPo.getPhone()))
                	dataMap.put("phone", openIdInfoPo.getPhone());
                if(StringUtil.isNotBlank(openIdInfoPo.getOpenId()))
                	dataMap.put("openId", openIdInfoPo.getOpenId());
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
