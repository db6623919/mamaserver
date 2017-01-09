package com.mama.server.main.controller.handler.main;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.ContactPo;

@Component
public class GetContactsHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("uid") == null) {
                genErrOutputMapWithCode("param error, uid required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int pageNum = -1;
            int pageCount = -1;
            if (param.get("pageNum") != null && param.get("pageCount") != null) {
                pageNum = (Integer)param.get("pageNum");
                pageCount = (Integer)param.get("pageCount");
                
                if (pageNum <= 0 || pageCount <= 0) {
                    genErrOutputMapWithCode("param error, invalid pageNum/pageCount", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
            }
            
            String uid = (String)param.get("uid");
            ContactPo contact = new ContactPo();
            contact.setUid(uid);
            List<ContactPo> contactList = mainService.getContactByUid(contact);
            if (contactList != null) {
                ArrayList<HashMap<String, Object>> contactMapList = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < contactList.size(); ++i) {
                    if (contactList.get(i).getRemoved() == 1) {
                        continue;
                    }
                    HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                    tmpMap.put("contactId", contactList.get(i).getContactId());
                    tmpMap.put("contactName", contactList.get(i).getName());
                    tmpMap.put("contactIdCard", contactList.get(i).getIdCard());
                    tmpMap.put("contactPhone", contactList.get(i).getPhone());
                    contactMapList.add(tmpMap);
                }
                
                if (pageNum == -1 || pageCount == -1) {
                    dataMap.put("contacts", contactMapList);
                    dataMap.put("num", contactMapList.size());
                    genSuccOutputMap();
                    return outputMap;
                }
                
                ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
                int startNum = (pageNum - 1) * pageCount;
                int endNum = startNum + pageCount;
                if (endNum > contactMapList.size()) {
                    endNum = contactMapList.size();
                }
                for (int i = startNum; i < endNum; ++i) {
                    pageList.add(contactMapList.get(i));
                }
                dataMap.put("contacts", pageList);
                dataMap.put("num", pageList.size());
                
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
