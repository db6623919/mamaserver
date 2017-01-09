var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?5ba6ad70c80e51c587195c3ddd64d1f2";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();

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

// 根据url点亮底部导航栏相应模块
function activeNav() {
	// 我的
	if (location.href.indexOf('usercenter') > -1) {
		$('#navMe').addClass('active-link');
	}
	// 首页
	else {
		$('#navHome').addClass('active-link');
	}
}

$(function() {
	adaptPage(adaptPageClass);
	/*首页底部导航*/
	activeNav();
	/*首页顶部导航*/
	/*var hMenuList = $('.hMenulist');
	var menumask = $('<div class="menumask" style="display:none;z-index:9;width:100%;position:absolute;top:0;left:0;bottom: 0;height:100%;background-colorT:red;opacity:0.01"></div>');
	$('header').append(menumask);
	$('.menumask').on("click", function() {
		if ($(".menu_action").hasClass('active')) {
			$(".menu_action").removeClass('active')
		} else {
			$(".menu_action").addClass('active')
		}
		hMenuList.find('.more_ul').slideUp();
		$('.menumask').hide();
		$('.pop_tri').slideUp();
	});
	$('.header-menu-btn').on("click", function() {
		if ($(".menu_action").hasClass('active')) {
			$(".menu_action").removeClass('active')
		} else {
			$(".menu_action").addClass('active')
		}
		var $this = $(this);
		$('.menumask').show();
		$this.find(".hMenulist").show();
		$('.pop_tri').show();
		$this.find(".more_ul").slideDown();
	});*/

	adaptPage(adaptPageClass);
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
	/*********点击态绑定***************/
	$(".listClickStatus").on("touchstart", function() {
		$(this).addClass("list-clickStatus");
	});
	$(".listClickStatus").on("touchend", function() {
		$(this).removeClass("list-clickStatus");
	});
	$(".buttonClickStatus").on("touchstart", function() {
		$(this).addClass("button-clickStatus");
	});
	$(".buttonClickStatus").on("touchend", function() {
		$(this).removeClass("button-clickStatus");
	});
})

/*校验手机号码格式*/
function checkMobile(str) {
	var re = /^(1(([34578][0-9])|(47)))\d{8}$/; //匹配 13x/15x/18x/17x 号段，如有遗漏请自行添加
	if (re.test(str)) {
		return true;
	} else {
		return false;
	}
}

/*校验验证码格式*/
function checkCode(str) {
	var re = /^\d{6}$/; //匹配6位数字，如有遗漏请自行添加
	if (re.test(str)) {
		return true;
	} else {
		return false;
	}
}

function loading(msn) {
	if (msn) {
		$('.loading_body').remove();
	} else {
		$('body').append('<div class="loading_body"><div class="loading_wrapper"><div class="loading"></div></div></div>');
		$('.loading_body').height($(window).height());
	}
}
if (typeof($(document).ajaxStart) === "function") {

	$(document).ajaxStart(function() {
		console.log(typeof $(document).ajaxStart);
		loading();
	});
	$(document).ajaxComplete(function(event, request, settings) {
		if(request.status==403){
			myAlert("友情提示","系统繁忙，请稍后重试");
		}
		loading("false");
	});

	$(document).ajaxSuccess(function(event, request, settings) {
		if(request.status==403){
			myAlert("友情提示","系统繁忙，请稍后重试");
		}
		loading("false");
	});

	$(document).ajaxError(function(event, request, settings) {
		if(request.status==403){
			myAlert("友情提示","系统繁忙，请稍后重试");
		}
		loading("false");

	});
}

//ajax 请求成功 之后的 状态处理
function ajaxRequest(data) {
	if ($.isEmptyObject(data)) {
		
		myAlert("友情提示","系统繁忙，请稍后重试");
		loading("false");
	}
	if (data.code != 0) {
		myAlert("友情提示","系统繁忙，请稍后重试");
		loading("false");
	}

}

function formatDate() {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	if (month < 10) {
		month = "0" + month;
	}
	if (date < 10) {
		date = "0" + date;
	}
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minute < 10) {
		minute = "0" + minute;
	}
	return year + "-" + month + "-" + date + " " + hour + ":" + minute;
}


function getLocalTime() {
	return  formatDate();
}

function myModel(title, body, fn) {

}

