package com.mama.server.main.controller.handler.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.RoomProperty;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.dao.vo.Supporting;
import com.mama.server.main.vo.HouseDetailVo;
import com.mama.server.util.CommonUtil;
import com.meidusa.fastjson.JSONObject;

@Component
public class GetHousesHandler extends BaseHandler {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private int k = 0;//正常条数
	private int j = 0;//异常条数
	
    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            List<Integer> devIds = null;
            if (param.get("devIds") != null) {
                devIds = (List<Integer>)param.get("devIds");
            }
            
            List<Integer> bldIds = null;
            if (param.get("bldIds") != null) {
                bldIds = (List<Integer>)param.get("bldIds");
            }
            
            List<Integer> houseIds = null;
            if (param.get("houseIds") != null) {
                houseIds = (List<Integer>)param.get("houseIds");
            }
            
            List<Integer> cityIds = null;
            if (param.get("cityIds") != null) {
                cityIds = (List<Integer>)param.get("cityIds");
            }
            
            List<Integer> rooms = null;
            if (param.get("rooms") != null) {
                rooms = (List<Integer>)param.get("rooms");
            }
            
            List<Integer> themes = null;
            if (param.get("themes") != null) {
                themes = (List<Integer>)param.get("themes");
            }
            
            List<Integer> luxurys = null;
            if (param.get("luxurys") != null) {
                luxurys = (List<Integer>)param.get("luxurys");
            }

            String lowerOperTime = null;
            if (param.get("lowerOperTime") != null) {
                lowerOperTime = (String)param.get("lowerOperTime");
            }
            
            String upperOperTime = null;
            if (param.get("upperOperTime") != null) {
                upperOperTime = (String)param.get("upperOperTime");
            }
            int reversed = 0;
            if (param.get("reversed") != null) {
                reversed = (Integer)param.get("reversed");
            }
            if (reversed != 1 && reversed != 0) {
                reversed = 0;
            }
            
            int recommended = -1;
            if (param.get("recommended") != null) {
                recommended = (Integer)param.get("recommended");
            }
            
            String summaryInfo = null;
            if (param.get("name") != null) {
            	summaryInfo = (String)param.get("name");
            }
            
            int pageNum = -1;
            int pageCount = -1;
            if (param.get("sync")==null || "".equals(param.get("sync"))) {
	            if (param.get("pageNum") != null && param.get("pageCount") != null) {
	                pageNum = (Integer)param.get("pageNum");
	                pageCount = (Integer)param.get("pageCount");
	                
	                if (pageNum <= 0 || pageCount <= 0) {
	                    genErrOutputMapWithCode("param error, invalid pageNum/pageCount", ReturnCode.PARAM_ERROR);
	                    return outputMap;
	                }
	            }
            }
            HousePo house = new HousePo();
            house.setSummaryInfo(summaryInfo);
            List<HousePo> houseList = mainService.getHouseByAllParam(house);
            if (houseList == null) {
                genErrOutputMapWithCode("fail to get houses", ReturnCode.GET_HOUSE_ERROR);
                return outputMap;
            }
            
            //同步房源详情到mangodb
            if (param.get("sync")!=null ) {
				syncDetailToMango(houseList);
			}
            
            //同步房源搜索到mongodb
            if(param.get("syncSearch")!=null){
            	syncSearchToMango(houseList);
            }
            
            List<HousePo> tmpHouseList = new ArrayList<HousePo>();
            if (devIds != null && devIds.size() != 0) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (devIds.contains(houseList.get(i).getDevId())) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            tmpHouseList = new ArrayList<HousePo>();
            if (bldIds != null && bldIds.size() != 0) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (bldIds.contains(houseList.get(i).getBldId())) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            tmpHouseList = new ArrayList<HousePo>();
            if (cityIds != null && cityIds.size() != 0) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (cityIds.contains(houseList.get(i).getCityId())) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            tmpHouseList = new ArrayList<HousePo>();
            if (houseIds != null && houseIds.size() != 0) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (houseIds.contains(houseList.get(i).getHouseId())) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            tmpHouseList = new ArrayList<HousePo>();
            if (rooms != null && rooms.size() != 0) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (rooms.contains(houseList.get(i).getRoom())) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            tmpHouseList = new ArrayList<HousePo>();
            if (luxurys != null && luxurys.size() != 0) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (luxurys.contains(houseList.get(i).getLuxury())) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            tmpHouseList = new ArrayList<HousePo>();
            if (themes != null && themes.size() != 0) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (themes.contains(houseList.get(i).getTheme())) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            tmpHouseList = new ArrayList<HousePo>();
            if (lowerOperTime != null) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (lowerOperTime.compareTo(houseList.get(i).getOperTime()) <= 0) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            tmpHouseList = new ArrayList<HousePo>();
            if (upperOperTime != null) {
                for (int i = 0; i < houseList.size(); ++i) {
                    if (upperOperTime.compareTo(houseList.get(i).getOperTime()) >= 0) {
                        tmpHouseList.add(houseList.get(i));
                    }
                }
                houseList = tmpHouseList;
            }
            
