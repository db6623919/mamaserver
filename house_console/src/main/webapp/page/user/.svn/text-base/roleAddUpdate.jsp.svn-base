<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>妈妈送房后台</title>
</head>
<body>
	<div id="body-wrapper">
		<div id="main-content">
			<div class="content-box">
				<div class="content-box-header">
					<h3><c:if test="${flag=='add'}">新增</c:if><c:if test="${flag=='update'}">修改</c:if>角色的资源</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post">
							<input type="hidden" id="resourceids" name="resourceids" value="${hasResourceId }" />
							<input type="hidden" id="roleName" name="roleName" value="${role.name }" />
							<input type="hidden" id="roleRemark" name="roleRemark" value="${role.description}" />
							<fieldset>
							
							<c:if test="${flag=='update' }">
								<p>
									<span class="public_title">角色名：</span>&nbsp;${role.name }
								</p>
							</c:if>	
							
							<c:if test="${flag=='add' }">
								<p>
									<span class="public_title">角色名：</span>&nbsp;
									<input class="text-input small-input" type="text" id="roleName" name="name"   />
								</p>
							</c:if>	
							
								<p>
									<span class="public_title">状态：</span>    
									<select name="status" id="status" class="small-input">
										<c:if test="${role.status =='0' }">
											<option value="1">启用</option>
											<option value="0" selected="selected">禁用</option>
										</c:if>
										<c:if test="${role.status !='0' }">
											<option value="1" selected="selected">启用</option>
											<option value="0">禁用</option>
										</c:if>
									</select> 
								</p>
								<p>
									<span class="public_title">资源：</span>
									<input class="text-input small-input" type="text" id="resourcenames" name="resourcenames" value="${hasResourceName}" readonly="readonly" />
									&nbsp;
									
								<c:if test="${!empty role}">	
									<input type="button" class="button" value="选择" onclick="openResource('${role.id}');">
								</c:if>	
								
								<c:if test="${empty role}">	
									<input type="button" class="button" value="选择" onclick="openResource(0);">
								</c:if>	
									
								</p>
								
								<p>
									<span class="public_title">备注：</span>&nbsp;
									<input class="text-input small-input" type="text" id="roleName" name="description"  value="${role.description}"  />
								</p>
								
								<p >
								    <span class="public_title">&nbsp;</span>
								    
								    <c:if test="${flag=='update'}">
										<input type="button" class="button" value="修改" onclick="updateRole('${role.id}');" />
									</c:if>
									
									<c:if test="${flag=='add'}">
										<input type="button" class="button" value="增加" onclick="addRole();" />
									</c:if>
									
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="button" value="返回" id="fanhui" />
								</p>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
$(function(){
	$("#fanhui").click(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/user/toRoleList.shtml");
		$("#mainForm").submit();
	});
});
//修改人员
function updateRole(id){
	$("#mainForm").attr("action","${pageContext.request.contextPath }/user/updateRole.shtml?id="+id);
	$("#mainForm").submit();
}

//修改人员
function addRole(){
	$("#mainForm").attr("action","${pageContext.request.contextPath }/user/addRole.shtml");
	$("#mainForm").submit();
}

//打开资源选择
function openResource(id){
	openWindow("${pageContext.request.contextPath }/user/to_getResourceMenu.shtml?id="+id,"资源选择",300,600);
}
</script>