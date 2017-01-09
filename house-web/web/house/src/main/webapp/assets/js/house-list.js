var selPosition;
var selMore;
var selSort;

$(function() {
	var adaptPageClass = "content";
	adaptPage(adaptPageClass);
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
    }, 1000);
	
	
	
	var this_width = $(window).width(); //当前浏览器可视窗口的宽度
	if (this_width > 640) {
		this_width = 640;
	}
	var font_size = parseInt((this_width - 320) * (3 / 55) + 10);
	if (font_size > 14) {
		font_size = 14 + 'px';
	} else {
		font_size = font_size + 'px';
	}
	$("html").css("font-size", font_size); //响应式布局，根据不同屏幕宽度，设置字体大小
	

	$('.key-container input').bind('focus', function() {
	    $('.more-bottom').css('position', 'static');
	    $('.more-bottom').css('background-color', 'transparent');
	  }).bind('blur', function() {
	    $('.more-bottom').css({
	      'position': 'fixed',
	      'background-color': '#fff'
	    });
	  });
	
	/**
	 * 设置评论分数
	 */
//	var www = 2.5 * 11; //
//	$('.comment-point').css("width", www);

	//更多筛选高度
	var moreHei = window.innerHeight - 93;
	$('.sel-position').css('min-height', moreHei);
	$('.sel-more').css('min-height', moreHei);
	$('.sel-sort').css('min-height', moreHei);

	/**
	 *日期 插件
	 */
	initDatePlugin();
	/**
	 * 搜索框条件显示||隐藏
	 */
	$(".search-condition li").on('click', function() {
		var eleClass = $(this).attr('data-ele');
		if($(this).hasClass('m-active')) {
			$(this).removeClass('m-active');
			$("." + eleClass).hide()
		} else {
			$(this).siblings().each(function() {
				var eleClass = $(this).attr('data-ele');
				$(this).removeClass('m-active');
				$("." + eleClass).hide()
			})
			$(this).addClass('m-active');
			$("." + eleClass).show()
		}
	});
	/**
	 * 位置区域
	 */
	$('.sel-position ul li').on('click', function() {
//		if($(this).hasClass('m-active')) {
//			return;
//		}
		$(this).addClass('m-active');
		$(this).siblings().each(function() {
			$(this).removeClass('m-active');
		})

		selPosition = $(this).text();
		
		$("#tradeAreaId").val($(this).attr('tradeAreaId'));
		$("#position span").text(selPosition);
		$("#position").removeClass('m-active');
		$('.sel-position').hide();
		
		var cityId = $("#cityId").val(); 
		var tradeAreaId = $("#tradeAreaId").val();
		var priceRange = $("#priceRange").val();
		var personNum = $("#person-num").val();
		var roomNum = $("#room-num").val();
		var keyWord = $("#keyWord").val();
		var sortBy = $("#sortBy").val();

		var cityName = $("#cityName").val();
		var tag = getTags();
	    var el, li, i,pageNum,cityId;
	    el = document.getElementById('h-list');
	    
	    var url="/moreSearchHouseList.htm?pageNum=1&cityId="+cityId+"&tradeAreaId="+tradeAreaId+"&priceRange="+priceRange
	          +"&personNum="+personNum+"&roomNum="+roomNum+"&keyWord="+keyWord+"&cityName="+cityName+"&tag="+tag+"&sortBy="+sortBy;
	    searchMore(url);

	});
	/**
	 * 更多筛选--价格区间
	 */
	$('.price-container span').on('click', function() {
		if($(this).hasClass('m-active')) {
			return;
		}
		
		$("#priceRange").val($(this).attr('priceRange'));
		$(this).addClass('m-active');
		$(this).siblings().each(function() {
			$(this).removeClass('m-active');
		})

	})
	
		/**
	 * 入住人数 居室
	 */
	$('.count-container .minus').click(function() {
		var spanID = $(this).next().attr('id');
		var numText = spanID === 'person-num' ? '人' : '居';
		var nulText = spanID === 'person-num' ? '入住人数' : '居室';
		var num = $(this).next().val();
		if(num == '' || num === 0) {
			$(this).next().val('');
			$(this).next().text(nulText);
			return;
		}
		num--;
		if(num === 0) {
			num = '';
			$(this).find('img').attr('src', 'http://file.88mmmoney.com/5370cf92-6d90-471a-9f20-883082326ab4');
		}
		if(num===''){
			$(this).next().text(nulText);
		}else{
			$(this).next().text(num + numText);
		}
//		if(num<8){
//			$(this).next().next().find('img').attr('src', 'http://file.88mmmoney.com/52504ca9-c9f7-4051-be3e-eda1fc82d5b4')
//		}
		
		
		$(this).next().val(num);
	})
	$('.count-container .plus').click(function() {
			var spanID = $(this).prev().attr('id');
			var numText = spanID === 'person-num' ? '人 ' : '居';
			var num = $(this).prev().val();
			if(num == '')
				num = 0;
//			if(num == 8)
//				return;
			num++;
			$(this).prev().prev().find('img').attr('src', 'http://file.88mmmoney.com/96c3c55e-0556-4cc2-bf0c-4165127cecc7');
//			if(num == 8) {
//				$(this).find('img').attr('src', 'http://file.88mmmoney.com/a56098da-97d5-4599-9ccf-53c55bd6b3ad')
//			}

			$(this).prev().text(num + numText);
			
			$(this).prev().val(num)
		})

	/**
	 * 更多筛选--标签
	 */

	$('.tag-container .tags div').on('click', function() {
		if($(this).hasClass('m-active')) {
			$(this).removeClass('m-active');
		} else {
			$(this).addClass('m-active');
		}

	})

	/**
	 * 更多筛选--清除筛选
	 */
	$('#clear-more').on('click',function(){
		$('.price-container span').removeClass('m-active');
		 $('#person-num').text('入住人数');
		 $('#person-num').val('');
		 $('#room-num').val('');
		 $('#room-num').text('居室');
		$('.tag-container .tags div').removeClass('m-active');
		$('.key-container input').val('');
	})
	
	/**
	 * --清除筛选
	 */
