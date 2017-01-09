<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/common/taglibs.jsp"%>
<%@ include file="/page/common/backstageTaglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>妈妈送房后台</title>

<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 80%;
	border: 0;
	margin-top: 55px;
	margin-left: 60px
}

table th {
	height: 30px;
	text-align: left;
	padding-left: 13px;
	font-weight: bold;
	border: 1px solid #666;
	font-size: 16px;
}

table td {
	padding-left: 13px;
	height: 30px;
	border: 1px solid #666;
	font-size: 14px;
}
</style>

</head>
<body>

	<div id="body-wrapper">
		<div id="main-content"  style="margin:0px;padding:0px;">
			<div class="content-box">
				<div class="content-box-header">
					<h3>资源明细</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">

						<form id="mainForm" action="" method="post">
							<br>
							<table border="1" align="center"  style="margin:0px;padding:0px; table-layout:fixed;">

								<tr>
									<td align="right" width="20%">资源名：</td>
									<td align="left">&nbsp; ${resource.name }&nbsp;</td>
								</tr>
								<tr>
									<td align="right" width="20%">URL：</td>
									<td align="left">&nbsp; ${resource.privilege}&nbsp;</td>
								</tr>
								<tr>
									<td align="right" width="20%">上级资源：</td>
									<td align="left">&nbsp; ${parName}&nbsp;</td>
								</tr>
								<tr>
									<td align="right" width="20%">状 态：</td>
									<td align="left">&nbsp; <c:if
											test="${resource.status =='0' }">
									禁用
								    </c:if> <c:if test="${resource.status !='0' }">
								      启用
							       </c:if>
									</td>
								</tr>
								<tr>
									<td align="right" width="20%">排 序：</td>
									<td align="left">&nbsp; ${resource.sort}&nbsp;</td>
								</tr>
								<tr>
									<td align="right" width="20%">备 注：</td>
									<td align="left">&nbsp; ${resource.description}&nbsp;</td>
								</tr>
								<tr>
									<td height="40" align="center" colspan="2"><span>&nbsp;
											<c:if test="${resource.parent_id == 0 }">
												<input type="button" value="增加同级资源"
													onclick="addResource('${resource.id}',0);" />&nbsp;
							<input type="button" value="增加下级资源"
													onclick="addResource('${resource.id}',1);" />&nbsp;
						</c:if> <input type="button" value="修改资源"
											onclick="updateResource('${resource.id}');" />&nbsp; <input
											type="button" value="删除资源"
											onclick="delResource('${resource.id}');" />&nbsp; <input
											type="reset" value="取消" />&nbsp;
									</span></td>
								</tr>

							</table>
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>

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