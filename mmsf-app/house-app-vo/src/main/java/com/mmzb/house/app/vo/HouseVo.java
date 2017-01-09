package com.mmzb.house.app.vo;

import java.util.List;

public class HouseVo
{
	// 房源ID
	private int houseId;

	// 房源名称
	private String houseName;

	// 房源图片URL
	private String imgUrl;

	// 房源价格
	private int price;

	// 房间价格单位
	private String houseUnit;

	// 房源描述，范例：2室 宜4人居住 30套
	private String description;
	
	//房源描述文本
	private String subDescription;

	// 收藏状态
	private boolean isFavor;
	
	//房源标签名
	private List<String> tagNames;
	
	//合作程度
	private String specialTag;
	
	//市场价
	private int marketPrice;
	
	//折扣miaos
	private String discountDescription;
	

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

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

	public void setFavor(boolean isFavor) {
		this.isFavor = isFavor;
	}

	public int getHouseId()
	{
		return houseId;
	}

	public void setHouseId(int houseId)
	{
		this.houseId = houseId;
	}

	public String getHouseName()
	{
		return houseName;
	}

	public void setHouseName(String houseName)
	{
		this.houseName = houseName;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public String getHouseUnit()
	{
		return houseUnit;
	}

	public void setHouseUnit(String houseUnit)
	{
		this.houseUnit = houseUnit;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getSubDescription()
	{
		return subDescription;
	}

	public void setSubDescription(String subDescription)
	{
		this.subDescription = subDescription;
	}

	public boolean isFavor()
	{
		return isFavor;
	}

	public void setIsFavor(boolean isFavor)
	{
		this.isFavor = isFavor;
	}

}