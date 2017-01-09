<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/common/backstageTaglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>妈妈送房后台</title>
</head>
<body>

	<div id="body-wrapper">
		<div id="main-content" style="margin: 0px; padding: 0px;">
			<div class="content-box">
				<div class="content-box-header">
					<h3>资源信息</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">


						<form id="mainForm" action="" method="post">
							<br>
							<table width="60%" border="1" align="center">
								<tr>
									<td align="right" width="25%"><font color="red">*</font>&nbsp;资源名：</td>
									<td height="25" align="left">&nbsp;  <input
											id="name" type="text" name="name" datatype="*"
											nullmsg="请输入资源名！" errormsg="资源名为显示菜单的名称！" maxlength="50"
											value="${resource.name }" style="width: 300" /><span
											class="Validform_checktip"></span>
									
									</td>
								</tr>
								<tr>
									<td align="right" width="25%"><font color="red">*</font>&nbsp;URL：</td>
									<td height="25" align="left">&nbsp;  <input
											id="url" type="text" name="privilege" datatype="*"
											nullmsg="请输入URL！" errormsg="URL为菜单的链接地址！" maxlength="100"
											value="${resource.privilege}" style="width: 300" /><span
											class="Validform_checktip"></span>
									
									</td>
								</tr>
								<tr>
									<td align="right" width="25%">上级资源：</td>
									<td height="25" align="left">&nbsp; 
											${parResource.name} 
									</td>
								</tr>
								<tr>
									<td align="right" width="25%">状 态：</td>
									<td height="25" align="left">&nbsp;  <select
											id="state" name="status">
												<c:if test="${resource.status =='0' }">
													<option value="1">启用</option>
													<option value="0" selected="selected">禁用</option>
												</c:if>
												<c:if test="${resource.status !='0' }">
													<option value="1" selected="selected">启用</option>
													<option value="0">禁用</option>
												</c:if>
										</select>
									
									</td>
								</tr>
								<tr>
									<td align="right" width="25%">排 序：</td>
									<td height="25" align="left">&nbsp;  <input
											id="orderno" type="text" name="sort" value="${resource.sort}" />&nbsp;
									
									</td>
								</tr>
								<tr>
									<td align="right" width="25%">备 注：</td>
									<td height="25" align="left">&nbsp;  <input
											id="remark" type="text" name="description"
											value="${resource.description}" style="width: 300" />&nbsp;
									
									</td>
								</tr>
								<tr>
									<td height="40" align="center" colspan="2"><span>&nbsp;
											<c:if test="${flag=='add' }">
												<input type="submit" value="增加" />
											</c:if> <c:if test="${flag=='update' }">
												<input type="submit" value="修改" />
											</c:if> <input type="reset" value="取消" />
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
	$("#mainForm").Validform(
			{
				tiptype : function(msg, o, cssctl) {
					if (!o.obj.is("form")) {
						var objtip = o.obj.siblings(".Validform_checktip");
						cssctl(objtip, o.type);
						objtip.text(msg);
					}
				},
				showAllError : true,
				beforeCheck : function(curform) {
					//alert("在表单提交执行验证之前执行的函数,这里明确return false的话将不会继续执行验证操作");	
				},
				beforeSubmit : function(curform) {
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
			});
</script>