function myAlert(title, body, callFn) {
	var html = "";
	html += '<div class="my-dimmer" ></div>' +
		'<div class="my-modal">' +
		'	<div class="my-modal-dialog am-radius-10">' +
		'		<div class="my-modal-hd">' + title + '</div>' +
		'		<div class="my-modal-bd">' + body + ' </div>' +
		'		<div class="my-modal-footer">' +
		'			<span class="my-modal-btn">确定</span>' +
		'		</div>' +
		'	</div>' +
		'</div>';
	$("body").append(html);
	$(".my-modal").css("marginTop", -$(".my-modal").height() / 2);
	$(".my-modal-btn").one("click", function() {
		$(".my-modal").remove();
		$(".my-dimmer").remove();
		if (callFn != undefined) {
			callFn();
		}
	});

}

function cutOut(str){
	if(str.length>6){
		str=str.substr(0,3)+"***"+str.substr(str.length-3);
	}
	return str;
}

function myConfirm(title, body, okFn, closeFn) {
	var html = "";
	html += '<div class="my-dimmer" ></div>' +
		'<div class="my-modal">' +
		'	<div class="my-modal-dialog am-radius-10">' +
		'		<div class="my-modal-hd">' + title + '</div>' +
		'		<div class="my-modal-bd">' + body + ' </div>' +
		'		<div class="my-modal-footer">' +
		'			<span class="my-modal-btn border-right-default close">取消</span>' +
		'			<span class="my-modal-btn ok">确定</span>' +
		'		</div>' +
		'	</div>' +
		'</div>';
	$("body").append(html);
	$(".my-modal").css("marginTop", -$(".my-modal").height() / 2);
	$(".ok").one("click", function() {
		$(".my-modal").remove();
		$(".my-dimmer").remove();
		if (okFn != undefined) {
			okFn();
		}
	});
	$(".close").one("click", function() {
		$(".my-modal").remove();
		$(".my-dimmer").remove();
		if (closeFn != undefined) {
			closeFn();
		}
	});
}

/*校验密码格式*/
function checkPassword(str) {
	var re = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,20}$/; //匹配6-12位字符，不能含空格，如有遗漏请自行添加
	if (re.test(str)) {
		return true;
	} else {
		return false;
	}
}

/*确认密码*/
function checkRepassword(repwd, str) {
	if (repwd != str) {
		return false;
	} else {
		return true;
	}
}

/*校验身份证格式*/
function checkIdcard(_id) {
	var powers = new Array("7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2");
	var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
	_id = _id + "";
	var _num = _id.substr(0, 17);
	var _parityBit = _id.substr(17);
	var _power = 0;
	for (var i = 0; i < 17; i++) {
		//校验每一位的合法性
		if (_num.charAt(i) < '0' || _num.charAt(i) > '9') {
			return false;
			break;
		} else {
			//加权
			_power += parseInt(_num.charAt(i)) * parseInt(powers[i]);
		}
	}
	//取模
	var mod = parseInt(_power) % 11;
	if (parityBit[mod] == _parityBit) {
		return true;
	}
	return false;
}

/*校验真实姓名格式*/
function checkName(str) {
	var re = /^\s*[\u4e00-\u9fa5]{1,}[\u4e00-\u9fa5.·]{0,15}[\u4e00-\u9fa5]{1,}\s*$/; //如有遗漏请自行添加
	if (re.test(str)) {
		return true;
	} else {
		return false;
	}
}

/*校验昵称格式*/
function checkNickname(str) {
	var re = /^[\u4e00-\u9fa5a-zA-Z0-9_]+$/; //不正确，需要更换
	if (re.test(str)) {
		return true;
	} else {
		return false;
	}
}

/*校验提交参数*/
function dataValidate() {
	var return_msg = '';
	$("input").each(function() {
		var input_value = $(this).val();
		var rel = $(this).attr("rel");
		var need = $(this).attr("need");
		var msg = $(this).attr("msg");

		if (need == 'need' && (input_value == '')) {
			return_msg = msg + "不能为空！";
		}
		var check_result = getCheckResult(rel, input_value);
		if (input_value && (check_result == false)) {
			return_msg = msg + "格式不正确，请重新填写！";
		}

		if (return_msg != '') {
			return false;
		}
	});

	return return_msg;
}

/*根据标示获取校验参数的结果*/
function getCheckResult(rel, value) {
	var re = true;
	switch (rel) {
		case "nickname":
			re = checkNickname(value);
			break;
		case "name":
			re = checkName(value);
			break;
		case "idcard":
			re = checkIdcard(value);
			break;
		case "phone":
			re = checkMobile(value);
			break;
		case "password":
			re = checkPassword(value);
			break;
		case "newpassword":
			re = checkPassword(value);
			break;
		case "repassword":
			repwd = $("input[rel='newpassword']").val(); //新密码
			re = checkRepassword(repwd, value);
			break;
		case "code":
			re = checkCode(value);
			break;
		default:
			break;
	}

	return re;

}

