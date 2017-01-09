<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>房源店铺管理</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/houseshop/list.shtml" method="post">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="10" name="index"/>
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
									<h2 class="text-uppercase">房源店铺管理</h2></div>

								<div class="row query">
									<div class="col-xs-12">
										<button type="button" class="btn  btn-style" id="addButton">新增</button>
										<button type="button" class="btn  btn-style" id="syncShopToMango">同步mangodb数据</button>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>店铺名</td>
												<td style="width:40%">简介</td>
												<td>城市</td>
												<td>级别</td>
												<td>类型</td>
												<td>合作关系</td>
												<td>boss姓名</td>
												<td>电话</td>
												<td>微信</td>
												<td>创建时间</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="houseshop">
									        	<tr>
												  <td>${houseshop.shopName }</td>
												  <td>${houseshop.shopDesc }</td>
												  <td>${houseshop.cityName }</td>
												  <td>${houseshop.level }</td>
												  <td>${houseshop.typeName }</td>
												  <td>
												 	<c:if test="${houseshop.partnership == 0}">会员客栈</c:if>
												  	<c:if test="${houseshop.partnership == 1}">控股客栈</c:if>
												  	<c:if test="${houseshop.partnership == 2}">深度合作</c:if>
												  	<c:if test="${houseshop.partnership == 3}">民宿贷客栈</c:if>
												  	<c:if test="${houseShop.partnership == 4}">联合运营客栈</c:if>
												  </td>
												  <td>${houseshop.bossName }</td>
												  <td>${houseshop.bossPhone }</td>
												  <td>${houseshop.bossWeChat }</td>
												  <td>${houseshop.createTime }</td>
									              <td>
									                <a href="javascript:editTag('${houseshop.id }')" class="templatemo-edit-btn" title="查看">查看</a>
									              	<a href="javascript:modifTag('${houseshop.id }')" class="templatemo-edit-btn" title="修改">修改</a>
									              	<a href="javascript:deleteTag('${houseshop.id }','${houseshop.shopName }')" class="templatemo-edit-btn" title="删除">删除</a>
									              	<a href="javascript:modifyCFHouseShop('${houseshop.id }')" class="templatemo-edit-btn" title="修改优惠">修改优惠</a>
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
		$("#mainForm").attr("action","${pageContext.request.contextPath }/city/toCity.shtml").val();
		$("#mainForm").submit();
	});
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/houseshop/to_addHouseShop.shtml';
	});
	
	$("#syncShopToMango").click(function(){
        $.ajax({
         	url:'${pageContext.request.contextPath }/houseshop/syncShopToMango.shtml',
         	data:'',
         	type:'post',success:function(data){
         		var code = data.result.code;
         		var msg = data.result.msg;
         		if(code==0){
         			alert("数据同步成功！");
         		}else{
         			alert(msg);
         		}
         		
         	}
         });
        
	});
	
});

function deleteTag(id,shopName){
	del(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/houseshop/addHouseShop.shtml?id="+id+"&flag=del&shopName="+shopName);
		$("#mainForm").submit();
	});
}

function editTag(id){
	window.location='${pageContext.request.contextPath }/houseshop/getHouseShopById.shtml?id='+id+'&flag=edit';
}

function modifTag(id){
	window.location='${pageContext.request.contextPath }/houseshop/getHouseShopById.shtml?id='+id;
}

function modifyCFHouseShop(id){
	window.location='${pageContext.request.contextPath }/houseshop/to_modifyCFHouseShop.shtml?id='+id;
}

</script>
</html>