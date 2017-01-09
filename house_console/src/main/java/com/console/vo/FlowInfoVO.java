package com.console.vo;

public class FlowInfoVO {
	
	public String flowId;//流水号
	
	public String uid;//会员编号
	
	public String operTime;//时间
	
	public String amt;//金额
	
	public String type;//类型
	
	public String showDetail;//详情

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShowDetail() {
		return showDetail;
	}

	public void setShowDetail(String showDetail) {
		this.showDetail = showDetail;
	}

	@Override
	public String toString() {
		return "FlowInfoVO [flowId=" + flowId + ", uid=" + uid + ", operTime="
				+ operTime + ", amt=" + amt + ", type=" + type
				+ ", showDetail=" + showDetail + "]";
	}
	
}
