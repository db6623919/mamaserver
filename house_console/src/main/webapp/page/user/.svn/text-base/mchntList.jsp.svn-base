<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>妈妈送房后台</title>
		<base target="_self">
	</head>
	<body>
		<form id="mainForm" action="${pageContext.request.contextPath }/system!to_mchnt.shtml" method="post">
			<input type="hidden" name="flag" value="${flag }" />
			<div id="body-wrapper">
				<div id="main-content">
					<div class="content-box">
						<div class="content-box-header">
							<h3>
							<c:if test="${flag =='sh' }">商户</c:if>
					    	<c:if test="${flag =='qy' }">企业</c:if>
							信息
							</h3>
							<div class="clear"></div>
						</div>
						<div class="content-box-content">
							<div class="tab-content default-tab">
								<p style="margin-bottom: 10px;">
									<span><c:if test="${flag =='sh' }">商户号：</c:if><c:if test="${flag =='qy' }">企业编号：</c:if></span>
									<input type="text" name="s_mchntid" class="search-input" style="width: 100px;" value="${s_mchntid }" />
									&nbsp;&nbsp;
									<span><c:if test="${flag =='sh' }">商户名：</c:if><c:if test="${flag =='qy' }">企业名：</c:if></span>
									<input type="text" name="s_chndes" class="search-input" style="width: 100px;" value="${s_chndes }" />
									&nbsp;&nbsp;
									<input type="submit" class="button" value="查&nbsp;询" />
									&nbsp;&nbsp;
									<input type="button" class="button" value="选&nbsp;择" onclick="choose();" />
								</p>
								<div id="message">
									<div id="detail"></div>
								</div>
								<table>
									<thead>
										<tr>
										   <th width="5%">&nbsp;</th>
										   <th width="20%">商户号</th>
										   <th width="20%">商户名</th>
										   <th width="10%">联系人</th>
										   <th width="20%">电     话</th>
										   <th width="20%">地     址</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${pager.list!=null }">
											<c:forEach items="${pager.list}" var="mchnt">
									        	<tr>
									        	  <td><input type="radio" name="mchnt" value="${mchnt.mchntId}|${mchnt.chndes}"></td>
										          <td>${mchnt.mchntId }</td>
										          <td>${mchnt.chndes }</td>
										          <td>${mchnt.man}</td>
										          <td>${mchnt.tel }</td>
									              <td>${mchnt.addr }</td>
									            </tr>
								       		</c:forEach>									
								    	</c:if>
									</tbody>
								</table>
								<jsp:include page="/page/common/backstagePager.jsp"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>	
	</body>
</html>
<script>
//选择商户
function choose(){
	$("#message2").removeAttr("class");
	$("#detail2").html("");
	var item = $(":radio:checked"); 
	var len=item.length; 
	if(len>0){ 
	  var arr = $(":radio:checked").val().split("|");
	  window.opener.document.getElementById("mchntId").value = arr[0];
	  window.opener.document.getElementById("chndes").value = arr[1];
	  window.close();
	}else{
		if('${flag}'=='sh'){
			$("#message").attr("class","notification information png_bg");
			$("#detail").html("请选择一个商户");
			return;
		}
		if('${flag}'=='qy'){
			$("#message").attr("class","notification information png_bg");
			$("#detail").html("请选择一个企业");
			return;
		}		
	} 
}
</script>