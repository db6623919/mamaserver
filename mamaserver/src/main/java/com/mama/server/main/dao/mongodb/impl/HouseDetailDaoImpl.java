package com.mama.server.main.dao.mongodb.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.mongodb.HouseDetailPo;
import com.mama.server.main.dao.model.mongodb.HouseInventory;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.model.mongodb.HouseShop;
import com.mama.server.main.dao.mongodb.IHouseDetailDao;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.vo.HouseDetailVo;

/**
 * 房源详情DAO
 * @author dengbiao
 *
 */
@Component
public class HouseDetailDaoImpl implements IHouseDetailDao{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void addHouseDetail(HouseDetailPo houseDeatil) {
		mongoTemplate.insert(houseDeatil);
	}

	@Override
	public void deleteHouseDetailById(int houseId) {
		Query query = new Query(where("_id").is(houseId));
        mongoTemplate.remove(query, HouseDetailPo.class);
	}

	@Override
	public void updateHouseDetail(HouseDetailPo houseDeatil) {
		Query query = new Query(where("_id").is(houseDeatil.getHouseId()));
		Update update = new Update();
		
		update.set("devId", houseDeatil.getDevId());
		update.set("bldId", houseDeatil.getBldId());
		update.set("cityId", houseDeatil.getCityId());
		update.set("name", houseDeatil.getName());
		update.set("imgList", houseDeatil.getImgList());
		update.set("tagList", houseDeatil.getTagList());
		update.set("luxury", houseDeatil.getLuxury());//舒适度
		update.set("freezeAmt", houseDeatil.getFreezeAmt());//普通会员定金
		update.set("totalAmt", houseDeatil.getTotalAmt());//普通会员全额
		update.set("memFreezeAmt", houseDeatil.getMemFreezeAmt());//会员定金
		update.set("memTotalAmt", houseDeatil.getMemTotalAmt());//会员全额
		update.set("status", houseDeatil.getStatus());//状态
		update.set("roomList", houseDeatil.getRoomList());//房屋属性
		update.set("facilitiesList", houseDeatil.getFacilitiesList());//房间设施
		update.set("recommendFacilities", houseDeatil.getRecommendFacilities());//推荐设施
		update.set("supportingList", houseDeatil.getSupportingList());//房间配套
		update.set("otherList", houseDeatil.getOtherList());//其它
		update.set("roomNum", houseDeatil.getRoomNum());//户型房间数
		update.set("shopId", houseDeatil.getShopId());////房源所属店铺
		update.set("aroundArea", houseDeatil.getAroundArea());//周边
		update.set("howToArrive", houseDeatil.getHowToArrive());//怎么去
		update.set("theme", houseDeatil.getTheme());//主题
		update.set("isRecommend", houseDeatil.getIsRecommend());////是否推荐:1、推荐；0、不推介；
		update.set("recommendTime", houseDeatil.getRecommendTime());//推荐时间
		update.set("onLineTime", houseDeatil.getOnLineTime());//上架时间
		update.set("offLineTime", houseDeatil.getOffLineTime());//下架时间
		update.set("inRemark", houseDeatil.getInRemark());//入住说明
//		update.set("geographicalList", houseDeatil.getGeographicalList());//经纬度
		update.set("location", houseDeatil.getLocation());//经纬度
		update.set("market_price", houseDeatil.getMarket_price());
		update.set("specials_stauts", houseDeatil.getSpecials_stauts());
		mongoTemplate.updateFirst(query, update, HouseDetailPo.class);
		
	}

	@Override
	public HouseDetailPo findHouseDeatilPoById(int houseId) {
	    return mongoTemplate.findById(houseId, HouseDetailPo.class);
	}


	@Override
	public QueryResultVo queryHouseDetailByCondition(
			HouseDetailVo houseDetailVo,int pageNo, int numPerPage) {
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        String now_date = format.format(new Date());
		QueryResultVo resultVo = new QueryResultVo();
		Query query = new Query();
		Criteria criatira = new Criteria();
		if(houseDetailVo.getShopId() > 0) {
			criatira.and("shopId").is(houseDetailVo.getShopId());
		}
		if(houseDetailVo.getSpecials_stauts() > 0) {
			criatira.and("specials_stauts").is(houseDetailVo.getSpecials_stauts());
		}else {
			criatira.and("specials_stauts").gt(0);
		}
		
		criatira.and("offLineTime").gte(now_date);
		query.addCriteria(criatira);
		/** 总记录数 */
		Query qry = new Query();
		Criteria criat = new Criteria();
		criat.and("specials_stauts").gt(0);
		criat.and("offLineTime").gt(now_date);
		if (houseDetailVo.getPriceRange()!=null) {
			if (!houseDetailVo.getPriceRange().equals("") && houseDetailVo.getPriceRange()!= null) {
				if (houseDetailVo.getPriceRange().equals("0")) {
					criatira.and("memTotalAmt").is(99);
					criat.and("memTotalAmt").is(99);
				}else {
					criatira.and("memTotalAmt").is(Integer.parseInt(houseDetailVo.getPriceRange()+"99"));
					criat.and("memTotalAmt").is(Integer.parseInt(houseDetailVo.getPriceRange()+"99"));
				}
			}
		}

		qry.addCriteria(criat);
		
		long totalNum = mongoTemplate.count(qry, HouseDetailPo.class);  
		Sort sort = new Sort(Direction.ASC, "memTotalAmt");
		if(pageNo>=1) {
			int skip = (pageNo - 1) * numPerPage;
			query.skip(skip);// skip相当于从那条记录开始  
	        query.limit(numPerPage);// 从skip开始,取多少条记录 
	        query.with(sort);
		}
        List<HouseDetailPo> list = mongoTemplate.find(query, HouseDetailPo.class);
        resultVo.setSourceList(list);
        resultVo.setTotalNum(totalNum);
		return resultVo;
	}

	@Override
	public List<HouseInventory> QueryAllHouseInventory()
	{
		List<HouseInventory> inventories = mongoTemplate.find(null, HouseInventory.class);
		return inventories;
	}

}