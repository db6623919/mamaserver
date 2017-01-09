<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- 
<%@ page import="com.mybank.enterprise.framework.auth.ShiroUser,org.apache.shiro.SecurityUtils;" %>
 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="title"/></title>
<c:if test="${session_localval == 'en_US' }">
	<link href="${pageContext.request.contextPath}/css/index_en.css" rel="stylesheet" type="text/css">
</c:if>
<c:if test="${session_localval == 'zh_TW' or session_localval == null or session_localval=='' }">
	<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css">
</c:if>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.2.min.js"></script>

</head>

<body>
      <div class="top_bg clearfix">
           <div class="wrap">
                <div class="t_l"><span></span>當前用戶： <a href="#" class="a1"><shiro:principal property="name" /></a>&nbsp;<font class="f1"> | </font>&nbsp;<a href="#" class="a2">退出</a></div>
           </div> 
      </div>
      
      <div class="header_bg clearfix">
           <div class="wrap">
                <div class="logo business_logo"></div>
                <div class="hb_nav">
                         <span></span>
                         
                          <c:forEach var="menu" items="${menuList }">
						  	<c:if test="${menu.type=='1'}">
						  		<c:if test="${menu.id=='1'}">
						  			<a href="#" class="nav_link current">${menu.name }</a>
						  		</c:if>
						  		<c:if test="${menu.id!='1'}">
						  			<a href="#" class="nav_link">${menu.name }</a>
						  		</c:if>
						  		<div class="nb_nav">
						  			 <c:forEach var="me" items="${menu.subMenu }">
						  			 	<c:if test="${me.privilege =='' || me.privilege =='#'}">
						  			 		<a href="#">${me.name }</a>
						  			 	</c:if>
						  			 	<c:if test="${me.privilege !='' && me.privilege !='#'}">
						  			 		<a href="${pageContext.request.contextPath}/${me.privilege }" target="main">${me.name }</a>
						  			 	</c:if>						  			 	
						  			 </c:forEach>
                             	</div>

						  	</c:if>
						  </c:forEach>
                </div>
           </div>
           <div class="nav_bg"></div>
      </div>
      
</body>
</html>


<script type="text/javascript">
 
        $(function(){
			
			//$.ajaxSetup ({ 
//				cache: false 
//				});
//				
//			$(".nav_link").click(function(){
//                
//				var linkurl     = $(this).attr("href");
//				var linkhtmlurl = $(this).attr("href").substring(1, linkurl.length);
//				var content  = $("#content");
//				content.load(linkhtmlurl);
//		        $(this).addClass("current").siblings().removeClass("current");
//				
//			    });
            $(".nb_nav").eq(0).show().siblings(".nb_nav").hide();
            $(".nb_nav a:first").addClass("current");
            $(".nav_link").click(function(){
				$(this).addClass("current").siblings().removeClass("current");
				$(".nb_nav").eq($(".nav_link").index(this)).show().siblings(".nb_nav").hide();
				});
				
		    $(".nb_nav a").click(function(){
				$(this).addClass("current").siblings().removeClass("current");
				});		
			
			})
  
</script>
