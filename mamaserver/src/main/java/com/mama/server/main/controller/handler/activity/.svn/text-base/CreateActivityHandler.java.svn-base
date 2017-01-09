package com.mama.server.main.controller.handler.activity;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.util.ThreadMap;

/**
 * 抽奖
 * @author Administrator
 *
 */
@Component
public class CreateActivityHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("memberId") == null) {//会员ID
                genErrOutputMapWithCode("param error, memberId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("memberIdentity") == null) {//会员标示手机号
                genErrOutputMapWithCode("param error, memberIdentity required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("activityCode") == null) {//活动编码
                genErrOutputMapWithCode("param error, activityCode required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            String memberId = (String)param.get("memberId");
            String memberIdentity = (String)param.get("memberIdentity");
            String activityCode = (String)param.get("activityCode");
            ThreadMap map = randomLuckyBeanService.randomLuckyBean(activityCode,memberId,memberIdentity,dataMap);
            dataMap=map;
            genSuccOutputMap();
        } catch (Exception e) {
        	log.error("===============CreateActivityHandler抽奖失败============", e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
