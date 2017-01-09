package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.CityPo;
import com.mama.server.main.dao.model.OpenIdInfoPo;
import com.mama.server.main.dao.model.ProvincePo;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface UtilDao{
	int insSms(SmsPo sp);
	List<SmsPo> getSmsByAllParam(SmsPo sp);
	int updateSmsById(SmsPo sp);
	int insProvince(ProvincePo pp);
	int checkProvince(String name);
	int updateProvinceByName(ProvincePo pro);
	List<ProvincePo> getProvince();
	List<ProvincePo> getProvinces(Map map);
	int insCity(CityPo cp);
	CityPo getCityById(int id);
	List<CityPo> getCity(Map map);
	List<CityPo> getCityByType(CityPo cp);
	List<CityPo> getCityByPar(CityPo cp);
	List<CityPo> getCityByName(CityPo cp);
    int updateCityById(CityPo cp);
    int updateProvinceById(ProvincePo cp);
    int insertOpen(OpenIdInfoPo op);
    int updateOpen(OpenIdInfoPo op);
    OpenIdInfoPo getOpenIdInfoPoByAllParam(OpenIdInfoPo op);
    int getCityByProId(CityPo city);
    CityPo checkCity(String name);
	int updateCityByName(CityPo city);
}