package com.mama.server.main.controller.handler.main; 

import java.util.Date;
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
public class InsertMyContactsHandler extends BaseHandler{

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
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
            String contactsName = (String) param.get("contactsName");
            String contactsPhone = (String) param.get("contactsPhone");
        
			ContactsPo contactsPo = new ContactsPo();
			contactsPo.setName(contactsName);
			contactsPo.setPhone(contactsPhone);
			contactsPo.setOperTime(new Date());
			contactsPo.setUid(uid);
			if (mainService.insertMyContacts(contactsPo) != 0) {
				genErrOutputMapWithCode("fail to add contacts", ReturnCode.ADD_CONTACT_ERROR);
			} else {
				dataMap.put("contactsId", contactsPo.getId());
				genSuccOutputMap();
			} 
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
 