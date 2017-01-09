/********************************************************************
 * Copyright    :   Shanghai Mybank Information Technology Co., Ltd.
 * 
 * Filename     :   MenuVO.java
 * Author       :   YY
 * Date         :   2013-9-17
 * Version      :   V1.00
 * Description  :   
 *
 * History      :   Modify Id  |  Date  |  Origin  |  Description
 *******************************************************************/


package com.console.vo;

import java.util.List;

import com.console.entity.TSysResource;

public class MenuVO implements java.io.Serializable {

	private static final long serialVersionUID = 2796130735328087379L;
	
	private Integer id;   //id
	private String type;  //资源类型 1菜单类型 2其他(按钮等)类型
	private String name;  //资源名称 类型是菜单时为菜单名 类型为其他时为模块名（是权限被操作的领域）
	private String privilege;  //类型是菜单时为url 为其他(按钮等)时为权限（是被执行的操作）
	private Integer parent_id; //父id（资源为菜单时必选）
	private String status;     //状态  0未启用 1启用
	private Integer sort;      //排序字段
	private String description;     //描述
	private List<TSysResource> subMenu;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<TSysResource> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<TSysResource> subMenu) {
		this.subMenu = subMenu;
	}
	
	
}