//	$('.nodata-tips .clear-more').on('click',function(){
//		
//	})
	
	
	/**
	 * 更多筛选--确定
	 */
	$('#more-confirm').on('click',function(){
		$("#more").removeClass('m-active');
		$('.sel-more').hide();
		var cityId = $("#cityId").val(); 
		var tradeAreaId = $("#tradeAreaId").val();
		var priceRange = $("#priceRange").val();
		var personNum = $("#person-num").val();
		var roomNum = $("#room-num").val();
		var keyWord = $("#keyWord").val();
		var cityName = $("#cityName").val();
		var sortBy = $("#sortBy").val();
		var tag = getTags();
		
	    var el, li, i,pageNum,cityId;
	    el = document.getElementById('h-list');
	    var url="/moreSearchHouseList.htm?pageNum=1&cityId="+cityId+"&tradeAreaId="+tradeAreaId+"&priceRange="+priceRange
	          +"&personNum="+personNum+"&roomNum="+roomNum+"&keyWord="+keyWord+"&cityName="+cityName+"&tag="+tag+"&sortBy="+sortBy;
	    searchMore(url);
		
	})

	//推荐排序
	$('.sel-sort ul li').on('click', function() {
//		if($(this).hasClass('m-active')) {
//			return;
//		}
		$(this).addClass('m-active');
		$(this).siblings().each(function() {
			$(this).removeClass('m-active');
		})

		selSort = $(this).text();
		$("#sort span").text(selSort);
		$("#sort").removeClass('m-active');
		$('.sel-sort').hide();
		$("#sortBy").val($(this).attr('sortBy'));
		
		var cityId = $("#cityId").val(); 
		var tradeAreaId = $("#tradeAreaId").val();
		var priceRange = $("#priceRange").val();
		var personNum = $("#person-num").val();
		var roomNum = $("#room-num").val();
		var keyWord = $("#keyWord").val();
		var sortBy = $(this).attr('sortBy');
		var cityName = $("#cityName").val();
		var tag = getTags();
//		alert( $(this).attr('sortBy')+"======"+$("#sortBy").val()+"======="+sortBy);
		var url = "/moreSearchHouseList.htm?pageNum=1&cityId="+cityId+"&tradeAreaId="+tradeAreaId+"&priceRange="+priceRange
        +"&personNum="+personNum+"&roomNum="+roomNum+"&keyWord="+keyWord+"&cityName="+cityName+"&tag="+tag+"&sortBy="+sortBy;
		searchMore(url);
	})

})

function adaptPage(id) {
	var sWidth = window.innerWidth;
	var sHeight = window.innerHeight;
	var plat = navigator.platform;
	if ((sWidth > 420) && (plat.indexOf("Android") > -1 || plat.indexOf("iPhone") > -1)) {
		if (document.compatMode == "CSSICompat") {
			sWidth = document.documentElement.clientWidth;
			sHeight = document.documentElement.clientHeight;
		} else {
			sWidth = document.body.clientWidth;
			sHeight = document.body.clientHeight;
		}
	}

	$("." + id).css("width", sWidth);
	$("." + id).css("max-width", "640px");
	$("." + id).css("margin", "0 auto");
	$("." + id).css("min-height", sHeight);
}


function getTags() {
    var tags='';
    $('.tag-container .tags div.m-active').each(function() {
      tags += ',' + $(this).attr('tag');
    });
//    alert('tags='+tags);
    return tags;
  }

function clearSel(){
	$('.price-container span').removeClass('m-active');
	 $('#person-num').text('入住人数');
	 $('#person-num').val('');
	 $('#room-num').val('');
	 $('#room-num').text('居室');
	$('.tag-container .tags div').removeClass('m-active');
	$('.key-container input').val('');
	//date
	$('.sel-date ul li').each(function() {
		$(this).removeClass('m-active');
	})
	$('#live-date').text('入住时间');
	
	//sort 
	$('.sel-sort ul li').each(function() {
		$(this).removeClass('m-active');
	})
	$('#sort').text('推荐排序');
	
	//position
	$('.sel-position ul li').each(function() {
		$(this).removeClass('m-active');
	})
	$("#position span").text('位置区域');
	$("#tradeAreaId").val('-1');
	$("#priceRange").val('-1');
	var cityId = $("#cityId").val(); 
	var url = "/moreSearchHouseList.htm?pageNum=1&cityId="+cityId;
	searchMore(url);
	
}

