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
				<jsp:param value="10" name="index"/>
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
										<form id="mainForm" action="" class="form-horizontal" enctype="multipart/form-data" method="post">
											<input type="hidden" name="id" value="${houseShop.id }"/>
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">名称：</label>
												<div class="col-xs-10">
													<input type="text" required value="${houseShop.shopName }" name="shopName" id="shopName" class="form-control" maxlength="128">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">老板头像：</label>
												<div class="col-xs-10">
												    <font class="star">*</font>路径 
												      <input type="file" id="bossImage" name="bossImage" class="form-control w-260 inline"/>
												                  原图路径： ${houseShop.bossImage }
												    <font class="star">*</font>压缩 <select id="compression" name="compression" class='form-control inline w-100'>
												    <option value="0">是</option><option value="1">否</option></select>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">级别：</label>
												<div class="col-xs-10">
													<select id="level" name="level" class="form-control valid" >
														<option value="1" <c:if test="${houseShop.level == 1}">selected="selected"</c:if>>A</option>
														<option value="2" <c:if test="${houseShop.level == 2}">selected="selected"</c:if>>AA</option>
														<option value="3" <c:if test="${houseShop.level == 3}">selected="selected"</c:if>>AAA</option>
													</select>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">类型：</label>
												<div class="col-xs-10">
													<select id="type" name="type" class="form-control valid">
														<option value="1" <c:if test="${houseShop.type == 1}">selected="selected"</c:if>>客栈</option>
														<option value="2" <c:if test="${houseShop.type == 2}">selected="selected"</c:if>>酒店</option>
														<option value="3" <c:if test="${houseShop.type == 3}">selected="selected"</c:if>>公寓</option>
													</select>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">客栈图片：</label>
												<div class="col-xs-10">
												    <font class="star">*</font>路径 <input type="file"  id="imgUrl" name="imgUrl" class="form-control w-260 inline"/>
												  <br>  圆路径：${houseShop.imgUrl}
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">城市：</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="cityID" name="cityID" aria-invalid="false">
														<c:forEach items="${CitysList}" var="pro">
													    	  <option value="${pro.cityId}" <c:if test="${houseShop.cityID == pro.cityId}">selected="selected"</c:if>>${pro.cityName}</option>							    
													     </c:forEach>
													</select>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">老板电话：</label>
												<div class="col-xs-10">
													<input type="number" required value="${houseShop.bossPhone }" name="bossPhone" id="bossPhone" class="form-control" maxlength="32">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">老板微信：</label>
												<div class="col-xs-10">
													<input type="text" value="${houseShop.bossWeChat }" name="bossWeChat" id="bossWeChat" class="form-control" maxlength="64">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">老板姓名：</label>
												<div class="col-xs-10">
													<input type="text" value="${houseShop.bossName }" name="bossName" id="bossName" class="form-control" maxlength="64">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="province" class="col-xs-2 control-label">合作关系：</label>
												<div class="col-xs-10">
													<select id="partnership" name="partnership" class="form-control valid">
														<option value="0" <c:if test="${houseShop.partnership == 0}">selected="selected"</c:if>>会员客栈</option>
														<option value="1" <c:if test="${houseShop.partnership == 1}">selected="selected"</c:if>>控股客栈 </option>
														<option value="2" <c:if test="${houseShop.partnership == 2}">selected="selected"</c:if>>深度合作</option>
														<option value="3" <c:if test="${houseShop.partnership == 3}">selected="selected"</c:if>>民宿贷客栈</option>
														<option value="4" <c:if test="${houseShop.partnership == 4}">selected="selected"</c:if>>联合运营客栈</option>
													</select>
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="province" class="col-xs-2 control-label">简介：</label>
												<div class="col-xs-10">
												    <textarea rows="3" cols="200" required value="${houseShop.shopDesc }" class="form-control" name="shopDesc" id="shopDesc" maxlength="500">${houseShop.shopDesc }</textarea>
												</div>
											</div>
											<br>
				
											<div class="form-group col-xs-6">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="button" class="btn  btn-style" value="提交"  onclick="javascript:updateHouseShop();"/>
												<input type="reset" class="btn  btn-style" value="重置" />
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
	<script>
		$().ready(function() {
			$("#mainForm").validate({
				rules: {
					shopName: "required",
					shopDesc:"required",
					bossPhone:"required",
				},
				messages: {
					shopName: "请输入店铺名称",
					shopDesc:"请输入店铺简介",
					bossPhone:"请输入老板电话",
				} /*,submitHandler:function(form){
					 if($(form).valid()){
						    $("#mainForm").attr("action","${pageContext.request.contextPath}/houseTag/addHouseTag.shtml?flag=add");
							$("#mainForm").submit();
	                        // submit form
	                        var p=$("#userFrom").serialize();
	                        $.ajax({
	                        	url:'${baseUrl}/dno/city_action',
	                        	data:p,
	                        	type:'post',success:function(result){
	                        		//alert(result);
	                        		myAlert({content:'修改成功',okfun:function(){
	                        			location.href="${baseUrl}/dno/city_list";
	                        		}})
	                        	}
	                        });
	                    }else{
	                        return false;
	                    }
					 
				} */
			});
			
		});

		
		function chooseText(number){
			if(number!=''||number!=null){
				if(isNaN(number)){
					alert("请输入数字!");
					$("#id").val("");
				}
			}
		}
		
		function updateHouseShop(){
			$("#mainForm").attr("action","${pageContext.request.contextPath}/houseshop/addHouseShop.shtml?flag=update").val();
			$("#mainForm").submit();
		}
	</script>
</html>