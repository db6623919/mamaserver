package com.mama.server.main.service.clickfarming;

import java.util.List;

import com.mama.server.main.vo.clickfarming.CFHouseShopVo;

/**
 * 
 * @author lenovo
 *
 */
public interface ICFHouseShopService
{
	/**
	 * 刷单-添加客栈
	 * @param vo
	 */
	void modifyCFHouseShop(CFHouseShopVo vo);
	
	/**
	 * 刷单-获取客栈信息
	 * @param shopId
	 * @return
	 */
	CFHouseShopVo getCFHouseShop(int shopId);
	
	/**
	 * 获取所有参与刷单的客栈
	 * @return
	 */
	List<CFHouseShopVo> getAllCFHouseShop();
	
	/**
	 * 获取客栈的优惠总金额
	 * @param shopId
	 * @return
	 */
	public int getTotalFreezeAmt(int shopId,String day);
}
