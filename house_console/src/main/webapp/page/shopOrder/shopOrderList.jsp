<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>资金结算</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/shopOrder/toShopOrderList.shtml" method="post">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="5" name="menu"/>
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
									<h2 class="text-uppercase">资金结算</h2></div>

								<div class="row query">
									<div class="col-xs-12">
										<div class="col-xs-11">
											客栈名称：<input type="text" id="shopName" name="shopName" value="${shopName}" />&nbsp;&nbsp;&nbsp;&nbsp;
											客栈老板：<input type="text" id="bossName" name="bossName" value="${bossName}" />&nbsp;&nbsp;&nbsp;&nbsp;
											联系方式：<input type="text" id="bossPhone" name="bossPhone" value="${bossPhone}" />
										</div>
										<div class="col-xs-1">	
											<input type="submit" value="查  询" class="btn  btn-style"/>
										</div>
									</div>
									<div class="col-xs-12"> 
										<div class="col-xs-2">
										</div>
										<div class="col-xs-2">
											<b>总金额：</b><span style="color: blue;">${totalAmt }</span>&nbsp;&nbsp;&nbsp;
										</div>
										<div class="col-xs-2">
											<b>总优惠：</b><span style="color: blue;">${freezeAmt }</span>&nbsp;&nbsp;&nbsp;
										</div>
										<div class="col-xs-2">
											<b>总支付：</b><span style="color: blue;">${payAmt }</span>&nbsp;&nbsp;&nbsp;
										</div>
										<div class="col-xs-2">
											<b>已支付数：</b><span style="color: blue;">${orderNum }</span>&nbsp;&nbsp;&nbsp;
										</div>
										<div class="col-xs-2">
											<b>已结算数：</b><span style="color: blue;">${settleNum }</span>
										</div>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>客栈名称</td>
												<td>原始订单总金额</td>
												<td>优惠总金额</td>
												<td>实际支付总金额</td>
												<td>已支付数</td>
												<td>已结算数</td>
												<td>客栈老板</td>
												<td>联系方式</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="cfShopOrder">
									        	<tr>
									        		<td>${cfShopOrder.shopName }</td>
										        	<td>${cfShopOrder.totalAmt }</td>
									        		<td>${cfShopOrder.freezeAmt } </td>
										         	<td>${cfShopOrder.payAmt }</td>
										         	<td>${cfShopOrder.orderNum }</td>
										         	<td>${cfShopOrder.settleNum }</td>
												  	<td>${cfShopOrder.bossName }</td>
												  	<td>${cfShopOrder.bossPhone }</td>
												  	<td>
												  	   <a href="javascript:toCFOrderList(${cfShopOrder.shopId })">明细</a>&nbsp;&nbsp;|&nbsp;&nbsp;
												  	   <a href="javascript:toBillList(${cfShopOrder.shopId },'${cfShopOrder.shopName }')">对账单</a>
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
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script type="text/javascript">
function toCFOrderList(shopId) {
	/* window.location='${pageContext.request.contextPath }/shopOrder/toCFOrderList.shtml?shopId=' + shopId; */
	openWindow('${pageContext.request.contextPath }/shopOrder/toCFOrderListByShop.shtml?shopId=' + shopId,"刷单列表",1300,700);
}

function toBillList(shopId,shopName) {
	openWindow('${pageContext.request.contextPath }/shopOrder/toShopOrderBillList.shtml?shopId=' + shopId + '&shopName=' + shopName,"刷单列表",1300,700);
}
</script>
</html>