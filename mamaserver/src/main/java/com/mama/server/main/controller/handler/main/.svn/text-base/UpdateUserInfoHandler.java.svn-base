package com.mama.server.main.controller.handler.main;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.UserInfoPo;

@Component
public class UpdateUserInfoHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String uid = (String)param.get("uid");
            String nickName = null;
            String email = null;
            
            if (param.get("nickName") != null) {
                nickName = (String)param.get("nickName");
            }
            if (param.get("email") != null) {
                email = (String)param.get("email");
            }
            
            UserInfoPo userInfo = new UserInfoPo();
            userInfo.setUid(uid);
            userInfo = mainService.getUserinfoByAllParam(userInfo);
            if (userInfo == null) {
                genErrOutputMapWithCode("uid not exists", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            userInfo.setEmail(email);
            userInfo.setNickName(nickName);
            if (mainService.updateUserInfo(userInfo) != 0) {
                genErrOutputMapWithCode("update user info failed", ReturnCode.UPDATE_USER_INFO_ERROR);
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
