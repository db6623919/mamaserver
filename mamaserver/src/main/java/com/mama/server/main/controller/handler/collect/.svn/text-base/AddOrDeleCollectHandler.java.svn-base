package com.mama.server.main.controller.handler.collect;
import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.main.service.imp.RandomLuckyBeanServiceImpl;

/**
 * 收藏
 * @author whl
 *
 */
@Component
public class AddOrDeleCollectHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	int houseId = (Integer)param.get("houseId");    //房源ID
        	String memberId = (String)param.get("memberId");  //会员ID
        	boolean favor = (Boolean) param.get("favor");
        	
        	logger.info("start AddOrDeleCollectHandler houseId:{" + houseId + "},memberId:{" + memberId + "}");
        	
        	CollectPo collect = new CollectPo();
        	collect.setHouseId(houseId);
        	collect.setUid(memberId);
        	//判断房源是否已收藏
        	List<CollectPo> collectList = mainService.getCollectByAllParam(collect);
        	if (favor) {
        		if(collectList.size() > 0) { //已收藏
        			genErrOutputMapWithCode("", ReturnCode.ADD_COLLECT_ERROR);
            	} else { //如果未收藏进行收藏
            		mainService.insertCollect(collect);
            		
            		RedisRequest req = new RedisRequest();
            		req.setKey("mmsf:collect:"+houseId+memberId);
            		req.setValue("1");
            		redisFacade.putKeyAndValue(req);
//            		RedisHelper.set(RedisConstants.COLLECT_KEY+houseId+memberId, "1");
            	}
        	} else {
        		if(collectList.size() < 1) { //已删除
        			genErrOutputMapWithCode("", ReturnCode.COLLECT_NOT_EXISTED_ERROR);
            	} else { //如果收藏则删除
            		mainService.removeCollect(collect);
            		
            		RedisRequest req = new RedisRequest();
            		req.setKey("mmsf:collect:"+houseId+memberId);
            		redisFacade.removeByKey(req);
//            		RedisHelper.del(RedisConstants.COLLECT_KEY+houseId+memberId);
            	}
        	}
        	
        	logger.info("end AddOrDeleCollectHandler ");
            genSuccOutputMap();
        } catch (Exception e) {
        	log.info("=====================AddOrDeleCollectHandler异常====================");
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
