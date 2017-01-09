<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp" %>
<%@ include file="/page/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>妈妈送房后台</title>
</head>
<body>
	<div id="body-wrapper">
		<div id="main-content">
			<div class="content-box">
				<div class="content-box-header">
					<h3>
						会员卡信息
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post">
							
							<fieldset>
								<p>
									<span class="public_title">会员卡号：</span>
									<span class="public_title">${card.cardId}</span>
								</p>
								<p>
									<span class="public_title">累计充值金额： </span>
									<span class="public_title">${card.totalRechargeAmt}</span>
								</p>
								<p>
									<span class="public_title">累计奖励金额： </span>
									<span class="public_title">${card.totalRewardAmt}</span>
								</p>
								<p>
									<span class="public_title">账户余额： </span>
									<span class="public_title">${card.availAmt}</span>
								</p>
								<p>
									<span class="public_title">冻结金额： </span>
									<span class="public_title">${card.freezeAmt}</span>
								</p>
								<p>
									<span class="public_title">会员卡等级： </span>
									<span class="public_title"><c:choose>
										<c:when test="${card.level == '1'}">铜卡</c:when>
										<c:when test="${card.level == '2'}">银卡</c:when>
										<c:when test="${card.level == '3'}">金卡</c:when>
										<c:when test="${card.level == '4'}">砖石卡</c:when>
									</c:choose></span>
								</p>
								<p>
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
/* $("#mainForm").Validform({
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
} */

//为商户/企业角色时默认显示div
$(function(){
	/* var item = $(":radio:checked"); 
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
	} */
	$("#fanhui").click(function(){
		$("#backForm").attr("action", "${pageContext.request.contextPath }/member/toMemberList.shtml?phonemy=${phone}");
		$("#backForm").submit();
	});
});
</script>