package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CollectPo;

@Component
public class RemoveCollectHandler extends BaseHandler {

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
            List<CollectPo> collectList = mainService.getCollectByAllParam(collect);
            if (collectList == null) {
                genErrOutputMapWithCode("collect not existed", ReturnCode.COLLECT_NOT_EXISTED_ERROR);
                return outputMap;
            }
            collect = null;
            for (int i = 0; i < collectList.size(); ++i) {
                if (collectList.get(i).getHouseId() == houseId) {
                    collect = collectList.get(i);
                    break;
                }
            }
            if (collect != null && mainService.removeCollect(collect) == 0) {
                genSuccOutputMap();
            } else {
                genErrOutputMapWithCode("fail to remove collect", ReturnCode.REMOVE_COLLECT_ERROR);
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
