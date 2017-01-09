package com.mmzb.house.app.web.http;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
/**
 * 请求参数设置接口
 * @author: whl
 *
 */
public interface RequestParamSetter {

	/**
	 * 设置参数值
	 * @param method
	 */
	public void setValue(HttpPost method)throws IOException;
}
