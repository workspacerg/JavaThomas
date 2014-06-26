$(function(){
	Esgi.module = Esgi.module || {}
	Esgi.module.Contact = Esgi.module.Contact || {}

	Esgi.module.Contact.Form = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/menu/contact/SendContact',
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
			        	  label : "Prenom",
			        	  emptyText : "", 
			        	  required : true
			          },
			          {
			        	  type : "Email",
			        	  name : 'email',
			        	  label : "nom@mail.fr",
			        	  emptyText : "",
			        	  required : true
			          },
			          {
			        	  type : "Textarea", 
			        	  name : 'sujet',
			        	  label : "Sujet",
			        	  emptyText : "",
			        	  rows : "20",
			        	  cols : "38",
			        	  required : false
			          }
			          ],
			    action: AjaxComplete
		});
		
	}
	
	function AjaxComplete(data){
		if(data["success"])
			$("#container").html("<h2> Votre message a bien été transmis à notre équipe.</h2>");
		else
			$("#errorMessage").html(data["message"]);
	}

});