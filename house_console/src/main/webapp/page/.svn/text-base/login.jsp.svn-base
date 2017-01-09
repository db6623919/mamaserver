<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.springframework.security.web.WebAttributes"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="title"/></title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.2.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/backstageStyle.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/invalid.css" type="text/css" media="screen" />	
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/simpla.jquery.configuration.js"></script> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/js/prompt/ymPrompt_source.js"></script> --%>
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/prompt/skin/vista/ymPrompt.css" /> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/RSA.js"></script> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/BigInt.js"></script> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/Barrett.js"></script> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/facebox.js"></script> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/resource/scripts/jquery.wysiwyg.js"></script> --%>
</head>
	<body id="login">
		<div id="login-wrapper" class="png_bg">
			<div id="login-top">
				<h1>妈妈资本</h1>
				<a><img id="logo" src="${pageContext.request.contextPath }/resource/images/logo.png" /></a>
			</div>
			<div id="login-content">
				<form id="iform" action="login" method="post">
					<div id="message">
						<div id="detail"></div>
					</div>
					<c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message!=null}">
						<div id="message2" class="notification information png_bg">
							<div id="detail2">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</div>
						</div>
					</c:if>
					<div class="clear"></div>
					<p >
						<label>用户名</label>
						<input id="j_username" name="j_username" class="text-input" type="text" value=""/>
					</p>
					<div class="clear"></div>
					<p>
						<label>登录密码</label>
						<input id="j_password" name="j_password" class="text-input" type="password" value=""/>
					</p>
					<div class="clear"></div>
					<p>
						<label>验证码：</label>
	                	<input class="text-input" id="j_validatecode" name="j_validatecode" maxlength="4" type="text"/>
					</p>
					<div class="clear"></div>
					<p>
						<div style="float: left; padding-left: 88px;">
							<img id="divImg" alt="random" src="${pageContext.request.contextPath }/randomcode" style="width:70px;height:27px;cursor: pointer; vertical-align:bottom;">
			                <a id="checkcode-text" style="cursor: pointer;">换一张</a>
		                </div>
		                <div style="float: right; " ><input style="width: 70px;"  class="button" type="button" id="buttonSubmit" onclick="submitForm();" value="登录" /></div>
					</p>

				</form>
			</div>
		</div>
	 </body>
</html>
<script type="text/javascript">
	$(function() {
		$("#divImg").click(function(){
			$("#divImg").attr("src", "${pageContext.request.contextPath }/randomcode?time="+new Date().getTime());
		});
		$("#checkcode-text").click(function(){
			$("#divImg").attr("src", "${pageContext.request.contextPath }/randomcode?time="+new Date().getTime());
		});
	});
	
	function submitForm(){
		var isPass=true;
		if($("#j_username").val()==""){
			$("#message2").removeAttr("class");
			$("#detail2").html("");
			$("#message").attr("class","notification information png_bg");
			$("#detail").html("请输入用户名");
			$("#j_username").focus();
			isPass = false;
			return false;
		}
		if($("#j_password_value").val()==""){
			$("#message2").removeAttr("class");
			$("#detail2").html("");
			$("#message").attr("class","notification information png_bg");
			$("#detail").html("请输入登录密码");
			$("#j_password_value").focus();
			isPass = false;
			return false;
		}
		if($("#j_validatecode").val()==""){
			$("#message2").removeAttr("class");
			$("#detail2").html("");
			$("#message").attr("class","notification information png_bg");
			$("#detail").html("请输入验证码");
			$("#j_validatecode").focus();
			isPass = false;
			return false;
		}
		$("#message").removeAttr("class");
		$("#detail").html("");
		$("#iform").submit();
	}
	
	$(document).keydown(function (e) {
		if (e.which == 13) {
			$("#buttonSubmit").focus();
			submitForm();
		}
	}); 
</script>
