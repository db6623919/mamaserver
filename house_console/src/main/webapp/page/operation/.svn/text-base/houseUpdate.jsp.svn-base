<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
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
					<h3>新增房源
					</h3>
					<div class="clear"></div>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<form id="mainForm" action="" method="post" enctype="multipart/form-data">
							<fieldset>
							<input type="hidden" name="houseId" value="${houseId }"/>
							<p>
									<span class="public_title"><font color="red">*</font>&nbsp;名称：</span>
									<input type="text" value="${houseName }" id="name" name="name" class="text-input small-input" style="width: 100px" width="60px" />								
									</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;主     题：</span>
									<select id="theme" name="theme">
									   <option value="1" <c:if test="${theme=='1'}">selected="selected"</c:if>>亲子出游</option>
									   <option value="2" <c:if test="${theme=='2'}">selected="selected"</c:if>>浪漫假期</option>
									   <option value="3" <c:if test="${theme=='3'}">selected="selected"</c:if>>新奇住宿</option>
									   <option value="4" <c:if test="${theme=='4'}">selected="selected"</c:if>>城市周末</option>
									   <option value="5" <c:if test="${theme=='5'}">selected="selected"</c:if>>特色主题</option>
									</select>							
									</p>
                                <p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;室：  </span>
									<input type="text" value="${room }" id="room" name="room" class="text-input" style="width: 140px" />
								
								   <span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;房屋类型：  </span>
								   	<select id="houseType" name="houseType">
									   <option value="1" <c:if test="${houseType=='1'}">selected="selected"</c:if>>别墅</option>
									   <option value="2" <c:if test="${houseType=='2'}">selected="selected"</c:if>>高档公寓</option>
									   <option value="3" <c:if test="${houseType=='3'}">selected="selected"</c:if>>酒店公寓</option>
									</select>	
								</p>
								<p>
								    <span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;床型：  </span>
									<input type="text" value="${bedType }" id="bedType" name="bedType" class="text-input" style="width: 140px" />
								
								   <span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;是否提供发票：  </span>
								   <select id="isInvoice" name="isInvoice">
								      <option value="1"  <c:if test="${isInvoice=='1' }">selected="selected"</c:if> >是</option>
								       <option value="0" <c:if test="${isInvoice=='0' }">selected="selected"</c:if>>否</option>
								   </select>
								</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;标志 ：</span>
									<select name="flag" id="flag">
									    <option value="1" <c:if test="${flag=='1' }">selected="selected"</c:if>>推荐</option>
									    <option value="2" <c:if test="${flag=='2' }">selected="selected"</c:if>>热门</option>
									</select>
									<span class="Validform_checktip"></span>
							  </p>
							       <p>
									<span class="public_title"><font color="red">*</font>&nbsp;介绍页面：</span>
									<c:if test="${IntroductionList.size()==0 }">
									       名称<input type="text" value="" id="introductionName" name="introductionName" class="text-input" style="width: 140px" />
										路径<input type="file" value="" id="introductionImg" name="introductionImg" class="text-input" style="width: 160px" />
										<span class="Validform_checktip"></span>
										<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('introductionImg','introductionImgUrl')"/>
									</c:if>
									</p>
									<c:forEach items="${IntroductionList }" var="introduction" varStatus="status">
									 <p id="${status.count }">
									   <span class="public_title"><font color="red">*</font>&nbsp;：</span>
										名称<input type="text" value="${introduction.introductionName }" id="introductionName" name="introductionName" class="text-input" style="width: 140px" />
										路径<input type="text" value="${introduction.introductionImg }" id="introductionImg" name="introductionImg" class="text-input" style="width: 160px" />
										<span class="Validform_checktip"></span>
										<c:if test="${status.first}"><input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('introductionImg','introductionImgUrl')"/></c:if>
										
					                     <input type="button" id="introduction_Btn"  value="删除" onclick="deleteDiv(${status.count })"/>
										</p>
								</c:forEach>
							
								<div id="introductionImgUrl"></div>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;标 签：</span>
									<span class="Validform_checktip"></span>
									<c:forEach items="${markList}" var="mark1">
								     <input type="checkbox" name="mark" <c:if test="${mark1.check==1 }"> checked="checked"</c:if>  value="${mark1.index }">${mark1.name }
								    </c:forEach>
								</p>
								<div id="markDiv"></div>
	                             <p>
									<span class="public_title"><font color="red">*</font>&nbsp;宜住人数：</span>
									<input type="text" value="${live }" id="live" name="live" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
								
									<span class="public_title"><font color="red">*</font>&nbsp;咨询电话：</span>
									<input type="text" value="${telephone }" id="telephone" name="telephone" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
								 </p>
								  <p>
									<span class="public_title"><font color="red">*</font>&nbsp;入住时间：</span>
									<input type="text" value="${ checkInTime}" id="checkInTime" name="checkInTime" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
								
									<span class="public_title"><font color="red">*</font>&nbsp;退房时间：</span>
									<input type="text" value="${ checkOutTime}" id="checkOutTime" name="checkOutTime" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
								 </p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;舒适度：</span>
									<input type="text" value="${luxury }" id="luxury" name="luxury" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>

									<span class="public_title"><font color="red">*</font>&nbsp;开发商：</span>
									<select id="devId" name="devId">
										<c:forEach items="${devList}" var="dev">
									      <option value="${dev.devId }" <c:if test="${dev.devId==devId }">selected="selected"</c:if> >${dev.devName }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;楼盘：</span>
									<select id="bldId" name="bldId">
										<c:forEach items="${buildings}" var="buildings">
									      <option value="${buildings.bldId }"  <c:if test="${bldId==buildings.bldId }">selected="selected"</c:if> >${buildings.name }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>

									<span class="public_title"><font color="red">*</font>&nbsp;城市：</span>
									<select id="cityId" name="cityId">
										<c:forEach items="${cityInfoList}" var="city">
									      <option value="${city.cityId }"  <c:if test="${city.cityId==cityId }">selected="selected"</c:if>>${city.cityName }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>
								</p>
								
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;详细地址：</span>
									<input type="text" value="${ address}" id="address" name="address" class="text-input small-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
									<span class="public_title"><font color="red">*</font>&nbsp;面积：</span>
									<input type="text" value="${houseArea }" id="houseArea" name="houseArea" class="text-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;入住说明：</span>
									<input type="text" value="${ checkdesc}" id="checkdesc" name="checkdesc" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;淡旺季说明：</span>
									<input type="text" value="${ inSeasonDesc}" id="inSeasonDesc" name="inSeasonDesc" class="text-input small-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								
							
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;设施 ：</span>
									<span class="Validform_checktip"></span>
									<input type="checkbox" name="tv" <c:if test="${tv=='1' }">checked="checked"</c:if> value="1">有线电视机
									<input type="checkbox" name="fridge" <c:if test="${fridge=='1' }">checked="checked"</c:if> value="1">冰箱
									<input type="checkbox" name="washing" <c:if test="${washing=='1' }">checked="checked"</c:if> value="1">洗衣机
									<input type="checkbox" name="conditione"  <c:if test="${conditione=='1' }">checked="checked"</c:if> value="1">空调
                                    <input type="checkbox" name="towels" <c:if test="${towels=='1' }">checked="checked"</c:if> value="1">毛巾 
                                    <input type="checkbox" name="tooth" <c:if test="${tooth=='1' }">checked="checked"</c:if> value="1">牙具
                                    <input type="checkbox" name="slipper" <c:if test="${slipper=='1' }">checked="checked"</c:if> value="1">拖鞋
                                    <input type="checkbox" name="shampoo" <c:if test="${shampoo=='1' }">checked="checked"</c:if> value="1">洗发/沐浴露
                                    <input type="checkbox" name="hairdrier" <c:if test="${hairdrier=='1' }">checked="checked"</c:if> value="1">吹风机
                                     <input type="checkbox" name="drinking" <c:if test="${drinking=='1' }">checked="checked"</c:if> value="1">淋浴
                                    <input type="checkbox" name="shower" <c:if test="${shower=='1' }">checked="checked"</c:if> value="1">浴缸
                                    <input type="checkbox" name="hotpot" <c:if test="${hotpot=='1' }">checked="checked"</c:if> value="1">热水壶
                                     <input type="checkbox" name="heater" <c:if test="${heater=='1' }">checked="checked"</c:if> value="1">热水器
                                    <input type="checkbox" name="dryer" <c:if test="${dryer=='1' }">checked="checked"</c:if> value="1">烘干机
                                    <input type="checkbox" name="smokedetector" <c:if test="${smokedetector=='1' }">checked="checked"</c:if> value="1">烟雾探测器
                                    <input type="checkbox" name="heating" <c:if test="${heating=='1' }">checked="checked"</c:if> value="1">暖气
                                    <input type="checkbox" name="extinguisher" <c:if test="${extinguisher=='1' }">checked="checked"</c:if> value="1">灭火器
                                    <input type="checkbox" name="aidkit" <c:if test="${aidkit=='1' }">checked="checked"</c:if> value="1">急救包
								</p>
								<div id="facilitiesDiv"></div>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;配套 ：</span>
									<span class="Validform_checktip"></span>
									<input type="checkbox" name="wifi" value="1" <c:if test="${wifi=='1' }">checked="checked"</c:if>>wifi
									<input type="checkbox" name="broadband" <c:if test="${broadband=='1' }">checked="checked"</c:if> value="1">宽带 
									<input type="checkbox" name="elevator" <c:if test="${elevator=='1' }">checked="checked"</c:if> value="1">电梯
									<input type="checkbox" name="swimming" <c:if test="${swimming=='1' }">checked="checked"</c:if> value="1">游泳池
									
									<input type="checkbox" name="accesscard" <c:if test="${accesscard=='1' }">checked="checked"</c:if> value="1">门禁卡
									<input type="checkbox" name="securitystaff" <c:if test="${securitystaff=='1' }">checked="checked"</c:if> value="1">保安
									<input type="checkbox" name="store" <c:if test="${store=='1' }">checked="checked"</c:if> value="1">便利店
									<input type="checkbox" name="parking" <c:if test="${parking=='1' }">checked="checked"</c:if> value="1">停车位
									<input type="checkbox" name="gym" <c:if test="${gym=='1' }">checked="checked"</c:if> value="1">健身房
									<input type="checkbox" name="playground" <c:if test="${playground=='1' }">checked="checked"</c:if> value="1">儿童乐园
									<input type="checkbox" name="wheelchair" <c:if test="${wheelchair=='1' }">checked="checked"</c:if> value="1">无障碍设施
									<input type="checkbox" name="buzzer" <c:if test="${buzzer=='1' }">checked="checked"</c:if> value="1">蜂鸣器/无线对讲机
								</p>
								<div id="supportingDiv"></div>
								
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;其他：</span>
									<span class="Validform_checktip"></span>
									<input type="checkbox" name="cook" <c:if test="${cook=='1' }">checked="checked"</c:if> value="1">可做饭
									<input type="checkbox" name="party" <c:if test="${party=='1' }">checked="checked"</c:if> value="1">可聚会
									<input type="checkbox" name="smoking" <c:if test="${smoking=='1' }">checked="checked"</c:if> value="1">可吸烟
									
									<input type="checkbox" name="pet" <c:if test="${pet=='1' }">checked="checked"</c:if> value="1">可带宠物
									<input type="checkbox" name="extrabed" <c:if test="${extrabed=='1' }">checked="checked"</c:if> value="1">可加床
									<input type="checkbox" name="guests" <c:if test="${guests=='1' }">checked="checked"</c:if> value="1">接待外宾
									<input type="checkbox" name="breakfast" <c:if test="${breakfast=='1' }">checked="checked"</c:if> value="1">提供早餐
									<input type="checkbox" name="childrenstay" <c:if test="${childrenstay=='1' }">checked="checked"</c:if> value="1">欢迎孩子入住
								</p>
								<div id="otherDiv"></div>
								
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;库存：</span>
									<input type="text" value="${stock }" id="stock" name="stock" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;视频地址：</span>
									<input type="text" value="${videoUrl }" id="video_url" name="sideo_url" class="text-input" style="width: 140px" />
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;普通定金：</span>
									<input type="text" value="${freezeAmt }" id="freezeAmt" name="freezeAmt" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>

									<span class="public_title"><font color="red">*</font>&nbsp;普通全额：</span>
									<input type="text" value="${totalAmt }" id="totalAmt" name="totalAmt" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;会员定金：</span>
									<input type="text" value="${memFreezeAmt }" id="memFreezeAmt" name="memFreezeAmt" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>

									<span class="public_title"><font color="red">*</font>&nbsp;会员全额：</span>
									<input type="text" value="${memTotalAmt }" id="memTotalAmt" name="memTotalAmt" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;状态：</span>
									<select name="stauts" id="status">
									   <option value="1" <c:if test="${status=='1' }">selected="selected"</c:if>>上架</option>
									   <option value="2"  <c:if test="${status=='2' }">selected="selected"</c:if>>下架</option>
									</select>
									<span class="Validform_checktip"></span>
								</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;地图图片：</span>
									<input type="file" name="mapImg" id="mapImg" />${mapImg }
									<span class="Validform_checktip"></span>
									是否替换
									<select id="mapImgFlag" name="mapImgFlag">
									    <option value="0">否</option>
									    <option value="1">是</option>
									</select>
								</p>
								  <p>
									<span class="public_title"><font color="red">*</font>&nbsp;经度：</span>
									<input type="text" value="${ longitude}" id="longitude" name="longitude" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>

									<span class="public_title"><font color="red">*</font>&nbsp;纬度：</span>
									<input type="text" value="${ latitude}" id="latitude" name="latitude" class="text-input" style="width: 140px"/>
									<span class="Validform_checktip"></span>
									<input  type="text" id="text_" name="text_" class="text-input"/> <input type="button" value="获取经纬度" onclick="searchByStationName()"/>
								</p>
								<p>
									<span class="public_title">&nbsp;</span> <input type="submit"	class="button" value="保存" /> &nbsp;&nbsp;&nbsp;&nbsp; 
									<input type="button" class="button" value="返回" id="fanhui" />
								</p>
								<p>
								 <div id="container" 
							            style="position: absolute;
							                margin-top:30px; 
							                width: 730px; 
							                height: 590px; 
							                top: 50; 
							                border: 1px solid gray;
							                overflow:hidden;">
							        </div>
															
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
								$("#mainForm").attr("action","${pageContext.request.contextPath}/house/houseAdd.shtml?flagUpdate=update");
						
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
							$("#backForm")
									.attr(
											"action",
											"${pageContext.request.contextPath }/house/toHouse.shtml");
							$("#backForm").submit();
						});
	});
	var index=100;
	function addDiv(id,divId){
		var html=$('#'+divId).html();
		$('#'+divId).html(html+"<p id='"+index+"'><span class='public_title'><font color='red'>*</font></span>名称<input type='text' value=''  name='introductionName' class='text-input' style='width: 140px'/>路径<input type='file' value=''  name='"+id+"' class='text-input' style='width: 160px'/>压缩<select id='compression' name='compression'><option value='0'>是</option><option value='1'>否</option></select><span class='Validform_checktip'></span><input type='button' id='label_Btn' value='删除' onclick='deleteDiv("+index+")'/></p>");
		index++;
	}
	function deleteDiv(id){	
		$('#'+id).remove();
	}
</script>
<script type="text/javascript">
    var address=$('#address').val();
    var map = new BMap.Map("container");
    map.centerAndZoom(""+address+"", 12);
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT }));   //右下角，打开

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
function searchByStationName() {
    map.clearOverlays();//清空原来的标注
    var keyword = document.getElementById("text_").value;
    localSearch.setSearchCompleteCallback(function (searchResult) {
        var poi = searchResult.getPoi(0);
        document.getElementById("longitude").value = poi.point.lng ;
        document.getElementById("latitude").value =  poi.point.lat;
        map.centerAndZoom(poi.point, 13);
       var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
        map.addOverlay(marker);
        var content = document.getElementById("text_").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
        marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
         marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    });
    localSearch.search(keyword);
} 
</script>