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
	<form id="mainForm" method="post" action="${pageContext.request.contextPath }/member/toMemberList.shtml"><!-- action="${pageContext.request.contextPath }/user/toUserList.shtml" method="post"> -->
		<div id="body-wrapper">
			<div id="main-content">
				<div class="content-box">
					<div class="content-box-header">
						<h3>会员列表</h3>
						<div class="clear"></div>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab">
							<p style="margin-bottom: 10px;">
								<span>手机号码：</span><input id="phone" name="phone" class="search-input" style="width: 100px;" value="${phone }" />
								&nbsp;&nbsp;
								<input type="button" class="button" id="serchButton" value="搜&nbsp;索">
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
									   <th width="8%">昵称</th>
									   <th width="8%">姓名</th>
									   <th width="10%">身份证号码</th>
									   <th width="8%">手机号码</th>
									   <th width="8%">余额</th>
									   <th width="8%">冻结金额</th>
									   <th width="8%">充值金额</th>
									   <th width="8%">奖励金额</th>
									   <th width="50%">操     作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${pager.list != null }">
							      		<c:forEach items="${pager.list}" var="member">
								        	<tr>
									          <td>${member.nickName }</td>
									          <td>${member.name }</td>
									          <td>${member.idCard}</td>
									          <td>${member.phone}</td>
									          <td>${member.availAmt}</td>
									          <td>${member.freezeAmt}</td>
									          <td>${member.totalRechargeAmt}</td>
									          <td>${member.totalRewardAmt}</td>
									          <td>
									           <div style="float: right; " ><input style="width: 70px;"  class="button" type="button" id="buttonSubmit" onclick="cardInfo('${member.uid }');" value="会员卡信息" /></div>
									           <div style="float: right; " ><input style="width: 70px;"  class="button" type="button" id="buttonSubmit" onclick="collectList('${member.uid }');" value="收藏记录" /></div>
									           <div style="float: right; " ><input style="width: 70px;"  class="button" type="button" id="buttonSubmit" onclick="recharge('${member.uid }','${member.name }','${member.phone}');" value="充值" /></div>
									           <div style="float: right; " ><input style="width: 70px;"  class="button" type="button" id="buttonSubmit" onclick="flowList('${member.uid }',100);" value="充值记录" /></div>
									           <div style="float: right; " ><input style="width: 70px;"  class="button" type="button" id="buttonSubmit" onclick="flowList('${member.uid }',200);" value="奖励记录" /></div>
									           <div style="float: right; " ><input style="width: 70px;"  class="button" type="button" id="buttonSubmit" onclick="flowList('${member.uid }',300);" value="消费记录" /></div>
									           <div style="float: right; " ><input style="width: 70px;"  class="button" type="button" id="buttonSubmit" onclick="orderList('${member.uid }');" value="订单列表" /></div>
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
		$("#mainForm").attr("action","${pageContext.request.contextPath }/member/toMemberList.shtml?phone="+$('#phone').val()+"&name="+$('#name').val());
		$("#mainForm").submit();
	});
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
	window.location='${pageContext.request.contextPath }/member/toRechargePage.shtml.shtml?uid='+uid+"&phone="+phone+"&name="+name;
}
function flowList(uid,type){
	var phone = $("#phone").val();
	window.location='${pageContext.request.contextPath }/member/toFlowList.shtml.shtml?id='+uid+"&phone="+phone+"&type="+type;
}
function orderList(uid){
	var phone = $("#phone").val();
	window.location='${pageContext.request.contextPath }/member/toOrderList.shtml.shtml?id='+uid+"&phone="+phone;
}
</script>