package com.mama.server.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public  class ThreadMap  extends HashMap<String, Object> implements Map<String, Object> {
	
	private ThreadLocal<Map<String, Object>> tMap;
	
	public void reset(){
		if(tMap.get() == null ){
			tMap.set(new HashMap<String, Object>());
		}else{
			this.clear();
		}
	}
	
	public HashMap<String, Object> getHashMap()
	{
		if(tMap.get() == null ){
			tMap.set(new HashMap<String, Object>());
		}
		return (HashMap<String, Object>) tMap.get();
	}
	
	public ThreadMap(){
		tMap=new ThreadLocal<Map<String, Object>>();
		tMap.set(new HashMap<String, Object>());
	}
	
	@Override
	public int size() {
		return tMap.get().size();
	}

	@Override
	public boolean isEmpty() {
		return tMap.get().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return tMap.get().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return tMap.get().containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return tMap.get().get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return tMap.get().put( key, value);
	}

	@Override
	public Object remove(Object key) {
		return tMap.get().remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		tMap.get().putAll(m);
		
	}

	@Override
	public void clear() {
		tMap.get().clear();
	}

	@Override
	public Set<String> keySet() {
		return tMap.get().keySet();
	}

	@Override
	public Collection<Object> values() {
		return tMap.get().values();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return tMap.get().entrySet();
	}
	
	
}
