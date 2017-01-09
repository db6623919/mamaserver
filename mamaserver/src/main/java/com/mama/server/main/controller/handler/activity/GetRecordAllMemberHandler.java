package com.mama.server.main.controller.handler.activity;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

/**
 * 所有抽奖记录
 * @author Administrator
 *
 */
@Component
public class GetRecordAllMemberHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String activityCode = (String)param.get("activityCode");
        	String recordDate = (String)param.get("recordDate");
            ActivityMemberRecordPo activityMemberRecord = new ActivityMemberRecordPo();
            activityMemberRecord.setRecordDate(recordDate);
            activityMemberRecord.setActivityCode(activityCode);
            List<ActivityMemberRecordPo> activityMemberRecordList = mainService.getRecordAllMember(activityMemberRecord);
            dataMap.put("activityMemberRecordList", activityMemberRecordList);
            genSuccOutputMap();
        } catch (Exception e) {
        	log.info("=====================GetRecordAllMember查询异常====================");
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
