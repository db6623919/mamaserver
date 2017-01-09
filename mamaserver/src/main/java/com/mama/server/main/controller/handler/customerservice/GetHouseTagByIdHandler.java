package com.mama.server.main.controller.handler.customerservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HouseTag;
import com.mama.server.main.dao.model.TradeArea;
import com.mama.server.main.dao.vo.HotelCouponGroupVo;

/**
 * 按ID查询标签
 * @author dengbiao
 *
 */
@Component
public class GetHouseTagByIdHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			/******************** 检查参数信息 ************************/
			Integer id = (Integer)param.get("id");; // 
			log.info("GetHouseTagByIdHandler request. id:{}", id);
			HouseTag houseTag = new HouseTag();
			houseTag.setId(id);
			List<HouseTag> list = new ArrayList<HouseTag>();
			list = mainService.getHouseTagById(houseTag);
			dataMap.put("list", list);
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
