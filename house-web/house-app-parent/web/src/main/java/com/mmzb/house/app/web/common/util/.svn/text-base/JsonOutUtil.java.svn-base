package com.mmzb.house.app.web.common.util;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletResponse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmzb.house.app.web.common.DataResponse;


/**
 * @author chenmeiyang
 * JSON工具类
 */
public class JsonOutUtil {
	
	 private static JsonGenerator jsonGenerator = null;
	 private static ObjectMapper objectMapper = null;   

	 public static    JsonGenerator createRetMaptJSONObject(ServletResponse response,DataResponse ret) throws IOException{  
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

			if(ret.getData()!=null){
				Map<String,Object> resultDate=ret.getData();
				jsonGenerator.writeObjectField("result", resultDate);
			}

			jsonGenerator.writeEndObject(); 
			jsonGenerator.flush();	
			jsonGenerator.close();
	        return jsonGenerator;  
	 }
}
