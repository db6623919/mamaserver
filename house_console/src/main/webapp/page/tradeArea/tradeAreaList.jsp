<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>商圈管理</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/tradeArea/list.shtml" method="post">
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
                            			<!--新增用户-->
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">商圈管理</h2></div>

								<div class="row query">
									<div class="col-xs-12">
										<button type="button" class="btn  btn-style" id="addButton">新增</button>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>id</td>
												<td>城市</td>
												<td>商圈</td>
												<td>排序</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="area">
									        	<tr>
									        	  <td>${area.id}</td>
										          <td>${area.cityId }</td>
												  <td style="max-width: 600px">${area.name }</td>
												  <td>${area.sort }</td>
									              <td>
									              	<a href="javascript:modifArea('${area.id }')" class="templatemo-edit-btn" title="修改">修改</a>
									              	<a href="javascript:deleteArea('${area.id }','${area.cityId }')" class="templatemo-edit-btn" title="删除">删除</a>
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
	$("#serchButton").click(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/city/toCity.shtml").val();
		$("#mainForm").submit();
	});
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/tradeArea/to_addTradeArea.shtml';
	});
});

function deleteArea(id,cityId){
	del(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/tradeArea/deleteTradeArea.shtml?id="+id+"&cityId="+cityId);
		$("#mainForm").submit();
	});
}

function modifArea(id){
	window.location='${pageContext.request.contextPath }/tradeArea/getTradeAreaById.shtml?id='+id;
}
</script>
</html>