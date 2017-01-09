package com.mmzb.house.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.common.entity.KWSearchResultEntityH5;
import com.mama.server.common.entity.RecommodHouse;
import com.mama.server.common.util.PartnershipEnum;
import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.core.common.vo.Response;
import com.mmzb.house.web.core.common.vo.ResponseOut;
import com.mmzb.house.web.model.houses.RecommondHouseVo;

/**
 * 推荐客栈
 * @author dengbiao
 *
 */
@Controller
public class RecommendAction extends BaseAction {

    @Resource(name="appProperties")
	private AppProperties appProperties;
    
    private static Logger logger = LoggerFactory.getLogger(HousesAction.class);
 
	@RequestMapping(value = "/house/recommend_list", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseOut<List<RecommondHouseVo>> recommend_list(){
		logger.info("start to run recommend_list");
		
		try 
		{
			Map<String, Object> postDataMap = new HashMap<String, Object>();
			JSONObject result = HttpClientPostMethod.httpDataReqUrl(postDataMap, appProperties.getRequestRoot(), "getRecommod");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			if(code.equals(Contants.CODE_SUCCESSED)){
				JSONObject jsonData = result.getJSONObject("data");
				com.meidusa.fastjson.JSONArray array = jsonData.getJSONArray("list");
				List<RecommodHouse> list = new ArrayList<RecommodHouse>();
				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					RecommodHouse recommodHouse = new RecommodHouse();
					recommodHouse.setHouseId(Integer.parseInt(jsonObject.getString("houseId")));
					recommodHouse.setHouseshop_id(Integer.parseInt(jsonObject.getString("houseshop_id")));
					recommodHouse.setMarket_price(Integer.parseInt(jsonObject.getString("market_price")));
					recommodHouse.setMemTotalAmt(Integer.parseInt(jsonObject.getString("memTotalAmt")));
					recommodHouse.setPartnership(jsonObject.getString("partnership"));
					recommodHouse.setRoom(Integer.parseInt(jsonObject.getString("room")));
					recommodHouse.setSpecials_stauts(Integer.parseInt(jsonObject.getString("specials_stauts")));
					
					net.sf.json.JSONObject jObject = net.sf.json.JSONObject.fromObject(jsonObject.getString("summaryInfo"));
					String name = jObject.getString("houseName");
					recommodHouse.setName(name);
					
					net.sf.json.JSONObject jbject = net.sf.json.JSONObject.fromObject(jsonObject.getString("showDetail"));
					String imageUrl = jbject.getString("introductionImg").split(",")[0];
					recommodHouse.setImageUrl(imageUrl);
					list.add(recommodHouse);
				}
				List<RecommondHouseVo> recommondHouseList = console2Vo(list);
				return new ResponseOut<List<RecommondHouseVo>>(Contants.SUCCESSED, "", recommondHouseList);
			}else{
				return new ResponseOut<List<RecommondHouseVo>>(Contants.FAILED, msg);
			}
			
		} 
		catch (Exception e)
		{
			logger.error("Failed to search by keyword", e);
		}
		
		return new ResponseOut<List<RecommondHouseVo>>(Contants.FAILED, "未知异常");
	}

	private List<RecommondHouseVo> console2Vo(List<RecommodHouse> list) {
		List<RecommondHouseVo> rhList = new ArrayList<RecommondHouseVo>();
		for (RecommodHouse recommodHouse:list) {
			RecommondHouseVo recommondHouseVo = new RecommondHouseVo();
			recommondHouseVo.setHouseId(recommodHouse.getHouseId());
			recommondHouseVo.setPrice(recommodHouse.getMemTotalAmt());
			recommondHouseVo.setMarketPrice(recommodHouse.getMarket_price());
			recommondHouseVo.setSpecials_stauts(recommodHouse.getSpecials_stauts());
			recommondHouseVo.setName(recommodHouse.getName());
			recommondHouseVo.setImageUrl(recommodHouse.getImageUrl());
			
			String room = String.valueOf(recommodHouse.getRoom());
			if (room!=null && !"".equals(room)) {
				recommondHouseVo.setDescription(room.substring(0, 1)+"室"+room.substring(1, 2)+"厅"+room.substring(2, 3)+"卫  "+room.substring(3, 4)+"床"
						+room.substring(6, 7)+"人");
			}
			//合作关系
			if (PartnershipEnum.MemberInn.getCode().equals(recommodHouse.getPartnership())) {
				recommondHouseVo.setSpecialTag(PartnershipEnum.MemberInn.getMessage());
			}else if (PartnershipEnum.Holding.getCode().equals(recommodHouse.getPartnership())) {
				recommondHouseVo.setSpecialTag(PartnershipEnum.Holding.getMessage());//控股
			}else if (PartnershipEnum.GuesthouseInn.getCode().equals(recommodHouse.getPartnership())) {
				recommondHouseVo.setSpecialTag(PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
			}else if (PartnershipEnum.DepthCooperation.getCode().equals(recommodHouse.getPartnership())) {
				recommondHouseVo.setSpecialTag(PartnershipEnum.DepthCooperation.getMessage());//深度合作
			}else {
				recommondHouseVo.setSpecialTag(PartnershipEnum.MemberInn.getMessage());
			}
			
			//折扣
			if (recommodHouse.getHouseshop_id()!=0&&recommodHouse.getMarket_price()>0) {
				float  discount = (float)recommodHouse.getMemTotalAmt()*10/(float)recommodHouse.getMarket_price();
				BigDecimal   b   =   new   BigDecimal(discount);  
				float   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();  
				recommondHouseVo.setDiscountDescription(f1+"折");
			}
			
			rhList.add(recommondHouseVo);
		}
		
		return rhList;
	}
	
}