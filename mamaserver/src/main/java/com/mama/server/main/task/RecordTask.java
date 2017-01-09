package com.mama.server.main.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.ActivityMemberRecordPo;
import com.mama.server.main.service.RandomLuckyBeanService;

/**
 * 发放中奖名单任务任务
 * @author majiafei
 *
 */
@Component
public class RecordTask
{

	
	//日期格式化
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
    @Autowired
    private RandomLuckyBeanService randomLuckyBeanService;
    
    //记录日志
    private static Logger LOG = LoggerFactory.getLogger(RecordTask.class);
    
	
	/**
	 *
	 *每天早上8：00发短信提醒用户订单情况
	 */
	public void updateRecordWinFlag(){
		try{
			//ActivityConfig activityConfig = randomLuckyBeanService.getConfig("AT_001");
			Date date = new Date();
			ActivityMemberRecordPo activityMemberRecordPo = new ActivityMemberRecordPo();
			activityMemberRecordPo.setActivityCode("AT_001");
			//前一天
			activityMemberRecordPo.setRecordDate(sdf.format(new Date(date.getTime()- 24 * 60 * 60 * 1000)));
			activityMemberRecordPo.setWinFlag("1");
			activityMemberRecordPo.setPrizeProdId("");
			activityMemberRecordPo.setPrizeProdName("");
			randomLuckyBeanService.updateRecordWinFlag(activityMemberRecordPo);
			
			
		}catch (Exception e){
			LOG.error("Failed to execute updateRecordWinFlag.", e);
		}
	}

	
	
}
