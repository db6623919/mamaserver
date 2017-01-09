package com.mama.server.main.controller.handler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.util.Log;

@Component
public class GetOrdersHandler extends BaseHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOrdersHandler.class);

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
    	LOGGER.debug("start to get orders, param is {}.", param.toString());
        try {
            String uid = null;
            String phone = null;
            if (param.get("uid") != null) {
                uid = (String)param.get("uid");
            }
            if (param.get("phone") != null) {
            	phone = (String)param.get("phone");
            }
            List<Integer> statuses = null;
            if (param.get("statuses") != null) {
                statuses = (List<Integer>)param.get("statuses");
            }
            List<String> orderIds = null;
            if (param.get("orderIds") != null) {
                orderIds = (List<String>)param.get("orderIds");
            }
            List<Integer> houseIds = null;
            if (param.get("houseIds") != null) {
                houseIds = (List<Integer>)param.get("houseIds");
            }
            String lowerOperTime = null;
            if (param.get("lowerOperTime") != null) {
                lowerOperTime = (String)param.get("lowerOperTime");
            }
            String upperOperTime = null;
            if (param.get("upperOperTime") != null) {
                upperOperTime = (String)param.get("upperOperTime");
            }
            List<String> devIds = null;
            if (param.get("devIds") != null) {
                devIds = (List<String>)param.get("devIds");
            }
            List<String> bldIds = null;
            if (param.get("bldIds") != null) {
                bldIds = (List<String>)param.get("bldIds");
            }
            List<String> cityIds = null;
            if (param.get("cityIds") != null) {
                cityIds = (List<String>)param.get("cityIds");
            }

            int pageNum = -1;
            int pageCount = -1;
            if (param.get("pageNum") != null && param.get("pageCount") != null) {
                pageNum = (Integer)param.get("pageNum");
                pageCount = (Integer)param.get("pageCount");
                
                if (pageNum <= 0 || pageCount <= 0) {
                    genErrOutputMapWithCode("param error, invalid pageNum/pageCount", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
            }
            
            OrderPo order = new OrderPo();
            order.setOrderType(1);
            List<OrderPo> orderList = mainService.getOrderByAllParam(order);
            if (orderList == null) {
                genErrOutputMapWithCode("fail to get orders", ReturnCode.GET_ORDER_ERROR);
                return outputMap;
            }
            
            List<OrderPo> tmpOrderList = new ArrayList<OrderPo>();
            if (uid != null) {
                for (int i = 0; i < orderList.size(); ++i) {
                    if (orderList.get(i).getUid().compareTo(uid) == 0) {
                        tmpOrderList.add(orderList.get(i));
                    }
                }
                orderList = tmpOrderList;
            }
            if (phone != null) {
                for (int i = 0; i < orderList.size(); ++i) {
                    if (orderList.get(i).getLiveDetail().indexOf(phone) != -1) {
                        tmpOrderList.add(orderList.get(i));
                    }
                }
                orderList = tmpOrderList;
            }
            
            tmpOrderList = new ArrayList<OrderPo>();
            if (statuses != null && statuses.size() != 0) {
                for (int i = 0; i < orderList.size(); ++i) {
                    if (statuses.contains(orderList.get(i).getStatus())) {
                        tmpOrderList.add(orderList.get(i));
                    }
                }
                orderList = tmpOrderList;
            }
            
            tmpOrderList = new ArrayList<OrderPo>();
            if (orderIds != null && orderIds.size() != 0) {
                for (int i = 0; i < orderList.size(); ++i) {
                    boolean found = false;
                    for (int j = 0; j < orderIds.size(); ++j) {
                        if (orderIds.get(j).compareTo(orderList.get(i).getOrderId()) == 0) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        tmpOrderList.add(orderList.get(i));
                    }
                }
                orderList = tmpOrderList;
            }
            
            tmpOrderList = new ArrayList<OrderPo>();
            if (houseIds != null && houseIds.size() != 0) {
                for (int i = 0; i < orderList.size(); ++i) {
                    if (houseIds.contains(orderList.get(i).getHouseId())) {
                        tmpOrderList.add(orderList.get(i));
                    }
                }
                orderList = tmpOrderList;
            }
            
            tmpOrderList = new ArrayList<OrderPo>();
            if (lowerOperTime != null) {
                for (int i = 0; i < orderList.size(); ++i) {
                    if (lowerOperTime.compareTo(orderList.get(i).getOperTime()) <= 0) {
                        tmpOrderList.add(orderList.get(i));
                    }
                }
                orderList = tmpOrderList;
            }
            
            tmpOrderList = new ArrayList<OrderPo>();
            if (upperOperTime != null) {
                for (int i = 0; i < orderList.size(); ++i) {
                    if (upperOperTime.compareTo(orderList.get(i).getOperTime()) >= 0) {
                        tmpOrderList.add(orderList.get(i));
                    }
                }
                orderList = tmpOrderList;
            }

            Log.SERVICE.error("##############");
            tmpOrderList = new ArrayList<OrderPo>();
            List<HousePo> houseList = null;
            for (int i = 0; i < orderList.size(); ++i) {
                Log.SERVICE.error(String.valueOf(orderList.get(i).getOrderId()));
                HousePo house = new HousePo();
                house.setHouseId(orderList.get(i).getHouseId());
                houseList = mainService.getHouseByAllParam(house);
                if (houseList != null && houseList.size() > 0) {
                    Log.SERVICE.error("##############");
                    house = houseList.get(0);
                    boolean judge = true;
                    if (devIds != null && devIds.size() > 0 && !devIds.contains(house.getDevId())) {
                        Log.SERVICE.error("1");
                        judge = false;
                    }
                    if (bldIds != null && bldIds.size() > 0 && !bldIds.contains(house.getBldId())) {
                        Log.SERVICE.error("2");
                        judge = false;
                    }
                    if (cityIds != null && cityIds.size() > 0 && !cityIds.contains(house.getCityId())) {
                        Log.SERVICE.error("3");
                        judge = false;
                    }
                    if (judge) {
                        tmpOrderList.add(orderList.get(i));
                    }
                }
            }
            orderList = tmpOrderList;
            
            ArrayList<HashMap<String, Object>> orderMapList = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < orderList.size(); ++i) {
                if (orderList.get(i).getRemoved() == 1) {
                    continue;
                }
                HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put("uid", orderList.get(i).getUid());
                tmpMap.put("orderId", orderList.get(i).getOrderId());
                tmpMap.put("houseId", orderList.get(i).getHouseId());
                tmpMap.put("operTime", orderList.get(i).getOperTime());
                tmpMap.put("status", orderList.get(i).getStatus());
                tmpMap.put("freezeAmt", orderList.get(i).getFreezeAmt());
                tmpMap.put("totalAmt", orderList.get(i).getTotalAmt());
                tmpMap.put("liveDetail", orderList.get(i).getLiveDetail());
                tmpMap.put("verifyCode", orderList.get(i).getVerifyCode());
                tmpMap.put("totalRoomNum", orderList.get(i).getTotalRoomNum());
                tmpMap.put("pay_type", orderList.get(i).getPay_type()!=null?orderList.get(i).getPay_type():"1");
                tmpMap.put("cancelOrdeReason", orderList.get(i).getCancelOrdeReason()!=null?orderList.get(i).getCancelOrdeReason():"");
                tmpMap.put("payAmt", orderList.get(i).getPayAmt());
                orderMapList.add(tmpMap);
            }
            
            if (pageNum == -1 || pageCount == -1) {
                dataMap.put("orders", orderMapList);
                dataMap.put("num", orderMapList.size());
                genSuccOutputMap();
                return outputMap;
            }
            
            ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
            int startNum = (pageNum - 1) * pageCount;
            int endNum = startNum + pageCount;
            if (endNum > orderMapList.size()) {
                endNum = orderMapList.size();
            }
            for (int i = startNum; i < endNum; ++i) {
                pageList.add(orderMapList.get(i));
            }
            dataMap.put("orders", pageList);
            dataMap.put("num", pageList.size());
            
            int totalPage = orderMapList.size() / pageCount;
            if (orderMapList.size() % pageCount != 0) {
                totalPage += 1;
            }
            dataMap.put("totalPage", totalPage);
            dataMap.put("totalNum", orderMapList.size());
            
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
