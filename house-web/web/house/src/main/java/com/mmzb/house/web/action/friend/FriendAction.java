package com.mmzb.house.web.action.friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.CityAction;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.util.JsonGeneratorUtils;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;

@Controller
public class FriendAction extends BaseAction {

	@Resource(name="appProperties")
	private AppProperties appProperties;
    private static Logger logger = LoggerFactory.getLogger(FriendAction.class);
    
    @RequestMapping(value = "oldFriends.htm", method = { RequestMethod.GET })
    public ModelAndView oldFriends(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	RestResponse restP = new RestResponse();
    	Map<String, Object> postData = new HashMap<String, Object>();
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		logger.info("查询老友巢首页显示请求参数,postData{}",postData);
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getContactById");
    		logger.info("查询老友巢首页显示返回结果,result{}",result);
    		JSONArray jsonArray = null;
    		if(result != null){
    			jsonArray = JSONArray.fromObject(result.get("date"));
    		}
//    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//    		Map<String, Object> map = new HashMap<String, Object>();
//    		map.put("image", "http://s.amazeui.org/media/i/demos/bing-1.jpg");
//    		map.put("title", "1111111111");
//    		map.put("image", "http://s.amazeui.org/media/i/demos/bing-2.jpg");
//    		map.put("title", "2222222222");
//    		map.put("image", "http://s.amazeui.org/media/i/demos/bing-3.jpg");
//    		map.put("title", "3333333333");
//    		map.put("image", "http://s.amazeui.org/media/i/demos/bing-4.jpg");
//    		map.put("title", "4444444444");
//    		list.add(map);
//    		JSONArray jsonArray = JSONArray.fromObject(list);
    		resultMap.put("result", jsonArray);
    		restP.setData(resultMap);
    		
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
    	return new ModelAndView(Contants.URL_PREFIX + "/friend/old_friend", "response",restP);
    }
}
