<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>充值</title>
	</head>
	<body>
		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="3" name="menu"/>
				<jsp:param value="1" name="index"/>
			</jsp:include>
			<div id="page-wrapper">
				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
                            			<!--新增用户-->
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">

							<div class="col-xs-12 ">
								<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

									<div class="panel-heading templatemo-position-relative">
										<h2 class="text-uppercase">充值</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" class="form-horizontal" method="post" action="">
											<input type="hidden" name="uid" id="uid" value="${uid}" />
											${result }
											<div class="form-group col-xs-12">
												<label for="phone" class="control-label text-left col-xs-2"><font class="star">*</font>手机号码</label>
												<div class="col-xs-10">
													<input type="text" name="phone" value="${phone}" id="phone" class="form-control ">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="type" class="control-label text-left col-xs-2"><font class="star">*</font>充值类型</label>
												<div class="col-xs-10">
													<input type="text" name="type" value="首次充值" id="type" class="form-control ">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="rechargeamt" class="control-label text-left col-xs-2"><font class="star">*</font>充值金额</label>
												<div class="col-xs-8">
													<input type="text" name="rechargeamt" value="" id="rechargeamt" class="form-control">
												</div>
												<div class="col-xs-2">
													<input type="button" name="orderNumber"  onclick="calculation()" value="计算奖励" id="orderNumber" class="form-control btn btn-style ">
												</div>
											</div>

											<div class="form-group col-xs-12">
												<label for="rewardamt" class="control-label text-left col-xs-2">奖励金额</label>
												<div class="col-xs-10">
													<input type="text" name="rewardamt" value="" id="rewardamt" class="form-control" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="showdetail" class="control-label text-left col-xs-2">充值描述</label>
												<div class="col-xs-10">
													<input type="text" name="showdetail" value="" id="showdetail" class="form-control">
												</div>
											</div>

											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="button" class="btn btn-style" value="充值" id="recharge" />
												<input type="button" class="btn btn-style" onclick="history.go(-1)" value="返回" />
											</div>
										</form>

									</div>

								</div>
							</div>
						</div>

					</div>
					<!-- /.row -->

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->

	</body>
	<%@include file="/page/common/foot.jsp" %>
	<script>
		$().ready(function() {
			$("#userFrom").validate({
				rules: {
					phone: "required",
					type: "required",
					money: 'required'
				},
				messages: {
					phone: "请输入手机号",
					type: "请输入充值类型",
					money: '请输入充值金额'
				}/* ,submitHandler:function(form){
					 if($(form).valid()){
	                        // submit form
	                        var p=$("#userFrom").serialize();
	                        $.ajax({
	                        	url:'${baseUrl}/order/pay_action',
	                        	data:p,
	                        	type:'post',success:function(result){
	                        		//alert(result);
	                        		myAlert({content:'修改成功',okfun:function(){
	                        			location.href="${baseUrl}/order/vip_list";
	                        		}})
	                        	}
	                        });
	                    }else{
	                        return false;
	                    }
				} */
			});
		});
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
					myWarning("您好充值金额不能为空，可以为0！");
					return;
				}
				
				if(!exp.test(rechargeamt)){
					myWarning("您的充值金额格式不正确，充值金额必须为整数或小数，小数点后不超过2位！");
					return;
				}
				if($.trim(rewardamt) == ""){
					myWarning("您好奖励金额不能为空，可以为0！");
					return;
				}
				if(!exp.test(rewardamt)){
					myWarning("您的奖励金额格式不正确，奖励金额必须为整数或小数，小数点后不超过2位！");
					return;
				}
				if($.trim(showdetail) == ""){
					myWarning("您好充值描述不能为空！");
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
								myWarning("恭喜您，充值成功！");
							} else {
								myWarning("充值失败！"+data.message);
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

</html>