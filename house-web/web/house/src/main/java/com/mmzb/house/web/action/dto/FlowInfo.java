package com.mmzb.house.web.action.dto;

public class FlowInfo {
	
	private int flowId;
	
	private String operTime;
	
	private int amt;
	
	private int type;
	
	private String showDetail;
	
	private int totalRecords;

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public int getAmt() {
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getShowDetail() {
		return showDetail;
	}

	public void setShowDetail(String showDetail) {
		this.showDetail = showDetail;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	
}
