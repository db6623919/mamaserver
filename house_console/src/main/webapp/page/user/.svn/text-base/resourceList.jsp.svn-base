<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/page/common/taglibs.jsp" %>
<%@ taglib prefix="define" uri="/WEB-INF/tld/define.tld"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>妈妈送房后台</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.2.js"></script>	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ztree.core-3.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ztree.excheck-3.4.js"></script>
	<style type="text/css">
	
		  body{background:#F0F0F0}
		  div{margin:0;padding:0}
		  #container{width:100%;height:100%;}
		  #container .box1{margin-left: 20px;width:200px;height:600px; float:left;}
		  #container .box2{margin-left: 240px;width:auto;height:600px; }
  	</style>
	<SCRIPT type="text/javascript">
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
		};
		var zNodes = ${menuStr};
		function onClick(event, treeId, treeNode, clickFlag) {
			$("#otherpage").attr("src","${pageContext.request.contextPath }/user/to_includeResource.shtml?id="+treeNode.id);
			
		}
		$(document).ready(function(){
			$.fn.zTree.init($("#treeResource"), setting, zNodes);
		});
	</SCRIPT>

</style>
</head>

<BODY>

<div id="body-wrapper">
			<div id="main-content">
				<div class="content-box">
					<div class="content-box-header">
						<h3>资源管理</h3>
						<div class="clear"></div>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab">
							<p style="margin-bottom: 10px;">	
							</p>
						</div>
					</div>
				</div>
			</div>
</div>

			<div id="container"> 
   							  <div class="box1"> 
   								<ul id="treeResource" class="ztree" style="width:200px;"></ul>
   							  </div> 
   							   <div class="box2">
									<iframe src="${pageContext.request.contextPath }/user/to_includeResource.shtml?id=1"  id="otherpage"  frameborder="0" style="width: 750px; height:600px"  scrolling="no"></iframe>
   								</div> 
 			</div>



 
  

<!-- <div style="width:1500;"> -->
<!-- 	<div style="float:left;"> -->
<!-- 		<ul id="treeResource" class="ztree" style="width:200px; "></ul> -->
<!-- 	</div> -->

<!-- 	<div> -->
<!-- 		<iframe src="system!getResourceById.shtml?id=1"  id="otherpage"  frameborder="0" style=" height:900px; width:900px;"></iframe> -->
<!-- 	</div> -->
<!-- </div> -->

</BODY>
</HTML>