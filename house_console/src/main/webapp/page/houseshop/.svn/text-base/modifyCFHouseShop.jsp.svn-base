<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!-- 修改优惠 -->
		<title>修改优惠</title>
	</head>
	<body>
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="10" name="index"/>
			</jsp:include>
			<div id="page-wrapper">
				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
                            			
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">

							<div class="col-xs-12 ">
								<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

									<div class="panel-heading templatemo-position-relative">
										<h2 class="text-uppercase">修改优惠</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="#" class="form-horizontal" enctype="multipart/form-data" method="post">
										
											<input type="hidden" required value="${shopId}" name="shopId" id="shopId" >
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-3 control-label">名称：</label>
												<div class="col-xs-9">${shopName}
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-3 control-label">优惠总额：</label>
												<div class="col-xs-9">
												    <input type="number" required value="${cfHouseShop.totalAmt }" name="totalAmt" id="totalAmt" class="form-control" maxlength="11">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="province" class="col-xs-3 control-label">优惠类型：</label>
												<div class="col-xs-9">
												    <select id="discountType" name="discountType" class='form-control inline w-150' onchange="changeType(this.value)">
												        <option value="1" <c:if test="${cfHouseShop.discountType=='1'}">selected="selected"</c:if> >折扣优惠</option>
												        <option value="2" <c:if test="${cfHouseShop.discountType=='2'}">selected="selected"</c:if> >金额优惠</option>
												    </select>										    
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="province" class="col-xs-3 control-label" id="discount_label">优惠折扣：</label>
												<div class="col-xs-9">
												    <input type="number" required value="${cfHouseShop.discount }" name="discount" id="discount" class="form-control" maxlength="11">
												    <span id="discount_span">(例如9折请输入0.9)</span>											    
												</div>
											</div>
											
											<div class="form-group col-xs-6" id="lowestAmt_div" <c:if test="${cfHouseShop.discountType=='1'}">style="display: none;"</c:if> >
												<label for="province" class="col-xs-3 control-label">下单最低金额：</label>
												<div class="col-xs-9">
												    <input type="number" required value="${cfHouseShop.lowestAmt }" name="lowestAmt" id="lowestAmt" class="form-control" maxlength="11">											    
												</div>
											</div>
											
											<br>

											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-3 control-label">每天优惠上限：</label>
												<div class="col-xs-9">
													<input type="number" value="${cfHouseShop.discountLimit }" name="discountLimit" id="discountLimit" class="form-control" maxlength="11">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="province" class="col-xs-3 control-label">短信开关：</label>
												<div class="col-xs-9">
													<select id="message_switch" name="message_switch" class='form-control inline w-100'>
												        <option value="0" <c:if test="${cfHouseShop.message_switch=='0'}">selected="selected"</c:if> >打开</option>
												        <option value="1" <c:if test="${cfHouseShop.message_switch=='1'}">selected="selected"</c:if> >关闭</option>
												    </select>
												</div>
											</div>
				
											<div class="form-group col-xs-6">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
												<input type="button" class="btn  btn-style" value="提交"  onclick="javascript:addCFHouseShop();"/>
												<input type="reset" class="btn  btn-style" value="重置" />
												<input type="button" class="btn btn-style" value="返回" onclick="history.go(-1)"/>
											</div>

										</form>

									</div>

								</div>
							</div>
						</div>

					</div>
					<!-- /.row -->

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->

	</body>
	<%@include file="/page/common/foot.jsp" %>
	<script>
		$().ready(function() {
			$("#mainForm").validate({
				rules: {
					shopName: "required",
					totalAmt:
					 {
						required: true,
						number: true,
						min: 0
					} ,
					discount:{
						required: true,
						number: true,
						min: 0
					},
					discountLimit:{
						required: true,
						number: true,
						min: 0
					},
				},
				messages: {
					shopName: "请输入店铺名称",
					totalAmt:"请输入店铺简介",
					discount:"请选择头像",
					discountLimit:"请输入老板电话",
				} /*,submitHandler:function(form){
					 if($(form).valid()){
						    $("#mainForm").attr("action","${pageContext.request.contextPath}/houseTag/addHouseTag.shtml?flag=add");
							$("#mainForm").submit();
	                        // submit form
	                        var p=$("#userFrom").serialize();
	                        $.ajax({
	                        	url:'${baseUrl}/dno/city_action',
	                        	data:p,
	                        	type:'post',success:function(result){
	                        		//alert(result);
	                        		myAlert({content:'修改成功',okfun:function(){
	                        			location.href="${baseUrl}/dno/city_list";
	                        		}})
	                        	}
	                        });
	                    }else{
	                        return false;
	                    }
					 
				} */
			});
			
		});

		
		function chooseText(number){
			if(number!=''||number!=null){
				if(isNaN(number)){
					alert("请输入数字!");
					$("#id").val("");
				}
			}
		}
		
		function addCFHouseShop(){
			$("#mainForm").attr("action","${pageContext.request.contextPath}/houseshop/addCFHouseShop.shtml").val();
			$("#mainForm").submit();
		}

changeType("${cfHouseShop.discountType}");
		
function changeType(type) {
	if (type == 2) {
		$("#discount_label").html("优惠金额：");
		$("#discount_span").html("");
		$("#lowestAmt_div").show();
	} else {
		$("#discount_label").html("优惠折扣：");
		$("#discount_span").html("(例如9折请输入0.9)");
		$("#lowestAmt_div").hide();
	}
}		
	</script>
</html>