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
						<h3>订单记录</h3>
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
									    <th width="20%">房源</th>
									   <th width="20%">订单时间</th>
									   <th width="20%">订单总金额</th>
									   <th width="20%">订单冻结金额</th>
									   <th width="20%">订单状态</th>
									   <th width="20%">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${pager.list != null }">
							      		<c:forEach items="${pager.list}" var="order">
								        	<tr>
								        	 <td>${order.houseName }</td>
									          <td>${order.operTime }</td>
									          <td>${order.totalAmt }</td>
									          <td>${order.freezeAmt}</td>
									          <td><c:if test="${order.status=='10' }">等待客服确认</c:if>
									              <c:if test="${order.status=='11' }">等待客户入住</c:if>
									              <c:if test="${order.status=='12' }">客户在店</c:if>
									              <c:if test="${order.status=='13' }">客户离店</c:if>
									              <c:if test="${order.status=='21' }">客户取消订单</c:if>
									              <c:if test="${order.status=='22' }">商户取消订单</c:if>
									              <c:if test="${order.status=='23' }">订单取消</c:if></td>
									          <td>
									          	  <a href="javascript:orderInfo('${uid }','${order.orderId }')" title="订单详情"><img src="${pageContext.request.contextPath }/resource/images/icons/pencil.png" alt="订单详情" title="订单详情" /></a>
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
function orderInfo(uid,orderId){
	window.location='${pageContext.request.contextPath }/member/toOrderInfo.shtml.shtml?id='+uid+"&orderId="+orderId+"&phone=${phone}";
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