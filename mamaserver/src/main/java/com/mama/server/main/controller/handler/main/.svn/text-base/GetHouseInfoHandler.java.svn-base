package com.mama.server.main.controller.handler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.HouseTag;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.vo.HouseCommentVo;

@Component
public class GetHouseInfoHandler extends BaseHandler {
	
	@Resource
	private CommentsService commentsService;
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("houseId") == null) {
                genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int houseId = (Integer)param.get("houseId");
            HousePo house = new HousePo();
            house.setHouseId(houseId);
            List<HousePo> houseList = mainService.getHouseByAllParam(house);
            if (houseList == null || houseList.size() == 0) {
                genErrOutputMapWithCode("house not found", ReturnCode.HOUSE_NOT_FOUND_ERROR);
                return outputMap;
            }
            house = houseList.get(0);
            dataMap.put("houseId", house.getHouseId());
            dataMap.put("devId", house.getDevId());
            dataMap.put("bldId", house.getBldId());
            dataMap.put("cityId", house.getCityId());
            dataMap.put("type", house.getType());
            dataMap.put("flag", house.getFlag());
            dataMap.put("room", house.getRoom());
            dataMap.put("mark", mainService.binary2array(house.getMark(),ConstValue.houseBinaryList));
            dataMap.put("luxury", house.getLuxury());
            dataMap.put("theme", house.getTheme());
            dataMap.put("freezeAmt", house.getFreezeAmt());
            dataMap.put("totalAmt", house.getTotalAmt());
            dataMap.put("memFreezeAmt", house.getMemFreezeAmt());
            dataMap.put("memTotalAmt", house.getMemTotalAmt());
            dataMap.put("summaryInfo", house.getSummaryInfo());
            dataMap.put("showDetail", house.getShowDetail());
            dataMap.put("operTime", house.getOperTime());
            dataMap.put("totalRoomNum", house.getTotalRoomNum());
            dataMap.put("recommendTime", house.getRecommendTime());
            dataMap.put("market_price", house.getMarket_price());
            
            setComments(houseId);
            
            List<Integer> idList = mainService.binary2array(house.getMark(),ConstValue.houseBinaryList);
            List<String> nameList = new ArrayList<String>();
            int num = idList.size() >=3 ? 3 : idList.size();
            for(int i = 0 ; i < num ; i ++ ) {
            	HouseTag houseTag = new HouseTag();
                houseTag.setId(idList.get(i));
                nameList.add(mainService.getHouseTagById(houseTag).get(0).getName());
            }
            dataMap.put("tagNameList", nameList);
            dataMap.put("houseshop_id", house.getHouseshop_id());
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }

	private void setComments(int houseId)
	{
		if (houseId <= 0)
		{
			return;
		}
		
		HouseCommentVo vo = commentsService.getHouseComments(houseId);
		if (vo != null) {
			dataMap.put("averageScore", vo.getAverageScore());
			dataMap.put("totalCommentsNum", vo.getTotalCommentsNum());
			dataMap.put("comment", vo.getSummary());		
		}
		else {
			dataMap.put("averageScore", 0);
			dataMap.put("totalCommentsNum", 0);
			dataMap.put("comment", "");
		}
	}
}
