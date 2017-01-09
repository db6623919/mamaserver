package com.console.entity;


/**
 * @ClassName:  AaaaVvvSss
 * @Description:工具自动生成的实体类
 * @author YY
 * @date   2013-09-11 17:45:56
 */
public class TSysRole implements java.io.Serializable {

	private static final long serialVersionUID = 1517052386223871310L;

	private Integer id;//id
	private String name;//角色名称
	private String status;//状态 0未启用 1启用
	private String description;//角色描述

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus(){
		return status;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		return description;
	}
}

