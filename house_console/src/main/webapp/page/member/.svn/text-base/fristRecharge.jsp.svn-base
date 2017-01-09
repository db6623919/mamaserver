<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>妈妈送房后台</title>
</head>
<body>
	<div id="body-wrapper">
		<div id="main-content">
			<div class="content-box">
				<div class="content-box-header">
					<h3>
						充值
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post">
							<input type="hidden" name="uid" id="uid" value="${uid}" />
							${result }
							<fieldset>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;手机号码：</span>
									<span >${phone}</span>
								</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;充值类型：</span>
									<span >首次充值<span>
								</p>
								<p>
									<span class="public_title">充值金额：</span>								
									  <input type="text" value="" name="rechargeamt" id="rechargeamt"  />
									  <input type="button"  value="计算奖励" onclick="calculation()">
									<span class="Validform_checktip"></span>
								</p>
								<p>
									<span class="public_title">奖励金额：</span>
									<input type="text"  name="rewardamt"  readonly="readonly"  id="rewardamt" class="text-input small-input" nullmsg="奖励金额！" /><span class="Validform_checktip"></span>
									
								</p>
								<p>
									<span class="public_title">充值描述：</span>
									<input type="text" value="" name="showdetail" id="showdetail" class="text-input small-input" nullmsg="充值描述！" /><span class="Validform_checktip"></span>
								</p>
								<p>
									<span class="public_title">&nbsp;</span>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="button" value="充值" id="recharge"/>
									
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="button" value="返回" id="fanhui" />
								</p>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="backForm" action="" method="post"></form>
</body>
</html>
<script>
function calculation(){
	var rechargeamt=$('#rechargeamt').val();
	var uid = $('#uid').val();
	var pars =  {"rechargeamt":rechargeamt,"uid":uid};
	$.ajax({ 
		type:"POST",
		data: pars,
		url: encodeURI("${pageContext.request.contextPath}/member/firstCalculation.shtml"),
		dataType:"json", 
		async:false , 
		success: function(data){
			$('#rewardamt').val(data.rechargeamtReward);
		},
	});
	
}
//为商户/企业角色时默认显示div
$(function(){
	$("#recharge").click(function(){
		var exp = /^(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d{1,2})?$/;
		var uid = $("#uid").val();
		var rechargeamt = $("#rechargeamt").val();
		var rewardamt= $("#rewardamt").val();
		var showdetail = $("#showdetail").val();
		var phone = '${phone}';
		var flag = $("#recharge").attr('disabled');
		if($.trim(rechargeamt) == ""){
			alert("您好充值金额不能为空，可以为0！");
			return;
		}
		
		if(!exp.test(rechargeamt)){
			alert("您的充值金额格式不正确，充值金额必须为整数或小数，小数点后不超过2位！");
			return;
		}
		if($.trim(rewardamt) == ""){
			alert("您好奖励金额不能为空，可以为0！");
			return;
		}
		if(!exp.test(rewardamt)){
			alert("您的奖励金额格式不正确，奖励金额必须为整数或小数，小数点后不超过2位！");
			return;
		}
		if($.trim(showdetail) == ""){
			alert("您好充值描述不能为空！");
			return;
		}
		
		var pars =  {"uid":uid,"rechargeamt":rechargeamt,"rewardamt":rewardamt,"showdetail":showdetail,"phone":phone};
		if(flag != 'disabled'){
			$("#recharge").attr("disabled","disabled");
			$.ajax({ 
				type:"POST",
				data: pars,
				url: encodeURI("${pageContext.request.contextPath}/member/toRecharge.shtml"),
				dataType:"json", 
				async:false , 
				success: function(data){
					if (data.success) {
						alert("恭喜您，充值成功！");
					} else {
						alert("充值失败！"+data.message);
						$("#recharge").attr('disabled',true);
					}
				},
			});
		}
	});
	$("#fanhui").click(function(){
		history.go(-1);
	});
});
</script>