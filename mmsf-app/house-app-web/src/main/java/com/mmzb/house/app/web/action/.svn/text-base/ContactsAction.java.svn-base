package com.mmzb.house.app.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.RestResponse;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.entity.ContactsEntity;
import com.mmzb.house.app.entity.ContactsPagerEntity;
import com.mmzb.house.app.vo.ContactsPagerVo;
import com.mmzb.house.app.vo.ContactsVo;
import com.mmzb.house.app.vo.Page;
import com.mmzb.house.app.vo.UserInfoVo;
import com.mmzb.house.app.integration.MemberTokenService;

/**
 * 入住人接口
 * @author dengbiao
 *
 */
@Controller
public class ContactsAction {
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	private Logger logger = LoggerFactory.getLogger(IndexAction.class);
		
	@Autowired
	private MemberTokenService memberTokenService;
	
    /**
     * 查询入住人列表接口
     * @return 
     */
    @RequestMapping(value = "/app/my/checkInPersonList" , method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResponseOut<ContactsPagerVo> checkInPersonList(HttpServletRequest request,HttpServletResponse resp)throws Exception{
    	Map<String, Object> postData = new HashMap<String, Object>();
    	String name = request.getParameter("name");
    	String mobile = request.getParameter("mobile");
    	
    	//用户信息
    	UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
    	
    	String pageIndex = request.getParameter("pageIndex");//当前页数   必选
    	String pageSize = request.getParameter("pageSize");//每页条数    不传后台默认设置一个值，比如：10
    	
    	String sort = request.getParameter("sort");//排序方式
    	
    	RestResponse restP = new RestResponse();
    	Map<String, Object> map = new HashMap<String, Object>();
    	ContactsPagerVo contactsPagerVo = new ContactsPagerVo();
    	try{
    		
    		postData.put("uid", userInfo.getMemberId());
//    		postData.put("uid", "100002680014");
    		List<ContactsVo> contactsList = new ArrayList<ContactsVo>();
    		Response<ContactsPagerEntity> rsp = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getMyContacts", ContactsPagerEntity.class);
    		
			String msg = rsp.getMsg();
			String code = rsp.getCode();
			int num = 0 ;

			if (rsp != null && "0".equals(rsp.getCode())) {
				//获取联系人列表信息
				ContactsPagerEntity contactsPagerEntity = rsp.getData();
				List<ContactsEntity> contactsInfoList = null;
				int itemCount = 0;
				if (contactsPagerEntity != null) {
					itemCount = contactsPagerEntity.getNum();
					contactsInfoList = contactsPagerEntity.getContacts();
				}
				
				List<ContactsVo> contacts = null;
				if (contactsInfoList != null) {
					contacts = convert2ContactsVo(contactsInfoList);
					contactsPagerVo.setCheckInPersonList(contacts);
				}
				
				if (logger.isDebugEnabled()) {
					logger.debug("get the contact info:{}", contacts);
				}
				
    			//设置分页信息
				Page pager = new Page();
				pager.setPageIndex(Integer.parseInt(pageIndex));
				pager.setPageSize(Integer.parseInt(pageSize));
    			pager.setItemCount(itemCount);
    			contactsPagerVo.setPager(pager);
			}
			
    	}catch(Exception e){
			logger.error("/app/checkInPersonList:查询联系人列表调用失败.",e);
			return new ResponseOut<ContactsPagerVo>(Constants.APP_FAILED,"查询联系人列表调用失败!",null);
    	}
    	return new ResponseOut<ContactsPagerVo>(Constants.APP_SUCCESSED,"查询联系人列表成功！",contactsPagerVo);
    	
    }
    
    /**
     * 添加入住人接口
     * @return 
     */
    @RequestMapping(value = "/app/my/addCheckInPerson" , method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResponseOut<ContactsVo> addCheckInPerson(HttpServletRequest request,HttpServletResponse resp)throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String name = request.getParameter("name");
		try {
			name= URLDecoder.decode(name,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
    	String mobile = request.getParameter("mobile");
    	
    	//用户信息
    	UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
    	
    	RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMemberId());
		postData.put("contactsName", name);
		postData.put("contactsPhone", mobile);
		ContactsVo contactsVo = new ContactsVo();
		try {
			Response<ContactsPagerEntity> results = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getIsExistMyContacts", null);
			if ("0".equals(results.getCode())) {
				Response result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "insertMyContacts", null);
				
				if ("0".equals(result.getCode())) {
					net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result.getData());
					String id = jsonObject.getString("contactsId").toString();
					contactsVo.setId(id);
					contactsVo.setCurrentDefault(false);
					contactsVo.setMobile(mobile);
					contactsVo.setName(name);

				}
			}else {
				logger.error("错误日志:添加入住人接口 addCheckInPerson异常：", "入住人已存在!");
				return new ResponseOut<ContactsVo>(Constants.APP_FAILED,"入住人已存在!",null);
			} 

		} catch (IOException e) {
			logger.error("错误日志:添加入住人接口 addCheckInPerson异常：", e);
			return new ResponseOut<ContactsVo>(Constants.APP_FAILED,"添加入住人调用失败!",null);
		}
		return new ResponseOut<ContactsVo>(Constants.APP_SUCCESSED,"添加入住人接口成功！",contactsVo);  	
    }
	
