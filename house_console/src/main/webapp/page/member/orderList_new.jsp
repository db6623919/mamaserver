<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!--会员订单记录-->
		<title>会员订单记录</title>
	</head>
	<body>
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
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">会员订单记录</h2></div>

								<div class="row query">
									<div class="col-xs-12">
										<input type="button" class="btn btn-style" onclick="history.go(-1)" value="返回"/>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>房源</td>
												<td>订单时间</td>
												<td>订单总金额</td>
												<td>订单冻结金额</td>
												<td>订单状态</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											
											<c:if test="${pager.list != null }">
									      		<c:forEach items="${pager.list}" var="order">
										        	<tr>
										        	 <td>${order.houseName }</td>
											          <td>${order.operTime }</td>
											          <td>${order.totalAmt }</td>
											          <td>${order.freezeAmt}</td>
											          <td><c:if test="${order.status=='10' }">等待客服确认</c:if>
											              <c:if test="${order.status=='11' }">等待客户入住</c:if>
											              <c:if test="${order.status=='12' }">客户在店</c:if>
											              <c:if test="${order.status=='13' }">客户离店</c:if>
											              <c:if test="${order.status=='21' }">客户取消订单</c:if>
											              <c:if test="${order.status=='22' }">商户取消订单</c:if>
											              <c:if test="${order.status=='23' }">订单取消</c:if></td>
											          <td>
											          	  <a href="javascript:orderInfo('${uid }','${order.orderId }')" class="templatemo-edit-btn" title="订单详情">订单详情</a>
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

	</body>
	<%@include file="/page/common/foot.jsp" %>
<script>
function orderInfo(uid,orderId){
	window.location='${pageContext.request.contextPath }/member/toOrderInfo.shtml.shtml?id='+uid+"&orderId="+orderId+"&phone=${phone}";
}
</script>

</html>