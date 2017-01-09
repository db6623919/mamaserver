package com.mama.server.main.controller.handler.main.n99;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.OrderPo;

/**
 * 新增房券
 * @author dengbiao
 *
 */
@Component
public class AppAddHouseCardHandler extends BaseHandler {
	
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {    	      	
            if (param.get("uid") == null || param.get("orderId")==null || param.get("houseId")==null || param.get("shopId")==null || param.get("phone")==null
            		|| param.get("totalRoomNum") == null || param.get("cardPrice")==null ) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
        	String orderId = param.get("orderId").toString();
        	String uid = param.get("uid").toString();
        	int houseId = (Integer)param.get("houseId");
        	int shopId = (Integer)param.get("shopId");
        	String phone = param.get("phone").toString();
        	int totalRoomNum = (Integer)param.get("totalRoomNum");
        	int cardPrice = (Integer)param.get("cardPrice");
        	
        	String No = creatNo(cardPrice);
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("orderNo", orderId);
        	List<HouseCardPo> list = mainService.getHouseCardListByOrderId(map);
        	
        	HousePo hp = new HousePo();
        	hp.setHouseId(houseId);
        	List<HousePo> houseList = mainService.getHouseByAllParam(hp);
        	int price = 0;
        	if (houseList!= null ) {
        		price = houseList.get(0).getMemTotalAmt();
			}
        	
        	if (list.size()==0) {
            	for (int i = 0; i < totalRoomNum; i++) {
            		HouseCardPo houseCardPo = new HouseCardPo();
            		houseCardPo.setHouseId(houseId);
            		houseCardPo.setBuyId(uid);
            		houseCardPo.setBuyPhone(phone);
            		houseCardPo.setCardNo(orderId + (i+1));
            		houseCardPo.setOrderNo(orderId);
            		houseCardPo.setShopId(shopId);
            		houseCardPo.setUseStatus(0);//未使用
            		houseCardPo.setEndDate("2017-03-31");//有效期 暂定 
            		houseCardPo.setCardPrice(price);
            		mainService.addHouseCard(houseCardPo);
    			}
			}
        	
        	OrderPo order = new OrderPo();
        	order.setOrderId(orderId);
        	order.setRetentionTime("2017-03-31");
        	if (mainService.updateOrder(order)==1) {
				log.info("更新 retentionTime 成功！");
			}
        	
        	genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
	}

	private String creatNo(int cardPrice) {
		String price = String.valueOf(cardPrice);
		if (cardPrice<1000) {
			price = price.substring(0,1);
			if (price.equals("")) {
				price = "00";
			}else {
				price = "0"+price;
			}
		}else {
			price = price.substring(0,2);
		}
		
		return price;
	}

}
