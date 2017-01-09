package com.mmzb.house.web.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年4月1日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
public class ExecResult {

	private int code;
	private String msg;
	private Object data;

	public static final int succ_code = 0;
	public static final int failure_code = 1;
	public static final String failure_msg = "系统繁忙，请稍后重试";
	public static final String succ_msg = "操作成功";

	private static final ExecResult succExecResult = new ExecResult(succ_code, succ_msg, null);
	private static final ExecResult failureExecResult = new ExecResult(failure_code, failure_msg, null);

	public ExecResult() {
	}

	public ExecResult(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static ExecResult getSucc() {
		return succExecResult;
	}

	public static ExecResult getFailure() {
		return failureExecResult;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
