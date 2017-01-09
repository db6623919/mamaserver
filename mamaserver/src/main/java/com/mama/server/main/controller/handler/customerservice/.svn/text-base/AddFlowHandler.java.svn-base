package com.mama.server.main.controller.handler.customerservice;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;

@Component
public class AddFlowHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("type") == null) {
                genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("amt") == null) {
                genErrOutputMapWithCode("param error, amt required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("showDetail") == null) {
                genErrOutputMapWithCode("param error, showDetail required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            String orderId = "";
            if (param.get("orderId") != null) {
                orderId = (String)param.get("orderId");
            }
            
            String uid = (String)param.get("uid");
            int type = (Integer)param.get("type");
            int amt = (Integer)param.get("amt");
            String showDetail = Json.format(param.get("showDetail"));
            String operTime = mainService.getCurrentDatetime();
            
            if (type != ConstValue.FLOW_FROZEN && type != ConstValue.FLOW_PAY && 
                    type != ConstValue.FLOW_RECHARGE && type != ConstValue.FLOW_REWARD) {
                genErrOutputMapWithCode("param error, invalid type", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            FlowPo flow = new FlowPo();
            flow.setType(type);
            flow.setUid(uid);
            flow.setAmt(amt);
            flow.setOperTime(operTime);
            flow.setShowDetail(showDetail);
            flow.setOrderId(orderId);
            if (mainService.insertFlow(flow) != 0) {
                genErrOutputMapWithCode("fail to insert flow", ReturnCode.ADD_FLOW_ERROR);
                return outputMap;
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
