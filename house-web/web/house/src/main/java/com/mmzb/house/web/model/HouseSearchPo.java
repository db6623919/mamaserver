package com.mmzb.house.web.model;

import java.util.Date;
import java.util.List;

/** 房源搜索实体类 */
public class HouseSearchPo {

	/** 房源id */
	private long houseId;
	
	/** 所属城市id */
	private String cityId;
	
	/** 房源名称 */
	private String name;
	
	/** 所属商圈id：{ "businessArea":["1","2","3"]}，1、2、3分别代表商圈一、二、三 */
	private List<String> businessAreaList;
	
	/** 价格 */
	private int price;
	
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
	private List<Integer> tagList;
	
	/** 关键字:
		{
		     key:"businessAreaName": value: ["商圈一","商圈二","商圈三"],
		     key:"name":value:"房源名称"
		}
	 *  */
	private List<KeyWordVo> keyWord;
	
	/** 房源图片 */
	private String imageUrl;
	
	/** 是否推荐:1、推荐；0、不推荐； */
	private int isRecommend;
	
	/** 推荐时间 */
	private Date recommendTime;
	
	/** 评分 */
	private double score;
	
	/** 上线时间 */
	private Date onLineTime;
	
	/** 是否删除：1:可用；0、软删除 */
	private int removed;
	
	/** 市场价*/
	private int marketPrice;

	public int getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}

	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
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

	public int getPriceRange() {
		return priceRange;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public List<Integer> getTagList() {
		return tagList;
	}

	public void setTagList(List<Integer> tagList) {
		this.tagList = tagList;
	}

	public List<KeyWordVo> getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(List<KeyWordVo> keyWord) {
		this.keyWord = keyWord;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Date getOnLineTime() {
		return onLineTime;
	}

	public void setOnLineTime(Date onLineTime) {
		this.onLineTime = onLineTime;
	}

	public int getRemoved() {
		return removed;
	}

	public void setRemoved(int removed) {
		this.removed = removed;
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
	
}