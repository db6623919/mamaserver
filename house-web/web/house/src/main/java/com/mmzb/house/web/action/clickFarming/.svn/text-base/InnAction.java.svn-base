package com.mmzb.house.web.action.clickFarming;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.common.errorCode.ReturnCode;
import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.core.common.vo.ResponseOut;
import com.mmzb.house.web.model.houses.ClickHousesVo;
import com.mmzb.house.web.model.houses.HouseShopVo;

/**
 * 刷单客栈Action
 * @author dengbiao
 *
 */
@Controller
public class InnAction extends BaseAction {

    @Resource(name="appProperties")
	private AppProperties appProperties;
    private Logger logger = LoggerFactory.getLogger(InnAction.class);
    
	/**
	 * 按客栈Id获取客栈信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/my/clickFarming/getHouseShop.htm", method = { RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
	public ResponseOut<String> getHouseShop(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	logger.info("start to run /my/clickFarming/getHouseShop.htm");
		int shopId = Integer.parseInt(request.getParameter("shopId"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shopId", shopId);
		String shopName = "";
		try {
			//按客栈ID查询客栈
			JSONObject houseShopJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot()+"/customer", "getHouseShopByPar");
			JSONObject daObj = houseShopJson.getJSONObject("data");
			JSONObject shopObj = daObj.getJSONObject("houseShop");
			shopName = shopObj.getString("shopName");
		} catch (Exception e) {
			logger.error("/my/clickFarming/getHouseShop.htm 异常： " + e);
			return new ResponseOut<String>(ReturnCode.INTERFACE_ERROR,"获取客栈信息失败！",null);
		}
		logger.info("end to run /my/clickFarming/getHouseShop.htm");
		return new ResponseOut<String>(ReturnCode.OK,"获取客栈信息成功！",shopName);
	}
	
}