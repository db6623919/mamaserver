<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title><c:if test="${flag=='add'}">新增</c:if><c:if test="${flag=='update'}">修改</c:if>角色的资源</title>
	</head>
	<body>
		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="1" name="menu"/>
				<jsp:param value="2" name="index"/>
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
										<h2 class="text-uppercase"><c:if test="${flag=='add'}">新增</c:if><c:if test="${flag=='update'}">修改</c:if>角色的资源</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="" method="post" class="form-horizontal">
											<input type="hidden" id="resourceids" name="resourceids" value="${hasResourceId }" />
											<input type="hidden" id="roleName" name="roleName" value="${role.name }" />
											<input type="hidden" id="roleRemark" name="roleRemark" value="${role.description}" />
											<div class="form-group col-xs-12">
												<label for="name" class="col-xs-2 control-label"><font class="star">*</font>角色名</label>
												<div class="col-xs-10">
													<input type="text" name="name" value="${role.name }" id="roleName" class="form-control" maxlength="50">
												</div>

											</div>

											<div class="form-group col-xs-12">
												<label for="status" class="col-xs-2 control-label">状态</label>
												<div class="col-xs-10">
														<select name="status" id="status" class="form-control">
															<c:if test="${role.status =='0' }">
																<option value="1">启用</option>
																<option value="0" selected="selected">禁用</option>
															</c:if>
															<c:if test="${role.status !='0' }">
																<option value="1" selected="selected">启用</option>
																<option value="0">禁用</option>
															</c:if>
														</select> 
													
														
													</select>
												</div>

											</div>
											<div class="form-group col-xs-12">
												<label for="resourcenames" class="col-xs-2 control-label">资源</label>
												<div class="col-xs-10">
													<div class="input-group">
														<input type="text" class="form-control" id="resourcenames" value="${hasResourceName}" name="resourcenames" readonly="readonly" />
														<span class="input-group-btn">
			      										  <button class="btn  btn-style" type="button" onclick="openResource('${!empty role?role.id:0}')">选择</button>
			     										 </span>
													</div>
												</div>

											</div>

											<div class="form-group col-xs-12">
												<label for="description" class="col-xs-2 control-label">备注</label>
												<div class="col-xs-10">
													<textarea rows="3" class="form-control" name="description" maxlength="100">${role.description}</textarea>
												</div>
												
											</div>

											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<c:if test="${flag=='update'}">
													<input type="submit" class="btn  btn-style" value="修改"  />
												</c:if>
												
												<c:if test="${flag=='add'}">
													<input type="submit"class="btn  btn-style" value="增加"  />
												</c:if>
												<input type="reset" class="btn  btn-style" value="重置" />
												<input type="button" class="btn btn-style" onclick="history.go(-1)" value="返回">
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
	<script>
		$().ready(function() {
			$("#mainForm").validate({
				rules: {
					name: "required"
				},
				messages: {
					name: "请输入角色名",
				},submitHandler:function(form){
					 if($(form).valid()){
							 if('${flag}'=='add'){
								 	
									$("#mainForm").attr("action","${pageContext.request.contextPath }/user/addRole.shtml");
								}else{
									var id="${role.id}";
									$("#mainForm").attr("action","${pageContext.request.contextPath }/user/updateRole.shtml?id="+id);
								}
							 form.submit();
	                    }else{
	                        return false;
	                    }
				}
				
			});
		});
		//打开资源选择
		function openResource(id){
			openWindow("${pageContext.request.contextPath }/user/to_getResourceMenu.shtml?id="+id,"资源选择",300,600);
		}
	</script>

</html>