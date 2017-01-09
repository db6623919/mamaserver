var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;
/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullDownAction () {
	var cityId = $("#cityId").val(); 
	var tradeAreaId = $("#tradeAreaId").val();
	var priceRange = $("#priceRange").val();
	var personNum = $("#person-num").val();
	var roomNum = $("#room-num").val();
	var keyWord = $("#keyWord").val();
	var sortBy = $("#sortBy").val();
	var cityName = $("#cityName").val();
	var tag = getTags();
	
	$("#pullDown").show();
    var el, li, i,pageNum,cityId;
    el = document.getElementById('h-list');
    cityId = $("#cityId").val();
	$.ajax({
		type: "POST",
	 	url: "/moreSearchHouseList.htm?pageNum=1&cityId="+cityId+"&tradeAreaId="+tradeAreaId+"&priceRange="+priceRange+"&personNum="+personNum
	     +"&roomNum="+roomNum+"&keyWord="+keyWord+"&sortBy="+sortBy+"&tag="+tag,
		data: '',
		success: function(data) {
			var Data = eval(data);
			var exp = Data.result;
			if (!exp && typeof(exp)!="undefined" && exp!=0)
			{
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '没有更多...';
				setTimeout(function(){$('#pullUp').hide();},500);
			    return;
			}
			var json1 = exp.list; // item是数组
			var data = '';
			$.each(json1,function(i,rs){
			    
			    var tag = ""+json1[i].tagNameList+"";
			    var tagNameList= new Array(); 
			    tagNameList = tag.split(","); //字符分割 
			    data += "<div class=\"house-item\"><a onclick=\"_czc.push(['_trackEvent', '房源详情', '房源详情-房源详情页', '房源详情-房源详情页','24','']);\" href=\"/house/toDetail.htm?houseId="+json1[i].houseId+"\" >" +
	    		"<img xzsrc=\""+json1[i].introductionImg[0] + "!h5i6s" +"\" class=\"lazy_load_img\" src=\"/assets/i/common/lazy_image.jpg\"   /></a>";
			    
			    data += "<div class=\"hot-bottom\"><p class=\"price\">¥ <span>"+json1[i].memTotalAmt+"</span>起/晚</p></div></div>";
			    data += "<p class=\"name\">"+json1[i].houseName+"</p>";
			    
			    data += "<div class=\"house-special\">";
			    for (i=0;i<tagNameList.length ;i++ ){  
			    	data += "<span>"+tagNameList[i]+"</span>";
			      }
			    data += "</div>";
//			    data += "<div class=\"house-comment\"><div class=\"comment-star\" style=\"\"><div class=\"comment-point\" style=\"width:55px;\"></div></div>"+
//				        "<span>5.0分</span><span>0条点评</span></div>";

			});
			$("#h-list").empty();
			$("#h-list").append(data);
			$("#pageNum").val(Data.result.pageNum);
			setTimeout(function(){
				$("#pullDown").hide();
				myScroll.refresh();    
				reload();
			},1500);
		}
	});
	
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	var cityId = $("#cityId").val(); 
	var tradeAreaId = $("#tradeAreaId").val();
	var priceRange = $("#priceRange").val();
	var personNum = $("#person-num").val();
	var roomNum = $("#room-num").val();
	var keyWord = $("#keyWord").val();
	var sortBy = $("#sortBy").val();
	var cityName = $("#cityName").val();
	var tag = getTags();
	
	$("#pullUp").show();
    var el, li, i,pageNum,cityId;
    el = document.getElementById('h-list');
    cityId = $("#cityId").val();
    pageNum = document.getElementById('pageNum').value;
    var next_page = parseInt(pageNum)+1;
	$.ajax({
		type: "POST",
	 	url: "/moreSearchHouseList.htm?pageNum="+next_page+"&cityId="+cityId+"&tradeAreaId="+tradeAreaId+"&priceRange="+priceRange+"&personNum="+personNum
	 	     +"&roomNum="+roomNum+"&keyWord="+keyWord+"&sortBy="+sortBy+"&tag="+tag,
		data: '',
		success: function(data) {
			var Data = eval(data);
			var exp = Data.result;
			var json1 = exp.list; // item是数组
			if (json1=="")
			{
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '没有更多...';
				setTimeout(function(){$('#pullUp').hide();},1500);
			    return;
			}
			var data = '';
			$.each(json1,function(i,rs){
			    
			    var tag = ""+json1[i].tagNameList+"";
			    var tagNameList= new Array(); 
			    tagNameList = tag.split(","); //字符分割 
			    data += "<div class=\"house-item\"><a onclick=\"_czc.push(['_trackEvent', '房源详情', '房源详情-房源详情页', '房源详情-房源详情页','24','']);\" href=\"/house/toDetail.htm?houseId="+json1[i].houseId+"\">" +
	    		"<img xzsrc=\""+json1[i].introductionImg[0] + "!h5i6s" +"\" class=\"lazy_load_img\" src=\"/assets/i/common/lazy_image.jpg\" /></a>";
			    data += "<div class=\"hot-bottom\"><p class=\"price\">¥ <span>"+json1[i].memTotalAmt+"</span>起/晚</p></div></div>";
			    data += "<p class=\"name\">"+json1[i].houseName+"</p>";
			    
			    data += "<div class=\"house-special\">";
			    for (i=0;i<tagNameList.length ;i++ ){  
			    	data += "<span>"+tagNameList[i]+"</span>";
			      }
			    data += "</div>";
//			    data += "<div class=\"house-comment\"><div class=\"comment-star\" style=\"\"><div class=\"comment-point\" style=\"width:55px;\"></div></div>"+
//				        "<span>5.0分</span><span>0条点评</span></div>";

			});
			$("#h-list").append(data);
			$("#pageNum").val(next_page);
			setTimeout(function(){
				$("#pullUp").hide();
				myScroll.refresh();
				reload();
			},1500);
		}
	})
     
}

