package com.mmzb.house.web.action.pay;

import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.AlipaySignature;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.core.common.util.JsonUtil;
import com.mmzb.house.web.core.common.util.StringUtil;
import com.mmzb.house.web.core.common.vo.Response;
import com.mmzb.house.web.util.pay.DataResponse;
import com.mmzb.house.web.util.pay.ParamUtil;
import com.mmzb.house.web.util.pay.PayConstants;
import com.netfinworks.common.util.DateUtil;

/** 支付宝支付异步通知 */
@Controller
public class AliPayAsyncNotifyAction {
	private static final Logger logger = LoggerFactory.getLogger(AliPayAsyncNotifyAction.class);
	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	
	/** 支付宝支付--异步通知接口 */
	@RequestMapping(value = "/pay/aliPayAsyncNotify.htm")
	@ResponseBody
	public DataResponse aliPayAsyncNotify(HttpServletRequest request, HttpServletResponse httpResponse) {
		try {
			/** 第一步： 在通知返回参数列表中，除去sign、sign_type两个参数外，凡是通知返回回来的参数皆是待验签的参数 */
			Map<String, String> paramsMap = new HashMap<String, String>();
			Enumeration enu=request.getParameterNames();  
			while(enu.hasMoreElements()){  
				String key	= (String)enu.nextElement();
				String value = request.getParameter(key);
				System.out.println(key + "," + value);
				if(!StringUtil.isEmpty(value)) {
					paramsMap.put(key, value);
				}
			}  
			System.out.println(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") + ":" + JsonUtil.objectToString(paramsMap));
			
			/** 第二步： 将剩下参数进行url_decode, 然后进行字典排序，组成字符串，得到待签名字符串 */
			ParamUtil.getSortedDecodedMap(paramsMap);
			
			/** 第三步： 将签名参数（sign）使用base64解码为字节码串 */
			boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, 
					PayConstants.ALIPAY_PUBLIC_KEY, "utf-8"); //调用SDK验证签名
			signVerified = true;
			/** 通知的类型:trade_status_sync */
			String notify_type = paramsMap.get("notify_type");
			if(signVerified && "trade_status_sync".equals(notify_type)) {
				System.out.println("-------------------支付宝异步校验通过-------------------");
				// TODO 验签成功后
				//按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验
				//1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号--原支付请求的商户订单号
				String out_trade_no = paramsMap.get("out_trade_no");
				//2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）--本次交易支付的订单金额，单位为人民币（元）
				String total_amount = paramsMap.get("total_amount");
				//3、校验通知中的seller_id（或者seller_email) 
				//是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
				String seller_id = paramsMap.get("seller_id");
				//4、验证app_id是否为该商户本身。
				String app_id = paramsMap.get("app_id");
				//在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功
				/** WAIT_BUYER_PAY:交易创建，等待买家付款;TRADE_CLOSED:未付款交易超时关闭，或支付完成后全额退款
				 *  TRADE_SUCCESS:交易支付成功;TRADE_FINISHED:交易结束，不可退款
				 *  */
				String trade_status = paramsMap.get("trade_status");
				/** 根据订单id查询订单价格 */
				Map<String, Object> postData = new HashMap<String, Object>();
				postData.put("out_trade_no", out_trade_no);
				postData.put("total_amount", total_amount);
				postData.put("seller_id", seller_id);
				postData.put("app_id", app_id);
				postData.put("trade_status", trade_status);
				Response response = HttpClientPostMethod.httpReqUrl(postData, 
						appProperties.getRequestRoot(), "aliPayAsyncNotify", null);
				String code = response.getCode();
				if("0".equals(code)) {
					printAsyncHandleToAlipay(httpResponse, "success");
				} else {
					printAsyncHandleToAlipay(httpResponse, "failure");
				}
			} else {
				System.out.println("*****************not valid-------------------");
				// TODO 验签失败则记录异常日志，并在response中返回failure.
				printAsyncHandleToAlipay(httpResponse, "failure");
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.info("支付宝异步通知处理过程中出错", e);
			printAsyncHandleToAlipay(httpResponse, "failure");
		}
		DataResponse response = new DataResponse();
		response.setCode("00000000");
		response.setMessage("处理成功");
		return response;
	}
	
	private void printAsyncHandleToAlipay(HttpServletResponse response, String handleResult) {
		try {
			OutputStream out = response.getOutputStream();
			out.write(handleResult.getBytes("UTF-8"));
			out.flush();
			out.close();
			logger.info("返回给支付宝的信息为:" + handleResult);
		} catch (Exception e) {
			logger.error("返回给支付宝的过程中出错！", e);
		}
	}
}
