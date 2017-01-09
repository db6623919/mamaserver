<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
var total_page = ${pager.total_page};
var action = document.forms[0].action;
function go_commonPager(){
	var current_page=document.getElementById("goCommonPageNO").value;
	if(current_page>total_page){
		//alert("请输入1到"+total_page+"之间的正整数.");
		document.forms[0].action=action+"?current_page="+total_page+"&page_flag=Y";
		document.forms[0].submit();
		return;
	}
	if(isNaN(current_page)||current_page<=0||!(/(^[0-9]*[1-9][0-9]*$)/.test(current_page))){
			//alert("请输入正整数");
			document.forms[0].action=action+"?current_page=1"+"&page_flag=Y";
			document.forms[0].submit();
		return ;
	}
	var i = action.indexOf("?");
	if(i == -1){
		document.forms[0].action=action+"?current_page="+current_page+"&page_flag=Y";
		
	}else{
		document.forms[0].action=action+"&current_page="+current_page+"&page_flag=Y";
	}
	document.forms[0].submit();
}
function do_commonPager(current_page){
	var i = action.indexOf("?");
	if(i == -1){
		document.forms[0].action=action+"?current_page="+current_page+"&page_flag=Y";
		
	}else{
		document.forms[0].action=action+"&current_page="+current_page+"&page_flag=Y";
	}
	document.forms[0].submit();
}
</script>
<div style="text-align: right; padding:5px 10px;">
	 共${pager.total_count}条 &nbsp;&nbsp;第${current_page}页/共${pager.total_page}页&nbsp;&nbsp;
	<c:if test="${pager.is_first == false }">	
		<a href="javascript:do_commonPager(${pager.first_page})" style="color: #666; margin-right: 6px;">首页</a>
		<a href="javascript:do_commonPager(${pager.pre_page})" style="color: #666; margin-right: 6px;">上一页</a>
	</c:if>
	<c:if test="${pager.is_first == true }">
		<a href="#" style="color: #666;margin-right: 6px; ">首页</a>
		<a href="#" style="color: #666; margin-right: 6px;">上一页</a>
	</c:if>			
	<c:if test="${pager.is_last == false }">		 
		<a href="javascript:do_commonPager(${pager.next_page})" style="color: #666; margin-right: 6px;">下一页</a>
		<a href="javascript:do_commonPager(${pager.last_page})" style="color: #666; margin-right: 6px;">最后一页&nbsp;</a>
	</c:if>
	<c:if test="${pager.is_last == true }">		 
		<a href="#" style="color: #666; margin-right: 6px;">下一页</a>
		<a href="#" style="color: #666; margin-right: 6px;">最后一页</a>
	</c:if>
	<input id="goCommonPageNO" type="text" size="1" style="width: 30px;"/>
	<input name="button" type="button" value="Go" onclick="go_commonPager();" class="button"/>
</div>
