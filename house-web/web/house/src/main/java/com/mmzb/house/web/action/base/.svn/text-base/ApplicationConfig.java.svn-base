package com.mmzb.house.web.action.base;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationConfig {
	
	private ApplicationConfig() {
	}
	
	private static class Singleton {
		private static final ApplicationConfig singleton = new ApplicationConfig();
	}

	public static final ApplicationConfig getInstance() {
		return Singleton.singleton;
	}

	//房屋描述
	public static ConcurrentHashMap<Integer, String> miaoshuMap = new ConcurrentHashMap<Integer, String>();
	
	//省份
    public static ConcurrentHashMap<Integer, String> provinceshuMap = new ConcurrentHashMap<Integer, String>();
	
    //城市
    public static ConcurrentHashMap<Integer, String> cityMap = new ConcurrentHashMap<Integer, String>();
    
    //房间信息
    public static ConcurrentHashMap<String, String> houseconfigMap = new ConcurrentHashMap<String, String>();
    
    //公众号关注列表
    public static Set<String>  wxGeneralSet = new HashSet<String>();
    
    
	static{
		miaoshuMap.put(1, "交通便捷");
		miaoshuMap.put(2, "轰趴聚会");
		miaoshuMap.put(3, "景观阳台");
		miaoshuMap.put(4, "全套家电");
		miaoshuMap.put(5, "绿化优美");
		miaoshuMap.put(6, "亲子家庭");
		miaoshuMap.put(7, "高尔夫球场");
		miaoshuMap.put(8, "毗邻景区");
		miaoshuMap.put(9, "美味小吃");
		miaoshuMap.put(10, "肥美海鲜");
		miaoshuMap.put(11, "阳光沙滩");
		miaoshuMap.put(12, "购物便利");
		miaoshuMap.put(13, "蜜月情侣");
		miaoshuMap.put(14, "娱乐齐全");
		miaoshuMap.put(15, "古色古香");
		miaoshuMap.put(16, "日式风格");
		miaoshuMap.put(17, "欧式风格");
		miaoshuMap.put(18, "现代风格");
		miaoshuMap.put(19, "简欧风格");
		miaoshuMap.put(20, "美式乡村");
		miaoshuMap.put(21, "高速WiFi");
		miaoshuMap.put(22, "充足车位");
		miaoshuMap.put(23, "全套厨具");
		miaoshuMap.put(24, "小资文艺");
		miaoshuMap.put(25, "景点免费");
		miaoshuMap.put(26, "带阳光房");
		miaoshuMap.put(27, "入室温泉");
		miaoshuMap.put(28, "温泉养生");
		
		//设施
		houseconfigMap.put("tv","有线电视");
		houseconfigMap.put("fridge","电冰箱");
		houseconfigMap.put("washing","洗衣机");
		houseconfigMap.put("conditione","空调");
		houseconfigMap.put("towels","毛巾");
		houseconfigMap.put("tooth","牙具");
		houseconfigMap.put("slipper","拖鞋");
		houseconfigMap.put("shampoo","洗发/沐浴露");
		houseconfigMap.put("hairdrier","吹风机");
		houseconfigMap.put("drinking","淋浴");
		houseconfigMap.put("shower","浴缸");
		houseconfigMap.put("hotpot","热水壶");
		houseconfigMap.put("heater","热水器");
		houseconfigMap.put("dryer","烘干机");
		houseconfigMap.put("smokedetector","烟雾探测器");
		houseconfigMap.put("heating","暖气");
		houseconfigMap.put("extinguisher","灭火器");
		houseconfigMap.put("aidkit","急救包");
		//配套
		houseconfigMap.put("wifi","WiFi");
		houseconfigMap.put("broadband","有线宽带");
		houseconfigMap.put("elevator","电梯");
		houseconfigMap.put("accesscard","门禁卡");
		houseconfigMap.put("securitystaff","保安");
		houseconfigMap.put("swimming","游泳池");
		houseconfigMap.put("store","便利店");
		houseconfigMap.put("parking","停车位");
		houseconfigMap.put("gym","健身房");
		houseconfigMap.put("playground","儿童乐园");
		houseconfigMap.put("wheelchair","无障碍设施");
		houseconfigMap.put("buzzer","蜂鸣器/无线对讲机");
	
		//其他
		houseconfigMap.put("smoking","可吸烟");
		houseconfigMap.put("cook","可做饭");
		houseconfigMap.put("pet","可带宠物");
		houseconfigMap.put("extrabed","可加床");
		houseconfigMap.put("party","适合聚会");
		houseconfigMap.put("guests","接待外宾");
		houseconfigMap.put("breakfast","提供早餐");
		houseconfigMap.put("childrenstay","欢迎孩子入住");
		
		
		
		
	}
}
