/********************************************************************
 * Copyright    :   Shanghai Mybank Information Technology Co., Ltd.
 * 
 * Filename     :   HttpUtil.java
 * Author       :   YY
 * Date         :   2013-8-29
 * Version      :   V1.00
 * Description  :   
 *
 * History      :   Modify Id  |  Date  |  Origin  |  Description
 *******************************************************************/

package com.console.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	//GET
	public static String queryMessage(String _url) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			URL url = new URL(_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}
	
	
	//POST
	public static String queryMessage(String _url,String str) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			URL url = new URL(_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.getOutputStream().write(str.getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			
//		    int code = conn.getResponseCode();
//		    System.out.println("code   " + code);

			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}
}
