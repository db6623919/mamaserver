package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CollectPo;
import com.mama.server.main.dao.model.OpenIdInfoPo;

@Component
public class InsertOpenIdPhoneHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("phone") == null) {
                genErrOutputMapWithCode("param error, phone required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("openId") == null) {
                genErrOutputMapWithCode("param error, openId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String phone = (String)param.get("phone");
            String openId = (String)param.get("openId");
            OpenIdInfoPo openIdInfoPo = new OpenIdInfoPo();
            openIdInfoPo.setPhone(phone);
            OpenIdInfoPo openIdInfo =mainService.getOpenIdInfoPoByAllParam(openIdInfoPo);
            OpenIdInfoPo openIdInfoPo1 = new OpenIdInfoPo();
            openIdInfoPo1.setOpenId(openId);
            OpenIdInfoPo openIdInfo1 =mainService.getOpenIdInfoPoByAllParam(openIdInfoPo1);
            if(null == openIdInfo && null == openIdInfo1){
            	openIdInfoPo.setOpenId(openId);
            	if(mainService.insertOpen(openIdInfoPo) != 0){
            		genErrOutputMapWithCode("openIdinfo insert error", ReturnCode.PARAM_ERROR);
            	}
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