function stringToDate(DateStr) {

	var converted = Date.parse(DateStr);
	var myDate = new Date(converted);
	if (isNaN(myDate)) {
		//var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';  
		var arys = DateStr.split('-');
		myDate = new Date(arys[0], --arys[1], arys[2]);
	}
	return myDate;
}

function addDate(date, days) {
	var d = new Date(date.replace(/-/g, "/"));
	d.setDate(d.getDate() + days);
	var m = d.getMonth() + 1;
	if (m < 10) {
		m = "0" + m;
	}
	var day = d.getDate();
	if (day < 10) {
		day = "0" + day;
	}
	return d.getFullYear() + '-' + m + '-' + day;
}

function minusDate(date1, date2) {
	var d1 = new Date(date1.replace(/-/g, "/"));
	var d2 = new Date(date2.replace(/-/g, "/"));
	return (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);
}
var showTip = function(tip, time) {
		if (!time || typeof(time) != 'number') {
			time = 2
		}
		if (typeof(tip) != "undefined" && tip != '') {
			var context = $('body');
			var tiparticle = $("#article_showTip");
			if (typeof(tiparticle) != "undefined")
				tiparticle.remove();
			var html = '<article id="article_showTip" class="tc-ar"><b>' + tip + '</b></article>';
			context.append(html);
			tiparticle = $("#article_showTip");
			tiparticle.on('click', function() {
				tiparticle.remove();
			})
			setTimeout(function() {
				tiparticle.remove();
			}, 1000 * time);
		}
	}
	/*校验提交参数*/
function dataValidateV2(obj, url) {
	$("#errorMsg").addClass("div_hidden");
	var return_msg = '';
	var errorMsg = '';
	var input_value = $(obj).val();
	var rel = $(obj).attr("rel");
	var need = $(obj).attr("need");
	var msgid = $(obj).attr("msgid");
	var isLogin = $(obj).attr("isLogin");
	var isCode = $(obj).attr("isCode");
	if (need == 'need' && (input_value == '')) {
		return_msg = msgid;
		if (msgid == 'error_1') {
			errorMsg = '手机号不能为空';
		} else if (msgid == 'error_2') {
			errorMsg = '验证码不能为空';
		} else if (msgid == 'error_3') {
			errorMsg = '登录密码不能为空';
		} else if (msgid == 'error_4') {
			errorMsg = '新登录密码不能为空';
		} else if (msgid == 'error_5') {
			errorMsg = '确认密码不能为空';
		} else if (msgid == 'error_6') {
			errorMsg = '邮件地址不能为空';
		} else if (msgid == 'error_7') {
			errorMsg = '姓名不能为空';
		} else if (msgid == 'error_8') {
			errorMsg = '身份证不能为空';
		} else if (msgid == 'error_9') {
			errorMsg = '昵称不能为空';
		}

	}
	var check_result = getCheckResult(rel, input_value);
	if (input_value && (check_result == false)) {
		return_msg = msgid;
		if (msgid == 'error_1') {
			errorMsg = '手机号格式不正确';
		} else if (msgid == 'error_2') {
			errorMsg = '验证码格式不正确';
		} else if (msgid == 'error_3') {
			errorMsg = '登录密码格式不正确';
		} else if (msgid == 'error_4') {
			errorMsg = '新登录密码格式不正确';
		} else if (msgid == 'error_5') {
			errorMsg = '两次密码输入不一致';
		} else if (msgid == 'error_6') {
			errorMsg = '邮件地址格式不正确';
		} else if (msgid == 'error_7') {
			errorMsg = '姓名格式不正确';
		} else if (msgid == 'error_8') {
			errorMsg = '身份证格式不正确';
		} else if (msgid == 'error_9') {
			errorMsg = '昵称格式不正确';
		}
	}

	if (return_msg != '') {
		$("#" + return_msg).removeClass("div_hidden");
		$("#" + return_msg + "_span").html(errorMsg);
	} else {
		if (msgid == 'error_1' && (isLogin != 'isLogin')) {
			var check = checkIsRegistered(input_value);
			if (check == true && isLogin != 'password') {
				$("#" + msgid).removeClass("div_hidden");
				$("#" + msgid + "_span").html("此手机号已经注册");
				$(".reg_send_botton").removeAttr("onclick");
			} else {
				if (check == false && isLogin == 'password') {
					$("#" + msgid).removeClass("div_hidden");
					$("#" + msgid + "_span").html("此手机号没有注册");
					$(".reg_send_botton").removeAttr("onclick");
				} else {
					$("#" + msgid).addClass("div_hidden");
					var sendVerifyCodeUrl = url ? url : "/sendVerifyCode.htm";
					if (isCode == 'password') {
						$(".reg_send_botton").attr("onclick", "countTime(this, '" + sendVerifyCodeUrl + "',2)");
					} else if (isCode == 'email') {
						$(".reg_send_botton").attr("onclick", "countTime(this, '" + sendVerifyCodeUrl + "',4)");
					} else {
						$(".reg_send_botton").attr("onclick", "countTime(this, '" + sendVerifyCodeUrl + "',1)");
					}
				}
			}
		} else {
			$("#" + msgid).addClass("div_hidden");
		}

	}

}

