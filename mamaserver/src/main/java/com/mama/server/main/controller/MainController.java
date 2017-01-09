package com.mama.server.main.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.controller.handler.activity.ActivityEnrollAddHandler;
import com.mama.server.main.controller.handler.activity.CheckEnrollHandler;
import com.mama.server.main.controller.handler.activity.CreateActivityHandler;
import com.mama.server.main.controller.handler.activity.DoShareHandler;
import com.mama.server.main.controller.handler.activity.GetLotteryRecordHandler;
import com.mama.server.main.controller.handler.activity.GetRecordAllMemberHandler;
import com.mama.server.main.controller.handler.activity.GetRecordMemberHandler;
import com.mama.server.main.controller.handler.activity.GetTopicActivitysHandler;
import com.mama.server.main.controller.handler.activity.GetTopicHousesHandler;
import com.mama.server.main.controller.handler.activity.GetTopicsHandler;
import com.mama.server.main.controller.handler.activity.InitActivityConfHandler;
import com.mama.server.main.controller.handler.activity.InitMemberInfoHandler;
import com.mama.server.main.controller.handler.app.AppCreateOrderHandler;
import com.mama.server.main.controller.handler.app.AppHouseSearchHandler;
import com.mama.server.main.controller.handler.app.AppVersionUpdateHandler;
import com.mama.server.main.controller.handler.clickFarming.ClickFarmingOrderHandler;
import com.mama.server.main.controller.handler.clickFarming.GetHouseDetailSearchHandler;
import com.mama.server.main.controller.handler.clickFarming.GetShuaDanOrdersHandler;
import com.mama.server.main.controller.handler.clickFarming.SendOrderMsgHandler;
import com.mama.server.main.controller.handler.collect.AddOrDeleCollectHandler;
import com.mama.server.main.controller.handler.hotelcoupon.AcceptHotelCouponExchangeHandler;
import com.mama.server.main.controller.handler.hotelcoupon.AddHotelCouponExchangeHandler;
import com.mama.server.main.controller.handler.hotelcoupon.AddHotelCouponGiveHandler;
import com.mama.server.main.controller.handler.hotelcoupon.AddHotelCouponGiveReceiveHandler;
import com.mama.server.main.controller.handler.hotelcoupon.CancelHotelCouponExchangeHandler;
import com.mama.server.main.controller.handler.hotelcoupon.GetHotelCouponExchangeDetailHandler;
import com.mama.server.main.controller.handler.hotelcoupon.GetHotelCouponExchangeHandler;
import com.mama.server.main.controller.handler.hotelcoupon.GetHotelCouponGiveDetailHandler;
import com.mama.server.main.controller.handler.hotelcoupon.GetHotelCouponGiveListHandler;
import com.mama.server.main.controller.handler.hotelcoupon.GetHotelCouponGroupByIdHandler;
import com.mama.server.main.controller.handler.hotelcoupon.GetHotelCouponGroupsHandler;
import com.mama.server.main.controller.handler.main.AddCollectHandler;
import com.mama.server.main.controller.handler.main.CancelOrderHandler;
import com.mama.server.main.controller.handler.main.CheckHouseDateHandler;
import com.mama.server.main.controller.handler.main.CheckPhoneHandler;
import com.mama.server.main.controller.handler.main.CheckVerifyCodeHandler;
import com.mama.server.main.controller.handler.main.CheckVipHandler;
import com.mama.server.main.controller.handler.main.CreateOrderHandlerNew;
import com.mama.server.main.controller.handler.main.GetBuildingInfoHandler;
import com.mama.server.main.controller.handler.main.GetBuildingsHandler;
import com.mama.server.main.controller.handler.main.GetCardInfoHandler;
import com.mama.server.main.controller.handler.main.GetCitiesHandler;
import com.mama.server.main.controller.handler.main.GetCollectsHandler;
import com.mama.server.main.controller.handler.main.GetCollectsPageHandler;
import com.mama.server.main.controller.handler.main.GetContactByIdHandler;
import com.mama.server.main.controller.handler.main.GetContactsHandler;
import com.mama.server.main.controller.handler.main.GetDeveloperInfoHandler;
import com.mama.server.main.controller.handler.main.GetDevelopersHandler;
import com.mama.server.main.controller.handler.main.GetFlowsHandler;
import com.mama.server.main.controller.handler.main.GetHouseInfoHandler;
import com.mama.server.main.controller.handler.main.GetHousePriceHandler;
import com.mama.server.main.controller.handler.main.GetHousesHandler;
import com.mama.server.main.controller.handler.main.GetHousesSearchHandler;
import com.mama.server.main.controller.handler.main.GetIndexSpecialsHousesHandler;
import com.mama.server.main.controller.handler.main.GetInvitedNumberHandler;
import com.mama.server.main.controller.handler.main.GetIsExistMyContactsHandler;
import com.mama.server.main.controller.handler.main.GetMyContactsByIdHandler;
import com.mama.server.main.controller.handler.main.GetMyContactsHandler;
import com.mama.server.main.controller.handler.main.GetOpenIdPhoneHandler;
import com.mama.server.main.controller.handler.main.GetOrderInfoHandler;
import com.mama.server.main.controller.handler.main.GetOrderPriceInfoHandler;
import com.mama.server.main.controller.handler.main.GetOrdersHandler;
import com.mama.server.main.controller.handler.main.GetProvincesHandler;
import com.mama.server.main.controller.handler.main.GetRecommodHandler;
import com.mama.server.main.controller.handler.main.GetSpecialsHousesHandler;
import com.mama.server.main.controller.handler.main.GetUserInfoHandler;
import com.mama.server.main.controller.handler.main.GetVersionByParHandler;
import com.mama.server.main.controller.handler.main.InsertMyContactsHandler;
import com.mama.server.main.controller.handler.main.InsertOpenIdPhoneHandler;
import com.mama.server.main.controller.handler.main.IsPhoneRegisteredHandler;
import com.mama.server.main.controller.handler.main.LoginHandler;
import com.mama.server.main.controller.handler.main.MMWalletLoginHandler;
import com.mama.server.main.controller.handler.main.ModifyContactHandler;
import com.mama.server.main.controller.handler.main.ModifyMyContactsHandler;
import com.mama.server.main.controller.handler.main.PutHouseTagsToRedisHandler;
import com.mama.server.main.controller.handler.main.RegisterHandler;
import com.mama.server.main.controller.handler.main.RemoveCollectHandler;
import com.mama.server.main.controller.handler.main.RemoveContactHandler;
import com.mama.server.main.controller.handler.main.RemoveMyContactsHandler;
import com.mama.server.main.controller.handler.main.SendOrderPayCodeHandler;
import com.mama.server.main.controller.handler.main.SendVerifyCodeHandler;
import com.mama.server.main.controller.handler.main.UpdatePasswordHandler;
import com.mama.server.main.controller.handler.main.UpdateUserInfoHandler;
import com.mama.server.main.controller.handler.main.VerifyIdentityHandler;
import com.mama.server.main.controller.handler.main.comments.AddOrderCommentHandler;
import com.mama.server.main.controller.handler.main.comments.DelOrderCommentHandler;
import com.mama.server.main.controller.handler.main.comments.DoReviewCommentHandler;
import com.mama.server.main.controller.handler.main.comments.GetCommentHandler;
import com.mama.server.main.controller.handler.main.comments.GetHouseCommentsHandler;
import com.mama.server.main.controller.handler.main.comments.GetOrderCommentByOrderIdHandler;
import com.mama.server.main.controller.handler.main.comments.GetOrderCommentHandler;
import com.mama.server.main.controller.handler.main.comments.StickOrderCommnetHandler;
import com.mama.server.main.controller.handler.main.house.GetHouseDetailHandler;
import com.mama.server.main.controller.handler.main.house.KWSearchHandler;
import com.mama.server.main.controller.handler.main.n99.AppAddHouseCardHandler;
import com.mama.server.main.controller.handler.main.n99.EarnedHouseCardListHandler;
import com.mama.server.main.controller.handler.main.n99.ExchangedHouseCardListHandler;
import com.mama.server.main.controller.handler.main.n99.GetRangeHousesHandler;
import com.mama.server.main.controller.handler.main.n99.GiveHouseCardHandler;
import com.mama.server.main.controller.handler.main.n99.HouseCardDetailHandler;
import com.mama.server.main.controller.handler.main.n99.HouseCardListHandler;
import com.mama.server.main.controller.handler.main.n99.HouseCardOrderListHandler;
import com.mama.server.main.controller.handler.main.n99.N99OrderHandler;
import com.mama.server.main.controller.handler.main.n99.ReceiveHouseCardHandler;
import com.mama.server.main.controller.handler.main.n99.UpdateShareStatusHandler;
import com.mama.server.main.controller.handler.merchant.AliPayAsyncNotifyHandler;
import com.mama.server.main.controller.handler.merchant.ChekShopOwnerStatusHandler;
import com.mama.server.main.controller.handler.merchant.QueryOrderStatusHandler;
import com.mama.server.main.controller.handler.merchant.UpdateOrderLiveDetailHandler;
import com.mama.server.util.Log;
import com.meidusa.fastjson.JSON;

