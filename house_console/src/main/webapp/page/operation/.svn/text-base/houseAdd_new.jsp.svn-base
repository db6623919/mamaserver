<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>新增房源</title>
		<style>
			.form-group {
				min-height: 84px;
				margin-bottom: 0
			}
		</style>
	</head>
	<body>
	<form id="mainForm" action="${pageContext.request.contextPath}/house/houseAdd.shtml" class="form-horizontal" method="post" enctype="multipart/form-data">
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="4" name="index"/>
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
										<h2 class="text-uppercase">新增房源</h2>
									</div>
									<div class="row mg-top-10">
											<div class=" col-xs-12 mg-left-15 mg-right-20">
												<h4 class="fw-bd">房源基本信息</h4>
												<hr />
											</div>
											<div class="form-group col-xs-6">
												<label for="name" class="col-xs-2 control-label"><font class="star">*</font>名称</label>
												<div class="col-xs-10">
													<input type="text" required value="${houseName }" name="name" id="name" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="theme" class="col-xs-2 control-label">主题</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="theme" name="theme" aria-invalid="false">
														<option value="1">亲子出游</option>
													   	<option value="2">浪漫假期</option>
													    <option value="3">新奇住宿</option>
												   	 	<option value="4">城市周末</option>
													   	<option value="5">特色主题</option>
													</select>
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="theme" class="col-xs-2 control-label"><font class="star">*</font>室</label>
												<div class="col-xs-10">
													<input type="number" required name="room" id="room" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="houseType" class="col-xs-2 control-label">房屋类型</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="type" name="houseType" aria-invalid="false">
														 <option value="1">别墅</option>
													   	 <option value="2">高档公寓</option>
													     <option value="3">酒店公寓</option>
													</select>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="bedType" class="col-xs-2 control-label"><font class="star">*</font>床型</label>
												<div class="col-xs-10">
													<input type="text" required name="bedType" id="bedType" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="isInvoice" class="col-xs-2 control-label">提供发票</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="isInvoice" name="isInvoice" aria-invalid="false">
														 <option value="1">是</option>
								       					 <option value="0">否</option>
													</select>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="flag" class="col-xs-2 control-label">标志</label>
												<div class="col-xs-10">
													<!-- <select class="form-control valid" id="flag" name="flag" aria-invalid="false">
														 <option value="1">推荐</option>
									    				 <option value="2">热门</option>
													</select> -->
													<input type="hidden" id="flag" name="flag" value="0" />
													<p ><input type="checkbox" onclick="checkRecommendTime(this)">&nbsp;&nbsp;推荐&nbsp;&nbsp;</p>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="telephone" class="col-xs-2 control-label"><font class="star">*</font>咨询电话</label>
												<div class="col-xs-10">
													<input type="text" required name="telephone" id="telephone" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="bedType" class="col-xs-1 control-label"><font class="star">*</font>介绍页面</label>
												<div class="col-xs-11">
													<p>
													    <font class="star">*</font>名称
													    <select class="form-control w-240 inline" id="introductionType" name="introductionType" aria-invalid="false">
													      <option value="1">首页特色</option>
													      <option value="2">公共空间</option>
													      <option value="3">卧室</option>
													      <option value="4">卫浴</option>
													      <option value="5">客厅&厨房&阳台</option>
													      <option value="6">外观</option>
													      <option value="7">周边</option>
													    </select>
														<font class="star">*</font>路径 <input type="file" required id="introductionImg" name="introductionImg" class="form-control w-260 inline"/>
														<font class="star">*</font>压缩 <select id="compression" name="compression" class='form-control inline w-100'><option value="0">是</option><option value="1">否</option></select>
													    <input type="button" id="introduction_Btn"  value="增加" class="btn  btn-style" onclick="addDiv('introductionImgUrl')"/>
													</p>
													<p id="introductionImgUrl" >
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="bedType" class="col-xs-1 control-label"><font class="star">*</font>分享图</label>
												<div class="col-xs-11">
													<p>
														<font class="star">*</font>路径 <input type="file" required id="shareImg" name="shareImg" class="form-control w-260 inline"/>
														<font class="star">*</font>压缩 <select id="compression" name="compression" class='form-control inline w-100'><option value="0">是</option><option value="1">否</option></select>
													</p>
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="isInvoice" class="col-xs-2 control-label"><font class="star">*</font>店铺</label>
												<div class="col-xs-10">
													<!--<input type="text" name="houseshop_id" id="houseshop_id" class="form-control">-->
												    <select class="form-control" name="houseshop_id"  id="houseshop_id" aria-invalid="false"  " >
														<c:forEach items="${shopList}" var="pro">
													      <option value="${pro.id }" >${pro.shopName }</option>							    
													     </c:forEach>
													</select>
												</div>
											</div>

											
											<div class="form-group col-xs-12">
												<label for="mark" class="col-xs-1 control-label">标签</label>
												<div class="checkbox col-xs-11" id="tag">
													<c:forEach items="${markList}" var="mark">
												     <label><input type="checkbox" name="mark" value="${mark.index }">${mark.name }</label>
												    </c:forEach>
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="live" class="col-xs-2 control-label"><font class="star">*</font>宜住人数</label>
												<div class="col-xs-10">
													<input type="number" required name="live" id="live" class="form-control">
												</div>
											</div>
											
											
											<div class="form-group col-xs-6">
												<label for="checkInTime" class="col-xs-2 control-label"><font class="star">*</font>入住时间</label>
												<div class="col-xs-10">
													<input type="date" required name="checkInTime" id="checkInTime" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="checkOutTime" class="col-xs-2 control-label"><font class="star">*</font>退房时间</label>
												<div class="col-xs-10">
													<input type="date" required	 name="checkOutTime" id="checkOutTime" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="luxury" class="col-xs-2 control-label"><font class="star">*</font>舒适度</label>
												<div class="col-xs-10">
													<input type="number" required name="luxury" id="luxury" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="devId" class="col-xs-2 control-label">开发商</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="devId" name="devId" aria-invalid="false">
													    <option value="-1">请选择</option>	
														<c:forEach items="${devList}" var="dev">
													      <option value="${dev.devId }">${dev.devName }</option>							    
													     </c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="bldId" class="col-xs-2 control-label">楼盘</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="bldId" name="bldId" aria-invalid="false">
													    <option value="-1">请选择</option>
														<c:forEach items="${buildings}" var="buildings">
													      <option value="${buildings.bldId }">${buildings.name }</option>							    
													     </c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="cityId" class="col-xs-2 control-label">城市</label>
												<div class="col-xs-10">
													<select class="form-control valid" id="cityId" name="cityId" aria-invalid="false">
														<c:forEach items="${cityInfoList}" var="city">
													      <option value="${city.cityId }">${city.cityName }</option>							    
													     </c:forEach>
													</select>
												</div>

											</div>
											<div class="form-group col-xs-6">
												<label for="checkdesc" class="col-xs-2 control-label"><font class="star">*</font>入住说明</label>
												<div class="col-xs-10">
													<input type="text" required name="checkdesc	" id="checkdesc" class="form-control">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="houseArea" class="col-xs-2 control-label"><font class="star">*</font>面积</label>
												<div class="col-xs-10">
													<input type="text" required name="houseArea" id="houseArea" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="stock" class="col-xs-2 control-label"><font class="star">*</font>库存</label>
												<div class="col-xs-10">
													<input type="number" required name="stock" id="stock" class="form-control">
												</div>
											</div>
											
												
												
											
											<div class="form-group col-xs-12 mg-left-15 mg-right-20">
												<h4 class=" fw-bd">其他参数信息</h4>
												<hr />
											</div>
											
											<div class="form-group col-xs-12">
												<label for="facility" class="col-xs-1 control-label">设施</label>
												<div class="checkbox col-xs-11" id="facility">
													<label><input type="checkbox" name="tv" value="1">有线电视机</label>
													<label><input type="checkbox" name="fridge" value="1">冰箱</label>
													<label> <input type="checkbox" name="washing" value="1">洗衣机</label>
													<label><input type="checkbox" name="conditione" value="1">空调</label>
				                                    <label><input type="checkbox" name="towels" value="1">毛巾 </label>
				                                    <label><input type="checkbox" name="tooth" value="1">牙具</label>
				                                    <label><input type="checkbox" name="slipper" value="1">拖鞋</label>
				                                    <label><input type="checkbox" name="shampoo" value="1">洗发/沐浴露</label>
				                                    <label><input type="checkbox" name="hairdrier" value="1">吹风机</label>
				                                    <label> <input type="checkbox" name="drinking" value="1">淋浴</label>
				                                    <label><input type="checkbox" name="shower" value="1">浴缸</label>
				                                    <label><input type="checkbox" name="hotpot" value="1">热水壶</label>
				                                     <label><input type="checkbox" name="heater" value="1">热水器</label>
				                                    <label><input type="checkbox" name="dryer" value="1">烘干机</label>
				                                   <label><input type="checkbox" name="smokedetector" value="1">烟雾探测器</label>
				                                    <label><input type="checkbox" name="heating" value="1">暖气</label>
				                                   <label> <input type="checkbox" name="extinguisher" value="1">灭火器</label>
				                                    <label><input type="checkbox" name="aidkit" value="1">急救包</label>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="equip" class="col-xs-1 control-label">配套</label>
												<div class="checkbox col-xs-11" id="equip">
													<label><input type="checkbox" name="wifi" value="1">wifi</label>
													<label><input type="checkbox" name="broadband" value="1">宽带 </label>
													<label><input type="checkbox" name="elevator" value="1">电梯</label>
													<label><input type="checkbox" name="swimming" value="1">游泳池</label>
													<label><input type="checkbox" name="accesscard" value="1">门禁卡</label>
													<label><input type="checkbox" name="securitystaff" value="1">保安</label>
													<label><input type="checkbox" name="store" value="1">便利店</label>
													<label><input type="checkbox" name="parking" value="1">停车位</label>
													<label><input type="checkbox" name="gym" value="1">健身房</label>
													<label><input type="checkbox" name="playground" value="1">儿童乐园</label>
													<label><input type="checkbox" name="wheelchair" value="1">无障碍设施</label>
													<label><input type="checkbox" name="buzzer" value="1">蜂鸣器/无线对讲机</label>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="other" class="col-xs-1 control-label">其他</label>
												<div class="checkbox col-xs-11" id="other">
													<label><input type="checkbox" name="cook" value="1">可做饭</label>
													<label><input type="checkbox" name="party" value="1">适合聚会</label>
													<label><input type="checkbox" name="smoking" value="1">可吸烟</label>
													<label><input type="checkbox" name="pet" value="1">可带宠物</label>
													<label><input type="checkbox" name="extrabed" value="1">可加床</label>
													<label><input type="checkbox" name="guests" value="1">接待外宾</label>
													<label><input type="checkbox" name="breakfast" value="1">提供早餐</label>
													<label><input type="checkbox" name="childrenstay" value="1">欢迎孩子入住</label>
												</div>
											</div>
											

											<div class="form-group col-xs-6">
												<label for="sideo_url" class="col-xs-2 control-label"><font class="star">*</font>视频地址</label>
												<div class="col-xs-10">
													<input type="text" required name="sideo_url" id="sideo_url" class="form-control">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<label for="freezeAmt" class="col-xs-2 control-label"><font class="star">*</font>普通定金</label>
												<div class="col-xs-10">
													<input type="number" min="0" required name="freezeAmt" id="freezeAmt" class="form-control">
												</div>

											</div>
											<div class="form-group col-xs-6">
												<label for="totalAmt" class="col-xs-2 control-label"><font class="star">*</font>普通全额</label>
												<div class="col-xs-10">
													<input type="number" min="0" required name="totalAmt" id="totalAmt" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="memFreezeAmt" class="col-xs-2 control-label"><font class="star">*</font>会员定金</label>
												<div class="col-xs-10">
													<input type="number" min="0" required name="memFreezeAmt" id="memFreezeAmt" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="memTotalAmt" class="col-xs-2 control-label"><font class="star">*</font>会员全额</label>
												<div class="col-xs-10">
													<input type="number" min="0" required name="memTotalAmt" id="memTotalAmt" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="memTotalAmt" class="col-xs-2 control-label"><font class="star">*</font>市场价</label>
												<div class="col-xs-10">
													<input type="number" min="0" required name="market_price" id="market_price" class="form-control">
												</div>
											</div>
											
											<div class="col-xs-6 form-group">
												<label for="stauts" class="col-xs-2 control-label">状态</label>
												<div class="col-xs-10">
													<select class="form-control  w-240 inline" id="status" name="stauts" onchange="checkOnLineTime(this.value)">
														<option value="2">下架</option>
														<option value="1" selected="selected">上架</option>
													</select>
													<span id="onLineTime_span"><input type="date" name="onLineTime" id="onLineTime" class="form-control w-240 inline"></span>
												</div>
											</div>
											
											<div class="form-group col-xs-6 ">
												<label for="mapImg" class="col-xs-2 control-label"><font class="star">*</font>地图略缩图</label>

												<div class=" col-xs-10">
													<input type="file" required id="mapImg" name="mapImg" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-6">
												<label for="address" class="col-xs-2 control-label"><font class="star">*</font>详细地址</label>
												<div id="r-result" class="col-xs-10">
													<input type="text" required id="suggest" name="address" size="20" value="" class="form-control" />
												</div>
												<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
											</div>

											<div class="form-group col-xs-6">
												<div class="col-xs-10">
													<input type="hidden" name="longitude" value="${longitude }" id="longitude" class="form-control" readonly="readonly">
												</div>
											</div>

											<div class="form-group col-xs-6">
												<div class="col-xs-10">
													<input type="hidden" name="latitude" value="${latitude}" id="latitude" class="form-control" readonly="readonly">
												</div>
											</div>
											
											<div class="form-group col-xs-6 text-left">
												<div class="error"></div>
											</div>
											<div class="form-group col-xs-6 text-right">
											    <input type="button" class="btn  btn-style" value="增加"  onclick="javascript:addHouse();"/>
												<!--<input type="submit" class="btn  btn-style" value="增加" />-->
												<input type="reset" class="btn  btn-style" value="重置" />
												<input type="button" class="btn btn-style" value="返回" onclick="history.go(-1)" />
											</div>
											<div class="form-group col-xs-12 mg-left-0">
												<label>百度地图的使用</label>
												<div class="baidu_map" id="baidu_map" >

												</div>
											</div>
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
</form>
	</body>
	<%@include file="/page/common/foot.jsp" %>
	<script src="http://api.map.baidu.com/api?v=2.0&ak=CF1924c9daf46e30c71c71db9c85a646" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/baidumap.js" defer="defer"></script>
    
	<script>
	
		var index=1;
		function addDiv(divId){
			var introductionType = "<select class=\"form-control w-240 inline\" id=\"introductionType\" name=\"introductionType\" aria-invalid=\"false\">"
			                       +"<option value=\"1\">首页特色</option>"
		   	                       +"<option value=\"2\">公共空间</option>"
		                           +"<option value=\"3\">卧室</option>"
		                           +"<option value=\"4\">卫浴</option>"
		                           +"<option value=\"5\">客厅</option>"
		                           +"<option value=\"6\">分享图</option>"
		                           +"<option value=\"7\">其它</option></select>";
		    
			$('#'+divId).append("<p id='"+index+"'><font class='star'>*</font>名称 "+introductionType+"<font class='star'>*</font>路径 <input type='file'  id='introductionImg'  name='introductionImg' class='form-control w-260 inline'/> <font class='star'>*</font>压缩 <select id='compression' name='compression' class='form-control inline w-100'><option value='0'>是</option><option value='1'>否</option></select> <input type='button' id='label_Btn' value='删除' class='btn  btn-style' onclick='deleteDiv("+index+")'/></p>");
			index++;
		}
		function deleteDiv(id){	
			$('#'+id).remove();
		}
	
		$("#mainForm").validate(	
				{
			rules:{
				name: "required",
				telephone: "required",
				bedType: "required",
				room: {
				    required: true,
				    minlength: 7,
				    maxlength: 7
				},
				bedType:'required',
				//introductionName:'required',
				introductionImg:'required',
				shareImg:'required',
				live: {
					required: true,
					number: true,
					min: 0
				},
				checkInTime:'required',
				checkOutTime:'required',
				luxury:'required',
				address:'required',
				checkdesc:'required',
				stock:{
					required: true,
					number: true,
					min: 0
				},
				sideo_url:'required',
				houseArea:'required',
				freezeAmt:{
					required: true,
					number: true,
					min: 0
				},
				totalAmt:{
					required: true,
					number: true,
					min: 0
				},
				memFreezeAmt:{
					required: true,
					number: true,
					min: 0
				},
				memTotalAmt:{
					required: true,
					number: true,
					min: 0
				},
				bldId:{
					required: true,
					number: true,
					min: 0
				},
				devId:{
					required: true,
					number: true,
					min: 0
				},
				market_price:{
					required: true,
					number: true,
					min: 0
				}
				
			},
			messages: {
				telephone: "请输入咨询电话",
				name: "请输入房源名称",
				bedType: '请输入床型',
				room:{
					required: "请输入室",
				    minlength: "室最小长度不能低于7位",
				    maxlength: "室最大长度不能超过7位"
				},
				bedType:'请输入床型',
				//introductionName:'请输入名称',
				introductionImg:'请输入路径',
				shareImg:'请输入路径',
				live:'请输入宜住人数',
				checkInTime:'请输入入住时间',
				checkOutTime:'请输入退房时间',
				luxury:'请输入舒适度',
				address:'请输入详细地址',
				checkdesc:'请输入入住说明',
				stock:'请输入库存',
				sideo_url:'请输入视频地址',
				houseArea:'请输入面积',
				bldId:'请选择楼盘',
				devId:'请选择开发商',
				freezeAmt:{
					required: "请输入普通定金",
					number: "格式不正确，必须是有效的数字",
					min: "金额不能小于0"
				},
				totalAmt:{
					required: "请输入普通全额",
					number: "格式不正确，必须是有效的数字",
					min: "金额不能小于0"
				},
				memFreezeAmt:{
					required: "请输入会员定金",
					number: "格式不正确，必须是有效的数字",
					min: "金额不能小于0"
				},
				memTotalAmt:{
					required: "请输入会员全额",
					number: "格式不正确，必须是有效的数字",
					min: "金额不能小于0"
				},
				market_price:{
					required: "请输入市场价",
					number: "格式不正确，必须是有效的数字",
					min: "金额不能小于0"
				}
					
			}
		}
				
		);
		 $().ready(function(){
			loadScript("suggest", "longitude", "latitude");
		});
		function getData(obj,url){
			var target=$("#"+obj);
			var html="";
			$.getJSON(url,function(data){
				$.each(data,function(index,e){
					html+='<label><input type="checkbox" name="'+obj+'" value="'+e.id+'">'+e.name+'</label> ';
				});
				target.html(html);
			});
			
		} 
		
		//是否推荐
		function checkRecommendTime(obj) {
			//alert(obj.checked);
			if(obj.checked) {
				$("#flag").val(1);
			} else {
				$("#flag").val(0);
			}
			
		}
		//上下架
		function checkOnLineTime(value) {
			if(value == 1) {
				$("#onLineTime_span").show();
			} else {
				$("#onLineTime_span").hide();
			}
		}
		
		function addHouse(){
			var ret = getIntroductionType();
			if(ret!=0){
			    $("#mainForm").attr("action","${pageContext.request.contextPath}/house/houseAdd.shtml").val();
			    $("#mainForm").submit();
			}
		}
		
		function getIntroductionType() {
		    var introductionType='';
		    $("select[name=introductionType]").each(function() {
		    	introductionType += ',' + $(this).val();
		  	 });
		    var type= new Array();
		     type = introductionType.split(",");
		    if(type.length<=5){
		    	alert("介绍页面图片数少于5张！");
		    	return 0;
		    }
		    return 1;
		  }
		
	</script>

</html>