<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Error_500_内部服务器错误</title>
	<style type="text/css">
	body{margin:8% auto 0;max-width: 520px; min-height: 20px;padding:10px;font-family:Arial;}p{color:#555;margin:10px 10px;}img {border:0px;}html{font-size:15px;}.d{color:#404040;}
	</style>
</head>
<body>
<a href="javascript:goHead();"><img width="400px" height="300px" src="${pageContext.request.contextPath }/images/errorpage.jpg" alt="mmsfang"/></a>
<p><b>500.</b> 抱歉! 内部服务器错误!</p>
<H1>错误：</H1><%=exception%>
<H2>错误内容：</H2>
<%-- <% exception.printStackTrace(response.getWriter()); %> --%>
<p><a href="javascript:history.go(-1);">返回上一步</a></p>
<!-- <P class="d">有关错误的详细信息，请联系系统管理员。</P> -->
<div>
<!-- 	错误堆栈信息：<br/> -->
<%-- 	<c:forEach var="trace" items="${pageContext.exception.stackTrace}"> --%>
<%-- 		<p>${trace}</p> --%>
<%-- 	</c:forEach> --%>
</div>
</body>
</html>

