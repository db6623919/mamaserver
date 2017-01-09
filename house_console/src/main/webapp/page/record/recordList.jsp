<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>抽奖记录</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/record/toRecord.shtml" method="post">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="6" name="index"/>
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
									<h2 class="text-uppercase">抽奖记录</h2></div>

								<div class="row query">
									<div class="col-xs-12">
												<div class="col-xs-1">
													抽奖日期:
												</div>
												<div class="col-xs-8">
													<input type="date"  value="${recordDate }" required name="recordDate" id="recordDate" class="form-control">
												</div>
												<div class="col-xs-3">
													<button type="button" class="btn  btn-style" onclick="search()">搜索</button>
													<button type="button" class="btn  btn-style" onclick="exportExcel()">导出</button>
												</div>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td width="5%">序号</td>
												<td>活动名称</td>
												<td>会员id</td>
												<td>手机号</td>
												<td>积分</td>
												<td>日期</td>
												<td>分享次数</td>
												<td>抽奖次数</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${activityMemberRecordList!=null }">
									      		<c:forEach items="${activityMemberRecordList}" var="activityMemberRecord" varStatus="status">
									        	<tr>
									        	    <td>${(pager.current_page - 1) * pager.page_size + status.index + 1}</td>
										        	<td>${activityMemberRecord.activityName }</td>
										        	<td>${activityMemberRecord.memberId }</td>
										        	<td>${activityMemberRecord.memberIdentity }</td>
											        <td>${activityMemberRecord.totalPoint }</td>
													<td>${activityMemberRecord.recordDate }</td>
													<td>${activityMemberRecord.shareTimes }</td>
													<td>${activityMemberRecord.recordTimes }</td>
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
		<script src="http://api.map.baidu.com/api?v=2.0&ak=CF1924c9daf46e30c71c71db9c85a646" ></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/baidumap.js" defer="defer"></script>
		
<script type="text/javascript">
function search() {
	var recordDate = $("#recordDate").val();
	//recordDate = recordDate.replace(/-/gm,"");
	//alert(recordDate);
	window.location='${pageContext.request.contextPath }/record/toRecord.shtml?recordDate='+recordDate;
}

function exportExcel() {
	var recordDate = $("#recordDate").val();
	//recordDate = recordDate.replace(/-/gm,"");
	//alert(recordDate);
	window.location='${pageContext.request.contextPath }/record/exportExcel.shtml?recordDate='+recordDate;
}
</script>
</html>