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
			        	  name : 'sujet',
			        	  label : "Sujet",
			        	  emptyText : ''
			          },
			          {
			        	  type : "Text",
			        	  name : 'nom',
			        	  label : "Nom",
			        	  emptyText : '' 
			          },
			          {
			        	  type : "Text",
			        	  name : 'prenom',
			        	  label : "Prénom",
			        	  emptyText : '' 
			          },
			          {
			        	  type : "Email",
			        	  name : 'email',
			        	  label : "Adresse e-mail",
			        	  emptyText : '' 
			          },
			          {
			        	  type : "Textarea", 
			        	  name : 'message',
			        	  label : "Message",
			        	  emptyText : '',
			        	  rows : "20",
			        	  cols : "38"
			          }
			          ]
		});
		
	}

});