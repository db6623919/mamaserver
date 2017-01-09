package com.mama.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http {
	private static final Logger logger = LoggerFactory.getLogger(Http.class);
  public static final String POST_URLENCODE = MediaType.APPLICATION_FORM_URLENCODED;
  public static final String POST_MULTIPART = MediaType.MULTIPART_FORM_DATA;

  public static String post(String uri) {
    return post(uri, null);
  }

  public static String poste(String uri, Map<String, String> params) throws IOException {
    return post0(uri, params);
  }


  public static String post(String uri, Map<String, String> params) {
    try {
      return post0(uri, params);
    } catch (IOException e) {
      Log.HTTP.error(e.getMessage(), e);
      return "";
    }

  }

  public static String post0(String uri, Map<String, String> params) throws IOException {
    Request post = Request.Post(uri).connectTimeout(30000).socketTimeout(30000).useExpectContinue();

    if (params != null && !params.isEmpty()) {
      Form form = Form.form();
      for (String name : params.keySet()) {
        form.add(name, params.get(name));
      }

      post.bodyForm(form.build());
    }

    return post.execute().returnContent().asString();
  }

  public static String postm(String uri, Map<String, Object> params) {
    Request post = Request.Post(uri).connectTimeout(30000).socketTimeout(30000).useExpectContinue();


    if (params != null && !params.isEmpty()) {

      MultipartEntityBuilder multipart = MultipartEntityBuilder.create();
      multipart.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

      for (String name : params.keySet()) {
        Object v = params.get(name);

        if (params.get(name) instanceof File) {
          File file = (File) v;
          multipart.addBinaryBody(name, file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
        } else if (v instanceof String) {
          multipart.addTextBody(name, v.toString());
        } else {
          throw new RuntimeException("Parameter must be a String or File, but is " + v.getClass());
        }
      }

      post.body(multipart.build());
    }

    try {
      return post.execute().returnContent().asString();
    } catch (IOException e) {
      Log.DEFAULT.error(e.getMessage(), e);
      return "";
    }
  }

  public static void main(String[] args) {

    System.out.println(postm("http://localhost:8080/api/hello/upload", new HashMap<String, Object>() {
      private static final long serialVersionUID = 1L;

      {
        put("name", "Bita");
        put("file", new File("/tmp/demo.txt"));
      }

    }));
  }



  public static String param(HttpServletRequest req, String name) {
    return req.getParameter(name) == null ? "" : req.getParameter(name);
  }

  public static String[] params(HttpServletRequest req, String name) {
    return params(req, name, true);
  }

  public static String[] params(HttpServletRequest req, String name, boolean force) {
    String[] ps = req.getParameterValues(name);

    if (ps == null) {
      if (force) {
        throw new NullPointerException("The value of request parameter <" + name + "> must not be null.");
      } else {
        return new String[0];
      }
    } else {
      return ps;
    }

  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////
  /** 
   * 方法名称:sendPost 
   * 作用：向指定 URL 发送POST方法的请求
   * 传入参数:url(发送请求的 URL), param(请求参数，请求参数应该是 name1=value1&name2=value2 的形式。)
   * 返回值:String(响应结果)
  */ 
  public static String sendPost(String url, String param) {
	  System.out.println("[sendPost]start~~~");
      PrintWriter out = null;
      BufferedReader in = null;
      String result = "";
      try {
          URL realUrl = new URL(url);
          System.out.println("[sendPost]url = " + url);
          // 打开和URL之间的连接
          URLConnection conn = realUrl.openConnection();
          // 设置通用的请求属性
          conn.setRequestProperty("accept", "*/*");
          conn.setRequestProperty("connection", "Keep-Alive");
          conn.setRequestProperty("Cache-Control", "no-cache");
          conn.setRequestProperty("user-agent",
                  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
          // 发送POST请求必须设置如下两行
          conn.setDoOutput(true);
          conn.setDoInput(true);
          
          System.out.println("[sendPost]conn = " + conn.toString());
          // 获取URLConnection对象对应的输出流
          out = new PrintWriter(conn.getOutputStream());
          // 发送请求参数
          out.print(param);
          // flush输出流的缓冲
          out.flush();
          // 定义BufferedReader输入流来读取URL的响应
          in = new BufferedReader(
                  new InputStreamReader(conn.getInputStream(),"UTF-8"));
          String line;
          while ((line = in.readLine()) != null) {
              result += line;
          }
      } catch (Exception e) {
          System.out.println("发送 POST 请求出现异常！"+e);
          e.printStackTrace();
      }
      //使用finally块来关闭输出流、输入流
      finally{
          try{
              if(out!=null){
                  out.close();
              }
              if(in!=null){
                  in.close();
              }
          }
          catch(IOException ex){
              ex.printStackTrace();
          }
      }
      System.out.println("[sendPost]result = " + result);
      return result;
  }  
  
  public static String sendGet(String url, String param) {
      String result = "";
      BufferedReader in = null;
      try {
          String urlNameString = url + "?" + param;
          URL realUrl = new URL(urlNameString);
          // 打开和URL之间的连接
          URLConnection connection = realUrl.openConnection();
          // 设置通用的请求属性
          connection.setRequestProperty("accept", "*/*");
          connection.setRequestProperty("connection", "Keep-Alive");
          connection.setRequestProperty("user-agent",
                  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
          // 建立实际的连接
          connection.connect();
          // 获取所有响应头字段
          Map<String, List<String>> map = connection.getHeaderFields();
          // 遍历所有的响应头字段
          for (String key : map.keySet()) {
              System.out.println(key + "--->" + map.get(key));
          }
          // 定义 BufferedReader输入流来读取URL的响应
          in = new BufferedReader(new InputStreamReader(
                  connection.getInputStream()));
          String line;
          while ((line = in.readLine()) != null) {
              result += line;
          }
      } catch (Exception e) {
          result = "";
          logger.error("发送GET请求出现异常！", e);
      }
      // 使用finally块来关闭输入流
      finally {
          try {
              if (in != null) {
                  in.close();
              }
          } catch (Exception e2) {
        	  logger.error("发送短信关闭流过程中出现异常！", e2);
          }
      }
      return result;
  }
  
  /** 
   * 方法名称:map2String 
   * 传入参数:map 
   * 返回值:String 形如 username=arvin&password=1234 
  */  
  public static String map2String(Map map){
  	
    java.util.Map.Entry entry;  
    StringBuffer sb = new StringBuffer();  
    
    for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)  
    {  
      entry = (java.util.Map.Entry)iterator.next();  
        sb.append(entry.getKey().toString()).append( "=" ).append(null==entry.getValue()?"":  
        entry.getValue().toString()).append (iterator.hasNext() ? "&" : "");  
    }
    
    return sb.toString();  
    
  }
}
