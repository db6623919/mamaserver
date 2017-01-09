/**
 * 页面验证方法
 * @param obj  页面对象
 * @returns {Boolean}
 * @author Yang.Y
 * @since  2011-11-18
 */   
// *  <input type="text" id="id" name="id" dataType="number(2,6)" msg="提示信息"/> 不设置msg将使用默认提示
// *  jquery each：
// *  return false; 退出整个循环 
// *  return true;  退出当前循环 
// *  js     undefined=null
// * 
function checkAllTextValid(obj){
  var $pattern_filter   = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/;//去掉所有全角半角特殊字符 包括空格
  var $pattern_number   = /^\d+$/;
  var $pattern_int      = /^(-|\+)?\d+$/;
  var $pattern_currency = /^-?\d+\.{0,}\d{0,}$/;  //200 200.50 -200 -200.50
  var $pattern_email    = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
  var $patternPhone     = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
  var $patternMobile    = /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/;
  var $patternUrl       = /^http=\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\'=+!]*([^<>\"\"])*$/;
  var $patternIdCard    = /^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}((19\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(19\d{2}(0[13578]|1[02])31)|(19\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)?$/;
  var $patternCurrency  = /^\d+(\.\d+)?$/;
  var $patternZip       = /[1-9]\d{5}(?!\d)/;
  var $patternQQ        = /^[1-9]\d{4,8}$/;
  var $patternInteger   = /^[-\+]?\d+$/;
  var $patternDouble    = /^[-\+]?\d+(\.\d+)?$/;
  var $patternEnglish   = /^[A-Za-z]+$/;
  var $pattern_chinese  = /^[\u0391-\uFFE5]+$/;
  var $pattern_username = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/;  //只能输入5-20个以字母开头、可带数字、“_”、“.”的字串
  var $patternUnSafe    = /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/;
  var $pattern_date     = /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/; 
 

  $inputs=$(obj).find("input");
  //遍历所有输入框
  $inputs.each(function(index){	  
	  if($inputs.eq(index).attr("type")=="text"){
		  var $dataType=$inputs.eq(index).attr("datatype");	
		  var $nullmsg=$inputs.eq(index).attr("nullmsg");	
		  var $errmsg=$inputs.eq(index).attr("errmsg");
		  var $input   =$inputs.eq(index);		  
		  //去掉前后空格,重设输入框
		  var $value   =input_trim($input);
		  var $length  =$value.length;
		  
		  if($dataType!=undefined){
//			  alert($dataType);
//			  alert($nullmsg);
//			  alert($errmsg);			  
			  //判断非空 格式：dataType="notnull",且有dataType标示的都会先验证是否为空,之后才验证格式
			  if($value==""){
					checkAllTextMsg($input,$nullmsg);
					$result=false;return false;				
			  }else{
				  
				//判断是否为email 格式：dataType="email"
				  if($dataType=="email"){
					  if(!$pattern_email.test($value)){
						  checkAllTextMsg($input,$errmsg);
						  $result=false;return false;
					  }
				  }
				  //判断是否为username 格式：dataType="username"
				  if($dataType=="username"){
					  if(!$pattern_username.test($value)){
						  checkAllTextMsg($input,$errmsg);
						  $result=false;return false;
					  }
				  }
				  //判断是否为数字
				  if($dataType.indexOf("number")>=0){
					  //格式：dataType="number(2,6)" 
					  if($dataType.indexOf(",")>=0&&$dataType.indexOf("(")>=0&&$dataType.indexOf(")")>=0){
						  var len=$dataType.substring($dataType.indexOf("(")+1,$dataType.indexOf(")"));
						  if($length>=len.split(",")[0]&&$length<=len.split(",")[1]&&$pattern_number.test($value)){
						  }else{
							  checkAllTextMsg($input,$errmsg);
							  $result=false;return false;
						  }	
					  }
					  //格式：dataType="number(2)"
					  if($dataType.indexOf("(")>=0&&$dataType.indexOf(")")>=0){
						  var len=$dataType.substring($dataType.indexOf("(")+1,$dataType.indexOf(")"));
						  if($length==len&&$pattern_number.test($value)){
						  }else{
							  checkAllTextMsg($input,$errmsg);
								$result=false;return false;
						  }
					  }
					  //格式：dataType="number"
					  if(!$pattern_number.test($value)){			    
						  checkAllTextMsg($input,$errmsg);
							$result=false;return false;
					  }
				}
			  //判断是否为正确的日期格式"yyyy-MM-dd"或者"yyyy/MM/dd" 格式：dataType="date"
			  if($dataType=="date"){
				  if($length!=10||!$pattern_date.test($value.replace(/\//g,"-"))){
					  checkAllTextMsg($input,$errmsg);
					  $result=false;return false;
				  }
			  }
			//判断是金额 格式：dataType="currency"
			  if($dataType=="currency"){
				  if(!$pattern_currency.test($value)){
					  checkAllTextMsg($input,$errmsg);
					  $result=false;return false;
				  }
			  }
				  
			  }
			  
		}
	  
		  
	  //非法字符验证
//	  if(!$pattern_filter.test($value)&&$value!=""){
//		  checkAllTextMsg($input,$msg,$msg_filter);
//		  $result=false;return false;
//	  }
	  }
  });
  //返回值
  if($result==false){
		return false;
  }else{
	  return true;
  }
}

//只允许输入字母和数字
function checkUserName77(userName){
	var b;
	if (/[a-zA-Z]/.test(userName) && /[0-9]/.test(userName)){
		//同时包含字母和数字
		if(/^[A-Za-z0-9]+$/.test(userName)){
			//只包含字母和数字
			b = true;
		}else{
			b =false;
		}
	}else{
		b=false;
	}
	return b;
}

/**
 * 公共提示
 * @param obj    
 * @param msg
 * @param staticMsg
 */
function checkAllTextMsg(obj,msg){
    alert(msg);
    $(obj).focus();
    if ($(obj).attr("style")!=undefined && $(obj).attr("style").indexOf("width:") != -1) {
        $(obj).attr("style","border:1px solid red;"+$(obj).attr("style"));
        $(obj).blur( function(){						
        	$(obj).attr("style","border:1px solid #d7d7d7;"+$(obj).attr("style"));
        	//$(obj).attr("style","height:18px;");									
    	});
    }else{
        $(obj).attr("style","border:1px solid red;");
        $(obj).blur( function(){						
        	$(obj).attr("style","border:1px solid #d7d7d7;");
        	//$(obj).attr("style","height:18px;");									
    	});
    }
}
/**
 * 去前后空格
 * @param obj
 * @returns
 */
function input_trim(obj){
	var $trimed=$(obj).val().replace(/(^\s*)|(\s*$)/g,"");
	$(obj).val($trimed);
	return $trimed;
}


/**
 * 金额格式化
 * @param obj 要格式化的input对象
 */
function formatCurrency(obj) {
	var $value=$(obj).val().replace(/(^\s*)|(\s*$)/g,"").replace("-","");
	var $result;
	if (isNaN($value)||$value=="") {
		$result = "";
	}else {
		if ($value.indexOf(".")!= -1) {
			var len=$value.substring($value.indexOf(".")+1,$value.length).length;
			if (len.length==0) {
				$result=$value+"00";
			} else if (len==1){
				$result=$value+"0";
			}else if (len>=3){
				var $begin=$value.substring(0,$value.indexOf("."));
				var $end=$value.substring($value.indexOf(".")+1,$value.indexOf(".")+3);
				$result=$begin+"."+$end;				
			}
		} else {
				$result=$value+".00";	
		}
	}
	$(obj).val($result);
}



/**
 * 格式div里面的数据
 * @param {} obj
 */
function formatCurrencyDiv(obj) {
	var $value=$(obj).html().replace(/(^\s*)|(\s*$)/g,"").replace("-","");
	var $result;
	if (isNaN($value)||$value=="") {
		$result = "";
	}else {
		if ($value.indexOf(".")!= -1) {
			var len=$value.substring($value.indexOf(".")+1,$value.length).length;
			if (len.length==0) {
				$result=$value+"00";
			} else if (len==1){
				$result=$value+"0";
			}else if (len>=3){
				var $begin=$value.substring(0,$value.indexOf("."));
				var $end=$value.substring($value.indexOf(".")+1,$value.indexOf(".")+3);
				$result=$begin+"."+$end;				
			}
		} else {
				$result=$value+".00";	
		}
	}
	$(obj).html($result);
}

function checkIdcard(idcard){
	var result=checkIdcardAlert(idcard);
	if(result!="验证通过!"){
		return false;
	}else{
		return true;
	}
} 
function checkIdcardAlert(idcard){ 
	//if(idcard.length!=18){
	//	idcard=idcard.substring(0,6)+19+idcard.substring(6,15)+'x';
	//}
	 var Errors=new Array( 
	 "验证通过!", 
	 "身份证号码位数不对!", 
	 "身份证号码出生日期超出范围或含有非法字符!", 
	 "身份证号码校验错误!", 
	 "身份证地区非法!" 
	 ); 
	//var Errors=new Array( 
	//	"验证通过!", 
	//	"请输入正确的身份证号码!", 
	//	"请输入正确的身份证号码!", 
	//	"请输入正确的身份证号码!", 
	//	"请输入正确的身份证号码!" 
	//); 
	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}  
	var idcard,Y,JYM; 
	var S,M; 
	var idcard_array = new Array(); 
	idcard_array = idcard.split(""); 
	//地区检验 
	if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4]; 
	//身份号码位数及格式检验 
	switch(idcard.length){ 
	case 15: 
	if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 ))
	{ 
		ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
	} else 
	{ 
		ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
	} 
	if(ereg.test(idcard)) return '111'; 
	else return Errors[2]; 
	break; 
	case 18: 
	//18位身份号码检测 
	//出生日期的合法性检查  
	//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
	//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
	if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 ))
	{ 
		ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式 
	} 
	else { 
		ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式 
	} 
	if(ereg.test(idcard)){//测试出生日期的合法性 
	//计算校验位 
	S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 
	+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 
	+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 
	+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 
	+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 
	+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 
	+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 
	+ parseInt(idcard_array[7]) * 1  
	+ parseInt(idcard_array[8]) * 6 
	+ parseInt(idcard_array[9]) * 3 ; 
	Y = S % 11; 
	M = "F"; 
	JYM = "10X98765432"; 
	M = JYM.substr(Y,1);//判断校验位 
	if(M == idcard_array[17]) return Errors[0]; //检测ID的校验位 
		else return Errors[3]; 
	} 
		else return Errors[2]; 
		break; 
	default: 
		return Errors[1]; 
	break; 
	} 
}

