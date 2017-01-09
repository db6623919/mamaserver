package com.mmzb.house.app.web.common.util;

import javax.servlet.http.HttpServletResponse;

import com.mmzb.house.app.web.common.DataResponse;

/**
 * @author chenmeiyang
 * 组装响应输出类
 */
public class ResponseOutUtil {
	
	
	/**
	 * chenmeiyang
	 * 组装响应内容，并输出
	 */
	public static void printOut(HttpServletResponse response , DataResponse data)throws Exception{
		JsonOutUtil.createRetMaptJSONObject(response, data);
	}
	
	/**
	 * chenmeiyang
	 * 组装响应内容，并输出
	 */
	public static void printOut(HttpServletResponse response , boolean isSuccess , String code , String msg )throws Exception{
		DataResponse resp = new DataResponse();
		resp.setSuccess(isSuccess);
		resp.setCode(code);
		resp.setMessage(msg);
		
		JsonOutUtil.createRetMaptJSONObject(response, resp);
	}

}
