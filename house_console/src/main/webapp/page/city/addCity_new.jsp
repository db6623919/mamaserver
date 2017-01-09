<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!-- 新增城市 -->
		<title>新增城市</title>
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
										<h2 class="text-uppercase">新增城市</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="${pageContext.request.contextPath}/city/addCity.shtml" class="form-horizontal" enctype="multipart/form-data" method="post">
											<div class="form-group col-xs-12">
												<label for="cityName" class="col-xs-2 control-label"><font class="star">*</font>名称</label>
												<div class="col-xs-10">
													<input type="text" name="cityName" value="" id="cityName" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="cityName" class="col-xs-2 control-label"><font class="star">*</font>拼音</label>
												<div class="col-xs-10">
													<input type="text" name="pinyin" value="" id="pinyin" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="province" class="col-xs-2 control-label">省</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="proId" name="proId" aria-invalid="false">
														<c:forEach items="${proList}" var="pro">
													    	  <option value="${pro.provId}" >${pro.provName}</option>							    
													     </c:forEach>
													</select>
												</div>
											</div>

											<div class="form-group col-xs-12">
												<label for="type" class="col-xs-2 control-label">类型</label>
												<div class="col-xs-10 ">
													<select class="form-control valid" id="type" name="type" aria-invalid="false">
														 <option value="0" >既不热门也不推荐</option>
													     <option value="1" >热门</option>	
													     <option value="2" >推荐</option>	
												         <option value="10" >推荐+热门</option>		
													</select>
												</div>
											</div>

											<div class="form-group col-xs-12">
												<label for="img" class="col-xs-2 control-label"><font class="star">*</font>背景图</label>
												<div class="col-xs-10">
														<input type="file"  name="img" value="" id="img" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="cityName" class="col-xs-2 control-label">排序</label>
												<div class="col-xs-10">
													<input type="text" name="sort" value="" id="sort" class="form-control" onchange="chooseText(this.value)">
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="description" class="col-xs-2 control-label"><font class="star">*</font>城市介绍</label>
												<div class="col-xs-10">
													<textarea  name="description" id="description" class="form-control"></textarea>
													<input type="hidden"  name="imgFlag" id="imgFlag" value="1">
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
					cityName: "required",
					img:"required",
					description:"required"
				},
				messages: {
					cityName: "请输入城市名称",
					img:"请选择图片",
					description:"请输入城市介绍"
					
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
		//打开商户选择
		function openMchnt(mchntId) {
			var flag;
			var item = $(":radio:checked");
			var len = item.length;
			if (len > 0) {
				if ($(":radio:checked").val() == '2') {
					flag = "sh";
				} else if ($(":radio:checked").val() == '4') {
					flag = "qy";
				} else {
					flag = "sh";
				}
			}
			openWindow("system!to_mchnt.shtml?flag=" + flag + "&s_mchntid="
					+ mchntId, "商户选择", 800, 600);
		}

		//点击商户/企业角色时默认显示div
		function checkradio() {
			var item = $(":radio:checked");
			var len = item.length;
			if (len > 0) {
				if ($(":radio:checked").val() == '2') {
					$("#span_sh_qy").html("商     户：");
					$("#mchntDIV").show();
				} else if ($(":radio:checked").val() == '4') {
					$("#span_sh_qy").html("企     业：");
					$("#mchntDIV").show();
				} else {
					$("#mchntDIV").hide();
				}
			}
		}

		//为商户/企业角色时默认显示div
		$(function() {
			var item = $(":radio:checked");
			var len = item.length;
			if (len > 0) {
				if ($(":radio:checked").val() == '2') {
					$("#span_sh_qy").html("商     户：");
					$("#mchntDIV").show();
				} else if ($(":radio:checked").val() == '4') {
					$("#span_sh_qy").html("企     业：");
					$("#mchntDIV").show();
				} else {
					$("#mchntDIV").hide();
				}
			}
			$("#fanhui")
					.click(
							function() {
								history.go(-1);
							});
		});
		var index=0;
		function addDiv(id,divId){
			var html=$('#'+divId).html();
			$('#'+divId).html(html+"<p id='"+index+"'><span class='public_title'><font color='red'>*</font></span>名称<input type='text' value=''  name='introductionName' class='text-input' style='width: 140px'/>路径<input type='text' value=''  name='"+id+"' class='text-input' style='width: 160px'/><span class='Validform_checktip'></span><input type='button' id='label_Btn' value='删除' onclick='deleteDiv("+index+")'/></p>");
			index++;
		}
		function deleteDiv(id){	
			$('#'+id).remove();
		}
		
		function chooseText(number){
			if(number!=''||number!=null){
				if(isNaN(number)){
					alert("请输入数字!");
					$("#sort").val("");
				}
			}
		}
	</script>
</html>