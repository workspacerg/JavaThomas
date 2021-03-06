var matched, browser;

jQuery.uaMatch = function( ua ) {
    ua = ua.toLowerCase();

    var match = /(chrome)[ \/]([\w.]+)/.exec( ua ) ||
        /(webkit)[ \/]([\w.]+)/.exec( ua ) ||
        /(opera)(?:.*version|)[ \/]([\w.]+)/.exec( ua ) ||
        /(msie) ([\w.]+)/.exec( ua ) ||
        ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec( ua ) ||
        [];

    return {
        browser: match[ 1 ] || "",
        version: match[ 2 ] || "0"
    };
};

matched = jQuery.uaMatch( navigator.userAgent );
browser = {};

if ( matched.browser ) {
    browser[ matched.browser ] = true;
    browser.version = matched.version;
}

// Chrome is Webkit, but Webkit is also Safari.
if ( browser.chrome ) {
    browser.webkit = true;
} else if ( browser.webkit ) {
    browser.safari = true;
}

jQuery.browser = browser;

var global = this,
loadMyLib = function(onloaded){

	global.Esgi = {};
	global.Esgi.html = {};

//	/
//	/  FORM
//	/


	/**
	 **  @cfg.renderTo : Dom css identifier
	 **  @cfg.url : url to submit form.
	 **  @cfg.inputs : list of inputs cfg
	 **  @cfg.action : action to perform on click
	 */

	global.Esgi.html.Form = function(cfg) {
		var me = this;
		me.cfg = cfg;
		me.render();
		me.submit = $("<button>Envoyer</button>");
		me.initInputs();
		//$(me.cfg.renderTo).append(me.submit);
		me.submit.on('click', function(e){me.onButtonClick(e)});
	};

	global.Esgi.html.Form.prototype = {
			initInputs : function(){
				var me = this;
				me._inputs = {};
				me.table = $('<table />');
				
				
				var colgroup = $('<colgroup/>');
				var col1 = $('<col/>');
				col1.css("width","0px");
				var col2 = $('<col/>')
				col2.css("width","140px");
				colgroup.append(col1,col2);

				me.table.append(colgroup);
				me.el.append(me.table);

				var tfoot = $('<tfoot/>');
				me.table.append(tfoot);
				
				var ligneFoot = $("<tr />");
				var cellFoot = $("<td colspan=\"2\"/>");
				cellFoot.css("height","40px");
				cellFoot.css("vertical-align","bottom");
				ligneFoot.append(cellFoot);
				var center = $("<center/>");

				center.append(me.submit);
				cellFoot.append(center);
				tfoot.append(ligneFoot);

				$.each(this.cfg.inputs, function(idx, item) {
					var elem = $("<tr/>");
					me.table.append(elem);
					item.renderTo = elem;
					me._inputs[item.name] = new Esgi.html.inputs[item.type](item); 
				});
			},
			render : function(){
				this.el = $("<form/>");
				$(this.cfg.renderTo).html(this.el)
			},
			onButtonClick : function(e) {
				var me = this, data = {};
				
				if(!$(this.el)[0].checkValidity()){
					$(this.el)[0].removeClass('success').addClass('error');
					e.preventDefault();
					return false;
				}

				var error = false;
				$.each(me._inputs, function(key, item) {
					if(item.isEmpty()){
						var message = $("#errorMessage");
						message.css("visibility","visible");
						message.css("-webkit-animation","notificationError 1s");
						message.css("animation","notificationError 1s");
						$("#errorMessage").html("<h2>Veuillez entrer une valeur pour : "+item.getName()+"</h2>");
						error = true;
						return false;
					}

					data[key] = item.getValue();
				});
				
				if(error){
					e.preventDefault();
					return false;
				}
				if(me.cfg.ajaxReplace){
					me.cfg.ajaxReplace(me.cfg.url,data);
				}
				else{
					$.ajax({
							url : me.cfg.url,
							method : 'POST',
							data : data,
							contentType: "application/x-www-form-urlencoded;charset=ISO-8859-1",
							success : function(response) {
								obj = JSON.parse(response);
								// Appel de l'action
								me.cfg.action.call(this,obj);
							}
		
						});
				}
				e.preventDefault();
				return false;
			}
	};

//	INPUTS

	var commons = {
			init : function(){
				var me = this;
				if (me.cfg.label) {
					var label = $('<label>');
					label.css("color","black");
					td1 = $('<td/>');
					td1.append(label)
					td2 = $('<td/>');
					td2.append(me.el);
					//label.text(me.cfg.label);
					$(me.cfg.renderTo).append(td1,td2);
				}
				else
					$(me.cfg.renderTo).append(me.el);
			},
			getValue : function(){
				return $(this.el).val();
			},
			isEmpty : function(){
				if(!this.cfg.required || this.cfg.content)
					return false;
				
				if($.trim($(this.el).val()) != '' || $(this.el).val() == null)
					return false;
				return true;
			},
			getName : function(){
				return this.cfg.name;
			}
	}

	Esgi.html.inputs = {};
	Esgi.html.inputs.Text = function(cfg){
		var me = this;
		me.cfg = cfg;
		me.el = $("<input placeholder='"+me.cfg.label+"'/>");
		this.init();

	}

	Esgi.html.inputs.Text.prototype = commons;

	Esgi.html.inputs.Password = function(cfg){
		var me = this;
		me.cfg = cfg;
		me.el = $("<input type='password' placeholder='"+me.cfg.label+"'/>");
		me.init();

	}
	Esgi.html.inputs.Password.prototype = commons;

	Esgi.html.inputs.Select = function(cfg){
		var me = this;
		me.cfg = cfg;
		me.el = $("<select/>");
		me.init();

	}
	Esgi.html.inputs.Select.prototype = commons;
	
	Esgi.html.inputs.Textarea = function(cfg){
		var me = this;
		me.cfg = cfg;
		me.el = $("<textarea rows='"+ me.cfg.rows + "' cols='" +me.cfg.cols+"' placeholder='"+me.cfg.label+"'/>");
		me.init();

	}
	Esgi.html.inputs.Textarea.prototype = commons;
	
	Esgi.html.inputs.Email = function(cfg){
		var me = this;
		me.cfg = cfg;
		me.el = $("<input type='email' name='"+me.cfg.label+"' placeholder='"+me.cfg.label+"'/>");
		me.init();

	}
	Esgi.html.inputs.Email.prototype = commons;
	
	Esgi.html.inputs.File = function(cfg){
		var me = this;
		me.cfg = cfg;
		me.el = $("<input type='file' id='imgFilmFile_"+me.cfg.label+"' name='"+me.cfg.label+"' accept='image/jpeg' />");
		me.init();

	}
	Esgi.html.inputs.File.prototype = commons;
	
	Esgi.html.inputs.innerHtml = function(cfg){
		var me = this;
		me.cfg = cfg;
		me.el = me.cfg.content;
		me.init();
	}
	
	Esgi.html.inputs.innerHtml.prototype = commons;
	
	Esgi.html.inputs.Time = function(cfg){
		var me = this;
		me.cfg = cfg;
		me.el = $("<input type='text' id='timepick_"+me.cfg.name+"' placeholder='"+me.cfg.label+"'/>");
		me.init();
		$("#timepick_"+me.cfg.name).timepicker();
	}
	
	Esgi.html.inputs.Time.prototype = commons;
}

$(loadMyLib);



