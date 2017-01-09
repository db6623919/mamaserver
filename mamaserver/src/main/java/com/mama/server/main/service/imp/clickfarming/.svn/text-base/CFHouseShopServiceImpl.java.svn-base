package com.mama.server.main.service.imp.clickfarming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mama.server.main.dao.clickfarming.CFHouseShopDao;
import com.mama.server.main.dao.model.clickfarming.CFHouseShopPo;
import com.mama.server.main.service.clickfarming.ICFHouseShopService;
import com.mama.server.main.vo.clickfarming.CFHouseShopVo;

/**
 * 刷单系统客栈服务实现类
 * @author lenovo
 *
 */
@Service
public class CFHouseShopServiceImpl implements ICFHouseShopService
{
	//日志
	private static final Logger log = LoggerFactory.getLogger(CFHouseShopServiceImpl.class);
	
	@Resource
	private CFHouseShopDao cfHouseShopDao;

	@Override
	public void modifyCFHouseShop(CFHouseShopVo vo)
	{
		log.info("start to add CFHouseShop.{}", vo);
		
		CFHouseShopPo po = convertVo2Po(vo);
		cfHouseShopDao.insertOrUpdatePo(po);
		
		log.info("add CFHouseShop succussfully");
	}

	private CFHouseShopPo convertVo2Po(CFHouseShopVo vo)
	{
		if (vo == null) {
			return null;
		}
		
		CFHouseShopPo po = new CFHouseShopPo();
		po.setId(vo.getId());
		po.setShopId(vo.getShopId());
		po.setTotalAmt(vo.getTotalAmt());
		po.setDiscount(vo.getDiscount());
		po.setDiscountLimit(vo.getDiscountLimit());
		
		return po;
	}

	@Override
	public CFHouseShopVo getCFHouseShop(int shopId)
	{
		log.info("start to get CFHouseShop, shopId = {}.", shopId);
		
		CFHouseShopPo selectPo = new CFHouseShopPo();
		selectPo.setShopId(shopId);
		
		CFHouseShopPo po = cfHouseShopDao.selectPo(selectPo);
		
		log.info("get CFHouseShop successfully");
		
		return convertPo2Vo(po);
	}

	private CFHouseShopVo convertPo2Vo(CFHouseShopPo po)
	{
		if (po == null) {
			return null;
		}
		
		CFHouseShopVo vo = new CFHouseShopVo();
		vo.setId(po.getId());
		vo.setShopId(po.getShopId());
		vo.setTotalAmt(po.getTotalAmt());
		vo.setDiscount(po.getDiscount());
		vo.setDiscountLimit(po.getDiscountLimit());
		vo.setDiscountType(po.getDiscountType());
		vo.setLowestAmt(po.getLowestAmt());
		return vo;
	}

	@Override
	public List<CFHouseShopVo> getAllCFHouseShop()
	{
		log.info("start to get all houseShop");
		
		List<CFHouseShopPo> pos = cfHouseShopDao.selectAll();
		if (CollectionUtils.isEmpty(pos)) {
			return null;
		}
		
		List<CFHouseShopVo> vos = new ArrayList<CFHouseShopVo>();
		for(CFHouseShopPo po : pos)
		{
			vos.add(convertPo2Vo(po));
		}
		
		log.info("get all houseShop successfully");
		
		return vos;
	}

	@Override
	public int getTotalFreezeAmt(int shopId,String day)
	{
		log.info("start to get totalFreezeAmt");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("shopId", shopId);
		map.put("day", day);
		if(cfHouseShopDao.selectFreezeAmtById(map) == null) {
			return 0;
		} 
		return cfHouseShopDao.selectFreezeAmtById(map);
	}

}