//公共弹窗
$.wx = $.wx || {};
$.wx.callback = function(){
};
// wx弹窗
/**
 * loading弹框
 * @params {string} 提示内容
 */
(function($){
	var $loading = null;
	$.wx.loading = function(content){
		content = content || '数据加载中';
		var html = '<div class="weui_loading_toast"><div class="weui_mask_transparent"></div><div class="weui_toast"><div class="weui_loading"><div class="weui_loading_leaf weui_loading_leaf_0"></div><div class="weui_loading_leaf weui_loading_leaf_1"></div><div class="weui_loading_leaf weui_loading_leaf_2"></div><div class="weui_loading_leaf weui_loading_leaf_3"></div><div class="weui_loading_leaf weui_loading_leaf_4"></div><div class="weui_loading_leaf weui_loading_leaf_5"></div><div class="weui_loading_leaf weui_loading_leaf_6"></div><div class="weui_loading_leaf weui_loading_leaf_7"></div><div class="weui_loading_leaf weui_loading_leaf_8"></div><div class="weui_loading_leaf weui_loading_leaf_9"></div><div class="weui_loading_leaf weui_loading_leaf_10"></div><div class="weui_loading_leaf weui_loading_leaf_11"></div></div><p class="weui_toast_content">'+content+'</p></div></div>';
		$loading = $(html);
		$('body').append($loading);
	}
	$.wx.hideLoading = function(){
		$loading && $loading.remove();
		$loading = null;
	}
})($);
/**
 * toast弹框
 * @params {string} 提示内容
 * @params {object/string/number} 配置选项 number->设置展示时间 function->设置回调函数；组合两个成object也可以
 */
;(function($){
	var $toast = null;
	$.wx.toast = function(content, options){
		content = content || '操作成功';
		if(typeof options === 'number'){
			options = {
				duration : options
			}
		}
		if(typeof options === 'function'){
			options = {
				callback : options
			}
		}
		options = $.extend({
			duration: 1500,
			callback: $.wx.callback
		}, options);
		var html = '<div><div class="weui_mask_transparent"></div><div class="weui_toast"><i class="weui_icon_toast"></i><p class="weui_toast_content">' + content + '</p></div></div>';
		$toast = $(html);
		$('body').append($toast);
		setTimeout(function(){
			$toast.remove();
			$toast = null;
			options.callback();
		}, options.duration);
	}
})($);
/**
 * dialog弹框
 * @params {string} 提示内容
 * @params {object} 配置选项
 */
;(function ($) {
	var $dialog = null;
	$.wx.dialog = function (options) {
		options = $.extend({
			clickMaskClose:true,
			title: '',
			content: '空',
			className: '',
			buttons: [{
				label: '确定',
				type: 'primary',
				onClick: $.wx.callback
			}]
		}, options);
		var buttons = options.buttons.map(function(button){
			return '<a href="javascript:;" class="weui_btn_dialog '+button.type+'">'+button.label+'</a>';
		}).join('\n');
		var html = '<div class="'+options.className+'"><div id="weuiMask" class="weui_mask"></div><div class="weui_dialog"><div class="weui_dialog_hd"><strong class="weui_dialog_title">'+options.title+'</strong></div><div class="weui_dialog_bd">'+options.content+'</div><div class="weui_dialog_ft">'+buttons+'</div></div></div>';
		$dialog = $(html);
		$('body').append($dialog);
		$dialog.on('click', '.weui_btn_dialog', function () {
			var button = options.buttons[$(this).index()];
			var cb = button.onClick || $.wx.callback;
			cb();
			$.wx.closeDialog();
		});
		$('#weuiMask').on('click',function(e) {
			if(options.clickMaskClose) {
				$.wx.closeDialog();
			}
			e.stopPropagation();
			e.preventDefault();
		})
	};
	$.wx.closeDialog = function () {
		if ($dialog) {
			$dialog.off('click');
			if (typeof $dialog.fadeOut === 'function') {
				$dialog.fadeOut('fast', function(){
					$dialog.remove();
					$dialog = null;
				});
			}
			else {
				$dialog.remove();
				$dialog = null;
			}
		}
	};
})($);