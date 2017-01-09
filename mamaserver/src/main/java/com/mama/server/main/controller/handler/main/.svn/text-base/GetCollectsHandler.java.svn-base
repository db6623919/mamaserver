package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CollectPo;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/**
 * 收藏列表显示
 * @author whl
 *
 */
@Component
public class GetCollectsHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String uid = (String)param.get("uid");
            
            CollectPo collect = new CollectPo();
            collect.setUid(uid);
            collect.setHouseId(-1);
            collect.setCurrent_page(1);
            collect.setPage_size(10);
            //List<CollectPo> collectList = mainService.getCollectByAllParam(collect);
            Page<CollectPo> page = mainService.getCollectByPage(collect);
            if (page.getResult() != null) {
                dataMap.put("collects", page.getResult());
                dataMap.put("num", page.getResult().size());
                genSuccOutputMap();
            } else {
            	dataMap.put("collects", page.getResult());
            	dataMap.put("num", 0);
                genSuccOutputMap();
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