//只允许输入中文和数字
function checkUserNameThree(userName){
	var b;
	if(/[^\u4E00-\u9FA50-9]/.test(userName)){
		b = "只允许输入中午字符与数字";
	}else{
		b = true;
	}
	return b;
}


//只允许输入中文和字母.
function checkUserNameFour(userName){
	var b;
	if(/[^\u4E00-\u9FA5a-zA-Z.]/.test(userName)){
		b = "只允许输入中文字符、字母和.";
	}else{
		b = true;
	}
	return b;
}

/* 
	用途：检查输入字符串是否只由汉字、字母、数字组成 
	输 入： 
		value：字符串 
	返回： 
		如果通过验证返回true,否则返回false 
*/ 
function isChinaOrNumbOrLett(s){//判断是否是汉字、字母、数字组成 
	var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) { 
		return true; 
	}else{ 
		return false; 
	}
}

/* 
	用途：检查输入手机号码是否正确 
	输入： 
		s：字符串 
	返回： 
		如果通过验证返回true,否则返回 false 
*/ 
function checkMobile(s){ 
	var regu =/^[1][0-9]{8}$/; 
	var re = new RegExp(regu); 
	if (re.test(s)) { 
		return true; 
	}else{ 
		return false; 
	} 
}

/* 
	用途：检查输入字符串是否只由英文字母和数字和下划线组成 
	输入： 
		s：字符串 
	返回： 
		如果 通过验证返回true,否则返回false 
*/ 
function isNumberOr_Letter(s){//判断是否是数字或字母 
	var regu = "^[0-9a-zA-Z\_]+$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) { 
		return true; 
	}else{ 
		return false; 
	} 
}

