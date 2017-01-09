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
@Document(collection = "house.shop")
public class HouseShop {

	@Id
	private String id;
	
	private String shopName;
	private String shopDesc;
	private String bossImage;
	private String bossPhone;
	private String bossWeChat;
	private int cityID;
	private String level;
	private String imgUrl;
	private String type;
	private String bossName;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	
}