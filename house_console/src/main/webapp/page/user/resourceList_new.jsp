<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >

	<head>

		<%@include file="/page/common/base.jsp" %>
		<title>权限分配</title>
		<!-- 公共样式 -->
		<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/zTreeStyle/zTreeStyle.css" type="text/css">
	</head>

	<body>
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="1" name="menu"/>
				<jsp:param value="3" name="index"/>
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
					<div class="row" >
					
						<div class="container-fluid">
							<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

								<div class="panel-heading templatemo-position-relative">
									<h2 class="text-uppercase">权限分配</h2></div>

								<div class="table-responsive table-hover">
									<div class="box1 col-xs-3">
										<ul id="treeResource" class="ztree" style="width:200px;"></ul>
									</div>
									
									<div class="box2 col-xs-9">
										<iframe src="${pageContext.request.contextPath }/user/to_includeResource.shtml?id=1"  id="otherpage"  frameborder="0" style="width: 100%; height:420px"  scrolling="no"></iframe>
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.ztree.core-3.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.ztree.excheck-3.4.js"></script>
<script type="text/javascript">
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
		};
		var zNodes = ${menuStr};
		function onClick(event, treeId, treeNode, clickFlag) {
			$("#otherpage").attr("src","${pageContext.request.contextPath }/user/to_includeResource.shtml?id="+treeNode.id);
			
		}
		$(document).ready(function(){
			$.fn.zTree.init($("#treeResource"), setting, zNodes);
		});
	</script>

</html>