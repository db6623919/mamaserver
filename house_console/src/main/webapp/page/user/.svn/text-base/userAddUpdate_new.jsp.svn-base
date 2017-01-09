<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<%@include file="/page/common/base.jsp" %>
		<title><c:if test="${flag=='add' }">新增</c:if><c:if test="${flag=='update' }">修改</c:if>用户</title>
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
										<h2 class="text-uppercase"><c:if test="${flag=='add' }">新增</c:if><c:if test="${flag=='update' }">修改</c:if>用户</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="#" method="post" class="form-horizontal">
											<input id="id" type="hidden" name="id" value="${user.id }" />
											<div class="form-group col-xs-12">
												<label for="username" class="col-xs-2 control-label">用户名</label>
												<div class="col-xs-10">
													<input type="text" name="username" value="${user.username }" id="update_username" class="form-control" maxlength="50">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="name" class="col-xs-2 control-label">密码</label>
												<div class="col-xs-10">
													<input type="password" name="user_password" value="${user.password}" id="user_password" class="form-control">
												</div>

											</div>
											<div class="form-group col-xs-12">
												<label for="name" class="col-xs-2 control-label">确认密码</label>
												<div class="col-xs-10">
													<input type="password" name="rep_user_password" value="${user.password}" id="rep_user_password" class="form-control">
												</div>

											</div>
											<div class="form-group col-xs-12">
												<label for="name" class="col-xs-2 control-label">姓名</label>
												<div class="col-xs-10">
													<input type="text" name="name" value="${user.name}" id="name" class="form-control" maxlength="50">
												</div>

											</div>
															
											
											<div class="form-group col-xs-12">
												<label for="status" class="control-label col-xs-2">状态</label>
												<div class="col-xs-10">
													<select class="form-control" name="status">
														<c:if test="${user.status =='0' }">
															<option value="1">启用</option>
															<option value="0" selected="selected">禁用</option>
														</c:if>
														<c:if test="${user.status !='0' }">
															<option value="1" selected="selected">启用</option>
															<option value="0">禁用</option>
														</c:if>
													</select>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="myRole" class="col-xs-2 control-label">角色</label>
												<div class="radio col-xs-10">
													<c:if test="${flag=='add' }">
														<c:forEach items="${allRoleList }" var="allrole">
														
													
																<c:if test="${allrole.id == '1'}">
																	<label><input type="radio" name="role" value="${allrole.id }"  checked="checked">${allrole.name }</label>
																</c:if>
																<c:if test="${allrole.id != '1'}">
																	<label><input type="radio" name="role" value="${allrole.id }"  >${allrole.name }</label>
																</c:if>	
																				
														</c:forEach>								
													</c:if>
													<c:if test="${flag=='update' }">
														<c:forEach items="${hasRoleList }" var="hasrole">		
																<c:forEach items="${allRoleList }" var="allrole">
																	
																		<c:if test="${hasrole.id == allrole.id}">
																			<label><input type="radio" name="role" value="${allrole.id }"   checked="checked">${allrole.name }</label>
																		</c:if>
																		<c:if test="${hasrole.id != allrole.id}">
																			<label><input type="radio" name="role" value="${allrole.id }"   >${allrole.name }</label>
																		</c:if>
																															
																</c:forEach>								
														</c:forEach>
													</c:if>
													
												</div>
											</div>

											<div class="form-group col-xs-12">
												<label for="description" class="control-label col-xs-2">备注</label>
												<div class="col-xs-10">
													<textarea rows="3" name="description" class="form-control">${user.description}</textarea>
												</div>
											</div>
											
											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<c:if test="${flag=='add' }">
													<input type="submit" class="btn  btn-style" value="增加" />
												</c:if> 
												<c:if test="${flag=='update' }">
													<input type="submit" class="btn  btn-style" value="修改" />
												</c:if>
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
					username: "required",
					name: "required",
					user_password:"required",
					rep_user_password:"required",	
					inputTest: {
						required: true,
						maxlength: 6
					}
				},
				messages: {
					inputUserName: "请输入用户名",
					inputName: "请输入姓名",
					inputUser_password: "请输入密码",
					inputRep_user_password: "请输入确认密码",
					inputTest: 'max length 6'
				},submitHandler:function(form){
					 if($(form).valid()){
							 setMaxDigits(130);
							var password = $("#user_password").val();
							var repassword = $("#rep_user_password").val();
							if(password!=repassword){
									alert('密码和确认密码不一致！');
									return false;
							}
							var key = new RSAKeyPair("10001","","8a8e5cf64f3c9f34b8f6bb3186d0b8df44be58ae56c1f46e7042779ec7a7d923eb41373746643cae02481567fa8843f9a0faca3e682c2b5ff55c30f83bfe51f99c0dca336fbf799f1f6f21fac0e9c2994699b643d3b18e14a07f7f12b49d46ddd5fa3e4ea0e2c175ed90e267f5b9641534886a2b9ae354f8bb2320c9f79486dd");

							password = encryptedString(key, encodeURIComponent(password));
						    //TODO: 先注释掉， 稍后再修改
							$("#user_password").val(password);
							
							form.submit();
							if('${flag}'=='add'){
								$("#mainForm").attr("action","${pageContext.request.contextPath}/user/saveUser.shtml");
							}else{
								$("#mainForm").attr("action","${pageContext.request.contextPath}/user/updateUser.shtml?id="+$("#id").val());
							}
							 form.submit();
							 
	                    }else{
	                        return false;
	                    }
				}
			});
		});
	</script>

</html>