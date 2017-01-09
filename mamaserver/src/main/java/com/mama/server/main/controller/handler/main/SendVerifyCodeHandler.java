package com.mama.server.main.controller.handler.main;

import java.util.HashMap;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.util.Log;
import com.mama.server.util.SmsUtil;

import org.springframework.stereotype.Component;

@Component
public class SendVerifyCodeHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("type") == null) {
                genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("phone") == null) {
                genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            int type = (Integer)param.get("type");
            String uid = "";
            if (type != ConstValue.SMS_REGISTER && type != ConstValue.SMS_UPDATE_PASSWORD) {
                if (param.get("uid") != null) {
                    uid = (String)param.get("uid");
                }
            }
            String phone = (String)param.get("phone");
            
            if (type != ConstValue.SMS_REGISTER && type != ConstValue.SMS_UPDATE_PASSWORD && 
                    type != ConstValue.SMS_ORDER && type != ConstValue.SMS_UPDATE_EMAIL
                    && type != ConstValue.SMS_HOTEL_COUPON_GIVE) {
                genErrOutputMap("invalid sms type");
                return outputMap;
            }
            
            String verifyCode = mainService.genVerifyCode(type);
            Log.SERVICE.debug("verifyCode:" + verifyCode);
            SmsPo sms = new SmsPo();
            sms.setType(type);
            sms.setContent(verifyCode);
            sms.setPhone(phone);
            sms.setStatus(0);
            sms.setUsed(0);
            sms.setUid(uid);
            if (mainService.insSms(sms) != 0) {
                genErrOutputMap("fail to send sms");
                return outputMap;
            }
            
            String msg = null;
            if (type == ConstValue.SMS_REGISTER) {
                msg = SmsUtil.genRegisterSms(verifyCode);
            } else if (type == ConstValue.SMS_UPDATE_PASSWORD) {
                msg = SmsUtil.genUpdatePwdSms(verifyCode);
            } else if (type == ConstValue.SMS_UPDATE_EMAIL) {
                msg = SmsUtil.genUpdateEmailSms(verifyCode);
            }else if(type == ConstValue.SMS_HOTEL_COUPON_GIVE){
            	msg = SmsUtil.genChangeFriendSms(verifyCode);
            }
            boolean res = SmsUtil.sendSms(phone, msg); // 发送短信
            if (!res) {
                genErrOutputMap("failed to send sms"); // 发送短信失败
            }
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
