package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CollectPo;

@Component
public class AddCollectHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("houseId") == null) {
                genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String uid = (String)param.get("uid");
            int houseId = (Integer)param.get("houseId");
            CollectPo collect = new CollectPo();
            collect.setUid(uid);
            collect.setHouseId(houseId);
            collect.setRemoved(0);
            List<CollectPo> collectList = mainService.getCollectByAllParam(collect);
            if (collectList != null && collectList.size() > 0 && collectList.get(0).getRemoved() == 0) {
                genErrOutputMapWithCode("user already has this collect", ReturnCode.DUPLICATED_COLLECT_ERROR);
            } else {
                if (mainService.insertCollect(collect) != 0) {
                    genErrOutputMapWithCode("fail to add collect", ReturnCode.ADD_COLLECT_ERROR);
                } else {
                    genSuccOutputMap();
                }
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
