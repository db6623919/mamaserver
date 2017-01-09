package com.mama.server.main.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mama.server.main.dao.ActivityDao;
import com.mama.server.main.dao.model.ActivityConfPo;
import com.mama.server.main.dao.model.ActivityMemberRecordPo;
import com.mama.server.main.dao.model.ActivityRecordPo;
import com.mama.server.main.dao.vo.ActivityConfig;
import com.mama.server.main.dao.vo.ActivityDate;
import com.mama.server.main.dao.vo.Luckybean;
import com.mama.server.main.dao.vo.TimePeriod;
import com.mama.server.main.service.Cache;
import com.mama.server.main.service.RandomLuckyBeanService;
import com.mama.server.util.Log;
import com.mama.server.util.ThreadMap;

@Service("randomLuckyBeanService")
public class RandomLuckyBeanServiceImpl implements RandomLuckyBeanService {
	private static final Random random = new Random();
	
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	
	@Autowired
	private Cache cache;
	@Autowired
	private ActivityDao activityDao;
	
	@Override
	@Transactional
	public ThreadMap randomLuckyBean(String activityCode,String memberId,String memberIdentity,ThreadMap dataMap) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String recordDate = sdf.format(new Date());
        // 锁定个人记录
        ActivityMemberRecordPo activityMemberRecord = getActivityMemberRecordForUpdate(memberId, activityCode, recordDate);
        logger.info("start getActivityMemberRecordForUpdate memberId:{} activityCode:{} recordDate{} ", memberId, activityCode, recordDate);
		//获取配置
		ActivityConfig activityConfig = getConfig(activityCode);
		// 2、查询是否抽奖日，不是则直接返回
		boolean isActivityDay=isActivityDay(activityConfig);
		if(isActivityDay==false) {   //不是抽奖日
			dataMap.put("isActivityDate", 0);
			return dataMap;
		}else{
			dataMap.put("isActivityDate", 1);
		}
		// 查询是否抽奖时间段，不是则直接返回
		TimePeriod timePeriod = this.judleTimePeriod(activityCode);
	
		if(timePeriod==null) { //不是抽奖时间段
			dataMap.put("isRcordTimes", 0);
			return dataMap;
		}else{
			dataMap.put("isRcordTimes", 1);
		}
		
		int recordSize = 0;
		int totalSize = activityConfig.getPeriodTimes();
		if(activityMemberRecord != null) {
			//时间段内抽奖次数
	    	ActivityRecordPo activityRecord = new ActivityRecordPo();
	    	activityRecord.setRecordId(activityMemberRecord.getId());
	    	recordSize = this.getActivityRecord(activityRecord).size();
	    	//可抽奖总次数
	    	if(activityMemberRecord.getShareTimes() <= activityConfig.getActivityShareTimes() ) { 
	    		totalSize += activityMemberRecord.getShareTimes();
	    	} else {
	    		totalSize += activityConfig.getActivityShareTimes();
	    	}
	    	if( recordSize >= totalSize) {
	    		dataMap.put("isExceedTimes", 0);
	    		logger.info("no getActivityRecord memberId:{}  recordSize:{} PeriodTimes:{} ", memberId, recordSize, activityConfig.getPeriodTimes());
	    		return dataMap;
	        }
		}
		dataMap.put("isExceedTimes", 1);
		
		//获取抽奖结果
		int num = 0;
		Luckybean tempLuckybean = null;
		if (isActivityDay) {
			random.setSeed(Calendar.getInstance().getTime().getTime());
			int randomNum = random.nextInt(100);
			for (int i = 0; i < activityConfig.getLuckyBeans().size(); i++) {
				tempLuckybean = activityConfig.getLuckyBeans().get(i);
				if (randomNum <= tempLuckybean.getRandomNumRange()) {
					num = i;
					logger.info("memberId:{} randomNum:{}", memberId, num);
					break;
				}
			}
		}
		//抽奖结果
		dataMap.put("luckyIndex", num);
        dataMap.put("luckybean", tempLuckybean);
        
        //插入抽奖记录,获取抽奖记录ID
        int recordId = 0;
        if(activityMemberRecord == null) {
        	ActivityMemberRecordPo activityMemberRecordPo = new ActivityMemberRecordPo();
        	activityMemberRecordPo.setMemberId(memberId);
        	activityMemberRecordPo.setActivityCode(activityCode);
        	activityMemberRecordPo.setMemberIdentity(memberIdentity);
        	activityMemberRecordPo.setTotalPoint(0);
        	activityMemberRecordPo.setWinFlag("0");
        	activityMemberRecordPo.setRecordDate(recordDate);
        	//配置抽奖次数放入记录表中
        	activityMemberRecordPo.setShareTimes(0);
            this.insertActivityMemberRecord(activityMemberRecordPo);
            recordId = this.getActivityMemberRecord(memberId,activityCode,recordDate).getId();
            
        }else{
        	recordId = activityMemberRecord.getId();
        }
    	
