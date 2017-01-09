package com.mama.server.main.controller.handler.main; 

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ContactsPo;

/** 
 * @author  zfm
 * @date 创建时间：2016年6月22日 下午3:52:25
 */
@Component
public class ModifyMyContactsHandler extends BaseHandler{

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
		    if (param.get("contactsId") == null) {
                genErrOutputMapWithCode("param error, contactsId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("contactsName") == null) {
                genErrOutputMapWithCode("param error, contactsName required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("contactsPhone") == null) {
                genErrOutputMapWithCode("param error, contactsPhone required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String uid = (String) param.get("uid");
            int contactsId = Integer.valueOf(param.get("contactsId").toString());
            String contactsName = (String) param.get("contactsName");
            String contactsPhone = (String) param.get("contactsPhone");
            
        	ContactsPo contactsPo = new ContactsPo();
        	contactsPo.setUid(uid);
        	contactsPo.setContactsId(contactsId);
        	contactsPo.setName(contactsName);
        	contactsPo.setPhone(contactsPhone);
        	if (mainService.updateMyContacts(contactsPo) != 0) {
        		genErrOutputMapWithCode("fail to update contacts", ReturnCode.UPDATE_CONTACT_ERROR);
                return outputMap;
			}
        	genSuccOutputMap();
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
 