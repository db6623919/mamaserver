package com.mmzb.house.ext.response;

/**
 * 
* @ClassName: CertResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lijiaqi 
* @date 2015年12月15日 上午10:35:59
 */
public class CertResponse extends BaseResponse {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	
	private static final long serialVersionUID = 393057083151561434L;

	private String name;
	
	private String certNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	
}
