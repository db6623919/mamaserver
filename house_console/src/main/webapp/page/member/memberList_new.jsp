<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!--订单管理-->
		<title>订单管理</title>
	</head>
	<body>
		<form id="mainForm" method="post" action="${pageContext.request.contextPath }/member/toMemberList.shtml">
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
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">订单管理</h2></div>

								<div class="row query form-horizontal">
										<div class="col-xs-10">
											<div class="input-group">
												<span class="input-group-addon" id="basic-addon1">手机号码</span> 
												<input type="text" class="form-control search-input" id="phone" value="" name="phone"  aria-describedby="basic-addon1">
											</div>
										</div>
	
										<div class="col-xs-2 right">
											<button type="submit" class="btn  btn-style">搜索</button>
										</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered " >
										<thead>
											<tr>
												<td>昵称</td>
												<td>姓名</td>
												<td>身份证号码</td>
												<td>手机号码</td>
												<td>余额</td>
												<td>冻结金额</td>
												<td>充值金额</td>
												<td>奖励金额</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${pager.list != null }">
									      		<c:forEach items="${pager.list}" var="member">
										        	<tr>
											          <td class="col-xs-1 text-center">${member.nickName }</td>
											          <td class="col-xs-1">${member.name }</td>
											          <td class="col-xs-1">${member.idCard}</td>
											          <td class="col-xs-1">${member.phone}</td>
											          <td class="col-xs-1">${member.availAmt}</td>
											          <td class="col-xs-1">${member.freezeAmt}</td>
											          <td class="col-xs-1">${member.totalRechargeAmt}</td>
											          <td class="col-xs-1">${member.totalRewardAmt}</td>
											          <td class="">
											           <input   class="btn btn-style btn-style-size" type="button"  onclick="cardInfo('${member.uid }');" value="会员卡信息" />
											           <input   class="btn btn-style btn-style-size" type="button"  onclick="collectList('${member.uid }');" value="收藏记录" />
											          <%--  <input   class="btn btn-style btn-style-size" type="button"  onclick="recharge('${member.uid }','${member.name }','${member.phone}');" value="充值" />
											           <input   class="btn btn-style btn-style-size" type="button"  onclick="flowList('${member.uid }',100);" value="充值记录" />
											           <input   class="btn btn-style btn-style-size" type="button"  onclick="flowList('${member.uid }',200);" value="奖励记录" />
											           <input   class="btn btn-style btn-style-size" type="button"  onclick="flowList('${member.uid }',300);" value="消费记录" /> --%>
											           <input   class="btn btn-style btn-style-size" type="button"  onclick="orderList('${member.uid }');" value="订单列表" />
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
<script>
$(function(){
	$("#serchButton").click(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/member/toMemberList.shtml?phone="+$('#phone').val()+"&name="+$('#name').val());
		$("#mainForm").submit();
	});
});
function cardInfo(uid){
	var phone = $("#phone").val();
	window.location='${pageContext.request.contextPath }/member/toCardInfo.shtml.shtml?id='+uid+"&phone="+phone;
}

function collectList(uid){
	var phone = $("#phone").val();
	window.location='${pageContext.request.contextPath }/member/toCollectList.shtml.shtml?id='+uid+"&phone="+phone;
}
function recharge(uid,name,phone){	
	window.location='${pageContext.request.contextPath }/member/toRechargePage.shtml.shtml?uid='+uid+"&phone="+phone+"&name="+name;
}
function flowList(uid,type){
	var phone = $("#phone").val();
	window.location='${pageContext.request.contextPath }/member/toFlowList.shtml.shtml?id='+uid+"&phone="+phone+"&type="+type;
}
function orderList(uid){
	var phone = $("#phone").val();
	window.location='${pageContext.request.contextPath }/member/toOrderList.shtml.shtml?id='+uid+"&phone="+phone;
}
</script>	

</html>