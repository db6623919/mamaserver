package com.mama.server.main.controller.handler.customerservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.BuildingPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.util.Json;
import com.mama.server.util.Log;

@Component
public class UpdateBuildingHandler extends BaseHandler {

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			if (param.get("bldId") == null) {
				genErrOutputMapWithCode("param error, bldId required", ReturnCode.PARAM_ERROR);
				return outputMap;
			}
			int bldId = (Integer) param.get("bldId");

			BuildingPo building = null;
			List<BuildingPo> buildingList = mainService.getBuilding(building);
			if (buildingList == null || buildingList.size() == 0) {
				genErrOutputMapWithCode("param error, invalid building id", ReturnCode.GET_BUILDING_ERROR);
				return outputMap;
			}
			for (int i = 0; i < buildingList.size(); ++i) {
				if (buildingList.get(i).getBldId() == bldId) {
					building = buildingList.get(i);
					break;
				}
			}
			if (building == null) {
				genErrOutputMapWithCode("param error, invalid building id", ReturnCode.GET_BUILDING_ERROR);
				return outputMap;
			}
			building.setDevId((Integer) param.get("devId"));
			building.setProvId((Integer) param.get("provId"));
			building.setCityId((Integer) param.get("cityId"));
			building.setShowDetail(Json.format(param.get("showDetail")));
			building.setType((Integer) param.get("type"));
			building.setMark((String) param.get("mark"));
			building.setRemoved(0);
			building.setName((String) param.get("name"));
			building.setProjectIntroduction((String) param.get("project_introduction"));
			building.setUnitArea((String) param.get("unit_area"));
			if (null != param.get("built_area"))
				building.setBuiltArea(new BigDecimal((Double) param.get("built_area")));
			if (null != param.get("average_price"))
				building.setAveragePrice((Integer) param.get("average_price"));
			building.setPropertyType((String) param.get("property_type"));
			building.setDecorationStandard((String) param.get("decoration_standard"));
			building.setBuiltAddress((String) param.get("built_address"));
			building.setOpenDate((String) param.get("open_date"));
			building.setPurchaseDiscount((String) param.get("purchase_discount"));
			building.setConsultantPhone((String) param.get("consultant_phone"));
			building.setDevelopName((String) param.get("develop_name"));
			building.setLaunchDate((String) param.get("launch_date"));
			if (null != param.get("year_limit"))
				building.setYearLimit((Integer) param.get("year_limit"));
			building.setBuiltStatus(0);
			building.setBuiltType((String) param.get("built_type"));
			building.setVolumeRate((String) param.get("volume_rate"));
			building.setGreeningRate((String) param.get("greening_rate"));
			building.setPlanHouseholds((String) param.get("plan_households"));
			building.setPlanParking((String) param.get("plan_parking"));
			building.setPresalePermit((String) param.get("presale_permit"));
			building.setPropertyCompName((String) param.get("property_comp_name"));
			building.setPropertyFee((String) param.get("property_fee"));
			building.setHeartingMode((String) param.get("hearting_mode"));
			building.setWaterElec((String) param.get("water_elec"));
			building.setProjectFeature((String) param.get("project_feature"));
			building.setTrade_area((String)param.get("trade"));
			if (null != param.get("scenic_resource"))
				building.setScenicResource((String) param.get("scenic_resource"));
			if (null != param.get("humanity_matching"))
				building.setHumanityMatching((String) param.get("humanity_matching"));
			if (null != param.get("education_matching"))
				building.setEducationMatching((String) param.get("education_matching"));
			if (null != param.get("business_matching"))
				building.setBusinessMatching((String) param.get("business_matching"));
			if (null != param.get("commerce_matching"))
				building.setCommerceMatching((String) param.get("commerce_matching"));
			if (null != param.get("leisure_matching"))
				building.setLeisureMatching((String) param.get("leisure_matching"));
			if (null != param.get("hospital_resource"))
				building.setHospitalResource((String) param.get("hospital_resource"));
			if (null != param.get("exhibition_images"))
				building.setExhibitionImages((String) param.get("exhibition_images"));
			if (null != param.get("apartment_images"))
				building.setApartmentImages((String) param.get("apartment_images"));
			if (null != param.get("showings_images"))
				building.setShowingsImages((String) param.get("showings_images"));
			if (null != param.get("showings_videos"))
				building.setShowingsVideos((String) param.get("showings_videos"));
			building.setBuildingType((String) param.get("buildingType"));
			if (null != param.get("index_image"))
				building.setIndexImage((String) param.get("index_image"));

			//修改mogodb房源对应的商圈信息
			String ids = (String)param.get("trade");
		    List<HousePo> houseList = mainService.getHouseByBldId(building.getBldId());
		    iHouseSearchService.updateLocationIdAndName(houseList,ids);
			
			if (mainService.updateBuliding(building) != 0) {
				genErrOutputMapWithCode("fail to update building", ReturnCode.UPDATE_BUILDING_ERROR);
				return outputMap;
			}
			genSuccOutputMap();
		} catch (Exception e) {
			Log.SERVICE.error("更新楼盘异常", e);
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}
