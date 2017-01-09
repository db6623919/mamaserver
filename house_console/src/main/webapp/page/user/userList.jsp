<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/taglibs.jsp" %>
<%@ taglib prefix="define" uri="/WEB-INF/tld/define.tld"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title"/></title>
<base target="_self">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.2.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/backstageStyle.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/invalid.css" type="text/css" media="screen" />	
<script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/simpla.jquery.configuration.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/facebox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/jquery.wysiwyg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/prompt/ymPrompt.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/prompt/skin/vista/ymPrompt.css" type="text/css" media="screen" />
</head>
<body>
	<form id="mainForm" action="${pageContext.request.contextPath }/user/toUserList.shtml" method="post">
		<div id="body-wrapper">
			<div id="main-content">
				<div class="content-box">
					<div class="content-box-header">
						<h3>用户管理</h3>
						<div class="clear"></div>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab">
							<p style="margin-bottom: 10px;">
								<span>用户名：</span><input id="s_username" class="search-input" style="width: 100px;" value="${s_username }" />
								&nbsp;&nbsp;
								<span>姓     名：</span><input id="s_name" class="search-input" style="width: 100px;" value="${s_name }" />
								&nbsp;&nbsp;
								<input type="button" class="button" id="serchButton" value="搜&nbsp;索">
								&nbsp;&nbsp;
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
									   <th width="20%">用户名</th>
									   <th width="20%">姓     名</th>
									   <th width="20%">状     态</th>
									   <th width="20%">备     注</th>
									   <th width="20%">操     作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${pager.list!=null }">
							      		<c:forEach items="${pager.list}" var="user">
							        	<tr>
								          <td>${user.username }</td>
								          <td>${user.name }</td>
								          <td>${define:trans_status(user.status)}</td>
								          <td>${user.description }</td>
							              <td>
							              	<a href="javascript:modifUser('${user.id }','${user.username }')" title="修改"><img src="${pageContext.request.contextPath }/resource/images/icons/pencil.png" alt="修改" title="修改" /></a>
							              	<a href="javascript:deleteUser('${user.id }','${user.username }')" title="删除"><img src="${pageContext.request.contextPath }/resource/images/icons/cross.png" alt="删除" title="删除" /></a>
							              </td>
							            </tr>
							       		</c:forEach>
							    	</c:if>
								</tbody>
							</table>
							<jsp:include page="/page/common/pager.jsp"/>
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
	$("#serchButton").click(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/user/to_user.shtml?s_username="+$("#s_username").val()+"&s_name="+$("#s_name").val());
		$("#mainForm").submit();
	});
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/user/to_addUser.shtml';
	});
});
function addUser(){
	window.location='${pageContext.request.contextPath }/user/to_addUser.shtml';
}
function modifUser(id,username){
	window.location='${pageContext.request.contextPath }/user/to_updateUser.shtml?id='+id+"&username="+username;
}
function deleteUser(id,username){
	ymPrompt.confirmInfo({
		title:'删除确认提示',
		message:'确定要删除吗?',
        maskAlphaColor:'#000',//遮罩透明色
		dragOut:false,//不允许拖出页面
		handler:function(tp){
			//tp的值可以为点击【确定】= ok 或者 点击【取消】= cancle 或者 点击【X】= close
			if(tp=='ok'){
				//放点击确定后的方法
				$("#mainForm").attr("action","${pageContext.request.contextPath }/user/deleteUser.shtml?id="+id+"&username="+username);
				$("#mainForm").submit();
			} else {
				return;
			}
		}
	});
}
</script>