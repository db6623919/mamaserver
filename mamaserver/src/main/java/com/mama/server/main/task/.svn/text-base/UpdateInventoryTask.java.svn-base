package com.mama.server.main.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.mongodb.HouseInventory;
import com.mama.server.main.service.IHouseDetailService;
import com.mama.server.main.service.IHouseSearchService;

@Component
public class UpdateInventoryTask
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateInventoryTask.class); 
	
	@Resource
	private IHouseDetailService houseDetailService;
	
	@Resource
	private IHouseSearchService houseSearchService;
	
	private static final long DAY_TIME = 24 * 60 * 60 * 1000;
	
	public void updateInventory()
	{
		try 
		{
			//将代表前一天的库存元素更新为，第120天的库存信息
			List<HouseInventory> inventories = houseDetailService.getAllHouseInventory();
			if(CollectionUtils.isEmpty(inventories))
			{
				return;
			}
			
			Month month = getMonthInfo();
			long firstDay = month.getStartDay();
			long lastDay = month.getEndDay();
			long delStartDay = month.getDelStartDay();
			long delEndDay = month.getDelEndDay();
			
			for(HouseInventory inventory : inventories)
			{
				//删除上个月的库存
				List<Long> delInventoryList = new ArrayList<Long>();
				for(long i = delStartDay; i <= delEndDay; i += DAY_TIME)
				{
					delInventoryList.add(i);
				}
				houseSearchService.deletInventory(inventory.getHouseId(), delInventoryList);
				
				//新增加一个月的库存
				Map<Long, Integer> addInventoryMap = new HashMap<Long, Integer>();
				for(long i = firstDay; i <= lastDay; i += DAY_TIME )
				{
					addInventoryMap.put(i, inventory.getRoomNum());
				}
				houseSearchService.addInventory(inventory.getHouseId(), addInventoryMap);
			}
					
		}
		catch (Exception e) 
		{
			LOGGER.error("failed to modify inventory.");
		}
	}
	
	private Month getMonthInfo() throws ParseException
	{
		Date nowDay = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(nowDay);
		int currentMonth = Integer.valueOf(today.split("-")[1]);
		int currentYear = Integer.valueOf(today.split("-")[0]);
		
		int tmpAddMonth = currentMonth + 3;
		int addMonth = tmpAddMonth % 12;
		int addYear = (tmpAddMonth > 12) ? (currentYear + 1) : currentYear;
		
		String startDate = addYear + "-" + addMonth + "-01";
		long startDay = sdf.parse(startDate).getTime();
		String endDate = addYear + "-" + addMonth + "-" + getMaxDayOfMonth(addMonth, addYear);
		long endDay = sdf.parse(endDate).getTime();
		
		int delMonth = currentMonth - 1;
		int delYear = currentYear;
		if (delMonth == 0)
		{
			delMonth = 1;
			delYear -= 1;
		}
		String delStartDate = delYear + "-" + delMonth + "-01";
		long delStartDay = sdf.parse(delStartDate).getTime();
		String delEndDate = delYear + "-" + delMonth + "-" + getMaxDayOfMonth(delMonth, delYear);
		long delEndDay = sdf.parse(delEndDate).getTime();
		
		return new Month(startDay, endDay, delStartDay, delEndDay);
	}
	
	private class Month
	{
		private long startDay;
		
		private long endDay;
		
		private long delStartDay;
		
		private long delEndDay;
		
		public Month(long startDay, long endDay, long delStartDay, long delEndDay)
		{
			this.startDay = startDay;
			this.endDay = endDay;
			this.delStartDay = delStartDay;
			this.delEndDay = delEndDay;
		}

		public long getStartDay()
		{
			return startDay;
		}

		public void setStartDay(long startDay)
		{
			this.startDay = startDay;
		}

		public long getEndDay()
		{
			return endDay;
		}

		public void setEndDay(long endDay)
		{
			this.endDay = endDay;
		}

		public long getDelStartDay()
		{
			return delStartDay;
		}

		public void setDelStartDay(long delStartDay)
		{
			this.delStartDay = delStartDay;
		}

		public long getDelEndDay()
		{
			return delEndDay;
		}

		public void setDelEndDay(long delEndDay)
		{
			this.delEndDay = delEndDay;
		}
	}
	
	private int getMaxDayOfMonth(int month, int year)
	{
		int[] bigMonth = new int[]{1, 3, 5, 7, 8, 10, 12};
		
		//大月31天
		if (ArrayUtils.contains(bigMonth, month)) 
		{
			return 31;
		}
		
		//小月30天
		int[] smallMonth = new int[]{4, 6, 9, 11};
		if (ArrayUtils.contains(smallMonth, month)) 
		{
			return 30;
		}
		
		//闰月29天
		if ((((year % 4) == 0) && (year % 100) != 0) || ((year % 400) == 0))
		{
			return 29;
		}
		else
		{
			return 28;
		}
	}
	
	public static void main(String[] args)
	{
		UpdateInventoryTask task = new UpdateInventoryTask();
		try 
		{
			Month month = task.getMonthInfo();
			System.out.println(month.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
