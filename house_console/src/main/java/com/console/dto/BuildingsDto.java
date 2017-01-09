package com.console.dto;

import java.math.BigDecimal;

public class BuildingsDto {
	private String video;
	private String houseExplain;
	private String peripheralConfig;
	private String vedio;
	
	public String getVedio() {
		return vedio;
	}
	public void setVedio(String vedio) {
		this.vedio = vedio;
	}
	public String getPeripheralConfig() {
		return peripheralConfig;
	}
	public void setPeripheralConfig(String peripheralConfig) {
		this.peripheralConfig = peripheralConfig;
	}
	public String getHouseExplain() {
		return houseExplain;
	}
	public void setHouseExplain(String houseExplain) {
		this.houseExplain = houseExplain;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	private String telphone;
    private String bldImg;
    public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getBldImg() {
		return bldImg;
	}
	public void setBldImg(String bldImg) {
		this.bldImg = bldImg;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
    private String description;
	
	/**:bldId*/
	private int bldId;

	/**:devId*/
	private int devId;

	/**:provId*/
	private int provId;

	/**:cityId*/
	private int cityId;

	/**:showDetail*/
	private String showDetail;

	/**:type*/
	private int type;

	/**:mark*/
	private String mark;

	/**:removed*/
	private int removed;

	/**:name*/
	private String name;

	/**项目介绍:project_introduction*/
	private String project_introduction;

	/**户型面积区间段:unit_area*/
	private String unit_area;

	/**建筑面积:built_area*/
	private BigDecimal built_area;

	/**楼盘均价:average_price*/
	private int average_price;

	/**周边景点:surrounding_scenic*/
	private String surrounding_scenic;

	/**物业类型:property_type*/
	private String property_type;

	/**装修标准:decoration_standard*/
	private String decoration_standard;

	/**楼盘地址:built_address*/
	private String built_address;

	/**最近开盘日期（yyyyMMdd）:open_date*/
	private String open_date;

	/**购房优惠:purchase_discount*/
	private String purchase_discount;

	/**置业顾问手机:consultant_phone*/
	private String consultant_phone;

	/**开发商全称:develop_name*/
	private String develop_name;

	/**最早交房日期（yyyyMMdd）:launch_date*/
	private String launch_date;

	/**产权年限:year_limit*/
	private int year_limit;

	/**楼盘状态:built_status*/
	private int built_status;

	/**建筑类型:built_type*/
	private String built_type;

	/**容积率:volume_rate*/
	private String volume_rate;

	/**绿化率:greening_rate*/
	private String greening_rate;

	/**规划户数:plan_households*/
	private String plan_households;

	/**规划车位:plan_parking*/
	private String plan_parking;

	/**预售许可:presale_permit*/
	private String presale_permit;

	/**物业公司全称:property_comp_name*/
	private String property_comp_name;

	/**物业费:property_fee*/
	private String property_fee;

	/**供暖方式:hearting_mode*/
	private String hearting_mode;

	/**水电煤气:water_elec*/
	private String water_elec;

	/**项目特色:project_feature*/
	private String project_feature;

	/**景观资源:scenic_resource*/
	private String scenic_resource;

	/**人文配套:humanity_matching*/
	private String humanity_matching;

	/**教育配套:education_matching*/
	private String education_matching;

	/**商业配套:business_matching*/
	private String business_matching;

	/**商务配套:commerce_matching*/
	private String commerce_matching;

	/**休闲配套:leisure_matching*/
	private String leisure_matching;

	/**医院资源:hospital_resource*/
	private String hospital_resource;

	/**展示图片:exhibition_images*/
	private String exhibition_images;

	/**户型图片:apartment_images*/
	private String apartment_images;

	/**看房图片:showings_images*/
	private String showings_images;

	/**看房视频:showings_videos*/
	private String showings_videos;

	/**楼盘类型（00：任我行 01:老友巢）:building_type*/
	private String building_type;

	/**首页展示图片:index_image*/
	private String index_image;
	
	private String trade_area;

	/**:bldId*/
	public int getBldId() {
		return bldId;
	}


	/**:bldId*/
	public void setBldId(int bldId) {
		this.bldId = bldId;
	}
	/**:devId*/
	public int getDevId() {
		return devId;
	}


	/**:devId*/
	public void setDevId(int devId) {
		this.devId = devId;
	}
	/**:provId*/
	public int getProvId() {
		return provId;
	}


	/**:provId*/
	public void setProvId(int provId) {
		this.provId = provId;
	}
	/**:cityId*/
	public int getCityId() {
		return cityId;
	}


	/**:cityId*/
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	/**:showDetail*/
	public String getShowDetail() {
		return showDetail;
	}


	/**:showDetail*/
	public void setShowDetail(String showDetail) {
		this.showDetail = showDetail;
	}
	/**:type*/
	public int getType() {
		return type;
	}


	/**:type*/
	public void setType(int type) {
		this.type = type;
	}
	/**:mark*/
	public String getMark() {
		return mark;
	}


	/**:mark*/
	public void setMark(String mark) {
		this.mark = mark;
	}
	/**:removed*/
	public int getRemoved() {
		return removed;
	}


	/**:removed*/
	public void setRemoved(int removed) {
		this.removed = removed;
	}
	/**:name*/
	public String getName() {
		return name;
	}


	/**:name*/
	public void setName(String name) {
		this.name = name;
	}
	/**项目介绍:project_introduction*/
	public String getProjectIntroduction() {
		return project_introduction;
	}


	/**项目介绍:project_introduction*/
	public void setProjectIntroduction(String project_introduction) {
		this.project_introduction = project_introduction;
	}
	/**户型面积区间段:unit_area*/
	public String getUnitArea() {
		return unit_area;
	}


	/**户型面积区间段:unit_area*/
	public void setUnitArea(String unit_area) {
		this.unit_area = unit_area;
	}
	/**建筑面积:built_area*/
	public BigDecimal getBuiltArea() {
		return built_area;
	}


	/**建筑面积:built_area*/
	public void setBuiltArea(BigDecimal built_area) {
		this.built_area = built_area;
	}
	/**楼盘均价:average_price*/
	public int getAveragePrice() {
		return average_price;
	}


	/**楼盘均价:average_price*/
	public void setAveragePrice(int average_price) {
		this.average_price = average_price;
	}
	/**周边景点:surrounding_scenic*/
	public String getSurroundingScenic() {
		return surrounding_scenic;
	}


	/**周边景点:surrounding_scenic*/
	public void setSurroundingScenic(String surrounding_scenic) {
		this.surrounding_scenic = surrounding_scenic;
	}
	/**物业类型:property_type*/
	public String getPropertyType() {
		return property_type;
	}


	/**物业类型:property_type*/
	public void setPropertyType(String property_type) {
		this.property_type = property_type;
	}
	/**装修标准:decoration_standard*/
	public String getDecorationStandard() {
		return decoration_standard;
	}


	/**装修标准:decoration_standard*/
	public void setDecorationStandard(String decoration_standard) {
		this.decoration_standard = decoration_standard;
	}
	/**楼盘地址:built_address*/
	public String getBuiltAddress() {
		return built_address;
	}


	/**楼盘地址:built_address*/
	public void setBuiltAddress(String built_address) {
		this.built_address = built_address;
	}
	/**最近开盘日期（yyyyMMdd）:open_date*/
	public String getOpenDate() {
		return open_date;
	}


	/**最近开盘日期（yyyyMMdd）:open_date*/
	public void setOpenDate(String open_date) {
		this.open_date = open_date;
	}
	/**购房优惠:purchase_discount*/
	public String getPurchaseDiscount() {
		return purchase_discount;
	}


	/**购房优惠:purchase_discount*/
	public void setPurchaseDiscount(String purchase_discount) {
		this.purchase_discount = purchase_discount;
	}
	/**置业顾问手机:consultant_phone*/
	public String getConsultantPhone() {
		return consultant_phone;
	}


	/**置业顾问手机:consultant_phone*/
	public void setConsultantPhone(String consultant_phone) {
		this.consultant_phone = consultant_phone;
	}
	/**开发商全称:develop_name*/
	public String getDevelopName() {
		return develop_name;
	}


	/**开发商全称:develop_name*/
	public void setDevelopName(String develop_name) {
		this.develop_name = develop_name;
	}
	/**最早交房日期（yyyyMMdd）:launch_date*/
	public String getLaunchDate() {
		return launch_date;
	}


	/**最早交房日期（yyyyMMdd）:launch_date*/
	public void setLaunchDate(String launch_date) {
		this.launch_date = launch_date;
	}
	/**产权年限:year_limit*/
	public int getYearLimit() {
		return year_limit;
	}


	/**产权年限:year_limit*/
	public void setYearLimit(int year_limit) {
		this.year_limit = year_limit;
	}
	/**楼盘状态:built_status*/
	public int getBuiltStatus() {
		return built_status;
	}


	/**楼盘状态:built_status*/
	public void setBuiltStatus(int built_status) {
		this.built_status = built_status;
	}
	/**建筑类型:built_type*/
	public String getBuiltType() {
		return built_type;
	}


	/**建筑类型:built_type*/
	public void setBuiltType(String built_type) {
		this.built_type = built_type;
	}
	/**容积率:volume_rate*/
	public String getVolumeRate() {
		return volume_rate;
	}


	/**容积率:volume_rate*/
	public void setVolumeRate(String volume_rate) {
		this.volume_rate = volume_rate;
	}
	/**绿化率:greening_rate*/
	public String getGreeningRate() {
		return greening_rate;
	}


	/**绿化率:greening_rate*/
	public void setGreeningRate(String greening_rate) {
		this.greening_rate = greening_rate;
	}
	/**规划户数:plan_households*/
	public String getPlanHouseholds() {
		return plan_households;
	}


	/**规划户数:plan_households*/
	public void setPlanHouseholds(String plan_households) {
		this.plan_households = plan_households;
	}
	/**规划车位:plan_parking*/
	public String getPlanParking() {
		return plan_parking;
	}


	/**规划车位:plan_parking*/
	public void setPlanParking(String plan_parking) {
		this.plan_parking = plan_parking;
	}
	/**预售许可:presale_permit*/
	public String getPresalePermit() {
		return presale_permit;
	}


	/**预售许可:presale_permit*/
	public void setPresalePermit(String presale_permit) {
		this.presale_permit = presale_permit;
	}
	/**物业公司全称:property_comp_name*/
	public String getPropertyCompName() {
		return property_comp_name;
	}


	/**物业公司全称:property_comp_name*/
	public void setPropertyCompName(String property_comp_name) {
		this.property_comp_name = property_comp_name;
	}
	/**物业费:property_fee*/
	public String getPropertyFee() {
		return property_fee;
	}


	/**物业费:property_fee*/
	public void setPropertyFee(String property_fee) {
		this.property_fee = property_fee;
	}
	/**供暖方式:hearting_mode*/
	public String getHeartingMode() {
		return hearting_mode;
	}


	/**供暖方式:hearting_mode*/
	public void setHeartingMode(String hearting_mode) {
		this.hearting_mode = hearting_mode;
	}
	/**水电煤气:water_elec*/
	public String getWaterElec() {
		return water_elec;
	}


	/**水电煤气:water_elec*/
	public void setWaterElec(String water_elec) {
		this.water_elec = water_elec;
	}
	/**项目特色:project_feature*/
	public String getProjectFeature() {
		return project_feature;
	}


	/**项目特色:project_feature*/
	public void setProjectFeature(String project_feature) {
		this.project_feature = project_feature;
	}
	/**景观资源:scenic_resource*/
	public String getScenicResource() {
		return scenic_resource;
	}


	/**景观资源:scenic_resource*/
	public void setScenicResource(String scenic_resource) {
		this.scenic_resource = scenic_resource;
	}
	/**人文配套:humanity_matching*/
	public String getHumanityMatching() {
		return humanity_matching;
	}


	/**人文配套:humanity_matching*/
	public void setHumanityMatching(String humanity_matching) {
		this.humanity_matching = humanity_matching;
	}
	/**教育配套:education_matching*/
	public String getEducationMatching() {
		return education_matching;
	}


	/**教育配套:education_matching*/
	public void setEducationMatching(String education_matching) {
		this.education_matching = education_matching;
	}
	/**商业配套:business_matching*/
	public String getBusinessMatching() {
		return business_matching;
	}


	/**商业配套:business_matching*/
	public void setBusinessMatching(String business_matching) {
		this.business_matching = business_matching;
	}
	/**商务配套:commerce_matching*/
	public String getCommerceMatching() {
		return commerce_matching;
	}


	/**商务配套:commerce_matching*/
	public void setCommerceMatching(String commerce_matching) {
		this.commerce_matching = commerce_matching;
	}
	/**休闲配套:leisure_matching*/
	public String getLeisureMatching() {
		return leisure_matching;
	}


	/**休闲配套:leisure_matching*/
	public void setLeisureMatching(String leisure_matching) {
		this.leisure_matching = leisure_matching;
	}
	/**医院资源:hospital_resource*/
	public String getHospitalResource() {
		return hospital_resource;
	}


	/**医院资源:hospital_resource*/
	public void setHospitalResource(String hospital_resource) {
		this.hospital_resource = hospital_resource;
	}
	/**展示图片:exhibition_images*/
	public String getExhibitionImages() {
		return exhibition_images;
	}


	/**展示图片:exhibition_images*/
	public void setExhibitionImages(String exhibition_images) {
		this.exhibition_images = exhibition_images;
	}
	/**户型图片:apartment_images*/
	public String getApartmentImages() {
		return apartment_images;
	}


	/**户型图片:apartment_images*/
	public void setApartmentImages(String apartment_images) {
		this.apartment_images = apartment_images;
	}
	/**看房图片:showings_images*/
	public String getShowingsImages() {
		return showings_images;
	}


	/**看房图片:showings_images*/
	public void setShowingsImages(String showings_images) {
		this.showings_images = showings_images;
	}
	/**看房视频:showings_videos*/
	public String getShowingsVideos() {
		return showings_videos;
	}


	/**看房视频:showings_videos*/
	public void setShowingsVideos(String showings_videos) {
		this.showings_videos = showings_videos;
	}
	/**楼盘类型（00：任我行 01:老友巢）:building_type*/
	public String getBuildingType() {
		return building_type;
	}


	/**楼盘类型（00：任我行 01:老友巢）:building_type*/
	public void setBuildingType(String building_type) {
		this.building_type = building_type;
	}
	/**首页展示图片:index_image*/
	public String getIndexImage() {
		return index_image;
	}


	/**首页展示图片:index_image*/
	public void setIndexImage(String index_image) {
		this.index_image = index_image;
	}
	public String getTrade_area() {
		return trade_area;
	}
	public void setTrade_area(String trade_area) {
		this.trade_area = trade_area;
	}

}
