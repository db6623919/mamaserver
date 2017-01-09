
var changeNumber = (function(){
	var txtNumber=0;
	var init=function(){
		
	}
	
	
	var minus = function(el,maxNumber){
		txtNumber=getNumber(el);
		txtNumber--;
		if(txtNumber<0){
			txtNumber=0;
			$(el).addClass("unactive");
		}else{
			$(el).siblings("[data-click='add']").removeClass("unactive");
		}
		setNumber(el,txtNumber);
		checkNumber(el,maxNumber);
	}
	var getNumber=function(el){
		return $(el).siblings("[data-number='selected']").html() >>>0;
	}
	var setNumber=function(el,value){
		$(el).siblings("[data-number='selected']").html(value) >>>0;
	}
	
	var checkNumber=function(el,maxNumber){
		if(getNumber(el)>=maxNumber){
			$(el).addClass("unactive");
		};
		if(getNumber(el)<=0){
			$(el).addClass("unactive");
		}
	}

	var add = function(el,maxNumber){
		txtNumber=getNumber(el);
		txtNumber++;
		if(txtNumber>maxNumber){
			txtNumber=maxNumber;
			$(el).addClass("unactive");
			return ;
		}else{
			$(el).siblings("[data-click='minus']").removeClass("unactive");
		}
		
		setNumber(el,txtNumber);
		checkNumber(el,maxNumber);
		
	}
	return{
		minus: minus,
		add: add,
		init:init
	}
})()