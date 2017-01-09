package com.mama.server.main.controller.handler.hotelcoupon;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.service.MainService;
import com.mama.server.util.ThreadMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GetHotelCouponGiveListHandler extends BaseHandler
{
  public HashMap<String, Object> handle(HashMap<String, Object> param)
  {
    try
    {
      String memberId = (String)param.get("memberId");
      Integer type = (Integer)param.get("type");
      this.log.info("GetHotelCouponGiveListHandler request. memberId:{}, type:{}", memberId, type);
      if (StringUtils.isBlank(memberId)) {
        genErrOutputMapWithCode("param error, memberId required", ReturnCode.PARAM_ERROR);
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

      Map params = new HashMap();
      params.put("memberId", memberId);
      params.put("type", type);
      List hotelCouponGives = this.mainService.getHotelCouponGiveList(params);
      this.dataMap.put("hotelCouponGives", hotelCouponGives);
      genSuccOutputMap();
    } catch (Exception e) {
      e.printStackTrace();
      genErrOutputMap("interface error");
    }
    return this.outputMap;
  }
}