@Controller
public class MainController extends BaseController {

	// interfaces
	@Autowired
	private LoginHandler loginHandler;
	@Autowired
	private MMWalletLoginHandler mmWalletLoginHandler;
	@Autowired
	private RegisterHandler registerHandler;
	@Autowired
	private SendVerifyCodeHandler sendVerifyCodeHandler;
	@Autowired
	private CheckVerifyCodeHandler checkVerifyCodeHandler;
	@Autowired
	private GetUserInfoHandler getUserInfoHandler;
	@Autowired
	private UpdateUserInfoHandler updateUserInfoHandler;
	@Autowired
	private GetCardInfoHandler getCardInfoHandler;
	@Autowired
	private CheckVipHandler checkVipHandler;
	@Autowired
	private VerifyIdentityHandler verifyIdentityHandler;
	@Autowired
	private UpdatePasswordHandler updatePasswordHandler;
	@Autowired
	private GetProvincesHandler getProvincesHandler;
	@Autowired
	private GetCitiesHandler getCitiesHandler;
	@Autowired
	private ModifyContactHandler modifyContactHandler;
	@Autowired
	private RemoveContactHandler removeContactHandler;
	@Autowired
	private GetContactsHandler getContactsHandler;
	@Autowired
	private GetHousesHandler getHousesHandler;
	@Autowired
	private GetHouseInfoHandler getHouseInfoHandler;
//	@Autowired
//	private CreateOrderHandler createOrderHandler;
	@Autowired
	private CancelOrderHandler cancelOrderHandler;
	@Autowired
	private GetOrdersHandler getOrdersHandler;
	@Autowired
	private GetOrderInfoHandler getOrderInfoHandler;
	@Autowired
	private AddCollectHandler addCollectHandler;
	@Autowired
	private RemoveCollectHandler removeCollectHandler;
	@Autowired
	private GetCollectsHandler getCollectsHandler;
	@Autowired
	private GetContactByIdHandler getContactByIdHandler;
	@Autowired
	private IsPhoneRegisteredHandler isPhoneRegisteredHandler;
	@Autowired
	private GetDevelopersHandler getDevelopersHandler;
	@Autowired
	private GetBuildingsHandler getBuildingsHandler;
	@Autowired
	private GetDeveloperInfoHandler getDeveloperInfoHandler;
	@Autowired
	private GetBuildingInfoHandler getBuildingInfoHandler;
	@Autowired
	private GetFlowsHandler getFlowsHandler;
	@Autowired
	private CheckHouseDateHandler checkHouseDateHandler;
	@Autowired
	private GetHousePriceHandler getHousePriceHandler;
	@Autowired
	private GetInvitedNumberHandler getInvitedNumberHandler;
	@Autowired
	private InsertOpenIdPhoneHandler insertOpenIdPhoneHandler;
	@Autowired
	private GetOpenIdPhoneHandler getOpenIdPhoneHandler;
	@Autowired
	private CheckPhoneHandler checkPhoneHandler;
	@Autowired
	private GetHotelCouponGroupsHandler getHotelCouponGroupsHandler;
	@Autowired
	private GetHotelCouponGroupByIdHandler getHotelCouponGroupByIdHandler;
	@Autowired
	private GetOrderPriceInfoHandler getOrderPriceInfoHandler;
	@Autowired
	private CreateOrderHandlerNew createOrderHandlerNew;
	@Autowired
	private AddHotelCouponExchangeHandler addHotelCouponExchangeHandler;
	@Autowired
	private GetHotelCouponExchangeDetailHandler getHotelCouponExchangeDetailHandler;
	@Autowired
	private GetHotelCouponExchangeHandler getHotelCouponExchangeHandler;
	@Autowired
	private CancelHotelCouponExchangeHandler cancelHotelCouponExchangeHandler;
	@Autowired
	private AcceptHotelCouponExchangeHandler acceptHotelCouponExchangeHandler;
	@Autowired
	private GetHotelCouponGiveListHandler getHotelCouponGiveListHandler;
	@Autowired
	private GetHotelCouponGiveDetailHandler getHotelCouponGiveDetailHandler;
	@Autowired
	private AddHotelCouponGiveReceiveHandler addHotelCouponGiveReceiveHandler;
	@Autowired
	private AddHotelCouponGiveHandler addHotelCouponGiveHandler;
	@Autowired
	private SendOrderPayCodeHandler sendOrderPayCodeHandler;
	@Autowired
	private DoShareHandler doShareHandler;

