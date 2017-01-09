package com.mama.server.main.controller.handler.main;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ContactPo;

@Component
public class GetContactByIdHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("contactId") == null) {
                genErrOutputMapWithCode("param error, contactId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String uid = (String)param.get("uid");
            int contactId = (Integer)param.get("contactId");
            
            if (contactId > 0) {
                ContactPo contact = new ContactPo();
                contact.setUid(uid);
                List<ContactPo> contactList = mainService.getContactByUid(contact);
                if (contactList == null) {
                    genErrOutputMapWithCode("invalid contact id", ReturnCode.PARAM_ERROR);
                    return outputMap;
                } 
                contact = null;
                for (int i = 0; i < contactList.size(); ++i) {
                    if (contactList.get(i).getContactId() == contactId) {
                        contact = contactList.get(i);
                        break;
                    }
                }
                if (contact == null) {
                    genErrOutputMapWithCode("invalid contact id", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
                dataMap.put("uid", contact.getUid());
                dataMap.put("contactId", contact.getContactId());
                dataMap.put("contactName", contact.getName());
                dataMap.put("contactIdCard", contact.getIdCard());
                dataMap.put("contactPhone", contact.getPhone());
                genSuccOutputMap();
            } else {
                genErrOutputMapWithCode("invalid contact id", ReturnCode.PARAM_ERROR);
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
