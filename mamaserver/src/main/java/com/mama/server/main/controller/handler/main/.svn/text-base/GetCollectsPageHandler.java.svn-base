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
public class GetCollectsPageHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int currentPage = (Integer)param.get("currentPage");
        	int pageSize = (Integer)param.get("pageSize");
            String uid = (String)param.get("uid");
            
            CollectPo collect = new CollectPo();
            collect.setUid(uid);
            collect.setHouseId(-1);
            collect.setPage_size(pageSize);
            collect.setCurrent_page(currentPage);
            
            Page<CollectPo> page = mainService.getCollectByPage(collect);
            dataMap.put("page", page.getResult());
            dataMap.put("total", page.getTotal());
            genSuccOutputMap();
            /*List<CollectPo> collectList = mainService.getCollectByAllParam(collect);
            if (collectList != null) {
                dataMap.put("collects", collectList);
                dataMap.put("num", collectList.size());
                genSuccOutputMap();
            } else {
            	dataMap.put("collects", collectList);
            	dataMap.put("num", 0);
                genSuccOutputMap();
            }*/
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
