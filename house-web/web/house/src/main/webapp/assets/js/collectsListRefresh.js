var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

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
	 	url: "/my/getCollectsPage.htm",
	 	data : {"pageNum":next_page},
		success: function(response) {
			var data = eval(response);
			var json1 = data.list; // item是数组
			if (json1 == null || json1 == "")
			{
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '没有更多...';
				setTimeout(function(){$('#pullUp').hide();},1500);
			    return;
			}
			var data = '';
			$.each(json1,function(i,rs){
			    
				data += "<div class='house-item'  id='div_" + json1[i].houseId + "'>";
				data += "	<img  class='lazy_load_img' src='#springUrl('/assets/i/common/lazy_image.jpg')' xzsrc='" + json1[i].imageUrl+'!h5i6s' + "' onclick='roomDetail(" + json1[i].houseId + ")'/>";
				data += "	<span class='span-collection'>";
				data += "	<img src='http://file.88mmmoney.com/a076edf3-9950-4431-862e-b2bfbe6ef4ed' onclick='removeHouse(" + json1[i].houseId + ")'/>";
				data += "	</span>";
				data += "	<div class='hot-bottom'>";
				data += "		<p class='price'>¥ <span>" + json1[i].price + "</span>/晚</p>";
				data += "	</div>";
				data += "</div>";
				data += "<p class='name'>" + json1[i].name + "</p>";
				data += "<div class='house-special'>";
				if(json1[i].tagNameList != null) {
					$.each(json1[i].tagNameList,function(y,tagName){
						data += "<span>" + tagName + "</span>";
					});
				}
				data += "</div>";
			});
			$("#h-list").append(data);
			$("#pageNum").val(next_page);
			setTimeout(function(){
				$("#pullUp").hide();
				myScroll.refresh();
				reloadData();
			},1500);
		}
	})
     
}
function reloadData(){
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