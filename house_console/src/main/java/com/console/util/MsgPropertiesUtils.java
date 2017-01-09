package com.console.util;

import java.util.Properties;

/**
 * 读取配置文件
 * @author majiafei
 *
 */
public final class MsgPropertiesUtils
{
	private static Properties properties;
	
	public static void setProperties(Properties properties)
	{
		MsgPropertiesUtils.properties = properties;
	}
	
	public static String getProperty(String key)
	{
		return properties.getProperty(key);
	}
	
	public static int getPageSize()
	{
		String pageSize = properties.getProperty("page_size", "10");
		try 
		{
			return Integer.valueOf(pageSize);
		} 
		catch (Exception e) 
		{
			return 10;
		}
				
	}
	
	public static String getUploadHouseUrl()
	{
		return properties.getProperty("upload_house_url");
	}
	
	public static String getChargeUrl()
	{
		return properties.getProperty("chargeUrl");
	}
	
	public static String getMaMaServerUrl()
	{
		return properties.getProperty("mamaserver_url");
	}
	
	public static String getCostumerUrl()
	{
		return properties.getProperty("customer_url");
	}
	
	public static String getMerchantUrl()
	{
		return properties.getProperty("merchant_url");
	}
	
	public static String getBillPath() {
		return properties.getProperty("bill.path");
	}
	
	public static String getExportPath() {
		return properties.getProperty("export.path");
	}
}
