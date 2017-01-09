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
						订单详情
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post">
							
							<fieldset>
								<p>
									<span class="public_title">订单编号： </span>
									<span >${order.orderId}</span>
								</p>
								<p>
									<span class="public_title">房屋编号： </span>
									<span >${order.houseId}</span>
								</p>
								<p>
									<span class="public_title">订单冻结金额： </span>
									<span >${order.freezeAmt}</span>
								</p>
								<p>
									<span class="public_title">订单总金额： </span>
									<span>${order.totalAmt}</span>
								</p>
								<p>
									<span class="public_title">订单时间： </span>
									<span>${order.operTime}</span>
								</p>
								<p>
									<span class="public_title">房源： </span>
									<span>${order.houseName}</span>
								</p>
								<p>
									<span class="public_title">订单状态： </span>
									<span>
									   <c:if test="${order.status=='10' }">等待客服确认</c:if>
						              <c:if test="${order.status=='11' }">等待客户入住</c:if>
						              <c:if test="${order.status=='12' }">客户在店</c:if>
						              <c:if test="${order.status=='13' }">客户离店</c:if>
						              <c:if test="${order.status=='21' }">客户取消订单</c:if>
						              <c:if test="${order.status=='22' }">商户取消订单</c:if>
						              <c:if test="${order.status=='23' }">订单取消</c:if>
								   </span>
								</p>
								<p>
									<input type="button" class="button" value="返回" id="fanhui" onclick="history.go(-1);" />
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
});
</script>