package com.mama.server.util;

import java.util.Properties;

/**
 * 读取配置文件
 * @author majiafei
 *
 */
public final class PropertiesUtils
{
	private static Properties properties;
	
	public static void setProperties(Properties properties)
	{
		PropertiesUtils.properties = properties;
	}
	
	public static String getProperty(String key)
	{
		return properties.getProperty(key);
	}
	
	/**
	 * 妈妈小管家手机号码
	 * @return
	 */
	public static String getMmManagerPhone()
	{
		return properties.getProperty("mama.manager.phone");
	}
	
	/**
	 * 妈妈家财务手机号码
	 * @return
	 */
	public static String getMmFinancePhone()
	{
		return properties.getProperty("mama.finance.phone");
	}

	/** 获取充值服务url */
	public static String getChargeCenterUrl() {
		return properties.getProperty("charge.center.url");
	}
	
	public static String getSmsServerUrl()
	{
		return properties.getProperty("sms.server.url");
	}
	
	public static String getClickFarmingPhone() {
		return properties.getProperty("clickFarming.phone");
	}
	
	public static String getBillPath() {
		return properties.getProperty("bill.path");
	}
	
	public static String getExportPath() {
		return properties.getProperty("export.path");
	}
	
	/** 获取h5域名地址 */
	public static String getMmsfWapWebUrl() {
		return properties.getProperty("mmsfWapWebUrl");
	}
}
