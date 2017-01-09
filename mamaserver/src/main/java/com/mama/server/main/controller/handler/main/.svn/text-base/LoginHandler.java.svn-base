package com.mama.server.main.controller.handler.main;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.UserInfoPo;
import com.mama.server.main.service.MainService;
import com.mama.server.main.service.imp.MainServiceImp;
import com.mama.server.util.Log;

@Component
public class LoginHandler extends BaseHandler {

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
                genErrOutputMapWithCode("login failed, phone not found", ReturnCode.USER_NOT_FOUND_ERROR);
                return outputMap;
            }
            
            if (userInfo.getPassword().compareTo(mainService.encodePassword(password, userInfo.getPasswordStamp())) != 0) {
                genErrOutputMapWithCode("login failed, password incorrect", ReturnCode.LOGIN_FAILED);
                return outputMap;
            }
            
            dataMap.put("uid", userInfo.getUid());
            genSuccOutputMap();
        } catch (Exception e) {
            e.printStackTrace();
            Log.SERVICE.error(e.getLocalizedMessage());
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
