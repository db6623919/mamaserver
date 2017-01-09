<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>主题活动管理</title>
	</head>
	<body>
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="2" name="index"/>
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
										<h2 class="text-uppercase">活动编辑</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm"  class="form-horizontal"  action="${pageContext.request.contextPath}/topic/doAddOrEdit.shtml?flag=edit" method="post" enctype="multipart/form-data">
											<input type="hidden"  name="id" id="id" value="${topicActivity.id }">
											<div class="form-group col-xs-12">
												<label for="cityName" class="col-xs-2 control-label"><font class="star">*</font>活动名称</label>
												<div class="col-xs-10">
													<input type="text" required name="activityName" id="activityName" class="form-control" value="${topicActivity.activityName }" maxlength="20">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="cityName" class="col-xs-2 control-label"><font class="star">*</font>活动类型</label>
												<div class="col-xs-10">
													<select id="activityType" name="activityType" class="form-control valid">
														<option value="1" <c:if test="${topicActivity.activityType == 1 }">selected="selected"</c:if>>单身</option>
														<option value="2" <c:if test="${topicActivity.activityType == 2 }">selected="selected"</c:if>>亲子</option>
														<option value="3" <c:if test="${topicActivity.activityType == 3 }">selected="selected"</c:if>>老人</option>
														<option value="4" <c:if test="${topicActivity.activityType == 4 }">selected="selected"</c:if>>团建</option>
													</select>
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="province" class="col-xs-2 control-label"><font class="star">*</font>专题图片</label>
												<div class="col-xs-10">
														<input type="file" id="imgUrl" name="imgUrl" class="form-control w-260 inline"/>
														原路径：${topicActivity.imgUrl }
												</div>
											</div>

											<div class="form-group col-xs-12">
												<label for="type" class="col-xs-2 control-label">简要介绍</label>
												<div class="col-xs-10 ">
													<input type="text" required name="title" id="title" class="form-control" value="${topicActivity.title }" maxlength="30">
												</div>
											</div>

											<div class="form-group col-xs-12">
												<label for="description" class="col-xs-2 control-label">活动介绍</label>
												<div class="col-xs-10">
													<textarea  required name="introduction" id="introduction" class="form-control" maxlength="200">${topicActivity.introduction }</textarea>
												</div>
											</div>

											<div class="form-group col-xs-6">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="submit" class="btn  btn-style" value="提交" />
												<input type="reset" class="btn  btn-style" value="重置" />
												<input type="button" class="btn btn-style" value="返回" onclick="history.go(-1)"/>
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
		
		
	</script>
</html>