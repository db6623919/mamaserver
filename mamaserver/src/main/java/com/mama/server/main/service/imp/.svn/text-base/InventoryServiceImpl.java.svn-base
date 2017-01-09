package com.mama.server.main.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.server.common.util.Contants;
import com.mama.server.main.dao.model.HouseOrderPo;
import com.mama.server.main.dao.model.mongodb.InventoryPo;
import com.mama.server.main.dao.mongodb.InventoryDao;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.service.InventoryService;
import com.mama.server.main.service.MainService;

/**
 * 库存service
 * @author dengbiao
 *
 */
@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	MainService mainService;
	
	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd ");
	
	@Override
	public void updateInventory(InventoryPo inventory) {
		inventoryDao.updateInventory(inventory);
	}

	/** 添加库存  **/
	@Override
	public void addInventory(int houseId,int totalRoomNum) {
		//未来90天库存
		for (int i = 0; i < 90; i++) {
			InventoryPo inventoryPo = new InventoryPo();
			inventoryPo.setHouseId(houseId);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i);
			String str_date =s.format(c.getTime()).trim();
			inventoryPo.setDate(str_date);
			
			//查询现有库存中记录
			HouseOrderPo houseOrder = new HouseOrderPo();
			houseOrder.setHouseId(houseId);
			houseOrder.setDate(str_date);
			houseOrder.setRemoved(Contants.REMOVED_STATUS);
			List<HouseOrderPo> list = mainService.getHouseOrderByAllParam(houseOrder);
			if (list.size()>0) {
				int order_inventory = 0;
				for (HouseOrderPo houseorder:list) {
					order_inventory+=houseorder.getTotalRoomNum();
				}
				inventoryPo.setInventory(totalRoomNum-order_inventory);
			}else {
				inventoryPo.setInventory(totalRoomNum);
			}
			if (inventoryDao.findInventory(inventoryPo)!=null&&!"".equals(inventoryDao.findInventory(inventoryPo))) {
				inventoryDao.updateInventory(inventoryPo);
			}else {
				inventoryDao.addInventory(inventoryPo);
			}
		}
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		String date =s.format(c.getTime()).trim();
		inventoryDao.delInventory(houseId,date);//删除过期库存记录
	}

	@Override
	public void deletInventory(long houseId) {

	}

	/**
	 * 查询库存
	 */
	public List<InventoryPo> findInventory(SearchCondition searchConditionVo,long houseId){
		 long livedTime = searchConditionVo.getLivedTime();
		 long leavedTime = searchConditionVo.getLeavedTime();
		 long betweenDays = (long)((leavedTime - livedTime) / (1000 * 60 * 60 *24) + 0.5);
		 List<InventoryPo> list = new ArrayList<InventoryPo>();
		 for (int i=0;i<betweenDays;i++) {
			 InventoryPo inventory = new InventoryPo();
				Calendar c = Calendar.getInstance();
				c.setTime(new Date(livedTime));
				c.add(Calendar.DATE, i);
				String str_date =s.format(c.getTime());
				inventory.setDate(str_date);
				inventory.setHouseId(houseId);
				InventoryPo inventoryPo = new InventoryPo();
				inventoryPo = inventoryDao.findInventory(inventory);
				if (null!=inventoryPo) {
					list.add(inventoryPo);
				}
		}
		 return list;
	}
	
	/**
	 * 查询库存
	 */
	public InventoryPo findInventoryByPar(InventoryPo inventoryPo){
		 return inventoryDao.findInventory(inventoryPo);
	}
}