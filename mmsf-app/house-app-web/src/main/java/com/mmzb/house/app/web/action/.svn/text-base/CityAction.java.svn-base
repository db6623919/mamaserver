package com.mmzb.house.app.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.entity.CityEntity;
import com.mmzb.house.app.entity.ServerCityEntity;
import com.mmzb.house.app.vo.AppCityVo;
import com.mmzb.house.app.vo.CityVo;

/**
 * 城市接口
 * @author whl
 *
 */
@Controller
public class CityAction {
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	private Logger logger = LoggerFactory.getLogger(CityAction.class);
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
	/**
	 * 城市列表接口
	 * @param request
	 * @return 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/app/cityList", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public ResponseOut<AppCityVo> getCitys(HttpServletRequest request,HttpServletResponse resp) throws IOException {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/cityList");
		}
		String currentVersionNo = request.getParameter("version");//传过来的当前版本号
		
		RedisRequest req = new RedisRequest();
		req.setKey("mmsf:cityVersion");
		String version = redisFacade.getValueByKey(req);
		
//		String version = HouseCityCache.getHouseCityListFromRedis();//redis中版本号
		
		if (null==version || "".equals(version)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("versionType", 1);
			JSONObject result=	HttpClientPostMethod.httpReqUrl(map, appProperties.getRequestRoot(),"getVersionByPar");
			if ("0".equals(result.get("code").toString())) {
				JSONArray array = JSONArray.fromObject(result.get("data")); 
				net.sf.json.JSONObject jsonObject = array.getJSONObject(0);
				version = jsonObject.get("version").toString();				
			}else {
				logger.error("getCitys 查询异常！");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		AppCityVo appCityVo = new AppCityVo();
		if (version.equals(currentVersionNo)) {
			appCityVo.setVersion(version);
		}else {
			try {
				Map<String, Object> postData = new HashMap<String, Object>();
				Response response = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCities", ServerCityEntity.class);
				if("0".equals(response.getCode())) {
					ServerCityEntity city = (ServerCityEntity)response.getData();
					//转化为APP Vo
					List<CityVo> cityVoList = this.entityToVo(city.getCities());
					Map dataMap = new HashMap();
					dataMap.put("cities", cityVoList);
					appCityVo.setCityData(dataMap);
					appCityVo.setVersion(version);

				} 

			} catch(Exception e) {
				logger.error("cityList:查询城市列表失败.",e);
				return new ResponseOut<AppCityVo>(Constants.APP_FAILED,"房源收藏取消失败",null);
			}
		}
		
	
		if (logger.isInfoEnabled()) {
		    logger.info("/app/cityList is finished,params is {currentVersionNo}.",currentVersionNo);
		}
		return new ResponseOut<AppCityVo>(Constants.APP_SUCCESSED,"查询城市列表成功！",appCityVo);
	}
	
	private List<CityVo> entityToVo(List<CityEntity> list) {
		List<CityVo> cityVoList = new ArrayList<CityVo>();
		for(int i = 0 ; i < list.size() ; i ++ ) {
			CityVo cityVo = new CityVo();
			cityVo.setCityId(list.get(i).getCityId());
			cityVo.setCityName(list.get(i).getCityName());
			cityVo.setCityPinyin(list.get(i).getPinyin());
			cityVo.setCityType(list.get(i).getType());
			cityVo.setSort(list.get(i).getSort());
			cityVoList.add(cityVo);
		}
		return cityVoList;
	}
}
