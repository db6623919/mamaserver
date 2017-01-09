function del(okfun) {
	var d = dialog({
		title: '提示',
		content: '确定删除吗？此操作不可恢复',
		okValue: '确定',
		ok: function() {
			this.title('提交中…');
			okfun();
			return false;
		},
		cancelValue: '返回',
		cancel: function() {}
	});
	d.width(250);
	d.focus();
	d.showModal();
}
function myWarning(title){
	var d = dialog({
		title: "提示",
		okValue:"确定",
		content:title,
		ok: function() {
			return true;
		}
		
	});
	d.width(250);
	d.showModal();
}
function myAlert(p) {
	var defaults={
		title:'提示',
		okValue:'确定',
		cancelValue:'返回',
		content:"提示信息",
		okfun:function(){},
		cancelfun:function(){},
		width:230
	}
	var parameter=$.extend({},defaults,p);
	var d = dialog({
		title: parameter.title,
		okValue:parameter.okValue,
		cancelValue:parameter.cancelValue,
		content: parameter.content,
		ok: function() {
			parameter.okfun();
		},cancel:function(){
			parameter.cancelfun();
		}
	});
	d.width(parameter.width);
	d.showModal();
}
function show(url,okfun,cancelfun) {
	var p={};
	p.content='<div id="content"></div>';
	p.width=600;
	p.okfun=okfun;
	p.cancelfun=cancelfun;
	myAlert(p);
	$("#content").load(url);
}

//获取参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null) return unescape(r[2]);
	return null; //返回参数值
}


function loading(msn) {
	if (msn) {
		$('.loading_body').remove();
	} else {
		$('body').append('<div class="loading_body"><div class="loading_wrapper"><div class="loading"></div></div></div>');
		$('.loading_body').height($(window).height());
	}
}
function search(index,url,callback){
	var p=$("#query_form").serialize();
	$.ajax({
		type:"post",
		url:url,
		data:p+"&currentPage="+index,
		success:function(data){
			if(callback!=null){
				callback(data);
			}
		}
	});
}

$(document).ajaxStart(function() {
	loading();
});

$(document).ajaxComplete(function(event, request, settings) {
	/*console.log("请求完成  ajax");*/
	loading("false");
});

$(document).ajaxSuccess(function(event, request, settings) {
	/*console.log("成功 ajax");*/
	loading("false");
});

$(document).ajaxError(function(event, request, settings) {
	/*console.log("失败 ajax");*/
	loading("false");
	myAlert("错误消息","");
});