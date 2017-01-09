<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!-- 新增商圈 -->
		<title>新增标签</title>
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
										<h2 class="text-uppercase">新增标签</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="${pageContext.request.contextPath}/houseTag/addHouseTag.shtml?flag=add" class="form-horizontal" enctype="multipart/form-data" method="post">
											<div class="form-group col-xs-12">
												<label for="name" class="col-xs-2 control-label"><font class="star">*</font>id</label>
												<div class="col-xs-10">
													<input type="text" name="id" value="" id="id" class="form-control" onchange="chooseText(this.value)">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="province" class="col-xs-2 control-label">名称：</label>
												<div class="col-xs-10">
													<input type="text" name="name" value="" id="name" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="province" class="col-xs-2 control-label">是否搜索显示：</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="isDispayOnSearch" name="isDispayOnSearch" aria-invalid="false">
													    	  <option value="0" >不显示</option>
													    	  <option value="1" >显示</option>
													</select>
												</div>
											</div>
				
											<div class="form-group col-xs-6">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="button" class="btn  btn-style" value="提交"  onclick="javascript:addTag();"/>
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
					id:"required"
				},
				messages: {
					name: "请输入标签名称",
					id:"请选择id"
					
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
		
		function addTag(){
			var name = $("#name").val();
			if(name.length>6){
				alert("标签名长度不能超过6位！");
				return;
			}
			
			$("#mainForm").attr("action","${pageContext.request.contextPath}/houseTag/addHouseTag.shtml?flag=add").val();
			$("#mainForm").submit();
		}
	</script>
</html>