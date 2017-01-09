package com.mama.server.main.controller.handler.activity;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.vo.ActivityConfig;
import com.mama.server.main.dao.vo.ActivityDate;
import com.mama.server.main.service.imp.RandomLuckyBeanServiceImpl;

/**
 * 中奖记录
 * @author Administrator
 *
 */
@Component
public class InitActivityConfHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (param.get("activityCode") == null) {//会员ID
                genErrOutputMapWithCode("param error, activityCode required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
        	String activityCode = (String)param.get("activityCode");//活动编号
        	
        	logger.info("start get InitActivityConfHandler ActivityConfig activityCode:{" + activityCode + "}");
            //抽奖配置
        	ActivityConfig activityConfig = randomLuckyBeanService.getConfig(activityCode);
        	dataMap.put("activityConfig", activityConfig);
        	dataMap.put("isActivityDate", isActivityDay(activityConfig));//是否抽奖日
        	
        	logger.info("end InitActivityConfHandler activityConfig success");
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface  error");
        }
        return outputMap;
    }
    
    /**
     * 判断是否是工作日
     * @param activityConfig
     * @return
     */
    public int isActivityDay(ActivityConfig activityConfig) {
		List<ActivityDate> activityDates = activityConfig.getActivityDates();
		if (activityDates != null) {
			Calendar today = Calendar.getInstance();
			for (ActivityDate activityDate : activityDates) {
				if (today.get(activityDate.getCalendarField()) == activityDate.getFieldValue()) {
					return 1;
				}
			}
		}
		
		return 0;
	}
    
}
