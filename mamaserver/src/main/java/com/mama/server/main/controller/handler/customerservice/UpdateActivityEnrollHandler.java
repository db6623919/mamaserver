package com.mama.server.main.controller.handler.customerservice;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ActivityEnrollPo;
import com.mama.server.util.Log;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.SmsUtil;

@Component
public class UpdateActivityEnrollHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("id") == null || param.get("id") == "") {
            	Log.SERVICE.error("[UpdateActivityEnrollHandler]param is empty : id("+param.get("id")+")");
            	genErrOutputMapWithCode("param is empty : id("+param.get("id")+")", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            String id = param.get("id").toString();
            ActivityEnrollPo activityEnroll = new ActivityEnrollPo();
            activityEnroll.setId(Integer.parseInt(id));
            
    		if (mainService.updateActivityEnrollPo(activityEnroll) != 0) {
    			genErrOutputMapWithCode("fail to update ActivityEnroll", ReturnCode.UPDATE_ORDER_ERROR);
    			return outputMap;
    		}
            String phone = param.get("phone").toString();
//    		String phone = "18620976967";
    		String msg_mama = SmsUtil.genActivityEnroll(phone);
    		SmsUtil.sendSms(PropertiesUtils.getMmManagerPhone(), msg_mama);
    		
            genSuccOutputMap();
            
        } catch (Exception e) {
        	Log.SERVICE.error("[UpdateOrderHandler]Exception!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}