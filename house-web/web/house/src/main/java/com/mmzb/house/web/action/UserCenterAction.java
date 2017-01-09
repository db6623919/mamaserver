package com.mmzb.house.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.entity.VirtualFlowEntity;
import com.mmzb.charge.facade.entity.VirtualFlowQueryRequest;
import com.mmzb.charge.facade.entity.VirtualFlowQueryResponse;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.CollectsInfo;
import com.mmzb.house.web.action.dto.OrderInfo;
import com.mmzb.house.web.action.dto.SessionUserInfo;
import com.mmzb.house.web.action.dto.UserCardInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.DateUtils;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.util.CS;

@Controller
public class UserCenterAction extends BaseAction {
	
    @Resource(name="appProperties")
	private AppProperties appProperties;
    
    @Autowired
    public GerneralMethod gerneralMethod;
    private static Logger logger = LoggerFactory.getLogger(UserCenterAction.class);
    
    @RequestMapping(value = "/my/usercenter.htm", method = { RequestMethod.GET })
    public String toLogin(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
//    	UserInfo loginUser = super.getJsonAttribute(request.getSession(),
//                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
    	//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
    	
    	SessionUserInfo loginUser = getLoginUserInfo(request);
//    	UserInfo loginUser = sessionUserInfo;
		UserCardInfo uci = new UserCardInfo();
		List<CollectsInfo> list = new ArrayList<CollectsInfo>();
		List<OrderInfo> orderList = new ArrayList<OrderInfo>();
		String uid = loginUser.getUid();
		List<VirtualFlowTypeEnum> types = new ArrayList<VirtualFlowTypeEnum>();
	    List<VirtualFlowEntity> data = null;
		try {
//			uci = gerneralMethod.getUserCardInfo(uid);
			if(uci.getLevel() != 0){
	       			switch(uci.getLevel()){
	       				case 1 :
	       					loginUser.setLevel("体验卡");
	       					break;
	       				case 2 :
	       					loginUser.setLevel("逍遥卡");
	       					break;
	       				case 3 :
	       					loginUser.setLevel("甜蜜卡");
	       					break;
	       				case 4 :
	       					loginUser.setLevel("欢聚卡");
	       					break;
	       				case 5 :
	       					loginUser.setLevel("云游卡");
	       					break;
	       				
	       			}
	       		}
			
			//7天内是否有收藏记录
			list = gerneralMethod.getCollects(uid);
			for (CollectsInfo collectsInfo : list) {
				String operTime = DateUtils.dateFormatter(collectsInfo.getOperTime(),"yyyy-MM-dd HH:mm:ss","yyyyMMdd");
				if(DateUtils.checkByDays(operTime,-7)){
					model.addAttribute("isCollect", 1);
					break;
				}
			}
			
			//7天内是否有订单记录
			orderList = gerneralMethod.getOrders(uid);
			for (OrderInfo orderInfo : orderList) {
				String operTime = DateUtils.dateFormatter(orderInfo.getOperTime(),"yyyy-MM-dd HH:mm:ss","yyyyMMdd");
				if(DateUtils.checkByDays(operTime,-7)){
					model.addAttribute("isOrder", 1);
					break;
				}
			}
			
			//7天内是否有积分记录
			VirtualFlowQueryRequest virtualFlowQueryRequest =  new VirtualFlowQueryRequest();
            virtualFlowQueryRequest.setMemberID(uid);
            types.add(VirtualFlowTypeEnum.VIRTUAL_FREEZED);
    		types.add(VirtualFlowTypeEnum.VIRTUAL_PAY);
    		types.add(VirtualFlowTypeEnum.VIRTUAL_UNFREEZED);
    		types.add(VirtualFlowTypeEnum.VIRTUAL_REFUND);
    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_ONLINE);
    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_OFFLINE);
    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_REWARD);
	   		virtualFlowQueryRequest.setType(types);
	   		VirtualFlowQueryResponse vfqs = gerneralMethod.virtualFlowQuery(virtualFlowQueryRequest);
	   		data = vfqs.getData();
	   		for(VirtualFlowEntity v : data){
	   			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	   			String operTime = simpleDateFormat.format(v.getCreateTime());
				if(DateUtils.checkByDays(operTime,-7)){
					model.addAttribute("isRecharge", 1);
					break;
				}
	   		}
			
		} catch (Exception e) {
			logger.error("个人资料异常",e);
		}
		model.addAttribute("userInfo", loginUser);
		model.addAttribute("userCardInfo", uci);
		model.addAttribute("imgurl", imgurl);
		return Contants.URL_PREFIX + "/center_login_new";
    }
    //余额
    @RequestMapping(value = "/my/wallet_new.htm", method = { RequestMethod.GET })
    public String toWallet(HttpServletRequest request, HttpServletResponse response,Model model) {
    	model.addAttribute("wallet", 258);
    	return Contants.URL_PREFIX + "/wallet_new";
    }
}
