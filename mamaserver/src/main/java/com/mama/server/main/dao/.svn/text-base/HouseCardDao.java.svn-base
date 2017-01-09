package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.dao.model.n99.HouseCardFlowPo;
import com.mama.server.main.dao.model.n99.HouseCardOrderPo;
import com.mama.server.main.dao.model.n99.HouseCardShareBatchVo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface HouseCardDao{
	
	List<HouseCardPo> getHouseCardList(Map<String,Object> map);
	
	/** 根据手机号码分页查询领取的房券列表  */
	public List<HouseCardPo> queryEarnedHouseCardList(Map<String,Object> map);
	
	/** 根据uid分页查询已经兑换的房券列表  */
	public List<HouseCardPo> queryExchangedHouseCardList(Map<String,Object> map);
	
	int exchangeHouseCard(HouseCardPo houseCard);
	
	/** 分页查询房券订单列表 */
	public List<HouseCardOrderPo> queryHouseCardOrderList(Map<String ,Object> map);
	
	/** 根据用户id查询当前用户下所有分享中的orderId */
	public List<String> queryOrderIdListByUid(String uid);
	
	void addHouseCard(HouseCardPo houseCard);
	
	List<HouseCardPo> getHouseCardListByOrderId(Map<String, Object> map);
	
	/** 查询剩余可分享房券数量 */
	public int queryHouseCardLeftNum(Map<String, Object> map);
	
	/** 保存房券分享批次号记录表信息 */
	public int saveHouseCardShareBatch(HouseCardShareBatchVo shareBatchVo);
	
	/** 根据房券编码查询房券详情 */
	public HouseCardPo queryHouseCardDetail(Map<String, Object> map);
	
	/** 根据订单id和用户id查询当前订单下可以用于分享的房券编码列表 */
	public List<String> queryCardNoList(Map<String, Object> map);
	
	/** 根据分享批次号查询领取房券 */
	public String queryToReceiveHouseCard(String sharePatchNo);
	
	/** 领取房券 */
	public int updateHouseCardForReceive(Map<String, Object> map);
	
	/** 分享点击确定后更新对应批次的分享状态 */
	public int updateShareStatus(String cardNo);
	
	/** 根据批房券分享批次号查询房券分享记录 */
	public HouseCardShareBatchVo queryHouseCardShareBatchRecord(String shareBatchNo);
	
	/** 查询当前批次号已经分享成功的总房券数 */
	public int queryTotalShareNumByBatchNo(String shareBatchNo);
	
	/** 查询批量分享待领取的房券信息 */
	public HouseCardPo queryToReceiveHouseCardForBatchShare(String orderId);
	
	/** 查询单次分享待领取的房券信息 */
	public HouseCardPo queryToReceiveHouseCardForCardNoShare(String cardNo);
	
	/** 保存房券分享领取流水记录 */
	public int saveHouseCardFlowPo(HouseCardFlowPo houseCardFlowPo);
	
	/** 根据订单号查询房券信息 */
	public HouseCardPo queryHouseCardByOrderId(String orderId);
	
	/** 根据订房券编码查询房券信息 */
	public HouseCardPo queryHouseCardByCardNo(String cardNo);
}