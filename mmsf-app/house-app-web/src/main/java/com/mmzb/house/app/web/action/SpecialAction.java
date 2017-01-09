package com.mmzb.house.app.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.common.entity.n99.N99HousePagerVo;
import com.mama.server.common.entity.n99.N99Info;
import com.mama.server.common.entity.n99.SpecialHousesPagerEntity;
import com.mama.server.common.entity.n99.SpecialsHouseVo;
import com.mama.server.common.entity.n99.Tabs;
import com.mama.server.common.util.PartnershipEnum;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.util.JsonUtil;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.entity.HouseShopEntity;
import com.mmzb.house.app.vo.HousesVo;
import com.mmzb.house.app.vo.Page;
import com.mmzb.house.app.vo.SpecialHousePagerVo;

/**
 * 特价房源
 * @author dengbiao
 *
 */
@Controller
@RequestMapping(value = "app")
public class SpecialAction {

	//记录日志
	private final Logger logger = LoggerFactory.getLogger(SpecialAction.class);
	
	//配置文件
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	@RequestMapping(value = "/n99Info", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseOut<N99Info> n99Info(HttpServletRequest request, String accessToken){
		if (logger.isInfoEnabled())
		{
			logger.info("SpecialAction get the request to n99Info, param is {}");
		}
		
		ResponseOut<N99Info> response = new ResponseOut<N99Info>();
		
		N99Info n99Info = new N99Info();
		List<Tabs> list = new ArrayList<Tabs>();
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("currentPage", -1);
			param.put("pageSize", -1);
			Response<SpecialHousesPagerEntity> resp = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getSpecialsHouses",SpecialHousesPagerEntity.class);
			Set<String> n99set = getN99HouseList(resp.getData().getList());
			for (String str:n99set) {
				Tabs tabs = new Tabs();
				tabs.setTabName(str);
				if(str.substring(0, str.length()-2).equals("")){
					tabs.setTabId("0");
				}else {
					tabs.setTabId(str.substring(0, str.length()-2));
				}
				
				list.add(tabs);
			}
			Collections.sort(list, new TabIdComparator());

			n99Info.setShareTitle("给自己放个假  住五心级民宿");
			n99Info.setShareSubTitle(" 妈妈美宿19家精品海景客栈");
			n99Info.setShareUrl("http://m.mmsfang.com/activity/special_offer_99/index.htm");
			n99Info.setShareImgURL("");
			
			n99Info.setTabs(list);	
			response.setCode(Constants.APP_SUCCESSED);
			response.setMessage(resp.getMsg());
			response.setResult(n99Info);
		}
		catch (Exception e) {
			response.setCode(Constants.APP_FAILED);
			response.setMessage(e.getMessage());
			logger.error("failed to get n99Info info", e);
		}
		return response;
	}
	
	   // 自定义比较器：按tabId  
    static class TabIdComparator implements Comparator {  
        public int compare(Object object1, Object object2) {// 实现接口中的方法  
            Tabs p1 = (Tabs) object1; // 强制转换  
            Tabs p2 = (Tabs) object2;  
            return new Double(p1.getTabId()).compareTo(new Double(p2.getTabId()));  
        }  
    } 
    
	@RequestMapping(value = "/n99HouseList", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseOut<N99HousePagerVo> n99HouseList(HttpServletRequest request, String accessToken){
		if (logger.isInfoEnabled())
		{
			logger.info("SpecialAction get the request to n99HouseList, param is {}", request.getParameter("tabId"));
		}
		
		ResponseOut<N99HousePagerVo> response = new ResponseOut<N99HousePagerVo>();
		String currentPage = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String priceRange = request.getParameter("tabId");//类型价位
		
		N99HousePagerVo n99HousePagerVo = new N99HousePagerVo();
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("currentPage", Integer.parseInt(currentPage));
			param.put("pageSize", Integer.parseInt(pageSize));
			param.put("priceRange", priceRange);
			Response<SpecialHousesPagerEntity> resp = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getRangeHouses",SpecialHousesPagerEntity.class);
			List<HousesVo> specialsHouseList = getSpecialList(resp.getData().getList());
			List<com.mama.server.common.entity.n99.HousesVo> list = conso2HousesVO(specialsHouseList);
			n99HousePagerVo.setN99Houses(list);
			
    			//设置分页信息
			com.mama.server.common.util.Page pager = new com.mama.server.common.util.Page();
    			pager.setItemCount(resp.getData().getItemCount());
    			pager.setPageIndex(Integer.parseInt(currentPage));
    			pager.setPageSize(Integer.parseInt(pageSize));
    			n99HousePagerVo.setPager(pager);
    			
    			n99HousePagerVo.setShareTitle("给自己放个假  住五心级民宿");
    			n99HousePagerVo.setShareSubTitle(" 妈妈美宿19家精品海景客栈");
    			n99HousePagerVo.setShareUrl("http://m.mmsfang.com/activity/special_offer_99/index.htm");
    			n99HousePagerVo.setShareImgURL("http://file.88mmmoney.com/7414bb59-e744-4478-b757-e434bbccf700");
    			
			response.setCode(Constants.APP_SUCCESSED);
			response.setMessage(resp.getMsg());
			response.setResult(n99HousePagerVo);
		}
		catch (Exception e) {
			response.setCode(Constants.APP_FAILED);
			response.setMessage(e.getMessage());
			logger.error("failed to get n99HouseList info", e);
		}
		return response;
	}	
		
