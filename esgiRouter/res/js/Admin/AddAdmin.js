$(function(){
	Esgi.module = Esgi.module || {}
	Esgi.module.Admin = Esgi.module.Admin || {}

	Esgi.module.Admin.AddAdmin = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/Admin/AddAdmin',
			renderTo : cfg.id,
			inputs : [
			          {
			        	  type : "Text",
			        	  name : 'nom',
			        	  label : "Nom",
			        	  emptyText : "",
			        	  required : true
			          },
			          {
			        	  type : "Text",
			        	  name : 'prenom',
			        	  label : "Prénom",
			        	  emptyText : "",
			        	  required : true
			        		
			          },
			          {
			        	  type : "Email",
			        	  name : 'email',
			        	  label : "Adresse e-mail",
			        	  emptyText : "",
			        	  required : true
			          },
			          {
			        	  type : "Text", 
			        	  name : 'login',
			        	  label : "Login",
			        	  emptyText : "",
			        	  required : true
			          },
			          {
			        	  type : "Password",
			        	  name : 'password',
			        	  label : "Mot de passe",
			        	  emptyText: "",
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
			message.html("<h2> Nouvel administrateur créé</h2>");
			$("input").val('');
		}
		else{
			var message = $("#errorMessage");
			message.css("visibility","visible");
			message.css("-webkit-animation","notificationError 1s");
			message.css("animation","notificationError 1s");
			messsage.html(data["message"]);
		}
		
	}

});