function searchMore(url){

	$.ajax({
		type: "POST",
        url:url,
		data: '',
		success: function(data) {
			var Data = eval(data);
			var exp = Data.result;
			var json1 = exp.list; // item是数组
			var data = '';
			if(json1==""||json1==null){
				data += "<div class=\"nodata-tips\"><p style=\"\">没有找到符合条件的房间</p>";
				data +=	"<div class=\"clear-more\" onClick='clearSel()'>清除筛选</div></div>";	
			}else{
				$.each(json1,function(i,rs){
				    
				    var tag = ""+json1[i].tagNameList+"";
				    var tagNameList= new Array(); 
				    tagNameList = tag.split(","); //字符分割 
				    data += "<div class=\"house-item\"><a href=\"/house/toDetail.htm?houseId="+json1[i].houseId+"\"><img xzsrc=\""+json1[i].introductionImg[0]+ "!h5i6s" + "\" class=\"lazy_load_img\" src=\"/assets/i/common/lazy_image.jpg\"  /></a>";
				   
				    data += "<span class='span-collection' style='cursor:pointer;'>";
				    if (json1[i].collectFlag == 1) {
				    	data += "<img onclick='checkColelect(this," + json1[i].houseId + ")' src='http://file.88mmmoney.com/4f5f0618-9ed0-4a29-a871-af3ba9be0355' />";
				    	
				    } else {
				    	data += "<img onclick='checkColelect(this," + json1[i].houseId + ")' src='http://file.88mmmoney.com/c9d46234-f7b9-4614-b09a-5136724009d6' />"; 
				    }
					data += "</span>";    
			        
				    data += "<div class=\"hot-bottom\"><p class=\"price\">¥ <span>"+json1[i].memTotalAmt+"</span>起/晚</p></div></div>";
				    data += "<p class=\"name\">"+json1[i].houseName+"</p>";
				    
				    data += "<div class=\"house-special\">";
				    for (i=0;i<tagNameList.length ;i++ ){  
				    	data += "<span>"+tagNameList[i]+"</span>";
				      }
				    data += "</div>";
//				    data += "<div class=\"house-comment\"><div class=\"comment-star\" style=\"\"><div class=\"comment-point\" style=\"width:55px;\"></div></div>"+
//					        "<span>5.0分</span><span>0条点评</span></div>";
				});
			}

			$("#h-list").empty();
			$("#h-list").append(data);
			$("#pageNum").val(Data.result.pageNum);
			myScroll.refresh();
			
			setTimeout(function(){
				document.getElementById('scroller').style.transform = 'translate(' + '0,'+ '0)' ;
				reload();
			},800);
			
		}
	});
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
 * 初始化日期
 */
function initDatePlugin() {
	var startDate = '20160725';
	var endDate = '20160726';
	startDate = startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6, 8);
	endDate = endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-" + endDate.substring(6, 8);

	var today = new Date();
	/*用来查询结束月份*/
	var priceEndDate = new Date(today.getFullYear(), (today.getMonth() + 5), today.getDate());
	var dateData = {};
	var special;

	special = (dateData && dateData.data) ? showPrice(dateData.data.items) : new Object;

	showClendar();
	var noBanner = '';

	function showClendar() {

		var bg = startDate;
		var ed = endDate;
		if(bg != '') {
			bg = startDate;
		} else {
			bg = '';
		}
		if(ed != '') {
			ed = endDate;
		} else {
			ed = '';
		}
		var m = {
			sBegin: bg,
			sEnd: ed
		};
		var options = {
			parentEl: '#reportrange01',
			startDate: bg,
			endDate: ed,
			veiwType: 'view',
			noBanner: noBanner,
			mindays: 1,
			special: special,
			enterFun: function() {
				$("#indexPage").hide();
			},
			backFun: function() {
				$("#indexPage").show();
			}
		};
		$('#live-date').daterangepicker(options, selectdate);

		//获取日期差
		var evt = document.createEvent("MouseEvents");
		evt.initEvent("click", true, true);
		document.getElementById("live-date").dispatchEvent(evt);
	}
	//日历日期选择后日期与晚数赋值
	function selectdate(startdate, enddate) {
		//$("#startdate").html(startdate);
		//$("#enddate").html(enddate);
		var startObj = moment(startdate, this.format);
		var endObj = moment(enddate, this.format);
		var totalDays = parseInt((endObj - startObj) / 1000 / 60 / 60 / 24);
		if(totalDays >= 1) {
			$(".daterangepicker").hide();
			var selDate = startdate.substring(5, 11).replace('-', '.') + '-' + enddate.substring(5, 11).replace('-', '.');
			$('#live-date span').text(selDate)
			$('#live-date').removeClass('m-active');
		}
	}
}