package com.mama.server.main.controller.handler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.mongodb.HouseDetailPo;
import com.mama.server.main.dao.vo.Geographical;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.vo.HouseDetailVo;
import com.mama.server.main.vo.SpecialsHouseVo;
import com.mama.server.util.SpecialStatusEnum;

/**
 * 特价房列表
 * @author dengbiao
 *
 */
@Component
public class GetSpecialsHousesHandler extends BaseHandler {
	
    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	int currentPage = (Integer)param.get("currentPage");
        	int pageSize = (Integer)param.get("pageSize");
        	HouseDetailVo hDetailVo = new HouseDetailVo();
        	List<SpecialsHouseVo> list = new ArrayList<SpecialsHouseVo>();

	    	QueryResultVo queryVo = new QueryResultVo();
	    	queryVo = iHouseDetailService.searchHouseDetailResultVo(hDetailVo,currentPage,pageSize);
	    	List<HouseDetailPo> houseDetList = queryVo.getSourceList();	    		    	
	    	list = consoPoToVo(list,houseDetList);	    	
	    	
	    	dataMap.put("list", list);
	    	dataMap.put("itemCount", queryVo.getTotalNum());
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private List<SpecialsHouseVo> consoPoToVo(List<SpecialsHouseVo> list,List<HouseDetailPo> houseDetList) {
    	for (int i = 0; i < houseDetList.size(); i++) {

    		HouseDetailPo houseDetailPo = houseDetList.get(i);
        	SpecialsHouseVo specialsHouseVo = new SpecialsHouseVo();
        	specialsHouseVo.setBeds(houseDetailPo.getRoomList().get(0).getBeds());
            specialsHouseVo.setShopId(houseDetailPo.getShopId());
        	specialsHouseVo.setHouseId(new Long(houseDetailPo.getHouseId()).intValue());
        	specialsHouseVo.setHouseName(houseDetailPo.getName());
        	specialsHouseVo.setImageUrl(houseDetailPo.getImgList().get(0).getImageUrl());
        	specialsHouseVo.setMarket_price(houseDetailPo.getMarket_price());
        	specialsHouseVo.setMemTotalAmt(houseDetailPo.getMemTotalAmt());
        	specialsHouseVo.setOffice(houseDetailPo.getRoomList().get(0).getOffice());
        	specialsHouseVo.setPersonNum(houseDetailPo.getRoomList().get(0).getPersonNum());
        	specialsHouseVo.setRoom(houseDetailPo.getRoomList().get(0).getRoom());
        	specialsHouseVo.setToilet(houseDetailPo.getRoomList().get(0).getToilet());
        	
			list.add(specialsHouseVo);
		}

		return list;
	}
    
   }