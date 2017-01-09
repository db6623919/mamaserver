package com.mama.server.main.service.n99;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.dao.model.n99.HouseCardOrderPo;
import com.mama.server.util.dao.PaginationInterceptor.Page;

/**
 * 房券接口
 */
public interface HouseCardService {

	/** 房券列表 */
	Page<HouseCardPo> houseCardList(Map<String,Object> map);
	
	/** 房券列表查询 */
	List<HouseCardPo> getHouseCardList(Map<String,Object> map);
	
	/** 房券兑换 */
	int exchangeHouseCard(HouseCardPo houseCard);
	
	/** 分页查询房券订单列表
	 *  @param pageNum  当前第几页
	 *  @param pageSize 每页记录数
	 *  @param uid      当前用户id
	 *  @param statusList 订单状态列表
	 *  */
	Page<HouseCardOrderPo> queryHouseCardOrderList(int pageNum, int pageSize, 
			String uid, List<Integer> statusList);
	
	/** 根据用户id查询当前用户下所有分享中的orderId */
	List<String> queryOrderIdListByUid(String uid);
	
	/** 查询剩余可分享房券数量
	 *  @param orderId 支付订单号
	 *  */
	public int queryHouseCardLeftNum(String uid, String orderId, String phoneNo);
	
	/** 根据订单号分页查询房券列表 */
	Page<HouseCardPo> queryHouseCardList(int pageNum, int pageSize, String orderId, String uid);
	
	/** 根据手机号码分页查询领取的房券列表  */
	Page<HouseCardPo> queryEarnedHouseCardList(int pageNum, int pageSize, String phoneNo);
	
	/** 根据uid分页查询已经兑换的房券列表  */
	Page<HouseCardPo> queryExchangedHouseCardList(int pageNum, int pageSize, String uid, String phoneNo);
	
	/** 根据房券编码查询房券详情 */
	public HouseCardPo queryHouseCardDetail(String cardNo);
	
	/** 根据房券主键id查询房券详情 */
	public HouseCardPo queryHouseCardDetailById(String id);
	
	/** 赠送房券
	 *  @param uid 用户会员编码
	 *  @param cardNo 房券编码
	 *  @param orderId 订单编号
	 *  @param giveType 分享方式：1、订单；2、单张房券；
	 *  */
	public Map<String, Object> giveHouseCardByOrderId(String uid, String phoneNo, String orderId, int cardNum);
	
	/** 赠送房券
	 *  @param cardNo 房券编码
	 *  @param receivePhone
	 *  */
	public Map<String, Object> giveHouseCardByCardNo(String uid, String cardNo, String receivePhone);
	
	/** 领取房券 */
	public Map<String, String> receiveHouseCard(String sharePatchNo, String cardNo);
			
	/** 根据房源id查询房源名称 */
	public String queryHouseNameById(int houseId);
}
