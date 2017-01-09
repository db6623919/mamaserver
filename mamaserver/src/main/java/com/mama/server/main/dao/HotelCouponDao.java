package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mama.server.main.dao.model.ExchangePo;
import com.mama.server.main.dao.model.ExchangeRequestPo;
import com.mama.server.main.dao.model.HotelCouponGivePo;
import com.mama.server.main.dao.model.HotelCouponPo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

/**
 * 
 * @author Jack Cai
 * 
 * @version 0.0.1 2016年3月2日
 * 
 * @Copyright: 2016 www.88mmmoney.com Inc. All rights reserved.
 * 
 */
@MyBatisDao
public interface HotelCouponDao {

	List<HotelCouponPo> getHotelCoupons(Map<String, Object> params);

	List<HotelCouponPo> getHotelCouponGroup(long id);

	int updateHotelCouponStatus(@Param("ids") List<Long> ids, @Param("status") int status);

	int addHotelCouponExchange(Map<String, Object> params);

	int addHotelCouponExchangeRequest(Map<String, Object> params);

	List<ExchangePo> getHotelCouponExchanges(Map<String, Object> params);

	List<ExchangeRequestPo> getExchangeRequests(long exchangeId);

	ExchangeRequestPo getExchangeRequestById(long id);

	ExchangePo getExchangeDetailById(long id);

	int updateExchange(@Param("id") long id, @Param("status") int status);

	int updateExchangeRequest(@Param("id") long id, @Param("status") int status);

	int updateExchangeRequestByExchange(@Param("exchangeId") long exchangeId, @Param("status") int status);

	int changeHotelCoupon(@Param("memberId") String memberId, @Param("hotelCouponIds") List<Long> hotelCouponIds);
	
	List<HotelCouponGivePo> getHotelCouponGiveList(Map<String, Object> paramMap);
	
	HotelCouponGivePo getHotelCouponGiveDetail(Map<String, Object> paramMap);
	
	int addHotelCouponGive(Map<String, Object> paramMap);
	
	int addHotelCouponGiveReceive(Map<String, Object> paramMap);
	
	int updateReceivedHotelCouponIds(@Param("id") long paramLong, @Param("receivedHotelCouponIds") String paramString);
	
	int updateHotelCouponGiveStatus(@Param("id") long paramLong, @Param("status") int paramInt);
	
	int findHotelCouponGiveReceiveCount(@Param("hotelCouponGiveId") long paramLong, @Param("accountRefId") String paramString);
	
	int copyHotelCoupon(Map<String, Object> paramMap);
	

}
