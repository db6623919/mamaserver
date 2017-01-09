package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.util.dao.PaginationInterceptor.Page;
import com.netfinworks.common.lang.StringUtil;

/**
 * 获取主题活动房源列表接口
 * @author whl
 *
 */
@Component
public class GetTopicHousesHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(GetTopicHousesHandler.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start GetTopicHousesHandler.");
        	}
        	
        	//获取主题活动关联房源ID
        	Page<Map<String,Object>> page = mainService.getTopicHousesPage(param);
        	String houseIds = "";
        	//房源对应的客栈合作程度
        	Map<Integer,Object> partnershipMap = new HashMap<Integer, Object>();
			for (int i = 0 ; i < page.getResult().size(); i ++) {
				houseIds += page.getResult().get(i).get("houseId") + ",";
				partnershipMap.put((Integer)page.getResult().get(i).get("houseId"), page.getResult().get(i).get("partnership"));
			}
			if (!StringUtil.isEmpty(houseIds)) {
				houseIds = houseIds.substring(0, houseIds.length() - 1);
			}
        	dataMap.put("houseIds", houseIds);
        	dataMap.put("houseCount", page.getTotal());
        	dataMap.put("partnershipMap", partnershipMap);
        	logger.info("run to end GetTopicHousesHandler.");
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("GetTopicHousesHandler is failed:活动主题房源列表获取失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
