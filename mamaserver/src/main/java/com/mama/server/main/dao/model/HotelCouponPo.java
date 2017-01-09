package com.mama.server.main.dao.model;

import java.util.Date;

/**
 * 旅居券实体
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年2月19日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
public class HotelCouponPo {

	private long id;// 旅居券ID
	private String mmWalletId;// 妈妈钱包会员ID
	private String name;// 旅居券名称
	private int status;// 状态: 0: 正常; 2:冻结; 1:未激活; 11:已使用; 12:已过期;
	private int sourceType;// 来源类型
	private String sourceGroupId;// 来源组ID， 可用来分类，批量查询和其他操作。 例如旅居宝产品ID等
	private String sourceId;// 来源ID
	private boolean inSeason;// 是否旺季
	private String disabledDates;// 不可用日期
	private int devId;// 开发商ID
	private int houseId;// 房源ID
	private Date expireTime;// 过期时间，空表示无过期限制
	private Date updateTime;// 修改时间
	private Date createTime;// 创建时间

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceGroupId() {
		return sourceGroupId;
	}

	public void setSourceGroupId(String sourceGroupId) {
		this.sourceGroupId = sourceGroupId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public boolean isInSeason() {
		return inSeason;
	}

	public void setInSeason(boolean inSeason) {
		this.inSeason = inSeason;
	}

	public String getDisabledDates() {
		return disabledDates;
	}

	public void setDisabledDates(String disabledDates) {
		this.disabledDates = disabledDates;
	}

	public int getDevId() {
		return devId;
	}

	public void setDevId(int devId) {
		this.devId = devId;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMmWalletId() {
		return mmWalletId;
	}

	public void setMmWalletId(String mmWalletId) {
		this.mmWalletId = mmWalletId;
	}

}
