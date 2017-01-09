<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="title"/></title>
</head>
<div>
<h1>系統發生內部錯誤</h1>
<% Exception ex = (Exception)request.getAttribute("exception"); %>
<H2>Exception: <%= ex.getMessage()%></H2>
<P/>
<%ex.printStackTrace(new java.io.PrintWriter(out)); %>
</div>
</html>