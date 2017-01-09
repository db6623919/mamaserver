<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-ex1-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="index.html">妈妈送房管理后台</a>
	</div>

	<ul class="nav navbar-right top-nav">
		<li class="dropdown"><a href="#">管理员</a></li>
		<li class="dropdown"><a href="${pageContext.request.contextPath }/user/to_changePwd.shtml">修改密码</a></li>
		<li class="dropdown"><a href="${pageContext.request.contextPath }/timeoutLogout.shtml">退出</a></li>
	</ul>

	<div class="collapse navbar-collapse navbar-ex1-collapse">
		<ul class="nav navbar-nav side-nav">
			<li ${param.menu==0?"class='active'":''}>
					<a href="${pageContext.request.contextPath }/main.shtml"><i class="fa fa-fw fa-dashboard"></i> 首页</a>
			</li>
			<c:forEach items="${menuList}" var="menu" varStatus="status" >
				<li ${param.menu==status.count?"class='active'":''}>
					<a href="javascript:;" data-toggle="collapse" data-target="#myMenu_${status.count}">
						${menu.name }
						 <i class="fa fa-fw fa-caret-down"></i>
					</a>
					<c:if test="${not empty menu.subMenu}" >
						<ul id="myMenu_${status.count}" class="collapse ${param.menu==status.count?'in':''} ">
							<c:forEach items="${menu.subMenu}" var="submenu" varStatus="sub_status">
								<li ${(param.menu==status.count)&&(param.index==sub_status.count)?"class='active'":''}>
									<a href="${pageContext.request.contextPath}/${submenu.privilege }" >${submenu.name }</a>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</li>
			</c:forEach>
		</ul>
	</div>
	<!-- /.navbar-collapse -->
</nav>