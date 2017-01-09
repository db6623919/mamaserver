(function($) {
  'use strict';
  //用来控制时延
  var timeout={};
  $(function() {
    
    $(".recharge-div").on("click",function(){
      var $this=$(this);
      var $icon=$this.find(".select-icon");
      if($icon)
      {
        if($icon.hasClass("select")){
          $icon.removeClass("select").addClass("selected");
          $this.siblings("div").find(".select-icon").removeClass("selected").addClass("select");
        }
      }
    });
    /*绑定input框property change事件，即时修改立即充值的数值以及即时发动后台请求*/
    $(".reg_input_1").bind("input propertychange change",function(event){
       var $this=$(this);
      if (event.type != "propertychange" || event.originalEvent.propertyName == "value") 
      {
        if($this.val()&&$this.val()!=""){
          var v=$this.val();
          $("#recharge-amount").html($this.val());
        }
        //异步接口获取数据
        editPayInfo($this);
      }
    });
  });
  function editPayInfo($input){
    var $this=$input;
    //清除之前相同的请求property change触发的timeout调用请求
    clearTimeout(timeout[0]);
    timeout[0]=setTimeout(function(){
      //定义异步请求并更新页面累计奖金等参数

    },500);
  }

})(jQuery);