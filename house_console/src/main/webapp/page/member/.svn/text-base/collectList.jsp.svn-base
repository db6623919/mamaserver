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
</head>
<body>
	<form id="mainForm" method="post"><!-- action="${pageContext.request.contextPath }/user/toUserList.shtml" method="post"> -->
		<div id="body-wrapper">
			<div id="main-content">
				<div class="content-box">
					<div class="content-box-header">
						<h3>会员收藏列表</h3>
						<div class="clear"></div>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab">
							<p style="margin-bottom: 10px;">
								<input type="button" class="button" id="serchButton" value="返&nbsp;回">
								<!-- &nbsp;&nbsp;
								<input type="button" class="button" id="addButton" value="新&nbsp;增"> -->
							</p>
							<c:if test="${errorMessage!='' and errorMessage !=null and errorMessage != 'null' }">
								<div class="notification error png_bg">
									<a class="close">
										<img alt="查询" title="查询" src="${pageContext.request.contextPath }/resource/images/icons/cross_grey_small.png">
									</a>
									<div>${errorMessage}</div>
								</div>
							</c:if>
							<c:if test="${successMessage!=''  and successMessage !=null and successMessage != 'null' }">
								<div class="notification success png_bg">
									<a class="close">
										<img alt="新增" title="新增" src="${pageContext.request.contextPath }/resource/images/icons/cross_grey_small.png">
									</a>
									<div>${successMessage}</div>
								</div>
							</c:if>
							<table>
								<thead>
									<tr>
									   <th width="20%">房屋编号</th>
									   <th width="10%">收藏时间</th>
									   <!-- <th width="20%">身份证号码</th>
									   <th width="15%">手机号码</th>
									   <th width="15%">邮箱</th>
									   <th width="10%">渠道</th> -->
									  
									</tr>
								</thead>
								<tbody>
									<c:if test="${pager.collectList != null }">
							      		<c:forEach items="${pager.collectList}" var="collect">
								        	<tr>
									          <td>${collect.houseId }</td>
									          <td>${collect.operTime }</td>
									          <%-- <td>${collect.idCard}</td>
									          <td>${collect.phone}</td>
									          <td>${collect.email}</td> --%>
									          <%-- <td><c:choose>
									          		<c:when test="${member.channel == '0' }">无效渠道</c:when>
									          		<c:when test="${member.channel == '1' }">网页</c:when>
									          		<c:when test="${member.channel == '2' }">IOS</c:when>
									          		<c:when test="${member.channel == '3' }">安卓</c:when>
									          </c:choose> --%>
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
		$("#mainForm").attr("action","${pageContext.request.contextPath }/member/toMemberList.shtml?phonemy=${phone}");
		$("#mainForm").submit();
	});
	/* $("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/house/houseAddUpdate.shtml?flag=add';
	}); */
});
function cardInfo(uid,phone){
	window.location='${pageContext.request.contextPath }/member/toCardInfo.shtml.shtml?id='+uid+"&phone="+phone;
}
/* function modifUser(id,username){
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
} */
</script>