	@Autowired
	private GetMyContactsHandler getMyContactsHandler;
	@Autowired
	private ModifyMyContactsHandler modifyMyContactsHandler;
	@Autowired
	private InsertMyContactsHandler insertMyContactsHandler;
	@Autowired
	private GetMyContactsByIdHandler getMyContactsByIdHandler;
	@Autowired
	private RemoveMyContactsHandler removeMyContactsHandler;
	@Autowired
	private GetIsExistMyContactsHandler getIsExistMyContactsHandler;

	@Autowired
	private UpdateOrderLiveDetailHandler updateOrderLiveDetail;

	@Autowired
	private CreateActivityHandler createActivityHandler;
	@Autowired
	private GetLotteryRecordHandler getLotteryRecordHandler;
	@Autowired
	private InitActivityConfHandler initActivityConfHandler;
	@Autowired
	private InitMemberInfoHandler initMemberInfoHandler;
	@Autowired
	private AddOrDeleCollectHandler addOrDeleCollectHandler;
	@Autowired
	private GetRecordMemberHandler getRecordMemberHandler;
	@Autowired
	private GetRecordAllMemberHandler getRecordAllMemberHandler;
	@Autowired
	private GetHousesSearchHandler getHousesSearchHandler;
	
	@Autowired
	private AppHouseSearchHandler appHouseSearchHandler;
	@Autowired
	private PutHouseTagsToRedisHandler putTagsToRedisHandler;
	@Autowired
	private GetOrderCommentHandler getOrderCommentHandler;
	@Autowired
	private StickOrderCommnetHandler stickOrderCommnetHandler;
	
