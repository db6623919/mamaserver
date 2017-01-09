package com.console.util;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;


/**
 * @Class       CommonUtil
 * @Description 公共方法类
 * @Author      YY
 * @Version     1.0
 * @Date	    2011-10-29/下午09:29:55
 */
public class CommonUtil {
	
	private static int count = 0;

	/**
	 * @Description 序列号的生成 生成策略:年月日时分毫秒加上5位随机数字 
	 * @return      一个20位长度的序列号
	 * @author      YY
	 * @date	    2011-10-29/下午09:36:47
	 */
	public static String getSerialNO() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		// 序列自加
		count++;
		if (count == 100000) {
			count = 0;
		}
		String par = formatString(String.valueOf(count), 5);
		return sdf.format(new Date()) + par;
	}
	
	// 补位
	public static String formatString(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		} else {
			str = str.substring(0, strLength);
		}
		return str;
	}



	/**
	 * @Description escapeSql
	 * @return      
	 * @author      YY
	 * @date	    2011-10-29/下午09:36:47
	 */
	public static String escapeSql(String data) {
		return StringEscapeUtils.escapeSql(data);
	}
	
	/**
	 * @Description escapeJavaScript
	 * @return      
	 * @author      YY
	 * @date	    2011-10-29/下午09:36:47
	 */
	public static String escapeJavaScript(String data) {
		return StringEscapeUtils.escapeJavaScript(data);
	}
	
	/**
	 * @Description escapeHtml
	 * @return      
	 * @author      YY
	 * @date	    2011-10-29/下午09:36:47
	 */
	public static String escapeHtml(String data) {
		return StringEscapeUtils.escapeHtml(data);
	}
	
	/**
	 * @Description 获取web项目的根目录， E:/WorkSpace/qyqt
	 *              可以在windows,linux下tomcat，weblogic以包方式正确执行    
	 * @author      YY
	 * @date	    2011-10-29/下午09:36:47
	 */
	public static String getRealRootPath() {
		String classPath = null;
		try {
			//toURI() 20% to 空格
			classPath = CommonUtil.class.getClassLoader().getResource("/").toURI().getPath();
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

		String rootPath  = "";
		//windows下
		if("\\".equals(File.separator)){			
			rootPath  = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		//linux下
		if("/".equals(File.separator)){			
			rootPath  = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
	
	
	/**
	 * @Description 获取web项目的根目录
	 *              需在webxml里配置变量和spring监听器 
	 * @author      YY
	 * @date	    2011-10-29/下午09:36:47
	 */
	public static String getRealRootPath2() {		
		return System.getProperty("miugoCSS.root"); 
	}
	
	 


	
}
