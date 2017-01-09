package com.mmzb.house.app.entity;

import java.util.Date;

/**
 * 房源评论信息
 * @author majiafei
 *
 */
public class HouseCommentEntity
{
	//主键id，自增
	private Integer id;
	
	//房源ID
	private Integer houseId;
	
	//总评论数
	private Integer totalCommentsNum;
	
	//平均评分
	private Double averageScore;
	
	//置顶评论
	private String summary;
	
	//是否已删除，0：未删除	1：软删除
	private Integer removed;
	
	//创建时间
	private Date createTime;
	
	//修改时间：数据库自动更新
	private Date updateTime;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getHouseId()
	{
		return houseId;
	}

	public void setHouseId(Integer houseId)
	{
		this.houseId = houseId;
	}

	public Integer getTotalCommentsNum()
	{
		return totalCommentsNum;
	}

	public void setTotalCommentsNum(Integer totalCommentsNum)
	{
		this.totalCommentsNum = totalCommentsNum;
	}

	public Double getAverageScore()
	{
		return averageScore;
	}

	public void setAverageScore(Double averageScore)
	{
		this.averageScore = averageScore;
	}

	public Integer getRemoved()
	{
		return removed;
	}

	public void setRemoved(Integer removed)
	{
		this.removed = removed;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

}
