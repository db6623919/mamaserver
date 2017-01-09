<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
	<%@include file="/page/common/base.jsp" %>
		<title>角色管理</title>
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
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">角色管理</h2></div>

								<div class="row query">

									<div class="col-xs-12">

										<button type="button" class="btn  btn-style" id="addButton">新增</button>
									</div>

								</div>

								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>角色名</td>
												<td>状态</td>
												<td>备注</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="role">
									        	<tr>
										          <td>${role.name }</td>
										          <td>${define:trans_status(role.status)}</td>
										          <td>${role.description }</td>
									              <td>
									              	<a href="javascript:modifRole('${role.id }')" class="templatemo-edit-btn"  title="修改">修改</a>
								              		<a href="javascript:deletRole('${role.id }')" class="templatemo-edit-btn" title="删除">删除</a>
									               </td>
									            </tr>
									       		</c:forEach>
							    			</c:if>
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

$(function(){
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/user/to_addRole.shtml';
	});
});


function modifRole(id){
	window.location='${pageContext.request.contextPath }/user/to_updateRole.shtml?id='+id;
}

function deletRole(id){
	del(function(){
		window.location='${pageContext.request.contextPath }/user/deleteRole.shtml?id='+id;
	});
	
}



</script>
</html>