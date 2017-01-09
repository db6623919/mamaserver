package com.mama.server.main.service.imp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.mama.server.main.service.Cache;

/**
 * 本地缓存管理实现类
 * @author loomz
 *
 */
@Service("cache")
public class CacheImpl implements Cache {
	private static final Map<Object, Object> cacheMap = new ConcurrentHashMap<Object, Object>();
	
	@PostConstruct
	public void init() {
		
	}

	public Object get(Object key) {
		return cacheMap.get(key);
	}

	public void set(Object key, Object value) {
		cacheMap.put(key, value);
	}
	
}