	@RequestMapping(value = "/discountHouseList", method = {RequestMethod.POST})
	@ResponseBody
	public ResponseOut<SpecialHousePagerVo> discountHouseList(HttpServletRequest request, String accessToken){
		if (logger.isInfoEnabled())
		{
			logger.info("SpecialAction get the request to discountHouseList, param is {}", request.getParameter("pageIndex"));
		}
		
		ResponseOut<SpecialHousePagerVo> response = new ResponseOut<SpecialHousePagerVo>();
		String currentPage = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		SpecialHousePagerVo specialHousePager = new SpecialHousePagerVo();
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("currentPage", Integer.parseInt(currentPage));
			param.put("pageSize", Integer.parseInt(pageSize));
			Response<SpecialHousesPagerEntity> resp = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getSpecialsHouses",SpecialHousesPagerEntity.class);
			List<HousesVo> specialsHouseList = getSpecialList(resp.getData().getList());
			
			specialHousePager.setDiscountHouses(specialsHouseList);
			int relatedInn = resp.getData().getItemCount();
            specialHousePager.setRelatedInn(relatedInn);
    			//设置分页信息
    			Page pager = new Page();
    			pager.setItemCount(resp.getData().getItemCount());
    			pager.setPageIndex(Integer.parseInt(currentPage));
    			pager.setPageSize(Integer.parseInt(pageSize));
    			specialHousePager.setPager(pager);
			
    			specialHousePager.setShareTitle("给自己放个假  住五心级民宿");
    			specialHousePager.setShareSubTitle(" 妈妈美宿19家精品海景客栈");
    			specialHousePager.setShareUrl("http://m.mmsfang.com/activity/special_offer_99/index.htm");
    			specialHousePager.setShareImgURL("");
    			specialHousePager.setBannerImg("http://file.88mmmoney.com/c3978b16-65d8-46ce-a5ba-d0ad8ad27f04");
    			
			response.setCode(Constants.APP_SUCCESSED);
			response.setMessage(resp.getMsg());
			response.setResult(specialHousePager);
		}
		catch (Exception e) 
		{
			response.setCode(Constants.APP_FAILED);
			response.setMessage(e.getMessage());
			logger.error("failed to get discountHouseList info", e);
		}
		
		return response;
	}
	
	private List<HousesVo> getSpecialList(List<SpecialsHouseVo> list) throws Exception{
		List<HousesVo> housesVosList = new ArrayList<HousesVo>();
		for (SpecialsHouseVo specialsHouseVo:list) {
			specialsHouseVo = consoHouseToSpecial(specialsHouseVo);
			HousesVo housesVo = conso2HousesVO(specialsHouseVo);
			housesVosList.add(housesVo);
		}
		return housesVosList;
	}
	
	private Set<String> getN99HouseList(List<SpecialsHouseVo> list) throws Exception{
		Set<String>  n99Set=new HashSet<String>();
		for (SpecialsHouseVo specialsHouseVo:list) {
			if (specialsHouseVo.getMemTotalAmt()>=99) {
				String price = String.valueOf(specialsHouseVo.getMemTotalAmt());
				price = price.substring(price.length()-2, price.length());
				if (price.equals("99")) {
					n99Set.add(String.valueOf(specialsHouseVo.getMemTotalAmt()));
				}
				
			}
		}
		return n99Set;
	}
	
	private SpecialsHouseVo consoHouseToSpecial(SpecialsHouseVo sHouseVo) throws Exception{
		//合作关系
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shopId", String.valueOf(sHouseVo.getShopId()));
		com.alibaba.fastjson.JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot()+"/customer", "getHouseShopByPar");
		com.alibaba.fastjson.JSONObject dataObj = houseDetailJson.getJSONObject("data");
		if (dataObj!=null) {
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
		
		
		
		return housesVo;
	}
	
	private List<com.mama.server.common.entity.n99.HousesVo> conso2HousesVO(List<HousesVo> list) {
		List<com.mama.server.common.entity.n99.HousesVo> hList = new ArrayList<com.mama.server.common.entity.n99.HousesVo>();
		for (HousesVo housesVo:list) {
			com.mama.server.common.entity.n99.HousesVo houses = new com.mama.server.common.entity.n99.HousesVo();
			houses.setDescription(housesVo.getDescription());
			houses.setDiscountDescription(housesVo.getDiscountDescription());
			houses.setHouseId(housesVo.getHouseId());
			houses.setHouseName(housesVo.getHouseName());
			houses.setHouseUnit(housesVo.getHouseUnit());
			houses.setImgUrl(housesVo.getImgUrl());
			houses.setMarketPrice(housesVo.getMarketPrice());
			houses.setPrice(housesVo.getPrice());
			houses.setSpecial_count(housesVo.getSpecial_count());
			houses.setSpecialTag(housesVo.getSpecialTag());
			hList.add(houses);
		}
		
		return hList;
	}
}