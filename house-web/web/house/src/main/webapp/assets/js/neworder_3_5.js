$('#back').on('click', function(){
	history.back(-1);
});

//choose person
var cp = (function(){
	var selectFn = function(item){
		var $this = $(item);
		var src = $this.attr('src');	//未选中的路径
		var selectedSrc = src.replace(/select/, 'selected');	//选中的路径
		if(src.indexOf('selected') < 0)
		{
			$('input[name="person"]').removeProp('checked');
			$('[data-selector="img"]').attr('src', src);
			$this.attr('src', selectedSrc).siblings('input[name="person"]').prop('checked','checked');
		}
	}

	return{
		selectToggle: selectFn
	}
})()

 
  



//choose coupon
var cc = (function(){
	
	var normal= $("#normal").html();//淡季的天数
	var hot=$("#hot").html();//旺季的天数
	
	var max_normal;
	var max_hot=hot;
	
	var _getNumber = function(el){
		var $this = $(el);
		var number = Number($this.siblings('[data-number="selected"]').text());
		return number;
	}

	var _setNumber = function(el, num){
		var $this = $(el);
		$this.siblings('[data-number="selected"]').text(num);
	}
	var getDays=function(){
		return 2*hot+1*normal;
	}
	var getCount=function(type){
		var number=0;
		$('.coupon[inseason="'+type+'"] [data-number="selected"]').each(function(){
			var _this = $(this);
			number += Number(_this.text());
		});
		return number;
	}
	
	var init=function(){
		checkCount("true");
		checkCount("false");
	}
	
	
	var checkCount=function(type){
		if(type=="true"){ 
			var r=getCount("false")-normal;
			
			if(r>=0){
				r=r%2==0?r/2:Math.floor(r/2)+1;
			}else{
				r=0;
			}
			max_hot=hot-r-getCount("true");
			max_hot<=0?$('.coupon[inseason="'+type+'"] [data-click="add"]').addClass('unactive'):$('.coupon[inseason="'+type+'"] [data-click="add"]').removeClass('unactive');
			
		}else{
			var i=2*(1*hot-1*getCount("true"))+1*normal-getCount("false");
			i<=0?$('.coupon[inseason="'+type+'"] [data-click="add"]').addClass('unactive'):$('.coupon[inseason="'+type+'"] [data-click="add"]').removeClass('unactive');
		}
		checkMaxCount();
	}
	
	var checkMaxCount= function(){
		$(".coupon").each(function(i,e){
			if($(e).find('[data-number="total"]').text()==$(e).find('[data-number="selected"]').text()){
				$(e).find('[data-click="add"]').addClass("unactive");
			}
		});
	}
	


	var minus = function(el){
		var $this = $(el);
		var current = _getNumber(el);
		var parent = $this.parents('[data-wrapper="coupon"]');
		var total = Number(parent.find('[data-number="total"]').text());
		var type=parent.attr("inseason");
		if(current > 0 && !$this.hasClass('unactive'))
		{
			current--;
			_setNumber(el, current)
			if(current == 0){
				$this.addClass('unactive');
			//	$('[data-click="add"]').removeClass('unactive');
			}else{
				//$('[data-click="add"]').removeClass('unactive');
			}
		}
		checkCount("true");
		checkCount("false");
	}

	var add = function(el){
		var $this = $(el);
		var current = _getNumber(el);
		var parent = $this.parents('[data-wrapper="coupon"]');
		var total = Number(parent.find('[data-number="total"]').text());
		var type=parent.attr("inseason");
		
		checkCount(type);
		
		
		if(current < total && !$this.hasClass('unactive'))
		{
			current++;
			_setNumber(el, current)
			current == total&&total!=1 ? $this.addClass('unactive') : $this.siblings('[data-click="minus"]').removeClass('unactive');
			
			
		}
		checkCount("true");
		checkCount("false");
		
	}
	return{
		minus: minus,
		add: add,
		init:init
	}
})()