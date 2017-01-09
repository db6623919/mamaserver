package com.mama.server.main.controller.handler.customerservice;
import java.math.BigDecimal;
import java.util.*;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.*;
import com.mama.server.util.Json;
import com.mama.server.util.Log;

@Component
public class AddBuildingHandler extends BaseHandler {
    
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("devId") == null) {
                genErrOutputMapWithCode("param error, devId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("provId") == null) {
                genErrOutputMapWithCode("param error, provId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("cityId") == null) {
                genErrOutputMapWithCode("param error, cityId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("name") == null) {
                genErrOutputMapWithCode("param error, name required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("type") == null) {
                genErrOutputMapWithCode("param error, type required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("mark") == null) {
                genErrOutputMapWithCode("param error, mark required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("showDetail") == null) {
                genErrOutputMapWithCode("param error, showDetail required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            BuildingPo building = new BuildingPo();
            building.setDevId((Integer)param.get("devId"));
            building.setProvId((Integer)param.get("provId"));
            building.setCityId((Integer)param.get("cityId"));
            building.setShowDetail(Json.format(param.get("showDetail")));
            building.setType((Integer)param.get("type"));
            building.setMark((String)param.get("mark"));
            building.setRemoved(0);
            building.setName((String)param.get("name"));
            building.setTrade_area((String)param.get("trade"));
            if(null != param.get("project_introduction"))
            	building.setProjectIntroduction((String)param.get("project_introduction"));
            if(null != param.get("unit_area"))
            	building.setUnitArea((String)param.get("unit_area"));
            if(null != param.get("built_area"))
            	building.setBuiltArea(new BigDecimal((Double)param.get("built_area")));
            if(null != param.get("average_price"))
            	building.setAveragePrice((Integer)param.get("average_price"));
            if(null != param.get("property_type"))
            	building.setPropertyType((String)param.get("property_type"));
            if(null != param.get("decoration_standard"))
            	building.setDecorationStandard((String)param.get("decoration_standard"));
            if(null != param.get("built_address"))
            	building.setBuiltAddress((String)param.get("built_address"));
            if(null != param.get("open_date"))
            	building.setOpenDate((String)param.get("open_date"));
            if(null != param.get("purchase_discount"))
            	building.setPurchaseDiscount((String)param.get("purchase_discount"));
            if(null != param.get("consultant_phone"))
            	building.setConsultantPhone((String)param.get("consultant_phone"));
            if(null != param.get("develop_name"))
            	building.setDevelopName((String)param.get("develop_name"));
            if(null != param.get("launch_date"))
            	building.setLaunchDate((String)param.get("launch_date"));
            if(null != param.get("year_limit"))
            	building.setYearLimit((Integer)param.get("year_limit"));
            building.setBuiltStatus(0);
            if(null != param.get("built_type"))
            	building.setBuiltType((String)param.get("built_type"));
            if(null != param.get("volume_rate"))
            	building.setVolumeRate((String)param.get("volume_rate"));
            if(null != param.get("greening_rate"))
            	building.setGreeningRate((String)param.get("greening_rate"));
            if(null != param.get("plan_households"))
            	building.setPlanHouseholds((String)param.get("plan_households"));
            if(null != param.get("plan_parking"))
            	building.setPlanParking((String)param.get("plan_parking"));
            if(null != param.get("presale_permit"))
            	building.setPresalePermit((String)param.get("presale_permit"));
            if(null != param.get("property_comp_name"))
            	building.setPropertyCompName((String)param.get("property_comp_name"));
            if(null != param.get("property_fee"))
            	building.setPropertyFee((String)param.get("property_fee"));
            if(null != param.get("hearting_mode"))
            	building.setHeartingMode((String)param.get("hearting_mode"));
            if(null != param.get("water_elec"))
            	building.setWaterElec((String)param.get("water_elec"));
            if(null != param.get("project_feature"))
            	building.setProjectFeature((String)param.get("project_feature"));
            if(null != param.get("scenic_resource"))
            	building.setScenicResource((String)param.get("scenic_resource"));
            if(null != param.get("humanity_matching"))
            	building.setHumanityMatching((String)param.get("humanity_matching"));
            if(null != param.get("education_matching"))
            	building.setEducationMatching((String)param.get("education_matching"));
            if(null != param.get("business_matching"))
            	building.setBusinessMatching((String)param.get("business_matching"));
            if(null != param.get("commerce_matching"))
            	building.setCommerceMatching((String)param.get("commerce_matching"));
            if(null != param.get("leisure_matching"))
            	building.setLeisureMatching((String)param.get("leisure_matching"));
            if(null != param.get("hospital_resource"))
            	building.setHospitalResource((String)param.get("hospital_resource"));
            if(null != param.get("exhibition_images"))
            	building.setExhibitionImages((String)param.get("exhibition_images"));
            if(null != param.get("apartment_images"))
            	building.setApartmentImages((String)param.get("apartment_images"));
            if(null != param.get("showings_images"))
            	building.setShowingsImages((String)param.get("showings_images"));
            if(null != param.get("showings_videos"))
            	building.setShowingsVideos((String)param.get("showings_videos"));
            building.setBuildingType((String)param.get("buildingType"));
            if(null != param.get("index_image"))
            	building.setIndexImage((String)param.get("index_image"));
            if (mainService.insertBuliding(building) != 0) {
                genErrOutputMapWithCode("fail to add building", ReturnCode.ADD_BUILDING_ERROR);
                return outputMap;
            }
            genSuccOutputMap();
        } catch (Exception e) {
        	Log.SERVICE.error("增加楼盘异常", e);
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
