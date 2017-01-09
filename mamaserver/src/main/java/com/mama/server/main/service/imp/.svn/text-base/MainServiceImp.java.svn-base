package com.mama.server.main.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.server.common.entity.RecommodHouse;
import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.dao.ActivityDao;
import com.mama.server.main.dao.ActivityEnrollDao;
import com.mama.server.main.dao.AppVersionDao;
import com.mama.server.main.dao.CollectDao;
import com.mama.server.main.dao.CustomerDao;
import com.mama.server.main.dao.FlowDao;
import com.mama.server.main.dao.HotelCouponDao;
import com.mama.server.main.dao.HouseCardDao;
import com.mama.server.main.dao.HouseDao;
import com.mama.server.main.dao.HouseShopDao;
import com.mama.server.main.dao.HouseTagDao;
import com.mama.server.main.dao.OrderDao;
import com.mama.server.main.dao.OrderPayDAO;
import com.mama.server.main.dao.OrderPayDO;
import com.mama.server.main.dao.TopicActivityDao;
import com.mama.server.main.dao.TradeAreaDao;
import com.mama.server.main.dao.UserDao;
import com.mama.server.main.dao.UtilDao;
import com.mama.server.main.dao.VersionDao;
import com.mama.server.main.dao.clickfarming.CFHouseShopDao;
import com.mama.server.main.dao.model.ActivityConfPo;
import com.mama.server.main.dao.model.ActivityEnrollPo;
import com.mama.server.main.dao.model.ActivityMemberRecordPo;
import com.mama.server.main.dao.model.AppVersionPo;
import com.mama.server.main.dao.model.BuildingPo;
import com.mama.server.main.dao.model.CityPo;
import com.mama.server.main.dao.model.CollectPo;
import com.mama.server.main.dao.model.ContactPo;
import com.mama.server.main.dao.model.ContactsPo;
import com.mama.server.main.dao.model.CustomerUserPo;
import com.mama.server.main.dao.model.DeveloperPo;
import com.mama.server.main.dao.model.ExchangePo;
import com.mama.server.main.dao.model.ExchangeRequestPo;
import com.mama.server.main.dao.model.FlowPo;
import com.mama.server.main.dao.model.HotelCouponGivePo;
import com.mama.server.main.dao.model.HotelCouponPo;
import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.HousePricePo;
import com.mama.server.main.dao.model.HouseShopPo;
import com.mama.server.main.dao.model.HouseTag;
import com.mama.server.main.dao.model.OpenIdInfoPo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.ProvincePo;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.main.dao.model.TopicActivityPo;
import com.mama.server.main.dao.model.TopicShopPo;
import com.mama.server.main.dao.model.TradeArea;
import com.mama.server.main.dao.model.UserCardPo;
import com.mama.server.main.dao.model.UserInfoPo;
import com.mama.server.main.dao.model.Version;
import com.mama.server.main.dao.model.clickfarming.CFHouseShopPo;
import com.mama.server.main.dao.vo.HotelCouponGroupVo;
import com.mama.server.main.dao.vo.OrderPriceInfoVo;
import com.mama.server.main.service.MainService;
import com.mama.server.util.Log;
import com.mama.server.util.dao.PaginationInterceptor;
import com.mama.server.util.dao.PaginationInterceptor.Page;
import com.meidusa.fastjson.JSONArray;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.domain.domain.PayDomain;
import com.netfinworks.common.domain.OperationEnvironment;
import com.netfinworks.ma.service.facade.IMemberFacade;
import com.netfinworks.ma.service.request.PersonalMemberQueryRequest;
import com.netfinworks.ma.service.response.PersonalMemberInfo;
import com.netfinworks.ma.service.response.PersonalMemberResponse;

