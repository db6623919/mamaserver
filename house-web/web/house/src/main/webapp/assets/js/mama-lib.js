
/**
* 通用的列表类，根据主键 code/key 检索和删除 Item
*
* @returns
* 
*/
var ItemList = function() {
    this.length = 0;
    this.items = [];
};
ItemList.prototype = {
    /**
* put data item into items
*
* @param d
*/
    put: function(d) {
        return this._getput(d.id, true, d);
    },
    /**
* delete data item from items by code
*
* @param code
*/
    del: function(code) {
        this._getput(code, true);
    },
    /**
*添加多条
*/
    join: function(items) {
        for (var i = 0; i < items.length; i++) {
            this.put(items[i]);
        }
    },
    empty: function() {
        var length = this.length;
        for (var i = 0; i < length; i++) {
            this.items.splice(0, 1);
        }
        this.length = 0;
    },
    /**
* get data item from items by code
*
* @param code
*/
    get: function(code, remove) {
        return this._getput(code);
    },
    /**
*
* @param code
* @param remove
* @param item
* @returns
*/
    _getput: function(code, remove, item) {
        var a = this.items;
        var n = a.length;
        for (var i = 0; i < n; i++) {
            var d = a[i];
            if (d.code == code) {
                if (item) {
                    d = item;
                    a[i] = d; // found and replace
                } else if (remove) {
                    a.splice(i, 1);
                    this.length = a.length;
                }
                return d; // found
            }
        }
        if (item) {
            // trace('_getput:' + item);
            // not found, append
            a[a.length] = item;
            this.length = a.length;
        }
        return null; // not found
    }
};

/**
* 通用的列表类，根据主键 code/key 检索和删除 Item
*
* @returns
*/
var ItemSet = function() {
    this.length = 0;
    this.items = {};
};
ItemSet.prototype = {
    /**
	* put data item into items
	*
	* @param d
	*/
    put: function(key, value) {
        var obj = this.items;
        if ('undefined' == typeof obj[key]) {
            obj[key] = value;
            this.length++;
        } else {
            obj[key] = value;
        }
    },
	/**
	* delete data item from items by code
	*
	* @param code
	*/
    del: function(key) {
        if ('undefined' == typeof this.items[key]) {
            return;
        }
        this.items[key] = undefined;
        delete this.items[key];
        this.length--;
    },
    empty: function() {
        this.length = 0;
        this.items = {};
    },
	/**
	* get data item from items by code
	*
	* @param code
	*/
    get: function(key) {
        var obj = this.items;
        if ('undefined' == typeof obj[key]) {
            return;
        }
        return obj[key];
    }
};

/**
 * 带参数的定时调用；代替 setTimeout 使用
 * 
 * @param o
 *            callback function.this
 * @param f
 *            callback function
 * @param t
 *            timeout
 */
function callTimeout(o, f, t)
{
	var a = $A(arguments);
	a.shift(); // drop parameter o
	a.shift(); // drop parameter f
	a.shift(); // drop parameter t
	setTimeout(function()
	{
		f.apply(o, a);
	}, t);
}

var $A = Array.from = function(iterable) {
	if (!iterable)
		return [];
	if (iterable.toArray) {
		return iterable.toArray();
	} else {
		var results = [];
		for ( var i = 0, length = iterable.length; i < length; i++)
			results.push(iterable[i]);
		return results;
	}
};

/**
 * 对字符串进行 UTF8 编码
 * 
 * @param s
 * @return
 */
function $U(s)
{
	return encodeURIComponent(s);
}



// ============================================================================
/**
 * 数据格式化器
 */
