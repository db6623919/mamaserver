<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/slick/slick.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/slick/slick-theme.css">

<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!-- 评论详情 -->
		<title>评论详情</title>
	</head>
	<body>
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
							<h1 class="page-header">
                            			
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">

							<div class="col-xs-12 ">
								<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
									<div class="panel-heading templatemo-position-relative">
										<h2 class="text-uppercase">评论详情</h2>
									</div>
									<div class="row mg-top-10">


											<div class="form-group col-xs-12">
												<div class="col-xs-2"></div>
												<div class="col-xs-2"><B>用户</B>：${orderComment.userPhone }</div>
												<div class="col-xs-2"><B>评分</B>:${orderComment.score } </div>
												<div class="col-xs-6">${orderComment.createTime } </div>
											</div>
											<div class="form-group col-xs-12">
												<div class="col-xs-2"></div>
												<div class="col-xs-8">${orderComment.comments }</div>
												<div class="col-xs-2"></div>
											</div>
											<div class="form-group col-xs-12">
												<div class="gallery">
													<c:forEach items="${orderComment.images }" var="image">
														<div class="img" style="height: 600px;width: 400px;"> <img src="${image }" alt=""/> </div>
													</c:forEach>
													<!-- <div class="img"> <img src="http://file.88mmmoney.com/6772e07d-52f4-467f-b2f6-fc598d359b3c!h5i6s" alt=""/> </div>
													<div class="img"> <img src="http://file.88mmmoney.com/018b778b-4b44-48bd-9381-979594869f8c!h5i6s" alt=""/> </div>
													<div class="img"> <img src="http://file.88mmmoney.com/27ba6d47-5114-47f8-af9e-19955667d722!h5i6s" alt=""/> </div> -->
										        </div>
											</div>
											<div class="form-group col-xs-12" style="text-align: center;">
												<input type="button" class="btn btn-style" value="返回" onclick="history.go(-1)" />
											</div>
											
									</div>
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/slick/slick.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/comment-list.js"></script>
	<script>

		
	</script>
</html>