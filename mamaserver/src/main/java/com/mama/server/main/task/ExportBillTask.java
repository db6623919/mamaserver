package com.mama.server.main.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.HouseShopPo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.service.MainService;
import com.mama.server.main.service.clickfarming.IShopOwnerService;
import com.mama.server.util.PropertiesUtils;
import com.mongodb.util.JSON;

/**
 * 定时生成店铺对账单
 * @author whl
 *
 */
@Component
public class ExportBillTask {

	//记录日志
    private static Logger logger = LoggerFactory.getLogger(ClickFarmingAmtTask.class);
    
    @Autowired
    protected IShopOwnerService iShopOwnerService;
    @Autowired
    protected MainService mainService;

    /**
     * 定时生成对账单
     */
    public void exportBill() {
		logger.info("ExportBillTask is start.");
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
		try { 
			 
			//所有店铺
			List<HouseShopPo> houseShopList = mainService.getHouseShops(null);
			for (int i = 0 ; i < houseShopList.size() ; i ++) {
				//查询店铺下所有订单
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("shopId", houseShopList.get(i).getId());
				map.put("month", "month");
				List<OrderPo> orderList = iShopOwnerService.getCfOrderListByShopId(map);
				//不存在订单明细无需创建对账单
				if (orderList.size() < 1) {
					continue;
				}
				
				// 第一步，创建一个webbook，对应一个Excel文件  
		        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(PropertiesUtils.getBillPath()))); 
		        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		        HSSFSheet sheet = wb.getSheetAt(0);
				
		        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		        HSSFRow row = sheet.createRow((int) 0);
		        
		        // 第四步，创建单元格，并设置值表头 设置表头居中  
		        HSSFCellStyle style = wb.createCellStyle();  
		        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		        /*HSSFFont font = wb.createFont();
		        font.setFontHeightInPoints((short) 14);
		        style.setFont(font);*/
		        
		        HSSFCell cell = row.createCell(0);  
		        cell.setCellValue(houseShopList.get(i).getShopName() + new SimpleDateFormat("yyyyMM").format(c.getTime()) + "对账单");  
		        HSSFCellStyle styleHerd = wb.createCellStyle();  
		        styleHerd.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		        HSSFFont font = wb.createFont();
		        font.setFontHeightInPoints((short) 14);
		        styleHerd.setFont(font);
		        cell.setCellStyle(styleHerd);  
				
				// 第四步， 实际应用中这些数据从数据库得到， 
		        int totalAmt = 0; //支付总金额
	        	int freezeAmt = 0; //支付优惠金额
	        	int payAmt = 0;   //实际支付金额
	        	int payNum = 0; //支付订单数
	        	int settleNum = 0;//结算订单数
		        for(int y = 0 ; y < orderList.size() ; y ++){
		        	
		        	Map<String,Object> orderMap = (Map<String, Object>) orderList.get(y);
					
					// 第五步，创建单元格，并设置值  
			        row = sheet.createRow((int) y+4);  
			        //订单号
			        HSSFCell cell0 = row.createCell(0); 
			        cell0.setCellValue((String)orderMap.get("orderId"));
			        cell0.setCellStyle(style);
			        
			        String liveDetail = (String)orderMap.get("liveDetail");
			        Map<String, Object> liveDetailMap = (Map<String, Object>) JSON.parse(liveDetail);
			        //联系人		
			        if (liveDetailMap.containsKey("userName")) {
			        	HSSFCell cell1 = row.createCell(1);
			        	cell1.setCellValue((String)liveDetailMap.get("userName"));
			        	cell1.setCellStyle(style);
			        }		
			        //联系电话
			        if (liveDetailMap.containsKey("userPhone")) {
			        	HSSFCell cell2 = row.createCell(2);
			        	cell2.setCellValue((String)liveDetailMap.get("userPhone"));
			        	cell2.setCellStyle(style);
			        }
			        
			        //原始金额
			        HSSFCell cell3 = row.createCell(3);
			        cell3.setCellValue((Integer)orderMap.get("totalAmt"));
			        cell3.setCellStyle(style);
			        
			        //优惠金额
			        HSSFCell cell4 = row.createCell(4);
			        cell4.setCellValue((Integer)orderMap.get("freezeAmt"));
			        cell4.setCellStyle(style);
			        
			        //实际支付金额
			        HSSFCell cell5 = row.createCell(5);
			        cell5.setCellValue((Integer)orderMap.get("payAmt"));
			        cell5.setCellStyle(style);
			        
			        //支付状态
			        String statusName = "";
			        if ((Integer)orderMap.get("status") == 9) {
			        	statusName = "未支付";
			        } else if ((Integer)orderMap.get("status") == 11) {
			        	statusName = "已支付";
			        }
			        HSSFCell cell6 = row.createCell(6);
			        cell6.setCellValue(statusName);
			        cell6.setCellStyle(style);
			        
			        //支付方式
			        HSSFCell cell7 = row.createCell(7);
			        cell7.setCellValue((String)orderMap.get("pay_type"));
			        cell7.setCellStyle(style);
			        
			        //结算状态
			        String settleName = "未结算";
			        if ((Integer)orderMap.get("settleStatus") == 1) {
			        	settleName = "已结算";
			        	settleNum += 1;
			        }
			        HSSFCell cell8 = row.createCell(8);
			        cell8.setCellValue(settleName);
			        cell8.setCellStyle(style);
			        
			        //订单时间
			        HSSFCell cell9 = row.createCell(9);
			        cell9.setCellValue((String)orderMap.get("operTime"));
			        cell9.setCellStyle(style);
			        
			        
			        if ((Integer)orderMap.get("status") == 11) {
			        	totalAmt += (Integer)orderMap.get("totalAmt");
			        	freezeAmt += (Integer)orderMap.get("freezeAmt");
			        	payAmt += (Integer)orderMap.get("payAmt");
			        	payNum +=1;
			        }
			        
			    }  
		        
		        row = sheet.createRow((int) 2);
		        HSSFCell cell1 = row.createCell(1);
		        cell1.setCellValue(totalAmt);
		        cell1.setCellStyle(style);
		        
		        HSSFCell cell2 = row.createCell(2);
		        cell2.setCellValue(freezeAmt);
		        cell2.setCellStyle(style);
		        
		        HSSFCell cell3 = row.createCell(3);
		        cell3.setCellValue(payAmt);
		        cell3.setCellStyle(style);
		        
		        HSSFCell cell4 = row.createCell(4);
		        cell4.setCellValue(orderList.size());
		        cell4.setCellStyle(style);
		        
		        HSSFCell cell5 = row.createCell(5);
		        cell5.setCellValue(payNum);
		        cell5.setCellStyle(style);
		        
		        HSSFCell cell6 = row.createCell(6);
		        cell6.setCellValue(settleNum);
		        cell6.setCellStyle(style);
		        
		        String path = PropertiesUtils.getExportPath();
		        String filePath = path + new SimpleDateFormat("yyyyMM").format(c.getTime()) + "/";
		        File f = new File(filePath);
		        if (!f.exists()) {
		        	f.mkdirs();
		        }
		        // 第六步，将文件存到指定位置  
		        FileOutputStream fout = new FileOutputStream(filePath + houseShopList.get(i).getShopName() + new SimpleDateFormat("yyyyMM").format(c.getTime()) +"对账单.xls");  
		        wb.write(fout);  
		        fout.close();  
			}
		logger.info("ExportBillTask is end.");
		} catch (Exception e) {  
        	logger.error("对账单生成异常.",e);
        } 
        
    }
    
  
}
