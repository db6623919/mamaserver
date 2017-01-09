var verification=function(){
	var max_time=60;
	var time=max_time;
	var interval;
	//获取验证码。
	var getCode=function(url,phone,e){
		interval=setInterval(function(){
			setTimer(e,function(){
				$(e).html("重新获取验证码");
				$(e).bind("click",function(){
					if(!checkPhone(phone.val())){
						myAlert("提示","手机号码格式不正确");
						return false;
					}
					$(this).unbind("click");
					getCode(url,phone,this);
				}).bind(e);
			});
		},1000);
		console.log(phone.val());
		$.ajax({
			type:"post",
			data:{phone:phone.val(),type:5},
			url:url,
			async:true,
			success:function(data){
				console.log(data);
			}
		});
	}
	
	//设置时间
	var setTimer=function(e,callback){
		$(e).html(time--+"s");
		if(time<=0){
			time=max_time;
			clearInterval(interval);
			callback();
		}
	}
	
	//领卷
	var getCoupons=function(p,url){
		$.ajax({
			type:"post",
			data:p,
			url:url,
			async:true,
			success:function(data){
				if(data.code==0){
					myAlert('友情提示', '恭喜您领券成功！！！',function(){
						
					});
				}else{
					myAlert('友情提示', data.msg);
				}
			}
		});
	}
	
	//验证手机
	var checkPhone=function(str){
		var re=/^(1[3587])\d{9}$/; //匹配 13x/15x/18x/17x 号段，如有遗漏请自行添加
		if(re.test(str)){
			return true;
		}else{
			return false;
		}
	}
	
	return {
		getCode:getCode,
		getCoupons:getCoupons,
		checkPhone:checkPhone
	}
	
}