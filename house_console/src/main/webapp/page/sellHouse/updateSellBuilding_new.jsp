<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/page/common/base.jsp" %>
		<title>修改售房楼盘</title>
		<style>
			.form-group {
				min-height: 60px;
				margin-bottom: 0
			}
		</style>
	</head>
	<body>
		<div id="wrapper">
			<!-- Navigation -->
			<jsp:include page="/page/common/menu.jsp" >
				<jsp:param value="2" name="menu"/>
				<jsp:param value="5" name="index"/>
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
										<h2 class="text-uppercase">修改售房楼盘</h2>
									</div>
									<div class="row mg-top-10">
										<form id="mainForm" action="${pageContext.request.contextPath}/sellHouse/addBuilding.shtml?update=update" method="post" enctype="multipart/form-data">
											<input type="hidden" name="bldId" value="${buildings.bldId }"/>
											<input type="hidden" name="buildId" id="buildId" value="${buildId}" />
											<div class="form-group col-xs-6">
												<label for="name" class="col-xs-2 control-label"><font class="star">*</font>名称</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.name }" name="name" id="name" class="form-control">
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
												<label for="cityIds" class="col-xs-2 control-label">城市</label>
												<div class="col-xs-10">
													<select class="form-control valid" name="cityId" id="cityId" aria-invalid="false">
														<c:forEach items="${cityList}" var="city">
													      <option value="${city.cityId }"  <c:if test="${buildings.cityId==city.cityId}">selected="selected"</c:if>>${city.cityName }</option>							    
													     </c:forEach>
													</select>
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
												<label for="project_introduction" class="col-xs-2 control-label">项目简介</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.projectIntroduction}" name="project_introduction" id="project_introduction" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="unit_area" class="col-xs-2 control-label">户型面积</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.unitArea}"  maxlength="10"  name="unit_area" id="unit_area" class="form-control" placeholder="请输入户型面积，最多输入10位">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="built_area" class="col-xs-2 control-label">建筑面积</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.builtArea}" name="built_area" id="built_area" class="form-control" placeholder="请输入建筑面积，最多两位小数">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="decoration_standard" class="col-xs-2 control-label">装修标准</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.decorationStandard}" maxlength="8" name="decoration_standard" id="decoration_standard" class="form-control" placeholder="请输入装修标准，最多输入8位">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="property_type" class="col-xs-2 control-label">物业类型</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.propertyType}" maxlength="8" name="property_type" id="property_type" class="form-control" placeholder="请输入物业类型，最多输入8位">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="average_price" class="col-xs-2 control-label">楼盘均价</label>
												<div class="col-xs-10">
													<input type="number" min="0" step="1" value="${buildings.averagePrice}" name="average_price" id="average_price" class="form-control" placeholder="请输入楼盘均价，只能输入0和正整数">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="open_date" class="col-xs-2 control-label">最近开盘日期</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.openDate}" maxlength="8" placeholder="日期格式yyyymmdd" name="open_date" id="open_date" class="form-control" placeholder="请输入最近开盘日期，如2008年08月08日，输入20080808">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="launch_date" class="col-xs-2 control-label">最早交房日期</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.launchDate}" maxlength="8" placeholder="日期格式yyyymmdd" name="launch_date" id="launch_date" class="form-control" placeholder="请输入最近开盘日期，如2008年08月08日，输入20080808">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="purchase_discount" class="col-xs-2 control-label">购房优惠</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.purchaseDiscount}" name="purchase_discount" id="purchase_discount" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="consultant_phone" class="col-xs-2 control-label">置业顾问手机</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.consultantPhone}" name="consultant_phone" id="consultant_phone" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="year_limit" class="col-xs-2 control-label">产权年限</label>
												<div class="col-xs-10">
													<input type="number" min="0" step="1" max="100" value="${buildings.yearLimit}" name="year_limit" id="year_limit" class="form-control" placeholder="请输入产权年限，只能输入0和正整数或">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="built_type" class="col-xs-2 control-label">建筑类型</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.builtType}" name="built_type" id="built_type" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="volume_rate" class="col-xs-2 control-label">容积率</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.volumeRate}" name="volume_rate" id="volume_rate" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="greening_rate" class="col-xs-2 control-label">绿化率</label>
												<div class="col-xs-10">
													 <input type="text" value="${buildings.greeningRate}" name="greening_rate" id="greening_rate" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="plan_households" class="col-xs-2 control-label">规划户数</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.planHouseholds}" name="plan_households" id="plan_households" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="plan_parking" class="col-xs-2 control-label">规划车位</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.planParking}" name="plan_parking" id="plan_parking" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="presale_permit" class="col-xs-2 control-label">预售许可</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.presalePermit}" name="presale_permit" id="presale_permit" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="property_comp_name" class="col-xs-2 control-label">物业公司全称</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.propertyCompName}" name="property_comp_name" id="property_comp_name" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="property_fee" class="col-xs-2 control-label">物业费</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.propertyFee}" name="property_fee" id="property_fee" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="hearting_mode" class="col-xs-2 control-label">供暖方式</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.heartingMode}" name="hearting_mode" id="hearting_mode" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="water_elec" class="col-xs-2 control-label">水电煤气</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.waterElec}" name="water_elec" id="water_elec" class="form-control">
												</div>
											</div>
											<div class="form-group col-xs-6">
												<label for="project_feature" class="col-xs-2 control-label">项目特色</label>
												<div class="col-xs-10">
													<input type="text" value="${buildings.projectFeature}" name="project_feature" id="project_feature" class="form-control">
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="project_feature" class="col-xs-1 control-label">景观资源</label>
												<div class="col-xs-10">
													<c:if test="${scenic_resource.size()==0 }">
														名称 <input type="text" value="" id="scenic_resource" name="scenic_resource" class="form-control inline w-80" />
														<input type="button"   value="增加" class="btn  btn-style" onclick="addDiv('scenicResource','scenic_resource')"/>
													</c:if>
													<c:forEach items="${scenic_resource}" var="scenic" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="scenic_${status.count}">
																<span class="public_title"></span>
																	名称 <input type="text" value="${scenic}" id="scenic_resource" name="scenic_resource" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn"  value="增加" class="btn  btn-style" onclick="addDiv('scenicResource','scenic_resource')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="scenic_${status.count}">
																<span class="public_title"></span>
																	名称 <input type="text" value="${scenic}" id="scenic_resource" name="scenic_resource" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn"  value="删除" class="btn  btn-style" onclick="deleteDiv('scenic_${status.count}')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="scenicResource"></p>
													
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="project_feature" class="col-xs-1 control-label">人文配套</label>
												<div class="col-xs-10">
													<c:if test="${humanity_matching.size()==0 }">
														<p>
															名称 <input type="text" value="" id="humanity_matching" name="humanity_matching" class="form-control inline w-80" />
															<input type="button"  value="增加" class="btn  btn-style" onclick="addDiv('humanityMatching','humanity_matching')"/>
														</p>
													</c:if>
													<c:forEach items="${humanity_matching}" var="humanity" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="humanity_${status.count }">
																	名称 <input type="text" value="${humanity}" id="humanity_matching" name="humanity_matching" class="form-control inline w-80"/>
																	<span class="Validform_checktip"></span>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="增加" onclick="addDiv('humanityMatching','humanity_matching')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="humanity_${status.count }">
																	名称 <input type="text" value="${humanity}" id="humanity_matching" name="humanity_matching" class="form-control inline w-80"/>
																	<span class="Validform_checktip"></span>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="删除" onclick="deleteDiv('humanity_${status.count}')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="humanityMatching"></p>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="project_feature" class="col-xs-1 control-label">教育配套</label>
												<div class="col-xs-10">
													<c:if test="${education_matching.size()==0 }">
													<p>
														名称 <input type="text" value="" id="education_matching" name="education_matching" class="form-control inline w-80" />
														<input type="button"   value="增加" class="btn  btn-style" onclick="addDiv('educationMatching','education_matching')"/>
													</p>
													</c:if>
													<c:forEach items="${education_matching}" var="education" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="education_${status.count }">
																	名称 <input type="text" value="${education}" id="education_matching" name="education_matching" class="form-control inline w-80" />
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="增加" onclick="addDiv('educationMatching','education_matching')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="education_${status.count }">
																	名称 <input type="text" value="${education}" id="education_matching" name="education_matching" class="form-control inline w-80" />
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="删除" onclick="deleteDiv('education_${status.count}')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="educationMatching"></p>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="project_feature" class="col-xs-1 control-label">商业配套</label>
												<div class="col-xs-10">
													<c:if test="${business_matching.size()==0 }">
													<p>
														名称 <input type="text" value="" id="business_matching" name="business_matching" class="form-control inline w-80" />
														<input type="button"   value="增加" class="btn  btn-style" onclick="addDiv('businessMatching','business_matching')"/>
													</p>
													</c:if>
													<c:forEach items="${business_matching}" var="business" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="business_${status.count }">
																	名称 <input type="text" value="${business}" id="business_matching" name="business_matching" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style" value="增加" onclick="addDiv('businessMatching','business_matching')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="business_${status.count }">
																	名称 <input type="text" value="${business}" id="business_matching" name="business_matching" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style" value="删除" onclick="deleteDiv('business_${status.count}')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="businessMatching"></p>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="project_feature" class="col-xs-1 control-label">商务配套</label>
												<div class="col-xs-10">
													<c:if test="${commerce_matching.size()==0 }">
														<p>
															名称 <input type="text" value="" id="commerce_matching" name="commerce_matching" class="form-control inline w-80" />
															<input type="button"   value="增加" class="btn  btn-style" onclick="addDiv('commerceMatching','commerce_matching')"/>
														</p>
													</c:if>
													<c:forEach items="${commerce_matching}" var="commerce" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="commerce_${status.count }">
																	名称 <input type="text" value="${commerce}" id="commerce_matching" name="commerce_matching" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="增加" onclick="addDiv('commerceMatching','commerce_matching')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="commerce_${status.count }">
																	名称 <input type="text" value="${commerce}" id="commerce_matching" name="commerce_matching" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="删除" onclick="deleteDiv('commerce_${status.count}')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="commerceMatching"></p>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="project_feature" class="col-xs-1 control-label">休闲配套</label>
												<div class="col-xs-10">
													<c:if test="${leisure_matching.size()==0 }">
														<p>
															名称 <input type="text" value="" id="leisure_matching" name="leisure_matching" class="form-control inline w-80" />
															<input type="button"   value="增加" class="btn  btn-style" onclick="addDiv('leisureMatching','leisure_matching')"/>
														</p>
													</c:if>
													<c:forEach items="${leisure_matching}" var="leisure" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="leisure_${status.count }">
																	名称 <input type="text" value="${leisure}" id="leisure_matching" name="leisure_matching" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="增加" onclick="addDiv('leisureMatching','leisure_matching')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="leisure_${status.count }">
																	名称 <input type="text" value="${leisure}" id="leisure_matching" name="leisure_matching" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="删除" onclick="deleteDiv('leisure_${status.count}')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="leisureMatching"></p>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="project_feature" class="col-xs-1 control-label">医院资源</label>
												<div class="col-xs-10">
													<c:if test="${hospital_resource.size()==0 }">
													<p>
														名称 <input type="text" value="" id="hospital_resource" name="hospital_resource" class="form-control inline w-80" />
														<input type="button"   value="增加" class="btn  btn-style" onclick="addDiv('hospitalResource','hospital_resource')"/>
													</p>
													</c:if>
													<c:forEach items="${hospital_resource}" var="hospital" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="hospital_${status.count }">
																	名称 <input type="text" value="${hospital}" id="hospital_resource" name="hospital_resource" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="增加" onclick="addDiv('hospitalResource','hospital_resource')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="hospital_${status.count }">
																	名称 <input type="text" value="${hospital}" id="hospital_resource" name="hospital_resource" class="form-control inline w-80"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="删除" onclick="deleteDiv('hospital_${status.count}')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="hospitalResource"></p>
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="bedType" class="col-xs-1 control-label">展示图片</label>
												<div class="col-xs-11">
													<c:if test="${exhibition_images.size()==0 }">
														<p>
															名称 <input type="text" value="" id="exhibition_images_name" name="exhibition_images_name" class="form-control w-260 inline"/>
															路径 <input type="file" value="" id="exhibition_images_url" name="exhibition_images_url" class="form-control w-260 inline"/>
															<input type="button" id="introduction_Btn"  value="增加" class="btn  btn-style" onclick="addDivImg('exhibitionImages','exhibition_images_name','exhibition_images_url')"/>
														</p>
													</c:if>
													<c:forEach items="${exhibition_images}" var="entry" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="exhibition_images_${status.count }">
																	名称 <input type="text" value="${entry.key}" id="exhibition_images_name" name="exhibition_images_name" class="form-control w-260 inline"/>
																	路径 <input type="text" value="${entry.value}" id="exhibition_images_url" name="exhibition_images_url" class="form-control w-260 inline"/>
																	<input type="button" id="introduction_Btn"  class="btn  btn-style" value="增加" onclick="addDivImg('exhibitionImages','exhibition_images_name','exhibition_images_url')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="exhibition_images_${status.count }">
																	名称 <input type="text" value="${entry.key}" id="exhibition_images_name" name="exhibition_images_name" class="form-control w-260 inline"/>
																	路径 <input type="text" value="${entry.value}" id="exhibition_images_url" name="exhibition_images_url" class="form-control w-260 inline"/>
																	<input type="button" id="introduction_Btn"  class="btn  btn-style" value="删除" onclick="deleteDiv('exhibition_images_${status.count }')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="exhibitionImages" >
													</p>
												</div>
											</div>
											
											
											<div class="form-group col-xs-12">
												<label for="bedType" class="col-xs-1 control-label">户型图片</label>
												<div class="col-xs-11">
													<c:if test="${apartment_images.size()==0 }">
														<p>
															名称 <input type="text" value="" id="apartment_images_name" name="apartment_images_name" class="form-control w-260 inline"/>
															路径 <input type="file" value="" id="apartment_images_url" name="apartment_images_url" class="form-control w-260 inline"/>
															<input type="button" id="introduction_Btn"  value="增加" class="btn  btn-style" onclick="addDivImg('apartmentImages','apartment_images_name','apartment_images_url')"/>
														</p>
													</c:if>
													<c:forEach items="${apartment_images}" var="entry" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="apartment_images_${status.count }">
																	名称 <input type="text" value="${entry.key}" id="apartment_images_name" name="apartment_images_name" class="form-control w-260 inline"/>
																	路径 <input type="text" value="${entry.value}" id="apartment_images_url" name="apartment_images_url" class="form-control w-260 inline"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style" value="增加" onclick="addDivImg('apartmentImages','apartment_images_name','apartment_images_url')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="apartment_images_${status.count }">
																	名称 <input type="text" value="${entry.key}" id="apartment_images_name" name="apartment_images_name" class="form-control w-260 inline"/>
																	路径 <input type="text" value="${entry.value}" id="apartment_images_url" name="apartment_images_url" class="form-control w-260 inline"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style" value="删除" onclick="deleteDiv('apartment_images_${status.count }')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="apartmentImages" >
													</p>
												</div>
											</div>
											<div class="form-group col-xs-12">
												<label for="bedType" class="col-xs-1 control-label">看房图片</label>
												<div class="col-xs-11">
													<c:if test="${showings_images.size()==0 }">
														<p>
															名称 <input type="text" value="" id="showings_images_name" name="showings_images_name" class="form-control w-260 inline"/>
															路径 <input type="file" value="" id="showings_images_url" name="showings_images_url" class="form-control w-260 inline"/>
															<input type="button" id="introduction_Btn"  value="增加" class="btn  btn-style" onclick="addDivImg('showingsImages','showings_images_name','showings_images_url')"/>
														</p>
													</c:if>
													<c:forEach items="${showings_images}" var="entry" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="showings_images_${status.count }">
																	名称 <input type="text" value="${entry.key}" id="showings_images_name" name="showings_images_name" class="form-control w-260 inline"/>
																	路径 <input type="text" value="${entry.value}" id="showings_images_url" name="showings_images_url" class="form-control w-260 inline"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="增加" onclick="addDivImg('showingsImages','showings_images_name','showings_images_url')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="showings_images_${status.count }">
																	名称 <input type="text" value="${entry.key}" id="showings_images_name" name="showings_images_name" class="form-control w-260 inline"/>
																	路径 <input type="text" value="${entry.value}" id="showings_images_url" name="showings_images_url" class="form-control w-260 inline"/>
																	<input type="button" id="introduction_Btn"  class="btn  btn-style" value="删除" onclick="deleteDiv('showings_images_${status.count }')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="showingsImages" >
													</p>
												</div>
											</div>
											
											<div class="form-group col-xs-12">
												<label for="bedType" class="col-xs-1 control-label">看房视频</label>
												<div class="col-xs-11">
													<c:if test="${showings_videos.size()==0 }">
														<p>
															名称 <input type="text" value="" id="showings_videos_name" name="showings_videos_name" class="form-control w-260 inline"/>
															路径 <input type="file" value="" id="showings_videos_url" name="showings_videos_url" class="form-control w-260 inline"/>
															<input type="button" id="introduction_Btn"  value="增加" class="btn  btn-style" onclick="addDivImg('showingsVideos','showings_videos_name','showings_videos_url')"/>
														</p>
													</c:if>
													<c:forEach items="${showings_videos}" var="entry" varStatus="status">
														<c:choose>
															<c:when test="${status.first }">
																<p id="showings_videos_${status.count }">
																	名称 <input type="text" value="${entry.key}" id="showings_videos_name" name="showings_videos_name" class="form-control w-260 inline"/>
																	路径 <input type="text" value="${entry.value}" id="showings_videos_url" name="showings_videos_url" class="form-control w-260 inline"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="增加" onclick="addDivImg('showingsVideos','showings_videos_name','showings_videos_url')"/>
																</p>
															</c:when>
															<c:otherwise>
																<p id="showings_videos_${status.count }">
																	名称 <input type="text" value="${entry.key}" id="showings_videos_name" name="showings_videos_name" class="form-control w-260 inline"/>
																	路径 <input type="text" value="${entry.value}" id="showings_videos_url" name="showings_videos_url" class="form-control w-260 inline"/>
																	<input type="button" id="introduction_Btn" class="btn  btn-style"  value="删除" onclick="deleteDiv('showings_videos_${status.count }')"/>
																</p>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<p id="showingsVideos" >
													</p>
												</div>
											</div>
											
											<div class="form-group col-xs-12 text-right">
												<input type="submit" class="btn  btn-style" value="修改" />
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
			$("#userFrom").validate({
				rules: {
					inputName: "required",
				},
				messages: {
					inputName: "请输入楼盘名称",
				}
			});
		});
		var index=0;
		function addDiv(divId,id){
			$('#'+divId).append("<p id='"+index+"'>名称<input type='text' value=''  name='"+id+"' class='form-control inline w-80'/> <input type='button' id='label_Btn' value='删除' class='btn  btn-style' onclick='deleteDiv("+index+")'/></p>");
			index++;
		}
		function addDivImg(divId,name,url){
			$('#'+divId).append("<p id='"+index+"'>名称 <input type='text'  id='"+name+"'  name='"+name+"' class='form-control inline w-260'/> 路径 <input type='file'  id='"+url+"'  name='"+url+"' class='form-control inline w-260' /> <input type='button' id='label_Btn' class='btn  btn-style' value='删除' onclick='deleteDiv("+index+")'/></p>");
			index++;
		}
		function deleteDiv(id){	
			$('#'+id).remove();
		}
		
		function getCity(){
			var provinceId = $("#proId").val();
			$("#cityId").empty();
			$.getJSON("${pageContext.request.contextPath}/city/getCitysByProId.shtml?provinceId="+provinceId,function(data){
				var Data = eval(data);
				var json1 = Data.result.list; // item是数组
				var data = '';
				$.each(json1,function(i,rs){
				    data += "<option value="+json1[i].cityId+">"+json1[i].cityName+"</option>";
				});
				$("#cityId").innerHTML = "";
			    $("#cityId").append(data);
			})
		}
		
	</script>

</html>