$(function(){
  var page = 1;
  var moredata = 0;
  $(window).scroll(function(){
  var scrollTop = $(this).scrollTop();//滚动条在Y轴上的滚动距离
  var scrollHeight = $(document).height();//文档的总高度
  var windowHeight = $(this).height();//浏览器视口的高度
    var d = scrollHeight - scrollTop;
    if(scrollTop + windowHeight > scrollHeight - 100){
    if(moredata == 1){
      return false;
    }
    ++page;
    $('#footer').html('加载中...');
    $.ajax({
          type : "post",
          dataType : "JSON",
          url : "/mamafang/temp/testdata.php", // 你请求的地址
          data : {
            'page': page,  // 传过去的页码
            'fun' : fun
          },
          success : function(data){
            ajaxResult(data);
          }
      });
    }
  });
});
