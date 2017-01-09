package com.mama.server.main.controller.handler.customerservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.vo.Facilities;
import com.mama.server.main.dao.vo.Geographical;
import com.mama.server.main.dao.vo.HouseImage;
import com.mama.server.main.dao.vo.KeyWordVo;
import com.mama.server.main.dao.vo.Other;
import com.mama.server.main.dao.vo.RoomProperty;
import com.mama.server.main.dao.vo.Supporting;
import com.mama.server.main.vo.HouseDetailVo;
import com.mama.server.util.CommonUtil;
import com.mama.server.util.Json;
import com.mama.server.util.Log;
import com.meidusa.fastjson.JSONObject;

@Component
public class AddHouseHandler extends BaseHandler {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
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
            String totalRoomNum = mainService.checkPostParam(param.get("totalRoomNum"));
            String recommendTime = mainService.checkPostParam(param.get("recommendTime"));
            String sort = mainService.checkPostParam(param.get("sort"));
            String houseshop_id = mainService.checkPostParam(param.get("houseshop_id"));
        	
            if (devId == null || bldId == null || cityId == null || type == null|| mark == null|| flag == null|| theme == null|| luxury == null|| freezeAmt == null|| 
            		totalAmt == null|| memFreezeAmt == null|| memTotalAmt == null|| summaryInfo == null|| showDetail == null || totalRoomNum == null || market_price == null) {
            	Log.SERVICE.error("[AddHouseHandler]param is empty : devId("+devId+"),bldId("+bldId+"),cityId("+cityId+"),type("+type+"),mark("+mark+"),flag("+flag+"),room("+room+"),theme("+theme+"),luxury("+luxury+"),freezeAmt("+freezeAmt+"),totalAmt("+totalAmt+"),memFreezeAmt("+memFreezeAmt+"),memTotalAmt("+memTotalAmt+"),summaryInfo.length("+summaryInfo.length()+"),showDetail.length("+showDetail.length()+")");
            	genErrOutputMapWithCode("param is empty : devId("+devId+"),bldId("+bldId+"),cityId("+cityId+"),type("+type+"),mark("+mark+"),flag("+flag+"),room("+room+"),theme("+theme+"),luxury("+luxury+"),freezeAmt("+freezeAmt+"),totalAmt("+totalAmt+"),memFreezeAmt("+memFreezeAmt+"),memTotalAmt("+memTotalAmt+"),summaryInfo.length("+summaryInfo.length()+"),showDetail.length("+showDetail.length()+")", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            List<Integer> listMark = (List<Integer>)param.get("mark");
            
            //List<Integer> listFlag = (List<Integer>)param.get("flag");
            
            
            //房源信息
            Map<String, Object> houseMap = (Map<String, Object>) param.get("summaryInfo");
            Map<String, Object> detailMap = (Map<String, Object>) param.get("showDetail");
            
			summaryInfo = Json.format(param.get("summaryInfo"));
			showDetail = Json.format(param.get("showDetail"));
			
            //insert house table
            HousePo hp = new HousePo();
            hp.setDevId(Integer.valueOf(devId));
            hp.setBldId(Integer.valueOf(bldId));
            hp.setCityId(Integer.valueOf(cityId));
            hp.setType(Integer.valueOf(type));
            hp.setMark(mainService.array2binary(listMark, ConstValue.houseBinaryList));
            hp.setFlag(Integer.valueOf(flag));
            hp.setRoom(Integer.valueOf(room));
            hp.setTheme(Integer.valueOf(theme));
            hp.setLuxury(Integer.valueOf(luxury));
            hp.setFreezeAmt(Integer.valueOf(freezeAmt));
            hp.setTotalAmt(Integer.valueOf(totalAmt));
            hp.setMemFreezeAmt(Integer.valueOf(memFreezeAmt));
            hp.setMemTotalAmt(Integer.valueOf(memTotalAmt));
            hp.setMarket_price(Integer.parseInt(market_price));
            hp.setSummaryInfo(summaryInfo);;
            hp.setShowDetail(showDetail);
            hp.setTotalRoomNum(Integer.valueOf(totalRoomNum));
            hp.setRecommendTime(recommendTime == "" ? null : recommendTime);
            hp.setHouseshop_id(houseshop_id==""?null:Integer.parseInt(houseshop_id));
            if (sort == null) hp.setSort(0);
            else hp.setSort(Integer.valueOf(sort));
            mainService.insertHouse(hp);
            
            HouseSearchPo houseSearch = new HouseSearchPo(); 
            houseSearch.setHouseId(hp.getHouseId());//房源id
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
            houseSearch.setMarketPrice(Integer.parseInt(market_price));
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
            //是否推荐
            houseSearch.setIsRecommend(Integer.parseInt(flag));
            if(flag.equals("1") && !StringUtils.isEmpty(recommendTime)) {
                houseSearch.setRecommendTime(sdf.parse(recommendTime).getTime());//推荐时间
            }
            houseSearch.setScore(5); //评分
            if(houseMap.get("status").toString().equals("1")) {
            	if(detailMap.get("checkInTime") != null && detailMap.get("checkInTime") != "") {
            		houseSearch.setOnLineTime(detailMap.get("checkInTime").toString()); //上线时间
            		houseSearch.setCheckOutTime(detailMap.get("checkOutTime").toString()); //下线时间
            	}
            } else {
            	houseSearch.setRemoved(2);
            }
            
            iHouseSearchService.addHouseSource(houseSearch);
            iHouseSearchService.addInventory(hp.getHouseId(), getInventory(Integer.valueOf(totalRoomNum)));
            
            //mangodb插入房源搜索详情
            HouseDetailVo houseDetailVo = new HouseDetailVo();
            houseDetailVo.setMarket_price(Integer.parseInt(market_price));
            houseDetailVo.setSpecials_stauts(0);
            houseDetailVo.setHouseId(hp.getHouseId());
            houseDetailVo.setDevId(Integer.parseInt(devId));//开发商id
            houseDetailVo.setBldId(Integer.parseInt(bldId));//楼盘ID
            houseDetailVo.setCityId(Integer.parseInt(cityId));//城市ID
            houseDetailVo.setName((String)houseMap.get("houseName"));//房源名称
            List<HouseImage> imgList = new ArrayList<HouseImage>();
            if(detailMap.get("introductionImg") !=null && detailMap.get("introductionImg") !="") {
            	String[] imgs = detailMap.get("introductionImg").toString().split(",");
            	String[] imageName = detailMap.get("introductionName").toString().split(",");
            	String[] imageType = detailMap.get("introductionType").toString().split(",");
            	for (int i = 0; i < imgs.length; i++) {
            		HouseImage houseImage = new HouseImage();
            		houseImage.setImageUrl(imgs[i]);
            		houseImage.setImageName(imageName[i]);
//            		houseImage.setImageName("测试");
            		String typeStr = imageType[i];
            		if (!"".equals(type)&&null!=type) {
            			houseImage.setImageType(Integer.parseInt(typeStr));
					}
            		imgList.add(houseImage);
				}
            }
            houseDetailVo.setImgList(imgList);
            houseDetailVo.setTagList(listMark);
            houseDetailVo.setLuxury(Integer.parseInt(luxury));
            houseDetailVo.setFreezeAmt(hp.getFreezeAmt());
            houseDetailVo.setTotalAmt(hp.getTotalAmt());
            houseDetailVo.setMemFreezeAmt(hp.getMemFreezeAmt());
            houseDetailVo.setMemTotalAmt(hp.getMemTotalAmt());
            houseDetailVo.setStatus(hp.getRemoved());
         
            
            RoomProperty roomProperty = new RoomProperty();
            roomProperty.setAddress(detailMap.get("address").toString());
            roomProperty.setArea(detailMap.get("houseArea").toString());
//            $!houseInfo.room.substring(0,1)室$!houseInfo.room.substring(1,2)厅$!houseInfo.room.substring(2,3)卫 $!houseInfo.room.substring(3,4)床  宜住$!houseInfo.room.substring(6,7)人
            
            roomProperty.setBeds(Integer.parseInt(room.substring(3,4)));
            roomProperty.setRoom(Integer.parseInt(room.substring(0, 1)));
            roomProperty.setOffice(Integer.parseInt(room.substring(1, 2)));
            roomProperty.setToilet(Integer.parseInt(room.substring(2, 3)));
            roomProperty.setPersonNum(Integer.parseInt(room.substring(6, 7)));
            roomProperty.setHouseType(Integer.parseInt(detailMap.get("houseType").toString()));//房屋类型：1、别墅；2、高档公寓；3、酒店公寓；
            roomProperty.setBedType(detailMap.get("bedType").toString());
            roomProperty.setIsInvoice(Integer.parseInt(detailMap.get("isInvoice").toString()));
            roomProperty.setTelephone(detailMap.get("telephone").toString());
            roomProperty.setVideoUrl(detailMap.get("videoUrl").toString());//视频地址
            roomProperty.setMapImg(detailMap.get("mapImg").toString());//地图图片
            houseDetailVo.setRoomList(roomProperty);
            
            JSONObject jObject = JSONObject.parseObject(showDetail);
            Facilities facilities = new Facilities();
            JSONObject jsonObject =JSONObject.parseObject(jObject.get("facilities").toString());
            facilities.setConditioner(Integer.parseInt(jsonObject.getString("conditione")));
            facilities.setWashing(Integer.parseInt(jsonObject.getString("washing")));
            facilities.setFridge(Integer.parseInt(jsonObject.getString("fridge")));
            facilities.setDrinking(Integer.parseInt(jsonObject.getString("drinking")));
            facilities.setTv(Integer.parseInt(jsonObject.getString("tv")));
            facilities.setTowels(Integer.parseInt(jsonObject.getString("towels")));
            facilities.setTooth(Integer.parseInt(jsonObject.getString("tooth")));
            facilities.setSlipper(Integer.parseInt(jsonObject.getString("slipper")));
            facilities.setShampoo(Integer.parseInt(jsonObject.getString("shampoo")));
            facilities.setHairdrier(Integer.parseInt(jsonObject.getString("hairdrier")));  
            facilities.setShower(Integer.parseInt(jsonObject.getString("shower")));
            facilities.setHeater(Integer.parseInt(jsonObject.getString("heater")));
            facilities.setDryer(Integer.parseInt(jsonObject.getString("dryer")));
            facilities.setSmokedetector(Integer.parseInt(jsonObject.getString("smokedetector")));
            facilities.setHeating(Integer.parseInt(jsonObject.getString("heating")));
            facilities.setExtinguisher(Integer.parseInt(jsonObject.getString("extinguisher")));
            facilities.setAidkit(Integer.parseInt(jsonObject.getString("aidkit")));
            facilities.setHotpot(Integer.parseInt(jsonObject.getString("hotpot")));
            houseDetailVo.setFacilitiesList(facilities);
        	houseDetailVo.setRecommendFacilities("");//推荐设施:保存设施的字段名称,多个以逗号隔开

        	
        	//房间配套
        	JSONObject jsonObject1 =JSONObject.parseObject(jObject.get("supporting").toString());
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
        	houseDetailVo.setSupportingList(supporting);
        	
        	//其它
        	JSONObject jsonObject2 =JSONObject.parseObject(jObject.get("other").toString());
        	Other other = new Other();
        	other.setCook(Integer.parseInt(jsonObject2.getString("cook")));
        	other.setParty(Integer.parseInt(jsonObject2.getString("party")));
        	other.setSmoking(Integer.parseInt(jsonObject2.getString("smoking")));
        	other.setPet(Integer.parseInt(jsonObject2.getString("pet")));
        	other.setExtrabed(Integer.parseInt(jsonObject2.getString("extrabed")));
        	other.setGuests(Integer.parseInt(jsonObject2.getString("guests")));
        	other.setBreakfast(Integer.parseInt(jsonObject2.getString("breakfast")));
        	other.setChildrenstay(Integer.parseInt(jsonObject2.getString("childrenstay")));
            houseDetailVo.setOtherList(other);
            houseDetailVo.setRoomNum(Integer.parseInt(room.substring(0, 1)));//房间数
            houseDetailVo.setShopId(hp.getHouseshop_id());
            houseDetailVo.setAroundArea("");//周边
            houseDetailVo.setHowToArrive("");//怎么去
            houseDetailVo.setTheme(Integer.parseInt(theme));//主题：1、亲子出游；2、浪漫假期；3、新奇住宿；4、城市周末；5、特色主题
            if (null!=hp.getRecommendTime() && !"".equals(recommendTime)) {
				houseDetailVo.setRecommendTime(hp.getRecommendTime());//推荐时间
				houseDetailVo.setIsRecommend("1");//是否推荐:1、推荐；0、不推介；
			}else {
				houseDetailVo.setRecommendTime("");//推荐时间
				houseDetailVo.setIsRecommend("0");//是否推荐:1、推荐；0、不推介；
			}
            
            houseDetailVo.setOnLineTime(detailMap.get("checkInTime").toString());
            houseDetailVo.setOffLineTime(detailMap.get("checkOutTime").toString());
            houseDetailVo.setInRemark(detailMap.get("checkdesc").toString());

            Geographical geographical = new Geographical();
            geographical.setLongitude(Double.valueOf(detailMap.get("longitude").toString()));//精度
            geographical.setLatitude(Double.valueOf(detailMap.get("latitude").toString()));//纬度
            houseDetailVo.setGeographicalList(geographical);
            
            iHouseDetailService.addHouseDetail(houseDetailVo);
            //return
            genSuccOutputMap();
            
        } catch (Exception e) {
        	Log.SERVICE.error("[AddHouseHandler]Exception!",e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
    
    private Map<Long, Integer> getInventory(int totalRoomNum)
    {
    	try 
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    		long currentDay = sdf.parse(sdf.format(new Date())).getTime();
    		long periodTime = 24 * 60 * 60 * 1000;//一天的毫秒值
    		Map<Long, Integer> inventory = new HashMap<Long, Integer>();
    		for(int i = 0; i < 120; i++)
    		{
    			inventory.put(currentDay + periodTime * i, totalRoomNum);
    		}
			
    		return inventory;
		} 
    	catch (Exception e)
    	{
		}
		
    	return null;
    }
    
}