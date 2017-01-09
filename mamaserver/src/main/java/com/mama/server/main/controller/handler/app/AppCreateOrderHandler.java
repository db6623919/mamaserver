package com.mama.server.main.controller.handler.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.BuildingPo;
import com.mama.server.main.dao.model.DeveloperPo;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.mongodb.InventoryPo;
import com.mama.server.main.dao.vo.OrderPriceInfoVo;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.util.Json;
import com.mama.server.util.Log;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.SmsUtil;

/**
 * 订单提交
 * @author whl
 *
 */
@Component
public class AppCreateOrderHandler extends BaseHandler {

	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd ");
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			
			Integer houseId = (Integer)param.get("houseId");
			if (houseId == null) {
				genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			
            HousePo house = new HousePo();
            house.setHouseId(houseId);
            List<HousePo> houseList = mainService.getHouseByAllParam(house);
            if (houseList.size() > 0) {
            	house = houseList.get(0);
            }
            
            
            BuildingPo buildingPo = new BuildingPo();
            buildingPo.setBldId(house.getBldId());
            List<BuildingPo> buildingList = mainService.getBuilding(buildingPo);
            if(buildingList.size() > 0) {
            	buildingPo = buildingList.get(0);
            }
            
            
            DeveloperPo developer = new DeveloperPo();
            Map<String, Object> devMap = new HashMap<String, Object>();
            devMap.put("devId", house.getDevId());
            List<DeveloperPo> developerList = mainService.getDeveloper(devMap);
            if(developerList.size() > 0) {
            	developer = developerList.get(0);
            }
            
            
			String uid = (String) param.get("uid");
			String mmWalletId = (String) param.get("mmWalletId");
			String startDateStr = (String) param.get("beginTime");
			String endDateStr = (String) param.get("endTime");
			Integer totalRoomNum = (Integer) param.get("totalRoomNum");
			List<Long> hotelCouponIds = (List<Long>) param.get("hotelCouponIds");
			HashMap<String, Object> liveDetailMap = (HashMap<String, Object>) param.get("liveDetail");
			
			Map<String,Object> summaryMap = Json.parse(house.getSummaryInfo(), Map.class);
			liveDetailMap.put("houseName", summaryMap.get("houseName"));
			
			liveDetailMap.put("buildingName", buildingPo.getName());
			
			Map<String,Object> detailMap = Json.parse(house.getShowDetail(), Map.class);;
			liveDetailMap.put("phone", detailMap.get("telephone"));
			
			liveDetailMap.put("developerName", developer.getName());
			
			
			String freezeAmt = (String)param.get("freezeAmt");
			log.info("CreateOrderHandler uid:{},mmWalletId:{}, houseId:{}, startDate:{}, endDate:{}, hotelCouponIds{},liveDetail:{}", uid, mmWalletId, houseId, startDateStr,
					endDateStr, hotelCouponIds, liveDetailMap);
			if (StringUtils.isBlank(uid)) {
				genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (StringUtils.isBlank(mmWalletId)) {
				genErrOutputMapWithCode("param error, mmWalletId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			
			if (StringUtils.isBlank(startDateStr)) {
				genErrOutputMapWithCode("param error, beginTime required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (StringUtils.isBlank(endDateStr)) {
				genErrOutputMapWithCode("param error, endTime required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (liveDetailMap == null) {
				genErrOutputMapWithCode("param error, liveDetail required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (totalRoomNum == null) {
				genErrOutputMapWithCode("param error, totalRoomNum required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (param.get("pay_type") == null) {
                genErrOutputMapWithCode("param error, pay_type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
			if (StringUtils.isBlank(freezeAmt)) {
				genErrOutputMapWithCode("param error, freezeAmt required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			
			Date startDate = mainService.stringToDate(startDateStr);
			Date endDate = mainService.stringToDate(endDateStr);
			if (startDate == null) {
				genErrOutputMapWithCode("param error,invalid beginTime", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (endDate == null) {
				genErrOutputMapWithCode("param error,invalid endTime", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (hotelCouponIds == null)
				hotelCouponIds = new ArrayList<Long>();
			liveDetailMap.put("beginTime", startDateStr);
			liveDetailMap.put("endTime", endDateStr);
			liveDetailMap.put("hotelCouponIds", hotelCouponIds);


			// check date
			String curTime = mainService.getCurrentDate();
			if (startDateStr.compareTo(curTime) < 0) {
				genErrOutputMapWithCode("begin time eallier than current time", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (startDateStr.compareTo(endDateStr) > 0) {
				genErrOutputMapWithCode("begin time later than end time", ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			// check house room
			Date tmpDate = (Date) startDate.clone();
			HouseOrderPo houseOrder;
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
				int total = 0;
				for (int j = 0; j < houseOrderList.size(); ++j) {
					total += houseOrderList.get(j).getTotalRoomNum();
				}
				if (house.getTotalRoomNum() - total - totalRoomNum < 0) {
					genErrOutputMapWithCode("[" + mainService.dateToString(tmpDate) + "]", ReturnCode.ROOM_NOT_ENOUGH_ERROR);
					return outputMap;
				}

				tmpDate = mainService.getNextDate(tmpDate);
			}

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

			OrderPriceInfoVo orderPriceInfoVo = mainService.getOrderPriceInfo(mmWalletId, houseId, startDate, endDate, hotelCouponIds);
			liveDetailMap.put("hotelCouponDiscountAmt", orderPriceInfoVo.getHotelCouponDiscountAmt());
			
			int totalAmt = orderPriceInfoVo.getTotalAmt();
			String pay_type = (String)param.get("pay_type");//支付方式 1 微信支付 2线下担保支付
			// create order
			OrderPo order = new OrderPo();
			order.setOrderId(newOrderId);
			order.setUid(uid);
			order.setHouseId(houseId);
			order.setOperTime(mainService.getCurrentDatetime());
			order.setStatus(ConstValue.ORDER_MERCHANT_VERIFYING);
			order.setFreezeAmt(Integer.parseInt(freezeAmt));
			//order.setTotalAmt(orderPriceInfoVo.getOrderFinalAmt());
			order.setTotalAmt(totalAmt);
            order.setPayAmt(orderPriceInfoVo.getOrderFinalAmt());
			order.setTotalRoomNum(totalRoomNum);
			order.setVerifyCode("");
			order.setLiveDetail(Json.format(liveDetailMap));
			String retentionTime = startDateStr+" 22:00:00";
			order.setRetentionTime(retentionTime);
			order.setPay_type(pay_type);
			order.setOrderType(1);
			if (mainService.insertOrder(order) != 0) {
				genErrOutputMapWithCode("fail to create order", ReturnCode.CREATE_ORDER_ERROR);
				return outputMap;
			}

			if (hotelCouponIds != null && hotelCouponIds.size() > 0)
				mainService.updateHotelCouponStatus(hotelCouponIds, 2);// 冻结旅居券

			// create house order
			Date dateTmp = (Date) startDate.clone();
			HouseOrderPo houseOrderTmp;
			List<HouseOrderPo> createdHouseOrderList = new ArrayList<HouseOrderPo>();
			while (!dateTmp.equals(endDate)) {
				houseOrderTmp = new HouseOrderPo();
				houseOrderTmp.setOrderId(order.getOrderId());
				houseOrderTmp.setHouseId(houseId);
				houseOrderTmp.setUid(uid);
				houseOrderTmp.setTotalRoomNum(totalRoomNum);
				houseOrderTmp.setDate(mainService.dateToString(dateTmp));
				if (mainService.insertHouseOrder(houseOrderTmp) != 0) {
					order.setRemoved(1);
					mainService.updateOrder(order);
					for (int i = 0; i < createdHouseOrderList.size(); ++i) {
						createdHouseOrderList.get(i).setRemoved(1);
						mainService.updateHouseOrderById(createdHouseOrderList.get(i));
					}
					genErrOutputMapWithCode("fail to create order", ReturnCode.CREATE_ORDER_ERROR);
					return outputMap;
				}
				dateTmp = mainService.getNextDate(dateTmp);
				
				//更新mongodb库存表
				InventoryPo inventory = new InventoryPo();
				inventory.setHouseId(order.getHouseId());
				inventory.setDate(houseOrderTmp.getDate());
				InventoryPo inventoryPo = inventoryService.findInventoryByPar(inventory);
				if (null!=inventoryPo) {
					int invertory = inventoryPo.getInventory();
					invertory = invertory-houseOrderTmp.getTotalRoomNum()>0?invertory-houseOrderTmp.getTotalRoomNum():0;
					inventory.setInventory(invertory);
					inventoryService.updateInventory(inventory);
				}
				
			}			
			
			
			// 短信通知妈妈小管家
			String houseName = liveDetailMap.get("houseName").toString();
			String userPhone = liveDetailMap.get("user_phone").toString();
			String merchantPhone = liveDetailMap.get("phone").toString();
			
			//短信通知妈妈小管家
			String msg = SmsUtil.genCreateOrderNotice2Mama(houseName, startDateStr, endDateStr, newOrderId, totalAmt, userPhone, merchantPhone);
			SmsUtil.sendSms(PropertiesUtils.getMmManagerPhone(), msg);

			//短信通知预订人
            String userMsg = SmsUtil.genCreateOrderNotice2User(startDateStr, endDateStr, houseName);
            SmsUtil.sendSms(userPhone, userMsg);     
            
			dataMap.put("orderId", order.getOrderId());
			genSuccOutputMap();
		} catch (Exception e) {
			genErrOutputMap("interface error");
			log.error("AppCreateOrderHandler exception:", e);
		}
		return outputMap;
	}

}
