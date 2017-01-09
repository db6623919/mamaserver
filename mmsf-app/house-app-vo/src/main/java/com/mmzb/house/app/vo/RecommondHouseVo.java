package com.mmzb.house.app.vo;

/**
 * 推荐搜索房源Vo
 * @author dengbiao
 *
 */
public class RecommondHouseVo {

	private int houseId;
	private int price;
	private int marketPrice;//市场价
	private String specialTag;
	private String description;//几室几厅描述
	private String discountDescription;//折扣
	private String houseName;//房源名
	private String imgUrl;//房源图片
	private String houseUnit="起/晚";//房间单位
	private Boolean favor;//收藏状态
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSpecialTag() {
		return specialTag;
	}
	public void setSpecialTag(String specialTag) {
		this.specialTag = specialTag;
	}
	public String getDiscountDescription() {
		return discountDescription;
	}
	public void setDiscountDescription(String discountDescription) {
		this.discountDescription = discountDescription;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getHouseUnit() {
		return houseUnit;
	}
	public void setHouseUnit(String houseUnit) {
		this.houseUnit = houseUnit;
	}
	public Boolean getFavor() {
		return favor;
	}
	public void setFavor(Boolean favor) {
		this.favor = favor;
	}
	
}