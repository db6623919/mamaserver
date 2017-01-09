package com.mama.server.main.dao.model;

import java.util.Date;

import com.mama.server.main.dao.Pagination;

/**
 * 主题活动
 * @author whl
 *
 */
public class TopicActivityPo extends Pagination{

	private static final long serialVersionUID = 1L;
	private Integer id;
	/** 活动编号 */
	//private String activityCode;
	/** 活动名称 */
	private String activityName;
	/** 活动类型  (1、单身 2、亲子 3、老人 4、团建)*/
	private String activityType;
	/** 图片url */
	private String imgUrl;
	/** 开始时间 */
	//private Date satrtTime;
	/** 结束时间 */
	//private Date endTime;
	/** 状态(0、下线  1、上线) */
	//private String status;
	/** 标题 */
	private String title;
	/** 描述 */
	private String introduction;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	
	//客栈关联数
	private int shopCount;
	
	public int getShopCount() {
		return shopCount;
	}
	public void setShopCount(int shopCount) {
		this.shopCount = shopCount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/*public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}*/
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/*public Date getSatrtTime() {
		return satrtTime;
	}
	public void setSatrtTime(Date satrtTime) {
		this.satrtTime = satrtTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}*/
	public String getIntroduction() {
		return introduction;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
