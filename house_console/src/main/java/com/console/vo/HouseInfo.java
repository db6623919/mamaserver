package com.console.vo;

import java.util.List;


/**
 * 
* @ClassName: HouseInfo 
* @Description: 房源信息表
* @author Lijiaqi 
* @date 2015年9月17日 下午4:01:45
 */
public class HouseInfo {

	private int devId;
	
	private int bldId;
	
	private int houseId;
	
	private int flag;
	
	private int memFreezeAmt;
	
	private int cityId;
	
	private int type;
	
	private String room;
	
	private int freezeAmt;
	
	private String address;
	
	private String bedType;
	
	private String checkInTime;
	
	private String checkOutTime;
	
	private String checkdesc;
	
	private String aidkit;
	
	private String conditione;
	
	private String drinking;
	
	private String dryer;
	
	private String extinguisher;
	
	private String fridge;
	
	private String hairdrier;
	
	private String heater;
	
	private String heating;
	
	private String hotpot;
	
	private String shampoo;
	
	private String shower;
	
	private String slipper;
	
	private String smokedetector;
	
	private String tooth;
	
	private String towels;
	
	private String tv;
	
	private String washing;
	
	private String houseName;
	
	private String houseType;
	
	private String[] introductionImg;
	
	private String[] introductionName;
	
	private String introductionNum;
	
	private String isInvoice;
	
	private String latitude;
	
	private String live;
	
	private String longitude;
	
	private String breakfast;
	
	private String childrenstay;
	
	private String cook;
	
	private String extrabed;
	
	private String guests;
	
	private String party;
	
	private String pet;
	
	private String smoking;
	
	private String accesscard;
	
	private String broadband;
	
	private String buzzer;
	
	private String elevator;
	
	private String gym;
	
	private String parking;
	
	private String playground;
	
	private String securitystaff;
	
	private String store;
	
	private String swimming;
	
	private String wheelchair;
	
	private String wifi;
	
	private String telephone;
	
	private String videoUrl;
	
	private int totalAmt;
	
	private String summaryInfo;
	
	private int memTotalAmt;
	
	private int luxury;
	
	private int theme;
	
	private List<Integer> mark;
	
	private String operTime;
	
	//是否收藏标示
	private int collectFlag;
	
	//楼盘介绍
	private String builIntroduction;
	
	//房屋面积
	private String houseArea;
	
	//设施
	private List<String> SsList;
	
	//配套
	private List<String> PtList;
	
	//其他
	private List<String> QtList;
	
	//房屋库存
	private int totalRoomNum;
	
	//房屋设定价格
	private List<Integer> housePrice;
	
	//城市名称
	private String cityName;
	
	//房源价格折扣
	private String discount;
	
	//最高再省
	private String discontMax;
	
	//顺序
	private int sort;
	
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

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getMemFreezeAmt() {
		return memFreezeAmt;
	}

