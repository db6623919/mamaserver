package com.mama.server.main.controller.handler.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.FlowPo;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.HousePricePo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.UserCardPo;
import com.mama.server.main.dao.model.UserInfoPo;
import com.mama.server.util.Json;
import com.mama.server.util.Log;
import com.mama.server.util.RequestChargeFacade;
import com.mama.server.util.SmsUtil;
import com.mmzb.charge.common.util.SmsTask;
import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.entity.VirtualChangeRequest;
import com.mmzb.charge.facade.entity.VirtualChangeResponse;
import com.mmzb.charge.facade.entity.VirtualQueryRequest;
import com.mmzb.charge.facade.entity.VirtualQueryResponse;

@Deprecated
@Component
public class CreateOrderHandler extends BaseHandler {

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("houseId") == null) {
                genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("beginTime") == null) {
                genErrOutputMapWithCode("param error, beginTime required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("endTime") == null) {
                genErrOutputMapWithCode("param error, endTime required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("contactIds") == null) {
                genErrOutputMapWithCode("param error, contactIds required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("liveDetail") == null) {
                genErrOutputMapWithCode("param error, liveDetail required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("totalRoomNum") == null) {
                genErrOutputMapWithCode("param error, totalRoomNum required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String uid = (String)param.get("uid");
            int houseId = (Integer)param.get("houseId");
            String beginTime = (String)param.get("beginTime");
            String endTime = (String)param.get("endTime");
            int totalRoomNum = (Integer)param.get("totalRoomNum");
            HashMap<String, Object> liveDetailMap = (HashMap<String, Object>)param.get("liveDetail");
            ArrayList<Integer> contactIds = (ArrayList<Integer>)param.get("contactIds");
            
            String newOrderId = null;
            boolean used = true;
            while (used) {
                newOrderId = mainService.getNewOrderId();
                OrderPo tmpOrder = new OrderPo();
                tmpOrder.setOrderId(newOrderId);
                List<OrderPo> tmpOrderList = mainService.getOrderByAllParam(tmpOrder);
                if (tmpOrderList == null || tmpOrderList.size() == 0) {
                    break;
                }
            }
            
            liveDetailMap.put("beginTime", beginTime);
            liveDetailMap.put("endTime", endTime);
            String liveDetail = Json.format(liveDetailMap);
            
            // check contact id
            if (contactIds.size() == 0) {
                genErrOutputMapWithCode("contact ids is empty", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            // search house
            HousePo house = new HousePo();
            house.setHouseId(houseId);
            List<HousePo> houseList = mainService.getHouseByAllParam(house);
            if (houseList == null || houseList.size() == 0) {
                genErrOutputMapWithCode("house not exists", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            house = houseList.get(0);
            
            // check date
            String curTime = mainService.getCurrentDate();
            if (beginTime.compareTo(endTime) > 0) {
                genErrOutputMapWithCode("begin time later than end time", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (endTime.compareTo(curTime) < 0) {
                genErrOutputMapWithCode("end time eallier than current time", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            HouseOrderPo houseOrder = new HouseOrderPo();
            
            // check user available amount
            int needFreezeAmt = 0;
            int needTotalAmt = 0;
            Date beginDate = mainService.stringToDate(beginTime);
            Date endDate = mainService.stringToDate(endTime);
            Date tmpDate = (Date) beginDate.clone();
            needFreezeAmt = house.getMemFreezeAmt() * totalRoomNum;
            while (!tmpDate.equals(endDate)) {
                HousePricePo housePrice = new HousePricePo();
                housePrice.setHouseId(houseId);
                housePrice.setDate(tmpDate);
                List<HousePricePo> housePriceList = mainService.getHousePriceByAllParam(housePrice);
                if (housePriceList == null || housePriceList.size() == 0) {
                    //needFreezeAmt += house.getMemFreezeAmt() * totalRoomNum;
                    needTotalAmt += house.getMemTotalAmt() * totalRoomNum;
                } else {
                    //needFreezeAmt += housePriceList.get(0).getMemFreezeAmt() * totalRoomNum;
                    needTotalAmt += housePriceList.get(0).getMemTotalAmt() * totalRoomNum;
                }
                
                tmpDate = mainService.getNextDate(tmpDate);
            }
            
            tmpDate = (Date) beginDate.clone();
            while (!tmpDate.equals(endDate)) {
                houseOrder = new HouseOrderPo();
                houseOrder.setHouseId(houseId);
                houseOrder.setDate(mainService.dateToString(tmpDate));
                Log.SERVICE.error("######" + mainService.dateToString(tmpDate));
                houseOrder.setRemoved(0);
                List<HouseOrderPo> houseOrderList = mainService.getHouseOrderByAllParam(houseOrder);
                if (houseOrderList == null) {
                    genErrOutputMapWithCode("fail to get house order list", ReturnCode.GET_HOUSE_ORDER_ERROR);
                    return outputMap;
                }
                Log.SERVICE.error("######" + houseOrderList.size());
                int total = 0;
                for (int j = 0; j < houseOrderList.size(); ++j) {
                    total += houseOrderList.get(j).getTotalRoomNum();
                }
                Log.SERVICE.error("######" + total);
                Log.SERVICE.error("######" + house.getTotalRoomNum());
                if (house.getTotalRoomNum() - total - totalRoomNum < 0) {
                    genErrOutputMapWithCode("no enough room", ReturnCode.ROOM_NOT_ENOUGH_ERROR);
                    return outputMap;
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
            if (currentAvailable< needFreezeAmt) {
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
            // freeze amount
            VirtualChangeRequest vcr = new VirtualChangeRequest();
            vcr.setMemberID(uid);
            vcr.setMoney(String.valueOf(needFreezeAmt));
            vcr.setOrderNo(newOrderId);
            vcr.setType(VirtualFlowTypeEnum.VIRTUAL_FREEZED);
            vcr.setRemark("支付定金("+liveDetailMap.get("houseName").toString()+")");
            VirtualChangeResponse vcs = RequestChargeFacade.VirtualChange(vcr);
            if (!vcs.isSuccess()) {
                genErrOutputMapWithCode("update user card info error", ReturnCode.UPDATE_USER_CARD_ERROR);
                return outputMap;
            }
            
            // add flow
            FlowPo flow = new FlowPo();
            flow.setType(ConstValue.FLOW_FROZEN);
            flow.setAmt(needFreezeAmt);
            flow.setOperTime(mainService.getCurrentDatetime());
            flow.setShowDetail("");
            flow.setUid(uid);
            flow.setOrderId(newOrderId);
            flow.setShowDetail("支付定金("+liveDetailMap.get("houseName").toString()+")");
            if (mainService.insertFlow(flow) != 0) {
                //disFreezeAmt(userCard, oldFreezeAmt, oldAvailAmt);
                genErrOutputMapWithCode("fail to add flow", ReturnCode.ADD_FLOW_ERROR);
                return outputMap;
            }
            
            // create order
            OrderPo order = new OrderPo();
            order.setOrderId(newOrderId);
            order.setUid(uid);
            order.setHouseId(houseId);
            order.setOperTime(mainService.getCurrentDatetime());
            order.setStatus(ConstValue.ORDER_MERCHANT_VERIFYING);
            order.setFreezeAmt(needFreezeAmt);
            order.setTotalAmt((int)(needTotalAmt * discount));
            order.setTotalRoomNum(totalRoomNum);
            order.setVerifyCode("");
            order.setLiveDetail(liveDetail); 
            if (mainService.insertOrder(order) != 0) {
               // disFreezeAmt(userCard, oldFreezeAmt, oldAvailAmt);
                genErrOutputMapWithCode("fail to create order", ReturnCode.CREATE_ORDER_ERROR);
                return outputMap;
            }
            
            // create house order
            tmpDate = (Date) beginDate.clone();
            List<HouseOrderPo> createdHouseOrderList = new ArrayList<HouseOrderPo>();
            while (!tmpDate.equals(endDate)) {
                houseOrder = new HouseOrderPo();
                houseOrder.setOrderId(order.getOrderId());
                houseOrder.setHouseId(houseId);
                houseOrder.setUid(uid);
                houseOrder.setTotalRoomNum(totalRoomNum);
                houseOrder.setDate(mainService.dateToString(tmpDate));
                if (mainService.insertHouseOrder(houseOrder) != 0) {
                    //disFreezeAmt(userCard, oldFreezeAmt, oldAvailAmt);
                    order.setRemoved(1);
                    mainService.updateOrder(order);
                    for (int i = 0; i < createdHouseOrderList.size(); ++i) {
                        createdHouseOrderList.get(i).setRemoved(1);
                        mainService.updateHouseOrderById(createdHouseOrderList.get(i));
                    }
                    genErrOutputMapWithCode("fail to create order", ReturnCode.CREATE_ORDER_ERROR);
                    return outputMap;
                }
                tmpDate = mainService.getNextDate(tmpDate);
            }
            
            //短信通知妈妈后台
            String msg = null;
            //SmsUtil.genMamaCreateOrderNotice(liveDetailMap.get("buildingName").toString(),newOrderId, liveDetailMap.get("phone").toString());
            SmsUtil.sendSms("15817459283", msg);
            
            //短信通知开发商
            msg = SmsUtil.genMerchantNotify(liveDetailMap.get("houseName").toString(), newOrderId);
            SmsUtil.sendSms(liveDetailMap.get("phone").toString(), msg);
            
            //TODO:短信通知用户
            String userNo = liveDetailMap.get("linkmanPhone").toString();
            String userMsg = SmsUtil.genCreateOrderNotice2User(beginTime, endTime, liveDetailMap.get("buildingName").toString());
            SmsUtil.sendSms(userNo, userMsg);            
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private boolean disFreezeAmt(UserCardPo userCard, int oldFreezeAmt, int oldAvailAmt) {
        userCard.setAvailAmt(oldAvailAmt);
        userCard.setFreezeAmt(oldFreezeAmt);
        return mainService.updateUserCard(userCard) == 0;
    }
}
