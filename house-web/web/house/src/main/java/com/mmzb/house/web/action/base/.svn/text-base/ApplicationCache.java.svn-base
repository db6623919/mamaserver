

package com.mmzb.house.web.action.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmzb.house.web.action.CardAction;


/**
 * 
 * @author lijiaqi
 * 缓存
 */
public class ApplicationCache {

	
	private static Logger logger = LoggerFactory.getLogger(ApplicationCache.class);

	private ConcurrentMap<String, Object> cacheMap = null;
	

	private List<OverdueInfoBean> overdueList = null;
	
	private OverdueThread overdue = null;

	private static class Singleton {
		private static final ApplicationCache singleton = new ApplicationCache();
	}

	public static final ApplicationCache getInstance() {
		return Singleton.singleton;
	}

	private ApplicationCache() {
		cacheMap = new ConcurrentHashMap<String, Object>();
		overdueList = new ArrayList<OverdueInfoBean>();
		overdue = new OverdueThread();
		overdue.start();
	}


	public void put(String key, Object value) {
		cacheMap.put(key, value);
	}
	
	

	public void put(String key, Object value, long overTime) {
		cacheMap.put(key, value);
		long startTime = System.currentTimeMillis();
		overTime = overTime * 1000 + startTime;
		OverdueInfoBean bean = new OverdueInfoBean(key, startTime, overTime);
		overdueList.add(bean);
		if(overdue == null || !overdue.isAlive()){
			overdue = new OverdueThread();
			overdue.start();
		}
	}

	
	public Object get(String key) {
		return cacheMap.get(key);
	}
	

	public String getStr(String key) {
		Object obj = cacheMap.get(key);
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	
	public int getInt(String key) {
		Object obj = cacheMap.get(key);
		if (obj != null) {
			return Integer.valueOf(obj.toString());
		}
		return 0;
	}


	public BigDecimal getBigDecimal(String key) {
		Object obj = cacheMap.get(key);
		if (obj != null) {
			return new BigDecimal(obj.toString());
		}
		return null;
	}

	@SuppressWarnings("unused")
	private class OverdueInfoBean {

		private String key;

		private long startTime;

		private long overdueTime;

		public OverdueInfoBean(String key, long startTime, long overdueTime) {
			super();
			this.key = key;
			this.startTime = startTime;
			this.overdueTime = overdueTime;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public long getStartTime() {
			return startTime;
		}

		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}

		public long getOverdueTime() {
			return overdueTime;
		}

		public void setOverdueTime(long overdueTime) {
			this.overdueTime = overdueTime;
		}

	}

	private class OverdueThread extends Thread {

		private boolean flag = true;
		@Override
		public void run() {
			try {
				while (flag) {
					overdueRemove();
					Thread.sleep(3000);
				}

			} catch (InterruptedException e) {
			}
		}

		@SuppressWarnings("rawtypes")
		private boolean overdueRemove() {
			try {
				if (overdueList != null) {
					Iterator iterator = overdueList.iterator();
					while (iterator.hasNext()) {
						OverdueInfoBean bean = (OverdueInfoBean) iterator.next();
						if (System.currentTimeMillis() > bean.getOverdueTime()) {
							logger.info("*****缓存过期********"+bean.getKey());
							logger.info("*****缓存过期********"+bean.getStartTime());
							logger.info("*****缓存过期********"+bean.getOverdueTime());
							logger.info("*****缓存过期********"+cacheMap.get(bean.getKey()));
							cacheMap.remove(bean.getKey());
							iterator.remove();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

	}
}
