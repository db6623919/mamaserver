package com.mmzb.house.web.action.util;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletResponse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmzb.house.web.constant.DataResponse;
import com.mmzb.house.web.core.common.RestResponse;
public class JsonGeneratorUtils {
	private static JsonGenerator jsonGenerator = null;
	private static ObjectMapper objectMapper = null;   
	 public static    JsonGenerator createRetMaptJSONObject(ServletResponse response,String code,String message) throws IOException{  
		    response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=UTF-8");
		    objectMapper=new ObjectMapper();
		    jsonGenerator=objectMapper.getFactory().createGenerator(response.getWriter());	
			jsonGenerator.writeStartObject();
		    jsonGenerator.writeObjectField("code", code);
		    jsonGenerator.writeObjectField("msg", message);
			jsonGenerator.writeEndObject(); 
			jsonGenerator.flush();	
			jsonGenerator.close();
	        return jsonGenerator;  
	 }
	 
	 public static    JsonGenerator createRetMaptJSONObject(ServletResponse response,RestResponse ret) throws IOException{  
		    response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=UTF-8");
		    objectMapper=new ObjectMapper();
		    jsonGenerator=objectMapper.getFactory().createGenerator(response.getWriter());	
			jsonGenerator.writeStartObject();
			if(ret.isSuccess()){
				jsonGenerator.writeStringField("success", "true");
			}else{
				jsonGenerator.writeStringField("success", "false");
			}
			if(ret.getCode()!=null){
				jsonGenerator.writeStringField("code", ret.getCode()); 
				jsonGenerator.writeStringField("message", ret.getMessage()); 
			}
			/*if(ret.isSuccess()){
				if(ret.getErrors()!=null){
					Map<String, String> errors=ret.getErrors();
					StringBuffer sb=new StringBuffer();
					for (Map.Entry<String, String> MapString : errors.entrySet()) {   
						String key=MapString.getKey();//次方法获取月份   
						String value=MapString.getValue();//次方法获这个月份的投资额  
						sb.append(":").append(value);
					}
					jsonGenerator.writeStringField("message", sb.toString());
					ret.setSuccess(false);
				}else{
					jsonGenerator.writeStringField("code", Constant.CODE00000000); 
					jsonGenerator.writeStringField("message", Constant.CODE00000000MSG); 
				}

			}else{
				if(ret.getErrors()!=null){
					Map<String, String> errors=ret.getErrors();
					StringBuffer sb=new StringBuffer();
					for (Map.Entry<String, String> MapString : errors.entrySet()) {   
						String key=MapString.getKey();//次方法获取月份   
						String value=MapString.getValue();//次方法获这个月份的投资额  
						sb.append(":").append(value);
					}
					jsonGenerator.writeStringField("message", sb.toString()); 
				}else if(ret.getMessage()!=null){
					jsonGenerator.writeStringField("message", ret.getMessage()); 
				}
			} */
			if(ret.getData()!=null){
				Map<String,Object> resultDate=ret.getData();
				jsonGenerator.writeObjectField("result", resultDate);
			}
//			if(ret.getProjectMap()!=null){
//				jsonGenerator.writeObjectField("result", ret.getProjectMap());
//			}
			jsonGenerator.writeEndObject(); 
			jsonGenerator.flush();	
			jsonGenerator.close();
	        return jsonGenerator;  
	 }
	 
}