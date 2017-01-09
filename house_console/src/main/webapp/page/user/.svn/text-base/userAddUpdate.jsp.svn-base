<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>妈妈资本后台</title>
</head>
<body>
	<div id="body-wrapper">
		<div id="main-content">
			<div class="content-box">
				<div class="content-box-header">
					<h3>
						<c:if test="${flag=='add' }">新增</c:if><c:if test="${flag=='update' }">修改</c:if>用户
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post">
							<input id="id" type="hidden" name="id" value="${user.id }" />
							
							
							<fieldset>
								<c:if test="${flag=='add' }">
									<p>
										<span class="public_title"><font color="red">*</font>&nbsp;用户名：</span>
										<input type="text" value="" name="username" class="text-input small-input" datatype="name" nullmsg="请输入用户名！" errormsg="用户名至少2个字符，最多20个字符，且以字母开头！"  /><span class="Validform_checktip"></span>
									</p>
								</c:if>
								<c:if test="${flag=='update' }">
									<p>
										<span class="public_title">用户名：</span>
										<span id="update_username">${user.username }</span>
									</p>
								</c:if>
								<p>
									<span class="public_title">姓&nbsp;名：</span>
									<input type="text" value="${user.name}" name="name" class="text-input small-input" nullmsg="请输入姓名！" /><span class="Validform_checktip"></span>
								</p>
								<p>
									<span class="public_title">状&nbsp;态： </span>
									<select name="status" id="state" class="small-input">
										<c:if test="${user.status =='0' }">
											<option value="1">启用</option>
											<option value="0" selected="selected">禁用</option>
										</c:if>
										<c:if test="${user.status !='0' }">
											<option value="1" selected="selected">启用</option>
											<option value="0">禁用</option>
										</c:if>
									</select> 
								</p>
								<p>
									<span class="public_title">角&nbsp;色： </span>
									<c:if test="${flag=='add' }">
										<c:forEach items="${allRoleList }" var="allrole">
										
									
												<c:if test="${allrole.id == '1'}">
													<input type="radio" name="role" value="${allrole.id }"  checked="checked">${allrole.name }&nbsp;
												</c:if>
												<c:if test="${allrole.id != '1'}">
													<input type="radio" name="role" value="${allrole.id }"  >${allrole.name }&nbsp;
												</c:if>	
																
										</c:forEach>								
									</c:if>
									<c:if test="${flag=='update' }">
										<c:forEach items="${hasRoleList }" var="hasrole">		
												<c:forEach items="${allRoleList }" var="allrole">
													
														<c:if test="${hasrole.id == allrole.id}">
															<input type="radio" name="role" value="${allrole.id }"   checked="checked">${allrole.name }&nbsp;
														</c:if>
														<c:if test="${hasrole.id != allrole.id}">
															<input type="radio" name="role" value="${allrole.id }"   >${allrole.name }&nbsp;
														</c:if>
																											
												</c:forEach>								
										</c:forEach>
									</c:if>
								</p>
								
								<p id="mchntDIV" style="display: none">
									<span id="span_sh_qy" class="public_title"></span>
									<input id="chndes" type="text" class="text-input small-input" value="${mchntChnDes }" name="chndes" readonly="readonly" />&nbsp;
								</p>
								<p>
									<span class="public_title">备&nbsp;注：</span>
									<input id="remark" class="text-input small-input" type="text" name="description" value="${user.description}" />
								</p>
								<p>
									<span class="public_title">&nbsp;</span>
									<c:if test="${flag=='add' }">
										<input type="submit" class="button" value="增加" />
									</c:if> 
									<c:if test="${flag=='update' }">
										<input type="submit" class="button" value="修改" />
									</c:if>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="button" value="返回" id="fanhui" />
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
$("#mainForm").Validform({
	tiptype:function(msg,o,cssctl){
		if(!o.obj.is("form")){
			var objtip=o.obj.siblings(".Validform_checktip");
			cssctl(objtip,o.type);
			objtip.text(msg);
		}
	},
	tipSweep:false,
	showAllError:true,
	postonce:true,//为true时，在数据成功提交后，表单将不能再继续提交。 
	beforeCheck:function(curform){
		//alert("在表单提交执行验证之前执行的函数,这里明确return false的话将不会继续执行验证操作");	
	},
	beforeSubmit:function(curform){
		if('${flag}'=='add'){
			$("#mainForm").attr("action","${pageContext.request.contextPath}/user/saveUser.shtml");
		}else{
			$("#mainForm").attr("action","${pageContext.request.contextPath}/user/updateUser.shtml?id="+$("#id").val()+""+"&username="+$("#update_username").text());
		}
	}
});

//打开商户选择
function openMchnt(mchntId){
	var flag;
	var item = $(":radio:checked"); 
	var len=item.length;
	if(len>0){ 
	  if($(":radio:checked").val()=='2'){  
		  flag = "sh";
	  }else if($(":radio:checked").val()=='4'){  
		  flag = "qy";		
	  } else{
		  flag = "sh";
	  }
	} 
	openWindow("system!to_mchnt.shtml?flag="+flag+"&s_mchntid="+mchntId,"商户选择",800,600);
}

//点击商户/企业角色时默认显示div
function checkradio(){ 
	var item = $(":radio:checked"); 
	var len=item.length; 
	if(len>0){ 
		
	  if($(":radio:checked").val()=='2'){  
		  $("#span_sh_qy").html("商     户：");
		  $("#mchntDIV").show();		
	  }else if($(":radio:checked").val()=='4'){  
		  $("#span_sh_qy").html("企     业：");
		  $("#mchntDIV").show();		
	  } else{
		  
		  $("#mchntDIV").hide();
	  }
	} 
}

//为商户/企业角色时默认显示div
$(function(){
	var item = $(":radio:checked"); 
	var len=item.length; 
	if(len>0){ 
		
//		  if($(":radio:checked").val()=='2'){  
//			  $("#span_sh_qy").html("商     户：");
//			  $("#mchntDIV").show();		
//		  }else if($(":radio:checked").val()=='4'){  
//			  $("#span_sh_qy").html("企     业：");
//			  $("#mchntDIV").show();		
//		  } else{
			  $("#mchntDIV").hide();
//		  }
	}
	$("#fanhui").click(function(){
		$("#backForm").attr("action", "${pageContext.request.contextPath }/user/to_user.shtml?successMessage=&errorMessage=");
		$("#backForm").submit();
	});
});
</script>