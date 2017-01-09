package com.mama.server.main.dao.model;

/**
 * 商家店铺表
 * @author dengbiao
 *
 */
public class HouseShopPo {

	private int id;
	/** 客栈名称 */
	private String shopName;
	/** 客栈描述 */
	private String shopDesc;
	/** boss头像 */
	private String bossImage;
	/** boss电话 */
	private String bossPhone;
	/** boss微信 */
	private String bossWeChat;
	/** 创建时间  */
	private String createTime;
	/** 修改时间 */
	private String updateTime;
	/** 城市ID*/
	private int cityID;
	/** 等级 */
	private String level;
	
	private String imgUrl;
	/** 类型 1、客栈 2、酒店 3、公寓*/
	private String type;
	/** boss姓名  */
	private String bossName;
	
	private String cityName;
	/** 合作关系*/
	private String partnership;
	
	/**  特价状态 */
	private int recommended_status;
	
	public int getRecommended_status() {
		return recommended_status;
	}
	public void setRecommended_status(int recommended_status) {
		this.recommended_status = recommended_status;
	}
	public String getPartnership() {
		return partnership;
	}
	public void setPartnership(String partnership) {
		this.partnership = partnership;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	/** 客栈图片 */
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopDesc() {
		return shopDesc;
	}
	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	public String getBossImage() {
		return bossImage;
	}
	public void setBossImage(String bossImage) {
		this.bossImage = bossImage;
	}
	public String getBossPhone() {
		return bossPhone;
	}
	public void setBossPhone(String bossPhone) {
		this.bossPhone = bossPhone;
	}
	public String getBossWeChat() {
		return bossWeChat;
	}
	public void setBossWeChat(String bossWeChat) {
		this.bossWeChat = bossWeChat;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}