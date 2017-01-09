package com.mmzb.house.app.vo;

import java.util.List;

/**
 * 房源搜索VO
 * @author majiafei
 *
 */
public class HouseSearchVo
{
	//搜索选项数据
	private HouseSearchOption option;
	
	//热房栏目房源列表
	private List<HouseVo> houses;
	
	//分页信息
	private Page pager;
	
	public HouseSearchOption getOption()
	{
		return option;
	}

	public void setOption(HouseSearchOption option)
	{
		this.option = option;
	}

	public List<HouseVo> getHouses()
	{
		return houses;
	}

	public void setHouses(List<HouseVo> houses)
	{
		this.houses = houses;
	}

	public Page getPager()
	{
		return pager;
	}

	public void setPager(Page pager)
	{
		this.pager = pager;
	}

	public static class HouseSearchOption
	{
		//商圈
		private List<NameIdVo> area;
		
		//价格区间
		private List<NameIdVo> priceSections;
		
		//房源标签
		private List<NameIdVo> tags;
		
		//排序规则
		private List<NameIdVo> sorts;

		public List<NameIdVo> getPriceSections()
		{
			return priceSections;
		}

		public void setPriceSections(List<NameIdVo> priceSections)
		{
			this.priceSections = priceSections;
		}

		public List<NameIdVo> getSorts()
		{
			return sorts;
		}

		public void setSorts(List<NameIdVo> sorts)
		{
			this.sorts = sorts;
		}

		public List<NameIdVo> getArea()
		{
			return area;
		}

		public void setArea(List<NameIdVo> area)
		{
			this.area = area;
		}

		public List<NameIdVo> getTags()
		{
			return tags;
		}

		public void setTags(List<NameIdVo> tags)
		{
			this.tags = tags;
		}

	}
	
	public static class NameIdVo
	{
		//名称
		private String name;
		
		//ID
		private int id;
		
		public NameIdVo()
		{
		}
		
		public NameIdVo(int id, String name)
		{
			this.name = name;
			this.id = id;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}
	}
	
}
