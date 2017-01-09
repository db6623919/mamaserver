<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<%@include file="/page/common/base.jsp" %>
<title>用户管理</title>
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
							<!--新增用户-->
						</h1>
					</div>
				</div>
				<!-- /.row -->
				<div class="row">
					<form action="${pageContext.request.contextPath }/user/toUserList.shtml" method="post" id="mainForm" method="post" >
					<div class="container-fluid">
						<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

							<div class="panel-heading templatemo-position-relative">
								<h2 class="text-uppercase">用户管理</h2>
							</div>

							<div class="row query">
								
									<div class="col-xs-4">
										<div class="input-group">
											<span class="input-group-addon" id="basic-addon1">用户名</span> <input type="text"  class="form-control search-input" id="s_username" value="${s_username}" name="s_username" placeholder="admin" aria-describedby="basic-addon1">
										</div>
									</div>
									<div class="col-xs-4">
										<div class="input-group">
											<span class="input-group-addon" id="basic-addon1">姓名</span> <input type="text" class="form-control search-input" id="s_name" value="${s_name }" name="s_name" placeholder="管理员" aria-describedby="basic-addon1">
										</div>
									</div>
									<div class="col-xs-4">
										<button type="button" id="serchButton" class="btn  btn-style">搜索</button>
										<button type="button" class="btn  btn-style" id="addButton">新增</button>
									</div>
								
							</div>

							<div class="table-responsive table-hover">
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<td>用户名</td>
											<td>姓名</td>
											<td>状态</td>
											<td>备注</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody class="data_list">
										<c:if test="${pager.list!=null }">
								      		<c:forEach items="${pager.list}" var="user">
								        	<tr>
									          <td>${user.username }</td>
									          <td>${user.name }</td>
									          <td>${define:trans_status(user.status)}</td>
									          <td>${user.description }</td>
								              <td>
								              	<a href="javascript:modifUser('${user.id }','${user.status }' )" class="templatemo-edit-btn"  title="修改">修改</a>
								              	<a href="javascript:deleteUser('${user.id }','${user.username }')" class="templatemo-edit-btn" title="删除">删除</a>
								              </td>
								            </tr>
								       		</c:forEach>
								    	</c:if>
										<%-- <tr>
											<td>${user.username}</td>
											<td>${user.name}</td>
											<td>${user.status}</td>
											<td>${user.remark}</td>

											<td><a href="${baseUrl}/user/user/update/${user.id}" class="templatemo-edit-btn">修改</a><a href="#" onclick="del(1,function(){myAlert('提示','删除成功')})" class="templatemo-edit-btn">删除</a></td>
										</tr> --%>
										
									</tbody>
								</table>
							</div>
							<div class="row pagination-div text-center ">
								<jsp:include page="/page/common/pager.jsp"/>
							</div>
						</div>

					</div>
				</form>
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
	$("#serchButton").click(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/user/to_user.shtml?s_username="+$("#s_username").val()+"&s_name="+$("#s_name").val());
		$("#mainForm").submit();
	});
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/user/to_addUser.shtml';
	});
});
function addUser(){
	window.location='${pageContext.request.contextPath }/user/to_addUser.shtml';
}
function modifUser(id,username){
	window.location='${pageContext.request.contextPath }/user/to_updateUser.shtml?id='+id+"&username="+username;
}
function deleteUser(id,username){
	del(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/user/deleteUser.shtml?id="+id+"&username="+username);
		$("#mainForm").submit();
	});
	
}
</script>
</html>