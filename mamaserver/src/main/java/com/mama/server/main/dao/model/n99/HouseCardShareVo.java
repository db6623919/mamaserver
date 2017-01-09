package com.mama.server.main.dao.model.n99;

/** 房券分享对应实体类 */
public class HouseCardShareVo {
	/** 分享批次号 */
	private String sharePatchNo;
	
	/** 分享标题 */
	private String shareTitle;
	
	/** 分享链接 */
	private String shareUrl;
	
	/**  分享副标题 */
    private String shareSubTitle;
    
    /** 分享图片URL */
    private String shareImgURL;
    
    /** h5详情url */
    private String h5DetailUrl;

	public String getSharePatchNo() {
		return sharePatchNo;
	}

	public void setSharePatchNo(String sharePatchNo) {
		this.sharePatchNo = sharePatchNo;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getShareSubTitle() {
		return shareSubTitle;
	}

	public void setShareSubTitle(String shareSubTitle) {
		this.shareSubTitle = shareSubTitle;
	}

	public String getShareImgURL() {
		return shareImgURL;
	}

	public void setShareImgURL(String shareImgURL) {
		this.shareImgURL = shareImgURL;
	}

	public String getH5DetailUrl() {
		return h5DetailUrl;
	}

	public void setH5DetailUrl(String h5DetailUrl) {
		this.h5DetailUrl = h5DetailUrl;
	}
    
}
