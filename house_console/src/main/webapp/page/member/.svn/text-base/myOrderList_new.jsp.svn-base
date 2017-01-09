<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!--订单列表查询-->
		<title>订单列表查询</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/member/toMyOrderList.shtml" method="post">
		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="3" name="menu"/>
				<jsp:param value="2" name="index"/>
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
									<h2 class="text-uppercase">订单列表查询</h2></div>

								<div class="row query form-horizontal">
										<div class="col-xs-10">
											<div class="form-group col-xs-12">
												<label for="city" class="col-xs-2 control-label text-left">城市</label>
												<div class="checkbox col-xs-10">
													<c:forEach items="${cityInfoList}" var="city">
													  <c:if test="${city.checked==1 }">
													    <label><input type="checkbox" name="cities" checked="checked" value="${city.cityId }"/>${city.cityName }</label>
													   </c:if>
													   <c:if test="${city.checked==0}">
													    <label><input type="checkbox" name="cities" value="${city.cityId }"/>${city.cityName }</label>
													   </c:if>
					
													</c:forEach>
												</div>
											</div>
										</div>
	
										<div class="col-xs-2 right">
											<button type="submit" class="btn  btn-style">搜索</button>
										</div>

								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>城市</td>
												<td>楼盘</td>
												<td>预订人</td>
												<td>订单时间</td>
												<td>状态</td>
												<td>房源</td>
												<td>入住时间</td>
												<td>离店时间</td>
												<td>联系方式</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody class="data_list">
											<c:if test="${pager.list != null }">
									      		<c:forEach items="${pager.list}" var="order">
										        	<tr>
										        	 <td>
										             <c:forEach items="${cityList}" var="city">
											              <c:if test="${order.cityId==city.cityId }">
												               ${city.cityName }	
												          </c:if>						    
												     </c:forEach>
										          </td>
										           <td>
										             <c:forEach items="${buildList}" var="build">
											              <c:if test="${build.bldId==order.bldId }">
												               ${build.name }	
												          </c:if>						    
												     </c:forEach>
										          </td>
										        	<td>${order.userName}</td>
											          <td>${order.operTime }</td>
											          <td><c:if test="${order.status=='10' }">等待客服确认</c:if>
											              <c:if test="${order.status=='11' }">等待客户入住</c:if>
											              <c:if test="${order.status=='12' }">客户在店</c:if>
											              <c:if test="${order.status=='13' }">客户离店</c:if>
											              <c:if test="${order.status=='14' }">超时未入住</c:if>
											              <c:if test="${order.status=='21' }">客户取消订单</c:if>
											              <c:if test="${order.status=='22' }">商户取消订单</c:if>
											              <c:if test="${order.status=='23' }">订单取消</c:if>
											              <c:if test="${order.status=='24' }">预订失败</c:if>
											          </td>
											          <td>${order.houseName}</td>
											          <td>${order.beginTime}</td>
											          <td>${order.endTime}</td>
											          <td>${order.phone}</td>
											          <td>
											          	<a href="javascript:orderInfo('${order.uid }','${order.orderId }')" class="templatemo-edit-btn" title="订单详情">订单详情</a>
											          </td>
										            </tr>
									       		</c:forEach>
									    	</c:if>
										</tbody>
									</table>
								</div>
								<div class="row pagination-div text-center ">
									<nav class="mg-right-20">
										<!--分页 -->
										<div class="pagination"></div>
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
	function orderInfo(uid,orderId){
		window.location='${pageContext.request.contextPath }/member/toOrderInfo.shtml.shtml?id='+uid+"&orderId="+orderId+"&phone=${phone}";
	}
	</script>

</html>