    /**
     * 删除入住人接口
     * @return 
     */
    @RequestMapping(value = "/app/my/deleteCheckInPerson" , method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResponseOut<String> deleteCheckInPerson(HttpServletRequest request,HttpServletResponse resp)throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
        String contactsId = request.getParameter("checkInPersonId");
        
        //用户信息
        UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
    	
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMemberId());
		postData.put("contactsId", contactsId);
		try {
			Response result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "removeMyContacts", null);
			if("0".equals(result.getCode())) {
				logger.info("deleteCheckInPerson：删除入住人成功!");
	       		return new ResponseOut<String>(Constants.APP_SUCCESSED,"删除入住人成功！","");
			}else {
				logger.error("错误日志deleteCheckInPerson：系统繁忙，删除入住人失败!");
				return new ResponseOut<String>(Constants.APP_FAILED,"系统繁忙，删除入住人失败!","");
			}
		}catch (Exception e) {
			logger.error("错误日志deleteCheckInPerson：", e);
			return new ResponseOut<String>(Constants.APP_FAILED,"系统繁忙，删除入住人失败!","");
		}
    }
    
    /**
     * 修改入住人接口
     * @return 
     */
    @RequestMapping(value = "/app/my/updateCheckInPerson" , method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResponseOut<String> updateCheckInPerson(HttpServletRequest request,HttpServletResponse resp)throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
        String contactsId = request.getParameter("checkInPersonId");
    	
        //用户信息
        UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
    	
    	RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMemberId());
		postData.put("contactsId", contactsId);
		String contactsName = request.getParameter("name");
		String contactsPhone = request.getParameter("mobile");
		postData.put("contactsName", contactsName);
		postData.put("contactsPhone", contactsPhone);
		postData.put("contactsId", contactsId);
		
		try {
			Response result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "modifyMyContacts", null);
       		if ("0".equals(result.getCode())) {
       			logger.info("修改入住人成功!");
				return new ResponseOut<String>(Constants.APP_SUCCESSED,"修改入住人成功!","");
			}  else {
				logger.error("系统繁忙，请稍后重试!");
				return new ResponseOut<String>(Constants.APP_FAILED,"系统繁忙，请稍后重试,修改入住人失败!","");
			}
		} catch (IOException e) {
			logger.error("修改入住人异常：!",e);
			return new ResponseOut<String>(Constants.APP_FAILED,"系统繁忙，请稍后重试,修改入住人失败!","");
		}
    }
    
	private List<ContactsVo> convert2ContactsVo(List<ContactsEntity> contactsInfoList){
		if (CollectionUtils.isEmpty(contactsInfoList)) 
		{
			return null;
		}

		List<ContactsVo> contactsVos = new ArrayList<ContactsVo>();
		for(ContactsEntity entity : contactsInfoList)
		{
			ContactsVo contactsVo = new ContactsVo();
			contactsVo.setId(String.valueOf(entity.getContactsId()));
			contactsVo.setMobile(entity.getContactsPhone());
			contactsVo.setName(entity.getContactsName());
			contactsVo.setCurrentDefault(false);
			contactsVos.add(contactsVo);
		}
		
		return contactsVos;
	}
	
}
