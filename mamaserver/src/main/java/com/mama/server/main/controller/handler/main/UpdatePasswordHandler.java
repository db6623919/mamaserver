package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.main.dao.model.UserInfoPo;

@Component
public class UpdatePasswordHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("phone") == null) {
                genErrOutputMapWithCode("param error, phone required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("password") == null) {
                genErrOutputMapWithCode("param error, password required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String phone = (String)param.get("phone");
            String password = (String)param.get("password");
            
            UserInfoPo userInfo = new UserInfoPo();
            userInfo.setPhone(phone);
            userInfo = mainService.getUserinfoByAllParam(userInfo);
            if (userInfo == null) {
                genErrOutputMapWithCode("user not exists", ReturnCode.USER_NOT_FOUND_ERROR);
                return outputMap;
            }
            userInfo.setPasswordStamp(String.valueOf(new Date().getTime()));
            userInfo.setPassword(mainService.encodePassword(password, userInfo.getPasswordStamp()));
            if (mainService.updateUserInfo(userInfo) != 0) {
                genErrOutputMapWithCode("update user info error", ReturnCode.UPDATE_USER_INFO_ERROR);
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
