
var ajaxFun = function(d){
  $.ajax({
    type:d.type || 'post',
    url:d.url|| '',
    data:d.data || {},
    async:d.async || true,
    dataType: d.dataType || 'json',
    success:d.success,
    error:d.error || function(d){alert('访问失败了，请刷新重试');}
  });
};

var ajaxMess = {
	fail:function(f){
		f = f?f:$(".content");
		f.html('<p class="noData">访问失败了，请刷新重试</p>');
	}
};
var getCookie = function(objName) {
     var arrStr = document.cookie.split("; ");
     for(var i = 0;i < arrStr.length;i ++){
            var temp = arrStr[i].split("=");
            if(temp[0] == objName) return decodeURI(temp[1]);
     }
};

var GetRequest = function(url) {
   var url = url?url:location.search; //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
      }
   }
   return theRequest;
};


var locHref=function(url){
	window.location.href=url;
	};

var numToDate = function(n){
	return new Date(parseInt(n) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " "); 	
};
var returnDate = function(s){
  return new Date(s.split("-")[0], (s.split("-")[1] - 1), s.split("-")[2])
};

var showEndInput = function(start,end,showWeek) {
    var s = start.html().split(" ")[0];
    var sDate = returnDate(s);
    var e = end.html().split(" ")[0];
    var eDate = returnDate(e);
    if(sDate>=eDate){
        var newDate = new Date(sDate.getFullYear(), sDate.getMonth(), (sDate.getDate() + offset));
        var weekDays = ['日', '一', '二', '三', '四', '五', '六'];
        var week = showWeek?' 周' + weekDays[newDate.getDay()]:"";
        end.text(newDate.getFullYear() + '-' + (10 <= newDate.getMonth() + 1 ? newDate.getMonth() + 1 : "0" + (newDate.getMonth() + 1)) + '-' + (10 <= newDate.getDate() ? newDate.getDate() : "0" + newDate.getDate()) + week);
    }
    
};

//日期控件-周 固定位置
var weekTilteFixed = function(){
  $(window).scroll(function() {
        var f = $(".datepicker_title");
        f.each(function(){
          var top = 44,left=0;
          var h = f.height();
            if ($(window).scrollTop() < top) {
                f.css({
                        "position": "relative",
                        "left": "0px"
                })
            } else {
                f.css({
                        "position": "fixed",
                        "left": left + "px"
                });
            }  
        });
    });
};

var returnUserInfo = function(){
    			var UserInfo =getCookie("MayiUserInfo");
    			if(UserInfo){
    				UserInfo = unescape(UserInfo);
    				var pattern = new RegExp(/^\".+\"$/);
	        	if (pattern.exec(UserInfo)) {
	    				UserInfo = UserInfo.substring(1,UserInfo.length-1);
	    			}
    				return eval('(' + UserInfo + ')');	
    			}else{
    				return;
    			}
    			
    		}

var showTip=function(tip,time){
	 if(!time||typeof(time)!='number'){
		 time=2
	 }
	 if(typeof(tip) != "undefined"&&tip!=''){
		 var context=$('body');
		 var tiparticle=$("#article_showTip");
		 if(typeof(tiparticle) != "undefined")
			 tiparticle.remove();
		 var html='<article id="article_showTip" class="tc-ar"><b>'+tip+'</b></article>';
		 context.append(html);
		 tiparticle=$("#article_showTip");
		 tiparticle.live('click',function(){
			 tiparticle.remove();
		 })
		 setTimeout(function(){
			 tiparticle.remove();
         },1000*time);
	 }
}

$.extend($.fn, {
  hover: function() {
        $(this).each(function() {
            if (!$(this).hasClass("active")) {
                $(this).on("touchstart mousedown",
                function() {
                    $(this).addClass("active");
                    $(document).on("touchcancel touchmove mousedup",
                    function() {
                        $(this).removeClass("active");
                    });
                }).on("touchcancel touchmove touchend mouseup",
                function() {
                    $(this).removeClass("active");
                });
            }
        });
        return $(this);
  }
  
});

$(function(){

  /*hover*/
    $(".back,.login-in,.login-out,.user-cen,footer a").hover();//header，footer的渐变
    $(".list-item,.sort li,.room-list li,.list .elaborate,.list .rule,.select li,.order-list li.normal,.list a").hover();//列表
    $(".btn-ok,.btn-more,.local-search,.login .checkcode,.localCen,.up,.down").hover();//按钮背景颜色变化
    $(".order-fix .start,.order-fix .end").hover();//其它内容的背景颜色变化 
  
  /*返回顶部*/
  $("#goTop").on("click",function(){
    window.scrollTo(0,1);
  });
  
  /*鼠标按下去效果*/
  $.extend($.fn, {
    hover: function() {
      $(this).each(function() {
          if (!$(this).hasClass("active")) {
	          $(this).on("touchstart mousedown",
	          function() {
	              $(this).addClass("active");
	              $(document).on("touchcancel touchmove mousedup",
	              function() {
	                  $(this).removeClass("active");
	              });
	          }).on("touchcancel touchmove touchend mouseup",
	          function() {
	              $(this).removeClass("active");
	          });
          }
      });
      return $(this);
    }  
  });
});
function stringToDate(DateStr)  
{   
  
    var converted = Date.parse(DateStr);  
    var myDate = new Date(converted);  
    if (isNaN(myDate))  
    {   
        //var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';  
        var arys= DateStr.split('-');  
        myDate = new Date(arys[0],--arys[1],arys[2]);  
    }  
    return myDate;  
}  

function addDate(date,days){
  var d=new Date(date.replace(/-/g,"/")); 
    d.setDate(d.getDate()+days); 
    var m=d.getMonth()+1;
    if(m<10){
      m="0"+m;
    }
    var day=d.getDate();
    if(day<10){
      day="0"+day;
    }
    return d.getFullYear()+'-'+m+'-'+day; 
}

function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth()+1;//获取当前月份的日期
    if(m<10){
        m="0"+m;
     }
    var d = dd.getDate();
    if(d<10){
        d="0"+d;
     }
    return y+""+m+""+d;
}

function GetDateNextStr(dateStr, AddDayCount) {
	var val = Date.parse(dateStr);
	var dd = new Date(val);
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth()+1;//获取当前月份的日期
    if(m<10){
        m="0"+m;
     }
    var d = dd.getDate();
    if(d<10){
        d="0"+d;
     }
    return y+""+m+""+d;
}

function minusDate(date1,date2){
  var d1=new Date(date1.replace(/-/g,"/")); 
  var d2=new Date(date2.replace(/-/g,"/")); 
  return (d2.getTime() - d1.getTime())/(24 * 60 * 60 * 1000);
}