package com.console.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年3月7日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
public class DateUtil {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	final public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	public static String format(Date date) {
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取两个日期之间相差的天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static int diffDays(String beginDate, String endDate, String format) throws ParseException {
		if (StringUtil.isEmpty(format)) {
			format = DEFAULT_DATE_FORMAT;
		}
		Date beginday = new SimpleDateFormat(format).parse(beginDate);
		Date endday = new SimpleDateFormat(format).parse(endDate);
		int d = (int) ((endday.getTime() - beginday.getTime()) / (1000 * 60 * 60 * 24));
		return d;
	}

	public static int diffDays(String beginDate, String endDate) throws ParseException {
		return diffDays(beginDate, endDate, null);
	}

	/**
	 * 得到两个日期之间的日期列表，包括开始日期和结算日期
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getDateList(String beginDate, String endDate, String format) throws ParseException {
		if (StringUtil.isEmpty(format)) {
			format = DEFAULT_DATE_FORMAT;
		}
		int days = diffDays(beginDate, endDate, format);
		List<String> datelist = new ArrayList<String>();
		Date d = new SimpleDateFormat(format).parse(beginDate);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		for (int i = 0; i <= days; i++) {
			datelist.add(new SimpleDateFormat(format).format(d));
			c.add(Calendar.DATE, 1);
			d = c.getTime();
		}
		return datelist;
	}

	public static List<String> getDateList(String beginDate, String endDate) throws ParseException {
		return getDateList(beginDate, endDate, null);
	}

	public static Date getDate(String dateStr, String format) throws ParseException {
		if (StringUtil.isEmpty(format)) {
			format = DEFAULT_DATE_FORMAT;
		}
		Date d = new SimpleDateFormat(format).parse(dateStr);
		return d;
	}
	
	

	public static String getDateFormatString(Date d, String format) {
		if (StringUtil.isEmpty(format)) {
			format = DEFAULT_DATE_FORMAT;
		}
		return new SimpleDateFormat(format).format(d);
	}
	
	public static String getDateFormatString(Date d){
		return getDateFormatString(d, null);
	}
	
	public static String modify(String beginDate, int n, String format) throws ParseException{
		if (StringUtil.isEmpty(format)) {
			format = DEFAULT_DATE_FORMAT;
		}
		Date d = new SimpleDateFormat(format).parse(beginDate);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, n);
		d = c.getTime();
		return new SimpleDateFormat(format).format(d);	
	}
	
	public static String modify(String beginDate, int n) throws ParseException{
		return modify(beginDate,n ,null);
	}

}