package com.mama.server.main.controller.handler.hotelcoupon;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ExchangePo;
import com.mama.server.main.dao.model.ExchangeRequestPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.vo.HotelCouponGroupVo;
import com.mama.server.util.DateUtils;
import com.meidusa.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AddHotelCouponExchangeHandler extends BaseHandler
{
  public HashMap<String, Object> handle(HashMap<String, Object> param)
  {
    try
    {
      String mmWalletId = (String)param.get("mmWalletId");
      String memberId = (String)param.get("memberId");
      String intention = (String)param.get("intention");
      Integer type = (Integer)param.get("type");

      Object exchangeIdObj = param.get("exchangeId");

      List hotelCouponIdsStr = (List)param.get("hotelCouponIds");
      this.log.info("AddHotelCouponExchangeHandler request. mmWalletId:{}, memberId:{}, intention:{}, type:{}, exchangeId:{}, hotelCouponIds:{}", new Object[] { mmWalletId, memberId, intention, type, exchangeIdObj, hotelCouponIdsStr });

      List<Long> hotelCouponIds = new ArrayList<Long>();
      for (Iterator i$ = hotelCouponIdsStr.iterator(); i$.hasNext(); ) { Object hotelCouponId = i$.next();
        hotelCouponIds.add(Long.valueOf(hotelCouponId.toString()));
      }

      if (StringUtils.isBlank(mmWalletId)) {
        genErrOutputMapWithCode("param error, mmWalletId required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }

      if (StringUtils.isBlank(memberId)) {
        genErrOutputMapWithCode("param error, memberId required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if (StringUtils.isBlank(intention)) {
        genErrOutputMapWithCode("param error, intention required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if (type == null) {
        genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if ((type.intValue() != 1) && (type.intValue() != 2)) {
        genErrOutputMapWithCode("param error, invalid type", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if ((hotelCouponIds == null) || (hotelCouponIds.isEmpty())) {
        genErrOutputMapWithCode("param error, hotelCouponIds required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if ((type.intValue() == 2) && (exchangeIdObj == null)) {
        genErrOutputMapWithCode("param error, invalid exchangeId", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      HotelCouponGroupVo hotelCouponGroupVo = this.mainService.getHotelCouponGroup(((Long)hotelCouponIds.get(0)).longValue());
      if ((hotelCouponGroupVo == null) || (hotelCouponGroupVo.getStatus() != 0) || (!hotelCouponGroupVo.getMmWalletId().equals(mmWalletId)))
      {
        genErrOutputMapWithCode("param error, invalid hotelCouponIds", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if (DateUtils.getTimeDiff(new Date(), hotelCouponGroupVo.getExpireTime()) < 86400L) {
        genErrOutputMapWithCode("param error, hotelCoupon expire time less than 24 hours", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      for (Long hotelCouponId : hotelCouponIds) {
        if (!hotelCouponGroupVo.getHotelCouponIds().contains(hotelCouponId)) {
          genErrOutputMapWithCode("param error, invalid hotelCouponIds", ReturnCode.PARAM_ERROR);
          return this.outputMap;
        }
      }

      HousePo house = new HousePo();
      house.setHouseId(hotelCouponGroupVo.getHouseId());
      List houseList = this.mainService.getHouseByAllParam(house);
      if ((houseList == null) || (houseList.isEmpty())) {
        genErrOutputMapWithCode("param error, invalid house id", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      HousePo housePo = (HousePo)houseList.get(0);

      this.mainService.updateHotelCouponStatus(hotelCouponIds, 2);

      JSONObject summaryInfoJsonObject = JSONObject.parseObject(housePo.getSummaryInfo());
      Map showDetail = new HashMap();
      showDetail.put("intention", intention);
      showDetail.put("inSeason", Integer.valueOf(hotelCouponGroupVo.isInSeason() ? 1 : 0));
      showDetail.put("expireTime", Long.valueOf(hotelCouponGroupVo.getExpireTime().getTime()));
      showDetail.put("houseName", summaryInfoJsonObject.getString("houseName"));
      if (type.intValue() == 1) {
        ExchangePo params = new ExchangePo();
        params.setMemberId(memberId);
        params.setHotelCouponIds(JSONObject.toJSONString(hotelCouponIds));
        params.setStatus(0);
        params.setShowDetail(JSONObject.toJSONString(showDetail));
        this.dataMap.put("id", Long.valueOf(this.mainService.addHotelCouponExchange(params)));
      } else {
        ExchangeRequestPo params = new ExchangeRequestPo();
        params.setMemberId(memberId);
        params.setExchangeId(Long.valueOf(exchangeIdObj.toString()).longValue());
        params.setHotelCouponIds(JSONObject.toJSONString(hotelCouponIds));
        params.setStatus(0);
        params.setShowDetail(JSONObject.toJSONString(showDetail));
        this.dataMap.put("id", Long.valueOf(this.mainService.addHotelCouponExchangeRequest(params)));
      }
      genSuccOutputMap();
    } catch (Exception e) {
      e.printStackTrace();
      genErrOutputMap("interface error");
    }
    return this.outputMap;
  }
}