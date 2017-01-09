package com.mama.server.main.controller.handler.merchant;
import java.math.BigDecimal;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;
import com.mama.server.util.Log;
import com.mama.server.util.RequestChargeFacade;
import com.mama.server.util.SmsUtil;
import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.entity.VirtualChangeRequest;
import com.mmzb.charge.facade.entity.VirtualChangeResponse;
import com.mmzb.charge.facade.entity.VirtualQueryRequest;
import com.mmzb.charge.facade.entity.VirtualQueryResponse;

@Component
public class ContinueOrderHandler extends BaseHandler {
    
    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("orderId") == null) {
                genErrOutputMapWithCode("param error, orderId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("endTime") == null) {
                genErrOutputMapWithCode("param error, endTime required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String orderId = (String)param.get("orderId");
            String endTime = (String)param.get("endTime");
            
            OrderPo order = new OrderPo();
            order.setOrderId(orderId);
            List<OrderPo> orderList = mainService.getOrderByAllParam(order);
            if (orderList == null || orderList.size() == 0) {
                genErrOutputMapWithCode("param error, invalid orderId", ReturnCode.GET_ORDER_ERROR);
                return outputMap;
            }
            order = orderList.get(0);
            
            HousePo house = new HousePo();
            house.setHouseId(order.getHouseId());
            List<HousePo> houseList = mainService.getHouseByAllParam(house);
            house = houseList.get(0);

            HashMap<String, Object> summaryInfo = (HashMap<String, Object>)Json.parse(order.getLiveDetail(), Object.class);
            String houseName = "";
            if (summaryInfo.get("houseName") != null) {
                houseName = (String)summaryInfo.get("houseName");
            }
            
            int cityId = house.getCityId();
            int bldId = house.getBldId();
            
            CityPo city = null;
            List<CityPo> cityList = mainService.getCity();
            if (cityList == null || cityList.size() == 0) {
                genErrOutputMapWithCode("fail to get city list", ReturnCode.GET_CITY_ERROR);
                return outputMap;
            }
            for (int i = 0; i < cityList.size(); ++i) {
                if (cityList.get(i).getCityId() == cityId) {
                    city = cityList.get(i);
                    break;
                }
            }
            if (city == null) {
                genErrOutputMapWithCode("fail to get city", ReturnCode.GET_CITY_ERROR);
                return outputMap;
            }
            String cityName = city.getName();
            
            BuildingPo building = null;
            List<BuildingPo> buildingList = mainService.getBuilding(building);
            if (buildingList == null || buildingList.size() == 0) {
                genErrOutputMapWithCode("fail to get building list", ReturnCode.GET_BUILDING_ERROR);
                return outputMap;
            }
            for (int i = 0; i < buildingList.size(); ++i) {
                if (buildingList.get(i).getBldId() == bldId) {
                    building = buildingList.get(i);
                    break;
                }
            }
            if (building == null) {
                genErrOutputMapWithCode("fail to get building", ReturnCode.GET_BUILDING_ERROR);
                return outputMap;
            }
            String buildingName = building.getName();
            
            String uid = order.getUid();
            
            UserInfoPo userInfo = new UserInfoPo();
            userInfo.setUid(uid);
            userInfo = mainService.getUserinfoByAllParam(userInfo);
            if (userInfo == null) {
                genErrOutputMapWithCode("fail to get user info", ReturnCode.GET_USER_INFO_ERROR);
                return outputMap;
            }
            String phone = userInfo.getPhone();
            
            HouseOrderPo houseOrder = new HouseOrderPo();
            houseOrder.setOrderId(orderId);
            List<HouseOrderPo> houseOrderList = mainService.getHouseOrderByAllParam(houseOrder);
            if (houseOrderList == null || houseOrderList.size() == 0) {
                genErrOutputMapWithCode("fail to get house order", ReturnCode.GET_HOUSE_ORDER_ERROR);
                return outputMap;
            }
            
            String oldEndTime = "0000-00-00";
            for (int i = 0; i < houseOrderList.size(); ++i) {
                if (endTime.compareTo(houseOrderList.get(i).getDate()) <= 0) {
                    genErrOutputMapWithCode("param error, invalid end time", ReturnCode.GET_ORDER_ERROR);
                    return outputMap;
                }
                Log.SERVICE.error("#########" + houseOrderList.get(i).getDate());
                if (houseOrderList.get(i).getDate().compareTo(oldEndTime) > 0) {
                    oldEndTime = houseOrderList.get(i).getDate();
                }
                Log.SERVICE.error("#########" + oldEndTime);
            }
            
            // check user available amount
            int continueAmt = 0;
            Date beginDate = mainService.stringToDate(oldEndTime);
            Date endDate = mainService.stringToDate(endTime);
            Date tmpDate = (Date) beginDate.clone();
            tmpDate = mainService.getNextDate(tmpDate);
            while (!tmpDate.equals(endDate)) {
                HousePricePo housePrice = new HousePricePo();
                housePrice.setHouseId(order.getHouseId());
                housePrice.setDate(tmpDate);
                List<HousePricePo> housePriceList = mainService.getHousePriceByAllParam(housePrice);
                if (housePriceList == null || housePriceList.size() == 0) {
                    continueAmt += house.getMemTotalAmt() * order.getTotalRoomNum();
                } else {
                    continueAmt += housePriceList.get(0).getMemTotalAmt() * order.getTotalRoomNum();;
                }
                tmpDate = mainService.getNextDate(tmpDate);
            }
            
            VirtualQueryRequest vqr = new VirtualQueryRequest();
            vqr.setMemberID(uid);
            VirtualQueryResponse vqs = RequestChargeFacade.virtualQuery(vqr);
            if (!vqs.isSuccess()) {
                genErrOutputMapWithCode("get user card info error", ReturnCode.USER_CARD_NOT_EXISTED_ERROR);
                return outputMap;
            }
            int currentAvailable = Integer.parseInt(vqs.getAvailableVirtual());
            if (currentAvailable < continueAmt) {
                genErrOutputMapWithCode("available amount not enough to freeze", ReturnCode.AMOUNT_NOT_ENOUGH_ERROR);
                return outputMap;
            }
            int total = Integer.parseInt(vqs.getTotalVirtual());
            double discount = 1.0;
            if (total >= 10000 && total < 30000) {
            	 discount = 0.99;
            } else if (total >= 30000 && total < 100000) {
            	discount = 0.98;
            } else if (total >= 100000 && total < 300000) {
            	discount = 0.97;
            } else if (total >= 300000){
            	discount = 0.95;
            }
            // 扣款
            VirtualChangeRequest vcr = new VirtualChangeRequest();
            vcr.setMemberID(uid);
            vcr.setMoney(new BigDecimal(continueAmt*discount).setScale(0, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            vcr.setOrderNo(order.getOrderId());
            vcr.setType(VirtualFlowTypeEnum.VIRTUAL_PAY);
            vcr.setRemark("支付续房房费("+houseName+")");
            VirtualChangeResponse vcs = RequestChargeFacade.VirtualChange(vcr);
            if (!vcs.isSuccess()) {
                genErrOutputMapWithCode("fail to update user card", ReturnCode.UPDATE_USER_CARD_ERROR);
                return outputMap;
            }
            
            // create house order
            beginDate = mainService.stringToDate(oldEndTime);
            endDate = mainService.stringToDate(endTime);
            tmpDate = (Date) beginDate.clone();
            tmpDate = mainService.getNextDate(tmpDate);
            while (!tmpDate.equals(endDate)) {
                houseOrder = new HouseOrderPo();
                houseOrder.setOrderId(order.getOrderId());
                houseOrder.setHouseId(order.getHouseId());
                houseOrder.setUid(order.getUid());
                houseOrder.setDate(mainService.dateToString(tmpDate));
                houseOrder.setTotalRoomNum(order.getTotalRoomNum());
                if (mainService.insertHouseOrder(houseOrder) != 0) {
                    genErrOutputMapWithCode("fail to continue order", ReturnCode.CONTINUE_ORDER_ERROR);
                    return outputMap;
                }
                tmpDate = mainService.getNextDate(tmpDate);
            }
            
            // add flow
            FlowPo flow = new FlowPo();
            flow.setType(ConstValue.FLOW_PAY);
            flow.setAmt(continueAmt);
            flow.setOperTime(mainService.getCurrentDatetime());
            flow.setShowDetail("");
            flow.setUid(order.getUid());
            flow.setOrderId(orderId);
            if (mainService.insertFlow(flow) != 0) {
                genErrOutputMapWithCode("fail to add flow", ReturnCode.ADD_FLOW_ERROR);
                return outputMap;
            }
            
            HashMap<String, Object> liveDetailMap = (HashMap<String, Object>)Json.parse(order.getLiveDetail(), Object.class);
            liveDetailMap.put("endTime", endTime);
            order.setLiveDetail(Json.format(liveDetailMap));
            order.setTotalAmt(new BigDecimal(continueAmt*discount).add(new BigDecimal(order.getTotalAmt())).intValue());
            if (mainService.updateOrder(order) != 0) {
                genErrOutputMapWithCode("fail to update order", ReturnCode.UPDATE_ORDER_ERROR);
                return outputMap;
            }
            
            // 发短信
            String msg = SmsUtil.genContinueOrderSms(phone, houseName, endTime,order.getOrderId());
            SmsUtil.sendSms(phone, msg);
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
