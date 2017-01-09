package com.mama.server.main.dao.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 订单评论
 * @author majiafei
 *
 */
@Document(collection = "house.comments")
public class OrderCommentPo
{
	//主键id，即评论id,自增
	@Id
	private String id;
	
	//用户ID
	@Field("UID")
	private long uid;
	
	//订单ID
	@Field("ORDERID")
	private String orderId;
	
	//用户电话号码
	@Field("USER_PHONE")
	private String userPhone;
	
	//房源ID
	@Field("HOUSE_ID")
	private int houseId;
	
	//评分
	@Field("SCORE")
	private int score;
	
	//评论
	@Field("COMMENTS")
	private String comments;
	
	//图片
	@Field("IMAGE")
	private String[] images;
	
	//是否置顶,若为空则不置顶
	@Field("RECOMMEND_TIME")
	private Long recommendTime;
	
	//订单状态，0：未审核1：审核通过2：审核拒绝
	@Field("STATUS")
	private int status;
	
	//是否已删除，0：未删除1：软删除
	@Field("IS_REMOVED")
	private int removed;
	
	//创建时间
	@Field("CREATE_TIME")
	private long createTime;
	
	//修改时间
	@Field("UPDATE_TIME")
	private long updateTime;
	
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public long getUid()
	{
		return uid;
	}

	public void setUid(long uid)
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

	public String[] getImages()
	{
		return images;
	}

	public void setImages(String[] images)
	{
		this.images = images;
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

	public long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(long createTime)
	{
		this.createTime = createTime;
	}

	public long getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(long updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getUserPhone()
	{
		return userPhone;
	}

	public void setUserPhone(String userPhone)
	{
		this.userPhone = userPhone;
	}

	public Long getRecommendTime()
	{
		return recommendTime;
	}

	public void setRecommendTime(Long recommendTime)
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

}
