package com.mama.server.main.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.HouseShopPo;
import com.mama.server.main.dao.model.mongodb.HouseShop;
import com.mama.server.main.service.IHouseShopService;
import com.mama.server.main.service.MainService;
import com.mama.server.main.service.clickfarming.ICFHouseShopService;
import com.mama.server.main.vo.clickfarming.CFHouseShopVo;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.SmsUtil;


/**
 * 刷单优惠金额监控
 * @author whl
 *
 */
@Component
public class ClickFarmingAmtTask {
	
    //记录日志
    private static Logger logger = LoggerFactory.getLogger(ClickFarmingAmtTask.class);
    
    @Autowired
	private ICFHouseShopService icfHouseShopService;
    @Autowired
    private  IHouseShopService iHouseShopService;
    
	/**
	 *
	 *定时查询每个客栈优惠金额
	 */
	public void clickFarmingAmt() {
		logger.info("run start to clickFarmingAmt.");
		try {
			//所有优惠客栈
			List<CFHouseShopVo> cfHouseList = icfHouseShopService.getAllCFHouseShop();
			if (cfHouseList != null) {
				for (int i = 0 ; i < cfHouseList.size() ; i++) {
					CFHouseShopVo cfHouseShopVo = cfHouseList.get(i);
					//客栈以优惠金额
					int totalFreezeAmt = icfHouseShopService.getTotalFreezeAmt(cfHouseShopVo.getShopId(),"");
					//百分比大于0.85发送短信提示
					if ((float)totalFreezeAmt/(float)cfHouseShopVo.getTotalAmt() > 0.85) {
						HouseShop houseShop = iHouseShopService.searchHouseShopPoById(String.valueOf(cfHouseShopVo.getShopId()));
						if (houseShop != null) {
							String phones = PropertiesUtils.getClickFarmingPhone();
							String msg = "尊敬的管理员，客栈 [" + houseShop.getShopName() + "]的优惠额度已经达到总额度的85%， 请登录管理台进行修改，并且通知合作客栈活动优惠的最新状况。谢谢！ ";
							String[] arr = phones.split(",");
							for (String phone : arr) {
								SmsUtil.sendSms(phone, msg);
							}
						}
					}
				}
			}
			logger.info("run end to clickFarmingAmt.");
		} catch (Exception e) {
			logger.error("clickFarmingAmt() 定时任务出错.",e);
		}
		
	}
	
}
