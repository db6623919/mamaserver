<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!--记录-->
		<title>会员收藏列表</title>
	</head>
	<body>
		<form id="mainForm" method="post">
		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="3" name="menu"/>
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
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">会员收藏列表</h2></div>

								<div class="row query">
									<div class="col-xs-12">
										<button type="button" class="btn  btn-style" onclick="history.go(-1)">返回</button>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>房屋编号</td>
												<td>收藏时间</td>
												
											</tr>
										</thead>
										<tbody>
											<c:if test="${pager.collectList != null }">
									      		<c:forEach items="${pager.collectList}" var="collect">
										        	<tr>
											          <td>${collect.houseId }</td>
											          <td>${collect.operTime }</td>
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
		$('.pagination').pagination({
			current:1,
			//总数量
			totalData:${favorite_list["total"]},
			//每页显示的数量
			showData:${favorite_list["pageSize"]},
			coping:true,
			homePage:'首页',
    		endPage:'末页',
			callback: function(index) {
				$.ajax({
					type:"get",
					url:"${baseUrl}/order/favorite/list",
					success:function(data){
						console.log(data);
					}
				});
			}
		});
	</script>

</html>