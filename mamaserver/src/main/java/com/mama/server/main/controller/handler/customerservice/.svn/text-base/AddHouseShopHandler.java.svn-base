package com.mama.server.main.controller.handler.customerservice;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.server.common.util.Contants;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.main.dao.model.mongodb.HouseShop;

@Component
public class AddHouseShopHandler extends BaseHandler {
    
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            
        	String flag = (String)param.get("flag");
        	String shopName = "";
        	String shopDesc = "";
        	String bossImage = "";
        	String bossPhone = "";
        	String bossWeChat = "";
        	String imgUrl = "";
        	String level = "";
        	String type = "";
        	String bossName = "";
        	String partnership = "";
        	String recommended_status = "";
        	int cityID = 0;
        	List<HouseShopPo> List = new ArrayList<HouseShopPo>();
        	HouseShopPo houseShop = new HouseShopPo();
        	if (!"del".equals(flag)) {
        		shopName = (String)param.get("shopName");
                shopDesc = (String)param.get("shopDesc");
                bossImage = (String)param.get("bossImage");
                bossPhone = (String)param.get("bossPhone");
                bossWeChat = (String)param.get("bossWeChat");
                
                imgUrl = (String)param.get("imgUrl");
                level = (String)param.get("level");
                type = (String)param.get("type");
                bossName = (String)param.get("bossName");
                partnership = (String)param.get("partnership");
                
                if (null!=shopName&&!"".equals(shopName)) {
                	houseShop.setShopName(shopName);
                    List = mainService.getHouseShopByPar(houseShop);
				}
                
        	}
            
            
            if ("add".equals(flag)) {
            	cityID = (Integer) param.get("cityID");
    				//新增
	                houseShop.setShopDesc(shopDesc);
	                houseShop.setBossImage(bossImage);
	                houseShop.setBossPhone(bossPhone);
	                houseShop.setBossWeChat(bossWeChat);
	                houseShop.setCityID(cityID);
	                houseShop.setImgUrl(imgUrl);
	                houseShop.setLevel(level);
	                houseShop.setType(type);
	                houseShop.setBossName(bossName);
	                houseShop.setPartnership(partnership);
    				if (mainService.insHouseShop(houseShop) != 0) {
    	            	genErrOutputMapWithCode("fail to add insHouseShop", ReturnCode.ADD_HOUSE_SHOP);
    	            	return outputMap;
    	            }
    				addHoseShopToMango(houseShop);
			}
            else if ("update".equals(flag)) {
            	String id = param.get("id").toString();
            	
				if (null!=List && 0 !=List.size()) {
					HouseShopPo shop = new HouseShopPo();
					shop = List.get(0);
					if (shop.getId()!= Integer.parseInt(id)) {
						genErrOutputMapWithCode("fail to updateHouseShop,"+shopName+"Already exist", ReturnCode.ADD_HOUSE_SHOP);
		            	return outputMap;
					}
			      }
					//update
            	if (null!=param.get("recommended_status")&&!"".equals(param.get("recommended_status"))) {
            		recommended_status = param.get("recommended_status").toString();
            		if (Contants.is_recommended_status.equals(recommended_status)) {
            			houseShop.setRecommended_status(Integer.parseInt(Contants.not_recommended_status));
					}else {
						houseShop.setRecommended_status(Integer.parseInt(Contants.is_recommended_status));
					}
				}else {
					houseShop.setRecommended_status(2);
				}
					houseShop.setId(Integer.parseInt(id));
	                houseShop.setShopDesc(shopDesc);
	                if (null!=bossImage && !"".equals(bossImage)) {
	                	houseShop.setBossImage(bossImage);
					}
	                houseShop.setBossPhone(bossPhone);
	                houseShop.setBossWeChat(bossWeChat);
	                if (param.get("cityID")!=null) {
	                	cityID = (Integer) param.get("cityID");
	                	houseShop.setCityID(cityID);
					}
	                
	                if (null!=imgUrl && !"".equals(imgUrl)) {
	                	houseShop.setImgUrl(imgUrl);
	                }
	                houseShop.setLevel(level);
	                houseShop.setType(type);
	                houseShop.setBossName(bossName);
	                houseShop.setPartnership(partnership);
	                
	            	if (mainService.updateHouseShop(houseShop)!=0) {
	            		genErrOutputMapWithCode("fail to update HouseShop", ReturnCode.ADD_HOUSE_SHOP);
		            	return outputMap;
					}
	            	updateHoseShopToMango(houseShop);
			} else if ("del".equals(flag)) {
            	//del
				String id = param.get("id").toString();
            	if (mainService.delHouseShop(Integer.parseInt(id))!=0) {
            		genErrOutputMapWithCode("fail to del HouseShop", ReturnCode.ADD_HOUSE_SHOP);
	            	return outputMap;
				}
            	deleteHouseShopToMangodbById(id);
            	
                //删除topic_shop中的对应记录
                TopicShopPo topicShopPo = new TopicShopPo();
                topicShopPo.setShopId(Integer.parseInt(id));
                mainService.removeTopicShop(topicShopPo);
			}
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    /*
     * add店铺到mangodb
     */
    private void addHoseShopToMango(HouseShopPo houseShop){
    	HouseShop houseShopPo = new HouseShop();
    	houseShopPo.setId(String.valueOf(houseShop.getId()));
    	houseShopPo.setBossImage(houseShop.getBossImage());
    	houseShopPo.setBossPhone(houseShop.getBossPhone());
    	houseShopPo.setBossWeChat(houseShop.getBossWeChat());
    	houseShopPo.setShopDesc(houseShop.getShopDesc());
    	houseShopPo.setShopName(houseShop.getShopName());
    	houseShopPo.setCityID(houseShop.getCityID());
    	houseShopPo.setImgUrl(houseShop.getImgUrl());
    	houseShopPo.setLevel(houseShop.getLevel());
    	houseShopPo.setType(houseShop.getType());
    	houseShopPo.setBossName(houseShop.getBossName());
    	houseShopPo.setPartnership(houseShop.getPartnership());
    	iHouseShopService.addHouseShopPo(houseShopPo);   	
    }
    
    /**
     * update 店铺mangodb
     */
    private void updateHoseShopToMango(HouseShopPo houseShop){
    	HouseShop houseShopPo = new HouseShop();
    	houseShopPo.setId(String.valueOf(houseShop.getId()));
    	houseShopPo.setBossImage(houseShop.getBossImage());
    	houseShopPo.setBossPhone(houseShop.getBossPhone());
    	houseShopPo.setBossWeChat(houseShop.getBossWeChat());
    	houseShopPo.setShopDesc(houseShop.getShopDesc());
    	houseShopPo.setShopName(houseShop.getShopName());
    	houseShopPo.setCityID(houseShop.getCityID());
    	houseShopPo.setImgUrl(houseShop.getImgUrl());
    	houseShopPo.setLevel(houseShop.getLevel());
    	houseShopPo.setType(houseShop.getType());
    	houseShopPo.setBossName(houseShop.getBossName());
    	houseShopPo.setPartnership(houseShop.getPartnership());
    	iHouseShopService.updateHouseShopPo(houseShopPo);   	
    }
    
    /**
     * 删除 店铺mangodb
     */
    private void deleteHouseShopToMangodbById(String shop_id){
    	iHouseShopService.delHouseShopPo(shop_id);
    }
    
    
    
        
}