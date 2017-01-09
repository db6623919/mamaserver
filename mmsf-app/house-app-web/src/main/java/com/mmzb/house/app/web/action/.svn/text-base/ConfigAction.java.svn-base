package com.mmzb.house.app.web.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.vo.ResponseOut;

/**
 * 设置
 * @author whl
 *
 */
@Controller
public class ConfigAction {

	
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	private Logger logger = LoggerFactory.getLogger(ConfigAction.class);
	
	/**
	 * 获取配置
	 * @param request
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/app/getConfig", method = { RequestMethod.POST,RequestMethod.POST })
	@ResponseBody
	public ResponseOut<String> getConfig(HttpServletRequest request,HttpServletResponse resp) throws IOException {
		
		return new ResponseOut<String>(Constants.APP_SUCCESSED,"",appProperties.getApp_config());
	}
}