	public void setMemFreezeAmt(int memFreezeAmt) {
		this.memFreezeAmt = memFreezeAmt;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(int freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getCheckdesc() {
		return checkdesc;
	}

	public void setCheckdesc(String checkdesc) {
		this.checkdesc = checkdesc;
	}

	public String getAidkit() {
		return aidkit;
	}

	public void setAidkit(String aidkit) {
		this.aidkit = aidkit;
	}

	public String getConditione() {
		return conditione;
	}

	public void setConditione(String conditione) {
		this.conditione = conditione;
	}

	public String getDrinking() {
		return drinking;
	}

	public void setDrinking(String drinking) {
		this.drinking = drinking;
	}

	public String getDryer() {
		return dryer;
	}

	public void setDryer(String dryer) {
		this.dryer = dryer;
	}

	public String getExtinguisher() {
		return extinguisher;
	}

	public void setExtinguisher(String extinguisher) {
		this.extinguisher = extinguisher;
	}

	public String getFridge() {
		return fridge;
	}

	public void setFridge(String fridge) {
		this.fridge = fridge;
	}

	public String getHairdrier() {
		return hairdrier;
	}

	public void setHairdrier(String hairdrier) {
		this.hairdrier = hairdrier;
	}

	public String getHeater() {
		return heater;
	}

	public void setHeater(String heater) {
		this.heater = heater;
	}

	public String getHeating() {
		return heating;
	}

	public void setHeating(String heating) {
		this.heating = heating;
	}

	public String getHotpot() {
		return hotpot;
	}

	public void setHotpot(String hotpot) {
		this.hotpot = hotpot;
	}

	public String getShampoo() {
		return shampoo;
	}

	public void setShampoo(String shampoo) {
		this.shampoo = shampoo;
	}

	public String getShower() {
		return shower;
	}

	public void setShower(String shower) {
		this.shower = shower;
	}

	public String getSlipper() {
		return slipper;
	}

	public void setSlipper(String slipper) {
		this.slipper = slipper;
	}

	public String getSmokedetector() {
		return smokedetector;
	}

	public void setSmokedetector(String smokedetector) {
		this.smokedetector = smokedetector;
	}

	public String getTooth() {
		return tooth;
	}

	public void setTooth(String tooth) {
		this.tooth = tooth;
	}

	public String getTowels() {
		return towels;
	}

	public void setTowels(String towels) {
		this.towels = towels;
	}

	public String getTv() {
		return tv;
	}

	public void setTv(String tv) {
		this.tv = tv;
	}

	public String getWashing() {
		return washing;
	}

	public void setWashing(String washing) {
		this.washing = washing;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String[] getIntroductionImg() {
		return introductionImg;
	}

	public void setIntroductionImg(String[] introductionImg) {
		this.introductionImg = introductionImg;
	}

	public String[] getIntroductionName() {
		return introductionName;
	}

	public void setIntroductionName(String[] introductionName) {
		this.introductionName = introductionName;
	}

	public String getIntroductionNum() {
		return introductionNum;
	}

	public void setIntroductionNum(String introductionNum) {
		this.introductionNum = introductionNum;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLive() {
		return live;
	}

	public void setLive(String live) {
		this.live = live;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getChildrenstay() {
		return childrenstay;
	}

	public void setChildrenstay(String childrenstay) {
		this.childrenstay = childrenstay;
	}

	public String getCook() {
		return cook;
	}

	public void setCook(String cook) {
		this.cook = cook;
	}

	public String getExtrabed() {
		return extrabed;
	}

	public void setExtrabed(String extrabed) {
		this.extrabed = extrabed;
	}

	public String getGuests() {
		return guests;
	}

	public void setGuests(String guests) {
		this.guests = guests;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getPet() {
		return pet;
	}

	public void setPet(String pet) {
		this.pet = pet;
	}

	public String getSmoking() {
		return smoking;
	}

	public void setSmoking(String smoking) {
		this.smoking = smoking;
	}

	public String getAccesscard() {
		return accesscard;
	}

	public void setAccesscard(String accesscard) {
		this.accesscard = accesscard;
	}

	public String getBroadband() {
		return broadband;
	}

	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}

	public String getBuzzer() {
		return buzzer;
	}

	public void setBuzzer(String buzzer) {
		this.buzzer = buzzer;
	}

	public String getElevator() {
		return elevator;
	}

	public void setElevator(String elevator) {
		this.elevator = elevator;
	}

	public String getGym() {
		return gym;
	}

	public void setGym(String gym) {
		this.gym = gym;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getPlayground() {
		return playground;
	}

	public void setPlayground(String playground) {
		this.playground = playground;
	}

	public String getSecuritystaff() {
		return securitystaff;
	}

	public void setSecuritystaff(String securitystaff) {
		this.securitystaff = securitystaff;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getSwimming() {
		return swimming;
	}

	public void setSwimming(String swimming) {
		this.swimming = swimming;
	}

	public String getWheelchair() {
		return wheelchair;
	}

	public void setWheelchair(String wheelchair) {
		this.wheelchair = wheelchair;
	}

	public String getWifi() {
		return wifi;
	}

	public void setWifi(String wifi) {
		this.wifi = wifi;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public int getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getSummaryInfo() {
		return summaryInfo;
	}

	public void setSummaryInfo(String summaryInfo) {
		this.summaryInfo = summaryInfo;
	}

	public int getMemTotalAmt() {
		return memTotalAmt;
	}

	public void setMemTotalAmt(int memTotalAmt) {
		this.memTotalAmt = memTotalAmt;
	}

	public int getLuxury() {
		return luxury;
	}

	public void setLuxury(int luxury) {
		this.luxury = luxury;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public List<Integer> getMark() {
		return mark;
	}

	public void setMark(List<Integer> mark) {
		this.mark = mark;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public int getCollectFlag() {
		return collectFlag;
	}

	public void setCollectFlag(int collectFlag) {
		this.collectFlag = collectFlag;
	}

	public String getBuilIntroduction() {
		return builIntroduction;
	}

	public void setBuilIntroduction(String builIntroduction) {
		this.builIntroduction = builIntroduction;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public List<String> getSsList() {
		return SsList;
	}

	public void setSsList(List<String> ssList) {
		SsList = ssList;
	}

	public List<String> getPtList() {
		return PtList;
	}

	public void setPtList(List<String> ptList) {
		PtList = ptList;
	}

	public List<String> getQtList() {
		return QtList;
	}

	public void setQtList(List<String> qtList) {
		QtList = qtList;
	}

	public int getTotalRoomNum() {
		return totalRoomNum;
	}

	public void setTotalRoomNum(int totalRoomNum) {
		this.totalRoomNum = totalRoomNum;
	}

	public List<Integer> getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(List<Integer> housePrice) {
		this.housePrice = housePrice;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDiscontMax() {
		return discontMax;
	}

	public void setDiscontMax(String discontMax) {
		this.discontMax = discontMax;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
	
}