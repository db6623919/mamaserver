package com.mama.server.util;

import com.caucho.hessian.client.HessianProxyFactory;
import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.VirtualFacade;
import com.mmzb.charge.facade.entity.OfflineChargeRequest;
import com.mmzb.charge.facade.entity.OfflineChargeResponse;
import com.mmzb.charge.facade.entity.VirtualChangeRequest;
import com.mmzb.charge.facade.entity.VirtualChangeResponse;
import com.mmzb.charge.facade.entity.VirtualFlowQueryRequest;
import com.mmzb.charge.facade.entity.VirtualFlowQueryResponse;
import com.mmzb.charge.facade.entity.VirtualQueryRequest;
import com.mmzb.charge.facade.entity.VirtualQueryResponse;

public class RequestChargeFacade {

	private static String chargeUrl;
	
	/**
	 * 资金变动处理
	 * VIRTUAL_ALL(0 ,"全部"),
	    VIRTUAL_FREEZED(1 ,"资金冻结"),
	    VIRTUAL_UNFREEZED(2 ,"资金解冻"),
	    VIRTUAL_REFUND(3 ,"退款"),
	    VIRTUAL_PAY(4 ,"支付"),
		VIRTUAL_CHARGE_ONLINE(5 ,"线上充值"),
		VIRTUAL_CHARGE_OFFLINE(6 ,"线下充值"),
		VIRTUAL_CHARGE_REWARD(7 ,"充值积分奖励");
	 * @param request
	 * @return
	 */
	public static VirtualChangeResponse VirtualChange(VirtualChangeRequest request){
		VirtualChangeResponse response =null;
		try{
			HessianProxyFactory factory = new HessianProxyFactory();
		 	VirtualFacade facade = (VirtualFacade) factory.create(VirtualFacade.class, chargeUrl+"virtualFacade");
		 	response= facade.virtualChange(request);
		}catch(Exception e){
			Log.SERVICE.error("VirtualChange请求支付系统异常",e);
		}
		return response;
	}
	
	
	/**
	 * 虚拟资产查询
	 * @param request
	 * @return
	 */
	public static VirtualQueryResponse virtualQuery(VirtualQueryRequest request){
		
		VirtualQueryResponse response =null;
		try{
			HessianProxyFactory factory = new HessianProxyFactory();
		 	VirtualFacade facade = (VirtualFacade) factory.create(VirtualFacade.class, chargeUrl+"virtualFacade");
		 	response= facade.virtualQuery(request);
		}catch(Exception e){
			Log.SERVICE.error("virtualQuery请求支付系统异常",e);
		}
		return response;
		
	}
	
	/**
     * 虚拟资产流水查询
     */
	public static VirtualFlowQueryResponse virtualFlowQuery(VirtualFlowQueryRequest request){
    	
		VirtualFlowQueryResponse response =null;
		try{
			HessianProxyFactory factory = new HessianProxyFactory();
		 	VirtualFacade facade = (VirtualFacade) factory.create(VirtualFacade.class, chargeUrl+"virtualFacade");
		 	response= facade.virtualFlowQuery(request);
		}catch(Exception e){
			Log.SERVICE.error("virtualFlowQuery请求支付系统异常",e);
		}
		return response;
    	
    }
	
	
	public String getChargeUrl() {
		return chargeUrl;
	}


	public void setChargeUrl(String chargeUrl) {
		this.chargeUrl = chargeUrl;
	}
	
	
	public static void main(String[] args) {
		VirtualQueryRequest vqr = new VirtualQueryRequest();
        vqr.setMemberID("41b9b9c31f56056bf6504bdbe5941ef0");
        System.out.println(virtualQuery(vqr));
	}
}
