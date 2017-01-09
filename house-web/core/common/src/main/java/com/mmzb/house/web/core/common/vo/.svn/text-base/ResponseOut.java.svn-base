package com.mmzb.house.web.core.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mmzb.house.web.core.common.util.Contants;

//将该标记放在属性上，如果该属性为NULL则不参与序列化
//如果放在类上边,那对这个类的全部属性起作用
//Include.Include.ALWAYS 默认
//Include.NON_DEFAULT 属性为默认值不序列化
//Include.NON_EMPTY 属性为 空（“”）  或者为 NULL 都不序列化
//Include.NON_NULL 属性为NULL 不序列化
@JsonInclude(Include.NON_NULL)
public class ResponseOut<T> {
	private int code = Contants.SUCCESSED;
	private String message;
	private T result;

	public ResponseOut() {
		super();
	}

	public ResponseOut(T result) {
		super();
		this.result = result;
	}

	public ResponseOut(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public ResponseOut(int code, String message, T result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}
	public ResponseOut(String message, T result) {
		super();
		this.message = message;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
}
