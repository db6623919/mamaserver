
Date.prototype.Format = function(fmt) 
{
    //author: meizz 
    var o =
    { 
        "M+" : this.getMonth() + 1, //month 
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hours 
        "m+" : this.getMinutes(), //munutes 
        "s+" : this.getSeconds(), //seconds 
        "q+" : Math.floor((this.getMonth() + 3) / 3), //floor 
        "S" : this.getMilliseconds() //milliseconds
    }; 
    if (/(y+)/.test(fmt)) 
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)); 
    for (var k in o) 
        if (new RegExp("(" + k + ")").test(fmt)) 
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length))); 
    return fmt; 
}


Date.prototype.addDays = function(d)
{
    this.setDate(this.getDate() + d);
};


Date.prototype.addWeeks = function(w)
{
    this.addDays(w * 7);
};


Date.prototype.addMonths= function(m)
{
    var d = this.getDate();
    this.setMonth(this.getMonth() + m);

    if (this.getDate() < d)
        this.setDate(0);
};


Date.prototype.addYears = function(y)
{
    var m = this.getMonth();
    this.setFullYear(this.getFullYear() + y);

    if (m < this.getMonth()) 
    {
        this.setDate(0);
    }
};


//var a = new Date();
//alert("现在"+a.Format("yyyy-MM-dd hh:mm:ss")); 
//
//var a1 = new Date();
//a1.addDays(-1);
//alert("昨天"+a1.Format("yyyy-MM-dd")); 
//
//var a2 = new Date();
//a2.addWeeks(-1);
//alert("上个星期"+a2.Format("yyyy-MM-dd")); 
//
//var a3 = new Date();
//a3.addMonths(-1);
//alert("上个月"+a3.Format("yyyy-MM-dd")); 
//
//var a4 = new Date();
//a4.addYears(-1);
//alert("去年"+a4.Format("yyyy-MM-dd")); 
