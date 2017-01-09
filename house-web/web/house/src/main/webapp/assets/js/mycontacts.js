/**
 * 查看联系人
 */
//loading = null;
$(function(){
	getSelectMyContactsInfo();
});

$("#selectBtn").click(function(){
	getSelectMyContactsInfo();
});

function getSelectMyContactsInfo() {
	$.ajax({
		type : "get",
		dataType : "json",
		url : "/my/getMyContactsJson.htm", //请求的地址
		data : {},
		success : function(resp){
			if (resp.success) {
				var contactsList = resp.data.contactsList;
				if (contactsList !=null && contactsList.length > 0) {
					for (var i = 0; i < contactsList.length; i++) {
						var this_html = '';
						/*this_html +=   '<div class="item-header clearfix">';
						this_html += ' <div class="pull-left">';
						this_html += '  <span class="color-grey">姓名：</span>'+ contactsList[i].contactsName+ ' </div>';
						this_html += '</div>';
						this_html += '<div class="item-content">';
						this_html += ' <span class="labels color-grey">手机号：</span>';
						this_html += ' <span class="text">'+ contactsList[i].contactsPhone+ '</span>';
						this_html += '</div>';
						this_html += '<div class="item-content">';
						this_html += ' <a href=/my/toModifyMyContacts.htm?contactsId='+ contactsList[i].contactsId +'><span class="labels color-grey" >编辑</span>';
						this_html += ' <a href="javascript:void()" onclick="deleteContact('+ contactsList[i].contactsId +')"><span class="labels color-grey" >删除</span>';
						this_html += '</div>';*/
						this_html += "<div class='contact-item' onclick='editContact(" + contactsList[i].contactsId + ");'>";
						this_html += "<p class='name'>" + contactsList[i].contactsName + "</p>";
						this_html += "<p class='phone'>" + contactsList[i].contactsPhone + "</p>";
						this_html += "</div>";
						$("#contacts").append(this_html);
					}
				} else {
					$(".hd clearfix").html("");
				}
			} else {
				$('#footer').html('没有更多内容了！');
				flag = true;
			}
		},
		afterSend : function() {
			$('#footer').html();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
		
	});
}

function editContact(contactsId){
	window.location.href="/my/toModifyMyContacts.htm?contactsId="+contactsId;
}


/**
 * 手机号码验证
 */
function checkMobile(contactsPhone){ 
	if(!(/^1[3|4|5|7|8][0-9]\d{8}$/.test(contactsPhone))){
		return false; 
	} 
	return true;
} 


/**
 * 增加联系人
 */
$(function(){
	$('#saveBtn').on('click',function(){
		addMyContacts();
	});
});


function addMyContacts() {
	var contactsName = $('#contactsName').val();
	var contactsPhone = $('#contactsPhone').val();
	
	if($("#contactsName").val() == ""){
		alert("请填写姓名！");
		$("contactsName").focus();
		return false;
	}
	if($("#contactsPhone").val() == ""){
		alert("请填写手机号码！");
		$("contactsPhone").focus();
		return false;
	}
	if (!checkMobile(contactsPhone)) {
		alert("手机格式不正确");
		$("#contactsPhone").focus();
		return false ;
	}	
	$.ajax({
		type : 'post',
		url : "/my/addMyContacts.htm",
		data : {"contactsName": contactsName,
				"contactsPhone": contactsPhone
				},
		dataType: "json",
		success : function(resp) {
			if (resp.data.code == 0) {
				showTip(resp.data.msg);
				window.location.href = "/my/getMyContacts.htm";
			} else {
				showTip(resp.data.msg);
			}
		},
	});
	
	
}

/**
 * 修改联系人
 */
$(function(){
	$('#editBtn').on('click',function(){
		editMyContact();
	});
});

function editMyContact() {
	var id = $('#contactsId').val();
	var contactsName = $('#contactsName').val();
	var contactsPhone = $('#contactsPhone').val();
	if($("#contactsName").val() == ""){
		alert("请填写姓名！");
		$("contactsName").focus();
		return false;
	}
	if($("#contactsPhone").val() == ""){
		alert("请填写手机号码！");
		$("contactsPhone").focus();
		return false;d
	}
	if (!checkMobile(contactsPhone)) {
		alert("手机格式不正确");
		$("#contactsPhone").focus();
		return false ;
	}

	$.ajax({
		type : 'post',
		url : "/my/modifyMyContacts.htm",
		data : {"contactsName": contactsName,
			    "contactsPhone": contactsPhone,
			    "contactsId": id
			   },
	    dataType: "json",
		success : function(resp) {
			if (resp.success) {
				//showTip("修改成功");
				window.location.href = "/my/getMyContacts.htm";
			} else {
				showTip(resp.data.msg);
			}
			
		},
		
	});
}


/**
 * 删除联系人
 */
function deleteContact(contactsId) {
	if(confirm("确认删除吗？")) {
		$.ajax({
			type : 'POST',
			url : "/my/removeMyContacts.htm",
			data : {
				"contactsId": contactsId
			},
			dataType: "json",
			success : function(resp) {
				if (resp.success) {
					//showTip("删除成功");
					window.location.href = "/my/getMyContacts.htm";
				} else {
					showTip(resp.data.msg);
				}
			},
			
		});
	}	
}


/**
 * 选择联系人
 */
function selectContacts(){
	var houseId=$("input[name='houseId']").val();
	var houseName=$("input[name='houseName']").val();
	var cityName=$("input[name='cityName']").val();
	var startdate= $("input[name='startTime']").val();//'$!startdate';
	var enddate= $("input[name='endTime']").val();//'$!enddate';
	var footerTotalAmt= $("#footerTotalAmt").html();
	var total=$("#total").html();
	var actual=$("#actual").html();
	window.location.href = "/my/choose_myContacts.htm?houseId="+houseId+"&houseName="+houseName+"&cityName="+cityName
	+"&startdate="+startdate+"&enddate="+enddate+"&footerTotalAmt="+footerTotalAmt+"&total="+total+"&actual="+actual;
}



