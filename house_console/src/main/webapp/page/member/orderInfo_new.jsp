<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>订单详情</title>
	</head>
	<body>
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

							<div class="col-xs-12 ">
								<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

									<div class="panel-heading templatemo-position-relative">
										<h2 class="text-uppercase">订单详情</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" class="form-horizontal">
											<div class="form-group col-xs-6">
												<label for="orderId" class="control-label text-left col-xs-3">订单编号</label>
												<div class="col-xs-9">
													<input type="text" name="orderId" value="${order.orderId}" id="orderId" class="form-control border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="houseId" class="control-label text-left col-xs-3">房屋编号</label>
												<div class="col-xs-9">
													<input type="text" name="houseId" value="${order.houseId}" id="houseId" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="freezeAmt" class="control-label text-left col-xs-3">订单冻结金额</label>
												<div class="col-xs-9">
													<input type="text" name="freezeAmt" value="${order.freezeAmt}" id="freezeAmt" class="form-control  border-0 bg-f"  readonly="readonly">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="totalAmt" class="control-label text-left col-xs-3">订单总金额</label>
												<div class="col-xs-9">
													<input type="text" name="totalAmt" value="${order.totalAmt}" id="totalAmt" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="operTime" class="control-label text-left col-xs-3">订单时间</label>
												<div class="col-xs-9">
													<input type="text" name="operTime" value="${order.operTime}" id="operTime" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="houseName" class="control-label text-left col-xs-3">房源</label>
												<div class="col-xs-9">
													<input type="text" name="houseName" value="${order.houseName}" id="houseName" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="status" class="control-label text-left col-xs-3">订单状态</label>
												<div class="col-xs-9">
													<c:set value="" var="status"></c:set>
													<c:if test="${order.status=='10' }"><c:set value="等待客服确认" var="status"></c:set></c:if>
										              <c:if test="${order.status=='11' }"><c:set value="等待客户入住" var="status"></c:set></c:if>
										              <c:if test="${order.status=='12' }"><c:set value="客户在店" var="status"></c:set></c:if>
										              <c:if test="${order.status=='13' }"><c:set value="客户离店" var="status"></c:set></c:if>
										              <c:if test="${order.status=='21' }"><c:set value="客户取消订单" var="status"></c:set></c:if>
										              <c:if test="${order.status=='22' }"><c:set value="商户取消订单" var="status"></c:set></c:if>
										              <c:if test="${order.status=='23' }"><c:set value="订单取消" var="status"></c:set></c:if>
													<input type="text" name="status" value="${status}" id="status" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												
											</div>
											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="button" class="btn btn-style" onclick="history.go(-1)" value="返回" />
											</div>
										</form>

									</div>

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
		$().ready(function() {
			$("#mainForm").validate({
				rules: {
					inputUserName: "required",
					inputName: "required",
					inputTest: {
						required: true,
						maxlength: 6
					}
				},
				messages: {
					inputUserName: "请输入用户名",
					inputName: "请输入姓名",
					inputTest: 'max length 6'
				},
				errorContainer: "div.error",
				errorLabelContainer: $("#mainForm div.error"),
				wrapper: "li"
			});
		});
	</script>

</html>