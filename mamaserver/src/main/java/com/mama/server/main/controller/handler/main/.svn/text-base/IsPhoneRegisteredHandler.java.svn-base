package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Log;

@Component
public class IsPhoneRegisteredHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("phone") == null) {
                genErrOutputMapWithCode("param error, phone required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String phone = (String)param.get("phone");
            
            UserInfoPo userInfo = new UserInfoPo();
            userInfo.setPhone(phone);
            userInfo = mainService.getUserinfoByAllParam(userInfo);
            
            if (userInfo != null) {
                dataMap.put("registered", 1);
                FlowPo flow = new FlowPo();
                flow.setUid(userInfo.getUid());
                flow.setType(ConstValue.FLOW_RECHARGE);
                List<FlowPo> flowList = mainService.getFlowByAllParam(flow);
                if (flowList != null && flowList.size() > 0) {
                    dataMap.put("recharged", 1);
                } else {
                    dataMap.put("recharged", 0);
                }
                if (userInfo.getIdCard() != null && userInfo.getIdCard().trim().length() != 0) {
                    dataMap.put("verified", 1);
                } else {
                    dataMap.put("verified", 0);
                }
            } else {
                dataMap.put("registered", 0);
                dataMap.put("recharged", 0);
                dataMap.put("verified", 0);
            }
            
            genSuccOutputMap();
        } catch (Exception e) {
            e.printStackTrace();
            Log.SERVICE.error(e.getLocalizedMessage());
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
