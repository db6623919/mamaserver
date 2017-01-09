package com.console.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.console.dto.ActivityMemberRecordDto;
import com.console.framework.dao.Pagination;
import com.console.util.HttpClientPostMethod;
import com.console.util.MsgPropertiesUtils;
import com.meidusa.fastjson.JSONObject;

@Controller
@RequestMapping("/record")
public class RecordAction extends BaseController{

public static Logger logger = Logger.getLogger(CityAction.class);
	
	/**
	 * 抽奖记录查询
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toRecord")
	public ModelAndView toCity(HttpSession session,HttpServletRequest request) throws  Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
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
		//搜索日期
		String recordDate = "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfDate= new SimpleDateFormat("yyyy-MM-dd");
		if (null==request.getParameter("recordDate") || ("").equals(request.getParameter("recordDate"))) {
			Date date = new Date(); //前一天
			recordDate=sdfDate.format(new Date(date.getTime()- 24 * 60 * 60 * 1000));
		} else {
			recordDate = request.getParameter("recordDate");
		}
        postData.put("recordDate", sdf.format(sdfDate.parse(recordDate)));
        postData.put("currentPage", current_page);
        postData.put("pageSize", MsgPropertiesUtils.getPageSize());
        postData.put("activityCode", "AT_001");
		JSONObject obj = HttpClientPostMethod.httpCustReqUrl(postData, "getRecordMember");
		net.sf.json.JSONObject jsonData= net.sf.json.JSONObject.fromObject(obj.get("data"));
		net.sf.json.JSONObject pageJson = jsonData.getJSONObject("page");
		List<ActivityMemberRecordDto> activityMemberRecordList = new ArrayList<ActivityMemberRecordDto>();
		JSONArray resultJson = pageJson.getJSONArray("result");
		for(int i=0 ; i<resultJson.size(); i++){
			net.sf.json.JSONObject jsonObject = resultJson.getJSONObject(i);
			ActivityMemberRecordDto activityMemberRecord = new ActivityMemberRecordDto();
			activityMemberRecord.setMemberId(jsonObject.getString("memberId"));
			activityMemberRecord.setMemberIdentity(jsonObject.getString("memberIdentity"));
			activityMemberRecord.setActivityName(jsonObject.getString("activityName"));
			activityMemberRecord.setWinFlag(jsonObject.getString("winFlag"));
			activityMemberRecord.setTotalPoint(jsonObject.getInt("totalPoint"));
			activityMemberRecord.setRecordDate(jsonObject.getString("recordDate"));
			activityMemberRecord.setShareTimes(jsonObject.getInt("shareTimes"));
			activityMemberRecord.setRecordTimes(jsonObject.getInt("recordTimes"));
			activityMemberRecordList.add(activityMemberRecord);
		}
		pager.paging(current_page, MsgPropertiesUtils.getPageSize(), pageJson.getInt("total"));
		mv.addObject("activityMemberRecordList", activityMemberRecordList);
		
		mv.addObject("recordDate", recordDate);
		mv.addObject("pager", pager);
		mv.setViewName("/record/recordList");
		return mv;
	}
	
	/**
	 * 抽奖记录导出
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpSession session,HttpServletRequest request, HttpServletResponse resposne) throws  Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
		//搜索日期
		String recordDate = "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfDate= new SimpleDateFormat("yyyy-MM-dd");
		if (null==request.getParameter("recordDate") || ("").equals(request.getParameter("recordDate"))) {
			Date date = new Date(); //前一天
			recordDate=sdfDate.format(new Date(date.getTime()- 24 * 60 * 60 * 1000));
		} else {
			recordDate = request.getParameter("recordDate");
		}
		postData.put("activityCode", "AT_001");
		postData.put("recordDate", sdf.format(sdfDate.parse(recordDate)));
		JSONObject obj = HttpClientPostMethod.httpCustReqUrl(postData, "getRecordAllMember");
		net.sf.json.JSONObject jsonData= net.sf.json.JSONObject.fromObject(obj.get("data"));
		List<ActivityMemberRecordDto> activityMemberRecordList = new ArrayList<ActivityMemberRecordDto>();
		JSONArray resultJson = jsonData.getJSONArray("activityMemberRecordList");
		
		
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
        cell.setCellValue("序号");  
        //style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(style);  
        
		
        
        
        cell = row.createCell(1);  
        cell.setCellValue("会员ID");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(2);  
        cell.setCellValue("手机号");  
        cell.setCellStyle(style);  
        
        cell = row.createCell(3);  
        cell.setCellValue("活动名称");  
        cell.setCellStyle(style);
        
        cell = row.createCell(4);  
        cell.setCellValue("积分");  
        cell.setCellStyle(style);
        
        cell = row.createCell(5);  
        cell.setCellValue("日期");  
        cell.setCellStyle(style);

        cell = row.createCell(6);  
        cell.setCellValue("分享次数");  
        cell.setCellStyle(style);
        
        cell = row.createCell(7);  
        cell.setCellValue("抽奖次数");  
        cell.setCellStyle(style);
        // 第四步， 实际应用中这些数据从数据库得到，  
        for(int i=0 ; i<resultJson.size(); i++){
        	net.sf.json.JSONObject jsonObject = resultJson.getJSONObject(i);
			
			// 第五步，创建单元格，并设置值  
	        row = sheet.createRow((int) i + 1);  
	        row.createCell(0).setCellValue(i+1);
	        row.createCell(1).setCellValue(jsonObject.getString("memberId"));
	        row.createCell(2).setCellValue(jsonObject.getString("memberIdentity"));
	        row.createCell(3).setCellValue(jsonObject.getString("activityName"));
	        row.createCell(4).setCellValue(jsonObject.getInt("totalPoint"));
	        row.createCell(5).setCellValue(jsonObject.getString("recordDate"));
	        row.createCell(6).setCellValue(jsonObject.getInt("shareTimes"));
	        row.createCell(7).setCellValue(jsonObject.getInt("recordTimes"));
	    
	     
	    }  
        sheet.autoSizeColumn((short)0); //调整第一列宽度
        sheet.autoSizeColumn((short)1); //调整第二列宽度
        sheet.autoSizeColumn((short)2); //调整第三列宽度
        sheet.autoSizeColumn((short)3);
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)5);
        sheet.autoSizeColumn((short)6);
	        // 第六步，将文件存到指定位置  
        try {  
        	OutputStream output=resposne.getOutputStream();
        	resposne.reset();
        	resposne.setHeader("Content-disposition", "attachment; filename=activity_record_" + sdf.format(sdfDate.parse(recordDate)) +".xls");
        	resposne.setContentType("application/msexcel");        
        	wb.write(output);
            output.close();
            
            //FileOutputStream fout = new FileOutputStream("E:/students.xls");  
            //wb.write(fout);  
            //fout.close();  
        } catch (Exception e) {  
        	logger.info(e);
            e.printStackTrace();  
        }  
		//return "";
	}
}
