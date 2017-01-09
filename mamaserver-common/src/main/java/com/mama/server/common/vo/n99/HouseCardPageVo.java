package com.mama.server.common.vo.n99;

import java.util.List;

import com.mama.server.common.util.Page;

/**
 * 房券列表Vo
 * @author lihaifeng
 */
public class HouseCardPageVo {
	/** 订单列表 */
	private List<HouseCardVo> houseCardList;

	/** 分页信息 */
	private Page pager;

	public List<HouseCardVo> getHouseCardList() {
		return houseCardList;
	}

	public void setHouseCardList(List<HouseCardVo> houseCardList) {
		this.houseCardList = houseCardList;
	}

	public Page getPager() {
		return pager;
	}

	public void setPager(Page pager) {
		this.pager = pager;
	}
}
