<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>妈妈送房后台</title>
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
										<h2 class="text-uppercase">SUCCESS</h2>
									</div>
									<div class="row mg-top-10">
										<div class="tab-content default-tab">
											<c:if test="${message!='' }">
												<p style="margin-left:30px;">操作成功：</p>
											</c:if>
											<c:if test="${message!='' }">
												<p style="margin-left:30px;">${message}</p>
											</c:if>
											<a style="margin-left:30px;margin-bottom:20px;" href="javascript:history.go(-1);"  class="btn  btn-style">返回</a>
										</div>
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
					username: "required",
					name: "required",
					inputTest: {
						required: true,
						maxlength: 6
					}
				},
				messages: {
					inputUserName: "请输入用户名",
					inputName: "请输入姓名",
					inputTest: 'max length 6'
				},submitHandler:function(form){
					 if($(form).valid()){
	                        // submit form
	                        var p=$("#userFrom").serialize();
	                        $.ajax({
	                        	url:'${baseUrl}/user/user_action',
	                        	data:p,
	                        	type:'post',success:function(result){
	                        		//alert(result);
	                        		myAlert({content:'修改成功',okfun:function(){
	                        			location.href="${baseUrl}/user/user_list";
	                        		}})
	                        	}
	                        });
	                    }else{
	                        return false;
	                    }
				}
			});
		});
	</script>

</html>