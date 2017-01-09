/*
* jQuery Frame Dialog 1.1.2
*
* Copyright (c) 2009 SolutionStream.com & Michael J. Ryan (http://www.solutionstream.com/)
* Dual licensed under the MIT (MIT-LICENSE.txt)
* and GPL (GPL-LICENSE.txt) licenses.
* 
* Requires:
*	jquery ui dialog
*
*	jQuery.FrameDialog namespaced
*		.create()	function will create an iframe, pass on the options 
*					and return from a jQueryUI Dialog.
*					additional url option
*
*	TODO:	- Add logic to allow for relative URLs.
*
*	CUSTOMIZATION:
*		see FrameDialog._defaultOptions below for additional changes from ui dialog defaults
*		Returns a jQuery.dialog extension, with the same options passed in.
*
*		refer to jQueryUI Dialog for more customization options
*
*
*	LOCALIZATION: create a window.localization object
*				localization.OK			override text for the OK button
*				localization.Cancel		override text for the Cancel button
*
*
*	FROM PARENT WINDOW: use the full url, including protocol://host/ portions
*				jQuery.FrameDialog
*					.create({
*						url: baseURL + 'framed-modal-test.aspx',
*						title: 'test title',
*						... Other jQueryUI Dialog Options ...
*					})
*					.bind('dialogclose', function(event, ui) {
*						alert("result: " + event.result);
*					});
*
*	INSIDE MODAL:
*				jQuery.FrameDialog.setResult(value);	//sets the result value
*				jQuery.FrameDialog.clearResult();		//clears the result value
*				jQuery.FrameDialog.closeDialog();		//close the dialog (same as OK)
*				jQuery.FrameDialog.cancelDialog();		//cancel the dialot (same as Cancel
*
*
*
*	!!!!!!!!!! WARNING WARNING WARNING WARNING !!!!!!!!!!
*	Modal must set the result from the same host address in order to access 
*	the parent for setting the result.
*/

