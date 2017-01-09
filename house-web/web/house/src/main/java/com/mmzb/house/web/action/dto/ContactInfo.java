package com.mmzb.house.web.action.dto;

public class ContactInfo {
	private String contactId;
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactIdCard() {
		return contactIdCard;
	}
	public void setContactIdCard(String contactIdCard) {
		this.contactIdCard = contactIdCard;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	private String contactName;
	private String contactIdCard;
	private String contactPhone;
}
