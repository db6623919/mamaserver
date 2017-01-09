// JavaScript Document

//头部导航

function naviselect(showContent,Obj){
	// 操作标签
	var tag = document.getElementById("navi").getElementsByTagName("a");
	var taglength = tag.length;
	for(i=0; i<taglength; i++){
		tag[i].className = "";
	}
	Obj.className = "navi_li_a_on";
	// 操作内容
	for(i=0; j=document.getElementById("navi_content"+i); i++){
		j.style.display = "none";
		
	}
	document.getElementById(showContent).style.display = "block";
};

function naviClick(naviId){
	// 操作标签
	var navi = document.getElementById(naviId);
	navi.className = "navi_li_a_on";
	var naviContent = document.getElementById(naviId+'Content1');
	naviContent.className = "navi_content_a_on";
};

//搜索框

//搜索输入框得到焦点时触发事件

function OnFocusFun(element,elementvalue)
{
	if(element.value==elementvalue)
	{
		element.value="";
		element.style.color="#333";
	}
}
//离开输入框时触发事件
function OnBlurFun(element,elementvalue)
{
	if(element.value==""||element.value.replace(/\s/g,"")=="")
	{
		element.value=elementvalue;    
		element.style.color="#999";
	}
}			

//