	@Autowired
	private GetHouseCommentsHandler getHouseCommentsHandler;
	@Autowired
	private ActivityEnrollAddHandler activityEnrollAddHandler;
	@Autowired
	private DoReviewCommentHandler doReviewCommentHandler;

	@Autowired
	private AddOrderCommentHandler addOrderCommentHandler;
	@Autowired 
	private GetCommentHandler getCommentHandler;
	
	@Autowired
	private QueryOrderStatusHandler queryOrderStatusHandler;
	@Autowired
	private CheckEnrollHandler checkEnrollHandler;
	@Autowired
	private GetCollectsPageHandler getCollectsPageHandler;
	
	@Autowired
	private GetHouseDetailHandler getHouseDetailHandler;
	
	@Autowired
	private GetVersionByParHandler getVersionByParHandler;
	@Autowired
	private AppCreateOrderHandler appCreateOrderHandler;
	
	@Autowired
	private DelOrderCommentHandler delOrderCommentHandler;
	@Autowired
	private GetOrderCommentByOrderIdHandler getOrderCommentByOrderIdHandler;
	@Autowired
	private ClickFarmingOrderHandler clickFarmingOrderHandler;
	@Autowired
	private GetHouseDetailSearchHandler getHouseDetailSearchHandler;
	@Autowired
	private GetIndexSpecialsHousesHandler getIndexSpecialsHousesHandler;
	@Autowired
	private GetTopicActivitysHandler getTopicActivitysHandler;
	@Autowired
	private GetSpecialsHousesHandler getSpecialsHousesHandler;
	@Autowired
	private GetTopicsHandler getTopicsHandler;
	@Autowired
	private GetTopicHousesHandler getTopicHousesHandler;
	@Autowired
	private KWSearchHandler kwSearchHandler;
	@Autowired
	private SendOrderMsgHandler sendOrderMsgHandler;
	
