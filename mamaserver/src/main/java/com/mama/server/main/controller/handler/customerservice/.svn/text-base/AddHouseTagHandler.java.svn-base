package com.mama.server.main.controller.handler.customerservice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.Flags.Flag;

import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseTag;

@Component
public class AddHouseTagHandler extends BaseHandler {
    
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {

            String name = (String)param.get("name");
            String id = (String)param.get("id");
            String isDispayOnSearch = (String)param.get("isDispayOnSearch");
            
            HouseTag houseTag = new HouseTag();
            
            houseTag.setId(Integer.parseInt(id));          
            houseTag.setName(name);
            String flag = (String)param.get("flag");
            List<HouseTag> list = new ArrayList<HouseTag>();
            if (!"del".equals(flag)) {
            	list = mainService.duplicateHouseTagByPar(houseTag);
			}
           
            
            if ("add".equals(flag)) {
                if (null!=list && 0 !=list.size()) {
                		genErrOutputMapWithCode("fail to add insHouseTag,"+id+"Already exist", ReturnCode.ADD_HOUSE_TAG);
    	            	return outputMap;
    			}else {
    				//新增
    				houseTag.setIsDispayOnSearch(Integer.parseInt(isDispayOnSearch));
    				if (mainService.insHouseTag(houseTag) != 0) {
    	            	genErrOutputMapWithCode("fail to add insHouseTag", ReturnCode.ADD_HOUSE_TAG);
    	            	return outputMap;
    	            }
                    
    			}
			}
            else if ("update".equals(flag)) {
            	HouseTag tag = new HouseTag();
            	tag.setName(name);
            	List<HouseTag> tagslist = mainService.getHouseTagByPar(tag);
            	if (null!=tagslist && 0 !=tagslist.size()) {
            		String tId = tagslist.get(0).getId().toString();
            		if (!tId.equals(id)) {
            			genErrOutputMapWithCode("fail to add updateHouseTag,"+name+"Already exist", ReturnCode.ADD_HOUSE_TAG);
    	            	return outputMap;
					}
			     }
            	
					//update
            	houseTag.setIsDispayOnSearch(Integer.parseInt(isDispayOnSearch));
	            	if (mainService.updateHouseTag(houseTag)!=0) {
	            		genErrOutputMapWithCode("fail to add updateHouseTag", ReturnCode.ADD_HOUSE_TAG);
		            	return outputMap;
					}
			}
            else if ("del".equals(flag)) {
            	//del
            	houseTag.setRemoved(1);
            	if (mainService.updateHouseTag(houseTag)!=0) {
            		genErrOutputMapWithCode("fail to add delHouseTag", ReturnCode.ADD_HOUSE_TAG);
	            	return outputMap;
				}
			}
            putRedis();
            genSuccOutputMap();
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
		redisRequest.setKey("mmsf:allHouseTag");
		redisRequest.setValue(val);
		redisFacade.putKeyAndValue(redisRequest);

    }
    
}
