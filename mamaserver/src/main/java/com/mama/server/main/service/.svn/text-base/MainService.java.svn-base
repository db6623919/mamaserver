package com.mama.server.main.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mama.server.common.entity.RecommodHouse;
import com.mama.server.main.dao.OrderPayDO;
import com.mama.server.main.dao.model.ActivityConfPo;
import com.mama.server.main.dao.model.ActivityEnrollPo;
import com.mama.server.main.dao.model.ActivityMemberRecordPo;
import com.mama.server.main.dao.model.AppVersionPo;
import com.mama.server.main.dao.model.BuildingPo;
import com.mama.server.main.dao.model.CityPo;
import com.mama.server.main.dao.model.CollectPo;
import com.mama.server.main.dao.model.ContactPo;
import com.mama.server.main.dao.model.ContactsPo;
import com.mama.server.main.dao.model.CustomerUserPo;
import com.mama.server.main.dao.model.DeveloperPo;
import com.mama.server.main.dao.model.ExchangePo;
import com.mama.server.main.dao.model.ExchangeRequestPo;
import com.mama.server.main.dao.model.FlowPo;
import com.mama.server.main.dao.model.HotelCouponGivePo;
import com.mama.server.main.dao.model.HotelCouponPo;
import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.HousePricePo;
import com.mama.server.main.dao.model.HouseShopPo;
import com.mama.server.main.dao.model.HouseTag;
import com.mama.server.main.dao.model.OpenIdInfoPo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.ProvincePo;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.main.dao.model.TopicActivityPo;
import com.mama.server.main.dao.model.TopicShopPo;
import com.mama.server.main.dao.model.TradeArea;
import com.mama.server.main.dao.model.UserCardPo;
import com.mama.server.main.dao.model.UserInfoPo;
import com.mama.server.main.dao.model.Version;
import com.mama.server.main.dao.model.clickfarming.CFHouseShopPo;
import com.mama.server.main.dao.vo.HotelCouponGroupVo;
import com.mama.server.main.dao.vo.OrderPriceInfoVo;
import com.mama.server.util.dao.PaginationInterceptor.Page;
import com.mmzb.charge.domain.domain.PayDomain;

public interface MainService {
	int insertUserInfo(UserInfoPo uip);
	int updateUserInfo(UserInfoPo uip);
	UserInfoPo getUserinfoByAllParam(UserInfoPo uip);
	List<UserInfoPo> getAllUserInfo();
	int insSms(SmsPo sp);
	List<SmsPo> getSmsByAllParam(SmsPo sp);
	int updateSmsById(SmsPo sp);
	List<ProvincePo> getProvince();
	List<ProvincePo> getProvinces(Map map);
	int insProvince(ProvincePo pp);
	int insCity(CityPo pp);
	/**
	 * 判断省份是否存在
	 * @param name 
	 * @return
	 */
	int checkProvince(String name);
	/**
	 * 根据名称修改省份信息
	 * @param pro
	 * @return
	 */
	int updateProvinceByName(ProvincePo pro);
	/**
	 * 判断城市是否存在
	 * @param name
	 * @return
	 */
	CityPo checkCity(String name);
	/**
	 * 根据城市名称修改城市信息
	 * @param city
	 * @return
	 */
	int updateCityByName(CityPo city);
	List<CityPo> getCity();
//	List<CityPo> getCityByType(CityPo cp);
	List<CityPo> getCityByType(Map map);
	int updateContact(ContactPo cp);
	int removeContact(ContactPo cp);
	List<ContactPo> getContactByUid(ContactPo cp);
	int insertContact(ContactPo cp);
	List<ContactPo> getContactByAllParam(ContactPo cp);
	int insertUserCard(UserCardPo ucp);
	int updateUserCard(UserCardPo ucp);
	List<UserCardPo> getUserCardByUid(UserCardPo ucp);
    int updateDeveloper(DeveloperPo dp);
    /**
     * 判断开发商是否被楼盘引用
     * @param devId
     * @return
     */
    int getBuildingByDevId(int devId);
    int insertDeveloper(DeveloperPo dp);
	List<DeveloperPo> getDeveloperByDevId(DeveloperPo dp);
	List<DeveloperPo> getDeveloper(Map map);
    int updateBuliding(BuildingPo bp);
    int insertBuliding(BuildingPo bp);
    List<BuildingPo> getBuildingByCityId(BuildingPo bp);
    List<BuildingPo> getBuilding(BuildingPo bp);
    List<BuildingPo> getBuildings(Map map);
	int insertHouse(HousePo hp);
	int updateHouse(HousePo hp);
	List<HousePo> getHouseByAllParam(HousePo hp);
	int insertOrder(OrderPo op);
	int updateOrder(OrderPo op);
    int updateHouseOrderByOrderId(HouseOrderPo ho);
    
    /** 根据orderId查询OrderPo对象记录信息 */
    OrderPo queryOrderPoByOrderId(String orderId);
    
