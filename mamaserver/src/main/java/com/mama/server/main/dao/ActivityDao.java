package com.mama.server.main.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;






import com.mama.server.main.dao.model.ActivityConfPo;
import com.mama.server.main.dao.model.ActivityMemberRecordPo;
import com.mama.server.main.dao.model.ActivityRecordPo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

/**
 * 抽奖活动
 * @author Whl
 *
 * @version 20160616
 */

@MyBatisDao
public interface ActivityDao{

	int insertActivityMemberRecord(ActivityMemberRecordPo amr);
	
	int insertActivityRecord(ActivityRecordPo ar);
	
	ActivityMemberRecordPo getActivityMemberRecord(ActivityMemberRecordPo amr);
	
	List<ActivityRecordPo> getActivityRecord(ActivityRecordPo ar);
	
	int updateActivityMemberRecordPoint(ActivityMemberRecordPo amr);
	
	List<ActivityMemberRecordPo> getLotteryRecord(ActivityMemberRecordPo amr);
	
	List<ActivityConfPo> getActivityConf(String activityCode);
	
	ActivityMemberRecordPo getActivityMemberRecordForUpdate(ActivityMemberRecordPo amr);
	
	ActivityMemberRecordPo getRecordPointByMemberId(Map<String,Object> map);
	
	int updateRecordWinFlag(ActivityMemberRecordPo activityMemberRecordPo);
	
	int updateActivityMemberRecordShareTimes(ActivityMemberRecordPo activityMemberRecordPo);
	
	List<ActivityMemberRecordPo> getRecordMember(ActivityMemberRecordPo activityMemberRecordPo);
}
