/*
    妈妈资本管理有限公司WAP端屏幕适配专用类库
*/
function adaptPage(id){
  var sWidth  = window.innerWidth;
  var sHeight = window.innerHeight;
  var plat    = navigator.platform;
  if( (sWidth > 420) && (plat.indexOf("Android") > -1 || plat.indexOf("iPhone") > -1) ){
    if ( document.compatMode== "CSSICompat"){
      sWidth  = document.documentElement.clientWidth;
      sHeight = document.documentElement.clientHeight;
    }else{
      sWidth  = document.body.clientWidth;
      sHeight = document.body.clientHeight;
    }
  }
  $("." + id).css("max-width","640px");
  $("." + id).css("margin","0 auto");
  $("." + id).css("min-height",sHeight);
}

$(function(){
  adaptPage(adaptPageClass);
  var this_width = $(window).width();//当前浏览器可视窗口的宽度
    if(this_width>640){
        this_width = 640;
    }
  var font_size = parseInt((this_width-320)*(3/55)+10);
    if(font_size>14){
        font_size = 14+'px';
    }else{
        font_size = font_size+'px';
    }
  $("html").css("font-size",font_size);//响应式布局，根据不同屏幕宽度，设置字体大小
})
