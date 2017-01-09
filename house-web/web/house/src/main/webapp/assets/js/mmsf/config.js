// 只有调试环境为8089端口
window.debug = (location.href.indexOf("m.mmsfang") > -1 || location.href.indexOf("tm.mmsfang") > -1) ? false : true;
window.test = location.href.indexOf("tm.mmsfang") > -1 ? true : false;
 window.debug = false;
// baseurl
window.apiBase = window.debug ? '/house-web' : ''
// 公共弹窗错误文案
window.err = '网络故障，请重试'
// 路由
window.url = {
  // 发现
  'discoverIndex' : '/discover/index.htm', // 发现首页
  'discoverDetail' :  '/discover/detail.htm', //  发现详情

  // 搜索
  'searchIndex' : '/search/index.htm', // 搜索首页
  'searchDetail' : '/search/detail.htm', // 搜索详情

  // 订房
  'orderSuccessWxpay' : '/my/weixin_pay/pay_success.htm', // 微信支付成功页面
  'orderSuccessAlipay' : '/pay/alipayReturnUrl.htm', // 支付宝支付成功页面

  // 账户
  'accountLogin' : '/account/login.htm', // 登录，包括了注册
  'retrievePwd' : '/account/retrieve_pwd.htm', // 找回密码

  //// 活动
  //99特价房
  'specialOffer99Index' : '/activity/special_offer_99/index.htm', // 99元特价房首页
  'specialOffer99List' : '/activity/special_offer_99/list.htm', // 99元特价房首页
  'specialOffer99Rule' : '/activity/special_offer_99/rule.htm', // 规则
  //团建
  'teamBuildIndex' : '/activity/team_build/index.htm', // 团建首页

  // 券
  'couponList' : '/my/coupon/list.htm', // 券列表
  'couponStatus' : '/my/coupon/status.htm', // 每个订单下券领取状态列表
  'couponShare' : '/my/coupon/share.htm', // 券分享
  'couponDetail' : '/coupon/detail.htm', // 券详情
  'couponReceive' : '/coupon/receive.htm' // 券领取页面
}
// api
window.api = {
	'discoverIndex': window.apiBase + '/discover/getIndexJson.htm', // 发现首页
	'discoverDetail': window.apiBase + '/discover/detailPage.htm', // 发现详情
  'searchResult': window.apiBase + '/house/searchResult.htm', // 搜索接口
  'getCityByName': window.apiBase + '/getCityByName.htm', // 通过城市名字查城市id
  'recommendList': window.apiBase + '/house/recommend_list.htm', // 搜索页面推荐房源列表
  'getCities': window.apiBase + '/house/getHotCities.htm', // 搜索页面获取城市列表
  'queryOrderStatus': window.apiBase + '/my/weixin_pay/queryorderstatus.htm', // 查询订单状态
  'moreSpecialsList' : window.apiBase + '/moreSpecialsList.htm', // 特价房列表
  'wxSignature' : window.apiBase + '/activity/loadWx.htm', // 微信签名验证
  // n99
  'n99Info' : window.apiBase + '/n99Info.htm', // n99信息
  'n99HouseList' : window.apiBase + '/n99HouseList.htm', // n99列表
  // 券
  'couponListBuy' : window.apiBase + '/my/houseCardOrderList.htm', // 购买的券列表
  'couponListReceive' : window.apiBase + '/my/earnedHouseCardList.htm', // 领取的券列表
  'couponListExchange' : window.apiBase + '/my/exchangedHouseCardList.htm', // 兑换的券列表
  'couponListStatus' : window.apiBase + '/my/houseCardList.htm', // 每个订单下券领取状态列表
  'shareCoupon' : window.apiBase + '/my/shareHouseCardOfOrder.htm', // 根据订单号分享房券
  'shareOneCoupon' : window.apiBase + '/my/shareHouseCard.htm', // 分享单张房券
  'couponDetail' : window.apiBase + '/my/houseCardDetail.htm', // 查看券详情
  'couponDetailOpen' : window.apiBase + '/open/viewHouseCardDetail.htm', // 查看券详情-不需登录
  'couponReceive' : window.apiBase + '/open/receiveHouseCard.htm' // 领取券
}

// 分享配置
window.shareOption = {
  title: '全球精品美宿 “任我行”！戳我免费领房券',
  desc: '海量美宿，等你来抢>>',
  link: window.test ? 'http://tm.mmsfang.com/coupon/receive.htm' : 'http://m.mmsfang.com/coupon/receive.htm',
  imgUrl: 'http://file.88mmmoney.com/793a2b71-1146-4682-af9c-f17b14f2b766',
};

