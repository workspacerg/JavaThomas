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
			        	  name : 'login',
			        	  label : "Blah",
			        	  emptyText : 'Login' 
			          },{
			        	  type : "Password",
			        	  label : "Password",
			        	  name : 'password',
			        	  emptyText : 'Password' 
			          },{
			        	  type : "Select",
			        	  label : "Blah",
			        	  name : 'select',
			        	  emptyText : 'Blah' 
			          }
			          ]
		});
		
	}

});