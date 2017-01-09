package com.mama.server.main.controller.handler.main;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.util.DateUtils;

@Component
public class CheckPhoneHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
    	List<SmsPo> list = null;
    	SmsPo sp = new SmsPo();
        try {
            if (param.get("phone") == null || "".equals((String)param.get("phone"))) {
                genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            String phone=(String)param.get("phone");
            sp.setPhone(phone);
            sp.setOperTime(DateUtils.getCurrDate("yyyy-MM-dd"));
            list  = mainService.getSmsByAllParam(sp);
            dataMap.put("isOutLimitCount", 0);
        	dataMap.put("isOutDifTime", 0);
            if(list.size() == 0){
            	dataMap.put("isOutLimitCount", 0);
            	dataMap.put("isOutDifTime", 0);
            	genSuccOutputMap();
            	return outputMap;
            }
            if(list.size()>=3){
            	dataMap.put("isOutLimitCount", 1);
            	dataMap.put("isOutDifTime", 0);
            	genSuccOutputMap();
            	return outputMap;
            }
            if(DateUtils.getTimeDiff(list.get(0).getOperTime(), DateUtils.getCurrDateTime()) < 60){
            	dataMap.put("isOutLimitCount", 0);
            	dataMap.put("isOutDifTime", 1);
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
