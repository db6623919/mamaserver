package com.mmzb.house.app.web.action;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caucho.hessian.client.HessianProxyFactory;
import com.mama.server.common.entity.n99.SpecialsHouseVo;
import com.mama.server.common.util.PartnershipEnum;
import com.mmzb.banner.facade.BannerFacade;
import com.mmzb.banner.facade.entity.BannerEntity;
import com.mmzb.banner.facade.entity.BannerQueryRequest;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.util.JsonUtil;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.entity.CityEntity;
import com.mmzb.house.app.entity.HouseShopEntity;
import com.mmzb.house.app.vo.AppBannerVo;
import com.mmzb.house.app.vo.AppIndexVo;
import com.mmzb.house.app.vo.BannerVo;
import com.mmzb.house.app.vo.Expert;
import com.mmzb.house.app.vo.HouseVo;
import com.mmzb.house.app.vo.HousesVo;
import com.mmzb.house.app.vo.Invest;
import com.mmzb.house.app.vo.Recommend;
import com.mmzb.house.app.web.util.CommonUtil;


/**
 * app首页
 * @author whl
 *
 */
@Controller
public class IndexAction{
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	private Logger logger = LoggerFactory.getLogger(IndexAction.class);
	
	/**
	 * 首页信息展示
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/app/index", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseOut<AppIndexVo> getIndex(HttpServletRequest request,HttpServletResponse response, String service) {
		
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/index.htm");
		}
		AppIndexVo index = new AppIndexVo();
		try {
			//首页特价房
			List<HousesVo> specialsHouseList = getSpecialList();
			
			//热门城市列表
	     	List<CityEntity> cityList = getCityList();
	     	
	     	//热房推荐列表
	     	List<Recommend> recommendList = this.getReommendList();
	     	
	     	//旅居理财（旅居宝）广告列表
	     	List<Invest> investList = new ArrayList<Invest>();
	     	//旅居宝
	     	Invest invest1 = new Invest();
	     	invest1.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/cb45792a-d597-4ab4-b976-dc3e7fe2f1d6"));
	     	invest1.setPageUrl("http://m.88mmmoney.com/travelList.htm");
	     	investList.add(invest1);
	     	//筹生活
	     	Invest invest2 = new Invest();
	     	invest2.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/85c57c6c-0e94-4253-93e1-134fd284b240"));
	     	invest2.setPageUrl("http://m.mmsfang.com/waiting.htm?type=" + URLEncoder.encode("筹生活", "utf-8"));
	     	investList.add(invest2);
	     	
	     	//达人同行列表
	     	List<Expert> expertList = new ArrayList<Expert>();
	     	//手记
	     	Expert expert1 = new Expert();
	     	expert1.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/637089dc-fb91-4b26-b8e6-07c29365721d"));
	     	expert1.setPageUrl("http://m.mmsfang.com/travels.htm");
	     	expertList.add(expert1);
	     	//专线
	     	Expert expert2 = new Expert();
	     	expert2.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/1ed8bb2a-0e28-4fae-aafe-1e3b80f44153"));
	     	expert2.setPageUrl("http://m.mmsfang.com//lxj/toPage.htm");
	     	expertList.add(expert2);
	     	
	     	index.setDiscountHouses(specialsHouseList);
	     	index.setHotCities(cityList);
	     	index.setRecommends(recommendList);
	     	index.setInvests(investList);
	     	index.setExperts(expertList);
	     	String novice_tips = new String(appProperties.getNoviceTips().getBytes("ISO8859-1"),"UTF-8");
	     	index.setNoviceTips(novice_tips);
	     	String specialTips = new String(appProperties.getSpecialTips().getBytes("ISO8859-1"),"UTF-8");
	     	index.setSpecialTips(specialTips);
		} catch (Exception e){
			logger.error("调用轮播图查询接口出现异常", e);
			return new ResponseOut<AppIndexVo>(Constants.APP_FAILED,"首页接口调用异常",index);
		}
     	if (logger.isInfoEnabled()) {
		    logger.info("/app/index.htm is finished,params is {}.",JsonUtil.objectToString(index));
		 }
     	
   		return new ResponseOut<AppIndexVo>(Constants.APP_SUCCESSED,"",index);
    }

	private List<HousesVo> getSpecialList() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		com.alibaba.fastjson.JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getIndexSpecialsHouses");
		
		com.alibaba.fastjson.JSONObject dataObj = houseDetailJson.getJSONObject("data");
		com.alibaba.fastjson.JSONObject data = dataObj.getJSONObject("specialsHouseVo");
		SpecialsHouseVo specialsHouseVo = JsonUtil.jsonToObject(data.toJSONString(), SpecialsHouseVo.class);
		specialsHouseVo = consoHouseToSpecial(specialsHouseVo);
		HousesVo housesVo = conso2HousesVO(specialsHouseVo);
		List<HousesVo> list = new ArrayList<HousesVo>();
		list.add(housesVo);
		return list;
	}

	//热门城市列表
	private List<CityEntity> getCityList()
	{
		List<CityEntity> cityList = new ArrayList<CityEntity>();
     	CityEntity city1 = new CityEntity();
     	city1.setCityId(3);
     	city1.setCityName("大理");
     	//city1.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/e2188e0b-a1c7-48c0-9e7a-41aa61247afd"));
     	city1.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/b1a66b94-d053-44e3-9033-8019dc625d87"));
     	city1.setEnglishName("DA LI");
     	cityList.add(city1);
     	
     	CityEntity city2 = new CityEntity();
     	city2.setCityId(30);
     	city2.setCityName("凤凰");
     	//city2.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/0aa9383f-4e30-43c8-a620-e7af4adae0b7"));
     	city2.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/166855f5-2f1e-4dd5-81a6-e1cd0dadabba"));
     	city2.setEnglishName("FENG HUANG");
     	cityList.add(city2);
     	
     	CityEntity city3 = new CityEntity();
     	city3.setCityId(1);
     	city3.setCityName("三亚");
     	//city3.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/64294ae5-796b-4438-8ac2-b024adb3559e"));
     	city3.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/9c9ae40e-91ed-46a0-ae2b-346723708db0"));
     	city3.setEnglishName("SAN YA");
     	cityList.add(city3);
     	
     	CityEntity city4 = new CityEntity();
     	city4.setCityId(37);
     	city4.setCityName("桂林");
     	//city4.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/b9d8bb56-0e8d-4987-a807-a93e910b030d"));
     	city4.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/6c52511d-97df-4535-8655-b106442d7d4d"));
     	city4.setEnglishName("GUI LIN");
     	cityList.add(city4);
		return cityList;
	}
	
	//热房推荐列表
	private List<Recommend> getReommendList() {
     	List<Recommend> recommendList = new ArrayList<Recommend>();
     	//一、消暑首选
     	Recommend recommend1 = new Recommend();
     	recommend1.setColumnName("暖冬首选");
     	List<HouseVo> houseList = new ArrayList<HouseVo>();
     	HouseVo house1 = new HouseVo();
     	house1.setHouseId(339);
     	house1.setHouseName("大理双廊青云客栈海景大床房");
     	house1.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/6bc5750e-cb74-4568-8efb-d187479702cd"));
     	house1.setPrice(540);
     	house1.setHouseUnit("起/晚");
     	house1.setDescription("1室1卫 1床 宜住2人");
     	house1.setSubDescription("不止艳遇，更有风月");
     	houseList.add(house1);
     	HouseVo house2 = new HouseVo();
     	house2.setHouseId(396);
     	house2.setHouseName("丽江雍舍客栈豪华阳光大床房");
     	house2.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/35998829-df35-41c9-bb38-d222a7020da7"));
     	house2.setPrice(488);
     	house2.setHouseUnit("起/晚");
     	house2.setDescription("1室1卫 1床 宜住2人");
     	house2.setSubDescription("丽江古城核心地段，远眺雪山，对饮小酌");
     	houseList.add(house2);
     	HouseVo house3 = new HouseVo();
     	house3.setHouseId(490);
     	house3.setHouseName("海洋微风度假民宿双床房");
     	house3.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/c81f45a2-e5d9-4842-96f6-3907ac65750b"));
     	house3.setPrice(680);
     	house3.setHouseUnit("起/晚");
     	house3.setDescription("1室1卫 2床 宜住2人");
     	house3.setSubDescription("远离城市纷扰，品海鲜、吹海风、踏海浪");
     	houseList.add(house3);
     	recommend1.setHouses(houseList);
     	recommendList.add(recommend1);
     	//二、亲子乐园
     	Recommend recommend2 = new Recommend();
     	recommend2.setColumnName("亲子乐园");
     	houseList = new ArrayList<HouseVo>();
     	house1 = new HouseVo();
     	house1.setHouseId(224);
     	house1.setHouseName("澜渤湾公寓长隆店家庭套房");
     	house1.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/fe7128fa-3ebb-40c6-b0ef-09090c2c556f"));
     	house1.setPrice(268);
     	house1.setHouseUnit("起/晚");
     	house1.setDescription("2室1厅1卫 2床 宜住4人");
     	house1.setSubDescription("广州长隆景区旁，最居家的复式套房");
     	houseList.add(house1);
     	house2 = new HouseVo();
     	house2.setHouseId(363);
     	house2.setHouseName("深圳官湖花木蓝客栈-木楼两房一厅");
     	house2.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/5a2c486c-f9ce-4b3c-823a-0e8c4f99085c"));
     	house2.setPrice(869);
     	house2.setHouseUnit("起/晚");
     	house2.setDescription("2室1厅2卫 2床 宜住4人");
     	house2.setSubDescription("设计师客栈，深圳海边家庭聚会首选");
     	houseList.add(house2);
     	house3 = new HouseVo();
     	house3.setHouseId(259);
     	house3.setHouseName("凤凰坡山公馆梅大床房");
     	house3.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/0a27984d-6cd8-451e-8eec-8ae862ecfa7b"));
     	house3.setPrice(328);
     	house3.setHouseUnit("起/晚");
     	house3.setDescription("1室1卫 1床 宜住2人");
     	house3.setSubDescription("凤凰古城核心位置，品读青山碧水");
     	houseList.add(house3);
     	recommend2.setHouses(houseList);
     	recommendList.add(recommend2);
     	
     	//三、山水之间
     	Recommend recommend3 = new Recommend();
     	recommend3.setColumnName("山水之间");
     	houseList = new ArrayList<HouseVo>();
     	house1 = new HouseVo();
     	house1.setHouseId(306);
     	house1.setHouseName("海西海景酒店海景双标房");
     	house1.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/2c066e03-343b-4f32-b19f-e9c0199a8b9b"));
     	house1.setPrice(780);
     	house1.setHouseUnit("起/晚");
     	house1.setDescription("1室1卫 2床 宜住2人");
     	house1.setSubDescription("苍洱之间，让心灵住下");
     	houseList.add(house1);
     	house2 = new HouseVo();
     	house2.setHouseId(474);
     	house2.setHouseName("阳朔西街江景楼观景大床房");
     	house2.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/7d444fe1-1aa0-4fa0-939e-049572b8bba6"));
     	house2.setPrice(158);
     	house2.setHouseUnit("起/晚");
     	house2.setDescription("1室0厅1卫 1床 宜住2人");
     	house2.setSubDescription("阳朔西街美房，饱览漓江美景");
     	houseList.add(house2);
     	house3 = new HouseVo();
     	house3.setHouseId(143);
     	house3.setHouseName("亚龙湾吉吉岛度假洋房别墅私密一室一厅");
     	house3.setImgUrl(CommonUtil.compressImageUrl("http://file.88mmmoney.com/f5cab3c8-e19c-4d6f-909d-3d30426b8df3"));
     	house3.setPrice(338);
     	house3.setHouseUnit("起/晚");
     	house3.setDescription("1室1厅1卫 1床 宜住2人");
     	house3.setSubDescription("亚龙湾度假区，您的专属度假洋房");
     	houseList.add(house3);
     	recommend3.setHouses(houseList);
     	recommendList.add(recommend3);
     	//house1.setIsFavor();
     	
     	return recommendList;
	}
	
	/**
	 * 首页banner
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/app/banner", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<AppBannerVo> getBanner() {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/banner.htm");
		}
		//return to APP vo
		AppBannerVo bannersVo = new AppBannerVo();
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			BannerFacade facade = (BannerFacade) factory.create(BannerFacade.class, appProperties.getBannerUrl());
			BannerQueryRequest request = new BannerQueryRequest();
			request.setPlatform(appProperties.getHouse_platform());
			request.setSource(appProperties.getHouse_resoure());
			request.setTerminal(1);
			List<BannerEntity> list = facade.queryBanners(request);
			//转化为app接口vo
			List<BannerVo> bannerList = this.beanToVo(list);
			bannersVo.setBanners(bannerList);
		} catch (Exception e) {
			logger.error("调用轮播图查询接口出现异常", e);
			return new ResponseOut<AppBannerVo>(Constants.APP_FAILED,"调用轮播图查询接口出现异常",bannersVo);
		}
		if (logger.isInfoEnabled()) {
		    logger.info("/app/index.htm is finished,params is {}.",JsonUtil.objectToString(bannersVo));
		}
		return new ResponseOut<AppBannerVo>(Constants.APP_SUCCESSED,"",bannersVo);
	}
	
	private List<BannerVo> beanToVo(List<BannerEntity> list) {
		List<BannerVo> bannerList = new ArrayList<BannerVo>();
		for(int i = 0 ; i < list.size() ; i ++ ) {
			BannerVo bannerVo = new BannerVo();
			bannerVo.setBannerId(list.get(i).getId());
			bannerVo.setImageUrl(list.get(i).getImg_url());
			bannerVo.setPageUrl(list.get(i).getLink());
			bannerList.add(bannerVo);
		}
		return bannerList;
	}
	
	private SpecialsHouseVo consoHouseToSpecial(SpecialsHouseVo sHouseVo) throws Exception{
		//合作关系
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shopId", String.valueOf(sHouseVo.getShopId()));
		com.alibaba.fastjson.JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot()+"/customer", "getHouseShopByPar");
		com.alibaba.fastjson.JSONObject dataObj = houseDetailJson.getJSONObject("data");
		if (null!=dataObj) {
			com.alibaba.fastjson.JSONObject data = dataObj.getJSONObject("houseShop");
			HouseShopEntity houseShop = JsonUtil.jsonToObject(data.toJSONString(), HouseShopEntity.class);
			if (PartnershipEnum.MemberInn.getCode().equals(houseShop.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
			}else if (PartnershipEnum.Holding.getCode().equals(houseShop.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.Holding.getMessage());//控股
			}else if (PartnershipEnum.GuesthouseInn.getCode().equals(houseShop.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
			}else if (PartnershipEnum.DepthCooperation.getCode().equals(houseShop.getPartnership())) {
				sHouseVo.setPartnership(PartnershipEnum.DepthCooperation.getMessage());//深度合作
			}else {
				sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//默认会员客栈
			}
		}else {
			sHouseVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//默认会员客栈
		}
		

		if (sHouseVo.getShopId()!=0&&sHouseVo.getMarket_price()>0) {
			float  discount = (float)sHouseVo.getMemTotalAmt()*10/(float)sHouseVo.getMarket_price();
			BigDecimal   b   =   new   BigDecimal(discount);  
			float   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();  
			sHouseVo.setDiscount(f1);
		}
		
		return sHouseVo;
	}
	
	private HousesVo conso2HousesVO(SpecialsHouseVo specialsHouseVo) {
		HousesVo housesVo = new HousesVo();
		housesVo.setHouseId(specialsHouseVo.getHouseId());
		housesVo.setPrice(String.valueOf(specialsHouseVo.getMemTotalAmt()));
		housesVo.setHouseName(specialsHouseVo.getHouseName());
		housesVo.setHouseUnit("起/晚");
		housesVo.setImgUrl(specialsHouseVo.getImageUrl());
		String market_price = "";
		if (specialsHouseVo.getMarket_price()>0) {
			market_price = String.valueOf(specialsHouseVo.getMarket_price());
		}
		housesVo.setMarketPrice(market_price);
		housesVo.setPrice(String.valueOf(specialsHouseVo.getMemTotalAmt()));
		housesVo.setSpecialTag(specialsHouseVo.getPartnership());
		housesVo.setDescription(specialsHouseVo.getRoom()+"室"+specialsHouseVo.getOffice()+"厅"+specialsHouseVo.getToilet()+"卫"+specialsHouseVo.getBeds()+"床 宜住"+specialsHouseVo.getPersonNum()+"人");
		String discount = "";
		if (specialsHouseVo.getDiscount()>0) {
			discount = String.valueOf(specialsHouseVo.getDiscount())+"折";
		}
		housesVo.setDiscountDescription(discount);
		
		housesVo.setSpecial_count(Constants.SPECIAL_COUNT);
		return housesVo;
	}
	
}
