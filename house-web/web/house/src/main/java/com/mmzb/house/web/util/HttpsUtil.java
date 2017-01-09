package com.mmzb.house.web.util;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpsUtil {
	
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final Logger logger = LoggerFactory.getLogger(HttpsUtil.class);
	
	//用于进行Https请求的HttpClient
	private static class SSLClient extends DefaultHttpClient{
		public SSLClient() throws Exception{
	        super();
	        SSLContext ctx = SSLContext.getInstance("TLS");
	        X509TrustManager tm = new X509TrustManager() {
					@Override
					public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
							throws CertificateException {
					}
					@Override
					public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
							throws CertificateException {
					}
					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
	        };
	        ctx.init(null, new TrustManager[]{tm}, null);
	        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	        ClientConnectionManager ccm = this.getConnectionManager();
	        SchemeRegistry sr = ccm.getSchemeRegistry();
	        sr.register(new Scheme("https", 443, ssf));
	    }
	}
	
	public static String doPost(String url, Object content){
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try{
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			
			if(content instanceof String) {
				String txt = (String) content;
				StringEntity entity = new StringEntity((String) txt, DEFAULT_ENCODING);
				httpPost.setEntity(entity);
			} else if(content instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) content;
				//设置参数
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String,String> elem = iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
				}
				if(list.size() > 0){
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, DEFAULT_ENCODING);
					httpPost.setEntity(entity);
				}
			}
			
			HttpResponse response = httpClient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity, DEFAULT_ENCODING);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public static String doGetString(String url){
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String result = null;
		try{
			httpClient = new SSLClient();
			httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity, DEFAULT_ENCODING);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public static InputStream doGetInputStream(String url){
		logger.info("请求二维码地址:"+url);
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		InputStream result = null;
		try{
			httpClient = new SSLClient();
			httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			logger.info("请求二维码返回:"+response);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				result = resEntity.getContent();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
}