<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>房源店铺</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/topic/shopList.shtml" method="post">
			<!-- Navigation -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="container-fluid">
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
								<input type="hidden" name="topicId" id="topicId" value="${topicId }">
								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">房源店铺</h2></div>
								<div class="row query">
									<div class="col-xs-4">
										客栈名称：<input type="text" id="shopName" name="shopName" value="${shopName}" />
									</div>
									<div class="col-xs-4">
										所属城市：
										<select id="cityName" name="cityName">
										  <option value="">--全部--</option>
										  <c:forEach items="${cityList}" var="city">
											  <option value="${city.cityName}" <c:if test="${city.cityName==cityName}">selected="selected"</c:if>>${city.cityName }</option>
										  </c:forEach>
										</select>
										
										<!--<input type="text" id="cityName" name="cityName" value="${cityName}"/>-->
									</div>
									<div class="col-xs-3">
										客栈类型：
										<select id="type" name="type">
										  <option value="">--全部--</option>
										  <option value="1" <c:if test="${type==1}">selected="selected"</c:if>>客栈</option>
										  <option value="2" <c:if test="${type==2}">selected="selected"</c:if>>酒店</option>
										  <option value="3" <c:if test="${type==3}">selected="selected"</c:if>>公寓</option>
										</select>
									</div>
									<div class="col-xs-1">	
										<input type="submit" value="查  询"/>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td><input type="checkbox" disabled="disabled"/></td>
												<td>店铺名</td>
												<td style="width:40%">简介</td>
												<td>城市</td>
												<td>级别</td>
												<td>类型</td>
												<td>boss姓名</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="houseshop">
									        	<tr>
												  <td>
											  	   <input type="checkbox" onclick="editTopicShop(this,${houseshop.id },${topicId })" <c:if test="${houseshop.checkEd == 1 }">checked="checked"</c:if> />
												  </td>
												  <td>${houseshop.shopName }</td>
												  <td>${houseshop.shopDesc }</td>
												  <td>${houseshop.cityName }</td>
												  <td>${houseshop.level }</td>
												  <td>${houseshop.typeName }</td>
												  <td>${houseshop.bossName }</td>
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
<script>
//是否关联客栈
function editTopicShop(obj,shopId,topicId) {
	//获取主题关联客栈数
	var num = window.opener.document.getElementById("td_" + topicId).innerHTML;
	var flag = "del";
	if (obj.checked) {
		flag = "add";
		num = parseInt(num) + 1; //计算主题关联客栈--增加
	} else {
		num = parseInt(num) - 1; //计算主题关联客栈--删除
	}
	$.ajax({
		url:"${pageContext.request.contextPath }/topic/addOrDelTopicShop.shtml",
		type:'post',
		async: false,
		data:{"topicId":topicId,"shopId":shopId,"flag":flag} ,
		success:function(data) {
			if(data.code == 0){
				if (obj.checked) {
					alert("已勾选");
				} else {
					alert("已取消");
				}
				window.opener.document.getElementById("td_" + topicId).innerHTML = num;
			} else {
				alert("操作失败!");
			}
		}
	}); 
}



</script>
</html>