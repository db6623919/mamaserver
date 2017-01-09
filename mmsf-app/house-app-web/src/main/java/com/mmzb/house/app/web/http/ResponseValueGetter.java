package com.mmzb.house.app.web.http;

import java.io.IOException;

import org.apache.http.HttpEntity;

/**
 * 响应结果值获取接口
 * @author: whl
 *
 */
public interface ResponseValueGetter<T> {
    /**
     * 获取响应值
     * @param entity
     * @return
     */
	public T getValue(HttpEntity entity) throws IOException;
}
