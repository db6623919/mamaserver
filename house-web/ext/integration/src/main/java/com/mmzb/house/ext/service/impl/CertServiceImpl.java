package com.mmzb.house.ext.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mmzb.house.ext.common.ResponseCodeEnum;
import com.mmzb.house.ext.request.CertRequest;
import com.mmzb.house.ext.response.CertResponse;
import com.mmzb.house.ext.service.CertService;
import com.netfinworks.cert.service.facade.ICertFacade;
import com.netfinworks.cert.service.model.enums.AuthType;
import com.netfinworks.cert.service.request.QueryAuthRequest;
import com.netfinworks.cert.service.response.QueryPageResponse;
import com.netfinworks.common.domain.OperationEnvironment;

/**
 * 
* @ClassName: CertServiceImpl 
* @Description: 请求认证系统
* @author lijiaqi 
* @date 2015年12月14日 下午5:06:58
 */
@Service("certService")
public class CertServiceImpl implements CertService {

	private static Logger logger = LoggerFactory.getLogger(CertServiceImpl.class);
	
	@Resource(name="certFacade")
	private ICertFacade certFacade;
	
	@Override
	public CertResponse getAuthInfoByMemberId(CertRequest certRequest) {
		QueryAuthRequest qr = new QueryAuthRequest();
		CertResponse cr = new CertResponse();
		OperationEnvironment environment = new OperationEnvironment();
		try{
			this.validateRequest(certRequest);
			qr.setMemberId(certRequest.getMemberId());
			qr.setAuthType(AuthType.REAL_NAME.getCode());
			qr.setStartRow(0);
			qr.setEndRow(10);
			QueryPageResponse qpr = certFacade.queryList(qr, environment);
			logger.info("认证系统返回："+qpr);
			if(qpr.getResultCode().equals(ResponseCodeEnum.SUCCESS.getCode())){
				cr.setSuccess(true);
				cr.setMsg("请求成功");
				cr.setName(qpr.getAuthList().get(0).getAuthName());
				cr.setCertNo(qpr.getAuthList().get(0).getAuthNo());
			}else{
				cr.setSuccess(false);
				cr.setMsg("请求失败");
			}
		}catch(Exception e){
			logger.error("请求认证系统异常",e);
			cr.setSuccess(false);
			cr.setMsg(e.getMessage());
		}
		return cr;
	}

	
	private void validateRequest(CertRequest certRequest){
		if(StringUtils.isBlank(certRequest.getMemberId()))
			throw new IllegalArgumentException("会员编号不能为空");
	}
	
}
