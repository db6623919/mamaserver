package com.mama.server.main.controller.handler.activity;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.main.dao.vo.ActivityConfig;
import com.mama.server.main.service.imp.RandomLuckyBeanServiceImpl;

/**
 * 分享记录
 * @author Administrator
 *
 */
@Component
public class DoShareHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String activityCode = (String)param.get("activityCode"); //活动编号
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        	String memberId = (String)param.get("memberId");        //会员ID
        	String memberIdentity = (String)param.get("memberIdentity");        //手机号
        	
        	logger.info("start DoShareHandler activityCode:{" + activityCode + "},memberId:{" + memberId + "},memberIdentity:{" +  memberIdentity + "}");
        	//判断是否是第一次抽奖
        	ActivityMemberRecordPo activityMemberRecord = randomLuckyBeanService.getActivityMemberRecordForUpdate(memberId, activityCode, sdf.format(new Date()));
        	
        	ActivityMemberRecordPo activityMemberRecordPo = new ActivityMemberRecordPo();
        	activityMemberRecordPo.setMemberId(memberId);
        	activityMemberRecordPo.setActivityCode(activityCode);
        	activityMemberRecordPo.setRecordDate(sdf.format(new Date()));
        	if(activityMemberRecord == null) { //不是第一次直接插入记录
            	activityMemberRecordPo.setMemberIdentity(memberIdentity);
            	activityMemberRecordPo.setTotalPoint(0);
            	activityMemberRecordPo.setWinFlag("0");
            	//配置抽奖次数放入记录表中
            	activityMemberRecordPo.setShareTimes(1);
            	randomLuckyBeanService.insertActivityMemberRecord(activityMemberRecordPo);
            	//分享次数
            	dataMap.put("shareTime", 1);
        	}else{   //修改抽奖次数
                int result = randomLuckyBeanService.updateActivityMemberRecordShareTimes(activityMemberRecordPo);
                if(result < 1){
                	genErrOutputMapWithCode("param error, DoShareHandler required", ReturnCode.PARAM_ERROR);
                	logger.info("end param error, DoShareHandler required");
                    return outputMap;
                }
                //获取抽奖配置
                ActivityConfig activityConfig = randomLuckyBeanService.getConfig(activityCode);
                int shareTime = activityMemberRecord.getShareTimes() + 1;//分享总次数
                //分享次数
            	dataMap.put("shareTime", shareTime);
                if(shareTime >= activityConfig.getActivityShareTimes()) {
                	dataMap.put("isShare", 1);
                } else {
                	dataMap.put("isShare", 0);
                }
        	}
        	
        	logger.info("end DoShareHandler success");
            genSuccOutputMap();
        } catch (Exception e) {
        	log.info("=====================DoShareHandler异常====================");
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
