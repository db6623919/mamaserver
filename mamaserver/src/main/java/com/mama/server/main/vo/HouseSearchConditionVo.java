package com.mama.server.main.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 房源搜索条件VO
 * @author majiafei
 *
 */
public class HouseSearchConditionVo
{
	//服务名称：
	private String service;
	
	//是否需要搜索选项数据
	private boolean needSearchOption;
	
	//城市ID
	private int cityId;
	
	//入住时间
	private String checkinDate;
	
	//离开时间
	private String checkoutDate;

	//区域ID
	private int areaId;
	
	//价格区间，默认 0：200以下，  1：200-300，  2：300-500，  3：500以上 
	private int priceSection;
	
	//入住人数
	private int peopleCount;
	
	//房间数
	private int roomCount;
	
	//标签
	private int[] tags;
	
	//搜索关键字
	private String keyword;
	
	//排序方法：0推荐排序 1价格高到低 2价格低到高
	private int sort;
	
	//当前页数
	private int pageIndex;
	
	//每页条数
	private int pageSize;
	
	//加密类型
	private String signType;
	
	private String sign;

	public String getService()
	{
		return service;
	}

	public void setService(String service)
	{
		this.service = service;
	}

	public boolean isNeedSearchOption()
	{
		return needSearchOption;
	}

	public void setNeedSearchOption(boolean needSearchOption)
	{
		this.needSearchOption = needSearchOption;
	}

	public int getCityId()
	{
		return cityId;
	}

	public void setCityId(int cityId)
	{
		this.cityId = cityId;
	}

	public String getCheckinDate()
	{
		return checkinDate;
	}

	public void setCheckinDate(String checkinDate)
	{
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate()
	{
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate)
	{
		this.checkoutDate = checkoutDate;
	}

	public int getAreaId()
	{
		return areaId;
	}

	public void setAreaId(int areaId)
	{
		this.areaId = areaId;
	}

	public int getPriceSection()
	{
		return priceSection;
	}

	public void setPriceSection(int priceSection)
	{
		this.priceSection = priceSection;
	}

	public int getPeopleCount()
	{
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount)
	{
		this.peopleCount = peopleCount;
	}

	public int getRoomCount()
	{
		return roomCount;
	}

	public void setRoomCount(int roomCount)
	{
		this.roomCount = roomCount;
	}

	public int[] getTags()
	{
		return tags;
	}

	public void setTags(int[] tags)
	{
		this.tags = tags;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}

	public int getPageIndex()
	{
		return pageIndex;
	}

	public void setPageIndex(int pageIndex)
	{
		this.pageIndex = pageIndex;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public String getSignType()
	{
		return signType;
	}

	public void setSignType(String signType)
	{
		this.signType = signType;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}
	
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		  .append("service", service)
		  .append("needSearchOption", needSearchOption)
		  .append("cityId", cityId)
		  .append("checkinDate", checkinDate)
		  .append("checkoutDate", checkoutDate)
		  .append("areaId", areaId)
		  .append("priceSection", priceSection)
		  .append("peopleCount", peopleCount)
		  .append("roomCount", roomCount)
		  .append("tags", tags)
		  .append("keyword", keyword)
		  .append("sort", sort)
		  .append("pageIndex", pageIndex)
		  .append("pageSize", pageSize)
		  .append("signType", signType)
		  .append("sign", sign)
		  .toString();
	}
	
}
