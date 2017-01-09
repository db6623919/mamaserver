package com.mama.server.main.dao.model.mongodb;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mama.server.main.dao.vo.Facilities;
import com.mama.server.main.dao.vo.Geographical;
import com.mama.server.main.dao.vo.HouseImage;
import com.mama.server.main.dao.vo.Other;
import com.mama.server.main.dao.vo.RoomProperty;
import com.mama.server.main.dao.vo.Supporting;

/**
 * 房源详情实体
 * @author dengbiao
 *
 */
@Document(collection = "house.detail")
public class HouseDetailPo {

	/** 房源id */
	@Id
	private long houseId;
	
	private int devId; //开发商id
	private int bldId; //楼盘id
	private int cityId;//城市ID
	private String name;//房源名
	
	private List<HouseImage> imgList ; //房源图片
	
	/** 标签：{ " tagList ":["1","2","3"]}，1、2、3分别代表"经济型","舒适型","海景房"*/
	private List<Integer> tagList;
	private int luxury;//舒适度
	private int freezeAmt;//普通会员定金
	private int totalAmt;//普通会员全额
	private int memFreezeAmt;//会员定金
	private int memTotalAmt;//会员全额
	private int status;//状态
	private String shareImg;//分享页面图片
	
	private List<RoomProperty> roomList; //房屋属性
    private List<Facilities> facilitiesList;//房间
	private String recommendFacilities;//推荐设施:保存设施的字段名称,多个以逗号隔开
    private List<Supporting> supportingList;//房间配套
	private List<Other> otherList;//其它

	private int roomNum;//户型房间数
    private int shopId;//房源所属店铺
	private String aroundArea;//周边
	private String howToArrive;//怎么去
	private int theme;//主题：1、亲子出游；2、浪漫假期；3、新奇住宿；4、城市周末；5、特色主题
	private String isRecommend;//是否推荐:1、推荐；0、不推介；
	private String recommendTime;//推荐时间
    private String onLineTime;//上架时间
    private String offLineTime;//下架时间
    private String inRemark;//入住说明
//    private List<Geographical> geographicalList;//经纬度
    
	private int market_price;//市场价
	private int specials_stauts=0;//特价状态默认0， 0 普通 1特价 2首页特价
	
    private Double[] location;//经纬度坐标数组
    
	public long getHouseId() {
		return houseId;
	}
	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}
	public int getDevId() {
		return devId;
	}
	public void setDevId(int devId) {
		this.devId = devId;
	}
	public int getBldId() {
		return bldId;
	}
	public void setBldId(int bldId) {
		this.bldId = bldId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<HouseImage> getImgList() {
		return imgList;
	}
	public void setImgList(List<HouseImage> imgList) {
		this.imgList = imgList;
	}
	public List<Integer> getTagList() {
		return tagList;
	}
	public void setTagList(List<Integer> tagList) {
		this.tagList = tagList;
	}

	public int getLuxury() {
		return luxury;
	}
	public void setLuxury(int luxury) {
		this.luxury = luxury;
	}
	public int getFreezeAmt() {
		return freezeAmt;
	}
	public void setFreezeAmt(int freezeAmt) {
		this.freezeAmt = freezeAmt;
	}
	public int getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}
	public int getMemFreezeAmt() {
		return memFreezeAmt;
	}
	public void setMemFreezeAmt(int memFreezeAmt) {
		this.memFreezeAmt = memFreezeAmt;
	}
	public int getMemTotalAmt() {
		return memTotalAmt;
	}
	public void setMemTotalAmt(int memTotalAmt) {
		this.memTotalAmt = memTotalAmt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<RoomProperty> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<RoomProperty> roomList) {
		this.roomList = roomList;
	}
	public List<Facilities> getFacilitiesList() {
		return facilitiesList;
	}
	public void setFacilitiesList(List<Facilities> facilitiesList) {
		this.facilitiesList = facilitiesList;
	}
	public String getRecommendFacilities() {
		return recommendFacilities;
	}
	public void setRecommendFacilities(String recommendFacilities) {
		this.recommendFacilities = recommendFacilities;
	}
	public List<Supporting> getSupportingList() {
		return supportingList;
	}
	public void setSupportingList(List<Supporting> supportingList) {
		this.supportingList = supportingList;
	}
	public List<Other> getOtherList() {
		return otherList;
	}
	public void setOtherList(List<Other> otherList) {
		this.otherList = otherList;
	}
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getAroundArea() {
		return aroundArea;
	}
	public void setAroundArea(String aroundArea) {
		this.aroundArea = aroundArea;
	}
	public String getHowToArrive() {
		return howToArrive;
	}
	public void setHowToArrive(String howToArrive) {
		this.howToArrive = howToArrive;
	}
	public int getTheme() {
		return theme;
	}
	public void setTheme(int theme) {
		this.theme = theme;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getRecommendTime() {
		return recommendTime;
	}
	public void setRecommendTime(String recommendTime) {
		this.recommendTime = recommendTime;
	}
	public String getOnLineTime() {
		return onLineTime;
	}
	public void setOnLineTime(String onLineTime) {
		this.onLineTime = onLineTime;
	}
	public String getOffLineTime() {
		return offLineTime;
	}
	public void setOffLineTime(String offLineTime) {
		this.offLineTime = offLineTime;
	}
	public String getInRemark() {
		return inRemark;
	}
	public void setInRemark(String inRemark) {
		this.inRemark = inRemark;
	}
	public Double[] getLocation() {
		return location;
	}
	public void setLocation(Double[] location) {
		this.location = location;
	}
	public String getShareImg()
	{
		return shareImg;
	}
	public void setShareImg(String shareImg)
	{
		this.shareImg = shareImg;
	}
	public int getMarket_price() {
		return market_price;
	}
	public void setMarket_price(int market_price) {
		this.market_price = market_price;
	}
	public int getSpecials_stauts() {
		return specials_stauts;
	}
	public void setSpecials_stauts(int specials_stauts) {
		this.specials_stauts = specials_stauts;
	}

}