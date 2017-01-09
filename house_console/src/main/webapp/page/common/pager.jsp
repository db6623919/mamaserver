<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
var total_page = ${pager.total_page};
var action = document.forms[0].action;
var words = action.split("?");
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
	document.forms[0].action=action+"?current_page="+current_page+"&page_flag=Y";
	document.forms[0].submit();
}
function do_commonPager(current_page){
	if(words[1]!= null){
		document.forms[0].action=action+"&current_page="+current_page+"&page_flag=Y";
	}else{
		document.forms[0].action=action+"?current_page="+current_page+"&page_flag=Y";
	}
	document.forms[0].submit();
}
</script>
<div style="text-align: right; padding:5px 10px;">
	<font color="green">共${pager.total_count}条 &nbsp;&nbsp;第${pager.current_page}页/共${pager.total_page}页</font>&nbsp;&nbsp;
	<c:if test="${pager.is_first == false }">	
		<a href="javascript:do_commonPager(${pager.first_page})" style="color: #666; margin-right: 6px;"><font color="green">首页</font></a>
		<a href="javascript:do_commonPager(${pager.pre_page})" style="color: #666; margin-right: 6px;"><font color="green">上一页</font></a>
	</c:if>
	<c:if test="${pager.is_first == true }">
		<a href="#" style="color: #666;margin-right: 6px; "><font color="green">首页</font></a>
		<a href="#" style="color: #666; margin-right: 6px;"><font color="green">上一页</font></a>
	</c:if>			
	<c:if test="${pager.is_last == false}">		 
		<a href="javascript:do_commonPager(${pager.next_page})" style="color: #666; margin-right: 6px;"><font color="green">下一页</font></a>
		<a href="javascript:do_commonPager(${pager.last_page})" style="color: #666; margin-right: 6px;"><font color="green">最后一页</font>&nbsp;</a>
	</c:if>
	<c:if test="${pager.is_last == true }">		 
		<a href="#" style="color: #666; margin-right: 6px;"><font color="green">下一页</font></a>
		<a href="#" style="color: #666; margin-right: 6px;"><font color="green">最后一页</font></a>
	</c:if>
	<input id="goCommonPageNO" type="text" size="1" style="width: 30px;"/>
	<input name="button" type="button" value="Go" onclick="go_commonPager();" class="button"/>
</div>
