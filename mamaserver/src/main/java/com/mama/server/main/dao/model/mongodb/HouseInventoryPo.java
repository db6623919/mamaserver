package com.mama.server.main.dao.model.mongodb;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mama.server.main.dao.vo.KeyWordVo;

@Document(collection = "house.search")
public class HouseInventoryPo
{
	/** 房源id */
	@Id
	private long houseId;
	
	/** 所属城市id */
	private int cityId;
	
	/** 房源名称 */
	private String name;
	
	/** 所属商圈id：{ "businessArea":["1","2","3"]}，1、2、3分别代表商圈一、二、三 */
	private List<Integer> businessAreaList;
	
	/** 价格 */
	private int price;
	
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
	private long recommendTime;
	
	/** 评分 */
	private double score;
	
	/** 上线时间 */
	private long onLineTime;
	
	/** 是否删除：0、可用; 1:软删除；2、已下架； */
	private int removed;
	
	private List<DateInventory> inventory;

	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
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

	public List<Integer> getBusinessAreaList() {
		return businessAreaList;
	}

	public void setBusinessAreaList(List<Integer> businessAreaList) {
		this.businessAreaList = businessAreaList;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public long getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(long recommendTime) {
		this.recommendTime = recommendTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public long getOnLineTime() {
		return onLineTime;
	}

	public void setOnLineTime(long onLineTime) {
		this.onLineTime = onLineTime;
	}

	public int getRemoved() {
		return removed;
	}

	public void setRemoved(int removed) {
		this.removed = removed;
	}

	public List<DateInventory> getInventory()
	{
		return inventory;
	}

	public void setInventory(List<DateInventory> inventory)
	{
		this.inventory = inventory;
	}
}