/* 
	用途：检查输入字符串是否只由英文字母和数字组成 
	输入： 
		s：字符串 
	返回： 
		如果通过验证返回true,否则返回false 
*/ 
function isNumberOrLetter(s){//判断是否是数字或字母 
	var regu = "^[0-9a-zA-Z]+$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) { 
		return true; 
	}else{ 
		return false; 
	} 
}

/* 
	用途：检查输入的Email信箱格式是否正确 
	输入： 
		strEmail：字符串 
	返回： 
		如果通过验证返回 true,否则返回false 
*/ 
function checkEmail(strEmail) { 
	//var emailReg = /^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}$/; 
	var emailReg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/; 
	if( emailReg.test(strEmail) ){ 
		return true; 
	}else{ 
		alert("您输入的 Email地址格式不正确！"); 
		return false; 
	} 
} 

/* 
	用途：检查输入的电话 号码格式是否正确 
	输入： 
		strPhone：字符串 
	返回： 
		如果通过验证返回true,否则返回false 
*/ 
function checkPhone( strPhone ) { 
	var phoneRegWithArea = /^[0][1-9]{2,3}-[0-9]{5,10}$/; 
	var phoneRegNoArea = /^[1-9]{1}[0-9]{5,8}$/; 
	var prompt = "您输入的电话号码不正确!";
	if( strPhone.length > 9 ) { 
		if( phoneRegWithArea.test(strPhone) ){ 
			return true; 
		}else{ 
			alert( prompt ); 
			return false; 
		} 
	}else{ 
		if( phoneRegNoArea.test( strPhone ) ){ 
			return true; 
		}else{ 
			alert( prompt ); 
			return false; 
		}
	}
}

