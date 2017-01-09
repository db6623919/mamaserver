package com.mama.server.main.controller.handler.merchant;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;
import com.mama.server.util.RequestChargeFacade;
import com.meidusa.fastjson.JSONArray;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.entity.VirtualChangeRequest;
import com.mmzb.charge.facade.entity.VirtualChangeResponse;
import com.mmzb.charge.facade.entity.VirtualQueryRequest;
import com.mmzb.charge.facade.entity.VirtualQueryResponse;

@Component
public class ReturnOrderHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
    	
    	@SuppressWarnings("unchecked")
		List<Long> hotelCouponIds = (List<Long>) param.get("hotelCouponIds");
    	
        try {
            if (param.get("orderId") == null) {
                genErrOutputMapWithCode("param error, orderId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("reason") == null) {
                genErrOutputMapWithCode("param error, reason required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
//            if (param.get("amt") == null) {
//                genErrOutputMapWithCode("param error, amt required", ReturnCode.PARAM_ERROR);
//                return outputMap;
//            }
            
            if (param.get("startDate") == null) {
                genErrOutputMapWithCode("param error, startTime required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String orderId = (String)param.get("orderId");
            String reason = (String)param.get("reason");
            String startDate = (String)param.get("startDate");
//            int amt = (int)param.get("amt");
            
            OrderPo order = new OrderPo();
            order.setOrderId(orderId);
            List<OrderPo> orderList = mainService.getOrderByAllParam(order);
            if (orderList == null || orderList.size() == 0) {
                genErrOutputMapWithCode("param error, invalid orderId", ReturnCode.GET_ORDER_ERROR);
                return outputMap;
            }
            order = orderList.get(0);
            
//            VirtualQueryRequest vqr = new VirtualQueryRequest();
//            vqr.setMemberID(order.getUid());
//            VirtualQueryResponse vqs = RequestChargeFacade.virtualQuery(vqr);
//            if (!vqs.isSuccess()) {
//                genErrOutputMapWithCode("get user card info error", ReturnCode.USER_CARD_NOT_EXISTED_ERROR);
//                return outputMap;
//            }
//            int total = Integer.parseInt(vqs.getTotalVirtual());
//            double discount = 1.0;
//            if (total >= 10000 && total < 30000) {
//            	 discount = 0.99;
//            } else if (total >= 30000 && total < 100000) {
//            	discount = 0.98;
//            } else if (total >= 100000 && total < 300000) {
//            	discount = 0.97;
//            } else if (total >= 300000){
//            	discount = 0.95;
//            }
//            
            HousePo house = new HousePo();
            house.setHouseId(order.getHouseId());
            List<HousePo> houseList = mainService.getHouseByAllParam(house);
            if (houseList == null || houseList.size() == 0) {
                genErrOutputMapWithCode("fail to find house", ReturnCode.GET_HOUSE_ERROR);
                return outputMap;
            }
            house = houseList.get(0);
            
//            HousePricePo housePrice = new HousePricePo();
//            housePrice.setHouseId(order.getHouseId());
//            List<HousePricePo> housePriceList = mainService.getHousePriceByAllParam(housePrice);
//            if (housePriceList == null) {
//                genErrOutputMapWithCode("fail to find house price", ReturnCode.GET_HOUSE_ORDER_ERROR);
//                return outputMap;
//            }
//            
            HouseOrderPo houseOrder = new HouseOrderPo();
            houseOrder.setHouseId(order.getHouseId());
            houseOrder.setOrderId(orderId);
            houseOrder.setRemoved(0);
            
            List<HouseOrderPo> houseOrderList = mainService.getHouseOrderByAllParam(houseOrder);
            if (houseOrderList == null) {
                genErrOutputMapWithCode("fail to find house order", ReturnCode.GET_HOUSE_ORDER_ERROR);
                return outputMap;
            }
//            
//            int amt = 0;
//            for (int i = 0; i < houseOrderList.size(); ++i) {
//                String date = houseOrderList.get(i).getDate();
//                if (date.compareTo(startDate) >= 0) {
//                    int price = house.getMemTotalAmt();
//                    for (int j = 0; j < housePriceList.size(); ++j) {
//                        if (mainService.dateToString(housePriceList.get(j).getDate()).compareTo(date) == 0) {
//                            price = housePriceList.get(j).getMemTotalAmt();
//                            break;
//                        }
//                    }
//                    amt += price * discount;
//                }
//            }
//            
//            if (amt <= 0 || amt > order.getTotalAmt()) {
//                genErrOutputMapWithCode("return amt larger than order amt", ReturnCode.PARAM_ERROR);
//                return outputMap;
//            }
            
            //退房之后释放房源
            HouseOrderPo ho=new HouseOrderPo();
			ho.setRemoved(1);
			ho.setOrderId(order.getOrderId());
			ho.setDate(startDate);
			mainService.updateHouseOrderByOrderId(ho);
			
			//修改旅居券的状态
//			if (hotelCouponIds != null && hotelCouponIds.size() > 0)
//				mainService.updateHotelCouponStatus(hotelCouponIds, 11);// 使用旅居券
			
			// 修改旅居券的状态
			if (StringUtils.isNotBlank(order.getLiveDetail())) {
				JSONObject jsonObject = JSONObject.parseObject(order.getLiveDetail());
				String hotelCouponIdsKey = "hotelCouponIds";
				if (jsonObject.containsKey(hotelCouponIdsKey)) {
					JSONArray jsonArray = jsonObject.getJSONArray(hotelCouponIdsKey);
					if (jsonArray != null && jsonArray.size() > 0) {
						List<Long> ids = new ArrayList<Long>();
						for (int i = 0; i < jsonArray.size(); i++) {
							ids.add(jsonArray.getLongValue(i));
						}
						mainService.updateHotelCouponStatus(ids, 11);
					}
				}
			}
			
            @SuppressWarnings("unchecked")
            HashMap<String, Object> liveDetailMap = (HashMap<String, Object>)Json.parse(order.getLiveDetail(), Object.class);
            liveDetailMap.put("returnReason", reason);
            order.setLiveDetail(Json.format(liveDetailMap));
            order.setStatus(ConstValue.ORDER_MERCHANT_CHECKOUT);
            if (mainService.updateOrder(order) != 0) {
                genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
                return outputMap;
            }
            
            VirtualChangeRequest vcr = new VirtualChangeRequest();
            vcr.setMemberID(order.getUid());
//            vcr.setMoney(String.valueOf(amt));
            vcr.setOrderNo(order.getOrderId());
            vcr.setType(VirtualFlowTypeEnum.VIRTUAL_REFUND);
            vcr.setRemark("提前退房退款("+liveDetailMap.get("houseName").toString()+")");
            VirtualChangeResponse vcs = RequestChargeFacade.VirtualChange(vcr);
            if (!vcs.isSuccess()) {
                genErrOutputMapWithCode("update user card info error", ReturnCode.UPDATE_USER_CARD_ERROR);
                return outputMap;
            }
            
            FlowPo flow = new FlowPo();
            flow.setType(ConstValue.FLOW_REFUND);
//            flow.setAmt(amt);
            flow.setOperTime(mainService.getCurrentDatetime());
            flow.setShowDetail("");
            flow.setUid(order.getUid());
            flow.setOrderId(orderId);
            if (mainService.insertFlow(flow) != 0) {
                genErrOutputMapWithCode("fail to add flow", ReturnCode.ADD_FLOW_ERROR);
                return outputMap;
            }
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