        //插入抽奖明细，明细表
        ActivityRecordPo activityRecordPo = new ActivityRecordPo();
        activityRecordPo.setRecordId(recordId);
        activityRecordPo.setPoint(tempLuckybean.getBeanNums());
        activityRecordPo.setRandom(tempLuckybean.getNote());
        this.insertActivityRecord(activityRecordPo);
        
        //更新总记录积分，主表
    	this.updateActivityMemberRecordPoint(recordId, tempLuckybean.getBeanNums());
        
    	//剩余抽奖次数 （recordSize + 1）已抽奖次数
        dataMap.put("recordSize", totalSize - (recordSize + 1) );
        //已抽奖次数
        dataMap.put("usedSize", recordSize + 1);
        
        logger.info("end getActivityMemberRecordForUpdate memberId:{} activityCode:{} recordDate{} ", memberId, activityCode, recordDate);
		return dataMap;
	}
	
	
	/**
	 * 是否是抽奖日期
	 */
	public boolean isActivityDay(ActivityConfig activityConfig) {
		List<ActivityDate> activityDates = activityConfig.getActivityDates();
		if (activityDates != null) {
			Calendar today = Calendar.getInstance();
			for (ActivityDate activityDate : activityDates) {
				if (today.get(activityDate.getCalendarField()) == activityDate.getFieldValue()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 初始化抽奖配置
	 */
	@Override
	public synchronized ActivityConfig getConfig(String activityCode) {
		Object cacheValue = cache.get(activityCode);
		 //ActivityConfig activityConfig = null;
		ActivityConfig ac=new ActivityConfig();
		 if (cacheValue != null) {
			 return (ActivityConfig)cacheValue;
		 } else {
			 // 1、从数据库中查询
			 List<ActivityConfPo> acList = activityDao.getActivityConf(activityCode);
			 // 2、转换为activityconfig
			 
			 for (ActivityConfPo activityConfPo : acList) {
	        		if("ACTIVITY_DATE".equals(activityConfPo.getType())){  //活动日
	        			List<ActivityDate> adList = new ArrayList<ActivityDate>();
	        			String typeValue = activityConfPo.getTypeValue();
	        			String[] arrValue = typeValue.split(",");
	        			for (String value : arrValue) {
	        				String[] arr = value.split("_");
	        				ActivityDate ad = new ActivityDate();
	        				ad.setCalendarField(Integer.parseInt(arr[0]));
	        				ad.setFieldValue(Integer.parseInt(arr[1]));
	        				adList.add(ad);
						}
	        			ac.setActivityDates(adList);
	        			
	        		}else if("ACTIVITY_TIME_PERIOD".equals(activityConfPo.getType())){ //抽奖时间段
	        			List<TimePeriod> tpList=new ArrayList<TimePeriod>();
	        			String typeValue = activityConfPo.getTypeValue();
	        			String[] arrValue = typeValue.split(",");
	        			for (String value : arrValue) {
	        				String[] arr=value.split("-");
	        				
	        				TimePeriod tp=new TimePeriod();
	        				tp.setStartDateTime(arr[0]);
	        				tp.setEndDateTime(arr[1]);
	        				
	        				tpList.add(tp);
						}
	        			ac.setTimePeriods(tpList);
	        		}else if("ACTIVITY_PERIOD_TIMES".equals(activityConfPo.getType())){//每个时间段抽奖次数
	        			ac.setPeriodTimes(Integer.parseInt(activityConfPo.getTypeValue()));
	        		}else if("PUBLISH_TIME".equals(activityConfPo.getType())){//中奖公布时间
	        			/*String typeValue = activityConfPo.getTypeValue();
	        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    				String dateStr = sdf.format(new Date());
	    				String time=dateStr+" "+typeValue;
	    				
	    				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    				Calendar scalendar = Calendar.getInstance();
	    				Date date=null;
						try {
							date = df.parse(time);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    				scalendar.setTime(date);
	    				ac.setPublishTime(scalendar);*/
	        		}else if("LUCKYBEAN".equals(activityConfPo.getType())){//抽奖豆子
	        			List<Luckybean> lbList=new ArrayList<Luckybean>();
	        			String typeValue=activityConfPo.getTypeValue();
	        			String[] arrValue=typeValue.split(",");
	        			int probability = -1;
	        			for (String value : arrValue) {
	        				String[] arr=value.split("\\|");
	        				Luckybean lb=new Luckybean();
	        				lb.setBeanNums(Integer.parseInt(arr[0]));
	        				lb.setProbability(Integer.parseInt(arr[1]));
	        				lb.setNote(arr[2]);
	        				
	        				probability = probability + Integer.parseInt(arr[1]);
	        				lb.setRandomNumRange(probability);
	        				
	        				lbList.add(lb);
	        			}
	        			ac.setLuckyBeans(lbList);
	        			
	        		}else if("ACTIVITY_SHARE_TIMES".equals(activityConfPo.getType())) { //分享可抽奖次数
	        			ac.setActivityShareTimes(Integer.parseInt(activityConfPo.getTypeValue()));
	        		}
				}
			 // 3、设置缓存 
			 cache.set(activityCode, ac);
			 // 4、返回
			 return ac;
		 }
	}
	
	/**
	 * 获取缓存配置
	 */
	@Override
	public ActivityConfig getCache(String activityCode){
		return (ActivityConfig)cache.get(activityCode);
	}

	/**
	 * 判断是否为抽奖时间段
	 * @throws ParseException 
	 */
	@Override
	public TimePeriod judleTimePeriod(String activityCode) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		Calendar today = Calendar.getInstance();
		try {
			ActivityConfig config = getConfig(activityCode);
			for(int i=0; i<config.getTimePeriods().size(); i++) {
				TimePeriod tp = config.getTimePeriods().get(i);
				String startTime = sdf1.format(new Date()) + " " + tp.getStartDateTime();
				String endTime = sdf1.format(new Date()) + " " + tp.getEndDateTime();
					if(today.getTime().getTime() >= sdf.parse(startTime).getTime() && 
							today.getTime().getTime() < sdf.parse(endTime).getTime()){
						return tp;
					}
			}
		} catch (ParseException e) {
			logger.error("judleTimePeriod 抽奖时间段获取失败!" , e);
		}
		return null;
	}
	
	
	
	

	
	@Override
	public ActivityMemberRecordPo getActivityMemberRecord(String memberId,String activityCode,String recordDate){
		ActivityMemberRecordPo amr = new ActivityMemberRecordPo();
		amr.setMemberId(memberId);
		amr.setActivityCode(activityCode);
		amr.setRecordDate(recordDate);
		return activityDao.getActivityMemberRecord(amr);
	}
	
	@Override
	public ActivityMemberRecordPo getActivityMemberRecordForUpdate(String memberId,String activityCode,String recordDate){
		ActivityMemberRecordPo amr = new ActivityMemberRecordPo();
		amr.setMemberId(memberId);
		amr.setActivityCode(activityCode);
		amr.setRecordDate(recordDate);
		return activityDao.getActivityMemberRecordForUpdate(amr);
	}
	
	@Override
	public int insertActivityMemberRecord(ActivityMemberRecordPo amr){
		return activityDao.insertActivityMemberRecord(amr);
	}
	
	@Override
	public int updateActivityMemberRecordPoint(int id,int totalPoint){
		ActivityMemberRecordPo amr = new ActivityMemberRecordPo();
		amr.setId(id);
		amr.setTotalPoint(totalPoint);
		return activityDao.updateActivityMemberRecordPoint(amr);
	}
	
	@Override
	public int insertActivityRecord(ActivityRecordPo ar){
		try {
			activityDao.insertActivityRecord(ar);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertOrder]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public List<ActivityRecordPo> getActivityRecord(ActivityRecordPo ar){
		return activityDao.getActivityRecord(ar);
	}
	
	@Override
	public int getRecordPointByMemberId(String memberId,String activityCode,String recordDate){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("activityCode", activityCode);
		map.put("recordDate", recordDate);
		ActivityMemberRecordPo amr = activityDao.getRecordPointByMemberId(map);
		if(amr==null){
			return 0;
		}else{
			return amr.getTotalPoint();
		}
		
	}
	
	@Override
	public int updateRecordWinFlag(ActivityMemberRecordPo activityMemberRecordPo){
		return activityDao.updateRecordWinFlag(activityMemberRecordPo);
	}
	
	@Override
	public int updateActivityMemberRecordShareTimes(ActivityMemberRecordPo activityMemberRecordPo){
		return activityDao.updateActivityMemberRecordShareTimes(activityMemberRecordPo);
	}
}
