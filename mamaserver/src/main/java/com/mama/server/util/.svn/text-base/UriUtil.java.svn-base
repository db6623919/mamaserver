package com.mama.server.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

public class UriUtil {

  public static String buildUri(String protocol, String host, int port, String path) {
    return buildUri(protocol, host, port, path, null);
  }

  public static String buildUri(String protocol, String host, int port, String path, Map<String, String> params) {
    StringBuilder url = new StringBuilder();

    path = cleanPath(path);

    url.append(protocol).append("://");
    url.append(host).append(":").append(port).append("/");
    url.append(path);

    if (params != null && !params.isEmpty()) {
      url.append("?");

      for (String name : params.keySet()) {
        url.append(name).append("=").append(params.get(name)).append("&");
      }

      url.setLength(url.length() - 1);
    }


    return url.toString();
  }


  public static String encode(String uri) {
    try {
      return URLEncoder.encode(uri, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String decode(String uri) {
    try {
      return URLDecoder.decode(uri, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }


  private static String cleanPath(String path) {
    if (path.startsWith("/")) {
      path = path.substring(1, path.length());
    }

    return path;
  }

}
