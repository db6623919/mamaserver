package com.console.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.console.dto.CFShopOrderDto;
import com.console.dto.OrderDto;
import com.console.framework.dao.Pagination;
import com.console.util.HttpClientPostMethod;
import com.console.util.JSONUtils;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.mama.server.common.errorCode.ReturnCode;
import com.meidusa.fastjson.JSONObject;

/**
 * 客栈刷单
 * @author whl
 *
 */
@Controller
@RequestMapping("/shopOrder")
public class ShopOrderAction extends BaseController{
	
	
	/**
	 * 客栈刷单总金额统计翻页
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShopOrderList")
	public ModelAndView toShopOrderList(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("run start to toShopOrderList.");
		
		ModelAndView mv = new ModelAndView();
		List<CFShopOrderDto> shopOrderList = new ArrayList<CFShopOrderDto>();
	    int current_page;// 当前页
	    if (null == request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		int totalCount = 0;
		String shopName = "";
		String bossName = "";
		String bossPhone = "";
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("currentPage", current_page);
			postData.put("pageSize", MsgPropertiesUtils.getPageSize());
			
			 //店铺名称
			
		    if (null != request.getParameter("shopName") && !("").equals(request.getParameter("shopName"))) {
		    	shopName = request.getParameter("shopName");
		    	postData.put("shopName", shopName);
		    }
			//老板姓名
		    if (null != request.getParameter("bossName") && !("").equals(request.getParameter("bossName"))) {
		    	bossName = request.getParameter("bossName");
		    	postData.put("bossName", bossName);
		    }
			//老板电话
			if (null != request.getParameter("bossPhone") && !("").equals(request.getParameter("bossPhone"))) {
				bossPhone = request.getParameter("bossPhone");
			    postData.put("bossPhone", bossPhone);
			}
			
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getShopOrder");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) {
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
				//所有店铺统计数据
				if (dataJson.containsKey("order")) {
					net.sf.json.JSONObject all = dataJson.getJSONObject("order");
					mv.addObject("totalAmt", all.getInt("totalAmt"));
					mv.addObject("freezeAmt", all.getInt("freezeAmt"));
					mv.addObject("payAmt", all.getInt("payAmt"));
					mv.addObject("orderNum", all.getInt("orderNum"));
					mv.addObject("settleNum", all.getInt("settleNum"));
				}
				net.sf.json.JSONObject pageData = dataJson.getJSONObject("page");
				totalCount = pageData.getInt("total");
				JSONArray arrJson = pageData.getJSONArray("result");
				for(int i = 0 ; i < arrJson.size() ; i ++ ) {
					net.sf.json.JSONObject shopOrderObject = arrJson.getJSONObject(i);
					CFShopOrderDto cfShopOrder = new CFShopOrderDto();
					cfShopOrder.setShopId(shopOrderObject.getInt("shopId"));
					cfShopOrder.setBossName(shopOrderObject.getString("bossName"));
					cfShopOrder.setBossPhone(shopOrderObject.getString("bossPhone"));
					cfShopOrder.setFreezeAmt(shopOrderObject.getInt("freezeAmt"));
					cfShopOrder.setPayAmt(shopOrderObject.getInt("payAmt"));
					cfShopOrder.setTotalAmt(shopOrderObject.getInt("totalAmt"));
					cfShopOrder.setShopName(shopOrderObject.getString("shopName"));
					cfShopOrder.setOrderNum(shopOrderObject.getInt("orderNum"));
					cfShopOrder.setSettleNum(shopOrderObject.getInt("settleNum"));
					shopOrderList.add(cfShopOrder);
				}
			}
		} catch (Exception e) {
			logger.error("getShopOrder:客栈刷单金额查询失败.",e);
		} 
		Pagination pager = new Pagination();
		pager.paging(current_page, MsgPropertiesUtils.getPageSize(), totalCount);
		
		mv.addObject("pager", pager);
		mv.addObject("list", shopOrderList);
		mv.addObject("shopName", shopName);
		mv.addObject("bossName", bossName);
		mv.addObject("bossPhone", bossPhone);
		mv.setViewName("/shopOrder/shopOrderList");
		logger.info("run end to toShopOrderList.");
		return mv;
	}
	
	/**
	 * 刷单订单明细
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCFOrderList")
	public ModelAndView toCFOrderList(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("run start to toCFOrderList.");
		
		List<OrderDto> orderList = new ArrayList<OrderDto>();
	    int current_page;// 当前页
	    if (null == request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		int totalCount = 0;
		String orderId = "";
		String startTime = "";
		String enTime = "";
		String shopName = "";
		String status = "";
		//Integer shopId = null;
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("currentPage", current_page);
			postData.put("pageSize", MsgPropertiesUtils.getPageSize());
			//订单ID
			if (null != request.getParameter("orderId") && !("").equals(request.getParameter("orderId"))) {
				orderId = request.getParameter("orderId");
		    	postData.put("orderId", orderId);
		    }
			//店铺名称
			if (null != request.getParameter("shopName") && !("").equals(request.getParameter("shopName"))) {
				shopName = request.getParameter("shopName");
		    	postData.put("shopName", shopName);
		    }
			//开始时间
			if (null != request.getParameter("startTime") && !("").equals(request.getParameter("startTime"))) {
				startTime = request.getParameter("startTime");
		    	postData.put("startTime", startTime);
		    }
			//结束时间
			if (null != request.getParameter("enTime") && !("").equals(request.getParameter("enTime"))) {
				enTime = request.getParameter("enTime");
		    	postData.put("enTime", enTime);
		    }
			//订单状态
			if (null != request.getParameter("status") && !("").equals(request.getParameter("status"))) {
				status = request.getParameter("status");
		    	postData.put("status", status);
		    }
			//店铺ID
			/*if (null != request.getParameter("shopId") && !("").equals(request.getParameter("shopId"))) {
				shopId = Integer.parseInt(request.getParameter("shopId"));
		    	postData.put("shopId", shopId);
		    }*/
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getCFOrder");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) {
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
				net.sf.json.JSONObject pageData = dataJson.getJSONObject("page");
				totalCount = pageData.getInt("total");
				JSONArray arrJson = pageData.getJSONArray("result");
				for(int i = 0 ; i < arrJson.size() ; i ++ ) {
					net.sf.json.JSONObject orderObject = arrJson.getJSONObject(i);
					OrderDto order = new OrderDto();
					order.setOrderId(orderObject.getString("orderId"));
					order.setFreezeAmt(orderObject.getInt("freezeAmt"));
					order.setTotalAmt(orderObject.getInt("totalAmt"));
					order.setPayAmt(orderObject.getInt("payAmt"));
					order.setPayType(orderObject.getString("pay_type"));
					order.setShopName(orderObject.getString("shopName"));
					order.setUid(orderObject.getString("uid"));
					order.setStatus(orderObject.getString("status"));
					order.setOperTime(orderObject.getString("operTime"));
					order.setSettleStatus(orderObject.getInt("settleStatus"));
					Map<String,Object> detailMap = JSONUtils.jsonToMap(orderObject.getString("liveDetail"));
					if (detailMap.containsKey("userPhone")) {
						order.setUserPhone((String)detailMap.get("userPhone"));
					}
					if (detailMap.containsKey("userName")) {
						order.setUserName((String)detailMap.get("userName"));
					}
					orderList.add(order);
				}
			}
		} catch (Exception e) {
			logger.error("getCFOrder:刷单订单明细.",e);
		} 
		Pagination pager = new Pagination();
		pager.paging(current_page, MsgPropertiesUtils.getPageSize(), totalCount);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pager", pager);
		mv.addObject("list", orderList);
		mv.addObject("orderId", orderId);
		mv.addObject("startTime", startTime);
		mv.addObject("enTime", enTime);
		mv.addObject("status", status);
		mv.addObject("shopName", shopName);
		mv.setViewName("/shopOrder/cfOrderList");
		logger.info("run end to toCFOrderList.");
		return mv;
	}
	
	/**
	 * 根据店铺查询刷单订单明细
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCFOrderListByShop")
	public ModelAndView toCFOrderListByShop(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("run start to cfOrderListByShop.");
		
		List<OrderDto> orderList = new ArrayList<OrderDto>();
	    int current_page;// 当前页
	    if (null == request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		int totalCount = 0;
		String orderId = "";
		String startTime = "";
		String enTime = "";
		Integer shopId = null;
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("currentPage", current_page);
			postData.put("pageSize", MsgPropertiesUtils.getPageSize());
			//订单ID
			if (null != request.getParameter("orderId") && !("").equals(request.getParameter("orderId"))) {
				orderId = request.getParameter("orderId");
		    	postData.put("orderId", orderId);
		    }
			//开始时间
			if (null != request.getParameter("startTime") && !("").equals(request.getParameter("startTime"))) {
				startTime = request.getParameter("startTime");
		    	postData.put("startTime", startTime);
		    }
			//结束时间
			if (null != request.getParameter("enTime") && !("").equals(request.getParameter("enTime"))) {
				enTime = request.getParameter("enTime");
		    	postData.put("enTime", enTime);
		    }
			//订单状态
			postData.put("status", "11");
			//店铺ID
			if (null != request.getParameter("shopId") && !("").equals(request.getParameter("shopId"))) {
				shopId = Integer.parseInt(request.getParameter("shopId"));
		    	postData.put("shopId", shopId);
		    }
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getCFOrder");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) {
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
				net.sf.json.JSONObject pageData = dataJson.getJSONObject("page");
				totalCount = pageData.getInt("total");
				JSONArray arrJson = pageData.getJSONArray("result");
				for(int i = 0 ; i < arrJson.size() ; i ++ ) {
					net.sf.json.JSONObject orderObject = arrJson.getJSONObject(i);
					OrderDto order = new OrderDto();
					order.setOrderId(orderObject.getString("orderId"));
					order.setFreezeAmt(orderObject.getInt("freezeAmt"));
					order.setTotalAmt(orderObject.getInt("totalAmt"));
					order.setPayAmt(orderObject.getInt("payAmt"));
					order.setPayType(orderObject.getString("pay_type"));
					order.setShopName(orderObject.getString("shopName"));
					order.setStatus(orderObject.getString("status"));
					order.setOperTime(orderObject.getString("operTime"));
					order.setSettleStatus(orderObject.getInt("settleStatus"));
					Map<String,Object> detailMap = JSONUtils.jsonToMap(orderObject.getString("liveDetail"));
					if (detailMap.containsKey("userPhone")) {
						order.setUserPhone((String)detailMap.get("userPhone"));
					}
					if (detailMap.containsKey("userName")) {
						order.setUserName((String)detailMap.get("userName"));
					}
					orderList.add(order);
				}
			}
		} catch (Exception e) {
			logger.error("getCFOrder:刷单订单明细.",e);
		} 
		Pagination pager = new Pagination();
		pager.paging(current_page, MsgPropertiesUtils.getPageSize(), totalCount);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pager", pager);
		mv.addObject("list", orderList);
		mv.addObject("orderId", orderId);
		mv.addObject("startTime", startTime);
		mv.addObject("enTime", enTime);
		mv.addObject("shopId", shopId);
		mv.setViewName("/shopOrder/cfOrderListByShop");
		logger.info("run end to cfOrderListByShop.");
		return mv;
	}

	/**
	 * 检查支付接口是否正确支付
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkOrder")
	@ResponseBody
	public RestResponse checkOrder(HttpSession session,HttpServletRequest request) throws  Exception {
		if(logger.isInfoEnabled()) {
			logger.info("start to run /checkOrder.");
		}
		RestResponse restP = new RestResponse();
		try {
			//1、基本请求参数校验
		    String  uid            =  request.getParameter("uid");//会员编号
			String  orderId          =  request.getParameter("orderId"); //业务订单号
			String  productCode    =  request.getParameter("productCode");//支付方式编码
			String  trade_type	   =  request.getParameter("trade_type");	// 微信统一下单贯口，trade_type
			
			Map<String, Object> postData = new HashMap<String, Object>();
		    postData.put("uid", uid);
		    postData.put("orderId", orderId);
		    postData.put("productCode", productCode);
		    postData.put("trade_type", trade_type);
			logger.info("============orderId==" + orderId + "====uid====" + uid);
			//2、请求妈妈送房后台查询
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "cfOrderStatus");
			logger.info("=========订单查询结果为result=" + result);
    		String code = result.getString("code");
    		if(ReturnCode.OK == Integer.parseInt(code)){
    			JSONObject json = result.getJSONObject("data"); 
    			restP.setSuccess(true);
        		restP.setCode(json.getString("resultCode"));
    		}
			logger.info("end to run /checkOrder.");
		} catch (Exception e) {
			restP.setSuccess(false);
			logger.error("checkOrder：订单支付检查失败.",e);
			e.printStackTrace();
		}
		return restP;
	}
	
	/**
	 * 订单结算
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/settleOrder")
	@ResponseBody
	public RestResponse settleOrder(HttpSession session,HttpServletRequest request) throws  Exception {
		if(logger.isInfoEnabled()) {
			logger.info("start to run /settleOrder.");
		}
		RestResponse restP = new RestResponse();
		try {
			String  orderId          =  request.getParameter("orderId"); //业务订单号
			
			Map<String, Object> postData = new HashMap<String, Object>();
		    postData.put("orderId", orderId);
			//2、请求妈妈送房后台查询
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "settleOrder");
			logger.info("=========订单查询结果为result=" + result);
    		String code = result.getString("code");
    		if(ReturnCode.OK == Integer.parseInt(code)){
    			JSONObject json = result.getJSONObject("data"); 
    			restP.setSuccess(true);
        		restP.setCode(json.getString("resultCode"));
    		}
			logger.info("end to run /settleOrder.");
		} catch (Exception e) {
			restP.setSuccess(false);
			logger.error("settleOrder：订单结算失败.",e);
			e.printStackTrace();
		}
		return restP;
	}
	
	/**
	 * 对账单列表查询
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShopOrderBillList")
	public ModelAndView toShopOrderBillList(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("run start to toShopOrderBillList.");
		//店铺名称
		String shopName = request.getParameter("shopName");
		Integer shopId = Integer.parseInt(request.getParameter("shopId"));
		List<OrderDto> orderList = new ArrayList<OrderDto>();
	    int current_page;// 当前页
	    if (null == request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
	    int totalCount = 0; 
	    try {
	    	Map<String,Object> postData = new HashMap<String, Object>();
	    	postData.put("currentPage", current_page);
			postData.put("pageSize", MsgPropertiesUtils.getPageSize());
			postData.put("shopId", shopId);
			postData.put("status", 11);
			postData.put("orderType", 2);
	    	JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "shopBillList");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) {
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
				net.sf.json.JSONObject pageData = dataJson.getJSONObject("page");
				totalCount = pageData.getInt("total");
				JSONArray arrJson = pageData.getJSONArray("result");
				for(int i = 0 ; i < arrJson.size() ; i ++ ) {
					net.sf.json.JSONObject orderObject = arrJson.getJSONObject(i);
					OrderDto order = new OrderDto();
					order.setFreezeAmt(orderObject.getInt("freezeAmt"));
					order.setTotalAmt(orderObject.getInt("totalAmt"));
					order.setPayAmt(orderObject.getInt("payAmt"));
					order.setOrderNum(orderObject.getInt("orderNum"));
					order.setShopId(orderObject.getInt("shopId"));
					order.setOperTime(orderObject.getString("operTime"));
					order.setSettleNum(orderObject.getInt("settleNum"));
					orderList.add(order);
				}
			}
	    } catch (Exception e) {
	    	logger.error("toShopOrderBillList:对账单查询失败.",e);
	    }
		Pagination pager = new Pagination();
		pager.paging(current_page, MsgPropertiesUtils.getPageSize(), totalCount);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pager", pager);
		mv.addObject("list", orderList);
		mv.addObject("shopName", shopName);
		mv.setViewName("/shopOrder/toShopOrderBillList");
		logger.info("run end to toShopOrderBillList.");
		return mv;
	}
	
	/**
	 * 检查长的是否存在
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkBillPath")
	@ResponseBody
	public RestResponse checkBillPath(HttpSession session,HttpServletRequest request) throws  Exception {
		RestResponse restP = new RestResponse();
		String operTime = request.getParameter("operTime"); //日期月份
		String shopName = request.getParameter("shopName"); //店铺名称
		//判断对账单文件是否存在
		String path = MsgPropertiesUtils.getExportPath() + operTime.replace("-", "") + "/" + shopName + operTime.replace("-", "") + "对账单.xls";
		File f = new File(path);
		if (!f.exists()) {
			restP.setSuccess(false);
		} else {
			restP.setSuccess(true);
		}
		return restP;
	}
	
	/**
	 * 对账单下载
	 * @param request
	 * @param resposne
	 * @throws Exception
	 */
	@RequestMapping("/exportBillExcel")
	public void exportBillExcel(HttpServletRequest request, HttpServletResponse resposne) throws  Exception {
		String operTime = request.getParameter("operTime"); //日期月份
		String shopName = request.getParameter("shopName"); //店铺名称
		//判断对账单文件是否存在
		String path = MsgPropertiesUtils.getExportPath() + operTime.replace("-", "") + "/"; //文件路径
		String fileName = shopName + operTime.replace("-", "") + "对账单.xls"; //文件名称
		InputStream inStream = new FileInputStream(path + fileName);// 文件的存放路径
		 try {  
	        	OutputStream output=resposne.getOutputStream();
	        	resposne.reset();
	        	resposne.setHeader("Content-disposition", "attachment; filename=" + toUtf8String(fileName));
	        	resposne.setContentType("application/msexcel");   
	            byte[] b = new byte[100];
	            int len;
	            while ((len = inStream.read(b)) > 0) {
	               	resposne.getOutputStream().write(b, 0, len);
	            }
	            inStream.close();
	            output.close();
	        } catch (Exception e) {  
	        	logger.info("exportBillExcel:对账单下载失败.",e);
	        }  
	}
	
	public static String toUtf8String(String s){ 
        StringBuffer sb = new StringBuffer(); 
          for (int i=0;i<s.length();i++){ 
             char c = s.charAt(i); 
             if (c >= 0 && c <= 255){sb.append(c);} 
           else{ 
           byte[] b; 
            try { b = Character.toString(c).getBytes("utf-8");} 
            catch (Exception ex) { 
                System.out.println(ex); 
                     b = new byte[0]; 
            } 
               for (int j = 0; j < b.length; j++) { 
                int k = b[j]; 
                 if (k < 0) k += 256; 
                 sb.append("%" + Integer.toHexString(k).toUpperCase()); 
                 } 
        } 
     } 
     return sb.toString(); 
   }
	
	/**
	 * 店铺明细订单导出
	 * @param session
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/exportOrderList")
	public void exportOrderList(HttpServletRequest request,HttpServletResponse resposne) throws  Exception {
		logger.info("run start to exportOrderList.");
		
		String orderId = "";
		String startTime = "";
		String enTime = "";
		Integer shopId = null;
		String shopName = "";
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("抽奖记录");  
        
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("订单编号");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(1);  
        cell.setCellValue("客栈名称");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(2);  
        cell.setCellValue("订单原始金额");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(3);  
        cell.setCellValue("优惠金额");  
        cell.setCellStyle(style);
        
        cell = row.createCell(4);  
        cell.setCellValue("实际支付金额");  
        cell.setCellStyle(style);
        
        cell = row.createCell(5);  
        cell.setCellValue("联系方式");  
        cell.setCellStyle(style);

        cell = row.createCell(6);  
        cell.setCellValue("联系人");  
        cell.setCellStyle(style);
        
        cell = row.createCell(7);  
        cell.setCellValue("状态");  
        cell.setCellStyle(style);
        
        cell = row.createCell(8);  
        cell.setCellValue("订单时间");  
        cell.setCellStyle(style);
        
        cell = row.createCell(9);  
        cell.setCellValue("是否结算");  
        cell.setCellStyle(style);
        
        
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			//订单ID
			if (null != request.getParameter("orderId") && !("").equals(request.getParameter("orderId"))) {
				orderId = request.getParameter("orderId");
		    	postData.put("orderId", orderId);
		    }
			//开始时间
			if (null != request.getParameter("startTime") && !("").equals(request.getParameter("startTime"))) {
				startTime = request.getParameter("startTime");
		    	postData.put("startTime", startTime);
		    }
			//结束时间
			if (null != request.getParameter("enTime") && !("").equals(request.getParameter("enTime"))) {
				enTime = request.getParameter("enTime");
		    	postData.put("enTime", enTime);
		    }
			//订单状态
			if (null != request.getParameter("status") && !("").equals(request.getParameter("status"))) {
				String status = request.getParameter("status");
		    	postData.put("status", status);
		    } else {
		    	postData.put("status", "11");
		    }
			
			//店铺ID
			if (null != request.getParameter("shopId") && !("").equals(request.getParameter("shopId"))) {
				shopId = Integer.parseInt(request.getParameter("shopId"));
		    	postData.put("shopId", shopId);
		    }
			
			if (null != request.getParameter("shopName") && !("").equals(request.getParameter("shopName"))) {
				shopName = request.getParameter("shopName");
		    	postData.put("shopName", shopName);
		    }
			
			
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getCFOrderByShop");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) { //成功
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
				JSONArray orderJson= dataJson.getJSONArray("orderList");
				
				for(int i = 0 ; i < orderJson.size() ; i ++ ) {
					net.sf.json.JSONObject orderObject = orderJson.getJSONObject(i);
					
					shopName = orderObject.getString("shopName");
					row = sheet.createRow((int) i + 1);  
					row.createCell(0).setCellValue(orderObject.getString("orderId"));
					row.createCell(1).setCellValue(orderObject.getString("shopName"));
					row.createCell(2).setCellValue(orderObject.getInt("totalAmt"));
					row.createCell(3).setCellValue(orderObject.getInt("freezeAmt"));
					row.createCell(4).setCellValue(orderObject.getInt("payAmt"));
					Map<String,Object> detailMap = JSONUtils.jsonToMap(orderObject.getString("liveDetail"));
					if (detailMap.containsKey("userPhone")) {
						row.createCell(5).setCellValue((String)detailMap.get("userPhone"));
					}
					if (detailMap.containsKey("userName")) {
						row.createCell(6).setCellValue((String)detailMap.get("userName"));
					}
					String statusName = "未支付";
					if (orderObject.getString("status").equals("11")) {
						statusName = "已支付";
					}
					row.createCell(7).setCellValue(statusName);
					row.createCell(8).setCellValue(orderObject.getString("operTime"));
					String settleName = "未结算";
					if (orderObject.getInt("settleStatus") == 1) {
						settleName = "已结算";
					}
					row.createCell(9).setCellValue(settleName);
				}
				sheet.autoSizeColumn((short)0); //调整第一列宽度
		        sheet.autoSizeColumn((short)1); //调整第二列宽度
		        sheet.autoSizeColumn((short)2); //调整第三列宽度
		        sheet.autoSizeColumn((short)3);
		        sheet.autoSizeColumn((short)4);
		        sheet.autoSizeColumn((short)5);
		        sheet.autoSizeColumn((short)6);
		        sheet.autoSizeColumn((short)7);
		        sheet.autoSizeColumn((short)8);
		        sheet.autoSizeColumn((short)9);
				
		        String fileName = shopName + "订单明细.xls";
		        OutputStream output=resposne.getOutputStream();
	        	resposne.reset();
	        	resposne.setHeader("Content-disposition", "attachment; filename=" + toUtf8String(fileName));
	        	resposne.setContentType("application/msexcel");        
	        	wb.write(output);
	            output.close();
				logger.info("run end to exportOrderList.");
			}
		} catch (Exception e) {
			logger.error("exportOrderList：店铺明细订单导出失败.",e);
		} 
	
	}
}
