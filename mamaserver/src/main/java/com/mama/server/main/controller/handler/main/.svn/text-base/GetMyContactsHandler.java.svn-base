 package com.mama.server.main.controller.handler.main; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ContactsPo;
 
/** 
 * @author  zfm
 * @date 创建时间：2016年6月21日 下午7:49:19
 */
@Component
public class GetMyContactsHandler extends BaseHandler{

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			if (param.get("uid") == null) {
				genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
			}			
			
			String uid = (String) param.get("uid");
			ContactsPo contactsPo = new ContactsPo();
			contactsPo.setUid(uid);
			List<ContactsPo> contactsPoList = mainService.getMyContactsByUid(contactsPo);
			if (contactsPoList != null) {
				ArrayList<HashMap<String, Object>> contactsMapList = new ArrayList<HashMap<String,Object>>();
				for (int i = 0; i < contactsPoList.size(); i++) {
					if (contactsPoList.get(i).getRemoved() == 1) {
						continue;
					}
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("contactsId", contactsPoList.get(i).getContactsId());
					map.put("contactsName", contactsPoList.get(i).getName());
					map.put("contactsPhone", contactsPoList.get(i).getPhone());
					contactsMapList.add(map);
				}
				
				dataMap.put("contacts",contactsMapList);
                dataMap.put("num",contactsPoList.size());
                
                genSuccOutputMap();
			} else {
				genErrOutputMapWithCode("fail to get contacts", ReturnCode.GET_CONTACT_ERROR);

			}
			
		} catch (Exception e) {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}

}
 