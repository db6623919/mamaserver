<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>修改密码</title>
	</head>

	<body>

		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="1" name="menu"/>
				<jsp:param value="1" name="index"/>
			</jsp:include>
			<div id="page-wrapper">
				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">
							<div class="col-xs-12 ">
								<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

									<div class="panel-heading templatemo-position-relative">
										<h2 class="text-uppercase">修改密码</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="${pageContext.request.contextPath }/user/changePwd.shtml" method="post" class="form-horizontal">
											<div class="form-group col-xs-12">
												<label for="oldPwd" class="col-xs-2 control-label">原始密码</label>
												<div class="col-xs-10">
													<input type="password" name="oldPwd" value="" id="oldPwd" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="newPwd" class="col-xs-2 control-label">新密码</label>
												<div class="col-xs-10">
													<input type="password" name="newPwd" value="" id="newPwd" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="newPwdTwo" class="col-xs-2 control-label">确认新密码</label>
												<div class="col-xs-10">
													<input type="password" name="newPwdTwo" value="" id="newPwdTwo" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="submit" class="btn  btn-style" value="确定" />
												<input type="reset" class="btn  btn-style" value="重置" />
												<input type="button" class="btn  btn-style" onclick="history.go(-1)" value="返回" />
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/RSA.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/BigInt.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/Barrett.js"></script>
	<script>
		$().ready(function() {
			$("#mainForm").validate({
				rules: {
					oldPwd: "required",
					newPwd: {
						required:true,
						minlength:8
					},
					newPwdTwo:{
						required:true,
						equalTo: "#newPwd",
						minlength:8
					}
				},
				messages: {
					oldPwd: "请输入原始密码",
					newPwd: {
						 minlength: "密码长度不能小于 8 个字母",
						 required:"请输入新密码"
					},
					newPwdTwo: {
						minlength: "密码长度不能小于 8 个字母",
						required:"请再次输入新密码",
						equalTo:'两次密码输入不一致',
						
					}
				},submitHandler:function(form){
					 if($(form).valid()){
							 setMaxDigits(130);
							var oldPwd = $("#oldPwd").val();
							var newPwd = $("#newPwd").val();
							var key = new RSAKeyPair("10001","","8a8e5cf64f3c9f34b8f6bb3186d0b8df44be58ae56c1f46e7042779ec7a7d923eb41373746643cae02481567fa8843f9a0faca3e682c2b5ff55c30f83bfe51f99c0dca336fbf799f1f6f21fac0e9c2994699b643d3b18e14a07f7f12b49d46ddd5fa3e4ea0e2c175ed90e267f5b9641534886a2b9ae354f8bb2320c9f79486dd");
							oldPwd = encryptedString(key, encodeURIComponent(oldPwd));
							newPwd = encryptedString(key, encodeURIComponent(newPwd));
						//TODO: 先注释掉， 稍后再修改
							$("#oldPwd").val(oldPwd);
							$("#newPwd").val(newPwd);
							form.submit();
	                    }else{
	                        return false;
	                    }
				}
			});
		});
	</script>

</html>