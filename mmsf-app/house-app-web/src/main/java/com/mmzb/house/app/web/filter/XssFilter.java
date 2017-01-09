package com.mmzb.house.app.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/** 拦截防止sql注入、xss注入   */
public class XssFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
                                                                                                   throws IOException,
                                                                                                   ServletException {

        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
            (HttpServletRequest) request);
        filterChain.doFilter(xssRequest, response);

    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}