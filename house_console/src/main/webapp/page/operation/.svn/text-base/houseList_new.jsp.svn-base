<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!--房源管理-->
		<title>房源管理</title>
	</head>

	<body>

		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="4" name="index"/>
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
									<h2 class="text-uppercase">房源管理</h2>
								</div>
								<div class="row query">
									<form action="${pageContext.request.contextPath }/house/toHouse.shtml" id="mainForm" method="post">
										<div class="col-xs-6">
													<div class="checkbox">
														<label class="checkbox-inline pd-left-0" for="city">
															 城市:
														</label>
														
														<c:forEach items="${cityInfoList}" var="city">
														  <c:if test="${city.checked==1 }">
														  	<label class="checkbox-inline">
														   	 <input type="checkbox" name="cities" checked="checked" value="${city.cityId }"/>${city.cityName }
														    </label>
														   </c:if>
														   <c:if test="${city.checked==0}">
														   	 <label class="checkbox-inline">
														    	<input type="checkbox" name="cities" value="${city.cityId }"/>${city.cityName }
														     </label>
														   </c:if>
						
														</c:forEach>
														
													</div>
										</div>
										<div class="col-xs-2">
												<label class="checkbox-inline pd-left-0">房源:</label>
												<input type="text" name="name" id="name" value="${name }"/> 
										</div>
										<div class="col-xs-4">
											<button type="submit" class="btn  btn-style" id="serchButton">搜索</button>
											<button type="button" class="btn  btn-style" id="addButton">新增</button>
											<button type="button" class="btn  btn-style" id="syncDetailToMango">同步mangodb数据</button>
											<button type="button" class="btn  btn-style" id="syncSearchToMango">同步mangodb房源搜索数据</button>
										</div>
									</form>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>城市</td>
												<td>房源</td>
												<td>会员预订</td>
												<td>普通价</td>
												<td>普通预订</td>
												<td>会员价</td>
												<td>是否上架</td>
												<td>状态</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody class="data_list">
											<c:forEach items="${pager.list}" var="house">
									        	<tr>
									        	<td>
										             <c:forEach items="${cityInfoList}" var="city">
											              <c:if test="${house.cityId==city.cityId }">
												               ${city.cityName }	
												          </c:if>						    
												     </c:forEach>
										          </td>
									        	 <td>${house.summaryInfo }</td>
										          <td>${house.freezeAmt }</td>
										          <td>${house.totalAmt }</td>
										          <td>${house.memFreezeAmt}</td>
										          <td>${house.memTotalAmt }</td>
										          <td><c:if test="${house.status==1 }">是</c:if></td>
										          <td>
										             <c:if test="${house.specials_stauts==0 }">普通</c:if>
										             <c:if test="${house.specials_stauts==1 }">特价</c:if>
										             <c:if test="${house.specials_stauts==2 }">首页特价</c:if>
										          </td>
									              <td>
									                <a href="javascript:houseDateList('${house.houseId }')" title="价格管理" class="templatemo-edit-btn">价格管理</a>
									              	<a href="javascript:modifUser('${house.houseId }')" title="修改" class="templatemo-edit-btn">修改</a>
									              	<a href="javascript:deleteFun('${house.houseId }','${house.specials_stauts }')" title="删除" class="templatemo-edit-btn">删除</a>
									              	<a href="javascript:toComment('${house.houseId }')" title="增加评论" class="templatemo-edit-btn">增加评论</a>
									              	<c:if test="${house.specials_stauts==0 }">
									              	  <a href="javascript:updateSpecialsStatus('${house.houseId }',1)" class="templatemo-edit-btn">       
									              	            设为特价
									              	  </a>
									              	</c:if>
									              	<c:if test="${house.specials_stauts==1 }">
									              	  <a href="javascript:updateSpecialsStatus('${house.houseId }',0)"  class="templatemo-edit-btn">       
									              	            取消特价
									              	  </a>
									              	  <a href="javascript:updateSpecialsStatus('${house.houseId }',2)"  class="templatemo-edit-btn">       
									              	            置首页
									              	  </a>
									              	</c:if>
										            
									              </td>
									            </tr>
									       	</c:forEach>
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

	</body>
	<%@include file="/page/common/foot.jsp" %>
	<script>
$(function(){
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/house/houseAddUpdate.shtml?flag=add';
	});
	
	$("#syncDetailToMango").click(function(){
        $.ajax({
         	url:'${pageContext.request.contextPath }/house/syncDetailToMango.shtml',
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
	
	$("#syncSearchToMango").click(function(){
        $.ajax({
         	url:'${pageContext.request.contextPath }/house/syncSearchToMango.shtml',
         	data:'',
         	type:'post',success:function(data){
         		var code = data.result.code;
         		var msg = data.result.message;
         		if(code==0){
         			alert(msg);
         		}else{
         			alert(msg);
         		}
         		
         	}
         });
        
	});
	
});
function houseDateList(houseId){
	window.location='${pageContext.request.contextPath }/house/toHouseDateList.shtml?houseId='+houseId;
}
function modifUser(id){
	window.location='${pageContext.request.contextPath }/house/to_houseUpdate.shtml?houseId='+id;
}
function deleteFun(id,specials_stauts){
	if(specials_stauts==2){
		alert('该房源已特价置顶，不能删除！');
		return;
	}
	del(function(){
		window.location='${pageContext.request.contextPath }/house/houseDel.shtml?houseId='+id;
	});
}
function toComment(houseId) {
	var url = '${pageContext.request.contextPath }/house/toComment.shtml?houseId=' + houseId;
	window.open(url, 'comment');
}
function updateSpecialsStatus(houseId,status){
	window.location='${pageContext.request.contextPath }/house/updateSpecialsStatus.shtml?specials_stauts='+status+'&houseId='+houseId;
}

</script>
</html>