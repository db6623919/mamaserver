package com.console.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

public class ExcelUtil {
	/**
	 * @Description    	           根据模板生成excel
	 * @param templateName  模板文件
	 * @param createName    生成的文件
	 * @param data      数据集
	 * @return      	返回生成文件的完整路径
	 * @author      	YY
	 * @date	    	2011-10-29/下午09:30:34
	 */
	public static void doExcel(String templateName, String createName, Map<?, ?> data,HttpServletResponse response) {

		String sfrom = "";
		String sto   = "";		
		try {
			String classPath = CommonUtil.getRealRootPath();
			XLSTransformer transformer = new XLSTransformer();
//			sfrom = classPath +File.separator+ "resource" +File.separator+ templateName;  // 模板文件
//			sto   = classPath +File.separator+ "resource" +File.separator+ createName;  
			sfrom = classPath +File.separator+ "WEB-INF" +File.separator+ "classes"+File.separator+templateName;  // 模板文件
			sto   = classPath +File.separator+ "WEB-INF" +File.separator+ "classes"+File.separator+createName; 
			if("\\".equals(File.separator)){
				sfrom = sfrom.replace("/", "\\");
				sto   = sto.replace("/", "\\");
			}
			if("/".equals(File.separator)){
				sfrom = sfrom.replace("\\", "/");
				sto   = sto.replace("\\", "/");
			}
//			transformer.transformXLS(sfrom, data, sto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//下载excel
        doDownLoad(sto, createName,response);
	}

	/**
	 * @Description 下载文件(在下载之前，不要去调用response.getWrite())
	 * @param  path 文件物理路径
	 * @param  name 文件要保存的名称
	 * @author      YY
	 * @date	    2011-10-29/下午09:34:16
	 */
	public static void doDownLoad(String path, String name,HttpServletResponse response) {
        String  filepath=path;//本地绝对路径
        File uploadFile = new File(filepath);
		try {
			  BufferedInputStream bis = null;
	          BufferedOutputStream bos = null;
	          OutputStream fos = null;
	          InputStream fis = null;
	          fis = new FileInputStream(uploadFile);
	          bis = new BufferedInputStream(fis);
	          fos = response.getOutputStream();
	          bos = new BufferedOutputStream(fos);
	          response.setHeader("Content-disposition","attachment;filename=" + URLEncoder.encode(name, "utf-8"));
	          int bytesRead = 0;
	          //用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
	          byte[] buffer = new byte[8192];
	          while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
	              bos.write(buffer, 0, bytesRead);
	          }         
	          bos.flush();
	          fis.close();
	          bis.close();
	          fos.close();
	          bos.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			uploadFile.delete();
		}
	}

}
