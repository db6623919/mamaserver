package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.TradeArea;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface TradeAreaDao extends GenericDao<TradeArea> {

	int insertTradeArea(TradeArea tradeArea);
	List<TradeArea> getAllTradeArea(Map map);
	List<TradeArea> getTradeAreaById(TradeArea tradeArea);
	List<TradeArea> getTradeAreaByPar(TradeArea tradeArea);
	int delTradeAreaById(TradeArea tradeArea);
	int updateTradeArea(TradeArea tradeArea);
	
}