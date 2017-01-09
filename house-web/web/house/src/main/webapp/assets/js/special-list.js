var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;
/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullDownAction () {
	
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	$("#pullUp").show();
    var el, li, i,pageNum;
    el = document.getElementById('h-list');
    pageNum = document.getElementById('pageNum').value;
    var next_page = parseInt(pageNum)+1;
	$.ajax({
		type: "POST",
	 	url: "/moreSpecialsList.htm?pageNum="+next_page,
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
			    data+="<div class=\"item house-detail\"><a class=\"to-special\"  />  ";
			    data+="<img class='lazy_load_img' href=\"house/toDetail.htm?houseId="+json1[i].houseId+"\" onclick=\"detail("+json1[i].houseId+");\"  src=\"/assets/i/common/lazy_image.jpg\" xzsrc=\""+json1[i].imageUrl+"!h5i6s\" /> ";
			    data+="</a><div class=\"title\"><h3>"+json1[i].houseName+"</h3>";
			    if (json1[i].market_price > 0) {
				    data+="<span>市场价：¥"+json1[i].market_price+"起/晚</span>";
			    }
			    data+="</div><div class=\"desc\">";
			    data+="<span class=\"info\">"+json1[i].room+"室"+json1[i].office+"厅"+json1[i].toilet+"卫"+ json1[i].beds+"床 宜住"+json1[i].personNum+"人</span>";
			    data+="<span class=\"discount-cont\">";
			    if (json1[i].market_price > 0) {
			    	data+="<i>"+json1[i].discount+"折</i>";
			    }
			    data+="送房价:<span class=\"special-price\">¥<strong>"+json1[i].memTotalAmt+"</strong></span>";
			    data+="起/晚</span></div></div>"

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