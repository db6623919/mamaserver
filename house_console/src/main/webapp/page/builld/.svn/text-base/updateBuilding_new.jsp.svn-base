<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<#include "../inc_comm.ftl"/>
 <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.2.js"></script>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>修改楼盘</title>
		<style>
			.form-group {
				min-height: 60px;
				margin-bottom: 0
			}
		</style>
	</head>

	<body onload="getCity();">

		<div id="wrapper">

			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="3" name="index"/>
			</jsp:include>
			<div id="page-wrapper">
				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">
                            			<!--新增用户-->
                       		 </h1>
						</div>
					</div>
					<!-- /.row -->
					<div class="row">
						<div class="container-fluid">

							<div class="col-xs-12 ">
								<div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">

									<div class="panel-heading templatemo-position-relative">
										<h2 class="text-uppercase">修改楼盘</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="${pageContext.request.contextPath}/builld/updateBuilding.shtml" class="form-horizontal" method="post">
											<input type="hidden" name="bldId" value="${buildings.bldId }"/>
											<div class="form-group col-xs-6">
												<label for="name" class="col-xs-2 control-label"><font class="star">*</font>名称</label>
												<div class="col-xs-10">
													<input type="text" name="name" value="${buildings.name }" id="name" class="form-control">
												</div>

											</div>
											<div class="form-group col-xs-6">
												<label for="type" class="col-xs-2 control-label">类型</label>
												<div class="col-xs-10">
													<select class="form-control valid" name="type" id="type" aria-invalid="false">
														<option value="1" <c:if test="${buildings.type==1}">selected="selected"</c:if>>别墅</option>
									   					<option value="2" <c:if test="${buildings.type==2}">selected="selected"</c:if>>高档公寓</option>
									   					<option value="3" <c:if test="${buildings.type==3}">selected="selected"</c:if>>酒店公寓</option>
													</select>
												</div>

											</div>
											<div class="form-group col-xs-6">
												<label for="proId" class="col-xs-2 control-label">省</label>
												<div class="col-xs-10">
													<select class="form-control" name="proId"  id="proId" aria-invalid="false"  onchange="getCity();" >
														<c:forEach items="${proList}" var="pro">
													      <option value="${pro.provId }" <c:if test="${buildings.provId==pro.provId}">selected="selected"</c:if>>${pro.provName }</option>							    
													     </c:forEach>
													</select>
												</div>

											</div>
											<div class="form-group col-xs-6">
											    <input type="hidden" name="buildingCityId" id="buildingCityId" value="${buildings.cityId}"/>
												<label for="cityIds" class="col-xs-2 control-label">城市</label>
												<div class="col-xs-10">
													<select class="form-control valid" name="cityId" id="cityId" aria-invalid="false" onchange="getTradeArea();">
														<c:forEach items="${cityList}" var="city">
													      <option value="${city.cityId }"  <c:if test="${buildings.cityId==city.cityId}">selected="selected"</c:if>>
													        ${city.cityName }</option>							    
													     </c:forEach>
													</select>
												</div>

											</div>
											
											<div class="form-group col-xs-12">
											    <input type="hidden" name="tradeStr" id="tradeStr" value="${tradeStr}">
												<label for="facility" class="col-xs-1 control-label">商圈</label>
												<div class="checkbox col-xs-11" id="tradeArea">
												   <c:forEach items="${tradeList}" var="trade">
												     <label><input type="checkbox" name="trade" value="${trade.id }" 
												     <c:if test="${trade.check==1 }"> checked="checked"</c:if> >${trade.name}</label>
												   </c:forEach>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="devId" class="col-xs-2 control-label">开发商</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="devId" name="devId" aria-invalid="false">
														<c:forEach items="${devList}" var="dev">
													      <option value="${dev.devId }" <c:if test="${buildings.devId==dev.devId}">selected="selected"</c:if>>${dev.devName }</option>							    
													     </c:forEach>
													</select>
												</div>

											</div>

											<div class="form-group col-xs-6">
												<label for="telphone" class="col-xs-2 control-label"><font class="star">*</font>联系电话</label>
												<div class="col-xs-10">
													<input type="number" value="${buildings.telphone }" name="telphone" id="telphone" class="form-control">
												</div>

											</div>
											
											<div class="form-group col-xs-6">
												<label for="bldImg" class="col-xs-2 control-label">楼盘图片</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.bldImg }" name="bldImg" id="bldImg" class="form-control">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="video" class="col-xs-2 control-label">楼盘视频</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.vedio}" name="video" id="video" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="description" class="col-xs-2 control-label">楼盘介绍</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.description }" name="description" id="description" class="form-control">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="houseExplain" class="col-xs-2 control-label">楼盘周边配置介绍</label>
												<div class="col-xs-10">
													<textarea  name="houseExplain" id="houseExplain" class="form-control">${buildings.houseExplain }</textarea>
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="mark" class="col-xs-2 control-label">标签</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.mark }" name="mark" id="mark" class="form-control">
												</div>
												
											</div>
											
											<div class="form-group col-xs-12 text-right">
												<input type="submit" class="btn  btn-style" value="更新" />
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
	<script type="text/javascript">
		$().ready(function() {
			$("#userFrom").validate({
				rules: {
					telphone: "required",
					name: "required",
					
				},
				messages: {
					name: "请输入楼盘名称",
					telphone:"请输入联系方式"
				},
				errorContainer: "div.error",
				errorLabelContainer: $("#userFrom div.error"),
				wrapper: "li"/* ,
				submitHandler:function(form){
					 if($(form).valid()){
	                        // submit form
	                        var p=$("#userFrom").serialize();
	                        $.ajax({
	                        	url:'${baseUrl}/dno/houses_action',
	                        	data:p,
	                        	type:'post',success:function(result){
	                        		//alert(result);
	                        		myAlert({content:'修改成功',okfun:function(){
	                        			location.href="${baseUrl}/dno/houses_list";
	                        		}})
	                        	}
	                        });
	                    }else{
	                        return false;
	                    }
				} */
			});
		});	 
			 
		function getCity(){
			var provinceId = $("#proId").val();
			var buildingCityId = $("#buildingCityId").val();
			$("#cityId").empty();
			
			$.getJSON("${pageContext.request.contextPath}/city/getCitysByProId.shtml?provinceId="+provinceId,function(data){
				var Data = eval(data);
				var json1 = Data.result.list; // item是数组
				var data = '';
				$.each(json1,function(i,rs){
					var cityId = json1[i].cityId;
					if(buildingCityId==cityId){
						data += "<option value="+json1[i].cityId+" selected=\"selected\" >"+json1[i].cityName+"</option>";
					}else{
						data += "<option value="+json1[i].cityId+">"+json1[i].cityName+"</option>";
					}
				    
				});
				$("#cityId").innerHTML = "";
			    $("#cityId").append(data);
			    getTradeArea();
			})
		}

		function getTradeArea(){
			var cityId = $("#cityId").val();
			var tradeStr = $("#tradeStr").val();
			var tradeList = tradeStr.split(",");
			$("#tradeArea").empty();
			$.getJSON("${pageContext.request.contextPath}/tradeArea/getTradeAreaByPro.shtml?cityId="+cityId,function(data){
				var Data = eval(data);
				var json1 = Data.result.list; // item是数组
				var data = '';
				$.each(json1,function(i,rs){
					var isChecked = 0;
					for(var k=0;k<tradeList.length;k++){
						var tradeId = tradeList[k];
						if(tradeId==json1[i].id){
							isChecked = 1;
							break;
						}
						
					}
					if(isChecked==0){
						data += "<label><input type=\"checkbox\" name=\"trade\" value="+json1[i].id+">"+json1[i].name+"</label>";
					}else{
						data += "<label><input type=\"checkbox\" name=\"trade\" value="+json1[i].id+" checked=\"checked\" >"+json1[i].name+"</label>";
					}
				});
				$("#tradeArea").innerHTML = "";
			    $("#tradeArea").append(data);
			})
		}
		
	</script>

</html>