package com.mama.server.main.controller.handler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetDevelopersHandler extends BaseHandler {

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
            
            List<DeveloperPo> developerList = mainService.getDeveloper(param);
            List<DeveloperPo> developerLists = mainService.getDeveloper(null);
            
            if (developerList != null) {
                ArrayList<HashMap<String, Object>> developerMapList = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < developerList.size(); ++i) {
                    HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                    tmpMap.put("devId", developerList.get(i).getDevId());
                    tmpMap.put("name", developerList.get(i).getName());
                    tmpMap.put("type", developerList.get(i).getType());
                    tmpMap.put("mark", developerList.get(i).getMark());
                    tmpMap.put("showDetail", developerList.get(i).getShowDetail());
                    developerMapList.add(tmpMap);
                }
                
                if (pageNum == -1 || pageCount == -1) {
                    dataMap.put("developers", developerMapList);
                    dataMap.put("num", developerLists.size());
                    genSuccOutputMap();
                    return outputMap;
                }
                
                ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
                int startNum = (pageNum - 1) * pageCount;
                int endNum = startNum + pageCount;
                if (endNum > developerMapList.size()) {
                    endNum = developerMapList.size();
                }
                for (int i = startNum; i < endNum; ++i) {
                    pageList.add(developerMapList.get(i));
                }
                dataMap.put("developers", pageList);
                dataMap.put("num", developerLists.size());
                
                genSuccOutputMap();
            } else {
                genErrOutputMapWithCode("fail to get developers", ReturnCode.GET_DEVELOPER_ERROR);
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}