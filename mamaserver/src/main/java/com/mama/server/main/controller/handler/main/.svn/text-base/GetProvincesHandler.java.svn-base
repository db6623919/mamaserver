package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.DeveloperPo;
import com.mama.server.main.dao.model.ProvincePo;

@Component
public class GetProvincesHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            int pageNum = -1;
            int pageCount = -1;
            if (param.get("pageNum") != null && param.get("pageCount") != null) {
                pageNum = (Integer)param.get("pageNum");
                pageCount = (Integer)param.get("pageCount");
                
                if (pageNum <= 0 || pageCount <= 0) {
                    genErrOutputMapWithCode("param error, invalid pageNum/pageCount", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
            }
            List<ProvincePo> provinceList = mainService.getProvinces(param);
            List<ProvincePo> provinceLists = mainService.getProvinces(null);
            
            if (provinceList != null) {
                ArrayList<HashMap<String, Object>> provinceMapList = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < provinceList.size(); ++i) {
                    if (provinceList.get(i).getRemoved() == 1) {
                        continue;
                    }
                    HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                    tmpMap.put("provId", provinceList.get(i).getProvId());
                    tmpMap.put("provName", provinceList.get(i).getName());
                    provinceMapList.add(tmpMap);
                }
                
                if (pageNum == -1 || pageCount == -1) {
                    dataMap.put("provinces", provinceMapList);
                    dataMap.put("num", provinceLists.size());
                    genSuccOutputMap();
                    return outputMap;
                }
                
                ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
                int startNum = (pageNum - 1) * pageCount;
                int endNum = startNum + pageCount;
                if (endNum > provinceMapList.size()) {
                    endNum = provinceMapList.size();
                }
                for (int i = startNum; i < endNum; ++i) {
                    pageList.add(provinceMapList.get(i));
                }
                dataMap.put("provinces", pageList);
                dataMap.put("num", provinceLists.size());
                
                genSuccOutputMap();
            } else {
                genErrOutputMapWithCode("fail to get provinces", ReturnCode.GET_PROVINCE_ERROR);
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
