package com.console.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DefineTag {

	public static String status(String state){
		if(state.equals("0")){
			return "禁用";
		}else if(state.equals("1")){
			return "启用";
		}else{
			return "启用";
		}
	}
	public static String date(String date){
		if(date.trim().indexOf(" ")==-1)
			return "";
		return date.split(" ")[0];
		
	}
	
	/**
	 * 时间格式化：将Date型转化成String
	 * @author 荣琪
	 * @createDate 2012年9月28日15:23:18
	 */
	public static String formatDateTOStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 时间格式化：将Date型转化成String的年月日格式
	 * @author 荣琪
	 * @createDate 2012年9月28日15:23:18
	 */
	public static String formatDateTOStrNYR(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 时间格式化：将年月日和时分秒的字符串转化成yyyy-mm-dd 24hh:mi:ss
	 */
	public static String formatDateStrToStr(String nyr,String sfm){
		String result = "";
		if(nyr == null || "".equals(nyr) || "null".equals(nyr)
				|| sfm == null || "".equals(sfm) || "null".equals(sfm)){
			return result;
		}
		String dateStr = nyr+sfm;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf1.parse(dateStr);
			result = sdf2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	/**
	 * 时间格式化：将TimeStamp型转化为String
	 * @author 荣琪
	 * @createDate 2012年10月11日17:14:12
	 */
	public static String formatTimeStampTOStr(Timestamp timestamp){
		if(timestamp == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}
	
	/**
	 * 时间格式化：将TimeStamp型转化为String的年月日格式
	 * @author 荣琪
	 * @createDate 2012年10月11日17:14:12
	 */
	public static String formatTimeStampTOStrNYR(Timestamp timestamp){
		if(timestamp == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(timestamp);
	}
	
	/**
	 * 时间格式化：将TimeStamp型转化为String的年月格式
	 * @author 乐生
	 * @createDate 2012年11月15日10:10:38
	 */
	public static String formatTimeStampTOStrNY(Timestamp timestamp){
		if(timestamp == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(timestamp);
	}
	
	/**
	 * 将数据库里取出来String格式日期转化成String的年月日格式
	 * @author 乐生
	 * @createDate 2012年10月25日17:21:28
	 */
	public static String formatStrTOStrNYR(String time){
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
		
		
	}
	
	/**
	 * 时间格式化：将数据库里取出来的String型转化为年月日时分秒型
	 */
	public static String formatDateStrToNYRSFM(String time) {
		String result = "";
		if(time == null || "".equals(time) || "null".equals(time)){
			return result;
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf1.parse(time);
			result = sdf2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	/**
	 * 数据库里去除的钱的单位是分，long型，这个方法可以转换成元，string类型
	 * @author 乐生
	 * @createDate 2012年10月25日17:21:28
	 */
	public static String parseFenToYuan(Long fen){
		if(fen == null) return "";
		return fen/100+"."+(fen%100 < 10 ? "0"+fen%100 : fen%100);
	}
	
	/**
	 * 将分转化成元
	 */
	public static String formateFenToYuan(String fen){
		if(StringUtil.isEmpty(fen)) return fen;
		return StringUtil.formatAmountFromFenToYuan(fen);
	}
	
	/**
	 * 分相减后转化成元
	 */
	public static String formateMinusYuan(Long txnAmt, Long feeNum) {
		String result = "";
		if(txnAmt == null || "".equals(txnAmt) || "null".equals(txnAmt)
			|| feeNum == null || "".equals(feeNum) || "null".equals(feeNum)) {
			return result;
		}else if(txnAmt < feeNum) {
			return result;
		}else {
			long feeAmt = txnAmt - feeNum;
			return parseFenToYuan(feeAmt);
		}
	}
	
	
	/**
	 * 剪切字符串
	 * @param str 要剪切的字符串
	 * @param start 起始位置
	 * @param length 长度
	 * @return
	 */
	public static String subString(String str, Integer start, Integer length){
		if(StringUtil.isEmpty(str)) return str;
		String result;
		int len = str.length();
		if(len > start){
			if(len >= start+length) result = str.substring(start, start+length) + "...";
			else result = str.substring(start, len);
		}else{
			result = subString(str, 0, length);
		}
		return result;
	}
	/**
	 * 对传进来的机票费用进行分割 格式化
	 * @return
	 */
	public static String formatJpFy(String str, Integer index){
		/*if(StringUtil.isEmpty(str) || "null".equalsIgnoreCase(str)) return "0";
		String[] fees = str.split("#");
		String fee =  fees.length >= index+1?fees[index]:"0";*/
		String fee = getAssignStr(str, index);
		fee = StringUtil.isEmpty(fee)?"0":fee;
		return StringUtil.formatAmountFromFenToYuan(fee);
	}
	
	/**
	 * 对含有"#"的字符串分割 获得指定位置的字符串
	 * @param str
	 * @param index
	 * @return
	 */
	public static String getAssignStr(String str, Integer index){
		if(StringUtil.isEmpty(str) || "null".equalsIgnoreCase(str)) return "";
		String[] strs = str.split("#");
		String result =  strs.length >= index+1?strs[index]:"";
		return result;
	}
	
	
	/**
	 * 测试类
	 */
	public static void main(String[] args){
		String operType = "6";
		System.err.println(formateMinusYuan(Long.valueOf(650),Long.valueOf(550)));
	}
	
	/**
	 * 显示带星号的字符串
	 * @param str	原字符串
	 * @param head	可视的头字符个数  最小为1
	 * @param foot	可视的尾字符个数  最小为1
	 * @return
	 */
	public static String showAsterisk(String str,String inthead,String intfoot){
		str = str == null ? "" : str;
		int head = 0;
		int foot = 0;
		head = Integer.valueOf(inthead);
		foot = Integer.valueOf(intfoot);
		head = head <= 0 ? 1 : head;
		foot = foot <= 0 ? 1 : foot;
		
		if(str.length() < (head + foot)){
			return str;
		}
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < str.length() ; i ++){
			if(i >= head && i < (str.length() - foot )){
				sb.append("*");
			}else{
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}
	
	public static String formatDateOrTime(String date, String type){
		SimpleDateFormat sdf1 = null;
		SimpleDateFormat sdf2 = null;
		String result = null;
		try {
			if(StringUtil.isEmpty(date)) return date;
			if("D".equalsIgnoreCase(type)){//日期格式  8位
				sdf1 = new SimpleDateFormat("yyyyMMdd");
				String str = date.substring(0, 8);
				sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				result = sdf2.format(sdf1.parse(str));
			}else if("T".equalsIgnoreCase(type)){//时间类型 6位
				sdf1 = new SimpleDateFormat("HHmmss");
				String str = date.substring(0, 6);
				sdf2 = new SimpleDateFormat("HH:mm:ss");
				result = sdf2.format(sdf1.parse(str));
			}else if("M".equalsIgnoreCase(type)){//月份
				sdf1 = new SimpleDateFormat("yyyyMM");
				String str = date.substring(0, 6);
				sdf2 = new SimpleDateFormat("yyyy-MM");
				result = sdf2.format(sdf1.parse(str));
			}else if("DT".equalsIgnoreCase(type)){//日期加时间  14位
				sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
				String str = date.substring(0, 14);
				sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result = sdf2.format(sdf1.parse(str));
			}else{
				result = "未知类型";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = date;
		}
		
		return result;
	}
	
	public static String formatDateOrTime2(String date){
		
		SimpleDateFormat sdf1 = null;
		SimpleDateFormat sdf2 = null;
		String result = null;
		try {
			if(date != null && date.length() == 8){//日期格式  8位
				sdf1 = new SimpleDateFormat("yyyyMMdd");
				String str = date.substring(0, 8);
				sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				result = sdf2.format(sdf1.parse(str));
			}else if(date != null && date.length() == 6){//时间类型 6位
				sdf1 = new SimpleDateFormat("HHmmss");
				String str = date.substring(0, 6);
				sdf2 = new SimpleDateFormat("HH:mm:ss");
				result = sdf2.format(sdf1.parse(str));
			}else if(date != null && date.length() == 14){//日期加时间  14位
				sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
				String str = date.substring(0, 14);
				sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result = sdf2.format(sdf1.parse(str));
			}else{
				result = date;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = date;
		}
		
		return result;
	}
	
	/**
	 * 对两个数进行加减乘除操作
	 * @param numberA
	 * @param numberB
	 * @param opr
	 * @return
	 */
	public static String calcNumb(String numberA, String numberB, String opr){
		long valA = !StringUtil.isEmpty(numberA)?Long.valueOf(numberA):0;
		long valB = !StringUtil.isEmpty(numberB)?Long.valueOf(numberB):0;
		long result = 0;
		if("+".equals(opr)){
			result = valA + valB;
		}else if("-".equals(opr)){
			result = valA - valB;
		}else if("*".equals(opr)){
			result = valA * valB;
		}else if("/".equals(opr)){
			result = valA / valB;
		}else if("%".equals(opr)){
			result = valA % valB;
		}
		
		return (""+result);
	}
	
	/**
	 * 将字符串的null转化为空字符串
	 * @param input
	 * @return
	 */
	public static String formatNullString(String input){
		if(input==null || "".equals(input) || "null".equals(input)){
			return "";
		}else{
			return input;
		}
	}
}
