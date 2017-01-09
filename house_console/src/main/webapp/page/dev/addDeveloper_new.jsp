<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>开发商管理</title>
	</head>
	<body>
		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
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
										<h2 class="text-uppercase">开发商管理</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="${pageContext.request.contextPath}/dev/addDeveloper.shtml" method="post" class="form-horizontal">
											<div class="form-group col-xs-12">
												<label for="devName" class="col-xs-2 control-label"><font class="star">*</font>名称</label>
												<div class="col-xs-10">
													<input type="text" name="devName" id="devName"  class="form-control">
												</div>

											</div>

											<div class="form-group col-xs-12">
												<label for="type" class="col-xs-2 control-label">类型</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="type" name="type" aria-invalid="false">
														<option value="1">热门</option>
														<option value="2">推荐</option>
													</select>
												</div>

											</div>

											<div class="form-group col-xs-12">
												<label for="telphone" class="col-xs-2 control-label"><font class="star">*</font>联系电话</label>
												<div class="col-xs-10">
													<input type="number" name="telphone" id="telphone" class="form-control">
												</div>
												
											</div>
											<div class="form-group col-xs-12">
												<label for="devImg" class="col-xs-2 control-label">开发商图片</label>
												<div class="col-xs-10">
													<input type="text" name="devImg" id="devImg" class="form-control">
												</div>
												
											</div>
											<div class="form-group col-xs-12">
												<label for="video" class="col-xs-2 control-label">开发商视频</label>
												<div class="col-xs-10">
													<input type="text"  name="video" id="video" class="form-control">
												</div>
												
											</div>

											<div class="form-group col-xs-12">
												<label for="description" class="col-xs-2 control-label">开发商介绍</label>
												<div class="col-xs-10">
													<input type="text" name="description" id="description" class="form-control">
												</div>
												
											</div>

											<div class="form-group col-xs-12">
												<label for="mark" class="col-xs-2 control-label">开发商标签</label>
												<div class="col-xs-10">
													<input type="text" name="mark"  id="mark" class="form-control">
												</div>
											</div>
											

											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="submit" class="btn  btn-style" value="增加" />
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
	<script>
		$().ready(function() {
			$("#mainForm").validate({
				rules: {
					devName: "required",
					telphone: "required"
				},
				messages: {
					devName: "请输入楼盘名称",
					telphone: "请输入联系方式"
						
				}
			});
		});
	</script>

</html>