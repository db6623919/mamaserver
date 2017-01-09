package com.mmzb.house.app.web.action.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数处理相关工具类
 * */
public class ParamUtil {

	/** 获取排好序的字符串信息
	 * 	@param params 参数信息map
	 *  @param isEncode 是否编码处理
	 *  */
	public static String getSortedStrInfo(Map<String, String> params, boolean isEncode) {
        if (params == null) {
            return null;
        }
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if(isEncode) {
            	value = encodeParam(value);
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }
	
	/** 获取排好序的字符串信息
	 * 	@param params 参数信息map
	 *  @param isEncode 是否编码处理
	 *  */
	public static Map<String, String> getSortedDecodedMap(Map<String, String> params) {
        if (params == null) {
            return null;
        }
        
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if(!"sign".equals(key) && !"sign_type".equals(key)) {
            	value = decodeParam(value);
            }
            params.put(key, value);
        }

        return params;
    }
	
	/** 获取排好序的字符串信息
	 * 	@param params 参数信息map
	 *  @param isEncode 是否编码处理
	 *  */
	public static String getSortedDecodedStrInfo(Map<String, String> params) {
        if (params == null) {
            return null;
        }
        
        params.remove("sign");
        params.remove("sign_type");
        
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            value = decodeParam(value);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }
	
	/**
	 * 从url参数字符串中获取参数map信息
	 * */
	public static Map<String, String> getParamMapFromUrl(String queryString) {
		Map<String, String> params = new HashMap<String, String>();
		String[] arr = queryString.split("&");
		for(String str : arr) {
			String[] keyArr = str.split("=");
			params.put(keyArr[0], keyArr[1]);
		}
		return params;
	}
	
	/** 字符传编码处理 */
	public static String encodeParam(String str) {
		String encodedStr = "";
		try {
			encodedStr = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedStr;
	}
	
	/** 字符传解码处理 */
	public static String decodeParam(String str) {
		String decodeStr = "";
		try {
			decodeStr = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodeStr;
	}
}
