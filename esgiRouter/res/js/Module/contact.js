$(function(){
	Esgi.module = Esgi.module || {}
	Esgi.module.Contact = Esgi.module.Contact || {}

	Esgi.module.Contact.Form = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/Contact/SendContact',
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
			        	  type : "Textarea", 
			        	  name : 'sujet',
			        	  label : "Sujet",
			        	  emptyText : "",
			        	  rows : "15",
			        	  cols : "35",
			        	  required : false
			          }
			          ],
			    action: AjaxComplete
		});
		
	}
	
	function AjaxComplete(data){
		if(data["success"]){
			$("#container").html("<center><h2> Votre message a bien été transmis à  notre équipe.</h2></center>");		
			$("#container").append('<center><a href="/esgiRouter/" style="background-color: #FF9500;padding: 9px; color: white; text-decoration: none;">Retour à l\'accueil<a></center>');
		}
		else{
			var message = $("#errorMessage");
			message.css("visibility","visible");
			message.css("-webkit-animation","notificationError 1s");
			message.css("animation","notificationError 1s");
			message.html(data["message"]);
		}
	}

});