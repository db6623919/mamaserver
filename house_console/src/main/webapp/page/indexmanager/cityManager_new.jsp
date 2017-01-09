<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<!-- 新增城市 -->
		<title>新增城市</title>
		<style type="text/css">
			span.public_title{
				width: 120px;
   				display: inline-block;
			}
		</style>
	</head>
	<body>
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="6" name="index"/>
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
										<h2 class="text-uppercase">新增城市</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="${pageContext.request.contextPath}/indexManager/changeManager.shtml?type=city" class="form-horizontal" enctype="multipart/form-data"  method="post">
											<input type="hidden" name="count" id="count" value="${fn:length(CitysList)}">
											<span class="public_title"><font color="red">*</font>&nbsp;所有城市：</span>
											<span class="public_title"></span>
											<span class="public_title"><font color="red">*</font>&nbsp;已排序城市：</span>
												<p>
													<span class="public_title"></span>
												     	<select id="allsort" name="allsort" multiple="true" class="form-control" style="height:300px;width:auto;display:inline-block">
												     		<c:forEach items="${CitysList}" var="num" varStatus="status">
												     			<c:if test="${num.sort==0}">
												     				<option value="${num.cityId}">${num.cityName}</option>
												     			</c:if>
												     		</c:forEach>
												     	</select>
												     	<input type="button"  value="-->" class="btn  btn-style" id="zengjia" style="margin-bottom: 18px;" />
												     	<input type="button" value="<--" class="btn  btn-style" id="chexiao" style="margin-bottom: 18px;" />
												     	<span class="public_title"></span>
												     	<select id="partsort" name="partsort" multiple="true" class="form-control" style="height:300px;width:auto;display:inline-block">
												     		<c:forEach items="${CitysList}" var="num" varStatus="status">
												     		<c:if test="${num.sort!=0}">
												     			<option value="${num.cityId}">${num.cityName}</option>
												     		</c:if>
												     		</c:forEach>
												     	</select>
												     	<input class="btn  btn-style" type="button"  value="向上" id="xiangshang" style="margin-bottom: 18px;" />
												     	<input class="btn  btn-style" type="button" value="向下" id="xiangxia" style="margin-bottom: 18px;" />
													<span class="Validform_checktip"></span>
												</p>
						
												<p>
													<span class="public_title">&nbsp;</span> <input type="submit"	class="btn  btn-style" value="保存" /> 
													<input type="button" class="btn  btn-style" value="返回" id="fanhui"  onclick="history.go(-1)" />
												</p>

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
					
				},
				messages: {
					
					
				} ,submitHandler:function(form){
					 if($(form).valid()){
						 	var obj = document.getElementById("partsort"); //定位id
							var length = obj.length;
							for (var i=0,len=length; i<len; i++)
							{
								obj[i].selected = true;
							}
							form.submit();
							
	                    }else{
	                        return false;
	                    }
				} 
			});
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
			$("#zengjia").click(function() {
				var obj = document.getElementById("allsort"); //定位id
				var obj1 = document.getElementById("partsort"); //定位id
				var index = obj.selectedIndex; // 选中索引
				var text = obj.options[index].text; // 选中文本
				var value = obj.options[index].value; // 选中值
				obj.options.remove(index);
				obj1.options.add(new Option(text,value));
			});
			
			$("#chexiao").click(function() {
				var obj = document.getElementById("allsort"); //定位id
				var obj1 = document.getElementById("partsort"); //定位id
				var index = obj1.selectedIndex; // 选中索引
				var text = obj1.options[index].text; // 选中文本
				var value = obj1.options[index].value; // 选中值
				obj1.options.remove(index);
				obj.options.add(new Option(text,value));
			});
			$("#xiangshang").click(function() {
				var obj = document.getElementById("partsort"); //定位id
				var index = obj.selectedIndex; // 选中索引
				if(index == 0){
					return false;
				}
				var text = obj.options[index].text; // 选中文本
				var value = obj.options[index].value; // 选中值
				var temptext = obj.options[index-1].text; // 选中文本
				var tempvalue =	obj.options[index-1].value; // 选中值
				obj.options[index].text = temptext;
				obj.options[index].value = tempvalue;
				obj.options[index-1].text = text;
				obj.options[index-1].value = value;
			});
			
			$("#xiangxia").click(function() {
				var obj = document.getElementById("partsort"); //定位id
				var length = obj.length;
				var index = obj.selectedIndex; // 选中索引
				if(index == length){
					return false;
				} 
				var text = obj.options[index].text; // 选中文本
				var value = obj.options[index].value; // 选中值
				var temptext = obj.options[index+1].text; // 选中文本
				var tempvalue =	obj.options[index+1].value; // 选中值
				obj.options[index].text = temptext;
				obj.options[index].value = tempvalue;
				obj.options[index+1].text = text;
				obj.options[index+1].value = value;
				
			});
			
			
			$("#fanhui").click(function() {
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
</html>