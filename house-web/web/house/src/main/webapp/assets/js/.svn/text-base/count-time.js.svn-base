var countdown=60; 
function countTime(val){
	if(!$(val).hasClass("code-sending")){
		$(val).addClass("code-sending");
		settime(val);
	}
}
function settime(val) { 
	if (countdown == 0) { 
		$(val).removeClass("code-sending")
		$(val).html("发送验证码"); 
		countdown = 60; 
		return;
	} else { 
		$(val).html(countdown + "S后重新发送"); 
		countdown--; 
	} 
	setTimeout(function() { 
		settime(val) 
	},1000)
} 