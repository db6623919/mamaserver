package com.mama.server.main.controller.handler.customerservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.BuildingPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.TradeArea;
import com.mama.server.main.dao.model.mongodb.HouseDetailPo;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.vo.Facilities;
import com.mama.server.main.dao.vo.HouseImage;
import com.mama.server.main.dao.vo.KeyWordVo;
import com.mama.server.main.dao.vo.Other;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.RoomProperty;
import com.mama.server.main.dao.vo.Supporting;
import com.mama.server.main.vo.HouseDetailVo;
import com.mama.server.util.CommonUtil;
import com.mama.server.util.Json;
import com.mama.server.util.Log;
import com.meidusa.fastjson.JSONObject;

@Component
public class UpdateHouseHandler extends BaseHandler {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	String houseId 		= mainService.checkPostParam(param.get("houseId"));
        	String devId 		= mainService.checkPostParam(param.get("devId"));
        	String bldId 		= mainService.checkPostParam(param.get("bldId"));
        	String cityId 		= mainService.checkPostParam(param.get("cityId"));
        	String type 		= mainService.checkPostParam(param.get("type"));
        	String mark 		= mainService.checkPostParam(param.get("mark"));
        	String flag 		= mainService.checkPostParam(param.get("flag"));
        	String room 		= mainService.checkPostParam(param.get("room"));
        	String theme 		= mainService.checkPostParam(param.get("theme"));
        	String luxury 		= mainService.checkPostParam(param.get("luxury"));
        	String freezeAmt 	= mainService.checkPostParam(param.get("freezeAmt"));
        	String totalAmt 	= mainService.checkPostParam(param.get("totalAmt"));
        	String memFreezeAmt = mainService.checkPostParam(param.get("memFreezeAmt"));
        	String memTotalAmt 	= mainService.checkPostParam(param.get("memTotalAmt"));
        	String market_price = mainService.checkPostParam(param.get("market_price"));
        	String summaryInfo 	= mainService.checkPostParam(param.get("summaryInfo"));
        	String showDetail 	= mainService.checkPostParam(param.get("showDetail"));
        	String removed	= mainService.checkPostParam(param.get("removed"));
            String totalRoomNum = mainService.checkPostParam(param.get("totalRoomNum"));
            String sort = mainService.checkPostParam(param.get("sort"));
            String recommendTime = mainService.checkPostParam(param.get("recommendTime"));
            String houseshop_id = mainService.checkPostParam(param.get("houseshop_id"));
            String specials_stauts = mainService.checkPostParam(param.get("specials_stauts"));
            String special_flag = mainService.checkPostParam(param.get("special_flag"));
        	
