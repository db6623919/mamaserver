package com.mama.server.main.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.server.main.dao.model.mongodb.HouseDetailPo;
import com.mama.server.main.dao.model.mongodb.HouseInventory;
import com.mama.server.main.dao.mongodb.IHouseDetailDao;
import com.mama.server.main.dao.vo.Facilities;
import com.mama.server.main.dao.vo.Geographical;
import com.mama.server.main.dao.vo.Other;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.RoomProperty;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.dao.vo.Supporting;
import com.mama.server.main.service.IHouseDetailService;
import com.mama.server.main.vo.HouseDetailVo;

@Service
public class HouseDetailServiceImpl implements IHouseDetailService{

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseDetailServiceImpl.class);
	
	@Autowired
	private IHouseDetailDao houseDetailDao;

	@Override
	public void addHouseDetail(HouseDetailVo houseDetail) 
	{
		if (houseDetail != null) {
			HouseDetailPo po = convertHouseDetailVo2Po(houseDetail);
			houseDetailDao.addHouseDetail(po);
		}
	}

	@Override
	public void delHouseDetail(int houseId) {
		houseDetailDao.deleteHouseDetailById(houseId);
	}

	@Override
	public void updateHouseDeatil(HouseDetailPo houseDeatil) {
		houseDetailDao.updateHouseDetail(houseDeatil);
	}

	@Override
	public HouseDetailVo searchHouseDeatilPoById(int houseId)
	{
		if (houseId <= 0)
		{
			LOGGER.warn("failed to get house detail, illeagal houseId:{}", houseId);
			return null;
		}
		
		HouseDetailPo po = houseDetailDao.findHouseDeatilPoById(houseId);
		HouseDetailVo vo = convertHouseDetailPo2Vo(po);
		
		return vo;
	}

	private HouseDetailVo convertHouseDetailPo2Vo(HouseDetailPo po)
	{
		if (po == null)
		{
			return null;
		}
		
		HouseDetailVo vo = new HouseDetailVo();
		vo.setHouseId(po.getHouseId());
		vo.setCityId(po.getCityId());
		vo.setDevId(po.getDevId());
		vo.setBldId(po.getBldId());
		vo.setName(po.getName());
		vo.setImgList(po.getImgList());
		vo.setTagList(po.getTagList());
		vo.setLuxury(po.getLuxury());
		vo.setFreezeAmt(po.getFreezeAmt());
		vo.setTotalAmt(po.getTotalAmt());
		vo.setMemFreezeAmt(po.getMemFreezeAmt());
		vo.setMemTotalAmt(po.getMemTotalAmt());
		vo.setStatus(po.getStatus());
		vo.setShareImg(po.getShareImg());
		vo.setRoomList(po.getRoomList().get(0));
		vo.setFacilitiesList(po.getFacilitiesList().get(0));
		vo.setRecommendFacilities(po.getRecommendFacilities());
		vo.setSupportingList(po.getSupportingList().get(0));
		vo.setOtherList(po.getOtherList().get(0));
		vo.setRoomNum(po.getRoomNum());
		vo.setShopId(po.getShopId());
		vo.setAroundArea(po.getAroundArea());
		vo.setHowToArrive(po.getHowToArrive());
		vo.setTheme(po.getTheme());
		vo.setIsRecommend(po.getIsRecommend());
		vo.setOnLineTime(po.getOnLineTime());
		vo.setOffLineTime(po.getOffLineTime());
		vo.setInRemark(po.getInRemark());
		vo.setMarket_price(po.getMarket_price());
		Geographical geographical = new Geographical();
		Double[] location = po.getLocation();
		if (location != null)
		{
			geographical.setLongitude(location[0]);
			geographical.setLatitude(location[1]);
			vo.setGeographicalList(geographical);
		}
		vo.setSpecials_stauts(po.getSpecials_stauts());
		return vo;
	}
	
	private HouseDetailPo convertHouseDetailVo2Po(HouseDetailVo vo)
	{
		if (vo == null)
		{
			return null;
		}
		
		HouseDetailPo po = new HouseDetailPo();
		po.setHouseId(vo.getHouseId());
		po.setDevId(vo.getDevId());
		po.setBldId(vo.getBldId());
		po.setName(vo.getName());
		po.setImgList(vo.getImgList());
		po.setTagList(vo.getTagList());
		po.setLuxury(vo.getLuxury());
		po.setFreezeAmt(vo.getFreezeAmt());
		po.setTotalAmt(vo.getTotalAmt());
		po.setMemFreezeAmt(vo.getMemFreezeAmt());
		po.setMemTotalAmt(vo.getMemTotalAmt());
		po.setStatus(vo.getStatus());
		po.setShareImg(vo.getShareImg());
		
		List<RoomProperty> roomList = new ArrayList<RoomProperty>();
		roomList.add(vo.getRoomList());
		po.setRoomList(roomList);
		
		List<Facilities> facilities = new ArrayList<Facilities>();
		facilities.add(vo.getFacilitiesList());
		po.setFacilitiesList(facilities);
		
		po.setRecommendFacilities(vo.getRecommendFacilities());
		
		List<Supporting> supportings = new ArrayList<Supporting>();
		supportings.add(vo.getSupportingList());
		po.setSupportingList(supportings);
		
		List<Other> others = new ArrayList<Other>();
		others.add(vo.getOtherList());
		po.setOtherList(others);
		po.setRoomNum(vo.getRoomNum());
		po.setShopId(vo.getShopId());
		po.setAroundArea(vo.getAroundArea());
		po.setHowToArrive(vo.getHowToArrive());
		po.setTheme(vo.getTheme());
		po.setIsRecommend(vo.getIsRecommend());
		po.setOnLineTime(vo.getOnLineTime());
		po.setOffLineTime(vo.getOffLineTime());
		po.setInRemark(vo.getInRemark());
		
		Geographical geographical = vo.getGeographicalList();
		Double[] locations = new Double[2];
		locations[0] = geographical.getLongitude();
		locations[1] = geographical.getLatitude();
		po.setLocation(locations);
		po.setMarket_price(vo.getMarket_price());
		po.setSpecials_stauts(vo.getSpecials_stauts());
		
		return po;
	}

	@Override
	public QueryResultVo searchHouseDetailResultVo(HouseDetailVo houseDetail,int pageNo,int numPerPage) {
		return houseDetailDao.queryHouseDetailByCondition(houseDetail,pageNo,numPerPage);
	}

	@Override
	public List<HouseInventory> getAllHouseInventory()
	{
		return houseDetailDao.QueryAllHouseInventory();
	}
	
}