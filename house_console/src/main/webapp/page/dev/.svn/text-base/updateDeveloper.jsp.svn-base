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
					<h3>新增楼盘
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post">
							<fieldset>
							<input type="hidden" name="devId" value="${developer.devId }">
							<p>
									<span class="public_title"><font color="red">*</font>&nbsp;名称：</span>
									<input type="text" value="${developer.devName }" id="devName" name="devName" class="text-input small-input" style="width: 100px" width="60px"  />								
									</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;类型：</span>
									<select id="type" name="type">
									   <option value="1"  <c:if test="${developer.type=='1' }">selected="selected"</c:if> >热门</option>
									   <option value="2" <c:if test="${developer.type=='2' }">selected="selected"</c:if>>推荐</option>
									</select>							
									</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;联系电话：</span>
									<input type="text" value="${developer.telphone }" id="telphone" name="telphone" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;开发商图片：</span>
									<input type="text" value="${developer.devImg }" id="devImg" name="devImg" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;开发商视频：</span>
									<input type="text" value="${developer.video }" id="video" name="video" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;开发商介绍：</span>
									<input type="text" value="${developer.description }" id="description" name="description" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								
							<p>
									<span class="public_title"><font color="red">*</font>&nbsp;开发商标签：</span>
									<input type="text" value="${developer.mark }" id="mark" name="mark" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title">&nbsp;</span> <input type="submit"	class="button" value="更新" /> &nbsp;&nbsp;&nbsp;&nbsp; 
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
								$("#mainForm").attr("action","${pageContext.request.contextPath}/dev/updateDeveloper.shtml");
						
						}
					});

	//打开商户选择
	function openMchnt(mchntId) {
		var flag;
		var item = $(":radio:checked");
		var len = item.length;
		if (len > 0) {
			if ($(":radio:checked").val() == '2') {
				flag = "sh";
			} else if ($(":radio:checked").val() == '4') {
				flag = "qy";
			} else {
				flag = "sh";
			}
		}
		openWindow("system!to_mchnt.shtml?flag=" + flag + "&s_mchntid="
				+ mchntId, "商户选择", 800, 600);
	}

	//点击商户/企业角色时默认显示div
	function checkradio() {
		var item = $(":radio:checked");
		var len = item.length;
		if (len > 0) {
			if ($(":radio:checked").val() == '2') {
				$("#span_sh_qy").html("商     户：");
				$("#mchntDIV").show();
			} else if ($(":radio:checked").val() == '4') {
				$("#span_sh_qy").html("企     业：");
				$("#mchntDIV").show();
			} else {
				$("#mchntDIV").hide();
			}
		}
	}

	//为商户/企业角色时默认显示div
	$(function() {
		var item = $(":radio:checked");
		var len = item.length;
		if (len > 0) {
			if ($(":radio:checked").val() == '2') {
				$("#span_sh_qy").html("商     户：");
				$("#mchntDIV").show();
			} else if ($(":radio:checked").val() == '4') {
				$("#span_sh_qy").html("企     业：");
				$("#mchntDIV").show();
			} else {
				$("#mchntDIV").hide();
			}
		}
		$("#fanhui")
				.click(
						function() {
							history.go(-1);
						});
	});
	var index=0;
	function addDiv(id,divId){
		var html=$('#'+divId).html();
		$('#'+divId).html(html+"<p id='"+index+"'><span class='public_title'><font color='red'>*</font></span>名称<input type='text' value=''  name='introductionName' class='text-input' style='width: 140px'/>路径<input type='text' value=''  name='"+id+"' class='text-input' style='width: 160px'/><span class='Validform_checktip'></span><input type='button' id='label_Btn' value='删除' onclick='deleteDiv("+index+")'/></p>");
		index++;
	}
	function deleteDiv(id){	
		$('#'+id).remove();
	}
</script>