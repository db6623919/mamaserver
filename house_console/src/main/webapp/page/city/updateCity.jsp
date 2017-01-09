<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>妙购一卡通后台</title>
</head>
<body>
	<div id="body-wrapper">
		<div id="main-content">
			<div class="content-box">
				<div class="content-box-header">
					<h3>更新城市
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post" enctype="multipart/form-data">
						<input type="hidden" name="cityId" value="${Citys.cityId }"/>
							<fieldset>
							<p>
									<span class="public_title"><font color="red">*</font>&nbsp;名称：</span>
									<input type="text" id="cityName" name="cityName" class="text-input small-input" style="width: 100px" width="60px"  value="${Citys.cityName }" />								
									</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;省：</span>
									<select id="proId" name="proId">
										<c:forEach items="${proList}" var="pro">
									      <option value="${pro.provId }" <c:if test="${Citys.provId==pro.provId}">selected="selected"</c:if>>${pro.provName }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>
								</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;类型：</span>
									    <select id="type" name="type">
									      <option value="0" <c:if test="${Citys.type==0 }">selected="selected" </c:if>>既不热门也不推荐</option>
									      <option value="1" <c:if test="${Citys.type==1 }">selected="selected" </c:if> >热门</option>	
									       <option value="2" <c:if test="${Citys.type==2 }">selected="selected" </c:if>  >推荐</option>	
									         <option value="10" <c:if test="${Citys.type==2 }">selected="selected" </c:if>  >推荐+热门</option>						    
								        </select>
									<span class="Validform_checktip"></span>
								</p>						
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;背景图：</span>
									<input type="file"  id="img" name="img" class="text-input small-input" style="width: 140px" /><input type="text" name="oldImg" id="oldImg" value="${Citys.img }"/>
									更换图片
									<select id="imgFlag" name="imgFlag">
									    <option value="0">否</option>
									    <option value="1">是</option>
									</select>
									<span class="Validform_checktip"></span>
								</p>
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;城市介绍：</span>
									<input type="text" value="${Citys.description }" id="description" name="description" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								
		
								<p>
									<span class="public_title">&nbsp;</span> <input type="submit"	class="button" value="更新" /> &nbsp;&nbsp;&nbsp;&nbsp; 
									<input type="button" class="button" value="返回" id="fanhui" onclick="history.go(-1)" />
								</p>
								
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="backForm" action="" method="post"></form>
</body>
</html>
<script>
	$("#mainForm")
			.Validform(
					{
						tiptype : function(msg, o, cssctl) {
							if (!o.obj.is("form")) {
								var objtip = o.obj
										.siblings(".Validform_checktip");
								cssctl(objtip, o.type);
								objtip.text(msg);
							}
						},
						tipSweep : false,
						showAllError : true,
						postonce : true,//为true时，在数据成功提交后，表单将不能再继续提交。 
						beforeCheck : function(curform) {
							//alert("在表单提交执行验证之前执行的函数,这里明确return false的话将不会继续执行验证操作");	
						},
						beforeSubmit : function(curform) {
								$("#mainForm").attr("action","${pageContext.request.contextPath}/city/addCity.shtml?flag=update");
						
						}
					});

	var index=100;
	function addDiv(id,divId){
		var html=$('#'+divId).html();
		$('#'+divId).html(html+"<p id='"+index+"'><span class='public_title'><font color='red'>*</font></span>名称<input type='text' value=''  name='introductionName' class='text-input' style='width: 140px'/>路径<input type='text' value=''  name='"+id+"' class='text-input' style='width: 160px'/><span class='Validform_checktip'></span><input type='button' id='label_Btn' value='删除' onclick='deleteDiv("+index+")'/></p>");
		index++;
	}
	function deleteDiv(id){	
		$('#'+id).remove();
	}
</script>