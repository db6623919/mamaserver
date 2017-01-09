<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="title"/></title>
<%@ include file="taglibs.jsp" %>
</head>
<body>

	  <c:if test="${success!=null }">
	  	<div class="yes clearfix"><span></span><font>${success}！</font></div>
	  </c:if>
	  <c:if test="${failure!=null }">
	  	<div class="no clearfix"><span></span><font>${failure}！</font></div>
	  </c:if>
	  
	  <c:if test="${backurl!=null }">
	  	<a href="${path}/${backurl}">返回</a>
	  </c:if>
	  <c:if test="${backurl==null }">
	  	<a href="${path}/index.shtml">返回首頁</a>
	  </c:if>
	  
</body>
</html>