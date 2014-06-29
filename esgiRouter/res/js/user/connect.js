$(function(){
	Esgi.module = Esgi.module || {}
	Esgi.module.user = Esgi.module.user || {}

	Esgi.module.user.Connect = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/user/connect',
			renderTo : cfg.id,
			inputs : [
			          {
			        	  type : "Text",
			        	  name : 'login',
			        	  label : "Login",
			        	  emptyText : 'Login',
			        	  required : true
			          },{
			        	  type : "Password",
			        	  label : "Password",
			        	  name : 'password',
			        	  emptyText : 'Password',
			        	  required : true
			          }
			          ],
			action : AjaxConnect
		});
		
	}
	
	function AjaxConnect(data){
		if(data["success"]){
			if(window.location.href.toString().indexOf('Connect')!=-1)
				document.location.href ="/esgiRouter"; // En localhost le slash ne suffit pas
			else
				window.location.reload();
		}
		else{
			var message = $("#errorMessage");
			message.css("visibility","visible");
			message.css("-webkit-animation","notificationError 1s");
			message.css("animation","notificationError 1s");
			$("#errorMessage").html("<h2>Login ou mot de passe incorrect.</h2>");		
		}
			
	}

});