package com.mama.server.main.dao.vo;

/**
 * 房源详情图片
 * @author dengbiao
 *
 */
public class HouseImage {

	private String  imageUrl;//图片url
	private int  imageType; //类型：1、名宿特色；2、公共空间的温馨度；3、卧室；4卫浴；5、客厅；6、厨房；7阳台；8、外观；9周边；10、微信分享正方形缩略图；11、其他；
	private String imageName;//图片名称
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getImageType() {
		return imageType;
	}
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
}