// 99 元特价房数据
window.specialOffer99Data = {
 index:[{
   id: 914,
   imgUrl: '/assets/i/mmsf/special-offer-99-xjw.png',
   title: '[深圳小酒窝1号店客栈]现代美式乡村风格+星级高端客栈+山景房',
   oldPrice: 168
 },{
   id: 891,
   imgUrl: '/assets/i/mmsf/special-offer-99-ml3h.png',
   title: '[深圳较场尾蜜里3号店客栈]一线海景+麻将、益智游戏、电影+烧烤场地',
   oldPrice: 188
 },{
   id: 904,
   imgUrl: '/assets/i/mmsf/special-offer-99-nc.png',
   title: '[深圳较场尾鸟巢海边客栈]房间宽敞+距离海滩仅十步之遥',
   oldPrice: 288
 },{
   id: 893,
   imgUrl: '/assets/i/mmsf/special-offer-99-cxzf.png',
   title: '[深圳城祥左府客栈]地中海风情+大鹏所城核心位置',
   oldPrice: 188
 },{
   id: 896,
   imgUrl: '/assets/i/mmsf/special-offer-99-gala.png',
   title: '[深圳官湖gala空间客栈]北欧现代时尚风格+美丽的官湖角海滩',
   oldPrice: 230
 },{
   id: 913,
   imgUrl: '/assets/i/mmsf/special-offer-99-ydyx.png',
   title: '[深圳较场尾雅典印象2号店客栈]西班牙浪漫风格+采光度极高的房间',
   oldPrice: 200
 }],

 list:[{
   id: 880,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-613xz.png',
   title: '[深圳较场尾613小栈]宽敞后院+林荫花香+KTV',
   discount: 169
 },{
   id: 896,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-gala.png',
   title: '[深圳官湖gala空间客栈]北欧现代时尚风格+美丽的官湖角海滩',
   discount: 132
 },{
   id: 894,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-aqgy.png',
   title: '[深圳较场尾爱情公寓]蔚蓝海岸线畔自助度假小屋',
   discount: 99
 },{
   id: 899,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-bakz.png',
   title: '[深圳官湖彼岸客栈]现代小清新风情+天台电影',
   discount: 69
 },{
   id: 893,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-cxzf2hd.png',
   title: '[深圳城祥左府2号店]大鹏所城核心位置+采光度好',
   discount: 89
 },{
   id: 883,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-cxzf1hd.png',
   title: '[深圳城祥左府1号店]地中海风情',
   discount: 139
 },{
   id: 900,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-hazx.png',
   title: '[深圳官湖海岸之星美景民宿]美式风格',
   discount: 69
 },{
   id: 885,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-jtql.png',
   title: '[深圳较场尾间堂经济店]以茶会友+休闲的生活空间',
   discount: 89
 },{
   id: 898,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-lqh.png',
   title: '[深圳较场尾乐其会海边度假屋]温馨浪漫海边小憩',
   discount: 69
 },{
   id: 891,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-ml3h.png',
   title: '[深圳较场尾蜜里3号店客栈]一线海景+麻将、益智游戏、电影+烧烤场地',
   discount: 89
 },{
   id: 901,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-nxn.png',
   title: '[深圳官湖那些年客栈]距离官湖角海滩仅十步之遥',
   discount: 186
 },{
   id: 904,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-nc.png',
   title: '[深圳较场尾鸟巢海边客栈]房间宽敞+距离海滩仅十步之遥',
   discount: 189
 },{
   id: 919,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-swkz.png',
   title: '[深圳较场尾水窝客栈]放松心灵的胜地',
   discount: 99
 },{
   id: 909,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-xmkz.png',
   title: '[深圳较场尾西美客栈]环境舒适+个性化',
   discount: 89
 },{
   id: 917,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-xyh.png',
   title: '[深圳较场尾象屿海主题客栈]泰式主题客栈',
   discount: 59
 },{
   id: 914,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-xjw1h.png',
   title: '[深圳小酒窝1号店客栈]现代美式乡村风格+星级高端客栈+山景房',
   discount: 69
 },{
   id: 912,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-xjw2h.png',
   title: '[深圳较场尾小酒窝2号店]梦幻温馨',
   discount: 169
 },{
   id: 918,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-ydyx1h.png',
   title: '[深圳较场尾雅典印象1号店]院子里面看海',
   discount: 101
 },{
   id: 913,
   imgUrl: '/assets/i/mmsf/special-offer-99-list-ydyx2h.png',
   title: '[深圳较场尾雅典印象2号店]西班牙浪漫风格+采光度极高的房间',
   discount: 101
 }]
}
