<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath }/date/css/jquery-ui.css"/>  
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath }/date/css/jquery-ui-timepicker-addon.css"/>  
<script type="text/javascript" src="${pageContext.request.contextPath }/date/jquery-1.7.2.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath }/date/jquery-ui.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath }/date/jquery-ui-timepicker-addon.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath }/date/jquery-ui-sliderAccess.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath }/date/jquery-ui-timepicker-zh-CN.js"></script>  
<script type="text/javascript">  
            $(function(){  
            	   $("#date").datepicker({//添加日期选择功能  
	                    numberOfMonths:1,//显示几个月  
	                    showButtonPanel:true,//是否显示按钮面板  
	                    dateFormat: 'yy-mm-dd',//日期格式  
	                    clearText:"清除",//清除日期的按钮名称  
	                    closeText:"关闭",//关闭选择框的按钮名称  
	                    yearSuffix: '年', //年的后缀  
	                    showMonthAfterYear:true,//是否把月放在年的后面  
	                    defaultDate:'${housePrice.date}',//默认日期    
	                    minDate:'1014-03-05',//最小日期  
	                    maxDate:'2100-03-20',//最大日期  
	                    monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
	                    dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
	                    dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
	                    dayNamesMin: ['日','一','二','三','四','五','六'],  
	                    onSelect: function(selectedDate) {//选择日期后执行的操作  
	                        
	                    }  
                   });  
            });  
</script>
<style type="text/css">   
            #ui-datepicker-div, .ui-datepicker{ font-size: 90%; }             
</style>
<title>妙购一卡通后台</title>
</head>
<body>
	<div id="body-wrapper">
		<div id="main-content">
			<div class="content-box">
				<div class="content-box-header">
					<h3>修改价格
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post" enctype="multipart/form-data">
							<fieldset>
							<p>
									<span class="public_title"><font color="red">*</font>&nbsp;开始时间：</span>
									<input type="text" id="date" name="date" class="text-input small-input" style="width: 100px" width="60px"  value="${housePrice.date}" />								
									</p>
																
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;会员价：</span>
									<input type="text"  id="memTotalAmt" name="memTotalAmt" class="text-input small-input" style="width: 140px" value="${housePrice.memTotalAmt}" />
									<span class="Validform_checktip"></span>
								</p>
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;会员冻结：</span>
									<input type="text"  id="memFreezeAmt" name="memFreezeAmt" class="text-input small-input" style="width: 140px" value="${housePrice.memFreezeAmt}" />
									<span class="Validform_checktip"></span>
								</p>
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;普通价：</span>
									<input type="text"  id="totalAmt" name="totalAmt" class="text-input small-input" style="width: 140px" value="${housePrice.totalAmt}" />
									<span class="Validform_checktip"></span>
								</p>
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;普通冻结：</span>
									<input type="text"  id="freezeAmt" name="freezeAmt" class="text-input small-input" style="width: 140px" value="${housePrice.freezeAmt}" />
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;是否旺季：</span>
									<input type="radio" name="inSeason" value="true" ${housePrice.inSeason ? 'checked' : '' }>是
									<input type="radio" name="inSeason" value="false" ${housePrice.inSeason ? '' : 'checked' }>否
								</p>
								
								<input type="hidden"  name="houseId" id="houseId"  value="${ houseId}"/>
		
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
								$("#mainForm").attr("action","${pageContext.request.contextPath}/house/addHouseDateList.shtml?flag=update");
						
						}
					});

</script>