<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Error_资源出现异常</title>
	<style type="text/css">
	body{margin:8% auto 0;max-width: 520px; min-height: 20px;padding:10px;font-family:Arial;}p{color:#555;margin:10px 10px;}img {border:0px;}html{font-size:15px;}.d{color:#404040;}
	</style>
</head>
<body>
<a href="javascript:goHead();"><img width="400px" height="300px" src="${pageContext.request.contextPath }/images/errorpage.jpg" alt="mmsfang"/></a>
<p><b>ERROR.</b> 抱歉! 您访问的资源出现异常!</p>
<p><a href="javascript:history.go(-1);">返回上一步</a></p>
<P><a href="javascript:hideException();">有关错误的详细信息，请联系系统管理员。</a></P>
<DIV style="display: none" id=infoBlockID>
	<P class="d" id=errorExplanation><s:property value="exception.class"/></P>
	<P class="d" id=errorExplanation><s:property value="exception.message"/></P>
	<P class="d" id=errorExplanation><s:property value="exception.stackTrace"/></P>
</DIV>
</body>
</html>
<script type="text/javascript">
	function hideException(){
		if($("#infoBlockID").attr("style")=="display: none"){
			$("#infoBlockID").attr("style", "display: ''");
		}else{
			$("#infoBlockID").attr("style", "display: none");
		}	
	}
</script>
