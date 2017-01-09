package com.console.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.console.framework.dialect.PaginationInterceptor;

/**
 * 拦截器
 */
public class BasedInterceptor extends HandlerInterceptorAdapter {
	// 日志对象
    protected static Logger logger = Logger.getLogger(PaginationInterceptor.class);

	// 预处理 (Action之前执行,可以进行编码、安全控制等处理)
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("preHandle");
		boolean handlerOk = super.preHandle(request, response, handler);
		//String className  =  handler.getClass().getName(); // package Name .ClassName 
		if (handlerOk) {
			//通过URL拦截
			String mappingURL = ".*";
			//String mappingURL = ".*/user/.*.shtml";
			//String mappingURL = ".*.shtml";
			String url = request.getRequestURL().toString();
			if(!url.matches(mappingURL)) {
				request.getRequestDispatcher("/page/common/error.jsp").forward(request, response);
				return false;
			}
		}
		// if(url.endsWith("doLogin"))
		// return true;
		// }
		return true;
	}

	// 后处理(生成视图之前执行,调用了Service并返回ModelAndView,但未进行页面渲染,有机会修改ModelAndView)
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// request.getRequestDispatcher("index.jsp").forward(request, response);
		logger.info("postHandle");

	}

	// 返回处理(最后执行，可用于释放资源,已经渲染了页面,可以根据ex是否为null判断是否发生了异常,进行日志记录)
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// request.getRequestDispatcher("index.jsp").forward(request, response);
		logger.info("afterCompletion");
	}
	

}
