<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>房源标签管理</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/houseTag/list.shtml" method="post">
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
                            			<!--新增用户-->
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">房源标签管理</h2></div>

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
												<td>标签</td>
												<td>是否搜索显示</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="tag">
									        	<tr>
									        	  <td>${tag.id}</td>
												  <td style="max-width: 600px">${tag.name }</td>
												  <td><c:if test="${tag.isDispayOnSearch==1 }">显示</c:if>
												      <c:if test="${tag.isDispayOnSearch==0 }">不显示</c:if>
												  </td>
									              <td>
									              	<a href="javascript:modifTag('${tag.id }')" class="templatemo-edit-btn" title="修改">修改</a>
									              	<a href="javascript:deleteTag('${tag.id }')" class="templatemo-edit-btn" title="删除">删除</a>
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
		window.location='${pageContext.request.contextPath }/houseTag/to_addHouseTag.shtml';
	});
});

function deleteTag(id){
	del(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/houseTag/addHouseTag.shtml?id="+id+"&flag=del");
		$("#mainForm").submit();
	});
}

function modifTag(id){
	window.location='${pageContext.request.contextPath }/houseTag/getHouseTagById.shtml?id='+id;
}
</script>
</html>