package com.console.dto;

/**
 * 刷单客栈
 * @author dengbiao
 *
 */
public class CFHouseShopDto {

    private Integer id;
	
	private Integer shopId;
	
	//总优惠金额
	private Integer totalAmt;
	
	//折扣
	private float discount;
	
	//单笔优惠上限
	private Integer discountLimit; 
	
	private int message_switch=0;//短信开关 0 打开 1 关闭 默认0
	
	private Integer discountType; //1:折扣 2：金额 
	
	private int lowestAmt;//优惠限制金额

	public int getLowestAmt() {
		return lowestAmt;
	}

	public void setLowestAmt(int lowestAmt) {
		this.lowestAmt = lowestAmt;
	}

	public Integer getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Integer discountType) {
		this.discountType = discountType;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getShopId()
	{
		return shopId;
	}

	public void setShopId(Integer shopId)
	{
		this.shopId = shopId;
	}

	public Integer getTotalAmt()
	{
		return totalAmt;
	}

	public void setTotalAmt(Integer totalAmt)
	{
		this.totalAmt = totalAmt;
	}

	public Float getDiscount()
	{
		return discount;
	}

	public void setDiscount(Float discount)
	{
		this.discount = discount;
	}

	public Integer getDiscountLimit()
	{
		return discountLimit;
	}

	public void setDiscountLimit(Integer discountLimit)
	{
		this.discountLimit = discountLimit;
	}

	public int getMessage_switch() {
		return message_switch;
	}

	public void setMessage_switch(int message_switch) {
		this.message_switch = message_switch;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

}