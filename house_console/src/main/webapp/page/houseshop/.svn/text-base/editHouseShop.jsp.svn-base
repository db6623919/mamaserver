<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!-- 修改商圈 -->
		<title>修改店铺</title>
	</head>
	<body>
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="2" name="index"/>
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
										<h2 class="text-uppercase">修改店铺</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="${pageContext.request.contextPath}/houseTag/addHouseTag.shtml?flag=add" class="form-horizontal" enctype="multipart/form-data" method="post">
											<input type="hidden" name="id" value="${houseShop.id }"/>
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">名称：</label>
												<div class="col-xs-10">${houseShop.shopName }
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">老板头像：</label>
												<div class="col-xs-10">
												    路径 :${houseShop.bossImage }
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">级别：</label>
												<div class="col-xs-10">
													<c:if test="${houseShop.level == 1}">A</c:if>
													<c:if test="${houseShop.level == 2}">AA</c:if>
													<c:if test="${houseShop.level == 3}">AAA</c:if>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">类型：</label>
												<div class="col-xs-10">
													<c:if test="${houseShop.type == 1}">客栈</c:if>
													<c:if test="${houseShop.type == 2}">酒店</c:if>
													<c:if test="${houseShop.type == 3}">公寓</c:if>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">客栈图片：</label>
												<div class="col-xs-10">
												    路径:${houseShop.imgUrl}
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">城市：</label>
												<div class="col-xs-10">
													${houseShop.cityName }
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">老板电话：</label>
												<div class="col-xs-10">${houseShop.bossPhone }
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">老板微信：</label>
												<div class="col-xs-10">${houseShop.bossWeChat }
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">老板姓名：</label>
												<div class="col-xs-10">
													${houseShop.bossName }
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="province" class="col-xs-2 control-label">合作关系：</label>
												<div class="col-xs-10">
													<c:if test="${houseShop.partnership == 0}">会员客栈</c:if>
													<c:if test="${houseShop.partnership == 1}">控股客栈</c:if>
													<c:if test="${houseShop.partnership == 2}">深度合作</c:if>
													<c:if test="${houseShop.partnership == 3}">民宿贷客栈</c:if>
													<c:if test="${houseShop.partnership == 4}">联合运营客栈</c:if>
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="province" class="col-xs-2 control-label">简介：</label>
												<div class="col-xs-10">${houseShop.shopDesc }
												</div>
											</div>
											<br><br><br>											
				
											<div class="form-group col-xs-6 text-right">
												<input type="button" class="btn btn-style" value="返回" onclick="history.go(-1)"/>
											</div>

										</form>

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
</html>