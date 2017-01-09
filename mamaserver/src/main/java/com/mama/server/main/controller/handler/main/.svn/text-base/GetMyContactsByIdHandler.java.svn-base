package com.mama.server.main.controller.handler.main; 

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ContactsPo;

/** 
 * @author  zfm
 * @date 创建时间：2016年6月22日 下午5:39:29
 */
@Component
public class GetMyContactsByIdHandler extends BaseHandler{

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			if (param.get("uid") == null) {
				genErrOutputMapWithCode("param error, uid required",ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			if (param.get("contactsId") == null) {
				genErrOutputMapWithCode("param error, contactsId required",ReturnCode.PARAM_ERROR);
				return outputMap;
			}

			String uid = (String) param.get("uid");
			int contactsId = Integer.valueOf(param.get("contactsId").toString());

			ContactsPo contactsPo = new ContactsPo();
			contactsPo.setContactsId(contactsId);
			contactsPo.setUid(uid);
			contactsPo = mainService.getMyContactsAllParam(contactsPo);

			dataMap.put("uid", contactsPo.getUid());
			dataMap.put("contactId", contactsPo.getContactsId());
			dataMap.put("contactName", contactsPo.getName());
			dataMap.put("contactPhone", contactsPo.getPhone());
			genSuccOutputMap();

		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
 