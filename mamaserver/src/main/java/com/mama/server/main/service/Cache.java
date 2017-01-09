package com.mama.server.main.service;

/**
 * 本地缓存管理接口
 * @author loomz
 *
 */
public interface Cache {
//	public static final String CACHE_KEY_ACTIVITY_CONF_AT001 = "AT001";
	
	void init();
	
	Object get(Object key);
	
	void set(Object key, Object value);
}
