<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!-- 新增城市 -->
		<title>更新城市</title>
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
										<h2 class="text-uppercase">更新城市</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm"  class="form-horizontal"  action="${pageContext.request.contextPath}/city/addCity.shtml?flag=update" method="post" enctype="multipart/form-data">
											<input type="hidden" name="cityId" value="${Citys.cityId }"/>
											<div class="form-group col-xs-12">
												<label for="cityName" class="col-xs-2 control-label"><font class="star">*</font>名称</label>
												<div class="col-xs-10">
													<input type="text" name="cityName"  value="${Citys.cityName }" id="cityName" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="cityName" class="col-xs-2 control-label"><font class="star">*</font>拼音</label>
												<div class="col-xs-10">
													<input type="text" name="pinyin"  value="${Citys.pinyin }" id="pinyin" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="province" class="col-xs-2 control-label">省</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="proId" name="proId" aria-invalid="false">
														<c:forEach items="${proList}" var="pro">
													      <option value="${pro.provId }" <c:if test="${Citys.provId==pro.provId}">selected="selected"</c:if>>${pro.provName }</option>							    
													     </c:forEach>
													</select>
												</div>

											</div>

											<div class="form-group col-xs-12">
												<label for="type" class="col-xs-2 control-label">类型</label>
												<div class="col-xs-10 ">
													<select class="form-control valid" id="type" name="type" aria-invalid="false">
														<option value="0" <c:if test="${Citys.type==0 }">selected="selected" </c:if>>既不热门也不推荐</option>
												        <option value="1" <c:if test="${Citys.type==1 }">selected="selected" </c:if> >热门</option>	
												        <option value="2" <c:if test="${Citys.type==2 }">selected="selected" </c:if>  >推荐</option>	
												        <option value="10" <c:if test="${Citys.type==10 }">selected="selected" </c:if>  >推荐+热门</option>	
													</select>
												</div>
											</div>

											<div class="form-group col-xs-12">
												<label for="img" class="col-xs-2 control-label">背景图</label>
												<div class="col-xs-10">
														<input type="file"  name="img"  id="img" class="form-control" >
														
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="img" class="col-xs-2 control-label"></label>
												<div class="col-xs-10">
														<input type="text" name="oldImg" id="oldImg" value="${Citys.img }"/>
														更换图片
														<select id="imgFlag" name="imgFlag">
														    <option value="0">否</option>
														    <option value="1">是</option>
														</select>
												</div>
											</div>

											<div class="form-group col-xs-12">
												<label for="cityName" class="col-xs-2 control-label">排序</label>
												<div class="col-xs-10">
													<input type="text" name="sort" value="${Citys.sort }" id="sort" class="form-control" onchange="chooseText(this.value)">
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="description" class="col-xs-2 control-label">城市介绍</label>
												<div class="col-xs-10">
													<textarea  name="description" id="description" class="form-control">${Citys.description }</textarea>
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
					name: "required"
				},
				messages: {
					name: "请输入城市名称",
					
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
					alert("请输入数字!");
					$("#sort").val("");
				}
			}else{
				$("#sort").val(number);
			}
		}
	</script>
</html>