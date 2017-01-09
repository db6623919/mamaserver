package com.mama.server.main.controller.handler.activity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ActivityMemberRecordPo;
import com.mama.server.main.dao.model.ActivityRecordPo;
import com.mama.server.main.dao.vo.ActivityConfig;
import com.mama.server.main.dao.vo.TimePeriod;
import com.mama.server.main.service.imp.RandomLuckyBeanServiceImpl;

/**
 * 中奖记录
 * @author Administrator
 *
 */
@Component
public class InitMemberInfoHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	//日期格式化
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        	if (param.get("activityCode") == null) {//会员ID
                genErrOutputMapWithCode("param error, activityCode required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
        	if (param.get("memberId") == null) {//会员ID
                genErrOutputMapWithCode("param error, memberId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
        	String activityCode = (String)param.get("activityCode");//活动编号
        	String memberId = (String)param.get("memberId");        //会员ID
        	
        	logger.info("start InitMemberInfoHandler activityCode:{" + activityCode + "} memberId:{" + memberId + "}" );
        	
        	//获取抽奖配置
        	ActivityConfig activityConfig = randomLuckyBeanService.getConfig(activityCode);
            int surplusSize = activityConfig.getPeriodTimes(); //剩余抽奖次数
            int totalPoint = 0;//抽奖积分
        	//判断是否在当前时间段
        	TimePeriod timePeriod = randomLuckyBeanService.judleTimePeriod(activityCode);
        	int totalSize =  activityConfig.getPeriodTimes();
        	if(timePeriod != null) {
        		String recordDate=sdf.format(new Date()); //当前时间yyyyMMdd
        		//获取当前时间段内剩余抽奖次数
        		ActivityMemberRecordPo activityMemberRecord = randomLuckyBeanService.getActivityMemberRecord(memberId,activityCode,recordDate);
        		if(activityMemberRecord != null) {
        			if(activityMemberRecord.getShareTimes() < activityConfig.getActivityShareTimes()) {//已分享
        				//获取可抽奖总次数 :配置次数+分享可抽奖次数(分享后)
        				totalSize += activityMemberRecord.getShareTimes();
        				dataMap.put("isShare", 0);
        			}else{//已分享
        				totalSize += activityConfig.getActivityShareTimes();
        				dataMap.put("isShare", 1); 
        			}
        			//当前已抽奖次数
        			ActivityRecordPo activityRecord = new ActivityRecordPo();
        			activityRecord.setRecordId(activityMemberRecord.getId());
        			//activityRecord.setStartDate(timePeriod.getStartDateTime().getTime());
        			//activityRecord.setEndDate(timePeriod.getEndDateTime().getTime());
    	        	int overSize = randomLuckyBeanService.getActivityRecord(activityRecord).size();
    	        	//剩余抽奖次数
    	        	surplusSize = totalSize - overSize;
        		}
            	//获取会员当天总积分
            	totalPoint = randomLuckyBeanService.getRecordPointByMemberId(memberId, activityCode, recordDate);
        	}else {
        		dataMap.put("isShare", 0);
        	}	
        	//检查当前时间所在时间段
        	dataMap.put("nowDate", getNowDate(activityConfig));
        	dataMap.put("recordSize", surplusSize);
        	dataMap.put("totalPoint", totalPoint);
        	
        	logger.info("end InitMemberInfoHandler success surplusSize:{" + surplusSize + "} recordSize:{" + surplusSize + "}");
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("=====================GetLotteryRecordHandler抽奖异常====================");
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    /**
     * 获取系统当前时间、抽奖时间段开始或结束时间
     * 1、当前时间在抽奖时间前
     * 2、当前时间在抽奖时间后
     * 0、 当前时间在两个时间段之间
     * @param config
     * @return
     * @throws ParseException 
     */
    public String getNowDate(ActivityConfig config) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		String str="";
		Date lastDate = null;
		if(config.getTimePeriods().size() == 1) {
			TimePeriod tp = config.getTimePeriods().get(0);
			
			String startTime = sdf1.format(new Date()) + " " + tp.getStartDateTime();
			String endTime = sdf1.format(new Date()) + " " + tp.getEndDateTime();
			
			if(new Date().getTime() < sdf.parse(startTime).getTime()){
				str = sdf.format(new Date()) + ",1," + startTime;
			}else if(new Date().getTime()>sdf.parse(endTime).getTime()){
				str = sdf.format(new Date()) + ",2," + endTime;
			}
		} else {
			for(int i=0; i<config.getTimePeriods().size(); i++) {
				TimePeriod tp = config.getTimePeriods().get(i);
				
				String startTime = sdf1.format(new Date()) + " " + tp.getStartDateTime();
				String endTime = sdf1.format(new Date()) + " " + tp.getEndDateTime();
				
				if(i == 0) {
					if(new Date().getTime() < sdf.parse(startTime).getTime()) {
						str = sdf.format(new Date()) + ",1," + startTime;
					}
					lastDate = sdf.parse(endTime);
				}else if(i == config.getTimePeriods().size()-1) {
					if(new Date().getTime() > sdf.parse(endTime).getTime()) {
						str = sdf.format(new Date()) + ",2," + endTime;
					}
				}else if(new Date().getTime()>lastDate.getTime() && new Date().getTime() < sdf.parse(startTime).getTime()) {
					str = sdf.format(new Date()) + ",0," + startTime;
				}
			}
		}
		return str;
	}
}
