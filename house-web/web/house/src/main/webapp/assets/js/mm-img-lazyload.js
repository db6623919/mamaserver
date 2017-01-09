$.fn.mmlazyload = function(options) {
    var objs = this;
    var gray_img = 'http://m.mmsfang.com/assets/i/common/lazy_image.jpg';
    var $window = $(window);
    var pxOffset = options.pxOffset ?  options.pxOffset : 30;
    var memorySaver = options.memorySaver ? true : false;
    var memorySaverOffset = $(window).height();
    var replaceImg = options.replaceImg ?  options.replaceImg : gray_img;
    var inViewZone = function (ele, extPxOffset) {
        if (!extPxOffset) extPxOffset = pxOffset;
        var scrollTop = $(window).scrollTop();
        var wHeight = $(window).height() ;
        var ele_top = scrollTop - ele.offset().top + wHeight;
        var ele_bottom =  (ele.offset().top + ele.height()) - scrollTop ;
        return ( (ele_top > 0 - extPxOffset && ele_top < wHeight + extPxOffset) || (ele_bottom > 0 - extPxOffset && ele_bottom < wHeight + extPxOffset) );
        //return ( (ele_top > 0 && ele_top < wHeight) || (ele_bottom > 0 && ele_bottom < wHeight) );
    }
    function update() {
        objs.each(function () {
            var me = $(this);
            var xzsrc = me.attr('xzsrc');
            var src = me.attr('src');
            if (  xzsrc && xzsrc != 'ok' && inViewZone(me)) {
                me.data('xzsrc',xzsrc);
                if (options.replaceImage) 
                me.attr('src', options.replaceImageSrc ? options.replaceImageSrc :gray_img);
                me.attr('xzsrc', 'ok');
                me.load(function () {
                    $(this).data('mmlazyload','ok');
                }).error(function () {
                    var mysrc = $(this).attr('src');
                    if ($(this).data('reload')) {
                        me.attr('src', me.attr('replacesrc') ? me.attr('replacesrc') : replaceImg);
                    } else {
                        $(this).attr('src', mysrc + '?' + Math.random());
                        $(this).data('reload',1)
                    }
                });
                me.attr('src', xzsrc);
            } else if ( memorySaver && xzsrc && xzsrc == 'ok' && inViewZone(me, pxOffset + memorySaverOffset)) {
                var org_src = me.data('xzsrc');
                if (org_src) {
                    me.attr('src', org_src);
                }
            }
//            else if ( memorySaver && xzsrc && xzsrc == 'ok' && !inViewZone(me, pxOffset + memorySaverOffset)) {
//                var replacesrc = me.attr('replacesrc');
//                replacesrc = replacesrc? replacesrc : (options.replaceImageSrc ? options.replaceImageSrc : replaceImg);
//                me.attr('src', replacesrc);
//            } else if (options.replaceImage && !src){
//                if (options.replaceImageSrc) {
//                    me.attr('src', options.replaceImageSrc);
//                } else {
//                    me.attr('src', gray_img);
//                }
//            }
        });
    }

    $window.bind("resize", function() { 
        update(); 
    });
    $(window).on('scroll', function(){
        update(); 
    });
    if($("#scroller")) {
        $("#scroller").on('touchend', function() {
          setTimeout(function(){
        	  update();
        	  if(myScroll){
        	        myScroll.refresh();
        	     }
          },800);
        });
      }
    update();
}
