/**
 * Created by Adam on 2016/8/17.
 */
(function() {
	/**
	 * 倒计时
	 * @param seconds 秒数
	 * @param cb 倒计时后执行的函数
	 */
	var timer;
	function countDown (seconds,cb) {
	    $('#seconds').html(seconds+'s');
	    seconds -= 1
	    timer = setInterval(function(){
	        if (seconds <= 0) {
	            clearInterval(timer);
	            setTimeout(cb,500);
	        }
	        $('#seconds').html(seconds+'s');
	        seconds -= 1
	    },1000);
	}
	
	/** 查询房源订单状态 */
	function queryHouseOrderStatus(callback) {
		/** 调用后台查询支付结果 */
	    var orderId = $("#confirmOrderId").val();
	    var productCode = $("#confirmProductCode").val();
	    var tradeType = $("#confirmTradeType").val();
	    $.ajax({
			type : 'post',
			url : "/my/weixin_pay/queryorderstatus.htm",
			data : {"orderId": orderId,"productCode": productCode,"trade_type": tradeType},
			dataType: "json",
			success : function(resp) {
				callback(resp);
			}
		})
	}
	
	
    $(function () {
        // 动画
        Circles.create({
            id: "countDown",
            value: 100,
            radius: 32,
            number: 20,
            text: "",
            width: 2,
            colors: ['#d8d8d8', '#ff5858'],
            duration: 5000
        });
        $('.circles-wrp').append($("<span id='seconds'></span>"));
        countDown(5,function() {
            
        });
        
        //点击重新支付
        $('#rePay').on('click',function() {
        	/** 调用重新支付相关方法 */
        	repayment();
        });
        
        // 点击支付完成
        $('#complete').on('click',function() {
            clearInterval(timer);
            queryHouseOrderStatus(function(resp) {
        		if (resp.success) {
    				var code = resp.data.resultCode;
    				//** 跳转到重新支付页面 *//*
    				/** 1、已支付成功；2、调用微信订单查询接口失败！；3、查询微信确认支付成功！；
    				 *  4、未查询到对应的房源订单信息！；5、支付失败!；6、未支付；；7、未查询到对应支付订单号，请联系管理员！ */
    				if(code == 1 || code == 3) {//支付成功跳转到订单列表页面
    					window.location.href = "/my/getOrders.htm?page=1&type=1";
    				} else if(code == 2 || code == 4 || code == 7) {//弹框提示原因
    					$.wx.dialog({
    		                clickMaskClose:false,
    		                title:'支付订单查询',
    		                content:resp.data.resultMsg,
    		                buttons:[{
    		                    label: '确认',
    		                    type: 'primary',
    		                    onClick: function() {
    		                    	window.location.href = "/my/getOrders.htm?page=1&type=1";
    		                    }
    		                }]
    		            });
    				} else if(code ==5 || code == 6) {//提示重新支付
    					$.wx.dialog({
    		                clickMaskClose:false,
    		                title:'支付订单查询',
    		                content:'若已完成支付，可再次查询支付结果</br>若未完成支付，请重新支付',
    		                buttons:[{
    		                    label: '重新支付',
    		                    type: 'primary',
    		                    onClick: function() {
    		                    	/** 调用重新支付相关方法 */
    		                    	repayment();
    		                    }
    		                },{
    		                    label: '查询支付结果',
    		                    type: 'primary',
    		                    onClick: function() {
    		                    	/** 调用后台查询支付结果 */
    		                    	queryOrderStatus();
    		                    }
    		                }]
    		            });
    				}
    			} else {
    				alert(resp.message);
    			}
    		})
        });
    });
    
    /** 重新支付相关方法 */
    function repayment() {
    	queryHouseOrderStatus(function(resp) {
    		if (resp.success) {
				var code = resp.data.resultCode;
				//** 跳转到重新支付页面 *//*
				/** 1、已支付成功；2、调用微信订单查询接口失败！；3、查询微信确认支付成功！；
				 *  4、未查询到对应的房源订单信息！；5、支付失败!；6、未支付；；7、未查询到对应支付订单号，请联系管理员！ */
				if(code == 5 || code == 6 || code == 7) {//尚未支付跳转到重新支付页面
					window.location.href = "/my/weixin_pay/orderInfo.htm";
				} else {//调试原因，并跳转到支付列表页面
					alert(resp.data.resultMsg);
					window.location.href = "/my/getOrders.htm?page=1&type=0";
				}
			} else {
				alert(resp.message);
				window.location.href = "/my/getOrders.htm?page=1&type=0";
			}
		})
    }
    
    /** 查询订单状态 */
    function queryOrderStatus() {
    	queryHouseOrderStatus(function(resp) {
    		if (resp.success) {
				/** 1、已支付成功；2、调用微信订单查询接口失败！；3、查询微信确认支付成功！；
				 *  4、未查询到对应的房源订单信息！；5、支付失败!；6、未支付；；7、未查询到对应支付订单号，请联系管理员！ */
				var code = resp.data.resultCode;
				if(code == 1 || code == 3) {//支付成功跳转到订单列表页面
					$.wx.dialog({
		                clickMaskClose:false,
		                title:'支付订单查询',
		                content:resp.data.resultMsg,
		                buttons:[{
		                    label: '确认',
		                    type: 'primary',
		                    onClick: function() {
		                    	
		                    }
		                }]
		            });
					window.location.href = "/my/weixin_pay/orderInfo.htm";
				} else if(code == 2 || code == 4 || code == 5 || code == 7) {//弹框提示原因
					$.wx.dialog({
		                clickMaskClose:false,
		                title:'支付订单查询',
		                content:resp.data.resultMsg,
		                buttons:[{
		                    label: '确认',
		                    type: 'primary',
		                    onClick: function() {
		                    	window.location.href = "/my/getOrders.htm?page=1&type=1";
		                    }
		                }]
		            });
				} else if(code == 6) {//提示重新支付
					$.wx.dialog({
		                clickMaskClose:false,
		                title:'支付订单查询',
		                content:"尚未支付，请重新支付！",
		                buttons:[{
		                    label: '确认',
		                    type: 'primary',
		                    onClick: function() {
		                    	window.location.href = "/my/weixin_pay/orderInfo.htm";
		                    }
		                }]
		            });
				}
			} else {
				/** 后台调用异常 */
				$.wx.dialog({
	                clickMaskClose:false,
	                title:'支付订单查询',
	                content:'系统异常！',
	                buttons:[{
	                    label: '确认',
	                    type: 'primary',
	                    onClick: function() {
	                    	window.location.href = "/my/getOrders.htm?page=1&type=1";
	                    }
	                }]
	            });
			}
		})
    }
})();


