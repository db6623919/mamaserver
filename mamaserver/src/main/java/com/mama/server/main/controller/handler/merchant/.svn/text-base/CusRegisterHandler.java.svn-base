package com.mama.server.main.controller.handler.merchant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CustomerUserPo;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.main.dao.model.UserInfoPo;
import com.mama.server.util.Log;

@Component
public class CusRegisterHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String user = param.get("user").toString();
        	String password = param.get("password").toString();
        	String level = param.get("level").toString();
        	String houseid = param.get("houseid").toString();
        	String bldid = param.get("bldid").toString();
        	String devid = param.get("devid").toString();
        	
            if (param.get("user") == null || param.get("password") == null || param.get("level") == null|| param.get("houseid") == null|| param.get("bldid") == null|| param.get("devid") == null) {
            	genErrOutputMapWithCode("param is empty : user("+user+"),password("+password+"),level("+level+"),houseid("+houseid+"),bldid("+bldid+"),devid("+devid+") ", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            CustomerUserPo cup = new CustomerUserPo();
            cup.setUser(user);
            cup.setPassword(mainService.encodePassword(password));
            cup.setLevel(Integer.valueOf(level));
            cup.setHouseId(Integer.valueOf(houseid));
            cup.setBldId(Integer.valueOf(bldid));
            cup.setDevId(Integer.valueOf(devid));

            int uid = mainService.cusInsertUser(cup);
            if ( uid == -1) {
            	Log.SERVICE.error("[CusRegisterHandler]uid = -1");
                genErrOutputMapWithCode("register failed", ReturnCode.REGISTER_FAILED);
                return outputMap;
            }
            
            dataMap.put("uid", uid);
            genSuccOutputMap();
        } catch (Exception e) {
        	Log.SERVICE.error("[CusRegisterHandler]Exception",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