(function($) {

	//create FrameDialog namespace
	$.FrameDialog = $.FrameDialog || {};
	//array for return values, placeholder
	$.FrameDialog._results = $.FrameDialog._results || {};

	//set localized variables
	var OK = (window.localization && window.localization.OK) || "确定";
	var Cancel = (window.localization && window.localization.CANCEL) || "取消";
	var buttons = {};
    buttons[Cancel] = function() {
				var frame = $(this);
				//$.FrameDialog.clearResult(frame.attr("id"));
				frame.dialog("close");
			};
	buttons[OK] = function() {
				$(this).dialog("close");
			};


	//default options
	$.FrameDialog._defaultOptions = {
		modal: true,
		closeOnEscape: false,
		position: 'center',
		buttons: buttons
	}

	var retry = 0;
	$.FrameDialog.create = function(options) {
		try	{
			//generate unique id
			var uid = Math.random().toString(16).replace(".", "") + (new Date()).valueOf().toString(16);

			//extend frame dialog options with passed in options.
			var opts = $.extend(
				$.FrameDialog._defaultOptions,
				options || {}
			)

			var url = (opts && opts.url) || null;
			if (url === null)
				throw new Error("MODAL ERROR: Option 'url' not specified!"); //diagnostic error

			//clean up redundant forward slashes in the url.
			url = url.replace(/(^|[^:])\/+/g, "$1/");

			//remove url argument from options to be passed to dialog.
			try {
				delete opts.url;
			} catch (err) { }

			//create iframe object
			//	object type="text/html doesn't seem to work in IE :(
			//	using iframe, which seems to work cross browser, tested in IE7, and Firefox 3.0.7
		/*	var iframe = $("<iframe frameborder='0' scrolling='auto' background='transparent' />")
				.attr("id", uid + "-VIEW")
				.attr("name", uid + "-VIEW")
				.attr("src", url)
				.css("margin", "0")
				.css("border", "0")
				.css("padding", "0")
				.css("top", "0")
				.css("left", "0")
				.css("right", "0")
				.css("bottom", "0")
				.css("width", "100px")
				.css("height", "100px")
				;*/
            var iframeBoolean =false;
            var iframe ;
            if( $("#dialogIframe").attr("id"))
              iframe = $("#dialogIframe");
            else
            {
                 iframe = $("<iframe frameborder='0' scrolling='auto' background='transparent' />");
                iframe.attr("id","dialogIframe");
                iframe.css("height","100%");
                iframe.css("weight","100%");
                iframeBoolean =true;
            }
			/*var overlay = $("<div>&nbsp;</div>")
				.css("position", "absolute")
				.css("margin", "0")
				.css("border", "0")
				.css("padding", "0")
				.css("top", "0")
				.css("left", "0")
				.css("right", "0")
				.css("bottom", "0")
				.css("width", "100%")
				.css("height", "100%")
				.css("display", "none")
				;*/
            var dialogboolean =false;
            var ret
             iframe.attr("src",url);
            if($("#frameDialog").attr("id"))
			 ret = $("#frameDialog");
            else
            {
               ret =$("<div></div>");
               ret.attr("width","100%");
                 ret.attr("height","100%");
               ret.attr("id","frameDialog");
                dialogboolean = true;
            }
             if(dialogboolean&&iframeBoolean)
             {
                 ret.append(iframe).appendTo(document.body);
                 dialogboolean = false;
                 iframeBoolean =false; 
             }
            if(options.callback)
            ret.attr("option","1");
				/*.attr("id", uid)
				.css("margin", "0")
				.css("border", "0")
				.css("padding", "0")
				.css("top", "0")
				.css("left", "0")
				.css("right", "0")
				.css("bottom", "0")
				.css("overflow", "hidden")
				.hide()
				.append(iframe)
				.append(overlay)
				.appendTo(document.body)*/
		/*		ret.bind("dialogbeforeclose", function(event, ui) {
					var frame = $(this);
					var uid = frame.attr("id");

					//default close (firefox) - clear response
					if (event && event.originalTarget && event.originalTarget.nodeName && event.originalTarget.nodeName == "SPAN")
						$.FrameDialog.clearResult(uid);

					//default close (IE7) - clear response
					if (event && event.originalEvent && event.originalEvent.currentTarget && event.originalEvent.currentTarget.tagName && event.originalEvent.currentTarget.tagName == "A")
						$.FrameDialog.clearResult(uid);

					//get the response value, attach to the object.
					var result = $.FrameDialog._results[uid] || null; //result or an explicit null

					return result;
				})*/
				ret.one('dialogclose', function(event, ui) {
                    if( ret.attr("option")=="1")
                    options.callback();
				   iframe.attr("src","");
                    ret.attr("option","0");
                   options.callback =null;  
				})
				ret.dialog(opts)
				;
             var winw = ret.width();
            iframe.css("width",winw);
			/*** BEGIN FIX FOR jQueryUI 1.7 Dialog's sizing bug *************************************************/
			//store the window width at the start
			/*var winw = $(document).width();
			var wrap = ret.parent(".ui-resizable")*/

			//set the window to the appropriate size - fix bug with jQueryUI's Dialog
			//	uses an overlay over the iframe, to prevent focus change.
			/*wrap.css('width', (opts.minWidth || opts.width || 200) + 'px')
					.css('height', (opts.minHeight || opts.height || 120) + 'px')
					.bind('dragstart', function() {
						overlay.show();
					})
					.bind('dragstop', function() {
						overlay.hide();
					})
					.bind('resizestart', function() {
						overlay.show();
					})
					.bind('resize', function() {
						iframe.css('height', ret.height() + 'px')
									.css('width', ret.width() + 'px');
					})
					.bind('resizestop', function() {
						overlay.hide();
						iframe.css('height', ret.height() + 'px')
								.css('width', ret.width() + 'px');
					});


			//force resize event.
			window.setTimeout(function(){ 
				wrap.trigger('resizestop');
			}, 100);

			//get window's new width
			var ww = wrap.width();

			//reset the center position, if needed
			if (opts.position == 'center' || (opts.position && (opts.position[0] == 'center' || opts.position[1] == 'center'))) {
				var pos = parseInt(parseFloat(winw - ww) / 2);
				if (pos < 0) pos = 0;

				wrap.css('left', pos + 'px');
			}

			//reset right position, as needed
			if (opts.position == 'right' || (opts.position && (opts.position[0] == 'right' || opts.position[1] == 'right'))) {
				var pos = winw - ww - 5; //set to rightmost, - 5 px
				if (pos < 0) pos = 0;

				wrap.css('left', pos + 'px');
			}
			*//*** END FIX FOR jQueryUI 1.7 Dialog's sizing bug *************************************************//*
			retry = 0; //reset the retry state.*/
			return ret;
		} catch (err) {
			//cleanup any left over ui elements...
			/*try { ret.dialog('destroy'); } catch(err) {}
			try { ret.remove(); } catch(err) {}
			try { overlay.remove(); } catch(err) {}
			try { iframe.remove(); } catch(err) {}

			//it was a TypeError (seems to be happening with the modal's overlay mask, retry once.
			if (err instanceof TypeError && retry < 10) {
				if (console && typeof console.dir == "function")
					console.dir({'Error Creating FrameDialog':{'error':err, 'retry':retry}});
				retry++;
				return $.FrameDialog.create(options); //retry once only
			}
			retry = 0; //reset the retry state.
			
			throw err; //rethrow error if something else*/
		}
	}

	//retrieves the uid for the current frame within the parent.
	/*$.FrameDialog._getUid = function() {
		//find the current frame within the parent window
		if (window.parent && window.parent.frames && window.parent.document && window.parent.document.getElementsByTagName) {
			var iframes = window.parent.document.getElementsByTagName("IFRAME");
			for (var i = 0; i < iframes.length; i++) {
				var id = iframes[i].id || iframes[i].name || i;
				if (window.parent.frames[id] == window) {
					return id.replace("-VIEW", "");
				}
			}
		}
		return null; //no match
	}

	//Returns the current dialog handle in the parent window as a jquery object.
	$.FrameDialog.current = function() {
		if (window.parent && window.parent.jQuery)
			return window.parent.jQuery("#" + $.FrameDialog._getUid());
		
		return null;
	}


	//clear the result value
	//	uid - id for child window, or empty for current in parent.
	$.FrameDialog.clearResult = function(uid) {
		if (uid) {
			//clear child's value
			try {
				delete $.FrameDialog._results[uid];
			} catch (err) { *//*nothing to delete*//* }
		} else {
			//clear for current dialog
			var uid = $.FrameDialog._getUid();

			if (uid != null && window.parent && window.parent.jQuery && window.parent.jQuery.FrameDialog && window.parent.jQuery.FrameDialog._results) {
				try {
					delete window.parent.jQuery.FrameDialog._results[uid];
				} catch (err) { *//*nothing to delete*//* }
			}
		}
	}

	//helper function to set response value	to the parent
	//	value - result value for the given FrameDialog
	//	uid - child id, or empty for current FrameDialog
	$.FrameDialog.setResult = function(value, uid) {
		if (uid) {
			//set child value
			jQuery.FrameDialog._results[uid] = value;
		} else {
			//set value from inside
			var uid = $.FrameDialog._getUid();

			if (uid != null && window.parent && window.parent.jQuery && window.parent.jQuery.FrameDialog && window.parent.jQuery.FrameDialog._results) {
				window.parent.jQuery.FrameDialog._results[uid] = value;
			}
		}
	}

	//same as clicking OK button
	//	uuid - for a child node, or empty for current
	$.FrameDialog.closeDialog = function(uid) {
		if (uid) {
			//close child
			jQuery("#" + uid).dialog('close');
		} else {
			//close self			
			var uid = $.FrameDialog._getUid();
			if (uid != null && window.parent && window.parent.jQuery) {
				window.parent.jQuery("#" + uid).dialog('close');
			}
		}

		return false;
	}

	//same as clicking Cancel button
	//	uid - for a child node, or empty for current frame
	$.FrameDialog.cancelDialog = function(uid) {
		$.FrameDialog.clearResult(uid);
		$.FrameDialog.closeDialog(uid);
		return false;
	}


	//extension methods for shortcuts in dealing with a framedialog handle
	$.fn.setResult = function(result) {
		return this.each(function() {
			$.FrameDialog.setResult(result, $(this).attr("id"));
		});
	}

	$.fn.clearResult = function() {
		return this.each(function() {
			$.FrameDialog.clearResult($(this).attr("id"));
		});
	}
	
	$.fn.closeDialog = function() {
		return this.dialog('close');
	}

	$.fn.cancelDialog = function() {
		return this.clearResult().closeDialog();
	}
	
	//get the window context for the object/iframe in question
	$.fn.window = function() {
		//this item is a frame or iframe
		if (this.attr('tagName') == "IFRAME" || this.attr('tagName') == "FRAME")
			return window.frames[this.attr('name') || this.attr('id')];
			
		//get the first frame/iframe child
		var frame = this.find('iframe, frame')[0];
		return (frame && window.frames[frame.name || frame.id]) || null;
	}
*/
})(jQuery);



