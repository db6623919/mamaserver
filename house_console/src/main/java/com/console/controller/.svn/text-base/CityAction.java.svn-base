package com.console.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.console.dto.CityInfo;
import com.console.dto.ProvincesDto;
import com.console.entity.TSysUser;
import com.console.framework.dao.Pagination;
import com.console.util.Constant;
import com.console.util.HttpClientPostMethod;
import com.console.util.JsonGeneratorUtils;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.fdfs.facade.FileFacade;
import com.mmzb.fdfs.facade.entity.FileUploadRequest;
import com.mmzb.fdfs.facade.entity.FileUploadResponse;

@Controller
@RequestMapping("/city")
public class CityAction extends BaseController {
	
	public static Logger logger = Logger.getLogger(CityAction.class);
	
	@RequestMapping("/toCity")
	public ModelAndView toCity(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pager = new Pagination();
	    int current_page;// 当前页
	    if (null==request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		pager.setCurrent_page(current_page);
		pager.setPage_size(MsgPropertiesUtils.getPageSize());
		List<CityInfo> CitysList=getCities(pager);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		mv.addObject("CitysList", CitysList);
		List<ProvincesDto> proList=getProvinces();
		mv.addObject("cityList", CitysList);
		mv.addObject("proList", proList);
		mv.setViewName("/city/cityList_new");
		return mv;
	}
	@RequestMapping("/toProvince")
	public ModelAndView toProvince(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		
		Pagination pager = new Pagination();
	    int current_page;// 当前页
	    if (null==request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		pager.setCurrent_page(current_page);
		pager.setPage_size(MsgPropertiesUtils.getPageSize());
		List<ProvincesDto> proList=getProvinces(pager);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		
		mv.addObject("proList", proList);
		mv.setViewName("/prov/provinceList_new");
		return mv;
	}
	
	
	
	@RequestMapping("/deleteCity")
	public ModelAndView deleteCity(HttpSession session,HttpServletRequest request) throws  Exception {
		String id=request.getParameter("id");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("cityId", Integer.valueOf(id));
		HttpClientPostMethod.httpReqUrl(postData, "removeCity");;
		return toCity(session, request);
	}
	
	@RequestMapping("/deleteProvince")
	public ModelAndView deleteProvince(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		String id=request.getParameter("id");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("provId", Integer.valueOf(id));
		
		JSONObject obj=HttpClientPostMethod.httpReqUrl(postData, "getCityByProId");
		JSONArray array = JSONArray.fromObject(obj.get("data"));
		net.sf.json.JSONObject jsonObject = array.getJSONObject(0);
		
		int result=Integer.parseInt(jsonObject.get("result").toString());
		//return toProvince(session, request);
		if(result<1){
			HttpClientPostMethod.httpReqUrl(postData, "removeProvince");
			List<ProvincesDto> proList=getProvinces();
			mv.addObject("proList", proList);
			mv.setViewName("/prov/provinceList_new");
		}else{
			mv.setViewName("/common/error_new");
			mv.addObject("message", "删除省份失败！  错误信息:省份已被城市关联使用");
		}
		return mv;
	}
	
	@RequestMapping("/getCitysDetail")
	public ModelAndView getCitysDetail(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		Integer cityId=Integer.valueOf(request.getParameter("cityId"));
		Integer[] cityIdInt=new Integer[1];
		cityIdInt[0]=cityId;
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("cityIds", cityIdInt);
		CityInfo CitysList=getCitiesDetailNew(postData, cityId);
		mv.addObject("Citys", CitysList);
		List<ProvincesDto> proList=getProvinces();
		mv.addObject("proList", proList);
		mv.setViewName("/city/updateCity_new");
		return mv;
	}
	
	
	@RequestMapping("/getProvinceDetail")
	public ModelAndView getProvinceDetail(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		Integer provId=Integer.valueOf(request.getParameter("provId"));
		List<ProvincesDto> proList=getProvinces();
		ProvincesDto pd = new ProvincesDto();
		for(ProvincesDto p : proList){
			if(p.getProvId() == provId){
				pd=p;
				break;
			}
				
		}
		mv.addObject("provinces", pd);
		mv.setViewName("/prov/updateProvince_new");
		return mv;
	}
	
	@RequestMapping("/to_addCity")
	public ModelAndView toaddCity(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		List<ProvincesDto> proList=getProvinces();
		mv.addObject("proList", proList);
		mv.setViewName("/city/addCity_new");
		return mv;
	}
	
	@RequestMapping("/to_addProvince")
	public ModelAndView toaddProvince(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/prov/addProvince_new");
		return mv;
	}
	
	@RequestMapping("/addCity")
	public ModelAndView addCity(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info( getSessionUser(request).getName()+" addCity");
		Integer proId=Integer.valueOf(request.getParameter("proId"));
		String cityName=request.getParameter("cityName");
		String type=request.getParameter("type");
		String imgFlag=request.getParameter("imgFlag");
		String oldImg=request.getParameter("oldImg");
		String pinyin=request.getParameter("pinyin");
		Integer sort=0;
		if(!StringUtils.isEmpty(request.getParameter("sort"))){
			String str = request.getParameter("sort").replaceAll(" ", "");
			if(!StringUtils.isEmpty(str)){
				sort=Integer.valueOf(str);
			}
		}
		
		logger.info( getSessionUser(request).getName()+" addCity; request paras:{proId="+proId+", cityName="+ cityName + ", imgFlag="+imgFlag+", oldImg"+oldImg +"}");
		String img= oldImg;
		if("1".equals(imgFlag)){
			String newImage = saveImage(request);
			if (newImage != null && newImage.trim().length() != 0) {
				img = saveImage(request);
				logger.info(getSessionUser(request).getName() + " addCity; new Image file path:" + img);
			}
		}
		String description=request.getParameter("description");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("provId", proId);
		postData.put("name", cityName);
		postData.put("type", Integer.valueOf(type));
		postData.put("sort", sort);
		postData.put("pinyin", pinyin);
		
		Map<String, Object> showDetailData=new HashMap<String, Object>();
		showDetailData.put("cityImg", img);
		showDetailData.put("description", description);
		postData.put("showDetail", showDetailData);
		
		String flag=request.getParameter("flag");
		
		if("update".equals(flag)){
		   
		   Integer cityId=Integer.valueOf(request.getParameter("cityId"));
		   postData.put("cityId", cityId);
		   logger.info( getSessionUser(request).getName()+" addCity; Invoke updateCity API");
		   HttpClientPostMethod.httpReqUrl(postData, "updateCity");
		}else{
			logger.info( getSessionUser(request).getName()+" addCity; Invoke addCity API");
			JSONObject obj=HttpClientPostMethod.httpReqUrl(postData, "addCity");
			JSONArray array = JSONArray.fromObject(obj.get("data"));
			if(array.size()>0){
				net.sf.json.JSONObject jsonObject = array.getJSONObject(0);
				if(jsonObject.size()>0){
					String result=jsonObject.get("result").toString();
					if(result.equals("ycz")){
						ModelAndView mv=new ModelAndView();
						mv.setViewName("/common/error_new");
						mv.addObject("message", "添加城市失败！  错误信息:该城市已存在");
						return mv;
					}
				}
			}
		}
		return toCity(session, request);
	}
	
	
	
	@RequestMapping("/addProv")
	public ModelAndView addProv(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info( getSessionUser(request).getName()+" addProv");
		String name=request.getParameter("name");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("name", name);
		Map<String, Object> showDetailData=new HashMap<String, Object>();
		postData.put("showDetail", showDetailData);
		String flag=request.getParameter("flag");
		
		if("update".equals(flag)){
		   Integer provId=Integer.valueOf(request.getParameter("provId"));
		   postData.put("provId", provId);
		   logger.info( getSessionUser(request).getName()+" updateProvince; Invoke updateProvince API");
		   HttpClientPostMethod.httpReqUrl(postData, "updateProvince");
		}else{
			logger.info( getSessionUser(request).getName()+" addProvince; Invoke addProvince API");
			HttpClientPostMethod.httpReqUrl(postData, "addProvince");
		}
		return toProvince(session, request);
	}
	
	@RequestMapping("/getCitysByProId")
	public ModelAndView getCitysByProId(HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws Exception{
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String provId = request.getParameter("provinceId");
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("provId", provId);
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getCity");
		
		JSONArray array = JSONArray.fromObject(result.get("data")); 
		List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
       		for(int i = 0; i < array.size(); i++){
       			net.sf.json.JSONObject jsonObject =array.getJSONObject(i);
       			JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("cityList").toString());
       			for(int j = 0; j < arrayShowDetail.size(); j++){
       				CityInfo city = new CityInfo();
	       			net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
	       			Integer cityId = Integer.valueOf(jsonObject1.get("cityId").toString());
	       			String name = jsonObject1.get("name").toString();
	       			city.setCityId(cityId);
	       			city.setCityName(name);
	    	    	cityInfoList.add(city);
       			}
       		}
		RestResponse restP = new RestResponse();
		restP.setData(result);
		Map<String , Object> infoMap = new HashMap<String , Object>();
		infoMap.put("list", cityInfoList);  //查询动态活动列表
		restP.setData(infoMap);

		JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);
		return null;
	}
	
	private String saveImage(HttpServletRequest request){
		
		  logger.info( getSessionUser(request).getName()+" saveImge");
		
		  String img="";
		  MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
          //取得request中的所有文件名  
          MultipartFile multipartFile = multipartRequest.getFile("img"); 
          String url = MsgPropertiesUtils.getUploadHouseUrl(); 
   	        HessianProxyFactory factory = new HessianProxyFactory();  
   	        try {
   	        	logger.info( getSessionUser(request).getName()+" saveImge,  create HessianProxy");
   				FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
    			    FileUploadRequest req = new FileUploadRequest();
   			    req.setSource("妈妈送房网");
   			    req.setFileType("jpg");//jpg 、png
   			    req.setGroupName("group1");
   			    req.setFile(multipartFile.getBytes());
   			    
   			    logger.info( getSessionUser(request).getName()+" saveImge, to uploading ...");
   			    FileUploadResponse fileResponse = facade.uploadFile(req);   
   			    img= fileResponse.getFileUrl();
   			    logger.info( getSessionUser(request).getName()+" saveImge,  uploading success. 图片路径为："+img);
//   			System.out.println("===图片路径为："+fileResponse.getFileUrl()+"========"+fileResponse.getMessage());
			  }catch(Exception ex){
				  logger.error( getSessionUser(request).getName()+" saveImge,  uploading file failed! 错误信息："+ex.getMessage());
				  logger.error(ex.getMessage(), ex);
				  ex.printStackTrace();
			  } 
      
      return img;
	}
	
}