	List<OrderPo> getOrderByAllParam(OrderPo op);
	int insertFlow(FlowPo fp);
	List<FlowPo> getFlowByAllParam(FlowPo fp);
	int insertCollect(CollectPo cp);
	List<CollectPo> getCollectByAllParam(CollectPo cp);
	Page<CollectPo> getCollectByPage(CollectPo collect);
	int removeCollect(CollectPo cp);
	int insertHouseOrder(HouseOrderPo hop);
	int updateHouseOrderById(HouseOrderPo hop);
	List<HouseOrderPo> getHouseOrderByAllParam(HouseOrderPo hop);
	int updateCityById(CityPo cp);
	int updateProvinceById(ProvincePo cp);
	int getCityByProId(CityPo city);
	
    int insertHousePrice(HousePricePo hp);
    int updateHousePriceById(HousePricePo hp);
    List<HousePricePo> getHousePriceByAllParam(HousePricePo hp);
    List<HousePricePo> getHousePriceByByDate(int houseId,Date startDate,Date endDate);
    
    int insertOpen(OpenIdInfoPo op);
    int updateOpen(OpenIdInfoPo op);
    OpenIdInfoPo getOpenIdInfoPoByAllParam(OpenIdInfoPo op);
    
	
	/********************************************************/
	int cusInsertUser(CustomerUserPo cup);
	List<CustomerUserPo> cusGetUserinfo(CustomerUserPo cup);
	
	/********************************************************/
	String getTimeToMd5();
	String encodePassword(String password);
	String encodePassword(String password, String stamp);
	String genVerifyCode(int type);
	String getCurrentDate();
	String getCurrentDatetime();
	Date stringToDate(String str);
	String dateToString(Date date);
	Date getNextDate(Date date);
	String getNewUid();
	List<String> genUserCard();
	String getNewOrderId();
	long array2binary(List<Integer> inputList, long[] staticArr);
	List<Integer> binary2array(long binary, long[] staticArr);
	String checkPostParam(Object obj);
	String getPhoneByUid(String uid);
	String getUidByPhone(String friendCode);
	int getInvitedNumberByPhone(String phone);
	
	OrderPriceInfoVo getOrderPriceInfo(String mmWalletId, int houseId, Date startDate, Date endDate, List<Long> hotelCouponIds);
	/******************** 旅居券相关 **************************/
	List<HotelCouponGroupVo> getHotelCouponGroups(Map<String, Object> params);
	HotelCouponGroupVo getHotelCouponGroup(long id);
	List<HotelCouponPo> getHotelCoupons(Map<String, Object> params);
	int updateHotelCouponStatus(List<Long> ids, int status);
	long addHotelCouponExchange(ExchangePo exchangePo);
	long addHotelCouponExchangeRequest(ExchangeRequestPo exchangeRequestPo);
	List<ExchangePo> getHotelCouponExchanges(Map<String, Object> params);
	List<ExchangeRequestPo> getExchangeRequests(long exchangeId);
	ExchangeRequestPo getExchangeRequestById(long id);
	ExchangePo getExchangeDetailById(long id);
	int updateExchange(long id, int status);
	int updateExchangeRequest(long id, int status);
	int updateExchangeRequestByExchange(long exchangeId, int status);
	void changeHotelCoupon(String fromMemberId, String toMemberId, List<Long> fromHotelCouponIds, List<Long> toHotelCouponIds);
	List<CityPo> getCityByPar(CityPo cp);
	CityPo getCityById(int id);
	List<HotelCouponGivePo> getHotelCouponGiveList(Map<String, Object> paramMap);
	HotelCouponGivePo getHotelCouponGiveDetail(long paramLong);
	HotelCouponGivePo getHotelCouponGiveDetail(String paramString);
	long addHotelCouponGive(HotelCouponGivePo paramHotelCouponGivePo);
	void giveHotelCoupon(HotelCouponGivePo paramHotelCouponGivePo, String paramString);
	int updateHotelCouponGiveStatus(long paramLong, int paramInt);

	
	/******************** 我的联系人相关 **************************/
	List<ContactsPo> getMyContactsByUid(ContactsPo contactsPo);
//	List<ContactsPo> getMyContactsByUid(Map map);
	List<ContactsPo> getMyContactsByNameOrPhone(ContactsPo contactsPo);
	ContactsPo getMyContactsAllParam(ContactsPo contactsPo);
	int updateMyContacts(ContactsPo contactsPo);
	int insertMyContacts(ContactsPo contactsPo);
	int removeMyContacts(ContactsPo contactsPo);
	/******************** 我的联系人相关 **************************/

	
	/**
	 * 查询中奖记录人员
	 * @param record_date
	 * @return
	 */
	List<ActivityMemberRecordPo> getLotteryRecord(ActivityMemberRecordPo amr);
	/**
	 * 获取抽奖配置信息
	 * @param activity_code
	 * @return
	 */
	List<ActivityConfPo> getActivityConf(String activityCode);
	
	  /**
	   * 记录支付订单操作
	   * @return
	   */
	int order(PayDomain payDomain);
	
	OrderPayDO findOrderBypar(String orderId);
	
