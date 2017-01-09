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
	<form id="mainForm" action="${pageContext.request.contextPath }/user/toUserList.shtml" method="post">
		<div id="body-wrapper">
			<div id="main-content">
				<div class="content-box">
					<div class="content-box-header">
						<h3>用户管理</h3>
						<div class="clear"></div>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab">
							<p style="margin-bottom: 10px;">
								<input type="button" class="button" id="serchButton" value="搜&nbsp;索">
								&nbsp;&nbsp;
								<input type="button" class="button" id="addButton" value="新&nbsp;增">
								&nbsp;&nbsp;
								<input type="button" class="button" value="返回" id="fanhui" onclick="history.go(-1)"/>
							</p>
							<input type="hidden" name="houseId" id="houseId" value="${houseId }"/>
							<table>
								<thead>
									<tr>
									    <th width="10%">日期</th>
									   <th width="10%">是否旺季</th>
									   <th width="10%">普通预订</th>
									   <th width="10%">普通价</th>
									   <th width="10%">会员预订</th>
									   <th width="10%">会员价</th>
									    <th width="20%">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${houseList!=null }">
							      		<c:forEach items="${houseList}" var="price">
							        	<tr>
							        	   <td>${price.date }</td>
								          <td>${price.inSeason ? "是" : "否" }</td>
								          <td>${price.freezeAmt }</td>
								          <td>${price.totalAmt }</td>
								          <td>${price.memFreezeAmt}</td>
								          <td>${price.memTotalAmt }</td>
							              <td>
							              	<a href="javascript:modifUser('${price.date }','${price.houseId }')" title="修改"><img src="${pageContext.request.contextPath }/resource/images/icons/pencil.png" alt="修改" title="修改" /></a>
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
		$("#mainForm").attr("action","${pageContext.request.contextPath }/house/toHouseDateList.shtml?houseId="+$("#houseId").val());
		$("#mainForm").submit();
	});
	$("#addButton").click(function(){
		var houseId=$('#houseId').val();
		window.location='${pageContext.request.contextPath }/house/toAddHouseDateList.shtml?houseId='+houseId;
	});
});
function addUser(){
	window.location='${pageContext.request.contextPath }/user/to_addUser.shtml';
}
function modifUser(date,houseId){
	window.location='${pageContext.request.contextPath }/house/toUpdateHouseDateList.shtml?dates='+date+"&houseId="+houseId;
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
}
</script>