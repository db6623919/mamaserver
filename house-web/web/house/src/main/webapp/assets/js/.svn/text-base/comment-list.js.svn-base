/**
 * Created by Adam on 2016/8/17.
 */
var commentList = window.commentList || {};

commentList.noData = false;// 是否还有数据表示为true说明没有数据了
commentList.flag = true;// 防重发标志
commentList.page = 2;
commentList.getStarCss = function(score){
	var scoreInt = Math.round(score);
	var scoreStr = score + "";
    var cssFlag = "";
    if((scoreInt < score) && (scoreInt != score)){
    	cssFlag = scoreInt + "5";
    }
    else if(scoreStr.indexOf(".5") > 0){
	    cssFlag = Math.floor(score) + "5";
    }
    else {
    	cssFlag = scoreInt + "0";
    }
    
    return cssFlag;
};
// 图片画廊
commentList.slick = function(ele) {
	ele.slick({
//		 responsive: [
//          {
//            breakpoint: 768,
//            settings: {
//              slidesToShow: 7,
//              slidesToScroll: 3
//            }
//          },
//          {
//            breakpoint: 414,
//            settings: {
//              slidesToShow: 5,
//              slidesToScroll: 2
//            }
//          },
//          {
//              breakpoint: 320,
//              settings: {
//                slidesToShow: 5,
//                slidesToScroll: 5
//              }
//            }
//        ],
		dot:true,
        slidesToShow: 5,
        slidesToScroll: 3,
        infinite: false,
		lazyLoad:'ondemand',
		variableWith: true,
        arrows: false
    });
}
// 点击看大图
commentList.clickShow = function(ele) {
	// 点击查看大图
	ele.each(function() {
        var photos = [];
        $(this).find('.img img').each(function() {
            photos.push($(this).data('lazy'));
        });
        $(this).find('.img').on('click', function () {
            var index = $(this).index();
            if ($('.photo-browser').length > 0) {
                $('.photo-browser').remove();
            }
            pb = Zepto.photoBrowser({
                initialSlide: index,
                photos: photos,
                ofText: '/',
                toolbar: false,
                theme: 'dark'
            });
            pb.open();
        });
    });
}
// 得到url参数
commentList.getUrlParam = function(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'),
    r = window.location.search.substr(1).match(reg);
	if (r != null) {
	  return decodeURIComponent(r[2]);
	} else {
	  return null;
	}
};

// 发送ajax
commentList._ajax = function() {
	var _this = this;

	if (_this.noData) {
		return ;
	}
	
	// 上次拉取数据还未结束，直接返回
	if(!_this.flag) {
		return ;
	}
    
	// 开始发送数据，flag设置为false
    _this.flag = false;
	var houseId = this.getUrlParam('houseId');
	$.ajax({
        type: "get",
        dataType: "json",
        timeout: "3000",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        async: false,
        url: "/house/getCommentsByPage.htm", // 你请求的地址
        data: {
            'houseId': houseId,
            'page': this.page
        },
        beforeSend: function () {
            $('#footer').html('加载中...');
        },
        success: function (data) {
            if (data.code == 0) {
            	if(data.results.length === 0) {
            		$('#footer').html('亲，没有更多内容了！');
            		_this.flag = true;
            		_this.noData = true;
                    return;
            	}
            	var this_html = '';
            	var $galleryList = [];
            	var idList = [];
                data.results.forEach(function (e) {
                	this_html += '<section class="group">';
                	if (e.recommendTime > 0) {
                    	this_html += '<div class="top"><span>精华</span></div>';
                	}
        			this_html += '<div class="head">';
        			this_html += '<span class="tel">'+e.userPhone+'</span>';
    	            this_html += '<span class="time">'+e.createTime+'</span>';
    	            this_html += '<ul data-score="'+e.score+'"  class="ul-score s-'+_this.getStarCss(e.score)+'">';
    	            this_html +=   '<li></li><li></li><li></li><li></li><li></li>';        	                
    	            this_html += '</ul></div>';
    	            this_html += '<div class="comment">';
    	            this_html += '<span>'+e.comments+'</span>';
    	            this_html += '</div>';
    	            if (e.images && e.images.length != 0) {
    	            	idList.push('#gallery-'+e.id); 
    	            	var gallery_html = '';
    	            	gallery_html += '<div id="gallery-'+e.id+'" class="gallery">';
    	            	e.images.forEach(function (i) {
    	            		gallery_html += '<div class="img">';
    	            		gallery_html += '<img data-lazy="'+i+'" alt=""/>';
    	            		gallery_html += '</div>';
    	            	});
    	            	gallery_html += '</div>';
    	   	            var $gallery = $(gallery_html);
    	   	            $galleryList.push($gallery);
    	            }
    	            this_html += gallery_html;
    	            this_html += '</section>';
                });
                $('#footer').html("");
  
                $("#commentList").append(this_html);
                var idListStr = idList.join(',');
                // 给新增加的元素绑定事件
                _this.slick($(idListStr));
                _this.clickShow($(idListStr));

                _this.page += 1;
            } else {
                $('#footer').html('亲，没有更多内容了！');
            }
        },
        complete: function () {
            _this.flag = true;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        	$('#footer').html('');
            alert(textStatus);
        }
    });
}
$(function () {

	
	// 窗口滑动事件
	$(window).scroll(function () {
        var scrollTop = $(this).scrollTop();// 滚动条在Y轴上的滚动距离
        var scrollHeight = $(document).height();// 文档的总高度
        var windowHeight = $(this).height();// 浏览器视口的高度
        if ((scrollTop + windowHeight) > (scrollHeight - 100) && commentList.flag) {
            commentList._ajax();
        }
    });
	
    // 图片画廊
	commentList.slick($('.gallery'));

    // 点击查看大图
	commentList.clickShow($('.gallery'));
       
    var score = $('#averageScore').val();
    var cssFlag = commentList.getStarCss(score);
    
    //设置平均评分的星样式
    $('#secSummray ul').attr("class", "ul-score s-" + cssFlag);
    
    //设置评分列表的星样式
    $('.group .head ul').each(function() {
		var commentScore = $(this).attr("data-score");
		cssFlag = commentList.getStarCss(commentScore);
		$(this).attr("class", "ul-score s-" + cssFlag);
	});
});



