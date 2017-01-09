/**
 * Created by Adam on 2016/8/17.
 */
var adaptPageClass = "content";
var photos = [];
(function(){
    var imgBoxSize = 0;
    var maxImg = 10;
    var pb = null;
    var imgExt = 'gif,bmp,jpeg,jpg,png';
    var imgSize = 5 * 1024 * 1024; //单位是byte
    var uploadUrl = '/house/importCommentImg.htm';    // 上传图片地址

    // 添加删除数组中指定元素的方法
    Array.prototype.indexOf = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
    
    // 图片重复加载
    function reSetImgUrl(imgObj,imgSrc,maxErrorNum){  
      if(maxErrorNum > 0){  
            imgObj.onerror=function(){  
                reSetImgUrl(imgObj,imgSrc,maxErrorNum-1);  
            };  
            setTimeout(function(){  
                imgObj.src=imgSrc;  
            },500);  
        }else{  
            imgObj.onerror=null;  
//            imgObj.src="<%=basePath%>images/noImg.png";  
        }  
  
    }  
      

    // 增加一张图片
    function addImgBox(imgUrl) {
        imgBoxSize += 1;
        var $imgBox = $('<div class="box img-box">' +
        '<img src="'+ imgUrl + '"/>' +
        '<div class="close"></div>' +
        '</div>');
        $imgBox.get(0).getElementsByTagName('img')[0].onerror = function() {
        	reSetImgUrl(this,this.src,3);
        }

        $imgBox.insertBefore($('#uploadBox'));
        if (imgBoxSize >= maxImg) {
            $('#uploadBox').hide();
        }
    }

    // 多文件上传
    function uploadMulti(fileId) {
        $('#'+fileId).on('change', function() {
        	var $file = $(this);
            var fileObj = document.getElementById(fileId).files;
            var len = fileObj.length > 10 ? 10 : fileObj.length;
            for (var i=0;i<len;i++) {
                var fileLowerPath = fileObj[i].name.toLowerCase();
                var ext =  fileLowerPath.substring(fileLowerPath.lastIndexOf('.') + 1);
                var size = fileObj[i].size;//byte

                if ($.inArray(ext, imgExt.split(',')) == -1) {
                	showTip('允许的格式:'+imgExt,3);
                    return ;
                }
                if (size > imgSize) {
                	showTip('请上传小于' + imgSize/1024/1024 +'M的图片',3);
                    return ;
                }
                
                loading();
                // 压缩文件并上传
                lrz(fileObj[i]).then(function(rst){
                   var form = new FormData();
                   form.append("imgfile", rst.file); // 文件对象添加到form表单中
                   $.ajax({
                       url: uploadUrl,
                       type: 'POST',
                       data: form,
                       async: true, // 进度条需要异步上传
                       cache: false,
                       contentType: false,
                       processData: false,
   
                       // 上传成功TODO
                       success: function (data) {
                           if (imgBoxSize < maxImg) {
                           	   photos.push(data.url);
                               addImgBox(data.url);
                           }
                       },
                       error: function (error) {
                           //$.wx.dialog({content:JSON.stringify(error)});
                       },
                       complete:function() {
                    	   $file.val(null);
                   		loading("false");
                       }
                   });
                })
                
//                form.append("imgfile", fileObj[i]); // 文件对象添加到form表单中
//                $.ajax({
//                    url: uploadUrl,
//                    type: 'POST',
//                    data: form,
//                    async: true, // 进度条需要异步上传
//                    cache: false,
//                    contentType: false,
//                    processData: false,
//
//                    // 上传成功TODO
//                    success: function (data) {
//   
//                        if (imgBoxSize == maxImg) {
//                            $('#uploadBox').hide();
//                        } else {
//                            photos.push(data.url);
//                            addImgBox(data.url);
//                        }
//                    },
//                    error: function (error) {
//                        $.wx.dialog({content:JSON.stringify(error)});
//                    }
//                });
            }
        });
    }


    $(function () {
    	// 不用公共的ajaxStart
    	$(document).ajaxStart = null;
        // 选择评分
        var liLen = $('#ulScore .li').length;
        $('#ulScore li').on('click', function () {
            $('#ulScore li').removeClass('active');
            var $this = $(this);
            var index = $this.index();
            for (var i = 0; i <= index; i++) {
                $('#ulScore li').eq(i).addClass('active');
            }
            $('#score').val(index+1);
        });

        // 删除图片
        $(document).on('click','.close', function(e) {
            var $parent = $(this).parent();
            var imgUrl = $parent.children('img').attr('src');
            $parent.remove();
            imgBoxSize -= 1;
            if (imgBoxSize < maxImg && $('#uploadBox').css("display") == "none") {
                $('#uploadBox').show();
            }
            $('#uploadInput').val(null);
            photos.remove(imgUrl);
            return false;
        });

        uploadMulti('uploadInput');

        // 点击图片看大图
        $(document).on('click','.img-box',function() {
            var index = $(this).index();
            if ($('.photo-browser').length > 0) {
                $('.photo-browser').remove();
            }
            pb = Zepto.photoBrowser({
                initialSlide: index,
                photos :photos,
                ofText:'/',
                toolbar:false,
                theme:'dark'
            });
            pb.open();
        })
    });
})();
function addComments() {
	var houseId = $('#houseId').val();
	var orderId = $('#orderId').val();
	var score = $('#score').val();
	var comments = encodeURIComponent($('#comment').val());
	$.ajax({
		url: "/house/addComment.htm",
		type: "post",
		data: "houseId=" + houseId +
		      "&score=" + score +
		      "&comments=" + comments +
		      "&images=" + photos + 
		      "&orderId=" + orderId,
		success:function(result){
			if(result.code == 0){
				location.href = "/house/comment-success.htm?houseId=" + houseId;
			}
			else{
				showTip(result.msg,3);
				return false;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
		    showTip("网络异常",3);
            return false;
        }
	});
}
