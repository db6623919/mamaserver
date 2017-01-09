package com.mmzb.house.web.action;

import java.io.IOException;
import java.net.URLEncoder;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.entity.ChargeEntity;
import com.mmzb.charge.facade.entity.ChargeQueryRequest;
import com.mmzb.charge.facade.entity.ChargeQueryResponse;
import com.mmzb.charge.facade.entity.OfflineChargeQueryResponse;
import com.mmzb.charge.facade.entity.VirtualFlowEntity;
import com.mmzb.charge.facade.entity.VirtualFlowQueryRequest;
import com.mmzb.charge.facade.entity.VirtualFlowQueryResponse;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.FlowInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.DateUtils;
import com.mmzb.house.web.action.util.StringUtils;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;

import net.sf.json.JSONArray;

@Controller
public class PersonAction extends BaseAction {
    
	@Resource(name="appProperties")
	private AppProperties appProperties;
    private static Logger logger = LoggerFactory.getLogger(PersonAction.class);
    
    @Autowired
    public GerneralMethod gerneralMethod;
    
    @RequestMapping(value = "/my/personalData.htm", method = { RequestMethod.GET })
    public ModelAndView personalData(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData=new HashMap<String, Object>();
		postData.put("uid", loginUser.getUid());
		Map<String, Integer> phoneInfo = new HashMap<String, Integer>();
		try {
			JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getCardInfo");
			if("0".equals(result.getString("code"))){
	       		JSONArray array = JSONArray.fromObject(result.get("data")); 
	       		for(int i = 0; i < array.size(); i++){
	       			net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
	       			switch(jsonObject.getInt("level")){
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
       		}
			phoneInfo = gerneralMethod.getPhoneInfo(loginUser.getPhone());
		} catch (Exception e) {
			logger.error("个人资料异常",e);
		}
		map.put("userInfo", loginUser);
		map.put("phoneInfo", phoneInfo);
		RestResponse restP = new RestResponse();
		restP.setData(map);
		return new ModelAndView(Contants.URL_PREFIX + "/person/personal_data", "response",restP);
    }
    
    @RequestMapping(value = "/my/changeNickName", method = { RequestMethod.GET })
    public ModelAndView changeNickName(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
    	model.addAttribute("userInfo", loginUser);
   		return new ModelAndView(Contants.URL_PREFIX + "/person/changeNickName");
    }
    
    
    @RequestMapping(value = "/my/changeEmail", method = { RequestMethod.GET })
    public ModelAndView changeEmail(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
    		model.addAttribute("userInfo", loginUser);
   		return new ModelAndView(Contants.URL_PREFIX + "/person/changeEmail");
    }
    
    @RequestMapping(value = "/my/changePassword", method = { RequestMethod.GET })
    public ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
    		model.addAttribute("userInfo", loginUser);   		
    	return new ModelAndView(Contants.URL_PREFIX + "/person/changePassword");
    }
    
    
    @RequestMapping(value = "/my/scoreRecord", method = { RequestMethod.GET })
    public ModelAndView scoreRecord(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
    	Map<String, Object> map = new HashMap<String, Object>();
    	RestResponse restP = new RestResponse();
    	String type = request.getParameter("type");
    	List<VirtualFlowEntity> data = null;
    	List<FlowInfo> flowInfoList = new ArrayList<FlowInfo>();
    	List<VirtualFlowTypeEnum> types = new ArrayList<VirtualFlowTypeEnum>();
    	try{
    		VirtualFlowQueryRequest vfqr = new VirtualFlowQueryRequest();
    		vfqr.setMemberID(loginUser.getUid());
    		if("1".equals(type)){
    			types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_REWARD);
    			vfqr.setType(types);
	    	}else if("2".equals(type)){
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_FREEZED);
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_PAY);
	    		vfqr.setType(types);
	    	}else{
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_FREEZED);
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_PAY);
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_UNFREEZED);
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_REFUND);
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_ONLINE);
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_OFFLINE);
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_REWARD);
	    		vfqr.setType(types);
	    	}
    		VirtualFlowQueryResponse vfqs = gerneralMethod.virtualFlowQuery(vfqr);
    		data = vfqs.getData();
    		for(VirtualFlowEntity v : data){
    			FlowInfo f = new FlowInfo();
    			f.setAmt(Integer.parseInt(v.getMoney()));
    			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			f.setOperTime(simpleDateFormat.format(v.getCreateTime()));
    			f.setShowDetail(v.getRemark());
    			f.setType(v.getType());
    			flowInfoList.add(f);
    		}
    		map.put("flowInfoList", flowInfoList);
    		map.put("type", type==null?"0":type);
    		restP.setData(map);
    	}catch(Exception e){
    		logger.info("查询积分异常",e);
    	}
    	
   		return new ModelAndView(Contants.URL_PREFIX + "/person/scoreRecord", "response",restP);
    }
    
    
    //继续加载积分记录
    @RequestMapping(value = "/my/getScoreRecord.htm", method = { RequestMethod.GET })
    public void getOrdersJson(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
    	net.sf.json.JSONObject resJson = new net.sf.json.JSONObject();
    	String type = request.getParameter("type");
    	List<FlowInfo> flowInfoList = new ArrayList<FlowInfo>();
    	try {
	    	List<Integer> list = new ArrayList<Integer>();
	    	if("1".equals(type)){
	    		list.add(200);
	    	}else if("2".equals(type)){
	    		list.add(300);
	    		list.add(400);
	    	}else if("3".equals(type)){
	    		list.add(100);
	    	}
	    	flowInfoList = gerneralMethod.getFlowByPage(loginUser.getUid(), Integer.parseInt(request.getParameter("page")), 10, list);
	    	for(FlowInfo f :flowInfoList ){
	    		f.setShowDetail(URLEncoder.encode(f.getShowDetail(),"utf-8"));
	    	}
	    	if(flowInfoList.size()!=0){
	    		resJson.put("code", 0);
	    	}else{
	    		resJson.put("code", 1);
	    	}
		} catch (Exception e) {
			logger.error("错误日志", e);
		}finally{
				resJson.put("flowInfoList", flowInfoList);
			 	response.setContentType("text/html,charset=UTF-8");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				try {
					response.getWriter().write(resJson.toString());
					response.getWriter().flush();
				} catch (IOException e) {
					logger.error("错误日志", e);
				}
		}
    	
    	
    }
    
    @RequestMapping(value = "/my/recharge", method = { RequestMethod.GET })
    public ModelAndView recharge(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
    	RestResponse restP = new RestResponse();
    	List<FlowInfo> flowInfoList = new ArrayList<FlowInfo>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<VirtualFlowTypeEnum> types = new ArrayList<VirtualFlowTypeEnum>();
    	List<VirtualFlowEntity> data = null;
    	try{
    		VirtualFlowQueryRequest vfqr = new VirtualFlowQueryRequest();
    		vfqr.setMemberID(loginUser.getUid());
    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_ONLINE);
    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_OFFLINE);
    		vfqr.setType(types);
    		VirtualFlowQueryResponse vfqs = gerneralMethod.virtualFlowQuery(vfqr);
    		data = vfqs.getData();
    		int amt = 0;
    		for(VirtualFlowEntity v : data){
    			FlowInfo f = new FlowInfo();
    			int money = Integer.parseInt(v.getMoney());
    			amt += money;
    			f.setAmt(money);
    			f.setShowDetail(v.getRemark()==null?"微信充值":v.getRemark());
    			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			f.setOperTime(simpleDateFormat.format(v.getCreateTime()));
    			flowInfoList.add(f);
    		}
    		map.put("flowInfoList", flowInfoList);
	    	if(flowInfoList.size() !=0){	
	    		map.put("totalRecords", flowInfoList.size());
	   		    map.put("totalAmt", amt);
	   		}else{
	   			map.put("totalRecords", 0);
	   		    map.put("totalAmt", 0);
	   		}
	   		restP.setData(map);
    	}catch(Exception e){
    		logger.error("查询积分记录异常",e);
    	}
   		return new ModelAndView(Contants.URL_PREFIX + "/person/recharge", "response",restP);
    }
    
    
    @RequestMapping(value = "/my/confirmRealName", method = { RequestMethod.GET })
    public ModelAndView confirmRealName(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	String type=request.getParameter("type");
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
    	if("1".equals(type)){
    		UserInfo userInfo =gerneralMethod.getUserInfo(loginUser.getUid(), null,null);
    		String name = userInfo.getName();
    		if(StringUtils.isNotEmpty(name))
    		name = name.substring(0,1)+"*"+name.substring(name.length()-1,name.length());
    		String idCard = userInfo.getIdCard();
    		if(StringUtils.isNotEmpty(name))
    		idCard = idCard.substring(0,4)+"*******"+idCard.substring(idCard.length()-4,idCard.length());
    		model.put("name", name);
    		model.put("idCard", idCard);
    		return new ModelAndView(Contants.URL_PREFIX + "/person/confirmRealNameSucc");
    	}
   		return new ModelAndView(Contants.URL_PREFIX + "/person/confirmRealName");
    }
    
}