var $FMT =
{
    // 格式化为秒显示
    fmtSeconds : function(d)
    {
	    return d + ' 秒';
    },
    // 格式化为时间单位
    fmtTimes : function(d, english)
    {
	    if (d < 60) return d + (english ? 's' : ' 秒');
	    if (d < 3600)
	    {
		    var s = parseInt(d / 60) + (english ? 'm' : ' 分');
		    if ((d % 60) > 0)
		    {
			    s += (english ? '' : ' ') + (d % 60) + (english ? 's' : ' 秒');
		    }
		    return s;
	    }
	    if (d < 86400)
	    {
		    var s = parseInt(d / 3600) + (english ? 'h' : ' 时');
		    if ((d % 3600) > 0)
		    {
			    s += (english ? '' : ' ') + parseInt((d % 3600) / 60) + (english ? 'm' : ' 分');
			    if ((d % 60) > 0)
			    {
				    s += (english ? '' : ' ') + (d % 60) + (english ? 's' : ' 秒');
			    }
		    }
	    } else
	    {
		    return (english ? '+1d' : '1 天以上');
	    }
	    return s;
    },
    // 格式化显示周几
    fmtWeekDay : function(o)
    {
	    if (typeof o == 'string') o = $DTU.parseDate(o);
	    var day = o.getDay();
	    return day == 0 ? 7 : day;
    },
    // 格式化显示时间(默认2009-10-01)，c作为分隔符（可以传'/'，则结果是2009/10/01）
    fmtDate : function(o, c)
    {
	    if (typeof o == 'string') o = $DTU.parseDate(o);
	    if ('undefined' == typeof o || null == o || '' == o)
	    {
		    return '';
	    }
	    var y = o.getFullYear();
	    var m = o.getMonth() + 1;
	    if (m < 10) m = '0' + m;
	    var d = o.getDate();
	    if (d < 10) d = '0' + d;
	    if (c)
	    {

		    return y + c + m + c + d;
	    } else
	    {

		    return y + '-' + m + '-' + d;
	    }
    },
    // 格式化显示时间(默认2009-10-01)，c作为分隔符（可以传'/'，则结果是2009/10/01）
    fmtZnDate : function(o, c)
    {
	    if (typeof o == 'string') o = $DTU.parseDate(o);
	    if ('undefined' == typeof o || null == o || '' == o)
	    {
		    return '';
	    }
	    var y = o.getFullYear();
	    var m = o.getMonth() + 1;
	    if (m < 10) m = '0' + m;
	    var d = o.getDate();
	    if (d < 10) d = '0' + d; 
	    return  + m + '月' + d+'日';
	    
    },
    // 格式化显示时间(2009-10-01 12:12:36)
    fmtTimestamp : function(o)
    {
	    if (typeof o == 'string')
	    {
		    if (o == '1970-01-01') return '';
		    o = o.replace(/-/g, '/');
	    } else
	    {
		    if (o == 0 || o == -1 || o == 1000) return null;
	    }
	    o = new Date(o);
	    var y = o.getFullYear();
	    var m = o.getMonth() + 1;
	    if (m < 10) m = '0' + m;
	    var d = o.getDate();
	    if (d < 10) d = '0' + d;
	    var h = o.getHours();
	    if (h < 10) h = '0' + h;
	    var i = o.getMinutes();
	    if (i < 10) i = '0' + i;
	    var s = o.getSeconds();
	    if (s < 10) s = '0' + s;
	    var str = y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;
	    if ('1970-01-01 08:00:00' == str || '1970-01-01 00:00:01' == str)
	    {
		    return '';
	    }
	    return str;

    },
    // 格式化新闻时间(2009-10-01 12:12)
    fmtNSTime : function(o, media)
    {
	    if (!o) return '';
	    o = o.replace(/-/g, '/');
	    if (typeof o == 'string') o = new Date(o);
	    var y = o.getFullYear();
	    var m = o.getMonth() + 1;
	    if (m < 10) m = '0' + m;
	    var d = o.getDate();
	    if (d < 10) d = '0' + d;
	    var h = o.getHours();
	    if (h < 10) h = '0' + h;
	    var i = o.getMinutes();
	    if (i < 10) i = '0' + i;
	    var s = y + '-' + m + '-' + d + ' ' + h + ':' + i;
	    // （2011-10-10 ylk） 现在无论何种媒体都显示 时分，以前是：没有指定类型，或电视新闻，或网络新闻并且不是0时0分，则 有时分
	    // || (CONST.MEDIA_TV == media || (CONST.MEDIA_WEB == media && (0 != h +
	    // i)))

	    return s;
    },
    // 格式化时分(2009-10-01 12:12 -> 12:12)
    fmtHourMin : function(o)
    {
	    if (!o) return '';
	    o = o.replace(/-/g, '/');
	    if (typeof o == 'string') o = new Date(o);

	    var h = o.getHours();
	    if (h < 10) h = '0' + h;
	    var i = o.getMinutes();
	    if (i < 10) i = '0' + i;
	    var s = h + ':' + i;

	    return s;
    },
    // 格式化时分(2009-10-01 12:12:12 -> 12:12:12)
    fmtHourMinSec : function(o)
    {
	    if (!o) return '';
	    o = o.replace(/-/g, '/');
	    if (typeof o == 'string') o = new Date(o);

	    var h = o.getHours();
	    if (h < 10) h = '0' + h;
	    var i = o.getMinutes();
	    if (i < 10) i = '0' + i;
	    var s = o.getSeconds();
	    if (s < 10) s = '0' + s;
	    var r = h + ':' + i + ':' + s;

	    return r;
    },
    // 格式化广告时间(10-01 12:12:36)
    fmtAdTime : function(o)
    {
	    if (typeof o == 'string')
	    {
		    o = o.replace(/-/g, '/');
		    o = new Date(o);
	    }
	    var m = o.getMonth() + 1;
	    if (m < 10) m = '0' + m;
	    var d = o.getDate();
	    if (d < 10) d = '0' + d;
	    var h = o.getHours();
	    if (h < 10) h = '0' + h;
	    var i = o.getMinutes();
	    if (i < 10) i = '0' + i;
	    var s = o.getSeconds();
	    if (s < 10) s = '0' + s;
	    return m + '-' + d + ' ' + h + ':' + i + ':' + s;
    },
    // 格式化广告名称
    fmtAdName : function(s, m)
    {
	    if (null == s) return '';
	    return s.fixed(m);
    },
    // 格式化节目名称
    fmtProgName : function(s, m)
    {
	    if (null == s) return '';
	    return s.fixed(m);
    },
    // 格式化年月
    fmtYearMonth : function(ym)
    {
	    return parseInt(ym / 100) + '年' + (ym % 100) + '月';
    },
    /**
	 * 格式化百分比
	 */
    fmtPercent : function(percent, fraction)
    {
	    return (isNaN(percent) ? 0 : percent).toFixed(isNaN(fraction) ? 0 : fraction);
    },
    /**
	 * 格式化千分比
	 */
    fmtPermille : function(permille, fraction)
    {
	    return (isNaN(permille) ? 0 : permille).toFixed(isNaN(fraction) ? 0 : fraction);
    },
    /**
	 * 格式化千分位
	 */
    fmtCommafy : function(n)
    {
	    var negative = n < 0; // 负数
	    if (negative) n = -n;
	    var s = n + '';
	    s = s.substring((parseInt(n) + '').length, s.length); // 小数
	    n = parseInt(n);
	    while (n > 1000)
	    {
		    s = ',' + $STU.pad(n % 1000, 3) + s;
		    n = parseInt(n / 1000);
	    }
	    s = n + s;
	    return negative ? '-' + s : s;
    }
};
/**
 * Date Util 日期时间辅助函数
 */
