<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>会员卡信息</title>
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
                            			<!--新增用户-->
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">

							<div class="col-xs-12 ">
								<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

									<div class="panel-heading templatemo-position-relative">
										<h2 class="text-uppercase">会员卡信息</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" class="form-horizontal" action="${pageContext.request.contextPath }/member/toMemberList.shtml?phonemy=${phone}">
											<div class="form-group col-xs-6">
												<label for="cardId" class="control-label text-left col-xs-3">会员卡号</label>
												<div class="col-xs-9">
													<input type="text" name="cardId" value="${card.cardId}"  class="form-control border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="totalRechargeAmt" class="control-label text-left col-xs-3">累计充值金额</label>
												<div class="col-xs-9">
													<input type="text" name="totalRechargeAmt" value="${card.totalRechargeAmt}"  class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="totalRewardAmt" class="control-label text-left col-xs-3">累计奖励金额</label>
												<div class="col-xs-9">
													<input type="text" name="totalRewardAmt" value="${card.totalRewardAmt}"  class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="availAmt" class="control-label text-left col-xs-3">账户余额</label>
												<div class="col-xs-9">
													<input type="text" name="availAmt" value="${card.availAmt}"  class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="freezeAmt" class="control-label text-left col-xs-3">冻结金额</label>
												<div class="col-xs-9">
													<input type="text" name="freezeAmt" value="${card.freezeAmt}"  class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">会员卡等级</label>
												<div class="col-xs-9">
													<c:set var="level" value=""></c:set>
													<c:choose>
														<c:when test="${card.level == '1'}"><c:set var="level" value="铜卡"></c:set></c:when>
														<c:when test="${card.level == '2'}"><c:set var="level" value="银卡"></c:set></c:when>
														<c:when test="${card.level == '3'}"><c:set var="level" value="金卡"></c:set></c:when>
														<c:when test="${card.level == '4'}"><c:set var="level" value="砖石卡"></c:set></c:when>
													</c:choose>
													<input type="text" name="orderNumber" value="${level}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
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