/* 
	用途：检查输入的电话 号码格式是否正确 
	输入： 
		s：字符串 
	返回： 
		如果通过验证返回true,否则返回false 
*/ 
function checkTel( s ) { 
	var regu = "^[0-9\-]+$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) { 
		return true; 
	}else{ 
		return false; 
	}
}

/*
	用途：校验传真号码：可以“+”开头，除数字外，可含有“-”
	输入：
		s：字符串
	返回：
		如果通过验证返回true,否则返回false 
 */    
function checkFax(s){    
	//国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"    
	var regu =/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	var re = new RegExp(regu);
	if(re.test(s)){
		return true;
	}else{
		return false;
	}
}

/*
用途：校验(国内)邮政编码
输入：
	s：字符串
返回：
	如果通过验证返回true,否则返回false 
*/
function checkPostalCode(s)    
{    
	var regu =/^[0-9]{6}$/;
	var re = new RegExp(regu);
	if(re.test(s)){
		return true;
	}else{
		return false;
	}
} 

/*
用途：校验手机号码
输入：
	s：字符串
返回：
	如果通过验证返回true,否则返回false 
*/
function checkMobile(s)    
{    
	var regu =/^[0-9]{11}$/;
	var re = new RegExp(regu);
	if(re.test(s)){
		return true;
	}else{
		return false;
	}
} 

/* 
	用途：检查输入的起止日期是否正确，规则为两个日期的格式正确， 
	且结束如 期>=起始日期 
	输入： 
		startDate：起始日期，字符串 
		endDate：结束如期，字符串 
	返回： 
		如 果通过验证返回true,否则返回false 
*/ 
function checkTwoDate( startDate,endDate ) { 
	if( !isDate(startDate) ) { 
		alert("起始日期不正 确!"); 
		return false; 
	} else if( !isDate(endDate) ) { 
		alert("终 止日期不正确!"); 
		return false; 
	} else if( startDate > endDate ) { 
		alert(" 起始日期不能大于终止日期!"); 
		return false; 
	} 
	return true; 
}

/* 
	用途：判断是否是日期 
	输入：date：日期；fmt：日期格式 
	返回：如果通过验证返回true,否 则返回false 
*/ 
function isDate( date, fmt ) { 
	if (fmt==null) fmt="yyyyMMdd"; 
		var yIndex = fmt.indexOf("yyyy"); 
	if(yIndex==-1) return false; 
		var year = date.substring(yIndex,yIndex+4); 
	var mIndex = fmt.indexOf("MM"); 
	if(mIndex==-1) return false; 
		var month = date.substring(mIndex,mIndex+2); 
	var dIndex = fmt.indexOf("dd"); 
	if(dIndex==-1) return false; 
		var day = date.substring(dIndex,dIndex+2); 
	if(!isNumber(year)||year>"2100" || year< "1900") return false; 
	if(!isNumber(month)||month>"12" || month< "01") return false; 
	if(day>getMaxDay(year,month) || day< "01") return false; 
	return true; 
}

/* 
	用途：检查输入字符串是否符合正整数格式 
	输入： 
		s：字符串 
	返回： 
		如果通过验证返回true,否则返回 false 
*/ 
function isNumber( s ){ 
	var regu = "^[0-9]+$"; 
	var re = new RegExp(regu); 
	if (s.search(re) != -1) { 
		return true; 
	} else { 
		return false; 
	} 
} 
