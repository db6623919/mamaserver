package com.mmzb.house.app.vo;

import java.util.List;

/**
 * 联系人
 * @author dengbiao
 *
 */
public class ContactsPagerVo
{
	/** 联系人列表  **/
	private List<ContactsVo> checkInPersonList;
	
	//分页信息
	private Page pager;

	public List<ContactsVo> getCheckInPersonList() {
		return checkInPersonList;
	}

	public void setCheckInPersonList(List<ContactsVo> checkInPersonList) {
		this.checkInPersonList = checkInPersonList;
	}

	public Page getPager() {
		return pager;
	}

	public void setPager(Page pager) {
		this.pager = pager;
	}

}