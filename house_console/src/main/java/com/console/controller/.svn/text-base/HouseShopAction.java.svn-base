package com.console.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.console.dto.CFHouseShopDto;
import com.console.dto.CityInfo;
import com.console.dto.HouseShop;
import com.console.entity.TSysUser;
import com.console.framework.dao.Pagination;
import com.console.util.Constant;
import com.console.util.HttpClientPostMethod;
import com.console.util.JsonGeneratorUtils;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.fdfs.facade.FileFacade;
import com.mmzb.fdfs.facade.entity.FileUploadRequest;
import com.mmzb.fdfs.facade.entity.FileUploadResponse;


@Controller
@RequestMapping("/houseshop")
public class HouseShopAction extends BaseController {
	
	public static Logger logger = Logger.getLogger(HouseShopAction.class);
	
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session,HttpServletRequest request) throws  Exception {
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
		List<HouseShop> list = getHouseShop(pager);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		mv.addObject("list", list);
		mv.setViewName("/houseshop/houseshopList");
		return mv;
	}
	
	/**
	 * 推荐客栈
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/recommended_list")
	public ModelAndView recommended_list(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("start to run recommended_list");
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
		List<HouseShop> list = getHouseShop(pager);
		pager.paging(current_page, pager.getPage_size(), pager.getTotal_count());
		
		mv.addObject("pager", pager);
		mv.addObject("list", list);
		mv.setViewName("/houseshop/recommendHouseshopList");
		logger.info("finish to run recommended_list");
		return mv;
	}
	
	/**
	 * 修改搜索推荐
	 */
	@RequestMapping("/updateRecommed")
	public ModelAndView updateRecommed(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("start to updateRecommed");
		ModelAndView mv = new ModelAndView();
		String shopId = request.getParameter("id");
		String recommended_status = request.getParameter("recommended_status");
		
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("id", Integer.parseInt(shopId));
		postData.put("recommended_status", recommended_status);
		postData.put("flag", "update");
		JSONObject obj=HttpClientPostMethod.httpReqUrl(postData, "addHouseShop");
		String code = obj.getString("code");
		if (Constant.OK.equals(code)) {
			logger.info("end to updateRecommed");
			return recommended_list(session, request);
		}else {
			mv.setViewName("/common/error_new");
			mv.addObject("message", "修改搜索推荐失败！  错误信息:修改搜索推荐失败！");
			logger.info("end to updateRecommed");
			return mv;
		}
	}
	
	@RequestMapping("/getShops")
	public ModelAndView getShops(HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws Exception{
		Map<String, Object> postData=new HashMap<String, Object>();
		Pagination pager = new Pagination();
		List<HouseShop> list = getHouseShop(pager);
		Map<String , Object> infoMap = new HashMap<String , Object>();
		infoMap.put("list", list);  
		RestResponse restP = new RestResponse();
		restP.setData(infoMap);

		JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);
		return null;
	}
	
	/**
	 * TO新增店铺页面
	 */
	@RequestMapping("/to_addHouseShop")
	public ModelAndView to_addHouseShop(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();;
		Pagination pager = new Pagination();
		List<CityInfo> CitysList=getCities(pager);
		mv.addObject("CitysList", CitysList);
		mv.setViewName("/houseshop/addHouseShop");
		return mv;
	}
	
	/**
	 * TO修改优惠modifyCFHouseShop
	 */
	@RequestMapping("/to_modifyCFHouseShop")
	public ModelAndView to_modifyCFHouseShop(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("start to modifyCFHouseShop");
		ModelAndView mv = new ModelAndView();
		Integer id=Integer.valueOf(request.getParameter("id"));
		Pagination pager = new Pagination();
		HouseShop houseShop = getHouseShopById(pager, id);
		mv.addObject("shopName", houseShop.getShopName());
		mv.addObject("shopId", id);
		mv.setViewName("/houseshop/modifyCFHouseShop");
		
		//查询原有优惠金额
		CFHouseShopDto cfHouseShopDto = new CFHouseShopDto();
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("shopId", id);
		JSONObject obj = HttpClientPostMethod.httpReqUrl(postData, "getCFHouseShop");
		JSONObject dataObj = obj.getJSONObject("data");
		JSONObject cfObj = dataObj.getJSONObject("cfHouseShop");
		cfHouseShopDto = JSON.toJavaObject(cfObj, CFHouseShopDto.class);
		mv.addObject("cfHouseShop", cfHouseShopDto);
		
		logger.info("end to modifyCFHouseShop");
		return mv;
	}

	/**
	 * 修改优惠modifyCFHouseShop
	 */
	@RequestMapping("/addCFHouseShop")
	public ModelAndView addCFHouseShop(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("start to modifyCFHouseShop");
		ModelAndView mv = new ModelAndView();
		String shopId = request.getParameter("shopId");
		String totalAmt = request.getParameter("totalAmt");
		String discount = request.getParameter("discount");
		String discountLimit = request.getParameter("discountLimit");
		String message_switch = request.getParameter("message_switch");
		Integer discountType = Integer.parseInt(request.getParameter("discountType"));
		Integer lowestAmt = 0;
		if (discountType == 2) {
			lowestAmt = Integer.parseInt(request.getParameter("lowestAmt"));
		}
		
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("shopId", Integer.parseInt(shopId));
		postData.put("totalAmt", Integer.parseInt(totalAmt));
		postData.put("discountLimit", Integer.parseInt(discountLimit));
		postData.put("discount", discount);
		postData.put("message_switch", message_switch);
		postData.put("discountType", discountType);
		postData.put("lowestAmt", lowestAmt);
		
		JSONObject obj=HttpClientPostMethod.httpReqUrl(postData, "addCFHouseShop");
		String code = obj.getString("code");
		if (Constant.OK.equals(code)) {
			logger.info("end to addCFHouseShop");
			return list(session, request);
		}else {
			mv.setViewName("/common/error_new");
			mv.addObject("message", "修改店铺优惠失败！  错误信息:修改店铺优惠失败！");
			logger.info("end to addCFHouseShop");
			return mv;
		}
		
	}
	
	/**addHouseShop
	 * 新增店铺
	 */
	@RequestMapping("/addHouseShop")
	public ModelAndView addHouseShop(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info( getSessionUser(request).getName()+" addHouseShop");
		
		Map<String, Object> postData=new HashMap<String, Object>();
		String flag = request.getParameter("flag");
		String id = request.getParameter("id");
		String shopName = request.getParameter("shopName");
		if (!flag.equals("del")) {
			StringBuffer bossImageStr = new StringBuffer();
			
			String shopDesc = request.getParameter("shopDesc").trim();
			saveBossImage(request,bossImageStr);   //保存头像图片，获取分享图片路径
			String bossPhone = request.getParameter("bossPhone");
			String bossWeChat = request.getParameter("bossWeChat");
			
			StringBuffer shopImageStr = new StringBuffer();//上传客栈照片
			saveShopImage(request,shopImageStr);
			int cityId = Integer.parseInt(request.getParameter("cityID")); //城市ID
			String level = request.getParameter("level");  //等级
			String type = request.getParameter("type");  //类型
			String bossName = request.getParameter("bossName");  //老板姓名
			String partnership = request.getParameter("partnership");  //合作关系
			
			logger.info( getSessionUser(request).getName()+" addHouseShop; request paras:{shopName="+shopName+", shopDesc="+ shopDesc + 
					", bossPhone="+ bossPhone+", bossWeChat="+ bossWeChat+", bossImageStr="+ bossImageStr+"}");
			
			
			if (null!=shopDesc && !"".equals(shopDesc)) {
				postData.put("shopDesc", shopDesc.trim());
			}
			
			postData.put("bossImage", bossImageStr);
			postData.put("bossPhone", bossPhone);
			postData.put("bossWeChat", bossWeChat);
			postData.put("imgUrl", shopImageStr);
			postData.put("cityID", cityId);
			postData.put("level", level);
			postData.put("type", type);
			postData.put("id", id);
			postData.put("bossName", bossName);
			postData.put("partnership", partnership);
		}
		postData.put("flag", flag);
		postData.put("id", id);
		postData.put("shopName", shopName);
		JSONObject object=HttpClientPostMethod.httpReqUrl(postData, "getHouseShopByPar");
		if ("0".equals(object.get("code").toString())) {
			net.sf.json.JSONObject jObject1 = net.sf.json.JSONObject.fromObject(object.get("data")); 
			net.sf.json.JSONObject jObject =jObject1.getJSONObject("houseShop");
			String idStr = jObject.getString("id");
			if (id.equals(idStr)) {
				logger.info( getSessionUser(request).getName()+" addHouseShop; Invoke addHouseShop API");
				JSONObject obj=HttpClientPostMethod.httpReqUrl(postData, "addHouseShop");
				String code = obj.getString("code");
				if ("0".equals(code)) {
					return list(session, request);
				}else {
					ModelAndView mv=new ModelAndView();
					mv.setViewName("/common/error_new");
					mv.addObject("message", "添加店铺失败！  错误信息:该店铺已存在");
					return mv;
				}
			  }
		}else {
			logger.info( getSessionUser(request).getName()+" addHouseShop; Invoke addHouseShop API");
			JSONObject obj=HttpClientPostMethod.httpReqUrl(postData, "addHouseShop");
			String code = obj.getString("code");
			if ("0".equals(code)) {
				return list(session, request);
			}else {
				ModelAndView mv=new ModelAndView();
				mv.setViewName("/common/error_new");
				mv.addObject("message", "添加店铺失败！  错误信息:该店铺已存在");
				return mv;
			}
		}
		return null;
		
	}

	/***
	 * 查询
	 */
	@RequestMapping("/getHouseShopById")
	public ModelAndView getHouseShopById(HttpSession session,HttpServletRequest request) throws  Exception {
		ModelAndView mv = new ModelAndView();
		Integer id=Integer.valueOf(request.getParameter("id"));
		logger.info( getSessionUser(request).getName()+" getTradeAreaById; request paras:{id="+id+"}");
		Pagination pager = new Pagination();
		HouseShop houseShop = getHouseShopById(pager, id);
		
		mv.addObject("houseShop", houseShop);
		String flag = request.getParameter("flag");
		
		
		if ("edit".equals(flag)) {
			mv.setViewName("/houseshop/editHouseShop");
		}else {
			Pagination pager1 = new Pagination();
			List<CityInfo> CitysList=getCities(pager1);
			mv.addObject("CitysList", CitysList);
			mv.setViewName("/houseshop/updateHouseShop");
		}
		
		return mv;
	}
	
	/**
	 * 同步店铺到mangodb
	 * @throws Exception
	 */
	@RequestMapping("/syncShopToMango")
	public ModelAndView syncShopToMango(HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws Exception{
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("sync", "1");                                         
		JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getHouseShop");
		String code = result.getString("code");
		String msg = result.get("msg").toString();
		RestResponse restP = new RestResponse();
		Map<String , Object> infoMap = new HashMap<String , Object>();
		infoMap.put("code", code);
		infoMap.put("msg", msg);
		restP.setData(infoMap);

		JsonGeneratorUtils.createRetMaptJSONObject(resp, restP);
		return null;
	}
	
	private void saveBossImage(HttpServletRequest request, StringBuffer shareImgStr) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("bossImage");
			for (int i = 0; i < iter.size(); i++) {
				String url = MsgPropertiesUtils.getUploadHouseUrl();
				HessianProxyFactory factory = new HessianProxyFactory();
				try {
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
					FileUploadRequest req = new FileUploadRequest();
					req.setSource("妈妈送房网");
					req.setFileType("jpg");// jpg 、png
					req.setGroupName("group1");
					req.setFile(iter.get(i).getBytes());
					if (req.getFile().length>0) {
						FileUploadResponse fileResponse = facade.uploadFile(req);
						if (null!=fileResponse.getFileUrl() && !"".equals(fileResponse.getFileUrl())) {
							shareImgStr.append(fileResponse.getFileUrl());
						}
						System.out.println("===图片路径为：" + fileResponse.getFileUrl() + "========" + fileResponse.getMessage());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}
	
	private void saveShopImage(HttpServletRequest request, StringBuffer shareImgStr) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("imgUrl");
			for (int i = 0; i < iter.size(); i++) {
				String url = MsgPropertiesUtils.getUploadHouseUrl();
				HessianProxyFactory factory = new HessianProxyFactory();
				try {
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
					FileUploadRequest req = new FileUploadRequest();
					req.setSource("妈妈送房网");
					req.setFileType("jpg");// jpg 、png
					req.setGroupName("group1");
					req.setFile(iter.get(i).getBytes());
					if (req.getFile().length>0) {
						FileUploadResponse fileResponse = facade.uploadFile(req);
						if (null!=fileResponse.getFileUrl() && !"".equals(fileResponse.getFileUrl())) {
							shareImgStr.append(fileResponse.getFileUrl());
						}
						System.out.println("===图片路径为：" + fileResponse.getFileUrl() + "========" + fileResponse.getMessage());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}
	
}