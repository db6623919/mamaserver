package com.mama.server.main.dao.model.crowd;

import java.util.Date;

/**
 * 众筹图片
 * @author majiafei
 *
 */
public class ProjectImagePo
{
	//主键，自增
	private Integer id;
	
	//众筹项目ID
	private Integer projectId;
	
	//图片URL
	private String imageUrl;
	
	//图片名称
	private String name;
	
	//类型
//	private int type;
	
	//备注
	private String remark;
	
	//创建时间
	private Date createTime;
	
	//修改时间
	private Date updateTime;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getProjectId()
	{
		return projectId;
	}

	public void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

//	public int getType()
//	{
//		return type;
//	}
//
//	public void setType(int type)
//	{
//		this.type = type;
//	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
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

}