	@Autowired
    private ChekShopOwnerStatusHandler chekShopOwnerStatus;
    @Autowired
    private GetShuaDanOrdersHandler getShuaDanOrders;
    @Autowired
    private AppVersionUpdateHandler appVersionUpdateHandler;
    @Autowired
    private GetRecommodHandler getRecommodHandler;
    
    @Autowired
    private AliPayAsyncNotifyHandler aliPayAsyncNotify;
    @Autowired
    private AppAddHouseCardHandler appAddHouseCardHandler;
    @Autowired
    private N99OrderHandler n99OrderHandler;
    
    @Autowired
    private HouseCardOrderListHandler houseCardOrderList;
    
    @Autowired
    private EarnedHouseCardListHandler earnedHouseCardList;
    
    @Autowired
    private ExchangedHouseCardListHandler exchangedHouseCardList;
    
    @Autowired
    private HouseCardListHandler houseCardList;
    
    @Autowired
    private HouseCardDetailHandler houseCardDetail;
    
    @Autowired
    private GiveHouseCardHandler giveHouseCard;
    
    @Autowired
    private ReceiveHouseCardHandler receiveHouseCard;
    
    @Autowired
    private UpdateShareStatusHandler updateShareStatus;
    
    @Autowired
    private GetRangeHousesHandler getRangeHousesHandler;
    
	private static Map<String, BaseHandler> interfaceMap = null;