var $DTU =
{
    /**
	 * 转换日期字符串为日期对象
	 * 
	 * trace($FMT.fmtDate($DTU.parseDate('2008-03-02')));
	 * trace($FMT.fmtDate($DTU.parseDate('2008-3-2')));
	 * trace($FMT.fmtDate($DTU.parseDate('2008/03/02')));
	 * trace($FMT.fmtDate($DTU.parseDate('2008/3/2')));
	 * trace($FMT.fmtDate($DTU.parseDate('2008 03 02')));
	 * trace($FMT.fmtDate($DTU.parseDate('2008 3 2')));
	 * 
	 */
    parseDate : function(s)
    {
	    var v = [];
	    var k = 0;
	    var i, c, n = s.length;
	    for (i = 0; i < n; i++)
	    {
		    c = s.charAt(i);
		    if (!('0' <= c && c <= '9') || i + 1 == n)
		    {
			    try
			    {
				    v.push(parseInt(s.substring(k, (i + 1 == n) ? n : i), 10));
				    k = i + 1;
			    } catch (e)
			    {
				    return null;
			    }
		    }
	    }
	    if (v.length < 3) return null;

	    return new Date(v[0], v[1] - 1, v[2]);
    },
    parseDateTime : function(s)
    {
	    var strDate = s;
	    if (strDate.length == 0) return false;

	    // 先判断是否为短日期格式：YYYY-MM-DD，如果是，将其后面加上00:00:00，转换为YYYY-MM-DD hh:mm:ss格式
	    var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/; // 短日期格式的正则表达式
	    var r = strDate.match(reg);

	    if (r != null) // 说明strDate是短日期格式，改造成长日期格式
	    strDate = strDate + " 00:00:00";

	    reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})/;
	    r = strDate.match(reg);
	    if (r == null)
	    {
		    alert("你输入的日期格式有误，正确格式为：2004-12-01 或 2004-12-01 12:23:45");
		    return false;
	    }

	    var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
	    if (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4] && d.getHours() == r[5]
	            && d.getMinutes() == r[6] && d.getSeconds() == r[7])
	    {
		    return d;
	    } else
	    {
		    alert("你输入的日期或时间超出有效范围，请仔细检查！");
		    return false;
	    }
    },
    addMonth : function(o, month)
    {
	    var y = o.getFullYear();
	    var m = o.getMonth() + 1 + month;
	    if (m < 10) m = '0' + m;
	    var d = o.getDate();
	    if (d < 10) d = '0' + d;

	    var s = y + '-' + m + '-' + d;
	    return $DTU.parseDate(s);
    },
    nextMonth : function(o)
    {
	    var y = o.getFullYear();
	    var m = o.getMonth() + 1 + 1;
	    if (m < 10) m = '0' + m;
	    var d = 1 ;
	    if (d < 10) d = '0' + d;

	    var s = y + '-' + m + '-' + d;
	    return $DTU.parseDate(s);
    },
    
    addDays : function(d, days)
    {
	    return new Date(d.getTime() + days * 24 * 60 * 60 * 1000);
    },
    addMinutes : function(d, minutes)
    {
	    return new Date(d.getTime() + minutes * 60 * 1000);
    },
    /**
	 * 计算两个日期之间的时间差-以天为单位
	 * 
	 * @param d1
	 * @param d2
	 * @returns
	 */
    getDateMargin : function(d1, d2)
    {
	    return this.getMargin(d1, d2, 'D');
    },
    /**
	 * 计算两个日期之间的时间差
	 * 
	 * @param d1
	 * @param d2
	 * @param t
	 *            计量单位[可取值Y,M,D,h,m,s,ms]
	 * @returns 默认以ms为单位
	 */
    getMargin : function(d1, d2, t)
    {
	    var margin = d1.getTime() - d2.getTime();
	    if (0 > margin)
	    {
		    margin = -margin;
	    }
	    var n = 1;
	    if ('s' == t)
	    {
		    n = 1000;
	    } else if ('m' == t)
	    {
		    n = 1000 * 60;
	    } else if ('h' == t)
	    {
		    n = 1000 * 60 * 60;
	    } else if (('D' == t) || ('d' == t))
	    {
		    n = 1000 * 60 * 60 * 24;
	    } else if ('M' == t)
	    {
		    n = 1000 * 60 * 60 * 24 * 30;
	    } else if (('Y' == t) || ('y' == t))
	    {
		    n = 1000 * 60 * 60 * 24 * 30 * 365;
	    }
	    return parseInt(margin / n);

    },
    /**
	 * 返回相对于当前时间的时间差
	 * 
	 * @param par_time
	 * @returns
	 */
    normalizeTimeStringEnglish : function(par_time)
    {
	    var time = $DTU.parseDateTime(par_time);
	    var seconds = $DTU._calcMargin(time);
	    return (0 == seconds) ? "" : ($DTU._normalizeTime(seconds) + $DTU._normalizeSizeUnitEnglish(seconds));
    },
    /**
	 * 返回秒
	 * 
	 * @param time
	 *            时间对象
	 * @returns
	 */
    _calcMargin : function(time)
    {
	    var now = new Date();

	    var margin = time.getTime() - now.getTime();

	    margin = (margin + (margin >= 0 ? (+1000 / 2) : (-1000 / 2))) / (1000);

	    return Math.round(margin);
    },
    _normalizeTime : function(seconds)
    {
	    var value = seconds < 0 ? -seconds : seconds;
	    var time = "";

	    if (value < 60)
	    {
		    time = seconds; // 分钟
	    } else if (value < 60 * 60)
	    {
		    time = seconds / 60; // 分钟
	    } else if (value < 60 * 60 * 24)
	    {
		    time = seconds / 60 / 60; // 小时
	    } else if (value < 60 * 60 * 24 * 365)
	    {
		    time = seconds / 60 / (60 * 24); // 天
	    } else
	    {
		    time = seconds / 60 / (60 * 24 * 365); // 年
	    }

	    if (time != '' && time.toString().indexOf(".") != -1)
	    {
		    var m = time.toString().indexOf(".");
		    time = time.toString().substring(0, m + 2);
	    }
	    return (seconds > 0 ? "+" : "") + time;
    },
    /**
	 * 
	 * @param margin
	 *            秒
	 * @return
	 */
    _normalizeSizeUnitEnglish : function(margin)
    {
	    var value = margin < 0 ? -margin : margin;

	    if (value < 60)
	    {
		    return "s";
	    } else if (value < 60 * 60)
	    {
		    return "m";
	    } else if (value < 60 * 60 * 24)
	    {
		    return "h";
	    } else if (value < 60 * 60 * 24 * 365)
	    {
		    return "d";
	    } else
	    {
		    return "y";
	    }
    },
    /**
	 * 根据范围得到日期字符串 0 - 今天 1 - 昨天 7 - 本周 30 - 本月 365 - 今年
	 */
    getQuickDateString : function(sel)
    {
	    var now = new Date();

	    var year = now.getFullYear();
	    var month = now.getMonth();
	    var day = now.getDate();

	    var date1;
	    var date2;

	    if (sel == 1) // 昨天
	    {
		    date1 = new Date(year, month, day);
		    date1.setDate(date1.getDate() - 1);
		    date2 = new Date(date1);
	    } else if (sel == 7) // 本周
	    {
		    var d = now.getDay();
		    date1 = new Date(year, month, day);
		    date1.setDate(date1.getDate() - (0 == d ? 6 : (d - 1)));
		    date2 = new Date(date1);
		    date2.setDate(date2.getDate() + 6);
	    } else if (sel == 30) // 本月
	    {
		    date1 = new Date(year, month, 1);
		    date2 = new Date(date1);
		    date2.setMonth(date2.getMonth() + 1);
		    date2.setDate(date2.getDate() - 1);
	    } else if (sel == 365) // 今年
	    {
		    date1 = new Date(year, 0, 1);
		    date2 = new Date(date1);
		    date2.setYear(date2.getFullYear() + 1);
		    date2.setDate(date2.getDate() - 1);
	    } else
	    // if (sel == 0) // 今天
	    {
		    date1 = new Date(year, month, day);
		    date2 = new Date(date1);
	    }

	    var data = {}; // 返回数据

	    data.date1 = date1;
	    data.date2 = date2;

	    return data;
    }
};

