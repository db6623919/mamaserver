<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<%@include file="/page/common/base.jsp" %>
		<!--预订管理 订单详情-->
		<title>订单详情</title>
	</head>

	<body>
		<form id="mainForm" action="" class="form-horizontal" method="post">
			<c:forEach items="${orderList}" var="order">
			<input type="hidden" id="orderId" name="orderId" value="${order.orderId }" />
		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
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
										<h2 class="text-uppercase">订单详情</h2>
									</div>
									<div class="row mg-top-10">
									<input type="hidden" name="realInSeasonDays" id="realInSeasonDays" value="${order.realInSeasonDays}"/>
									<input type="hidden" name="user_phone" id="user_phone" value="${order.user_phone}"/>
									<input type="hidden" name="buildPhone" id="buildPhone" value="${order.buildPhone}"/>
											<div class="form-group col-xs-6">
												<label for="cityName" class="control-label text-left col-xs-3">城市</label>
												<div class="col-xs-9">
													<c:set value="" var="cityName"></c:set>
													<c:forEach items="${cityList}" var="city">
														<c:if test="${order.cityId==city.cityId }">
											               <c:set value="${city.cityName }" var="cityName"></c:set>
											          </c:if>
													</c:forEach>
													<input type="text"  name="orderNumber" value="${cityName }" id="cityName" class="form-control border-0 bg-f" readonly="readonly" >
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="buildName" class="control-label text-left col-xs-3">楼盘</label>
												<div class="col-xs-9">
													<c:set value="" var="buildName"></c:set>
													<c:forEach items="${buildList}" var="build">
															<c:if test="${build.bldId==order.bldId }">
											               	<c:set value="${build.name }" var="buildName"></c:set>
											          </c:if>
											</c:forEach>
													<input type="text" name="buildName" value="${buildName }" id="buildName" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">会员手机号</label>
												<div class="col-xs-9">
													<input type="text" name="orderNumber" value="${order.phone}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">订单编号</label>
												<div class="col-xs-9">
													<input type="text" name="orderNumber" value="${order.orderId}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">房屋编号</label>
												<div class="col-xs-9">
													<input type="text" name="houseId" value="${order.houseId}" id="houseId" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">订单冻结金额</label>
												<div class="col-xs-9">
													<input type="text" name="orderNumber" value="${order.freezeAmt}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">订单总金额</label>
												<div class="col-xs-9">
													<input type="text" name="orderNumber" value="${order.totalAmt}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">预订房间套数</label>
												<div class="col-xs-9">
													<input type="text" name="orderNumber" value="${order.totalRoomNum}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">订单时间</label>
												<div class="col-xs-9">
													<input type="text" name="orderNumber" value="${order.operTime}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">房源</label>
												<div class="col-xs-9">
													<input type="text" name="houseName" value="${order.houseName}" id="houseName" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">入住时间</label>
												<div class="col-xs-9">
													<input type="text" name="orderNumber" value="${order.beginTime}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">离店时间</label>
												<div class="col-xs-9">
													<input type="text" name="orderNumber" value="${order.endTime}" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="orderNumber" class="control-label text-left col-xs-3">状态</label>
												<div class="col-xs-9"><input type="hidden" name="pay_type" id="pay_type" value="${order.pay_type}"/> 
													<c:set var="status" value=""></c:set>
													<c:if test="${order.status=='10' }"><c:set var="status" value="待审核"></c:set></c:if> 
													<c:if test="${order.status=='11' }"><c:set var="status" value="等待客户入住"></c:set></c:if>
													<c:if test="${order.status=='12' }"><c:set var="status" value="客户在店"></c:set></c:if> 
													<c:if test="${order.status=='13' }"><c:set var="status" value="客户离店"></c:set></c:if>
													<c:if test="${order.status=='21' }"><c:set var="status" value="客户取消订单"></c:set></c:if>
												    <c:if test="${order.status=='22' }"><c:set var="status" value="商户取消订单"></c:set></c:if>
												    <c:if test="${order.status=='23' }"><c:set var="status" value="订单取消"></c:set></c:if>
													<input type="text" name="orderNumber" value="${status }" id="orderNumber" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											
											<c:if test="${ halist !=null }">
												<div class="form-group col-xs-12">
													<h2 class="pd-left-15">房源状况</h2>
												</div>
												<c:if test="${type==1 }">
													<div class="form-group col-xs-12 mg-left-0">
														<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
															<div class="table-responsive table-hover">
																<table class="table table-striped table-bordered">
																	<thead>
																		<tr>
																			<td>日期</td>
																			<td>空房间数</td>
																			<td>占用房间数</td>
																			<td>会员价</td>
																			<td>现价</td>
																			<td>修改价</td>
																			<td>修改原因</td>
																		</tr>
																	</thead>
																	<tbody>
																	<input type="hidden" name="size" id="size" value="${size}"/>
																		<c:forEach items="${halist}" var="ha" varStatus="status">
																			<tr>
																				<td><input type="hidden" name="data_${ status.index}" id="data_${ status.index}" value="${ha.date }">${ha.date }</td>
																				<td>${ha.emptyRoom }</td>
																				<td>1</td>
																				<td>
																				<input type="hidden" name="originalPrice_${ status.index}" id="originalPrice_${ status.index}" value="${ha.memTotalAmt }">
																				${ha.memTotalAmt}</td>
																				<td>${ha.memTotalAmt}</td>
																				<td><input type="text" name="memTotalAmt_${ status.index}" id="memTotalAmt_${ status.index}"></td>
																				<td><input type="text" name="update_resson_${ status.index}" id="update_resson_${ status.index}"></td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
													
												</c:if>
												<c:if test="${type==2 || type==3}">
													<div class="form-group col-xs-12 mg-left-0">
														<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
															<div class="table-responsive table-hover">
																<table class="table table-striped table-bordered">
																	<thead>
																		<tr>
																			<td>日期</td>
																			<td>会员价</td>
																			<td>现价</td>
																			<td>修改原因</td>
																		</tr>
																	</thead>
																	<tbody>
																	<input type="hidden" name="size" id="size" value="${size}"/>
																		<c:forEach items="${halist}" var="ha" varStatus="status">
																			<tr>
																				<td><input type="hidden" name="data_${ status.index}" id="data_${ status.index}" value="${ha.date }">${ha.date }</td>
																				<td>
																				${ha.originalPrice}</td>
																				<td>${ha.presentPrice}</td>
																				<td>${ha.update_resson}</td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</c:if>
												
												<c:if test="${type==2 || type==1}">
													<div class="form-group col-xs-6">
														<label for="orderNumber" class="control-label text-left col-xs-3">取消原因：</label>
														
															<input type="text" name="cancelOrdeReason" id="cancelOrdeReason" maxlength="128" />
														
												    </div>
											    </c:if>
											    <c:if test="${type==3 }">
													<div class="form-group col-xs-6">
														<label for="orderNumber" class="control-label text-left col-xs-3">取消原因：${order.cancelOrdeReason}</label>
												    </div>
											    </c:if>
												<!-- <textarea rows="3" cols="20">-->
											</c:if>
											
											
											
											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
											  <c:if test="${type==1 }">
											    <input type="button" class="btn btn-style" id="updatePrice"  value="修改价格" />
												<input type="button" class="btn btn-style" id="cancle"  value="取消订单" />
												<input type="button" class="btn btn-style" id="confirm" value="审核通过，等待客户入住" />
												<input type="button" class="btn btn-style" onclick="history.go(-1)" value="返回" />
											  </c:if>
											  <c:if test="${type==2 }">
											    <input type="button" class="btn btn-style" id="cancle"  value="取消订单" />
											    <input type="button" class="btn btn-style" onclick="history.go(-1)" value="返回" />
											  </c:if>
											  <c:if test="${type==3 }">
											    <input type="button" class="btn btn-style" onclick="history.go(-1)" value="返回" />
											  </c:if>	
											</div>

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
		</c:forEach>
	</form>
	</body>
