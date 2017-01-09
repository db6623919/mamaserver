<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
	<%@include file="/page/common/base.jsp" %>
		<title>首页展示顺序配置</title>
	</head>
	<body>
		<div id="wrapper">
			
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="6" name="index"/>
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
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">首页展示顺序配置</h2></div>

								<div class="row query">

									<div class="col-xs-12">

									</div>

								</div>

								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td >序号</td>
									   			<td >名称</td>
									   			<td >操     作</td>
											</tr>
										</thead>
										<tbody>
										 <tr>
								        	<td>1</td>
								        	<td>城市排序管理</td>
								            <td><a href="javascript:modifUser('1')" title="修改" class="templatemo-edit-btn">修改</a> </td>
							             </tr>
										</tbody>
									</table>
								</div>
								<div class="row pagination-div text-center ">
									<nav class="mg-right-20">
									</nav>
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
function modifUser(id){
	window.location='${pageContext.request.contextPath }/indexManager/toManager.shtml?id='+id;
}
</script>
</html>