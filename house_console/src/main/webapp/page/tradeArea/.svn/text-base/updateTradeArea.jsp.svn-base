<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!-- 新增商圈 -->
		<title>更新商圈</title>
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
										<h2 class="text-uppercase">更新商圈</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm"  class="form-horizontal"  action="${pageContext.request.contextPath}/tradeArea/addTradeArea.shtml?flag=update" method="post" enctype="multipart/form-data">
											<input type="hidden" name="id" value="${tArea.id }"/>
											<div class="form-group col-xs-12">
												<label for="name" class="col-xs-2 control-label"><font class="star">*</font>名称</label>
												<div class="col-xs-10">
													<input type="text" name="name"  value="${tArea.name }" id="name" class="form-control" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="province" class="col-xs-2 control-label">城市</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="cityId" name="cityId" aria-invalid="false">
														<c:forEach items="${CitysList}" var="pro">
													      <option value="${pro.cityId }" <c:if test="${tArea.cityId==pro.cityId}">selected="selected"</c:if>>${pro.cityName }</option>							    
													     </c:forEach>
													</select>
												</div>

											</div>

											<div class="form-group col-xs-12">
												<label for="sort" class="col-xs-2 control-label">排序</label>
												<div class="col-xs-10">
													<input type="text" name="sort" value="${tArea.sort }" id="sort" class="form-control" onchange="chooseText(this.value)">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="submit" class="btn  btn-style" value="提交" />
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
					name: "required",
					sort: "required"
				},
				messages: {
					name: "请输入城市名称",
					sort: "请输入排序"
					
				}/* ,submitHandler:function(form){
					 if($(form).valid()){
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
					alert("排序请输入数字!");
					$("#sort").val("");
				}
			}else{
				$("#sort").val(number);
			}
		}
	</script>
</html>