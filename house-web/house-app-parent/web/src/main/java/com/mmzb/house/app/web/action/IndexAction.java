package com.mmzb.house.app.web.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author chenmeiyang
 * 首页
 */
@Controller
public class IndexAction extends BaseAction{
   
    @RequestMapping(value = "/index.htm" , method = { RequestMethod.POST, RequestMethod.GET })
    public void index(HttpServletRequest req,HttpServletResponse resp)throws Exception{
    	System.out.println("========");
    	Enumeration en = req.getHeaderNames();
    	while(en.hasMoreElements()){
    		System.out.println("=========="+req.getHeader("host")+"===="+req.getHeader("accept-encoding"));
    	}
    }
}