<%@include file="/page/common/foot.jsp" %>
		
	<script>
	$(function() {
		$("#fanhui").click(function() {
			history.go(-1);
		});
		
		//修改价格
		$("#updatePrice").click(
				function() {
					var size = $("#size").val();
					var jsonArray = '';
					var isNull = 0;
					var user_phone = $("#user_phone").val();
					var realInSeasonDays = $("#realInSeasonDays").val();
					for(var i=0;i<size;i++){
						var presentPrice = $('#memTotalAmt_'+i).val();//修改价格
						var update_resson =  $('#update_resson_'+i).val();//修改原因 
						var data = $('#data_'+i).val();//日期
						var originalPrice = $('#originalPrice_'+i).val();//原价
						if(isNaN(presentPrice))
						{
						   alert('价格必须为数字！');
						   return;
						}
						if(presentPrice!=null && presentPrice!=undefined && presentPrice!=''){
							if(update_resson==null || update_resson==undefined || update_resson==''){
								alert("请输入取消订单原因！");
							    return;
							}else{
								if(parseInt(presentPrice)!=presentPrice){
									alert('修改价格必须为整数！');
									return;
								}
							}
							isNull = 1;
						}
						
						if(i!=size-1){
							jsonArray += "{\"data\":\""+data+"\",\"originalPrice\":\""+originalPrice+"\",\"presentPrice\":\""+presentPrice+"\",\"update_resson\":\""+update_resson+"\"};";
						}else{
							jsonArray += "{\"data\":\""+data+"\",\"originalPrice\":\""+originalPrice+"\",\"presentPrice\":\""+presentPrice+"\",\"update_resson\":\""+update_resson+"\"}";
						}
					}
					
					if(isNull==0){
						alert("请输入取消订单原因和修改价！");
					    return;
					}
					myAlert({title:'修改价格提示',content:'确定要修改价格吗?',okfun:function(){
						var orderId = $('#orderId').val();
						$("#mainForm").attr("action", "${pageContext.request.contextPath }/order/updateOrderLiveDetail.shtml?orderId="+ orderId+
								"&houseName="+houseName+"&status=15&jsonArray="+jsonArray+"&user_phone="+user_phone+"&realInSeasonDays="+realInSeasonDays);
						$("#mainForm").submit();
						}
					});
				});
		
		$("#cancle").click(
				function() {
					var cancelOrdeReason = $("#cancelOrdeReason").val();
					if(cancelOrdeReason==null || cancelOrdeReason==undefined || cancelOrdeReason==''){
					    alert("请输入取消订单原因！");
					    return;
					}
					var orderPhone=$("#buildPhone").val();
					var user_phone = $("#user_phone").val();
					myAlert({title:'取消订单提示',content:'确定要取消订单吗?',okfun:function(){
							var orderId = $('#orderId').val();
							$("#mainForm").attr("action", "${pageContext.request.contextPath }/order/audtingInfo.shtml?orderId="+ orderId + "&status=23&cancelOrdeReason="+cancelOrdeReason+"&orderPhone="+orderPhone+"&user_phone="+user_phone);
							$("#mainForm").submit();
						}
					});
				});

		$("#confirm").click(
				function() {
					myAlert({title:'订单审核确认提示',content:'订单已审核通过?',okfun:function(){
						var orderId = $('#orderId').val();
						var orderPhone=$("#orderNumber").val();
						var pay_type = $("#pay_type").val();
						var user_phone = $("#user_phone").val();
						/*if("offlinepay"==pay_type){
							$("#mainForm").attr("action","${pageContext.request.contextPath }/order/audtingInfo.shtml?orderId=" + orderId + "&status=11&orderPhone="+orderPhone+"&pay_type="+pay_type+"&user_phone="+user_phone);
						}else if("wxpay"==pay_type){*/
							$("#mainForm").attr("action","${pageContext.request.contextPath }/order/audtingInfo.shtml?orderId=" + orderId + "&status=9&orderPhone="+orderPhone+"&pay_type="+pay_type+"&user_phone="+user_phone);
						//}
						
						$("#mainForm").submit();
					}});
				});
		});
	
	function updatePrice(index,date) {
		var presentPrice = $('#memTotalAmt_'+index).val();//修改价格
		var update_resson =  $('#update_resson_'+index).val();//修改原因
		if(''==presentPrice || null == presentPrice || ''==update_resson || null == update_resson){
			alert('修改价格和原因不能为空！');
			return;
		}
		var houseName = $('#houseName').val();
		myAlert({title:'修改价格提示',content:'确定要修改价格吗?',okfun:function(){
			var orderId = $('#orderId').val();
			$("#mainForm").attr("action", "${pageContext.request.contextPath }/order/updateOrderLiveDetail.shtml?orderId="+ orderId+"&presentPrice="+presentPrice+"&index="+index+
					"&houseName="+houseName+"&status=15&update_resson="+update_resson);
			$("#mainForm").submit();
		  }
	   });
	}
	
	</script>

</html>