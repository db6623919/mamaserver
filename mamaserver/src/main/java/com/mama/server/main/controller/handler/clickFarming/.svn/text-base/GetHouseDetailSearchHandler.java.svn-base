package com.mama.server.main.controller.handler.clickFarming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.mongodb.HouseDetailPo;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.vo.Facilities;
import com.mama.server.main.dao.vo.Geographical;
import com.mama.server.main.dao.vo.HouseImage;
import com.mama.server.main.dao.vo.Other;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.RoomProperty;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.dao.vo.Supporting;
import com.mama.server.main.service.IHouseDetailService;
import com.mama.server.main.service.imp.HouseDetailServiceImpl;
import com.mama.server.main.vo.HouseDetailVo;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 按搜索条件查询房源详情列表
 * @author dengbiao
 *
 */
@Component
public class GetHouseDetailSearchHandler extends BaseHandler {

	@Autowired
	private IHouseDetailService houseDetailService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {

    	int shopId = -1;
    	if (param.get("shopId") != null) {
    		shopId = (Integer)param.get("shopId");
        }
    	
    	HouseDetailVo houseDetailVo = new HouseDetailVo();
    	houseDetailVo.setShopId(shopId);
    	QueryResultVo queryResultVo = new QueryResultVo();
    	queryResultVo = houseDetailService.searchHouseDetailResultVo(houseDetailVo,0,0);
      	/** 房源详情列表 */
    	List<HouseDetailPo> houseDetailList = queryResultVo.getSourceList();
    	
    	List<HouseDetailVo> list = consoPoToVo(houseDetailList);
    	//总记录数
    	long totalNum = queryResultVo.getTotalNum();
    	
    	dataMap.put("totalPage", totalNum);
        dataMap.put("list", list);
            
        genSuccOutputMap();
        
		return outputMap;
	}

	private List<HouseDetailVo> consoPoToVo(List<HouseDetailPo> houseDetailList) {
		List<HouseDetailVo> list = new ArrayList<HouseDetailVo>();
		for (int i = 0; i < houseDetailList.size(); i++) {
			HouseDetailVo houseDetailVo = new HouseDetailVo();
			HouseDetailPo houseDetailPo = new HouseDetailPo();
			houseDetailPo = houseDetailList.get(i);
			
			houseDetailVo.setHouseId(houseDetailPo.getHouseId());
			houseDetailVo.setDevId(houseDetailPo.getDevId());
			houseDetailVo.setBldId(houseDetailPo.getBldId());
			houseDetailVo.setCityId(houseDetailPo.getCityId());
			houseDetailVo.setName(houseDetailPo.getName());
			houseDetailVo.setImgList(houseDetailPo.getImgList());
			houseDetailVo.setTagList(houseDetailPo.getTagList());
			houseDetailVo.setLuxury(houseDetailPo.getLuxury());
			houseDetailVo.setFreezeAmt(houseDetailPo.getFreezeAmt());
			houseDetailVo.setTotalAmt(houseDetailPo.getTotalAmt());
			houseDetailVo.setMemFreezeAmt(houseDetailPo.getMemFreezeAmt());
			houseDetailVo.setMemTotalAmt(houseDetailPo.getMemTotalAmt());
			houseDetailVo.setStatus(houseDetailPo.getStatus());
			houseDetailVo.setShareImg(houseDetailPo.getShareImg());
			houseDetailVo.setRoomList(houseDetailPo.getRoomList().get(0));
			houseDetailVo.setFacilitiesList(houseDetailPo.getFacilitiesList().get(0));
			houseDetailVo.setRecommendFacilities(houseDetailPo.getRecommendFacilities());
			houseDetailVo.setSupportingList(houseDetailPo.getSupportingList().get(0));
			houseDetailVo.setOtherList(houseDetailPo.getOtherList().get(0));
			houseDetailVo.setRoomNum(houseDetailPo.getRoomNum());
			houseDetailVo.setShopId(houseDetailPo.getShopId());
			houseDetailVo.setAroundArea(houseDetailPo.getAroundArea());
			houseDetailVo.setHowToArrive(houseDetailPo.getHowToArrive());
			houseDetailVo.setTheme(houseDetailPo.getTheme());
			houseDetailVo.setIsRecommend(houseDetailPo.getIsRecommend());
			houseDetailVo.setRecommendTime(houseDetailPo.getRecommendTime());
			houseDetailVo.setOnLineTime(houseDetailPo.getOnLineTime());
			houseDetailVo.setOffLineTime(houseDetailPo.getOffLineTime());
			houseDetailVo.setInRemark(houseDetailPo.getInRemark());
			Double[] location = houseDetailPo.getLocation();
			Geographical geographical = new Geographical();
			geographical.setLongitude(location[0]);
			geographical.setLatitude(location[1]);
			houseDetailVo.setGeographicalList(geographical);
  
		    list.add(houseDetailVo);
		}
		return list;
	}

}
