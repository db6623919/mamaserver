package com.mama.server.main.service.n99.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.jedis.service.facade.RedisUtilFacade;
import com.mama.server.main.dao.HouseCardDao;
import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.n99.HouseCardFlowPo;
import com.mama.server.main.dao.model.n99.HouseCardOrderPo;
import com.mama.server.main.dao.model.n99.HouseCardShareBatchVo;
import com.mama.server.main.dao.model.n99.HouseCardShareVo;
import com.mama.server.main.service.MainService;
import com.mama.server.main.service.n99.HouseCardService;
import com.mama.server.util.SmsUtil;
import com.mama.server.util.constants.BusinessConstant;
import com.mama.server.util.dao.PaginationInterceptor;
import com.mama.server.util.dao.PaginationInterceptor.Page;
import com.mongodb.util.JSON;

@Service
public class HouseCardServiceImpl implements HouseCardService{
	private static final Logger logger = LoggerFactory.getLogger(HouseCardServiceImpl.class);
	
	@Autowired
    protected MainService mainService;
	
	@Autowired
	private HouseCardDao houseCardDao;
	
	@Resource(name = "redisUtilFacade")
    private RedisUtilFacade redisUtilFacade;
	
	@Override
	public Page<HouseCardPo> houseCardList(Map<String,Object> map) {
		PaginationInterceptor.startPage(Integer.parseInt(map.get("currentPage").toString()), Integer.parseInt(map.get("pageSize").toString()));
		houseCardDao.getHouseCardList(map);
		Page<HouseCardPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	public List<HouseCardPo> getHouseCardList(Map<String,Object> map) {
		return houseCardDao.getHouseCardList(map);
	}
	
	@Override
	public int exchangeHouseCard(HouseCardPo houseCard) {
		return  houseCardDao.exchangeHouseCard(houseCard);
	}
	
	@Override
	/** 分页查询房券订单列表 */
	public Page<HouseCardOrderPo> queryHouseCardOrderList(int pageNum, int pageSize, 
			String uid, List<Integer> statusList) {
		PaginationInterceptor.startPage(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("orderStatusList", statusList);
		houseCardDao.queryHouseCardOrderList(map);
		Page<HouseCardOrderPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	/** 根据用户id查询当前用户下所有分享中的orderId */
	public List<String> queryOrderIdListByUid(String uid) {
		return houseCardDao.queryOrderIdListByUid(uid);
	}
	
	@Override
	/** 查询剩余可分享房券数量 */
	public int queryHouseCardLeftNum(String uid, String orderId, String phoneNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("orderId", orderId);
		map.put("phoneNo", phoneNo);
		return houseCardDao.queryHouseCardLeftNum(map);
	}
	
	@Override
	/** 根据订单号分页查询房券列表 */
	public Page<HouseCardPo> queryHouseCardList(int pageNum, int pageSize, String orderId, String uid) {
		PaginationInterceptor.startPage(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderId);
		map.put("uid", uid);
		houseCardDao.getHouseCardList(map);
		Page<HouseCardPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	/** 根据手机号码分页查询领取的房券列表  */
	public Page<HouseCardPo> queryEarnedHouseCardList(int pageNum, int pageSize, String phoneNo) {
		PaginationInterceptor.startPage(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivePhone", phoneNo);
		houseCardDao.queryEarnedHouseCardList(map);
		Page<HouseCardPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	/** 根据uid分页查询已经兑换的房券列表  */
	public Page<HouseCardPo> queryExchangedHouseCardList(int pageNum, int pageSize, String uid, String phoneNo) {
		PaginationInterceptor.startPage(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("phoneNo", phoneNo);
		houseCardDao.queryExchangedHouseCardList(map);
		Page<HouseCardPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	/** 根据房券编码查询房券详情 */
	public HouseCardPo queryHouseCardDetail(String cardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardNo", cardNo);
		return houseCardDao.queryHouseCardDetail(map);
	}
	
	@Override
	/** 根据房券主键id查询房券详情 */
	public HouseCardPo queryHouseCardDetailById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return houseCardDao.queryHouseCardDetail(map);
	}
	
	@Override
	/** 赠送房券
	 *  @param uid 用户会员编码
	 *  @param orderId 订单编号
	 *  @param cardNum 分享房券数量
	 *  */
	public Map<String, Object> giveHouseCardByOrderId(String uid, String phoneNo, String orderId, int cardNum) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String key = "STR:MMSF:ORDERID:SHARE:" + orderId;//订单分享对应的key
		try {
			if(redisUtilFacade.isKeyExist(key)) {
				String oldTime = redisUtilFacade.get(key);
				long oldTimeLong = Long.valueOf(oldTime);
				long currentTime = System.currentTimeMillis();
				if(currentTime - oldTimeLong > 8000) {
					redisUtilFacade.del(key);
				} else {
					resultMap.put("code", "-1");
					resultMap.put("msg", "正在分享中，分享失败！");
					return resultMap;
				}
			}
			redisUtilFacade.set(key, "" + System.currentTimeMillis());
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("uid", uid);
			paramMap.put("orderId", orderId);
			paramMap.put("phoneNo", phoneNo);
			/** 剩余可分享房券数 */
			int cardLeftNum = houseCardDao.queryHouseCardLeftNum(paramMap);
			if(cardLeftNum > 0) {
				if(cardLeftNum < cardNum) {
					resultMap.put("code", "-1");
					resultMap.put("msg", "剩余房券不够，分享失败，最多可分享 " + cardLeftNum + " 张房券！");
				} else {
					UUID uuid  =  UUID.randomUUID(); 
					String uuidStr = uuid.toString();
					String shareBatchNo = uuidStr.replace("-", "");//分享批次号
					/** 保存分享批次号记录 */
					HouseCardShareBatchVo shareBatchVo = new HouseCardShareBatchVo();
					shareBatchVo.setOrderId(orderId);
					shareBatchVo.setShareBatchNo(shareBatchNo);
					shareBatchVo.setShareCardNum(cardNum);
					shareBatchVo.setShareUid(uid);
					shareBatchVo.setShareType(0);
					houseCardDao.saveHouseCardShareBatch(shareBatchVo);
					
					HouseCardPo houseCardPo = houseCardDao.queryHouseCardByOrderId(orderId);
					resultMap.put("code", "0");
					resultMap.put("msg", cardNum + "张房券分享成功！");
					resultMap.put("houseCardShareVo", generateHouseCardShareVo(shareBatchNo, houseCardPo.getCardPrice()));
				}
			} else {
				resultMap.put("code", "-1");
				resultMap.put("msg", "没有可用的房券，分享失败！");
			}
			redisUtilFacade.del(key);
		} catch(Exception e) {
			logger.error("分享过程中出现异常.", e);
			resultMap.put("code", "-1");
			resultMap.put("msg", "分享过程中出现异常，分享失败！");
			redisUtilFacade.del(key);
		}
		return resultMap;
	}
	
	@Override
	/** 赠送房券
	 *  @param cardNo 房券编码
	 *  @param receivePhone
	 *  */
	public Map<String, Object> giveHouseCardByCardNo(String uid, String cardNo, String receivePhone) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String key = "STR:MMSF:CARDNO:SHARE:" + cardNo;//订单分享对应的key
		try {
			if(redisUtilFacade.isKeyExist(key)) {
				String oldTime = redisUtilFacade.get(key);
				long oldTimeLong = Long.valueOf(oldTime);
				long currentTime = System.currentTimeMillis();
				if(currentTime - oldTimeLong > 8000) {
					redisUtilFacade.del(key);
				} else {
					resultMap.put("code", "-1");
					resultMap.put("msg", "正在分享中，分享失败！");
					return resultMap;
				}
			}
			redisUtilFacade.set(key, "" + System.currentTimeMillis());
			
			/** 查询当前房券是否 */
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cardNo", cardNo);
			HouseCardPo houseCardPo = houseCardDao.queryHouseCardDetail(map);
			if(houseCardPo == null) {
				resultMap.put("code", "-1");
				resultMap.put("msg", "未查询到对应的房券信息，分享失败！");
			} else {
				/** 
				 * 1、如果是自己的，则接收人电话号码为空；
				 * 2、如果不是自己的，则接收人电话号码需要等于当前登录人电话号码；
				 * */
				if((uid.equals(houseCardPo.getBuyId()) && 
						(houseCardPo.getReceivePhone() == null || houseCardPo.getReceivePhone().trim().length() <=0)) 
						|| receivePhone.equals(houseCardPo.getReceivePhone())) {
					if(houseCardPo.getUseStatus() == 0) {
						UUID uuid  =  UUID.randomUUID(); 
						String uuidStr = uuid.toString();
						String shareBatchNo = uuidStr.replace("-", "");//分享批次号
						
						houseCardDao.updateShareStatus(cardNo);
						
						/** 保存分享批次号记录 */
						HouseCardShareBatchVo shareBatchVo = new HouseCardShareBatchVo();
						shareBatchVo.setOrderId(houseCardPo.getOrderNo());
						shareBatchVo.setShareBatchNo(shareBatchNo);
						shareBatchVo.setShareCardNum(1);
						shareBatchVo.setShareUid(uid);
						shareBatchVo.setShareType(1);
						shareBatchVo.setCardNo(cardNo);
						houseCardDao.saveHouseCardShareBatch(shareBatchVo);
						
						resultMap.put("code", "0");
						resultMap.put("msg", "分享成功！");
						
						HouseCardPo cardPo = houseCardDao.queryHouseCardByCardNo(cardNo);
						resultMap.put("houseCardShareVo", generateHouseCardShareVo(shareBatchNo, cardPo.getCardPrice()));
						redisUtilFacade.del(key);
						return resultMap;
					} else {
						resultMap.put("code", "-1");
						resultMap.put("msg", "当前房券已经被兑换，不能用于分享，分享失败！");
					}
				} else {
					resultMap.put("code", "-1");
					resultMap.put("msg", "当前房券不能用于分享，分享失败！");
				}
			}
			redisUtilFacade.del(key);
		} catch(Exception e) {
			logger.error("分享过程中出现异常！", e);
			resultMap.put("code", "-1");
			resultMap.put("msg", "分享过程中出现异常，分享失败！");
			redisUtilFacade.del(key);
		}
		return resultMap;
	}
	
	@Override
	/** 领取房券 */
	public Map<String, String> receiveHouseCard(String shareBatchNo, String phoneNo) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String key = "STR:MMSF:PHONENO:" + shareBatchNo + ":" + phoneNo;
		try {
			if(redisUtilFacade.isKeyExist(key)) {
				String value = redisUtilFacade.get(key);
				if("3".equals(value)) {
					resultMap.put("code", "5");
					resultMap.put("msg", "不能重复领取，领取失败！");
					return resultMap;
				} else {
					String oldTime = redisUtilFacade.get(key);
					long oldTimeLong = Long.valueOf(oldTime);
					long currentTime = System.currentTimeMillis();
					if(currentTime - oldTimeLong > 8000) {
						redisUtilFacade.del(key);
					} else {
						resultMap.put("code", "2");
						resultMap.put("msg", "领取过于频繁，领取失败！");
						return resultMap;
					}
				}
			}
			redisUtilFacade.set(key, "" + System.currentTimeMillis());
			
			/** 根据批次号查询当前批次剩余的可分享房券张数 */
			HouseCardShareBatchVo shareBatchVo = houseCardDao.queryHouseCardShareBatchRecord(shareBatchNo);
			if(shareBatchVo != null) {
				/** 查询当前批次号已经分享成功的总房券数 */
				int totalLeftShareNum = houseCardDao.queryTotalShareNumByBatchNo(shareBatchNo);
				if(totalLeftShareNum < shareBatchVo.getShareCardNum()) {
					String orderId = shareBatchVo.getOrderId();
					int shareType = shareBatchVo.getShareType();
					HouseCardPo houseCardPo = null;
					if(shareType == 1) {//单张分享领取
						String cardNo = shareBatchVo.getCardNo();
						houseCardPo = houseCardDao.queryToReceiveHouseCardForCardNoShare(cardNo);
					} else {//批量分享领取
						houseCardPo = houseCardDao.queryToReceiveHouseCardForBatchShare(orderId);
					}
					
					if(houseCardPo != null) {
						String cardNo = houseCardPo.getCardNo();
						String randStr = generateRandStr(3);
						String newCardNo = cardNo.split("A")[0] + "A" + randStr;
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("receivePhone", phoneNo);
						paramMap.put("cardNo", cardNo);
						paramMap.put("newCardNo", newCardNo);
						houseCardDao.updateHouseCardForReceive(paramMap);
						saveHouseCardFlowPo(cardNo, phoneNo, shareBatchNo);
						String msg = "您已成功领取“N99 任我行 系列 ”房券，编号" + newCardNo + 
								"。请于2017/03/31前致电客服4009966633兑换。登录妈妈送房APP/H5“我的—电子房券”查看。APP下载地址：http://download.mmsfang.com/app";
						SmsUtil.sendSms(phoneNo, msg);
						resultMap.put("code", "0");
						resultMap.put("msg", "领取成功！");
						resultMap.put("cardNo", newCardNo);
						redisUtilFacade.set(key, "3");//已领取
						return resultMap;
					} else {
						resultMap.put("code", "3");
						resultMap.put("msg", "房券已领取完毕，领取失败！");
					}
				} else {
					resultMap.put("code", "3");
					resultMap.put("msg", "房券已领取完毕，领取失败！");
				}
			} else {
				resultMap.put("code", "4");
				resultMap.put("msg", "当前分享链接已失效，领取失败！");
			}
		} catch(Exception e) {
			logger.error("分享过程中出现异常.", e);
			resultMap.put("code", "-1");
			resultMap.put("msg", "房券领取过程中出现异常，领取失败！");
			redisUtilFacade.del(key);
		}
		redisUtilFacade.del(key);
		return resultMap;
	}
	
	/** 保存房券分享领取流水记录 */
	private void saveHouseCardFlowPo(String cardNo, String receivePhone, String shareBatchNo) {
		HouseCardFlowPo houseCardFlowPo = new HouseCardFlowPo();
		houseCardFlowPo.setCardNo(cardNo);
		houseCardFlowPo.setReceivePhone(receivePhone);
		houseCardFlowPo.setShareBatchNo(shareBatchNo);
		houseCardFlowPo.setReceivePhone(receivePhone);
		
		houseCardDao.saveHouseCardFlowPo(houseCardFlowPo);
	}
	
	/** 生成分享相关实体类对象 */
	private HouseCardShareVo generateHouseCardShareVo(String shareBatchNo, double cardPrice) {
		String cardType = "0";
		if(cardPrice == 299) {
			cardType = "1";
		}
		HouseCardShareVo houseCardShareVo = new HouseCardShareVo();
		houseCardShareVo.setSharePatchNo(shareBatchNo);
		houseCardShareVo.setShareTitle(BusinessConstant.N99_SHARE_TITLE);
		houseCardShareVo.setShareUrl(BusinessConstant.N99_SHARE_URL + shareBatchNo + "&cardType=" + cardType);
		houseCardShareVo.setShareSubTitle(BusinessConstant.N99_SHARE_SUB_TITLE);
		houseCardShareVo.setShareImgURL(BusinessConstant.N99_SHARE_IMAGE_URL);
		return houseCardShareVo;
	}
	
	/** 生成固定长度的随机数字 */
	public static String generateRandStr(int length) {
		String randStr = "";
		String[] arr = {"B", "C", "G", "3", "M",
						"R", "S", "U", "W", "Z",
						"P", "Q","7", "5", "N", 
						"D", "0", "1", "O", "8", 
						"H", "K", "T", "J", "X", 
						"L", "Y", "6", "E", "9", 
						"4", "I", "V", "2", "F"};
		int max = arr.length;
		for (int i = 0; i < length; i++) {
			int x = (int)(Math.random()*max);
			randStr += arr[x];
		}
		return randStr;
	}
	
	@Override
	/** 根据房源id查询房源名称 */
	public String queryHouseNameById(int houseId) {
		//获取房源名称
		HousePo hosue = new HousePo();
		hosue.setHouseId(houseId);
		List<HousePo> houseList = mainService.getHouseByAllParam(hosue);
		Map<String,Object> map = (Map<String, Object>) JSON.parse(houseList.get(0).getSummaryInfo());
		return (String)map.get("houseName");
	}
}