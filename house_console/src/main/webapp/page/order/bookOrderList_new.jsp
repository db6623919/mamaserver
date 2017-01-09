<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<%@include file="/page/common/base.jsp" %>
		
		<!--预订管理-->
	</head>
	<body>
		<form id="mainForm" method="post" action="${pageContext.request.contextPath }/order/toBookOrderList.shtml">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="4" name="menu"/>
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
									<h2 class="text-uppercase">预订管理</h2></div>

								<div class="row query form-horizontal">
										<div class="form-group col-xs-6">
											<label for="like" class="col-xs-3 control-label text-left">状态</label>
											<div class="checkbox col-xs-9">
												<label><input type="checkbox" name="checkedstatus"  value="-1" checked="checked" hidden /></label>
												<label><input type="checkbox" id="check_s15" name="checkedstatus" checked="checked"   value="15"/>待确认</label>
							     				<label><input type="checkbox" id="check_s10" name="checkedstatus" checked="checked"   value="10"/>待审核</label>
								  				<label><input type="checkbox" id="check_s24" name="checkedstatus" checked="checked"   value="24"/>预订失败</label>
											</div>
										</div>
										<div class="form-group col-xs-6">
											<label for="x" class="col-xs-3 control-label">城市</label>
											<div class="col-xs-9">
												<select id="cityselect" class="form-control" name="city">
													<option value='' selected="selected">全部</option>
													<c:forEach items="${cityList}" var="city">
														<option value="${city.cityId}">${city.cityName}</option>
													</c:forEach>
												</select>
											</div>
	
										</div>
										<div class="form-group col-xs-6">
											<label for="x" class="col-xs-3 control-label">楼盘</label>
											<div class="col-xs-9">
												<select id="buildselect" class="form-control" name="build">
													<option value='' selected="selected">全部</option>
													<c:forEach items="${buildList}" var="build">
														<option value="${build.bldId}">${build.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group col-xs-6">
											<label for="x" class="col-xs-3 control-label">手机号码</label>
											<div class="col-xs-7" >
												<input type="text" value="${phone}" name="phone" id="phone" class="form-control" />
											</div>
											<div class="col-xs-2">
												<button type="button" id="serchButton" class="btn  btn-style">搜索</button>
											</div>
										</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>订单号</td>
												<td>城市</td>
												<td>楼盘</td>
												<td>房型</td>
												<td>预订人</td>
												<td>金额</td>
												<td>入住日期</td>
												<td>离开日期</td>
												<td>联系方式</td>
												<td>状态</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody class="data_list">
											<c:if test="${pager != null }">
												<c:forEach items="${pager.list}" var="order">
													<tr>
														<td>${order.orderId}</td>
		
														<td><c:forEach items="${cityList}" var="city">
																<c:if test="${order.cityId==city.cityId }">
												               ${city.cityName }	
												          </c:if>
															</c:forEach></td>
														<td><c:forEach items="${buildList}" var="build">
																<c:if test="${build.bldId==order.bldId }">
												               ${build.name }	
												          </c:if>
															</c:forEach></td>
														<td>${order.room }室${order.hall }厅${order.health }卫${order.bed }床</td>
														<td>${order.userName }</td>
														<td>${order.payAmt }</td>
														<td>${order.beginTime}</td>
														<td>${order.endTime}</td>
														<td>${order.phone}</td>
		
														<td><c:if test="${order.status=='10' }">待审核</c:if> <c:if test="${order.status=='11' }">等待客户入住</c:if> <c:if test="${order.status=='12' }">客户在店</c:if>
															<c:if test="${order.status=='13' }">客户离店</c:if><c:if test="${order.status=='15' }">待确认</c:if><c:if test="${order.status=='21' }">客户取消订单</c:if> <c:if test="${order.status=='24' }">预订失败</c:if> <c:if
																test="${order.status=='23' }">订单已取消</c:if></td>
														<td><c:if test="${order.status=='10' }">
		
																<a href="javascript:audtingInfo('${order.orderId}',1)" class="templatemo-edit-btn" title="审核">审核</a>
															</c:if>
															<c:if test="${order.status=='15' }">
																<a href="javascript:audtingInfo('${order.orderId}',2)" class="templatemo-edit-btn" title="价格修改查看">查看</a>
															</c:if> 
															<c:if test="${order.status=='23' }">
																<a href="javascript:audtingInfo('${order.orderId}',3)" class="templatemo-edit-btn" title="已取消查看">查看</a>
															</c:if>
															<c:if test="${order.status=='24' }">
																	
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
										<!--分页 -->
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
		<script type="text/javascript">
			function audtingInfo(orderId) {
				window.location = '${pageContext.request.contextPath }/member/toOrderInfo.shtml.shtml?id='
						+ uid + "&orderId=" + orderId + "&phone=${phone}";
			}
		
			var citys = ${cityStr};
			var builds = ${buildStr};
		
			/*
			alert(citys);
			alert(citys.length);
			alert(builds);
			alert(builds.length);
			*/
			
		</script>
	<script>
	$(function() {
		$("#serchButton").click(
				function() {
					$("#mainForm").attr(
							"action",
							"${pageContext.request.contextPath }/order/toBookOrderList.shtml?phone="
									+ $('#phone').val() + "&status="
									+ $('#status').val());
					$("#mainForm").submit();
				});
		$("#fanhui")
				.click(
						function() {
							$("#backForm")
									.attr(
											"action",
											"${pageContext.request.contextPath }/member/toMemberList.shtml?phonemy=${phone}");
							$("#backForm").submit();
						});
		/* $("#addButton").click(function(){
			window.location='${pageContext.request.contextPath }/house/houseAddUpdate.shtml?flag=add';
		}); */

		$("#cityselect").change(function() {
							var htm = "<option value='' selected=\"selected\">全部</option>"
							var cid = $("#cityselect").val();
							var i = builds.length;
							if (cid != "") {
								while (i--) {
									if(cid==builds[i].cityId){									
									htm += "<option value=\""+builds[i].bldId+"\">"
											+ builds[i].name + "</option>";
										}
								}
							} else {
								while (i--) {
									htm += "<option value=\""+builds[i].bldId+"\">"
									+ builds[i].name + "</option>";
								}
							}
							$("#buildselect").html(htm);
						});
		
		
		
		
	});

	function houseorderInfo(houseId, orderId, beginTime, endTime) {
		window.location = '${pageContext.request.contextPath }/order/toHouseOrderInfo.shtml?houseId='
				+ houseId
				+ '&orderId='
				+ orderId
				+ '&begintime='
				+ beginTime
				+ '&endtime=' + endTime;
	}

	//审核
	function audtingInfo(orderId,type) {
		window.location = '${pageContext.request.contextPath }/order/toAudtingInfo.shtml?orderId='
				+ orderId+"&type="+type;
	}
	
	//查看
	function viewInfo(orderId) {
		window.location = '${pageContext.request.contextPath }/order/viewInfo.shtml?orderId='
				+ orderId;
	}

	function cancel(orderId) {
		window.location = '${pageContext.request.contextPath }/order/toCancel.shtml?orderId='
				+ orderId;
	}

	function checkin(orderId) {
		window.location = '${pageContext.request.contextPath }/order/toCheckin.shtml?orderId='
				+ orderId;
	}
	function departure(orderId) {
		window.location = '${pageContext.request.contextPath }/order/toDeparture.shtml?orderId='
				+ orderId;
	}

	function prolong(orderId) {
		window.location = '${pageContext.request.contextPath }/order/toProlong.shtml?orderId='
				+ orderId;
	}

	function checkout(orderId) {
		window.location = '${pageContext.request.contextPath }/order/toCheckout.shtml?orderId='
				+ orderId;
	}

	function refund(orderId) {
		window.location = '${pageContext.request.contextPath }/order/toRefund.shtml?orderId='
				+ orderId;
	}
	
	/*
	
	*/
	
	
	
	</script>

</html>