package com.mmzb.house.app.web.http;




import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http客户端请求
 * 
 * @author: whl
 * 
 */
public class HttpClientRequest {
    private static Logger logger = LoggerFactory.getLogger(HttpClientRequest.class);

    


    /**
     * POST 普通参数请求
     * 
     * @param url
     * @param param
     * @param getter
     * @return
     * @throws IOException
     */
    public static <T> T httpPost(String url, final Map<String, String[]> param, 
    		Map<String,String> headers, ResponseValueGetter<T> getter) throws IOException {
        return (T)httpPost(url,null,param,headers, new RequestParamSetter() {
            public void setValue(HttpPost method) throws IOException {             
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        Iterator<Entry<String, String[]>> iterator = param.entrySet().iterator();
                        Entry<String, String[]> entry = null;
                        while (iterator.hasNext()) {
                            entry = iterator.next();
                            for(String str :entry.getValue()){
                                params.add(new BasicNameValuePair(entry.getKey(), str));
                            }
                        }
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                        method.setEntity(entity);
                    }                
            }, getter);
    }

    /**
     * post请求
     * 
     * @param url
     * @param param
     * @param rps
     * @param getter
     * @return
     */
    public static <T> T httpPost(String url, HttpClientCreate create, Map<String, String[]> param,
           Map<String,String> headers, RequestParamSetter rps, ResponseValueGetter<T> getter) throws IOException {
        CloseableHttpClient httpClient = null;
        if (create != null) {
            httpClient = create.createHttpClient();
        } else {
            httpClient = HttpClients.createDefault();
        }
        HttpPost method = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            Header[] headerArr = new Header[headers.size()];
            Set<Entry<String, String>> sets = headers.entrySet();
            int i = 0;
            for (Entry<String, String> entry : sets) {
                headerArr[i++] = new BasicHeader(entry.getKey(), entry.getValue());
            }
            method.setHeaders(headerArr);
        }
        T t = null;
        try {
            if (rps != null) {
                rps.setValue(method);
            }
            HttpResponse result = httpClient.execute(method);
            Header[] headerArr= result.getAllHeaders();
            for(Header hd: headerArr){
            	if("Set-Cookie".equals(hd.getName())){
            		headers.put("Cookie",hd.getValue());
            	}
            }
            t = getter.getValue(result.getEntity());
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("连接关闭失败", e);
            }
        }
        return t;
    }
    
   
}
