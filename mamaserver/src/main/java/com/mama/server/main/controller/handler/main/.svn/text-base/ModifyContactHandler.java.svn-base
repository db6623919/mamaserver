package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ContactPo;

@Component
public class ModifyContactHandler extends BaseHandler {

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
            if (param.get("contactName") == null) {
                genErrOutputMapWithCode("param error, contactName required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("contactIdCard") == null) {
                genErrOutputMapWithCode("param error, contactIdCard required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("contactPhone") == null) {
                genErrOutputMapWithCode("param error, contactPhone required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            String uid = (String)param.get("uid");
            int contactId = (Integer)param.get("contactId");
            String contactName = (String)param.get("contactName");
            String contactIdCard = (String)param.get("contactIdCard");
            String contactPhone = (String)param.get("contactPhone");
            
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
                contact.setIdCard(contactIdCard);
                contact.setName(contactName);
                contact.setPhone(contactPhone);
                if (mainService.updateContact(contact) != 0) {
                    genErrOutputMapWithCode("fail to update contact", ReturnCode.UPDATE_CONTACT_ERROR);
                    return outputMap;
                }
                genSuccOutputMap();
            } else if (contactId == 0) {
                ContactPo contact = new ContactPo();
                contact.setIdCard(contactIdCard);
                contact.setName(contactName);
                contact.setPhone(contactPhone);
                contact.setUid(uid);
                contact.setShowDetail("");
                if (mainService.insertContact(contact) != 0) {
                    genErrOutputMapWithCode("fail to add contact", ReturnCode.ADD_CONTACT_ERROR);
                } else {
                    genSuccOutputMap();
                }
            } else {
                genErrOutputMapWithCode("invalid contact id", ReturnCode.PARAM_ERROR);
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
