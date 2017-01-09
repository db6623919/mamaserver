<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>售房列表查询</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/user/toUserList.shtml" method="post">
		<div id="wrapper">
	
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="5" name="index"/>
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
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">售房列表查询</h2></div>

								<div class="row query">
									<div class="col-xs-12">
										<button type="button" class="btn  btn-style" id="addButton">新增</button>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>名称</td>
												<td>省</td>
												<td>市</td>
												<td>开发商</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${buildingsList!=null }">
									      		<c:forEach items="${buildingsList}" var="build">
									        	<tr>
										          <td>${build.name }</td>
										          <td>
											          <c:forEach items="${proList}" var="pro">
											              <c:if test="${build.provId==pro.provId }">
												               ${pro.provName }	
												          </c:if>						    
												     </c:forEach>
												  </td>
										          <td>
										             <c:forEach items="${cityList}" var="city">
											              <c:if test="${build.cityId==city.cityId }">
												               ${city.cityName }	
												          </c:if>						    
												     </c:forEach>
										          </td>
										          <td>
										            <c:forEach items="${devList}" var="dev">
											              <c:if test="${build.devId==dev.devId }">
												               ${dev.devName }	
												          </c:if>						    
												     </c:forEach>
										          </td>
									              <td>
									              	<a href="javascript:modifUser('${build.bldId }')" title="修改" class="templatemo-edit-btn">修改</a>
									              	<a href="javascript:deleteUser('${build.bldId }')" title="删除" class="templatemo-edit-btn">删除</a>
									              </td>
									            </tr>
									       		</c:forEach>
									    	</c:if>
											
										</tbody>
									</table>
								</div>
								<div class="row pagination-div text-center ">
									<nav class="mg-right-20">
										<jsp:include page="/page/common/pager.jsp"/>
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
	</form>
	</body>
	<%@include file="/page/common/foot.jsp" %>
	<script>
$(function(){
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/sellHouse/to_addBuilding.shtml';
	});
});
function addUser(){
	window.location='${pageContext.request.contextPath }/sellHouse/addBuilding.shtml';
}
function modifUser(id){
	window.location='${pageContext.request.contextPath }/sellHouse/getBuildingsDetail.shtml?buildId='+id;
}
function deleteUser(id,username){
	del(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/sellHouse/deleteBuilding.shtml?id="+id);
		$("#mainForm").submit();
	});
}
</script>
</html>