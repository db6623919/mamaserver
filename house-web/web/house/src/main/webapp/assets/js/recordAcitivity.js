var num = 0;
//var totalPint = 0;
//var note = ""
var lottery = {
	index: -1, //当前转动到哪个位置，起点位置
	count: 0, //总共有多少个位置
	timer: 0, //setTimeout的ID，用clearTimeout清除
	speed: 20, //初始转动速度
	times: 0, //转动次数
	cycle: 50, //转动基本次数：即至少需要转动多少次再进入抽奖环节
	prize: -1, //中奖位置
	init: function(id) {
		if ($("#" + id).find(".lottery-unit").length > 0) {
			$lottery = $("#" + id);
			$units = $lottery.find(".lottery-unit");
			this.obj = $lottery;
			this.count = $units.length;
			$lottery.find(".lottery-unit-" + this.index).addClass("active");
		};
	},
	roll: function() {
		var index = this.index;
		var count = this.count;
		var lottery = this.obj;
		$(lottery).find(".lottery-unit-" + index).removeClass("active");
		index += 1;
		if (index > count - 1) {
			index = 0;
		};
		$(lottery).find(".lottery-unit-" + index).addClass("active");
		this.index = index;
		return false;
	},
	stop: function(index) {
		this.prize = index;
		return false;
	}
};


function roll() {
	lottery.times += 1;
	lottery.roll();
	if (lottery.times > lottery.cycle + 10 && lottery.prize == lottery.index) {
		clearTimeout(lottery.timer);
		doRoll(); //滚动停止处理事件
		lottery.prize = -1;
		lottery.times = 0;
		isRolled = false;
	} else {
		//lottery.prize = 10; //中奖项
		if (lottery.times < lottery.cycle) {
			lottery.speed -= 10;
		} else {
			if (lottery.times > lottery.cycle + 10 && ((lottery.prize == 0 && lottery.index == 7) || lottery.prize == lottery.index + 1)) {
				lottery.speed += 110;
			} else {
				lottery.speed += 20;
			}
		}
		if (lottery.speed < 40) {
			lottery.speed = 40;
		};
		lottery.timer = setTimeout(roll, lottery.speed);
	}
	return false;
}

//滚动停止处理
function doRoll() {
	var note = $("#note").val();
	var totalPint = $("#totalPint").val();
	//总积分 = 已有 + 现得 
	$("#total_pint").html(parseInt($("#total_pint").html()) + parseInt(totalPint));
	$("#start_award").removeClass("unactive");
	if(totalPint != 0) {
		addDialog(2);
		$("#count_bean").html(totalPint);
		$("#note_bean").html(note);
	}else {
		addDialog(3);
	}
	initDialog();
}

var isRolled = false;
	//			window.onload = function() {};
$(document).ready(function() {
	//var adaptPageClass = "content";
	adaptPage("content");
	/**
	 *计算总豆背景与抽奖框位置
	 */
	var wWidth = window.innerWidth;
	if(wWidth > 640) {
		wWidth = 640
	}
	var headHeight = wWidth * 1.5053;
	//				$("#lottery").css('top', headHeight / 1.7);
	//				$('#lottery').css('max-width', 640 * 0.75 + 'px');
	//				$('.list_history').css('margin-top', $('#lottery').height() / 1.7 + 'px');
	//计算总豆距离上边距离
	var beanConHei = wWidth * 0.8 * 161 / 574;
	$('.bean-line').css('margin-top', -beanConHei / 1.98);

	//计算奖项高度
	var lWidth = $("#lottery").width();
	var lHeight = lWidth * 500 / 513;
	$('#lottery').height(lHeight);
	$('#lottery').css('padding-top', lHeight / 14.6);
	$('#lottery').css('padding-right', lWidth / 18.5);
	$('#lottery').css('padding-left', lWidth / 18.5);

	var sWidth = $('.lottery-unit').width();
	var height = sWidth * 106 / 113;
	$('#lottery table .lottery-unit').css('height', height);
	$('#start button').css('height', 2 * sWidth * 0.963768116);
	$('#start div').css('height', 2 * sWidth * 0.963768116);
	lottery.init('lottery');
	$("#lottery button").click(function() {
		//判断是否登陆
		if(checkLogin()) {  
			if (isRolled) {
				return false;
			} else {
				var isShare = $("#isShare").val();
				if(parseInt($("#record_size").html())==0 && isShare ==0 ){ //未分享(分享次数小于3) 无次数
					alert("微信分享后多抽一次!");
					return false;
				} else if (parseInt($("#record_size").html())==0 && isShare ==1 ){ //已分享(分享次数不小于3) 无次数
					addDialog(4);
					initDialog();
					return false;
				} else { //进行抽奖
					getRecord(lottery);
					return false;
				}
			}
		} else { //未登陆跳转到登陆页面
			var friendcode = $("#friendcode").val();
			var url = "/my/toPage.htm";
			if(friendcode != "" && friendcode != null) {
				url += "?friendcode=" + friendcode;
			}
			window.location.href = url;
		}
	});

})

function initDialog(){
	//计算对话框高度，及距离上边距
	var cwi = window.innerWidth;
	if(cwi>640){
		cwi=640;
	}
    var chei = cwi * 615 / 750;
    
    var noChanHei = cwi *715/750;
    try {

      $(".congratulation").height(chei);
      $('.pity').height(chei)
      $('.share-more').height(chei);
      $('.nochance').height(noChanHei);
    } catch(e) {
      //弹框元素不存在
    }
    $('.dialog-content').css('margin-top', chei / 1.9);
    $('.nochance-content').css('margin-top', -noChanHei / 1.6);
	$('.dialog').on('click',function(){
		$("#start_award").removeAttr("disabled");
		$('.dialog').remove();
	})
}

var lazyload = {};
    lazyload.img = function(obj){
    var img = typeof(obj) == 'object' ? obj : $(obj);
    var xz_src = img.attr('xz_src');
    if (xz_src && xz_src != 'ok') {
       img.attr('src',xz_src);
       img.attr('xz_src','ok');
     }
}

var lazy_load_imgs = $('.lazy_load_img');
    setTimeout(function () {
       lazy_load_imgs.mmlazyload({replaceImage:false,pxOffset:50, memorySaver:true});
    },300);
			
