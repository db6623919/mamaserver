package com.mama.server.main.controller.handler.activity;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/**
 * 抽奖记录
 * @author Administrator
 *
 */
@Component
public class GetRecordMemberHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String activityCode = (String)param.get("activityCode");
        	String recordDate = (String)param.get("recordDate");
        	int currentPage = (Integer)param.get("currentPage");
        	int pageSize = (Integer)param.get("pageSize");
        	
            ActivityMemberRecordPo activityMemberRecord = new ActivityMemberRecordPo();
            activityMemberRecord.setRecordDate(recordDate);
            activityMemberRecord.setActivityCode(activityCode);
            activityMemberRecord.setCurrent_page(currentPage);
            activityMemberRecord.setPage_size(pageSize);
            Page<ActivityMemberRecordPo> page = mainService.getRecordMember(activityMemberRecord);
            dataMap.put("page", page);
            genSuccOutputMap();
        } catch (Exception e) {
        	log.info("=====================GetRecordMemberHandler查询异常====================");
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