            for (int i = 0; i < houseList.size(); ++i) {
                for (int j = i + 1; j < houseList.size(); ++j) {
                    if (reversed == 0 && houseList.get(i).getSort() > houseList.get(j).getSort() || 
                            reversed == 1 && houseList.get(i).getSort() < houseList.get(j).getSort()) {
                        HousePo tmpHouse = houseList.get(i);
                        houseList.set(i, houseList.get(j));
                        houseList.set(j, tmpHouse);
                    }
                }
            }
            
            ArrayList<HashMap<String, Object>> houseMapList = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < houseList.size(); ++i) {
                if (houseList.get(i).getRemoved() == 1) {
                    continue;
                }
                List<Integer> flagList = mainService.binary2array(houseList.get(i).getFlag(),ConstValue.houseBinaryList);
                if (recommended != -1 && !flagList.contains(recommended)) {
                    continue;
                }
                HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put("houseId", houseList.get(i).getHouseId());
                tmpMap.put("cityId", houseList.get(i).getCityId());
                //tmpMap.put("showDetail", houseList.get(i).getShowDetail());
                tmpMap.put("type", houseList.get(i).getType());
                tmpMap.put("mark", mainService.binary2array(houseList.get(i).getMark(),ConstValue.houseBinaryList));
                tmpMap.put("freezeAmt", houseList.get(i).getFreezeAmt());
                tmpMap.put("totalAmt", houseList.get(i).getTotalAmt());
                tmpMap.put("memFreezeAmt", houseList.get(i).getMemFreezeAmt());
                tmpMap.put("memTotalAmt", houseList.get(i).getMemTotalAmt());
                tmpMap.put("summaryInfo", houseList.get(i).getSummaryInfo());
                tmpMap.put("room", houseList.get(i).getRoom());
                tmpMap.put("bldId", houseList.get(i).getBldId());
                tmpMap.put("totalRoomNum", houseList.get(i).getTotalRoomNum());
                tmpMap.put("flag", mainService.binary2array(houseList.get(i).getFlag(),ConstValue.houseBinaryList));
                tmpMap.put("sort", houseList.get(i).getSort());
                tmpMap.put("specials_stauts", houseList.get(i).getSpecials_stauts());
                houseMapList.add(tmpMap);
            }
            
            if (pageNum == -1 || pageCount == -1) {
                dataMap.put("houses", houseMapList);
                dataMap.put("num", houseMapList.size());
                dataMap.put("success_count", k);
                dataMap.put("false_count", j);
                genSuccOutputMap();
                return outputMap;
            }
            
            ArrayList<HashMap<String, Object>> pageList = new ArrayList<HashMap<String, Object>>();
            int startNum = (pageNum - 1) * pageCount;
            int endNum = startNum + pageCount;
            if (endNum > houseMapList.size()) {
                endNum = houseMapList.size();
            }
            for (int i = startNum; i < endNum; ++i) {
                pageList.add(houseMapList.get(i));
            }
            dataMap.put("houses", pageList);
            dataMap.put("num", pageList.size());
            
            int totalPage = houseMapList.size() / pageCount;
            if (houseMapList.size() % pageCount != 0) {
                totalPage += 1;
            }
            dataMap.put("totalPage", totalPage);
            dataMap.put("totalNum", houseMapList.size());
            
            dataMap.put("success_count", k);
            dataMap.put("false_count", j);
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
            log.error("GetHousesHandler exceptin:"+e);
        }
        return outputMap;
    }
    
    //同步房源搜索
    private void syncSearchToMango(List<HousePo> houseList){
    	log.info("start to syncSearchToMango同步搜索");
    	for (HousePo hp:houseList) {
            try {
				HouseSearchPo houseSearch = new HouseSearchPo();
				houseSearch.setHouseId(hp.getHouseId());//房源id
				houseSearch.setRemoved(hp.getRemoved());
				if(hp.getRemoved() != 1) {
				    houseSearch.setCityId(hp.getCityId());  //城市ID
				    
				    String summaryInfo = hp.getSummaryInfo();//房源简介信息，在房源列表页展示
				    JSONObject jsonObject = JSONObject.parseObject(summaryInfo);
				    String showDetail = hp.getShowDetail();//房源详细信息
				    JSONObject showDetailObject = JSONObject.parseObject(showDetail);
				    houseSearch.setName((String)jsonObject.get("houseName"));//房源名称
				    
				    //查询楼盘信息
				    BuildingPo building = new BuildingPo();
				    building.setBldId(hp.getBldId());
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
				        		if (mainService.getTradeAreaById(tradeArea).size()>0) {
				        			businessAreaName += mainService.getTradeAreaById(tradeArea).get(0).getName() + ",";
								}
							}
				    	}
				    }
				    houseSearch.setBusinessAreaList(businessAreaList);//所属商圈ID
				    houseSearch.setPrice(hp.getMemTotalAmt());//价格
				    houseSearch.setPriceRange(CommonUtil.getPriceRange(hp.getMemTotalAmt()));//价格范围
				    houseSearch.setPersonNum(Integer.parseInt(String.valueOf(hp.getRoom()).substring(6, 7)));//人数
				    houseSearch.setRoomNum(Integer.parseInt(String.valueOf(hp.getRoom()).substring(0, 1)));//房间数
				    houseSearch.setTagList(mainService.binary2array(hp.getMark(),ConstValue.houseBinaryList));//标签
				    List<KeyWordVo> keyWordList = new ArrayList<KeyWordVo>();
				    KeyWordVo keyWord = new KeyWordVo();
				    keyWord.setKey("businessAreaName");
				    keyWord.setValue(businessAreaName);
				    keyWordList.add(keyWord);
				    KeyWordVo keyWord1 = new KeyWordVo();
				    keyWord1.setKey("name");
				    keyWord1.setValue(jsonObject.get("houseName").toString());
				    keyWordList.add(keyWord1);
				    houseSearch.setKeyWord(keyWordList); //关键字
				    if(showDetailObject.get("introductionImg") !=null && showDetailObject.get("introductionImg") !="") {
				    	String[] imgs = showDetailObject.get("introductionImg").toString().split(",");
				    	houseSearch.setImageUrl(imgs[0]);//房源图片
				    }
				    houseSearch.setImageUrl(showDetailObject.get("introductionImg").toString());//房源图片
				    
				    //是否推荐
				    if (null!=hp.getRecommendTime() && !"".equals(hp.getRecommendTime())) {
				    	try {
							houseSearch.setRecommendTime(sdf.parse(hp.getRecommendTime()).getTime());
						} catch (ParseException e) {
							e.printStackTrace();
						}//推荐时间
				    	houseSearch.setIsRecommend(1);//是否推荐:1、推荐；0、不推介；
					}else {
						houseSearch.setRecommendTime(0);//推荐时间
						houseSearch.setIsRecommend(0);//是否推荐:1、推荐；0、不推介；
					}
				    
				    
				    houseSearch.setScore(5); //评分
				    if(jsonObject.get("status").toString().equals("1")) {//上下架时间
				    	if(showDetailObject.get("checkInTime") != null && showDetailObject.get("checkInTime") != "") {
				    		String checkInTime = "";
				    		String checkOutTime = "";
							try {
								if (null!=showDetailObject.get("checkInTime")&& null!=showDetailObject.get("checkOutTime")) {
									checkInTime = showDetailObject.get("checkInTime").toString();
									checkOutTime = showDetailObject.get("checkOutTime").toString();
								}
							} catch (Exception e) {
								log.error("syncSearchToMango ："+e);
								e.printStackTrace();
							}
				    		houseSearch.setOnLineTime(checkInTime); //上线时间
				    		houseSearch.setCheckOutTime(checkOutTime);//下线时间
				    	}
				    	houseSearch.setRemoved(0);
				    } else {
				    	houseSearch.setRemoved(2);
				    }
				    houseSearch.setMarketPrice(hp.getMarket_price());
				}
				SearchCondition searchConditionVo = new SearchCondition();
				int[] houseIds = {hp.getHouseId()};
				searchConditionVo.setHouseIds(houseIds);
				QueryResultVo queryResultVo = iHouseSearchService.searchHouseSourceAdvanced(searchConditionVo, -1, 1, 10);
				if (queryResultVo.getSourceList().size()>0) {
					iHouseSearchService.updateHouseSource(houseSearch);
				}else {
					iHouseSearchService.addHouseSource(houseSearch);
				}
				inventoryService.addInventory(hp.getHouseId(),hp.getTotalRoomNum());//更新库存
				k = k+1;
				log.info("syncSearchToMango："+hp.getHouseId()+"同步成功！");
			} catch (Exception e) {
				e.printStackTrace();
				j=j+1;
				log.error("syncSearchToMango exception!", e);
			}
            
    	}
    	log.info("syncSearchToMango finish!");
    }
    
    //同步房源详情
    private void syncDetailToMango(List<HousePo> houseList){
    	for (HousePo housePo:houseList) {
    		try {
				HouseDetailVo houseDetail = iHouseDetailService.searchHouseDeatilPoById(housePo.getHouseId());
				if (houseDetail!=null) {//已存在  去重 不添加
					continue;
				}
				houseDetail = new HouseDetailVo();
				houseDetail.setHouseId(housePo.getHouseId());
				houseDetail.setDevId(housePo.getDevId());//开发商id
				houseDetail.setBldId(housePo.getBldId());//楼盘ID
				houseDetail.setCityId(housePo.getCityId());//城市ID
				houseDetail.setMarket_price(housePo.getMarket_price());//市场价
				houseDetail.setSpecials_stauts(housePo.getSpecials_stauts());//特价状态
				
				String summaryInfo = housePo.getSummaryInfo();//房源简介信息，在房源列表页展示
				JSONObject jsonObject = JSONObject.parseObject(summaryInfo);
				houseDetail.setName((String)jsonObject.get("houseName"));//房源名称
				
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
				houseDetail.setImgList(imgList);
				houseDetail.setTagList(mainService.binary2array(housePo.getMark(),ConstValue.houseBinaryList));
				houseDetail.setLuxury(housePo.getLuxury());
				houseDetail.setFreezeAmt(housePo.getFreezeAmt());
				houseDetail.setTotalAmt(housePo.getTotalAmt());
				houseDetail.setMemFreezeAmt(housePo.getMemFreezeAmt());
				houseDetail.setMemTotalAmt(housePo.getMemTotalAmt());
				houseDetail.setStatus(Integer.parseInt(jsonObject.get("status").toString()));
       
				
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
				
				houseDetail.setRoomList(roomProperty);

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
				houseDetail.setFacilitiesList(facilities);
				houseDetail.setRecommendFacilities("");//推荐设施:保存设施的字段名称,多个以逗号隔开

				
				//房间配套
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
				houseDetail.setSupportingList(supporting);
				
				//其它
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
				houseDetail.setOtherList(other);
				houseDetail.setRoomNum(Integer.parseInt(String.valueOf(housePo.getRoom()).substring(0, 1)));//房间数
				houseDetail.setShopId(housePo.getHouseshop_id());
				houseDetail.setAroundArea("");//周边
				houseDetail.setHowToArrive("");//怎么去
				houseDetail.setTheme(housePo.getTheme());//主题：1、亲子出游；2、浪漫假期；3、新奇住宿；4、城市周末；5、特色主题
				if (null!=housePo.getRecommendTime() && !"".equals(housePo.getRecommendTime())) {
					houseDetail.setRecommendTime(housePo.getRecommendTime());//推荐时间
					houseDetail.setIsRecommend("1");//是否推荐:1、推荐；0、不推介；
				}else {
					houseDetail.setRecommendTime("");//推荐时间
					houseDetail.setIsRecommend("0");//是否推荐:1、推荐；0、不推介；
				}
				
				houseDetail.setShareImg(jsonObject2.getString("shareImg"));
				
				houseDetail.setOnLineTime(jsonObject2.get("checkInTime").toString());
				houseDetail.setOffLineTime(jsonObject2.get("checkOutTime").toString());
				houseDetail.setInRemark(jsonObject2.get("checkdesc").toString());

				Geographical geographical = new Geographical();
				geographical.setLongitude(Double.valueOf(jsonObject2.get("longitude").toString()));//精度
				geographical.setLatitude(Double.valueOf(jsonObject2.get("latitude").toString()));//纬度
				houseDetail.setGeographicalList(geographical);
				
				iHouseDetailService.addHouseDetail(houseDetail);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("GetHousesHandler syncDetailToMango() exception:", e);
			}
		}
    	
       }
    }
