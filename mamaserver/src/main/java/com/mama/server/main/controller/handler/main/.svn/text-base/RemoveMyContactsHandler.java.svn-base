package com.mama.server.main.controller.handler.main; 

import java.util.HashMap;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ContactsPo;

/** 
 * @author  zfm
 * @date 创建时间：2016年6月22日 下午7:02:56
 */
@Component
public class RemoveMyContactsHandler extends BaseHandler{

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
            
            String uid = (String)param.get("uid");
            int contactsId = Integer.valueOf(param.get("contactsId").toString());
            ContactsPo contactsPo = new ContactsPo();
            contactsPo.setUid(uid);
            contactsPo.setContactsId(contactsId);
			if (mainService.removeMyContacts(contactsPo) != 0) {
				 genErrOutputMapWithCode("fail to remove contact", ReturnCode.REMOVE_CONTACT_ERROR);
			} else {
				genSuccOutputMap();

			}
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
 