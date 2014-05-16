$(function(){
	Esgi.module = Esgi.module || {}
	Esgi.module.Contact = Esgi.module.Contact || {}

	Esgi.module.Contact.Form = function (cfg) {
		new Esgi.html.Form({
			url : APP_CONTEXT+'/menu/contact/Contact',
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
			        	  label : "Prenom",
			        	  emptyText : '' 
			          },
			          {
			        	  type : "Email",
			        	  name : 'email',
			        	  label : "nom@mail.fr",
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