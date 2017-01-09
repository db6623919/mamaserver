//弹出子窗体
function openWindow(url,name,iWidth,iHeight)
{
 var url;                            //转向网页的地址;
 var name;                           //网页名称，可为空;
 var iWidth;                         //弹出窗口的宽度;
 var iHeight;                        //弹出窗口的高度;
 var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
 var iLeft =(window.screen.availWidth-10-iWidth)/2;        //获得窗口的水平位置;
 window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
}
//全选反选
function selectAll(checkBoxName) {
	var checkboxs=document.getElementsByName(checkBoxName);
	for (var i=0;i<checkboxs.length;i++) {
			var e=checkboxs[i];
			e.checked=!e.checked;
	}
}
//获取上个月今天
function getLastMonth(){
	 var up = new Date();
	 up.setMonth(up.getMonth()-1);
	 var up_year=up.getFullYear();
	 var up_month=up.getMonth()+1;
	 var up_day=up.getDate();
	 if(up_month<10){
		  up_month="0"+up_month;
	 }
	 if(up_day<10){
		  up_day="0"+up_day;
	 }	  
	 return up_year+"-"+up_month+"-"+up_day;
}
//获取今天
function getToday(){
	 var up = new Date();
	 var up_year=up.getFullYear();
	 var up_month=up.getMonth()+1;
	 var up_day=up.getDate();
	 if(up_month<10){
		  up_month="0"+up_month;
	 }
	 if(up_day<10){
		  up_day="0"+up_day;
	 }	  
	 return up_year+"-"+up_month+"-"+up_day;
}
//获取昨天
function getYesterday(){
	 var up = new Date();
	 up.setDate(up.getDate()-1);
	 var up_year=up.getFullYear();
	 var up_month=up.getMonth()+1;
	 var up_day=up.getDate();
	 if(up_month<10){
		  up_month="0"+up_month;
	 }
	 if(up_day<10){
		  up_day="0"+up_day;
	 }	  
	 return up_year+"-"+up_month+"-"+up_day;
}

var contextPath = "/miugoCSS";

/**
* jQuery ajax 超时处理 
**/
$(function(){
	sessionTimeout();
});

function sessionTimeout() {
	$(document).ajaxComplete(function(event, response, settings){
		var sessionStatus = response.getResponseHeader("sessionStatus");
		var mainUrl = parent.window.location.href;
		if(sessionStatus != null && sessionStatus == "enterpriseTimeout") {
			window.location=contextPath+'/enterprise/enterpriseSystem!enterpriseLogOut.shtml';
		}else if(sessionStatus != null && sessionStatus == "merchantTimeout") {
			window.location=contextPath+'/merchant/merchantSystem!merchantLogOut.shtml';
		}else if(sessionStatus != null && sessionStatus == "cssTimeout") {
			parent.window.location=contextPath+'/system!logout.shtml';
		}
	}); 
}