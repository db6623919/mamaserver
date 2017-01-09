<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>资源信息</title>
	</head>

	<body class="mg-top-0">
		<!-- /.row -->
		<div class="row">
			<div class="col-xs-12 ">
				<div class="panel  templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

					<div class=" templatemo-position-relative">
						<h2 class="text-uppercase mg-top-0">资源信息</h2>
					</div>
					<div class="row mg-top-10">
						<form id="mainForm" action="" onsubmit="toSubmit()" method="post" class="form-horizontal">
							<div class="form-group col-xs-12">
								<label for="name" class=" col-xs-2 checkbox-inline"><font class="star">*</font>资源名</label>
								<div class="col-xs-10">
									<input type="text" name="name" id="name" value="${resource.name }" class="form-control">
								</div>

							</div>
							<div class="form-group col-xs-12">
								<label for="privilege" class=" col-xs-2 checkbox-inline"><font class="star">*</font>url</label>
								<div class="col-xs-10">
									<input type="text" name="privilege" id="privilege" value="${resource.privilege}" class="form-control">
								</div>
							</div>
							
							<div class="form-group col-xs-12">
								<label for="url" class=" col-xs-2 checkbox-inline"><font class="star">*</font>url</label>
								<div class="col-xs-10">
									<input type="text" name=parResource id="parResource" value="${parResource.name} " class="form-control border-0 bg-f" readonly="readonly" >
								</div>
							</div>

							<div class="col-xs-12 form-group">
								<label for="status" class="col-xs-2  checkbox-inline">状态</label>
								<div class="col-xs-10">
									<select class="form-control col-xs-10" name="status">
										<option value="0" ${resource.status =='0'?'selected="selected"':''}>禁用</option>
										<option value="1" ${resource.status =='1'?'selected="selected"':''}>启用</option>
									</select>
								</div>
							</div>
							<div class="form-group col-xs-12">
								<label for="sort" class=" col-xs-2 checkbox-inline">排序</label>
								<div class="col-xs-10">
									<input type="text" name="sort" id="sort" value="${resource.sort}" class="form-control">
								</div>

							</div>
							<div class="form-group col-xs-12">
								<label for="description" class=" col-xs-2 checkbox-inline">备注</label>
								<div class="col-xs-10">
									<input type="text" name="description" id="description" value="${resource.description}" class="form-control">
								</div>
							</div>
							<div class="form-group col-xs-12 text-left">
								<div class="col-xs-12">
									<input type="submit" class="btn  btn-style" value="${flag=='add'?"增加":"修改"}" />
									<input type="reset" class="btn  btn-style" value="返回" onclick="history.go(-1)"/>
								</div>

							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->
	</body>
	<%@include file="/page/common/foot.jsp" %>
	<script>
		function toSubmit(){
			if ('${flag}' == 'add') {
				var pid = '${parResource.id }';
				$("#mainForm").attr(
						"action",
						"${pageContext.request.contextPath }/user/addResource.shtml?pid="
								+ pid);
			} else {
				var id = '${resource.id}';
				$("#mainForm").attr(
						"action",
						"${pageContext.request.contextPath }/user/updateResource.shtml?id="
								+ id);
			}
		}
	
		$().ready(function() {
			$("#addResourceFrom").validate({
				rules: {
					name: "required",
					url: "required"
				},
				messages: {
					name: "请输入资源名",
					url: "请输入url"
				},
				submitHandler:function(form){
					console.log(form);
				},
				errorContainer: "div.error",
				errorLabelContainer: $("#addResourceFrom div.error"),
				wrapper: "li"
			});
		});
	</script>

</html>