            if (houseId == null || houseId == "") {
            	Log.SERVICE.error("[UpdateHouseHandler]param is empty : houseId("+houseId+")");
            	genErrOutputMapWithCode("param is empty : houseId("+houseId+")", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
          //房源信息
            Map<String, Object> houseMap = (Map<String, Object>) param.get("summaryInfo");
            Map<String, Object> detailMap = (Map<String, Object>) param.get("showDetail");
            showDetail = Json.format(param.get("showDetail"));
            summaryInfo = Json.format(param.get("summaryInfo"));
            
            //insert house table
            HousePo hp = new HousePo();
            hp.setHouseId(Integer.valueOf(houseId));
            hp = mainService.getHouseByAllParam(hp).get(0);
            if(devId != null){
            	hp.setDevId(Integer.valueOf(devId));
            }
            if(bldId != null){
            	hp.setBldId(Integer.valueOf(bldId));
            }
            if(cityId != null){
            	hp.setCityId(Integer.valueOf(cityId));
            }
            if(type != null){
            	hp.setType(Integer.valueOf(type));
            }
            List<Integer> listMark = null;
            if(mark != null){
            	listMark = (List<Integer>)param.get("mark");
            	hp.setMark(mainService.array2binary(listMark, ConstValue.houseBinaryList));
            }
            //List<Integer> listFlag = null;
            if(flag != null){
            	//listFlag = (List<Integer>)param.get("flag");
            	hp.setFlag(Integer.valueOf(flag));
            }
            if(room != null){
            	hp.setRoom(Integer.valueOf(room));
            }
            if(theme != null){
            	hp.setTheme(Integer.valueOf(theme));
            }
            if(luxury != null){
            	hp.setLuxury(Integer.valueOf(luxury));
            }
            if(freezeAmt != null){
            	hp.setFreezeAmt(Integer.valueOf(freezeAmt));
            }
            if(totalAmt != null){
            	hp.setTotalAmt(Integer.valueOf(totalAmt));
            }
            if(memFreezeAmt != null){
            	hp.setMemFreezeAmt(Integer.valueOf(memFreezeAmt));
            }
            if(memTotalAmt != null){
            	hp.setMemTotalAmt(Integer.valueOf(memTotalAmt));
            }
            if (market_price != null) {
				hp.setMarket_price(Integer.parseInt(market_price));
			}
            if(summaryInfo != null && !"".equals(summaryInfo)){
            	hp.setSummaryInfo(summaryInfo);;
            }
            if(showDetail != null && !"".equals(summaryInfo)){
            	hp.setShowDetail(showDetail);
            }
            if(removed != null){
            	hp.setRemoved(Integer.valueOf(removed));
            }
            if (totalRoomNum != null) {
                hp.setTotalRoomNum(Integer.valueOf(totalRoomNum));
            }
            if (sort != null) {
                hp.setSort(Integer.valueOf(sort));
            }
            if (houseshop_id != null) {
				hp.setHouseshop_id(Integer.valueOf(houseshop_id));
			}
            
            hp.setRecommendTime(recommendTime == "" ? null : recommendTime);
            
            
            if (specials_stauts!= null) {
				hp.setSpecials_stauts(Integer.valueOf(specials_stauts));
				int sp_stauts = Integer.parseInt(specials_stauts);
				if (sp_stauts==2) {//特价状态为2的是否存在
					HouseDetailVo hDetailVo = new HouseDetailVo();
					hDetailVo.setSpecials_stauts(2);
			    	QueryResultVo queryResultVo = new QueryResultVo();
			    	queryResultVo = iHouseDetailService.searchHouseDetailResultVo(hDetailVo,0,0);
			    	List<HouseDetailPo> houseDetailList = queryResultVo.getSourceList();
			    	if (houseDetailList.size()>0) {//存在
						for (int i = 0; i < houseDetailList.size(); i++) {
							HouseDetailPo houseDetailPo = houseDetailList.get(i);
							houseDetailPo.setSpecials_stauts(1);
							iHouseDetailService.updateHouseDeatil(houseDetailPo);//更新mongodb首页特价为特价1状态
							//更新sql中首页特价为1
							HousePo house = new HousePo();
							house.setHouseId(new Long(houseDetailPo.getHouseId()).intValue());
							house.setSpecials_stauts(1);
							mainService.updateHouse(house);
						}
					}
			    	
				}
				
			}
			
            mainService.updateHouse(hp);
            
            if ("1".equals(removed)) {
				//删除mangodb房源详情数据
            	iHouseDetailService.delHouseDetail(Integer.parseInt(houseId));
			}else {
				//更新mangodb房源详情数据
				syncUpdateDetailToMango(hp);
			}
          
            if (special_flag!= null) {
				if (special_flag.equals("0")) {
					genSuccOutputMap();
					return outputMap;
				}
			}
            
            HouseSearchPo houseSearch = new HouseSearchPo();
            houseSearch.setHouseId(hp.getHouseId());//房源id
            houseSearch.setRemoved(hp.getRemoved());
            if(hp.getRemoved() != 1) {
	            houseSearch.setCityId(Integer.valueOf(cityId));  //城市ID
	            houseSearch.setName((String)houseMap.get("houseName"));//房源名称
	            
	            //查询楼盘信息
	            BuildingPo building = new BuildingPo();
	            building.setBldId(Integer.valueOf(bldId));
	            List<Integer> businessAreaList = new ArrayList<Integer>();
	            String businessAreaName = "";
	            if(mainService.getBuilding(building).size()>0) { 
	            	//获取所属商圈ID
	            	String businessAreas = mainService.getBuilding(building).get(0).getTrade_area();
	            	if(!StringUtils.isEmpty(businessAreas)) {
	            		String[] ids = businessAreas.split(",");
	                	for (String areaId : ids) {
	                		businessAreaList.add(Integer.parseInt(areaId));
	                		//获取商圈名称
	                		TradeArea tradeArea = new TradeArea();
	                		tradeArea.setId(Integer.valueOf(areaId));
	                		businessAreaName += mainService.getTradeAreaById(tradeArea).get(0).getName() + ",";
	    				}
	            	}
	            }
	            houseSearch.setBusinessAreaList(businessAreaList);//所属商圈ID
	            houseSearch.setPrice(Integer.valueOf(memTotalAmt));//价格
	            houseSearch.setPriceRange(CommonUtil.getPriceRange(Integer.valueOf(memTotalAmt)));//价格范围
	            houseSearch.setPersonNum(Integer.parseInt(room.substring(6, 7)));//人数
	            houseSearch.setRoomNum(Integer.parseInt(room.substring(0, 1)));//房间数
	            houseSearch.setTagList(listMark);//标签
	            List<KeyWordVo> keyWordList = new ArrayList<KeyWordVo>();
	            KeyWordVo keyWord = new KeyWordVo();
	            keyWord.setKey("businessAreaName");
	            keyWord.setValue(businessAreaName);
	            keyWordList.add(keyWord);
	            KeyWordVo keyWord1 = new KeyWordVo();
	            keyWord1.setKey("name");
	            keyWord1.setValue(houseMap.get("houseName").toString());
	            keyWordList.add(keyWord1);
	            houseSearch.setKeyWord(keyWordList); //关键字
	            if(detailMap.get("introductionImg") !=null && detailMap.get("introductionImg") !="") {
	            	String[] imgs = detailMap.get("introductionImg").toString().split(",");
	            	houseSearch.setImageUrl(imgs[0]);//房源图片
	            }
	            //houseSearch.setImageUrl(detailMap.get("introductionImg").toString());//房源图片
	            //是否推荐
	            houseSearch.setIsRecommend(Integer.parseInt(flag));
	            if(flag.equals("1") && !StringUtils.isEmpty(recommendTime)) {
	            	houseSearch.setRecommendTime(sdf.parse(recommendTime).getTime());//推荐时间
	            }
	            houseSearch.setScore(5); //评分
	            if(houseMap.get("status").toString().equals("1")) {//上下架时间
	            	if(detailMap.get("checkInTime") != null && detailMap.get("checkInTime") != "") {
	            		houseSearch.setOnLineTime(detailMap.get("checkInTime").toString()); //上线时间
	            		houseSearch.setCheckOutTime(detailMap.get("checkOutTime").toString()); //下线时间
	            	}
	            	houseSearch.setRemoved(0);
	            } else {
	            	houseSearch.setRemoved(2);
	            }
	            houseSearch.setMarketPrice(hp.getMarket_price());
            }
            iHouseSearchService.updateHouseSource(houseSearch);
            
            //return
            genSuccOutputMap();
            
        } catch (Exception e) {
        	Log.SERVICE.error("[UpdateHouseHandler]Exception!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private void syncUpdateDetailToMango(HousePo housePo){
    	HouseDetailPo houseDeatil = new HouseDetailPo();
            houseDeatil.setHouseId(housePo.getHouseId());
            houseDeatil.setDevId(housePo.getDevId());//开发商id
            houseDeatil.setBldId(housePo.getBldId());//楼盘ID
            houseDeatil.setCityId(housePo.getCityId());//城市ID
            
            String summaryInfo = housePo.getSummaryInfo();//房源简介信息，在房源列表页展示
            JSONObject jsonObject = JSONObject.parseObject(summaryInfo);
            houseDeatil.setName((String)jsonObject.get("houseName"));//房源名称
            
            String showDetail = housePo.getShowDetail();//房源全部信息，在详情页展示
            JSONObject jsonObject2 = JSONObject.parseObject(showDetail);
            List<HouseImage> imgList = new ArrayList<HouseImage>();
            if(jsonObject2.get("introductionImg") !=null && jsonObject2.get("introductionImg") !="") {
            	String[] imgs = jsonObject2.get("introductionImg").toString().split(",");
            	String[] imageName = {};
            	if (null!=jsonObject2.get("introductionName")&&!"".equals(jsonObject2.get("introductionName")) 
            			&& !"[]".equals(jsonObject2.get("introductionName"))) {
            		imageName = jsonObject2.get("introductionName").toString().split(",");
				}
            	
            	String[] imageType = {};
            	if (null!=jsonObject2.get("introductionType")&&!"".equals(jsonObject2.get("introductionType"))
            			&& !"[]".equals(jsonObject2.get("introductionType"))) {
            		imageType = jsonObject2.get("introductionType").toString().split(",");
				}
            	for (int i = 0; i < imgs.length; i++) {
            		HouseImage houseImage = new HouseImage();
            		houseImage.setImageUrl(imgs[i]);
            		if (imageName.length>0) {
            			if (null!=imgs[i] && !"".equals(imgs[i]) && !"null".equals(imgs[i])) {
            				houseImage.setImageName(imageName[i]);
						}
					}

            		if (imageType.length>0) {
            			houseImage.setImageType(Integer.parseInt(imageType[i]));
					}
            		imgList.add(houseImage);
				}
            }
            houseDeatil.setImgList(imgList);
            houseDeatil.setTagList(mainService.binary2array(housePo.getMark(),ConstValue.houseBinaryList));
            houseDeatil.setLuxury(housePo.getLuxury());
            houseDeatil.setFreezeAmt(housePo.getFreezeAmt());
            houseDeatil.setTotalAmt(housePo.getTotalAmt());
            houseDeatil.setMemFreezeAmt(housePo.getMemFreezeAmt());
            houseDeatil.setMemTotalAmt(housePo.getMemTotalAmt());
            houseDeatil.setStatus(Integer.parseInt(jsonObject.get("status").toString()));
            houseDeatil.setMarket_price(housePo.getMarket_price());
            houseDeatil.setSpecials_stauts(housePo.getSpecials_stauts());
         
            
            List<RoomProperty> roomList = new ArrayList<RoomProperty>(); 
            RoomProperty roomProperty = new RoomProperty();
            roomProperty.setAddress(jsonObject2.get("address").toString());
            roomProperty.setArea(jsonObject2.get("houseArea").toString());
            
            roomProperty.setBeds(Integer.parseInt(String.valueOf(housePo.getRoom()).substring(3,4)));
            roomProperty.setRoom(Integer.parseInt(String.valueOf(housePo.getRoom()).substring(0, 1)));
            roomProperty.setOffice(Integer.parseInt(String.valueOf(housePo.getRoom()).substring(1, 2)));
            roomProperty.setToilet(Integer.parseInt(String.valueOf(housePo.getRoom()).substring(2, 3)));
            roomProperty.setPersonNum(Integer.parseInt(String.valueOf(housePo.getRoom()).substring(6, 7)));
            roomProperty.setHouseType(Integer.parseInt(jsonObject2.get("houseType").toString()));//房屋类型：1、别墅；2、高档公寓；3、酒店公寓；
            roomProperty.setBedType(jsonObject2.get("bedType").toString());
            roomProperty.setIsInvoice(Integer.parseInt(jsonObject2.get("isInvoice").toString()));
            roomProperty.setTelephone(jsonObject2.get("telephone").toString());
            roomProperty.setVideoUrl(jsonObject2.get("videoUrl").toString());//视频地址
            if(null!=jsonObject2.get("mapImg")){
            	roomProperty.setMapImg(jsonObject2.get("mapImg").toString());//地图图片
            }
            
            roomList.add(roomProperty);
            houseDeatil.setRoomList(roomList);

            List<Facilities> facilitiesList = new ArrayList<Facilities>();//房间设施
            Facilities facilities = new Facilities();
            JSONObject facilities_jsonObject =JSONObject.parseObject(jsonObject2.get("facilities").toString());
            facilities.setConditioner(Integer.parseInt(facilities_jsonObject.getString("conditione")));
            facilities.setWashing(Integer.parseInt(facilities_jsonObject.getString("washing")));
            facilities.setFridge(Integer.parseInt(facilities_jsonObject.getString("fridge")));
            facilities.setDrinking(Integer.parseInt(facilities_jsonObject.getString("drinking")));
            facilities.setTv(Integer.parseInt(facilities_jsonObject.getString("tv")));
            facilities.setTowels(Integer.parseInt(facilities_jsonObject.getString("towels")));
            facilities.setTooth(Integer.parseInt(facilities_jsonObject.getString("tooth")));
            facilities.setSlipper(Integer.parseInt(facilities_jsonObject.getString("slipper")));
            facilities.setShampoo(Integer.parseInt(facilities_jsonObject.getString("shampoo")));
            facilities.setHairdrier(Integer.parseInt(facilities_jsonObject.getString("hairdrier")));  
            facilities.setShower(Integer.parseInt(facilities_jsonObject.getString("shower")));
            facilities.setHeater(Integer.parseInt(facilities_jsonObject.getString("heater")));
            facilities.setDryer(Integer.parseInt(facilities_jsonObject.getString("dryer")));
            facilities.setSmokedetector(Integer.parseInt(facilities_jsonObject.getString("smokedetector")));
            facilities.setHeating(Integer.parseInt(facilities_jsonObject.getString("heating")));
            facilities.setExtinguisher(Integer.parseInt(facilities_jsonObject.getString("extinguisher")));
            facilities.setAidkit(Integer.parseInt(facilities_jsonObject.getString("aidkit")));
            facilities.setHotpot(Integer.parseInt(facilities_jsonObject.getString("hotpot")));
            facilitiesList.add(facilities);
            houseDeatil.setFacilitiesList(facilitiesList);
        	houseDeatil.setRecommendFacilities("");//推荐设施:保存设施的字段名称,多个以逗号隔开

        	
        	//房间配套
        	List<Supporting> supportingList = new ArrayList<Supporting>();
        	JSONObject jsonObject1 =JSONObject.parseObject(jsonObject2.get("supporting").toString());
        	Supporting supporting = new Supporting();
        	supporting.setWifi(Integer.parseInt(jsonObject1.getString("wifi")));
        	supporting.setBroadband(Integer.parseInt(jsonObject1.getString("broadband")));
        	supporting.setElevator(Integer.parseInt(jsonObject1.getString("elevator")));
        	supporting.setSwimming(Integer.parseInt(jsonObject1.getString("swimming")));
        	supporting.setAccesscard(Integer.parseInt(jsonObject1.getString("accesscard")));
        	supporting.setSecuritystaff(Integer.parseInt(jsonObject1.getString("securitystaff")));
        	supporting.setStore(Integer.parseInt(jsonObject1.getString("store")));
        	supporting.setParking(Integer.parseInt(jsonObject1.getString("parking")));
        	supporting.setGym(Integer.parseInt(jsonObject1.getString("gym")));
        	supporting.setPlayground(Integer.parseInt(jsonObject1.getString("playground")));
        	supporting.setWheelchair(Integer.parseInt(jsonObject1.getString("wheelchair")));
        	supporting.setBuzzer(Integer.parseInt(jsonObject1.getString("buzzer")));
        	supportingList.add(supporting);
        	houseDeatil.setSupportingList(supportingList);
        	
        	//其它
        	List<Other> otherList = new ArrayList<Other>();
        	JSONObject other_jsonObject =JSONObject.parseObject(jsonObject2.get("other").toString());
        	Other other = new Other();
        	other.setCook(Integer.parseInt(other_jsonObject.getString("cook")));
        	other.setParty(Integer.parseInt(other_jsonObject.getString("party")));
        	other.setSmoking(Integer.parseInt(other_jsonObject.getString("smoking")));
        	other.setPet(Integer.parseInt(other_jsonObject.getString("pet")));
        	other.setExtrabed(Integer.parseInt(other_jsonObject.getString("extrabed")));
        	other.setGuests(Integer.parseInt(other_jsonObject.getString("guests")));
        	other.setBreakfast(Integer.parseInt(other_jsonObject.getString("breakfast")));
        	other.setChildrenstay(Integer.parseInt(other_jsonObject.getString("childrenstay")));
            otherList.add(other);
            houseDeatil.setOtherList(otherList);
            houseDeatil.setRoomNum(Integer.parseInt(String.valueOf(housePo.getRoom()).substring(0, 1)));//房间数
            houseDeatil.setShopId(housePo.getHouseshop_id());
            houseDeatil.setAroundArea("");//周边
            houseDeatil.setHowToArrive("");//怎么去
            houseDeatil.setTheme(housePo.getTheme());//主题：1、亲子出游；2、浪漫假期；3、新奇住宿；4、城市周末；5、特色主题
            if (null!=housePo.getRecommendTime() && !"".equals(housePo.getRecommendTime())) {
				houseDeatil.setRecommendTime(housePo.getRecommendTime());//推荐时间
				houseDeatil.setIsRecommend("1");//是否推荐:1、推荐；0、不推介；
			}else {
				houseDeatil.setRecommendTime("");//推荐时间
				houseDeatil.setIsRecommend("0");//是否推荐:1、推荐；0、不推介；
			}
            
            houseDeatil.setOnLineTime(jsonObject2.get("checkInTime").toString());
            houseDeatil.setOffLineTime(jsonObject2.get("checkOutTime").toString());
            houseDeatil.setInRemark(jsonObject2.get("checkdesc").toString());

//            List<Geographical> geographicalList = new ArrayList<Geographical>();
//            Geographical geographical = new Geographical();
//            geographical.setLongitude(Double.valueOf(jsonObject2.get("longitude").toString()));//精度
//            geographical.setLatitude(Double.valueOf(jsonObject2.get("latitude").toString()));//纬度
//            geographicalList.add(geographical);
            Double[] location = new Double[]{Double.valueOf(jsonObject2.get("longitude").toString()),Double.valueOf(jsonObject2.get("latitude").toString())};
            houseDeatil.setLocation(location);
//            houseDeatil.setGeographicalList(geographicalList);
            
            iHouseDetailService.updateHouseDeatil(houseDeatil);
			
		}
    
}