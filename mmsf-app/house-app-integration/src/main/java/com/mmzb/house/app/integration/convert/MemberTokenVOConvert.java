package com.mmzb.house.app.integration.convert;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.netfinworks.authorize.ws.request.impl.GetMemberTokenByAccessTokenRequest;
import com.netfinworks.authorize.ws.request.impl.RequestHeader;

public class MemberTokenVOConvert {

	
    public static RequestHeader createRequestHeader(){
    	
    	String version = "1.0";
        String sourceId = "PRO";
        String extension = "ext";
        String mac = "ssssss";
        String operatorType = "1";
        String requestCode = "testcosde";
        String requestOperator = "netfinworks";
        Date requestTime = new Date();
      
        
        RequestHeader header = new RequestHeader();
        header.setVersion(version);
        header.setSourceId(sourceId);
        header.setExtension(extension);
        header.setMac(mac);
        header.setOperatorType(operatorType);
        header.setRequestCode(requestCode);
        header.setRequestOperator(requestOperator);
        header.setRequestTime(requestTime);
        header.setRequestNo(UUID.randomUUID().toString().replace("-", ""));
    
        return header;
    }
    
    public static GetMemberTokenByAccessTokenRequest selectMemberTokenRequest(String  tokenId) {
        if (tokenId == null) {
            return null;
        }
        GetMemberTokenByAccessTokenRequest request = new GetMemberTokenByAccessTokenRequest();
        request.setAccessToken(tokenId);
        BeanUtils.copyProperties(tokenId, request);
        RequestHeader header = createRequestHeader();
        request.setHeader(header);
        return request;
    }
}
