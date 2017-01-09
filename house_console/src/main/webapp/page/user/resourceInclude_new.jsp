<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>资源明细</title>
	</head>
	<body class="mg-top-0">
					<!-- /.row -->
					<div class="row">
							<div class="col-xs-12 ">
								<div class="panel  templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

									<div class=" templatemo-position-relative">
										<h2 class="text-uppercase mg-top-0">资源明细</h2>
									</div>
									<div class="row mg-top-10">
									<form id="mainForm" class="form-horizontal" method="post">
											<div class="form-group col-xs-12">
												<label for="name" class=" col-xs-2 checkbox-inline">资源名</label>
												<div class="col-xs-10">
													<input type="text" name="name" id="name" value="${resource.name }" class="form-control border-0 bg-f" readonly="readonly">
												</div>
												
											</div>
											<div class="form-group col-xs-12">
												<label for="url" class=" col-xs-2 checkbox-inline">url</label>
												<div class="col-xs-10">
													<input type="text" name="url" id="url" value="${resource.privilege}" class="form-control border-0 bg-f" readonly="readonly">
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="url" class=" col-xs-2 checkbox-inline">上级资源</label>
												<div class="col-xs-10">
													<input type="text" name="parName" id="parName" value="${parName}" class="form-control border-0 bg-f" readonly="readonly">
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="status" class=" col-xs-2 checkbox-inline">状态</label>
												<div class="col-xs-10">
													<input type="text" name="status" id="status" value="${resource.status =='0'?'禁用':'启用'}" class="form-control border-0 bg-f" readonly="readonly">
												</div>
												
											</div>
											<div class="form-group col-xs-12">
												<label for="orderby" class=" col-xs-2 checkbox-inline">排序</label>
												<div class="col-xs-10">
													<input type="text" name="orderby" id="orderby" value="${resource.sort}" class="form-control border-0 bg-f" readonly="readonly">
												</div>
												
											</div>
											<div class="form-group col-xs-12">
												<label for="remark" class=" col-xs-2 checkbox-inline">备注</label>
												<div class="col-xs-10">
													<input type="text" name="remark" id="remark" value="${resource.description}" class="form-control border-0 bg-f" readonly="readonly">
												</div>
												
											</div>
											<div class="form-group col-xs-12 text-left">
												<div class="col-xs-12">
													<!--addResource('1',0);-->
													<c:if test="${resource.parent_id == 0 }">
														<input type="button" class="btn  btn-style" value="增加同级资源" onclick="addResource('${resource.id}',0);" />
														<input type="button" class="btn  btn-style" value="增加下级资源" onclick="addResource('${resource.id}',1);" />
													</c:if> 
														<input type="button" class="btn  btn-style" value="修改资源" onclick="updateResource('${resource.id}');" />
														<input type="button" class="btn  btn-style" value="删除资源" onclick="delResource('${resource.id}');" /> 
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
	//增加人员
	function addResource(pid, type) {
		$("#mainForm").attr(
				"action",
				"${pageContext.request.contextPath }/user/to_addResource.shtml?pid="
						+ pid + "&type=" + type);
		$("#mainForm").submit();
	}

	//修改资源
	function updateResource(id) {
		$("#mainForm").attr(
				"action",
				"${pageContext.request.contextPath }/user/to_updateResource.shtml?id="
						+ id);
		$("#mainForm").submit();
	}

	function delResource(id) {
		if (confirm("确定删除此资源吗?删除后不可恢复!")) {
			window.parent.location.href = "${pageContext.request.contextPath }/user/deleteResource.shtml?id="
					+ id;
		}

	}

	//删除资源
	function deleteResource(id) {
		if (confirm("确定删除此资源吗?删除后不可恢复!")) {
			//判断是否存在子资源
			var urlstr = "${pageContext.request.contextPath }/system!getResourceByPid.shtml";
			$
					.ajax({
						type : "POST",
						url : urlstr,
						data : "id=" + id,
						dataType : "html",
						async : false,
						success : function(data) {
							//已存在
							if (data == '1') {
								alert("存在子资源,请先删除子资源!");
							} else {
								// $("#mainForm").attr("action","system!deleteResource.shtml?id="+id);
								window.parent.location.href = "system!deleteResource.shtml?id="
										+ id;
								//$("#mainForm").submit();
							}
						},
						error : function() {
							alert("error!");
						}
					});
		}
	}
	</script>

</html>