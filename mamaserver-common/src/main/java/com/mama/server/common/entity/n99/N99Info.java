package com.mama.server.common.entity.n99;

import java.util.List;

/**
 * N99信息
 * @author dengbiao
 *
 */
public class N99Info {

	private List<Tabs> tabs;

	private String shareTitle;//分享标题
    private String shareSubTitle; //分享子标题
    private String shareUrl; //分享链接
    private String shareImgURL;//分享缩略图
    
	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareSubTitle() {
		return shareSubTitle;
	}

	public void setShareSubTitle(String shareSubTitle) {
		this.shareSubTitle = shareSubTitle;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getShareImgURL() {
		return shareImgURL;
	}

	public void setShareImgURL(String shareImgURL) {
		this.shareImgURL = shareImgURL;
	}

	public List<Tabs> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tabs> tabs) {
		this.tabs = tabs;
	}
	
}