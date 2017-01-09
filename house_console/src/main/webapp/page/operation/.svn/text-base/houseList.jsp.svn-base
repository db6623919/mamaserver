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
	<form id="mainForm" action="${pageContext.request.contextPath }/house/toHouse.shtml" method="post">
		<div id="body-wrapper">
			<div id="main-content">
				<div class="content-box">
					<div class="content-box-header">
						<h3>房源管理</h3>
						<div class="clear"></div>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab">
							<p style="margin-bottom: 10px;">
								<span>城市：</span>				
								<c:forEach items="${cityInfoList}" var="city">
								  <c:if test="${city.checked==1 }">
								    <input type="checkbox" name="cities" checked="checked" value="${city.cityId }"/>${city.cityName }
								   </c:if>
								   <c:if test="${city.checked==0}">
								    <input type="checkbox" name="cities" value="${city.cityId }"/>${city.cityName }
								   </c:if>

								</c:forEach>
								<input type="submit" class="button" id="serchButton" value="搜&nbsp;索">
								&nbsp;&nbsp;
								<input type="button" class="button" id="addButton" value="新&nbsp;增">
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
									   <th width="10%">城市</th>
									   <th width="20%">房源</th>
									   <th width="20%">普通预订</th>
									   <th width="10%">普通价</th>
									   <th width="20%">会员预订</th>
									   <th width="10%">会员价</th>
									    <th width="10%">操作</th>
									</tr>
								</thead>
								<tbody>
							      		<c:forEach items="${pager.list}" var="house">
							        	<tr>
							        	<td>
								             <c:forEach items="${cityInfoList}" var="city">
									              <c:if test="${house.cityId==city.cityId }">
										               ${city.cityName }	
										          </c:if>						    
										     </c:forEach>
								          </td>
							        	 <td>${house.summaryInfo }</td>
								          <td>${house.freezeAmt }</td>
								          <td>${house.totalAmt }</td>
								          <td>${house.memFreezeAmt}</td>
								          <td>${house.memTotalAmt }</td>
							              <td>
							                <a href="javascript:houseDateList('${house.houseId }')" title="价格管理">价格管理</a>
							              	<a href="javascript:modifUser('${house.houseId }')" title="修改">修改</a>
							              </td>
							            </tr>
							       		</c:forEach>
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
	$("#addButton").click(function(){
		window.location='${pageContext.request.contextPath }/house/houseAddUpdate.shtml?flag=add';
	});
});
function houseDateList(houseId){
	window.location='${pageContext.request.contextPath }/house/toHouseDateList.shtml?houseId='+houseId;
}
function modifUser(id){
	window.location='${pageContext.request.contextPath }/house/to_houseUpdate.shtml?houseId='+id;
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