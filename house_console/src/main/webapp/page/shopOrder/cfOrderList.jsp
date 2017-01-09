<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>刷单明细</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/shopOrder/toCFOrderList.shtml" method="post">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="5" name="menu"/>
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
									<h2 class="text-uppercase">刷单明细</h2></div>

								<div class="row query">
									<div class="col-xs-12">
										<div class="col-xs-11">
											订单编号：<input type="text" id="orderId" name="orderId" value="${orderId}" />&nbsp;&nbsp;&nbsp;&nbsp;
											店铺名称：<input type="text" id="shopName" name="shopName" value="${shopName}" />&nbsp;&nbsp;&nbsp;&nbsp;
											订单时间：<input type="text" id="startTime" name="startTime" value="${startTime }" onFocus="WdatePicker({maxDate:new Date(),skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'});"/> 至
											<input type="text" id="enTime" name="enTime" value="${enTime }" onFocus="WdatePicker({maxDate:new Date(),skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'});"/>&nbsp;&nbsp;&nbsp;&nbsp;
										  状态：<select id="status" name="status">
										  		<option value="">全部</option>
										        <option value="9" <c:if test="${status == 9 }">selected="selected"</c:if>>待支付</option>
										        <option value="11" <c:if test="${status == 11 }">selected="selected"</c:if>>已支付</option>
										     </select>
										</div>
										<div class="col-xs-1">	
											<input type="submit" value="查  询" class="btn  btn-style"/>
										</div>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>订单编号</td>
												<td>客栈名称</td>
												<td width="110px">订单原始金额</td>
												<td width="100px">优惠金额</td>
												<td width="110px">待/实际支付</td>
												<!-- <td>支付方式</td> -->
												<td>联系方式</td>
												<td>联系人</td>
												<td width="70px;">状态</td>
												<td width="180px">订单时间</td>
												<td>操作</td>
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
												  	<td id="td_${order.orderId }">
												  	    <c:if test="${order.status == 9 }">待支付</c:if>
												  	    <c:if test="${order.status == 11 }">已支付</c:if>
												  	</td>
												  	<%-- <td>${order.payType }</td> --%>
												  	<td>${order.operTime }</td>
												  	<td  id="td_settleStatus_${order.orderId }">
												  		<c:if test="${order.status == 9 }">
												  			<input type="button" value="检  查" class="btn  btn-style" onclick="checkOrder(${order.orderId },'${order.uid }')"/>
												  		</c:if>
												  		<c:if test="${order.status == 11 }">
												  			<c:choose>
												  				<c:when test="${order.settleStatus == 0 }">
												  					<input type="button" value=" 结 算 " onclick="settleOrder(${order.orderId })"/>
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

		</div>
		<!-- /#wrapper -->
	</form>
	</body>
		<%@include file="/page/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script src="${pageContext.request.contextPath }/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function checkOrder(orderId,uid) {
	var trade_type = "JSAPI";
	var productCode = "wxpay";
	$.ajax({
		url:"${pageContext.request.contextPath }/shopOrder/checkOrder.shtml",
		type:'post',
		async: false,
		data:{"orderId":orderId,"uid":uid,"productCode":productCode,"trade_type":trade_type} ,
		success:function(data) {
			if(data.success){
				if (data.code == "7") {
					alert("未支付订单!");
					return;
				}
				if (data.code == "0") {
					$("#td_" + orderId).html("已支付");
					alert("成功检查!");
					return;
				}
			} else {
				alert("操作失败!");
			}
		}
	}); 
}

//订单结算
function settleOrder(orderId) {
	if(confirm("确认是否进行结算！")) {
		$.ajax({
			url:"${pageContext.request.contextPath }/shopOrder/settleOrder.shtml",
			type:'post',
			async: false,
			data:{"orderId":orderId} ,
			success:function(data) {
				if(data.success){
					alert("结算成功!");
					$("#td_settleStatus_" + orderId).html("已结算");
				} else {
					alert("操作失败!");
				}
				
			}
		}); 
	}
} 

/* function settle(okfun) {
	var d = dialog({
		title: '提示',
		content: '确认是否进行结算！',
		okValue: '确定',
		ok: function() {
			this.title('提交中…');
			okfun();
			return false;
		},
		cancelValue: '返回',
		cancel: function() {}
	});
	d.width(250);
	d.focus();
	d.showModal();
} */
</script>
</html>