import $ from 'jquery'
import queryString from 'query-string'

exports.getUrlParam = (name) => {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'),
			r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	} else {
		return null;
	}
}

exports.mmPost = (url,params) => {
	return _ajax(url,params,'POST');
}

exports.mmGet =  (url,params) => {
	return _ajax(url,params,'GET');
}

function _ajax(url,params,method,token) {
	var defer = $.Deferred();

	 $.ajax({
				type: method,
				url : url,
				data: params,
				timeout: 30000,
				dataType:"json",
				xhrFields: {
					 withCredentials: true
					},
				success: function(data) {
						// 判断是否登录
						if (data.code === -2) {
							var _url = data.loginUrl.split('?')[0];
							var _query = data.loginUrl.split('?')[1];
							var _obj = queryString.parse(_query);
							_obj.returnUrl = window.location.protocol + '//' + window.location.host + window.location.pathname + window.location.search;
							window.location.replace(_url+'?'+queryString.stringify(_obj));
						} else {
							defer.resolve(data);
						}
				},
				error: function(err) {
						defer.reject(err);
				}
			});

	 return defer.promise();
}


// 显示错误
exports.showErr = (that,err) => {
	that.setState({
			isShowErr:true,
			err:err
	});
	that.timer = setTimeout(function() {
		that.setState({
			isShowErr:false
		})
	},1500);
}

// 清除定时器
exports.clearTimer = (that)  => {
	clearTimeout(that.timer);
}