function isDefined(v)
{
	return ('undefined' != typeof v);
}

//Class Browser
function Browser()
{
	var s = navigator.userAgent;
	this.IE = s.indexOf("MSIE") != -1;
	this.FX = s.indexOf("Firefox") != -1;
	this.MZ = s.indexOf("Mozila") != -1;
	this.OP = s.indexOf("Opera") != -1;
	this.SAFARI = s.indexOf("AppleWebKit") != -1;
	
	this.IE6= s.indexOf("MSIE 6") != -1;
	this.IE7= s.indexOf("MSIE 7") != -1;
	this.IE8= s.indexOf("MSIE 8") != -1;
	this.IE9= s.indexOf("MSIE 9") != -1;
	this.FX2 = s.indexOf("Firefox/2") != -1;
	this.FX3 = s.indexOf("Firefox/3") != -1;
	this.GOOGLE = s.indexOf("Chrome") != -1;
	
	var s2 = navigator.appVersion.toLowerCase();
	this.WIN = s2.indexOf("win") != -1;
	this.MAC = s2.indexOf("mac") != -1;
	
	// 判断操作系统
	this.WIN2000= s.indexOf("NT 5.0") != -1;
	this.XP= s.indexOf("NT 5.1") != -1;
	this.WIN2003= s.indexOf("NT 5.2") != -1;
	this.VISTA= s.indexOf("NT 6.0") != -1;
	this.WIN2008= s.indexOf("NT 6.1") != -1;
	this.WIN7= s.indexOf("NT 7.0") != -1;
	
	this.IPOD = s.indexOf("iPod") != -1;
	this.IPHONE = s.indexOf("iPhone") != -1;
	this.IPAD = s.indexOf("iPad") != -1;
}
//================================Class Browser===============================
var _browser = new Browser();
//================================Class Browser===============================
