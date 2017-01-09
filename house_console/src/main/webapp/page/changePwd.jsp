<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/RSA.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/BigInt.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rsajs/Barrett.js"></script>
<title>妈妈送房后台</title>
</head>
<body >
	<div id="body-wrapper">
		<div id="main-content">
			<div class="content-box">
				<div class="content-box-header">
					<h3>
						修改登录密码
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
<%-- 						<c:if test="${errorMessage!='' and errorMessage !=null and errorMessage != 'null' }"> --%>
							<div class="notification error png_bg">
								<a class="close">
									<img alt="关闭" title="关闭" src="${pageContext.request.contextPath }/resource/images/icons/cross_grey_small.png">
								</a>
								<div>错误${errorMessage}</div>
							</div>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${successMessage!=''  and successMessage !=null and successMessage != 'null' }"> --%>
							<div class="notification success png_bg">
								<a class="close">
									<img alt="关闭" title="关闭" src="${pageContext.request.contextPath }/resource/images/icons/cross_grey_small.png">
								</a>
								<div>成功${successMessage}</div>
							</div>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${successMessage!=''  and successMessage !=null and successMessage != 'null' }"> --%>
							<div class="notification information png_bg">
								<a class="close">
									<img alt="关闭" title="关闭" src="${pageContext.request.contextPath }/resource/images/icons/cross_grey_small.png">
								</a>
								<div>提示${successMessage}</div>
							</div>
<%-- 						</c:if> --%>
						<form id="mainForm" action="${pageContext.request.contextPath }/system!updatePwdForm.shtml" method="post">
							<fieldset>
								<p>
									<span class="public_title">原始密码：</span>
									<input type="password" id="oldPwd" name="oldPwd" tabindex="1" class="text-input small-input" datatype="pwd6-20" nullmsg="请输入原始密码" errormsg="密码至少6个字符，最多20个字符，由字母或数字组成" /><span class="Validform_checktip">请输入原始密码</span>
								</p>
								<p>
									<span class="public_title">新密码：</span>
									<input type="password" id="newPwd" name="newPwd" tabindex="2" class="text-input small-input" datatype="pwd6-20" nullmsg="请输入新密码" errormsg="密码至少6个字符，最多20个字符，由字母或数字组成" /><span class="Validform_checktip">请输入新密码</span>
								</p>
								<p>
									<span class="public_title">确认新密码：</span>
									<input type="password" id="newPwdTwo" name="newPwdTwo" tabindex="3" class="text-input small-input" datatype="*" recheck="newPwd" nullmsg="请再次输入新密码" errormsg="您两次输入的密码不相同" /><span class="Validform_checktip">请再次输入新密码</span>
								</p>
								<p>
									<span class="public_title">&nbsp;</span>
									<input type="submit" class="button" value="确定" tabindex="4" />
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="reset" class="button" value="清空" tabindex="5" />
								</p>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>	
</body>
</html>
<script type="text/JavaScript">
$("#mainForm").Validform({
	tiptype:function(msg,o,cssctl){
		//msg：提示信息;
		//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
		//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
		if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
			var objtip=o.obj.siblings(".Validform_checktip");
			cssctl(objtip,o.type);
			objtip.text(msg);
		}
	},
	tipSweep:false,
	showAllError:true,
	beforeCheck:function(curform){
		//验证之前调用
	},
	beforeSubmit:function(curform){
		setMaxDigits(130);
		var oldPwd = $("#oldPwd").val();
		var newPwd = $("#newPwd").val();
		var key = new RSAKeyPair("10001","","8a8e5cf64f3c9f34b8f6bb3186d0b8df44be58ae56c1f46e7042779ec7a7d923eb41373746643cae02481567fa8843f9a0faca3e682c2b5ff55c30f83bfe51f99c0dca336fbf799f1f6f21fac0e9c2994699b643d3b18e14a07f7f12b49d46ddd5fa3e4ea0e2c175ed90e267f5b9641534886a2b9ae354f8bb2320c9f79486dd");
		oldPwd = encryptedString(key, encodeURIComponent(oldPwd));
		newPwd = encryptedString(key, encodeURIComponent(newPwd));
		$("#oldPwd").val(oldPwd);
		$("#newPwd").val(newPwd);
	}
});
</script>
