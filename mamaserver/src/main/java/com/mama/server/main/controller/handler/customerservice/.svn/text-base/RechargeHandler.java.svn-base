package com.mama.server.main.controller.handler.customerservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.FlowPo;
import com.mama.server.main.dao.model.UserCardPo;
import com.mama.server.main.dao.model.UserInfoPo;
import com.mama.server.util.Log;

@Component
public class RechargeHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String sUid = mainService.checkPostParam(param.get("uid"));
        	String sRechargeamt = mainService.checkPostParam(param.get("rechargeamt"));
        	String sRewardamt = mainService.checkPostParam(param.get("rewardamt"));
        	String sShowdetail = mainService.checkPostParam(param.get("showdetail"));
        	
            if (sUid == null || sRechargeamt == null || sRewardamt == null || sShowdetail == null) {
            	Log.SERVICE.error("[RechargeHandler]param is empty : uid("+sUid+"),rechargeamt("+sRechargeamt+"),rewardamt("+sRewardamt+"),showdetail("+sShowdetail+")");
            	genErrOutputMapWithCode("param is empty : uid("+sUid+"),rechargeamt("+sRechargeamt+"),rewardamt("+sRewardamt+"),showdetail("+sShowdetail+")", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            int iRechargeamt = Integer.valueOf(sRechargeamt);
            int iRewardamt = Integer.valueOf(sRewardamt);
            if(iRechargeamt < 0 || iRewardamt < 0){
            	Log.SERVICE.error("[RechargeHandler]");
            	genErrOutputMapWithCode("param is error : rechargeamt("+sRechargeamt+"),rewardamt("+sRewardamt+")", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            //get user info
            /*UserInfoPo userInfo = new UserInfoPo();
            userInfo.setUid(sUid);
            userInfo = mainService.getUserinfoByAllParam(userInfo);
            if (userInfo == null) {
                genErrOutputMapWithCode("param error, uid not exists", ReturnCode.USER_NOT_FOUND_ERROR);
                return outputMap;
            }*/
            //get user card info
            UserCardPo ucp = new UserCardPo();
            ucp.setUid(sUid);
            ucp = mainService.getUserCardByUid(ucp).get(0);
            
            //update user card
            ucp.setTotalRechargeAmt(ucp.getTotalRechargeAmt() + iRechargeamt);
            ucp.setTotalRewardAmt(ucp.getTotalRewardAmt() + iRewardamt);
            ucp.setAvailAmt(ucp.getAvailAmt() + iRechargeamt + iRewardamt);
            mainService.updateUserCard(ucp);
            
            //insert into flow
            if (iRechargeamt > 0) {
                FlowPo fpRecharge = new FlowPo();
                fpRecharge.setAmt(iRechargeamt);
                fpRecharge.setType(ConstValue.FLOW_RECHARGE);
                fpRecharge.setShowDetail(sShowdetail);
                fpRecharge.setUid(sUid);
                fpRecharge.setOrderId("");
                if (mainService.insertFlow(fpRecharge) != 0) {
                    genErrOutputMapWithCode("fail to recharge", ReturnCode.RECHARGE_ERROR);
                    return outputMap;
                }
            }
            
            if (iRewardamt > 0) {
                FlowPo fpReward = new FlowPo();
                fpReward.setAmt(iRewardamt);
                fpReward.setType(ConstValue.FLOW_REWARD);
                fpReward.setShowDetail("充值奖励红包");
                fpReward.setUid(sUid);
                fpReward.setOrderId("");
                if (mainService.insertFlow(fpReward) != 0) {
                    genErrOutputMapWithCode("fail to recharge", ReturnCode.RECHARGE_ERROR);
                    return outputMap;
                }
            }
            
            // update user card level
            FlowPo flow = new FlowPo();
            flow.setUid(sUid);
            flow.setType(ConstValue.FLOW_RECHARGE);
            flow.setRemoved(0);
            List<FlowPo> flowList = mainService.getFlowByAllParam(flow);
            if (flowList != null) {
                int total = 0;
                for (int i = 0; i < flowList.size(); ++i) {
                    total += flowList.get(i).getAmt();
                }
                int level = 0;
                if (total < 10000) {
                    level = 1;
                } else if (total >= 10000 && total < 30000) {
                    level = 2;
                } else if (total >= 30000 && total < 100000) {
                    level = 3;
                } else if (total >= 100000 && total < 300000) {
                    level = 4;
                } else {
                    level = 5;
                }
                ucp.setLevel(level);
                mainService.updateUserCard(ucp);
            }
            
            //return
            genSuccOutputMap();
            
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}