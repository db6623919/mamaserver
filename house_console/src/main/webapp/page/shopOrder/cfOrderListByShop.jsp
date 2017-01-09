<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>刷单明细</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/shopOrder/toCFOrderListByShop.shtml" method="post">
			<!-- Navigation -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">刷单明细</h2></div>
								<input  type="hidden" id="shopId" name="shopId" value="${shopId }"/>
								<div class="row query">
									<div class="col-xs-12">
										<div class="col-xs-10">
											订单编号：<input type="text" id="orderId" name="orderId" value="${orderId}" />&nbsp;&nbsp;&nbsp;&nbsp;
											订单时间：<input type="text" id="startTime" name="startTime" value="${startTime }" onFocus="WdatePicker({maxDate:new Date(),skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'});"/> 至
											<input type="text" id="enTime" name="enTime" value="${enTime }" onFocus="WdatePicker({maxDate:new Date(),skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'});"/>&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
										<div class="col-xs-2">	
											<input type="submit" value="查  询" class="btn  btn-style"/>
											<input type="button" onclick="exportOrderList()" value="导   出" class="btn  btn-style"/>
										</div>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>订单编号</td>
												<td>客栈名称</td>
												<td>订单原始金额</td>
												<td>优惠金额</td>
												<td>实际支付金额</td>
												<!-- <td>支付方式</td> -->
												<td>联系方式</td>
												<td>联系人</td>
												<td>状态</td>
												<td>订单时间</td>
												<td>结算状态</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="order">
									        	<tr>
									        		<td>${order.orderId }</td>
										        	<td>${order.shopName }</td>
									        		<td>${order.totalAmt } </td>
										         	<td>${order.freezeAmt }</td>
										         	<td>${order.payAmt }</td>
										         	<td>${order.userPhone }</td>
												  	<td>${order.userName }</td>
												  	<%-- <td>${order.payType }</td> --%>
												  	<td>
												  	    <c:if test="${order.status == 9 }">待支付</c:if>
												  	    <c:if test="${order.status == 11 }">已支付</c:if>
												  	</td>
												  	<td>${order.operTime }</td>
												  	<td>
												  		<c:if test="${order.status == 11 }">
												  			<c:choose>
												  				<c:when test="${order.settleStatus == 0 }">
												  					未结算
												  				</c:when>
												  				<c:otherwise>
												  					已结算
												  				</c:otherwise>
												  			</c:choose>
												  			
												  		</c:if>
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
	</form>
	</body>
		<%@include file="/page/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script src="${pageContext.request.contextPath }/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
//导出
function exportOrderList() {
	var shopId = $("#shopId").val();
	var orderId = $("#orderId").val();
	var startTime = $("#startTime").val();
	var enTime = $("#enTime").val();
	window.location='${pageContext.request.contextPath }/shopOrder/exportOrderList.shtml?startTime=' + startTime + "&orderId=" + orderId + "&shopId=" + shopId + "&enTime=" + enTime;
}

</script>
</html>