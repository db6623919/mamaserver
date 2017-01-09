<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/backstageTaglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
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
							<p>
									<span class="public_title"><font color="red">*</font>&nbsp;名称：</span>
									<input type="text" value="${houseName }" id="name" name="name" class="text-input small-input" style="width: 100px" width="60px"   datatype="*"  nullmsg="请输入名称" />	
									<span class="Validform_checktip"></span>							
									</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;主     题：</span>
									<select id="theme" name="theme">
									   <option value="1">亲子出游</option>
									   <option value="2">浪漫假期</option>
									   <option value="3">新奇住宿</option>
									   <option value="4">城市周末</option>
									   <option value="5">特色主题</option>
									</select>							
									</p>
                                <p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;室：  </span>
									<input type="text" value="${room }" id="room" name="room" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入室"/>
								<span class="Validform_checktip"></span>
								   <span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;房屋类型：  </span>
									<select id="houseType" name="houseType">
									   <option value="1">别墅</option>
									   <option value="2">高档公寓</option>
									   <option value="3">酒店公寓</option>
									</select>
								</p>
								<p>
								    <span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;床型：  </span>
									<input type="text" value="" id="bedType" name="bedType" class="text-input" style="width: 140px"   datatype="*"  nullmsg="请输入床型"/>
								<span class="Validform_checktip"></span>
								   <span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;是否提供发票：  </span>
								   <select id="isInvoice" name="isInvoice">
								      <option value="1">是</option>
								       <option value="0">否</option>
								   </select>
								</p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;标志 ：</span>
									<select name="flag" id="flag">
									    <option value="1">推荐</option>
									    <option value="2">热门</option>
									</select>
									<span class="Validform_checktip"></span>
							  </p>
							  <p>
									<span class="public_title"><font color="red">*</font>&nbsp;介绍页面：</span>
									名称<input type="text" value="" id="introductionName" name="introductionName" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入名称"/>
									<span class="Validform_checktip"></span>
									路径<input type="file" value="" id="introductionImg" name="introductionImg" class="text-input" style="width: 160px"   datatype="*"  nullmsg="请输入路径"/>
									压缩<select id="compression" name="compression"><option value="0">是</option><option value="1">否</option></select>
									<span class="Validform_checktip"></span>
									<input type="button" id="introduction_Btn"  value="增加" onclick="addDiv('introductionImgUrl')"/>
								</p>
								<div id="introductionImgUrl"></div>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;标 签：</span>
									<span class="Validform_checktip"></span>
									<c:forEach items="${markList}" var="mark">
								     <input type="checkbox" name="mark" value="${mark.index }">${mark.name }
								    </c:forEach>
								</p>
								<div id="markDiv"></div>
	                             <p>
									<span class="public_title"><font color="red">*</font>&nbsp;宜住人数：</span>
									<input type="text" value="" id="live" name="live" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入宜住人数"/>
									<span class="Validform_checktip"></span>
								</p><p>
									<span class="public_title"><font color="red">*</font>&nbsp;咨询电话：</span> 
									<input type="text" value="" id="telephone" name="telephone" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入咨询电话"/>
									<span class="Validform_checktip"></span>
								 </p>
								  <p>
									<span class="public_title"><font color="red">*</font>&nbsp;入住时间：</span>
									<input type="text" value="" id="checkInTime" name="checkInTime" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入入住时间"/>
									<span class="Validform_checktip"></span>
								</p><p>
									<span class="public_title"><font color="red">*</font>&nbsp;退房时间：</span>
									<input type="text" value="" id="checkOutTime" name="checkOutTime" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入退房时间"/>
									<span class="Validform_checktip"></span>
								 </p>
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;舒适度：</span>
									<input type="text" value="" id="luxury" name="luxury" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入舒适度"/>
									<span class="Validform_checktip"></span>

									<span class="public_title"><font color="red">*</font>&nbsp;开发商：</span>
									<select id="devId" name="devId">
										<c:forEach items="${devList}" var="dev">
									      <option value="${dev.devId }">${dev.devName }</option>							    
									     </c:forEach>
								     </select>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;楼盘：</span>
									<select id="bldId" name="bldId">
										<c:forEach items="${buildings}" var="buildings">
									      <option value="${buildings.bldId }">${buildings.name }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>

									<span class="public_title"><font color="red">*</font>&nbsp;城市：</span>
									<select id="cityId" name="cityId">
										<c:forEach items="${cityInfoList}" var="city">
									      <option value="${city.cityId }">${city.cityName }</option>							    
									     </c:forEach>
								     </select>
									<span class="Validform_checktip"></span>
								</p>
								
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;详细地址：</span>
									<input type="text" value="" id="address" name="address" class="text-input small-input" style="width: 140px"  datatype="*"  nullmsg="请输入详细地址"/>
									<span class="Validform_checktip"></span>
								</p>
								
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;入住说明：</span>
									<input type="text" value="" id="checkdesc" name="checkdesc" class="text-input small-input" style="width: 140px"  datatype="*"  nullmsg="请输入入住说明"/>
									<span class="Validform_checktip"></span>
								</p>
								
							
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;设施 ：</span>
									<span class="Validform_checktip"></span>
									 <input type="checkbox" name="tv" value="1">有线电视机
									<input type="checkbox" name="fridge" value="1">冰箱
									 <input type="checkbox" name="washing" value="1">洗衣机
									<input type="checkbox" name="conditione" value="1">空调
                                    <input type="checkbox" name="towels" value="1">毛巾 
                                    <input type="checkbox" name="tooth" value="1">牙具
                                    <input type="checkbox" name="slipper" value="1">拖鞋
                                    <input type="checkbox" name="shampoo" value="1">洗发/沐浴露
                                    <input type="checkbox" name="hairdrier" value="1">吹风机
                                     <input type="checkbox" name="drinking" value="1">淋浴
                                    <input type="checkbox" name="shower" value="1">浴缸
                                    <input type="checkbox" name="hotpot" value="1">热水壶
                                     <input type="checkbox" name="heater" value="1">热水器
                                    <input type="checkbox" name="dryer" value="1">烘干机
                                    <input type="checkbox" name="smokedetector" value="1">烟雾探测器
                                    <input type="checkbox" name="heating" value="1">暖气
                                    <input type="checkbox" name="extinguisher" value="1">灭火器
                                    <input type="checkbox" name="aidkit" value="1">急救包
								</p>
								<div id="facilitiesDiv"></div>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;配套 ：</span>
									<span class="Validform_checktip"></span>
									<input type="checkbox" name="wifi" value="1">wifi
									<input type="checkbox" name="broadband" value="1">宽带 
									<input type="checkbox" name="elevator" value="1">电梯
									<input type="checkbox" name="swimming" value="1">游泳池
									<input type="checkbox" name="accesscard" value="1">门禁卡
									<input type="checkbox" name="securitystaff" value="1">保安
									<input type="checkbox" name="store" value="1">便利店
									<input type="checkbox" name="parking" value="1">停车位
									<input type="checkbox" name="gym" value="1">健身房
									<input type="checkbox" name="playground" value="1">儿童乐园
									<input type="checkbox" name="wheelchair" value="1">无障碍设施
									<input type="checkbox" name="buzzer" value="1">蜂鸣器/无线对讲机
								</p>
								<div id="supportingDiv"></div>
								
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;&nbsp;&nbsp;其他：</span>
									<span class="Validform_checktip"></span>
									<input type="checkbox" name="cook" value="1">可做饭
									<input type="checkbox" name="party" value="1">适合聚会
									<input type="checkbox" name="smoking" value="1">可吸烟
									<input type="checkbox" name="pet" value="1">可带宠物
									<input type="checkbox" name="extrabed" value="1">可加床
									<input type="checkbox" name="guests" value="1">接待外宾
									<input type="checkbox" name="breakfast" value="1">提供早餐
									<input type="checkbox" name="childrenstay" value="1">欢迎孩子入住
								</p>
								<div id="otherDiv"></div>
								
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;库存：</span>
									<input type="text" value="" id="stock" name="stock" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入库存"/>
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;视频地址：</span>
									<input type="text" value="" id="video_url" name="sideo_url" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入视频地址"/>
									<span class="Validform_checktip"></span>
							  </p>
                                 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;面积：</span>
									<input type="text" value="" id="houseArea" name="houseArea" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入视频面积"/>
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;普通定金：</span>
									<input type="text" value="" id="freezeAmt" name="freezeAmt" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入普通定金"/>
									<span class="Validform_checktip"></span>
                                   </p>
                                 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;普通全额：</span>
									<input type="text" value="" id="totalAmt" name="totalAmt" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入普通全额"/>
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;会员定金：</span>
									<input type="text" value="" id="memFreezeAmt" name="memFreezeAmt" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入会员定金"/>
									<span class="Validform_checktip"></span>
                                 </p>
                                 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;会员全额：</span>
									<input type="text" value="" id="memTotalAmt" name="memTotalAmt" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入会员全额"/>
									<span class="Validform_checktip"></span>
								</p>
								
								<p>
									<span class="public_title"><font color="red">*</font>&nbsp;状态：</span>
									<select name="stauts" id="status">
									   <option value="1">上架</option>
									   <option value="2">下架</option>
									</select>
									<span class="Validform_checktip"></span>
								</p>
								<p>
									<span class="public_title">&nbsp;地图图片：</span>
									<input type="file" name="mapImg" id="mapImg"/>
									<span class="Validform_checktip"></span>
								</p>
								 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;经度：</span>
									<input type="text" value="${longitude }" id="longitude" name="longitude" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入经度"/>
									<span class="Validform_checktip"></span>
                                 </p>
                                 <p>
									<span class="public_title"><font color="red">*</font>&nbsp;纬度：</span>
									<input type="text" value="${ latitude}" id="latitude" name="latitude" class="text-input" style="width: 140px"  datatype="*"  nullmsg="请输入纬度"/>
									<span class="Validform_checktip"></span>
									<input  type="text" id="text_" name="text_" class="text-input"/> <input type="button" value="获取经纬度" onclick="searchByStationName()"/>
								</p>
								<p>
									<span class="public_title">&nbsp;</span> <input type="submit"	class="button" value="增加" /> &nbsp;&nbsp;&nbsp;&nbsp; 
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
								var objtip = o.obj.siblings(".Validform_checktip");
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
								$("#mainForm").attr("action","${pageContext.request.contextPath}/house/houseAdd.shtml");
						
						}
					});
   function baiduMap(){
	   window.location='${pageContext.request.contextPath }/house/baiduMap.shtml';
   }
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
	var index=1;
	function addDiv(divId){
		var html=$('#'+divId).html();
		$('#'+divId).html(html+"<p id='"+index+"'><span class='public_title'><font color='red'>*</font></span>名称<input type='text'  id='introductionName'  name='introductionName' class='text-input' style='width: 140px'/>路径<input type='file'  id='introductionImg'  name='introductionImg' class='text-input' style='width: 160px'/>压缩<select id='compression' name='compression'><option value='0'>是</option><option value='1'>否</option></select><span class='Validform_checktip'></span><input type='button' id='label_Btn' value='删除' onclick='deleteDiv("+index+")'/></p>");
		index++;
	}
	function deleteDiv(id){	
		$('#'+id).remove();
	}
</script>
<script type="text/javascript">
    var map = new BMap.Map("container");
    map.centerAndZoom("宁波", 12);
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