package com.mama.server.main.controller.handler.customerservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.util.Log;

@Component
public class UpdateOrderHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String orderId 		= mainService.checkPostParam(param.get("orderId"));
        	String uid 			= mainService.checkPostParam(param.get("uid"));
        	String houseId 		= mainService.checkPostParam(param.get("houseId"));
        	String freezeAmt 	= mainService.checkPostParam(param.get("freezeAmt"));
        	String totalAmt 	= mainService.checkPostParam(param.get("totalAmt"));
        	String status 		= mainService.checkPostParam(param.get("status"));
        	String verifyCode 	= mainService.checkPostParam(param.get("verifyCode"));
        	String liveDetail 	= mainService.checkPostParam(param.get("liveDetail"));
        	String removed 		= mainService.checkPostParam(param.get("removed"));
        	
            if (orderId == null || orderId == "") {
            	Log.SERVICE.error("[UpdateOrderHandler]param is empty : orderId("+orderId+")");
            	genErrOutputMapWithCode("param is empty : orderId("+orderId+")", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            //insert order table
            OrderPo op = new OrderPo();
            op.setOrderId(orderId);
            if(uid != null){
            	op.setUid(uid);
            }
            if(houseId != null){
            	op.setHouseId(Integer.valueOf(houseId));
            }
            if(freezeAmt != null){
            	op.setFreezeAmt(Integer.valueOf(freezeAmt));
            }
            if(totalAmt != null){
            	op.setTotalAmt(Integer.valueOf(totalAmt));
            }
            if(status != null){
            	op.setStatus(Integer.valueOf(status));
            }
            if(verifyCode != null){
            	op.setVerifyCode(verifyCode);
            }
            if(liveDetail != null){
            	op.setLiveDetail(liveDetail);
            }
            if(removed != null){
            	op.setRemoved(Integer.valueOf(removed));
            }
            mainService.updateOrder(op);
            //return
            genSuccOutputMap();
            
        } catch (Exception e) {
        	Log.SERVICE.error("[UpdateOrderHandler]Exception!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}