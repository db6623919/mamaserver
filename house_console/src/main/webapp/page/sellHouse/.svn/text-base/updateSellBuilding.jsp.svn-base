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
					<h3>更新楼盘
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post" enctype="multipart/form-data">
						<input type="hidden" name="bldId" value="${buildings.bldId }"/>
							<fieldset>
							<p>
									<span class="public_title"><font color="red">*</font>&nbsp;名称：</span>
									<input type="text" id="name" name="name" class="text-input small-input" style="width: 100px" width="60px"  value="${buildings.name }" />								
									<span class="public_title"><font color="red">*</font>&nbsp;类型：</span>
									<select id="type" name="type">
									   <option value="1" <c:if test="${buildings.type==1}">selected="selected"</c:if>>别墅</option>
									   <option value="2" <c:if test="${buildings.type==2}">selected="selected"</c:if>>高档公寓</option>
									   <option value="3" <c:if test="${buildings.type==3}">selected="selected"</c:if>>酒店公寓</option>
									</select>
									</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;省：</span>
									<select id="proId" name="proId">
										<c:forEach items="${proList}" var="pro">
									      <option value="${pro.provId }" <c:if test="${buildings.provId==pro.provId}">selected="selected"</c:if>>${pro.provName }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>
									<span class="public_title"><font color="red">*</font>&nbsp;城市：</span>
									<select id="cityId" name="cityId">
										<c:forEach items="${cityList}" var="city">
									      <option value="${city.cityId }"  <c:if test="${buildings.cityId==city.cityId}">selected="selected"</c:if>>${city.cityName }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>
									<span class="public_title"><font color="red">*</font>&nbsp;开发商：</span>
									<select id="devId" name="devId">
										<c:forEach items="${devList}" var="dev">
									      <option value="${dev.devId }" <c:if test="${buildings.devId==dev.devId}">selected="selected"</c:if>>${dev.devName }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>
								</p>
								
								 <p>
									<span class="public_title">&nbsp;项目简介：</span>
									<input type="text" value="${buildings.projectIntroduction}" id="project_introduction" name="project_introduction" class="text-input small-input" style="width: 140px" />
									<span class="public_title">&nbsp;户型面积区间段：</span>
									<input type="text" value="${buildings.unitArea}" id="unit_area" name="unit_area" class="text-input small-input" style="width: 140px" />
								</p>
								<p>
									<span class="public_title">&nbsp;建筑面积：</span>
									<input type="text" value="${buildings.builtArea}" id="built_area" name="built_area" class="text-input small-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
									<span class="public_title">&nbsp;装修标准：</span>
									<input type="text" value="${buildings.decorationStandard}" id="decoration_standard" name="decoration_standard" class="text-input small-input" style="width: 140px" />
								</p>
								<p>
									<span class="public_title">&nbsp;物业类型：</span>
									<input type="text" value="${buildings.propertyType}" id="property_type" name="property_type" class="text-input small-input" style="width: 140px" />
									<span class="public_title">&nbsp;楼盘均价：</span>
									<input type="text" value="${buildings.averagePrice}" id="average_price" name="average_price" class="text-input small-input" style="width: 140px"/>
								</p>
								<p>
									<span class="public_title">&nbsp;最近开盘日期（yyyyMMdd）：</span>
									<input type="text" value="${buildings.openDate}" id="open_date" name="open_date" class="text-input small-input" style="width: 140px" maxlength="8"/>
									<span class="Validform_checktip"></span>
									<span class="public_title">&nbsp;购房优惠：</span>
									<input type="text" value="${buildings.purchaseDiscount}" id="purchase_discount" name="purchase_discount" class="text-input small-input" style="width: 140px" />
								</p>
									<p>
									<span class="public_title">&nbsp;置业顾问手机：</span>
									<input type="text" value="${buildings.consultantPhone}" id="consultant_phone" name="consultant_phone" class="text-input small-input" style="width: 140px" />
									<span class="public_title">&nbsp;最早交房日期（yyyyMMdd）：</span>
									<input type="text" value="${buildings.launchDate}" id="launch_date" name="launch_date" class="text-input small-input" style="width: 140px" maxlength="8"/>
									<span class="Validform_checktip"></span>
								</p>
								 <p>
									<span class="public_title">&nbsp;产权年限：</span>
									<input type="text" value="${buildings.yearLimit}" id="year_limit" name="year_limit" class="text-input small-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
									<span class="public_title">&nbsp;建筑类型：</span>
									<input type="text" value="${buildings.builtType}" id="built_type" name="built_type" class="text-input small-input" style="width: 140px" />
								</p>
								 <p>
									<span class="public_title">&nbsp;容积率：</span>
									<input type="text" value="${buildings.volumeRate}" id="volume_rate" name="volume_rate" class="text-input small-input" style="width: 140px" />
									<span class="public_title">&nbsp;绿化率：</span>
									<input type="text" value="${buildings.greeningRate}" id="greening_rate" name="greening_rate" class="text-input small-input" style="width: 140px" />
								</p>
								 <p>
									<span class="public_title">&nbsp;规划户数：</span>
									<input type="text" value="${buildings.planHouseholds}" id="plan_households" name="plan_households" class="text-input small-input" style="width: 140px" />
									<span class="public_title">&nbsp;规划车位：</span>
									<input type="text" value="${buildings.planParking}" id="plan_parking" name="plan_parking" class="text-input small-input" style="width: 140px" />
								</p>
								 <p>
									<span class="public_title">&nbsp;预售许可：</span>
									<input type="text" value="${buildings.presalePermit}" id="presale_permit" name="presale_permit" class="text-input small-input" style="width: 140px" />
									<span class="public_title">&nbsp;物业公司全称：</span>
									<input type="text" value="${buildings.propertyCompName}" id="property_comp_name" name="property_comp_name" class="text-input small-input" style="width: 140px" />
								</p>
								 <p>
									<span class="public_title">&nbsp;物业费：</span>
									<input type="text" value="${buildings.propertyFee}" id="property_fee" name="property_fee" class="text-input small-input" style="width: 140px" />
									<span class="public_title">&nbsp;供暖方式：</span>
									<input type="text" value="${buildings.heartingMode}" id="hearting_mode" name="hearting_mode" class="text-input small-input" style="width: 140px" />
								</p>
								 <p>
									<span class="public_title">&nbsp;水电煤气：</span>
									<input type="text" value="${buildings.waterElec}" id="water_elec" name="water_elec" class="text-input small-input" style="width: 140px" />
									<span class="public_title">&nbsp;项目特色：</span>
									<input type="text" value="${buildings.projectFeature}" id="project_feature" name="project_feature" class="text-input small-input" style="width: 140px" />
								</p>
								<p>
									<span class="public_title">&nbsp;景观资源：</span>
									<c:if test="${scenic_resource.size()==0 }">
										名称<input type="text" value="" id="scenic_resource" name="scenic_resource" class="text-input" style="width: 1000px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('scenicResource','scenic_resource')"/>
									</c:if>
									<c:forEach items="${scenic_resource}" var="scenic" varStatus="status">
										<p id="scenic_${status.count}">
										<span class="public_title"></span>
											名称<input type="text" value="${scenic}" id="scenic_resource" name="scenic_resource" class="text-input" style="width: 1000px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('scenicResource','scenic_resource')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('scenic_${status.count}')"/>
										</p>
									</c:forEach>
								</p>
								<div id="scenicResource"></div>
								<p>
									<span class="public_title">&nbsp;人文配套：</span>
									<c:if test="${humanity_matching.size()==0 }">
											名称<input type="text" value="" id="humanity_matching" name="humanity_matching" class="text-input" style="width: 1000px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('humanityMatching','humanity_matching')"/>
									</c:if>
									<c:forEach items="${humanity_matching}" var="humanity" varStatus="status">
										<p id="humanity_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${humanity}" id="humanity_matching" name="humanity_matching" class="text-input" style="width: 1000px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('humanityMatching','humanity_matching')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('humanity_${status.count}')"/>
										</p>
									</c:forEach>
								</p>
								<div id="humanityMatching"></div>
								
								
								
								<p>
									<span class="public_title">&nbsp;教育配套：</span>
									<c:if test="${education_matching.size()==0 }">
										名称<input type="text" value="" id="education_matching" name="education_matching" class="text-input" style="width: 1000px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('educationMatching','education_matching')"/>
									</c:if>
									<c:forEach items="${education_matching}" var="education" varStatus="status">
										<p id="education_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${education}" id="education_matching" name="education_matching" class="text-input" style="width: 1000px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('educationMatching','education_matching')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('education_${status.count}')"/>
										</p>
									</c:forEach>
								</p>
								<div id="educationMatching"></div>
								
								
								<p>
									<span class="public_title">&nbsp;商业配套：</span>
									<c:if test="${business_matching.size()==0 }">
										名称<input type="text" value="" id="business_matching" name="business_matching" class="text-input" style="width: 1000px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('businessMatching','business_matching')"/>
									</c:if>
									<c:forEach items="${business_matching}" var="business" varStatus="status">
										<p id="business_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${business}" id="business_matching" name="business_matching" class="text-input" style="width: 1000px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('businessMatching','business_matching')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('business_${status.count}')"/>
										</p>
									</c:forEach>
								</p>
								<div id="businessMatching"></div>
								
								<p>
									<span class="public_title">&nbsp;商务配套：</span>
									<c:if test="${commerce_matching.size()==0 }">
										名称<input type="text" value="" id="commerce_matching" name="commerce_matching" class="text-input" style="width: 1000px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('commerceMatching','commerce_matching')"/>
									</c:if>
									<c:forEach items="${commerce_matching}" var="commerce" varStatus="status">
										<p id="commerce_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${commerce}" id="commerce_matching" name="commerce_matching" class="text-input" style="width: 1000px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('commerceMatching','commerce_matching')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('commerce_${status.count}')"/>
										</p>
									</c:forEach>
								</p>
								<div id="commerceMatching"></div>
								
								
								<p>
									<span class="public_title">&nbsp;休闲配套：</span>
									<c:if test="${leisure_matching.size()==0 }">
										名称<input type="text" value="" id="leisure_matching" name="leisure_matching" class="text-input" style="width: 1000px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('leisureMatching','leisure_matching')"/>
									</c:if>
									<c:forEach items="${leisure_matching}" var="leisure" varStatus="status">
										<p id="leisure_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${leisure}" id="leisure_matching" name="leisure_matching" class="text-input" style="width: 1000px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('leisureMatching','leisure_matching')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('leisure_${status.count}')"/>
										</p>
									</c:forEach>
								</p>
								<div id="leisureMatching"></div>
								
								
								
								<p>
									<span class="public_title">&nbsp;医院资源：</span>
									<c:if test="${hospital_resource.size()==0 }">
										名称<input type="text" value="" id="hospital_resource" name="hospital_resource" class="text-input" style="width: 1000px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('hospitalResource','hospital_resource')"/>
									</c:if>
									<c:forEach items="${hospital_resource}" var="hospital" varStatus="status">
										<p id="hospital_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${hospital}" id="hospital_resource" name="hospital_resource" class="text-input" style="width: 1000px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('hospitalResource','hospital_resource')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('hospital_${status.count}')"/>
										</p>
									</c:forEach>
								</p>
								<div id="hospitalResource"></div>
								
								
								<p>
									<span class="public_title">&nbsp;展示图片：</span>
									<c:if test="${exhibition_images.size()==0 }">
										名称<input type="text" value="" id="exhibition_images_name" name="exhibition_images_name" class="text-input" style="width: 140px"/>
										<span class="Validform_checktip"></span>
										路径<input type="file" value="" id="exhibition_images_url" name="exhibition_images_url" class="text-input" style="width: 160px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDivImg('exhibitionImages','exhibition_images_name','exhibition_images_url')"/>
									</c:if>
									<c:forEach items="${exhibition_images}" var="entry" varStatus="status">
										<p id="exhibition_images_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${entry.key}" id="exhibition_images_name" name="exhibition_images_name" class="text-input" style="width: 140px"/>
											<span class="Validform_checktip"></span>
											路径<input type="text" value="${entry.value}" id="exhibition_images_url" name="exhibition_images_url" class="text-input" style="width: 160px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDivImg('exhibitionImages','exhibition_images_name','exhibition_images_url')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('exhibition_images_${status.count }')"/>
										</p>
									</c:forEach>
								</p>
								<div id="exhibitionImages"></div>
								
								
								
								<p>
									<span class="public_title">&nbsp;户型图片：</span>
									<c:if test="${apartment_images.size()==0 }">
										名称<input type="text" value="" id="apartment_images_name" name="apartment_images_name" class="text-input" style="width: 140px"/>
										<span class="Validform_checktip"></span>
										路径<input type="file" value="" id="apartment_images_url" name="apartment_images_url" class="text-input" style="width: 160px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDivImg('apartmentImages','apartment_images_name','apartment_images_url')"/>
									</c:if>
									<c:forEach items="${apartment_images}" var="entry" varStatus="status">
										<p id="apartment_images_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${entry.key}" id="apartment_images_name" name="apartment_images_name" class="text-input" style="width: 140px"/>
											<span class="Validform_checktip"></span>
											路径<input type="text" value="${entry.value}" id="apartment_images_url" name="apartment_images_url" class="text-input" style="width: 160px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDivImg('apartmentImages','apartment_images_name','apartment_images_url')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('apartment_images_${status.count }')"/>
										</p>
									</c:forEach>
								</p>
								<div id="apartmentImages"></div>
								
								
								
								
								
								<p>
									<span class="public_title">&nbsp;看房图片：</span>
									<c:if test="${showings_images.size()==0 }">
										名称<input type="text" value="" id="showings_images_name" name="showings_images_name" class="text-input" style="width: 140px"/>
										<span class="Validform_checktip"></span>
										路径<input type="file" value="" id="showings_images_url" name="showings_images_url" class="text-input" style="width: 160px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDivImg('showingsImages','showings_images_name','showings_images_url')"/>
									</c:if>
									<c:forEach items="${showings_images}" var="entry" varStatus="status">
										<p id="showings_images_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${entry.key}" id="showings_images_name" name="showings_images_name" class="text-input" style="width: 140px"/>
											<span class="Validform_checktip"></span>
											路径<input type="text" value="${entry.value}" id="showings_images_url" name="showings_images_url" class="text-input" style="width: 160px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDivImg('showingsImages','showings_images_name','showings_images_url')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('showings_images_${status.count }')"/>
										</p>
									</c:forEach>
								</p>
								<div id="showingsImages"></div>
								
								
								
								<p>
									<span class="public_title">&nbsp;看房视频：</span>
									<c:if test="${showings_videos.size()==0 }">
										名称<input type="text" value="" id="showings_videos_name" name="showings_videos_name" class="text-input" style="width: 140px"/>
										<span class="Validform_checktip"></span>
										路径<input type="text" value="" id="showings_videos_url" name="showings_videos_url" class="text-input" style="width: 160px"/>
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDivVideo('showingsVideos','showings_videos_name','showings_videos_url')"/>
									</c:if>
									<c:forEach items="${showings_videos}" var="entry" varStatus="status">
										<p id="showings_videos_${status.count }">
											<span class="public_title"></span>
											名称<input type="text" value="${entry.key}" id="showings_videos_name" name="showings_videos_name" class="text-input" style="width: 140px"/>
											<span class="Validform_checktip"></span>
											路径<input type="text" value="${entry.value}" id="showings_videos_url" name="showings_videos_url" class="text-input" style="width: 160px"/>
											<span class="Validform_checktip"></span>
											<input type="button" id="introduction_Btn"  value="增加" onclick="addDivVideo('showingsVideos','showings_videos_name','showings_videos_url')"/>
											<input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv('showings_videos_${status.count }')"/>
										</p>
									</c:forEach>
								</p>
								<div id="showingsVideos"></div>
								
								
								
								
								
								
								<p>
									<span class="public_title">&nbsp;详情页首图：</span>
									<input type="text" value="${buildings.indexImage}" id="index_image" name="index_image" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
									<input type="file" value="" id="index_image_update" name="index_image_update" class="text-input small-input" style="width: 140px" />
								</p>
								<p>
									<span class="public_title">&nbsp;</span> <input type="submit"	class="button" value="更新" /> &nbsp;&nbsp;&nbsp;&nbsp; 
									<input type="button" class="button" value="返回" id="fanhui" />
								</p>
								<input type="hidden" name="buildId" id="buildId" value="${buildId}" />
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
								$("#mainForm").attr("action","${pageContext.request.contextPath}/sellHouse/addBuilding.shtml?update=update");
						
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
	function addDiv(divId,id){
		var html=$('#'+divId).html();
		$('#'+divId).html(html+"<p id='"+index+"'><span class='public_title'><font color='red'>*</font></span>名称<input type='text' value=''  name='"+id+"' class='text-input' style='width: 1000px'/><span class='Validform_checktip'></span><input type='button' id='label_Btn' value='删除' onclick='deleteDiv("+index+")'/></p>");
		index++;
	}
	function addDivImg(divId,name,url){
		var html=$('#'+divId).html();
		$('#'+divId).html(html+"<p id='"+index+"'><span class='public_title'><font color='red'>*</font></span>名称<input type='text'  id='"+name+"'  name='"+name+"' class='text-input' style='width: 140px'/>路径<input type='file'  id='"+url+"'  name='"+url+"' class='text-input' style='width: 160px'/><span class='Validform_checktip'></span><input type='button' id='label_Btn' value='删除' onclick='deleteDiv("+index+")'/></p>");
		index++;
	}
	function addDivVideo(divId,name,url){
		var html=$('#'+divId).html();
		$('#'+divId).html(html+"<p id='"+index+"'><span class='public_title'><font color='red'>*</font></span>名称<input type='text'  id='"+name+"'  name='"+name+"' class='text-input' style='width: 140px'/>路径<input type='text'  id='"+url+"'  name='"+url+"' class='text-input' style='width: 160px'/><span class='Validform_checktip'></span><input type='button' id='label_Btn' value='删除' onclick='deleteDiv("+index+")'/></p>");
		index++;
	}
	function deleteDiv(id){	
		$('#'+id).remove();
	}
</script>