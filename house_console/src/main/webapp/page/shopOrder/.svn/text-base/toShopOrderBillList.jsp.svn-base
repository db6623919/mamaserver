<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>对账单列表</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/shopOrder/toShopOrderBillList.shtml" method="post">
			<!-- Navigation -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">${shopName }对账单</h2></div>
								<input  type="hidden" id="shopId" name="shopId" value="${shopId }"/>
								<div class="row query">
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>序号</td>
												<td>原始总金额</td>
												<td>优惠总金额</td>
												<td>实际支付总金额</td>
												<td>订单数</td>
												<td>结算数</td>
												<td>对账周期</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="order" varStatus="status">
									        	<tr>
									        		<td>${(pager.current_page - 1) * pager.page_size + status.index + 1}</td>
									        		<td>${order.totalAmt } </td>
										         	<td>${order.freezeAmt }</td>
										         	<td>${order.payAmt }</td>
										         	<td>${order.orderNum }</td>
										         	<td>${order.settleNum }</td>
												  	<td>${order.operTime }</td>
												  	<td><a href="javascript:exportExcel(${order.shopId },'${shopName }','${order.operTime }')">导出</a></td>
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
//检查文件是否存在
function exportExcel(shopId,shopName,operTime) {
	$.ajax({
		url:"${pageContext.request.contextPath }/shopOrder/checkBillPath.shtml",
		type:'post',
		async: false,
		data:{"shopName":shopName,"operTime":operTime} ,
		success:function(data) {
			if(data.success){
				window.location='${pageContext.request.contextPath }/shopOrder/exportBillExcel.shtml?shopName=' + shopName + "&operTime=" + operTime + "&shopId=" + shopId;
			} else {
				alert("对账单尚未生成!");
			}
		}
	}); 
}

</script>
</html>