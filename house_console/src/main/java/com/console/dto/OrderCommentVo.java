package com.console.dto;

import java.util.List;

/**
 * 订单评论
 * @author majiafei
 *
 */
public class OrderCommentVo
{
	//主键id，即评论id,自增
	private String id;
	
	//用户ID
	private int uid;
	
	private String orderId;
	
	//用户电话号码
	private String userPhone;
	
	//房源ID
	private int houseId;
	
	//评分
	private int score;
	
	//评论
	private String comments;
	
	//图片
	private int imageSize;
	
	//是否置顶,若为空则不置顶
	private long recommendTime;
	
	//订单状态，0：未审核1：审核通过2：审核拒绝
	private int status;
	
	//是否已删除，0：未删除1：软删除
	private int removed;
	
	//评论时间
	private String createTime;

	//图片
	private List images;
	


	public List getImages() {
		return images;
	}

	public void setImages(List images) {
		this.images = images;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getUid()
	{
		return uid;
	}

	public void setUid(int uid)
	{
		this.uid = uid;
	}

	public int getHouseId()
	{
		return houseId;
	}

	public void setHouseId(int houseId)
	{
		this.houseId = houseId;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public int getImageSize() {
		return imageSize;
	}

	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getRemoved()
	{
		return removed;
	}

	public void setRemoved(int removed)
	{
		this.removed = removed;
	}

	public String getUserPhone()
	{
		return userPhone;
	}

	public void setUserPhone(String userPhone)
	{
		this.userPhone = userPhone;
	}

	public long getRecommendTime()
	{
		return recommendTime;
	}

	public void setRecommendTime(long recommendTime)
	{
		this.recommendTime = recommendTime;
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

}
