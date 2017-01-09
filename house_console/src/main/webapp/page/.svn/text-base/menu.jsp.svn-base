<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />		
	<base target="main" />
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.2.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/reset.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/backstageStyle.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/invalid.css" type="text/css" media="screen" />	
	<script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/simpla.jquery.configuration.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/facebox.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/jquery.wysiwyg.js"></script>
</head>
	<body target="main" style="background: #f0f0f0 url('${pageContext.request.contextPath }/resource/images/bg-body.gif') top left repeat-y;">
		<div id="body-wrapper">
			<div id="sidebar">
				<div id="sidebar-wrapper">
					<h1 id="sidebar-title"><a href="#">妈妈送房后台</a></h1>
					<img id="logo" src="${pageContext.request.contextPath }/resource/images/logo.png" />
					<div id="profile-links">
						您好, <a>${sessionScope.session_userinfo.name }</a>
						<br />
						<a href="${pageContext.request.contextPath }/user/to_changePwd.shtml" title="修改登录密码">修改密码</a> | <a href="${pageContext.request.contextPath }/timeoutLogout.shtml" title="退出系统">退出</a>
					</div>        
					<ul id="main-nav">  <!-- Accordion Menu -->
						<li>
							<a href="main.jsp" class="nav-top-item_home" >首页</a>       
						</li>
						<c:forEach items="${menuList}" var="menu">
							<li> 
								<a href="#" class="nav-top-item no-submenu">${menu.name }</a>
								<c:if test="${not empty menu.subMenu}">
									<ul>
										<c:forEach items="${menu.subMenu}" var="submenu">
											<li><a href="${pageContext.request.contextPath}/${submenu.privilege }" onclick="changeColor(this);" flag_color>${submenu.name }</a></li>
										</c:forEach>
									</ul>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	function changeColor(obj){
		var val1 = $(obj).attr("href").toString();
		var ins = $("[flag_color]");
		for(var i = 0; i < ins.length; i++){
			var val2 = ins[i].toString();
		 	if(val2.indexOf(val1)!=-1){
		 		$(obj).attr("style","color: #238E23");
		 	}else{
		 		$(ins[i]).attr("style","color: #A8A8A8");
		 	}
		}
	}
</script>