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
		if(data["success"])
			window.location.reload();
		else
			$("#errorMessage").html("<h1 style='color:red;'>Login ou mot de passe incorrect.</h1>");		
	}

});