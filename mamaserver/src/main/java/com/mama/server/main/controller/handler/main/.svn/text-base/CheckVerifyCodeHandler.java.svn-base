package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.SmsPo;

@Component
public class CheckVerifyCodeHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("type") == null) {
                genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("phone") == null) {
                genErrOutputMapWithCode("param error, phone required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("verifyCode") == null) {
                genErrOutputMapWithCode("param error, verifyCode required", ReturnCode.PHONE_VERIFY_FAILED);
                return outputMap;
            }
            
            int type = (Integer)param.get("type");
            String phone = (String)param.get("phone");
            String verifyCode = (String)param.get("verifyCode");
            
            SmsPo sms = new SmsPo();
            sms.setType(type);
            sms.setPhone(phone);
            sms.setContent(verifyCode);
            List<SmsPo> smsList = mainService.getSmsByAllParam(sms);
            
            if (smsList == null || smsList.size() == 0) {
                genErrOutputMapWithCode("check verify code failed", ReturnCode.CHECK_VERIFY_CODE_FAILED);
                return outputMap;
            }
            
            sms = smsList.get(0);
            if (sms.getUsed() == 1) {
                genErrOutputMapWithCode("verify code incorrect", ReturnCode.CHECK_VERIFY_CODE_FAILED);
                return outputMap;
            }
            sms.setUsed(1);
            if (mainService.updateSmsById(sms) != 0) {
                genErrOutputMapWithCode("check verify code failed", ReturnCode.UPDATE_VERIFY_CODE_ERROR);
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
