package com.mmzb.house.app.vo.order;

import java.util.List;

import com.mmzb.house.app.vo.Page;

/**
 * 订单列表接口Vo
 * @author lihaifeng
 */
public class ShuaDanOrdersVo {
	/** 订单列表 */
	private List<ShuaDanOrderVo> orders;

	/** 分页信息 */
	private Page pager;

	public List<ShuaDanOrderVo> getOrders() {
		return orders;
	}

	public void setOrders(List<ShuaDanOrderVo> orders) {
		this.orders = orders;
	}

	public Page getPager() {
		return pager;
	}

	public void setPager(Page pager) {
		this.pager = pager;
	}
}
