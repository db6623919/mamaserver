package com.mama.server.main.controller.handler.hotelcoupon;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.service.MainService;
import com.mama.server.util.ThreadMap;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GetHotelCouponGiveDetailHandler extends BaseHandler
{
  public HashMap<String, Object> handle(HashMap<String, Object> param)
  {
    try
    {
      Object idObj = param.get("id");
      String giveCode = (String)param.get("giveCode");
      this.log.info("GetHotelCouponGiveDetailHandler request. id:{}, giveCode:{}", idObj, giveCode);
      if ((idObj == null) && (StringUtils.isBlank(giveCode))) {
        genErrOutputMapWithCode("param error, id or giveCode required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if (idObj != null)
        this.dataMap.put("hotelCouponGive", this.mainService.getHotelCouponGiveDetail(Long.valueOf(idObj.toString()).longValue()));
      else {
        this.dataMap.put("hotelCouponGive", this.mainService.getHotelCouponGiveDetail(giveCode));
      }
      genSuccOutputMap();
    } catch (Exception e) {
      e.printStackTrace();
      genErrOutputMap("interface error");
    }
    return this.outputMap;
  }
}