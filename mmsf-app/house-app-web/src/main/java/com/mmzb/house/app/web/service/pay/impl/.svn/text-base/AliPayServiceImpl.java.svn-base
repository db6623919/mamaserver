package com.mmzb.house.app.web.service.pay.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.util.JsonUtil;
import com.mmzb.house.app.vo.alipay.PayBizContentVo;
import com.mmzb.house.app.vo.pay.DataResponse;
import com.mmzb.house.app.web.action.alipay.ParamUtil;
import com.mmzb.house.app.web.action.alipay.PayConstants;
import com.mmzb.house.app.web.service.pay.AliPayService;

@Service("aliPayService")
public class AliPayServiceImpl implements AliPayService {
	private static Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);
	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	
	/** 根据订单信息和当前登录用户信息处理订单相关信息 */
	public DataResponse handleByAliPayOrderInfo(String out_trade_no, String money) {
		PayBizContentVo bizContentVo = new PayBizContentVo();
		bizContentVo.setBody("妈妈送房订单支付信息");
		bizContentVo.setSubject("妈妈送房订单支付信息");
		bizContentVo.setOut_trade_no(out_trade_no);//商户网站唯一订单号
		bizContentVo.setTimeout_express("24h");//该笔订单允许的最晚付款时间，逾期将关闭交易。
		bizContentVo.setTotal_amount(money);//金额
		bizContentVo.setSeller_id(PayConstants.ALIPAY_SELLER_ID);
		bizContentVo.setProduct_code("QUICK_MSECURITY_PAY");
		String bizContent = JsonUtil.objectToString(bizContentVo);
		String timestamp = getCurrentDate();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("app_id", PayConstants.APP_ID);
		params.put("biz_content", bizContent);
		params.put("charset", "utf-8");
		params.put("format", "json");
		params.put("method", "alipay.trade.app.pay");
		params.put("notify_url", appProperties.getMmsfWapWebUrl() + PayConstants.ALIPAY_ASYNC_NOTIFY_URL);
		params.put("sign_type", "RSA");
		params.put("timestamp", timestamp);
		params.put("version", "1.0");
		String toSignOrderInfo = AlipaySignature.getSignContent(params);
		
		/** 2. 再对原始字符串进行签名 */
		String signedOrderInfo = "";
		try {
			signedOrderInfo = AlipaySignature.rsaSign(toSignOrderInfo, 
					PayConstants.APP_PRIVATE_KEY, 
					"utf-8");
		} catch (AlipayApiException e) {
			logger.error("调用支付宝sdk进行rsa加密失败！", e);
			DataResponse response = new DataResponse();
			response.setCode(Constants.APP_FAILED);
			response.setMessage("订单处理失败失败！");
			return response;
		}
		
		/** 3. 最后对请求字符串的所有一级value（biz_content作为一个value）进行encode，
		 * 编码格式按请求串中的charset为准，没传charset按UTF-8处理 */
		params.put("sign", signedOrderInfo);
		String orderInfo = ParamUtil.getSortedStrInfo(params, true);
		DataResponse response = new DataResponse();
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("alipayOrderStr", orderInfo);
		response.setCode(Constants.APP_SUCCESSED);
		response.setMessage("订单信息处理成功！");
		response.setResult(resultMap);
		return response;
	}
	
	public static String getCurrentDate() {
    	String secondFormat = "yyyy-MM-dd HH:mm:ss";
    	DateFormat dateFormat = new SimpleDateFormat(secondFormat);
    	Date day = new Date(); 
    	String result = dateFormat.format(day);
        return result;
    }
}
