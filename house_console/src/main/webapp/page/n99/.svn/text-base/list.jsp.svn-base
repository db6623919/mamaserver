<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>兑换管理</title>
		<style>
			#modal {
				top: 50%;
				left: 50%;
				margin-left: -50px;
				margin-top: -25px;
				position: fixed;
				height: 50px;
				width: 100px;
				background-color: rgba(242, 242, 242, 1);
			}
			#mask {
				position: absolute;
				top:0;
				left:0;
				right:0;
				bottom:0;
				background-color:rgba(100,100,100,0.6)
			}
			#herd {
				
				margin-top:10px;
				margin-left: 10px;
				font-size: 20px;
			}
		</style>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/ticket/toExchange.shtml" method="post">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="12" name="index"/>
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
									<h2 class="text-uppercase">房券兑换</h2></div>

								<div class="row query">
									<div class="col-xs-11">
										房券编号：<input type="text" id="cardNo" name="cardNo" value="${cardNo}" />&nbsp;&nbsp;&nbsp;&nbsp;
										购买人联系电话：<input type="text" id="buyPhone" name="buyPhone" value="${buyPhone}" />
									</div>
									<div class="col-xs-1">	
											<input type="submit" value="查  询" class="btn  btn-style"/>
										</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>房券编号</td>
												<td>订单编号</td>
												<td>房券价格</td>
												<td>房源名称</td>
												<td>店铺名称</td>
												<td>客栈掌柜</td>
												<td>掌柜电话</td>
												<td>购买人联系电话</td>
												<td>购买时间</td>
												<td>兑换人</td>
												<td>兑换人联系方式</td>
												<td>兑换时间</td>
												<td>是否已兑换</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="houseCard">
									      			<tr>
									        		<td>${houseCard.cardNo }</td>
									        		<td>${houseCard.orderNo }</td>
									        		<td>${houseCard.cardPrice }</td>
									        		<td>${houseCard.houseName }</td>
									        		<td>${houseCard.shopName }</td>
									        		<td>${houseCard.bossName }</td>
									        		<td>${houseCard.bossPhone }</td>
									        		<td>${houseCard.buyPhone }</td>
									        		<td>${houseCard.buyTime }</td>
									        		<td>${houseCard.exchangeName }</td>
									        		<td>${houseCard.exchangePhoneNo }</td>
									        		<td>${houseCard.exchangeTime }</td>
									        		<td>
									        			<c:if test="${houseCard.useStatus == 0 }">未兑换</c:if>
									        			<c:if test="${houseCard.useStatus == 1 }">已兑换</c:if>
									        		</td>
									        		<td>
									        			<c:if test="${houseCard.useStatus == 0 }">
									        			   <a href="javascript:doExchange(${houseCard.id },'${houseCard.cardNo }')" class="templatemo-edit-btn" title="兑换">兑换</a>
									        			</c:if>
									        			<c:if test="${houseCard.useStatus == 1 }">--</c:if>
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
	
<div >

</div>	

	<div id="mask" style="display:none;">
		<input type="hidden" id ="houseCardId" />
		<input type="hidden" id ="houseCardNo" />
		<div id="modal" style="height: 270px;width: 500px;">
			<div id="herd" ><b>房券兑换</b></div>
			<hr style="border : 1px dashed blue;" />
			<div >
			<div class="form-group col-xs-12">
				<label for="cityName" class="col-xs-3 control-label">兑换人:</label>
				<div class="col-xs-9">
					<input type="text" required name="exchangeName" id="exchangeName" class="form-control" style="width: 200px">
				</div>
			</div>
			<div class="form-group col-xs-12">
				<label for="cityName" class="col-xs-3 control-label">联系方式:</label>
				<div class="col-xs-9">
					<input type="text" required name="exchangePhoneNo" id="exchangePhoneNo" class="form-control" style="width: 200px" maxlength="11">
				</div>
			</div>
			<div class="form-group col-xs-6 text-right">
				<input type="button" class="btn  btn-style" value="提交" onclick="submitExchange()"/>
				<input type="button" class="btn btn-style" value="关闭" onclick="closeDiv()"/>
			</div>
		</div>
			
		</div>
	</div>
</body>
<%@include file="/page/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script type="text/javascript">
function closeDiv() {
	$("#exchangeName").val("");
	$("#exchangePhoneNo").val("");
	$("#houseCardId").val("");
	$("#houseCardNo").val("");
	$("#mask").hide();
} 

function doExchange(houseCardId,cardNo) {
	$("#houseCardId").val(houseCardId);
	$("#houseCardNo").val(cardNo);
	$("#mask").show();
}

function submitExchange() {
	var exchangeName = $("#exchangeName").val();
	var exchangePhoneNo = $("#exchangePhoneNo").val();
	var houseCardId = $("#houseCardId").val();
	var cardNo = $("#houseCardNo").val();
	if (exchangeName == "") {
		alert("请输入兑换人!");
		return;
	}
	if (exchangePhoneNo == "") {
		alert("请输入联系方式!");
		return;
	}
	
	$.ajax({
		url:"${pageContext.request.contextPath }/ticket/doExchange.shtml",
		type:'post',
		async: false,
		data:{"exchangeName":exchangeName,"exchangePhoneNo":exchangePhoneNo,"id":houseCardId,"cardNo":cardNo} ,
		success:function(data) {
			if(data.success == true){
				location.reload();
			} else {
				alert("操作失败!");
			}
		}
	}); 
	
}

</script>
</html>