	/** 根据payId查询对应的支付记录信息 */
    OrderPayDO selectOrderPayByPayId(String payId);
	
	int updateByPrimaryKey(OrderPayDO record);
	List<CityPo> getCityByName(CityPo cp);
    List<TradeArea> getTradeAreas(Map map);
    int insTradeArea(TradeArea tArea);
	List<TradeArea> getTradeAreaById(TradeArea tArea);
	List<TradeArea> getTradeAreaByPar(TradeArea tArea);
	int delTradeArea(TradeArea tradeArea);
	int updateTradeArea(TradeArea tradeArea);
	/**
	 * 抽奖记录查询
	 * @param activityMemberRecordPo
	 * @return
	 */
	Page<ActivityMemberRecordPo> getRecordMember(ActivityMemberRecordPo activityMemberRecordPo);
	/**
	 * 获取所有抽奖记录
	 * @param activityMemberRecordPo
	 * @return
	 */
	public List<ActivityMemberRecordPo> getRecordAllMember(ActivityMemberRecordPo activityMemberRecordPo);
	
	//房源标签
	List<HouseTag> getHouseTags(Map map);
    int insHouseTag(HouseTag houseTag);
	List<HouseTag> getHouseTagById(HouseTag houseTag);
	List<HouseTag> getHouseTagByPar(HouseTag houseTag);
	List<HouseTag> duplicateHouseTagByPar(HouseTag houseTag);
	int updateHouseTag(HouseTag houseTag);
	/** 根据楼盘ID查询房源信息*/
	List<HousePo> getHouseByBldId(int bldId); 
	
	/**店铺**/
	List<HouseShopPo> getHouseShops(Map map);
	/**店铺-关联活动主题**/
	List<HouseShopPo> getAllHouseShopsByTopicId(Map map);
	List<HouseShopPo> getHouseShopByPar(HouseShopPo houseShop);
	int insHouseShop(HouseShopPo houseShop);
	int updateHouseShop(HouseShopPo houseShop);
	int delHouseShop(int id);
	
	/**活动报名表**/
	List<ActivityEnrollPo> getActivityEnrolls(Map map);
	/** 活动报名 */
	int insertActivityEnroll(ActivityEnrollPo activityEnroll);
	int updateActivityEnrollPo(ActivityEnrollPo op);
	
	/** 修改房源订单及支付订单状态 */
	public void updateHouseOrderAnaPayStatus(String uid, String orderId, String payId);
	
	/** 支付宝异步支付修改相关支付状态 */
	public void updateAlipayAsyncPayStatus(String uid, String orderId, String payId, String pay_type);
	
	/** 订单超时未支付或失败相关状态更新 */
	public void updateAlipayAsyncPayStatusForFail(String uid, String orderId, String payId, String pay_type);
	
	/** 根据房源定好号查询支付记录 */
	public List<OrderPayDO> queryPayOrderListByOrderId(String orderId);
	
	/**版本号记录**/
	int addVersion(Version version);
	int updateVersion(Version version);
	Version getVersionByPar(int versionType);
	
	/** 查询刷单*/
	List<OrderPo> getOrderClickFarming(OrderPo order);
	/** 插入刷单记录*/
	int insertClickFarming(OrderPo order);
	/** 刷单优惠 */
	int insCFHouseShop(CFHouseShopPo cfHouseShopPo);
	CFHouseShopPo getCFHouseShop(CFHouseShopPo cfHouseShopPo);
	
	/** 增加活动信息*/
	int insertTopicActivity(TopicActivityPo topicActivity);
	/** 获取活动信息*/
	Page<TopicActivityPo> getTopicActivity(TopicActivityPo topicActivity);
	/** 修改活动信息*/
	void updateTopicActivity(TopicActivityPo topicActivity);
	/** 删除活动信息*/
	void removeTopicActivity(TopicActivityPo topicActivity);
	/** 获取活动信息*/
	List<TopicActivityPo> getTopicActivityBy(TopicActivityPo topicActivity);
	/** 关联客栈查询*/
	List<TopicShopPo> getTopicShopList(TopicShopPo topicShop);
    /** 删除房源关联客栈*/
	void removeTopicShop(TopicShopPo topicShop);
	/** 添加活动关联客栈*/
	int insertTopicShop(TopicShopPo topicShop);
	/** 获取活动主题关联房源*/
	List<HousePo> getTopicHouses(Map map);
	/** 获取活动主题关联房源翻页*/
	Page<Map<String,Object>> getTopicHousesPage(Map map);
	/** 获取版本信息*/
	List<AppVersionPo> getAppVersion(AppVersionPo appVersion);
	/** 推荐房源列表 */
	List<RecommodHouse> getRecommondList();
	int addHouseCard(HouseCardPo houseCard);//房券添加
	List<HouseCardPo> getHouseCardListByOrderId(Map<String, Object> map);//按订单号查询房券
	
	/** 根据订单号查询最新一条支付成功的支付记录 */
	public OrderPayDO queryLatestOrderPayByOrderId(String orderId);
}
