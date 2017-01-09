<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!--住宿管理 订单信息-->
		<title>订单信息</title>
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

							<div class="col-xs-12 ">
								<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

									<div class="panel-heading templatemo-position-relative">
										<h2 class="text-uppercase">订单信息</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" class="form-horizontal" method="post">
										<c:forEach items="${orderList}" var="order">
										<input type="hidden" id="orderId" name="orderId" value="${order.orderId }"/>
											<div class="form-group col-xs-6">
												<label for="houses" class="control-label text-left col-xs-3">楼盘</label>
												<div class="col-xs-9">
													<c:set value="" var="buildName"></c:set>
													<c:forEach items="${buildList}" var="build">
											              <c:if test="${build.bldId==order.bldId }">
												               <c:set value="${build.name }" var="buildName"></c:set>
												          </c:if>						    
												     </c:forEach>
													<input type="text" name="houses" value="${buildName}" id="" class="form-control  border-0 bg-f"  disabled="disabled" >
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="city" class="control-label text-left col-xs-3">城市</label>
												<div class="col-xs-9">
													<c:set value="" var="cityName"></c:set>
													<c:forEach items="${cityList}" var="city">
											              <c:if test="${order.cityId==city.cityId }">
												               <c:set value="${city.cityName }" var="cityName"></c:set>	
												          </c:if>						    
												     </c:forEach>
													<input type="text"  name="city" value="${cityName}" class="form-control border-0 bg-f"  disabled="disabled">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="housesRes" class="control-label text-left col-xs-3">房源</label>
												<div class="col-xs-9">
													<input type="text" name="housesRes" value="${order.houseName}" id="housesRes" class="form-control  border-0 bg-f" disabled="disabled">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="checkInDate" class="control-label text-left col-xs-3">入住时间</label>
												<div class="col-xs-9">
													<input type="text" name="checkInDate" value="${order.beginTime}" id="checkInDate" class="form-control  border-0 bg-f"  disabled="disabled">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="checkOutDate" class="control-label text-left col-xs-3">离店时间</label>
												<div class="col-xs-9">
													<input type="text" name="checkOutDate" value="${order.endTime}" id="checkOutDate" class="form-control  border-0 bg-f"  disabled="disabled">
												</div>
											</div>
											
											<c:if test="${flag=='cancel'}">
												<div class="form-group col-xs-6">
													<label for="orderId" class="control-label text-left col-xs-3">订单编号</label>
													<div class="col-xs-9">
														<input type="text" name="orderId" value="${order.orderId}"  class="form-control  border-0 bg-f" readonly="readonly">
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="userName" class="control-label text-left col-xs-3">预订人</label>
													<div class="col-xs-9">
														<input type="text" name="userName" value="${order.userName }"  class="form-control  border-0 bg-f" readonly="readonly">
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="phone" class="control-label text-left col-xs-3">会员手机号</label>
													<div class="col-xs-9">
														<input type="text" name="phone" value="${order.phone}"  class="form-control  border-0 bg-f" readonly="readonly">
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="beginTime" class="control-label text-left col-xs-3">预计入住</label>
													<div class="col-xs-9">
														<input type="text" name="beginTime" value="${order.beginTime}"  class="form-control  border-0 bg-f" readonly="readonly">
													</div>
												</div>
											</c:if>
													
											<c:if test="${flag=='checkin'}">		
											<div class="form-group col-xs-6">
												<label for="orderId" class="control-label text-left col-xs-3">订单编号</label>
												<div class="col-xs-9">
													<input type="text" name="orderId" value="${order.orderId}"  class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="roomType" class="control-label text-left col-xs-3">房型</label>
												<div class="col-xs-9">
													<c:set var="houseType" value=""></c:set>
													<c:choose>
														<c:when test="${order.houseType == 1 }"><c:set var="houseType" value="别墅"></c:set></c:when>
														<c:when test="${order.houseType == 2 }"><c:set var="houseType" value="高档公寓"></c:set></c:when>
														<c:when test="${order.houseType == 3 }"><c:set var="houseType" value="酒店公寓"></c:set></c:when>
													</c:choose>
													<input type="text" name="roomType" value="${houseType }" id="roomType" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="userName" class="control-label text-left col-xs-3">姓名</label>
												<div class="col-xs-9">
													<input type="text" name="userName" value="${order.userName }"  class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="phone" class="control-label text-left col-xs-3">联系方式</label>
												<div class="col-xs-9">
													<input type="text" name="phone" id="phone" value="${order.phone}"  class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											
											
											<div class="form-group col-xs-6">
												<label for="beginTime" class="control-label text-left col-xs-3">预计入住</label>
												<div class="col-xs-9">
													<input type="text" name="beginTime" value="${order.beginTime}"  class="form-control  border-0 bg-f"  disabled="disabled">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="verifyCode" class="control-label text-left col-xs-3">验证码</label>
												<div class="col-xs-9">
													<input type="text" name="verifyCode" value="" id="verifyCode" class="form-control ">
												</div>
											</div>
											</c:if>
											
											<c:if test="${flag=='prolong'}">
												<div class="form-group col-xs-6">
													<label for="orderId" class="control-label text-left col-xs-3">订单编号</label>
													<div class="col-xs-9">
														<input type="text" name="orderId" value="${order.orderId}"  class="form-control ">
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="userName" class="control-label text-left col-xs-3">姓名</label>
													<div class="col-xs-9">
														<input type="text" name="userName" value="${order.userName }"  class="form-control ">
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="phone" class="control-label text-left col-xs-3">联系方式</label>
													<div class="col-xs-9">
														<input type="text" name="phone" value="${order.phone }"  class="form-control ">
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="phone" class="control-label text-left col-xs-3">续房天数</label>
													<div class="col-xs-9">
														<select  name="date" id="selectDay" class="form-control " >
															<c:forEach items="${dateList}" var="item" varStatus="statu">
																<option	value="${item}">${statu.index}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="phone" class="control-label text-left col-xs-3">新的离店日期</label>
													<div class="col-xs-9">
														<input type="text" value=""  id="newEndTime"  class="form-control " readonly="true" />
													</div>
												</div>
											</c:if>
											
											<c:if test="${flag=='checkout'}">
												<div class="form-group col-xs-6">
													<label for="orderId" class="control-label text-left col-xs-3">订单编号</label>
													<div class="col-xs-9">
														<input type="text" value="${order.orderId}"  id=""  class="form-control " readonly="true" />
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="" class="control-label text-left col-xs-3">姓名</label>
													<div class="col-xs-9">
														<input type="text" value="${order.userName }"    class="form-control " readonly="true" />
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="" class="control-label text-left col-xs-3">联系方式</label>
													<div class="col-xs-9">
														<input type="text" value="${order.phone}"   class="form-control " readonly="true" />
													</div>
												</div>
											</c:if>
											
											<c:if test="${flag=='refund'}">
												<div class="form-group col-xs-6">
													<label for="" class="control-label text-left col-xs-3">订单编号</label>
													<div class="col-xs-9">
														<input type="text" value="${order.orderId}"   class="form-control " readonly="true" />
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="" class="control-label text-left col-xs-3">姓名</label>
													<div class="col-xs-9">
														<input type="text" value="${order.userName }"   class="form-control " readonly="true" />
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="" class="control-label text-left col-xs-3">联系方式</label>
													<div class="col-xs-9">
														<input type="text" value="${order.phone}"   class="form-control " readonly="true" />
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="" class="control-label text-left col-xs-3">退房原因</label>
													<div class="col-xs-9">
														<input type="text" name="reason" value=""   class="form-control " />
													</div>
												</div>
												<div class="form-group col-xs-6">
													<label for="" class="control-label text-left col-xs-3">退还金额</label>
													<div class="col-xs-9">
														<input type="text" value="0"  name="amt"  class="form-control "  />
													</div>
												</div>
											</c:if>
											
											
											<div class="form-group col-xs-6">
												<label for="status" class="control-label text-left col-xs-3">订单状态</label>
												<div class="col-xs-9">
													  <c:set var="status" value=""></c:set>
													  <c:if test="${order.status=='10' }"><c:set var="status" value="等待客服确认"></c:set></c:if>
										              <c:if test="${order.status=='11' }"><c:set var="status" value="等待客户入住"></c:set></c:if>
										              <c:if test="${order.status=='12' }"><c:set var="status" value="客户在店"></c:set></c:if>
										              <c:if test="${order.status=='13' }"><c:set var="status" value="客户离店"></c:set></c:if>
										              <c:if test="${order.status=='21' }"><c:set var="status" value="客户取消订单"></c:set></c:if>
										              <c:if test="${order.status=='22' }"><c:set var="status" value="商户取消订单"></c:set></c:if>
										              <c:if test="${order.status=='23' }"><c:set var="status" value="订单已退款"></c:set></c:if>
													<input type="text" name="status" value="${status}" id="status" class="form-control  border-0 bg-f" readonly="readonly">
												</div>
											</div>
											
											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
													<c:if test="${flag=='cancel'}">
														<input type="button" class="btn btn-style" value="取消入住" id="cancel" />
														<input type="button" class="btn btn-style" value="返回" id="fanhui" />
													</c:if>
													
													<c:if test="${flag=='checkin'}">
														<input type="button" class="btn btn-style" value="确认入住" id="checkin" />
														<input type="button" class="btn btn-style" value="返回" id="fanhui" />
													</c:if>
													
													<c:if test="${flag=='prolong'}">
														<input type="button" class="btn btn-style" value="确认续房" id="prolong" />
														<input type="button" class="btn btn-style" value="返回" id="fanhui" />
													</c:if>
													
													<c:if test="${flag=='checkout'}">
														<input type="button" class="btn btn-style" value="确认离店" id="checkout" />
														<input type="button" class="btn btn-style" value="返回" id="fanhui" />
													</c:if>
													
													<c:if test="${flag=='refund'}">
														<input type="button" class="btn btn-style" value="确认退款" id="refund" />
														<input type="button" class="btn btn-style" value="返回" id="fanhui" />
												</c:if>
												<input type="hidden" name="houseId" id="houseId" value="${order.houseId }"/>
											</div>
											
										</c:forEach>
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
				wrapper: "li"/* ,
				submitHandler:function(form){
					 if($(form).valid()){
	                       
	                    }else{
	                        return false;
	                    }
				} */
			});
			
		});
		
		
		//为商户/企业角色时默认显示div
		$(function(){
			$("#fanhui").click(function(){
				history.go(-1);
			});
			
			$("#cancel").click(function(){
				myAlert({title:'取消订单提示',content:'商户确定取消订单吗 ?',okfun:function(){
					var orderId=$('#orderId').val();
					$("#mainForm").attr("action", "${pageContext.request.contextPath }/order/cancel.shtml?orderId="+orderId+"&status=23");
					$("#mainForm").submit();
				}});
			});
			
			$("#checkin").click(function(){
				var orderId=$('#orderId').val();
				var houseId=$('#houseId').val();
				var verifyCode=$('#verifyCode').val();
				var phone = $('#phone').val();
				if(verifyCode==''){
					myWarning('请输入验证码');
					return;
				}
				myAlert({title:'客户入住提示',content:'确定客户入住吗?',okfun:function(){
					$("#mainForm").attr("action", "${pageContext.request.contextPath }/order/checkin.shtml?orderId="+orderId+"&status=12&houseId="+houseId+"&orderPhone="+phone);
					$("#mainForm").submit();
				}});
			});
			
			$("#prolong").click(function(){
				myAlert({title:'客户续房提示',content:'确定客户续房吗?',okfun:function(){
					var orderId=$('#orderId').val();
					$("#mainForm").attr("action", "${pageContext.request.contextPath }/order/prolong.shtml?orderId="+orderId+"&status=12");
					$("#mainForm").submit();
				}});
			});
			
			$("#checkout").click(function(){
				var phone = $("#phone").val();
				myAlert({title:'客户离店提示',content:'确定客户离店吗?',okfun:function(){
					var orderId=$('#orderId').val();
					$("#mainForm").attr("action", "${pageContext.request.contextPath }/order/checkout.shtml?orderId="+orderId+"&status=13&orderPhone="+phone);
					$("#mainForm").submit();
				}});
			});
			
			$("#selectDay").change(function()
					{
						$("#newEndTime").val($("#selectDay").val());
					});
			});
		
	</script>

</html>