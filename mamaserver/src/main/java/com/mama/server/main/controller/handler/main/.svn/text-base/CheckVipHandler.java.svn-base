package com.mama.server.main.controller.handler.main;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class CheckVipHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("name") == null) {
                genErrOutputMapWithCode("param error, name required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("phone") == null) {
                genErrOutputMapWithCode("param error, phone required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("idCard") == null) {
                genErrOutputMapWithCode("param error, idCard required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String name = (String)param.get("name");
            String phone = (String)param.get("phone");
            String idCard = (String)param.get("idCard");
            
            UserInfoPo userInfo = new UserInfoPo();
            userInfo.setName(name);
            userInfo.setPhone(phone);
            userInfo.setIdCard(idCard);
           
            //userInfo = mainService.getUserinfoByAllParam(userInfo);
            //if (userInfo == null) {
                //genErrOutputMapWithCode("vip not exists", ReturnCode.VIP_NOT_EXISTS_ERROR);
            //} else {
                genSuccOutputMap();
            //}
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
