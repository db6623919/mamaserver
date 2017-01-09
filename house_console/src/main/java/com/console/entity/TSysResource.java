package com.console.entity;

/**
 * @ClassName:  TSysResource
 * @Description:工具自动生成的实体类
 * @author YY
 * @date   2014-01-24 14:09:20
 */
public class TSysResource implements java.io.Serializable {

	private static final long serialVersionUID = 2481275668378632258L;

	private Integer id;//id
	private String type;//资源类型 1菜单类型 2其他(按钮等)类型
	private String name;//资源名称 类型是菜单时为菜单名 类型为其他时为模块名（是权限被操作的领域）
	private String privilege;//类型是菜单时为url 为其他(按钮等)时为权限（是被执行的操作）
	private Integer parent_id;//父id（资源为菜单时必选）
	private String status;//状态  0未启用 1启用
	private Integer sort;//排序字段
	private String description;//描述
	private String bz1;//
	private String bz2;//
	private String bz3;//
	private String bz4;//
	private String bz5;//
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setPrivilege(String privilege){
		this.privilege=privilege;
	}
	public String getPrivilege(){
		return privilege;
	}
	public void setParent_id(Integer parent_id){
		this.parent_id=parent_id;
	}
	public Integer getParent_id(){
		return parent_id;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus(){
		return status;
	}
	public void setSort(Integer sort){
		this.sort=sort;
	}
	public Integer getSort(){
		return sort;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		return description;
	}
	public void setBz1(String bz1){
		this.bz1=bz1;
	}
	public String getBz1(){
		return bz1;
	}
	public void setBz2(String bz2){
		this.bz2=bz2;
	}
	public String getBz2(){
		return bz2;
	}
	public void setBz3(String bz3){
		this.bz3=bz3;
	}
	public String getBz3(){
		return bz3;
	}
	public void setBz4(String bz4){
		this.bz4=bz4;
	}
	public String getBz4(){
		return bz4;
	}
	public void setBz5(String bz5){
		this.bz5=bz5;
	}
	public String getBz5(){
		return bz5;
	}
}
//<sql id="base_column_comment">
//	id,type,name,privilege,parent_id,status,sort,description,bz1,bz2,bz3,bz4,bz5
//</sql>

