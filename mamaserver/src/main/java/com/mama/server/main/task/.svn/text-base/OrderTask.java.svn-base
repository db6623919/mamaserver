package com.mama.server.main.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.service.MainService;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.SmsUtil;
import com.meidusa.fastjson.JSONArray;
import com.meidusa.fastjson.JSONObject;

/**
 * 订单监控任务
 * @author majiafei
 *
 */
@Component
public class OrderTask
{
	//订单超时时间为30分钟
	private static long TIMEOUT_TIME = 30 * 60 * 1000;
	
	//一天的毫秒数
	private static long ONE_DAY_IN_MILLIONS = 24 * 60 * 60 * 1000;
	
	//日期格式化
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    @Autowired
    private MainService mainService;
    
    //记录日志
    private static Logger LOG = LoggerFactory.getLogger(OrderTask.class);
    
	/**
	 * (商户确认,用户确认,支付)超时取消订单，每1分钟执行一次
	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancelOrderTask()
	{
		try
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("Start to run cancelOrderTask.");
			}
			
			//获取系统所有订单
			List<OrderPo> pos = mainService.getOrderByAllParam(null);
			if (CollectionUtils.isEmpty(pos)) 
			{
				if (LOG.isWarnEnabled()) 
				{
					LOG.warn("no orders found.");
				}
				return;
			}
			
			for(OrderPo po : pos)
			{
				//如果订单状态为待商户确认或待用户确认或待支付，且操作时间超过30分钟，则取消订单
				if ((po.getStatus() == ConstValue.ORDER_MERCHANT_VERIFYING)
						|| (po.getStatus() == ConstValue.ORDER_USER_TO_COMMIT)
						|| (po.getStatus() == ConstValue.ORDER_WAITING_FOR_PAYMENT))
				{
					long currentTime = System.currentTimeMillis();
					String operTime = po.getOperTime();
					long time = sdf.parse(operTime).getTime();
					if ((currentTime - time) >= TIMEOUT_TIME)
					{
						if (LOG.isDebugEnabled())
						{
							LOG.debug("found order which is needed to cancel. the orderId is {}.", po.getOrderId());
						}
						cancelOrder(po);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			LOG.error("Failed to execute cancelOrderTask.", e);
		}
	}
	
	/**
	 * 检查是否有需要通知用户入住的订单，提前一天通知
	 */
	public void checkVerifiedOrdersTask()
	{
		try 
		{
			//获取系统所有订单
			if (LOG.isDebugEnabled()) 
			{
				LOG.debug("Start to run checkVerifiedOrdersTask.");
			}
			
			List<OrderPo> pos = mainService.getOrderByAllParam(null);
			if (CollectionUtils.isEmpty(pos)) 
			{
				return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long currTime = sdf.parse(sdf.format(new Date())).getTime();
			for(OrderPo po : pos)
			{
				//过滤出状态为待入住的订单
				if ((po.getStatus() == ConstValue.ORDER_MERCHANT_VERIFIED))
				{
					JSONObject jsonObject = JSONObject.parseObject(po.getLiveDetail());
					String beginTime = jsonObject.getString("beginTime");
					long liveTime = sdf.parse(beginTime).getTime();
					
					//过滤出超时未入住的订单，并通知客服
					if ((currTime - liveTime) == ONE_DAY_IN_MILLIONS)
					{
						if (LOG.isDebugEnabled()) 
						{
							LOG.debug("found not-lived order, the order is {}.", po.toString());
						}
						
						po.setStatus(ConstValue.ORDER_USER_NOT_LIVED);
						mainService.updateOrder(po);
						
						String linkManPhone = jsonObject.getString("linkmanPhone");
				    	String houseName = jsonObject.getString("houseName");
						String merchantPhone = jsonObject.getString("phone");
						String msg = SmsUtil.genCanceledOrderSms(linkManPhone, houseName, po.getOrderId());
						
						if (LOG.isInfoEnabled())
						{
							LOG.info("Start to send not-lived message to the merchant and the mama, not-lived order is {}", po.toString());
						}
						
						//通知妈妈客服
						SmsUtil.sendSms(PropertiesUtils.getMmManagerPhone(), msg);
						
						//通知商户客服
						SmsUtil.sendSms(merchantPhone, msg);
					}
				}
			}
			
		} 
		catch (Exception e) 
		{
			LOG.error("Failed to execute checkVerifiedOrdersTask.", e);
		}
	}
	
	/**
	 *
	 *每天早上8：00发短信提醒用户订单情况
	 */
	public void checkNotLivedOrdersTask()
	{
		try 
		{
			if (LOG.isDebugEnabled()) 
			{
				LOG.debug("Start to run checkNotLivedOrdersTask");
			}
			
			//获取系统所有订单
			List<OrderPo> pos = mainService.getOrderByAllParam(null);
			if (CollectionUtils.isEmpty(pos)) 
			{
				return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long currTime = sdf.parse(sdf.format(new Date())).getTime();
			for(OrderPo po : pos)
			{
				//过滤出状态为待入住或未入住的订单
				if ((po.getStatus() == ConstValue.ORDER_MERCHANT_VERIFIED)
						|| (po.getStatus() == ConstValue.ORDER_USER_NOT_LIVED))
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug("Found not-lived or verified orders, the order is {}.", po.toString());
					}
					
					JSONObject jsonObject = JSONObject.parseObject(po.getLiveDetail());
					String beginTime = jsonObject.getString("beginTime");
					long liveTime = sdf.parse(beginTime).getTime();
			    	String phone = jsonObject.getString("linkmanPhone");
			    	String houseName = jsonObject.getString("houseName");
			    	
			    	String msg = null;
					
					//提前提醒消息
					if ((liveTime - currTime) == ONE_DAY_IN_MILLIONS)
					{
						msg = SmsUtil.genUserLivingOrderSms(phone, houseName, beginTime, po.getOrderId());
						
					}
					//超时未入住消息
					else if ((currTime - liveTime) == ONE_DAY_IN_MILLIONS)
					{
						msg = SmsUtil.genUserNotLivedOrderSms(phone, houseName, po.getOrderId());
					}
					
					//发送消息
					SmsUtil.sendSms(phone, msg);
				}
			}
			
		} 
		catch (Exception e) 
		{
			LOG.error("Failed to execute checkNotLivedOrdersTask.", e);
		}
	}

