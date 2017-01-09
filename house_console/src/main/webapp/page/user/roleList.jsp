<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>妈妈送房后台</title>
</head>
<body>
	<form id="mainForm" action="${pageContext.request.contextPath }/system!to_user.shtml" method="post">
		<div id="body-wrapper">
			<div id="main-content">
				<div class="content-box">
					<div class="content-box-header">
						<h3>角色管理</h3>
						<div class="clear"></div>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab" id="tab1">
						<p style="margin-bottom: 10px;">
								<input type="button" class="button" id="addButton" value="新&nbsp;增">
						</p>
							
							
							
							<c:if test="${errorMessage!='' and errorMessage !=null and errorMessage != 'null' }">
								<div class="notification error png_bg">
									<a class="close">
										<img alt="关闭" title="关闭" src="${pageContext.request.contextPath }/resource/images/icons/cross_grey_small.png">
									</a>
									<div>${errorMessage}</div>
								</div>
							</c:if>
							<c:if test="${successMessage!=''  and successMessage !=null and successMessage != 'null' }">
								<div class="notification success png_bg">
									<a class="close">
										<img alt="关闭" title="关闭" src="${pageContext.request.contextPath }/resource/images/icons/cross_grey_small.png">
									</a>
									<div>${successMessage}</div>
								</div>
							</c:if>
							<table>
								<thead>
									<tr>
									   <th>角色名</th>
									   <th>状态</th>
									   <th>备注</th>
									   <th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${list!=null }">
							      		<c:forEach items="${list}" var="role">
							        	<tr>
								          <td>${role.name }</td>
								          <td>${define:trans_status(role.status)}</td>
								          <td>${role.description }</td>
							              <td>
							              	<a href="javascript:modifRole('${role.id }')" title="修改"><img src="${pageContext.request.contextPath }/resource/images/icons/pencil.png" alt="修改" title="修改" /></a>
							                <a href="javascript:deletRole('${role.id }')" title="删除"><img src="${pageContext.request.contextPath }/resource/images/icons/cross.png" alt="删除" title="删除" /></a>
							               
							               </td>
							            </tr>
							       		</c:forEach>
							    	</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>	
</body>
</html>
<script>

$(function(){
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/user/to_addRole.shtml';
	});
});


function modifRole(id){
	window.location='${pageContext.request.contextPath }/user/to_updateRole.shtml?id='+id;
}

function deletRole(id){
	window.location='${pageContext.request.contextPath }/user/deleteRole.shtml?id='+id;
}



</script>