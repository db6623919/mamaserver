package com.mmzb.house.web.model.houses;

/**
 * 房间设施
 * @author lenovo
 *
 */
public class RoomFacilityVo
{
	//设施名称
	private String name;
	
	//图标url
	private String iconUrl;
	
	public RoomFacilityVo(String name, String iconUrl)
	{
		this.name = name;
		this.iconUrl = iconUrl;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIconUrl()
	{
		return iconUrl;
	}

	public void setIconUrl(String iconUrl)
	{
		this.iconUrl = iconUrl;
	}

}
