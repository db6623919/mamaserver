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
						<h3>${flowType }记录
						</h3>
						<div class="clear"></div>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab">
							<p style="margin-bottom: 10px;">
								<input type="button" class="button" id="fanhui" value="返&nbsp;回">
							</p>
							<table>
								<thead>
									<tr>
									   <th width="25%">${flowType }金额</th>
									   <th width="25%">${flowType }时间</th>
									   <th width="25%">${flowType }类型</th>
									   <th width="25%">${flowType }描述</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${pager.flowList != null }">
							      		<c:forEach items="${pager.flowList}" var="flow">
								        	<tr>
									          <td>${flow.amt }</td>
									          <td>${flow.operTime }</td>
									          <td>
									             <c:if test="${flow.type==5}">充值</c:if>
									             <c:if test="${flow.type==6}">充值</c:if>
									             <c:if test="${flow.type==7}">奖励</c:if>
									             <c:if test="${flow.type==4}">支付</c:if>
									             <c:if test="${flow.type==1}">冻结</c:if>
									          </td>
									          <td>${flow.showDetail}</td>
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
	<form id="backForm" action="" method="post"></form>
</body>
</html>
<script>
$(function(){
	$("#serchButton").click(function(){
		$("#mainForm").attr("action","${pageContext.request.contextPath }/member/toMemberList.shtml?phonemy="+$('#phone').val()+"&name="+$('#name').val());
		$("#mainForm").submit();
	});
	$("#fanhui").click(function(){
		history.go(-1);
	});
	/* $("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/house/houseAddUpdate.shtml?flag=add';
	}); */
});
function cardInfo(uid){
	var phone = $("#phone").val();
	window.location='${pageContext.request.contextPath }/member/toCardInfo.shtml.shtml?id='+uid+"&phone="+phone;
}

function collectList(uid){
	var phone = $("#phone").val();
	window.location='${pageContext.request.contextPath }/member/toCollectList.shtml.shtml?id='+uid+"&phone="+phone;
}
function recharge(uid,name,phone){
	
	window.location='${pageContext.request.contextPath }/member/toRechargePage.shtml.shtml?id='+uid+"&phone="+phone+"&name="+name;
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