package com.mmzb.house.app.common.util;

import java.util.List;

import com.alibaba.fastjson.JSON;

/** json转换工具类 */
public class JsonUtil {
	/** 将json字符串转换成对象
	 *  @param json 需要转换成对象的json字符串
	 *  @param clazz 转换后的目标对象类型
	 *  @return T 转换后所得到的类型
	 *  */
	public static <T> T jsonToObject(String json, Class<T> clazz) {
		T object = JSON.parseObject(json, clazz);
		return object;
	}
	
	/** 将对象转换成json格式字符串
	 * 	@param obj 待转换成json字符串的对象
	 *  @return String 转换后生成的json字符串
	 *  */
	public static String objectToString(Object obj) {
		String jsonData = JSON.toJSONString(obj);
		return jsonData;
	}
	
	/** 将对象转换成json格式字符串
	 *  @param json 需要转换成List的json字符串
	 *  @param clazz 转换后的目标对象类型
	 *  @return List<T> 转换后所得到目标类型的list
	 *  */
	public static <T> List<T> jsonToList(String json, Class<T> clazz) {
		List<T> list = JSON.parseArray(json, clazz);
		return list;
	}
}
