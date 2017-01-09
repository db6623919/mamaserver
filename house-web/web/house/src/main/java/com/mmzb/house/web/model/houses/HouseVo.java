package com.mmzb.house.web.model.houses;

import java.util.List;

/**
 * 房源Vo
 * @author whl
 *
 */
public class HouseVo {
	
	/** 房源id */
	private long houseId;
	
	/** 房源名称 */
	private String name;
	
	/** 所属商圈id：{ "businessArea":["1","2","3"]}，1、2、3分别代表商圈一、二、三 */
	private List<String> businessAreaList;
	
	/** 价格 */
	private String price;
	
	/** 市场价 */
	private double totalAmt;
	
	/** 房源套数 */
	private int totalRoomNum;
	
	/** 价格范围 */
	private int priceRange;
	
	/** 人数 */
	private int personNum;
	
	/** 户型房间数 */
	private int roomNum;
	
	/** 标签：{ " tagList ":["1","2","3"]}，1、2、3分别代表"经济型","舒适型","海景房"*/
	private List<String> tagNameList;
	
	
	/** 房源图片 */
	private String imageUrl;
	
	
	private int collectFlag;
	
	/** 合作程度*/
	private String specialTag;
	
	/** 市场价*/
	private int marketPrice;

	/** 折扣描述*/
	private String discountDescription;
	
	/** 房间描述*/
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscountDescription() {
		return discountDescription;
	}

	public void setDiscountDescription(String discountDescription) {
		this.discountDescription = discountDescription;
	}

	public int getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getSpecialTag() {
		return specialTag;
	}

	public void setSpecialTag(String specialTag) {
		this.specialTag = specialTag;
	}

	public int getCollectFlag() {
		return collectFlag;
	}

	public void setCollectFlag(int collectFlag) {
		this.collectFlag = collectFlag;
	}

	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getBusinessAreaList() {
		return businessAreaList;
	}

	public void setBusinessAreaList(List<String> businessAreaList) {
		this.businessAreaList = businessAreaList;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getTotalRoomNum() {
		return totalRoomNum;
	}

	public void setTotalRoomNum(int totalRoomNum) {
		this.totalRoomNum = totalRoomNum;
	}

	public int getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(int priceRange) {
		this.priceRange = priceRange;
	}

	public int getPersonNum() {
		return personNum;
	}

	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getTagNameList() {
		return tagNameList;
	}

	public void setTagNameList(List<String> tagNameList) {
		this.tagNameList = tagNameList;
	}
	
	

}
