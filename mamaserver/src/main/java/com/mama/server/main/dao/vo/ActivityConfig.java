package com.mama.server.main.dao.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class ActivityConfig {
	/**
	 * 活动编号
	 */
	private String activityCode;
	/**
	 * 活动周
	 */
	private List<ActivityDate> activityDates;
	/**
	 * 抽奖时间段
	 */
	private List<TimePeriod> timePeriods;
	/**
	 * 抽奖次数
	 */
	private int periodTimes;
	/**
	 * 公布时间
	 */
	private Calendar publishTime;
	/**
	 * 抽奖率
	 */
	private List<Luckybean> luckyBeans;
	/**
	 * 分享可抽奖次数
	 */
	private int activityShareTimes;
	
	
	public int getActivityShareTimes() {
		return activityShareTimes;
	}
	public void setActivityShareTimes(int activityShareTimes) {
		this.activityShareTimes = activityShareTimes;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public List<ActivityDate> getActivityDates() {
		return activityDates;
	}
	public void setActivityDate(ActivityDate activityDate) {
		if (this.activityDates == null)
			this.activityDates = new ArrayList<ActivityDate>();
		
		this.activityDates.add(activityDate);
	}
	public void setActivityDates(List<ActivityDate> activityDates) {
		this.activityDates = activityDates;
	}
	public List<TimePeriod> getTimePeriods() {
		return timePeriods;
	}
	public void setTimePeriod(TimePeriod timePeriod) {
		if (this.timePeriods == null)
			this.timePeriods = new ArrayList<TimePeriod>();
		
		this.timePeriods.add(timePeriod);
	}
	public void setTimePeriods(List<TimePeriod> timePeriods) {
		this.timePeriods = timePeriods;
	}
	public int getPeriodTimes() {
		return periodTimes;
	}
	public void setPeriodTimes(int periodTimes) {
		this.periodTimes = periodTimes;
	}
	public Calendar getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Calendar publishTime) {
		this.publishTime = publishTime;
	}
	public List<Luckybean> getLuckyBeans() {
		return luckyBeans;
	}
	public void setLuckyBeans(List<Luckybean> luckyBeans) {
		this.luckyBeans = luckyBeans;
	}
	public void setLuckyBean(Luckybean luckyBean) {
		if (this.luckyBeans == null)
			this.luckyBeans = new ArrayList<Luckybean>();
		
		this.luckyBeans.add(luckyBean);
	}
	
	
	
	
	
	
	
	
	
}
