package com.mama.server.main.controller.handler.customerservice;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

/**
 * 活动报名管理
 * @author dengbiao
 *
 */
@Component
public class GetActivityEnrollsHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            
        	List<ActivityEnrollPo> list = new ArrayList<ActivityEnrollPo>();
        	List<ActivityEnrollPo> aeList = new ArrayList<ActivityEnrollPo>();
        	List<ActivityEnrollPo> list2 = new ArrayList<ActivityEnrollPo>();
        	List<ActivityEnrollPo> list3 = new ArrayList<ActivityEnrollPo>();
        	HashMap<String, Object> par = new HashMap<String, Object>();
        	par.put("status", 0);//未确认状态
        	list2 = mainService.getActivityEnrolls(par);
        	
        	par.put("status", 1);//确认状态
        	list3 = mainService.getActivityEnrolls(par);
        	
        	list = mainService.getActivityEnrolls(param);
        	aeList = mainService.getActivityEnrolls(null);
        	dataMap.put("list", list);
        	dataMap.put("num", aeList.size());
        	dataMap.put("NoConfirmNum", list2.size());
        	dataMap.put("ConfirmNum", list3.size());
        	
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
}
