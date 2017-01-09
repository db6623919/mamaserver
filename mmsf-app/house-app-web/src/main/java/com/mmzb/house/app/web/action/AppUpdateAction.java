package com.mmzb.house.app.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.common.errorCode.ReturnCode;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.RestResponse;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.util.JsonUtil;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.vo.AppVersionVo;
import com.mmzb.house.app.vo.TopicListVo;
import com.mmzb.house.app.vo.VersionVo;
import com.mmzb.house.app.web.http.HttpClientRequest;
import com.mmzb.house.app.web.http.ResponseValueGetter;
import com.netfinworks.service.exception.ServiceException;

/**
 * app版本判断更新
 * @author whl
 *
 */
@Controller
public class AppUpdateAction {

	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	private Logger logger = LoggerFactory.getLogger(AppUpdateAction.class);
	
	@RequestMapping(value = "/app/update", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<VersionVo> update(HttpServletRequest request,HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/update");
		}
		VersionVo versionVo = new VersionVo();
		try {
			//获取头部数据
			Map<String,String> map = (Map<String,String>)request.getSession().getAttribute("headerSession");
			Map<String,Object> param= new HashMap<String, Object>();
			param.put("version_no", map.get("x-client-version"));//版本
			param.put("source", map.get("x-client-bundle-id"));//来源
			param.put("product_type", map.get("x-client-device").toLowerCase()); //app类型
			logger.info("param {}.",param.toString());
			Response<AppVersionVo> respose = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "appVersionUpdate", AppVersionVo.class);
			if (ReturnCode.OK == Integer.parseInt(respose.getCode())) {
				AppVersionVo appVersion = respose.getData();
				//1:更新 0：不更新
				if (appVersion.getVersionBool().equals("1")) {
					versionVo.setUpdate(true);
					versionVo.setUpdateContent("检查到最新版本!");
					versionVo.setUpdateText("更新");
					versionVo.setUpdateTitle("新版本升级");
					versionVo.setCancelText("取消");
					versionVo.setVersionName(appVersion.getAppVersion().getVersion_no());
					versionVo.setVersionCode(appVersion.getAppVersion().getVersion_no().replace(".", ""));
					versionVo.setBundleId(map.get("x-client-bundle-id"));
					versionVo.setUpdateUrl(appVersion.getAppVersion().getUrl());
					//1：强制更新 0：不
					if (appVersion.getAppVersion().getForceUpdateFlag() == 1) {
						versionVo.setForceUpdate(true);
					}
				}
			}
		} catch (Exception e) {
			logger.error("/app/update:版本更新判断异常.",e);
			return new ResponseOut<VersionVo>(Constants.APP_FAILED,"版本更新判断异常.",null);
		}
		logger.info("end to run /app/update.");
		return new ResponseOut<VersionVo>(Constants.APP_SUCCESSED,"",versionVo);
	}
	
	/**
     * 参数签名验证失败统一返回
     * @param request
     * @param resp
     * @param model
     * @return
     * @throws BizException
     * @throws ServiceException
     * @throws IOException 
     */
    @RequestMapping(value = "/errorReturn" , method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public String signValidError(HttpServletRequest request,HttpServletResponse resp) throws Exception {
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("application/json;charset=UTF-8");
    	RestResponse restP = new RestResponse();
        restP.setMessage("签名失败");
    	restP.setCode("99990001");
    	restP.setSuccess(false);
    	logger.info(request.getRequestURL() + "请求签名失败！");
//    	return JsonGeneratorUtils.createRetMaptJSONObject(resp,restP);
//        return null;
    	return JsonUtil.objectToString(restP);
    }
}
