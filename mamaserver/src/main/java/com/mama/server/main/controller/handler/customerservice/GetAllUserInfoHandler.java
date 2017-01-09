package com.mama.server.main.controller.handler.customerservice;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.UserInfoPo;

@Component
public class GetAllUserInfoHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            List<UserInfoPo> userInfoList = mainService.getAllUserInfo();
            if (userInfoList == null) {
                genErrOutputMapWithCode("fail to get all user", ReturnCode.PARAM_ERROR);
                return outputMap;
            }

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

            ArrayList<HashMap<String, Object>> userInfoMapList = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < userInfoList.size(); ++i) {
                HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put("uid", userInfoList.get(i).getUid());
                tmpMap.put("name", userInfoList.get(i).getName());
                tmpMap.put("idCard", userInfoList.get(i).getIdCard());
                tmpMap.put("phone", userInfoList.get(i).getPhone());
                tmpMap.put("type", userInfoList.get(i).getType());
                tmpMap.put("icon", userInfoList.get(i).getIcon());
                tmpMap.put("nickName", userInfoList.get(i).getNickName());
                tmpMap.put("email", userInfoList.get(i).getEmail());
                tmpMap.put("channel", userInfoList.get(i).getChannel());
                userInfoMapList.add(tmpMap);
            }
            
            if (pageNum == -1 || pageCount == -1) {
                dataMap.put("userInfos", userInfoMapList);
                dataMap.put("num", userInfoMapList.size());
                genSuccOutputMap();
                return outputMap;
            }
            
            ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
            int startNum = (pageNum - 1) * pageCount;
            int endNum = startNum + pageCount;
            if (endNum > userInfoMapList.size()) {
                endNum = userInfoMapList.size();
            }
            for (int i = startNum; i < endNum; ++i) {
                pageList.add(userInfoMapList.get(i));
            }
            dataMap.put("userInfos", pageList);
            dataMap.put("num", pageList.size());
            
            int totalPage = userInfoMapList.size() / pageCount;
            if (userInfoMapList.size() % pageCount != 0) {
                totalPage += 1;
            }
            dataMap.put("totalPage", totalPage);
            dataMap.put("totalNum", userInfoMapList.size());
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
