package com.mama.server.main.controller.handler.merchant;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CustomerUserPo;
import com.mama.server.util.Log;

@Component
public class CusLoginHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String user = param.get("user").toString();
        	String password = param.get("password").toString();
        	
            if (user == null || password == null) {
            	genErrOutputMapWithCode("param is empty : user("+user+"),password("+password+")", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            CustomerUserPo cup = new CustomerUserPo();
            cup.setUser(user);
            cup.setPassword(mainService.encodePassword(password));

            List<CustomerUserPo> listCup = mainService.cusGetUserinfo(cup);
            if ( listCup.size() == 0) {
            	Log.SERVICE.error("[CusGetUserInfoHandler]Invaild uid!");
                genErrOutputMapWithCode("Login failed!", ReturnCode.LOGIN_FAILED);
                return outputMap;
            }
            
            CustomerUserPo cupUser = listCup.get(0);
            
            dataMap.put("uid", cupUser.getUid());
            dataMap.put("user", cupUser.getUser());
            dataMap.put("level", cupUser.getLevel());
            dataMap.put("houseId", cupUser.getHouseId());
            dataMap.put("bldId", cupUser.getBldId());
            dataMap.put("devId", cupUser.getDevId());
            genSuccOutputMap();
            
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
