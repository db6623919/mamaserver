package com.mama.server.main.controller.handler.main;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;

@Component
public class GetBuildingInfoHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("bldId") == null) {
                genErrOutputMapWithCode("param error, bldId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int bldId = (Integer)param.get("bldId");
            BuildingPo po = new BuildingPo();
            po.setBldId(bldId);
            List<BuildingPo> buildingList = mainService.getBuilding(po);
            if (buildingList != null) {
                BuildingPo building = null;
                for (int i = 0; i < buildingList.size(); ++i) {
                    if (buildingList.get(i).getBldId() == bldId) {
                        building = buildingList.get(i);
                        break;
                    }
                }
                if (building == null) {
                    genErrOutputMapWithCode("param error, invalid bld id", ReturnCode.PARAM_ERROR);
                    return outputMap;
                }
                dataMap.put("bldId", building.getBldId());
                dataMap.put("devId", building.getDevId());
                dataMap.put("provId", building.getProvId());
                dataMap.put("cityId", building.getCityId());
                dataMap.put("showDetail", building.getShowDetail());
                dataMap.put("type", building.getType());
                dataMap.put("mark", building.getMark());
                dataMap.put("removed", building.getRemoved());
                dataMap.put("name", building.getName());
                dataMap.put("project_introduction", building.getProjectIntroduction());
                dataMap.put("unit_area", building.getUnitArea());
                dataMap.put("built_area", building.getBuiltArea());
                dataMap.put("average_price", building.getAveragePrice());
                dataMap.put("property_type", building.getPropertyType());
                dataMap.put("decoration_standard", building.getDecorationStandard());
                dataMap.put("built_address", building.getBuiltAddress());
                dataMap.put("open_date", building.getOpenDate());
                dataMap.put("purchase_discount", building.getPurchaseDiscount());
                dataMap.put("consultant_phone", building.getConsultantPhone());
                dataMap.put("develop_name", building.getDevelopName());
                dataMap.put("launch_date", building.getLaunchDate());
                dataMap.put("year_limit", building.getYearLimit());
                dataMap.put("built_status", building.getBuiltStatus());
                dataMap.put("built_type", building.getBuiltType());
                dataMap.put("volume_rate", building.getVolumeRate());
                dataMap.put("greening_rate", building.getGreeningRate());
                dataMap.put("plan_households", building.getPlanHouseholds());
                dataMap.put("plan_parking", building.getPlanParking());
                dataMap.put("presale_permit", building.getPresalePermit());
                dataMap.put("property_comp_name", building.getPropertyCompName());
                dataMap.put("property_fee", building.getPropertyFee());
                dataMap.put("hearting_mode", building.getHeartingMode());
                dataMap.put("water_elec", building.getWaterElec());
                dataMap.put("project_feature", building.getProjectFeature());
                dataMap.put("scenic_resource", building.getScenicResource());
                dataMap.put("humanity_matching", building.getHumanityMatching());
                dataMap.put("education_matching", building.getEducationMatching());
                dataMap.put("business_matching", building.getBusinessMatching());
                dataMap.put("commerce_matching", building.getCommerceMatching());
                dataMap.put("leisure_matching", building.getLeisureMatching());
                dataMap.put("hospital_resource", building.getHospitalResource());
                dataMap.put("exhibition_images", building.getExhibitionImages());
                dataMap.put("apartment_images", building.getApartmentImages());
                dataMap.put("showings_images", building.getShowingsImages());
                dataMap.put("showings_videos", building.getShowingsVideos());
                dataMap.put("building_type", building.getBuildingType());
                dataMap.put("index_image", building.getIndexImage());
                dataMap.put("trade_area", building.getTrade_area());
                genSuccOutputMap();
            } else {
                genErrOutputMapWithCode("fail to get building info", ReturnCode.GET_BUILDING_ERROR);
            }
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}