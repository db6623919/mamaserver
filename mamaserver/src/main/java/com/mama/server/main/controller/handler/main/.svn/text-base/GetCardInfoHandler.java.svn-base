package com.mama.server.main.controller.handler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.util.RequestChargeFacade;
import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.entity.VirtualFlowEntity;
import com.mmzb.charge.facade.entity.VirtualFlowQueryRequest;
import com.mmzb.charge.facade.entity.VirtualFlowQueryResponse;
import com.mmzb.charge.facade.entity.VirtualQueryRequest;
import com.mmzb.charge.facade.entity.VirtualQueryResponse;

@Component
public class GetCardInfoHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
    	 List<VirtualFlowTypeEnum> types = new ArrayList<VirtualFlowTypeEnum>();
	     List<VirtualFlowEntity> data = null;
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            String uid = (String)param.get("uid");
            /* userCard.setUid(uid);
            List<UserCardPo> userCardList = mainService.getUserCardByUid(userCard);
            if (userCardList == null || userCardList.size() != 1) {
                genErrOutputMapWithCode("user card not exists", ReturnCode.USER_CARD_NOT_EXISTED_ERROR);
                return outputMap;
            }
            userCard = userCardList.get(0);*/
            
            VirtualQueryRequest vqr = new VirtualQueryRequest();
            vqr.setMemberID(uid);
            VirtualQueryResponse vqs = RequestChargeFacade.virtualQuery(vqr);
           // dataMap.put("cardId", userCard.getCardId());
           // dataMap.put("cardPassword", userCard.getCardPassword());
            int total = Integer.parseInt(vqs.getTotalVirtual());
            dataMap.put("totalRechargeAmt", total);
            dataMap.put("totalRewardAmt", vqs.getRewardVirtual());
            dataMap.put("availAmt", Integer.parseInt(vqs.getAvailableVirtual()));
            dataMap.put("freezeAmt", Integer.parseInt(vqs.getFreezedVirtual()));
            
            VirtualFlowQueryRequest virtualFlowQueryRequest =  new VirtualFlowQueryRequest();
            virtualFlowQueryRequest.setMemberID(uid);
            types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_ONLINE);
	   		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_OFFLINE);
	   		virtualFlowQueryRequest.setType(types);
	   		VirtualFlowQueryResponse vfqs = RequestChargeFacade.virtualFlowQuery(virtualFlowQueryRequest);
	   		data = vfqs.getData();
	   		int amt = 0;
	   		for(VirtualFlowEntity v : data){
	   			int money = Integer.parseInt(v.getMoney());
	   			amt += money;
	   		}
	   		int level =1;
           if (amt >= 10000 && amt < 30000) {
        	  level = 2;
           } else if (amt >= 30000 && amt < 100000) {
        	   level = 3;
           } else if (amt >= 100000 && amt < 300000) {
        	   level = 4;
           } else if (amt >= 300000) {
        	   level = 5;
           }
            dataMap.put("level", level);
            //dataMap.put("status", userCard.getStatus());
            //dataMap.put("operTime", userCard.getOperTime());
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