function reload(){
	var lazyload = {};
    lazyload.img = function(obj) {
      var img = typeof(obj) == 'object' ? obj : $(obj);
      var xz_src = img.attr('xzsrc');
      if (xz_src && xz_src != 'ok') {
        img.attr('src', xz_src);
        img.attr('xzsrc', 'ok');
      }
    }
    var lazy_load_imgs = $('.lazy_load_img');
    setTimeout(function() {
      lazy_load_imgs.mmlazyload({
        replaceImage: false,
        pxOffset: 50,
        memorySaver: true
      });
    }, 300);
	
}

/**
 * 初始化iScroll控件
 */
function loaded() {
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');	
	pullUpOffset = pullUpEl.offsetHeight;
//	$('#pullUp').show();
//	$('#pullUp').css('opacity','0');
	myScroll = new iScroll('wrapper', {
		scrollbarClass: 'myScrollbar', /* 重要样式 */
		useTransition: false, /* 此属性不知用意，本人从true改为false */
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('fresh-loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
			} else if (pullUpEl.className.match('fresh-loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
			}
		},
		onScrollMove: function () {
			if (this.y > 45 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
				this.minScrollY = 0;
			} else if (this.y < 45 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
	
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
//			if (pullDownEl.className.match('flip')) {
//				pullDownEl.className = 'fresh-loading';
//				pullDownEl.querySelector('.pullDownLabel').innerHTML = '';	
//				if ($("#pullDown").css('display') === 'block') {
//					return;
//				}
//				pullDownAction();	// Execute custom function (ajax call?)
//			} else
				if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'fresh-loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '';	
				if ($("#pullUp").css('display') === 'block') {
					return;
				}
				pullUpAction();	// Execute custom function (ajax call?)
			}
		}
		
	});
	
	setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}

//初始化绑定iScroll控件 
//document.addEventListener('touchmove', function(e){ e.preventDefault();}, false);
window.addEventListener('load', loaded, false);