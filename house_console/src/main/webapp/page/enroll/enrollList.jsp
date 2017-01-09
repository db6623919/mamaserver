<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>单身party用户管理</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/houseshop/list.shtml" method="post">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="11" name="index"/>
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
									<h2 class="text-uppercase">单身party用户管理</h2></div>
                              <div class="row query form-horizontal">
								<div class="table-responsive table-hover">
								        <div class="form-group col-xs-6">
											<label for="x" class="col-xs-3 control-label">用户名：</label>
											<div class="col-xs-7" >
												<input type="text" value="${ name}" name="name" id="name" class="form-control" />
											</div>
										</div>
										<div class="form-group col-xs-6">
											<label for="x" class="col-xs-3 control-label">手机号码:</label>
											<div class="col-xs-7" >
												<input type="text" value="${ phone}" name="phone" id="phone" class="form-control" />
											</div>
										</div>
										
										<div class="form-group col-xs-6">
											<label for="x" class="col-xs-3 control-label">学历：</label>
											<div class="col-xs-7" >
												<select id="educated" name="educated" class="form-control" >
													<option value='' selected="selected">全部</option>
													<option value='1' <c:if test="${educated==1}">selected="selected"</c:if> >高中</option>
													<option value='2' <c:if test="${educated==2}">selected="selected"</c:if>>大专</option>
													<option value='3' <c:if test="${educated==3}">selected="selected"</c:if> >本科</option>
													<option value='4' <c:if test="${educated==4}">selected="selected"</c:if> >硕士</option>
													<option value='5' <c:if test="${educated==5}">selected="selected"</c:if> >博士</option>
												</select>
											</div>
										</div>
										<div class="form-group col-xs-6">
											<label for="x" class="col-xs-3 control-label">状态：</label>
											<div class="col-xs-7" >
												<select id="status" name="status" class="form-control" name="build">
													<option value='' selected="selected">全部</option>
													<option value='0' <c:if test="${status==0}">selected="selected"</c:if> >未确认</option>
													<option value='1' <c:if test="${status==1}">selected="selected"</c:if> >确认</option>
												</select>
											</div>
											<div class="col-xs-2">
												<button type="button" id="serchButton" class="btn  btn-style">搜索</button>
											</div>
										</div>
										
										<div class="form-group col-xs-6">
											<label for="x" class="col-xs-8 control-label">已确认用户数：${ ConfirmNum}  &nbsp;&nbsp; 未确认用户数：${ NoConfirmNum } </label>
										</div>
									
									</div>
										
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>用户名</td>
												<td>手机号</td>
												<td>微信号</td>
												<td>年龄</td>
												<td>性别</td>
												<td>学历</td>
												<td>报名时间</td>
												<td>是否确认参加</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${list!=null }">
									      		<c:forEach items="${list}" var="enroll">
									        	<tr>
									        	  <td>${enroll.name}</td>
									        	  <td>${enroll.phone}</td>
												  <td>${enroll.weChat }</td>
												  <td>${enroll.age }</td>
												  <td><c:if test="${enroll.sex==1 }">女</c:if>
												      <c:if test="${enroll.sex==0 }">男</c:if>
												  </td>
												  <td><c:if test="${enroll.educated==1 }">高中</c:if><c:if test="${enroll.educated==2 }">大专</c:if>
												      <c:if test="${enroll.educated==3 }">本科</c:if><c:if test="${enroll.educated==4 }">硕士</c:if>
												      <c:if test="${enroll.educated==5 }">博士</c:if>
												  </td>
												  <td>${enroll.enroll_time }</td>
												  <td><c:if test="${enroll.status==0 }">未确认</c:if><c:if test="${enroll.status==1 }">已确认</c:if></td>
									              <td>
									                 <c:if test="${enroll.status==0 }">
									                    <a href="javascript:modifStatus('${enroll.id }',${enroll.phone})" class="templatemo-edit-btn" title="修改">确认</a>
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
<script>
$(function(){
	$("#serchButton").click(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/enroll/list.shtml").val();
		$("#mainForm").submit();
	});

});


function modifStatus(id,phone){
	window.location='${pageContext.request.contextPath }/enroll/updateEnroll.shtml?id='+id+"&phone="+phone;
}
</script>
</html>