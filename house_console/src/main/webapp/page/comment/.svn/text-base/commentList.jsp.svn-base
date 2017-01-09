<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>评论管理</title>
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/comment/toList.shtml" method="post">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="7" name="index"/>
			</jsp:include>
			<div id="page-wrapper">
				<div class="container-fluid">
					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header"> </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">评论管理</h2></div>
								<div class="row query">
									<div class="col-xs-12">
									    <select id="status" name="status"  ">
									         <option value="-1">全部</option>
									         <option value="0" <c:if test="${status == 0 }">selected="selected"</c:if>>未审核</option>
									         <option value="1" <c:if test="${status == 1 }">selected="selected"</c:if>>审核通过</option>
									         <option value="2" <c:if test="${status == 2 }">selected="selected"</c:if>>审核拒绝</option>
									    </select>
										<button type="button" class="btn  btn-style" onclick="search()">查询</button>
									</div>
								</div>
								<div class="table-responsive table-hover">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<td>编号</td>
												<td>评价人（用户）</td>
												<td>评分</td>
												<td>评论内容</td>
												<td>图片数量</td>
												<td>评论时间</td>
												<td width="70px;">状态</td>
												<td width="70px;">是否精华</td>
												<td width="90px;">审核</td>
												<td width="50px;">预览</td>
												<td width="100px;">置顶</td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${orderCommentList!=null }">
									      		<c:forEach items="${orderCommentList}" var="comment">
									        	<tr>
									        		<td>${comment.id }</td>
									        		<td>${comment.uid }</td>
									        		<td>${comment.score }</td>
									        		<td>${comment.comments }</td>
									        		<td>${comment.imageSize }</td>
									        		<td>${comment.createTime }</td>
									        		<td id="status_td${comment.id }">
									        			<c:if test="${comment.status==0 }">未审核</c:if>
									        			<c:if test="${comment.status==1 }">已通过</c:if>
									        			<c:if test="${comment.status==2 }">未通过</c:if>
									        		</td>
									        		<td id="recommendTime_td${comment.id }">
									        			<c:if test="${comment.recommendTime>0 }">已精华</c:if>
									        			<c:if test="${comment.recommendTime==0 }">未精华</c:if>
									        		</td>
									        		<td id="commentStatus_td${comment.id }">
									        			<c:if test="${comment.status==0 }">
									        				<a href="javascript:doReview('${comment.id }',1,'${comment.houseId }')">审核通过</a>
									        				<a href="javascript:doReview('${comment.id }',2,'${comment.houseId }')">审核拒绝</a>
									        			</c:if>
									        			<c:if test="${comment.status==1 || comment.status==2 }">--</c:if>
									        		</td>
									        		<td><a href="javascript:show('${comment.id }')">预览</a></td>
									        		<td id="stickOrderCommnet_td${comment.id }">
									        			<c:if test="${comment.recommendTime>0 }"><a href="javascript:stickOrderCommnet('${comment.id }',0,'${comment.houseId }')">取消精华</a></c:if>
									        			<c:if test="${comment.recommendTime==0 }"><a href="javascript:stickOrderCommnet('${comment.id }',1,'${comment.houseId }')">精华</a></c:if>
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
<script type="text/javascript">
function search() {
	var status = $("#status").val();
	window.location='${pageContext.request.contextPath }/comment/toList.shtml?status='+status;
}

//评论审核
function doReview(commentId,status,houseId) {
	$.ajax({
		url:"${pageContext.request.contextPath }/comment/doReview.shtml",
		type:'post',
		async: false,
		data:{"id":commentId,"status":status,"houseId":houseId} ,
		success:function(data) {
			if(data.code == 0){
				if(status == 1) {
					$("#status_td" + commentId).html("已通过");
					$("#commentStatus_td" + commentId).html("--");
					alert("审核通过!");
					return;
				}
				if(status == 2) {
					$("#status_td" + commentId).html("未通过");
					$("#commentStatus_td" + commentId).html("--");
					alert("审核不通过!");
					return;
				}
			}
		}
	});
}

//精华
function stickOrderCommnet(commentId,recommendTime,houseId) {
	var stickHtml = $("#status_td" + commentId).html();
	 if(stickHtml.indexOf("已通过") >= 0) { 
		$.ajax({
			url:"${pageContext.request.contextPath }/comment/stickOrderCommnet.shtml",
			type:'post',
			async: false,
			data:{"id":commentId,"recommendTime":recommendTime,"houseId":houseId} ,
			success:function(data) {
				if(data.code == 0){
					if(recommendTime == 0) {
						$("#recommendTime_td" + commentId).html("未精华");
						$("#stickOrderCommnet_td" + commentId).html("<a href='javascript:stickOrderCommnet(\"" + commentId + "\",1," + houseId + ")'>精华</a>");
						alert("已取消精华!");
						return;
					}
					if(recommendTime == 1) {
						$("#recommendTime_td" + commentId).html("已精华");
						$("#stickOrderCommnet_td" + commentId).html("<a href='javascript:stickOrderCommnet(\"" + commentId + "\",0," + houseId + ")'>取消精华</a>");
						alert("已精华!");
						return;
					}
				}
			}
		}); 	
	 } else {
		alert("未通过审核!");
	} 
	
}

function show(id){
	window.location='${pageContext.request.contextPath }/comment/show.shtml?id=' + id;
}
</script>
</html>