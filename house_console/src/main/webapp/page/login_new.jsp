<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>妈妈资本后台</title>
	</head>

	<body class="bg-login">
		<div class="container-login">
			<header class="text-center">
				<h1>妈妈资本后台管理</h1>
			</header>
			<c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message!=null}">
					${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
			</c:if>
			<form action="login" method="post" id="loginForm" class="form-horizontal">
				<div class="form-group col-xs-12 mg-left-0 mg-right-0 pd-left-0 pd-right-0">
					<div class="error alert-danger pd-left-15"></div>
				</div>
				<div class="form-group">
					<label for="j_username" class="col-xs-3 ">用&nbsp;户&nbsp;名</label>
					<div class="col-xs-9">
						<input type="text" name="j_username" class="form-control">
					</div>

				</div>
				<div class="form-group">
					<label for="j_password" class="col-xs-3">登录密码</label>
					<div class="col-xs-9">
						<input type="password" name="j_password" class="form-control">
					</div>

				</div>
				<div class="form-group">
					<label for="j_validatecode" class="col-xs-3">验&nbsp;证&nbsp;码</label>
					<div class="col-xs-9">
						<input type="text" id="vcode" name="j_validatecode" class="form-control">
					</div>

				</div>
				<div class="form-group">
					<div class="col-xs-6">
						<img src="${pageContext.request.contextPath }/randomcode?r=Math.random()" id="divImg" class="pointer">
						<label class="color-39"><a id="ck" class="pointer">换一张</a></label>
					</div>
					<div class="col-xs-6 text-right">
						<button type="submit" class="btn btn-style">登录</button>
					</div>
				</div>
			</form>
		</div>
	</body>
	<%@include file="/page/common/foot.jsp" %>
	<script>
	$().ready(function() {
		$("#divImg").click(function(){
			$("#divImg").attr("src", "${pageContext.request.contextPath }/randomcode?r=Math.random()");
		});
		$("#ck").click(function(){
			$("#divImg").attr("src", "${pageContext.request.contextPath }/randomcode?r=Math.random()");
		});
		
		
		$("#loginForm").validate({
			rules : {
				j_username : "required",
				j_password : 'required'
			},
			messages : {
				j_username : "请输入用户名",
				j_password : '请输入密码'
			}
		});
	});
	</script>

</html>