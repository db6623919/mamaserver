<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/page/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>妈妈送房后台</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="./../css/demo.css" type="text/css">
	<link rel="stylesheet" href="./../css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="./../js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="./../js/jquery.ztree.core-3.4.js"></script>
	<script type="text/javascript" src="./../js/jquery.ztree.excheck-3.4.js"></script>
	<SCRIPT type="text/javascript">
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: onCheck
			}
		};

		var zNodes =${menuStr};
		
		function onCheck(e, treeId, treeNode) {
		}		

		$(document).ready(function(){
			$.fn.zTree.init($("#resourcetree"), setting, zNodes);
					
		});
		
		function chooseBox(){
			var treeObj = $.fn.zTree.getZTreeObj("resourcetree");
			var nodes = treeObj.getCheckedNodes(true);
			var ids;
			var names;
			for(var i=0;i<nodes.length;i++)   
			{   
				ids = ids +","+ nodes[i].id;
				names = names +","+ nodes[i].name;
						       
			}
			if(ids == undefined){
				window.opener.document.getElementById("resourceids").value = "";
			}else{
				window.opener.document.getElementById("resourceids").value = ids.replace("undefined,","");
			}
			if(ids == undefined){
				window.opener.document.getElementById("resourcenames").value = "";
			}else{
				window.opener.document.getElementById("resourcenames").value = names.replace("undefined,","");
			}
		   	window.close();
		}
	</SCRIPT>
 </HEAD>

<BODY>

<div align="center">
	<div class="zTreeDemoBackground center">
		<ul id="resourcetree" class="ztree"></ul>
		<ul><input type="button" value="选择" onclick="chooseBox();"></input> </ul>
	</div>
</div>

</BODY>
</HTML>