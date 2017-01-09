// 只有调试环境为8089端口
window.debug = location.href.indexOf(":8089") > -1 ? true : false;
// baseurl
window.apiBase = window.debug ? 'http://localhost/house-web' : ''
// 公共弹窗错误文案
window.err = '网络故障，请重试'
// 路由
window.url = {
    'discoverIndex' : '/discover/index.htm', // 发现首页
    'discoverDetail' :  '/discover/detail.htm', //  发现详情
    'searchIndex' : '/search/index.htm', // 搜索首页
    'searchDetail' : '/search/detail.htm', // 搜索详情
    'orderSuccessWxpay' : '/my/weixin_pay/pay_success.htm', // 微信支付成功页面
    'orderSuccessAlipay' : '/pay/alipayReturnUrl.htm', // 支付宝支付成功页面
    'specialOffer99Index' : '/activity/special-offer-99/index.htm', // 99元特价房首页
    'specialOffer99List' : '/activity/special-offer-99/list.htm' // 99元特价房首页
}
// api
 window.api = {
 	'discoverIndex': window.apiBase + '/discover/getIndexJson.htm', // 发现首页
 	'discoverDetail': window.apiBase + '/discover/detailPage.htm', // 发现详情
  'searchResult': window.apiBase + '/house/searchResult.htm', // 搜索接口
  'getCityByName': window.apiBase + '/getCityByName.htm', // 通过城市名字查城市id
  'recommendList': window.apiBase + '/house/recommend_list.htm', // 搜索页面推荐房源列表
  'getCities': window.apiBase + '/house/getHotCities.htm', // 搜索页面获取城市列表
  'queryOrderStatus': window.apiBase + '/my/weixin_pay/queryorderstatus.htm' // 查询订单状态
 }

 // 99 元特价房数据
 window.specialOffer99Data = {
   index:[{
     id: 914,
     imgUrl: '/assets/i/mmsf/special-offer-99-xjw.png',
     title: '[深圳小酒窝1号店客栈]现代美式乡村风格+五星级高端客栈+休闲书吧',
     oldPrice: 168
   },{
     id: 891,
     imgUrl: '/assets/i/mmsf/special-offer-99-ml3h.png',
     title: '[深圳较场尾蜜里3号店客栈]无敌海景+单车、麻将、咖啡、烧烤等娱乐休闲设施',
     oldPrice: 188
   },{
     id: 904,
     imgUrl: '/assets/i/mmsf/special-offer-99-nc.png',
     title: '[深圳较场尾鸟巢海边客栈]VIP麻将房+自助式厨房+多功能西餐厅',
     oldPrice: 288
   },{
     id: 893,
     imgUrl: '/assets/i/mmsf/special-offer-99-cxzf.png',
     title: '[深圳大鹏所城城祥左府客栈]地中海风情+距离大鹏所城5米+酒吧+花茶吧',
     oldPrice: 188
   },{
     id: 896,
     imgUrl: '/assets/i/mmsf/special-offer-99-gala.png',
     title: '[深圳官湖gala空间客栈]北欧现代时尚风格+步行至海滩1分钟+窑鸡餐厅',
     oldPrice: 230
   },{
     id: 913,
     imgUrl: '/assets/i/mmsf/special-offer-99-ydyx.png',
     title: '[深圳较场尾雅典印象2号店客栈]西班牙浪漫风格+意大利咖啡+进口啤酒',
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
     title: '[深圳较场尾gala空间客栈]北欧风格+依山傍海+1分钟海滩距离',
     discount: 132
   },{
     id: 894,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-aqgy.png',
     title: '[深圳较场尾爱情公寓]蔚蓝海岸线畔+匠心特色的公寓',
     discount: 99
   },{
     id: 899,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-bakz.png',
     title: '[深圳较场尾彼岸客栈]小清新风情+多家餐馆和便利店',
     discount: 69
   },{
     id: 893,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-cxzf2hd.png',
     title: '[深圳较场尾城祥左府2号店]位于大鹏古城+通风采光较好',
     discount: 89
   },{
     id: 883,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-cxzf1hd.png',
     title: '[深圳较场尾城祥左府1号店]地中海风情+复古的壁灯+蓝白相间的床幔',
     discount: 139
   },{
     id: 900,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-hazx.png',
     title: '[深圳官湖海岸之星美景民宿]精装修客房+紧邻官湖角海岸线',
     discount: 69
   },{
     id: 885,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-jtql.png',
     title: '[深圳较场尾间堂青旅]以茶会友+休闲的生活空间',
     discount: 89
   },{
     id: 898,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-lqh.png',
     title: '[深圳较场尾乐其会海边度假屋]主人热情好客+整洁舒适',
     discount: 69
   },{
     id: 891,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-ml3h.png',
     title: '[深圳较场尾蜜里3号海边客栈]无敌海景房+麻将、电影、烧烤等娱乐设施',
     discount: 89
   },{
     id: 901,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-nxn.png',
     title: '[深圳较场尾那些年客栈]房间风格多变+住宿环境舒适',
     discount: 186
   },{
     id: 904,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-nc.png',
     title: '[深圳较场尾鸟巢海边客栈]多功能西餐厅+自助厨房+VIP麻将房',
     discount: 189
   },{
     id: 919,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-swkz.png',
     title: '[深圳较场尾水窝客栈]重拾心情的小窝+放松心灵的胜地',
     discount: 99
   },{
     id: 909,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-xmkz.png',
     title: '[深圳较场尾西美客栈]客房干净、卫生+娱乐性设施',
     discount: 89
   },{
     id: 917,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-xyh.png',
     title: '[深圳较场尾象屿海主题客栈]泰式主题元素+大露天阳台',
     discount: 59
   },{
     id: 914,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-xjw1h.png',
     title: '[深圳较场尾小酒窝1号店]五星级高端客栈+复古简约',
     discount: 69
   },{
     id: 912,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-xjw2h.png',
     title: '[深圳较场尾小酒窝2号店]一房一主题+自带游泳池和烧烤场',
     discount: 169
   },{
     id: 918,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-ydyx1h.png',
     title: '[深圳较场尾雅典印象1号店]电影/红酒/书吧+中西式早餐、下午茶',
     discount: 101
   },{
     id: 913,
     imgUrl: '/assets/i/mmsf/special-offer-99-list-ydyx2h.png',
     title: '[深圳较场尾雅典印象2号店]西班牙浪漫风格+下午茶或咖啡',
     discount: 101
   }]
 }
