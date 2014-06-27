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
			$("#container").html("<h2> Votre message a bien été transmis à  notre équipe.</h2>");
			$("#container").append("<a href=\" /esgiRouter \">Retour à  l'accueil</a>");
		}
		else
			$("#errorMessage").html(data["message"]);
	}

});