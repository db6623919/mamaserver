package com.mama.server.main.controller.handler.main;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseTag;

/**
 * 生成houseTag to redis
 * @author dengbiao
 *
 */
@Component
public class PutHouseTagsToRedisHandler extends BaseHandler {

    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
        	putRedis();
            genSuccOutputMap();
            return outputMap;
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
	
	 private void putRedis(){
	    	//显示标签
			List<HouseTag> tList = new ArrayList<HouseTag>();
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("isDispayOnSearch", 1);
			tList = mainService.getHouseTags(param);
			String value = "";
			for (int i = 0; i < tList.size(); i++) {
				HouseTag houseTag = new HouseTag();
				HouseTag tag = new HouseTag();
				tag = tList.get(i);
				if (i!=tList.size()-1) {
					value+="{\"id\":"+tag.getId().toString()+",\"name\":\""+tag.getName()+"\"},";
				}else {
					value+="{\"id\":"+tag.getId().toString()+",\"name\":\""+tag.getName()+"\"}";
				}
			}

			if (tList.size()==0) {
				value = "{}";
			}
    		RedisRequest req = new RedisRequest();
    		req.setKey("mmsf:houseTag");
    		req.setValue(value);
    		redisFacade.putKeyAndValue(req);
    		
//			HouseTagCache.setHouseTagToRedis(value);
			
			//所有标签
			List<HouseTag> htList = new ArrayList<HouseTag>();
			HashMap<String, Object> par = new HashMap<String, Object>();
			htList = mainService.getHouseTags(par);
			String val = "";
			for (int i = 0; i < htList.size(); i++) {
				HouseTag houseTag = new HouseTag();
				HouseTag tag = new HouseTag();
				tag = htList.get(i);
				if (i!=htList.size()-1) {
					val+="{\"id\":"+tag.getId().toString()+",\"name\":\""+tag.getName()+"\"},";
				}else {
					val+="{\"id\":"+tag.getId().toString()+",\"name\":\""+tag.getName()+"\"}";
				}
			}
	  		RedisRequest redisRequest = new RedisRequest();
	  		redisRequest.setKey("mmsf:allHouseTagg");
	  		redisRequest.setValue(val);
    		redisFacade.putKeyAndValue(redisRequest);
    		
//			HouseTagCache.setAllHouseTagToRedis(val);
			
	    }
}