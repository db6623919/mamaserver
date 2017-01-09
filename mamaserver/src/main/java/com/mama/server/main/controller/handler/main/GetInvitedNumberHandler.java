package com.mama.server.main.controller.handler.main;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;

@Component
public class GetInvitedNumberHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
    	
    	String phone = (String) param.get("phone");
    	if (phone == null) {
            genErrOutputMapWithCode("param error, phone required", ReturnCode.PARAM_ERROR);
            return outputMap;
        }
    	
    	try {
    		int count = mainService.getInvitedNumberByPhone(phone);
    		dataMap.put("count", count);
    		genSuccOutputMap();
    	} catch (Exception e) {
            genErrOutputMap("interface error");
        }
    	
        return outputMap;
    }
}
