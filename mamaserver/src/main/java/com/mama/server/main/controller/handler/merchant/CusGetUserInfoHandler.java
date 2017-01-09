package com.mama.server.main.controller.handler.merchant;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CustomerUserPo;
import com.mama.server.util.Log;

@Component
public class CusGetUserInfoHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String uid = param.get("uid").toString();
        	
            if (uid == null) {
            	genErrOutputMapWithCode("param is empty : uid("+uid+")", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            CustomerUserPo cup = new CustomerUserPo();
            cup.setUid(Integer.valueOf(uid));

            List<CustomerUserPo> listCup = mainService.cusGetUserinfo(cup);
            if ( listCup.size() == 0) {
            	Log.SERVICE.error("[CusGetUserInfoHandler]Invaild uid!");
                genErrOutputMapWithCode("Invaild uid!", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            CustomerUserPo cupUser = listCup.get(0);
            
            dataMap.put("uid", uid);
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
