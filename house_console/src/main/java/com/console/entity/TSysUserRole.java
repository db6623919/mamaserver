package com.console.entity;


/**
 * @ClassName:  AaaaVvvSss
 * @Description:工具自动生成的实体类
 * @author YY
 * @date   2013-09-11 17:45:57
 */
public class TSysUserRole implements java.io.Serializable {

	private static final long serialVersionUID = 654267330740058768L;

	private Integer user_id;//
	private Integer role_id;//

	public void setUser_id(Integer user_id){
		this.user_id=user_id;
	}
	public Integer getUser_id(){
		return user_id;
	}
	public void setRole_id(Integer role_id){
		this.role_id=role_id;
	}
	public Integer getRole_id(){
		return role_id;
	}
}

