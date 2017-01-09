package com.mmzb.house.app.web.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class FinalUtil {
	
	
	
	//年月日
	public static final SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	//保留2位小数点
	public static final DecimalFormat df_2 = new DecimalFormat("0.00");
	
	public static final String PARTNER_ID = "188888888888";
	public static final String TRUE = "T";
	public static final String LOGIN_USER = "sess_userinfo";//sys_user对象
	
	public static final String SESSION_ATTR_NAME_CURRENT_USER = "current_member";//personMember对象
	
	public static final String FILE_SPLIT = "/";
	
	
	public static Date getAddData(String day,Date date)
	{
		day = day.trim();
		String d = day.substring(0, day.length()-1);
		int num = Integer.parseInt(d);
		String unit = day.substring(day.length()-1, day.length());
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		if(unit.equals("月"))
		{
			calendar.add(Calendar.MONTH, num);
		}
		else if(unit.equals("年"))
		{
			calendar.add(Calendar.YEAR, num);
		}
		else {
			calendar.add(Calendar.DATE, num);
		}
		return calendar.getTime();
	}
}
