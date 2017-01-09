package com.mama.server.main.controller.handler.activity;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ActivityEnrollPo;
import com.mama.server.main.service.imp.RandomLuckyBeanServiceImpl;

/**
 * 单身paty活动报名添加
 * @author Administrator
 *
 */
@Component
public class ActivityEnrollAddHandler extends BaseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(RandomLuckyBeanServiceImpl.class);
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	if (logger.isInfoEnabled()) {
        		logger.info("run to start ActivityEnrollAddHandler.");
        	}
        	//String a = (String)param.get("activityEnroll");
        	//JSONObject jsonObject = JSONObject.fromObject(param.get("activityEnroll"));
        	//ActivityEnrollPo activityEnroll = (ActivityEnrollPo) JSONObject.toBean(jsonObject, ActivityEnrollPo.class);
        	String name = (String)param.get("name");
        	String age = (String)param.get("age");
        	int sex = (Integer)param.get("sex");
        	String phone = (String)param.get("phone");
        	String weChat = (String)param.get("weChat");
        	int educated = (Integer)param.get("educated");
        	ActivityEnrollPo activityEnroll = new ActivityEnrollPo();
        	activityEnroll.setName(name);
        	activityEnroll.setAge(age);
        	activityEnroll.setSex(sex);
        	activityEnroll.setPhone(phone);
        	activityEnroll.setWeChat(weChat);
        	activityEnroll.setEducated(educated);
        	activityEnroll.setStatus(0);
        	
        	int result = mainService.insertActivityEnroll(activityEnroll);
        	if(result < 1) {
        		 logger.error("ActivityEnrollAddHandler is add failed,result:{}",result);
        		 genErrOutputMapWithCode("ActivityEnrollAddHandler error, ActivityEnrollAddHandler is add failed", ReturnCode.PARAM_ERROR);
                 return outputMap;
        	} 
            genSuccOutputMap();
        } catch (Exception e) {
        	logger.error("ActivityEnrollAddHandler is failed:报名失败!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
  
}
