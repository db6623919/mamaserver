package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ContactPo;

@Component
public class RemoveContactHandler extends BaseHandler {

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
            ContactPo contact = new ContactPo();
            contact.setUid(uid);
            contact.setContactId(contactId);
            List<ContactPo> contactList = mainService.getContactByUid(contact);
            if (contactList == null || contactList.size() == 0) {
                genErrOutputMapWithCode("invalid contact id", ReturnCode.PARAM_ERROR);
                return outputMap;
            } else {
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
                if (mainService.removeContact(contact) != 0) {
                    genErrOutputMapWithCode("fail to remove contact", ReturnCode.REMOVE_CONTACT_ERROR);
                } else {
                    genSuccOutputMap();
                }
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
