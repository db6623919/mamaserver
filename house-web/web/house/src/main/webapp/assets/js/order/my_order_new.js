(function() {
	// 防重发标志
	var flag = true;
	function _ajax(type) {
		
	    // 得到当前需要拉取的页数
	    var page = $('.order-list').eq(type).data('page');
		
		// 上次拉取数据还未结束，直接返回
		if(!flag) {
			return ;
		}
	    
		// 开始发送数据，flag设置为false
        flag = false;
		
		$.ajax({
	        type: "get",
	        dataType: "json",
	        timeout: "3000",
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",
	        async: false,
	        url: "/my/getOrdersJson.htm", // 你请求的地址
	        data: {
	            'page': page,
	            'type': type
	        },
	        beforeSend: function () {
	            $('#footer').html('加载中...');
	        },
	        success: function (data) {
	            if (data.code == 0) {
	            	if(data.orderInfoList.length === 0) {
	            		$('#footer').html('亲，没有更多内容了！');
	                    flag = true;
	                    return;
	            	}
	                var this_html = '';
	                data.orderInfoList.forEach(function (e) {
	                	this_html += '<div class="group">' +
			            				'<div class="title">' +
			        					'订单编号：' + e.orderId +
			        					'<span class="status red">' + 
			        						decodeURI(e.status) + 
			        					'</span>' +
			        					'</div>' +
				        				'<a class="details" onclick="orderDetail('+e.orderId+')">' +
				        					'<div class="row">' +
				        						'酒店：'+decodeURI(e.buildingName)+
				        					'</div>' + 
				        					'<div class="row">' +
				        						'房型：'+ decodeURI(e.houseName)+
				        					'</div>'+
				        					'<div class="row">'+
				        						'时间：'+e.beginTime + '到' + e.endTime +
				        					'</div>' +
				        					'<div class="row">'+
				        						'费用：￥'+ e.payAmt +
				        					'</div>'+
				        					'<i class="icon-arrow"/>'+
				        				'</a>';
	                    if (decodeURI(e.status)=="待审核") {
	                        this_html += '  <div class="footer">' +
			                                '    <a class="btn item-button" onclick="cancelOrder(' + e.orderId + ') ">取消订单</a>' + 
			                                '</div>';
	                    } else if(decodeURI(e.status) == "已完成") {
	                    	this_html += 
	                        '<div class="footer">' +
	                        '    <a class="btn item-button" onclick="_czc.push([\'_trackEvent\', \'订单\', \'订单-待评价状态下，评价按钮\', \'订单-待评价状态下，评价按钮-点击\',\'20\',\'\']);" href="/house/toComment.htm?houseId='+ e.houseId + '&orderId=' + e.orderId + '">去评价</a>' + 
	                        '</div>';
	                    } else if(decodeURI(e.status) == "待支付") {
	                    	this_html += 
	                    	'<div class="footer">' +
	                        '    <a class="btn item-button" href="/my/orderDetail.htm?orderId='+ e.orderId + '">去支付</a>' + 
	                        '</div>';
	                    } else if(decodeURI(e.status) == "待确认") {
	                    	this_html += 
	                    	'<div class="footer">' +
	                        '    <a class="btn item-button" href="/my/orderDetail.htm?orderId='+ e.orderId + '">去确认</a>' + 
	                        '</div>';
	                    }
	                    this_html += '</div>';
	                });
	                $('#footer').html("");
	                $(".order-list").eq(type).data('page',++page).append(this_html);
	            } else {
	                $('#footer').html('亲，没有更多内容了！');
	            }
	        },
	        complete: function () {
	            $('#footer').html('');
	            flag = true;
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	        	$('#footer').html('');
	            alert(textStatus);
	        }
	    });
	}

	window.onpageshow = function () {
	    // 初始化数据
	    _ajax(0);
	    _ajax(1);
	}
	
	$(function () {
				
		// 切换tab
		$('#my_ul li').on('click',function() {
			$('#my_ul li').removeClass('active');
			$('.order-list').removeClass('active');
			var index = $(this).index();
			$('#my_ul li').eq(index).addClass('active');
			$('.order-list').eq(index).addClass('active');
		});
		
		// 下拉刷新
	    $(window).scroll(function () {
	        var scrollTop = $(this).scrollTop();// 滚动条在Y轴上的滚动距离
	        var scrollHeight = $(document).height();// 文档的总高度
	        var windowHeight = $(this).height();// 浏览器视口的高度

	        if ((scrollTop + windowHeight) > (scrollHeight - 100) && flag) {
	    		// 得到当前查看的类型
	    	    var type = 0;
	    	    var is_rel = $("#wwc").hasClass("active");
	    	    if (is_rel == true) {
	    	        type = 0;
	    	    } else {
	    	        type = 1;
	    	    }
	            _ajax(type);
	        }
	    });
	});
})();

function orderDetail(orderId) {
    self.location.href = "/my/orderDetail.htm?orderId=" + orderId;
}

function cancelOrder(orderId) {
    $.ajax({
        type: 'post',
        data: 'orderId=' + orderId,
        url: "/my/cancelOrder.htm",
        success: function (data) {
            if (data.code == 0) {
                window.location.reload();
            } else {
                showTip("取消失败，请重试", 3);
            }
        }
    });
    event.stopPropagation();
}
