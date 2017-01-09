package com.mama.server.main.vo.clickfarming;

/**
 * 客栈VO
 * @author lenovo
 *
 */
public class CFHouseShopVo
{
    private Integer id;
	
	private Integer shopId;
	
	//总优惠金额
	private Integer totalAmt;
	
	//折扣
	private Float discount;
	
	//单笔优惠上限
	private Integer discountLimit;
	
	private int discountType; //优惠类型：1、折扣；2、优惠额度
	
	private int lowestAmt;//优惠限制金额

	public int getLowestAmt() {
		return lowestAmt;
	}

	public void setLowestAmt(int lowestAmt) {
		this.lowestAmt = lowestAmt;
	}

	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Integer totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Integer getDiscountLimit() {
		return discountLimit;
	}

	public void setDiscountLimit(Integer discountLimit) {
		this.discountLimit = discountLimit;
	} 

	

}
