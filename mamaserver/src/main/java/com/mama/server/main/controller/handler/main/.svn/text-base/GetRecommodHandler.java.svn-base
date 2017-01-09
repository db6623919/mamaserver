package com.mama.server.main.controller.handler.main;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.common.entity.RecommodHouse;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.service.MainService;

/**
 * 获取搜索推荐房源
 * @author dengbiao
 *
 */
@Component
public class GetRecommodHandler extends BaseHandler
{
	//记录日志
	private static final Logger log = LoggerFactory.getLogger(GetRecommodHandler.class);
	
	@Resource
	private MainService mainService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param){
		log.info("start to run GetRecommodHandler.");
		try{
			
			List<RecommodHouse> list = mainService.getRecommondList();
			
			//推荐房源
			dataMap.put("list", list);
			
			genSuccOutputMap();
			
		} 
		catch (Exception e) 
		{
			genErrOutputMap("查询失败");
			log.error("failed to GetRecommodHandler."+e );
		}
		
		log.info("end of GetRecommodHandler");
		
		return outputMap;
	}

	
}