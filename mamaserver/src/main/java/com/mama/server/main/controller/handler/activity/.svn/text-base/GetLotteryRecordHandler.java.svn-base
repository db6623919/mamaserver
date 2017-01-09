package com.mama.server.main.controller.handler.activity;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

/**
 * 中奖记录
 * @author Administrator
 *
 */
@Component
public class GetLotteryRecordHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String activityCode = (String)param.get("activityCode");
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            String recordDate=sdf.format(new Date());
            ActivityMemberRecordPo amr = new ActivityMemberRecordPo();
            amr.setRecordDate(recordDate);
            amr.setActivityCode(activityCode);
            List<ActivityMemberRecordPo> amrList = mainService.getLotteryRecord(amr);
            dataMap.put("amrList", amrList);
            genSuccOutputMap();
        } catch (Exception e) {
        	log.info("=====================GetLotteryRecordHandler抽奖异常====================", e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