	//取消订单
	private void cancelOrder(OrderPo order) 
	{
		//TODO:一个事务、锁表（防止被审核）
		if (LOG.isDebugEnabled()) 
		{
			LOG.debug("start to cancel order:{}.", order);
		}
		
		//1.更新订单状态为取消状态
		order.setStatus(ConstValue.ORDER_MERCHANT_REFUND);
		mainService.updateOrder(order);
		
		//2.更新房间预订信息
		HouseOrderPo ho = new HouseOrderPo();
		ho.setRemoved(1);
		ho.setOrderId(order.getOrderId());
		mainService.updateHouseOrderByOrderId(ho);
		
		//3. 取消订单，如使用旅居券，退还旅居券
		if (StringUtils.isNotBlank(order.getLiveDetail())) 
		{
			JSONObject jsonObject = JSONObject.parseObject(order.getLiveDetail());
			String hotelCouponIdsKey = "hotelCouponIds";
			if (jsonObject.containsKey(hotelCouponIdsKey)) 
			{
				JSONArray jsonArray = jsonObject.getJSONArray(hotelCouponIdsKey);
				if (jsonArray != null && jsonArray.size() > 0) 
				{
					List<Long> ids = new ArrayList<Long>();
					for (int i = 0; i < jsonArray.size(); i++)
					{
						ids.add(jsonArray.getLongValue(i));
					}
					mainService.updateHotelCouponStatus(ids, 0);
				}
			}
		}
		
		//4.向用户发送取消订单的信息
		String details = order.getLiveDetail();
    	JSONObject showDetailJsonObject = JSONObject.parseObject(details);
    	String phone = showDetailJsonObject.getString("linkmanPhone");
    	String houseName = showDetailJsonObject.getString("houseName");
		String msg = SmsUtil.genMerchantCancelOrderSms(phone, order.getOrderId(), houseName, "订单已取消");
		SmsUtil.sendSms(phone, msg);
	}
	
}
