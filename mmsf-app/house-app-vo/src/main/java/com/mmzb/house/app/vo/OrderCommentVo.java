package com.mmzb.house.app.vo;

import java.util.List;

import com.mmzb.house.app.entity.CommentEntity;

/**
 * 订单评论
 * @author lenovo
 *
 */
public class OrderCommentVo
{
	private List<CommentEntity> comments;
	
	private User userInfo;
	
	//分页信息
	private Page pager;

	public List<CommentEntity> getComments()
	{
		return comments;
	}

	public void setComments(List<CommentEntity> comments)
	{
		this.comments = comments;
	}

	public Page getPager()
	{
		return pager;
	}

	public void setPager(Page pager)
	{
		this.pager = pager;
	}

	public User getUserInfo()
	{
		return userInfo;
	}

	public void setUserInfo(User userInfo)
	{
		this.userInfo = userInfo;
	}

}
