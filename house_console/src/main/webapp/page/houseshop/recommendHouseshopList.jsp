<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>房源店铺管理</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/houseshop/recommended_list.shtml" method="post">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="13" name="index"/>
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
									<h2 class="text-uppercase">房源店铺管理</h2></div>

								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
											    <td>店铺ID</td>
												<td>店铺名</td>
												<td>城市</td>
												<td>级别</td>
												<td>类型</td>
												<td>合作关系</td>
												<td>boss姓名</td>
												<td>电话</td>
												<td>状态</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="houseshop">
									        	<tr>
									        	  <td>${houseshop.id }</td>
												  <td>${houseshop.shopName }</td>
												  <td>${houseshop.cityName }</td>
												  <td>${houseshop.level }</td>
												  <td>${houseshop.typeName }</td>
												  <td>
												 	<c:if test="${houseshop.partnership == 0}">会员客栈</c:if>
												  	<c:if test="${houseshop.partnership == 1}">控股客栈</c:if>
												  	<c:if test="${houseshop.partnership == 2}">深度合作</c:if>
												  	<c:if test="${houseshop.partnership == 3}">民宿贷客栈</c:if>
												  </td>
												  <td>${houseshop.bossName }</td>
												  <td>${houseshop.bossPhone }</td>
												  <c:if test="${houseshop.recommended_status=='0'}">
												     <td>未推荐</td>
												     <td>
									                   <a href="javascript:updateRecommed('${houseshop.id }','${houseshop.recommended_status}')" class="templatemo-edit-btn" title="查看">推荐</a>
									              </td>
												  </c:if>
												  <c:if test="${houseshop.recommended_status=='1'}">
												     <td>推荐</td>
												     <td>
									                   <a href="javascript:updateRecommed('${houseshop.id }','${houseshop.recommended_status}')" class="templatemo-edit-btn" title="查看">取消推荐</a>
									                 </td>
												  </c:if>
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

//推荐OR取消推荐
function updateRecommed(id,recommended_status){
	window.location='${pageContext.request.contextPath }/houseshop/updateRecommed.shtml?id='+id+'&recommended_status='+recommended_status;
}


</script>
</html>