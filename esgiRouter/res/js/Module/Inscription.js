$(function(){
	Esgi.module = Esgi.module || {}
	Esgi.module.Inscription = Esgi.module.Inscription || {}

	Esgi.module.Inscription.Form = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/index/SaveInscription',
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
			$("#container").html("<h2> Votre enregistrement a bien été pris en compte</h2>");
			$("#container").append("<a href=\"/esgiRouter/\">Retour à la page de connexion.</a>");
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