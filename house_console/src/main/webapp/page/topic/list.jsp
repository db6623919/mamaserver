<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>主题活动管理</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/topic/toList.shtml" method="post">
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
									<h2 class="text-uppercase">主题管理</h2></div>

								<div class="row query">
									<div class="col-xs-12">
										<button type="button" class="btn  btn-style" id="addButton">新增</button>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>活动名称</td>
												<td width="150px">活动类型</td>
												<td>一句话介绍</td>
												<td>具体介绍说明</td>
												<td width="100px">关联客栈数</td>
												<td width="300px">操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="topic">
									        	<tr>
									        		<td>${topic.activityName }</td>
										        	<td>
										        	     <c:if test="${topic.activityType == 1 }">单身</c:if>
										        	     <c:if test="${topic.activityType == 2 }">亲子</c:if>
										        	     <c:if test="${topic.activityType == 3 }">老人</c:if>
										        	     <c:if test="${topic.activityType == 4 }">团建</c:if>
										        	</td>
									        		<td>${topic.title } </td>
										         	<td>${topic.introduction }</td>
												  	<td id="td_${topic.id }">${topic.shopCount }</td>
												  	<td>
									              		<a href="javascript:modifTopicActivity('${topic.id }')" class="templatemo-edit-btn" title="修改">修改</a>
									              		<a href="javascript:deleteTopicActivity('${topic.id }',${topic.shopCount })" class="templatemo-edit-btn" title="删除">删除</a>
									              		<a href="javascript:showShopList('${topic.id }')" class="templatemo-edit-btn" title="客栈关联">客栈关联</a>
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
<script type="text/javascript">
//活动主题删除
function deleteTopicActivity(topicId,shopCount) {
	if (parseInt(shopCount) > 0) {
		dele(function(){
			window.location='${pageContext.request.contextPath }/topic/toAddOrEdit.shtml?id='+topicId +'&flag=del';
		});
	} else {
		del(function(){
			window.location='${pageContext.request.contextPath }/topic/toAddOrEdit.shtml?id='+topicId +'&flag=del';
		});
	}
	
}
//活动主题编辑
function modifTopicActivity(topicId) {
	window.location='${pageContext.request.contextPath }/topic/toAddOrEdit.shtml?id='+topicId +'&flag=edit';
}
$(function(){
	//活动主题增加
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/topic/toAddOrEdit.shtml?flag=add';
	});
});

function showShopList(topicId){
	openWindow("${pageContext.request.contextPath }/topic/shopList.shtml?topicId=" + topicId,"客栈列表",1300,600);
}

function dele(okfun) {
	var d = dialog({
		title: '提示',
		content: '存在关联店铺,是否继续删除？',
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
}
</script>
</html>