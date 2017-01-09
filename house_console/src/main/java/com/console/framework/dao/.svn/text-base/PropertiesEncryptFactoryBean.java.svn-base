package com.console.framework.dao;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

import com.console.util.DES;

public class PropertiesEncryptFactoryBean implements FactoryBean<Object> {

	private Properties properties;

	public Object getObject() throws Exception {
		return getProperties();
	}

	public Class<Properties> getObjectType() {
		return java.util.Properties.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties inProperties) {
		this.properties = inProperties;
		String originalUsername = properties.getProperty("username");
		String originalPassword = properties.getProperty("password");
		if (originalUsername != null) {
			String newUsername = deEncryptUsername(originalUsername);
			properties.put("user", newUsername);
		}
		if (originalPassword != null) {
			String newPassword = deEncryptPassword(originalPassword);
			properties.put("password", newPassword);
		}
	}

	private String deEncryptUsername(String originalUsername) {
		return deEncryptString(originalUsername);
	}

	private String deEncryptPassword(String originalPassword) {
		return deEncryptString(originalPassword);
	}
	

	private String deEncryptString(String originalString) {
		try {
			return DES.Decode(originalString).toString().trim();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws UnsupportedEncodingException {
		DES m = new DES();
		String s = "!mmsfang%";
		String miwen = m.Encode(s);
		System.out.println("密文:[" + miwen + "]");
		
		miwen = "3C2DA8FCD7948996C90F72EF86272E74";
		String minwen = m.Decode(miwen).toString();
		System.out.println("明文:[" + minwen.trim() + "]");
	}
}