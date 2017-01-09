package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.CollectPo;
import com.mama.server.main.dao.model.ContactPo;
import com.mama.server.main.dao.model.ContactsPo;
import com.mama.server.main.dao.model.UserCardPo;
import com.mama.server.main.dao.model.UserInfoPo;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface UserDao extends GenericDao<UserInfoPo> {
	UserInfoPo getUserInfoByAllParam(UserInfoPo uip);
	List<UserInfoPo> getAll();
	int updateUserInfo(UserInfoPo uip);
	int insertContact(ContactPo cp);
	int updateContact(ContactPo cp);
	int removeContact(ContactPo cp);
	List<ContactPo> getContactByUid(ContactPo cp);
	List<ContactPo> getContactByAllParam(ContactPo cp);
	int insertUserCard(UserCardPo ucp);
	int updateUserCard(UserCardPo ucp);
	List<UserCardPo> getUserCardByUid(UserCardPo ucp);
	int insertCollect(CollectPo cp);
	List<CollectPo> getCollectByAllParam(CollectPo cp);
	int removeCollect(CollectPo cp);
	String getUidByPhone(String friendCode);
	String getPhoneByUid(String uid);
	int getInvitedNumberByPhone(String phone);
	
	int insertMyContacts(ContactsPo contactsPo);
	int updateMyContacts(ContactsPo contactsPo);
	int removeMyContacts(ContactsPo contactsPo);
	List<ContactsPo> getMyContactsByUid(ContactsPo contactsPo);
//	List<ContactsPo> getMyContactsByUid(Map map);
	ContactsPo getMyContactsByAllParam(ContactsPo contactsPo);
	List<ContactsPo> getMyContactsByNameOrPhone(ContactsPo contactsPo);
	
}