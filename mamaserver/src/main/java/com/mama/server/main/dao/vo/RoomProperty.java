package com.mama.server.main.dao.vo;

/**
 * 房屋属性
 * @author dengbiao
 *
 */
public class RoomProperty {
	
	private int room;  //室
	private int office;//厅
	private int toilet;//卫
	private String area;//面积
	private int beds;//床位
	private int personNum;//人数
	private int houseType;//房屋类型：1、别墅；2、高档公寓；3、酒店公寓；
	private String bedType;//床型
	private int isInvoice; //是否要发票
	private String telephone;//咨询电话
	private String videoUrl;//视频地址
	private String mapImg;//地图图片
	private String address;//详细地址
	
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public int getOffice() {
		return office;
	}
	public void setOffice(int office) {
		this.office = office;
	}
	public int getToilet() {
		return toilet;
	}
	public void setToilet(int toilet) {
		this.toilet = toilet;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getBeds() {
		return beds;
	}
	public void setBeds(int beds) {
		this.beds = beds;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public int getHouseType() {
		return houseType;
	}
	public void setHouseType(int houseType) {
		this.houseType = houseType;
	}
	public String getBedType() {
		return bedType;
	}
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	public int getIsInvoice() {
		return isInvoice;
	}
	public void setIsInvoice(int isInvoice) {
		this.isInvoice = isInvoice;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getMapImg() {
		return mapImg;
	}
	public void setMapImg(String mapImg) {
		this.mapImg = mapImg;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}