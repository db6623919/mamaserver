package com.mama.server.main.controller.handler.customerservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.TradeArea;
import com.mama.server.main.dao.vo.HotelCouponGroupVo;

/**
 * 按ID查询商圈
 * @author dengbiao
 *
 */
@Component
public class GetTradeAreaByParHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			Integer id = (Integer)param.get("id");; // id
			String cityId = (String)param.get("cityId");
			log.info("GetTradeAreaByIdHandler request. id:{}", id+";cityId="+cityId);
			TradeArea tradeArea = new TradeArea();
			tradeArea.setId(id);
			if (!"".equals(cityId)&&null!=cityId) {
				tradeArea.setCityId(Integer.parseInt(cityId));
			}
			
			List<TradeArea> list = new ArrayList<TradeArea>();
			list = mainService.getTradeAreaByPar(tradeArea);
			dataMap.put("list", list);
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
