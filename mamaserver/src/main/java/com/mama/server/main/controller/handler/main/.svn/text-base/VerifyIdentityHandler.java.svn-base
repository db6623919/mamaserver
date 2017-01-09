package com.mama.server.main.controller.handler.main;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.UserInfoPo;

@Component
public class VerifyIdentityHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("name") == null) {
                genErrOutputMapWithCode("param error, name required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("idCard") == null) {
                genErrOutputMapWithCode("param error, idCard required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            String uid = (String)param.get("uid");
            String name = (String)param.get("name");
            String idCard = (String)param.get("idCard");
            
            UserInfoPo userInfo = new UserInfoPo();
            userInfo.setUid(uid);
            userInfo = mainService.getUserinfoByAllParam(userInfo);
            if (userInfo == null) {
                genErrOutputMapWithCode("param error, invalid uid", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            userInfo.setName(name);
            userInfo.setIdCard(idCard);
            if (mainService.updateUserInfo(userInfo) != 0) {
                genErrOutputMapWithCode("fail to verify identity", ReturnCode.UPDATE_USER_INFO_ERROR);
                return outputMap;
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
