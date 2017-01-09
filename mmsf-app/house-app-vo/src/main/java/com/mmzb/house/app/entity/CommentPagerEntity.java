package com.mmzb.house.app.entity;

import java.util.List;

public class CommentPagerEntity
{
	/** 评论列表  **/
	private List<CommentEntity> sourceList;
	
	/** 总记录数 */
	private int totalNum;

	public List<CommentEntity> getSourceList()
	{
		return sourceList;
	}

	public void setSourceList(List<CommentEntity> sourceList)
	{
		this.sourceList = sourceList;
	}

	public int getTotalNum()
	{
		return totalNum;
	}

	public void setTotalNum(int totalNum)
	{
		this.totalNum = totalNum;
	}

}