	@Override
	protected void initInterfaceMap() {
		if (interfaceMap == null) {
			interfaceMap = new ConcurrentHashMap<String, BaseHandler>();
			interfaceMap.put("login", loginHandler);
			interfaceMap.put("mmWalletLogin", mmWalletLoginHandler);
			interfaceMap.put("register", registerHandler);
			interfaceMap.put("sendVerifyCode", sendVerifyCodeHandler);
			interfaceMap.put("checkVerifyCode", checkVerifyCodeHandler);
			interfaceMap.put("getUserInfo", getUserInfoHandler);
			interfaceMap.put("updateUserInfo", updateUserInfoHandler);
			interfaceMap.put("verifyIdentity", verifyIdentityHandler);
			interfaceMap.put("updatePassword", updatePasswordHandler);
			interfaceMap.put("getCardInfo", getCardInfoHandler);
			interfaceMap.put("checkVip", checkVipHandler);
			interfaceMap.put("getProvinces", getProvincesHandler);
			interfaceMap.put("getCities", getCitiesHandler);
			interfaceMap.put("modifyContact", modifyContactHandler);
			interfaceMap.put("removeContact", removeContactHandler);
			interfaceMap.put("getContacts", getContactsHandler);
			interfaceMap.put("getHouses", getHousesHandler);
			interfaceMap.put("getHouseInfo", getHouseInfoHandler);
			// interfaceMap.put("createOrder", createOrderHandler);
			interfaceMap.put("cancelOrder", cancelOrderHandler);
			interfaceMap.put("getOrders", getOrdersHandler);
			interfaceMap.put("getOrderInfo", getOrderInfoHandler);
			interfaceMap.put("addCollect", addCollectHandler);
			interfaceMap.put("removeCollect", removeCollectHandler);
			interfaceMap.put("getCollects", getCollectsHandler);
			interfaceMap.put("getContactById", getContactByIdHandler);
			interfaceMap.put("isPhoneRegistered", isPhoneRegisteredHandler);
			interfaceMap.put("getDevelopers", getDevelopersHandler);
			interfaceMap.put("getBuildings", getBuildingsHandler);
			interfaceMap.put("getDeveloperInfo", getDeveloperInfoHandler);
			interfaceMap.put("getBuildingInfo", getBuildingInfoHandler);
			interfaceMap.put("getFlows", getFlowsHandler);
			interfaceMap.put("checkHouseDate", checkHouseDateHandler);
			interfaceMap.put("getHousePrice", getHousePriceHandler);
			interfaceMap.put("getInvitedNumber", getInvitedNumberHandler);
			interfaceMap.put("insertOpenIdInfo", insertOpenIdPhoneHandler);
			interfaceMap.put("getOpenIdInfo", getOpenIdPhoneHandler);
			interfaceMap.put("checkPhone", checkPhoneHandler);
			interfaceMap.put("getHotelCouponGroups", getHotelCouponGroupsHandler);
			interfaceMap.put("getHotelCouponGroupById", getHotelCouponGroupByIdHandler);
			interfaceMap.put("getOrderPriceInfo", getOrderPriceInfoHandler);
			interfaceMap.put("createOrder", createOrderHandlerNew);
			interfaceMap.put("addHotelCouponExchange", addHotelCouponExchangeHandler);
			interfaceMap.put("getHotelCouponExchange", getHotelCouponExchangeHandler);
			interfaceMap.put("getHotelCouponExchangeDetail", getHotelCouponExchangeDetailHandler);
			interfaceMap.put("cancelHotelCouponExchange", cancelHotelCouponExchangeHandler);
			interfaceMap.put("acceptHotelCouponExchange", acceptHotelCouponExchangeHandler);
			interfaceMap.put("getHotelCouponGiveList", getHotelCouponGiveListHandler);
			interfaceMap.put("addHotelCouponGive", this.addHotelCouponGiveHandler);
			interfaceMap.put("getHotelCouponGiveDetail", this.getHotelCouponGiveDetailHandler);
			interfaceMap.put("addHotelCouponGiveReceive", this.addHotelCouponGiveReceiveHandler);
			interfaceMap.put("sendOrderPayCode", this.sendOrderPayCodeHandler);

			interfaceMap.put("getMyContacts", getMyContactsHandler);
			interfaceMap.put("modifyMyContacts", modifyMyContactsHandler);
			interfaceMap.put("insertMyContacts", insertMyContactsHandler);
			interfaceMap.put("getMyContactsById", getMyContactsByIdHandler);
			interfaceMap.put("removeMyContacts", removeMyContactsHandler);
			interfaceMap.put("getIsExistMyContacts", getIsExistMyContactsHandler);
			
			interfaceMap.put("updateOrderLiveDetail", this.updateOrderLiveDetail);
			interfaceMap.put("createActivity", this.createActivityHandler);
			interfaceMap.put("getLotteryRecord", this.getLotteryRecordHandler);
			interfaceMap.put("initActivityConf", this.initActivityConfHandler);
			interfaceMap.put("initMemberInfo", this.initMemberInfoHandler);
			interfaceMap.put("doShare", this.doShareHandler);
			interfaceMap.put("addOrDeleCollect", this.addOrDeleCollectHandler);
			interfaceMap.put("getRecordMember", this.getRecordMemberHandler);
			interfaceMap.put("getRecordAllMember", this.getRecordAllMemberHandler);
			interfaceMap.put("getHousesSearch", getHousesSearchHandler);
			interfaceMap.put("appHouseSearch", appHouseSearchHandler);
			interfaceMap.put("putTagsToRedis", putTagsToRedisHandler);
			interfaceMap.put("getOrderComment", getOrderCommentHandler);
			interfaceMap.put("stickOrderCommnet", stickOrderCommnetHandler);
			interfaceMap.put("getHouseComments", getHouseCommentsHandler);
			interfaceMap.put("addOrderComment", addOrderCommentHandler);
			interfaceMap.put("activityEnrollAdd", activityEnrollAddHandler);
			interfaceMap.put("doReviewComment", doReviewCommentHandler);
			interfaceMap.put("getComment", getCommentHandler);
			interfaceMap.put("queryOrderStatus", queryOrderStatusHandler);
			interfaceMap.put("checkEnroll", checkEnrollHandler);
			interfaceMap.put("getCollectsPage", getCollectsPageHandler);
			interfaceMap.put("getVersionByPar", getVersionByParHandler);
			interfaceMap.put("getHouseDetail", getHouseDetailHandler);
			interfaceMap.put("appCreateOrder", appCreateOrderHandler);
			interfaceMap.put("delOrderComment", delOrderCommentHandler);
			interfaceMap.put("getOrderCommentByOrderId", getOrderCommentByOrderIdHandler);
			interfaceMap.put("clickFarmingOrder", clickFarmingOrderHandler);
			interfaceMap.put("getHouseDetailSearch", getHouseDetailSearchHandler);
			interfaceMap.put("getIndexSpecialsHouses", getIndexSpecialsHousesHandler);
			interfaceMap.put("getTopicActivitys", getTopicActivitysHandler);
			interfaceMap.put("getSpecialsHouses", getSpecialsHousesHandler);
			interfaceMap.put("getTopics", getTopicsHandler);
			interfaceMap.put("getTopicHouses", getTopicHousesHandler);
			interfaceMap.put("kwSearch", kwSearchHandler);
			interfaceMap.put("sendOrderMsg", sendOrderMsgHandler);
			interfaceMap.put("chekShopOwnerStatus", chekShopOwnerStatus);
            interfaceMap.put("getShuaDanOrders", getShuaDanOrders);
            interfaceMap.put("aliPayAsyncNotify", aliPayAsyncNotify);
            interfaceMap.put("appVersionUpdate", appVersionUpdateHandler);
            interfaceMap.put("getRecommod", getRecommodHandler);
            interfaceMap.put("appAddHouseCard", appAddHouseCardHandler);
            interfaceMap.put("n99Order", n99OrderHandler);
            interfaceMap.put("houseCardOrderList", houseCardOrderList);
            interfaceMap.put("earnedHouseCardList", earnedHouseCardList);
            interfaceMap.put("exchangedHouseCardList", exchangedHouseCardList);
            interfaceMap.put("houseCardList", houseCardList);
            interfaceMap.put("houseCardDetail", houseCardDetail);
            interfaceMap.put("giveHouseCard", giveHouseCard);
            interfaceMap.put("receiveHouseCard", receiveHouseCard);
            interfaceMap.put("updateShareStatus", updateShareStatus);
            interfaceMap.put("getRangeHouses", getRangeHousesHandler);
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	public String index(HttpServletRequest req, @RequestBody Map<String, Object> inputMap) {
		Map<String, Object> outputMap = new HashMap<String, Object>();

		if (!inputMap.containsKey("interface") || !inputMap.containsKey("param")) {
			Log.SERVICE.error("no interface or param");
			genErrOutputMap(outputMap, "param error");
			// return Json.format(outputMap);
			return JSON.toJSONString(outputMap);
		}

		initInterfaceMap();

		try {
			String keyInterface = (String) inputMap.get("interface");
			HashMap<String, Object> keyParam = (HashMap<String, Object>) (inputMap.get("param"));

			BaseHandler handler = interfaceMap.get(keyInterface);
			if (handler == null) {
				Log.SERVICE.error("no handler for " + keyInterface);
				genErrOutputMap(outputMap, "interface not exists");
			} else {

				// HashMap<String, Object> resultMap = handler.handle(keyParam);
				// 解决handler单例问题， 使用 InvokeHandle 替代直接调用 handle()
				HashMap<String, Object> resultMap = handler.InvokeHandle(keyParam);
				Log.SERVICE.info(resultMap.toString());
				int code = (Integer) resultMap.get("code");
				String msg = (String) resultMap.get("msg");
				Object data = (Object) resultMap.get("data");
				if (code == ReturnCode.OK) {
					genSuccOutputMap(outputMap, data);
				} else {
					genErrOutputMapWithCode(outputMap, msg, code);
				}
			}
		} catch (Exception e) {
			Log.SERVICE.error(e.getMessage(), e);
			genErrOutputMap(outputMap, "param error");
		}

		try {
			// return Json.format(outputMap);
			System.out.println("=== 测试===");
			return JSON.toJSONString(outputMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