@Service
public class MainServiceImp implements MainService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UtilDao utilDao;

	@Autowired
	private HouseDao houseDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private FlowDao flowDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private HotelCouponDao hotelCouponDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private CollectDao collectDao;
	
	@Resource(name = "memberFacade")
	private IMemberFacade memberFacade;
	
	@Autowired
	private OrderPayDAO orderPayDAO;
	
	@Autowired
	private TradeAreaDao tradeAreaDao;
	
	@Autowired
	private HouseTagDao houseTagDao;
	
	@Autowired
	private HouseShopDao houseShopDao;
	
	@Autowired
	private ActivityEnrollDao activityEnrollDao;
	
	@Autowired
	private VersionDao versionDao;
	
	@Autowired
	private CFHouseShopDao cfHouseShopDao;
	
	@Autowired
	private TopicActivityDao topicActivityDao;
	
	@Autowired
	private AppVersionDao appVersionDao;
	
	@Autowired
	private HouseCardDao houseCardDao;

	/*@Override
	public int insertUserInfo(UserInfoPo uip) {
		try {
			// TODO 在UserMapper.xml中save对应的SQL中添加friendCode这个字段
			userDao.save(uip);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[userRegister]Exception:", e);
			return -1;
		}
		return 0;
	}*/
	@Override
	public int insertUserInfo(UserInfoPo uip) {
		try {
			// TODO 在UserMapper.xml中save对应的SQL中添加friendCode这个字段
			throw new RuntimeException("insertUserInfo");
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[userRegister]Exception:", e);
			return -1;
		}
	}

	/*@Override
	public int updateUserInfo(UserInfoPo uip) {
		try {
			userDao.updateUserInfo(uip);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateUserInfo]Exception:", e);
			return -1;
		}
		return 0;
	}*/
	@Override
	public int updateUserInfo(UserInfoPo uip) {
		try {
			throw new RuntimeException("updateUserInfo");
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateUserInfo]Exception:", e);
			return -1;
		}
	}

	/*@Override
	public UserInfoPo getUserinfoByAllParam(UserInfoPo uip) {
		UserInfoPo uipRet = new UserInfoPo();
		try {
			uipRet = userDao.getUserInfoByAllParam(uip);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getUserInfoByAllParam]Exception:", e);
			return null;
		}
		return uipRet;
	}*/
	@Override
	public UserInfoPo getUserinfoByAllParam(UserInfoPo uip) {
		if(uip==null){
			Log.SERVICE.info("getUserinfoByAllParam[ uip is null ]", uip);
			throw new RuntimeException("updateUserInfo");
		}
		if(StringUtils.isEmpty(uip.getUid())){
			Log.SERVICE.info("getUserinfoByAllParam[ uid is null ]", uip);
			throw new RuntimeException("updateUserInfo");
		}
		try {
			PersonalMemberInfo pm = getPersonalMemberInfo(uip.getUid());
			uip.setNickName(pm.getRealName());
			uip.setName(pm.getRealName());
			uip.setMmWalletId(uip.getUid());
			uip.setRemoved(1);
			uip.setType(1);
			uip.setChannel(0);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getUserInfoByAllParam]Exception:", e);
			return null;
		}
		return uip;
	}

	/*@Override
	public List<UserInfoPo> getAllUserInfo() {
		List<UserInfoPo> uipRet = new ArrayList<UserInfoPo>();
		try {
			uipRet = userDao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getUserInfoByAllParam]Exception:", e);
			return null;
		}
		return uipRet;
	}*/
	@Override
	public List<UserInfoPo> getAllUserInfo() {
		List<UserInfoPo> uipRet = new ArrayList<UserInfoPo>();
		try {
			throw new RuntimeException("getAllUserInfo");
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getAllUserInfo]Exception:", e);
			return null;
		}
	}

	@Override
	public int insSms(SmsPo sp) {
		try {
			utilDao.insSms(sp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insSms]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<SmsPo> getSmsByAllParam(SmsPo sp) {
		List<SmsPo> listSms = new ArrayList<SmsPo>();
		try {
			listSms = utilDao.getSmsByAllParam(sp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getSmsByAllParam]Exception:", e);
			return null;
		}
		return listSms;
	}

	@Override
	public int updateSmsById(SmsPo sp) {
		try {
			utilDao.updateSmsById(sp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateSmsById]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int insProvince(ProvincePo pp) {
		try {
			utilDao.insProvince(pp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insProvince]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public int checkProvince(String name){
		try {
			return utilDao.checkProvince(name);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[checkProvince]Exception:", e);
			return -1;
		}
	}

	@Override
	public int updateProvinceByName(ProvincePo pro){
		try {
			return utilDao.updateProvinceByName(pro);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateProvinceByName]Exception:", e);
			return -1;
		}
	}
	
	@Override
	public List<ProvincePo> getProvinces(Map map) {
		List<ProvincePo> listProvince = new ArrayList<ProvincePo>();
		try {
			listProvince = utilDao.getProvinces(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getProvince]Exception:", e);
			return null;
		}
		return listProvince;
	}
	
	@Override
	public List<ProvincePo> getProvince() {
		List<ProvincePo> listProvince = new ArrayList<ProvincePo>();
		try {
			listProvince = utilDao.getProvince();
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getProvince]Exception:", e);
			return null;
		}
		return listProvince;
	}

	@Override
	public int insCity(CityPo pp) {
		try {
			utilDao.insCity(pp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insCity]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public CityPo checkCity(String name){
		try {
			return utilDao.checkCity(name);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[checkCity]Exception:", e);
			return null;
		}
	}
    
	@Override
	public int updateCityByName(CityPo city){
		try {
			return utilDao.updateCityByName(city);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateCityByName]Exception:", e);
			return -1;
		}
	}
	
	@Override
	public List<CityPo> getCity() {
		List<CityPo> listProvince = new ArrayList<CityPo>();
		try {
			listProvince = utilDao.getCity(null);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getCity]Exception:", e);
			return null;
		}
		return listProvince;
	}

	@Override
	public List<CityPo> getCityByPar(CityPo cp) {
		List<CityPo> listProvince = new ArrayList<CityPo>();
		try {
			listProvince = utilDao.getCityByPar(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getCityByPar]Exception:", e);
			return null;
		}
		return listProvince;
	}
		
	@Override
	public List<CityPo> getCityByName(CityPo cp) {
		List<CityPo> listProvince = new ArrayList<CityPo>();
		try {
			listProvince = utilDao.getCityByName(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getCityByName]Exception:", e);
			return null;
		}
		return listProvince;
	}
	
	@Override
	public List<CityPo> getCityByType(Map map) {
		List<CityPo> listProvince = new ArrayList<CityPo>();
		try {
			listProvince = utilDao.getCity(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getCityByType]Exception:", e);
			return null;
		}
		return listProvince;
	}

	@Override
	public int insertContact(ContactPo cp) {
		try {
			userDao.insertContact(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertContact]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateContact(ContactPo cp) {
		try {
			userDao.updateContact(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateContact]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int removeContact(ContactPo cp) {
		try {
			userDao.removeContact(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[removeContact]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int removeCollect(CollectPo cp) {
		try {
			collectDao.removeCollect(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[removeCollect]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<ContactPo> getContactByUid(ContactPo cp) {
		List<ContactPo> listContact = new ArrayList<ContactPo>();
		try {
			listContact = userDao.getContactByUid(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getContactByUid]Exception:", e);
			return null;
		}
		return listContact;
	}

	@Override
	public List<ContactPo> getContactByAllParam(ContactPo cp) {
		List<ContactPo> listContact = new ArrayList<ContactPo>();
		try {
			listContact = userDao.getContactByAllParam(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getContactByAllParam]Exception:", e);
			return null;
		}
		return listContact;
	}

	@Override
	public int insertUserCard(UserCardPo ucp) {
		try {
			userDao.insertUserCard(ucp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertUserCard]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateUserCard(UserCardPo ucp) {
		try {
			userDao.updateUserCard(ucp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateUserCard]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<UserCardPo> getUserCardByUid(UserCardPo ucp) {
		List<UserCardPo> listUserCard = new ArrayList<UserCardPo>();
		try {
			listUserCard = userDao.getUserCardByUid(ucp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getUserCardByUid]Exception:", e);
			return null;
		}
		return listUserCard;
	}

	@Override
	public int insertDeveloper(DeveloperPo dp) {
		try {
			houseDao.insertDeveloper(dp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertDeveloper]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateDeveloper(DeveloperPo dp) {
		try {
			houseDao.updateDeveloper(dp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateDeveloper]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public int getBuildingByDevId(int devId){
		try {
			return houseDao.getBuildingByDevId(devId);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getBuildingByDevId]Exception:", e);
			return -1;
		}
	}

	@Override
	public List<DeveloperPo> getDeveloperByDevId(DeveloperPo dp) {
		List<DeveloperPo> listDeveloper = new ArrayList<DeveloperPo>();
		try {
			listDeveloper = houseDao.getDeveloperByDevId(dp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getUserCardByUid]Exception:", e);
			return null;
		}
		return listDeveloper;
	}

	@Override
	public List<DeveloperPo> getDeveloper(Map map) {
		List<DeveloperPo> listDeveloper = new ArrayList<DeveloperPo>();
		try {
			listDeveloper = houseDao.getDeveloper(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getUserCardByUid]Exception:", e);
			return null;
		}
		return listDeveloper;
	}

	@Override
	public int insertBuliding(BuildingPo bp) {
		try {
			houseDao.insertBuilding(bp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertBuilding]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateBuliding(BuildingPo bp) {
		try {
			houseDao.updateBuilding(bp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateBuilding]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<BuildingPo> getBuildings(Map map) {
		List<BuildingPo> listBuilding = new ArrayList<BuildingPo>();
		try {
			listBuilding = houseDao.getBuildings(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getBuildingByCityId]Exception:", e);
			return null;
		}
		return listBuilding;
	}
	
	@Override
	public List<BuildingPo> getBuilding(BuildingPo bp) {
		List<BuildingPo> listBuilding = new ArrayList<BuildingPo>();
		try {
			listBuilding = houseDao.getBuilding(bp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getBuildingByCityId]Exception:", e);
			return null;
		}
		return listBuilding;
	}

	@Override
	public List<BuildingPo> getBuildingByCityId(BuildingPo bp) {
		List<BuildingPo> listBuilding = new ArrayList<BuildingPo>();
		try {
			listBuilding = houseDao.getBuildingByCityId(bp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getBuildingByCityId]Exception:", e);
			return null;
		}
		return listBuilding;
	}

	@Override
	public int insertHouse(HousePo hp) {
		try {
			houseDao.insertHouse(hp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertHouse]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateHouse(HousePo hp) {
		try {
			houseDao.updateHouse(hp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateHouse]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<HousePo> getHouseByAllParam(HousePo hp) {
		List<HousePo> listHouse = new ArrayList<HousePo>();
		try {
			listHouse = houseDao.getHouseByAllParam(hp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseByAllParam]Exception:", e);
			return null;
		}
		return listHouse;
	}

	@Override
	public int insertOrder(OrderPo op) {
		try {
			orderDao.insertOrder(op);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertOrder]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public int getCityByProId(CityPo city){
		int result=0;
		try {
			result=utilDao.getCityByProId(city);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[SelectCityByProvinceHandler]Exception:", e);
			return -1;
		}
		return result;
	}

	@Override
	public int updateOrder(OrderPo op) {
		try {
			orderDao.updateOrder(op);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateOrder]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public int updateHouseOrderByOrderId(HouseOrderPo ho) {
		try {
			houseDao.updateHouseOrderByOrderId(ho);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateOrder]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<OrderPo> getOrderByAllParam(OrderPo op) {
		List<OrderPo> listOrder = new ArrayList<OrderPo>();
		try {
			listOrder = orderDao.getOrderByAllParam(op);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getOrderByAllParam]Exception:", e);
			return null;
		}
		return listOrder;
	}

	@Override
	public int insertFlow(FlowPo fp) {
		try {
			flowDao.save(fp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertFlow]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<FlowPo> getFlowByAllParam(FlowPo fp) {
		List<FlowPo> listFlow = new ArrayList<FlowPo>();
		try {
			listFlow = flowDao.getFlowByAllParam(fp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getFlowByAllParam]Exception:", e);
			return null;
		}
		return listFlow;
	}

	@Override
	public int insertCollect(CollectPo cp) {
		try {
			collectDao.insertCollect(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertCollect]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<CollectPo> getCollectByAllParam(CollectPo cp) {
		List<CollectPo> listCollect = new ArrayList<CollectPo>();
		try {
			listCollect = collectDao.getCollectByAllParam(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getCollectByAllParam]Exception:", e);
			return null;
		}
		return listCollect;
	}
	
	public Page<CollectPo> getCollectByPage(CollectPo collect) {
		PaginationInterceptor.startPage(collect.getCurrent_page(), collect.getPage_size());
		collectDao.getCollectByAllParam(collect);
		Page<CollectPo> page = PaginationInterceptor.endPage();
		return page;
	}

	@Override
	public int insertHouseOrder(HouseOrderPo hop) {
		try {
			houseDao.insertHouseOrder(hop);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertHouseOrder]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateHouseOrderById(HouseOrderPo hop) {
		try {
			houseDao.updateHouseOrderById(hop);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateHouseOrderById]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<HouseOrderPo> getHouseOrderByAllParam(HouseOrderPo hop) {
		List<HouseOrderPo> listHouseOrder = new ArrayList<HouseOrderPo>();
		try {
			listHouseOrder = houseDao.getHouseOrderByAllParam(hop);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseOrderByAllParam]Exception:", e);
			return null;
		}
		return listHouseOrder;
	}

	@Override
	public int insertHousePrice(HousePricePo hp) {
		try {
			houseDao.deleteHousePrice(hp);
			houseDao.insertHousePrice(hp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertHousePrice]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateHousePriceById(HousePricePo hp) {
		try {
			houseDao.updateHousePriceById(hp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateHousePriceById]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<HousePricePo> getHousePriceByAllParam(HousePricePo hp) {
		List<HousePricePo> listHousePrice = new ArrayList<HousePricePo>();
		try {
			listHousePrice = houseDao.getHousePriceByAllParam(hp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHousePriceByAllParam]Exception:", e);
			return null;
		}
		return listHousePrice;
	}

	@Override
	public List<HousePricePo> getHousePriceByByDate(int houseId, Date startDate, Date endDate) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("houseId", houseId);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			return houseDao.getHousePriceByByDate(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHousePriceByAllParam]Exception:", e);
			return null;
		}
	}

	/****************************************
	 * CUSTOMER FUNCTION
	 ***********************************************/
	@Override
	public int cusInsertUser(CustomerUserPo cup) {
		try {
			customerDao.save(cup);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[cusInsertUser]Exception:", e);
			return -1;
		}
		return cup.getUid();
	}

	@Override
	public List<CustomerUserPo> cusGetUserinfo(CustomerUserPo cup) {
		List<CustomerUserPo> listCup = new ArrayList<CustomerUserPo>();
		try {
			listCup = customerDao.getUserInfoByAllParam(cup);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[cusGetUserinfo]Exception:", e);
			return null;
		}
		return listCup;
	}

	@Override
	public int updateCityById(CityPo cp) {
		try {
			utilDao.updateCityById(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateCityException]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateProvinceById(ProvincePo cp) {
		try {
			utilDao.updateProvinceById(cp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateProvinceException]Exception:", e);
			return -1;
		}
		return 0;
	}

	/****************************************
	 * TOOL FUNCTION
	 ***********************************************/
	/*@Override
	public String getTimeToMd5() {
		String strMd5 = "";
		UserInfoPo uip = new UserInfoPo();
		try {
			do {
				long lTime = System.currentTimeMillis();
				strMd5 = DigestUtils.md5Hex(String.valueOf(lTime));
				uip.setUid(strMd5);
				uip = userDao.getUserInfoByAllParam(uip);
			} while (uip != null);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getTimeToMd5]Exception:", e);
			return null;
		}
		return strMd5;
	}*/
	@Override
	public String getTimeToMd5() {

		throw new RuntimeException("getTimeToMd5");

	}

	@Override
	public String encodePassword(String password) {
		String strMd5 = "";
		try {
			strMd5 = DigestUtils.md5Hex(password);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[encodePassword]Exception:", e);
			return null;
		}
		return strMd5;
	}

	@Override
	public String encodePassword(String password, String stamp) {
		String strMd5 = "";
		try {
			strMd5 = DigestUtils.md5Hex(password + stamp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[encodePassword]Exception:", e);
			return null;
		}
		return strMd5;
	}

	@Override
	public List<String> genUserCard() {
		List<String> res = new ArrayList<String>();

		String base = "0123456789ABCDEF";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 12; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		res.add(sb.toString());

		base = "0123456789";
		sb = new StringBuffer();

		for (int i = 0; i < 8; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		res.add(sb.toString());

		return res;
	}

	@Override
	public String genVerifyCode(int type) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		int length = 0;
		// 注册
		if (type == ConstValue.SMS_REGISTER) {
			length = 6;
		}
		// 更新密码
		else if (type == ConstValue.SMS_UPDATE_PASSWORD) {
			length = 6;
		}
		// 订单验证码
		else if (type == ConstValue.SMS_ORDER) {
			length = 6;
		}
		// 更新邮箱
		else if (type == ConstValue.SMS_UPDATE_EMAIL) {
			length = 6;
		} else if(type == ConstValue.SMS_HOTEL_COUPON_GIVE){
			length = 6;
		}

		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	@Override
	public String getCurrentDatetime() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new java.util.Date());
	}

	@Override
	public String getCurrentDate() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new java.util.Date());
	}

	@Override
	public Date stringToDate(String str) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	@Override
	public String dateToString(Date date) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	@Override
	public Date getNextDate(Date date) {
		long longTime = (date.getTime() / 1000) + 24 * 60 * 60;
		Date nextDate = new Date();
		nextDate.setTime(longTime * 1000);
		return nextDate;
	}

	@Override
	public String getNewUid() {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		int length = 16;

		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	@Override
	public String getNewOrderId() {
		String orderId = null;
		orderId = String.valueOf((int) (Math.random() * 10000)) + String.valueOf((int) (Math.random() * 10000));
		while (orderId.length() < 8) {
			orderId = orderId + String.valueOf((int) (Math.random() * 10));
		}
		return orderId;
	}

	@Override
	public long array2binary(List<Integer> inputList, long[] staticArr) {
		long iRet = 0;
		for (int i = 0; i < inputList.size(); ++i) {
			iRet = iRet | staticArr[inputList.get(i)];
		}
		return iRet;
	}

	@Override
	public List<Integer> binary2array(long binary, long[] staticArr) {
		ArrayList<Integer> arrRet = new ArrayList<Integer>();
		for (int i = 0; i < staticArr.length; ++i) {
			if ((staticArr[i] & binary) > 0) {
				arrRet.add(i);
			}
		}
		return arrRet;
	}

	@Override
	public String checkPostParam(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	/*@Override
	public String getPhoneByUid(String uid) {
		return userDao.getPhoneByUid(uid);
	}*/
	@Override
	public String getPhoneByUid(String uid) {
		throw new RuntimeException("getPhoneByUid");
	}

	/*@Override
	public String getUidByPhone(String phone) {
		return userDao.getUidByPhone(phone);
	}*/
	@Override
	public String getUidByPhone(String phone) {
		throw new RuntimeException("getUidByPhone");
	}

	/*@Override
	public int getInvitedNumberByPhone(String phone) {
		return userDao.getInvitedNumberByPhone(phone);
	}*/
	@Override
	public int getInvitedNumberByPhone(String phone) {
		throw new RuntimeException("getInvitedNumberByPhone");
	}

	@Override
	public int insertOpen(OpenIdInfoPo op) {
		return utilDao.insertOpen(op);
	}

	@Override
	public int updateOpen(OpenIdInfoPo op) {
		return utilDao.updateOpen(op);
	}

	@Override
	public OpenIdInfoPo getOpenIdInfoPoByAllParam(OpenIdInfoPo op) {
		return utilDao.getOpenIdInfoPoByAllParam(op);
	}

	@Override
	public List<HotelCouponGroupVo> getHotelCouponGroups(Map<String, Object> params) {
		List<HotelCouponPo> hotelCoupons = getHotelCoupons(params);
		/********************** 合并相同属性的券 ***********************/
		Map<String, HotelCouponGroupVo> map = new LinkedHashMap<String, HotelCouponGroupVo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (HotelCouponPo hotelCoupon : hotelCoupons) {
			// 券名字，开发商ID，房源ID，是否旺季，状态，过期时间和不可使用时间完全一致，才表示相同属性的券
			String key = String.format("%s-%s-%s-%s-%s-%s-%s", //
					hotelCoupon.getName(), //
					hotelCoupon.getDevId(), //
					hotelCoupon.getHouseId(), //
					hotelCoupon.isInSeason(), //
					hotelCoupon.getStatus(), //
					hotelCoupon.getExpireTime() == null ? -1 : sdf.format(hotelCoupon.getExpireTime()), //
					hotelCoupon.getDisabledDates());
			HotelCouponGroupVo hotelCouponGroupVo = map.get(key);
			if (hotelCouponGroupVo != null) {
				hotelCouponGroupVo.setCount(hotelCouponGroupVo.getCount() + 1);
				hotelCouponGroupVo.getHotelCouponIds().add(hotelCoupon.getId());
			} else {
				List<Long> hotelCouponIds = new ArrayList<Long>();
				hotelCouponIds.add(hotelCoupon.getId());
				hotelCouponGroupVo = new HotelCouponGroupVo();
				BeanUtils.copyProperties(hotelCoupon, hotelCouponGroupVo);
				hotelCouponGroupVo.setCount(1);
				hotelCouponGroupVo.setHotelCouponIds(hotelCouponIds);
			}
			map.put(key, hotelCouponGroupVo);
		}
		return new ArrayList<HotelCouponGroupVo>(map.values());
	}

	@Override
	public List<HotelCouponPo> getHotelCoupons(Map<String, Object> params) {
		return hotelCouponDao.getHotelCoupons(params);
	}

	@Override
	public OrderPriceInfoVo getOrderPriceInfo(String mmWalletId, int houseId, Date startDate, Date endDate, List<Long> hotelCouponIds) {
		/******************** 获取酒店房间和价格信息 ************************/
		int days = 0;// 总天数
		int offSeasonDays = 0;// 淡季天数
		int inSeasonDays = 0;// 旺季天数
		Map<String, HousePricePo> priceMap = new HashMap<String, HousePricePo>();// 价格详细Map
		int totalAmt = 0;// 总价格
		int hotelCouponDiscountAmt = 0;// 旅居券抵扣金额

		long startDay = startDate.getTime() / (3600 * 1000 * 24);
		long endDay = endDate.getTime() / (3600 * 1000 * 24);
		days = (int) (endDay - startDay); // 总天数

		HousePo house = new HousePo();
		house.setHouseId(houseId);
		List<HousePo> houseList = getHouseByAllParam(house);
		if (houseList == null || houseList.size() == 0) {
			throw new IllegalArgumentException("param error, invalid house id");
		}
		house = houseList.get(0);
		List<HousePricePo> housePrices = getHousePriceByByDate(houseId, startDate, endDate);
		if (housePrices == null) {
			throw new RuntimeException("fail to get house price");
		}
		HousePricePo defaultHousePricePo = new HousePricePo();
		defaultHousePricePo.setMemTotalAmt(house.getMemTotalAmt());
		defaultHousePricePo.setMemFreezeAmt(house.getMemFreezeAmt());
		defaultHousePricePo.setTotalAmt(house.getTotalAmt());
		defaultHousePricePo.setFreezeAmt(house.getFreezeAmt());
		defaultHousePricePo.setDate(startDate);
		defaultHousePricePo.setInSeason(false);
		for (int i = 0; i < days; i++) {
			HousePricePo housePricePo = new HousePricePo();
			BeanUtils.copyProperties(defaultHousePricePo, housePricePo);
			housePricePo.setDate(DateUtils.addDays(startDate, i));
			priceMap.put(dateToString(housePricePo.getDate()), housePricePo);
		}
		for (HousePricePo housePricePo : housePrices) {
			if (housePricePo.isInSeason())
				inSeasonDays++;
			priceMap.put(dateToString(housePricePo.getDate()), housePricePo);
		}
		offSeasonDays = days - inSeasonDays;

		List<Integer> seasonPriceAmtList = new ArrayList<Integer>();// 平季价格列表
		List<Integer> inSeasonPriceAmtList = new ArrayList<Integer>();// 旺季价格列表
		for (Entry<String, HousePricePo> entry : priceMap.entrySet()) {
			HousePricePo housePrice = entry.getValue();
			totalAmt += housePrice.getMemTotalAmt();
			if (housePrice.isInSeason())
				inSeasonPriceAmtList.add(housePrice.getMemTotalAmt());
			else
				seasonPriceAmtList.add(housePrice.getMemTotalAmt());
		}

		int realDays = 0;// 真实抵扣天数
		/******************* 检查旅居券 *** ****************************/
		if (hotelCouponIds != null && hotelCouponIds.size() != 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", hotelCouponIds);
			params.put("mmWalletId", mmWalletId);
			params.put("houseId", houseId);
			params.put("statusIds", Arrays.asList(0));
			List<HotelCouponPo> hotelCoupons = getHotelCoupons(params);
			if (hotelCoupons.size() != hotelCouponIds.size()) {
				throw new IllegalArgumentException("param error, invalid hotel coupon id.");
			}
			int offSeasonHotelCouponCount = 0; // 平季券
			int inSeasonHotelCouponCount = 0;// 旺季券
			for (HotelCouponPo hotelCouponPo : hotelCoupons) {
				if (hotelCouponPo.isInSeason())
					inSeasonHotelCouponCount++;
				else
					offSeasonHotelCouponCount++;
			}

			/*************** 计算旅居券抵扣的金额 *****************************/
			int realInSeasonDays = 0;// 真实抵扣旺季天数
			int realOffeasonDays = 0;// 真实抵扣平季天数

			/***********************
			 * 1.1计算真实抵扣的平旺季天数
			 ************************/
			realInSeasonDays = inSeasonHotelCouponCount;
			realOffeasonDays = offSeasonHotelCouponCount;
			if (realInSeasonDays > inSeasonDays) {// 旺季券多于的旺季天数(旺季券不可抵扣平季，但是2张平季券可以抵扣一晚旺季)
				throw new IllegalArgumentException("param error, to many in season hotel coupon.");
			}

			if (offSeasonHotelCouponCount > offSeasonDays) {// 当平季券有多余时，则把多余的按照2张换成一张旺季券
				realOffeasonDays = offSeasonDays;
				if (realInSeasonDays >= inSeasonDays) {// 没有需要抵扣旺季的天数，则返回错误信息
					throw new IllegalArgumentException("param error, to many off season hotel coupon.");
				}
				realOffeasonDays = realOffeasonDays - offSeasonDays;
				if (realOffeasonDays % 2 != 0) {// 多余的平季券是单数，则少扣抵一天平季券，用来补旺季券
					realOffeasonDays--;
				}
				realDays = realInSeasonDays + (offSeasonHotelCouponCount - realOffeasonDays) / 2;
				realInSeasonDays = realDays;
			}else {
				realDays = offSeasonHotelCouponCount;
			}

			/***********************
			 * 1.2 计算真实抵扣的平旺季价格
			 ************************/
			for (int i = 0; i < realInSeasonDays; i++) {// 有旺季券，抵扣旺季价格
				hotelCouponDiscountAmt += inSeasonPriceAmtList.get(i);
			}
			for (int i = 0; i < realOffeasonDays; i++) {// 有平季券，抵扣平季价格
				hotelCouponDiscountAmt += seasonPriceAmtList.get(i);
			}
		}else {
			realDays = offSeasonDays;
		}
		OrderPriceInfoVo result = new OrderPriceInfoVo();
		result.setTotalAmt(totalAmt);
		result.setDays(days);
		result.setInSeasonDays(inSeasonDays);
		result.setOffSeasonDays(offSeasonDays);
		result.setPriceMap(priceMap);
		result.setHotelCouponDiscountAmt(hotelCouponDiscountAmt);
		result.setOrderFinalAmt(totalAmt - hotelCouponDiscountAmt);
		result.setRealInSeasonDays(realDays);//真实抵扣天数
		return result;
	}

	@Override
	public int updateHotelCouponStatus(List<Long> ids, int status) {
		return hotelCouponDao.updateHotelCouponStatus(ids, status);
	}

	@Override
	public HotelCouponGroupVo getHotelCouponGroup(long id) {
		List<HotelCouponPo> list = hotelCouponDao.getHotelCouponGroup(id);
		if (list == null || list.size() == 0)
			return null;
		List<Long> hotelCouponIds = new ArrayList<Long>();
		for (HotelCouponPo hotelCouponPo : list) {
			hotelCouponIds.add(hotelCouponPo.getId());
		}
		HotelCouponGroupVo result = new HotelCouponGroupVo();
		BeanUtils.copyProperties(list.get(0), result);
		result.setCount(list.size());
		result.setHotelCouponIds(hotelCouponIds);
		return result;
	}

	@Override
	public long addHotelCouponExchange(ExchangePo exchange) {
		Map<String, Object> params = new HashMap<String, Object>();
		String idName = "id";
		params.put("data", exchange);
		params.put(idName, 0);
		hotelCouponDao.addHotelCouponExchange(params);
		return Long.valueOf(String.valueOf(params.get(idName)));

	}

	@Override
	public long addHotelCouponExchangeRequest(ExchangeRequestPo exchangeRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		String idName = "id";
		params.put("data", exchangeRequest);
		params.put(idName, 0);
		hotelCouponDao.addHotelCouponExchangeRequest(params);
		return Long.valueOf(String.valueOf(params.get(idName)));
	}

	@Override
	public List<ExchangePo> getHotelCouponExchanges(Map<String, Object> params) {
		List<ExchangePo> eList = hotelCouponDao.getHotelCouponExchanges(params);
		for (int i = 0; i < eList.size(); i++) {
			String memberId=eList.get(i).getMemberId();
			String memberName=getPersonalMemberInfo(memberId).getRealName();
			eList.get(i).setMemberName(memberName);
		}
		//return hotelCouponDao.getHotelCouponExchanges(params);
		return eList;
	}
	
	/**
	 * 接口获取个人会员信息
	 * @param memberId 会员ID
	 * @return
	 */
	public PersonalMemberInfo getPersonalMemberInfo(String memberId){
		PersonalMemberResponse response = memberFacade.queryPersonalMember(getEnv(), new PersonalMemberQueryRequest(memberId));
		return response.getPersonalMemberInfo();
	}
	
	@Override
	public List<ExchangeRequestPo> getExchangeRequests(long exchangeId) {
		return hotelCouponDao.getExchangeRequests(exchangeId);
	}

	@Override
	public ExchangeRequestPo getExchangeRequestById(long id) {
		ExchangeRequestPo erPo = hotelCouponDao.getExchangeRequestById(id);
		erPo.setMemberName(getPersonalMemberInfo(erPo.getMemberId()).getRealName());
		return erPo;
	}

	@Override
	public ExchangePo getExchangeDetailById(long id) {
		ExchangePo ePo=hotelCouponDao.getExchangeDetailById(id);
		List<ExchangeRequestPo> erPoList=ePo.getRequests();
		for(int i=0;i<erPoList.size();i++){
			erPoList.get(i).setMemberName(getPersonalMemberInfo(erPoList.get(i).getMemberId()).getRealName());
		}
		ePo.setRequests(erPoList);
		ePo.setMemberName(getPersonalMemberInfo(ePo.getMemberId()).getRealName());
		//return hotelCouponDao.getExchangeDetailById(id);
		return ePo;
	}

	@Override
	public int updateExchange(long id, int status) {
		return hotelCouponDao.updateExchange(id, status);
	}

	@Override
	public int updateExchangeRequest(long id, int status) {
		return hotelCouponDao.updateExchangeRequest(id, status);
	}

	@Override
	public int updateExchangeRequestByExchange(long exchangeId, int status) {
		return hotelCouponDao.updateExchangeRequestByExchange(exchangeId, status);
	}

	@Override
	public void changeHotelCoupon(String fromMemberId, String toMemberId, List<Long> fromHotelCouponIds, List<Long> toHotelCouponIds) {
		hotelCouponDao.changeHotelCoupon(fromMemberId, toHotelCouponIds);
		hotelCouponDao.changeHotelCoupon(toMemberId, fromHotelCouponIds);
	}
	
	@Override
	public List<HotelCouponGivePo> getHotelCouponGiveList(Map<String, Object> params){
	    return this.hotelCouponDao.getHotelCouponGiveList(params);
	  }
	
	@Override
	public HotelCouponGivePo getHotelCouponGiveDetail(long id){
	    Map params = new HashMap();
	    params.put("id", Long.valueOf(id));
	    return this.hotelCouponDao.getHotelCouponGiveDetail(params);
	  }

	@Override
	public HotelCouponGivePo getHotelCouponGiveDetail(String giveCode){
	    Map params = new HashMap();
	    params.put("giveCode", giveCode);
	    return this.hotelCouponDao.getHotelCouponGiveDetail(params);
	  }
	
	@Override
	public long addHotelCouponGive(HotelCouponGivePo hotelCouponGive){
	    Map params = new HashMap();
	    String idName = "id";
	    params.put("data", hotelCouponGive);
	    params.put(idName, Integer.valueOf(0));
	    this.hotelCouponDao.addHotelCouponGive(params);
	    return Long.valueOf(String.valueOf(params.get(idName))).longValue();
	  }
	
	@Override
	public void giveHotelCoupon(HotelCouponGivePo givePo, String phoneNumber){
	    List<Long> hotelCouponIds = new ArrayList<Long>();
	    List<Long> receivedIds = new ArrayList<Long>();
	    JSONArray jsonArray = JSONArray.parseArray(givePo.getHotelCouponIds());
	    for (int i = 0; i < jsonArray.size(); i++) {
	      hotelCouponIds.add(Long.valueOf(jsonArray.getLongValue(i)));
	    }
	    jsonArray = JSONArray.parseArray(givePo.getReceivedHotelCouponIds());
	    for (int i = 0; i < jsonArray.size(); i++) {
	      receivedIds.add(Long.valueOf(jsonArray.getLongValue(i)));
	    }
	    hotelCouponIds.removeAll(receivedIds);

	    if (existGiveHotelCouponReceive(givePo.getId(), phoneNumber)) {
	      throw new RuntimeException("已经领取过了，不能重复领取");
	    }
	    JSONObject showDetailJsonObject = JSONObject.parseObject(givePo.getShowDetail());
	    int copiesLimit = showDetailJsonObject.getIntValue("copiesLimit");
	    if (hotelCouponIds.size() < copiesLimit) {
	      throw new RuntimeException("旅居券已被领取完");
	    }
	    hotelCouponIds = hotelCouponIds.subList(0, copiesLimit);
	    updateHotelCouponStatus(hotelCouponIds, 11);
	    String hotelCouponIdsJson = JSONObject.toJSONString(hotelCouponIds);
	    String mmWalletId = null;
	    String memberId = null;
	    Map<String,Object> receiveParams = new HashMap<String,Object>();
	    Map<String,Object> addParams = new HashMap<String,Object>();
	    receiveParams.put("hotelCouponGiveId", Long.valueOf(givePo.getId()));
	    receiveParams.put("hotelCouponIds", hotelCouponIdsJson);
	    String idName = "id";
	    addParams.put("data", receiveParams);
	    addParams.put(idName, Integer.valueOf(0));
	    if ((StringUtils.isNotBlank(mmWalletId)) && (StringUtils.isNotBlank(memberId))) {
	      Map<String,Object> copyParams = new HashMap<String,Object>();
	      copyParams.put("mmWalletId", mmWalletId);
	      copyParams.put("status", Integer.valueOf(0));
	      copyParams.put("hotelCouponIds", hotelCouponIds);
	      this.hotelCouponDao.copyHotelCoupon(copyParams);

	      receiveParams.put("accountRefId", memberId);
	      receiveParams.put("accountType", Integer.valueOf(0));
	    } else {
	      receiveParams.put("accountRefId", phoneNumber);
	      receiveParams.put("accountType", Integer.valueOf(1));
	    }
	    this.hotelCouponDao.addHotelCouponGiveReceive(addParams);
	    receivedIds.addAll(hotelCouponIds);
	    this.hotelCouponDao.updateReceivedHotelCouponIds(givePo.getId(), JSONArray.toJSONString(receivedIds));

	    if (givePo.getReceivedCount() + 1 >= JSONObject.parseObject(givePo.getShowDetail()).getIntValue("copiesCount"))
	      updateHotelCouponGiveStatus(givePo.getId(), 1);
	  }
	
	public boolean existGiveHotelCouponReceive(long giveId, String phoneNumber)
	  {
	    String accountRefId = phoneNumber;
	    int t = this.hotelCouponDao.findHotelCouponGiveReceiveCount(giveId, accountRefId);
	    if (t >= 1)
	      return true;
	    return false;
	  }
	
	@Override
	public int updateHotelCouponGiveStatus(long id, int status)
	  {
	    return this.hotelCouponDao.updateHotelCouponGiveStatus(id, status);
	  }
	
	private OperationEnvironment getEnv() {
		OperationEnvironment operationenvironment = new OperationEnvironment();
		operationenvironment.setClientId(getClientId());
		
		return operationenvironment;
	}
	
	private String getClientId(){
		return String.format("house_web:%s", UUID.randomUUID().toString());
	}

	@Override
	public List<ContactsPo> getMyContactsByUid(ContactsPo contactsPo) {
		List<ContactsPo> contactsPoList = new ArrayList<ContactsPo>();
		try {
			contactsPoList = userDao.getMyContactsByUid(contactsPo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getMyContactsByUid]Exception:", e);
			return null;
		}
		return contactsPoList;
	}

	/* (non-Javadoc)
	 * @see com.mama.server.main.service.MainService#updateMyContacts(com.mama.server.main.dao.model.ContactsPo)
	 */
	@Override
	public int updateMyContacts(ContactsPo contactsPo) {
		try {
			userDao.updateMyContacts(contactsPo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateMyContacts]Exception:", e);
			return -1;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.mama.server.main.service.MainService#insertMyContacts(com.mama.server.main.dao.model.ContactsPo)
	 */
	@Override
	public int insertMyContacts(ContactsPo contactsPo) {
		try {
			userDao.insertMyContacts(contactsPo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertMyContacts]Exception:", e);
			return -1;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.mama.server.main.service.MainService#removeMyContacts(com.mama.server.main.dao.model.ContactsPo)
	 */
	@Override
	public int removeMyContacts(ContactsPo contactsPo) {
		try {
			userDao.removeMyContacts(contactsPo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[removeMyContacts]Exception:", e);
			return -1;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.mama.server.main.service.MainService#getMyContactsAllParam(com.mama.server.main.dao.model.ContactsPo)
	 */
	@Override
	public ContactsPo getMyContactsAllParam(ContactsPo contactsPo) {
		try {
			contactsPo = userDao.getMyContactsByAllParam(contactsPo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getMyContactsByAllParam]Exception:", e);
			return null;
		}
		return contactsPo;
	}
	
	
	@Override
	public List<ActivityMemberRecordPo> getLotteryRecord(ActivityMemberRecordPo amr){
		return activityDao.getLotteryRecord(amr);
	}
	
	@Override
	public List<ActivityConfPo> getActivityConf(String activityCode){
		return activityDao.getActivityConf(activityCode);
	}

	@Override
	public int order(PayDomain payDomain) {
		OrderPayDO orderPayDO = convictDomainToDo(payDomain);		
		return orderPayDAO.insert(orderPayDO);
	}

	@Override
	public OrderPayDO findOrderBypar(String orderId) {
		OrderPayDO orderPayDO = new OrderPayDO();
		try {
			orderPayDO = orderPayDAO.selectByOrderId(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderPayDO;
	}
	
	@Override
	/** 根据orderId查询OrderPo对象记录信息 */
    public OrderPo queryOrderPoByOrderId(String orderId) {
		return orderDao.queryOrderPoByOrderId(orderId);
	}
	
	/** 根据payId查询对应的支付记录信息 */
	@Override
	public OrderPayDO selectOrderPayByPayId(String payId) {
		return orderPayDAO.selectOrderPayByPayId(payId);
    }
	
	/** 根据房源定好号查询支付记录 */
	public List<OrderPayDO> queryPayOrderListByOrderId(String orderId) {
		return orderPayDAO.queryPayOrderListByOrderId(orderId);
	}
	
	@Override
	public int updateByPrimaryKey(OrderPayDO record) {
		// TODO Auto-generated method stub
		return orderPayDAO.updateByPrimaryKey(record);
	}
	
	private OrderPayDO convictDomainToDo(PayDomain payDomain){
		OrderPayDO orderPayDO = new OrderPayDO();
		orderPayDO.setPayId(payDomain.getPayId());
		orderPayDO.setOrderId(payDomain.getOrderId());		
		orderPayDO.setFee(payDomain.getFee());
		orderPayDO.setAccountDate(payDomain.getAccountDate());
		orderPayDO.setStatus(payDomain.getStatus());
		orderPayDO.setCreateTime(payDomain.getCreateTime());
		orderPayDO.setUpdateTime(payDomain.getUpdateTime());
		orderPayDO.setPayType(payDomain.getPayType());
		return orderPayDO;
	}
	
	private PayDomain convictDoToDomain(OrderPayDO orderPayDO){
		PayDomain payDomain = new PayDomain();
		payDomain.setPayId(orderPayDO.getPayId());
		payDomain.setOrderId(orderPayDO.getOrderId());		
		payDomain.setFee(orderPayDO.getFee());
		payDomain.setAccountDate(orderPayDO.getAccountDate());
		payDomain.setStatus(orderPayDO.getStatus());
		payDomain.setCreateTime(orderPayDO.getCreateTime());
		payDomain.setUpdateTime(orderPayDO.getUpdateTime());
		payDomain.setPayType(orderPayDO.getPayType());
		return payDomain;
	}

	/* (non-Javadoc)
	 * @see com.mama.server.main.service.MainService#getMyContactsByNameOrPhone(com.mama.server.main.dao.model.ContactsPo)
	 */
	@Override
	public List<ContactsPo> getMyContactsByNameOrPhone(ContactsPo contactsPo) {
		List<ContactsPo> contactsPoList = new ArrayList<ContactsPo>();
		try {
			contactsPoList = userDao.getMyContactsByNameOrPhone(contactsPo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getMyContactsByUid]Exception:", e);
			return null;
		}
		return contactsPoList;
	}

	@Override
	public List<TradeArea> getTradeAreas(Map map) {
		List<TradeArea> areaList = new ArrayList<TradeArea>();
		try {
			areaList = tradeAreaDao.getAllTradeArea(map);
		} catch (Exception e) {
			Log.SERVICE.error("[getTradeAreas]Exception:", e);
			return null;
		}
		return areaList;
	}

	@Override
	public int insTradeArea(TradeArea tArea) {
		try {
			tradeAreaDao.insertTradeArea(tArea);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insTradeArea]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	public Page<ActivityMemberRecordPo> getRecordMember(ActivityMemberRecordPo activityMemberRecordPo) {
		PaginationInterceptor.startPage(activityMemberRecordPo.getCurrent_page(), activityMemberRecordPo.getPage_size());
		activityDao.getRecordMember(activityMemberRecordPo);
		Page<ActivityMemberRecordPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	public List<ActivityMemberRecordPo> getRecordAllMember(ActivityMemberRecordPo activityMemberRecordPo) {
		return activityDao.getRecordMember(activityMemberRecordPo);
	}
	
	@Override
	public List<TradeArea> getTradeAreaById(TradeArea tArea) {
		List<TradeArea> areaList = new ArrayList<TradeArea>();
		try {
			areaList = tradeAreaDao.getTradeAreaById(tArea);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getTradeAreaById]Exception:", e);
			return null;
		}
		return areaList;
	}

	@Override
	public int delTradeArea(TradeArea tradeArea) {
		try {
			tradeAreaDao.delTradeAreaById(tradeArea);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[delTradeArea]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<TradeArea> getTradeAreaByPar(TradeArea tArea) {
		List<TradeArea> areaList = new ArrayList<TradeArea>();
		try {
			areaList = tradeAreaDao.getTradeAreaByPar(tArea);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getTradeAreaByPar]Exception:", e);
			return null;
		}
		return areaList;
	}

	@Override
	public int updateTradeArea(TradeArea tradeArea) {
		try {
			tradeAreaDao.updateTradeArea(tradeArea);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateTradeArea]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int insHouseTag(HouseTag houseTag) {
		try {
			houseTagDao.insertHouseTag(houseTag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insTradeArea]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<HouseTag> getHouseTagById(HouseTag houseTag) {
		List<HouseTag> htList = new ArrayList<HouseTag>();
		try {
			htList = houseTagDao.getHouseTagById(houseTag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseTagById]Exception:", e);
			return null;
		}
		return htList;
	}

	@Override
	public List<HouseTag> getHouseTagByPar(HouseTag houseTag) {
		List<HouseTag> tagList = new ArrayList<HouseTag>();
		try {
			tagList = houseTagDao.getHouseTagByPar(houseTag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseTagByPar]Exception:", e);
			return null;
		}
		return tagList;
	}

	@Override
	public int updateHouseTag(HouseTag houseTag) {
		try {
			houseTagDao.updateHouseTag(houseTag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateHouseTag]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<HouseTag> getHouseTags(Map map) {
		List<HouseTag> tagList = new ArrayList<HouseTag>();
		try {
			tagList = houseTagDao.getAllHouseTag(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseTags]Exception:", e);
			return null;
		}
		return tagList;
	}

	@Override
	public List<HouseTag> duplicateHouseTagByPar(HouseTag houseTag) {
		List<HouseTag> tagList = new ArrayList<HouseTag>();
		try {
			tagList = houseTagDao.duplicateHouseTagByPar(houseTag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseTagByPar]Exception:", e);
			return null;
		}
		return tagList;
	}
	
	@Override
	public List<HousePo> getHouseByBldId(int bldId) {
		HousePo house = new HousePo();
		house.setBldId(bldId);
		return houseDao.getHouseByBldId(house);
	}

	@Override
	public List<HouseShopPo> getHouseShops(Map map) {
		List<HouseShopPo> hsList = new ArrayList<HouseShopPo>();
		try {
			hsList = houseShopDao.getAllHouseShops(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseShops]Exception:", e);
			return null;
		}
		return hsList;
	}
	
	@Override
	public List<HouseShopPo> getAllHouseShopsByTopicId(Map map) {
		List<HouseShopPo> hsList = new ArrayList<HouseShopPo>();
		try {
			hsList = houseShopDao.getAllHouseShopsByTopicId(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseShops]Exception:", e);
			return null;
		}
		return hsList;
	}

	@Override
	public List<HouseShopPo> getHouseShopByPar(HouseShopPo houseShop) {
		List<HouseShopPo> hsList = new ArrayList<HouseShopPo>();
		try {
			hsList = houseShopDao.getHouseShopByPar(houseShop);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getHouseShopByPar]Exception:", e);
			return null;
		}
		return hsList;
	}

	@Override
	public List<ActivityEnrollPo> getActivityEnrolls(Map map){
		List<ActivityEnrollPo> list = new ArrayList<ActivityEnrollPo>();
		try {
			list = activityEnrollDao.getActivityEnrollByPar(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getActivityEnrolls]Exception:", e);
			return null;
		}
		return list;
	}

	@Override
	public int updateActivityEnrollPo(ActivityEnrollPo op) {
		try {
			activityEnrollDao.updateActivityEnrollPo(op);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateActivityEnrollPo]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public int insertActivityEnroll(ActivityEnrollPo activityEnroll) {
		int result = 0;
		try {
			result = activityEnrollDao.insertActivityEnroll(activityEnroll);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insertActivityEnroll]Exception:", e);
			return -1;
		}
		return result;
	}

	@Override
	public int insHouseShop(HouseShopPo houseShop) {
		try {
			houseShopDao.insertHouseShop(houseShop);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insHouseShop]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public int insCFHouseShop(CFHouseShopPo cfHouseShopPo) {
		try {
			cfHouseShopDao.insertOrUpdatePo(cfHouseShopPo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[insCFHouseShop]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	@Override
	public CFHouseShopPo getCFHouseShop(CFHouseShopPo cfHouseShopPo) {
		CFHouseShopPo cHouseShop = new CFHouseShopPo();
		try {
			cHouseShop = cfHouseShopDao.selectPo(cfHouseShopPo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[getCFHouseShop]Exception:", e);
			return null;
		}
		return cHouseShop;
	}

	@Override
	public int updateHouseShop(HouseShopPo houseShop) {
		try {
			houseShopDao.updateHouseShop(houseShop);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[updateHouseShop]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int delHouseShop(int id) {
		try {
			houseShopDao.delHouseShop(id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[delHouseShop]Exception:", e);
			return -1;
		}
		return 0;
	}
	
	/** 修改房源订单及支付订单状态 */
	public void updateHouseOrderAnaPayStatus(String uid, String orderId, String payId) {
		/** 修改房源订单状态 */
		OrderPo op = new OrderPo();
		op.setOrderId(orderId);
		op.setStatus(11);
		op.setUid(uid);
		updateOrder(op);
		
		/** 修改支付订单状态 */
		OrderPayDO record = new OrderPayDO();
		record.setPayId(payId);
		record.setStatus("PAY_SUCCESS");
		orderPayDAO.updateByPrimaryKey(record);
	}
 
	/** 支付宝异步支付修改相关支付状态 */
	public void updateAlipayAsyncPayStatus(String uid, String orderId, String payId, String pay_type) {
		/** 修改房源订单状态 */
		OrderPo op = new OrderPo();
		op.setOrderId(orderId);
		op.setStatus(11);
		op.setUid(uid);
		op.setPay_type(pay_type);
		updateOrder(op);
		
		/** 修改支付订单状态 */
		OrderPayDO record = new OrderPayDO();
		record.setPayId(payId);
		record.setStatus("PAY_SUCCESS");
		orderPayDAO.updateByPrimaryKey(record);
	}
	
	/** 订单超时未支付或失败相关状态更新 */
	public void updateAlipayAsyncPayStatusForFail(String uid, String orderId, String payId, String pay_type) {
		/** 修改房源订单状态 */
		OrderPo op = new OrderPo();
		op.setOrderId(orderId);
		op.setStatus(ConstValue.ORDER_MERCHANT_REFUND);
		op.setUid(uid);
		op.setPay_type(pay_type);
		updateOrder(op);
		
		/** 修改支付订单状态 */
		OrderPayDO record = new OrderPayDO();
		record.setPayId(payId);
		record.setStatus("PAY_FAIL");
		orderPayDAO.updateByPrimaryKey(record);
	}
	
	@Override
	public int addVersion(Version version) {
		try {
			versionDao.insertVersion(version);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[addVersion]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public int updateVersion(Version version) {
		try {
			versionDao.updateVersion(version);
		} catch (Exception e) {
			Log.SERVICE.error("[updateVersion]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public Version getVersionByPar(int versionType) {
		Version version = new Version();
		try {
			version = versionDao.getVersionByPar(versionType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}
	
	@Override
	public List<OrderPo> getOrderClickFarming(OrderPo order) {
		try {
			return orderDao.getOrderClickFarming(order);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public int insertClickFarming(OrderPo order) {
		try {
			return orderDao.insertClickFarming(order);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public int insertTopicActivity(TopicActivityPo topicActivity) {
		try {
			return topicActivityDao.insertTopicActivity(topicActivity);
		} catch (Exception e) {
			Log.SERVICE.error("serivce insertTopicActivity 异常：", e);
			return 0;
		}
	}
	
	@Override
	public List<TopicActivityPo> getTopicActivityBy(TopicActivityPo topicActivity) {
		try {
			return topicActivityDao.getTopicActivity(topicActivity);
		} catch (Exception e) {
			Log.SERVICE.error("serivce getTopicActivity 异常：", e);
			return null;
		}
	}
	@Override
	public Page<TopicActivityPo> getTopicActivity(TopicActivityPo topicActivity) {
		PaginationInterceptor.startPage(topicActivity.getCurrent_page(), topicActivity.getPage_size());
		topicActivityDao.getTopicActivity(topicActivity);
		Page<TopicActivityPo> page = PaginationInterceptor.endPage();
		return page;
	}
	
	
	@Override
	public void updateTopicActivity(TopicActivityPo topicActivity) {
		topicActivityDao.updateTopicActivity(topicActivity);
	}
	
	@Override
	public void removeTopicActivity(TopicActivityPo topicActivity) {
		topicActivityDao.removeTopicActivity(topicActivity);
	}
	
	@Override
	public List<TopicShopPo> getTopicShopList(TopicShopPo topicShop) {
		return topicActivityDao.getTopicShopList(topicShop);
	}
	
	@Override
	public void removeTopicShop(TopicShopPo topicShop) {
		topicActivityDao.removeTopicShop(topicShop);
	}
	
	@Override
	public int insertTopicShop(TopicShopPo topicShop){
		try {
			return topicActivityDao.insertTopicShop(topicShop);
		} catch (Exception e) {
			Log.SERVICE.error("serivce insertTopicShop 异常：", e);
			return 0;
		}
		
	}
	
	@Override
	public List<HousePo> getTopicHouses(Map map) {
		return houseDao.getTopicHouses(map);
	}
	
	@Override
	public Page<Map<String,Object>> getTopicHousesPage(Map map) {
		PaginationInterceptor.startPage((Integer) map.get("pageNum"), (Integer) map.get("pageSize"));
		houseDao.getTopicHouses(map);
		Page<Map<String,Object>> page = PaginationInterceptor.endPage();
		return page;
	}
	
	@Override
	public List<AppVersionPo> getAppVersion(AppVersionPo appVersion) {
		return appVersionDao.getAppVersion(appVersion);
	}

	@Override
	public List<RecommodHouse> getRecommondList() {
		List<RecommodHouse> list = houseDao.getRecommondHouse();
		return list;
	}

	@Override
	public CityPo getCityById(int id) {
//		Version version = new Version();
		CityPo cityPo = new CityPo();
		try {
//			version = versionDao.getVersionByPar(versionType);
			cityPo = utilDao.getCityById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityPo;
	}
	
	@Override
	public int addHouseCard(HouseCardPo houseCard) {
		try {
			houseCardDao.addHouseCard(houseCard);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("[addHouseCard]Exception:", e);
			return -1;
		}
		return 0;
	}

	@Override
	public List<HouseCardPo> getHouseCardListByOrderId(Map<String, Object> map) {
		List<HouseCardPo> list = houseCardDao.getHouseCardListByOrderId(map);
		return list;
	}
	
	@Override
	/** 根据订单号查询最新一条支付成功的支付记录 */
	public OrderPayDO queryLatestOrderPayByOrderId(String orderId) {
		return orderPayDAO.queryLatestOrderPayByOrderId(orderId);
	}
}