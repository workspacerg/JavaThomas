$(function(){
	(function($){
		   $.fn.outside = function(ename, cb){
		      return this.each(function(){
		         var $this = $(this),
		              self = this;
		         $(document.body).bind(ename, function tempo(e){
		             if(e.target !== self && !$.contains(self, e.target)){
		                cb.apply(self, [e]);
		                if(!self.parentNode) $(document.body).unbind(ename, tempo);
		             }
		         });
		      });
		   };
		}(jQuery));
	
	var isOpenFirst = false;
	$('#container').outside('click', function(e){
		if(isOpenFirst){
			isOpenFirst = false;
			e.preventDefault();
			return false;
		}
		if($("#container").is(":visible"))
			$("#container").slideUp( "slow");
	});
	
	$("#changePassword").click(function(){
			isOpenFirst = true;
			$("#container").slideDown( "slow");
	});
	
	Esgi.module = Esgi.module || {}
	Esgi.module.Information = Esgi.module.Information || {}

	Esgi.module.Information.Form = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/Profil/SaveInformation',
			renderTo : cfg.id,
			inputs : [
				          {
				        	  type : "Password",
				        	  name : 'newPassword',
				        	  label : "Nouveau mot de passe",
				        	  emptyText : "",
				        	  required : true
				          },
				          {
				        	  type : "Password",
				        	  name : 'confirmPassword',
				        	  label : "Confirmer le mot de passe",
				        	  emptyText : "",
				        	  required : true
				        		
				          }
			          ],
			     action : AjaxComplete
		});
		
	}
	
	function AjaxComplete(data){
		if(data["success"]){
			var message = $("#errorMessage");
			message.css("visibility","visible");
			message.css("-webkit-animation","notificationError 1s");
			message.css("animation","notificationError 1s");
			$("#errorMessage").html("<h2> Mise à jour du mot de passe effectuée.</h2>");
			$(":input").val("");
			$("#container").slideUp( "slow");
		}
		else{
			var message = $("#errorMessage");
			message.css("visibility","visible");
			message.css("-webkit-animation","notificationError 1s");
			message.css("animation","notificationError 1s");
			alert(data["message"]);
			$("#errorMessage").html(data["message"]);
		}
			
		
	}
});