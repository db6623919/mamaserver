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
 * 首页特价
 * @author dengbiao
 *
 */
@Component
public class GetIndexSpecialsHousesHandler extends BaseHandler {
	
    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
			HouseDetailVo hDetailVo = new HouseDetailVo();
			hDetailVo.setSpecials_stauts(SpecialStatusEnum.Home_Special.getMessage());
	    	QueryResultVo queryResultVo = new QueryResultVo();
	    	queryResultVo = iHouseDetailService.searchHouseDetailResultVo(hDetailVo,0,0);
	    	List<HouseDetailPo> houseDetailList = queryResultVo.getSourceList();
	    	SpecialsHouseVo specialsHouseVo = new SpecialsHouseVo();
	    	if (houseDetailList.size()>0) {
	    		HouseDetailPo houseDetailPo = houseDetailList.get(0);
	    		specialsHouseVo = consoPoToVo(houseDetailPo);
			}else {
				//首页特价有值时
				hDetailVo.setSpecials_stauts(SpecialStatusEnum.Special.getMessage());//首页特价房
		    	QueryResultVo queryVo = new QueryResultVo();
		    	queryVo = iHouseDetailService.searchHouseDetailResultVo(hDetailVo,1,1);
		    	List<HouseDetailPo> houseDetList = queryVo.getSourceList();
		    	if (houseDetList.size()>0) {
		    		specialsHouseVo = consoPoToVo(houseDetList.get(0));
				}
			}	        	
	    	
	    	dataMap.put("specialsHouseVo", specialsHouseVo);
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private SpecialsHouseVo consoPoToVo(HouseDetailPo houseDetailPo) {

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
		return specialsHouseVo;
	}
    
   }