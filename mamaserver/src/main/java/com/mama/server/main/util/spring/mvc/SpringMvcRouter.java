package com.mama.server.main.util.spring.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.resthub.web.springmvc.router.HTTPRequestAdapter;
import org.resthub.web.springmvc.router.Router;
import org.resthub.web.springmvc.router.Router.ActionDefinition;

public class SpringMvcRouter {
  public static String reverse(String action) {
    HTTPRequestAdapter currentRequest = HTTPRequestAdapter.getCurrent();
    Map<String,Object> map = new HashMap<String,Object>();
    ActionDefinition ad = Router.reverse(action, map);

    String url = ad.url;

    if (currentRequest != null) {

      if(!currentRequest.servletPath.isEmpty() && !currentRequest.servletPath.equals("/")) {
        String servletPath = currentRequest.servletPath;
        url = url.replace(servletPath.startsWith("/") ?  servletPath : "/" + servletPath, "");
      }

      if(!currentRequest.contextPath.isEmpty() && !currentRequest.contextPath.equals("/")) {
        String contextPath = currentRequest.contextPath;
        url = url.replace(contextPath.startsWith("/") ? contextPath : "/" + contextPath, "");
      }


    }

    return url;
  }
}
