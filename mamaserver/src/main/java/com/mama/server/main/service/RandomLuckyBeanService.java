package com.mama.server.main.service;

import java.util.List;

import com.mama.server.main.dao.model.ActivityMemberRecordPo;
import com.mama.server.main.dao.model.ActivityRecordPo;
import com.mama.server.main.dao.vo.ActivityConfig;
import com.mama.server.main.dao.vo.TimePeriod;
import com.mama.server.util.ThreadMap;

public interface RandomLuckyBeanService {
	/**
	 * 抽奖
	 */
	ThreadMap randomLuckyBean(String activityCode,String memberId,String memberIdentity,ThreadMap dataMap);
	
	/**
	 * 是否是抽奖日
	 * @return
	 */
	boolean isActivityDay(ActivityConfig activityConfig);
	
	/**
	 * 判断是哪个时间段
	 * @return
	 */
	TimePeriod judleTimePeriod(String activityCode);
	
	/**
	 * 获取配置
	 * @return
	 */
	ActivityConfig getConfig(String activityCode);
	
	/**
	 * 获取缓存配置
	 * @param activityCode
	 * @return
	 */
	public ActivityConfig getCache(String activityCode);
	
	/**
	 * <!-- 查询会员抽奖记录 -->
	 * @param params
	 * @return
	 */
	ActivityMemberRecordPo getActivityMemberRecord(String memberId,String activityCode,String recordDate);
	
	/**
	 * 添加会员抽奖记录
	 * @param param
	 * @return
	 */
	int insertActivityMemberRecord(ActivityMemberRecordPo amr);
	
	/**
	 * 修改抽奖记录总积分
	 * @param id
	 * @param total_point
	 * @return
	 */
	int updateActivityMemberRecordPoint(int id,int total_point);
	
	/**
	 * 插入抽奖记录明细
	 * @param param
	 * @return
	 */
	int insertActivityRecord(ActivityRecordPo ar);
	
	/**
	 * 查询抽奖明细
	 * @param recordId
	 * @return
	 */
	List<ActivityRecordPo> getActivityRecord(ActivityRecordPo ar);
	
	/**
	 * 锁住当前记录
	 * @param memberId
	 * @param activityCode
	 * @param recordDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ActivityMemberRecordPo getActivityMemberRecordForUpdate(String memberId,String activityCode,String recordDate);
	
	
	/**
	 * 获取会员当天总积分
	 * @param memberId
	 * @param activityCode
	 * @param recordDate
	 * @return
	 */
	int getRecordPointByMemberId(String memberId,String activityCode,String recordDate);
	
	/**
	 * 发放中奖名单
	 * @param activityMemberRecordPo
	 * @return
	 */
	int updateRecordWinFlag(ActivityMemberRecordPo activityMemberRecordPo);
	/**
	 * 修改分享记录次数
	 * @param activityMemberRecordPo
	 * @return
	 */
	int updateActivityMemberRecordShareTimes(ActivityMemberRecordPo activityMemberRecordPo);
}
