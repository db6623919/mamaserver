/*百度地图*/
var map;
var longitude;
var latitude;
function initialize(suggest,longitude,latitude) {
	this.longitude=longitude;
	this.latitude=latitude;
	var suggest=$("#"+suggest).val();
	var lng=$("#"+longitude).val();
	var lat=$("#"+latitude).val();
	map = new BMap.Map('baidu_map');
	if(lng!=''&&lat!=''){
		var p=new BMap.Point(lng,lat);
		var initMarket=resetMapCenter(p);
		initMarket.addEventListener("click",function(){
		});
	}else{
		map.centerAndZoom("北京", 12); // 初始化地图,设置城市和地图级别。
	}
	map.addControl(new BMap.NavigationControl());
	map.addControl(new BMap.ScaleControl());
	map.addControl(new BMap.OverviewMapControl());
	map.addControl(new BMap.MapTypeControl());
	map.enableScrollWheelZoom(false);
	map.addEventListener("click", function(e){
		resetMapCenter(e.point);
		setLongitudeAndLatitude(longitude,latitude,e.point);
	});
	var ac = new BMap.Autocomplete( //建立一个自动完成的对象
		{
			"input": "suggest",
			"location": map
		});
	//ac.search(suggest);
	ac.setInputValue(suggest);
	ac.addEventListener("onhighlight", function(e) { //鼠标放在下拉列表上的事件
		var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province + _value.city + _value.district + _value.street + _value.business;
		}
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province + _value.city + _value.district + _value.street + _value.business;
		}
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
		G("searchResultPanel").innerHTML = str;
	});
	var myValue;
	ac.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
		var _value = e.item.value;
		myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
		G("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		setPlace(map, myValue);
	});
}

 /*参数说明 suggest:详细地址 ； longitude：经度 ；latitude：纬度 。对象为id*/
function loadScript(suggest,longitude,latitude) {
	var script = document.createElement("script");
	script.src = "http://api.map.baidu.com/api?v=2.0&ak=CF1924c9daf46e30c71c71db9c85a646&callback=initialize('"+suggest+"','"+longitude+"','"+latitude+"')"; //此为v2.0版本的引用方式  
	// http://api.map.baidu.com/api?v=1.4&ak=您的密钥&callback=initialize"; //此为v1.4版本及以前版本的引用方式  
	document.body.appendChild(script);
}

function G(id) {
	return document.getElementById(id);
}


function setPlace(map, myValue) {
	map.clearOverlays(); //清除地图上所有覆盖物
	function myFun() {
		var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
		map.centerAndZoom(pp, 18);
		var myMarker =resetMapCenter(pp);
		setLongitudeAndLatitude(longitude,latitude,myMarker.getPosition());
	}
	
	var local = new BMap.LocalSearch(map, { //智能搜索
		onSearchComplete: myFun
	});
	local.search(myValue);
}


function setLongitudeAndLatitude(longitude,latitude,p){
	$("#"+longitude).val(p.lng);
	$("#"+latitude).val(p.lat);
}




/*添加标注的描述， 参数说明 suggest：关键字，content：描述*/
function addMapContent(suggest, content, img) {
	var suggest = $("#" + suggest);
	var content = $("#" + content);
	var img = $("#" + img);
	
	var sContent =
		"<h4 style='margin:0 0 5px 0;padding:0.2em 0'>" + suggest.val() + "</h4>" +
		"<p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>" + content.val() + "</p>" +
		"</div>";
	var p=map.getCenter();
	var marker=resetMapCenter(p);        // 将标注添加到地图中
	var infoWindow = new BMap.InfoWindow(sContent); // 创建信息窗口对象
	marker.openInfoWindow(infoWindow);
	marker.addEventListener("click", function(e) {
		this.openInfoWindow(infoWindow);
		/*//图片加载完毕重绘infowindow
		document.getElementById('imgDemo').onload = function() {
			infoWindow.redraw(); //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
		}*/
		
	});

}
/*用经纬度设置地图中心点,返回新的marker*/
function resetMapCenter(p){
	var marker = new BMap.Marker(p);
	marker.enableDragging();
	map.clearOverlays(); //清除地图上所有覆盖物
	map.centerAndZoom(p,18);
	map.addOverlay(marker);  
	marker.setAnimation(BMAP_ANIMATION_BOUNCE);
	marker.addEventListener("dragstart",function(){
	});
	marker.addEventListener("dragging",function(){
		setLongitudeAndLatitude(longitude,latitude,this.getPosition());
	});
	marker.addEventListener("dragend",function(){
		setLongitudeAndLatitude(longitude,latitude,this.getPosition());
	});
	return marker;
}

