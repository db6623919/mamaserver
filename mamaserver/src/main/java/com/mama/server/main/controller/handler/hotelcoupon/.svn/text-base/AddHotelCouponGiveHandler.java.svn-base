package com.mama.server.main.controller.handler.hotelcoupon;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HotelCouponGivePo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.vo.HotelCouponGroupVo;
import com.mama.server.main.service.MainService;
import com.mama.server.util.DateUtils;
import com.mama.server.util.ThreadMap;
import com.meidusa.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AddHotelCouponGiveHandler extends BaseHandler
{
  public HashMap<String, Object> handle(HashMap<String, Object> param)
  {
    try
    {
      String mmWalletId = (String)param.get("mmWalletId");
      String memberId = (String)param.get("memberId");
      String msg = (String)param.get("msg");
      Integer copiesCount = (Integer)param.get("copiesCount");

      List hotelCouponIdsStr = (List)param.get("hotelCouponIds");
      this.log.info("AddHotelCouponGiveHandler request. mmWalletId:{}, memberId:{}, msg:{}, hotelCouponIds:{}, copiesCount:{}", new Object[] { mmWalletId, memberId, msg, hotelCouponIdsStr, copiesCount });

      List<Long> hotelCouponIds = new ArrayList();
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
      if (StringUtils.isBlank(msg)) {
        genErrOutputMapWithCode("param error, msg required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if ((hotelCouponIds == null) || (hotelCouponIds.isEmpty())) {
        genErrOutputMapWithCode("param error, hotelCouponIds required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if (copiesCount == null) {
        genErrOutputMapWithCode("param error, copiesCount required", ReturnCode.PARAM_ERROR);
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
      if ((copiesCount.intValue() <= 0) || (hotelCouponIds.size() % copiesCount.intValue() != 0)) {
        genErrOutputMapWithCode("param error, invalid copiesCount", ReturnCode.PARAM_ERROR);
        return this.outputMap;
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
      showDetail.put("msg", msg);
      showDetail.put("inSeason", Integer.valueOf(hotelCouponGroupVo.isInSeason() ? 1 : 0));
      showDetail.put("houseName", summaryInfoJsonObject.getString("houseName"));
      showDetail.put("total", Integer.valueOf(hotelCouponIds.size()));
      showDetail.put("copiesCount", copiesCount);
      showDetail.put("copiesLimit", Integer.valueOf(hotelCouponIds.size() / copiesCount.intValue()));

      HotelCouponGivePo params = new HotelCouponGivePo();
      params.setMemberId(memberId);
      params.setGiveCode(DigestUtils.md5Hex(memberId + "-" + System.currentTimeMillis()));
      params.setHotelCouponIds(JSONObject.toJSONString(hotelCouponIds));
      params.setReceivedHotelCouponIds(JSONObject.toJSONString(Collections.emptyList()));
      params.setStatus(0);
      params.setShowDetail(JSONObject.toJSONString(showDetail));
      this.dataMap.put("id", Long.valueOf(this.mainService.addHotelCouponGive(params)));
      genSuccOutputMap();
    } catch (Exception e) {
      e.printStackTrace();
      genErrOutputMap("interface error");
    }
    return this.outputMap;
  }
}