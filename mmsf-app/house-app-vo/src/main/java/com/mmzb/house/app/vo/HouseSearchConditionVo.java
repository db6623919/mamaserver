package com.mmzb.house.app.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 房源搜索条件VO
 * @author majiafei
 *
 */
public class HouseSearchConditionVo
{
	//房源ID
	private int[] houseIds; 
	
	//服务名称：
	private String service;
	
	//是否需要搜索选项数据
	private boolean needSearchOption;
	
	//城市ID
	private Integer cityId;
	
	//入住时间
	private String checkinDate;
	
	//离开时间
	private String checkoutDate;

	//区域ID
	private Integer areaId;
	
	//价格区间，默认 0:不限, 1：200以下，  2：200-300，  3：300-500， 4：500以上 
	private Integer priceSection;
	
	//入住人数
	private Integer peopleCount;
	
	//房间数
	private Integer roomCount;
	
	//标签
	private Integer[] tags;
	
	//搜索关键字
	private String keyword;
	
	//排序方法：0推荐排序 3价格高到低 2价格低到高
	private Integer sort;
	
	//当前页数
	private Integer pageIndex;
	
	//每页条数
	private Integer pageSize;
	
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

	public Integer getCityId()
	{
		return cityId;
	}

	public void setCityId(Integer cityId)
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

	public Integer getAreaId()
	{
		return areaId;
	}

	public void setAreaId(Integer areaId)
	{
		this.areaId = areaId;
	}

	public Integer getPriceSection()
	{
		return priceSection;
	}

	public void setPriceSection(Integer priceSection)
	{
		this.priceSection = priceSection;
	}

	public Integer getPeopleCount()
	{
		return peopleCount;
	}

	public void setPeopleCount(Integer peopleCount)
	{
		this.peopleCount = peopleCount;
	}

	public Integer getRoomCount()
	{
		return roomCount;
	}

	public void setRoomCount(Integer roomCount)
	{
		this.roomCount = roomCount;
	}

	public Integer[] getTags()
	{
		return tags;
	}

	public void setTags(Integer[] tags)
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

	public Integer getSort()
	{
		return sort;
	}

	public void setSort(Integer sort)
	{
		this.sort = sort;
	}

	public Integer getPageIndex()
	{
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex)
	{
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
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

	public int[] getHouseIds()
	{
		return houseIds;
	}

	public void setHouseIds(int[] houseIds)
	{
		this.houseIds = houseIds;
	}
	
}
